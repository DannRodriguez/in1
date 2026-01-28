package mx.ine.procprimerinsa.dto;

import java.util.Date;
import java.util.Map;

public class DTOGrupoHorarioInsaculacion implements java.io.Serializable {

	private static final long serialVersionUID = -7114342439630944770L;

	/*********** Atributos de la clase ***************************/
	private Integer idGrupo;
	
	private Map<Integer, DTOEstado> estados;
	
	private Date horaInicio;
	
	private Date horaFinal;
	
	private Float porcentaje;
	
	private Integer participaciones;
	
	private Integer participacionesFinalizadas;
	
	/******************* Getter And Setter ***********************/

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Map<Integer, DTOEstado> getEstados() {
		return estados;
	}

	public void setEstados(Map<Integer, DTOEstado> estados) {
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

	public Integer getParticipaciones() {
		return participaciones;
	}

	public void setParticipaciones(Integer participaciones) {
		this.participaciones = participaciones;
	}

	public Integer getParticipacionesFinalizadas() {
		return participacionesFinalizadas;
	}

	public void setParticipacionesFinalizadas(Integer participacionesFinalizadas) {
		this.participacionesFinalizadas = participacionesFinalizadas;
	}
	
}
