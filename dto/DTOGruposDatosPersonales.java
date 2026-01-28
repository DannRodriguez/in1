package mx.ine.procprimerinsa.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DTOGruposDatosPersonales implements Serializable {

	private static final long serialVersionUID = -333591639019742944L;

	private Integer idGrupo;
	private List<DTOEstadoGeneral> estados;
	private Date horaInicio;
	private Date horaFinal;
	private Float porcentaje;
	private boolean finalizado;

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public List<DTOEstadoGeneral> getEstados() {
		return estados;
	}

	public void setEstados(List<DTOEstadoGeneral> estados) {
		this.estados = estados;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(Date horaFinal) {
		this.horaFinal = horaFinal;
	}

	public Float getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Float porcentaje) {
		this.porcentaje = porcentaje;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}
	
}
