package mx.ine.procprimerinsa.dao;

import java.util.List;

import mx.ine.parametrizacion.model.dto.DTOEstado;

public interface DAOEstadosDetalleInterface {
	
	public List<DTOEstado> obtenerEstadosPorProceso(Integer idSistema, Integer idProceso, 
			Integer idDetalle, Integer idEstado);

	public List<DTOEstado> obtenerEstadosConProcesosVigentes(Integer idSistema, Integer idEstado);

}
