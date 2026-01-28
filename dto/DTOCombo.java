package mx.ine.procprimerinsa.dto;

public class DTOCombo implements java.io.Serializable {
	
	private static final long serialVersionUID = 4284699481175582398L;
	
	private String value;
	private String id;

	public DTOCombo() {
		super();
	}
	
	public DTOCombo(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setIdInteger(Integer id) {
		this.id = String.valueOf(id);
	}
	
	public void setValueInteger(Integer value) {
		this.value = String.valueOf(value);
	}

	@Override
	public String toString() {
		return "DTOCombo [value=" + value + ", id=" + id + "]";
	}

}
