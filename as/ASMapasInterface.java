package mx.ine.procprimerinsa.as;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mx.ine.procprimerinsa.dto.DTOEstado;
import mx.ine.procprimerinsa.dto.DTOEstadoDetalles;
import mx.ine.procprimerinsa.dto.DTOMapaInformacion;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.db.DTOMapas;

public interface ASMapasInterface extends Serializable {
	
	public List<DTOMapas> getBaseGrafica(Integer idEstado);
	
	public List<DTOMapaInformacion> obtenerDatosMapasBD(List<Integer> estados, List<Integer> detalles, 
			Integer idCorte);
	
	public List<Integer> getDetallesProceso(Integer idProceso, String tipoEleccion);
	
	public List<DTOEstadoDetalles> getDetallesProcesoHistoricos(Integer idEstado, Integer idDistrito);
	
	public Map<Integer, DTOEstado> getEstados(List<Integer> detalles, Integer idCorte);
	
	public Map<Integer, DTOParticipacionGeneral> getParticipaciones(Integer idDetalle, Integer idCorte);

}
