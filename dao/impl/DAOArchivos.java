package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import mx.ine.procprimerinsa.dao.DAOArchivosInterface;
import mx.ine.procprimerinsa.dto.db.DTOArchivos;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository("daoArchivos")
@Scope("prototype")
public class DAOArchivos extends DAOGeneric<DTOArchivos, Serializable> implements DAOArchivosInterface {
		
	@Override
	public List<DTOArchivos> consultaArchivos(DTOArchivos archivo) {
		Session session = getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOArchivos> criteria = builder.createQuery(DTOArchivos.class);
		Root<DTOArchivos> root = criteria.from(DTOArchivos.class);
		Predicate[] predicates = new Predicate[3];
		predicates[0] = builder.equal(root.get("idProcesoElectoral"), archivo.getIdProcesoElectoral());
		predicates[1] = builder.equal(root.get("idDetalleProceso"), archivo.getIdDetalleProceso());
		predicates[2] = builder.equal(root.get("idParticipacion"), archivo.getIdParticipacion());
		
		criteria.select(root).where(predicates);
		
		Query<DTOArchivos> query = session.createQuery(criteria);
		return query.getResultList();
	}
	
	@Override
	public void guardarArchivo(DTOArchivos archivo){
		getSession().merge(archivo);
		getSession().flush();
	}
	
	@Override
	public void eliminarArchivo(DTOArchivos archivo) {
		getSession().remove(archivo);
		getSession().flush();
	}
	
}
