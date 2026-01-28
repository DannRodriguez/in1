package mx.ine.procprimerinsa.batch.launchers.impl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.procprimerinsa.batch.launchers.JobLauncherDatosPersonalesInterface;

@Service("jobLauncherDatosPersonales")
@Scope("prototype")
public class JobLauncherDatosPersonales extends JobLauncherGeneric implements JobLauncherDatosPersonalesInterface {
	
	@Autowired
	@Qualifier("jobGenerarDatosMinimos")
	Job jobGenerarDatosMinimos;
	
	@Autowired
	@Qualifier("jobValidarDatosPersonalesPorEstado")
	Job jobValidarDatosPersonalesPorEstado;
	
	@Autowired
	@Qualifier("jobActualizarDatosPersonalesPorEstado")
	Job jobActualizarDatosPersonalesPorEstado;

	@Override
	public boolean generarArchivoDatosMinimosPorEstado(Integer idProcesoElectoral, Integer idDetalleProceso,
			Integer idEstado, String usuario) throws Exception {
		JobParameters jobParameters = null;
		JobExecution execution = null;
		
		jobParameters = new JobParametersBuilder()
				.addString("ejecutaGeneracionDatosMinimos", String.valueOf(System.currentTimeMillis()), true)
				.addLong("idProcesoElectoral", idProcesoElectoral.longValue(), false)
				.addLong("idDetalleProceso", idDetalleProceso.longValue(), true)
				.addLong("idEstado", idEstado.longValue(), true)
				.addString("usuario", usuario, false).toJobParameters();

		execution = ejecutaJobExecutionParticipacion(jobGenerarDatosMinimos,
													jobParameters,
													idProcesoElectoral, 
													idDetalleProceso, 
													idEstado, 
													0);
		
		return jobExecutionCompletadoParticipacion(execution, 
													idProcesoElectoral, 
													idDetalleProceso, 
													idEstado,
													0);
	}
	
	@Override
	public boolean validaArchivosDatosPersonalesPorEstado(Integer idProcesoElectoral, Integer idDetalleProceso, 
		Integer idEstado, String rutaArchivo, String usuario, String patternToValidate, String encoding) throws Exception {
		JobParameters jobParameters = null;
		JobExecution execution = null;
		
		jobParameters = new JobParametersBuilder()
				.addString("ejecutaValidacionArchivosDatosPersonales", String.valueOf(System.currentTimeMillis()), true)
				.addLong("idProcesoElectoral", idProcesoElectoral.longValue(), false)
				.addLong("idDetalleProceso", idDetalleProceso.longValue(), true)
				.addLong("idEstado", idEstado.longValue(), true)
				.addString("rutaArchivo", rutaArchivo, false)
				.addString("usuario", usuario, false)
				.addString("encoding", encoding, false)
				.addString("patternToValidate", patternToValidate, false)
				.toJobParameters();
		
		execution = ejecutaJobExecutionParticipacion(jobValidarDatosPersonalesPorEstado, 
													jobParameters, 
													idProcesoElectoral,
													idDetalleProceso, 
													idEstado, 
													0);
		
		return jobExecutionCompletadoParticipacion(execution, 
												idProcesoElectoral, 
												idDetalleProceso, 
												idEstado, 
												0);
	}

	@Override
	public boolean actualizarDatosPersonalesPorParticipacion(Integer idProceso, String tipoEleccion,
			Integer idDetalleProceso, Integer idEstado, String rutaArchivo, String encoding) throws Exception {
		JobParameters jobParameters = null;
		JobExecution execution = null;
		
		jobParameters = new JobParametersBuilder()
				.addString("ejecutaActualizacionArchivoDatosPersonales", String.valueOf(System.currentTimeMillis()), true)
				.addLong("idProceso", idProceso.longValue(), false)
				.addString("tipoEleccion", tipoEleccion, false)
				.addLong("idDetalleProceso", idDetalleProceso.longValue(), true)
				.addLong("idEstado", idEstado.longValue(), true)
				.addString("rutaArchivo", rutaArchivo, false)
				.addString("encoding", encoding, false).toJobParameters();

		execution = ejecutaJobExecutionParticipacion(jobActualizarDatosPersonalesPorEstado, 
													jobParameters,
													idProceso, 
													idDetalleProceso, 
													idEstado, 
													0);
		
		return jobExecutionCompletadoParticipacion(execution, 
													idProceso, 
													idDetalleProceso, 
													idEstado,
													0);
	}
}
