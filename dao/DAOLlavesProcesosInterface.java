package mx.ine.procprimerinsa.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mx.ine.procprimerinsa.dto.db.DTOLlavesProcesos;

public interface DAOLlavesProcesosInterface extends DAOGenericInterface<DTOLlavesProcesos, Serializable>{
	
	public Map<Integer, String[]> obtenerLlavesProceso(Integer idDetalleProceso, Integer modoEjecucion);
	
	public List<DTOLlavesProcesos> obtenerParLlavesProceso(Integer idDetalleProceso, Integer idParticipacion, 
			Integer modoEjecucion);
	
	public void guardarActualizarLlave(DTOLlavesProcesos llave);

}
