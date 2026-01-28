package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.procprimerinsa.dao.DAOCategoriasProcesoInterface;
import mx.ine.procprimerinsa.dto.db.DTOCategoriasProceso;

@Repository("daoCategoriasProceso")
@Scope("prototype")
public class DAOCategoriasProceso extends DAOGeneric<DTOCategoriasProceso, Serializable> implements DAOCategoriasProcesoInterface{

	@Override
	public List<DTOCategoriasProceso> obtenerCategorias(Integer idDetalleProceso) {
		Session session = getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOCategoriasProceso> criteria = builder.createQuery(DTOCategoriasProceso.class);
		Root<DTOCategoriasProceso> root = criteria.from(DTOCategoriasProceso.class);
		Predicate[] predicates = new Predicate[1];
		predicates[0] = builder.equal(root.get("idDetalleProceso"), idDetalleProceso);
		
		criteria.orderBy(builder.asc(root.get("idCategoria")));
		criteria.select(root).where(predicates);
		
		Query<DTOCategoriasProceso> query = session.createQuery(criteria);
		return query.getResultList();
	}

}
