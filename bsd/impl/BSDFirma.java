package mx.ine.procprimerinsa.bsd.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Date;

import jakarta.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.jboss.logging.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASCEtiquetasInterface;
import mx.ine.procprimerinsa.as.ASFirmaInterface;
import mx.ine.procprimerinsa.bsd.BSDFirmaInterface;
import mx.ine.procprimerinsa.dto.db.DTOCEtiquetas;
import mx.ine.procprimerinsa.dto.db.DTOFirmasCartas;
import mx.ine.procprimerinsa.util.Archivo;
import mx.ine.procprimerinsa.util.Constantes;

@Component("bsdFirma")
@Scope("prototype")
public class BSDFirma implements BSDFirmaInterface {
	
	private static final long serialVersionUID = 2405684213583850695L;
	private static final Logger logger = Logger.getLogger(BSDFirma.class);
	
	@Autowired
	@Qualifier("asFirma")
	private ASFirmaInterface asFirma;
	
	@Autowired
	@Qualifier("asEtiquetas")
	private ASCEtiquetasInterface asEtiquetas;
	
	public boolean guardarArchivo(Integer idProceso, Integer idDetalle, Integer idParticipacion, 
			String usuario, Archivo archivo) {
		DTOFirmasCartas firma = new DTOFirmasCartas();
		firma.setIdProceso(idProceso);
		firma.setIdDetalleProceso(idDetalle);
		firma.setIdParticipacion(idParticipacion);
		firma.setNombreArchivo(archivo.getNombre());
		firma.setUsuario(usuario);
		firma.setFechaHora(new Date());
		
		String picadillo = archivo.getPicadillo("SHA-256");
		if(picadillo == null) {
			return false;
		}
		
		firma.setPicadillo(picadillo);
		
		DTOCEtiquetas relativePathEtiqueta = asEtiquetas.obtenerEtiqueta(0, 0, 0, 0, Constantes.ETIQUETA_RUTA_FIRMA);
		if(relativePathEtiqueta == null) {
			return false;
		}
		
		return asFirma.guardarArchivoGluster(firma, archivo, relativePathEtiqueta.getDescripcionEtiqueta())
				&& asFirma.guardarFirma(firma);
	}
	
	@Override
	public StreamedContent obtenerFirma(Integer idDetalle, Integer idParticipacion) {
		DTOFirmasCartas firma = asFirma.obtenerFirma(idDetalle, idParticipacion);
			
		if(firma == null) {
			return null;
		}
		
		try {
			StringBuilder ruta = new StringBuilder(Constantes.RUTA_LOCAL_FS)
										.append(firma.getRutaArchivo()).append(File.separator)
										.append(firma.getPicadillo());
			File f = new File(ruta.toString());
			BufferedImage imgbuff = ImageIO.read(f);
			ByteArrayOutputStream bstr = new ByteArrayOutputStream();
			ImageIO.write(imgbuff, "png", bstr);
			InputStream fis2 = new ByteArrayInputStream(bstr.toByteArray());
			String mime = FacesContext.getCurrentInstance()
								.getExternalContext()
								.getMimeType(ruta.toString());
			return DefaultStreamedContent.builder()
										.contentType(mime)
										.stream(() -> fis2)
										.build();
		} catch(Exception e) {
			logger.error("ERROR BSDFirma -obtenerFirma: ", e);
			return null;
		}
	}
}
