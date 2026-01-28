package mx.ine.procprimerinsa.dao;

import mx.ine.procprimerinsa.dto.db.DTOCParametros;

public interface DAOCParametrosInterface {
    
    //Método para la obtención de parámetros del proceso
	public DTOCParametros obtenerParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer id, Integer idParametro) throws Exception;
	
	//Método para actualizar un parametro
	public void actualizaParametro(DTOCParametros parametro) throws Exception;

}
