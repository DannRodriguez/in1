package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import mx.ine.procprimerinsa.dto.DTOCSVPrintableInterface;

@Entity
@Table(name = "C_TIPO_VOTO_PARTICIPACION", schema = "INSA1")
public class DTOCTipoVotoParticipacion implements Serializable, DTOCSVPrintableInterface {
	
	private static final long serialVersionUID = 481725469643169521L;

	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@Column(name = "ID_PARTICIPACION", nullable = false, precision = 9, scale = 0)
	private Integer idParticipacion;
	
	@Id
	@Column(name = "ID_TIPO_VOTO", nullable = false, precision = 2, scale = 0)
	private Integer idTipoVoto;
	
	public DTOCTipoVotoParticipacion() {
		super();
	}

	public DTOCTipoVotoParticipacion(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
			Integer idTipoVoto) {
		super();
		this.idProcesoElectoral = idProcesoElectoral;
		this.idDetalleProceso = idDetalleProceso;
		this.idParticipacion = idParticipacion;
		this.idTipoVoto = idTipoVoto;
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

	@Override
	public int hashCode() {
		return Objects.hash(idDetalleProceso, idParticipacion, idProcesoElectoral, idTipoVoto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DTOCTipoVotoParticipacion)) {
			return false;
		}
		DTOCTipoVotoParticipacion other = (DTOCTipoVotoParticipacion) obj;
		return Objects.equals(idDetalleProceso, other.idDetalleProceso)
				&& Objects.equals(idParticipacion, other.idParticipacion)
				&& Objects.equals(idProcesoElectoral, other.idProcesoElectoral)
				&& Objects.equals(idTipoVoto, other.idTipoVoto);
	}

	@Override
	public String toString() {
		return "DTOCTipoVotoParticipacion [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso="
				+ idDetalleProceso + ", idParticipacion=" + idParticipacion + ", idTipoVoto=" + idTipoVoto + "]";
	}
	
	@Override
	public Object[] getCSVPrintable() {
		return new Object[] {
				idProcesoElectoral,
				idDetalleProceso,
				idParticipacion,
				idTipoVoto
		};
	}

	
}
