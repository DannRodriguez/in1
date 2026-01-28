package mx.ine.procprimerinsa.as.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.parametrizacion.controller.EstatusController;
import mx.ine.parametrizacion.model.request.ModelRequestModulo;
import mx.ine.procprimerinsa.as.ASModuloServiceInterface;

@Scope("prototype")
@Component("asModuloServ")
public class ASModuloService implements ASModuloServiceInterface {

	private static final Logger logger = Logger.getLogger(ASModuloService.class);

	@Autowired
	private EstatusController estatusController;

	@Override
	public String validaModuloAbierto(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idMunDto,
			Integer idSistema, Integer idModulo, String grupo, String ambitoCaptura) {
		
		try {
			ModelRequestModulo parametros = new ModelRequestModulo();
			parametros.setIdSistema(idSistema);
			parametros.setIdProceso(idProceso);
			parametros.setIdDetalle(idDetalle);
			parametros.setIdEstado(idEstado);
			parametros.setIdDistrito(idMunDto != null ? idMunDto : 0);
			parametros.setIdMunicipio(null);
			parametros.setGrupoSistema(grupo);
			parametros.setIdModulo(idModulo);
			parametros.setJndi("ESTATUSINSA1jndi");
			return estatusController.obtieneEstatusModulo(parametros);
		} catch (Exception e) {
			logger.error("ERROR ASModuloService -validaModuloAbierto -" + idModulo + ": ", e);
		}
		return "";
	}

}
