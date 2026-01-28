package mx.ine.procprimerinsa.as;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;

import mx.ine.procprimerinsa.dto.DTODocumento;

public interface ASAdminGlusterInterface {
	
	public boolean existeArchivo(String ruta) throws IOException;
	
	public TreeNode<DTODocumento> generarArbol(String directorio);
	
	public boolean bajarArchivo(String ruta) throws IOException;
	
	public void crearDocumentos(File directorio, TreeNode<DTODocumento> nodo, String ruta);
	
	public boolean guardarArchivo(UploadedFile file, String rutaDestino);
	
	public boolean subirArchivos(String ruta, List<UploadedFile> listaArchivos);
	
	public String generaDirectorioPrincipal(Integer idProcesoElectoral, Integer idDetalleProceso);
	
	public boolean crearCarpeta(String directorioPrincipal, String ruta, String nombreCarpeta);
	
	public void comprimirArchivos(String directorioEntrada, String nombreComprimido) throws Exception;
	
	public boolean descargarArchivo(String rutaArchivo);
}
