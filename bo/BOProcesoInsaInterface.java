package mx.ine.procprimerinsa.bo;

import mx.ine.procprimerinsa.dto.db.DTOBitacoraProcesos;
import mx.ine.procprimerinsa.dto.db.DTOEstatusProcesos;

public interface BOProcesoInsaInterface {

	public DTOBitacoraProcesos generaBitacora(DTOEstatusProcesos estatusProceso);
	
	public int calculaNumeroInsacular(int numeroCiudadanos, int porcentajeInsacular, int minimoInsacular);
	
}
