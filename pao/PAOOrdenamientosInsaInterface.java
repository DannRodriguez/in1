package mx.ine.procprimerinsa.pao;

import java.io.Serializable;

import mx.ine.procprimerinsa.dao.DAOGenericInterface;

public interface PAOOrdenamientosInsaInterface extends DAOGenericInterface<Serializable, Serializable> {

	public String ejecutaOrdenamientos(Integer idProceso, Integer idDetalle, Integer idParticipacion,
			String letra, String usuario);
	
}
