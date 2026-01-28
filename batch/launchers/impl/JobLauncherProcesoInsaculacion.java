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
import mx.ine.procprimerinsa.batch.launchers.JobLauncherProcesoInsaculacionInterface;
import mx.ine.procprimerinsa.util.ApplicationContextUtils;
import mx.ine.procprimerinsa.util.Constantes;

@Service("jobLauncherProcesoInsaculacion")
@Scope("prototype")
public class JobLauncherProcesoInsaculacion extends JobLauncherGeneric implements JobLauncherProcesoInsaculacionInterface {

	@Autowired
	@Qualifier("jobPartitionerProcesoInsaculacion")
	Job jobPartitionerProcesoInsaculacion;
	
	private ASProcesoInsaInterface asProcesoInsa;

	@Override
	public boolean ejecutaCalculaInsaculados(Integer idCorteLN, Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idEstado, Integer idParticipacion, Integer idGeograficoParticipacion, Integer mesInsacular, 
			String letraInsacular, Integer idCorteLNAActualizar, Integer validaYaEsInsaculado, 
			String mesesYaSorteados, Integer consideraExtraordinarias, Integer idEjecucion) throws Exception {
		JobParameters jobParameters = null;
		JobExecution execution = null;
		asProcesoInsa = (ASProcesoInsaInterface) ApplicationContextUtils.getApplicationContext().getBean("asProcesoInsa");
		
		jobParameters = new JobParametersBuilder()
								.addString("jobInsaculacion", String.valueOf(System.currentTimeMillis()) + idParticipacion, true)
								.addLong("idCorteLN", idCorteLN.longValue(), false)
								.addLong("idProcesoElectoral", idProcesoElectoral.longValue(), false)
								.addLong("idDetalleProceso", idDetalleProceso.longValue(), true)
								.addLong("idEstado", idEstado.longValue(), false)
								.addLong("idParticipacion", idParticipacion.longValue(), true)
								.addLong("idGeograficoParticipacion", idGeograficoParticipacion.longValue(), false)
								.addLong("mesInsacular", mesInsacular.longValue(), false)
								.addString("letraInsacular", letraInsacular, false)
								.addLong("idCorteLNAActualizar", idCorteLNAActualizar.longValue(), false)
								.addLong("validaYaEsInsaculado", validaYaEsInsaculado.longValue(), false)
								.addString("mesesYaSorteados", mesesYaSorteados, false)
								.addLong("consideraExtraordinarias", consideraExtraordinarias.longValue(), false)
								.addLong("idReinicio", idEjecucion.longValue(), false).toJobParameters();

		execution = ejecutaJobExecutionParticipacion(jobPartitionerProcesoInsaculacion, 
												jobParameters,
												idProcesoElectoral, 
												idDetalleProceso, 
												idParticipacion, 
												idEjecucion);

		asProcesoInsa.actualizaEstatusEtapasInsaculacion(idDetalleProceso,
												idParticipacion, 
												Constantes.ESTATUS_EJECUTANDO_PROCESO_I,
												execution.getId().intValue());

		return jobExecutionCompletadoParticipacion(execution, 
												idProcesoElectoral, 
												idDetalleProceso, 
												idParticipacion,
												idEjecucion);
	}

}
