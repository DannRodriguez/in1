package mx.ine.procprimerinsa.as.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.logging.Logger;
import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASAdministradorDatosPersonalesInterface;
import mx.ine.procprimerinsa.batch.launchers.JobLauncherDatosPersonalesInterface;
import mx.ine.procprimerinsa.bo.BOArchivosInterface;
import mx.ine.procprimerinsa.bo.BODatosPersonalesInterface;
import mx.ine.procprimerinsa.dao.DAODatosInsaculadosInterface;
import mx.ine.procprimerinsa.dao.DAODatosPersonalesInterface;
import mx.ine.procprimerinsa.dto.DTOArchivoDatosPersonales;
import mx.ine.procprimerinsa.dto.DTODocumento;
import mx.ine.procprimerinsa.dto.DTOEstadoGeneral;
import mx.ine.procprimerinsa.dto.db.DTOEstatusDatosPersonales;
import mx.ine.procprimerinsa.util.Constantes;

@Scope("prototype")
@Service("asAdministradorDatosPersonales")
public class ASAdministradorDatosPersonales implements ASAdministradorDatosPersonalesInterface {
	
	private static final long serialVersionUID = -2963686798910868455L;

	private static final Logger logger = Logger.getLogger(ASAdministradorDatosPersonales.class);
	
	@Autowired
	@Qualifier("boDatosPersonales")
	private BODatosPersonalesInterface boDatosPersonales;
	
	@Autowired
	@Qualifier("boArchivos")
	private transient BOArchivosInterface boArchivos;
	
	@Autowired
	@Qualifier("daoDatosInsaculados")
	private transient DAODatosInsaculadosInterface daoDatosInsaculados;
	
	@Autowired
	@Qualifier("daoDatosPersonalesImpl")
	private transient DAODatosPersonalesInterface daoDatosPersonales;
	
	@Autowired
	@Qualifier("jobLauncherDatosPersonales")
	private transient JobLauncherDatosPersonalesInterface jobLauncherDatosPersonales;

	@Override
	public String generaDirectorioPrincipal(Integer idProcesoElectoral, Integer idDetalleProceso) {
		return boDatosPersonales.generaDirectorioPrincipal(idProcesoElectoral, idDetalleProceso);
	}
	
	@Override
	public String generaNombreArchivo(Integer idEstado) {
		return boDatosPersonales.generaNombreArchivo(idEstado);
	}
	
	@Override
	public String generaDirectorioDatosDerfe(Integer idProcesoElectoral, Integer idDetalleProceso) {
		return boDatosPersonales.generaDirectorioDatosDerfe(idProcesoElectoral, idDetalleProceso);
	}
	
	@Override
	public String generarNombreArchivoCarga(Integer idEstado) {
		return boDatosPersonales.generarNombreArchivoCarga(idEstado);
	}
	
	@Override
	public boolean compruebaExistenciaArchivo(String rutaCompleta) {
		return boArchivos.compruebaExistenciaArchivo(rutaCompleta);
	}
	
