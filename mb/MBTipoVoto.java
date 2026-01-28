package mx.ine.procprimerinsa.mb;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.jboss.logging.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.procprimerinsa.bsd.BSDMapasInterface;
import mx.ine.procprimerinsa.bsd.BSDTipoVotoInterface;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVotoParticipacion;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@RequestScoped
@Qualifier("mbTipoVoto")
public class MBTipoVoto implements Serializable {

	private static final long serialVersionUID = -8971885171572348752L;
	private static final Logger logger = Logger.getLogger(MBTipoVoto.class);
	private static final String MENSAJE_ALERT = "mensajeAlert";
	private static final String TIPO_VOTO = "tipoVoto";
	private static final String ARCHIVO_TIPO_VOTO = "cTipoVoto";
	private static final String ARCHIVO_TIPO_VOTO_PARTICIPACION = "cTipoVotoParticipacion";
	private static final String[] HEADER_TIPO_VOTO = new String[] {
															"ID_PROCESO_ELECTORAL",
															"ID_DETALLE_PROCESO",
															"ID_TIPO_VOTO",
															"DESCRIPCION",
															"SIGLAS",
															"PORCENTAJE_INSACULAR",
															"MINIMO_INSACULAR",
															"NOMBRE_ARCHIVO_LISTADO",
															"TITULO_ARCHIVO_LISTADO",
															"NOMBRE_ARCHIVO_CEDULA",
															"TITULO_ARCHIVO_CEDULA",
															"TIPO_INTEGRACION",
															"LEYENDA_CASILLA"};
	private static final String[] HEADER_TIPO_VOTO_PARTICIPACION = new String[] {
															"ID_PROCESO_ELECTORAL",
															"ID_DETALLE_PROCESO",
															"ID_PARTICIPACION",
															"ID_TIPO_VOTO"};
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdTipoVoto")
	private BSDTipoVotoInterface bsdTipoVoto;
	
	@Autowired
	@Qualifier("bsdMapas")
	private BSDMapasInterface bsdMapas;
	
	private List<Integer> detalles;
	private List<DTOCTipoVoto> lCTipoVoto;
	private DTOCTipoVoto tipoVoto;
	private Integer idTipoVoto;
	private Map<Integer, DTOParticipacionGeneral> participaciones;
	private DualListModel<String> participacionesPickList;
	private boolean procesoValido;
	private String mensaje;
	
	public void init() {
		try {
			
			String menuValido = mbAdmin.verificaElementosMenu(true, false, false);
			
			if (!menuValido.isEmpty()) {
				procesoValido = false;
				mensaje = menuValido;
				return;
			}
			
			detalles = bsdMapas.getDetallesProceso(
					mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
					mbAdmin.getAdminData().getTipoProceso());
			
			actualizarDatos();
			
			procesoValido = true;
		} catch(Exception e) {
			procesoValido = false;
			mensaje = Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo");
			logger.error("ERROR MBTipoVoto -init: ", e);
		}
	}
	
	private void actualizarDatos() {
		lCTipoVoto = bsdTipoVoto.obtieneCTiposVoto(
						mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso());

		participaciones = bsdMapas.getParticipaciones(
						mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
		 				mbAdmin.getAdminData().getProcesoSeleccionado().getCorte());
		
		tipoVoto = null;
		idTipoVoto = null;
	}
	
	public void seleccionaTipoVoto() {
		participacionesPickList = new DualListModel<>();
		Map<Integer, DTOParticipacionGeneral> participacionesSeleccionadas = new LinkedHashMap<>();
		
		if(idTipoVoto == -1) {
			tipoVoto = new DTOCTipoVoto();
			tipoVoto.setIdProcesoElectoral(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral());
			tipoVoto.setIdDetalleProceso(mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso());
			participacionesPickList.setTarget(new ArrayList<>());
		} else {
			Optional<DTOCTipoVoto> tipoVotoSeleccionado  = lCTipoVoto
														.stream()
														.filter(tv -> tv.getIdTipoVoto().equals(idTipoVoto))
														.findFirst();
			if(!tipoVotoSeleccionado.isPresent()) {
				FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
								"Error al seleccionar el tipo de voto"));
				return;
			}
			
