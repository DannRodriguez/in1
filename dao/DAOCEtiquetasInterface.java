package mx.ine.procprimerinsa.dao;

import mx.ine.procprimerinsa.dto.db.DTOCEtiquetas;

public interface DAOCEtiquetasInterface {

   public DTOCEtiquetas obtenerEtiqueta(Integer idProceso, Integer idDetalle, Integer idEstado, 
		   Integer idDistrito, Integer idEtiqueta);
    
}
