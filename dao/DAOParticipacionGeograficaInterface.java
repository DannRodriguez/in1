package mx.ine.procprimerinsa.dao;

import java.io.Serializable;
import java.util.Map;

import mx.ine.procprimerinsa.dto.db.DTOParticipacionGeografica;

public interface DAOParticipacionGeograficaInterface extends DAOGenericInterface<DTOParticipacionGeografica, Serializable> {

	public Map<Integer, DTOParticipacionGeografica> consultaParticipacionesEstadoProceso(Integer idProceso, 
			Integer idDetalle, Integer idEstado);
	
}
