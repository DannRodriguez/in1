package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.dao.DAOResultados1AInsaInterface;
import mx.ine.procprimerinsa.dto.db.DTOResultados1aInsa;

@Scope("prototype")
@Service("daoResultadosInsaculacion")
public class DAOResultados1AInsa extends DAOGeneric<DTOResultados1aInsa, Serializable> implements DAOResultados1AInsaInterface {

	private static final String ID_DETALLE = "idDetalle";
	private static final String ID_PARTICIPACION = "idParticipacion";
	private static final String ID_TIPO_VOTO = "idTipoVoto";
	private static final String OFFSET = "offset";
	private static final String LIMITE = "limite";

	@Override
	public List<DTOResultados1aInsa> getResultadoInsaculacionPorParticipacion(Integer idDetalleProceso, 
			Integer idParticipacion, Integer idTipoVoto) {
		Session session = getSessionReportes();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<DTOResultados1aInsa> criteria = builder.createQuery(DTOResultados1aInsa.class);
		Root<DTOResultados1aInsa> root = criteria.from(DTOResultados1aInsa.class);
		Predicate[] predicates = new Predicate[3];
		predicates[0] = builder.equal(root.get("idDetalleProceso"), idDetalleProceso);
		predicates[1] = builder.equal(root.get(ID_PARTICIPACION), idParticipacion);
		predicates[2] = builder.equal(root.get(ID_TIPO_VOTO), idTipoVoto);
		
		criteria.orderBy(builder.asc(root.get("seccion")), 
						builder.asc(root.get("tipoCasilla")),
						builder.asc(root.get("idCasilla")));
		criteria.select(root).where(predicates);
		
		Query<DTOResultados1aInsa> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public Queue<DTOResultados1aInsa> getResultadoInsaculacionPorParticipacionPaginado(Integer idDetalleProceso, 
			Integer idParticipacion, Integer idTipoVoto, Integer offset, Integer limite) {
		Session session = getSessionReportes();
		Queue<DTOResultados1aInsa> resultados = new LinkedList<>();
		DTOResultados1aInsa resultado;
		
		List<Object[]> list = session.createNativeQuery(getContainer().getQuery("query_procesoInsaculacion_obtieneResultadosPaginadosPorTipoVoto"), 
														Object[].class)
									.setParameter(ID_DETALLE, idDetalleProceso)
									.setParameter(ID_PARTICIPACION, idParticipacion)
									.setParameter(ID_TIPO_VOTO, idTipoVoto)
									.setParameter(OFFSET, offset)
									.setParameter(LIMITE, limite)
									.stream()
									.collect(Collectors.toCollection(LinkedList::new));
		
		for(Object[] o : list) {
			resultado = new DTOResultados1aInsa();
			resultado.setIdProcesoElectoral(o[0] != null ? Integer.parseInt(o[0].toString()) : null);
			resultado.setIdDetalleProceso(Integer.parseInt(o[1].toString()));
			resultado.setIdParticipacion(Integer.parseInt(o[2].toString()));
			resultado.setSeccion(Integer.parseInt(o[3].toString()));
			resultado.setIdCasilla(Integer.parseInt(o[4].toString()));
			resultado.setTipoCasilla(o[5].toString());
			resultado.setExtContigua(o[6] != null ? Integer.parseInt(o[6].toString()) : null);
			resultado.setIdTipoVoto(Integer.parseInt(o[7].toString()));
			resultado.setListaNominal(Integer.parseInt(o[8].toString()));
			resultado.setEnero(Integer.parseInt(o[9].toString()));
			resultado.setFebrero(Integer.parseInt(o[10].toString()));
			resultado.setMarzo(Integer.parseInt(o[11].toString()));
			resultado.setAbril(Integer.parseInt(o[12].toString()));
			resultado.setMayo(Integer.parseInt(o[13].toString()));
			resultado.setJunio(Integer.parseInt(o[14].toString()));
			resultado.setJulio(Integer.parseInt(o[15].toString()));
			resultado.setAgosto(Integer.parseInt(o[16].toString()));
			resultado.setSeptiembre(Integer.parseInt(o[17].toString()));
			resultado.setOctubre(Integer.parseInt(o[18].toString()));
			resultado.setNoviembre(Integer.parseInt(o[19].toString()));
			resultado.setDiciembre(Integer.parseInt(o[20].toString()));
			resultado.setHombres(Integer.parseInt(o[21].toString()));
			resultado.setMujeres(Integer.parseInt(o[22].toString()));
			resultado.setNoBinario(Integer.parseInt(o[23].toString()));
			resultado.setPorcentajeHombres(Integer.parseInt(o[24].toString()));
			resultado.setPorcentajeMujeres(Integer.parseInt(o[25].toString()));
			resultado.setPorcentajeNoBinarios(Integer.parseInt(o[26].toString()));
			resultado.setInsaculados(Integer.parseInt(o[27].toString()));
			
			resultados.add(resultado);
		}
		
		return resultados;
		
	}

	@Override
	@Transactional
	public void guardar(DTOResultados1aInsa resultado) {
		getSession().persist(resultado);
		getSession().flush();
	}

}
