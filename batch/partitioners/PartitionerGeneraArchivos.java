package mx.ine.procprimerinsa.batch.partitioners;

import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import mx.ine.procprimerinsa.as.ASCTipoVotoInterface;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;

public class PartitionerGeneraArchivos implements Partitioner, InitializingBean {

	private static final Logger logger = Logger.getLogger(PartitionerGeneraArchivos.class);

	private Integer idDetalleProceso;
	private Integer idParticipacion;
	
	@Autowired
	@Qualifier("asCTipoVoto")
	private ASCTipoVotoInterface asCTipoVoto;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(idDetalleProceso, "Se debe proporcionar el idDetalleProceso.");
		Assert.notNull(idParticipacion, "Se debe proporcionar idParticipacion.");
	}
	
	@Override
	public @NonNull Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> partitionMap = new HashMap<>();
		
		try {
			
			Map<Integer, DTOCTipoVoto> tiposVoto = asCTipoVoto.obtieneTiposVotoPorParticipacion(idDetalleProceso, idParticipacion);
			
			ExecutionContext executionContext = null;

			for (Map.Entry<Integer, DTOCTipoVoto> tipoVoto : tiposVoto.entrySet()) {
				
				executionContext = new ExecutionContext();
				
				executionContext.putInt("idTipoVoto", tipoVoto.getKey());
				executionContext.putString("descripcion", tipoVoto.getValue().getDescripcion());
				executionContext.putString("siglas", tipoVoto.getValue().getSiglas());
				executionContext.putString("nombreArchivoListado", tipoVoto.getValue().getNombreArchivoListado());
				executionContext.putString("tituloArchivoListado", tipoVoto.getValue().getTituloArchivoListado());
				executionContext.putString("nombreArchivoCedula", tipoVoto.getValue().getNombreArchivoCedula());
				executionContext.putString("tituloArchivoCedula", tipoVoto.getValue().getTituloArchivoCedula());
				executionContext.putInt("tipoIntegracion", tipoVoto.getValue().getTipoIntegracion());
				executionContext.putString("leyendaCasilla", tipoVoto.getValue().getLeyendaCasilla());

				partitionMap.put("D" + idDetalleProceso 
						+ "PA" + idParticipacion 
						+ "TV"  + tipoVoto.getKey(), executionContext);
			}

		} catch (Exception e) {
			logger.error("ERROR PartitionerGeneraArchivos -partition: ", e);
			throw new RuntimeException("ERROR PartitionerGeneraArchivos -partition: ", e);
		}
		
		logger.info("PartitionerGeneraArchivos - partition: Número de particiones creadas: " + partitionMap.size());
		return partitionMap;
	}
	
	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}

	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}
	
}
