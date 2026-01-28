package mx.ine.procprimerinsa.batch.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.ine.procprimerinsa.dto.db.DTODatosInsaculados;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

public class RMDatosPersonales implements RowMapper<DTODatosInsaculados> {
	
	@Override
	public DTODatosInsaculados mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
		DTODatosInsaculados dto = new DTODatosInsaculados();
		dto.setIdEstado(rs.getInt("ID_ESTADO"));
		dto.setIdDistritoFederal(rs.getInt("ID_DISTRITO_FEDERAL"));
		dto.setNumeroCredencialElector(rs.getString("NUMERO_CREDENCIAL_ELECTOR"));
		return dto;
	}

}
