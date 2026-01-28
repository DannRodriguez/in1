package mx.ine.procprimerinsa.as.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASArchivosInterface;
import mx.ine.procprimerinsa.batch.launchers.JobLauncherGeneraArchivosInterface;
import mx.ine.procprimerinsa.dao.DAOArchivosInterface;
import mx.ine.procprimerinsa.dto.db.DTOArchivos;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@Service("asArchivos")
@Scope("prototype")
public class ASArchivos implements ASArchivosInterface {

	private static final long serialVersionUID = -8066627510842910362L;
	private static final Logger logger = Logger.getLogger(ASArchivos.class);

	@Autowired
	@Qualifier("daoArchivos")
	private transient DAOArchivosInterface daoArchivos;

	@Autowired
	@Qualifier("jobLauncherGeneraArchivos")
	private transient JobLauncherGeneraArchivosInterface jobLauncherGeneraArchivos;

	@Override
	@Transactional(readOnly = true, rollbackFor = { Exception.class })
	public Map<String, DTOArchivos> obtenerListadoArchivosProceso(DTOArchivos dtoArchivo) {
		List<DTOArchivos> archivos = daoArchivos.consultaArchivos(dtoArchivo);
		return Utilidades.collectionToStream(archivos)
						.collect(Collectors.toMap(e -> e.getIdTipoVoto() + "-" + e.getArchivoSistema(), 
												Function.identity()));
	}

	@Override
	public boolean ejecutaGeneracionArchivos(Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idEstado, Integer id, Integer idParticipacion, String nombreProceso, String nombreDetalleProceso, 
			String nombreEstado, String nombreParticipacion, String modoEjecucion, String letra) 
			throws Exception {
		return jobLauncherGeneraArchivos.ejecutaGeneracionArchivos(idProcesoElectoral,
																	idDetalleProceso, 
																	idEstado, 
																	id,
																	idParticipacion,
																	nombreProceso, 
																	nombreDetalleProceso,
																	nombreEstado, 
																	nombreParticipacion,
																	modoEjecucion, 
																	letra);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void guardarArchivo(DTOArchivos archivo) {
		archivo.setUsuario("CARGA.PROCESO");
		archivo.setFechaHora(new Date());
		daoArchivos.guardarArchivo(archivo);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public boolean eliminaRegistroArchivos(Integer idProcesoElectoral, Integer idDetalleProceso, 
		Integer idParticipacion) {

		try {
			DTOArchivos buscarArchivo = new DTOArchivos();
			buscarArchivo.setIdDetalleProceso(idDetalleProceso);
			buscarArchivo.setIdProcesoElectoral(idProcesoElectoral);
			buscarArchivo.setIdParticipacion(idParticipacion);
			List<DTOArchivos> archivos = daoArchivos.consultaArchivos(buscarArchivo);

			for (DTOArchivos archivo : archivos) {

				File archivoBorrar = new File(Constantes.RUTA_LOCAL_FS + archivo.getRutaArchivo());
				
				if (archivoBorrar.exists()) {
					if (archivoBorrar.delete()) {
						daoArchivos.eliminarArchivo(archivo);
					} else {
						logger.error("ERROR ASArchivos -elminaRegistroArchivos: " 
									+ Constantes.RUTA_LOCAL_FS + archivo.getRutaArchivo());
						return false;
					}
				} else {
					daoArchivos.eliminarArchivo(archivo);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error("ERROR ASArchivos -elminaRegistroArchivos: ", e);
		}
		return false;
	}
	
}
