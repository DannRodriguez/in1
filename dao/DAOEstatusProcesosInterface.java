package mx.ine.procprimerinsa.dao;

import java.io.Serializable;

import mx.ine.procprimerinsa.dto.db.DTOEstatusProcesos;

public interface DAOEstatusProcesosInterface extends DAOGenericInterface<DTOEstatusProcesos, Serializable>{
	
	public DTOEstatusProcesos consultaEstatusProceso(Integer idDetalle, Integer idParticipacion);
	
	public void actualizaEstatus(DTOEstatusProcesos estatusProceso);

}
