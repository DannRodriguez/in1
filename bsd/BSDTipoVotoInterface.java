package mx.ine.procprimerinsa.bsd;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVotoParticipacion;

public interface BSDTipoVotoInterface extends Serializable {
	
	public List<DTOCTipoVoto> obtieneCTiposVoto(Integer idDetalle);
	
	public List<DTOCTipoVotoParticipacion> obtieneCTiposVotoParticipacion(List<Integer> detalles);
	
	public Map<Integer, DTOParticipacionGeneral> obtieneParticipacionesPorTipoVoto(DTOCTipoVoto tipoVoto,
			Integer idCorte);
		
	public String guardaTipoVoto(List<Integer> detalles, DTOCTipoVoto tipoVoto);
	
	public String actualizaTipoVoto(List<Integer> detalles, DTOCTipoVoto tipoVoto);
	
	public String eliminaTipoVoto(List<Integer> detalles, DTOCTipoVoto tipoVoto);
	
	public String guardaTipoVotoParticipaciones(List<DTOCTipoVotoParticipacion> participaciones);
	
	public String eliminaTipoVotoParticipaciones(Integer idDetalle, Integer idTipoVoto);
	
	public String reiniciaCargaTipoVoto(List<Integer> detalles);
	
	public String reiniciaCargaTipoVotoParticipacion(List<Integer> detalles);
	
}
