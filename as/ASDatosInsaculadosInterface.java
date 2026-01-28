package mx.ine.procprimerinsa.as;

import java.io.Serializable;
import java.util.List;
import java.util.Queue;

import mx.ine.procprimerinsa.dto.db.DTODatosInsaculados;
import mx.ine.procprimerinsa.dto.db.DTOResultados1aInsa;

public interface ASDatosInsaculadosInterface extends Serializable {

	public List<DTOResultados1aInsa> consultaResultados(Integer idDetalleProceso, Integer idParticipacion, 
			Integer idTipoVoto);
	
	public Queue<DTOResultados1aInsa> consultaResultados(Integer idDetalleProceso, Integer idParticipacion, 
			Integer idTipoVoto, Long offset, Integer limite);

	public Queue<DTODatosInsaculados> obtenerListaInsaculados(Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idParticipacion, Integer idTipoVoto, Integer tipoIntegracion, 
			String letra, Integer maxResults, Long offset);
		
}
