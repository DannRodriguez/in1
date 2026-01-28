package mx.ine.procprimerinsa.batch.itemswriters;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

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
import mx.ine.procprimerinsa.pdf.ListadoPDF;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

public class IWDGListadoInsaculadosPDF implements ItemWriter<DTODatosInsaculados>, InitializingBean, DisposableBean {
		
	@Autowired
	@Qualifier("asArchivos")
	private ASArchivosInterface asArchivos;
	
	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	private String nombreProceso;
	private String nombreEstado;
	private Integer idGeograficoParticipacion;
	private String nombreParticipacion;
	private Integer idParticipacion;
	private String modoEjecucion;
	private Integer idTipoVoto;
	private String nombreArchivoListado;
	private String tituloArchivoListado;
	private Integer tipoIntegracion;
	private String leyendaCasilla;
	
	private FileOutputStream outputstream;
	private ListadoPDF listadoPDF;
	private StringBuilder ruta;
	private StringBuilder nombreArchivo;
	
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
				.append(".pdf");
		
		ruta.append(File.separator).append(nombreArchivo);
		
		outputstream = new FileOutputStream(Constantes.RUTA_LOCAL_FS + ruta);
		
		listadoPDF = new ListadoPDF(outputstream,
									nombreProceso, 
									nombreEstado, 
									String.format("%02d", idGeograficoParticipacion),
									nombreParticipacion,
									modoEjecucion,
									tituloArchivoListado,
									tipoIntegracion, 
									leyendaCasilla);
		
		listadoPDF.abrirDocumento();
		listadoPDF.creaEncabezado();
	}
	
	@Override
	public void destroy() throws Exception {
		
		listadoPDF.cerrarDocumento();
		outputstream.flush();
		outputstream.close();

		DTOArchivos archivo = new DTOArchivos();
		archivo.setIdProcesoElectoral(idProcesoElectoral);
		archivo.setIdDetalleProceso(idDetalleProceso);
		archivo.setIdParticipacion(idParticipacion);
		archivo.setIdTipoVoto(idTipoVoto);
		archivo.setArchivoSistema(Constantes.LISTADO_PDF);
		archivo.setRutaArchivo(ruta.toString());
		archivo.setNombreArchivo(nombreArchivo.toString());
		archivo.setCodigoIntegridad(Utilidades.obtenerPicadillo("SHA-256", Constantes.RUTA_LOCAL_FS + ruta));
		asArchivos.guardarArchivo(archivo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void write(@NonNull Chunk<? extends DTODatosInsaculados> items) throws Exception{
		listadoPDF.escribeDatos((List<DTODatosInsaculados>) items.getItems());
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

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
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

	public String getModoEjecucion() {
		return modoEjecucion;
	}

	public void setModoEjecucion(String modoEjecucion) {
		this.modoEjecucion = modoEjecucion;
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

	public String getTituloArchivoListado() {
		return tituloArchivoListado;
	}

	public void setTituloArchivoListado(String tituloArchivoListado) {
		this.tituloArchivoListado = tituloArchivoListado;
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
