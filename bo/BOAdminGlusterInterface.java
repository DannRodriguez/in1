package mx.ine.procprimerinsa.bo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;

import mx.ine.procprimerinsa.dto.DTODocumento;

public interface BOAdminGlusterInterface extends Serializable {
	
	public boolean existeArchivo(String ruta) throws IOException ;
	
	/**
	 * Genera el directorio principal (ruta completa) para el administrador del gluster
	 * @param idProcesoElectoral Identificador del proceso electoral
	 * @param idDetalleProceso Identificador del detalle proceso
	 * @return Cadena con el directorio principal de trabajo
	 */
	public String generaDirectorioPrincipal(Integer idProcesoElectoral, Integer idDetalleProceso);
	
	public boolean guardarArchivo(UploadedFile file, String rutaDestino);
	
	/**
	 * Función que sube múltiples archivos 
	 * @param ruta Ruta completa donde se subirán los archivos
	 * @param listaArchivos Lista de archivos a subir
	 * @return True si se subieron los archivos
	 */
	public boolean subirArchivos(String ruta, List<UploadedFile> listaArchivos);
	
	/**
	 * Función que permite crear nodos a partir de un nodo base y la ruta correspondiente
	 * @param directorio Directorio
	 * @param nodo Nodo del árbol
	 * @param ruta Ruta que genera
	 */
	public void crearDocumentos(File directorio, TreeNode<DTODocumento> nodo, String ruta);
	
	/**
	 * Elimina un archivo
	 * @param ruta Ruta completa donde se encuentra el archivo a eliminar
	 * @return True si se eliminó correctamente
	 * @throws IOException
	 */
	public boolean bajarArchivo(String ruta) throws IOException;
	
	/**
	 * Genera el árbol de directorio para el componente correspondiente
	 * @param directorio Directorio principal de trabajo(ruta completa)
	 * @return Árbol de directorio para el componente de primefaces
	 */
	public TreeNode<DTODocumento> generarArbol(String directorio);
	
	/**
	 * Función que crea una carpeta en el directorio especificado
	 * @param directorioPrincipal Directorio principal de trabajo del árbol de directorios
	 * @param ruta Ruta que se va generando de acuerdo a lo que el usuario selecciona en el componente tree
	 * @param nombreCarpeta Nombre de la carpeta a crear
	 * @return True si se creó la carpeta
	 */
	public boolean crearCarpeta(String directorioPrincipal, String ruta, String nombreCarpeta);
	
	/**
	 * Función que comprime los archivos de un directorio especificado
	 * @param directorioEntrada Ruta completa del directorio a comprimir
	 * @param nombreComprimido Nombre del archivo de salida
	 * @throws Exception
	 */
	public void comprimirArchivos(String directorioEntrada, String nombreComprimido) throws Exception;
	
	/**
	 * Función que descarga un archivo del servidor
	 * @param rutaArchivo Ruta completa del archivo
	 * @return True si la descarga finalizó correctamente
	 */
	public boolean descargarArchivo(String rutaArchivo);
}
