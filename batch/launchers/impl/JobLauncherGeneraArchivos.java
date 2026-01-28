package mx.ine.procprimerinsa.batch.launchers.impl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.procprimerinsa.as.ASProcesoInsaInterface;
import mx.ine.procprimerinsa.batch.launchers.JobLauncherGeneraArchivosInterface;
import mx.ine.procprimerinsa.util.Constantes;

@Service("jobLauncherGeneraArchivos")
@Scope("prototype")
public class JobLauncherGeneraArchivos extends JobLauncherGeneric implements JobLauncherGeneraArchivosInterface {
	
	@Autowired
	@Qualifier("jobPartitionerGeneraArchivos")
	private Job jobPartitionerGeneraArchivos;

	@Autowired
	@Qualifier("asProcesoInsa")
	private ASProcesoInsaInterface asProcesoInsa;

	@Override
	public boolean ejecutaGeneracionArchivos(Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idEstado, Integer id, Integer idParticipacion, String nombreProceso, String nombreDetalleProceso, 
			String nombreEstado, String nombreParticipacion, String modoEjecucion, String letra) 
			throws Exception {

		JobParameters jobParameters = null;
		JobExecution execution = null;

		jobParameters = new JobParametersBuilder()
				.addString("ejecutaGeneracionArchivos", String.valueOf(System.currentTimeMillis()) + idParticipacion, true)
				.addLong("idProcesoElectoral", idProcesoElectoral.longValue(), false)
				.addLong("idDetalleProceso", idDetalleProceso.longValue(), false)
				.addLong("idEstado", idEstado.longValue(), false)
				.addLong("idGeograficoParticipacion", id.longValue(), false)
				.addLong("idParticipacion", idParticipacion.longValue(), false)
				.addString("nombreProceso", nombreProceso, false)
				.addString("nombreDetalleProceso", nombreDetalleProceso, false)
				.addString("nombreEstado", nombreEstado, false)
				.addString("nombreParticipacion", nombreParticipacion, false)
				.addString("modoEjecucion", modoEjecucion, false)
				.addString("letra", letra, false).toJobParameters();
		
		execution = ejecutaJobExecutionParticipacion(jobPartitionerGeneraArchivos, 
													jobParameters, 
													idProcesoElectoral,
													idDetalleProceso, 
													idParticipacion, 
													0);

		asProcesoInsa.actualizaEstatusEtapasInsaculacion(idDetalleProceso,
														idParticipacion, 
														Constantes.ESTATUS_GUARDANDO_BD_I,
														execution.getId().intValue());

		return jobExecutionCompletadoParticipacion(execution, 
												idProcesoElectoral, 
												idDetalleProceso, 
												idParticipacion, 
												0);

	}
}
