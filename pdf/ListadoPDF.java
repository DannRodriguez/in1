package mx.ine.procprimerinsa.pdf;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import mx.ine.procprimerinsa.dto.db.DTODatosInsaculados;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

public class ListadoPDF extends GenericPDF{
	
	private Integer seccionActual = 0;
	private Integer contadorSeccion = 0;
	
	private PdfPTable tablaListado;
	private String tituloTabla;
	
	private String fecha;
	private boolean isIntegracionCasilla;
	private String leyendaCasilla;
	
	public ListadoPDF(OutputStream outputStream, String nombreProceso, String nombreEstado, String id, 
			String cabecera, String modoEjecucion, String tituloArchivoListado, Integer tipoIntegracion,
			String leyendaCasilla) throws Exception {
		documentTitle = tituloArchivoListado;
		tituloTabla = tituloArchivoListado;
		
		leyendaTitulo = Utilidades.mensajeProperties("04E_listadoInsaculadosPDF_reporte_titulo") 
						+ "\n\n" + nombreProceso + "\n\n" + String.format("%s %s - %s", nombreEstado, id, cabecera);		
		document = new Document(PageSize.A4, 10, 10, 110, 35);			
		writer = PdfWriter.getInstance(document, outputStream);
		writer.setPageEvent(new PEHGeneric());
		writeMarcaAgua = modoEjecucion.equals("S");
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		this.fecha = "Fecha: " + dateFormat.format(new Date());
		this.isIntegracionCasilla = Constantes.INTEGRACION_CASILLA.equals(tipoIntegracion);
		this.leyendaCasilla = leyendaCasilla;
	}
	
	public void abrirDocumento() throws Exception {
		try{
			document.open();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void cerrarDocumento() throws Exception{
		try{
			tablaListado.setComplete(true);
			document.add(tablaListado);
			document.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void creaEncabezado() {
			
		if(isIntegracionCasilla) {
			tablaListado = new PdfPTable(new float[]{3F, 3F, 4F, 9F});
		} else {
			tablaListado = new PdfPTable(new float[]{3F, 3F, 13F});
		}
		tablaListado.setComplete(false);
		tablaListado.setWidthPercentage(93F);
		
		tablaListado.getDefaultCell().setColspan(tablaListado.getNumberOfColumns());
		tablaListado.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		tablaListado.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		tablaListado.getDefaultCell().disableBorderSide(1);
		tablaListado.getDefaultCell().disableBorderSide(4);
		tablaListado.getDefaultCell().disableBorderSide(8);
		tablaListado.addCell(new Phrase(fecha, CELDA_FECHA));
		
		tablaListado.getDefaultCell().enableBorderSide(1);
		tablaListado.getDefaultCell().enableBorderSide(4);
		tablaListado.getDefaultCell().enableBorderSide(8);
		tablaListado.getDefaultCell().setBackgroundColor(BACKGROUND_GRIS);
		tablaListado.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		tablaListado.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

		tablaListado.getDefaultCell().setColspan(tablaListado.getNumberOfColumns());
		tablaListado.addCell(new Phrase(tituloTabla, CELDA_TITULO));						
						
		tablaListado.getDefaultCell().setColspan(1);
		tablaListado.getDefaultCell().setPadding(4F);
		tablaListado.addCell(new Phrase(Utilidades.mensajeProperties("04E_listadoInsaculadosPDF_columna_no"), CELDA_TITULO));
		tablaListado.addCell(new Phrase(Utilidades.mensajeProperties("04E_listadoInsaculadosPDF_columna_seccion"), CELDA_TITULO));
		if(isIntegracionCasilla) {
			tablaListado.addCell(new Phrase(leyendaCasilla, CELDA_TITULO));
		}
		tablaListado.addCell(new Phrase(Utilidades.mensajeProperties("04E_listadoInsaculadosPDF_columna_nombre"), CELDA_TITULO));
		//tablaListado.addCell(new Phrase(Utilidades.mensajeProperties("04E_listadoInsaculadosPDF_columna_servidora_publica"), CELDA_TITULO));
		tablaListado.setHeaderRows(3);
		tablaListado.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
	}
	
	public void escribeDatos(List<DTODatosInsaculados> insaculados) {
		
		for(DTODatosInsaculados datos : insaculados) {
			if(seccionActual.equals(datos.getSeccion())) {
				contadorSeccion++;
			} else {
				seccionActual   = datos.getSeccion();
				contadorSeccion = 1;
			}
			
			tablaListado.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaListado.addCell(new Phrase(contadorSeccion.toString(), CELDA_TEXTO));
			tablaListado.addCell(new Phrase(datos.getSeccion().toString(), CELDA_TEXTO));
			if(isIntegracionCasilla) {
				tablaListado.addCell(new Phrase(datos.getCasilla(), CELDA_TEXTO));
			}
			tablaListado.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			tablaListado.addCell(new Phrase(datos.getApellidoPaterno() + " " + datos.getApellidoMaterno() + " " + datos.getNombre(), CELDA_TEXTO));
			/*tablaListado.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaListado.addCell(new Phrase(datos.getServidorPublico().equals(Constantes.PERSONA_SERVIDORA_PUBLICA)?
					Utilidades.mensajeProperties("etiqueta_generales_si"):
					Utilidades.mensajeProperties("etiqueta_generales_no"), CELDA_TEXTO));*/
		}
	}
	
}
