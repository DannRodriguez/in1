package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SERVIDORES_PUBLICOS", schema = "LN")
public class DTOServidoresPublicos implements Serializable {
	
	private static final long serialVersionUID = -3659267002301099493L;

	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@Column(name = "ID_CORTE_SPN", nullable = false, precision = 3, scale = 0)
	private Integer idCorteSPN;
	
	@Id
	@Column(name = "NUMERO_CREDENCIAL_ELECTOR", nullable = false, length = 18)
	private String numeroCredencialElector;

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

	public Integer getIdCorteSPN() {
		return idCorteSPN;
	}

	public void setIdCorteSPN(Integer idCorteSPN) {
		this.idCorteSPN = idCorteSPN;
	}

	public String getNumeroCredencialElector() {
		return numeroCredencialElector;
	}

	public void setNumeroCredencialElector(String numeroCredencialElector) {
		this.numeroCredencialElector = numeroCredencialElector;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCorteSPN, idDetalleProceso, idProcesoElectoral, numeroCredencialElector);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOServidoresPublicos other = (DTOServidoresPublicos) obj;
		return Objects.equals(idCorteSPN, other.idCorteSPN) && Objects.equals(idDetalleProceso, other.idDetalleProceso)
				&& Objects.equals(idProcesoElectoral, other.idProcesoElectoral)
				&& Objects.equals(numeroCredencialElector, other.numeroCredencialElector);
	}

	@Override
	public String toString() {
		return "DTOServidoresPublicos [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso="
				+ idDetalleProceso + ", idCorteSPN=" + idCorteSPN + ", numeroCredencialElector="
				+ numeroCredencialElector + "]";
	}

}
