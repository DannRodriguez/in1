package mx.ine.procprimerinsa.as;

import java.io.Serializable;
import java.util.List;

import mx.ine.parametrizacion.model.dto.DTODetalleProceso;

public interface ASProcesoDetalleInterface extends Serializable {

	public List<DTODetalleProceso> obtenerDetalleProcesosElectorales(String vigencia, Integer idSistema,
			Integer idEstado, Integer idDistrito, Integer idMunicipio, String ambitoUsuario);

}
