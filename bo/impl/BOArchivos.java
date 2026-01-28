package mx.ine.procprimerinsa.bo.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import mx.ine.procprimerinsa.bo.BOArchivosInterface;
import mx.ine.procprimerinsa.dto.DTODocumento;
import mx.ine.procprimerinsa.dto.DTOEstadoGeneral;

@Component("boArchivos")
@Scope("prototype")
public class BOArchivos implements BOArchivosInterface {

	private static final Logger logger = Logger.getLogger(BOArchivos.class);
	
	@Override
	public boolean compruebaExistenciaArchivo(String rutaCompleta) {
		File archivo = new File(rutaCompleta);
		return archivo.exists();
	}
	
	@Override
	public Integer obtenerIdEstadoNombreArchivo(String nombreArchivo, String extension) {
		try {
			return Integer.parseInt(nombreArchivo.substring(2, nombreArchivo.lastIndexOf(extension)));
		} catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public boolean validarNombresArchivos(Map<Integer, DTOEstadoGeneral> estados, 
		List<UploadedFile> listaArchivos) {
		for(UploadedFile archivo : listaArchivos) {
			Integer idEstado = obtenerIdEstadoNombreArchivo(archivo.getFileName(), ".txt");
			if(idEstado == null) return false;
			if(!estados.containsKey(idEstado)) return false;
		}
		
		return true;
	}
	
	@Override
	public TreeNode<DTODocumento> generarArbol(String directorio) {
		TreeNode<DTODocumento> nodoPrincipal = new DefaultTreeNode<>(new DTODocumento("Files", 
																					"-", 
																					DTODocumento.FOLDER), 
																	null);
		
		crearDocumentos(new File(directorio), nodoPrincipal, "");
		
		return nodoPrincipal;
	}
	
	private void crearDocumentos(File directorio, TreeNode<DTODocumento> nodo, String ruta) {
        File[] listFile = directorio.listFiles();
        
        if(listFile == null) return;
        
        for (int i = 0; i < listFile.length; i++) {
            if (listFile[i].isDirectory()) {
            	new DefaultTreeNode<>(new DTODocumento(listFile[i].getName(), 
            											"-", 
            											DTODocumento.FOLDER, 
            											ruta), 
            						nodo);
            } else {
            	new DefaultTreeNode<>(DTODocumento.FILE, 
            						new DTODocumento(listFile[i].getName(), 
            										"-", 
            										DTODocumento.FILE, 
            										ruta), 
            						nodo);
            }
        }
	}

	@Override
	public void comprimirArchivos(String directorioEntrada, Set<String> filesToInclude, 
			String nombreComprimido) throws IOException {
		File inputDirectory = new File(directorioEntrada);
        List<File> listFiles = new ArrayList<>();  
        listFiles(listFiles, inputDirectory);
        createZipFile(listFiles, inputDirectory, filesToInclude, nombreComprimido);
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private  List<File> listFiles(List listFiles, File inputDirectory) throws IOException {
    	File inputDirectoryV = inputDirectory.getCanonicalFile();
        File[] allFiles = inputDirectoryV.listFiles();
        
        for (File file : allFiles) {
            if (file.isDirectory()) {
                listFiles(listFiles, file.getCanonicalFile());
            } else {
                listFiles.add(file.getCanonicalFile());
            }
        }
        
        return listFiles;
    }
    
    private void createZipFile(List<File> listaArchivos, File inputDirectory, Set<String> filesToInclude, 
    		String nombreZip) throws IOException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	ZipOutputStream zipOutputStream = new ZipOutputStream(baos);
    	zipOutputStream.setLevel(9);
    	
    	for (File file : listaArchivos) {	
    		if(!filesToInclude.contains(file.getName())) continue;
    		
			String filePath = file.getCanonicalPath();
			int lengthDirectoryPath = inputDirectory.getCanonicalPath().length();
			int lengthFilePath = file.getCanonicalPath().length();
			
			String zipFilePath = filePath.substring(lengthDirectoryPath + 1, lengthFilePath);
			
			ZipEntry zipEntry = new ZipEntry(zipFilePath);
			zipOutputStream.putNextEntry(zipEntry);
			
			try(FileInputStream inputStream = new FileInputStream(file)) {
				byte[] bytes = new byte[1024];
				int length;
				while ((length = inputStream.read(bytes)) >= 0) {
					zipOutputStream.write(bytes, 0, length);
				}
				zipOutputStream.closeEntry();
			}
		}
    	
		zipOutputStream.close();			
		
		FacesContext context = FacesContext.getCurrentInstance();
		escribirRespuestaEnZip(context.getExternalContext(), baos, nombreZip);
		context.responseComplete();		
	}
    
	private void escribirRespuestaEnZip(ExternalContext externalContext, ByteArrayOutputStream baos, 
		String nombreArchivo) throws IOException{
		externalContext.responseReset();
		externalContext.setResponseContentType("application/zip");
		externalContext.setResponseHeader("Expires", "0");
		externalContext.setResponseHeader(
				"Cache-Control", "must-revalidate, post-check=0, pre-check=0, no-cache, no-store");
		externalContext.setResponseHeader("Pragma", "no-cache");
		externalContext.setResponseHeader(
				"Content-disposition", "attachment;filename=" + nombreArchivo + ".zip");
		externalContext.setResponseContentLength(baos.size());
		OutputStream out = externalContext.getResponseOutputStream();
		baos.writeTo(out);
		externalContext.responseFlushBuffer();
		
		out.flush();
		out.close();
		
		baos.flush();
		baos.close();
	}

	@Override
	public boolean subirArchivos(String ruta, List<UploadedFile> listaArchivos) {
		try {
			for(UploadedFile file : listaArchivos){
				StringBuilder archivo = new StringBuilder();
				archivo.append(ruta);
				archivo.append(file.getFileName());
				
				File fileRename = new File(archivo.toString());
				FileUtils.writeByteArrayToFile(fileRename, file.getContent());
			}
			return true;
		} catch (IOException e) {
			logger.error("ERROR BOArchivos -subirArchivos: ", e);
			return false;
		}
	}

	@Override
	public boolean bajarArchivo(String ruta) {
		try {
			File rutaArchivo = new File(ruta);	
			
			if(!rutaArchivo.exists()) return false;
			
			if(rutaArchivo.isDirectory()) {
				return FileSystemUtils.deleteRecursively(rutaArchivo);				
			} else {
				return rutaArchivo.delete();
			}
		} catch (Exception e) {
			logger.error("ERROR BOArchivos -bajarArchivo: ", e);
			return false;
		}
	}
	
}
