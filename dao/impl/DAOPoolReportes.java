package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.query.NativeQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import jakarta.persistence.TemporalType;
import mx.ine.procprimerinsa.dao.DAOPoolReportesInterface;
import mx.ine.procprimerinsa.dto.DTOAccesosSistema;
import mx.ine.procprimerinsa.dto.DTOPoolReportes;

@Scope("prototype")
@Repository("daoPoolReportes")
public class DAOPoolReportes  extends DAOGeneric<DTOPoolReportes, Serializable> implements DAOPoolReportesInterface {
	
	private static final String ID_SISTEMA = "idSistema";
	private static final String ID_PROCESO = "idProceso";
	private static final String DETALLES = "detalles";
	private static final String ID_CORTE = "idCorte";
	private static final String FECHA = "fecha";
			
	@Override
	public List<DTOPoolReportes> obtenerDatosInsaculados(List<Integer> detalles, Integer idCorte) {
		List<DTOPoolReportes> reportes = new ArrayList<>();
		DTOPoolReportes reporte;
		
		List<Object[]> lista = getSessionReportes().createNativeQuery(getContainer().getQuery("query_poolReportes_datosInsaculados"),
																	Object[].class)
													.setParameterList(DETALLES, detalles)
													.setParameter(ID_CORTE, idCorte)
													.list();
		
		for(Object[] o:lista) {
			reporte = new DTOPoolReportes();
			
			reporte.setNombreEstado(o[0].toString());
			reporte.setIdDistrito(Integer.parseInt(o[1].toString()));
			reporte.setCabeceraDistrital(o[2].toString());
			reporte.setSeccionesInsaculadas(Integer.parseInt(o[3].toString()));
			reporte.setCiudadanosInsaculados(Integer.parseInt(o[4].toString()));
			reporte.setHoraInicio(o[5].toString());
			reporte.setHoraFin(o[6].toString());
			reporte.setTiempoEjecucion(o[7].toString());
			
			reportes.add(reporte);
		}
	
		return reportes;
	}
	
	@Override
	public List <DTOAccesosSistema> obtenerAccesosSistema(Integer idSistema, Date fecha){
		List<DTOAccesosSistema> accesos = new ArrayList<>();
		DTOAccesosSistema acceso;
		
		List<Object[]> lista = getSessionReportes().createNativeQuery(getContainer().getQuery("query_poolReportes_accesoSistema"),
																	Object[].class)
													.setParameter(ID_SISTEMA, idSistema)
													.setParameter(FECHA, fecha, TemporalType.DATE)
													.list();
		
		for(Object[] o:lista) {
			acceso = new DTOAccesosSistema();
			
			acceso.setIdSistema(Integer.parseInt(o[0].toString()));
			acceso.setRolUsuario(o[1].toString());
			acceso.setAccesos(Integer.parseInt(o[2].toString()));
			
			accesos.add(acceso);
		}
	
		return accesos;
	}
	
	@Override
	public List<String> regresaDatos(Integer idProceso, List<Integer> detalles, Integer idCorte, 
			Date fecha, String consulta) {
		NativeQuery<String> qry = getSessionReportes().createNativeQuery(getContainer().getQuery(consulta), 
																		String.class);
		
		if(idProceso != null) {
			qry.setParameter(ID_PROCESO, idProceso);
		}
		
		if(detalles != null && !detalles.isEmpty()) {
			qry.setParameterList(DETALLES, detalles);
		}
		
		if(idCorte != null) {
			qry.setParameter(ID_CORTE, idCorte);
		}
		
		if(fecha != null) {
			qry.setParameter(FECHA, fecha, TemporalType.DATE);
		}
		
		return qry.list();
	}
	
}
