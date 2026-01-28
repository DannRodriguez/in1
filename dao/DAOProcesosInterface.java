package mx.ine.procprimerinsa.dao;

import java.util.List;

import mx.ine.parametrizacion.model.dto.DTODetalleProceso;

public interface DAOProcesosInterface {

	public List<DTODetalleProceso> getProcesosDetalle(String vigencia, Integer idSistema,
			Integer idEstado, Integer idDistrito, Integer idMunicipio, String ambitoUsuario);
	
}
