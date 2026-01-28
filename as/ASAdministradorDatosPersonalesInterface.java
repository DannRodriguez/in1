package mx.ine.procprimerinsa.as;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;

import mx.ine.procprimerinsa.dto.DTOArchivoDatosPersonales;
import mx.ine.procprimerinsa.dto.DTODocumento;
import mx.ine.procprimerinsa.dto.DTOEstadoGeneral;
import mx.ine.procprimerinsa.dto.db.DTOEstatusDatosPersonales;

public interface ASAdministradorDatosPersonalesInterface extends Serializable {		
	
	public String generaDirectorioPrincipal(Integer idProcesoElectoral, Integer idDetalleProceso);
	
	public String generaNombreArchivo(Integer idEstado);
	
	public String generaDirectorioDatosDerfe(Integer idProcesoElectoral, Integer idDetalleProceso);
	
	public String generarNombreArchivoCarga(Integer idEstado);
	
	public boolean compruebaExistenciaArchivo(String rutaCompleta);
	
	public TreeNode<DTODocumento> generarArbol(String directorio);
	
	public List<DTOArchivoDatosPersonales> obtenerListaEstatusDatosPersonales(Integer idProceso, 
			Integer idDetalle, Integer idCorte);
	
	public String generarArchivoDatosMinimosPorEstado(Integer idProceso, Integer idDetalle, Integer idEstado,
			String usuario);
	
	public String validarArchivosDatosPersonalesPorEstado(Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idEstado, String rutaArchivo, String usuario, String patternToValidate, String encoding);
	
	public String actualizarDatosPersonalesPorEstado(Integer idProceso, String tipoEleccion,
			Integer idDetalle, Integer idEstado, String rutaArchivo, String encoding);
	
	public Integer contarCiudadanosInsaculadosPorEstado(Integer idDetalleProceso, Integer idEstado);
	
	public DTOEstatusDatosPersonales obtenerEstatusDatosPersonales(Integer idProceso, 
			Integer idDetalle, Integer idEstado);

	public void actualizarEstatusDatosPersonales(DTOEstatusDatosPersonales estatusDatosPersonales);
	
	public void actualizarEstatusDatosPersonalesEliminar(Integer idProcesoElectoral, Integer idDetalleProceso, 
			String nombreArchivo, String usuario);
	
	public void comprimirArchivos(String directorioEntrada, Set<String> filesToInclude, 
			String nombreComprimido) throws Exception;
		
	public boolean subirArchivos(Integer idProceso, Integer idDetalle, Map<Integer, DTOEstadoGeneral> estados,
			String ruta, List<UploadedFile> listaArchivos, String usuario);
		
	public boolean bajarArchivo(String ruta);
	
}
