package mx.ine.procprimerinsa.helper;

import java.io.Serializable;

import mx.ine.procprimerinsa.helper.impl.HLPTransformadorMensajes.Mensaje;
import mx.ine.procprimerinsa.helper.impl.HLPTransformadorMensajes.TipoMensajes;

/**
 * Interfaz que define los métodos que deben implementarse para manejar los
 * mensajes a mostrar al usuario
 * 
 * @author Isabel Espinoza Espinoza (isabel.espinozae@ine.mx)
 * @version 1.0
 * @since 04/10/2016
 */
public interface HLPTransformadorMensajesInterface extends Serializable {
	/**
	 * Permite mostrar un mensaje generico (que corresponde a varios módulos)
	 * considerando el mensaje, el componente y tipo de mensaje que se desea
	 * mostrar
	 * 
	 * @author Isabel Espinoza Espinoza (isabel.espinozae@ine.mx)
	 * @version 1.0
	 * @since 04/10/2016
	 * @param tipoMensaje
	 *            : info: SEVERITY_INFO, warn: SEVERITY_WARN, error:
	 *            SEVERITY_ERROR
	 * @param componente
	 *            : nombre del componente donde va a aparecer, en caso de que
	 *            sea nulo lo mostrará en un componente llamado mensajesInfo,
	 *            mensajesAdvertencia, mensajesError
	 * @param clave
	 *            : para cuando sea generico
	 */
	void agregaMensajeGenerico(TipoMensajes tipoMensaje, String componente,
			Mensaje clave);

	/**
	 * Permite mostrar un mensaje personalizado considerando el mensaje, el
	 * componente y tipo de mensaje que se desea mostrar
	 * 
	 * @author Isabel Espinoza Espinoza (isabel.espinozae@ine.mx)
	 * @version 1.0
	 * @since 04/10/2016
	 * @param tipoMensaje
	 * @param componente
	 * @param mensaje
	 */
	void agregarMensajePersonalizado(TipoMensajes tipoMensaje,
			String componente, String mensaje);
}
