package mx.ine.procprimerinsa.bsd;

import java.io.Serializable;

import org.primefaces.model.StreamedContent;

import mx.ine.procprimerinsa.util.Archivo;

public interface BSDFirmaInterface extends Serializable {

	public boolean guardarArchivo(Integer idProceso,Integer idDetalle, Integer idParticipacion, 
			String usuario, Archivo archivo);

	public StreamedContent obtenerFirma(Integer idDetalle, Integer idParticipacion);

}
