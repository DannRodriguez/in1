package mx.ine.procprimerinsa.as;

import java.util.Date;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOUsuarioLogin;
import mx.ine.procprimerinsa.dto.db.DTOBitacoraAcceso;

public interface ASBitacoraInterface {
	
	public List<DTOBitacoraAcceso> obtenerBitacoraAcceso(Date fechaInicial);

	public void regitroBitacoraAcceso(DTOBitacoraAcceso bitacora);
	
	public void regitroBitacoraCierre(DTOUsuarioLogin usuario);
	
}
