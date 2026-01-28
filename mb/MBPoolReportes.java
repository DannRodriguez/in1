package mx.ine.procprimerinsa.mb;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;

import org.jboss.logging.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.procprimerinsa.bsd.BSDMapasInterface;
import mx.ine.procprimerinsa.bsd.BSDPoolReportesInterface;
import mx.ine.procprimerinsa.dto.DTOAccesosSistema;
import mx.ine.procprimerinsa.dto.DTOPoolReportes;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@Qualifier("mbPoolReportes")
@RequestScoped
public class MBPoolReportes implements Serializable {
	
	private static final long serialVersionUID = 3802269789189727473L;
	private static final Logger logger = Logger.getLogger(MBPoolReportes.class);

	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdPoolReportes")
	private BSDPoolReportesInterface bsdPoolReportes;
	
	@Autowired
	@Qualifier("bsdMapas")
	private BSDMapasInterface bsdMapas;

	private List<DTOPoolReportes> datosInsaculados;
	private List<DTOAccesosSistema> accesosInsa;
	private List<Integer> detalles;
	private Date fecha = new Date();
	private boolean procesoValido;
	private String mensaje;
	
	public void init() {
		try {
			mbAdmin.getAdminData().setIdModulo(Constantes.ID_MODULO_POOL_REPORTES);
			
			String menuValido = mbAdmin.verificaElementosMenu(true, false, false);
			
			if (!menuValido.isEmpty()) {
				procesoValido = false;
				mensaje = menuValido;
				return;
			} 
			
			detalles = bsdMapas.getDetallesProceso(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
												mbAdmin.getAdminData().getTipoProceso());
			datosInsaculados = bsdPoolReportes.obtenerDatosInsaculados(detalles, 
												mbAdmin.getAdminData().getProcesoSeleccionado().getCorte());
			cambiaFecha();
			
			procesoValido = true;
		} catch (Exception e) {
			procesoValido = false;
			mensaje = Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo");
			logger.error("ERROR MBPoolReportes -init: ", e);		
		}
	}

	public void cambiaFecha() {
		accesosInsa = bsdPoolReportes.obtenerAccesosSistema(
						Integer.valueOf(ResourceBundle.getBundle("ApplicationConfig").getString("application.id")), 
						fecha);
	}
	
	public DefaultStreamedContent generarTxt() {
		
		try(ByteArrayInputStream bis = new ByteArrayInputStream(bsdPoolReportes.crearZip(
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													detalles, 
													mbAdmin.getAdminData().getProcesoSeleccionado().getCorte(),
													fecha));
			InputStream stream = bis) {
			return DefaultStreamedContent.builder()
									.contentType("zip")
									.name("spool.zip")
									.stream(() -> stream)
									.build();
		} catch(Exception e) {
			logger.error("ERROR MBPoolReportes -generarTxt:", e);
			return null;
		}
	    
	}
	
	public List<DTOPoolReportes> getDatosInsaculados() {
		return datosInsaculados;
	}

	public List<DTOAccesosSistema> getAccesosInsa() {
		return accesosInsa;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isProcesoValido() {
		return procesoValido;
	}

	public String getMensaje() {
		return mensaje;
	}
	
}
