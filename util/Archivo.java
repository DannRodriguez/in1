package mx.ine.procprimerinsa.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.List;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.mime.MimeTypes;
import org.jboss.logging.Logger;

import org.apache.tika.metadata.Metadata;

public class Archivo implements Serializable{	

	private static final long serialVersionUID = 4274768539728995527L;
	private static final Logger logger = Logger.getLogger(Archivo.class);
	
	private static final Detector DETECTOR = new DefaultDetector(MimeTypes.getDefaultMimeTypes());
	private static final int BUFFER_SIZE = 1024; 
	
	private static File archivo;
	
	private String nombre;
	private String tipo;
	private String picadillo;
	private String extension;
	private String descripcion;
	private String ruta;
	private ByteArrayInputStream inputStream;
	private List<String> mimesPermitidos;
	
	public Archivo(File archivo, List<String> mimesPermitidos) throws IOException {
		Archivo.archivo = archivo;
		
		nombre = archivo.getName();
		ruta = archivo.getPath();
		extension = detectMimeType(archivo);
		
		this.mimesPermitidos = mimesPermitidos;
	}
	
	public boolean isValido() throws IOException {
		return mimesPermitidos.contains(detectMimeType(archivo));
	}
	
	public String getPicadillo(String funcionPicadillo) {
				
		try (FileInputStream fis = new FileInputStream(archivo)) {
			
			MessageDigest digesto = MessageDigest.getInstance(funcionPicadillo);
			byte[] dataBytes = new byte[BUFFER_SIZE];
			int nread = 0; 
			
			while ((nread = fis.read(dataBytes)) != -1) {
				digesto.update(dataBytes, 0, nread);
			}
			
			byte[] mdbytes = digesto.digest();
			picadillo = stringHexa(mdbytes);
			return picadillo;	
		} catch(Exception e) {
			logger.error("ERROR Archivo -getPicadillo: ", e);
			return null;
		}
		
	}
	
	private static String detectMimeType(final File file) throws IOException {
	    TikaInputStream tikaIS = null;
	    try {
	        tikaIS = TikaInputStream.get(file.toPath());
	        final Metadata metadata = new Metadata();
	        return DETECTOR.detect(tikaIS, metadata).toString();
	    } finally {
	        if (tikaIS != null) {
	            tikaIS.close();
	        }
	    }
	}
	
	private static String stringHexa(byte[] bytes) {
		StringBuilder s = new StringBuilder();
		
		for (int i = 0; i < bytes.length; i++) {
			int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
			int parteBaixa = bytes[i] & 0xf;
			
			if (parteAlta == 0) s.append('0');
			
			s.append(Integer.toHexString(parteAlta | parteBaixa));
		}
		
		return s.toString();
	}
	
	public File getArchivo() {
		return archivo;
	}
	
	public void setArchivo(File archivo) {
		Archivo.archivo = archivo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setPicadillo(String picadillo) {
		this.picadillo = picadillo;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public List<String> getMimesPermitidos() {
		return mimesPermitidos;
	}

	public void setMimesPermitidos(List<String> mimesPermitidos) {
		this.mimesPermitidos = mimesPermitidos;
	}
	
}
