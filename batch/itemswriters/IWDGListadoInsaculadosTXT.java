package mx.ine.procprimerinsa.batch.itemswriters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;

import mx.ine.procprimerinsa.as.ASArchivosInterface;
import mx.ine.procprimerinsa.dto.db.DTOArchivos;
import mx.ine.procprimerinsa.dto.db.DTODatosInsaculados;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

public class IWDGListadoInsaculadosTXT implements ItemWriter<DTODatosInsaculados>, InitializingBean, DisposableBean {

	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	private Integer idEstado;
	private String nombreEstado;
	private Integer idGeograficoParticipacion;
	private String nombreParticipacion;
	private Integer idParticipacion;
	private Integer idTipoVoto;
	private String nombreArchivoListado;
	private Integer tipoIntegracion;
	private String leyendaCasilla;
	
	private StringBuilder ruta;
	private StringBuilder nombreArchivo;
	private StringBuilder contextoGeograficoInsaculado;
	private BufferedWriter writer;
	
	@Autowired
	@Qualifier("asArchivos")
	private ASArchivosInterface asArchivos;

	@Override
	public void afterPropertiesSet() throws Exception {
		
		ruta =  new StringBuilder()
				.append(idProcesoElectoral).append(File.separator) 
				.append(idDetalleProceso).append(File.separator) 
				.append(Constantes.CARPETA_PROC_GLUSTER_INSA1).append(File.separator) 
				.append(Constantes.CARPETA_LISTADOS).append(File.separator) 
				.append(nombreEstado).append(File.separator)
				.append(String.format("%02d", idGeograficoParticipacion))
				.append("_").append(Utilidades.cleanStringForFileName(nombreParticipacion));
		
		File directorio = new File(Constantes.RUTA_LOCAL_FS + ruta);
		directorio.mkdirs();
		
		nombreArchivo = new StringBuilder()
				.append(Constantes.TITULO_ARCHIVO_LISTADO)
				.append(nombreEstado).append("_") 
				.append(String.format("%02d", idGeograficoParticipacion)).append("_")
				.append(Utilidades.cleanStringForFileName(nombreParticipacion))
				.append(nombreArchivoListado.length() > 1 ? nombreArchivoListado : "")
				.append(".txt");

		ruta.append(File.separator).append(nombreArchivo);

		writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(Constantes.RUTA_LOCAL_FS + ruta), 
										StandardCharsets.UTF_8));

		StringBuilder nombreColumnas = new StringBuilder();
		nombreColumnas.append("ID_ESTADO|NOMBRE_ESTADO|ID_DISTRITO|CABECERA_DISTRITAL|SECCIÓN|");
		if(Constantes.INTEGRACION_CASILLA.equals(tipoIntegracion)) {
			nombreColumnas.append(leyendaCasilla.toUpperCase());
			nombreColumnas.append("|");
		}
		nombreColumnas.append("APELLIDO_PATERNO|APELLIDO_MATERNO|NOMBRE");
		
		writer.write(nombreColumnas.toString());
		
		contextoGeograficoInsaculado = new StringBuilder().append(idEstado).append("|")
												.append(nombreEstado).append("|")
												.append(idGeograficoParticipacion).append("|")
												.append(nombreParticipacion).append("|");
	}

	@Override
	public void destroy() throws Exception {

		writer.close();

		DTOArchivos archivo = new DTOArchivos();
		archivo.setIdProcesoElectoral(idProcesoElectoral);
		archivo.setIdDetalleProceso(idDetalleProceso);
		archivo.setIdParticipacion(idParticipacion);
		archivo.setIdTipoVoto(idTipoVoto);
		archivo.setArchivoSistema(Constantes.LISTADO_TXT);
		archivo.setRutaArchivo(ruta.toString());
		archivo.setNombreArchivo(nombreArchivo.toString());
		archivo.setCodigoIntegridad(Utilidades.obtenerPicadillo("SHA-256", Constantes.RUTA_LOCAL_FS + ruta));
		asArchivos.guardarArchivo(archivo);
	}
	
	@Override
	public void write(@NonNull Chunk<? extends DTODatosInsaculados> items) throws Exception {
		StringBuilder fila = null;
		boolean isIntegracionCasilla = Constantes.INTEGRACION_CASILLA.equals(tipoIntegracion);
		
		for (DTODatosInsaculados elemento : items.getItems()) {
			writer.newLine();
			fila = new StringBuilder().append(contextoGeograficoInsaculado);
			fila.append(elemento.getSeccion()).append("|");
			if(isIntegracionCasilla) fila.append(elemento.getCasilla()).append("|");
			fila.append(elemento.getApellidoPaterno()).append("|");
			fila.append(elemento.getApellidoMaterno()).append("|");
			fila.append(elemento.getNombre());/*.append("|");
			fila.append(elemento.getServidorPublico().equals(Constantes.PERSONA_SERVIDORA_PUBLICA)?
					Utilidades.mensajeProperties("etiqueta_generales_si"):
					Utilidades.mensajeProperties("etiqueta_generales_no"));*/
			writer.write(fila.toString());
		}
	}

	public Integer getIdProcesoElectoral() {
		return idProcesoElectoral;
	}

	public void setIdProcesoElectoral(Integer idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}

	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public Integer getIdGeograficoParticipacion() {
		return idGeograficoParticipacion;
	}

	public void setIdGeograficoParticipacion(Integer idGeograficoParticipacion) {
		this.idGeograficoParticipacion = idGeograficoParticipacion;
	}

	public String getNombreParticipacion() {
		return nombreParticipacion;
	}

	public void setNombreParticipacion(String nombreParticipacion) {
		this.nombreParticipacion = nombreParticipacion;
	}

	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}

	public Integer getIdTipoVoto() {
		return idTipoVoto;
	}

	public void setIdTipoVoto(Integer idTipoVoto) {
		this.idTipoVoto = idTipoVoto;
	}

	public String getNombreArchivoListado() {
		return nombreArchivoListado;
	}

	public void setNombreArchivoListado(String nombreArchivoListado) {
		this.nombreArchivoListado = nombreArchivoListado;
	}

	public Integer getTipoIntegracion() {
		return tipoIntegracion;
	}

	public void setTipoIntegracion(Integer tipoIntegracion) {
		this.tipoIntegracion = tipoIntegracion;
	}
	
	public String getLeyendaCasilla() {
		return leyendaCasilla;
	}

	public void setLeyendaCasilla(String leyendaCasilla) {
		this.leyendaCasilla = leyendaCasilla;
	}
	
}
