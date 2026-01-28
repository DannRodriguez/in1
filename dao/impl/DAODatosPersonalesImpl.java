package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.procprimerinsa.dao.DAODatosPersonalesInterface;
import mx.ine.procprimerinsa.dto.DTOArchivoDatosPersonales;
import mx.ine.procprimerinsa.dto.db.DTOEstatusDatosPersonales;

@Repository("daoDatosPersonalesImpl")
@Scope("prototype")
public class DAODatosPersonalesImpl extends DAOGeneric<DTOEstatusDatosPersonales, Serializable> implements DAODatosPersonalesInterface {

	private static final String ID_PROCESO = "idProcesoElectoral";
	private static final String ID_DETALLE = "idDetalleProceso";
	private static final String ID_ESTADO = "idEstado";
	private static final String ID_CORTE = "idCorte";
	
	@Override
	public DTOEstatusDatosPersonales obtenerEstatusDatosPersonales(Integer idProceso, 
			Integer idDetalle, Integer idEstado) {
		Session session = getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOEstatusDatosPersonales> criteria = builder.createQuery(DTOEstatusDatosPersonales.class);
		Root<DTOEstatusDatosPersonales> root = criteria.from(DTOEstatusDatosPersonales.class);
		Predicate[] predicates = new Predicate[3];
		predicates[0] = builder.equal(root.get(ID_PROCESO), idProceso);
		predicates[1] = builder.equal(root.get(ID_DETALLE), idDetalle);
		predicates[2] = builder.equal(root.get(ID_ESTADO), idEstado);
		
		criteria.select(root).where(predicates);
		
		Query<DTOEstatusDatosPersonales> query = session.createQuery(criteria);
		return query.uniqueResult();
	}
	
	@Override
	public List<DTOArchivoDatosPersonales> obtenerListaEstatusDatosPersonales(Integer idProceso, 
			Integer idDetalle, Integer idCorte) {
		List<DTOArchivoDatosPersonales> estados = new ArrayList<>();
		DTOArchivoDatosPersonales estado;
		
		List<Object[]> lista = getSessionReportes().createNativeQuery(getContainer().getQuery("query_datosPersonales_listaEstatus"),
																	Object[].class)
													.setParameter(ID_PROCESO, idProceso)
													.setParameter(ID_DETALLE, idDetalle)
													.setParameter(ID_CORTE, idCorte)
													.list();
		
		for(Object[] o : lista) {
			estado = new DTOArchivoDatosPersonales();
			estado.setIdProceso(Integer.parseInt(o[0].toString()));
			estado.setIdDetalle(Integer.parseInt(o[1].toString()));
			estado.setIdEstado(Integer.parseInt(o[2].toString()));
			estado.setNombreEstado(o[3].toString());
			estado.setIdHorario(Integer.parseInt(o[4].toString()));
			estado.setFinalizoProcesoInsaculacion(Integer.parseInt(o[5].toString()) == 0);
			estado.setIdAccion((Character)o[6]);
			estado.setEstatus(o[7] != null ? o[7].toString() : null);
			estado.setCiudadanosDescarga(Integer.parseInt(o[8].toString()));
			estado.setCiudadanosCarga(Integer.parseInt(o[9].toString()));
			estado.setUsuario(o[10].toString());
			estado.setFechaHora((Date)o[11]);
			estados.add(estado);
		}
		
		return estados;
	}

	@Override
	public void actualizarEstatusDatosPersonales(DTOEstatusDatosPersonales estatusDatosPersonales) {
		getSession().merge(estatusDatosPersonales);
	}

}
