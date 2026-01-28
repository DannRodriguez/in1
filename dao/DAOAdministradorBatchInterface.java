package mx.ine.procprimerinsa.dao;

import java.io.Serializable;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOAdministradorBatch;
import mx.ine.procprimerinsa.dto.DTOTiempoEjecucionProceso;
import mx.ine.procprimerinsa.dto.db.DTOBatchJobExecution;
import mx.ine.procprimerinsa.dto.db.DTOCEstatusProceso;

public interface DAOAdministradorBatchInterface extends DAOGenericInterface<DTOBatchJobExecution, Serializable> {

	public List<DTOAdministradorBatch> getJobsInsaByEstado(Integer idDetalleProceso, Integer idEstado, Integer idCorte);

	public DTOBatchJobExecution getJobExecutionById(Integer idJobExecution);
	
	public List<DTOCEstatusProceso> obtenerCEstatusProceso(Integer idDetalle);
	
	public List<DTOAdministradorBatch> obtenerProcesosEjecutados(List<Integer> detalles, Integer idCorte);
	
	public void actualizaEjecuciones(List<Integer> detalles);
	
	public void reiniciaBatch();
	
	public void reiniciaBitacoraProceso(List<Integer> detalles);
	
	public void reiniciaEstatusDatosPersonales(List<Integer> detalles, String usuario);
	
	public List<DTOTiempoEjecucionProceso> obtenerTiempoEjecucionProceso(List<Integer> detalles, Integer idCorte);
	
}
