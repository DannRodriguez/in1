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
import mx.ine.procprimerinsa.pao.PAOAdminIndicesDIInterface;
import mx.ine.procprimerinsa.util.Constantes;

@Scope("prototype")
@Repository("paoAdminIndicesDI")
public class PAOAdminIndicesDI extends DAOGeneric<Serializable, Serializable> implements PAOAdminIndicesDIInterface {
	
	@Override
	public String creaEliminaIndices(boolean isCrea) {
		IndicesWork indices = new IndicesWork(isCrea ? Constantes.STORED_PROCEDURE_CREA_INDICES_DI 
													: Constantes.STORED_PROCEDURE_ELIMINA_INDICES_DI);
		
		Session session = getSession();
		session.doWork(indices);
		return indices.getRespuesta();
	}
	
	public static class IndicesWork implements Work {
		
		private String pl;
		private String respuesta;
		
		public IndicesWork(String pl) {
			super();
			this.pl = pl;
		}
		
		public void execute(Connection conn) throws SQLException {
			
			try(CallableStatement cs = conn.prepareCall(pl)) {
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
