package mx.ine.procprimerinsa.bsd.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASReporteBitacoraInterface;
import mx.ine.procprimerinsa.bsd.BSDReporteBitacoraInterface;
import mx.ine.procprimerinsa.dto.DTOReporteBitacora;

@Component("bsdReporteBitacora")
@Scope("prototype")
public class BSDReporteBitacoraImpl implements BSDReporteBitacoraInterface {

	private static final long serialVersionUID = 2718517544526718038L;
	
	@Autowired
	@Qualifier("asReporteBitacora")
	private ASReporteBitacoraInterface asReporteBitacora;
	
	@Override
	public List<DTOReporteBitacora> obtenerRegistrosBitacora(List<Integer> detalles, Date fechaInicio, Date fechaFin) {
		return asReporteBitacora.obtenerRegistrosBitacora(detalles, fechaInicio, fechaFin);
	}
	
}
