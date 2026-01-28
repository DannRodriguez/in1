package mx.ine.procprimerinsa.as;

import java.io.Serializable;
import java.util.List;

import mx.ine.procprimerinsa.dto.db.DTOCategoriasProceso;
import mx.ine.procprimerinsa.dto.db.DTOHorariosInsaculacion;
import mx.ine.procprimerinsa.dto.db.DTOLlavesProcesos;
import mx.ine.procprimerinsa.form.FormProcesoInsa;

public interface ASProcesoInsaInterface extends Serializable {

	public List<DTOLlavesProcesos> obtenerLlaves(FormProcesoInsa formProceso) throws Exception;

	public Integer obtenerIdEstatusActual(Integer idDetalleProceso, Integer idParticipacion) throws Exception;

	public Integer actualizaEstatus(Integer idDetalleProceso, Integer idParticipacion, Integer estatus, Integer jobExecutionId)
			throws Exception;
	
	public Integer obtenerIdReinicio(Integer idDetalleProceso, Integer idParticipacion);
	
	public boolean ejecutaCalculoInsaculados(Integer idCorteLN, Integer idProcesoElectoral, Integer idDetalleProcesoElectoral,
			Integer idEstado, Integer idParticipacion, Integer idGeograficoParticipacion, Integer mesInsacular, 
			String letraInsacular, Integer idCorteLNAActualizar, Integer validaYaEsInsaculado, 
			String mesesYaSorteados, Integer consideraExtraordinarias, Integer idReinicio) throws Exception;
	
	public Integer getTotalListaNominalPorDistrito(Integer idCorteLN, 
			Integer idEstado, Integer idDistrito);
	
	public Integer getCorteLNActivo(Integer idProceso, Integer idDetalle);
	
	public List<DTOHorariosInsaculacion> obtenerHorarios(List<Integer> detalles);

	public List<DTOCategoriasProceso> obtenerListaCategorias(Integer idDetalleProceso);
	
	public boolean truncaParticionDatosInsaculados(Integer idDetalleProcesoElectoral, Integer idParticipacion);

	public Integer actualizaEstatusEtapasInsaculacion(Integer idDetalleProceso, Integer idParticipacion, 
			Integer estatus, Integer jobExecutionId) throws Exception;
	
	public boolean eliminaResultadosInsa(Integer idDetalleProceso, Integer idParticipacion);
	
}
