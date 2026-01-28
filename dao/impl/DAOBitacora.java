package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mx.ine.procprimerinsa.dao.DAOBitacoraInterface;
import mx.ine.procprimerinsa.dto.DTOUsuarioLogin;
import mx.ine.procprimerinsa.dto.db.DTOBitacoraAcceso;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Scope("prototype")
@Repository("daoBitacora")
public class DAOBitacora extends DAOGeneric<Serializable, Serializable> implements DAOBitacoraInterface {

	@Override
	public List<DTOBitacoraAcceso> obtenerBitacoraAcceso(Date fechaInicial) {
		Session session = getSessionReportes();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOBitacoraAcceso> criteria = builder.createQuery(DTOBitacoraAcceso.class);
		Root<DTOBitacoraAcceso> root = criteria.from(DTOBitacoraAcceso.class);
		
		Predicate predicateDate = builder.greaterThanOrEqualTo(
											root.get("fechaHoraInicio").as(Date.class),
											fechaInicial);
		
		criteria.select(root)
				.where(predicateDate)
				.orderBy(builder.desc(root.get("idBitacoraAcceso")));
		
		Query<DTOBitacoraAcceso> query = session.createQuery(criteria);
		return query.getResultList();
	}
	
	@Override
	public void regitroBitacoraAcceso(DTOBitacoraAcceso bitacora) {
		Session session = getSession();
		bitacora.setIdBitacoraAcceso(obtenNumSecBitacoraAcceso());
		bitacora.setFechaHoraInicio(new Date());
		bitacora.setFechaHoraFin(null);
		session.persist(bitacora);
		session.flush();
	}

	@Override
	public void regitroBitacoraCierre(DTOUsuarioLogin usuario) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_bitacora_fin_session"))
				.setParameter("idBitacoraAcceso", usuario.getIdBitacoraAcceso())
				.setParameter("ipUsuario", usuario.getIpUsuario())
				.setParameter("fechaHoraFin", new Date())
				.executeUpdate();
		session.flush();
	}
	
	private Integer obtenNumSecBitacoraAcceso() {
		return getSession().createNativeQuery(getContainer().getQuery("query_bitacora_secuencia"),
													BigDecimal.class)
										.getSingleResult().intValue();
	}

}
