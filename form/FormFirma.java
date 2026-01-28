package mx.ine.procprimerinsa.form;

import java.io.Serializable;

import org.primefaces.model.file.UploadedFile;

public class FormFirma implements Serializable{
	
	private static final long serialVersionUID = -5871494995418977272L;

	private transient UploadedFile archivo; 
	private boolean procesoValido;
	private boolean mostrarFirma;
	private boolean mostrarError;
	private String mensajeError;
	
	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public boolean isProcesoValido() {
		return procesoValido;
	}

	public void setProcesoValido(boolean procesoValido) {
		this.procesoValido = procesoValido;
	}

	public boolean isMostrarFirma() {
		return mostrarFirma;
	}

	public void setMostrarFirma(boolean mostrarFirma) {
		this.mostrarFirma = mostrarFirma;
	}

	public boolean isMostrarError() {
		return mostrarError;
	}

	public void setMostrarError(boolean mostrarError) {
		this.mostrarError = mostrarError;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	@Override
	public String toString() {
		return "FormFirma [procesoValido=" + procesoValido + ", mostrarFirma=" + mostrarFirma + ", mostrarError="
				+ mostrarError + ", mensajeError=" + mensajeError + "]";
	}
	
}
