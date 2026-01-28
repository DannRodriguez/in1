package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.procprimerinsa.dao.DAOImpresionCNInterface;
import mx.ine.procprimerinsa.dto.DTOCombo;
import mx.ine.procprimerinsa.dto.DTOParametrosMargenes;
import mx.ine.procprimerinsa.dto.DTOSeccionesLocalidades;
import mx.ine.procprimerinsa.dto.db.DTODatosInsaculados;

@Scope("prototype")
@Repository("daoImpresionCN")
public class DAOImpresionCN extends DAOGeneric<DTOCombo, Serializable> implements DAOImpresionCNInterface {

	private static final String ID_PROCESO = "idProceso";
	private static final String ID_DETALLE = "idDetalle";
	private static final String ID_PARTICIPACION = "idParticipacion";
	private static final String ID_ESTADO = "idEstado";
	private static final String ID_CORTE = "idCorte";
	private static final String ID_DISTRITO_FEDERAL = "idDistritoFederal";
	private static final String ID_DISTRITO_LOCAL = "idDistritoLocal";
	private static final String ID_MUNICIPIO = "idMunicipio";
	private static final String ID_LOCALIDAD = "idLocalidad";
	private static final String SECCION = "seccion";
	
	@Override
	public List<DTOCombo> obtenerARES(Integer idProceso, Integer idDetalle, Integer idParticipacion) {		
		List<DTOCombo> ares = new ArrayList<>();
		
		List<Object[]> list = getSessionReportes().createNativeQuery(getContainer().getQuery("query_impresionCN_comboARE"),
																	Object[].class)
												.setParameter(ID_PROCESO, idProceso)
												.setParameter(ID_DETALLE, idDetalle)
												.setParameter(ID_PARTICIPACION, idParticipacion)
												.list();

		for(Object[] o : list) {
			ares.add(new DTOCombo(o[0].toString(), 
								o[1].toString()));
		}
		
		return ares;
	}

	@Override
	public List<DTOSeccionesLocalidades> obtenerSecciones(Integer idEstado, Integer idCorte, Integer idDistritoFederal, Integer idDistritoLocal, Integer idMunicipio) {
		Map<Integer, DTOSeccionesLocalidades> secciones =  new LinkedHashMap<>();
		
		List<Object[]> list = getSessionReportes().createNativeQuery(getContainer().getQuery("query_impresionCN_comboSECCION"), 
																	Object[].class)
												.setParameter(ID_ESTADO, idEstado)
												.setParameter(ID_CORTE, idCorte)
												.setParameter(ID_DISTRITO_FEDERAL, idDistritoFederal)
												.setParameter(ID_DISTRITO_LOCAL, idDistritoLocal)
												.setParameter(ID_MUNICIPIO, idMunicipio)
												.list();
		
		for(Object[] o : list) {
			Integer seccion = Integer.parseInt(o[0].toString());
			Integer localidad = Integer.parseInt(o[1].toString());
			
			secciones.putIfAbsent(seccion, new DTOSeccionesLocalidades(seccion, 
																	new LinkedHashMap<>()));
			
			secciones.get(seccion)
					.getLocalidades()
					.putIfAbsent(localidad, new DTOCombo(localidad.toString(), 
														o[2].toString().trim()));
		}
		
		return new ArrayList<>(secciones.values());
	}

	@Override
	public List<Integer> obtenerManzanas(Integer idEstado, Integer idCorte, Integer seccion, Integer idLocalidad) {
		return getSessionReportes().createNativeQuery(getContainer().getQuery("query_impresionCN_comboManzanas"), 
													Integer.class)
									.setParameter(ID_ESTADO, idEstado)
									.setParameter(ID_CORTE, idCorte)
									.setParameter(SECCION, seccion)
									.setParameter(ID_LOCALIDAD, idLocalidad)
									.addScalar("manzana", StandardBasicTypes.INTEGER)
									.list();
	}
	
