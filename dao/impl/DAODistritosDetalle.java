package mx.ine.procprimerinsa.dao.impl;

import java.util.Collections;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.parametrizacion.controller.GeograficoController;
import mx.ine.parametrizacion.model.dto.DTODistrito;
import mx.ine.parametrizacion.model.request.ModelRequestDistritos;
import mx.ine.procprimerinsa.dao.DAODistritosDetalleInterface;

@Scope("prototype")
@Repository("daoDistritosDetalle")
public class DAODistritosDetalle implements DAODistritosDetalleInterface {

	private static final Logger logger = Logger.getLogger(DAODistritosDetalle.class);

	@Autowired
	private GeograficoController geograficoController;

	@Override
	public List<DTODistrito> getDistritosByProcesoDetalleEstado(Integer idSistema, Integer idProceso,
			Integer idDetalle, Integer idEstado, Integer idDistrito) {

		try {

			ModelRequestDistritos parametros = new ModelRequestDistritos();
			parametros.setIdSistema(idSistema);
			parametros.setIdProceso(idProceso);
			parametros.setIdDetalle(idDetalle);
			parametros.setIdEstado(idEstado);
			parametros.setIdDistrito(idDistrito);
			parametros.setJndi("PARAMINSA1jndi");
			return geograficoController.obtieneDistritosFederales(parametros);
		} catch (Exception e) {
			logger.error("ERROR DAODistritosDetalle -getDistritosByProcesoDetalleEstado: ", e);
			return Collections.emptyList();
		}
	}

}
