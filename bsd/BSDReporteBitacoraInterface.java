package mx.ine.procprimerinsa.bsd;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOReporteBitacora;

public interface BSDReporteBitacoraInterface extends Serializable {
	
	public List<DTOReporteBitacora> obtenerRegistrosBitacora(List<Integer> detalles, Date fechaInicio, Date fechaFin);

}
