package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.dao.DAOCargaPrimeraCapaInterface;
import mx.ine.procprimerinsa.dto.DTOEstatusCargaPrimeraCapaDistrito;
import mx.ine.procprimerinsa.dto.db.DTOServidoresPublicos;

@Scope("prototype")
@Repository("daoCargaPrimeraCapa")
public class DAOCargaPrimeraCapa extends DAOGeneric<Serializable, Serializable> implements DAOCargaPrimeraCapaInterface {
		
	private static final String DETALLES = "detalles";
	private static final String ID_PROCESO = "idProceso";
	private static final String TIPO_ELECCION = "tipoEleccion";
	private static final String ID_DETALLE = "idDetalle";
	private static final String ID_PARTICIPACION = "idParticipacion";
	private static final String ID_CORTE = "idCorte";
	private static final String USUARIO = "usuario";
	
	@Override
	public List<DTOEstatusCargaPrimeraCapaDistrito> obtieneEstatusCargaPrimeraCapa(List<Integer> detalles,
			Integer idCorte) {
		List<DTOEstatusCargaPrimeraCapaDistrito> distritos = new LinkedList<>();
		
		List<Object[]> list = getSessionReportes().createNativeQuery(getContainer().getQuery("query_cargaPrimeraCapa_obtieneEstatus"),
																	Object[].class)
							.setParameterList(DETALLES, detalles)
							.setParameter(ID_CORTE, idCorte)
							.list();
		
		for(Object[] o : list) {
			distritos.add(mapeaDistrito(o));
		}
		
		return distritos;
	}

	@Override
	public DTOEstatusCargaPrimeraCapaDistrito obtieneEstatusCargaPrimeraCapaDistrito(Integer idDetalle,
			Integer idParticipacion, Integer idCorte) {
		Object[] o = getSession().createNativeQuery(getContainer().getQuery("query_cargaPrimeraCapa_obtieneEstatusDistrito"),
													Object[].class)
							.setParameter(ID_DETALLE, idDetalle)
							.setParameter(ID_PARTICIPACION, idParticipacion)
							.setParameter(ID_CORTE, idCorte)
							.getSingleResult();
		
		return mapeaDistrito(o);
	}
	
	@Override
	public List<DTOEstatusCargaPrimeraCapaDistrito> obtieneLogEstatusCargaPrimeraCapa(List<Integer> detalles, Integer idCorte) {
		List<DTOEstatusCargaPrimeraCapaDistrito> distritos = new LinkedList<>();
		DTOEstatusCargaPrimeraCapaDistrito distrito;
		List<Object[]> list = getSessionReportes().createNativeQuery(getContainer().getQuery("query_cargaPrimeraCapa_obtieneLogEstatusCarga"),
																	Object[].class)
												.setParameterList(DETALLES, detalles)
												.setParameter(ID_CORTE, idCorte)
												.list();
		for(Object[] o : list) {
			distrito = mapeaDistrito(o);
			distritos.add(mapeaLogDistrito(o, distrito));
		}
		
		return distritos;
		
	}
	
