package mx.ine.procprimerinsa.as.impl;

import java.util.Collections;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASBitacoraProcesosInterface;
import mx.ine.procprimerinsa.dao.DAOBitacoraProcesosInterface;
import mx.ine.procprimerinsa.dto.db.DTOBitacoraProcesos;

@Scope("prototype")
@Service("asBitacoraProcesos")
public class ASBitacoraProcesos implements ASBitacoraProcesosInterface {

	private static final long serialVersionUID = 6614187733786567339L;
	private static final Logger logger = Logger.getLogger(ASBitacoraProcesos.class);
	
	@Autowired
	@Qualifier("daoBitacoraProcesos")
	private transient DAOBitacoraProcesosInterface daoBitacoraProcesos;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
	public List<DTOBitacoraProcesos> obtenerBitacoraProcesosRegistros(List<Integer> detalles) {
		try {
			return daoBitacoraProcesos.consultaBitacora(detalles);
		} catch (Exception e) {
			logger.error("ERROR ASBitacoraProcesos -obtenerBitacoraProcesosRegistros: ", e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public boolean setJobExecutionIdDefaultPorParticipacion(Integer idDetalleProceso, Integer idParticipacionGeografica) {
		try {
			daoBitacoraProcesos.setBitacoraJobExecutionIdDefaultPorParticipacion(idDetalleProceso, idParticipacionGeografica);
			return true;
		} catch (Exception e) {
			logger.error("ERROR ASBitacoraProcesos -setJobExecutionIdDefaultPorParticipacion: ", e);
		}
		return false;
	}

}
