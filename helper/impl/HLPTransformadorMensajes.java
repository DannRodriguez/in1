package mx.ine.procprimerinsa.helper.impl;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.component.visit.VisitCallback;
import jakarta.faces.component.visit.VisitContext;
import jakarta.faces.component.visit.VisitResult;
import jakarta.faces.context.FacesContext;

import mx.ine.procprimerinsa.helper.HLPTransformadorMensajesInterface;
import mx.ine.procprimerinsa.util.Utilidades;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("hlpTransformadorMensajes")
@Scope("prototype")
public class HLPTransformadorMensajes implements HLPTransformadorMensajesInterface {

	private static final long serialVersionUID = 1960715458249588597L;
	
	private static final Logger log = LoggerFactory.getLogger(HLPTransformadorMensajes.class);

	@Override
	public void agregaMensajeGenerico(TipoMensajes tipoMensaje,
			String componente, Mensaje clave) {
		String mensaje = clave.getClaveMensajeSede();
		agregarMensajePersonalizado(tipoMensaje, componente, mensaje);
	}
	
	public static UIComponent findComponent(final String id) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();
		final UIComponent[] found = new UIComponent[1];

		try {
			if (root != null) {
				root.visitTree(VisitContext.createVisitContext(FacesContext.getCurrentInstance()),
						new VisitCallback() {
							@Override
							public VisitResult visit(VisitContext context,
									UIComponent component) {
								if (component != null && component.getId().equals(id)) {
									found[0] = component;
									return VisitResult.COMPLETE;
								}
								return VisitResult.ACCEPT;
							}
						});
			}
		} catch (Exception e) {
			log.error("Error  HPLTransformadorMensajes.findComponent", e);
		}

		return found[0];
	}

	@Override
	public void agregarMensajePersonalizado(TipoMensajes tipoMensaje,
			String componente, String mensaje) {
		FacesMessage message = new FacesMessage();
		if (tipoMensaje == null) {
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			if (componente == null) {
				componente = "mensajesInfo";
			}
		} else {
			if (tipoMensaje.toString().equals("INFO")) {
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				if (componente == null) {
					componente = "mensajesInfo";
				}
			} else if (tipoMensaje.toString().equals("WARNING")) {
				message.setSeverity(FacesMessage.SEVERITY_WARN);
				if (componente == null) {
					componente = "mensajesAdvertencia";
				}
			} else if (tipoMensaje.toString().equals("ERROR")) {
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				if (componente == null) {
					componente = "mensajesError";
				} else {
					UIComponent componenteId = findComponent(componente);
					String clientId = componenteId.getClientId();
					componente = clientId;
				}
			}
		}
		message.setSummary(mensaje);
		FacesContext.getCurrentInstance().addMessage(componente, message);
	}

	/**
	 * Enum para manejar textos de mensajes genéricos
	 * 
	 * @author Isabel Espinoza Espinoza (isabel.espinozae@ine.mx)
	 * @version 1.0
	 * @since 04/10/2016
	 */
	public enum Mensaje {
		// Definicion de claves de los mensajes
		/**
		 * Mensaje de exito al guardar
		 */
		GUARDA_OK("mensaje_generales_guardaExito"),
		/**
		 * Mensaje de que no se pudo guardar
		 */
		GUARDA_NO_OK("mensaje_generales_guardaNoExito"), /**
		 * Mensaje de exito al
		 * modificar
		 */
		MODIFICA_OK("mensaje_generales_modificaExito"), /**
		 * Mensaje de que no se
		 * pudo modificar
		 */
		MODIFICA_NO_OK("mensaje_generales_modificaNoExito"), /**
		 * Mensaje de exito
		 * al eliminar
		 */
		ELIMINA_OK("mensaje_generales_eliminaExito"), /**
		 * Mensaje de que no se
		 * pudo eliminar
		 */
		ELIMINA_NO_OK("mensaje_generales_eliminaNoExito"), /**
		 * Mensaje cuando no
		 * hay resultados de la consulta
		 */
		SIN_RESULTADOS("mensaje_generales_sinResultados"), /**
		 * Mensaje para
		 * fechas mayores a la actual
		 */
		FECHA_MAYOR_HOY("validacion_mensajes_generales_fechaMayorActual"), /**
		 * 
		 * Mensaje para fechas invalidas
		 */
		FECHA_INVALIDA("validacion_mensajes_generales_fecha_invalida");
		private final String claveMensajeSede;

		private Mensaje(String claveMensajeSede) {
			this.claveMensajeSede = claveMensajeSede;
		}

		/**
		 * @return el atributo claveMensajeSede
		 */
		public String getClaveMensajeSede() {
			return Utilidades.mensajeProperties(claveMensajeSede);
		}

	}

	/**
	 * Enum para tipo de mensajes
	 * 
	 * @author Isabel Espinoza Espinoza (isabel.espinozae@ine.mx)
	 * @version 1.0
	 * @since 04/10/2016
	 */
	public enum TipoMensajes {
		/**
		 * Tipo informativo
		 */
		INFO, /**
		 * Tipo advertencia
		 */
		WARNING, /**
		 * Tipo error
		 */
		ERROR;
	}
}
