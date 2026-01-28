package mx.ine.procprimerinsa.bsd.impl;

import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASMapasInterface;
import mx.ine.procprimerinsa.as.ASParametrosInterface;
import mx.ine.procprimerinsa.as.ASProcesoInsaInterface;
import mx.ine.procprimerinsa.bo.BOMapasInterface;
import mx.ine.procprimerinsa.bsd.BSDMapasInterface;
import mx.ine.procprimerinsa.dto.DTOEstado;
import mx.ine.procprimerinsa.dto.DTOEstadoDetalles;
import mx.ine.procprimerinsa.dto.DTOEstatusParticipacion;
import mx.ine.procprimerinsa.dto.DTOGrupoHorarioInsaculacion;
import mx.ine.procprimerinsa.dto.DTOMapaInformacion;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.db.DTOCEstatusProceso;
import mx.ine.procprimerinsa.dto.db.DTOCategoriasProceso;
import mx.ine.procprimerinsa.dto.db.DTOHorariosInsaculacion;
import mx.ine.procprimerinsa.dto.db.DTOMapas;
import mx.ine.procprimerinsa.form.FormMapas;
import mx.ine.procprimerinsa.util.Constantes;

@Component("bsdMapas")
@Scope("prototype")
public class BSDMapas implements BSDMapasInterface {

	private static final long serialVersionUID = 2961138521323137567L;
	private static final Logger logger = Logger.getLogger(BSDMapas.class);
	
	@Autowired
	@Qualifier("asMapas")
	private ASMapasInterface asMapas;
	
	@Autowired
	@Qualifier("asProcesoInsa")
	private ASProcesoInsaInterface asProcesoInsa;
	
	@Autowired
	@Qualifier("asParametros")
	private ASParametrosInterface asParametros;
	
	@Autowired
	@Qualifier("boMapas")
	private transient BOMapasInterface boMapas;
	
	@Override
	public List<DTOMapas> getBaseGrafica(Integer idEstado) {
		return asMapas.getBaseGrafica(idEstado);
	}
	
	@Override
	public List<DTOMapaInformacion> obtenerDatosMapasBD(List<Integer> estados, List<Integer> detalles, 
			Integer idCorte){
		return asMapas.obtenerDatosMapasBD(estados, detalles, idCorte);
	}
	
	@Override
	public List<Integer> getDetallesProceso(Integer idProceso, String tipoEleccion) {
		return asMapas.getDetallesProceso(idProceso, tipoEleccion);
	}
	
	@Override
	public List<DTOEstadoDetalles> getDetallesProcesoHistoricos(Integer idEstado, Integer idDistrito) {
		return asMapas.getDetallesProcesoHistoricos(idEstado, idDistrito);
	}
	
	@Override
	public Map<Integer, DTOEstado> getEstados(List<Integer> detalles, Integer idCorte) {
		return asMapas.getEstados(detalles, idCorte);
	}
	
	@Override
	public Map<Integer, DTOParticipacionGeneral> getParticipaciones(Integer idDetalle, Integer idCorte) {
		return asMapas.getParticipaciones(idDetalle, idCorte);
	}
	
	@Override
	public List<DTOCategoriasProceso> getListaCategorias(Integer idDetalleProceso) {
		return asProcesoInsa.obtenerListaCategorias(idDetalleProceso);
	}
	
	@Override
	public List<DTOHorariosInsaculacion> obtenerHorarios(List<Integer> detalles) {
		return asProcesoInsa.obtenerHorarios(detalles);
	}
	
	@Override
	public boolean obtenerParametros(FormMapas form, Integer idProceso, Integer idDetalle, Integer idEstado, Integer id) throws Exception{
		
		String parametro = asParametros.obtenerParametro(idProceso, 
													idDetalle,
													idEstado, 
													id, 
													Constantes.PARAMETRO_POLL_ACTUALIZACION_MAPA);

		if (parametro == null) {
			logger.error("ERROR BSDMapasSeguimiento -obtenerParametros: No se encontró el parámetro de tiempo de poll de mapa");
			return false;
		} else {
			form.setTiempoPoll(Integer.valueOf(parametro));
		}
		
		return true;
	}
	
	@Override
	public List<DTOGrupoHorarioInsaculacion> getGrupos(List<DTOHorariosInsaculacion> horarios,
			Map<Integer, DTOEstado> estados) {
		return boMapas.getGrupos(horarios, estados);
	}
	
	@Override
	public Map<Integer, DTOCEstatusProceso> convetirCategoriasToEstatus(List<DTOCategoriasProceso> listaCategorias) {
		return boMapas.convetirCategoriasToEstatus(listaCategorias);
	}
	
	@Override
	public List<DTOEstatusParticipacion> obtenerEstadisticasParticipacion(List<DTOMapaInformacion> listaMapaInformacion, 
			Map<Integer, DTOCEstatusProceso> listaEstatus) {
		return boMapas.obtenerEstadisticasParticipacion(listaMapaInformacion, listaEstatus);
	}
	
	@Override
	public List<DTOGrupoHorarioInsaculacion> calcularPorcentajeGrupalPorEstatus(
			List<DTOMapaInformacion> listaMapaInformacion, int i, List<DTOGrupoHorarioInsaculacion> grupos) {
		return boMapas.calcularPorcentajeGrupalPorEstatus(listaMapaInformacion, i, grupos);
	}
	
}
