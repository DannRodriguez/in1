package mx.ine.procprimerinsa.dao;

import java.util.Date;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOUsuarioLogin;
import mx.ine.procprimerinsa.dto.db.DTOBitacoraAcceso;

public interface DAOBitacoraInterface {
	
	public List<DTOBitacoraAcceso> obtenerBitacoraAcceso(Date fechaInicial);

	public void regitroBitacoraAcceso(DTOBitacoraAcceso bitacora);
	
	public void regitroBitacoraCierre(DTOUsuarioLogin bitacora);

}
