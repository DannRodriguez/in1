package mx.ine.procprimerinsa.dao;

import java.io.Serializable;
import java.util.Queue;

import mx.ine.procprimerinsa.dto.db.DTODatosInsaculados;

public interface DAODatosInsaculadosInterface extends DAOGenericInterface<DTODatosInsaculados, Serializable> {
	
	public void truncatePartitionDatosInsaculados(Integer idDetalleProcesoElectoral, Integer idParticipacion);

	public void eliminaResultadosInsa(Integer idDetalleProceso, Integer idParticipacion);

	public Queue<DTODatosInsaculados> obtenerListaInsaculados(Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idParticipacion, Integer idTipoVoto, Integer tipoIntegracion, 
			String letra, Integer maxResults, Long offset);
		
	public Integer contarCiudadanosInsaculadosPorEstado(Integer idDetalleProceso, Integer idEstado);
	
}
