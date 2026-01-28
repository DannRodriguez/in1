package mx.ine.procprimerinsa.batch.listeners;

import java.util.List;

import mx.ine.procprimerinsa.util.Utilidades;

import org.jboss.logging.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.lang.NonNull;

public class JobListenerGeneric implements JobExecutionListener {

	private static final Logger logger = Logger.getLogger(JobListenerGeneric.class);
	private Long startTime;
	
	@Override
	public void beforeJob(@NonNull JobExecution jobExecution) {
		startTime = System.currentTimeMillis();
		logger.error("Job:" + jobExecution.getJobInstance().getJobName());
		logger.error("Starting at " + Utilidades.formatoTiempo(startTime));
	}

	@Override
	public void afterJob(@NonNull JobExecution jobExecution) {
		Long stopTime = System.currentTimeMillis();
		logger.error("Job:" + jobExecution.getJobInstance().getJobName());
		logger.error("Starting at " + Utilidades.formatoTiempo(startTime));
		logger.error("Ending at " + Utilidades.formatoTiempo(stopTime));
		logger.error("Total time take " + Utilidades.formatoTiempo(stopTime - startTime));
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.error("Completed successfully");
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			logger.error("Failed with following exceptions: ");
			List<Throwable> exceptionList = jobExecution.getAllFailureExceptions();
			for (Throwable th : exceptionList) {
				logger.error("Exception :", th);
			}
		}
	}

}
