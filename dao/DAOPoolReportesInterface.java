package mx.ine.procprimerinsa.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOAccesosSistema;
import mx.ine.procprimerinsa.dto.DTOPoolReportes;

public interface DAOPoolReportesInterface extends  DAOGenericInterface<DTOPoolReportes, Serializable> {

	public List<DTOPoolReportes> obtenerDatosInsaculados(List<Integer> detalles, Integer idCorte);
	
	public List <DTOAccesosSistema> obtenerAccesosSistema(Integer idSistema, Date fecha);

	public List<String> regresaDatos(Integer idProceso, List<Integer> detalles, Integer idCorte, 
			Date fecha, String consulta);
	
}
