package mx.ine.procprimerinsa.dto;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTODireccionJuntaDistritalResponse {
	
	private String message;
	private String code;
	private String causa;
	private String status;
	
	List<DTODireccionJuntaDistrital> domicilios;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCausa() {
		return causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DTODireccionJuntaDistrital> getDomicilios() {
		return domicilios;
	}

	public void setDomicilios(List<DTODireccionJuntaDistrital> domicilios) {
		this.domicilios = domicilios;
	}

	@Override
	public String toString() {
		return "DTODireccionJuntaDistritalResponse [message=" + message + ", code=" + code + ", causa=" + causa
				+ ", status=" + status + ", domicilios=" + domicilios + "]";
	}
	
}
