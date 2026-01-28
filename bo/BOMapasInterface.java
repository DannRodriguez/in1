package mx.ine.procprimerinsa.bo;

import java.util.List;
import java.util.Map;

import mx.ine.procprimerinsa.dto.db.DTOCategoriasProceso;
import mx.ine.procprimerinsa.dto.DTOEstado;
import mx.ine.procprimerinsa.dto.DTOEstatusParticipacion;
import mx.ine.procprimerinsa.dto.DTOGrupoHorarioInsaculacion;
import mx.ine.procprimerinsa.dto.DTOMapaInformacion;
import mx.ine.procprimerinsa.dto.db.DTOCEstatusProceso;
import mx.ine.procprimerinsa.dto.db.DTOHorariosInsaculacion;

public interface BOMapasInterface {
	
	public List<DTOGrupoHorarioInsaculacion> getGrupos(List<DTOHorariosInsaculacion> horarios,
			Map<Integer, DTOEstado> estados);

	public Map<Integer, DTOCEstatusProceso> convetirCategoriasToEstatus(List<DTOCategoriasProceso> listaCategorias);
	
	public List<DTOEstatusParticipacion> obtenerEstadisticasParticipacion(List<DTOMapaInformacion> listaMapaInformacion,
			Map<Integer, DTOCEstatusProceso> listaEstatus);
	
	public List<DTOGrupoHorarioInsaculacion> calcularPorcentajeGrupalPorEstatus(
			List<DTOMapaInformacion> listaMapaInformacion, int i, List<DTOGrupoHorarioInsaculacion> grupos);

}
