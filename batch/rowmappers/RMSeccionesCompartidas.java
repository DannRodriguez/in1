package mx.ine.procprimerinsa.batch.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import mx.ine.procprimerinsa.dto.db.DTOSeccionesCompartidas;

public class RMSeccionesCompartidas implements RowMapper<DTOSeccionesCompartidas> {
	
	@Override
	public DTOSeccionesCompartidas mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
		DTOSeccionesCompartidas dto = new DTOSeccionesCompartidas();
		dto.setIdProcesoElectoral(rs.getInt("ID_PROCESO_ELECTORAL"));
		dto.setIdDetalleProceso(rs.getInt("ID_DETALLE_PROCESO"));
		dto.setIdParticipacion(rs.getInt("ID_PARTICIPACION"));
		dto.setIdAreaResponsabilidad(rs.getInt("ID_AREA_RESPONSABILIDAD"));
		dto.setIdTipoVoto(rs.getInt("ID_TIPO_VOTO"));
		dto.setSeccion(rs.getInt("SECCION"));
		dto.setIdLocalidad(rs.getInt("ID_LOCALIDAD"));
		dto.setManzana(rs.getInt("MANZANA"));
		dto.setUsuario(rs.getString("USUARIO"));
		dto.setFechaHora(rs.getDate("FECHA_HORA"));
		return dto;
	}
}
