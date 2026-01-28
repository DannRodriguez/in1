package mx.ine.procprimerinsa.batch.listeners;

import org.jboss.logging.Logger;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.lang.NonNull;

public class CLLoadData implements ChunkListener {

	private static final Logger LOGGER = Logger.getLogger(CLLoadData.class);
	
	@Override
	public void beforeChunk(@NonNull ChunkContext chunkContext) {
		LOGGER.debug("beforeChunk " + chunkContext);
	}
	
	@Override
	public void afterChunk(@NonNull ChunkContext chunkContext) {
		LOGGER.debug("afterChunk " + chunkContext);
	}

	@Override
	public void afterChunkError(@NonNull ChunkContext chunkContext) {
		LOGGER.info("afterChunkError " + chunkContext);
	}

}