	@Override
	public List<Integer> obtenerOrdenVisita(Integer idProceso, Integer idDetalle, Integer idParticipacion, 
			Integer idAreaResponsabilidad, Integer seccion){
		return getSessionReportes().createNativeQuery(getContainer().getQuery("query_impresionCN_ordenVisitadatosInsaculadosParticion"),
													Integer.class)
								.setParameter(ID_PROCESO, idProceso)
								.setParameter(ID_DETALLE, idDetalle)
								.setParameter(ID_PARTICIPACION, idParticipacion)
								.setParameter("idAreaResponsabilidad", idAreaResponsabilidad)
								.setParameter(SECCION, seccion)
								.addScalar("ordenVisita", StandardBasicTypes.INTEGER)
								.list();
	}
	
	@Override
	public List<DTODatosInsaculados> obtenerCiudadanos(Integer idProceso, Integer idDetalle, Integer idParticipacion, 
										Integer idTipoVoto, Integer idAreaResponsabilidad, Integer ordenVisitaInicio, Integer ordenVisitaFin,
										Integer seccion, Integer idLocalidad, Integer manzana, 
										String numeroCredencialElector, Integer folio, Integer ordenamiento) {
		List<DTODatosInsaculados> ciudadanos = new LinkedList<>();
		DTODatosInsaculados ciudadano;

		List<Object[]> lista = getSessionReportes().createNativeQuery(getContainer().getQuery("query_impresionCN_datosInsaculadosParticion"),
																		Object[].class)
													.setParameter(ID_PROCESO, idProceso)
													.setParameter(ID_DETALLE, idDetalle)
													.setParameter(ID_PARTICIPACION, idParticipacion)
													.setParameter("idTipoVoto", idTipoVoto)
													.setParameter("idAreaResponsabilidad", idAreaResponsabilidad)
													.setParameter("ordenVisitaInicio", ordenVisitaInicio)
													.setParameter("ordenVisitaFin", ordenVisitaFin)
													.setParameter(SECCION, seccion)
													.setParameter(ID_LOCALIDAD, idLocalidad)
													.setParameter("manzana", manzana)
													.setParameter("numeroCredencialElector", numeroCredencialElector)
													.setParameter("folio", folio)
													.setParameter("ordenamiento", ordenamiento)
													.list();
		
		for(Object[] o: lista) {
			
			ciudadano = new DTODatosInsaculados();
			
			ciudadano.setIdProcesoElectoral(Integer.parseInt(o[0].toString()));
			ciudadano.setIdDetalleProceso(Integer.parseInt(o[1].toString()));
			ciudadano.setIdParticipacion(Integer.parseInt(o[2].toString()));
			ciudadano.setIdEstado(Integer.parseInt(o[3].toString()));
			ciudadano.setIdDistritoFederal(Integer.parseInt(o[4].toString()));
			ciudadano.setIdDistritoLocal(o[5]!=null?Integer.parseInt(o[5].toString()):null);
			ciudadano.setIdMunicipio(Integer.parseInt(o[6].toString()));
			ciudadano.setSeccion(o[7]!=null?Integer.parseInt(o[7].toString()):null);
			ciudadano.setIdCasilla(o[8]!=null?Integer.parseInt(o[8].toString()):null);
			ciudadano.setTipoCasilla(o[9]!=null?o[9].toString():null);
			ciudadano.setIdAreaResponsabilidad(o[10]!=null?Integer.parseInt(o[10].toString()):null);
			ciudadano.setNumeroCredencialElector(o[11].toString());
			ciudadano.setApellidoPaterno(o[12]!=null?o[12].toString():null);
			ciudadano.setApellidoMaterno(o[13]!=null?o[13].toString():null);
			ciudadano.setNombre(o[14]!=null?o[14].toString():null);
			ciudadano.setFolio(o[15]!=null?Integer.parseInt(o[15].toString()):null);
			ciudadano.setIdLocalidad(o[16]!=null?Integer.parseInt(o[16].toString()):null);
			ciudadano.setManzana(o[17]!=null?Integer.parseInt(o[17].toString()):null);
			ciudadano.setCalle(o[18]!=null?o[18].toString():null);
			ciudadano.setNumeroExterior(o[19]!=null?o[19].toString():null);
			ciudadano.setNumeroInterior(o[20]!=null?o[20].toString():null);
			ciudadano.setColonia(o[21]!=null?o[21].toString():null);
			ciudadano.setCodigoPostal(o[22]!=null?o[22].toString():null);
			ciudadano.setOrdenAlfabetico(o[23]!=null?Integer.parseInt(o[23].toString()):null);
			ciudadano.setOrdenVisita(o[24]!=null?Integer.parseInt(o[24].toString()):null);
			ciudadano.setOrdenLetra(o[25]!=null?Integer.parseInt(o[25].toString()):null);
			ciudadano.setOrdenGeografico(o[26]!=null?Integer.parseInt(o[26].toString()):null);
			ciudadano.setSemilla(o[27]!=null?Integer.parseInt(o[27].toString()):null);
			
			ciudadanos.add(ciudadano);
		}
			
		return ciudadanos;
	}
	
