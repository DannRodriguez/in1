package mx.ine.procprimerinsa.exception;

import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Clase INEException que implementa los métodos para el control de excepciones de los sistemas 
 * desarrollados en el INE
 * 
 * @author Berenice De la Luz Morales (berenice.delaluz@ine.mx) & Manuel Portillo Cedillo (manuel.portillo@ine)
 * @since 09/06/2017
 * @version 1.0
 * @copyright Direccion de sistemas - INE
 */
public class INEException extends Exception{

	/**
	 * Variable para la serialización de los objetos
	 */

	//Objeto BO para el envío de correos electrónicos
	//BOCorreoExcepcionInterface boCorreoExcepcion;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2855270497770348700L;

	/*************** ATRIBUTOS *****************/
	//Variable que almacena el nombre de la clase donde se genera la excepción
	private String nombreClase;
	
	//Variable que almacena el nombre del método de la clase donde se genera la excepción
	private String nombreMetodo;
	
	//Variable que almacena el número de línea de código donde se genera la excepción
	private Integer lineaError;
	
	//Variable que almacena la causa que genera la excepción
	private Throwable causa;
	
	//Variable que almacena la pila de la excepción en forma de arreglo
	private StackTraceElement elementos[];
	
	//Variable que almacena el mensaje dirigido a desarrollo para su interpretación
	private String mensajeDesarrollo;
	
	//Variable que almacena el mensaje dirigido al usuario
	private String mensajeUsuario;
	
	//Variable que almacena el nombre del proyecto origen
	private String nombreProyecto;
	
	//Variable que almancena el nombre del usuario en sesión
	private String usuario;
	
	//Variable que almacena la fecha y hora de la excepción
	private Date fechaHora;
	
	//Variable que indica si se hará una notificación de la excepción
	private boolean enviarNotificacion;
	
	//Variable que almacena la lista de direcciones de correo electrónico
	private List<String> correosNotificacion;
	
	//Variable que almacena el nombre de la capa donde ocurrió la excepción
	private String capa;
	
	//Variable que almancena la lista de parámetros recibidos
	private String parametros;
	
	/*************** CONSTRUCTOR *****************/
	/**
	 * Método para instanciar la excepción producida
	 * 
	 * @param mensajeDesarrollo
	 * @param mensajeUsuario
	 * @param causa
	 * @param enviarNotificacion
	 */
	public INEException(String mensajeDesarrollo, String mensajeUsuario, Throwable causa, String parametros, boolean enviarNotificacion){
		super();
		
		//Se obtienen los mensajes recibidos
		this.mensajeDesarrollo = mensajeDesarrollo;
		this.mensajeUsuario = mensajeUsuario;
		
		//Se obtiene la causa de la excepción y se procesa la pila de excepciones
		this.causa = causa;
		elementos = causa.getStackTrace();
		
		//Se obtiene el valor de la variable para saber si se requiere que se envíe una notificación
		this.enviarNotificacion = enviarNotificacion;
		
		//Se obtiene el nombre de la clase, el nombre del método y la línea de error
		nombreClase = elementos[0].getClassName();
		nombreMetodo = elementos[0].getMethodName();
		lineaError = elementos[0].getLineNumber();
		
		//Se obtiene el nombre de la capa donde ocurrió el error
		obtenerNombreCapa(nombreClase);
		
		//Se obtienen los parámetros recibidos
		this.parametros = parametros;
		
		//Se obtiene el nombre del usuario, fecha y hora de la excepcion
		usuario = SecurityContextHolder.getContext().getAuthentication().getName();
		fechaHora = new Date();
		
		if(enviarNotificacion){
			String errorCorreo = "ERROR: "+ nombreClase + "." + nombreMetodo + "()\n\tLinea: " + lineaError + 
					"\tCapa: " + capa + "\tUsuario: " + usuario + "\tFecha: " + fechaHora + 
					"\tMensaje: " + mensajeDesarrollo+"\tMensaje usuario: " + mensajeUsuario+ "\nParámetros: " + parametros +"\nCausa:"+causa.getStackTrace();
			
			enviarCorreo(errorCorreo);
		}
	}
	
	/*************** MÉTODOS DE LA CLASE ***************/
	/**
	 * Método para imprimir los datos de la excepción generada, en el log
	 * 
	 * @author Manuel Portillo Cedillo (manuel.portillo@ine.mx)
	 * @since 09/06/2017
	 */
	public void imprimirExcepcionLog(){
		
		//Se instancia el logger
		Logger logger = Logger.getLogger(nombreClase);
		
		//Se realiza la impresión en el log
		logger.error("ERROR: "+ nombreClase + "." + nombreMetodo + "()\n\tLinea: " + lineaError + 
				"\tCapa: " + capa + "\tUsuario: " + usuario + "\tFecha: " + fechaHora + 
				"\tMensaje: " + mensajeDesarrollo + "\nParámetros: " + parametros + "\nCausa",causa);
 	}
	
	/**
	 * Método para obtener el nombre de la capa a partir del nombre de la clase
	 * 
	 * @author Manuel Portillo Cedillo (manuel.portillo@ine.mx)
	 * @since 09/06/2017
	 */
	protected void obtenerNombreCapa(String clase){
		if(clase.contains("mb"))
			capa = "MB";
		else if(clase.contains("bsd"))
			capa = "BSD";
		else if(clase.contains("as"))
			capa = "AS";
		else if(clase.contains("dao"))
			capa = "DAO";
		else if(clase.contains("form"))
			capa = "FORM";
		else if(clase.contains("helper"))
			capa = "HELPER";
		else if(clase.contains("util"))
			capa = "UTIL";
		else
			capa = "No se puede identificar la capa";
	}
	