	@Override
	public void actualizaEstatusCargaPrimeraCapaDistrito(DTOEstatusCargaPrimeraCapaDistrito distrito) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_cargaPrimeraCapa_actualizaEstatusCarga"))
				.setParameter(ID_DETALLE, distrito.getIdDetalleProceso())
				.setParameter(ID_PARTICIPACION, distrito.getIdParticipacion())
				.setParameter("etapa", distrito.getEtapa())
				.setParameter("estatus", distrito.getEstatus())
				.setParameter(USUARIO, distrito.getUsuario())
				.executeUpdate();
		session.flush();
	}
	
	@Override
	public void reiniciarEstatusCarga(List<Integer> detalles, String usuario) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_cargaPrimeraCapa_reiniciaEstatusCarga"))
				.setParameterList(DETALLES, detalles)
				.setParameter(USUARIO, usuario)
				.executeUpdate();
		session.createNativeMutationQuery(getContainer().getQuery("query_cargaPrimeraCapa_reiniciaLogEstatusCarga"))
				.setParameterList(DETALLES, detalles)
				.executeUpdate();
		session.flush();
	}
	
	@Override
	public void reiniciaCargaOrdenada(Integer idProceso, List<Integer> detalles) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_cargaPrimeraCapa_reiniciaCargaOrdenada"))
				.setParameter(ID_PROCESO, idProceso)
				.setParameterList(DETALLES, detalles)
				.executeUpdate();
		session.flush();
	}
	
	@Override
	public void reiniciaCargaServidoresPublicos(Integer idDetalle) {
		Session session = getSessionLN();
		session.createNativeMutationQuery(getContainer().getQuery("query_cargaPrimeraCapa_reiniciaCargaServidoresPublicos"))
				.setParameter(ID_DETALLE, idDetalle)
				.executeUpdate();
		session.flush();
	}
	
	@Override
	public Queue<DTOServidoresPublicos> obtieneCargaServidoresPublicos(Integer idProceso, Integer idDetalle, 
			Integer maxResults, Long offset) {
		Queue<DTOServidoresPublicos> servidores = new LinkedList<>();
		DTOServidoresPublicos servidor;

		List<Object[]> list = getSessionReportes().createNativeQuery(getContainer().getQuery("query_cargaPrimeraCapa_obtieneCargaServidoresPublicos"),
																	Object[].class)
							.setParameter(ID_PROCESO, idProceso)
							.setParameter(ID_DETALLE, idDetalle)
							.setParameter("offset", offset.intValue())
							.setParameter("maxResults", maxResults)
							.list();
		
		for(Object[] o : list) {
			servidor = new DTOServidoresPublicos();
			servidor.setIdProcesoElectoral(Integer.parseInt(o[0].toString()));
			servidor.setIdDetalleProceso(Integer.parseInt(o[1].toString()));
			servidor.setIdCorteSPN(Integer.parseInt(o[2].toString()));
			servidor.setNumeroCredencialElector(o[3]!=null?o[3].toString():" ");
			servidores.add(servidor);
		}
		
		return servidores;
	}
	
	@Override
	public Queue<DTOServidoresPublicos> obtieneSpoolServidoresPublicos(Integer idDetalle, 
			Integer maxResults, Long offset) {
		Queue<DTOServidoresPublicos> servidores = new LinkedList<>();
		DTOServidoresPublicos servidor;
		
		try(Session session = openSessionLN()) {
			List<Object[]> list = session.createNativeQuery(getContainer().getQuery("query_cargaPrimeraCapa_obtieneSpoolServidoresPublicos"),
																		Object[].class)
								.setParameter(ID_DETALLE, idDetalle)
								.setParameter("offset", offset.intValue())
								.setParameter("maxResults", maxResults)
								.list();
			
			for(Object[] o : list) {
				servidor = new DTOServidoresPublicos();
				servidor.setIdProcesoElectoral(Integer.parseInt(o[0].toString()));
				servidor.setIdDetalleProceso(Integer.parseInt(o[1].toString()));
				servidor.setIdCorteSPN(Integer.parseInt(o[2].toString()));
				servidor.setNumeroCredencialElector(o[3]!=null?o[3].toString():" ");
				servidores.add(servidor);
			}
		}
		
		return servidores;
	}
	
	@Override
	@Transactional
	public Object[] obtieneCorteServidoresPublicos(Integer idDetalle) {
		try(Session session = openSessionLN()) {
			return session.createNativeQuery(getContainer().getQuery("query_cargaPrimeraCapa_obtieneCorteServidoresPublicos"),
											Object[].class)
						.setParameter(ID_DETALLE, idDetalle)
						.getSingleResult();
		}
	}
	
	@Override
	public Object[] obtieneCorteAreasSecciones(List<Integer> detalles) {
		return getSession().createNativeQuery(getContainer().getQuery("query_cargaPrimeraCapa_obtieneCorteAreasSecciones"),
										Object[].class)
						.setParameterList(DETALLES, detalles)
						.getSingleResult();
	}

	@Override
	public Object[] obtieneCorteSeccionesCompartidas(List<Integer> detalles) {
		return getSession().createNativeQuery(getContainer().getQuery("query_cargaPrimeraCapa_obtieneCorteSeccionesCompartidas"),
											Object[].class)
							.setParameterList(DETALLES, detalles)
							.getSingleResult();
	}

	@Override
	public Object[] obtieneCorteOrdenada(Integer idProceso, List<Integer> detalles) {
		return getSession().createNativeQuery(getContainer().getQuery("query_cargaPrimeraCapa_obtieneCorteOrdenada"),
											Object[].class)
							.setParameter(ID_PROCESO, idProceso)
							.setParameterList(DETALLES, detalles)
							.getSingleResult();
	}

	@Override
	public List<String> obtieneEstatusIndices() {
		return getSession().createNativeQuery(getContainer().getQuery("query_cargaPrimeraCapa_obtieneEstatusIndices"),
											String.class)
							.getResultList();
	}

	@Override
	public List<String> obtieneEstatusTriggers() {
		return getSession().createNativeQuery(getContainer().getQuery("query_cargaPrimeraCapa_obtieneEstatusTriggers"),
											String.class)
							.getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public Map<String, String> obtieneMapaParticipaciones(Integer idProceso, String tipoEleccion) {
		Map<String, String> participaciones = new HashMap<>();
		
		List<Object[]> list = getSessionReportes().createNativeQuery(getContainer().getQuery("query_cargaPrimeraCapa_obtieneMapaParticipaciones"),
																	Object[].class)
													.setParameter(ID_PROCESO, idProceso)
													.setParameter(TIPO_ELECCION, tipoEleccion)
													.list();
		
		for(Object[] o : list) {
			participaciones.put(o[1].toString(), o[0].toString());
		}
		
		return participaciones;
	}
	
	private DTOEstatusCargaPrimeraCapaDistrito mapeaDistrito(Object[] o) {
		DTOEstatusCargaPrimeraCapaDistrito distrito = new DTOEstatusCargaPrimeraCapaDistrito();
		distrito.setIdProcesoElectoral(Integer.parseInt(o[0].toString()));
		distrito.setIdDetalleProceso(Integer.parseInt(o[1].toString()));
		distrito.setIdEstado(Integer.parseInt(o[2].toString()));
		distrito.setNombreEstado(o[3].toString());
		distrito.setIdGrupo(Integer.parseInt(o[4].toString()));
		distrito.setIdParticipacion(Integer.parseInt(o[5].toString()));
		distrito.setIdDistrito(Integer.parseInt(o[6].toString()));
		distrito.setNombreDistrito(o[7].toString());
		distrito.setEtapa(Integer.parseInt(o[8].toString()));
		distrito.setEstatus(o[9] != null ? o[9].toString() : null);
		distrito.setDatosInsaculados(Integer.parseInt(o[10].toString()));
		distrito.setDatosInsaculadosCapa1(Integer.parseInt(o[11].toString()));
		distrito.setResultados1aInsa(Integer.parseInt(o[12].toString()));
		distrito.setResultados1aInsaCapa1(Integer.parseInt(o[13].toString()));
		distrito.setUsuario(o[14].toString());
		distrito.setFechaHora((Date)o[15]);
		return distrito;
	}
	
	private DTOEstatusCargaPrimeraCapaDistrito mapeaLogDistrito(Object[] o, DTOEstatusCargaPrimeraCapaDistrito distrito) {
		distrito.setNombreOrden(o[16] != null ? (Date)o[16] : null);
		distrito.setNombreOrdenFinalizado(o[17] != null ? (Date)o[17] : null);
		distrito.setTiempoMarcadoNombreOrden(o[18] != null ? o[18].toString() : null);
		distrito.setReinicioYaEsInsaculado(o[19] != null ? (Date)o[19] : null);
		distrito.setReinicioYaEsInsaculadoFinalizado(o[20] != null ? (Date)o[20] : null);
		distrito.setTiempoReinicioYaEsInsaculado(o[21] != null ? o[21].toString() : null);
		distrito.setAreasSecciones(o[22] != null ? (Date)o[22] : null);
		distrito.setSeccionesCompartidas(o[23] != null ? (Date)o[23] : null);
		distrito.setMalReferenciados(o[24] != null ? (Date)o[24] : null);
		distrito.setMarcadoFinalizado(o[25] != null ? (Date)o[25] : null);
		distrito.setTiempoMarcadoARE(o[26] != null ? o[26].toString() : null);
		distrito.setOrdenGeografico(o[27] != null ? (Date)o[27] : null);
		distrito.setOrdenLetraAlf(o[28] != null ? (Date)o[28] : null);
		distrito.setOrdenVisita(o[29] != null ? (Date)o[29] : null);
		distrito.setOrdenFinalizado(o[30] != null ? (Date)o[30] : null);
		distrito.setTiempoOrden(o[31] != null ? o[31].toString() : null);
		distrito.setBorradoPrimeraCapa(o[32] != null ? (Date)o[32] : null);
		distrito.setCargaDatosInsaculados(o[33] != null ? (Date)o[33] : null);
		distrito.setCargaResultados1aInsa(o[34] != null ? (Date)o[34] : null);
		distrito.setCreacionReinicioSecuencias(o[35] != null ? (Date)o[35] : null);
		distrito.setCargaFinalizada(o[36] != null ? (Date)o[36] : null);
		distrito.setTiempoCarga(o[37] != null ? o[37].toString() : null);
		return distrito;
	}

}
