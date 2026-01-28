package mx.ine.procprimerinsa.as;

import java.io.Serializable;

import mx.ine.procprimerinsa.dto.db.DTOCEtiquetas;

public interface ASCEtiquetasInterface extends Serializable {

	public DTOCEtiquetas obtenerEtiqueta(Integer idProceso, Integer idDetalle, Integer idEstado, 
			Integer idDistrito, Integer idEtiqueta);
	
}