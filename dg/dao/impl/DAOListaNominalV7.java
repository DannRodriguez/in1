package mx.ine.procprimerinsa.dg.dao.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import org.jboss.logging.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.dao.impl.DAOGeneric;
import mx.ine.procprimerinsa.dg.dao.DAOListaNominalV7Interface;
import mx.ine.procprimerinsa.dg.dto.DTOListaNominalDG;
import mx.ine.procprimerinsa.util.Constantes;

@Repository("daoListaNominalV7")
@Scope("prototype")
public class DAOListaNominalV7 extends DAOGeneric<DTOListaNominalDG, Serializable> implements DAOListaNominalV7Interface {
	
	private static final Logger logger = Logger.getLogger(DAOListaNominalV7.class);
	
	private static final String ID_CORTE_LN = "idCorteLN";
	private static final String ID_PROCESO = "idProceso";
	private static final String ID_DETALLE = "idDetalle";
	private static final String ID_ESTADO = "idEstado";
	private static final String ID_DISTRITO = "idDistrito";
	private static final String SECCION = "seccion";
	private static final String TIPO_CASILLA = "tipoCasilla";
	private static final String ID_CASILLA = "idCasilla";
	private static final String MES_INSACULAR = "mesInsacular";
	private static final String LETRA_INSACULAR = "letraInsacular";
	private static final String VALIDA_YA_ES_INSACULADO = "validaYaEsInsaculado";
	private static final String MESES_YA_SORTEADOS = "mesesYaSorteados";
	private static final String CONSIDERA_EXTRAORDINARIAS = "consideraExtraordinarias";
	private static final String OFFSET = "offset";
	private static final String LIMITE = "limite";

	@Override
	@Transactional
	public Integer getCorteLNActivo(Integer idProceso, Integer idDetalle) {
		try(Session session = openSessionLN()) {
			return session.createNativeQuery(getContainer().getQuery("query_listaNominal_obtieneCorteLNActivo"),
											BigDecimal.class)
						  .setParameter(ID_PROCESO, idProceso)
						  .setParameter(ID_DETALLE, idDetalle)
						  .getSingleResult().intValue();
		}
	}

	@Override
	@Transactional
	public List<Object[]> getSeccionesCasillasAInsacularPorDistrito(Integer idCorteLN, Integer idEstado, 
			Integer idDistrito, Integer consideraExtraordinarias) {
		try(Session session = openSessionLN()) {
			return session.createNativeQuery(getContainer().getQuery(
																consideraExtraordinarias.equals(0) ? 
																		"query_listaNominal_obtieneSeccionesAInsacularPorDistrito"
																		: "query_listaNominal_obtieneSeccionesCasillasAInsacularPorDistrito"),
											Object[].class)
						  .setParameter(ID_CORTE_LN, idCorteLN)
						  .setParameter(ID_ESTADO, idEstado)
						  .setParameter(ID_DISTRITO, idDistrito)
						  .list();
		}
	}
	
	@Override
	@Transactional
	public Integer getTotalListaNominalPorDistrito(Integer idCorteLN, Integer idEstado, Integer idDistrito) {
		try(Session session = openSessionLN()) {
			return session.createNativeQuery(getContainer().getQuery("query_listaNominal_obtieneTotalListaNominalPorDistrito"),
											BigDecimal.class)
						.setParameter(ID_CORTE_LN, idCorteLN)
						.setParameter(ID_ESTADO, idEstado)
						.setParameter(ID_DISTRITO, idDistrito)
						.getSingleResult().intValue();
		}
	}
	
