package mx.ine.procprimerinsa.as.impl;

import java.util.List;

import mx.ine.parametrizacion.model.dto.DTODetalleProceso;
import mx.ine.procprimerinsa.as.ASProcesoDetalleInterface;
import mx.ine.procprimerinsa.dao.DAOProcesosInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("prototype")
@Service("asProcesoDetalle")
public class ASProcesoDetalle implements ASProcesoDetalleInterface {

	private static final long serialVersionUID = -3631838404735585766L;
	
	@Autowired
	@Qualifier("daoProceso")
	private transient DAOProcesosInterface daoProceso;
	
	@Override
	public List<DTODetalleProceso> obtenerDetalleProcesosElectorales(String vigencia, Integer idSistema, 
			Integer idEstado, Integer idDistrito, Integer idMunicipio, String ambitoUsuario) {
		return daoProceso.getProcesosDetalle(vigencia, idSistema, idEstado, idDistrito, 
									idMunicipio, ambitoUsuario);
	}
}