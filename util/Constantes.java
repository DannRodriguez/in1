package mx.ine.procprimerinsa.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("constantes")
@Scope("singleton")
public class Constantes implements Serializable {

	private static final long serialVersionUID = 268284506518563312L;

	public static final String DEFAULT_LINE_SEPARATOR = System.getProperty("line.separator");

	/*************** IDENTIFICADORES DE PARÁMETROS **********************/
	public static final Integer PARAMETRO_FECHA_EJECUCION_PROCESO = 1;
	public static final Integer PARAMETRO_LETRA_A_INSACULAR = 2;
	public static final Integer PARAMETRO_MES_A_INSACULAR = 3;
	public static final Integer PARAMETRO_THREAD_POOL_SIZE_CARGA_PRIMERA_CAPA = 7;
	public static final Integer PARAMETRO_ENCODING_ARCHIVO_CARGA_DATOS_PERSONALES = 8;
	public static final Integer PARAMETRO_BOTON_EJECUCION_PROCESO = 9;
	public static final Integer PARAMETRO_VALIDAR_LLAVES = 10;
	public static final Integer PARAMETRO_POLL_EJECUCION_PROCESO = 11;
	public static final Integer PARAMETRO_POLL_DESPLIEGUE_PROCESO = 12;
	public static final Integer PARAMETRO_REGEX_TO_VALIDATE_CARGA_DATOS_PERSONALES = 13;
	public static final Integer PARAMETRO_TIPO_DE_EJECUCION = 14;
	public static final Integer PARAMETRO_INICIO_PERIODO_SIMULACRO = 15;
	public static final Integer PARAMETRO_FIN_PERIODO_SIMULACRO = 16;
	public static final Integer PARAMETRO_LETRA_A_INSACULAR_SIMULACRO = 17;
	public static final Integer PARAMETRO_MES_A_INSACULAR_SIMULACRO = 18;
	public static final Integer PARAMETRO_TIPO_DE_MAPA_A_MOSTRAR = 19;
	public static final Integer PARAMETRO_POLL_ACTUALIZACION_MAPA = 20;
	public static final Integer PARAMETRO_CAPTCHA_SISTEMA = 21;
	public static final Integer PARAMETRO_CORTE_LN_A_ACTUALIZAR = 52;
	public static final Integer PARAMETRO_VALIDAR_YA_ES_INSACULADO = 53;
	public static final Integer PARAMETRO_MESES_YA_SORTEADOS = 54;
	public static final Integer PARAMETRO_CONSIDERA_EXTRAORDINARIAS = 55;

	/**************** PASOS DEL WIZARD DEL PROCESO **********************/
	public static final String PASO_VALIDACION = "validacion";
	public static final String PASO_PARAMETROS = "parametros";
	public static final String PASO_EJECUCION = "ejecucion";
	public static final String PASO_RESULTADOS = "resultados";
	public static final String PASO_PROCESO_FINALIZADO = "procesoFinalizado";

	/*************** CONSTANTES DE ESTATUS DEL PROCESO ********************/
	public static final int ESTATUS_NO_PARTICIPA = 0;
	public static final int ESTATUS_PROCESO_INICIADO = 1;
	public static final int ESTATUS_PREPARANDO_BD_I = 2;
	public static final int ESTATUS_PREPARANDO_BD_F = 3;
	public static final int ESTATUS_PREPARANDO_DG_I = 4;
	public static final int ESTATUS_PREPARANDO_DG_F = 5;
	public static final int ESTATUS_PERMISOS_VALIDOS = 6;
	public static final int ESTATUS_EJECUTANDO_PROCESO_I = 7;
	public static final int ESTATUS_EJECUTANDO_PROCESO_F = 8;
	public static final int ESTATUS_GENERANDO_ARCHIVOS = 9;
	public static final int ESTATUS_DESPLIEGUE_RESULTADOS = 10;
	public static final int ESTATUS_GUARDANDO_BD_I = 11;
	public static final int ESTATUS_GUARDANDO_BD_F = 12;
	public static final int ESTATUS_PROCESO_FINALIZADO = 13;
	public static final int ESTATUS_PROCESO_REINICIADO = 14;

