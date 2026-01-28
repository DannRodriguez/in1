package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.procprimerinsa.dao.DAOBitacoraProcesosInterface;
import mx.ine.procprimerinsa.dto.db.DTOBitacoraProcesos;
import mx.ine.procprimerinsa.util.Constantes;

@Repository("daoBitacoraProcesos")
@Scope("prototype")
public class DAOBitacoraProcesos extends DAOGeneric<DTOBitacoraProcesos, Serializable> implements DAOBitacoraProcesosInterface {

	private static final String ID_DETALLE_PROCESO = "idDetalleProceso";
	private static final String ID_PARTICIPACION = "idParticipacion";

	@Override
	public void guardarBitacora(DTOBitacoraProcesos bitacora) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_bitacoraProcesos_insertaConSecuencia"))
				.setParameter("idProceso",  bitacora.getIdProcesoElectoral())
				.setParameter("idDetalle",  bitacora.getIdDetalleProceso())
				.setParameter(ID_PARTICIPACION,  bitacora.getIdParticipacion())
				.setParameter("idEjecucion",  bitacora.getIdEjecucion())
				.setParameter("idReinicio",  bitacora.getIdReinicio())
				.setParameter("idEstatus",  bitacora.getIdEstatusProceso())
				.setParameter("usuario",  bitacora.getUsuario())
				.setParameter("fechaHora",  bitacora.getFechaHora())
				.setParameter("jobExecutionId",  bitacora.getJobExecutionId())
				.executeUpdate();
		session.flush();
	}
	
	@Override
	public List<DTOBitacoraProcesos> consultaBitacora(List<Integer> detalles) {
		Session session = getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOBitacoraProcesos> criteria = builder.createQuery(DTOBitacoraProcesos.class);
		Root<DTOBitacoraProcesos> root = criteria.from(DTOBitacoraProcesos.class);
		
		criteria.select(root)
				.where(root.get(ID_DETALLE_PROCESO).in(detalles))
				.orderBy(builder.asc(root.get(ID_DETALLE_PROCESO)),
						builder.asc(root.get(ID_PARTICIPACION)), 
						builder.desc(root.get("fechaHora")));
		
		Query<DTOBitacoraProcesos> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public void setBitacoraJobExecutionIdDefaultPorParticipacion(Integer idDetalleProceso, Integer idParticipacionGeografica) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_reinicioProcesoInsaculacion_setJobExecutionIdDefault"))
				.setParameter(ID_DETALLE_PROCESO, idDetalleProceso)
				.setParameter(ID_PARTICIPACION, idParticipacionGeografica)
				.setParameter("idDefault", Constantes.DEFAULT_JOB_EXECUTION_ID)
				.executeUpdate();
		session.flush();
	}

}
