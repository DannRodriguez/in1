package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.procprimerinsa.dao.DAOCTipoVotoInterface;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVotoParticipacion;

@Scope("prototype")
@Repository("daoCTipoVoto")
public class DAOCTipoVoto extends DAOGeneric<DTOCTipoVoto, Serializable> implements DAOCTipoVotoInterface {
	
	private static final String ID_DETALLE = "idDetalle";
	private static final String ID_DETALLE_PROCESO = "idDetalleProceso";
	private static final String DETALLES = "detalles";
	private static final String ID_PARTICIPACION = "idParticipacion";
	private static final String ID_TIPO_VOTO = "idTipoVoto";
	private static final String ID_CORTE = "idCorte";
	
	@Override
	public List<DTOCTipoVoto> obtieneCTiposVoto(Integer idDetalle) {
		Session session = getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOCTipoVoto> criteria = builder.createQuery(DTOCTipoVoto.class);
		Root<DTOCTipoVoto> root = criteria.from(DTOCTipoVoto.class);
		Predicate[] predicates = new Predicate[1];
		predicates[0] = builder.equal(root.get(ID_DETALLE_PROCESO), idDetalle);
		
		criteria.select(root)
			.where(predicates)
			.orderBy(builder.asc(root.get(ID_TIPO_VOTO)));
		
		Query<DTOCTipoVoto> query = session.createQuery(criteria);
		return query.getResultList();
	}
	
	@Override
	public List<DTOCTipoVotoParticipacion> obtieneCTiposVotoParticipacion(List<Integer> detalles) {
		Session session = getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOCTipoVotoParticipacion> criteria = builder.createQuery(DTOCTipoVotoParticipacion.class);
		Root<DTOCTipoVotoParticipacion> root = criteria.from(DTOCTipoVotoParticipacion.class);
		
		criteria.select(root)
			.where(root.get(ID_DETALLE_PROCESO).in(detalles))
			.orderBy(builder.asc(root.get(ID_DETALLE_PROCESO)),
					builder.asc(root.get(ID_PARTICIPACION)),
					builder.asc(root.get(ID_TIPO_VOTO)));
		
		Query<DTOCTipoVotoParticipacion> query = session.createQuery(criteria);
		return query.getResultList();
	}
	
	@Override
	public Map<Integer, DTOCTipoVoto> obtieneTiposVotoPorParticipacion(Integer idDetalle, Integer idParticipacion) {
		Map<Integer, DTOCTipoVoto> tiposVoto = new LinkedHashMap<>();
		DTOCTipoVoto tipoVoto;

		List<Object[]> list = getSession().createNativeQuery(getContainer().getQuery("query_procesoInsaculacion_obtieneTiposVoto"),
															Object[].class)
										.setParameter(ID_DETALLE, idDetalle)
										.setParameter(ID_PARTICIPACION, idParticipacion)
										.list();
		
		for(Object[] o : list) {
			tipoVoto = new DTOCTipoVoto();
			tipoVoto.setIdProcesoElectoral(Integer.parseInt(o[0].toString()));
			tipoVoto.setIdDetalleProceso(Integer.parseInt(o[1].toString()));
			tipoVoto.setIdTipoVoto(Integer.parseInt(o[2].toString()));
			tipoVoto.setDescripcion(o[3].toString());
			tipoVoto.setSiglas(o[4].toString());
			tipoVoto.setPorcentajeInsacular(Integer.parseInt(o[5].toString()));
			tipoVoto.setMinimoInsacular(Integer.parseInt(o[6].toString()));
			tipoVoto.setNombreArchivoListado(o[7].toString());
			tipoVoto.setTituloArchivoListado(o[8].toString());
			tipoVoto.setNombreArchivoCedula(o[9].toString());
			tipoVoto.setTituloArchivoCedula(o[10].toString());
			tipoVoto.setTipoIntegracion(Integer.parseInt(o[11].toString()));
			tipoVoto.setLeyendaCasilla(o[12].toString());
			tiposVoto.put(tipoVoto.getIdTipoVoto(), tipoVoto);
		}
		
		return tiposVoto;
	}
	
	@Override
	public Map<Integer, DTOParticipacionGeneral> obtieneParticipacionesPorTipoVoto(DTOCTipoVoto tipoVoto, 
			Integer idCorte) {
		Map<Integer, DTOParticipacionGeneral> participaciones = new LinkedHashMap<>();
		DTOParticipacionGeneral participacion;
		
		List<Object[]> lista = getSessionReportes().createNativeQuery(getContainer().getQuery("query_tipoVoto_obtieneParticipacionesPorTipoVoto"),
																	Object[].class)
													.setParameter(ID_DETALLE, tipoVoto.getIdDetalleProceso())
													.setParameter(ID_TIPO_VOTO, tipoVoto.getIdTipoVoto())
													.setParameter(ID_CORTE, idCorte)
													.list();
		
		for(Object[] o:lista) {
			participacion = new DTOParticipacionGeneral(Integer.parseInt(o[0].toString()),
										Integer.parseInt(o[1].toString()),
										o[2].toString());
			
			participaciones.put(participacion.getIdParticipacion(), participacion);
		}
		
		return participaciones;
	}
	
	@Override
	public boolean existeIdCTipoVoto(DTOCTipoVoto tipoVoto) {
		Optional<BigDecimal> tipoVotoOptional = getSession().createNativeQuery(getContainer().getQuery("query_tipoVoto_existeIdCTipoVoto"),
																				BigDecimal.class)
															.setParameter(ID_DETALLE, tipoVoto.getIdDetalleProceso())
															.setParameter(ID_TIPO_VOTO, tipoVoto.getIdTipoVoto())
															.uniqueResultOptional();
		return tipoVotoOptional.isPresent();
	}
	
	@Override
	public void guardaCTipoVoto(DTOCTipoVoto tipoVoto) {
		Session session = getSession();
		session.merge(tipoVoto);
		session.flush();
	}
	
	@Override
	public void eliminaCTipoVoto(DTOCTipoVoto tipoVoto) {
		Session session = getSession();
		session.remove(tipoVoto);
		session.flush();
	}
	
	@Override
	public void guardaTipoVotoParticipacion(DTOCTipoVotoParticipacion tipoVotoParticipacion) {
		Session session = getSession();
		session.merge(tipoVotoParticipacion);
		session.flush();
	}
	
	@Override
	public void eliminaParticipacionesTipoVoto(Integer idDetalle, Integer idTipoVoto) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_tipoVoto_eliminaParticipacionesTipoVoto"))
				.setParameter(ID_DETALLE, idDetalle)
				.setParameter(ID_TIPO_VOTO, idTipoVoto)
				.executeUpdate();
		session.flush();
	}
	
	@Override
	public void reiniciaCargaTipoVoto(List<Integer> detalles) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_tipoVoto_reiniciaCargaTipoVotoParticipacion"))
				.setParameterList(DETALLES, detalles)
				.executeUpdate();
		session.createNativeMutationQuery(getContainer().getQuery("query_tipoVoto_reiniciaCargaTipoVoto"))
				.setParameterList(DETALLES, detalles)
				.executeUpdate();
		session.flush();
	}
	
	@Override
	public void reiniciaCargaTipoVotoParticipacion(List<Integer> detalles) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_tipoVoto_reiniciaCargaTipoVotoParticipacion"))
				.setParameterList(DETALLES, detalles)
				.executeUpdate();
		session.flush();
	}
	
}
