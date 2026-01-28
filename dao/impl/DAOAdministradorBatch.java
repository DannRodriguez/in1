package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.procprimerinsa.dao.DAOAdministradorBatchInterface;
import mx.ine.procprimerinsa.dto.DTOAdministradorBatch;
import mx.ine.procprimerinsa.dto.DTOTiempoEjecucionProceso;
import mx.ine.procprimerinsa.dto.db.DTOBatchJobExecution;
import mx.ine.procprimerinsa.dto.db.DTOCEstatusProceso;
import mx.ine.procprimerinsa.util.Constantes;

@Scope("prototype")
@Repository("daoAdminBatch")
public class DAOAdministradorBatch extends DAOGeneric<DTOBatchJobExecution, Serializable> implements DAOAdministradorBatchInterface {

	private static final String DETALLES = "detalles";
	private static final String ID_CORTE = "idCorte";
	
	@Override
	public List<DTOAdministradorBatch> getJobsInsaByEstado(Integer idDetalleProceso, Integer idEstado, Integer idCorte) {
		List<DTOAdministradorBatch> jobs = new ArrayList<>();
		DTOAdministradorBatch job;
		
		List<Object[]> lista = getSessionReportes().createNativeQuery(getContainer().getQuery("query_adminBatch_obtenerJobsPorEstadoAmbitoFederal"),
																	Object[].class)
									.setParameter("idDetalleProceso", idDetalleProceso)
									.setParameter("idEstado", idEstado)
									.setParameter(ID_CORTE, idCorte)
									.list();
		for (Object[] o: lista) {
			job = new DTOAdministradorBatch();
			job.setIdDetalleProceso(Integer.parseInt(o[0].toString()));
			job.setIdParticipacion(Integer.parseInt(o[1].toString()));
			job.setDescripcionParticipacion(o[2].toString());
			job.setJobCalculaInsaculados(o[3].toString());
			job.setIdJobCalculaInsaculados(job.getIdJobFromStatus(job.getJobCalculaInsaculados()));
			job.setEstatusJobCalculaInsaculados(job.getDescripcionJobFromStatus(job.getJobCalculaInsaculados()));
			job.setJobGeneraArchivos(o[4].toString());
			job.setIdJobGeneraArchivos(job.getIdJobFromStatus(job.getJobGeneraArchivos()));
			job.setEstatusJobGeneraArchivos(job.getDescripcionJobFromStatus(job.getJobGeneraArchivos()));
			job.setEstatus(Integer.valueOf(o[5].toString()));
			job.setNuevoEstatus(Integer.valueOf(o[5].toString()));
			job.setIdGeograficoParticipacion(o[6] != null ? Integer.valueOf(o[6].toString()) : null);
			job.setCodigoColorCategoria(o[7].toString());
			jobs.add(job);
		}
		return jobs;
	}

	@Override
	public DTOBatchJobExecution getJobExecutionById(Integer idJobExecution) {
		Session session = getSessionReportes();
		HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOBatchJobExecution> criteria = builder.createQuery(DTOBatchJobExecution.class);
		Root<DTOBatchJobExecution> root = criteria.from(DTOBatchJobExecution.class);
		Predicate[] predicates = new Predicate[1];
		predicates[0] = builder.equal(root.get("jobExecutionId"), idJobExecution);
		
		criteria.select(root).where(predicates);
		
		Query<DTOBatchJobExecution> query = session.createQuery(criteria);
		return query.getSingleResult();
	}
	
	@Override
	public List<DTOCEstatusProceso> obtenerCEstatusProceso(Integer idDetalle) {
		Session session = getSessionReportes();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOCEstatusProceso> criteria = builder.createQuery(DTOCEstatusProceso.class);
		Root<DTOCEstatusProceso> root = criteria.from(DTOCEstatusProceso.class);
		Predicate[] predicates = new Predicate[2];
		predicates[0] = builder.equal(root.get("idDetalleProceso"), idDetalle);
		predicates[1] = builder.notEqual(root.get("idEstatusProceso"), 0);
		
		criteria.select(root).where(predicates);
		
		Query<DTOCEstatusProceso> query = session.createQuery(criteria);
		return query.getResultList();
	}
	
