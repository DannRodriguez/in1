package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.dao.DAODatosInsaculadosInterface;
import mx.ine.procprimerinsa.dto.db.DTODatosInsaculados;

@Repository("daoDatosInsaculados")
@Scope("prototype")
public class DAODatosInsaculados extends DAOGeneric<DTODatosInsaculados, Serializable> implements DAODatosInsaculadosInterface {

	private static final String REPLACE_SUBPARTITION = "REPLACEPORSUBPARTITION";
	private static final String ID_PROCESO = "idProceso";
	private static final String ID_DETALLE = "idDetalle";
	private static final String ID_ESTADO = "idEstado";
	private static final String ID_PARTICIPACION = "idParticipacion";

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void truncatePartitionDatosInsaculados(Integer idDetalleProcesoElectoral, Integer idParticipacion) {
		StringBuilder particion = new StringBuilder("DATOS_INSA_")
												.append("D").append(idDetalleProcesoElectoral)
												.append("P").append(String.format("%03d", idParticipacion));
		
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_reinicioProcesoInsaculacion_truncadoDatosInsaculadosPorSubParticion")
														.replace(REPLACE_SUBPARTITION, particion.toString()))
				.executeUpdate();
		session.flush();
	}
	
	@Override
	public void eliminaResultadosInsa(Integer idDetalleProceso, Integer idParticipacion) {
		Session session = getSession();
		session.createNativeMutationQuery(getContainer().getQuery("query_reinicioProcesoInsaculacion_eliminaResultadosInsa"))
				.setParameter(ID_DETALLE, idDetalleProceso)
				.setParameter(ID_PARTICIPACION, idParticipacion)
				.executeUpdate();
		session.flush();
	}

	@Override
	public Queue<DTODatosInsaculados> obtenerListaInsaculados(Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idParticipacion, Integer idTipoVoto, Integer tipoIntegracion, String letra, 
			Integer maxResults, Long offset) {
		Queue<DTODatosInsaculados> insaculados = new LinkedList<>();
		DTODatosInsaculados insaculado;

		List<Object[]> list = getSessionReportes().createNativeQuery(getContainer().getQuery("query_procesoInsaculacion_obtieneResultadosListadoInsaculadosPorTipoVoto"),
																	Object[].class)
												.setParameter(ID_PROCESO, idProcesoElectoral)
												.setParameter(ID_DETALLE, idDetalleProceso)
												.setParameter(ID_PARTICIPACION, idParticipacion)
												.setParameter("idTipoVoto", idTipoVoto)
												.setParameter("tipoIntegracion", tipoIntegracion)
												.setParameter("offset", offset.intValue())
												.setParameter("maxResults", maxResults)
												.setParameter("letraInsacular", letra)
												.list();
		
		for(Object[] o : list) {
			insaculado = new DTODatosInsaculados();
			insaculado.setSeccion(Integer.parseInt(o[0].toString()));
			insaculado.setCasilla(o[1].toString());
			insaculado.setApellidoPaterno(o[2]!=null?o[2].toString():" ");
			insaculado.setApellidoMaterno(o[3]!=null?o[3].toString():" ");
			insaculado.setNombre(o[4].toString());
			insaculado.setServidorPublico(Integer.parseInt(o[5].toString()));
			insaculados.add(insaculado);
		}
		
		return insaculados;
	}
	
	@Override
	@Transactional
	public Integer contarCiudadanosInsaculadosPorEstado(Integer idDetalleProceso, Integer idEstado) {
		try (Session session = openSessionReportes()) {
			return session.createNativeQuery(getContainer().getQuery("query_datosPersonales_obtenerNumeroInsaculadosPorEstado"),
											BigDecimal.class)
							.setParameter(ID_DETALLE, idDetalleProceso)
							.setParameter(ID_ESTADO, idEstado).getSingleResult().intValue();
		} catch(Exception e) {
			return 0;
		}
	}
	
}