	public static final int DEFAULT_JOB_EXECUTION_ID = -1;

	/*************** PROCEDIMIENTOS ALMACENADOS ********************/
	public static final String STORED_PROCEDURE_DBMS_GATHER_SCHEMA_STATS = "{call DBMS_STATS.GATHER_SCHEMA_STATS(?)}";
	public static final String STORED_PROCEDURE_EJECUTA_CARGA_ARE = "{call INSA1.PG_MARCADO_ARE.PL_EJECUTA_CARGA(?,?,?)}";
	public static final String STORED_PROCEDURE_EJECUTA_ACTUALIZACION_ARE = "{call INSA1.PG_MARCADO_ARE.PL_EJECUTA_ACTUALIZACION(?,?,?,?,?)}";
	public static final String STORED_PROCEDURE_CREA_INDICES_DI = "{call INSA1.PG_ADMIN_INDICES_DI.PL_CREA_INDICES(?)}";
	public static final String STORED_PROCEDURE_ELIMINA_INDICES_DI = "{call INSA1.PG_ADMIN_INDICES_DI.PL_ELIMINA_INDICES(?)}";
	public static final String STORED_PROCEDURE_ORDENAMIENTOS = "{call INSA1.PG_ORDEN_SYSTEM.PL_EJECUTA_ORDEN(?,?,?,?,?,?)}";
	public static final String STORED_PROCEDURE_CARGA_PRIMERA_CAPA = "{call INSA1.PG_CARGA_PRIMERA_CAPA.PL_EJECUTA_CARGA(?,?,?,?,?,?)}";
	public static final String STORED_PROCEDURE_HABILITA_TRIGGERS_PRIMERA_CAPA = "{call PRIMERA_CAPA.PG_CARGA_PRIMERA_CAPA.PL_HABILITA_TRIGGERS_DI(?)}";
	public static final String STORED_PROCEDURE_DESHABILITA_TRIGGERS_PRIMERA_CAPA = "{call PRIMERA_CAPA.PG_CARGA_PRIMERA_CAPA.PL_DESHABILITA_TRIGGERS_DI(?)}";
	public static final String STORED_PROCEDURE_LN_MARCADO_NOMBRE_ORDEN = "{call PG_UPDATE_LN.PL_UPDATE_NOMBRE_ORDEN(?,?,?,?,?)}";
	public static final String STORED_PROCEDURE_LN_REINICIO_YA_ES_INSACULADO = "{call PG_UPDATE_LN.PL_REINICIO_YA_ES_INSACULADO(?,?,?,?,?)}";
	public static final String STORED_PROCEDURE_LN_MARCADO_SERVIDOR_PUBLICO = "{call PG_UPDATE_LN.PL_UPDATE_SERVIDOR_PUBLICO(?,?,?)}";
	public static final String STORED_PROCEDURE_LN_REINICIO_SERVIDOR_PUBLICO = "{call PG_UPDATE_LN.PL_REINICIO_SERVIDOR_PUBLICO(?,?,?)}";

	/*************** GLUSTER ********************/
	public static final String RUTA_LOCAL_FS = Gluster.getGlusterInsa();
	public static final String CARPETA_PROC_GLUSTER_INSA1 = "proc";
	public static final String CARPETA_CEDULAS = "CEDULAS";
	public static final String CARPETA_LISTADOS = "LISTADOS";
	public static final String CARPETA_LLAVES = "LLAVES";
	public static final String CARPETA_DATOS_PERSONALES = "DATOS_MINIMOS";
	public static final String CARPETA_DATOS_DERFE = "DATOS_PERSONALES";
	public static final String CARPETA_LLAVE_PUBLICA = "LLAVE_PUBLICA";
	public static final String CARPETA_ORDENADA = "ORDENADA";
	public static final String CARPETA_SPOOL = "SPOOL";
	public static final String TITULO_ARCHIVO_CEDULA = "ESTADISTICO_";
	public static final String TITULO_ARCHIVO_LISTADO = "LISTADO_";
	public static final String TITULO_ARCHIVO_LLAVE = "LLAVE_";
	public static final String TITULO_ARCHIVO_LLAVES_SIMULACRO = "_SIMULACRO";
	public static final String TITULO_ARCHIVO_LLAVES_VE = "VE";
	public static final String TITULO_ARCHIVO_LLAVES_VCEYEC = "VCEYEC";
	public static final String TITULO_ARCHIVO_SPOOL = "_spool.csv";
	public static final String TITULO_ARCHIVO_SERVIDORES_PUBLICOS = "servidoresPublicos.txt";
	public static final String TITULO_ARCHIVO_AREAS_SECCIONES = "areasSecciones.txt";
	public static final String TITULO_ARCHIVO_SECCIONES_COMPARTIDAS = "seccionesCompartidas.txt";
	public static final String TITULO_ARCHIVO_ORDENADA = "ordenada.txt";
	public static final String TITULO_ARCHIVO_ORDENADA_CARGA = "ordenadaCarga.txt";

