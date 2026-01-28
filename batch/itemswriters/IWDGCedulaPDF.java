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
import mx.ine.procprimerinsa.dto.db.DTOResultados1aInsa;
import mx.ine.procprimerinsa.pdf.CedulaInsaculadosPDF;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

public class IWDGCedulaPDF implements ItemWriter<DTOResultados1aInsa>, InitializingBean, DisposableBean {

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
	private String nombreArchivoCedula;
	private String tituloArchivoCedula;
	private String leyendaCasilla;
	
	private FileOutputStream outputstream;
	private CedulaInsaculadosPDF cedulaPDF;
	private StringBuilder ruta;
	private StringBuilder nombreArchivo;

	@Override
	public void afterPropertiesSet() throws Exception {

		ruta =  new StringBuilder()
				.append(idProcesoElectoral).append(File.separator) 
				.append(idDetalleProceso).append(File.separator) 
				.append(Constantes.CARPETA_PROC_GLUSTER_INSA1).append(File.separator) 
				.append(Constantes.CARPETA_CEDULAS).append(File.separator) 
				.append(nombreEstado).append(File.separator)
				.append(String.format("%02d", idGeograficoParticipacion))
				.append("_").append(Utilidades.cleanStringForFileName(nombreParticipacion));

		File directorio = new File(Constantes.RUTA_LOCAL_FS + ruta);
		directorio.mkdirs();

		nombreArchivo = new StringBuilder()
				.append(Constantes.TITULO_ARCHIVO_CEDULA)
				.append(nombreEstado).append("_") 
				.append(String.format("%02d", idGeograficoParticipacion)).append("_")
				.append(Utilidades.cleanStringForFileName(nombreParticipacion))
				.append(nombreArchivoCedula.length() > 1 ? nombreArchivoCedula : "")
				.append(".pdf");
		
		ruta.append(File.separator).append(nombreArchivo);

		outputstream = new FileOutputStream(Constantes.RUTA_LOCAL_FS + ruta);

		cedulaPDF = new CedulaInsaculadosPDF(outputstream, 
											nombreProceso, 
											nombreEstado,
											String.format("%02d", idGeograficoParticipacion), 
											nombreParticipacion,
											modoEjecucion,
											tituloArchivoCedula, 
											leyendaCasilla);

		cedulaPDF.abrirDocumento();
		cedulaPDF.creaEncabezado();
	}

	@Override
	public void destroy() throws Exception {
		
		cedulaPDF.cerrarDocumento();
		outputstream.flush();
		outputstream.close();
		
		DTOArchivos archivo = new DTOArchivos();
		archivo.setIdProcesoElectoral(idProcesoElectoral);
		archivo.setIdDetalleProceso(idDetalleProceso);
		archivo.setIdParticipacion(idParticipacion);
		archivo.setIdTipoVoto(idTipoVoto);
		archivo.setArchivoSistema(Constantes.CEDULA_PDF);
		archivo.setRutaArchivo(ruta.toString());
		archivo.setNombreArchivo(nombreArchivo.toString());
		archivo.setCodigoIntegridad(Utilidades.obtenerPicadillo("SHA-256", Constantes.RUTA_LOCAL_FS + ruta));
		asArchivos.guardarArchivo(archivo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(@NonNull Chunk<? extends DTOResultados1aInsa> items) throws Exception {
		cedulaPDF.escribeDatos((List<DTOResultados1aInsa>) items.getItems());
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

	public String getNombreArchivoCedula() {
		return nombreArchivoCedula;
	}

	public void setNombreArchivoCedula(String nombreArchivoCedula) {
		this.nombreArchivoCedula = nombreArchivoCedula;
	}

	public String getTituloArchivoCedula() {
		return tituloArchivoCedula;
	}

	public void setTituloArchivoCedula(String tituloArchivoCedula) {
		this.tituloArchivoCedula = tituloArchivoCedula;
	}

	public String getLeyendaCasilla() {
		return leyendaCasilla;
	}

	public void setLeyendaCasilla(String leyendaCasilla) {
		this.leyendaCasilla = leyendaCasilla;
	}

}
