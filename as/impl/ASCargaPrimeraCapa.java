package mx.ine.procprimerinsa.as.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASCargaPrimeraCapaInterface;
import mx.ine.procprimerinsa.batch.launchers.JobLauncherCargaPrimeraCapaInterface;
import mx.ine.procprimerinsa.dao.DAOCargaPrimeraCapaInterface;
import mx.ine.procprimerinsa.dto.DTOEstatusCargaPrimeraCapaDistrito;
import mx.ine.procprimerinsa.dto.db.DTOServidoresPublicos;
import mx.ine.procprimerinsa.form.FormCargaPrimeraCapa;
import mx.ine.procprimerinsa.pao.PAOAdminIndicesDIInterface;
import mx.ine.procprimerinsa.pao.PAOCargaPrimeraCapaInterface;
import mx.ine.procprimerinsa.pao.PAOGatherSchemaStatsInterface;
import mx.ine.procprimerinsa.pao.PAOMarcadoAREInterface;
import mx.ine.procprimerinsa.pao.PAOActualizaLNInterface;
import mx.ine.procprimerinsa.pao.PAOMarcadoServidorPublicoInterface;
import mx.ine.procprimerinsa.pao.PAOOrdenamientosInsaInterface;
import mx.ine.procprimerinsa.pao.PAOTriggersPrimeraCapaInterface;
import mx.ine.procprimerinsa.util.Constantes;

@Service("asCargaPrimeraCapa")
@Scope("prototype")
public class ASCargaPrimeraCapa implements ASCargaPrimeraCapaInterface {
	
	private static final long serialVersionUID = -4638794870392488549L;
	private static final Logger logger = Logger.getLogger(ASCargaPrimeraCapa.class);
	
	@Autowired
	@Qualifier("daoCargaPrimeraCapa")
	private transient DAOCargaPrimeraCapaInterface daoCargaPrimeraCapa;
	
	@Autowired
	@Qualifier("paoAdminIndicesDI")
	private transient PAOAdminIndicesDIInterface paoAdminIndicesDI;
	
	@Autowired
	@Qualifier("paoGatherSchemaStats")
	private transient PAOGatherSchemaStatsInterface paoGatherSchemaStats;
	
	@Autowired
	@Qualifier("paoMarcadoARE")
	private transient PAOMarcadoAREInterface paoMarcadoARE;
	
	@Autowired
	@Qualifier("paoOrdenamientos")
	private transient PAOOrdenamientosInsaInterface paoOrdenamientos;
	
	@Autowired
	@Qualifier("paoCargaPrimeraCapa")
	private transient PAOCargaPrimeraCapaInterface paoCargaPrimeraCapa;
	
	@Autowired
	@Qualifier("paoTriggersPrimeraCapa")
	private transient PAOTriggersPrimeraCapaInterface paoTriggersPrimeraCapa;
	
	@Autowired
	@Qualifier("paoActualizaLN")
	private transient PAOActualizaLNInterface paoActualizaLN;
	
	@Autowired
	@Qualifier("paoMarcadoServidorPublico")
	private transient PAOMarcadoServidorPublicoInterface paoMarcadoServidorPublico;
	
	@Autowired
	@Qualifier("jlCargaPrimeraCapa")
	private transient JobLauncherCargaPrimeraCapaInterface jlCargaPrimeraCapa;
	
