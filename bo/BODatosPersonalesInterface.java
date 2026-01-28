package mx.ine.procprimerinsa.bo;

import java.io.Serializable;

public interface BODatosPersonalesInterface extends Serializable {
	
	public String generaDirectorioPrincipal(Integer idProcesoElectoral, Integer idDetalleProceso);
	
	public String generaNombreArchivo(Integer idEstado);
	
	public String generaDirectorioDatosDerfe(Integer idProcesoElectoral, Integer idDetalleProceso);
	
	public String generarNombreArchivoCarga(Integer idEstado);
	
}
