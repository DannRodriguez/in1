package mx.ine.procprimerinsa.as;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mx.ine.procprimerinsa.dto.DTOCombo;
import mx.ine.procprimerinsa.dto.DTOConfiguracionParametros;

public interface ASConfiguracionParametrosInterface extends Serializable {

	public List<DTOConfiguracionParametros> obtenerLista(Integer idCorte);
	public Map<String, DTOCombo> obtenerDescripcionParametros();
	public boolean actualizaParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito, String tipoJunta, Integer idParametro, String valorParametro);
	public boolean eliminaParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito, String tipoJunta, Integer idParametro);
	public boolean agregaParametro(DTOConfiguracionParametros parametro, boolean agregaDescripcion);
	
}
