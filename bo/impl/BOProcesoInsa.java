package mx.ine.procprimerinsa.bo.impl;

import mx.ine.procprimerinsa.bo.BOProcesoInsaInterface;
import mx.ine.procprimerinsa.dto.db.DTOBitacoraProcesos;
import mx.ine.procprimerinsa.dto.db.DTOEstatusProcesos;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("boProcesoInsa")
@Scope("prototype")
public class BOProcesoInsa implements BOProcesoInsaInterface {
	
	@Override
	public DTOBitacoraProcesos generaBitacora(DTOEstatusProcesos estatusProceso) {
		DTOBitacoraProcesos bitacora = new DTOBitacoraProcesos();
		bitacora.setIdProcesoElectoral(estatusProceso.getIdProcesoElectoral());
		bitacora.setIdDetalleProceso(estatusProceso.getIdDetalleProceso());
		bitacora.setIdParticipacion(estatusProceso.getIdParticipacion());
		bitacora.setIdEjecucion(estatusProceso.getIdEjecucion());
		bitacora.setIdReinicio(estatusProceso.getIdReinicio());
		bitacora.setIdEstatusProceso(estatusProceso.getIdEstatusProceso());
		bitacora.setUsuario(estatusProceso.getUsuario());
		bitacora.setFechaHora(new Date());
		bitacora.setJobExecutionId(estatusProceso.getJobExecutionId());
		return bitacora;
	}
	
	@Override
	public int calculaNumeroInsacular(int numeroCiudadanos, int porcentajeInsacular, int minimoInsacular) {
		double calculo = Math.ceil(numeroCiudadanos * (porcentajeInsacular * 0.01));
		return (int) calculo < minimoInsacular ? minimoInsacular : (int)calculo;
	}
	
}
