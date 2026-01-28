package mx.ine.procprimerinsa.bo.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.NameFileComparator;
import org.jboss.logging.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import mx.ine.procprimerinsa.bo.BOAdminGlusterInterface;
import mx.ine.procprimerinsa.dto.DTODocumento;
import mx.ine.procprimerinsa.util.Constantes;

@Component("boAdminGlusterImpl")
@Scope("prototype")
public class BOAdminGlusterImpl implements BOAdminGlusterInterface {

	private static final long serialVersionUID = 8324906505095260083L;
	private static final Logger logger = Logger.getLogger(BOAdminGlusterImpl.class);
	
	@Override
	public boolean existeArchivo(String ruta) throws IOException {
		File rutaArchivo = new File(ruta);
		return rutaArchivo.exists();
	}

	@Override
	public String generaDirectorioPrincipal(Integer idProcesoElectoral, Integer idDetalleProceso) {
		return new StringBuilder(Constantes.RUTA_LOCAL_FS)
					.append(idProcesoElectoral)
					.append(File.separator)
					.append(idDetalleProceso)
					.append(File.separator)
					.append(Constantes.CARPETA_PROC_GLUSTER_INSA1)
					.append(File.separator).toString();
	}
	
	@Override
	public boolean guardarArchivo(UploadedFile file, String rutaDestino) {
		try {
			Files.deleteIfExists(Paths.get(rutaDestino));
			File newFile = new File(rutaDestino);
			FileUtils.writeByteArrayToFile(newFile, file.getContent());
			return true;
		} catch(IOException e) {
			logger.error("ERROR BOAdminGlusterImpl -guardarArchivo: ", e);
			return false;
		}
	}

	@Override
	public boolean subirArchivos(String ruta, List<UploadedFile> listaArchivos) {
		try {
			for(UploadedFile file : listaArchivos){
				StringBuilder archivo = new StringBuilder(ruta)
											.append(file.getFileName());
				File fileRename = new File(archivo.toString());
				FileUtils.writeByteArrayToFile(fileRename, file.getContent());
			}
			return true;
		} catch (IOException e) {
			logger.error("ERROR BOAdminGlusterImpl -subirArchivos: ", e);
			return false;
		}
	}

	@Override
	public void crearDocumentos(File directorio, TreeNode<DTODocumento> nodo, String ruta) {
        File[] listFile = directorio.listFiles();
        
        if(listFile == null) return;
        Arrays.sort(listFile, NameFileComparator.NAME_COMPARATOR);
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
	public boolean bajarArchivo(String ruta) throws IOException {
		File rutaArchivo = new File(ruta);		
		
		if(!rutaArchivo.exists()) {
			throw new IOException("El archivo o directorio no existe");
		}
			
		if(rutaArchivo.isDirectory()) {
			return FileSystemUtils.deleteRecursively(rutaArchivo);				
		} else {
			return rutaArchivo.delete();	
		}
		
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

	@Override
	public boolean crearCarpeta(String directorioPrincipal, String ruta, String nombreCarpeta) {
		StringBuilder rutaCarpeta = new StringBuilder(directorioPrincipal)
												.append(ruta)
												.append(nombreCarpeta);
		
		File carpeta = new File(rutaCarpeta.toString());
		
		return carpeta.mkdir();
	}

	@Override
	public void comprimirArchivos(String directorioEntrada, String nombreComprimido) throws Exception {
		File inputDirectory = new File(directorioEntrada);
		List<File> listFiles = new ArrayList<>();  
        listFiles(listFiles, inputDirectory);
        createZipFile(listFiles, inputDirectory, nombreComprimido);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
    private List<File> listFiles(List listFiles, File inputDirectory) throws IOException {
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
    
    private void createZipFile(List<File> listaArchivos, File inputDirectory, String nombreZip) throws IOException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	ZipOutputStream zipOutputStream = new ZipOutputStream(baos);
    	zipOutputStream.setLevel(9);
    	
    	for (File file : listaArchivos) {
				String filePath = file.getCanonicalPath();
				int lengthDirectoryPath = inputDirectory.getCanonicalPath().length();
				int lengthFilePath = file.getCanonicalPath().length();
				
				String zipFilePath = filePath.substring(lengthDirectoryPath + 1, lengthFilePath);
				
				ZipEntry zipEntry = new ZipEntry(zipFilePath);
				zipOutputStream.putNextEntry(zipEntry);
				
				try (FileInputStream inputStream = new FileInputStream(file)) {
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
		nombreArchivo += ".zip";
		externalContext.setResponseContentType("application/zip");
		externalContext.setResponseHeader("Expires", "0");
		externalContext.setResponseHeader(
				"Cache-Control", "must-revalidate, post-check=0, pre-check=0, no-cache, no-store");
		externalContext.setResponseHeader("Pragma", "no-cache");
		externalContext.setResponseHeader(
				"Content-disposition", "attachment;filename=" + nombreArchivo);
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
	public boolean descargarArchivo(String rutaArchivo) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			File file = new File(rutaArchivo);
			
			if(!file.exists()) 
				return false;
			
        	try(FileInputStream stream = new FileInputStream(file)) {
	        	stream.transferTo(baos);
	        	   
	       		externalContext.responseReset();
	    		externalContext.setResponseContentType("application/download");
	    		externalContext.setResponseHeader("Expires", "0");
	    		externalContext.setResponseHeader(
	    				"Cache-Control", "must-revalidate, post-check=0, pre-check=0, no-cache, no-store");
	    		externalContext.setResponseHeader("Pragma", "no-cache");
	    		externalContext.setResponseHeader(
	    				"Content-disposition", "attachment;filename=" + file.getName());
	    		externalContext.setResponseContentLength(baos.size());
	    		OutputStream out = externalContext.getResponseOutputStream();
	    		baos.writeTo(out);
	    		externalContext.responseFlushBuffer();
	    		
	    		out.flush();
	    		out.close();
	    		
	    		baos.flush();
	    		baos.close();
        	}
    		
    		context.responseComplete();	
    		return true;
		} catch(Exception e) {
			logger.error("ERROR BOAdminGlusterImpl -descargarArchivo: ", e);
			return false;
		}
	}
	
}
