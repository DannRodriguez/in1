package mx.ine.procprimerinsa.bsd.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASAdministradorBatchInterface;
import mx.ine.procprimerinsa.bsd.BSDAdmistradorBatchInterface;
import mx.ine.procprimerinsa.dto.DTOAdministradorBatch;
import mx.ine.procprimerinsa.dto.DTOTiempoEjecucionProceso;
import mx.ine.procprimerinsa.dto.db.DTOBatchJobExecution;
import mx.ine.procprimerinsa.dto.db.DTOCEstatusProceso;

@Component("bsdAdminBatch")
@Scope("prototype")
public class BSDAdministradorBatch implements BSDAdmistradorBatchInterface {

	@Autowired
	@Qualifier("asAdminBatch")
	private ASAdministradorBatchInterface asAdminBatch;
	
	@Override
	public List<DTOAdministradorBatch> obtenerJobsPorEstado(Integer idDetalleProceso, Integer idEstado, Integer idCorte) {
		return asAdminBatch.obtenerJobsPorEstado(idDetalleProceso, idEstado, idCorte);
	}

	@Override
	public DTOBatchJobExecution obtenerJobPorId(Integer idJobExecution) {
		return asAdminBatch.obtenerJobPorId(idJobExecution);
	}
	
	@Override
	public Map<Integer, DTOCEstatusProceso> obtenerCEstatusProceso(Integer idDetalle) {
		return asAdminBatch.obtenerCEstatusProceso(idDetalle);
	}
	
	@Override
	public List<DTOAdministradorBatch> obtenerProcesosEjecutados(List<Integer> detalles, Integer idCorte) {
		return asAdminBatch.obtenerProcesosEjecutados(detalles, idCorte);
	}
	
	@Override
	public boolean actualizaEjecuciones(List<Integer> detalles) {
		return asAdminBatch.actualizaEjecuciones(detalles);
	}
	
	@Override
	public boolean reiniciaBatch() {
		return asAdminBatch.reiniciaBatch();
	}
	
	@Override
	public boolean reiniciaBitacoraProceso(List<Integer> detalles) {
		return asAdminBatch.reiniciaBitacoraProceso(detalles);
	}
	
	@Override
	public boolean reiniciaEstatusDatosPersonales(List<Integer> detalles, String usuario) {
		return asAdminBatch.reiniciaEstatusDatosPersonales(detalles, usuario);
	}
	
	@Override
	public List<DTOTiempoEjecucionProceso> obtenerTiempoEjecucionProceso(List<Integer> detalles, Integer idCorte){
		return asAdminBatch.obtenerTiempoEjecucionProceso(detalles, idCorte);
	}
	
}
