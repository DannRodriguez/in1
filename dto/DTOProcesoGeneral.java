package mx.ine.procprimerinsa.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class DTOProcesoGeneral implements Serializable{

	private static final long serialVersionUID = -2862282352472030187L;

	private Integer idProceso;
	private String nombreProceso;
	private Integer idDetalle;
	private String nombreDetalle;
	private String ambitoSistema;
	private String ambitoDetalle;
	private String ambitoCaptura;
	 private String tipoCapturaSistema;
	private Integer idCorte;
	
	private Map<Integer, DTOEstadoGeneral> estados;
	
	public DTOProcesoGeneral() {
		
	}

	public DTOProcesoGeneral(Integer idProceso, String nombreProceso, Integer idDetalle, String nombreDetalle,
			Map<Integer, DTOEstadoGeneral> estados) {
		super();
		this.idProceso = idProceso;
		this.nombreProceso = nombreProceso;
		this.idDetalle = idDetalle;
		this.nombreDetalle = nombreDetalle;
		this.estados = estados;
	}

	public DTOProcesoGeneral(Integer idProceso, String nombreProceso, Integer idDetalle, String nombreDetalle,
			String ambitoSistema, String ambitoDetalle, String ambitoCaptura, String tipoCapturaSistema,
			Integer idCorte, Map<Integer, DTOEstadoGeneral> estados) {
		super();
		this.idProceso = idProceso;
		this.nombreProceso = nombreProceso;
		this.idDetalle = idDetalle;
		this.nombreDetalle = nombreDetalle;
		this.ambitoSistema = ambitoSistema;
		this.ambitoDetalle = ambitoDetalle;
		this.ambitoCaptura = ambitoCaptura;
		this.tipoCapturaSistema = tipoCapturaSistema;
		this.idCorte = idCorte;
		this.estados = estados;
	}

	public Integer getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Integer idProceso) {
		this.idProceso = idProceso;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public Integer getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	public String getNombreDetalle() {
		return nombreDetalle;
	}

	public void setNombreDetalle(String nombreDetalle) {
		this.nombreDetalle = nombreDetalle;
	}

	public String getAmbitoSistema() {
		return ambitoSistema;
	}

	public void setAmbitoSistema(String ambitoSistema) {
		this.ambitoSistema = ambitoSistema;
	}

	public String getAmbitoDetalle() {
		return ambitoDetalle;
	}

	public void setAmbitoDetalle(String ambitoDetalle) {
		this.ambitoDetalle = ambitoDetalle;
	}

	public String getAmbitoCaptura() {
		return ambitoCaptura;
	}

	public void setAmbitoCaptura(String ambitoCaptura) {
		this.ambitoCaptura = ambitoCaptura;
	}

	public String getTipoCapturaSistema() {
		return tipoCapturaSistema;
	}

	public void setTipoCapturaSistema(String tipoCapturaSistema) {
		this.tipoCapturaSistema = tipoCapturaSistema;
	}

	public Integer getIdCorte() {
		return idCorte;
	}

	public void setIdCorte(Integer idCorte) {
		this.idCorte = idCorte;
	}

	public Map<Integer, DTOEstadoGeneral> getEstados() {
		return estados;
	}

	public void setEstados(Map<Integer, DTOEstadoGeneral> estados) {
		this.estados = estados;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idDetalle, idProceso);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOProcesoGeneral other = (DTOProcesoGeneral) obj;
		return Objects.equals(idDetalle, other.idDetalle) && Objects.equals(idProceso, other.idProceso);
	}

	@Override
	public String toString() {
		return "DTOProcesoGeneral [idProceso=" + idProceso + ", nombreProceso=" + nombreProceso + ", idDetalle="
				+ idDetalle + ", nombreDetalle=" + nombreDetalle + ", ambitoSistema=" + ambitoSistema
				+ ", ambitoDetalle=" + ambitoDetalle + ", ambitoCaptura=" + ambitoCaptura + ", tipoCapturaSistema="
				+ tipoCapturaSistema + ", idCorte=" + idCorte + ", estados=" + estados + "]";
	}

}

