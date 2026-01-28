package mx.ine.procprimerinsa.dao;

import java.util.List;

import mx.ine.parametrizacion.model.dto.DTODistrito;

public interface DAODistritosDetalleInterface {

	public List<DTODistrito> getDistritosByProcesoDetalleEstado(Integer idSistema, Integer idProceso,
			Integer idDetalle, Integer idEstado, Integer idDistrito);

}
