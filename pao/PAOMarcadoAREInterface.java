package mx.ine.procprimerinsa.pao;

import java.io.Serializable;

import mx.ine.procprimerinsa.dao.DAOGenericInterface;

public interface PAOMarcadoAREInterface extends DAOGenericInterface<Serializable, Serializable> {
	
	public String ejecutaCargaARES(Integer idDetalle, String usuario);
	
	public String ejecutaActualizacionARES(Integer idProceso, Integer idDetalle, Integer idParticipacion, String usuario);

}
