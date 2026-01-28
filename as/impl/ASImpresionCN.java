package mx.ine.procprimerinsa.as.impl;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASImpresionCNInterface;
import mx.ine.procprimerinsa.dao.DAOCEtiquetasInterface;
import mx.ine.procprimerinsa.dao.DAOImpresionCNInterface;
import mx.ine.procprimerinsa.dto.DTOCombo;
import mx.ine.procprimerinsa.dto.DTOJuntaDistrital;
import mx.ine.procprimerinsa.dto.DTOParametrosMargenes;
import mx.ine.procprimerinsa.dto.DTOSeccionesLocalidades;
import mx.ine.procprimerinsa.dto.db.DTODatosInsaculados;
import mx.ine.procprimerinsa.helper.impl.HLPPDFImpresionCarta;
import mx.ine.procprimerinsa.util.Constantes;

@Scope("prototype")
@Service("asImpresionCN")
public class ASImpresionCN implements ASImpresionCNInterface {
	
	private static final long serialVersionUID = 9021920314070198043L;
	private static final Logger logger = Logger.getLogger(ASImpresionCN.class);
	
	@Autowired
	@Qualifier("daoImpresionCN")
	private transient DAOImpresionCNInterface daoImpresionCN;

	@Autowired
    @Qualifier("daoEtiquetas")
	private transient DAOCEtiquetasInterface daoEtiquetas;
	
	@Override
	@Transactional(value="transactionManagerReportes", propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public HLPPDFImpresionCarta imprimirCartas(Integer idProceso, Integer idDetalle, Integer idParticipacion, 
			Integer idTipoVoto, Integer idEstado, Integer idDistrito, String estado,
			Integer idAreaResponsabilidad, Integer ordenVisitaInicio, Integer ordenVisitaFin,
			Integer seccion, Integer idLocalidad, Integer manzana, String numeroCredencialElector,
			Integer folio, Integer ordenamiento, List<DTOSeccionesLocalidades> seccionesLocalidades, 
			boolean logo) {
		try {
			List<DTODatosInsaculados> ciudadanos = daoImpresionCN.obtenerCiudadanos(idProceso, idDetalle, idParticipacion, idTipoVoto,
															idAreaResponsabilidad, ordenVisitaInicio, ordenVisitaFin, 
															seccion, idLocalidad, manzana,
															numeroCredencialElector, folio, ordenamiento);
			
			if(!ciudadanos.isEmpty()) {
				File archivoLogo = logo ? obtenerArchivoLogo(idProceso, idDetalle, idEstado) : null;
				return new HLPPDFImpresionCarta(ciudadanos, 
										seccionesLocalidades, 
										estado, 
										obtenerParametrosDocu(idProceso, idDetalle, idEstado, idDistrito), 
										archivoLogo);
			}
		} catch(Exception e) {
			logger.error("ERROR ASImpresionCN -imprimirCartas: ", e);
		}
		
		return null;
	}

	@Override
	@Transactional(value="transactionManagerReportes", propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public HLPPDFImpresionCarta imprimeReversoCN(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito, File archivoFirma, 
			DTOJuntaDistrital direccion, String telefono, boolean logo) {
		try {
			File archivoLogo = logo ? obtenerArchivoLogo(idProceso, idDetalle, idEstado) : null;
			return new HLPPDFImpresionCarta(archivoFirma, 
									archivoLogo, 
									direccion,
									telefono, 
									obtenerParametrosDocu(idProceso, idDetalle, idEstado, idDistrito));
		} catch(Exception e) {
			logger.error("ERROR ASImpresionCN -imprimeReversoCN: ", e);
			return null;
		}
	}
	
	private File obtenerArchivoLogo(Integer idProceso, Integer idDetalle, Integer idEstado) {
		return new File(new StringBuilder().append(Constantes.RUTA_LOCAL_FS)
										.append(idProceso).append(File.separator)
										.append(idDetalle).append(File.separator)
										.append(daoEtiquetas.obtenerEtiqueta(0, 0, 0, 0, Constantes.RUTA_LOGOS_OPLES)
															.getDescripcionEtiqueta())
										.append(File.separator)
										.append(idEstado).append("_1.png").toString());
	}
	
	private DTOParametrosMargenes obtenerParametrosDocu(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito) {
		return daoImpresionCN.obtenerParametrosDocumento(idProceso, idDetalle, idEstado, idDistrito);
	}

	@Override
	@Transactional(value="transactionManagerReportes", propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public List<DTOCombo> obtenerARES(Integer idProceso, Integer idDetalle, Integer idParticipacion) {
		try {
			return daoImpresionCN.obtenerARES(idProceso, idDetalle, idParticipacion);
		} catch(Exception e) {
			logger.error("ERROR ASImpresionCN -obtenerARES: ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(value="transactionManagerReportes", propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public List<DTOSeccionesLocalidades> obtenerSecciones(Integer idEstado, Integer idCorte, Integer idDistritoFederal, Integer idDistritoLocal, 
			Integer idMunicipio){
		try {
			return daoImpresionCN.obtenerSecciones(idEstado, idCorte, idDistritoFederal, idDistritoLocal, idMunicipio);
		} catch(Exception e) {
			logger.error("ERROR ASImpresionCN -obtenerSecciones: ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(value="transactionManagerReportes", propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public List<Integer> obtenerManzanas(Integer idEstado, Integer idCorte, Integer seccion, Integer idLocalidad) {
		try {
			return daoImpresionCN.obtenerManzanas(idEstado, idCorte, seccion, idLocalidad);
		} catch(Exception e) {
			logger.error("ERROR ASImpresionCN -obtenerManzanas: ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(value="transactionManagerReportes", propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public List<Integer> obtenerOrdenVisita(Integer idProceso, Integer idDetalle, Integer idParticipacion, Integer idAreaResponsabilidad, 
			Integer seccion) {
		try {
			return daoImpresionCN.obtenerOrdenVisita(idProceso, idDetalle, idParticipacion, idAreaResponsabilidad, seccion);
		} catch(Exception e) {
			logger.error("ERROR ASImpresionCN -obtenerOrdenVisita: ", e);
			return Collections.emptyList();
		}
	}

}
