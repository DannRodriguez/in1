package mx.ine.procprimerinsa.as.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASCEtiquetasInterface;
import mx.ine.procprimerinsa.dao.DAOCEtiquetasInterface;
import mx.ine.procprimerinsa.dto.db.DTOCEtiquetas;

@Scope("prototype")
@Service("asEtiquetas")
public class ASCEtiquetas implements ASCEtiquetasInterface {

	private static final long serialVersionUID = -7639951968811503595L;
	private static final Logger logger = Logger.getLogger(ASCEtiquetas.class);

	@Autowired
	@Qualifier("daoEtiquetas")
	private transient DAOCEtiquetasInterface daoEtiquetas;

	@Override
	@Transactional(readOnly = true, rollbackFor = { Exception.class })
	public DTOCEtiquetas obtenerEtiqueta(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito,
			Integer idEtiqueta) {
		try {
			return daoEtiquetas.obtenerEtiqueta(idProceso, idDetalle, idEstado, idDistrito, idEtiqueta);
		} catch(Exception e) {
			logger.error("ERROR ASCEtiquetas -obtenerEtiqueta: ", e);
			return null;
		}
	}

}
