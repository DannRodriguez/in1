package mx.ine.procprimerinsa.as.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASFirmaInterface;
import mx.ine.procprimerinsa.dao.DAOFirmasCartasInterface;
import mx.ine.procprimerinsa.dto.db.DTOFirmasCartas;
import mx.ine.procprimerinsa.util.Archivo;
import mx.ine.procprimerinsa.util.Constantes;

@Service("asFirma")
@Scope("prototype")
public class ASFirma implements ASFirmaInterface {	
	
	private static final long serialVersionUID = 4846599833128860074L;
	private static final Logger logger = Logger.getLogger(ASFirma.class);
	
	@Autowired
	@Qualifier("daoFirmasCartas")
	private transient DAOFirmasCartasInterface daoFirmasCartas;
	
	@Override
	public boolean guardarArchivoGluster(DTOFirmasCartas firma, Archivo archivo, String relativePath) {
		try(InputStream input = new FileInputStream(archivo.getArchivo())) {
			StringBuilder rutaArchivo = new StringBuilder()
											.append(firma.getIdProceso()).append(File.separator)
											.append(firma.getIdDetalleProceso()).append(File.separator)
											.append(relativePath).append(File.separator)
											.append(firma.getIdParticipacion());
			firma.setRutaArchivo(rutaArchivo.toString());
			
			rutaArchivo.insert(0, Constantes.RUTA_LOCAL_FS);
			
			File directorio = new File(rutaArchivo.toString());
	        if(directorio.exists()
	    		&& directorio.isDirectory()) {
	    		File[] ficheros = directorio.listFiles();
	    		for(int i = 0 ; i<ficheros.length; i++) {
	    			if(ficheros[i].exists())  ficheros[i].delete();
	    		}
	        } else {
	        	directorio.mkdirs();
	        }
	        
	        rutaArchivo.append(File.separator)
	        			.append(firma.getPicadillo());
		    
        	Path file = Files.createFile(Paths.get(rutaArchivo.toString()));
        	Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        } catch(Exception e){
			logger.error("ERROR ASFirma -guardarArchivoGluster: ", e);
			return false;
		}
        
		return true;
	}
	
	@Override
	public File obtenerArchivoGluster(String ruta) {
		return new File(Constantes.RUTA_LOCAL_FS + ruta);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public boolean guardarFirma(DTOFirmasCartas firma) {
		try {
			daoFirmasCartas.guardarFirma(firma);
		} catch(Exception e) {
			logger.error("ERROR ASFirma -guardarFirma: ", e);
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public DTOFirmasCartas obtenerFirma(Integer idDetalle, Integer idParticipacion) {
		try {
			return daoFirmasCartas.obtenerFirma(idDetalle, idParticipacion);
		} catch(Exception e) {
			logger.error("ERROR ASFirma -obtenerFirma: ", e);
			return null;
		}
	}
}
