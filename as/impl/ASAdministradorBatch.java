package mx.ine.procprimerinsa.as.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASAdministradorBatchInterface;
import mx.ine.procprimerinsa.dao.DAOAdministradorBatchInterface;
import mx.ine.procprimerinsa.dto.DTOAdministradorBatch;
import mx.ine.procprimerinsa.dto.DTOTiempoEjecucionProceso;
import mx.ine.procprimerinsa.dto.db.DTOBatchJobExecution;
import mx.ine.procprimerinsa.dto.db.DTOCEstatusProceso;
import mx.ine.procprimerinsa.util.Utilidades;

@Scope("prototype")
@Service("asAdminBatch")
public class ASAdministradorBatch implements ASAdministradorBatchInterface {
	
	private static final Logger logger = Logger.getLogger(ASAdministradorBatch.class);
	
	@Autowired
	@Qualifier("daoAdminBatch")
	private DAOAdministradorBatchInterface daoAdminBatch;
	
	@Override
	@Transactional(value="transactionManagerReportes", propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
	public List<DTOAdministradorBatch> obtenerJobsPorEstado(Integer idDetalleProceso, Integer idEstado, Integer idCorte) {
		try {
			return daoAdminBatch.getJobsInsaByEstado(idDetalleProceso, idEstado, idCorte);
		} catch (Exception e) {
			logger.error("ERROR ASAdministradorBatch -obtenerJobsPorEstado: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(value="transactionManagerReportes", propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
	public DTOBatchJobExecution obtenerJobPorId(Integer idJobExecution) {
		try {
			return daoAdminBatch.getJobExecutionById(idJobExecution);
		} catch(Exception e) {
			logger.error("ERROR ASAdministradorBatch -obtenerJobPorId: ", e);
			return null;
		}
	}
	
	@Override
	@Transactional(value="transactionManagerReportes", propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
	public Map<Integer, DTOCEstatusProceso> obtenerCEstatusProceso(Integer idDetalle) {
		List<DTOCEstatusProceso> estatus = daoAdminBatch.obtenerCEstatusProceso(idDetalle);
		return Utilidades.collectionToStream(estatus)
						.collect(Collectors.toMap(DTOCEstatusProceso::getIdEstatusProceso, Function.identity()));
	}
	
	@Override
	@Transactional(value="transactionManagerReportes", propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
	public List<DTOAdministradorBatch> obtenerProcesosEjecutados(List<Integer> detalles, Integer idCorte) {
		try {
			return daoAdminBatch.obtenerProcesosEjecutados(detalles, idCorte);
		} catch(Exception e) {
			logger.error("ERROR ASAdministradorBatch -obtenerProcesosEjecutados: ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public boolean actualizaEjecuciones(List<Integer> detalles) {
		try {
			daoAdminBatch.actualizaEjecuciones(detalles);
		} catch(Exception e) {
			logger.error("ERROR ASAdministradorBatch -actualizaEjecuciones: ", e);
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public boolean reiniciaBatch() {
		try {
			daoAdminBatch.reiniciaBatch();
		} catch(Exception e) {
			logger.error("ERROR ASAdministradorBatch -reiniciaBatch: ", e);
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public boolean reiniciaBitacoraProceso(List<Integer> detalles) {
		try {
			daoAdminBatch.reiniciaBitacoraProceso(detalles);
		} catch(Exception e) {
			logger.error("ERROR ASAdministradorBatch -reiniciaBitacoraProceso: ", e);
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public boolean reiniciaEstatusDatosPersonales(List<Integer> detalles, String usuario) {
		try {
			daoAdminBatch.reiniciaEstatusDatosPersonales(detalles, usuario);
		} catch(Exception e) {
			logger.error("ERROR ASAdministradorBatch -reiniciaEstatusDatosPersonales: ", e);
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional(value="transactionManagerReportes", propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
	public List<DTOTiempoEjecucionProceso> obtenerTiempoEjecucionProceso(List<Integer> detalles, Integer idCorte) {
		try {
			return daoAdminBatch.obtenerTiempoEjecucionProceso(detalles, idCorte);
		} catch(Exception e) {
			logger.error("ERROR ASAdministradorBatch -obtenerTiempoEjecucionProceso: ", e);
			return Collections.emptyList();
		}
	}
	
}