	@Override
	@Transactional(value="transactionManagerReportes", readOnly = true)
	public List<DTOEstatusCargaPrimeraCapaDistrito> obtieneEstatusCargaPrimeraCapa(List<Integer> detalles, 
			Integer idCorte) {
		try {
			return daoCargaPrimeraCapa.obtieneEstatusCargaPrimeraCapa(detalles, idCorte);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -obtieneEstatusCargaPrimeraCapa: ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	@Transactional(value="transactionManagerReportes", readOnly = true)
	public List<DTOEstatusCargaPrimeraCapaDistrito> obtieneLogEstatusCargaPrimeraCapa(List<Integer> detalles, Integer idCorte) {
		try {
			return daoCargaPrimeraCapa.obtieneLogEstatusCargaPrimeraCapa(detalles, idCorte);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -obtieneLogEstatusCargaPrimeraCapa: ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public boolean reiniciarEstatusCarga(List<Integer> detalles, String usuario) {
		try {
			daoCargaPrimeraCapa.reiniciarEstatusCarga(detalles, usuario);
			return true;
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -reiniciarEstatusCarga: ", e);
			return false;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public boolean reiniciaCargaOrdenada(Integer idProceso, List<Integer> detalles) {
		try {
			daoCargaPrimeraCapa.reiniciaCargaOrdenada(idProceso, detalles);
			return true;
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -reiniciaCargaOrdenada: ", e);
			return false;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public List<String> obtieneEstatusIndices() {
		try {
			return daoCargaPrimeraCapa.obtieneEstatusIndices();
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -obtieneEstatusIndices: ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public String creaEliminaIndices(boolean isCrea) {
		try {
			return paoAdminIndicesDI.creaEliminaIndices(isCrea);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -creaEliminaIndices: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public String recolectarEstadisticas() {
		try {
			paoGatherSchemaStats.recolectaEstadisticas();
			return "";
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -recolectarEstadisticas: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	public boolean obtieneEstatusServidoresPublicos(FormCargaPrimeraCapa form, Integer idDetalle) {
		try {
			
			Object[] corte = daoCargaPrimeraCapa.obtieneCorteServidoresPublicos(idDetalle);
			form.setConteoServidoresPublicos(Integer.parseInt(corte[0].toString()));
			form.setIdCorteServidoresPublicos(corte[1] != null ? Integer.parseInt(corte[1].toString()) : null);
			
			return true;
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -obtieneEstatusServidoresPublicos: ", e);
			return false;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public boolean obtieneEstatusInsumos(FormCargaPrimeraCapa form, Integer idProceso) {
		try {
			
			Object[] corte = daoCargaPrimeraCapa.obtieneCorteAreasSecciones(form.getDetalles());
			form.setConteoAreasSecciones(Integer.parseInt(corte[0].toString()));
			form.setFechaActualizacionAreasSecciones(corte[1] != null ? (Date)corte[1] : null);
			
			corte = daoCargaPrimeraCapa.obtieneCorteSeccionesCompartidas(form.getDetalles());
			form.setConteoSeccionesCompartidas(Integer.parseInt(corte[0].toString()));
			form.setFechaActualizacionSeccionesCompartidas(corte[1] != null ? (Date)corte[1] : null);
			
			corte = daoCargaPrimeraCapa.obtieneCorteOrdenada(idProceso, form.getDetalles());
			form.setConteoOrdenada(Integer.parseInt(corte[0].toString()));
			form.setFechaActualizacionOrdenada(corte[1] != null ? (Date)corte[1] : null);
			
			return true;
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -obtieneEstatusInsumos: ", e);
			return false;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public String ejecutaCargaARES(Integer idDetalle, String usuario) {
		try {
			return paoMarcadoARE.ejecutaCargaARES(idDetalle, usuario);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -ejecutaCargaARES: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	public boolean spoolServidoresPublicos(Integer idDetalle, String rutaArchivo) {
		try {
			Files.deleteIfExists(Paths.get(rutaArchivo));
			return jlCargaPrimeraCapa.spoolServidoresPublicos(idDetalle, rutaArchivo);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -spoolServidoresPublicos: ", e);
		}
		return false;
	}
	
	@Override
	public boolean spoolAreasSecciones(Integer idProceso, String tipoEleccion, String rutaArchivo) {
		try {
			Files.deleteIfExists(Paths.get(rutaArchivo));
			return jlCargaPrimeraCapa.spoolAreasSecciones(idProceso, tipoEleccion, rutaArchivo);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -spoolAreasSecciones: ", e);
		}
		return false;
	}

	@Override
	public boolean spoolSeccionesCompartidas(Integer idProceso, String tipoEleccion, String rutaArchivo) {
		try {
			Files.deleteIfExists(Paths.get(rutaArchivo));
			return jlCargaPrimeraCapa.spoolSeccionesCompartidas(idProceso, tipoEleccion, rutaArchivo);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -spoolSeccionesCompartidas: ", e);
		}
		return false;
	}

	@Override
	public boolean ejecutaCargaOrdenada(Integer idProceso, String tipoEleccion, String rutaArchivo, String usuario) {
		try {	
			return jlCargaPrimeraCapa.ejecutaCargaOrdenada(idProceso, tipoEleccion, rutaArchivo, usuario);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -ejecutaCargaOrdenada: ", e);
		}
		return false;
	}

	@Override
	public boolean spoolOrdenada(Integer idProceso, String tipoEleccion, String rutaArchivo) {
		try {
			Files.deleteIfExists(Paths.get(rutaArchivo));
			return jlCargaPrimeraCapa.spoolOrdenada(idProceso, tipoEleccion, rutaArchivo);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -spoolOrdenada: ", e);
		}
		return false;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public DTOEstatusCargaPrimeraCapaDistrito obtieneEstatusCargaPrimeraCapaDistrito(Integer idDetalle,
			Integer idParticipacion, Integer idCorte) { 
		try {
			return daoCargaPrimeraCapa.obtieneEstatusCargaPrimeraCapaDistrito(idDetalle, idParticipacion, idCorte);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -obtieneEstatusCargaPrimeraCapaDistrito: ", e);
			return null;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public boolean actualizaEstatusCargaPrimeraCapaDistrito(DTOEstatusCargaPrimeraCapaDistrito distrito) {
		try {
			daoCargaPrimeraCapa.actualizaEstatusCargaPrimeraCapaDistrito(distrito);
			return true;
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -actualizaEstatusCargaPrimeraCapaDistrito: ", e);
		}
		return false;
	}
	
	@Override
	public String ejecutaMarcadoNombreOrden(DTOEstatusCargaPrimeraCapaDistrito distrito) {
		try {	
			return paoActualizaLN.ejecutaMarcadoNombreOrden(distrito.getIdProcesoElectoral(),
																distrito.getIdDetalleProceso(),
																distrito.getIdEstado(),
																distrito.getIdDistrito());
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -ejecutaMarcadoNombreOrden: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	public String ejecutaReinicioYaEsInsaculado(DTOEstatusCargaPrimeraCapaDistrito distrito) {
		try {	
			return paoActualizaLN.ejecutaReinicioYaEsInsaculado(distrito.getIdProcesoElectoral(),
																distrito.getIdDetalleProceso(),
																distrito.getIdEstado(),
																distrito.getIdDistrito());
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -ejecutaReinicioYaEsInsaculado: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public Queue<DTOServidoresPublicos> obtieneCargaServidoresPublicos(Integer idProceso, Integer idDetalle, 
			Integer maxResults, Long offset) {
		return daoCargaPrimeraCapa.obtieneCargaServidoresPublicos(idProceso, idDetalle, maxResults, offset);
	}
	
	@Override
	@Transactional(value="transactionManagerLN", propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public Queue<DTOServidoresPublicos> obtieneSpoolServidoresPublicos(Integer idDetalle, 
			Integer maxResults, Long offset) {
		return daoCargaPrimeraCapa.obtieneSpoolServidoresPublicos(idDetalle, maxResults, offset);
	}
	
	@Override
	@Transactional(value="transactionManagerLN", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public String reiniciaCargaServidoresPublicos(Integer idDetalle) {
		try {	
			daoCargaPrimeraCapa.reiniciaCargaServidoresPublicos(idDetalle);
			return "";
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -reiniciaCargaServidoresPublicos: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	public boolean ejecutaCargaServidoresPublicos(Integer idProceso, Integer idDetalle) {
		try {	
			return jlCargaPrimeraCapa.ejecutaCargaServidoresPublicos(idProceso, idDetalle);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -ejecutaCargaServidoresPublicos: ", e);
		}
		return false;
	}
	
	@Override
	public String ejecutaMarcadoServidorPublico(Integer idProceso, Integer idDetalle) {
		try {	
			return paoMarcadoServidorPublico.ejecutaMarcadoServidorPublico(idProceso, idDetalle);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -ejecutaMarcadoServidorPublico: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	public String ejecutaReinicioServidorPublico(Integer idProceso, Integer idDetalle) {
		try {	
			return paoMarcadoServidorPublico.ejecutaReinicioServidorPublico(idProceso, idDetalle);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -ejecutaReinicioServidorPublico: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public String ejecutaCarga(Integer tipoEjecucion, DTOEstatusCargaPrimeraCapaDistrito distrito, 
			String letra, String usuario) {
				
		try {
						
			if(Constantes.TIPO_EJECUCION_MARCADO_ARE == tipoEjecucion) {
				return paoMarcadoARE.ejecutaActualizacionARES(distrito.getIdProcesoElectoral(),
																distrito.getIdDetalleProceso(),
																distrito.getIdParticipacion(),
																usuario);
			} else if(Constantes.TIPO_EJECUCION_ORDENAMIENTOS == tipoEjecucion) {
				return paoOrdenamientos.ejecutaOrdenamientos(distrito.getIdProcesoElectoral(),
																distrito.getIdDetalleProceso(),
																distrito.getIdParticipacion(), 
																letra,
																usuario);
			} else if(Constantes.TIPO_EJECUCION_CARGA_PRIMERA_CAPA == tipoEjecucion) {
				StringBuilder partition = new StringBuilder("DATOS_INSA_D")
						.append(distrito.getIdDetalleProceso())
						.append("P").append(String.format("%03d", distrito.getIdParticipacion()));
				return paoCargaPrimeraCapa.ejecutaCargaPrimeraCapa(distrito.getIdProcesoElectoral(),
																	distrito.getIdDetalleProceso(),
																	distrito.getIdParticipacion(),
																	partition.toString(),
																	usuario);
			}
			
			return "No se realizó ninguna ejecución";
			
		} catch(Exception e) {
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public List<String> obtieneEstatusTriggers() {
		try {
			return daoCargaPrimeraCapa.obtieneEstatusTriggers();
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -obtieneEstatusTriggers: ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public String habilitaDeshabilitaTriggers(boolean isHabilita) {
		try {
			return paoTriggersPrimeraCapa.habilitaDeshabilitaTriggers(isHabilita);
		} catch(Exception e) {
			logger.error("ERROR ASCargaPrimeraCapa -habilitaDeshabilitaTriggers: ", e);
			return e.getLocalizedMessage();
		}
	}
	
}
