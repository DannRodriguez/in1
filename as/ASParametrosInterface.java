package mx.ine.procprimerinsa.as;

import java.io.Serializable;

public interface ASParametrosInterface extends Serializable{

	//Método para obtener el parametro solicitado
	public String obtenerParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer id, Integer idParametro)
			throws Exception;

	//Método para actualizar un parametro
	public boolean actualizarParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer id, Integer idParametro, String valor)
			throws Exception;
	
}
