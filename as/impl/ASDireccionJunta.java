package mx.ine.procprimerinsa.as.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASDireccionJuntaInterface;
import mx.ine.procprimerinsa.dao.DAODireccionJuntaDistritalInterface;
import mx.ine.procprimerinsa.dto.DTODireccionJuntaDistrital;
import mx.ine.procprimerinsa.dto.DTOJuntaDistrital;

@Service("asDireccionJunta")
@Scope("prototype")
public class ASDireccionJunta implements ASDireccionJuntaInterface {
	
	private static final long serialVersionUID = 5037568694822761661L;
	
	@Autowired
	@Qualifier("daoDireccionJuntaDistrital")
	private transient DAODireccionJuntaDistritalInterface daoDireccionJuntaDistrital;
	
	@Override
	@Transactional(readOnly = true, rollbackFor = { Exception.class })
	public DTOJuntaDistrital obtenerDatosJuntaDistrital(Integer idEstado, Integer idDistrito, String nombreEstado) {
		DTOJuntaDistrital junta = new DTOJuntaDistrital();

		StringBuilder params = new StringBuilder();
		params.append("{");
		params.append("\"idEstado\":");
		params.append(idEstado);
		params.append(",");
		params.append("\"idDistritoFederal\":");
		params.append(idDistrito);
		params.append("}");

		List<DTODireccionJuntaDistrital> dir = daoDireccionJuntaDistrital.getDireccionJuntasDistrital(params.toString());
						
		for (DTODireccionJuntaDistrital dtoDireccion : dir) {
			if(dtoDireccion.getNombreSede().equalsIgnoreCase("JUNTA DISTRITAL")) {
				StringBuilder domicilio = new StringBuilder();
				domicilio.append(dtoDireccion.getDireccion());
				domicilio.append(", ");
				domicilio.append(nombreEstado);
				junta.setDomicilio(domicilio.toString());	
			}
		}
				
		return junta;
	}

}
