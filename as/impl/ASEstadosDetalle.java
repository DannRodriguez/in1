package mx.ine.procprimerinsa.as.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.parametrizacion.model.dto.DTOEstado;
import mx.ine.procprimerinsa.as.ASEstadosDetalleInterface;
import mx.ine.procprimerinsa.dao.DAOEstadosDetalleInterface;
import mx.ine.procprimerinsa.util.Utilidades;

@Scope("prototype")
@Service("asEstadosDetalle")
public class ASEstadosDetalle implements ASEstadosDetalleInterface, Serializable {
	
	private static final long serialVersionUID = 631895685818629863L;
	
	@Autowired
	@Qualifier("daoEstadosDetalle")
	private transient DAOEstadosDetalleInterface daoEstadosDetalle;
	
	@Override
	public List<DTOEstado> obtenerEstadosPorProceso(Integer idSistema, Integer idProceso, 
			Integer idDetalle, Integer idEstado) {
		return daoEstadosDetalle.obtenerEstadosPorProceso(idSistema, idProceso, idDetalle, idEstado);
	}
	
	@Override
	public List<DTOEstado> obtenerEstadosConProcesosVigentes(Integer idSistema, Integer idEstado, String rol) {
		List<DTOEstado> estados = daoEstadosDetalle.obtenerEstadosConProcesosVigentes(idSistema, 
																					idEstado);
		if(Utilidades.isRolOC(rol) 
			&& !estados.isEmpty()
			&& estados.get(0).getIdEstado() != null
			&& !estados.get(0).getIdEstado().equals(0)) {
			estados.add(0, new DTOEstado(0, "OFICINAS CENTRALES"));
		}
		
		return estados;
	}

}