	/********** ARCHIVOS GENERADOS POR EL PROCESO ******/
	public static final Integer LISTADO_PDF = 1;
	public static final Integer LISTADO_TXT = 2;
	public static final Integer CEDULA_PDF = 3;

	/********** TIPO INTEGRACION ******/
	public static final Integer INTEGRACION_CASILLA = 0;
	public static final Integer INTEGRACION_MESA = 1;
	public static final Map<Integer, String> TIPOS_DE_INTEGRACION = Map.of(
			INTEGRACION_CASILLA, "Casilla",
			INTEGRACION_MESA, "Mesa");

	/********** SERVIDORES PÚBLICOS ******/
	public static final Integer PERSONA_SERVIDORA_PUBLICA = 1;

	/*************** TYPES DE ORACLE ********************/
	public static final Integer ORACLE_TYPES_NUMBER = 2;
	public static final Integer ORACLE_TYPES_VARCHAR = 12;

	/********* ESTATUS DEL MÓDULO DE CARGA DE DATOS PERSONALES *************/
	public static final int TIPO_EJECUCION_GENERACION_DATOS_MINIMOS = 1;
	public static final int TIPO_EJECUCION_VALIDACION_ARCHIVO = 2;
	public static final int TIPO_EJECUCION_ACTUALIZACION_DATOS_PERSONALES = 3;
	public static final Character ESTATUS_DATOS_PERSONALES_GENERADO = 'G';
	public static final Character ESTATUS_DATOS_PERSONALES_SUBIDO = 'S';
	public static final Character ESTATUS_DATOS_PERSONALES_ELIMINADO = 'E';
	public static final Character ESTATUS_DATOS_PERSONALES_VALIDADO = 'V';
	public static final Character ESTATUS_DATOS_PERSONALES_ACTUALIZADO = 'A';
	public static final Character ESTATUS_DATOS_PERSONALES_NO_VALIDO = 'N';
	public static final Map<Character, String> ETAPA_ESTATUS_DATOS_PERSONALES = Map.of(
			'-', "Proceso no iniciado",
			'G', "Archivo con datos mínimos generado",
			'S', "Archivo de datos personales cargado",
			'V', "Archivo de datos personales validado",
			'A', "Datos personales actualizados",
			'E', "Archivo de datos personales eliminado",
			'N', "Error en la validación");

	/********* TIPO DE CAPTURA *************/
	public static final Integer IDDISTRITO_CAPTURA_POR_MUNICIPIO = 99;
	public static final Integer IDMUNICIPIO_CAPTURA_POR_DISTRITO = 999;
	public static final Integer CORTE_MAPAS = 4;
	public static final String VERIFICA_TIPO_DE_PROCESO = "-EXT-";
	public static final String TIPO_DE_PROCESO_EXTRAORDINARIO = "E";
	public static final String TIPO_DE_PROCESO_ORDINARIO = "O";

	/********* VALIDACIÓN DE MÓDULOS *************/
	public static final Integer MODULO_ACCION_CAPTURA = 1;
	public static final Integer MODULO_ACCION_CONSULTA = 2;
	public static final Integer MODULO_ACCION_MODIFICA = 3;
	public static final Integer ID_MODULO_GENERACION_DE_LLAVES = 4;
	public static final Integer ID_MODULO_REINICIO_PROCESO_INSACULACION = 5;
	public static final Integer ID_MODULO_PROCESO_INSACULACION = 6;
	public static final Integer ID_MODULO_POOL_REPORTES = 9;
	public static final Integer ID_MODULO_REPORTES = 13;

