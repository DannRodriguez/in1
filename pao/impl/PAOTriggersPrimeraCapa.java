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
import mx.ine.procprimerinsa.pao.PAOTriggersPrimeraCapaInterface;
import mx.ine.procprimerinsa.util.Constantes;

@Scope("prototype")
@Repository("paoTriggersPrimeraCapa")
public class PAOTriggersPrimeraCapa extends DAOGeneric<Serializable, Serializable> implements PAOTriggersPrimeraCapaInterface {
	
	@Override
	public String habilitaDeshabilitaTriggers(boolean isHabilita) {
		TriggersWork triggers = new TriggersWork(isHabilita ? 
									Constantes.STORED_PROCEDURE_HABILITA_TRIGGERS_PRIMERA_CAPA
									: Constantes.STORED_PROCEDURE_DESHABILITA_TRIGGERS_PRIMERA_CAPA);

		Session session = getSession();
		session.doWork(triggers);
		return triggers.getRespuesta();
	}
	
	public static class TriggersWork implements Work {
		
		private String pl;
		private String respuesta;
		
		public TriggersWork(String pl) {
			super();
			this.pl = pl;
		}
		
		public void execute(Connection conn) throws SQLException {
			
			try(CallableStatement cs = conn.prepareCall(pl)){
				cs.registerOutParameter(1, Constantes.ORACLE_TYPES_VARCHAR);
				
				cs.execute();
				
				respuesta = cs.getString(1);
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
