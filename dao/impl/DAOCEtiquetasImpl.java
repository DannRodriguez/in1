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

import mx.ine.procprimerinsa.dao.DAOCEtiquetasInterface;
import mx.ine.procprimerinsa.dto.db.DTOCEtiquetas;

@Scope("prototype")
@Repository("daoEtiquetas")
public class DAOCEtiquetasImpl extends DAOGeneric<DTOCEtiquetas, Serializable> implements DAOCEtiquetasInterface {
	
	@Override
	public DTOCEtiquetas obtenerEtiqueta(Integer idProceso, Integer idDetalle, Integer idEstado, 
			Integer idDistrito, Integer idEtiqueta) {
		Session session = getSessionReportes();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOCEtiquetas> criteria = builder.createQuery(DTOCEtiquetas.class);
		Root<DTOCEtiquetas> root = criteria.from(DTOCEtiquetas.class);
		Predicate[] predicates = new Predicate[5];
		predicates[0] = builder.equal(root.get("idProcesoElectoral"), idProceso);
		predicates[1] = builder.equal(root.get("idDetalleProceso"), idDetalle);
		predicates[2] = builder.equal(root.get("idEstado"), idEstado);
		predicates[3] = builder.equal(root.get("idDistrito"), idDistrito);
		predicates[4] = builder.equal(root.get("idEtiqueta"), idEtiqueta);
		
		criteria.select(root).where(predicates);
		
		Query<DTOCEtiquetas> query = session.createQuery(criteria);
		return query.uniqueResult();
	}
   
}
