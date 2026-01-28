package mx.ine.procprimerinsa.batch.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import mx.ine.procprimerinsa.dto.db.DTOAreasSecciones;

public class RMAreasSecciones implements RowMapper<DTOAreasSecciones> {
	
	@Override
	public DTOAreasSecciones mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
		DTOAreasSecciones dto = new DTOAreasSecciones();
		dto.setIdProcesoElectoral(rs.getInt("ID_PROCESO_ELECTORAL"));
		dto.setIdDetalleProceso(rs.getInt("ID_DETALLE_PROCESO"));
		dto.setIdParticipacion(rs.getInt("ID_PARTICIPACION"));
		dto.setIdAreaResponsabilidad(rs.getInt("ID_AREA_RESPONSABILIDAD"));
		dto.setIdTipoVoto(rs.getInt("ID_TIPO_VOTO"));
		dto.setSeccion(rs.getInt("SECCION"));
		dto.setUsuario(rs.getString("USUARIO"));
		dto.setFechaHora(rs.getDate("FECHA_HORA"));
		return dto;
	}

}
