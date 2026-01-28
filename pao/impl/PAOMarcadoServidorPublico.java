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
import mx.ine.procprimerinsa.pao.PAOMarcadoServidorPublicoInterface;
import mx.ine.procprimerinsa.util.Constantes;

@Scope("prototype")
@Repository("paoMarcadoServidorPublico")
public class PAOMarcadoServidorPublico extends DAOGeneric<Serializable, Serializable> implements PAOMarcadoServidorPublicoInterface {
	
	@Override
	@Transactional
	public String ejecutaMarcadoServidorPublico(Integer idProceso, Integer idDetalle) {
		CargaWork carga = new CargaWork(idProceso,
									idDetalle,
									Constantes.STORED_PROCEDURE_LN_MARCADO_SERVIDOR_PUBLICO);
		
		try(Session session = openSessionLN()) {
			session.doWork(carga);
			return carga.getRespuesta();
		}
	}
	
	@Override
	@Transactional
	public String ejecutaReinicioServidorPublico(Integer idProceso, Integer idDetalle) {
		CargaWork carga = new CargaWork(idProceso,
									idDetalle,
									Constantes.STORED_PROCEDURE_LN_REINICIO_SERVIDOR_PUBLICO);
		
		try(Session session = openSessionLN()) {
			session.doWork(carga);
			return carga.getRespuesta();
		}
	}
	
	public static class CargaWork implements Work {
		
		private Integer idProceso;
		private Integer idDetalle;
		private String pl;
		private String respuesta;
		
		public CargaWork(Integer idProceso, Integer idDetalle, String pl) {
			super();
			this.idProceso = idProceso;
			this.idDetalle = idDetalle;
			this.pl = pl;
		}
		
		public void execute(Connection conn) throws SQLException {
			
			try(CallableStatement cs = conn.prepareCall(pl)) {
				cs.setInt(1, idProceso);
				cs.setInt(2, idDetalle);
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

}