	/********* SEXO *************/
	public static final String GENERO_HOMBRE = "H";
	public static final String GENERO_MUJER = "M";
	public static final String GENERO_NO_BINARIO = "X";

	/********* DOBLE NACIONALIDAD *************/
	public static final String ENTIDAD_NACIMIENTO_DOBLE_NACIONALIDAD = "87";

	/****
	 * Constante carpeta local donde se almacenan las firmas de los vocales
	 * Ejecutivos.
	 ******/
	public static final Integer ETIQUETA_RUTA_FIRMA = 3;

	/**** Impresion carta notificación ******/
	public static final Integer SERVIDOR_WS_SESIONES = 5;
	public static final Integer URL_SERVICIO_DIR_JUNTAS_DISTRITALES = 1;
	public static final Integer RUTA_LOGOS_OPLES = 2;

	/********* ADMIN SPOOL *************/
	public static final Integer ESQUEMA_LISTA_NOMINAL = 0;
	public static final Integer ESQUEMA_INSA1 = 1;
	public static final Map<Integer, String> ESQUEMAS = Map.of(
			ESQUEMA_LISTA_NOMINAL, "Lista nominal",
			ESQUEMA_INSA1, "Primera Insaculación");
	public static final Pattern SPOOL_PATTERN_ENCABEZADO = Pattern
			.compile("^[a-zA-Zá-úÁ-Úä-üÄ-ÜñÑ_.'()\\d\\-]+(,[a-zA-Zá-úÁ-Úä-üÄ-ÜñÑ_.'()\\d\\-]+)*+$");
	public static final Pattern SPOOL_PATTERN_EJECUTOR = Pattern.compile(
			"^SELECT [a-zA-Zá-úÁ-Úä-üÄ-ÜñÑ*_+,.:'()!=><|&¿?\\d\\s\\/\\-]+FROM [a-zA-Zá-úÁ-Úä-üÄ-ÜñÑ*_+,.:'()!=><|&¿?\\d\\s\\/\\-]+WHERE [a-zA-Zá-úÁ-Úä-üÄ-ÜñÑ*_+,.:'()!=><|&¿?\\d\\s\\/\\-]+FETCH FIRST [\\d]+ ROWS ONLY$",
			Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
	public static final Map<Integer, String> SPOOL_LLAVES_VALIDAS = Map.of(
			1, "du18OH20nc+0cvinAfu1mGGKrEmI4jbCtVoRiEfIdKk=",
			2, "7ttiew+wYPkCzw7ELYDxmwBCOrh9juPA6o8zCpXi2MU=",
			3, "V8kkl4Cs5yOIHDFKhSTOkedEFix4qrUn9en8++vHU3Q=",
			4, "VwDuIqR6NzBpOveXOrtZ3rkdiWRK84ctDrvGNk1VcWI=");

	/********* ADMINISTRADOR BATCH *************/
	public static final int TIPO_EJECUCION_PROCESO_PRIMERA_INSA = 1;
	public static final int TIPO_EJECUCION_GENERACION_ARCHIVOS = 2;
	public static final int TIPO_EJECUCION_PROCESO_PRIMERA_INSA_COMPLETO = 3;
	public static final int TIPO_EJECUCION_REINICIO = 4;
	
	/********* CARGA PRIMERA CAPA *************/
	public static final int TIPO_EJECUCION_MARCADO_ARE = 1;
	public static final int TIPO_EJECUCION_ORDENAMIENTOS = 2;
	public static final int TIPO_EJECUCION_CARGA_PRIMERA_CAPA = 3;
	public static final int TIPO_EJECUCION_COMPLETA = 4;
	public static final int TIPO_EJECUCION_MARCADO_NOMBRE_ORDEN = 5;
	public static final int TIPO_EJECUCION_REINICIO_YA_ES_INSACULADO = 6;
	public static final Map<Integer, String> ETAPA_CARGA_PRIMERA_CAPA = Map.ofEntries(
			Map.entry(96, "Marcado de NOMBRE_ORDEN"),
			Map.entry(97, "Marcado de NOMBRE_ORDEN finalizado"),
			Map.entry(98, "Reinicio de YA_ES_INSACULADO"),
			Map.entry(99, "Reinicio de YA_ES_INSACULADO finalizado"),
			Map.entry(0, "El proceso no ha sido ejecutado"),
			Map.entry(1, "Marcado de AREAS_SECCIONES"),
			Map.entry(2, "Marcado de SECCIONES_COMPARTIDAS"),
			Map.entry(3, "Marcado de mal referenciados"),
			Map.entry(4, "Marcado finalizado"),
			Map.entry(5, "Orden geográfico"),
			Map.entry(6, "Orden letra/alfabético"),
			Map.entry(7, "Orden visita"),
			Map.entry(8, "Orden finalizado"),
			Map.entry(9, "Borrado de PRIMERA_CAPA"),
			Map.entry(10, "Carga DATOS_INSACULADOS"),
			Map.entry(11, "Carga RESULTADOS_1A_INSA"),
			Map.entry(12, "Creación/reinicio de secuencias"),
			Map.entry(13, "Carga finalizada"));

	/*** Constante que define el valor del tipo de junta general */
	public static final String TIPO_JUNTA_CAPTURA = "JD";
	public static final Integer PARAMETRO_PRUEBAS_CARGA = 47;

	/************* Constantes para el servicio de BITACORA */
	public static final String SERVICIO_BITACORA_LLAVES = "Generación de llaves de acceso";
	public static final String SERVICIO_BITACORA_REINICIO = "Reinicio";
	public static final String SERVICIO_BITACORA_MAPA = "Mapa de seguimiento";
	public static final String SERVICIO_BITACORA_PROCESO = "Ejecución del proceso INSA1";
	public static final String SERVICIO_BITACORA_ADMIN_DATOS_PERSONALES = "Administración de datos personales (carga, descarga)";
	public static final String SERVICIO_BITACORA_FIRMA = "Firma del vocal-presidente";
	public static final String SERVICIO_BITACORA_IMPRESION = "Impresión carta notificación";

	public static final String EJECUTA = "Ejecuta";

	/*************** VERSIÓN Y FECHA DE LA APLICACIÓN ********************/
	public static final String VERSION_APP = "Versión del sistema 13.0";
	public static final String HIDDEN_VERSION_APP = "13.0.0 Rev. 1, 12/11/2026 16:00";

	/*************** ROLES DEL SISTEMA ********************/
	public static final List<String> ROLES_SISTEMA = List.of("SCE.ADMIN.OC",
			"SCE.UNICOM.OC",
			"SCE.CONSULTA.OC",
			"SCE.CONSULTA.JL",
			"SCE.CONSULTA.JD",
			"SCE.CAPTURA_VE.JD",
			"SCE.CAPTURA_VECEYEC.JD",
			"SCE.CAPTURA_VCEYEC.JD",
			"SCE.CAPTURA.OC",
			"SCE.CAU.OC",
			"SCE.CONSULTA_RESTRINGIDA.OC",
			"SCE.CAPTURA.JL",
			"SCE.CAPTURA_CONSEJERO.JL",
			"SCE.CAPTURA_VE.JL",
			"SCE.CAPTURA_VECEYEC.JL",
			"SCE.CAPTURA_VCEYEC.JL",
			"SCE.CAPTURA_VERFE.JL",
			"SCE.CAPTURA_VRFE.JL",
			"SCE.CAPTURA_VOE.JL",
			"SCE.CAPTURA_VS.JL",
			"SCE.CONSULTA_RESTRINGIDA.JL",
			"SCE.CAPTURA.JD",
			"SCE.CAPTURA_VERFE.JD",
			"SCE.CAPTURA_VRFE.JD",
			"SCE.CAPTURA_VOE.JD",
			"SCE.CAPTURA_VS.JD",
			"SCE.CAPTURA_CONSEJERO.JD",
			"SCE.CONSULTA_RESTRINGIDA.JD",
			"SCE.DERFE.OC");

	public String getVERSION_APP() {
		return VERSION_APP;
	}

	public String getHIDDEN_VERSION_APP() {
		return HIDDEN_VERSION_APP;
	}

}
