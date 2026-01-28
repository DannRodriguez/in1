package mx.ine.procprimerinsa.as;

import java.util.List;
import java.util.Map;

import mx.ine.procprimerinsa.dto.DTOAdministradorBatch;
import mx.ine.procprimerinsa.dto.DTOTiempoEjecucionProceso;
import mx.ine.procprimerinsa.dto.db.DTOBatchJobExecution;
import mx.ine.procprimerinsa.dto.db.DTOCEstatusProceso;

public interface ASAdministradorBatchInterface {

	public List<DTOAdministradorBatch> obtenerJobsPorEstado(Integer idDetalleProceso, Integer idEstado, Integer idCorte);

	public DTOBatchJobExecution obtenerJobPorId(Integer idJobExecution);
	
	public Map<Integer, DTOCEstatusProceso> obtenerCEstatusProceso(Integer idDetalle);
	
	public List<DTOAdministradorBatch> obtenerProcesosEjecutados(List<Integer> detalles, Integer idCorte);
	
	public boolean actualizaEjecuciones(List<Integer> detalles);
	
	public boolean reiniciaBatch();
	
	public boolean reiniciaBitacoraProceso(List<Integer> detalles);
	
	public boolean reiniciaEstatusDatosPersonales(List<Integer> detalles, String usuario);
	
	public List<DTOTiempoEjecucionProceso> obtenerTiempoEjecucionProceso(List<Integer> detalles, Integer idCorte);
	
}
