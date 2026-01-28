package mx.ine.procprimerinsa.bsd.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASArchivosInterface;
import mx.ine.procprimerinsa.bsd.BSDArchivosInterface;
import mx.ine.procprimerinsa.dto.db.DTOArchivos;

@Component("bsdArchivos")
@Scope("prototype")
public class BSDArchivos implements BSDArchivosInterface {

	private static final long serialVersionUID = -1270430055213804941L;
	
	@Autowired
	@Qualifier("asArchivos")
	private ASArchivosInterface asArchivos;

	@Override
	public Map<String, DTOArchivos> obtenerListadoArchivosProceso(DTOArchivos dtoArchivo) {
		return asArchivos.obtenerListadoArchivosProceso(dtoArchivo);
	}

}
