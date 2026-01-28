package mx.ine.procprimerinsa.pao;

import java.io.Serializable;

import mx.ine.procprimerinsa.dao.DAOGenericInterface;

public interface PAOAdminIndicesDIInterface extends DAOGenericInterface<Serializable, Serializable> {
	
	public String creaEliminaIndices(boolean isCrea);

}
