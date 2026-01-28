package mx.ine.procprimerinsa.as;

import java.util.List;

import mx.ine.parametrizacion.model.dto.DTOMenu;

public interface ASMenuServiceInterface {

	public List<DTOMenu> generaMenuLateral(Integer idProceso, Integer idDetalle, Integer idSistema,
	        Integer idEstado, Integer idMunDto, String grupoSistema, String ambitoCaptura, 
	        String porSeccion);
	
}
