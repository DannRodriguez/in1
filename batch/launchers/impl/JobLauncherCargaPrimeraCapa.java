package mx.ine.procprimerinsa.batch.launchers.impl;

import java.nio.charset.StandardCharsets;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.procprimerinsa.batch.launchers.JobLauncherCargaPrimeraCapaInterface;

@Service("jlCargaPrimeraCapa")
@Scope("prototype")
public class JobLauncherCargaPrimeraCapa extends JobLauncherGeneric implements JobLauncherCargaPrimeraCapaInterface {
	
	@Autowired
	@Qualifier("jobCargaServidoresPublicos")
	Job jobCargaServidoresPublicos;
	
	@Autowired
	@Qualifier("jobSpoolServidoresPublicos")
	Job jobSpoolServidoresPublicos;
	
	@Autowired
	@Qualifier("jobSpoolAreasSecciones")
	Job jobSpoolAreasSecciones;
	
	@Autowired
	@Qualifier("jobSpoolSeccionesCompartidas")
	Job jobSpoolSeccionesCompartidas;
	
	@Autowired
	@Qualifier("jobCargaOrdenada")
	Job jobCargaOrdenada;
	
	@Autowired
	@Qualifier("jobSpoolOrdenada")
	Job jobSpoolOrdenada;
	
	@Override
	public boolean ejecutaCargaServidoresPublicos(Integer idProceso, Integer idDetalle) throws Exception {
		JobParameters jobParameters = null;
		JobExecution execution = null;

		jobParameters = new JobParametersBuilder()
				.addString("jobCargaServidoresPublicos", String.valueOf(System.currentTimeMillis()), true)
				.addLong("idProceso", idProceso.longValue(), false)
				.addLong("idDetalle", idDetalle.longValue(), false).toJobParameters();

		execution = ejecutaJobExecutionParticipacion(jobCargaServidoresPublicos, 
												jobParameters, 
												0,
												0, 
												0, 
												0);

		return jobExecutionCompletadoParticipacion(execution, 
												0, 
												0, 
												0,
												0);
	}
	
	@Override
	public boolean spoolServidoresPublicos(Integer idDetalle, String rutaArchivo) throws Exception {
		JobParameters jobParameters = null;
		JobExecution execution = null;

		jobParameters = new JobParametersBuilder()
				.addString("jobSpoolServidoresPublicos", String.valueOf(System.currentTimeMillis()), true)
				.addLong("idDetalle", idDetalle.longValue(), false)
				.addString("rutaArchivo", rutaArchivo, false)
				.addString("encoding", StandardCharsets.ISO_8859_1.name(), false).toJobParameters();

		execution = ejecutaJobExecutionParticipacion(jobSpoolServidoresPublicos, 
												jobParameters, 
												0,
												0, 
												0, 
												0);

		return jobExecutionCompletadoParticipacion(execution, 
												0, 
												0, 
												0,
												0);
	}
	
	@Override
	public boolean spoolAreasSecciones(Integer idProceso, String tipoEleccion, String rutaArchivo) throws Exception {
		JobParameters jobParameters = null;
		JobExecution execution = null;

		jobParameters = new JobParametersBuilder()
				.addString("jobSpoolAreasSecciones", String.valueOf(System.currentTimeMillis()), true)
				.addLong("idProceso", idProceso.longValue(), false)
				.addString("tipoEleccion", tipoEleccion, false)
				.addString("rutaArchivo", rutaArchivo, false)
				.addString("encoding", StandardCharsets.ISO_8859_1.name(), false).toJobParameters();

		execution = ejecutaJobExecutionParticipacion(jobSpoolAreasSecciones, 
												jobParameters, 
												0,
												0, 
												0, 
												0);

		return jobExecutionCompletadoParticipacion(execution, 
												0, 
												0, 
												0,
												0);
	}

	@Override
	public boolean spoolSeccionesCompartidas(Integer idProceso, String tipoEleccion, String rutaArchivo) throws Exception {
		JobParameters jobParameters = null;
		JobExecution execution = null;

		jobParameters = new JobParametersBuilder()
				.addString("jobSpoolSeccionesCompartidas", String.valueOf(System.currentTimeMillis()), true)
				.addLong("idProceso", idProceso.longValue(), false)
				.addString("tipoEleccion", tipoEleccion, false)
				.addString("rutaArchivo", rutaArchivo, false)
				.addString("encoding", StandardCharsets.ISO_8859_1.name(), false).toJobParameters();

		execution = ejecutaJobExecutionParticipacion(jobSpoolSeccionesCompartidas, 
												jobParameters, 
												0,
												0, 
												0, 
												0);

		return jobExecutionCompletadoParticipacion(execution, 
												0, 
												0, 
												0,
												0);
	}
	
	@Override
	public boolean ejecutaCargaOrdenada(Integer idProceso, String tipoEleccion, String rutaArchivo, String usuario) 
			throws Exception {
		JobParameters jobParameters = null;
		JobExecution execution = null;

		jobParameters = new JobParametersBuilder()
				.addString("jobCargaOrdenada", String.valueOf(System.currentTimeMillis()), true)
				.addLong("idProceso", idProceso.longValue(), false)
				.addString("tipoEleccion", tipoEleccion, false)
				.addString("rutaArchivo", rutaArchivo, false)
				.addString("usuario", usuario, false)
				.addString("encoding", StandardCharsets.ISO_8859_1.name(), false).toJobParameters();

		execution = ejecutaJobExecutionParticipacion(jobCargaOrdenada, 
												jobParameters, 
												0,
												0, 
												0, 
												0);

		return jobExecutionCompletadoParticipacion(execution, 
												0, 
												0, 
												0,
												0);
	}

	@Override
	public boolean spoolOrdenada(Integer idProceso, String tipoEleccion, String rutaArchivo) throws Exception {
		JobParameters jobParameters = null;
		JobExecution execution = null;

		jobParameters = new JobParametersBuilder()
				.addString("jobSpoolOrdenada", String.valueOf(System.currentTimeMillis()), true)
				.addLong("idProceso", idProceso.longValue(), false)
				.addString("tipoEleccion", tipoEleccion, false)
				.addString("rutaArchivo", rutaArchivo, false)
				.addString("encoding", StandardCharsets.ISO_8859_1.name(), false).toJobParameters();

		execution = ejecutaJobExecutionParticipacion(jobSpoolOrdenada, 
												jobParameters, 
												0,
												0, 
												0, 
												0);

		return jobExecutionCompletadoParticipacion(execution, 
												0, 
												0, 
												0,
												0);
	}

}
