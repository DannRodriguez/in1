package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

public class DTOAccesosSistema implements Serializable {
	
	private static final long serialVersionUID = 653292722734161236L;
	
	private Integer idSistema;
	private String rolUsuario;
	private Integer accesos;
	
	public Integer getIdSistema() {
		return idSistema;
	}
	
	public void setIdSistema(Integer idSistema) {
		this.idSistema = idSistema;
	}
	
	public String getRolUsuario() {
		return rolUsuario;
	}
	
	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
	
	public Integer getAccesos() {
		return accesos;
	}
	
	public void setAccesos(Integer accesos) {
		this.accesos = accesos;
	}
	
}