	@Override
	public TreeNode<DTODocumento> generarArbol(String directorio) {
		return boArchivos.generarArbol(directorio);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })	
	public List<DTOArchivoDatosPersonales> obtenerListaEstatusDatosPersonales(Integer idProceso, 
			Integer idDetalle, Integer idCorte) {
		try {
			return daoDatosPersonales.obtenerListaEstatusDatosPersonales(idProceso, idDetalle, idCorte);
		} catch(Exception e) {
			logger.error("ERROR ASAdministradorDatosPersonales -obtenerListaEstatusDatosPersonales: ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public String generarArchivoDatosMinimosPorEstado(Integer idProceso, Integer idDetalle, Integer idEstado,
			String usuario) {
		try {
			return jobLauncherDatosPersonales.generarArchivoDatosMinimosPorEstado(
												idProceso, 
												idDetalle,
												idEstado,
												usuario) ? "" 
														: "Error al ejecutar el job de generación de datos mínimos";
		} catch(Exception e) {
			logger.error("ERROR ASAdministradorDatosPersonales -generarArchivoDatosMinimosPorEstado: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	public String validarArchivosDatosPersonalesPorEstado(Integer idProcesoElectoral, Integer idDetalleProceso,
			Integer idEstado, String rutaArchivo, String usuario, String patternToValidate, String encoding) {
		try {
			return jobLauncherDatosPersonales.validaArchivosDatosPersonalesPorEstado(idProcesoElectoral, 
													idDetalleProceso, 
													idEstado, 
													rutaArchivo, 
													usuario,
													patternToValidate,
													encoding) ? ""
															: "Error al ejecutar el job de validación del archivo de datos personales";
		} catch(Exception e) {
			logger.error("ERROR ASAdministradorDatosPersonales -validarArchivosDatosPersonalesPorEstado: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	public String actualizarDatosPersonalesPorEstado(Integer idProceso, String tipoEleccion,
			Integer idDetalle, Integer idEstado, String rutaArchivo, String encoding) {
		try {
		    return jobLauncherDatosPersonales.actualizarDatosPersonalesPorParticipacion(
												    		idProceso, 
												    		tipoEleccion,
															idDetalle,
												    		idEstado,
												    		rutaArchivo,
												    		encoding) ? ""
												    				: "Error al ejecutar el job de actualización de datos personales";
		} catch(Exception e) {
			logger.error("ERROR ASAdministradorDatosPersonales -actualizarDatosPersonalesPorParticipacion: ", e);
			return e.getLocalizedMessage();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public Integer contarCiudadanosInsaculadosPorEstado(Integer idDetalleProceso, Integer idEstado) {
		return daoDatosInsaculados.contarCiudadanosInsaculadosPorEstado(idDetalleProceso, idEstado);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })	
	public DTOEstatusDatosPersonales obtenerEstatusDatosPersonales(Integer idProceso, Integer idDetalle,
			Integer idEstado) {
		return daoDatosPersonales.obtenerEstatusDatosPersonales(idProceso, idDetalle, idEstado);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void actualizarEstatusDatosPersonales(DTOEstatusDatosPersonales estatusDatosPersonales) {
		daoDatosPersonales.actualizarEstatusDatosPersonales(estatusDatosPersonales);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void actualizarEstatusDatosPersonalesEliminar(Integer idProcesoElectoral, Integer idDetalleProceso,
			String nombreArchivo, String usuario) {
		try {
			Integer idEstado = boArchivos.obtenerIdEstadoNombreArchivo(nombreArchivo, ".txt");
			
			if(idEstado == null) return;
			
			DTOEstatusDatosPersonales estatusDatosPersonales = daoDatosPersonales.obtenerEstatusDatosPersonales(
																		idProcesoElectoral, 
																		idDetalleProceso, 
																		idEstado);
			
			estatusDatosPersonales.setIdAccion(Constantes.ESTATUS_DATOS_PERSONALES_ELIMINADO);
			estatusDatosPersonales.setEstatus(null);
			estatusDatosPersonales.setCiudadanosCarga(0);
			estatusDatosPersonales.setUsuario(usuario);
			estatusDatosPersonales.setFechaHora(new Date());
			
			daoDatosPersonales.actualizarEstatusDatosPersonales(estatusDatosPersonales);
		} catch(Exception e) {
			logger.error("ERROR ASAdministradorDatosPersonales -actualizarEstatusDatosPersonalesEliminar: ", e);
		}
	}

	@Override
	public void comprimirArchivos(String directorioEntrada, Set<String> filesToInclude, 
			String nombreComprimido) throws Exception {
		boArchivos.comprimirArchivos(directorioEntrada, filesToInclude, nombreComprimido);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public boolean subirArchivos(Integer idProceso, Integer idDetalle, Map<Integer, DTOEstadoGeneral> estados,
			String ruta, List<UploadedFile> listaArchivos, String usuario) {
		
		if(!boArchivos.validarNombresArchivos(estados, listaArchivos)) return false;
		
		try {
			
			if(!boArchivos.subirArchivos(ruta, listaArchivos)) {
				return false;
			}
			
			for(UploadedFile archivo : listaArchivos) {
				Integer idEstado = boArchivos.obtenerIdEstadoNombreArchivo(archivo.getFileName(), ".txt");
				DTOEstatusDatosPersonales estatusDatosPersonales = daoDatosPersonales.obtenerEstatusDatosPersonales(
																		idProceso, 
																		idDetalle, 
																		idEstado);
				
				estatusDatosPersonales.setIdAccion(Constantes.ESTATUS_DATOS_PERSONALES_SUBIDO);
				estatusDatosPersonales.setEstatus(null);
				estatusDatosPersonales.setCiudadanosCarga(0);
				estatusDatosPersonales.setUsuario(usuario);
				estatusDatosPersonales.setFechaHora(new Date());
				
				daoDatosPersonales.actualizarEstatusDatosPersonales(estatusDatosPersonales);
			}
			
			return true;
		} catch(Exception e) {
			logger.error("ERROR ASAdministradorDatosPersonales -subirArchivos: ", e);
			return false;
		}
	}

	@Override
	public boolean bajarArchivo(String ruta) {
		return boArchivos.bajarArchivo(ruta);
	}
	
}
