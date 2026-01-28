package mx.ine.procprimerinsa.bsd.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASCTipoVotoInterface;
import mx.ine.procprimerinsa.as.ASDireccionJuntaInterface;
import mx.ine.procprimerinsa.as.ASFirmaInterface;
import mx.ine.procprimerinsa.as.ASImpresionCNInterface;
import mx.ine.procprimerinsa.bsd.BSDImpresionCNInterface;
import mx.ine.procprimerinsa.dto.DTOCombo;
import mx.ine.procprimerinsa.dto.DTOJuntaDistrital;
import mx.ine.procprimerinsa.dto.DTOSeccionesLocalidades;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.dto.db.DTOFirmasCartas;
import mx.ine.procprimerinsa.helper.impl.HLPPDFImpresionCarta;

@Component("bsdImpresionCN")
@Scope("prototype")
public class BSDImpresionCN implements BSDImpresionCNInterface {
	
	private static final long serialVersionUID = -6311060376938020166L;

	@Autowired
	@Qualifier("asImpresionCN")
	private ASImpresionCNInterface asImpresionCN;
	
	@Autowired
	@Qualifier("asFirma")
	private ASFirmaInterface asFirma;
	
	@Autowired
	@Qualifier("asCTipoVoto")
	private ASCTipoVotoInterface asCTipoVoto;
	
	@Autowired
	@Qualifier("asDireccionJunta")
	private ASDireccionJuntaInterface asDireccionJunta;
	
	@Override
	public HLPPDFImpresionCarta imprimirCartas(Integer idProceso, Integer idDetalle, Integer idParticipacion, 
			Integer idTipoVoto, Integer idEstado, Integer idDistrito, String estado,
			Integer idAreaResponsabilidad, Integer ordenVisitaInicio, Integer ordenVisitaFin,
			Integer seccion, Integer idLocalidad, Integer manzana, String numeroCredencialElector,
			Integer folio, Integer ordenamiento, List<DTOSeccionesLocalidades> seccionesLocalidades, boolean logo) {
		return asImpresionCN.imprimirCartas(idProceso, idDetalle, idParticipacion, idTipoVoto, idEstado, idDistrito, 
				estado, idAreaResponsabilidad, ordenVisitaInicio, ordenVisitaFin, seccion, idLocalidad, manzana, 
				numeroCredencialElector, folio, ordenamiento, seccionesLocalidades, logo);
	}
	
	@Override
	public HLPPDFImpresionCarta imprimirReverso(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito, 
			DTOJuntaDistrital junta, boolean firma, boolean logo, boolean domicilio) {
		File archivoFirma = firma ? junta.getFirma() : null;
		DTOJuntaDistrital direccion = domicilio ? junta : null;
		String telefono = junta.getTelefono() != null 
							&& !junta.getTelefono().isBlank() ?
							junta.getTelefono() : null;
		return asImpresionCN.imprimeReversoCN(idProceso, 
											idDetalle, 
											idEstado, 
											idDistrito, 
											archivoFirma,
											direccion, 
											telefono,
											logo);
	}
	
	@Override
	public DTOJuntaDistrital obtenerDatosJuntaDistrital(Integer idDetalle, Integer idEstado, String nombreEstado, 
			Integer idDistrito, Integer idParticipacion) {
		DTOJuntaDistrital datos = asDireccionJunta.obtenerDatosJuntaDistrital(idEstado,
																	idDistrito, 
																	nombreEstado);			
		
		DTOFirmasCartas firma = asFirma.obtenerFirma(idDetalle,
													idParticipacion);
		
		if(firma!= null) {
			File archivo = asFirma.obtenerArchivoGluster(firma.getRutaArchivo() + File.separator + firma.getPicadillo());
			if(archivo.exists()) {
				datos.setFirma(archivo);
			}
		}
		
		return datos;
	}

	@Override
	public List<DTOCombo> obtenerARES(Integer idProceso, Integer idDetalle, Integer idParticipacion) {
		return asImpresionCN.obtenerARES(idProceso, idDetalle, idParticipacion);
	}

	@Override
	public List<DTOSeccionesLocalidades> obtenerSecciones(Integer idEstado, Integer idCorte, Integer idDistritoFederal, 
			Integer idDistritoLocal, Integer idMunicipio) {
		return asImpresionCN.obtenerSecciones(idEstado, idCorte, idDistritoFederal, idDistritoLocal, idMunicipio);
	}

	@Override
	public List<Integer> obtenerManzanas(Integer idEstado, Integer idCorte, Integer seccion, Integer idLocalidad){
		return asImpresionCN.obtenerManzanas(idEstado, idCorte, seccion, idLocalidad);
	}
	
	@Override
	public List<Integer> obtenerOrdenVisita(Integer idProceso, Integer idDetalle, Integer idParticipacion, Integer idAreaResponsabilidad, Integer seccion) {
		return asImpresionCN.obtenerOrdenVisita(idProceso, idDetalle, idParticipacion, idAreaResponsabilidad, seccion);
	}
	
	@Override
	public Map<Integer, DTOCTipoVoto> obtieneTiposVotoPorParticipacion(Integer idDetalle, Integer idParticipacion) {
		return asCTipoVoto.obtieneTiposVotoPorParticipacion(idDetalle, idParticipacion);
	}

}
