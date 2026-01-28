package mx.ine.procprimerinsa.bsd.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASBitacoraInterface;
import mx.ine.procprimerinsa.bsd.BSDBitacoraInterface;
import mx.ine.procprimerinsa.dto.DTOUsuarioLogin;
import mx.ine.procprimerinsa.dto.db.DTOBitacoraAcceso;

@Component("bsdBitacora")
@Scope("prototype")
public class BSDBitacora implements BSDBitacoraInterface {

	@Autowired
	@Qualifier("asBitacora")
	private ASBitacoraInterface asBitacora;
	
	@Override
	public List<DTOBitacoraAcceso> obtenerBitacoraAcceso(Date fechaInicial) {
		return asBitacora.obtenerBitacoraAcceso(fechaInicial);
	}

	@Override
	public void regitroBitacoraAcceso(DTOBitacoraAcceso bitacora) {
		asBitacora.regitroBitacoraAcceso(bitacora);
	}

	@Override
	public void regitroBitacoraCierre(DTOUsuarioLogin usuario) {
		asBitacora.regitroBitacoraCierre(usuario);
	}

}