	@Override
	public DTOParametrosMargenes obtenerParametrosDocumento(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito) {
		DTOParametrosMargenes parametros = new DTOParametrosMargenes();

		List<Object[]> lista = getSessionReportes().createNativeQuery(getContainer().getQuery("query_impresionCN_parametrosDocumento"),
																	Object[].class)
													.setParameter(ID_PROCESO, idProceso)
													.setParameter(ID_DETALLE, idDetalle)
													.setParameter(ID_ESTADO, idEstado)
													.setParameter(ID_DISTRITO_FEDERAL, idDistrito)
													.list();
		
		for(Object[] o: lista) {
			
			switch(Integer.parseInt(o[0].toString())) {
				case 23:
					parametros.setdAncho(Float.parseFloat(o[1].toString()));
					break;
				case 24:
					parametros.setdLargo(Float.parseFloat(o[1].toString()));
					break;
				case 25:
					parametros.setDatos1Width(Float.parseFloat(o[1].toString()));
					break;
				case 26:
					parametros.setDatos1Left(Float.parseFloat(o[1].toString()));
					break;
				case 27:
					parametros.setDatos1Top(Float.parseFloat(o[1].toString()));
					break;
				case 28:
					parametros.setDatos2Width(Float.parseFloat(o[1].toString()));
					break;
				case 29:
					parametros.setDatos2Left(Float.parseFloat(o[1].toString()));
					break;
				case 30:
					parametros.setDatos2Top(Float.parseFloat(o[1].toString()));
					break;
				case 31:
					parametros.setDatos3Width(Float.parseFloat(o[1].toString()));
					break;
				case 32:
					parametros.setDatos3Left(Float.parseFloat(o[1].toString()));
					break;
				case 33:
					parametros.setDatos3Top(Float.parseFloat(o[1].toString()));
					break;
				case 34:
					parametros.setFolioAncho(Float.parseFloat(o[1].toString()));
					break;
				case 35:
					parametros.setFolioLargo(Float.parseFloat(o[1].toString()));
					break;
				case 36:
					parametros.setFolio1Right(Float.parseFloat(o[1].toString()));
					break;
				case 37:
					parametros.setFolio1Top(Float.parseFloat(o[1].toString()));
					break;
				case 38:
					parametros.setFolio2Right(Float.parseFloat(o[1].toString()));
					break;
				case 39:
					parametros.setFolio2Top(Float.parseFloat(o[1].toString()));
					break;
				case 40:
					parametros.setLogoAncho(Float.parseFloat(o[1].toString()));
					break;
				case 41:
					parametros.setLogoLargo(Float.parseFloat(o[1].toString()));
					break;
				case 42:
					parametros.setLogoRight(Float.parseFloat(o[1].toString()));
					break;
				case 43:
					parametros.setLogoTop(Float.parseFloat(o[1].toString()));
					break;
				case 44:
					parametros.setFirmaAncho(Float.parseFloat(o[1].toString()));
					break;
				case 45:
					parametros.setFirmaLargo(Float.parseFloat(o[1].toString()));
					break;
				case 46:
					parametros.setFirmaRight(Float.parseFloat(o[1].toString()));
					break;
				case 47:
					parametros.setFirmaTop(Float.parseFloat(o[1].toString()));
					break;
				case 48:
					parametros.setDireJuntaWidth(Float.parseFloat(o[1].toString()));
					break;
				case 49:
					parametros.setDireJuntaLeft(Float.parseFloat(o[1].toString()));
					break;
				case 50:
					parametros.setDireJuntaTop(Float.parseFloat(o[1].toString()));
					break;
			}
		}
		
		return parametros;
	}
	
}