	@Override
	public List<DTOAdministradorBatch> obtenerProcesosEjecutados(List<Integer> detalles, Integer idCorte){
		List<DTOAdministradorBatch> procesos = new ArrayList<>();
		DTOAdministradorBatch proceso;
		
		List<Object[]> lista = getSessionReportes().createNativeQuery(getContainer().getQuery("query_adminBatch_obtenerProcesosEjecutados"),
																	Object[].class)
								.setParameterList(DETALLES, detalles)
								.setParameter("estatus", Constantes.ESTATUS_PREPARANDO_DG_F)
								.setParameter(ID_CORTE, idCorte) 
								.list();
		
		for(Object[] o: lista) {
			proceso = new DTOAdministradorBatch();
			
			proceso.setIdDetalleProceso(Integer.parseInt(o[0].toString()));
			proceso.setIdParticipacion(Integer.parseInt(o[1].toString()));
			proceso.setDescripcionParticipacion(o[2].toString());
			
			procesos.add(proceso);
		}
		
		return procesos;
	}
	
	@Override
	public void actualizaEjecuciones(List<Integer> detalles){
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_adminBatch_actualizaEjecuciones"))
				.setParameterList(DETALLES, detalles)
				.executeUpdate();
		session.flush();
	}
	
	@Override
	public void reiniciaBatch() {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_adminBatch_reiniciaBatch_stepContext"))
				.executeUpdate();
		session.createNativeMutationQuery(getContainer().getQuery("query_adminBatch_reiniciaBatch_jobContext"))
				.executeUpdate();
		session.createNativeMutationQuery(getContainer().getQuery("query_adminBatch_reiniciaBatch_jobParams"))
				.executeUpdate();
		session.createNativeMutationQuery(getContainer().getQuery("query_adminBatch_reiniciaBatch_stepExecution"))
				.executeUpdate();
		session.createNativeMutationQuery(getContainer().getQuery("query_adminBatch_reiniciaBatch_jobExecution"))
				.executeUpdate();
		session.createNativeMutationQuery(getContainer().getQuery("query_adminBatch_reiniciaBatch_jobInstance"))
				.executeUpdate();
		session.flush();
	}
	
	@Override
	public void reiniciaBitacoraProceso(List<Integer> detalles) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_adminBatch_reiniciaBatch_bitacoraProcesos"))
				.setParameterList(DETALLES, detalles)
				.executeUpdate();
		session.flush();	
	}
	
	@Override
	public void reiniciaEstatusDatosPersonales(List<Integer> detalles, String usuario) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_adminBatch_reiniciaBatch_estatusDatosPersonales"))
				.setParameterList(DETALLES, detalles)
				.setParameter("usuario", usuario)
				.executeUpdate();
		session.flush();	
	}
	
	@Override
	public List<DTOTiempoEjecucionProceso> obtenerTiempoEjecucionProceso(List<Integer> detalles, Integer idCorte){
		List<DTOTiempoEjecucionProceso> tiemposEjecucion = new ArrayList<>();
		DTOTiempoEjecucionProceso tiempoEjecucion;
		
		List<Object[]> lista = getSessionReportes().createNativeQuery(getContainer().getQuery("query_adminBatch_tiempo_insaculacion"),
																	Object[].class)
								.setParameterList(DETALLES, detalles)
								.setParameter(ID_CORTE, idCorte) 
								.list();
		
		for(Object[] o: lista) {
			tiempoEjecucion = new DTOTiempoEjecucionProceso();
			
			tiempoEjecucion.setIdDetalle(Integer.parseInt(o[0].toString()));
			tiempoEjecucion.setIdParticipacion(Integer.parseInt(o[1].toString()));
			tiempoEjecucion.setEstado(o[2].toString());
			tiempoEjecucion.setDistrito(o[3].toString());
			tiempoEjecucion.setIdEstatusProceso(Integer.parseInt(o[4].toString()));
			tiempoEjecucion.setEjecuciones(Integer.parseInt(o[5].toString()));
			tiempoEjecucion.setReinicios(Integer.parseInt(o[6].toString()));
			tiempoEjecucion.setCiudadanos(Integer.parseInt(o[7].toString()));
			tiempoEjecucion.setInicioInsaculacion(o[8]!=null?o[8].toString():null);
			tiempoEjecucion.setFinInsaculacion(o[9]!=null?o[9].toString():null);
			tiempoEjecucion.setTiempoInsaculacion(o[10]!=null?o[10].toString():null);
			tiempoEjecucion.setInicioProceso(o[11]!=null?o[11].toString():null);
			tiempoEjecucion.setFinProceso(o[12]!=null?o[12].toString():null);
			tiempoEjecucion.setTiempoProceso(o[13]!=null?o[13].toString():null);
			tiempoEjecucion.setInicioArchivos(o[14]!=null?o[14].toString():null);
			tiempoEjecucion.setFinArchivos(o[15]!=null?o[15].toString():null);
			tiempoEjecucion.setTiempoArchivos(o[16]!=null?o[16].toString():null);
			
			tiemposEjecucion.add(tiempoEjecucion);
		}
		
		return tiemposEjecucion;
	}

}