	@Override
	public Queue<DTOListaNominalDG> getInsaculadosPorDistritoSeccionCasilla(Integer idCorteLN, Integer idEstado, Integer idDistrito,
			Integer seccion, String tipoCasilla, Integer idCasilla, Integer mesInsacular, String letraInsacular, Integer validaYaEsInsaculado,
			List<Integer> mesesYaSorteados,  Integer consideraExtraordinarias, Integer offset, Integer limite) {
		Queue<DTOListaNominalDG> insaculados = new LinkedList<>();
		DTOListaNominalDG insaculado;
		
		try(Session session = openSessionLN()) {

			List<Object[]> list = session.createNativeQuery(getContainer().getQuery("query_listaNominal_obtieneInsaculadosPorDistritoSeccionCasilla"),
															Object[].class)
											.setParameter(ID_CORTE_LN, idCorteLN)
											.setParameter(ID_ESTADO, idEstado)
											.setParameter(ID_DISTRITO, idDistrito)
											.setParameter(SECCION, seccion)
											.setParameter(TIPO_CASILLA, tipoCasilla)
											.setParameter(ID_CASILLA, idCasilla)
											.setParameter(MES_INSACULAR, mesInsacular)
											.setParameter(LETRA_INSACULAR, letraInsacular)
											.setParameter(VALIDA_YA_ES_INSACULADO, validaYaEsInsaculado)
											.setParameterList(MESES_YA_SORTEADOS, mesesYaSorteados)
											.setParameter(CONSIDERA_EXTRAORDINARIAS, consideraExtraordinarias)
											.setParameter(OFFSET, offset)
											.setParameter(LIMITE, limite)
											.list();
			for(Object[] o : list) {
				insaculado = new DTOListaNominalDG();
				insaculado.setIdCiudadano(o[0] != null ? Integer.parseInt(o[0].toString()) : null);
				insaculado.setNumeroCredencialElector(o[1].toString());
				insaculado.setApellidoPaterno(o[2] != null ? o[2].toString() : null);
				insaculado.setApellidoMaterno(o[3] != null ? o[3].toString() : null);
				insaculado.setNombre(o[4].toString());
				insaculado.setIdEstado(Integer.parseInt(o[5].toString()));
				insaculado.setIdDistrito(Integer.parseInt(o[6].toString()));
				insaculado.setSeccion(Integer.parseInt(o[7].toString()));
				insaculado.setIdCasilla(Integer.parseInt(o[8].toString()));
				insaculado.setTipoCasilla(o[9].toString());
				insaculado.setIdMunicipio(Integer.parseInt(o[10].toString()));
				insaculado.setIdLocalidad(Integer.parseInt(o[11].toString()));
				insaculado.setManzana(Integer.parseInt(o[12].toString()));
				insaculado.setEdad(Integer.parseInt(o[13].toString()));
				insaculado.setNombreOrden(o[14] != null ? o[14].toString() : null);
				insaculado.setMesNacimiento(Integer.parseInt(o[15].toString()));
				insaculado.setSexo(o[16].toString());
				insaculado.setEntidadNacimiento(o[17].toString());
				insaculado.setIdDistritoLocal(o[18] != null ? Integer.parseInt(o[18].toString()) : null);
		        insaculado.setMesOrden(o[19] != null ? Integer.parseInt(o[19].toString()) : null);
		        insaculado.setLetraOrden(o[20] != null ? Integer.parseInt(o[20].toString()) : null);
		        insaculado.setServidorPublico(Integer.parseInt(o[21].toString()));
		        insaculado.setIdCircuitoJudicial(o[22] != null ? Integer.parseInt(o[22].toString()) : null);
		        insaculado.setIdDistritoJudicial(o[23] != null ? Integer.parseInt(o[23].toString()) : null);
		        
				insaculados.add(insaculado);
			}
			
			return insaculados;

		}
		
	}
	
	@Override
	public void generaSpool(String ruta, String[] header, String query) throws Exception {
		try (Session session = openSessionLN();
			FileWriter fstream = new FileWriter(ruta, StandardCharsets.ISO_8859_1);
			BufferedWriter writer =  new BufferedWriter(fstream);) {
			
			StringBuilder fila = new StringBuilder();
			Arrays.stream(header).forEach(h -> fila.append(h).append('|'));
			fila.deleteCharAt(fila.length() - 1);
			fila.append(Constantes.DEFAULT_LINE_SEPARATOR);
			writer.write(fila.toString());
			
			session.doWork(new Work() {
			    @Override
			    public void execute(Connection connection) throws SQLException {
			    	try(Statement stmt = connection.createStatement(
						                            ResultSet.TYPE_FORWARD_ONLY,
						                            ResultSet.CONCUR_READ_ONLY)) {
			    		
			    		ResultSet resultSet = stmt.executeQuery(query);
			    		int columnCount = resultSet.getMetaData().getColumnCount();
			    		
			    		while (resultSet.next()) {
			    		    for (int i = 1; i <= columnCount; i++) {
			    		    	final Object object = resultSet.getObject(i);
			    		    	String stringData = Objects.toString(object, null);
			    		    	writer.write(stringData);
			    		    	if(i<columnCount) writer.write('|');
			    		    }
			    		    writer.write(Constantes.DEFAULT_LINE_SEPARATOR);
			    		}
			    		
			    	} catch (IOException e) {
						logger.error("ERROR DAOListaNominalV7 -generaSpool: ", e);
					}
			    }
			});
		}
	}

}
