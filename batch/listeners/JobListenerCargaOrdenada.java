package mx.ine.procprimerinsa.batch.listeners;

import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;

import mx.ine.procprimerinsa.dao.DAOCargaPrimeraCapaInterface;
import mx.ine.procprimerinsa.util.Utilidades;

public class JobListenerCargaOrdenada implements JobExecutionListener {
	
	private static final Logger logger = Logger.getLogger(JobListenerCargaOrdenada.class);
	private long startTime;
	
	@Autowired
	@Qualifier("daoCargaPrimeraCapa")
	private DAOCargaPrimeraCapaInterface daoCargaPrimeraCapa;

	@Override
	public void beforeJob(@NonNull JobExecution jobExecution) {
		Integer idProceso = jobExecution.getJobParameters().getLong("idProceso").intValue();
		String tipoEleccion = jobExecution.getJobParameters().getString("tipoEleccion");
		
		ExecutionContext executionContext = jobExecution.getExecutionContext();
		
		Map<String, String> participacionesMap = daoCargaPrimeraCapa.obtieneMapaParticipaciones(idProceso, tipoEleccion);
		executionContext.put("participacionesMap", participacionesMap);
		
		logger.error("JobListenerCargaOrdenada -executionContext: " + executionContext);
		startTime = System.currentTimeMillis();
		logger.error("JobListenerCargaOrdenada -starting at " + Utilidades.formatoTiempo(startTime));
	}

	@Override
	public void afterJob(@NonNull JobExecution jobExecution) {
		long stopTime = System.currentTimeMillis();
		logger.error("JobListenerCargaOrdenada -stopping at " + Utilidades.formatoTiempo(stopTime));
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
