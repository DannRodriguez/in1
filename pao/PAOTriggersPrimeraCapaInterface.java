package mx.ine.procprimerinsa.pao;

import java.io.Serializable;

import mx.ine.procprimerinsa.dao.DAOGenericInterface;

public interface PAOTriggersPrimeraCapaInterface extends DAOGenericInterface<Serializable, Serializable> {
	
	public String habilitaDeshabilitaTriggers(boolean isHabilita);

}