			tipoVoto = tipoVotoSeleccionado.get();
			participacionesSeleccionadas.putAll(bsdTipoVoto.obtieneParticipacionesPorTipoVoto(
									tipoVoto,
									mbAdmin.getAdminData().getProcesoSeleccionado().getCorte()));
			
			participacionesPickList.setTarget(participacionesSeleccionadas.values()
								        .stream()
								        .sorted((p1, p2)->p1.getIdParticipacion().compareTo(p2.getIdParticipacion()))
								        .map(p -> p.getNombre())
								        .collect(Collectors.toList()));
		}
		
		participacionesPickList.setSource(participaciones.values()
										.stream()
										.filter(p -> !participacionesSeleccionadas.containsKey(p.getIdParticipacion()))
										.sorted((p1, p2)->p1.getIdParticipacion().compareTo(p2.getIdParticipacion()))
										.map(p -> p.getNombre())
										.collect(Collectors.toList()));
		
	}
	
	public String guardaTipoVoto() {
		if(!validaForm(false)) return "";
		
		String response = bsdTipoVoto.guardaTipoVoto(detalles, tipoVoto);
		
		if(response != null && !response.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al guardar el tipo de voto: " + response));
			return "";
		}
		
		return guardaParticipaciones();
	}
	
	public String actualizaTipoVoto() {
		if(!validaForm(false)) return "";
		
		String response = bsdTipoVoto.actualizaTipoVoto(detalles, tipoVoto);
		
		if(response != null && !response.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al actualizar el tipo de voto: " + response));
			return "";
		}
		
		return guardaParticipaciones();
	}
	
	private String guardaParticipaciones() {
		List<DTOCTipoVotoParticipacion> participacionesTarget = participacionesPickList
				.getTarget()
				.stream()
				.map(p -> new DTOCTipoVotoParticipacion(tipoVoto.getIdProcesoElectoral(),
												tipoVoto.getIdDetalleProceso(),
												Integer.parseInt(p.split("\\.")[0]),
												tipoVoto.getIdTipoVoto()))
				.toList();
		
		String response = bsdTipoVoto.eliminaTipoVotoParticipaciones(
													tipoVoto.getIdDetalleProceso(),
													tipoVoto.getIdTipoVoto());
		
		if(response != null && !response.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al elimininar las participaciones para el tipo de voto: " + response));
			return "";
		} 
		
		response = bsdTipoVoto.guardaTipoVotoParticipaciones(participacionesTarget);
		
		if(response == null || response.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
							"Éxito al guardar el tipo de voto"));
			return TIPO_VOTO;
		} else {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al guardar las participaciones para el tipo de voto: " + response));
			return "";
		}
		
	}
	
	public String eliminaTipoVoto() {
		if(!validaForm(true)) return "";
		
		String response = bsdTipoVoto.eliminaTipoVoto(detalles, tipoVoto);
		
		if(response == null || response.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
							"Éxito al eliminar el tipo de voto"));
			return TIPO_VOTO;
		} else {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al eliminar el tipo de voto: " + response));
			return "";
		}
	}
	
	private boolean validaForm(boolean isDelete) {
		if(tipoVoto == null) {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"El tipo de voto seleccionado no existe"));
			return false;
		}
		
		if((!isDelete) 
			&& (tipoVoto.getIdTipoVoto() == null
			|| tipoVoto.getDescripcion() == null || tipoVoto.getDescripcion().isBlank()
			|| tipoVoto.getSiglas() == null || tipoVoto.getSiglas().isBlank()
			|| tipoVoto.getPorcentajeInsacular() == null
			|| tipoVoto.getMinimoInsacular() == null
			|| tipoVoto.getNombreArchivoListado() == null || tipoVoto.getNombreArchivoListado().isBlank()
			|| tipoVoto.getTituloArchivoListado() == null || tipoVoto.getTituloArchivoListado().isBlank()
			|| tipoVoto.getNombreArchivoCedula() == null || tipoVoto.getNombreArchivoCedula().isBlank()
			|| tipoVoto.getTituloArchivoCedula() == null || tipoVoto.getTituloArchivoCedula().isBlank()
			|| !Constantes.TIPOS_DE_INTEGRACION.containsKey(tipoVoto.getTipoIntegracion()))
			|| tipoVoto.getLeyendaCasilla() == null || tipoVoto.getLeyendaCasilla().isBlank()) {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Los datos del tipo de voto están incompletos"));
			return false;
		}
		
		return true;
	}
	
	public void descargaCSVTipoVoto() {
		try {
			Utilidades.descargaArchivoCSV(HEADER_TIPO_VOTO, 
										lCTipoVoto, 
										ARCHIVO_TIPO_VOTO);
		} catch (Exception e) {
			logger.error("ERROR MBTipoVoto -descargaCSVTipoVoto: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
								"Error al descargar el archivo de tipos de voto"));
		}
	}
	
	public void descargaCSVTipoVotoParticipacion() {
		try {
			Utilidades.descargaArchivoCSV(HEADER_TIPO_VOTO_PARTICIPACION, 
										bsdTipoVoto.obtieneCTiposVotoParticipacion(detalles), 
										ARCHIVO_TIPO_VOTO_PARTICIPACION);
		} catch (Exception e) {
			logger.error("ERROR MBTipoVoto -descargaCSVTipoVotoParticipacion: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
								"Error al descargar el archivo participaciones por tipo de voto"));
		}
	}
	
	public void cargaTipoVoto(FileUploadEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(event.getFile() == null) {
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"Debe seleccionar previamente un archivo CSV"));	
			return;
		}
				
		try(Reader reader = new InputStreamReader(event.getFile().getInputStream(), StandardCharsets.UTF_8);
			CSVParser parser = CSVParser.builder()
										.setFormat(CSVFormat.EXCEL.builder()
																.setHeader(HEADER_TIPO_VOTO)
																.setSkipHeaderRecord(true)
																.setDelimiter('|')
																.get())
										.setReader(reader)
										.get();) {
			
			List<DTOCTipoVoto> tipoVotoCSV =  parser.getRecords()
										.stream()
										.map(line -> {
											DTOCTipoVoto tv = new DTOCTipoVoto();
											tv.setIdProcesoElectoral(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral());
											tv.setIdDetalleProceso(Integer.parseInt(line.get(HEADER_TIPO_VOTO[1])));
											tv.setIdTipoVoto(Integer.parseInt(line.get(HEADER_TIPO_VOTO[2])));
											tv.setDescripcion(line.get(HEADER_TIPO_VOTO[3]));
											tv.setSiglas(line.get(HEADER_TIPO_VOTO[4]));
											tv.setPorcentajeInsacular(Integer.parseInt(line.get(HEADER_TIPO_VOTO[5])));
											tv.setMinimoInsacular(Integer.parseInt(line.get(HEADER_TIPO_VOTO[6])));
											tv.setNombreArchivoListado(line.get(HEADER_TIPO_VOTO[7]));
											tv.setTituloArchivoListado(line.get(HEADER_TIPO_VOTO[8]));
											tv.setNombreArchivoCedula(line.get(HEADER_TIPO_VOTO[9]));
											tv.setTituloArchivoCedula(line.get(HEADER_TIPO_VOTO[10]));
											tv.setTipoIntegracion(Integer.parseInt(line.get(HEADER_TIPO_VOTO[11])));
											tv.setLeyendaCasilla(line.get(HEADER_TIPO_VOTO[12]));
											return tv;
										}).filter(Objects::nonNull)
										.collect(Collectors.toList());
			
			Map<Integer, DTOCTipoVoto> mapTipoVoto = new HashMap<>();
			
			for(DTOCTipoVoto tv : tipoVotoCSV) {
				mapTipoVoto.put(tv.getIdTipoVoto(), tv);
			}
			
			Integer tipoVotoCargados = 0;
			for (Map.Entry<Integer, DTOCTipoVoto> tv : mapTipoVoto.entrySet()) {
				String response = bsdTipoVoto.guardaTipoVoto(detalles, tv.getValue());
				if(response == null || response.isEmpty()) {
					tipoVotoCargados++;
				} else {
					logger.error("Error al cargar el tipo de voto: " + tv.getValue() + ". " + response);
				}
			}
			
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
					"Se cargaron " + tipoVotoCargados + " tipos de voto de " + mapTipoVoto.size()));
		} catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
			logger.error("ERROR MBTipoVoto -cargaTipoVoto: ", e);
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"El archivo no contiene los datos requeridos con el formato correcto"));
		} catch(Exception e) {
			logger.error("ERROR MBTipoVoto -cargaTipoVoto: ", e);
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"Error al realizar la carga de tipos de voto : " + e.getLocalizedMessage()));
		}
		
		actualizarDatos();
	}
	
	public void cargaTipoVotoParticipacion(FileUploadEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(event.getFile() == null) {
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"Debe seleccionar previamente un archivo CSV"));	
			return;
		}
				
		try(Reader reader = new InputStreamReader(event.getFile().getInputStream(), StandardCharsets.UTF_8);
			CSVParser parser = CSVParser.builder()
										.setFormat(CSVFormat.EXCEL.builder()
																	.setHeader(HEADER_TIPO_VOTO_PARTICIPACION)
																	.setSkipHeaderRecord(true)
																	.setDelimiter('|')
																	.get())
										.setReader(reader)
										.get();) {
			
			List<DTOCTipoVotoParticipacion> tipoVotoParticipacionCSV =  parser.getRecords()
										.stream()
										.map(line -> {
											DTOCTipoVotoParticipacion tvp = new DTOCTipoVotoParticipacion();
											tvp.setIdProcesoElectoral(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral());
											tvp.setIdDetalleProceso(Integer.parseInt(line.get(HEADER_TIPO_VOTO_PARTICIPACION[1])));
											tvp.setIdParticipacion(Integer.parseInt(line.get(HEADER_TIPO_VOTO_PARTICIPACION[2])));
											tvp.setIdTipoVoto(Integer.parseInt(line.get(HEADER_TIPO_VOTO_PARTICIPACION[3])));
											return tvp;
										}).filter(Objects::nonNull)
										.collect(Collectors.toList());
			
			String response = bsdTipoVoto.guardaTipoVotoParticipaciones(tipoVotoParticipacionCSV);
			if(response == null || response.isEmpty()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
						"Éxito al cargar las participaciones por tipo de voto"));
			} else {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"Error al cargar las participaciones por tipo de voto: " + response));
			}
			
		} catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
			logger.error("ERROR MBTipoVoto -cargaTipoVotoParticipacion: ", e);
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"El archivo no contiene los datos requeridos con el formato correcto"));
		} catch(Exception e) {
			logger.error("ERROR MBTipoVoto -cargaTipoVotoParticipacion: ", e);
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"Error al realizar la carga de participaciones: " + e.getLocalizedMessage()));
		}
		
		actualizarDatos();
	}

	public void reiniciaCargaTipoVoto() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
		
			String response = bsdTipoVoto.reiniciaCargaTipoVoto(detalles);
			if(response == null || response.isBlank()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
						"Éxito al realizar el reinicio del catálogo de tipo de voto"));
			} else {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"Error al realizar el reinicio del catálogo de tipo de voto: " + response));
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBTipoVoto -reiniciaCargaTipoVoto: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al realizar el reinicio del catálogo de tipo de voto: " + e.getLocalizedMessage()));
		}
		
		actualizarDatos();
		
	}
	
	public void reiniciaCargaTipoVotoParticipacion() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
		
			String response = bsdTipoVoto.reiniciaCargaTipoVotoParticipacion(detalles);
			if(response == null || response.isBlank()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
						"Éxito al realizar el reinicio del catálogo de participaciones por tipo de voto"));
			} else {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"Error al realizar el reinicio del catálogo de participaciones por tipo de voto: " + response));
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBTipoVoto -reiniciaCargaTipoVotoParticipacion: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al realizar el reinicio del catálogo de participaciones por tipo de voto: " + e.getLocalizedMessage()));
		}
		
		actualizarDatos();
		
	}

	public List<DTOCTipoVoto> getlCTipoVoto() {
		return lCTipoVoto;
	}

	public DTOCTipoVoto getTipoVoto() {
		return tipoVoto;
	}

	public void setTipoVoto(DTOCTipoVoto tipoVoto) {
		this.tipoVoto = tipoVoto;
	}

	public Integer getIdTipoVoto() {
		return idTipoVoto;
	}

	public void setIdTipoVoto(Integer idTipoVoto) {
		this.idTipoVoto = idTipoVoto;
	}

	public DualListModel<String> getParticipacionesPickList() {
		return participacionesPickList;
	}

	public void setParticipacionesPickList(DualListModel<String> participacionesPickList) {
		this.participacionesPickList = participacionesPickList;
	}

	public boolean isProcesoValido() {
		return procesoValido;
	}

	public String getMensaje() {
		return mensaje;
	}
	
}