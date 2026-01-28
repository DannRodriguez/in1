package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import mx.ine.procprimerinsa.dao.DAOLlavesProcesosInterface;
import mx.ine.procprimerinsa.dto.db.DTOLlavesProcesos;

@Repository("daoLlavesProcesos")
@Scope("prototype")
public class DAOLlavesProcesos extends DAOGeneric<DTOLlavesProcesos, Serializable> implements DAOLlavesProcesosInterface {
	
	@Override
	public Map<Integer, String[]> obtenerLlavesProceso(Integer idDetalleProceso, Integer modoEjecucion) {
		Map<Integer, String[]> participaciones = new HashMap<>();
		String[] participacion;
		
		List<Object[]> lista = getSession().createNativeQuery(getContainer().getQuery("query_llavesProceso_obtieneLLavesProceso"),
																Object[].class)
											.setParameter("idDetalle", idDetalleProceso)
											.setParameter("modoEjecucion", modoEjecucion)
											.list();
		
		for(Object[] o : lista) {
			participacion = new String[2];
			participacion[0] = o[1] != null ? o[1].toString() : null;
			participacion[1] = o[2] != null ? o[2].toString() : null;
			participaciones.put(Integer.parseInt(o[0].toString()), participacion);
		}
		
		return participaciones;
	}
	
	@Override
	public List<DTOLlavesProcesos> obtenerParLlavesProceso(Integer idDetalleProceso, Integer idParticipacion,
		Integer modoEjecucion) {
		Session session = getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOLlavesProcesos> criteria = builder.createQuery(DTOLlavesProcesos.class);
		Root<DTOLlavesProcesos> root = criteria.from(DTOLlavesProcesos.class);
		Predicate[] predicates = new Predicate[3];
		predicates[0] = builder.equal(root.get("idDetalleProceso"), idDetalleProceso);
		predicates[1] = builder.equal(root.get("idParticipacion"), idParticipacion);
		predicates[2] = builder.equal(root.get("modoEjecucion"), modoEjecucion);
		
		criteria.orderBy(builder.asc(root.get("tipoLlave")));
		criteria.select(root).where(predicates);
		
		return session.createQuery(criteria).getResultList();
	}
	
	@Override
	public void guardarActualizarLlave(DTOLlavesProcesos llave) {
		llave.setUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
		llave.setFechaHora(new Date());
		getSession().merge(llave);
		getSession().flush();
	}
}
