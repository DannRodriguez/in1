package mx.ine.procprimerinsa.dao;

import java.util.Date;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOReporteBitacora;

public interface DAOReporteBitacoraInterface{

	public List<DTOReporteBitacora>  obtenerRegistrosBitacora(List<Integer> detalles, Date fechaInicio, Date fechaFin);
	
}
