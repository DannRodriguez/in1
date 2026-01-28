package mx.ine.procprimerinsa.bsd.impl;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASGenerarLlavesInterface;
import mx.ine.procprimerinsa.as.ASParametrosInterface;
import mx.ine.procprimerinsa.bsd.BSDGenerarLlavesInterface;
import mx.ine.procprimerinsa.dto.DTOEstadoGeneral;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.form.FormGenerarLlaves;
import mx.ine.procprimerinsa.util.Constantes;

@Component("bsdGenerarLlaves")
@Scope("prototype")
public class BSDGenerarLlaves implements BSDGenerarLlavesInterface {

	private static final long serialVersionUID = -8787041517383985056L;
	private static final Logger logger = Logger.getLogger(BSDGenerarLlaves.class);
	
	@Autowired
	@Qualifier("asGenerarLlaves")
	private ASGenerarLlavesInterface asGenerarLlaves;
	
	@Autowired
	@Qualifier("asParametros")
	private ASParametrosInterface asParametros;
	
	@Override
	public Map<Integer, String[]> obtenerLlavesProceso(Integer idDetalleProceso, Integer modoEjecucion) {
		return asGenerarLlaves.obtenerLlavesProceso(idDetalleProceso, modoEjecucion);
	}
	
	@Override
	public void generarLlaves(String nombreProceso, Map<Integer, DTOEstadoGeneral> estados, FormGenerarLlaves form) throws Exception {
		asGenerarLlaves.generarLlaves(nombreProceso, estados, form);
	}
	
	@Override
	public void reiniciarLlaves(String nombreProceso, Map<Integer, DTOEstadoGeneral> estados, FormGenerarLlaves form) throws Exception {
		asGenerarLlaves.reiniciarLlaves(nombreProceso, estados, form);
	}

	@Override
	public ByteArrayOutputStream descargarLlaves(DTOEstadoGeneral estado, DTOParticipacionGeneral distrito, 
			FormGenerarLlaves form) throws Exception {
		return asGenerarLlaves.descargarLlaves(estado, distrito, form);
	}
	
	@Override
	public void obtenerModoEjecucion(FormGenerarLlaves form) throws Exception {
		String modoEjecucion = asParametros.obtenerParametro(form.getIdProcesoElectoral(), 
															form.getIdDetalleProceso(), 
															0, 
															0, 
															Constantes.PARAMETRO_TIPO_DE_EJECUCION);
		
		if(modoEjecucion != null) {
			form.setModoEjecucion(modoEjecucion.equals("J") ? 1 : 0);
		} else {
			logger.error("ERROR BSDGenerarLlaves -generarLlaves(): No se encuentra el parámetro de tipo de ejecución, por lo que se utiliza J por defecto");
			form.setModoEjecucion(1);
		}

	}
}
