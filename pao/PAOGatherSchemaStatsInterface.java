package mx.ine.procprimerinsa.pao;

import java.io.Serializable;

import mx.ine.procprimerinsa.dao.DAOGenericInterface;

public interface PAOGatherSchemaStatsInterface  extends DAOGenericInterface<Serializable, Serializable> {
	
	public void recolectaEstadisticas();

}
