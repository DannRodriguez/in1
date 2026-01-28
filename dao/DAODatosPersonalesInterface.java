package mx.ine.procprimerinsa.dao;

import java.io.Serializable;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOArchivoDatosPersonales;
import mx.ine.procprimerinsa.dto.db.DTOEstatusDatosPersonales;

public interface DAODatosPersonalesInterface extends DAOGenericInterface<DTOEstatusDatosPersonales, Serializable> {
	
	public DTOEstatusDatosPersonales obtenerEstatusDatosPersonales(Integer idProceso, 
			Integer idDetalle, Integer idEstado);
	
	public List<DTOArchivoDatosPersonales> obtenerListaEstatusDatosPersonales(Integer idProceso, 
			Integer idDetalle, Integer idCorte);
	
	public void actualizarEstatusDatosPersonales(DTOEstatusDatosPersonales estatusDatosPersonales);
	
}
