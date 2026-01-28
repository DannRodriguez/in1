package mx.ine.procprimerinsa.pao;

import java.io.Serializable;

import mx.ine.procprimerinsa.dao.DAOGenericInterface;

public interface PAOActualizaLNInterface extends DAOGenericInterface<Serializable, Serializable> {
	
	public String ejecutaMarcadoNombreOrden(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito);
	
	public String ejecutaReinicioYaEsInsaculado(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito);

}
