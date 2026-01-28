package mx.ine.procprimerinsa.bo.impl;

import java.io.File;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.bo.BODatosPersonalesInterface;
import mx.ine.procprimerinsa.util.Constantes;

@Component("boDatosPersonales")
@Scope("prototype")
public class BODatosPersonales implements BODatosPersonalesInterface {
	
	private static final long serialVersionUID = -2037749069328058351L;
	
	@Override
	public String generaDirectorioPrincipal(Integer idProcesoElectoral, Integer idDetalleProceso) {
		return new StringBuilder()
				.append(Constantes.RUTA_LOCAL_FS)
				.append(idProcesoElectoral)
				.append(File.separator)
				.append(idDetalleProceso)
				.append(File.separator)
				.append(Constantes.CARPETA_PROC_GLUSTER_INSA1)
				.append(File.separator)
				.append(Constantes.CARPETA_DATOS_PERSONALES)
				.append(File.separator)
				.toString();
	}
	
	@Override
	public String generaNombreArchivo(Integer idEstado) {
		return new StringBuilder()
				.append(idEstado)
				.append(".txt")
				.toString();
	}
	
	@Override
	public String generaDirectorioDatosDerfe(Integer idProcesoElectoral, Integer idDetalleProceso) {
		return new StringBuilder()
				.append(Constantes.RUTA_LOCAL_FS)
				.append(idProcesoElectoral)
				.append(File.separator)
				.append(idDetalleProceso)
				.append(File.separator)
				.append(Constantes.CARPETA_PROC_GLUSTER_INSA1)
				.append(File.separator)
				.append(Constantes.CARPETA_DATOS_DERFE)
				.append(File.separator)
				.toString();
	}
	
	@Override
	public String generarNombreArchivoCarga(Integer idEstado) {
		return new StringBuilder()
				.append("LN")
				.append(String.format("%02d", idEstado))
				.append(".txt")
				.toString();
	}
	
}
