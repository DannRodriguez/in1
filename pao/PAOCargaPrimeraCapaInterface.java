package mx.ine.procprimerinsa.pao;

import java.io.Serializable;

import mx.ine.procprimerinsa.dao.DAOGenericInterface;

public interface PAOCargaPrimeraCapaInterface extends DAOGenericInterface<Serializable, Serializable> {
	
	public String ejecutaCargaPrimeraCapa(Integer idProceso, Integer idDetalle, Integer idParticipacion,
			String participacion, String usuario);

}
