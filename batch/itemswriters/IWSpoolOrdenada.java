package mx.ine.procprimerinsa.batch.itemswriters;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.NonNull;

import mx.ine.procprimerinsa.dto.db.DTOOrdenadaP;
import mx.ine.procprimerinsa.util.Constantes;

public class IWSpoolOrdenada implements ItemWriter<DTOOrdenadaP>, InitializingBean, DisposableBean {
	
	private String rutaArchivo;
	private String encoding;
	
	private BufferedWriter writer;
		
	@Override
	public void destroy() throws Exception {
		writer.close();
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(rutaArchivo), encoding));
		writer.write("ID_PROCESO_ELECTORAL|ID_DETALLE_PROCESO|ID_PARTICIPACION|ID_MUNICIPIO|SECCION|ID_LOCALIDAD|MANZANA|ORDEN|USUARIO|FECHA_HORA" 
				+ Constantes.DEFAULT_LINE_SEPARATOR);
	}
	
	@Override
	public void write(@NonNull Chunk<? extends DTOOrdenadaP> items) throws Exception {
		StringBuilder fila = new StringBuilder();
		
		for (DTOOrdenadaP e : items.getItems()) {			
			fila.append(e.getIdProcesoElectoral()).append("|");
			fila.append(e.getIdDetalleProceso()).append("|");
			fila.append(e.getIdParticipacion()).append("|");
			fila.append(e.getIdMunicipio()).append("|");
			fila.append(e.getSeccion()).append("|");
			fila.append(e.getIdLocalidad()).append("|");
			fila.append(e.getManzana()).append("|");
			fila.append(e.getOrden()).append("|");
			fila.append(e.getUsuario()).append("|");
			fila.append(e.getFechaHora());
			fila.append(Constantes.DEFAULT_LINE_SEPARATOR);
		}
		
		writer.write(fila.toString());
	}
	
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
