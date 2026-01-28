package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.procprimerinsa.dao.DAOFirmasCartasInterface;
import mx.ine.procprimerinsa.dto.db.DTOFirmasCartas;

@Repository("daoFirmasCartas")
@Scope("prototype")
public class DAOFirmasCartas extends DAOGeneric<DTOFirmasCartas, Serializable> implements DAOFirmasCartasInterface {
	
	@Override
	public void guardarFirma(DTOFirmasCartas firma){
		getSession().merge(firma);
		getSession().flush();
	}
	
	@Override
	public DTOFirmasCartas obtenerFirma(Integer idDetalle, Integer idParticipacion) {
		Session session = getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOFirmasCartas> criteria = builder.createQuery(DTOFirmasCartas.class);
		Root<DTOFirmasCartas> root = criteria.from(DTOFirmasCartas.class);
		Predicate[] predicates = new Predicate[2];
		predicates[0] = builder.equal(root.get("idDetalleProceso"), idDetalle);
		predicates[1] = builder.equal(root.get("idParticipacion"), idParticipacion);
		
		criteria.select(root).where(predicates);
		
		Query<DTOFirmasCartas> query = session.createQuery(criteria);
		return query.uniqueResult();
	}
}
