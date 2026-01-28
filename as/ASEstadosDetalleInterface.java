package mx.ine.procprimerinsa.as;

import java.io.Serializable;
import java.util.List;

import mx.ine.parametrizacion.model.dto.DTOEstado;

public interface ASEstadosDetalleInterface extends Serializable {
	
	public List<DTOEstado> obtenerEstadosPorProceso(Integer idSistema, Integer idProceso, 
			Integer idDetalle, Integer idEstado);

	public List<DTOEstado> obtenerEstadosConProcesosVigentes(Integer idSistema, Integer idEstado, String rol);

}
