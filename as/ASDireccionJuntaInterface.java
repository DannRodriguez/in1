package mx.ine.procprimerinsa.as;

import java.io.Serializable;

import mx.ine.procprimerinsa.dto.DTOJuntaDistrital;

public interface ASDireccionJuntaInterface extends Serializable {
	
	public DTOJuntaDistrital obtenerDatosJuntaDistrital(Integer idEstado, Integer idDistrito, String nombreEstado);

}
