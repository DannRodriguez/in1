package mx.ine.procprimerinsa.bsd;

import java.io.Serializable;

public interface BSDReinicioProcesoInterface extends Serializable {

	public Integer obtenerIdReinicio(Integer idDetalleProceso, Integer idParticipacion);
	public boolean ejecutaReinicio(Integer idProcesoElectoral, Integer idDetalleProcesoElectoral, Integer idParticipacion);
	public boolean eliminaDatosInsaculados(Integer idDetalleProcesoElectoral, Integer idParticipacion);
	public boolean eliminaResultadosInsa(Integer idDetalleProcesoElectoral, Integer idParticipacion) ;
	public boolean eliminaRegistroArchivos(Integer idProcesoElectoral, Integer idDetalleProcesoElectoral, Integer idParticipacion);
	public boolean setJobExecutionIdDefaultPorParticipacion(Integer idDetalleProcesoElectoral, Integer idParticipacion);
	public Integer actualizaEstatus(Integer idDetalleProceso, Integer idParticipacion, Integer estatus) throws Exception;
	
}
