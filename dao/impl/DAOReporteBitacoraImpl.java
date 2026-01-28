package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.query.NativeQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import jakarta.persistence.TemporalType;
import mx.ine.procprimerinsa.dao.DAOReporteBitacoraInterface;
import mx.ine.procprimerinsa.dto.DTOReporteBitacora;
import mx.ine.procprimerinsa.util.Utilidades;

@Scope("prototype")
@Repository("daoReporteBitacora")
public class DAOReporteBitacoraImpl extends DAOGeneric<Serializable, Serializable> implements DAOReporteBitacoraInterface {
	
	@Override
	public List<DTOReporteBitacora> obtenerRegistrosBitacora(List<Integer> detalles, Date fechaInicio, Date fechaFin) {
		List<DTOReporteBitacora> registrosBitacora = new ArrayList<>();
		DTOReporteBitacora registroBitacora;
		
		NativeQuery<Object[]> query = getSessionReportes().createNativeQuery(getContainer().getQuery("query_reporteBitacora_obtenerRegistros"),
																	Object[].class)
												.setParameterList("detalles", detalles);
		
		Utilidades.setParameterDate(query, "fechaInicio", fechaInicio, TemporalType.DATE);
		Utilidades.setParameterDate(query, "fechaFin", fechaFin, TemporalType.DATE);
		
		List<Object[]> list = query.list();
		
		for(Object[] o : list) {
			registroBitacora = new DTOReporteBitacora();
			registroBitacora.setIdProceso(Integer.parseInt(o[0].toString()));
			registroBitacora.setIdDetalle(Integer.parseInt(o[1].toString()));
			registroBitacora.setNombreSistema(o[2].toString());
			registroBitacora.setUsuarioSistema(o[3].toString());
			registroBitacora.setRolUsuario(o[4].toString());
			registroBitacora.setNivelGeografico(o[5].toString());
			registroBitacora.setVista(o[6].toString());
			registroBitacora.setModulo(o[7].toString());
			registroBitacora.setFechaIngreso(o[8].toString());
			registrosBitacora.add(registroBitacora);
		}
		
		
		return registrosBitacora;
	}

}
