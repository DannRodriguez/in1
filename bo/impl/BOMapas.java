package mx.ine.procprimerinsa.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.bo.BOMapasInterface;
import mx.ine.procprimerinsa.dto.DTOEstado;
import mx.ine.procprimerinsa.dto.DTOEstatusParticipacion;
import mx.ine.procprimerinsa.dto.DTOGrupoHorarioInsaculacion;
import mx.ine.procprimerinsa.dto.DTOMapaInformacion;
import mx.ine.procprimerinsa.dto.db.DTOCEstatusProceso;
import mx.ine.procprimerinsa.dto.db.DTOCategoriasProceso;
import mx.ine.procprimerinsa.dto.db.DTOHorariosInsaculacion;

@Component("boMapas")
@Scope("prototype")
public class BOMapas implements BOMapasInterface {
	
	@Override
	public List<DTOGrupoHorarioInsaculacion> getGrupos(List<DTOHorariosInsaculacion> horarios,
			Map<Integer, DTOEstado> estados) {
		
		Map<Integer, DTOGrupoHorarioInsaculacion> grupos = new LinkedHashMap<>();
		
		for(DTOHorariosInsaculacion horario: horarios) {
			
			grupos.computeIfAbsent(horario.getIdHorario(), key -> {
				DTOGrupoHorarioInsaculacion aux = new DTOGrupoHorarioInsaculacion();
				aux.setIdGrupo(key);
				aux.setHoraInicio(horario.getHorarioEjecucion());
				aux.setHoraFinal(horario.getHorarioFinEjecucion());
				aux.setEstados(new LinkedHashMap<>());
				return aux;
			});
			
			DTOEstado estado = new DTOEstado(horario.getIdEstado(),
															estados.containsKey(horario.getIdEstado()) ?
															estados.get(horario.getIdEstado()).getNombreEstado()
															: " ");
			grupos.get(horario.getIdHorario()).getEstados().put(estado.getIdEstado(), estado);
				
		}
		
		return new ArrayList<>(grupos.values());
	}
	
	@Override
	public Map<Integer, DTOCEstatusProceso> convetirCategoriasToEstatus(List<DTOCategoriasProceso> listaCategorias) {
		Map<Integer, DTOCEstatusProceso> estatus = new LinkedHashMap<>();
		for(DTOCategoriasProceso categoria: listaCategorias){
			DTOCEstatusProceso estatusProceso = new DTOCEstatusProceso();
			estatusProceso.setIdProcesoElectoral(categoria.getIdProcesoElectoral());
			estatusProceso.setIdDetalleProceso(categoria.getIdDetalleProceso());
			estatusProceso.setIdEstatusProceso(categoria.getIdCategoria());
			estatusProceso.setCodigoColorEstatus(categoria.getCodigoColorCategoria());
			estatusProceso.setDescripcionEstatusProceso(categoria.getEtiquetaCategoria());
			estatus.put(estatusProceso.getIdEstatusProceso(), estatusProceso);
		}
		return estatus;
	}

	@Override
	public List<DTOEstatusParticipacion> obtenerEstadisticasParticipacion(List<DTOMapaInformacion> listaMapaInformacion, 
			Map<Integer, DTOCEstatusProceso> listaEstatus) {
		int totalParticipaciones = listaMapaInformacion.size();
		Map<Integer, DTOEstatusParticipacion> estadisticas = new LinkedHashMap<>();
		
		Iterator<DTOCEstatusProceso> itEstatus = listaEstatus.values().iterator();
		while (itEstatus.hasNext()){
			DTOCEstatusProceso estatus = itEstatus.next();
			DTOEstatusParticipacion aux = new DTOEstatusParticipacion();	
			aux.setIdEstatusProceso(estatus.getIdEstatusProceso());
			aux.setDescripcionEstatusProceso(estatus.getDescripcionEstatusProceso());
			aux.setCodigoColorEstatus(estatus.getCodigoColorEstatus());
			estadisticas.put(aux.getIdEstatusProceso(), aux);
		}
		
		for(DTOMapaInformacion mapa: listaMapaInformacion) {
			if(estadisticas.containsKey(mapa.getEstatus())) {
				estadisticas.get(mapa.getEstatus()).setParticipacion(estadisticas.get(mapa.getEstatus()).getParticipacion()+1);
			}
		}
		
		Iterator<DTOEstatusParticipacion> itEstadisticas = estadisticas.values().iterator();
		while (itEstadisticas.hasNext()){
			DTOEstatusParticipacion estadistica = itEstadisticas.next();
			if((float)totalParticipaciones == 0){
				estadistica.setPorcentaje((float)0.00);
			} else {
				estadistica.setPorcentaje((float)(estadistica.getParticipacion()*100)/(float)totalParticipaciones);
			}
		}
		
		return new ArrayList<>(estadisticas.values());
	}

	@Override
	public List<DTOGrupoHorarioInsaculacion> calcularPorcentajeGrupalPorEstatus(
			List<DTOMapaInformacion> listaMapaInformacion, int i, List<DTOGrupoHorarioInsaculacion> grupos) {
		
		Map<Integer, DTOGrupoHorarioInsaculacion> gruposPorcentaje = new LinkedHashMap<>();
		DTOGrupoHorarioInsaculacion grupoPorcentaje;
		Map<Integer, Integer> estados = new HashMap<>();
		
		for(DTOGrupoHorarioInsaculacion grupo : grupos) {
			grupo.setParticipaciones(0);
			grupo.setParticipacionesFinalizadas(0);
			gruposPorcentaje.put(grupo.getIdGrupo(), grupo);
			for(Integer estado : grupo.getEstados().keySet()) {
				estados.put(estado, grupo.getIdGrupo());
			}
		}
		
		for(DTOMapaInformacion participacion: listaMapaInformacion){
			
			if(!estados.containsKey(participacion.getIdEstado())
					|| !gruposPorcentaje.containsKey(estados.get(participacion.getIdEstado()))) {
				continue;
			}
			
			grupoPorcentaje = gruposPorcentaje.get(estados.get(participacion.getIdEstado()));
			
			grupoPorcentaje.setParticipaciones(grupoPorcentaje.getParticipaciones()+1);
			if(participacion.getEstatus() >= i) {
				grupoPorcentaje.setParticipacionesFinalizadas(grupoPorcentaje.getParticipacionesFinalizadas()+1);
			}
		}
		
		for(DTOGrupoHorarioInsaculacion grupo : gruposPorcentaje.values()) {
			if((float)grupo.getParticipaciones() == 0){
				grupo.setPorcentaje((float)0.00);
			}else{
				grupo.setPorcentaje((float)(grupo.getParticipacionesFinalizadas()*100)/(float)grupo.getParticipaciones());
			}
		}
		
		return new ArrayList<>(gruposPorcentaje.values());
		
	}
	
}
