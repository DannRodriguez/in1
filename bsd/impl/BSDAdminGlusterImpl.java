package mx.ine.procprimerinsa.bsd.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASAdminGlusterInterface;
import mx.ine.procprimerinsa.bsd.BSDAdminGlusterInterface;
import mx.ine.procprimerinsa.dto.DTODocumento;

@Component("bsdAdminGlusterImpl")
@Scope("prototype")
public class BSDAdminGlusterImpl implements BSDAdminGlusterInterface {
	
	@Autowired
	@Qualifier("asAdminGlusterImpl")
	private ASAdminGlusterInterface asAdminGluster;
	
	@Override
	public boolean existeArchivo(String ruta) throws IOException {
		return asAdminGluster.existeArchivo(ruta);		
	}
	
	@Override
	public TreeNode<DTODocumento> generarArbol(String directorio) {
		return asAdminGluster.generarArbol(directorio);
	}

	@Override
	public boolean bajarArchivo(String ruta) throws IOException {
		return asAdminGluster.bajarArchivo(ruta);
	}

	@Override
	public void crearDocumentos(File directorio, TreeNode<DTODocumento> nodo, String ruta) {
		asAdminGluster.crearDocumentos(directorio, nodo, ruta);
	}
	
	@Override
	public boolean guardarArchivo(UploadedFile file, String rutaDestino) {
		return asAdminGluster.guardarArchivo(file, rutaDestino);
	}

	@Override
	public boolean subirArchivos(String ruta, List<UploadedFile> listaArchivos) {
		return asAdminGluster.subirArchivos(ruta, listaArchivos);
	}

	@Override
	public String generaDirectorioPrincipal(Integer idProcesoElectoral, Integer idDetalleProceso) {
		return asAdminGluster.generaDirectorioPrincipal(idProcesoElectoral, idDetalleProceso);
	}

	@Override
	public boolean crearCarpeta(String directorioPrincipal, String ruta, String nombreCarpeta) {
		return asAdminGluster.crearCarpeta(directorioPrincipal, ruta, nombreCarpeta);
	}

	@Override
	public void comprimirArchivos(String directorioEntrada, String nombreComprimido) throws Exception {
		asAdminGluster.comprimirArchivos(directorioEntrada, nombreComprimido);		
	}

	@Override
	public boolean descargarArchivo(String rutaArchivo) {
		return asAdminGluster.descargarArchivo(rutaArchivo);
	}

}
