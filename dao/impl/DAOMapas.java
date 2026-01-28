package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import mx.ine.procprimerinsa.dao.DAOMapasInterface;
import mx.ine.procprimerinsa.dto.DTODetalleParticipaciones;
import mx.ine.procprimerinsa.dto.DTOEstado;
import mx.ine.procprimerinsa.dto.DTOEstadoDetalles;
import mx.ine.procprimerinsa.dto.DTOMapaInformacion;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.db.DTOMapas;
import mx.ine.procprimerinsa.util.Constantes;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Scope("prototype")
@Repository("daoMapas")
public class DAOMapas extends DAOGeneric<DTOMapaInformacion, Serializable> implements DAOMapasInterface {
	
	private static final String ID_CORTE = "idCorte";
	private static final String DETALLES = "detalles";

	@Override
	public List<DTOMapas> getBaseGrafica(Integer idEstado) {
		Session session = getSessionReportes();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOMapas> criteria = builder.createQuery(DTOMapas.class);
		Root<DTOMapas> root = criteria.from(DTOMapas.class);
		Predicate[] predicates;
		
		if(idEstado != null) {
			predicates = new Predicate[2];
			predicates[1] = builder.equal(root.get("idEstado"), idEstado);
		} else {
			predicates = new Predicate[1];
		}
		
		predicates[0] = builder.equal(root.get(ID_CORTE), Constantes.CORTE_MAPAS);
		
		criteria.select(root).where(predicates);
		
		Query<DTOMapas> query = session.createQuery(criteria);
		
		return query.getResultList();
		
	}
	
	@Override
	public List<DTOMapaInformacion> obtenerDatosMapasBD(List<Integer> estados, List<Integer> detalles, 
			Integer idCorte) {
		
		List<DTOMapaInformacion> distritos = new ArrayList<>();
		DTOMapaInformacion distrito;
		
		List<Object[]> lista = getSessionReportes().createNativeQuery(getContainer().getQuery("query_mapas_obtenerMapasInformacion"),
																	Object[].class)
												.setParameter(ID_CORTE, idCorte)
												.setParameter("inicioProceso", Constantes.ESTATUS_PERMISOS_VALIDOS)
												.setParameter("finProceso", Constantes.ESTATUS_GUARDANDO_BD_I)
												.setParameter("reinicioProceso", Constantes.ESTATUS_PROCESO_REINICIADO)
												.setParameterList(DETALLES, detalles)
												.setParameter("validaEstados", estados.isEmpty()?0:1)
												.setParameterList("estados", estados.isEmpty()?Arrays.asList(0):estados)
												.list();
		
		for(Object[] o:lista) {
			distrito = new DTOMapaInformacion();
	
			distrito.setIdEstado(Integer.parseInt(o[0].toString()));
			distrito.setNombreEstado(o[1].toString());
			distrito.setIdDistrito(Integer.parseInt(o[2].toString()));
			distrito.setCabecera(o[3].toString());
			distrito.setAbreviatura(o[4].toString());
			distrito.setColor(o[5].toString());
			distrito.setDescripcion(o[6].toString());
			distrito.setEstatus(Integer.parseInt(o[7].toString()));
			distrito.setHora(o[8].toString());
			distrito.setFechaHoraInicioProceso(o[9].toString());
			distrito.setPorcentajeAvance(Double.parseDouble(o[10].toString()));
			distrito.setFechaHoraFinProceso(o[11].toString());
			
			distritos.add(distrito);
		}
		
		return distritos;
		
	}
	
	@Override
	public List<Integer> getDetallesProceso(Integer idProceso, String tipoEleccion) {
		return getSessionReportes().createNativeQuery(getContainer().getQuery("query_mapas_obtenerDetalles"), 
													Integer.class)
								.setParameter("idProceso", idProceso)
								.setParameter("tipoEleccion", tipoEleccion)
								.list();
	}
	
	@Override
	public List<DTOEstadoDetalles> getDetallesProcesoHistoricos(Integer idEstado, Integer idDistrito) {
		Map<Integer, DTOEstadoDetalles> estados = new LinkedHashMap<>();
		
		List<Object[]> lista = getSessionReportes().createNativeQuery(getContainer().getQuery("query_mapas_obtenerDetallesProcesoHistoricos"),
																	Object[].class)
													.setParameter("idEstado", idEstado)
													.setParameter("idDistrito", idDistrito)
													.list();
		
		for(Object[] o : lista) {
			Integer idEstadoHistorico = Integer.parseInt(o[5].toString());
			Integer idDetalleHistorico = Integer.parseInt(o[2].toString());
			Integer idParticipacionHistorico = Integer.parseInt(o[4].toString());
			
			estados.putIfAbsent(idEstadoHistorico, 
						new DTOEstadoDetalles(
								idEstadoHistorico,
								o[6].toString()));
			
			estados.get(idEstadoHistorico)
					.getDetalles()
					.putIfAbsent(idDetalleHistorico, 
								new DTODetalleParticipaciones(Integer.parseInt(o[0].toString()),
													o[1].toString(),
													idDetalleHistorico, 
													o[3].toString()));
			
			estados.get(idEstadoHistorico)
					.getDetalles()
					.get(idDetalleHistorico)
					.getParticipaciones()
					.put(idParticipacionHistorico, 
						new DTOParticipacionGeneral(idParticipacionHistorico,
												Integer.parseInt(o[7].toString()),
												o[8].toString()));
						
		}
		
		return new ArrayList<>(estados.values());
		
	}
	
	@Override
	public Map<Integer, DTOEstado> getEstados(List<Integer> detalles, Integer idCorte) {
		Map<Integer, DTOEstado> estados = new LinkedHashMap<>();
		DTOEstado estado;
		
		List<Object[]> lista = getSessionReportes().createNativeQuery(getContainer().getQuery("query_mapas_obtenerEstados"), 
																	Object[].class)
												.setParameterList(DETALLES, detalles)
												.setParameter(ID_CORTE, idCorte)
												.list();
		
		for(Object[] o:lista) {
			estado = new DTOEstado(Integer.parseInt(o[0].toString()),
										o[1].toString());
			
			estados.put(estado.getIdEstado(), estado);
		}
		
		return estados;
	}
	
	@Override
	public Map<Integer, DTOParticipacionGeneral> getParticipaciones(Integer idDetalle, Integer idCorte) {
		Map<Integer, DTOParticipacionGeneral> participaciones = new LinkedHashMap<>();
		DTOParticipacionGeneral participacion;
		
		List<Object[]> lista = getSessionReportes().createNativeQuery(getContainer().getQuery("query_mapas_obtenerParticipaciones"), 
																	Object[].class)
												.setParameter("idDetalle", idDetalle)
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

}
