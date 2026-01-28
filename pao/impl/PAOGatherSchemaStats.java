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
import mx.ine.procprimerinsa.pao.PAOGatherSchemaStatsInterface;
import mx.ine.procprimerinsa.util.Constantes;

@Scope("prototype")
@Repository("paoGatherSchemaStats")
public class PAOGatherSchemaStats extends DAOGeneric<Serializable, Serializable> implements PAOGatherSchemaStatsInterface {
	
	@Override
	public void recolectaEstadisticas() {
		EstadisticasWork estadisticas = new EstadisticasWork("INSA1", 
														Constantes.STORED_PROCEDURE_DBMS_GATHER_SCHEMA_STATS);
		
		Session session = getSession();
		session.doWork(estadisticas);
	}
	
	public static class EstadisticasWork implements Work {
		
		private String esquema;
		private String pl;
		
		public EstadisticasWork(String esquema, String pl) {
			super();
			this.esquema =  esquema;
			this.pl = pl;
		}
		
		public void execute(Connection conn) throws SQLException {
			
			try(CallableStatement cs = conn.prepareCall(pl)) {
				cs.setString(1, esquema);
				cs.execute();
			}
		}
		
	}

}
