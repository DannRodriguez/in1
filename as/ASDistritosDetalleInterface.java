package mx.ine.procprimerinsa.as;

import java.io.Serializable;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;

public interface ASDistritosDetalleInterface extends Serializable {

	public List<DTOParticipacionGeneral> obtenerParticipacionesEstadoProceso(Integer idSistema, Integer idProceso,
			Integer idDetalle, Integer idEstado, Integer idDistrito, Integer idMunicipio, String ambitoUsuario,
			String tipoCapturaSistema);

}
