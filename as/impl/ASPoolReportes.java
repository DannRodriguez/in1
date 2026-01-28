package mx.ine.procprimerinsa.as.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASPoolReportesInterface;
import mx.ine.procprimerinsa.dao.DAOPoolReportesInterface;
import mx.ine.procprimerinsa.dto.DTOAccesosSistema;
import mx.ine.procprimerinsa.dto.DTOPoolReportes;

@Service("asPoolReportes")
@Scope("prototype")
public class ASPoolReportes implements ASPoolReportesInterface {

	private static final long serialVersionUID = -648751817795558100L;
	
	@Autowired
	@Qualifier("daoPoolReportes")
	private transient DAOPoolReportesInterface daoPoolReportes;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<DTOPoolReportes> obtenerDatosInsaculados(List<Integer> detalles, Integer idCorte) {
		return daoPoolReportes.obtenerDatosInsaculados(detalles, idCorte);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<DTOAccesosSistema> obtenerAccesosSistema(Integer idSistema, Date fecha) {
		return daoPoolReportes.obtenerAccesosSistema(idSistema, fecha);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public byte[] crearZip(Integer idProceso, List<Integer> detalles, Integer idCorte, Date fecha) 
		throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		
		agregaArchivo(zos,
				"01_SECCIONES_POR_DISTRITO_INSACULADOS.txt",
				"ID_ESTADO|NOMBRE_ESTADO|ID_DISTRITO_FEDERAL|CABECERA_DISTRITAL_FEDERAL|TOTAL_SECCIONES|TOTAL_SECCIONES_INSACULADAS\r\n",
				"query_poolReportes_seccionesPorDistritoInsaculados",
				null,
				detalles,
				idCorte,
				null);
		
		agregaArchivo(zos,
				"02_TOTAL_INSACULADOS.txt",
				"TOTAL_CIUDADANOS_INSACULADOS|TOTAL_CIUDADANOS_LN|TOTAL_DI\r\n",
				"query_poolReportes_totalInsaculados",
				idProceso,
				detalles,
				null,
				null);
		
		agregaArchivo(zos,
				"03_TIEMPO_INICIO_FIN_PROCESO.txt",
				"ID_ESTADO|NOMBRE_ESTADO|ID_DISTRITO|CABECERA_DISTRITAL_FEDERAL|ID_ESTATUS_PROCESO|DESCRIPCION_ESTATUS_PROCESO|FECHA_HORA_INICIO_PROCESO|FECHA_HORA_FIN_PROCESO\r\n",
				"query_poolReportes_tiempoInicioFinProceso",
				null,
				detalles,
				idCorte,
				null);
		
		agregaArchivo(zos,
				"04_TOTAL_CIUDADANOS_INSACULADOS_POR_DTO.txt",
				"ID_ESTADO|NOMBRE_ESTADO|ID_DISTRITO_FEDERAL|CABECERA_DISTRITAL_FEDERAL|TOTAL_INSACULADOS\r\n",
				"query_poolReportes_totalCiudadanosInsaculadosPorDto",
				null,
				detalles,
				idCorte,
				null);
		
		agregaArchivo(zos,
				"05_TOTAL_CIUDADANOS_INSACULADOS_POR_SECCION.txt",
				"ID_ESTADO|NOMBRE_ESTADO|ID_DISTRITO_FEDERAL|CABECERA_DISTRITAL_FEDERAL|SECCION|TOTAL_INSACULADOS\r\n",
				"query_poolReportes_totalCiudadanosInsaculadosPorSeccion",
				null,
				detalles,
				idCorte,
				null);
		
		agregaArchivo(zos,
				"06_PROM_INSACULADOS_POR_SECCION_EN_CADA_DTO.txt",
				"ID_ESTADO|NOMBRE_ESTADO|ID_DISTRITO_FEDERAL|CABECERA_DISTRITAL_FEDERAL|TOTAL_INSACULADOS|TOTAL_SECCIONES|PROMEDIO_INSACULADOS_POR_SECCION\r\n",
				"query_poolReportes_promInsaculadosPorSeccionEnCadaDto",
				null,
				detalles,
				idCorte,
				null);
		
		agregaArchivo(zos,
				"08_BITACORA_PROCESOS.txt",
				"ID_PROCESO_ELECTORAL|ID_DETALLE_PROCESO|ID_PARTICIPACION|ID_EJECUCION|ID_REINICIO|ID_ESTATUS_PROCESO|USUARIO|FECHA_HORA|JOB_EXECUTION_ID|ID_BITACORA_PROCESOS\r\n",
				"query_poolReportes_bitacoraProcesos",
				null,
				detalles,
				null,
				null);
		
		agregaArchivo(zos,
				"TOTAL_ROLES_BITACORA_ACCESOS.txt",
				"ID_SISTEMA|FECHA|ROL_USUARIO|TOTAL\r\n",
				"query_poolReportes_totalRolesBitacoraAcceso",
				null,
				null,
				null,
				fecha);
		
		agregaArchivo(zos,
				"BITACORA_ACCESO.txt",
				"ID_BITACORA_ACCESO|ID_SISTEMA|USUARIO|ROL_USUARIO|FECHA_HORA_INICIO|FECHA_HORA_FIN|IP_USUARIO|IP_NODO\r\n",
				"query_poolReportes_bitacoraAcceso",
				null,
				null,
				null,
				fecha);
		
		agregaArchivo(zos,
				"10_CONTEO_NATURALIZADOS_DOBLE_NACIONALIDAD_POR_DTO.txt",
				"NOMBRE_ESTADO|ID_DISTRITO_FEDERAL|CABECERA_DISTRITAL_FEDERAL|TOTAL_C_DOBLE_NACIONALIDA|TOTAL_C_NATURALIZADOS\r\n",
				"query_poolReportes_conteoNaturalizadosDobleNacionalidadPorDto",
				idProceso,
				detalles,
				idCorte,
				null);
		
		agregaArchivo(zos,
				"11_CONTEO_RANGO_EDAD.txt",
				"NOMBRE_ESTADO|ID_DISTRITO_FEDERAL|CABECERA_DISTRITAL_FEDERAL|18-19|20-24|25-29|30-34|35-39|40-44|45-49|50-54|55-59|60-64|65-69|70 o más\r\n",
				"query_poolReportes_conteoRangoEdad",
				idProceso,
				detalles,
				idCorte,
				null);
		
		zos.close();

	    return baos.toByteArray();
	}
	
	private void agregaArchivo(ZipOutputStream zos, String fileName, String header, String consulta,
			Integer idProceso, List<Integer> detalles, Integer idCorte, Date fecha) throws IOException {
		
		zos.putNextEntry(new ZipEntry(fileName));
		
		zos.write(header.getBytes(), 0, header.length());		
		
		List<String> data = daoPoolReportes.regresaDatos(idProceso, detalles, idCorte, fecha, consulta);
		
		if(data != null) {
			for (String value : data) {
				zos.write(value.getBytes(), 0, value.length());
			}
		}
		
		zos.closeEntry();
	}
	
}
