package mx.ine.procprimerinsa.dao.impl;

import java.util.Collections;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.parametrizacion.controller.GeograficoController;
import mx.ine.parametrizacion.model.dto.DTODetalleProceso;
import mx.ine.parametrizacion.model.request.ModelRequestProcesos;
import mx.ine.procprimerinsa.dao.DAOProcesosInterface;

@Scope("prototype")
@Repository("daoProceso")
public class DAOProceso implements DAOProcesosInterface {

	private static final Logger logger = Logger.getLogger(DAOProceso.class);

	@Autowired
	private GeograficoController geograficoController;
	
	@Override
	public List<DTODetalleProceso> getProcesosDetalle(String vigencia, Integer idSistema,
			Integer idEstado, Integer idDistrito, Integer idMunicipio, String ambitoUsuario) {

		try {
			ModelRequestProcesos parametros = new ModelRequestProcesos();
			parametros.setIdSistema(idSistema);
			parametros.setIdEstado(idEstado);
			parametros.setIdDistrito(idDistrito);
			parametros.setIdMunicipio(null);
			parametros.setAmbitoUsuario(ambitoUsuario);
			parametros.setVigente("A");
			parametros.setJndi("PARAMINSA1jndi");
			return geograficoController.obtieneProcesos(parametros);
		} catch(Exception e) {
			logger.error("ERROR DAOProceso -getProcesosDetalle: ", e);
			return Collections.emptyList();
		}
	}
}
