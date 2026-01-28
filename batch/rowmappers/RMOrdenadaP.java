package mx.ine.procprimerinsa.batch.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import mx.ine.procprimerinsa.dto.db.DTOOrdenadaP;

public class RMOrdenadaP implements RowMapper<DTOOrdenadaP> {
	
	@Override
	public DTOOrdenadaP mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
		DTOOrdenadaP dto = new DTOOrdenadaP();
		dto.setIdProcesoElectoral(rs.getInt("ID_PROCESO_ELECTORAL"));
		dto.setIdDetalleProceso(rs.getInt("ID_DETALLE_PROCESO"));
		dto.setIdParticipacion(rs.getInt("ID_PARTICIPACION"));
		dto.setIdMunicipio(rs.getInt("ID_MUNICIPIO"));
		dto.setSeccion(rs.getInt("SECCION"));
		dto.setIdLocalidad(rs.getInt("ID_LOCALIDAD"));
		dto.setManzana(rs.getInt("MANZANA"));
		dto.setOrden(rs.getInt("ORDEN"));
		dto.setUsuario(rs.getString("USUARIO"));
		dto.setFechaHora(rs.getDate("FECHA_HORA"));
		return dto;
	}

}
