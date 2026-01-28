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
import mx.ine.procprimerinsa.pao.PAOCargaPrimeraCapaInterface;
import mx.ine.procprimerinsa.util.Constantes;

@Scope("prototype")
@Repository("paoCargaPrimeraCapa")
public class PAOCargaPrimeraCapa extends DAOGeneric<Serializable, Serializable> implements PAOCargaPrimeraCapaInterface {

	@Override
	public String ejecutaCargaPrimeraCapa(Integer idProceso, Integer idDetalle, Integer idParticipacion,
			String participacion, String usuario) {
		CargaWork carga = new CargaWork(idProceso,
										idDetalle,
										idParticipacion,
										participacion,
										usuario,
										Constantes.STORED_PROCEDURE_CARGA_PRIMERA_CAPA);
		
		Session session = getSession();
		session.doWork(carga);
		return carga.getRespuesta();
	}
	
	public static class CargaWork implements Work {
		
		private Integer idProceso;
		private Integer idDetalle;
		private Integer idParticipacion;
		private String participacion;
		private String usuario;
		private String pl;
		private String respuesta;
		
		public CargaWork(Integer idProceso, Integer idDetalle, Integer idParticipacion, String participacion, String usuario,
			String pl) {
			super();
			this.idProceso = idProceso;
			this.idDetalle = idDetalle;
			this.idParticipacion = idParticipacion;
			this.participacion = participacion;
			this.usuario = usuario;
			this.pl = pl;
		}
		
		public void execute(Connection conn) throws SQLException {
			
			try(CallableStatement cs = conn.prepareCall(pl)) {
				cs.setInt(1, idProceso);
				cs.setInt(2, idDetalle);
				cs.setInt(3, idParticipacion);
				cs.setString(4, participacion);
				cs.setString(5, usuario);
				cs.registerOutParameter(6, Constantes.ORACLE_TYPES_VARCHAR);
				
				cs.execute();
				
				respuesta = cs.getString(6);
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
