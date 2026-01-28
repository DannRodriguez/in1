package mx.ine.procprimerinsa.as.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASBitacoraInterface;
import mx.ine.procprimerinsa.dao.DAOBitacoraInterface;
import mx.ine.procprimerinsa.dto.DTOUsuarioLogin;
import mx.ine.procprimerinsa.dto.db.DTOBitacoraAcceso;

@Scope("prototype")
@Service("asBitacora")
public class ASBitacora implements ASBitacoraInterface {
	
	private static final Logger logger = Logger.getLogger(ASBitacora.class);

	@Autowired
	@Qualifier("daoBitacora")
	private DAOBitacoraInterface daoBitacora;
	
	@Override
	@Transactional(value="transactionManagerReportes", propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
	public List<DTOBitacoraAcceso> obtenerBitacoraAcceso(Date fechaInicial) {
		try {
			return daoBitacora.obtenerBitacoraAcceso(fechaInicial);
		} catch(Exception e) {
			logger.error("ERROR ASBitacoraInterface -obtenerBitacoraAcceso: ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void regitroBitacoraAcceso(DTOBitacoraAcceso bitacora) {
		daoBitacora.regitroBitacoraAcceso(bitacora);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void regitroBitacoraCierre(DTOUsuarioLogin usuario) {
		daoBitacora.regitroBitacoraCierre(usuario);
	}

}
