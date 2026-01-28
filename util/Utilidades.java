package mx.ine.procprimerinsa.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.TemporalType;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.hibernate.query.NativeQuery;
import org.jboss.logging.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.dto.DTOCSVPrintableInterface;

@Scope("prototype")
@Component("util")
public class Utilidades implements Serializable {

	private static final long serialVersionUID = 1031233590655920205L;
	private static final Logger logger = Logger.getLogger(Utilidades.class);
	private static final String UNKNOWN = "unknown";

	public static String mensajeProperties(String llave) {
		ResourceBundleMessageSource messageSource = null;
		
		try { 
		  messageSource = ((ResourceBundleMessageSource) ApplicationContextUtils.getApplicationContext().getBean("messageSource"));
		  return messageSource.getMessage(llave, null, null);
		} catch (Exception e) {
			logger.error("ERROR Utilidades -mensajeProperties: ", e);
			return null;
		}
	}	
	
	public static String formatoTiempo(long timeInMillis) {
		if (timeInMillis >= 3600000) {
			// Retorna con horas
			return String.format("%1$tH:%1$tM:%1tS", timeInMillis);
		} else {
			// Retorna sin horas
			return String.format("%1$tM:%1tS", timeInMillis);
		}
	}
	
	public static ByteArrayOutputStream creaArchivoZIP(String directorio) throws Exception{
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(fos);
		addDirToZipArchive(zos, new File(directorio), null);
		zos.flush();
		zos.close();
		return fos;
	}

	public static void addDirToZipArchive(ZipOutputStream zos, File archivoZIP, String directorio) throws Exception {
	    
		if (archivoZIP == null || !archivoZIP.exists()) {
	        return;
	    }
		
	    String nombreZIP = archivoZIP.getName();
	    
	    if (directorio != null && !directorio.isEmpty()) {
	    	nombreZIP = directorio + "/" + archivoZIP.getName();
	    }

	    if (archivoZIP.isDirectory()){
	        for (File file : archivoZIP.listFiles()) {
	            addDirToZipArchive(zos, file, nombreZIP);
	        }
	    } else {
	    	try(FileInputStream fis = new FileInputStream(archivoZIP);) {
	    		byte[] buffer = new byte[1024];
	 	        zos.putNextEntry(new ZipEntry(nombreZIP));
	 	        int length;
	 	        while ((length = fis.read(buffer)) > 0) {
	 	            zos.write(buffer, 0, length);
	 	        }
	 	        zos.closeEntry();
			} catch (Exception e) {
				logger.error("ERROR Utilidades -addDirToZipArchive: ", e);
			}
	    }
	}
	
	public static String obtenerPicadillo(String funcionPicadillo, String ruta) throws IOException{
		
		String picadillo = null;
		File documento = new File(ruta);
				
		try(FileInputStream fis = new FileInputStream(documento)) {
					
			MessageDigest digesto = MessageDigest.getInstance(funcionPicadillo);
			
			byte[] dataBytes = new byte[1024];
			int nread = 0; 
			while ((nread = fis.read(dataBytes)) != -1) {
				digesto.update(dataBytes, 0, nread);
			}
			
			byte[] mdbytes = digesto.digest();
			
			picadillo = stringHexa(mdbytes);
			
		} catch (Exception e) {
			logger.error("ERROR Utilidades -obtenerPicadillo: ", e);		
		}
		
		return picadillo;
	}
	
