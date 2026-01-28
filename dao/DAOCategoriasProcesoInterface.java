package mx.ine.procprimerinsa.dao;

import java.io.Serializable;
import java.util.List;

import mx.ine.procprimerinsa.dto.db.DTOCategoriasProceso;

public interface DAOCategoriasProcesoInterface extends DAOGenericInterface<DTOCategoriasProceso, Serializable>{

	public List<DTOCategoriasProceso> obtenerCategorias(Integer idDetalleProceso);
	
}
