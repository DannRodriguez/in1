package mx.ine.procprimerinsa.bo;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;

import mx.ine.procprimerinsa.dto.DTODocumento;
import mx.ine.procprimerinsa.dto.DTOEstadoGeneral;

public interface BOArchivosInterface {
	
	public boolean compruebaExistenciaArchivo(String rutaCompleta);
	
	public Integer obtenerIdEstadoNombreArchivo(String nombreArchivo, String extension);
	
	public boolean validarNombresArchivos(Map<Integer, DTOEstadoGeneral> estados, List<UploadedFile> listaArchivos);
	
	public TreeNode<DTODocumento> generarArbol(String directorio);
			
	public void comprimirArchivos(String directorioEntrada, Set<String> filesToInclude, 
			String nombreComprimido) throws IOException;
	
	public boolean subirArchivos(String ruta, List<UploadedFile> listaArchivos);
	
	public boolean bajarArchivo(String ruta);
	
}
