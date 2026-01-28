package mx.ine.procprimerinsa.bsd;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;

import mx.ine.procprimerinsa.dto.DTODocumento;

public interface BSDAdminGlusterInterface {
	
	public boolean existeArchivo(String ruta) throws IOException;
	
	/**
	 * Función que genera el árbol de directorios y documentos
	 * @param directorio Ruta principal desde donde se generará el árbol
	 */
	public TreeNode<DTODocumento> generarArbol(String directorio);
	
	/**
	 * Método que elimina un archivo 
	 * @author berenice.delaluz
	 * @param ruta
	 * @return true si se eliminó, false cuando no se eliminó
	 * @throws IOException
	 */
	public boolean bajarArchivo(String ruta) throws IOException;
	
	/**
	 * Método para crear los documentos que integran un directorio en el árbol
	 * @param directorio Directorio
	 * @param nodo Nodo
	 * @param ruta Ruta
	 */
	public void crearDocumentos(File directorio, TreeNode<DTODocumento> nodo, String ruta);
	
	public boolean guardarArchivo(UploadedFile file, String rutaDestino);
	
	/**
	 * Método que permite subir múltiples archivos
	 * @param ruta Directorio donde se subirán los archivos
	 * @param listaArchivos Lista que contiene los archivos a subir
	 */
	public boolean subirArchivos(String ruta, List<UploadedFile> listaArchivos);
	
	/**
	 * Método que genera el directorio principal donde se almacenan los archivos que genera
	 * el módulo de datos personales
	 * @param idProcesoElectoral Identificador del proceso electoral
	 * @param idDetalleProceso Identificador de detalle proceso
	 * @return
	 */
	public String generaDirectorioPrincipal(Integer idProcesoElectoral, Integer idDetalleProceso);
	
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
