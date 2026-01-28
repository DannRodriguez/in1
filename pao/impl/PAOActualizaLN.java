package mx.ine.procprimerinsa.pao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.dao.impl.DAOGeneric;
import mx.ine.procprimerinsa.pao.PAOActualizaLNInterface;
import mx.ine.procprimerinsa.util.Constantes;

@Scope("prototype")
@Repository("paoActualizaLN")
public class PAOActualizaLN extends DAOGeneric<Serializable, Serializable> implements PAOActualizaLNInterface {

	@Override
	@Transactional
	public String ejecutaMarcadoNombreOrden(Integer idProceso, Integer idDetalle, Integer idEstado,
			Integer idDistrito) {
		ActualizacionWork actualizacion = new ActualizacionWork(idProceso, 
															idDetalle,
															idEstado,
															idDistrito,
															Constantes.STORED_PROCEDURE_LN_MARCADO_NOMBRE_ORDEN);
		
		try(Session session = openSessionLN()) {
			session.doWork(actualizacion);
			return actualizacion.getRespuesta();
		}
		
	}
	
	@Override
	@Transactional
	public String ejecutaReinicioYaEsInsaculado(Integer idProceso, Integer idDetalle, Integer idEstado,
			Integer idDistrito) {
		ActualizacionWork actualizacion = new ActualizacionWork(idProceso, 
															idDetalle,
															idEstado,
															idDistrito,
															Constantes.STORED_PROCEDURE_LN_REINICIO_YA_ES_INSACULADO);
		
		try(Session session = openSessionLN()) {
			session.doWork(actualizacion);
			return actualizacion.getRespuesta();
		}
		
	}
	
	public static class ActualizacionWork implements Work{
		
		private Integer idProceso;
		private Integer idDetalle;
		private Integer idEstado;
		private Integer idDistrito;
		private String pl;
		private String respuesta;
		
		public ActualizacionWork(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito, String pl) {
			super();
			this.idProceso = idProceso;
			this.idDetalle = idDetalle;
			this.idEstado = idEstado;
			this.idDistrito = idDistrito;
			this.pl = pl;
		}
		
		public void execute(Connection conn) throws SQLException {
			
			try(CallableStatement cs = conn.prepareCall(pl)){
				cs.setInt(1, idProceso);
				cs.setInt(2, idDetalle);
				cs.setInt(3, idEstado);
				cs.setInt(4, idDistrito);
				cs.registerOutParameter(5, Constantes.ORACLE_TYPES_VARCHAR);
				
				cs.execute();
				
				respuesta = cs.getString(5);
			}
		}

		public String getRespuesta() {
			return respuesta;
		}

		public void setRespuesta(String respuesta) {
			this.respuesta = respuesta;
		}
	}

}
