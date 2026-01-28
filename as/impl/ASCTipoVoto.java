package mx.ine.procprimerinsa.as.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASCTipoVotoInterface;
import mx.ine.procprimerinsa.dao.DAOCTipoVotoInterface;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVotoParticipacion;

@Scope("prototype")
@Service("asCTipoVoto")
public class ASCTipoVoto implements ASCTipoVotoInterface {

	private static final long serialVersionUID = 2203849260483472182L;
	private static final Logger logger = Logger.getLogger(ASCTipoVoto.class);
	
	@Autowired
	@Qualifier("daoCTipoVoto")
	private transient DAOCTipoVotoInterface daoCTipoVoto;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public List<DTOCTipoVoto> obtieneCTiposVoto(Integer idDetalle) {
		try {
			return daoCTipoVoto.obtieneCTiposVoto(idDetalle);
		} catch(Exception e) {
			logger.error("ERROR ASCTipoVoto -obtieneCTiposVoto: ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public List<DTOCTipoVotoParticipacion> obtieneCTiposVotoParticipacion(List<Integer> detalles) {
		try {
			return daoCTipoVoto.obtieneCTiposVotoParticipacion(detalles);
		} catch(Exception e) {
			logger.error("ERROR ASCTipoVoto -obtieneCTiposVotoParticipacion: ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public Map<Integer, DTOCTipoVoto> obtieneTiposVotoPorParticipacion(Integer idDetalle, Integer idParticipacion) {
		try {
			return daoCTipoVoto.obtieneTiposVotoPorParticipacion(idDetalle, idParticipacion);
		} catch(Exception e) {
			logger.error("ERROR ASCTipoVoto -obtieneTiposVotoPorParticipacion: ", e);
			return Collections.emptyMap();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public Map<Integer, DTOParticipacionGeneral> obtieneParticipacionesPorTipoVoto(DTOCTipoVoto tipoVoto,
			Integer idCorte) {
		try {
			return daoCTipoVoto.obtieneParticipacionesPorTipoVoto(tipoVoto, idCorte);
		} catch(Exception e) {
			logger.error("ERROR ASCTipoVoto -obtieneParticipacionesPorTipoVoto: ", e);
			return Collections.emptyMap();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public String guardaTipoVoto(List<Integer> detalles, DTOCTipoVoto tipoVoto) {
		try {
			
			if(daoCTipoVoto.existeIdCTipoVoto(tipoVoto)) {
				return "El id seleccionado para el tipo de voto ya existe";
			}
			
			for(Integer detalle : detalles) {
				DTOCTipoVoto tv = new DTOCTipoVoto(tipoVoto);
				tv.setIdDetalleProceso(detalle);
				daoCTipoVoto.guardaCTipoVoto(tv);
			}
			
			return "";
		} catch(Exception e) {
			logger.error("ERROR ASCTipoVoto -guardaTipoVoto: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public String actualizaTipoVoto(List<Integer> detalles, DTOCTipoVoto tipoVoto) {
		try {
			
			if(!daoCTipoVoto.existeIdCTipoVoto(tipoVoto)) {
				return "El tipo de voto seleccionado no existe";
			}
			
			for(Integer detalle : detalles) {
				DTOCTipoVoto tv = new DTOCTipoVoto(tipoVoto);
				tv.setIdDetalleProceso(detalle);
				daoCTipoVoto.guardaCTipoVoto(tv);
			}
						
			return "";
		} catch(Exception e) {
			logger.error("ERROR ASCTipoVoto -actualizaTipoVoto: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public String eliminaTipoVoto(List<Integer> detalles, DTOCTipoVoto tipoVoto) {
		try {
			
			if(!daoCTipoVoto.existeIdCTipoVoto(tipoVoto)) {
				return "El tipo de voto seleccionado no existe";
			}
			
			for(Integer detalle : detalles) {
				DTOCTipoVoto tv = new DTOCTipoVoto(tipoVoto);
				tv.setIdDetalleProceso(detalle);
				daoCTipoVoto.eliminaParticipacionesTipoVoto(tv.getIdDetalleProceso(), 
														tv.getIdTipoVoto());
				daoCTipoVoto.eliminaCTipoVoto(tv);
			}
			
			return "";
		} catch(Exception e) {
			logger.error("ERROR ASCTipoVoto -eliminaTipoVoto: ", e);
			return e.getLocalizedMessage();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public String guardaTipoVotoParticipaciones(List<DTOCTipoVotoParticipacion> participaciones) {
		try {
			
			for(DTOCTipoVotoParticipacion participacion : participaciones) {
				daoCTipoVoto.guardaTipoVotoParticipacion(participacion);
			}
			
			return "";
		} catch(Exception e) {
			logger.error("ERROR ASCTipoVoto -guardaTipoVotoParticipaciones: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public String eliminaTipoVotoParticipaciones(Integer idDetalle, Integer idTipoVoto) {
		try {
			
			daoCTipoVoto.eliminaParticipacionesTipoVoto(idDetalle, idTipoVoto);
			
			return "";
		} catch(Exception e) {
			logger.error("ERROR ASCTipoVoto -eliminaTipoVotoParticipaciones: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public String reiniciaCargaTipoVoto(List<Integer> detalles) {
		try {
			daoCTipoVoto.reiniciaCargaTipoVoto(detalles);
			return "";
		} catch(Exception e) {
			logger.error("ERROR ASCTipoVoto -reiniciaCargaTipoVoto: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public String reiniciaCargaTipoVotoParticipacion(List<Integer> detalles) {
		try {
			daoCTipoVoto.reiniciaCargaTipoVotoParticipacion(detalles);
			return "";
		} catch(Exception e) {
			logger.error("ERROR ASCTipoVoto -reiniciaCargaTipoVotoParticipacion: ", e);
			return e.getLocalizedMessage();
		}
	}
	
}
