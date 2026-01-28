package mx.ine.procprimerinsa.batch.tasklets;

import org.jboss.logging.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import mx.ine.procprimerinsa.as.ASProcesoInsaInterface;
import mx.ine.procprimerinsa.util.Constantes;

public class TActualizaEstatus implements Tasklet, InitializingBean{

	private static final Logger logger = Logger.getLogger(TActualizaEstatus.class);
	
	@Autowired
	@Qualifier("asProcesoInsa")
	private ASProcesoInsaInterface asProcesoInsa;
	
	private Integer idDetalleProceso;
	private Integer idParticipacion;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(idDetalleProceso, "Se debe proporcionar el idDetalleProceso");
		Assert.notNull(idParticipacion, "Se debe proporcionar el idParticipacion");
	}
	
	@Override
	public RepeatStatus execute(@NonNull StepContribution contribution, @NonNull ChunkContext chunkContext) throws Exception {
		
		logger.info("Actualizando estatus del proceso");
		
		//Actualiza el estatus a 12 - EN DESUSO
		asProcesoInsa.actualizaEstatus(idDetalleProceso,
									idParticipacion, 
									Constantes.ESTATUS_GUARDANDO_BD_I, 
									Constantes.DEFAULT_JOB_EXECUTION_ID);
		
		//Actualiza el estatus a 13 - PROCESO FINALIZADO
		asProcesoInsa.actualizaEstatus(idDetalleProceso,
									idParticipacion, 
									Constantes.ESTATUS_GUARDANDO_BD_F, 
									Constantes.DEFAULT_JOB_EXECUTION_ID);
		
		return RepeatStatus.FINISHED;
	}

	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}

	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}
}
