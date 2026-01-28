package mx.ine.procprimerinsa.as;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Map;

import mx.ine.procprimerinsa.dto.DTOEstadoGeneral;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.db.DTOLlavesProcesos;
import mx.ine.procprimerinsa.form.FormGenerarLlaves;

public interface ASGenerarLlavesInterface extends Serializable {
	
	public Map<Integer, String[]> obtenerLlavesProceso(Integer idDetalleProceso, Integer modoEjecucion);
	
	public void generarLlaves(String nombreProceso, Map<Integer, DTOEstadoGeneral> estados, FormGenerarLlaves form) throws Exception;
	
	public String generaLlave(String funcionPicadillo, DTOLlavesProcesos llave) throws Exception;
		
	public void reiniciarLlaves(String nombreProceso, Map<Integer, DTOEstadoGeneral> estados, FormGenerarLlaves form) throws Exception;
	
	public ByteArrayOutputStream descargarLlaves(DTOEstadoGeneral estado, DTOParticipacionGeneral distrito, 
			FormGenerarLlaves form) throws Exception;

}
