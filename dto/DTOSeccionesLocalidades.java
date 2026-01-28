package mx.ine.procprimerinsa.dto;

import java.io.Serializable;
import java.util.Map;


public class DTOSeccionesLocalidades implements Serializable {	
	
	private static final long serialVersionUID = 4176579995705491404L;
	
	private Integer sec;
    private Map<Integer, DTOCombo> localidades;
    
	public DTOSeccionesLocalidades() {
		super();
	}

	public DTOSeccionesLocalidades(Integer sec) {
		super();
		this.sec = sec;
	}

	public DTOSeccionesLocalidades(Integer sec, Map<Integer, DTOCombo> localidades) {
		super();
		this.sec = sec;
		this.localidades = localidades;
	}

	public Integer getSec() {
		return sec;
	}
	
	public void setSec(Integer sec) {
		this.sec = sec;
	}
	
	public Map<Integer, DTOCombo> getLocalidades() {
		return localidades;
	}
	
	public void setLocalidades(Map<Integer, DTOCombo> localidades) {
		this.localidades = localidades;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sec == null) ? 0 : sec.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOSeccionesLocalidades other = (DTOSeccionesLocalidades) obj;
		if (sec == null) {
			if (other.sec != null)
				return false;
		} else if (!sec.equals(other.sec))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "DTOSeccionesLocalidades [sec=" + sec + ", localidades=" + localidades + "]";
	}
}
