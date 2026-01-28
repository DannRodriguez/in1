package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import mx.ine.procprimerinsa.dao.DAOCParametrosInterface;
import mx.ine.procprimerinsa.dto.db.DTOCParametros;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Scope("prototype")
@Repository("daoParametros")
public class DAOCParametros extends DAOGeneric<DTOCParametros, Serializable> implements DAOCParametrosInterface {
	
	@Override
	public DTOCParametros obtenerParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer id, Integer idParametro) throws Exception{
		Session session = getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOCParametros> criteria = builder.createQuery(DTOCParametros.class);
		Root<DTOCParametros> root = criteria.from(DTOCParametros.class);
		Predicate[] predicates = new Predicate[5];
		predicates[0] = builder.equal(root.get("idProcesoElectoral"), idProceso);
		predicates[1] = builder.equal(root.get("idDetalleProceso"), idDetalle);
		predicates[2] = builder.equal(root.get("idEstado"), idEstado);
		predicates[3] = builder.equal(root.get("idDistrito"), id);
		predicates[4] = builder.equal(root.get("idParametro"), idParametro);
		
		criteria.select(root).where(predicates);
		
		Query<DTOCParametros> query = session.createQuery(criteria);
		return query.uniqueResult();
	}
	
	@Override
	public void actualizaParametro(DTOCParametros parametro) throws Exception{
		getSession().merge(parametro);
	}

}
