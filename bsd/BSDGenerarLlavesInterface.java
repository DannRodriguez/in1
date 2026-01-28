package mx.ine.procprimerinsa.bsd;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Map;

import mx.ine.procprimerinsa.dto.DTOEstadoGeneral;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.form.FormGenerarLlaves;

public interface BSDGenerarLlavesInterface extends Serializable {
	
	public Map<Integer, String[]> obtenerLlavesProceso(Integer idDetalleProceso, Integer modoEjecucion);
	
	public void generarLlaves(String nombreProceso, Map<Integer, DTOEstadoGeneral> estados, FormGenerarLlaves form) throws Exception;
	
	public void reiniciarLlaves(String nombreProceso, Map<Integer, DTOEstadoGeneral> estados, FormGenerarLlaves form) throws Exception;
	
	public ByteArrayOutputStream descargarLlaves(DTOEstadoGeneral estado, DTOParticipacionGeneral distrito, 
			FormGenerarLlaves form) throws Exception;

	public void obtenerModoEjecucion(FormGenerarLlaves form) throws Exception;

}
