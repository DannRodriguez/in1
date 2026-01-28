package mx.ine.procprimerinsa.as.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASGenerarLlavesInterface;
import mx.ine.procprimerinsa.bo.BOGenerarLlavesInterface;
import mx.ine.procprimerinsa.dao.DAOLlavesProcesosInterface;
import mx.ine.procprimerinsa.dto.DTOEstadoGeneral;
import mx.ine.procprimerinsa.dto.DTOLlave;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.db.DTOLlavesProcesos;
import mx.ine.procprimerinsa.form.FormGenerarLlaves;
import mx.ine.procprimerinsa.pdf.LlavesAccesoPDF;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.ServletContextUtils;
import mx.ine.procprimerinsa.util.Utilidades;

@Service("asGenerarLlaves")
@Scope("prototype")
public class ASGenerarLlaves implements ASGenerarLlavesInterface {

	private static final long serialVersionUID = -2263114573281987896L;
	private static final Logger logger = Logger.getLogger(ASGenerarLlaves.class);
	private static final String FUNCION_HASH = "SHA-1";
	private static final String DIRECTORIO_INEXISTENTE = "Los archivos de las llaves que se reiniciarán no existían en la ruta indicada en base de datos: ";

	@Autowired
	@Qualifier("daoLlavesProcesos")
	private transient DAOLlavesProcesosInterface daoLlavesProcesos;
	
	@Autowired
	@Qualifier("boGenerarLlaves")
	private transient BOGenerarLlavesInterface boGenerarLlaves;
	
