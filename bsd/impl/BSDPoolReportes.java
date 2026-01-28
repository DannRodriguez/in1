package mx.ine.procprimerinsa.bsd.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASPoolReportesInterface;
import mx.ine.procprimerinsa.bsd.BSDPoolReportesInterface;
import mx.ine.procprimerinsa.dto.DTOAccesosSistema;
import mx.ine.procprimerinsa.dto.DTOPoolReportes;

@Component("bsdPoolReportes")
@Scope("prototype")
public class BSDPoolReportes implements BSDPoolReportesInterface {
	
	private static final long serialVersionUID = -7974367629864174984L;

	@Autowired
	@Qualifier("asPoolReportes")
	private ASPoolReportesInterface asPoolReportes;
	
	@Override
	public List<DTOPoolReportes> obtenerDatosInsaculados(List<Integer> detalles, Integer idCorte) {
		return asPoolReportes.obtenerDatosInsaculados(detalles, idCorte);
	}

	@Override
	public List<DTOAccesosSistema> obtenerAccesosSistema(Integer idSistema, Date fecha) {
		return asPoolReportes.obtenerAccesosSistema(idSistema, fecha);
	}

	@Override
	public byte[] crearZip(Integer idProceso, List<Integer> detalles, Integer idCorte, 
			Date fecha) throws IOException {
		return asPoolReportes.crearZip(idProceso, detalles, idCorte, fecha);
	}
	
}
