package mx.ine.procprimerinsa.batch.listeners;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import mx.ine.procprimerinsa.util.Utilidades;

import org.jboss.logging.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.lang.NonNull;

public class JobListenerPrimeraInsaculacion implements JobExecutionListener {

	private static final Logger logger = Logger.getLogger(JobListenerPrimeraInsaculacion.class);
	private long startTime;

	@Override
	public void beforeJob(@NonNull JobExecution jobExecution) {
		String mesesYaSorteados = jobExecution.getJobParameters().getString("mesesYaSorteados");
		
		ExecutionContext executionContext = jobExecution.getExecutionContext();
		
		executionContext.put("folio", new AtomicInteger());
		executionContext.put("mesesYaSorteadosList", mesesYaSorteados == null ? Arrays.asList(99) 
																			: Arrays.stream(mesesYaSorteados.split(","))
																					.map(Integer::valueOf)
																					.toList());
		
		logger.error("JobListenerPrimeraInsaculacion -executionContext: " + executionContext);
		startTime = System.currentTimeMillis();
		logger.error("JobListenerPrimeraInsaculacion -starting at " + Utilidades.formatoTiempo(startTime));
	}

	@Override
	public void afterJob(@NonNull JobExecution jobExecution) {
		long stopTime = System.currentTimeMillis();
		logger.error("JobListenerPrimeraInsaculacion -stopping at " + Utilidades.formatoTiempo(stopTime));
		logger.error("Total time take " + Utilidades.formatoTiempo(stopTime - startTime));
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.error("Completed successfully");
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			logger.error("Failed with following exceptions: ");
			List<Throwable> exceptionList = jobExecution.getAllFailureExceptions();
			for (Throwable th : exceptionList) {
				logger.error("Exception: ", th);
			}
		}
	}

}
