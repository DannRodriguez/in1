package mx.ine.procprimerinsa.bsd.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.parametrizacion.model.dto.DTODetalleProceso;
import mx.ine.parametrizacion.model.dto.DTOEstado;
import mx.ine.parametrizacion.model.dto.DTOMenu;
import mx.ine.procprimerinsa.as.ASEstadosDetalleInterface;
import mx.ine.procprimerinsa.as.ASMenuServiceInterface;
import mx.ine.procprimerinsa.as.ASModuloServiceInterface;
import mx.ine.procprimerinsa.as.ASParametrosInterface;
import mx.ine.procprimerinsa.as.ASProcesoDetalleInterface;
import mx.ine.procprimerinsa.bsd.BDSAdminServInterface;
import mx.ine.procprimerinsa.configuration.GeneraEstructuraProcesos;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;

@Component("bsdAdminServ")
@Scope("prototype")
public class BSDAdminServ implements BDSAdminServInterface {

	private static final long serialVersionUID = 3220543592252838871L;

	@Autowired
	@Qualifier("asParametros")
	private ASParametrosInterface asParametros;
	
	@Autowired
	@Qualifier("asEstadosDetalle")
	private ASEstadosDetalleInterface asEstadosDetalle;
	
	@Autowired
	@Qualifier("asProcesoDetalle")
	private ASProcesoDetalleInterface asProcesoDetalle;
	
	@Autowired
	@Qualifier("estructuraProcesos")
	private GeneraEstructuraProcesos estructuraProcesos;
	
	@Autowired
	@Qualifier("asMenu")
	private transient ASMenuServiceInterface asMenu;
	
	@Autowired
	@Qualifier("asModuloServ")
	private transient ASModuloServiceInterface asModuloServ;
	
	@Override
	public String obtenerParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer id, 
			Integer idParametro) throws Exception {
		return asParametros.obtenerParametro(idProceso, idDetalle, idEstado, id, idParametro);
	}
	
	@Override
	public List<DTOEstado> obtenerEstadosConProcesosVigentes(Integer idSistema, Integer idEstado, String rol) {
		return asEstadosDetalle.obtenerEstadosConProcesosVigentes(idSistema, idEstado, rol);
	}
	
	@Override
	public List<DTODetalleProceso> obtenerDetalleProcesosElectorales(String vigencia, Integer idSistema,
			Integer idEstado, Integer idDistrito, Integer idMunicipio, String ambitoUsuario) {
		return asProcesoDetalle.obtenerDetalleProcesosElectorales(vigencia, idSistema, idEstado, idDistrito,
				idMunicipio, ambitoUsuario);
	}
	
	@Override
	public List<DTOParticipacionGeneral> obtenerParticipacionesEstadoProceso(Integer idDetalle, Integer idEstado, 
			Integer idDistrito) {
		 return estructuraProcesos.getProcesos().get(idDetalle)
		 				.getEstados().get(idEstado)
		 				.getParticipaciones()
		 				.values()
		 				.stream()
		 				.filter(p -> idDistrito.equals(0) || p.getId().equals(idDistrito))
		 				.toList();
	}
	
	@Override
	public List<DTOMenu> generaMenuLateral(Integer idProceso, Integer idDetalle, Integer idSistema, Integer idEstado,
			Integer idMunDto, String rol, String ambitoCaptura, String porSeccion) {
		return asMenu.generaMenuLateral(idProceso, idDetalle, idSistema, idEstado, idMunDto, rol, ambitoCaptura,
				porSeccion);
	}

	@Override
	public String validaModuloAbierto(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idMunDto,
			Integer idSistema, Integer idModulo, String grupo, String ambitoCaptura) {
		return asModuloServ.validaModuloAbierto(idProceso, idDetalle, idEstado,idMunDto, idSistema, 
				idModulo, grupo, ambitoCaptura);
	}
	
}
