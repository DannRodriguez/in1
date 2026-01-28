package mx.ine.procprimerinsa.bsd.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.procprimerinsa.as.ASCTipoVotoInterface;
import mx.ine.procprimerinsa.bsd.BSDTipoVotoInterface;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVotoParticipacion;

@Scope("prototype")
@Service("bsdTipoVoto")
public class BSDTipoVoto implements BSDTipoVotoInterface {

	private static final long serialVersionUID = 3254082809180335263L;
	
	@Autowired
	@Qualifier("asCTipoVoto")
	private ASCTipoVotoInterface asCTipoVoto;

	@Override
	public List<DTOCTipoVoto> obtieneCTiposVoto(Integer idDetalle) {
		return asCTipoVoto.obtieneCTiposVoto(idDetalle);
	}
	
	@Override
	public List<DTOCTipoVotoParticipacion> obtieneCTiposVotoParticipacion(List<Integer> detalles) {
		return asCTipoVoto.obtieneCTiposVotoParticipacion(detalles);
	}
	
	@Override
	public Map<Integer, DTOParticipacionGeneral> obtieneParticipacionesPorTipoVoto(DTOCTipoVoto tipoVoto,
			Integer idCorte) {
		return asCTipoVoto.obtieneParticipacionesPorTipoVoto(tipoVoto, idCorte);
	}

	@Override
	public String guardaTipoVoto(List<Integer> detalles, DTOCTipoVoto tipoVoto) {
		return asCTipoVoto.guardaTipoVoto(detalles, tipoVoto);
	}

	@Override
	public String actualizaTipoVoto(List<Integer> detalles, DTOCTipoVoto tipoVoto) {
		return asCTipoVoto.actualizaTipoVoto(detalles, tipoVoto);
	}

	@Override
	public String eliminaTipoVoto(List<Integer> detalles, DTOCTipoVoto tipoVoto) {
		return asCTipoVoto.eliminaTipoVoto(detalles, tipoVoto);
	}
	
	@Override
	public String guardaTipoVotoParticipaciones(List<DTOCTipoVotoParticipacion> participaciones) {
		return asCTipoVoto.guardaTipoVotoParticipaciones(participaciones);
	}
	
	@Override
	public String eliminaTipoVotoParticipaciones(Integer idDetalle, Integer idTipoVoto) {
		return asCTipoVoto.eliminaTipoVotoParticipaciones(idDetalle, idTipoVoto);
	}
	
	@Override
	public String reiniciaCargaTipoVoto(List<Integer> detalles) {
		return asCTipoVoto.reiniciaCargaTipoVoto(detalles);
	}
	
	@Override
	public String reiniciaCargaTipoVotoParticipacion(List<Integer> detalles) {
		return asCTipoVoto.reiniciaCargaTipoVotoParticipacion(detalles);
	}

}
