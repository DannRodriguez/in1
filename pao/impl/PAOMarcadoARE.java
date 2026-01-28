package mx.ine.procprimerinsa.pao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import mx.ine.procprimerinsa.dao.impl.DAOGeneric;
import mx.ine.procprimerinsa.pao.PAOMarcadoAREInterface;
import mx.ine.procprimerinsa.util.Constantes;

@Scope("prototype")
@Repository("paoMarcadoARE")
public class PAOMarcadoARE extends DAOGeneric<Serializable, Serializable> implements PAOMarcadoAREInterface {
	
	@Override
	public String ejecutaCargaARES(Integer idDetalle, String usuario) {
		CargaWork carga = new CargaWork(idDetalle,
									usuario,
									Constantes.STORED_PROCEDURE_EJECUTA_CARGA_ARE);
		
		Session session = getSession();
		session.doWork(carga);
		return carga.getRespuesta();
	}

	@Override
	public String ejecutaActualizacionARES(Integer idProceso, Integer idDetalle, Integer idParticipacion, String usuario) {
		ActualizacionWork actualizacion = new ActualizacionWork(idProceso,
															idDetalle,
															idParticipacion,
															usuario,
															Constantes.STORED_PROCEDURE_EJECUTA_ACTUALIZACION_ARE);
		
		Session session = getSession();
		session.doWork(actualizacion);
		return actualizacion.getRespuesta();
	}
	
	public static class CargaWork implements Work {
		
		private Integer idDetalle;
		private String usuario;
		private String pl;
		private String respuesta;
		
		public CargaWork(Integer idDetalle, String usuario, String pl) {
			super();
			this.idDetalle = idDetalle;
			this.usuario = usuario;
			this.pl = pl;
		}
		
		public void execute(Connection conn) throws SQLException {
			
			try(CallableStatement cs = conn.prepareCall(pl)) {
				cs.setInt(1, idDetalle);
				cs.setString(2, usuario);
				cs.registerOutParameter(3, Constantes.ORACLE_TYPES_VARCHAR);
				
				cs.execute();
				
				respuesta = cs.getString(3);
			}
		}

		public String getRespuesta() {
			return respuesta;
		}

		public void setRespuesta(String respuesta) {
			this.respuesta = respuesta;
		}
	}

	public static class ActualizacionWork implements Work {
		
		private Integer idProceso;
		private Integer idDetalle;
		private Integer idParticipacion;
		private String usuario;
		private String pl;
		private String respuesta;
		
		public ActualizacionWork(Integer idProceso, Integer idDetalle, Integer idParticipacion, String usuario, String pl) {
			super();
			this.idProceso = idProceso;
			this.idDetalle = idDetalle;
			this.idParticipacion = idParticipacion;
			this.usuario = usuario;
			this.pl = pl;
		}
		
		public void execute(Connection conn) throws SQLException {
			
			try(CallableStatement cs = conn.prepareCall(pl)) {
				cs.setInt(1, idProceso);
				cs.setInt(2, idDetalle);
				cs.setInt(3, idParticipacion);
				cs.setString(4, usuario);
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
