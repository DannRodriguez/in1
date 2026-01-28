package mx.ine.procprimerinsa.form;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTODetalleParticipaciones;
import mx.ine.procprimerinsa.dto.DTOEstadoDetalles;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;

public class FormReportesHistoricos implements Serializable {
	
	private static final long serialVersionUID = -8753775839440019131L;
	
	private boolean procesoValido;
	private String mensaje;
	
	private Integer idEstadoUsuario;
	private Integer idDistritoUsuario;
	
	private List<DTOEstadoDetalles> estadosReportesHistoricos;
	private DTOEstadoDetalles estadoSeleccionado;
	
	private Integer idDetalleSeleccionado;
	private DTODetalleParticipaciones detalleSeleccionado;
	
	private Integer idParticipacionSeleccionada;
	private DTOParticipacionGeneral participacionSeleccionada;
	
	private String directorioPrincipal;
	
	private List<File> documentos;

	public boolean isProcesoValido() {
		return procesoValido;
	}

	public void setProcesoValido(boolean procesoValido) {
		this.procesoValido = procesoValido;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getIdEstadoUsuario() {
		return idEstadoUsuario;
	}

	public void setIdEstadoUsuario(Integer idEstadoUsuario) {
		this.idEstadoUsuario = idEstadoUsuario;
	}

	public Integer getIdDistritoUsuario() {
		return idDistritoUsuario;
	}

	public void setIdDistritoUsuario(Integer idDistritoUsuario) {
		this.idDistritoUsuario = idDistritoUsuario;
	}

	public List<DTOEstadoDetalles> getEstadosReportesHistoricos() {
		return estadosReportesHistoricos;
	}

	public void setEstadosReportesHistoricos(List<DTOEstadoDetalles> estadosReportesHistoricos) {
		this.estadosReportesHistoricos = estadosReportesHistoricos;
	}

	public DTOEstadoDetalles getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	public void setEstadoSeleccionado(DTOEstadoDetalles estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	public Integer getIdDetalleSeleccionado() {
		return idDetalleSeleccionado;
	}

	public void setIdDetalleSeleccionado(Integer idDetalleSeleccionado) {
		this.idDetalleSeleccionado = idDetalleSeleccionado;
	}

	public DTODetalleParticipaciones getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(DTODetalleParticipaciones detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

	public Integer getIdParticipacionSeleccionada() {
		return idParticipacionSeleccionada;
	}

	public void setIdParticipacionSeleccionada(Integer idParticipacionSeleccionada) {
		this.idParticipacionSeleccionada = idParticipacionSeleccionada;
	}

	public DTOParticipacionGeneral getParticipacionSeleccionada() {
		return participacionSeleccionada;
	}

	public void setParticipacionSeleccionada(DTOParticipacionGeneral participacionSeleccionada) {
		this.participacionSeleccionada = participacionSeleccionada;
	}

	public String getDirectorioPrincipal() {
		return directorioPrincipal;
	}

	public void setDirectorioPrincipal(String directorioPrincipal) {
		this.directorioPrincipal = directorioPrincipal;
	}

	public List<File> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<File> documentos) {
		this.documentos = documentos;
	}

	@Override
	public String toString() {
		return "FormReportesHistoricos [procesoValido=" + procesoValido + ", mensaje=" + mensaje + ", idEstadoUsuario="
				+ idEstadoUsuario + ", idDistritoUsuario=" + idDistritoUsuario + ", estadosReportesHistoricos="
				+ estadosReportesHistoricos + ", estadoSeleccionado=" + estadoSeleccionado + ", idDetalleSeleccionado="
				+ idDetalleSeleccionado + ", detalleSeleccionado=" + detalleSeleccionado
				+ ", idParticipacionSeleccionada=" + idParticipacionSeleccionada + ", participacionSeleccionada="
				+ participacionSeleccionada + ", directorioPrincipal=" + directorioPrincipal + ", documentos="
				+ documentos + "]";
	}
	
}
