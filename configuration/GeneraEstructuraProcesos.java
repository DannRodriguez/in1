package mx.ine.procprimerinsa.configuration;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import mx.ine.parametrizacion.model.dto.DTODetalleProceso;
import mx.ine.parametrizacion.model.dto.DTOEstado;
import mx.ine.procprimerinsa.as.ASDistritosDetalleInterface;
import mx.ine.procprimerinsa.as.ASEstadosDetalleInterface;
import mx.ine.procprimerinsa.as.ASProcesoDetalleInterface;
import mx.ine.procprimerinsa.dto.DTOEstadoGeneral;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.DTOProcesoGeneral;
import mx.ine.procprimerinsa.util.ApplicationContextUtils;
import mx.ine.procprimerinsa.util.Utilidades;


public class GeneraEstructuraProcesos implements Serializable {

	private static final long serialVersionUID = -6079425608925041779L;
	private static final Logger logger = Logger.getLogger(GeneraEstructuraProcesos.class);
	private Map<Integer, DTOProcesoGeneral> procesos;

	public GeneraEstructuraProcesos() {
		super();
		logger.info("********************************** Inicia GeneraEstructuraProcesos  **********************************");
		try {
			
			Integer idSistema = Integer.valueOf(ResourceBundle.getBundle("ApplicationConfig")
										.getString("application.id"));

			List<DTODetalleProceso> detallesProceso = ((ASProcesoDetalleInterface) ApplicationContextUtils
							.getApplicationContext()
							.getBean("asProcesoDetalle"))
							.obtenerDetalleProcesosElectorales("S",
															idSistema, 
															0, 
															0, 
															0, 
															"F");

			procesos = new LinkedHashMap<>();

			for (DTODetalleProceso proceso : detallesProceso) {
				DTOProcesoGeneral procesoGeneral = new DTOProcesoGeneral(proceso.getIdProcesoElectoral(),
																		proceso.getNombreProceso(),
																		proceso.getIdDetalleProceso(),
																		proceso.getDescripcionDetalle(),
																		proceso.getAmbitoSistema(),
																		proceso.getAmbitoDetalle(),
																		proceso.getAmbitoSistema(),
																		proceso.getTipoCapturaSistema(),
																		proceso.getCorte(),
																		new LinkedHashMap<>());
				
				List<DTOEstado> estados = ((ASEstadosDetalleInterface) ApplicationContextUtils
														.getApplicationContext()
														.getBean("asEstadosDetalle"))
														.obtenerEstadosPorProceso(idSistema, 
																		procesoGeneral.getIdProceso(), 
																		procesoGeneral.getIdDetalle(), 
																		0);
				
				for(DTOEstado estado : estados) {
					if(estado.getIdEstado() == null || estado.getIdEstado().equals(0)) continue;
					
					DTOEstadoGeneral estadoGeneral = new DTOEstadoGeneral(estado.getIdEstado(),
														estado.getNombreEstado(),
														null);
					
					List<DTOParticipacionGeneral> participaciones = (((ASDistritosDetalleInterface) ApplicationContextUtils
														.getApplicationContext()
														.getBean("asDistritosDetalle"))
														.obtenerParticipacionesEstadoProceso(idSistema, 
																		procesoGeneral.getIdProceso(), 
																		procesoGeneral.getIdDetalle(), 
																		estado.getIdEstado(), 
																		0,
																		0,
																		"F",
																		"D"));
					
					estadoGeneral.setParticipaciones(Utilidades.collectionToStream(participaciones)
																.collect(Collectors.toMap(DTOParticipacionGeneral::getId, 
																						Function.identity())));
					
					procesoGeneral.getEstados().put(estadoGeneral.getIdEstado(), 
													estadoGeneral);
				}
				
				procesos.put(procesoGeneral.getIdDetalle(), 
							procesoGeneral);
			}

			logger.info(procesos);
			logger.info("********************************** Termina GeneraEstructuraProcesos  **********************************");
		} catch (Exception e) {
			logger.error("ERROR GeneraEstructuraGeografico: ", e);
		}
	}

	public Map<Integer, DTOProcesoGeneral> getProcesos() {
		return procesos;
	}

}