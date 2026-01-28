package mx.ine.procprimerinsa.pao;

import java.io.Serializable;

import mx.ine.procprimerinsa.dao.DAOGenericInterface;

public interface PAOMarcadoServidorPublicoInterface extends DAOGenericInterface<Serializable, Serializable> {
	
	public String ejecutaMarcadoServidorPublico(Integer idProceso, Integer idDetalle);
	
	public String ejecutaReinicioServidorPublico(Integer idProceso, Integer idDetalle);

}
