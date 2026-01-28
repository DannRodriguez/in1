package mx.ine.procprimerinsa.bo.impl;

import java.io.File;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.bo.BOGenerarLlavesInterface;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@Component("boGenerarLlaves")
@Scope("prototype")
public class BOGenerarLlaves implements BOGenerarLlavesInterface {

	@Override
	public String generaRuta(Integer idProceso, Integer idDetalle, String estado, String participacion, Integer id,
			Integer modo) {
		StringBuilder ruta = new StringBuilder()
						.append(idProceso).append(File.separator)
						.append(idDetalle).append(File.separator)
						.append(Constantes.CARPETA_PROC_GLUSTER_INSA1).append(File.separator)
						.append(Constantes.CARPETA_LLAVES)
						.append(modo.equals(0) ? Constantes.TITULO_ARCHIVO_LLAVES_SIMULACRO : "").append(File.separator)
						.append(estado).append(File.separator)
						.append(String.format("%02d", id)).append("_").append(Utilidades.cleanStringForFileName(participacion));
		return ruta.toString();
	}

	@Override
	public String generaSemilla() {
		return RandomStringUtils.secure().next(4, true, true);
	}
}
