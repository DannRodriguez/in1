package mx.ine.procprimerinsa.form;

import java.io.Serializable;

public class FormReinicio implements Serializable {

	private static final long serialVersionUID = -6711868093456601758L;

	private Integer idProcesoElectoral;
	private String nombreProcesoElectoral;
	private Integer idDetalleProcesoElectoral;
	private Integer idEstado;
	private String nombreEstado;
	private boolean nivelParticipacion;
	
	private boolean procesoValido;
	private String mensaje;

	public FormReinicio() {
		super();
	}

	public Integer getIdProcesoElectoral() {
		return idProcesoElectoral;
	}

	public void setIdProcesoElectoral(Integer idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}

	public String getNombreProcesoElectoral() {
		return nombreProcesoElectoral;
	}

	public void setNombreProcesoElectoral(String nombreProcesoElectoral) {
		this.nombreProcesoElectoral = nombreProcesoElectoral;
	}

	public Integer getIdDetalleProcesoElectoral() {
		return idDetalleProcesoElectoral;
	}

	public void setIdDetalleProcesoElectoral(Integer idDetalleProcesoElectoral) {
		this.idDetalleProcesoElectoral = idDetalleProcesoElectoral;
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

	public boolean isNivelParticipacion() {
		return nivelParticipacion;
	}

	public void setNivelParticipacion(boolean nivelParticipacion) {
		this.nivelParticipacion = nivelParticipacion;
	}

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
	
}
