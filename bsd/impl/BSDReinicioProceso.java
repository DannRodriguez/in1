package mx.ine.procprimerinsa.bsd.impl;

import mx.ine.procprimerinsa.as.ASArchivosInterface;
import mx.ine.procprimerinsa.as.ASBitacoraProcesosInterface;
import mx.ine.procprimerinsa.as.ASDatosInsaculadosInterface;
import mx.ine.procprimerinsa.as.ASProcesoInsaInterface;
import mx.ine.procprimerinsa.bsd.BSDReinicioProcesoInterface;
import mx.ine.procprimerinsa.util.Constantes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("bsdReinicioProceso")
@Scope("prototype")
public class BSDReinicioProceso implements BSDReinicioProcesoInterface {

	private static final long serialVersionUID = -4646626531350132737L;

	@Autowired
	@Qualifier("asProcesoInsa")
	private ASProcesoInsaInterface asProcesoInsa;
	
	@Autowired
	@Qualifier("asBitacoraProcesos")
	private ASBitacoraProcesosInterface asBitacoraProcesos;

	@Autowired
	@Qualifier("asArchivos")
	private ASArchivosInterface asArchivos;

	@Autowired
	@Qualifier("asDatosInsaculados")
	private ASDatosInsaculadosInterface asDatosInsaculados;

	@Override
	public Integer obtenerIdReinicio(Integer idDetalleProceso, Integer idParticipacion) {
		return asProcesoInsa.obtenerIdReinicio(idDetalleProceso, idParticipacion);
	}

	@Override
	public boolean ejecutaReinicio(Integer idProcesoElectoral, Integer idDetalleProcesoElectoral, Integer idParticipacion) {
		return asProcesoInsa.truncaParticionDatosInsaculados(idDetalleProcesoElectoral, idParticipacion)
				&& asProcesoInsa.eliminaResultadosInsa(idDetalleProcesoElectoral, idParticipacion)
				&& asArchivos.eliminaRegistroArchivos(idProcesoElectoral, idDetalleProcesoElectoral, idParticipacion)
				&& asBitacoraProcesos.setJobExecutionIdDefaultPorParticipacion(idDetalleProcesoElectoral, idParticipacion);
	}
	
	@Override
	public boolean eliminaDatosInsaculados(Integer idDetalleProcesoElectoral, Integer idParticipacion) {
		return asProcesoInsa.truncaParticionDatosInsaculados(idDetalleProcesoElectoral, idParticipacion);
	}
	
	@Override
	public boolean eliminaResultadosInsa(Integer idDetalleProcesoElectoral, Integer idParticipacion) {
		return asProcesoInsa.eliminaResultadosInsa(idDetalleProcesoElectoral, idParticipacion);
	}
	
	@Override
	public boolean eliminaRegistroArchivos(Integer idProcesoElectoral, Integer idDetalleProcesoElectoral, Integer idParticipacion) {
		return asArchivos.eliminaRegistroArchivos(idProcesoElectoral, idDetalleProcesoElectoral, idParticipacion);
	}
	
	@Override
	public boolean setJobExecutionIdDefaultPorParticipacion(Integer idDetalleProcesoElectoral, Integer idParticipacion) {
		return asBitacoraProcesos.setJobExecutionIdDefaultPorParticipacion(idDetalleProcesoElectoral, idParticipacion);
	}

	@Override
	public Integer actualizaEstatus(Integer idDetalleProceso, Integer idParticipacion, Integer estatus) throws Exception {
		if (estatus.equals(Constantes.ESTATUS_PROCESO_FINALIZADO)
			|| estatus.equals(asProcesoInsa.obtenerIdEstatusActual(idDetalleProceso, 
																	idParticipacion))) {
				return asProcesoInsa.actualizaEstatus(idDetalleProceso,
													idParticipacion, 
													estatus,
													Constantes.DEFAULT_JOB_EXECUTION_ID);
		}
		return estatus;
	}
}
