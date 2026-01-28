package mx.ine.procprimerinsa.as.impl;

import java.util.Collections;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.parametrizacion.controller.MenuController;
import mx.ine.parametrizacion.model.dto.DTOMenu;
import mx.ine.parametrizacion.model.request.ModelRequestMenu;
import mx.ine.procprimerinsa.as.ASMenuServiceInterface;

@Scope("prototype")
@Component("asMenu")
public class ASMenuService implements ASMenuServiceInterface {

	private static final Logger logger = Logger.getLogger(ASMenuService.class);
	
	@Autowired
	private MenuController menuController;

	@Override
	public List<DTOMenu> generaMenuLateral(Integer idProceso, Integer idDetalle, Integer idSistema, Integer idEstado,
			Integer idMunDto, String grupoSistema, String ambitoCaptura, String porSeccion) {
		try {
			ModelRequestMenu parametros = new ModelRequestMenu();
			parametros.setIdSistema(idSistema);
			parametros.setIdProceso(idProceso);
			parametros.setIdDetalle(idDetalle);
			parametros.setIdEstado(idEstado);
			parametros.setIdDistrito(idMunDto != null ? idMunDto : 0);
			parametros.setIdMunicipio(null);
			parametros.setGrupoSistema(grupoSistema);
			parametros.setJndi("MENUINSA1jndi");
			return menuController.obtieneMenuLateral(parametros);
		} catch (Exception e) {
			logger.error("ERROR ASMenuService -generaMenuLateral: ", e);
		}
		return Collections.emptyList();
	}

}
