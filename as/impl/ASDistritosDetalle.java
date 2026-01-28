package mx.ine.procprimerinsa.as.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASDistritosDetalleInterface;
import mx.ine.procprimerinsa.dao.DAODistritosDetalleInterface;
import mx.ine.procprimerinsa.dao.DAOParticipacionGeograficaInterface;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.db.DTOParticipacionGeografica;
import mx.ine.procprimerinsa.util.Utilidades;

@Scope("prototype")
@Repository("asDistritosDetalle")
public class ASDistritosDetalle implements ASDistritosDetalleInterface, Serializable {

	private static final long serialVersionUID = 8323801840836745221L;
	
	@Autowired
	@Qualifier("daoDistritosDetalle")
	private transient DAODistritosDetalleInterface daoDistritosDetalle;
	
	@Autowired
	@Qualifier("daoParticipacionGeografica")
	private transient DAOParticipacionGeograficaInterface daoParticipacionGeografica;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public List<DTOParticipacionGeneral> obtenerParticipacionesEstadoProceso(Integer idSistema, Integer idProceso,
			Integer idDetalle, Integer idEstado, Integer idDistrito, Integer idMunicipio, String ambitoUsuario,
			String tipoCapturaSistema) {
		Map<Integer, DTOParticipacionGeografica> participaciones = daoParticipacionGeografica
																		.consultaParticipacionesEstadoProceso(idProceso,
																											idDetalle, 
																											idEstado);
		
		return Utilidades.collectionToStream(daoDistritosDetalle.getDistritosByProcesoDetalleEstado(
																			idSistema,
																			idProceso, 
																			idDetalle, 
																			idEstado, 
																			idDistrito))
				.map(d -> {
					if(!participaciones.containsKey(d.getIdDistrito())) return null;
					DTOParticipacionGeneral participacion = new DTOParticipacionGeneral();
					participacion.setId(d.getIdDistrito());
					participacion.setNombre(StringUtils.stripAccents(d.getNombreDistrito()));
					participacion.setIdParticipacion(participaciones.get(d.getIdDistrito()).getIdParticipacion());
					return participacion;
				})
				.filter(Objects::nonNull)
				.toList();
	}

}
