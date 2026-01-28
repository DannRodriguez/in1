package mx.ine.procprimerinsa.as.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.procprimerinsa.as.ASAdminGlusterInterface;
import mx.ine.procprimerinsa.bo.BOAdminGlusterInterface;
import mx.ine.procprimerinsa.dto.DTODocumento;

@Scope("prototype")
@Service("asAdminGlusterImpl")
public class ASAdminGlusterImpl implements ASAdminGlusterInterface {
	
	@Autowired
	@Qualifier("boAdminGlusterImpl")
	private BOAdminGlusterInterface boAdminGluster;
	
	@Override
	public boolean existeArchivo(String ruta) throws IOException {
		return boAdminGluster.existeArchivo(ruta);
	}

	@Override
	public TreeNode<DTODocumento> generarArbol(String directorio) {
		return boAdminGluster.generarArbol(directorio);
	}

	@Override
	public boolean bajarArchivo(String ruta) throws IOException {
		return boAdminGluster.bajarArchivo(ruta);
	}

	@Override
	public void crearDocumentos(File directorio, TreeNode<DTODocumento> nodo, String ruta) {
		boAdminGluster.crearDocumentos(directorio, nodo, ruta);
	}
	
	@Override
	public boolean guardarArchivo(UploadedFile file, String rutaDestino) {
		return boAdminGluster.guardarArchivo(file, rutaDestino);
	}

	@Override
	public boolean subirArchivos(String ruta, List<UploadedFile> listaArchivos) {
		return boAdminGluster.subirArchivos(ruta, listaArchivos);
	}

	@Override
	public String generaDirectorioPrincipal(Integer idProcesoElectoral, Integer idDetalleProceso) {
		return boAdminGluster.generaDirectorioPrincipal(idProcesoElectoral, idDetalleProceso);
	}

	@Override
	public boolean crearCarpeta(String directorioPrincipal, String ruta, String nombreCarpeta) {
		return boAdminGluster.crearCarpeta(directorioPrincipal, ruta, nombreCarpeta);
	}

	@Override
	public void comprimirArchivos(String directorioEntrada, String nombreComprimido) throws Exception {
		boAdminGluster.comprimirArchivos(directorioEntrada, nombreComprimido);
	}

	@Override
	public boolean descargarArchivo(String rutaArchivo) {
		return boAdminGluster.descargarArchivo(rutaArchivo);
	}	
	
}
