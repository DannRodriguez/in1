package mx.ine.procprimerinsa.bsd.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASAdministradorDatosPersonalesInterface;
import mx.ine.procprimerinsa.bsd.BSDAdministradorDatosPersonalesInterface;
import mx.ine.procprimerinsa.dto.DTOArchivoDatosPersonales;
import mx.ine.procprimerinsa.dto.DTODocumento;
import mx.ine.procprimerinsa.dto.DTOEstadoGeneral;
import mx.ine.procprimerinsa.dto.db.DTOEstatusDatosPersonales;

@Component("bsdAdministradorDatosPersonales")
@Scope("prototype")
public class BSDAdministradorDatosPersonales implements BSDAdministradorDatosPersonalesInterface {
		
	private static final long serialVersionUID = -1759029688129651099L;

	@Autowired
	@Qualifier("asAdministradorDatosPersonales")
	private ASAdministradorDatosPersonalesInterface asAdministradorDatosPersonales;
	
	@Override
	public String generaDirectorioPrincipal(Integer idProcesoElectoral, Integer idDetalleProceso) {
		return asAdministradorDatosPersonales.generaDirectorioPrincipal(idProcesoElectoral, idDetalleProceso);
	}
	
	@Override
	public String generaNombreArchivo(Integer idEstado) {
		return asAdministradorDatosPersonales.generaNombreArchivo(idEstado);
	}
	
	@Override
	public String generaDirectorioDatosDerfe(Integer idProcesoElectoral, Integer idDetalleProceso) {
		return asAdministradorDatosPersonales.generaDirectorioDatosDerfe(idProcesoElectoral, idDetalleProceso);
	}
	
	@Override
	public String generarNombreArchivoCarga(Integer idEstado) {
		return asAdministradorDatosPersonales.generarNombreArchivoCarga(idEstado);
	}
	
	@Override
	public boolean compruebaExistenciaArchivo(String rutaCompleta) {
		return asAdministradorDatosPersonales.compruebaExistenciaArchivo(rutaCompleta);
	}
	
	@Override
	public TreeNode<DTODocumento> generarArbol(String directorio) {
		return asAdministradorDatosPersonales.generarArbol(directorio);
	}
	
	@Override
	public List<DTOArchivoDatosPersonales> obtenerListaEstatusDatosPersonales(Integer idProceso, 
			Integer idDetalle, Integer idCorte) {
		return asAdministradorDatosPersonales.obtenerListaEstatusDatosPersonales(idProceso, idDetalle, idCorte);
	}
	
	@Override
	public String generarArchivoDatosMinimosPorEstado(Integer idProceso, Integer idDetalle, Integer idEstado,
			String usuario) {
		return asAdministradorDatosPersonales.generarArchivoDatosMinimosPorEstado(idProceso, idDetalle, idEstado, usuario);
	}
	
	@Override
	public String validarArchivosDatosPersonalesPorEstado(Integer idProcesoElectoral, Integer idDetalleProceso,
			Integer idEstado, String rutaArchivo, String usuario, String patternToValidate, String encoding) {
		return asAdministradorDatosPersonales.validarArchivosDatosPersonalesPorEstado(idProcesoElectoral, 
				idDetalleProceso, idEstado, rutaArchivo, usuario, patternToValidate, encoding);
	}
	
	@Override
	public String actualizarDatosPersonalesPorEstado(Integer idProceso, String tipoEleccion,
			Integer idDetalle, Integer idEstado, String rutaArchivo, String encoding) {
		return asAdministradorDatosPersonales.actualizarDatosPersonalesPorEstado(idProceso,
																				tipoEleccion,
																				idDetalle,
																				idEstado, 
																				rutaArchivo,
																				encoding);
	}

	@Override
	public DTOEstatusDatosPersonales obtenerEstatusDatosPersonales(Integer idProcesoElectoral, 
			Integer idDetalleProceso, Integer idEstado) {
		return asAdministradorDatosPersonales.obtenerEstatusDatosPersonales(idProcesoElectoral, 
				idDetalleProceso, idEstado);
	}

	@Override
	public void actualizarEstatusDatosPersonales(DTOEstatusDatosPersonales estatusDatosPersonales) {
		asAdministradorDatosPersonales.actualizarEstatusDatosPersonales(estatusDatosPersonales);		
	}
	
	@Override
	public void actualizarEstatusDatosPersonalesEliminar(Integer idProcesoElectoral, Integer idDetalleProceso,
			String nombreArchivo, String usuario) {
		asAdministradorDatosPersonales.actualizarEstatusDatosPersonalesEliminar(idProcesoElectoral, idDetalleProceso, 
				nombreArchivo, usuario);
	}
	
	@Override
	public void comprimirArchivos(String directorioEntrada, Set<String> filesToInclude, 
			String nombreComprimido) throws Exception {
		asAdministradorDatosPersonales.comprimirArchivos(directorioEntrada, filesToInclude, nombreComprimido);
	}
	
	@Override
	public boolean subirArchivos(Integer idProceso, Integer idDetalle, Map<Integer, DTOEstadoGeneral> estados,
			String ruta, List<UploadedFile> listaArchivos, String usuario) {
		return asAdministradorDatosPersonales.subirArchivos(idProceso, idDetalle, estados, ruta, listaArchivos,
				usuario);
	}

	@Override
	public boolean bajarArchivo(String ruta) {
		return asAdministradorDatosPersonales.bajarArchivo(ruta);
	}
	
}
