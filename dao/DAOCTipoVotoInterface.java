package mx.ine.procprimerinsa.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVotoParticipacion;

public interface DAOCTipoVotoInterface extends DAOGenericInterface<DTOCTipoVoto, Serializable> {
	
	public List<DTOCTipoVoto> obtieneCTiposVoto(Integer idDetalle);
	
	public List<DTOCTipoVotoParticipacion> obtieneCTiposVotoParticipacion(List<Integer> detalles);
	
	public Map<Integer, DTOCTipoVoto> obtieneTiposVotoPorParticipacion(Integer idDetalle, Integer idParticipacion);
	
	public Map<Integer, DTOParticipacionGeneral> obtieneParticipacionesPorTipoVoto(DTOCTipoVoto tipoVoto, 
			Integer idCorte);
	
	public boolean existeIdCTipoVoto(DTOCTipoVoto tipoVoto);
	
	public void guardaCTipoVoto(DTOCTipoVoto tipoVoto);
	
	public void eliminaCTipoVoto(DTOCTipoVoto tipoVoto);
	
	public void guardaTipoVotoParticipacion(DTOCTipoVotoParticipacion tipoVotoParticipacion);
	
	public void eliminaParticipacionesTipoVoto(Integer idDetalle, Integer idTipoVoto);
	
	public void reiniciaCargaTipoVoto(List<Integer> detalles);
	
	public void reiniciaCargaTipoVotoParticipacion(List<Integer> detalles);
	
}
