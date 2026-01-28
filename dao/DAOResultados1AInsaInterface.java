package mx.ine.procprimerinsa.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Queue;

import mx.ine.procprimerinsa.dto.db.DTOResultados1aInsa;

public interface DAOResultados1AInsaInterface extends DAOGenericInterface<DTOResultados1aInsa, Serializable> {
	
	public List<DTOResultados1aInsa> getResultadoInsaculacionPorParticipacion(Integer idDetalleProceso, 
			Integer idParticipacion, Integer idTipoVoto);

	public Queue<DTOResultados1aInsa> getResultadoInsaculacionPorParticipacionPaginado(Integer idDetalleProceso, 
			Integer idParticipacion, Integer idTipoVoto, Integer offset, Integer limite);

	public void guardar(DTOResultados1aInsa resultado);

}
