package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import mx.ine.procprimerinsa.dao.DAOEstatusProcesosInterface;
import mx.ine.procprimerinsa.dto.db.DTOEstatusProcesos;

@Repository("daoEstatusProceso")
@Scope("prototype")
public class DAOEstatusProcesos extends DAOGeneric<DTOEstatusProcesos, Serializable> implements DAOEstatusProcesosInterface {
	
	@Override
	public DTOEstatusProcesos consultaEstatusProceso(Integer idDetalle, Integer idParticipacion) {
		Session session = getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOEstatusProcesos> criteria = builder.createQuery(DTOEstatusProcesos.class);
		Root<DTOEstatusProcesos> root = criteria.from(DTOEstatusProcesos.class);
		Predicate[] predicates = new Predicate[2];
		predicates[0] = builder.equal(root.get("idDetalleProceso"), idDetalle);
		predicates[1] = builder.equal(root.get("idParticipacion"), idParticipacion);
		
		criteria.select(root).where(predicates);
		
		Query<DTOEstatusProcesos> query = session.createQuery(criteria);
		return query.uniqueResult();
	}
	
	@Override
	public void actualizaEstatus(DTOEstatusProcesos estatusProceso) {
		getSession().merge(estatusProceso);
		getSession().flush();
	}
	
}
