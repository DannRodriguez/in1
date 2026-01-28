package mx.ine.procprimerinsa.as.impl;

import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASDatosInsaculadosInterface;
import mx.ine.procprimerinsa.dao.DAODatosInsaculadosInterface;
import mx.ine.procprimerinsa.dao.DAOResultados1AInsaInterface;
import mx.ine.procprimerinsa.dto.db.DTODatosInsaculados;
import mx.ine.procprimerinsa.dto.db.DTOResultados1aInsa;

@Service("asDatosInsaculados")
@Scope("prototype")
public class ASDatosInsaculados implements ASDatosInsaculadosInterface {
	
	private static final long serialVersionUID = -1510755867402794902L;
	
	@Autowired
	@Qualifier("daoDatosInsaculados")
	private transient DAODatosInsaculadosInterface daoDatosInsaculados;

	@Autowired
	@Qualifier("daoResultadosInsaculacion")
	private transient DAOResultados1AInsaInterface daoResultadosInsaculacion;

	@Override
	@Transactional(readOnly = true, rollbackFor = { Exception.class })
	public List<DTOResultados1aInsa> consultaResultados(Integer idDetalleProceso, Integer idParticipacion, 
			Integer idTipoVoto) {
		return daoResultadosInsaculacion.getResultadoInsaculacionPorParticipacion(
																idDetalleProceso, 
																idParticipacion, 
																idTipoVoto);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public Queue<DTOResultados1aInsa> consultaResultados(Integer idDetalleProceso, Integer idParticipacion, 
		Integer idTipoVoto, Long offset, Integer limite) {
		return daoResultadosInsaculacion.getResultadoInsaculacionPorParticipacionPaginado(
																idDetalleProceso, 
																idParticipacion, 
																idTipoVoto, 
																offset.intValue(), 
																limite);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = { Exception.class })
	public Queue<DTODatosInsaculados> obtenerListaInsaculados(Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idParticipacion, Integer idTipoVoto, Integer tipoIntegracion,
			String letra, Integer maxResults, Long offset) {
		return daoDatosInsaculados.obtenerListaInsaculados(idProcesoElectoral,
														idDetalleProceso,
														idParticipacion, 
														idTipoVoto, 
														tipoIntegracion,
														letra, 
														maxResults, 
														offset);
	}

}