	public static String stringHexa(byte[] bytes) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
			int parteBaixa = bytes[i] & 0xf;
			if (parteAlta == 0) s.append('0');
			s.append(Integer.toHexString(parteAlta | parteBaixa));
		}
		return s.toString();
	}
	
	public static String obtenerMes(String mesNum){		
		switch(mesNum){
			case "1": return "ENERO";
			case "2": return "FEBRERO";
			case "3": return "MARZO";
			case "4": return "ABRIL";
			case "5": return "MAYO";
			case "6": return "JUNIO";
			case "7": return "JULIO";
			case "8": return "AGOSTO";
			case "9": return "SEPTIEMBRE";
			case "10": return "OCTUBRE";
			case "11": return "NOVIEMBRE";
			case "12": return "DICIEMBRE";
			default: return null;
		}
	}
	
	public static void descargaArchivoCSV(String[] header, List<? extends DTOCSVPrintableInterface> lista, 
			String nombreArchivo) throws IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		String filePath = new StringBuilder().append(Constantes.RUTA_LOCAL_FS)
											.append(Instant.now())
											.toString();
		
	    StringBuilder fila = new StringBuilder();
	    
	    Arrays.stream(header).forEach(h -> fila.append(h).append('|'));
	    fila.deleteCharAt(fila.length() - 1);
	    fila.append(Constantes.DEFAULT_LINE_SEPARATOR);
	    
	    for(DTOCSVPrintableInterface data : lista) {
	    	Arrays.stream(data.getCSVPrintable()).forEach(d -> {
	    		String stringData = Objects.toString(d, null);
	    		fila.append(stringData);
	    		fila.append('|');
	    	});
	    	fila.deleteCharAt(fila.length() - 1);
	    	fila.append(Constantes.DEFAULT_LINE_SEPARATOR);
	    }
	    
	    File newFile = new File(filePath);
		FileUtils.writeByteArrayToFile(newFile, fila.toString().getBytes());
		
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			FileInputStream stream = new FileInputStream(newFile)) {
        	stream.transferTo(baos);
        	   
        	ec.responseReset();
        	ec.setResponseContentType("application/download");
        	ec.setResponseHeader("Expires", "0");
        	ec.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0, no-cache, no-store");
        	ec.setResponseHeader("Pragma", "no-cache");
        	ec.setResponseHeader("Content-disposition", "attachment;filename=" + nombreArchivo + ".txt");
        	ec.setResponseContentLength(baos.size());
    		OutputStream out = ec.getResponseOutputStream();
    		baos.writeTo(out);
    		ec.responseFlushBuffer();
    		
    		out.flush();
    		out.close();
    		
    		baos.flush();
    	}
		
		Files.deleteIfExists(Paths.get(filePath));
		
		fc.responseComplete();
	}
	
	public static <T> Stream<T> collectionToStream(Collection<T> collection) {
	    return Optional.ofNullable(collection)
	      .map(Collection::stream)
	      .orElseGet(Stream::empty);
	}
	
	public static String formatDate(Date date) {
		return date != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date) : null;
	}
	
	public static Integer validaUsuario(String llave) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(llave.getBytes(StandardCharsets.UTF_8));
			String encoded = Base64.getEncoder().encodeToString(hash);
			for(Map.Entry<Integer, String> llaves : Constantes.SPOOL_LLAVES_VALIDAS.entrySet()) {
				if(llaves.getValue().equals(encoded)) {
					return llaves.getKey();
				}
			}
		} catch (Exception e) {
			logger.error("ERROR Utilidades -validaUsuario: ", e);
		}
		
		return null;
	}
	
	public static String cleanStringForFileName(String ruta) {
		return ruta.trim().replace('/', ' ');
	}
	
	public static boolean isRolOC(String rol) {
		String[] datosRol = rol.split("\\.");
		return datosRol.length > 0 
				&& datosRol[datosRol.length-1].equalsIgnoreCase("OC");
	}

	public static float cmtoPuntos(float cm) {
		// Itext 1 pulgada (inch) = 72 puntos
		// 1 pulgada = 2.54cm
		float pulgadas = (cm / Float.valueOf("2.54"));
		return (Float.valueOf(72)) * pulgadas;

	}
	
   public static String sanitizeSQLInjection(String main) {
       return main.replace("<", "").replace(">", "")
               .replace("\\", "").replace("|", "")
               .replace("?", "").replace("*", "");
   }
   
   public static String getClientIP(HttpServletRequest request) {
	    try {
	        String ip = request.getHeader("X-Forwarded-For");
	        if (ip != null && !ip.isEmpty() && !UNKNOWN.equalsIgnoreCase(ip)) {
	            ip = ip.split(",")[0].trim();
	        } else {
	            ip = request.getHeader("X-Real-IP");
	            if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
	                ip = request.getHeader("CF-Connecting-IP");
	            }
	            if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
	                ip = request.getHeader("Proxy-Client-IP");
	            }
	            if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
	                ip = request.getHeader("WL-Proxy-Client-IP");
	            }
	            if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
	                ip = request.getHeader("X-Client-IP");
	            }
	            if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
	                ip = request.getRemoteAddr();
	            }
	        }
	       
	        return (ip != null && !ip.trim().isEmpty()) ? ip.trim() : null;
	    } catch (Exception e) {
	        return null;
	    }
	}
   
   public static <T> NativeQuery<T> setParameterDate(NativeQuery<T> query, String parameterName, 
		   Date parameterValue, TemporalType temporalType) {
	   return parameterValue != null ? 
			   	query.setParameter(parameterName, parameterValue, temporalType)
			   : query.setParameter(parameterName, null);
   }
}