	/**
	 * Método que sirve para enviar correo con el error.
	 * 
	 * @author Bere De la luz
	 * @param errorCorreo
	 * @since 09/06/2017
	 */
	public void enviarCorreo(String errorCorreo){
//		try {
//			boCorreoExcepcion = new BOCorreoExcepcion();
//			boCorreoExcepcion.enviarExcepcion(errorCorreo, causa);
//		} catch (ApplicationException e) {
//			logger.error("Error al tratar de enviar el correo de Error",e);
//		}
	}
	/*************** GETTERS & SETTERS *****************/
	/**
	 * Método para obtener el nombre de la clase donde se generó la excepción
	 * @return String
	 */
	public String getNombreClase() {
		return nombreClase;
	}

	/**
	 * Método para asignar el nombre de la clase donde se generó la excepción
	 * @param nombreClase
	 */
	public void setNombreClase(String nombreClase) {
		this.nombreClase = nombreClase;
	}

	/**
	 * Método para obtener el nombre del método de la clase donde se generó la excepción
	 * @return String
	 */
	public String getNombreMetodo() {
		return nombreMetodo;
	}

	/**
	 * Método para asignar el nombre del método de la clase donde se generó la excepción
	 * @param nombreMetodo
	 */
	public void setNombreMetodo(String nombreMetodo) {
		this.nombreMetodo = nombreMetodo;
	}

	/**
	 * Método para obtener el número de línea donde se generó la excepción
	 * @return Integer
	 */
	public Integer getLineaError() {
		return lineaError;
	}

	/**
	 * Método para asignar el número de línea donde se generó la excepción
	 * @param lineaError
	 */
	public void setLineaError(Integer lineaError) {
		this.lineaError = lineaError;
	}

	/**
	 * Método para obtener la pila de la excepción que se generó
	 * @return Throwable
	 */
	public Throwable getCausa() {
		return causa;
	}

	/**
	 * Método para asignar la pila de la excepción que se generó
	 * @param causa
	 */
	public void setCausa(Throwable causa) {
		this.causa = causa;
	}

	/**
	 * Método para obtener la pila de la excepción en forma de arreglo
	 * @return StackTraceElement[]
	 */
	public StackTraceElement[] getElementos() {
		return elementos;
	}

	/**
	 * Método para asginar la pila de la excepción en forma de arreglo
	 * @param elements
	 */
	public void setElementos(StackTraceElement[] elementos) {
		this.elementos = elementos;
	}

	/**
	 * Método para obtener el mensaje de error dirigido a desarrollo
	 * @return String
	 */
	public String getMensajeDesarrollo() {
		return mensajeDesarrollo;
	}

	/**
	 * Método para asignar el mensaje de error dirigido a desarrollo
	 * @param mensajeDesarrollo
	 */
	public void setMensajeDesarrollo(String mensajeDesarrollo) {
		this.mensajeDesarrollo = mensajeDesarrollo;
	}

	/**
	 * Método para obtener el mensaje de error dirigido al usuario
	 * @return String
	 */
	public String getMensajeUsuario() {
		return mensajeUsuario;
	}

	/**
	 * Método para asignar el mensaje de error dirigido al usuario
	 * @param mensajeUsuario
	 */
	public void setMensajeUsuario(String mensajeUsuario) {
		this.mensajeUsuario = mensajeUsuario;
	}

	/**
	 * Método para obtener el nombre del proyecto del sistema donde se generó la excepción
	 * @return String
	 */
	public String getNombreProyecto() {
		return nombreProyecto;
	}

	/**
	 * Método para asignar el nombre del proyecto del sistema donde se generó la excepción
	 * @param nombreProyecto
	 */
	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	/**
	 * Método para obtener el nombre de usuario en sesión quien generó la excepción
	 * @return String
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Método para asignar el nombre de usuario en sesión quien generó la excepción
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Método para obtener el fecha y hora de cuando se generó la excepción
	 * @return Date
	 */
	public Date getFechaHora() {
		return fechaHora;
	}

	/**
	 * Método para asignar el fecha y hora de cuando se generó la excepción
	 * @param fechaHora
	 */
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	/**
	 * Método para saber si la excepción requiere ser notificada
	 * @return boolean
	 */
	public boolean isEnviarNotificacion() {
		return enviarNotificacion;
	}

	/**
	 * Método para asignar la variable de envío de correo si la excepción requiere ser notificada
	 * @param enviarNotificacion
	 */
	public void setEnviarNotificacion(boolean enviarNotificacion) {
		this.enviarNotificacion = enviarNotificacion;
	}

	/**
	 * Método que obtiene la lista de correos definidos para recibir la notifiación de la excepción
	 * @return List<String>
	 */
	public List<String> getCorreosNotificacion() {
		return correosNotificacion;
	}

	/**
	 * Método que asigna la lista de correos definidos para recibir la notifiación de la excepción
	 * @param correosNotificacion
	 */
	public void setCorreosNotificacion(List<String> correosNotificacion) {
		this.correosNotificacion = correosNotificacion;
	}

	/**
	 * Método para obtener el nombre de la capa donde se generó la excepción
	 * @return String
	 */
	public String getCapa() {
		return capa;
	}

	/**
	 * Método para asignar el nombre de la capa donde se generó la excepción
	 * @param capa
	 */
	public void setCapa(String capa) {
		this.capa = capa;
	}

	/**
	 * Método para obtener la lista de parametros recibidos
	 * @return String
	 */
	public String getParametros() {
		return parametros;
	}

	/**
	 * Método para asginar la lista de parametros recibidos
	 * @param parametros
	 */
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
	
}
