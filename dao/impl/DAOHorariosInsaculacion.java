package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.procprimerinsa.dao.DAOHorariosInsaculacionInterface;
import mx.ine.procprimerinsa.dto.db.DTOHorariosInsaculacion;

@Repository("daoHorariosInsaculacion")
@Scope("prototype")
public class DAOHorariosInsaculacion extends DAOGeneric<DTOHorariosInsaculacion, Serializable> implements DAOHorariosInsaculacionInterface{

	@Override
	public List<DTOHorariosInsaculacion> obtenerHorarios(List<Integer> detalles) {
		Session session = getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOHorariosInsaculacion> criteria = builder.createQuery(DTOHorariosInsaculacion.class);
		Root<DTOHorariosInsaculacion> root = criteria.from(DTOHorariosInsaculacion.class);
		criteria.orderBy(builder.asc(root.get("idHorario")),
						builder.asc(root.get("idEstado")));
		criteria.select(root).where(root.get("idDetalleProceso").in(detalles));
		Query<DTOHorariosInsaculacion> query = session.createQuery(criteria);
		return query.getResultList();
	}
	
	@Override
	public void eliminaHorarios(List<Integer> detalles, Integer idHorario) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_horariosInsaculacion_eliminaHorarios"))
				.setParameterList("detalles", detalles)
				.setParameter("idHorario", idHorario)
				.executeUpdate();
		session.flush();
	}

	@Override
	public Long obtieneIdHorario(List<Integer> detalles) {
		return getSession().createNativeQuery(getContainer().getQuery("query_horariosInsaculacion_obtieneIdHorario"),
											BigDecimal.class)
							.setParameterList("detalles", detalles)
							.getSingleResult().longValue();
	}

	@Override
	public void insertaHorario(Integer idProceso, List<Integer> detalles, Integer idEstado, Integer idHorario, Date horaInicio, Date horaFinal,
			String username) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_horariosInsaculacion_insertaHorario"))
				.setParameter("idProceso", idProceso)
				.setParameterList("detalles", detalles)
				.setParameter("idEstado", idEstado)
				.setParameter("idHorario", idHorario)
				.setParameter("horaInicio", horaInicio)
				.setParameter("horaFinal", horaFinal)
				.setParameter("username", username)
				.executeUpdate();
		session.flush();
	}

}
