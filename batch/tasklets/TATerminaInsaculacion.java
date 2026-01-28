package mx.ine.procprimerinsa.batch.tasklets;

import org.jboss.logging.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.NonNull;

public class TATerminaInsaculacion implements Tasklet, InitializingBean {

	private static final Logger logger = Logger.getLogger(TATerminaInsaculacion.class);
	
	@Override
	public void afterPropertiesSet() throws Exception {
	}
	
	@Override
	public RepeatStatus execute(@NonNull StepContribution contribution, @NonNull ChunkContext chunkContext) throws Exception {
		logger.info("Termina proceso de insaculación");
		return RepeatStatus.FINISHED;
	}
	
}
