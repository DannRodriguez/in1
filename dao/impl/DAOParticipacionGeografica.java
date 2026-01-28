package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.procprimerinsa.dao.DAOParticipacionGeograficaInterface;
import mx.ine.procprimerinsa.dto.db.DTOParticipacionGeografica;

@Scope("prototype")
@Repository("daoParticipacionGeografica")
public class DAOParticipacionGeografica extends DAOGeneric<DTOParticipacionGeografica, Serializable> implements DAOParticipacionGeograficaInterface {

	private static final String ID_PROCESO = "idProcesoElectoral";
	private static final String ID_DETALLE = "idDetalleProceso";
	private static final String ID_ESTADO = "idEstado";
	
	@Override
	public Map<Integer, DTOParticipacionGeografica> consultaParticipacionesEstadoProceso(Integer idProceso,
			Integer idDetalle, Integer idEstado) {
		Session session = getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOParticipacionGeografica> criteria = builder.createQuery(DTOParticipacionGeografica.class);
		Root<DTOParticipacionGeografica> root = criteria.from(DTOParticipacionGeografica.class);
		Predicate[] predicates = new Predicate[3];
		predicates[0] = builder.equal(root.get(ID_PROCESO), idProceso);
		predicates[1] = builder.equal(root.get(ID_DETALLE), idDetalle);
		predicates[2] = builder.equal(root.get(ID_ESTADO), idEstado);
		
		criteria.select(root).where(predicates);
		
		Query<DTOParticipacionGeografica> query = session.createQuery(criteria);
		return query.stream()
					.collect(Collectors.toMap(DTOParticipacionGeografica::getIdDistrito, 
											Function.identity()));
	}

}
