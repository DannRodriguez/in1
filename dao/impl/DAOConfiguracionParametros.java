package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.jboss.logging.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.procprimerinsa.dao.DAOConfiguracionParametrosInterface;
import mx.ine.procprimerinsa.dto.DTOCombo;
import mx.ine.procprimerinsa.dto.DTOConfiguracionParametros;

@Scope("prototype")
@Repository("daoConfiguracionParametros")
public class DAOConfiguracionParametros extends DAOGeneric<Serializable, Serializable> implements DAOConfiguracionParametrosInterface{

	private static final Logger logger = Logger.getLogger(DAOConfiguracionParametros.class);
	private static final String ID_PROCESO = "idProceso";
	private static final String ID_DETALLE = "idDetalle";
	private static final String ID_ESTADO = "idEstado";
	private static final String ID_DISTRITO = "idDistrito";
	private static final String ID_PARAMETRO = "idParametro";
	private static final String VALOR_PARAMETRO = "valorParametro";
	private static final String TIPO_JUNTA = "tipoJunta";
	private static final String ID_CORTE = "idCorte";
	
	@Override
	public List<DTOConfiguracionParametros> obtenerLista(Integer idCorte){
		List<DTOConfiguracionParametros> parametros = new ArrayList<>();
		DTOConfiguracionParametros parametro;
		
		try {
			List<Object[]> lista = getSession().createNativeQuery(getContainer().getQuery("query_configuracionParametros_consultaGeneral"), 
															Object[].class)
									.setParameter(ID_CORTE, idCorte)
									.list();
			
			for(Object[] o: lista) {
				parametro = new DTOConfiguracionParametros();
				
				parametro.setIdParametro(Integer.valueOf(o[0].toString()));
				parametro.setDescripcionParametro(o[1]!=null?o[1].toString():null);
				parametro.setDescripcionValores(o[2]!=null?o[2].toString():null);
				parametro.setIdProceso(o[3]!=null?Integer.valueOf(o[3].toString()):null);
				parametro.setIdDetalle(o[4]!=null?Integer.valueOf(o[4].toString()):null);
				parametro.setIdEstado(o[5]!=null?Integer.valueOf(o[5].toString()):null);
				parametro.setEstado(o[6]!=null?o[6].toString():null);
				parametro.setIdDistrito(o[7]!=null?Integer.valueOf(o[7].toString()):null);
				parametro.setCabeceraDistrital(o[8]!=null?o[8].toString():null);
				parametro.setTipoJunta(o[9]!=null?o[9].toString():null);
				parametro.setValorParametro(o[10]!=null?o[10].toString():null);
				
				parametros.add(parametro);
			}
		} catch(Exception e) {
			logger.error("ERROR DAOConfiguracionParametros -obtenerLista: ", e);
		}
		
		return parametros;
	}
	
	@Override
	public Map<String, DTOCombo> obtenerDescripcionParametros(){
		Map<String, DTOCombo> parametros = new LinkedHashMap<>();
		DTOCombo parametro;
		
		try {
			List<Object[]> lista = getSession().createNativeQuery(getContainer().getQuery("query_configuracionParametros_consultaDescripcion"),
																Object[].class)
									.list();
			
			for(Object[] o: lista) {
				parametro = new DTOCombo();
				
				parametro.setId(o[0]!=null?o[0].toString():null);
				parametro.setValue(o[1]!=null?o[1].toString():null);
				
				parametros.put(parametro.getId(), parametro);
			}
		} catch(Exception e) {
			logger.error("ERROR DAOConfiguracionParametros -obtenerDescripcionParametros: ", e);
		}
		
		return parametros;
	}
	
	@Override
	public Long obtenerIdParametro() {
		try {
			return (getSession().createNativeQuery(getContainer().getQuery("query_configuracionParametros_consultaIdParametro"), 
												BigDecimal.class)
					.getSingleResult()).longValue();
			
		} catch(Exception e) {
			logger.error("ERROR DAOConfiguracionParametros -obtenerIdParametro: ", e);
			return null;
		}
	}
	
	@Override
	public boolean actualizaParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito, String tipoJunta, 
			Integer idParametro, String valorParametro) {
		try {
			Session session = getSession();
			int result = session.createNativeMutationQuery(getContainer().getQuery("query_configuracionParametros_actualizacion"))
								.setParameter(ID_PROCESO, idProceso)
								.setParameter(ID_DETALLE, idDetalle)
								.setParameter(ID_ESTADO, idEstado)
								.setParameter(ID_DISTRITO, idDistrito)
								.setParameter(TIPO_JUNTA, tipoJunta)
								.setParameter(ID_PARAMETRO, idParametro)
								.setParameter(VALOR_PARAMETRO, valorParametro)
								.executeUpdate();
			session.flush();
			
			return result > 0;
		} catch(Exception e) {
			logger.error("ERROR ConfiguracionParametros -actualizaParametro: ", e);
			return false;
		}
	}
	
	@Override
	public boolean eliminaParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito, String tipoJunta, Integer idParametro) {
		
		try {
			Session session = getSession();
			int result = session.createNativeMutationQuery(getContainer().getQuery("query_configuracionParametros_eliminacion"))
						.setParameter(ID_PROCESO, idProceso)
						.setParameter(ID_DETALLE, idDetalle)
						.setParameter(ID_ESTADO, idEstado)
						.setParameter(ID_DISTRITO, idDistrito)
						.setParameter(TIPO_JUNTA, tipoJunta)
						.setParameter(ID_PARAMETRO, idParametro)
						.executeUpdate();
			session.flush();
			
			return result > 0;
		} catch(Exception e) {
			logger.error("ERROR ConfiguracionParametros -eliminaParametro: ", e);
			return false;
		}
	}
	
	@Override
	public boolean agregaDescripcion(Integer idParametro, String descripcionParametro, String descripcionValores) {
		
		try {
			Session session = getSession();
			int result = session.createNativeMutationQuery(getContainer().getQuery("query_configuracionParametros_insertaDescripcion"))
					.setParameter(ID_PARAMETRO, idParametro)
					.setParameter("descripcionParametro", descripcionParametro)
					.setParameter("descripcionValores", descripcionValores)
					.executeUpdate();
			session.flush();
			
			return result > 0;
		} catch(Exception e) {
			logger.error("ERROR ConfiguracionParametros -agregaDescripcion: ", e);
			return false;
		}
	}
	
	@Override
	public boolean agregaParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito, String tipoJunta, 
			Integer idParametro, String valorParametro) {
		
		try {
			Session session = getSession();
			int result = session.createNativeMutationQuery(getContainer().getQuery("query_configuracionParametros_insertaParametro"))
								.setParameter(ID_PROCESO, idProceso)
								.setParameter(ID_DETALLE, idDetalle)
								.setParameter(ID_ESTADO, idEstado)
								.setParameter(ID_DISTRITO, idDistrito)
								.setParameter(TIPO_JUNTA, tipoJunta)
								.setParameter(ID_PARAMETRO, idParametro)
								.setParameter(VALOR_PARAMETRO, valorParametro)
								.executeUpdate();
			session.flush();
			
			return result > 0;
		} catch(Exception e) {
			logger.error("ERROR ConfiguracionParametros -agregaParametro: ", e);
			return false;
		}
	}
	
}
