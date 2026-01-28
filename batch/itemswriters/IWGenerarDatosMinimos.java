package mx.ine.procprimerinsa.batch.itemswriters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;

import mx.ine.procprimerinsa.as.ASAdministradorDatosPersonalesInterface;
import mx.ine.procprimerinsa.dto.db.DTODatosInsaculados;
import mx.ine.procprimerinsa.dto.db.DTOEstatusDatosPersonales;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.PGPFileProcessor;

public class IWGenerarDatosMinimos implements ItemWriter<DTODatosInsaculados>, InitializingBean, DisposableBean {
		
	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	private Integer idEstado;
	private String usuario;
	
	private String ruta;
	private BufferedWriter writer;
	private Integer count = 0;
	
	@Autowired
	@Qualifier("asAdministradorDatosPersonales")
	private ASAdministradorDatosPersonalesInterface asAdministradorDatosPersonales;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		ruta = asAdministradorDatosPersonales.generaDirectorioPrincipal(idProcesoElectoral, 
																		idDetalleProceso);
		File directorio = new File(ruta);

		directorio.mkdirs();

		ruta += asAdministradorDatosPersonales.generaNombreArchivo(idEstado);

		writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(ruta), StandardCharsets.ISO_8859_1));
		
		writer.write("ID_ESTADO|ID_DISTRITO|CLAVE_ELECTOR" + Constantes.DEFAULT_LINE_SEPARATOR);
		
	}
	
	@Override
	public void destroy() throws Exception {
		writer.close();
		
		PGPFileProcessor p = new PGPFileProcessor();
		StringBuilder certificado =  new StringBuilder(Constantes.RUTA_LOCAL_FS)
				.append(idProcesoElectoral).append(File.separator)
				.append(idDetalleProceso).append(File.separator) 
				.append(Constantes.CARPETA_PROC_GLUSTER_INSA1).append(File.separator)
				.append(Constantes.CARPETA_LLAVE_PUBLICA).append(File.separator)
				.append("llave.asc");
		 
        p.setKeyFile(certificado.toString());
        p.setInputFile(ruta);
        p.setOutputFile(ruta + ".gpg");
        p.encrypt();
        
		new File(ruta).delete();
		
		DTOEstatusDatosPersonales estatusDatosPersonales = asAdministradorDatosPersonales.obtenerEstatusDatosPersonales(
																				idProcesoElectoral, 
																				idDetalleProceso, 
																				idEstado);
		estatusDatosPersonales.setIdAccion(Constantes.ESTATUS_DATOS_PERSONALES_GENERADO);
		estatusDatosPersonales.setEstatus(null);
		estatusDatosPersonales.setCiudadanosDescarga(count);
		estatusDatosPersonales.setCiudadanosCarga(0);
		estatusDatosPersonales.setUsuario(usuario);
		estatusDatosPersonales.setFechaHora(new Date());
		
		asAdministradorDatosPersonales.actualizarEstatusDatosPersonales(estatusDatosPersonales);
	}

	@Override
	public void write(@NonNull Chunk<? extends DTODatosInsaculados> items) throws Exception {
		StringBuilder fila = new StringBuilder();
		
		for(DTODatosInsaculados i : items.getItems()) {
			fila.append(i.getIdEstado()).append("|");
			fila.append(i.getIdDistritoFederal()).append("|");
			fila.append(i.getNumeroCredencialElector());
			fila.append(Constantes.DEFAULT_LINE_SEPARATOR);
			count++;
		}
		
		writer.write(fila.toString());
	}

	public Integer getIdProcesoElectoral() {
		return idProcesoElectoral;
	}

	public void setIdProcesoElectoral(Integer idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}

	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}
