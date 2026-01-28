package mx.ine.procprimerinsa.bsd;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mx.ine.procprimerinsa.dto.DTOCombo;
import mx.ine.procprimerinsa.dto.DTOJuntaDistrital;
import mx.ine.procprimerinsa.dto.DTOSeccionesLocalidades;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.helper.impl.HLPPDFImpresionCarta;

public interface BSDImpresionCNInterface extends Serializable{
	
	public HLPPDFImpresionCarta imprimirCartas(Integer idProceso, Integer idDetalle, Integer idParticipacion, 
			Integer idTipoVoto, Integer idEstado, Integer idDistrito, String estado,
			Integer idAreaResponsabilidad, Integer ordenVisitaInicio, Integer ordenVisitaFin,
			Integer seccion, Integer idLocalidad, Integer manzana, String numeroCredencialElector,
			Integer folio, Integer ordenamiento, List<DTOSeccionesLocalidades> seccionesLocalidades, 
			boolean logo);
	public HLPPDFImpresionCarta imprimirReverso(Integer idProceso, Integer idDetalle, Integer idEstado, 
			Integer idDistrito, DTOJuntaDistrital junta, boolean firma, boolean logo, boolean domicilio);
	public List<DTOCombo> obtenerARES(Integer idProceso, Integer idDetalle, Integer idParticipacion);
	public List<DTOSeccionesLocalidades> obtenerSecciones(Integer idEstado, Integer idCorte, Integer idDistritoFederal, 
			Integer idDistritoLocal, Integer idMunicipio);
	public List<Integer> obtenerManzanas(Integer idEstado, Integer idCorte, Integer seccion, Integer idLocalidad);
	public List<Integer> obtenerOrdenVisita(Integer idProceso, Integer idDetalle, Integer idParticipacion, Integer idAreaResponsabilidad, 
			Integer seccion);
	public DTOJuntaDistrital obtenerDatosJuntaDistrital(Integer idDetalle, Integer idEstado, String nombreEstado, 
			Integer idDistrito, Integer idParticipacion);
	public Map<Integer, DTOCTipoVoto> obtieneTiposVotoPorParticipacion(Integer idDetalle, Integer idParticipacion);
	
}