	@Override
	@Transactional(readOnly = true, rollbackFor = { Exception.class })
	public Map<Integer, String[]> obtenerLlavesProceso(Integer idDetalleProceso, Integer modoEjecucion) {
		return daoLlavesProcesos.obtenerLlavesProceso(idDetalleProceso, modoEjecucion);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void generarLlaves(String nombreProceso, Map<Integer, DTOEstadoGeneral> estados, FormGenerarLlaves form) throws Exception {
		DTOLlavesProcesos llaveVE;
		DTOLlavesProcesos llaveVCEyEC;
		String estado;
		String participacion;
		Integer idEstado;
			
		if (form.getIdEstado().equals(0)) {
			Iterator<Integer> estadosIt = estados.keySet().iterator();

			while (estadosIt.hasNext()) {
				Integer key = estadosIt.next();
				
				if(key == 0) continue;

				Map<Integer, DTOParticipacionGeneral> participaciones = estados.get(key).getParticipaciones();
				Iterator<Integer> participacionesIt = participaciones.keySet().iterator();

				estado = estados.get(key).getNombreEstado();
				idEstado = estados.get(key).getIdEstado();

				while (participacionesIt.hasNext()) {
					Integer keyP = participacionesIt.next();

					participacion = participaciones.get(keyP).getNombre();

					llaveVE = convierteLlave(form, 0, participaciones.get(keyP).getIdParticipacion(),
							estado, participacion, participaciones.get(keyP).getId());
					llaveVCEyEC = convierteLlave(form, 1, participaciones.get(keyP).getIdParticipacion(),
							estado, participacion, participaciones.get(keyP).getId());

					escribeLlavePDF(llaveVE, generaLlave(FUNCION_HASH, llaveVE), 
							nombreProceso, idEstado, estado, keyP, participacion);
					escribeLlavePDF(llaveVCEyEC, generaLlave(FUNCION_HASH, llaveVCEyEC), 
							nombreProceso, idEstado, estado, keyP, participacion);

					guardarActualizarLlave(llaveVE);
					guardarActualizarLlave(llaveVCEyEC);
				}
			}
		} else {
			
			if (form.getIdParticipacion().equals(0)) {
				Map<Integer, DTOParticipacionGeneral> participaciones = estados.get(form.getIdEstado()).getParticipaciones();
				Iterator<Integer> itP = participaciones.keySet().iterator();

				estado = estados.get(form.getIdEstado()).getNombreEstado();
				idEstado = form.getIdEstado();

				while (itP.hasNext()) {
					Integer keyP = itP.next();

					participacion = participaciones.get(keyP).getNombre();

					llaveVE = convierteLlave(form, 0, participaciones.get(keyP).getIdParticipacion(), 
							estado, participacion, participaciones.get(keyP).getId());
					llaveVCEyEC = convierteLlave(form, 1, participaciones.get(keyP).getIdParticipacion(),
							estado, participacion, participaciones.get(keyP).getId());

					escribeLlavePDF(llaveVE, generaLlave(FUNCION_HASH, llaveVE), nombreProceso, idEstado, estado, keyP,
							participacion);
					escribeLlavePDF(llaveVCEyEC, generaLlave(FUNCION_HASH, llaveVCEyEC), nombreProceso, idEstado, estado,
							keyP, participacion);

					guardarActualizarLlave(llaveVE);
					guardarActualizarLlave(llaveVCEyEC);
				}
			} else { 
				estado = estados.get(form.getIdEstado()).getNombreEstado();
				idEstado = form.getIdEstado();

				participacion = estados.get(form.getIdEstado()).getParticipaciones()
						.get(form.getIdParticipacion()).getNombre();
				Integer id = estados.get(form.getIdEstado()).getParticipaciones()
						.get(form.getIdParticipacion()).getId();
				Integer idParticipacion = estados.get(form.getIdEstado())
						.getParticipaciones().get(form.getIdParticipacion()).getIdParticipacion();

				llaveVE = convierteLlave(form, 0, idParticipacion, estado, participacion, id);
				llaveVCEyEC = convierteLlave(form, 1, idParticipacion, estado, participacion, id);

				escribeLlavePDF(llaveVE, generaLlave(FUNCION_HASH, llaveVE), nombreProceso, idEstado, estado, id,
						participacion);
				escribeLlavePDF(llaveVCEyEC, generaLlave(FUNCION_HASH, llaveVCEyEC), nombreProceso, idEstado, estado, id,
						participacion);

				guardarActualizarLlave(llaveVE);
				guardarActualizarLlave(llaveVCEyEC);
			}
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void reiniciarLlaves(String nombreProceso, Map<Integer, DTOEstadoGeneral> estados, FormGenerarLlaves form) throws Exception {
		List<DTOLlavesProcesos> llaves = null;
		String estado;
		String participacion;
		Integer idEstado;
			
		if (form.getIdEstado().equals(0)) {
			Iterator<Integer> it = estados.keySet().iterator();

			while (it.hasNext()) {
				Integer key = it.next();

				if(key == 0) continue;

				Map<Integer, DTOParticipacionGeneral> participaciones = estados.get(key).getParticipaciones();
				Iterator<Integer> itP = participaciones.keySet().iterator();

				estado = estados.get(key).getNombreEstado();
				idEstado = estados.get(key).getIdEstado();
				
				while (itP.hasNext()) {
					Integer keyP = itP.next();
					participacion = participaciones.get(keyP).getNombre();

					llaves = daoLlavesProcesos.obtenerParLlavesProceso(
									form.getIdDetalleProceso(), 
									participaciones.get(keyP).getIdParticipacion(),
									form.getModoEjecucion());

					if (!llaves.isEmpty()) {

						llaves.get(0).setSemilla(boGenerarLlaves.generaSemilla());
						llaves.get(1).setSemilla(boGenerarLlaves.generaSemilla());
						
						File parentFile = new File(Constantes.RUTA_LOCAL_FS + llaves.get(0).getRutaLlave()).getParentFile();
						if(!parentFile.exists()) {
							logger.info(DIRECTORIO_INEXISTENTE + parentFile.getCanonicalPath());
							parentFile.mkdirs();
						}

						escribeLlavePDF(llaves.get(0), generaLlave(FUNCION_HASH, llaves.get(0)), 
								nombreProceso, idEstado, estado, keyP, participacion);
						escribeLlavePDF(llaves.get(1), generaLlave(FUNCION_HASH, llaves.get(1)), 
								nombreProceso, idEstado, estado, keyP, participacion);

						guardarActualizarLlave(llaves.get(0));
						guardarActualizarLlave(llaves.get(1));
					}
				}
			}
		} else { 
			if (form.getIdParticipacion().equals(0)) {
				Map<Integer, DTOParticipacionGeneral> participaciones = estados.get(form.getIdEstado()).getParticipaciones();
				Iterator<Integer> itP = participaciones.keySet().iterator();
				
				estado = estados.get(form.getIdEstado()).getNombreEstado();
				idEstado = form.getIdEstado();

				while (itP.hasNext()) {
					Integer keyP = itP.next();
					participacion = participaciones.get(keyP).getNombre();

					llaves = daoLlavesProcesos.obtenerParLlavesProceso(
							form.getIdDetalleProceso(), 
							participaciones.get(keyP).getIdParticipacion(),
							form.getModoEjecucion());

					if (!llaves.isEmpty()) {

						llaves.get(0).setSemilla(boGenerarLlaves.generaSemilla());
						llaves.get(1).setSemilla(boGenerarLlaves.generaSemilla());
						
						File parentFile = new File(Constantes.RUTA_LOCAL_FS +  llaves.get(0).getRutaLlave()).getParentFile();
						if(!parentFile.exists()) {
							logger.info(DIRECTORIO_INEXISTENTE + parentFile.getCanonicalPath());
							parentFile.mkdirs();
						}

						escribeLlavePDF(llaves.get(0), generaLlave(FUNCION_HASH, llaves.get(0)), 
								nombreProceso, idEstado, estado, keyP, participacion);
						escribeLlavePDF(llaves.get(1), generaLlave(FUNCION_HASH, llaves.get(1)), 
								nombreProceso, idEstado, estado, keyP, participacion);

						guardarActualizarLlave(llaves.get(0));
						guardarActualizarLlave(llaves.get(1));
					}
				}
			} else {
				estado = estados.get(form.getIdEstado()).getNombreEstado();
				idEstado = form.getIdEstado();

				participacion = estados.get(form.getIdEstado()).getParticipaciones()
									.get(form.getIdParticipacion()).getNombre();

				llaves = daoLlavesProcesos.obtenerParLlavesProceso(
							form.getIdDetalleProceso(), 
							estados.get(form.getIdEstado()).getParticipaciones().get(form.getIdParticipacion()).getIdParticipacion(),
							form.getModoEjecucion());

				if (!llaves.isEmpty()) {

					llaves.get(0).setSemilla(boGenerarLlaves.generaSemilla());
					llaves.get(1).setSemilla(boGenerarLlaves.generaSemilla());
					
					File parentFile = new File(Constantes.RUTA_LOCAL_FS +  llaves.get(0).getRutaLlave()).getParentFile();
					if(!parentFile.exists()) {
						logger.info(DIRECTORIO_INEXISTENTE + parentFile.getCanonicalPath());
						parentFile.mkdirs();
					}

					escribeLlavePDF(llaves.get(0), generaLlave(FUNCION_HASH, llaves.get(0)), 
							nombreProceso, idEstado, estado, 
							form.getIdParticipacion(), participacion);
					escribeLlavePDF(llaves.get(1), generaLlave(FUNCION_HASH, llaves.get(1)), 
							nombreProceso, idEstado, estado,
							form.getIdParticipacion(), participacion);

					guardarActualizarLlave(llaves.get(0));
					guardarActualizarLlave(llaves.get(1));
				}
			}
		}
	}
	
	@Override
	public String generaLlave(String funcionPicadillo, DTOLlavesProcesos llave) throws Exception {
		StringBuilder cadena = new StringBuilder()
			.append(llave.getIdProcesoElectoral())
			.append(llave.getIdDetalleProceso())
			.append(llave.getIdParticipacion())
			.append(llave.getTipoLlave() == 0 ? Constantes.TITULO_ARCHIVO_LLAVES_VE : Constantes.TITULO_ARCHIVO_LLAVES_VCEYEC)
			.append(llave.getModoEjecucion() == 0 ? "S" : "J")
			.append(llave.getSemilla());
		
		MessageDigest picadillo = MessageDigest.getInstance(funcionPicadillo);
		picadillo.update(cadena.toString().getBytes(StandardCharsets.UTF_8));
		byte[] b = picadillo.digest();

		StringBuilder llaveString = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			llaveString.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
		}

		return llaveString.toString();
	}

	@Override
	public ByteArrayOutputStream descargarLlaves(DTOEstadoGeneral estado, DTOParticipacionGeneral distrito, 
			FormGenerarLlaves form) throws Exception {
		
		StringBuilder directorio = new StringBuilder()
				.append(Constantes.RUTA_LOCAL_FS)
				.append(form.getIdProcesoElectoral()).append(File.separator)
				.append(form.getIdDetalleProceso()).append(File.separator)
				.append(Constantes.CARPETA_PROC_GLUSTER_INSA1).append(File.separator)
				.append(Constantes.CARPETA_LLAVES)
				.append(form.getModoEjecucion().equals(0) ? Constantes.TITULO_ARCHIVO_LLAVES_SIMULACRO : "");
		
		if(!form.getIdEstado().equals(0)) {
			
			if(form.getIdParticipacion().equals(0)) {
				directorio.append(File.separator);
				directorio.append(estado.getNombreEstado());
			} else {
				String participacion = distrito.getNombre();
				
				directorio.append(File.separator);
				directorio.append(estado.getNombreEstado());
				directorio.append(File.separator);
				directorio.append(String.format("%02d", form.getIdParticipacion()) + "_" + Utilidades.cleanStringForFileName(participacion));
				
				if(!form.getRuta().contains("nodisponible")) directorio = new StringBuilder(form.getRuta());
				
			}
			
		} 

		return Utilidades.creaArchivoZIP(directorio.toString());
	}
	
	private DTOLlavesProcesos convierteLlave(FormGenerarLlaves form, Integer vocal, Integer idParticipacion,
			String estado, String participacion, Integer id) {
		DTOLlavesProcesos llave = new DTOLlavesProcesos();
		llave.setIdProcesoElectoral(form.getIdProcesoElectoral());
		llave.setIdDetalleProceso(form.getIdDetalleProceso());
		llave.setIdParticipacion(idParticipacion);
		llave.setModoEjecucion(form.getModoEjecucion());
		llave.setTipoLlave(vocal);

		String rutaDirectorio = boGenerarLlaves.generaRuta(llave.getIdProcesoElectoral(), 
															llave.getIdDetalleProceso(),
															estado, 
															participacion, 
															id, 
															llave.getModoEjecucion());

		File directorio = new File(Constantes.RUTA_LOCAL_FS + rutaDirectorio);
		directorio.mkdirs();

		StringBuilder rutaLLave = new StringBuilder()
					.append(rutaDirectorio).append(File.separator)
					.append(Constantes.TITULO_ARCHIVO_LLAVE)
					.append(vocal == 0 ? Constantes.TITULO_ARCHIVO_LLAVES_VE : Constantes.TITULO_ARCHIVO_LLAVES_VCEYEC)
					.append("_")
					.append(estado).append("_")
					.append(Utilidades.cleanStringForFileName(participacion)).append(".pdf");
		
		llave.setRutaLlave(rutaLLave.toString());
		llave.setSemilla(boGenerarLlaves.generaSemilla());

		return llave;
	}

	private void escribeLlavePDF(DTOLlavesProcesos llave, String picadillo, String detalle, Integer idEstado,
			String estado, Integer idDistrito, String distrito) throws Exception {
		String rutaImagenes = ServletContextUtils.getRealResourcesPath("img") + File.separator;
		
		DTOLlave llaveContenido = new DTOLlave();
		llaveContenido.setNombreArchivo(Constantes.RUTA_LOCAL_FS + llave.getRutaLlave());
		llaveContenido.setTituloArchivo("LLAVE DE ACCESO");
		llaveContenido.setRutaLogo(rutaImagenes + "logoINE.png");
		llaveContenido.setTituloProceso(detalle);
		llaveContenido.setLeyendaSimulacro(llave.getModoEjecucion().equals(0) ? "SIMULACRO" : "");
		llaveContenido.setVocal(llave.getTipoLlave().equals(0) ? "Vocal Ejecutivo"
				: "Vocal de Capacitación Electoral y Educación Cívica");
		llaveContenido.setIdEstado(idEstado);
		llaveContenido.setEstado(estado);
		llaveContenido.setIdDistrito(idDistrito);
		llaveContenido.setDistrito(distrito);
		llaveContenido.setLlave(picadillo.substring(0, 10));
		llaveContenido.setRutaTrama(rutaImagenes + "trama.gif");
		
		LlavesAccesoPDF llavePDF = new LlavesAccesoPDF(llaveContenido);
		llavePDF.abrirDocumento();
		llavePDF.escribirLlave(llaveContenido);
		llavePDF.cerrarDocumento();
	}
	
	private void guardarActualizarLlave(DTOLlavesProcesos llave) {
		daoLlavesProcesos.guardarActualizarLlave(llave);
	}
	
}
