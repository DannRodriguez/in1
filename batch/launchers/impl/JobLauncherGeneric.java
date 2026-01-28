package mx.ine.procprimerinsa.batch.launchers.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.logging.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class JobLauncherGeneric {

	private static final Logger logger = Logger.getLogger(JobLauncherGeneric.class);

	@Autowired
	@Qualifier("jobExplorer")
	private JobExplorer jobExplorer;

	@Autowired
	@Qualifier("jobOperator")
	private JobOperator jobOperator;

	@Autowired
	@Qualifier("jobLauncher")
	private JobLauncher jobLauncher;

	private static final Map<String, JobExecution> runningJobExecutions = new ConcurrentHashMap<>();

	protected JobExecution ejecutaJobExecutionParticipacion(Job job, JobParameters jobParameters,
			Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion, Integer idEjecucion)
			throws Exception {

		synchronized (runningJobExecutions) {

			JobExecution execution = null;

			// Verificando si hay una ejecución en proceso
			execution = runningJobExecutions.get(job.getName() + ":P" + idProcesoElectoral + "DP" + idDetalleProceso
					+ "PA" + idParticipacion + "IE" + idEjecucion);

			// Si no hay una ejecución en proceso o existe una previa fallida
			if (execution == null || execution.getStatus().isUnsuccessful()) {
				// lanzando la ejecución del proceso
				execution = jobLauncher.run(job, jobParameters);
				runningJobExecutions.put(job.getName() + ":P" + idProcesoElectoral + "DP" + idDetalleProceso + "PA"
						+ idParticipacion + "IE" + idEjecucion, execution);
			}

			return execution;
		}

	}

	protected boolean verificaJobExecutionParticipacion(String jobName, Integer idProcesoElectoral,
			Integer idDetalleProceso, Integer idParticipacion, Integer idEjecucion) throws Exception {

		synchronized (runningJobExecutions) {

			JobExecution execution = null;

			// Verificando si hay una ejecución en proceso
			execution = runningJobExecutions.get(jobName + ":P" + idProcesoElectoral + "DP" + idDetalleProceso + "PA"
					+ idParticipacion + "IE" + idEjecucion);

			// Si hay una ejecución activa
			if (execution != null)
				return false;

			// Verificando si hay una ejecución previa terminada
			execution = obtenJobExecutionTerminadoParticipacion(jobName, idProcesoElectoral, idDetalleProceso,
					idParticipacion, idEjecucion);

			// Si existe una previa fallida
			if (execution != null) {
				return execution.getStatus().isUnsuccessful();
			}

			// Si no hay una ejecución en proceso ni existe una previa
			return true;

		}
	}

	protected boolean jobExecutionCompletadoParticipacion(JobExecution execution, Integer idProcesoElectoral,
			Integer idDetalleProceso, Integer idParticipacion, Integer idEjecucion) throws Exception {

		while (execution.getStatus().isRunning())
			logger.debug("JobExecution Status " + execution.getStatus());

		synchronized (runningJobExecutions) {
			runningJobExecutions.remove(execution.getJobInstance().getJobName() + ":P" + idProcesoElectoral + "DP"
					+ idDetalleProceso + "PA" + idParticipacion + "IE" + idEjecucion);
		}
		
		// Se valida si la carga se realizó correctamente
		return !execution.getStatus().isUnsuccessful();
		
	}

	protected JobExecution obtenJobExecutionTerminadoParticipacion(String jobName, Integer idProcesoElectoral,
			Integer idDetalleProceso, Integer idParticipacion, Integer idEjecucion) throws Exception {

		JobExecution execution = null;
		JobExecution item = null;
		List<JobInstance> instances = jobExplorer.getJobInstances(jobName, 0, 1);

		if (instances != null && !instances.isEmpty()) {
			item = jobExplorer.getJobExecution(instances.get(0).getId());
			if (idProcesoElectoral.longValue() == item.getJobParameters().getLong("idProcesoElectoral")
					&& idDetalleProceso.longValue() == item.getJobParameters().getLong("idDetalleProceso")
					&& idParticipacion.longValue() == item.getJobParameters().getLong("idParticipacion")
					&& idEjecucion.longValue() == item.getJobParameters().getLong("idEjecucion")
					&& !item.getStatus().isRunning()) {
				execution = item;
			}
		}

		return execution;
	}

}