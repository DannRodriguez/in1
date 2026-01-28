package mx.ine.procprimerinsa.bsd;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

public interface BSDMapasInterface extends Serializable{
	
	public List<DTOMapas> getBaseGrafica(Integer idEstado);
	public List<DTOMapaInformacion> obtenerDatosMapasBD(List<Integer> estados, List<Integer> detalles, Integer idCorte);
	public List<Integer> getDetallesProceso(Integer idProceso, String tipoEleccion);
	public List<DTOEstadoDetalles> getDetallesProcesoHistoricos(Integer idEstado, Integer idDistrito);
	public Map<Integer, DTOEstado> getEstados(List<Integer> detalles, Integer idCorte);
	public Map<Integer, DTOParticipacionGeneral> getParticipaciones(Integer idDetalle, Integer idCorte);
	
	public List<DTOCategoriasProceso> getListaCategorias(Integer idDetalleProceso);
	public List<DTOHorariosInsaculacion> obtenerHorarios(List<Integer> detalles);
	
	public boolean obtenerParametros(FormMapas form, Integer idProceso, Integer idDetalle, Integer idEstado, Integer id)
			throws Exception;
	
	public List<DTOGrupoHorarioInsaculacion> getGrupos(List<DTOHorariosInsaculacion> horarios,
			Map<Integer, DTOEstado> estados);
	public Map<Integer, DTOCEstatusProceso> convetirCategoriasToEstatus(List<DTOCategoriasProceso> listaCategorias);
	public List<DTOEstatusParticipacion> obtenerEstadisticasParticipacion(
			List<DTOMapaInformacion> listaMapaInformacion, Map<Integer, DTOCEstatusProceso> listaEstatus);
	public List<DTOGrupoHorarioInsaculacion> calcularPorcentajeGrupalPorEstatus(List<DTOMapaInformacion> listaMapaInformacion, 
			int i, List<DTOGrupoHorarioInsaculacion> grupos);
	
}
