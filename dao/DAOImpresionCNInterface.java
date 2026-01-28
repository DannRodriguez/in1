package mx.ine.procprimerinsa.dao;

import java.util.List;

import mx.ine.procprimerinsa.dto.DTOCombo;
import mx.ine.procprimerinsa.dto.DTOParametrosMargenes;
import mx.ine.procprimerinsa.dto.DTOSeccionesLocalidades;
import mx.ine.procprimerinsa.dto.db.DTODatosInsaculados;

public interface DAOImpresionCNInterface {
	
	public List<DTOCombo> obtenerARES(Integer idProceso, Integer idDetalle, Integer idParticipacion);
	public List<DTOSeccionesLocalidades> obtenerSecciones(Integer idEstado, Integer idCorte, Integer idDistritoFederal, 
			Integer idDistritoLocal, Integer idMunicipio);
	public List<Integer> obtenerManzanas(Integer idEstado, Integer idCorte, Integer seccion, Integer idLocalidad);
	public List<Integer> obtenerOrdenVisita(Integer idProceso, Integer idDetalle, Integer idParticipacion, 
			Integer idAreaResponsabilidad, Integer seccion);
	public List<DTODatosInsaculados> obtenerCiudadanos(Integer idProceso, Integer idDetalle, Integer idParticipacion, 
			Integer idTipoVoto, Integer idAreaResponsabilidad, Integer ordenVisitaInicio, Integer ordenVisitaFin,
			Integer seccion, Integer idLocalidad, Integer manzana, String numeroCredencialElector, Integer folio,
			Integer ordenamiento);
	public DTOParametrosMargenes obtenerParametrosDocumento(Integer idProceso, Integer idDetalle, Integer idEstado, 
			Integer idDistrito);
	
}
