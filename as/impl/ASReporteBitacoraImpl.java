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

import mx.ine.procprimerinsa.as.ASReporteBitacoraInterface;
import mx.ine.procprimerinsa.dao.DAOReporteBitacoraInterface;
import mx.ine.procprimerinsa.dto.DTOReporteBitacora;

@Scope("prototype")
@Service("asReporteBitacora")
public class ASReporteBitacoraImpl implements ASReporteBitacoraInterface {

	private static final long serialVersionUID = 382804953379401050L;
	private static final Logger logger = Logger.getLogger(ASReporteBitacoraImpl.class);
	
	@Autowired
	@Qualifier("daoReporteBitacora")
	private transient DAOReporteBitacoraInterface daoReporteBitacora;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public List<DTOReporteBitacora> obtenerRegistrosBitacora(List<Integer> detalles, Date fechaInicio, Date fechaFin) {
		try {
			return daoReporteBitacora.obtenerRegistrosBitacora(detalles, fechaInicio, fechaFin);
		} catch(Exception e) {
			logger.error("ERROR ASReporteBitacoraImpl -obtenerRegistrosBitacora: ", e);
			return Collections.emptyList();
		}
		
	}

}
