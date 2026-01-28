package mx.ine.procprimerinsa.dao.impl;

import java.util.Collections;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.parametrizacion.controller.GeograficoController;
import mx.ine.parametrizacion.model.dto.DTOEstado;
import mx.ine.parametrizacion.model.request.ModelRequestEstados;
import mx.ine.parametrizacion.model.request.ModelRequestEstadosConProcesosVigentes;
import mx.ine.procprimerinsa.dao.DAOEstadosDetalleInterface;

@Scope("prototype")
@Repository("daoEstadosDetalle")
public class DAOEstadosDetalle implements DAOEstadosDetalleInterface {

	private static final Logger logger = Logger.getLogger(DAOEstadosDetalle.class);

	@Autowired
	private GeograficoController geograficoController;
	
	@Override
	public List<DTOEstado> obtenerEstadosPorProceso(Integer idSistema, Integer idProceso, 
			Integer idDetalle, Integer idEstado) {
		try {
			ModelRequestEstados parametros = new ModelRequestEstados();
			parametros.setIdSistema(idSistema);
			parametros.setIdProceso(idProceso);
			parametros.setIdDetalle(idDetalle);
			parametros.setIdEstado(idEstado);
			parametros.setJndi("PARAMINSA1jndi");
			return geograficoController.obtieneEstados(parametros);
		} catch (Exception e) {
			logger.error("ERROR DAOEstadosDetalle -obtenerEstadosPorProceso: ", e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<DTOEstado> obtenerEstadosConProcesosVigentes(Integer idSistema, Integer idEstado) {
		try {
			ModelRequestEstadosConProcesosVigentes parametros = new ModelRequestEstadosConProcesosVigentes();
			parametros.setIdSistema(idSistema);
			parametros.setIdEstado(idEstado);
			parametros.setJndi("PARAMINSA1jndi");
			return geograficoController.obtieneEstadosConProcesosVigentes(parametros);
		} catch (Exception e) {
			logger.error("ERROR DAOEstadosDetalle -obtenerEstadosConProcesosVigentes: ", e);
			return Collections.emptyList();
		}
	}

}
