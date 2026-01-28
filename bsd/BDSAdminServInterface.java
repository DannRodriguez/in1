package mx.ine.procprimerinsa.bsd;

import java.io.Serializable;
import java.util.List;

import mx.ine.parametrizacion.model.dto.DTODetalleProceso;
import mx.ine.parametrizacion.model.dto.DTOEstado;
import mx.ine.parametrizacion.model.dto.DTOMenu;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;

public interface BDSAdminServInterface extends Serializable {
	
	public String obtenerParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer id, 
			Integer idParametro) throws Exception;
	
	public List<DTOEstado> obtenerEstadosConProcesosVigentes(Integer idSistema, Integer idEstado, String rol);
	
	public List<DTODetalleProceso> obtenerDetalleProcesosElectorales(String vigencia, Integer idSistema, 
			Integer idEstado, Integer idDistrito, Integer idMunicipio, String ambitoUsuario);
	
	public List<DTOParticipacionGeneral> obtenerParticipacionesEstadoProceso(Integer idDetalle, Integer idEstado, 
			Integer idDistrito);
	
	public List<DTOMenu> generaMenuLateral(Integer idProceso, Integer idDetalle, Integer idSistema, Integer idEstado, 
			Integer idMunDto, String rol, String ambitoCaptura, String porSeccion);
	
	public String validaModuloAbierto(Integer idProceso, Integer idDetalle,
			Integer idEstado, Integer idMunDto, Integer idSistema,
			Integer idModulo, String grupo, String ambitoCaptura);
	
}
