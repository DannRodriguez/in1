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
import mx.ine.procprimerinsa.pao.PAOOrdenamientosInsaInterface;
import mx.ine.procprimerinsa.util.Constantes;

@Scope("prototype")
@Repository("paoOrdenamientos")
public class PAOOrdenamientosInsa extends DAOGeneric<Serializable, Serializable> implements PAOOrdenamientosInsaInterface {
	
	@Override
	public String ejecutaOrdenamientos(final Integer idProceso, final Integer idDetalle, final Integer idParticipacion,
			final String letra, final String usuario) {
		OrdenamientosWork ordenamientos = new OrdenamientosWork(idProceso, 
															idDetalle,
															idParticipacion,
															letra, 
															usuario,
															Constantes.STORED_PROCEDURE_ORDENAMIENTOS);

		Session session = getSession();
		session.doWork(ordenamientos);
		return ordenamientos.getRespuesta();
	}
	
	public static class OrdenamientosWork implements Work{
		
		private Integer idProceso;
		private Integer idDetalle;
		private Integer idParticipacion;
		private String letra;
		private String usuario;
		private String pl;
		private String respuesta;
		
		public OrdenamientosWork(Integer idProceso, Integer idDetalle, Integer idParticipacion, String letra, String usuario, 
				String pl) {
			super();
			this.idProceso = idProceso;
			this.idDetalle = idDetalle;
			this.idParticipacion = idParticipacion;
			this.letra = letra;
			this.usuario = usuario;
			this.pl = pl;
		}
		
		public void execute(Connection conn) throws SQLException {
			
			try(CallableStatement cs = conn.prepareCall(pl)){
				cs.setInt(1, idProceso);
				cs.setInt(2, idDetalle);
				cs.setInt(3, idParticipacion);
				cs.setString(4, letra);
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