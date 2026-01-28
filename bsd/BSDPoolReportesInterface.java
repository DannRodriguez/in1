package mx.ine.procprimerinsa.bsd;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOAccesosSistema;
import mx.ine.procprimerinsa.dto.DTOPoolReportes;

public interface BSDPoolReportesInterface extends Serializable {

	public List<DTOPoolReportes> obtenerDatosInsaculados(List<Integer> detalles, Integer idCorte);
	
	public List <DTOAccesosSistema> obtenerAccesosSistema(Integer idSistema, Date fecha);
	
	public byte[] crearZip(Integer idProceso, List<Integer> detalles, Integer idCorte, 
			Date fecha) throws IOException;
}
