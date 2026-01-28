package mx.ine.procprimerinsa.batch.itemswriters;

import java.math.BigDecimal;
import java.util.Date;

import org.jboss.logging.Logger;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;

import mx.ine.procprimerinsa.as.ASAdministradorDatosPersonalesInterface;
import mx.ine.procprimerinsa.dto.DTODatosPersonales;
import mx.ine.procprimerinsa.dto.db.DTOEstatusDatosPersonales;
import mx.ine.procprimerinsa.util.Constantes;

public class IWDGValidarDatosPersonales implements ItemWriter<DTODatosPersonales>, InitializingBean, DisposableBean {
	
	private static final Logger logger = Logger.getLogger(IWDGValidarDatosPersonales.class);
	
	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	private Integer idEstado;
	private String usuario;
	
	private Integer numeroLineas = 0;
	
	@Autowired
	@Qualifier("asAdministradorDatosPersonales")
	private ASAdministradorDatosPersonalesInterface asAdministradorDatosPersonales;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	}
	
	@Override
	public void destroy() throws Exception {
		Integer ciudadanosInsaculados = asAdministradorDatosPersonales.contarCiudadanosInsaculadosPorEstado(
																			idDetalleProceso, 
																			idEstado);
		
		DTOEstatusDatosPersonales estatusDatosPersonales = asAdministradorDatosPersonales.obtenerEstatusDatosPersonales(
																			idProcesoElectoral, 
																			idDetalleProceso, 
																			idEstado);
		
		estatusDatosPersonales.setUsuario(usuario);
		estatusDatosPersonales.setFechaHora(new Date());
		estatusDatosPersonales.setCiudadanosCarga(numeroLineas);
		
		if(ciudadanosInsaculados.equals(numeroLineas)) {
			estatusDatosPersonales.setIdAccion(Constantes.ESTATUS_DATOS_PERSONALES_VALIDADO);
			estatusDatosPersonales.setEstatus(null);
		} else {
			estatusDatosPersonales.setIdAccion(Constantes.ESTATUS_DATOS_PERSONALES_NO_VALIDO);
			estatusDatosPersonales.setEstatus("El archivo no cumple con las reglas de validación");
			logger.error("El número de ciudadanos del archivo " 
						+ idProcesoElectoral + "/" + idDetalleProceso + "/" + idEstado
						+ " es menor/mayor al de base de datos o no cumplen con la validación: Archivo: " 
						+ numeroLineas 
						+ " - Base de datos: " 
						+ ciudadanosInsaculados);	
		}
		
		asAdministradorDatosPersonales.actualizarEstatusDatosPersonales(estatusDatosPersonales);
	}

	@Override
	public void write(@NonNull Chunk<? extends DTODatosPersonales> items) throws Exception {
		numeroLineas += new BigDecimal(items.getItems().stream().filter(i -> i != null).count()).intValue();
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
