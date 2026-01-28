package mx.ine.procprimerinsa.pdf;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import mx.ine.procprimerinsa.dto.db.DTOResultados1aInsa;
import mx.ine.procprimerinsa.util.Utilidades;

public class CedulaInsaculadosPDF extends GenericPDF {

	private PdfPTable tablaCedula;
	private String tituloTabla;
	private String fecha;
	private String leyendaCasilla;
	private DTOResultados1aInsa totales;
	private Integer totalInsaculados;
	private DecimalFormat numberformatter;
	
	public CedulaInsaculadosPDF(OutputStream outputStream, String nombreProceso, String nombreEstado, String id,
			String cabecera, String modoEjecucion, String tituloArchivoCedula, String leyendaCasilla) throws DocumentException {
		documentTitle = tituloArchivoCedula;
		tituloTabla = tituloArchivoCedula;
		
		leyendaTitulo = Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_reporte_titulo") + "\n\n"
				+ nombreProceso + "\n\n" + String.format("%s %s - %s", nombreEstado, id, cabecera);
		document = new Document(PageSize.A4.rotate(), 10, 10, 110, 35);
		writer = PdfWriter.getInstance(document, outputStream);
		writer.setPageEvent(new PEHGeneric());
		writeMarcaAgua = modoEjecucion.equals("S");
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		this.fecha = "Fecha: " + dateFormat.format(new Date());
		
		this.leyendaCasilla = leyendaCasilla;
		
		numberformatter = new DecimalFormat("#,###");
	}

	public void abrirDocumento() throws Exception {
		try {
			document.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cerrarDocumento() throws Exception {
		try {
			PdfPCell celda = new PdfPCell(new Phrase("TOTAL", CELDA_TITULO));
			celda.setBackgroundColor(BACKGROUND_GRIS);
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
			celda.setColspan(2);
			tablaCedula.addCell(celda);
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getListaNominal()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getEnero()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getFebrero()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getMarzo()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getAbril()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getMayo()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getJunio()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getJulio()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getAgosto()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getSeptiembre()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getOctubre()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getNoviembre()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getDiciembre()), CELDA_TEXTO));
			
			if ((totales.getHombres() != null && totales.getHombres() > 0)
					|| (totales.getMujeres() != null && totales.getMujeres() > 0)
					|| (totales.getNoBinario() != null && totales.getNoBinario() > 0)) {
				int total = totales.getHombres() + totales.getMujeres() + totales.getNoBinario();
				totales.setPorcentajeHombres((int) Math.round(totales.getHombres() * 100.0 / total));
				totales.setPorcentajeMujeres((int) Math.round(totales.getMujeres() * 100.0 / total));
				totales.setPorcentajeNoBinarios((int) Math.round(totales.getNoBinario() * 100.0 / total));
			}
			
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getHombres()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getPorcentajeHombres()), CELDA_TEXTO));
			
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getMujeres()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getPorcentajeMujeres()), CELDA_TEXTO));
			
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getNoBinario()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(totales.getPorcentajeNoBinarios()), CELDA_TEXTO));

			tablaCedula.addCell(new Phrase(numberformatter.format(totalInsaculados), CELDA_TEXTO));

			tablaCedula.setComplete(true);
			document.add(tablaCedula);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void creaEncabezado() {

		tablaCedula = new PdfPTable(
				new float[] { 1.2F, 1.3F, 1.2F, 1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F, 1.2F, 1.2F, 1.2F, 1.2F, 1.2F, 1.2F, 1F });
		tablaCedula.setComplete(false);
		tablaCedula.setWidthPercentage(93F);

		tablaCedula.getDefaultCell().setColspan(tablaCedula.getNumberOfColumns());
		tablaCedula.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		tablaCedula.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		tablaCedula.getDefaultCell().disableBorderSide(1);
		tablaCedula.getDefaultCell().disableBorderSide(4);
		tablaCedula.getDefaultCell().disableBorderSide(8);
		tablaCedula.addCell(new Phrase(fecha, CELDA_FECHA));

		tablaCedula.getDefaultCell().enableBorderSide(1);
		tablaCedula.getDefaultCell().enableBorderSide(4);
		tablaCedula.getDefaultCell().enableBorderSide(8);
		tablaCedula.getDefaultCell().setBackgroundColor(BACKGROUND_GRIS);
		tablaCedula.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		tablaCedula.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

		tablaCedula.getDefaultCell().setColspan(tablaCedula.getNumberOfColumns());
		tablaCedula.addCell(new Phrase(tituloTabla, CELDA_TITULO));

		tablaCedula.getDefaultCell().setColspan(1);
		tablaCedula.getDefaultCell().setPadding(4F);
		
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_seccion"), CELDA_TITULO));
		
		tablaCedula.addCell(new Phrase(leyendaCasilla, CELDA_TITULO));
		
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_listaNominal"), CELDA_TITULO));

		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_enero"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_febrero"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_marzo"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_abril"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_mayo"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_junio"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_julio"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_agosto"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_septiembre"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_octubre"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase
				(Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_noviembre"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_diciembre"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_hombres"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase( "% " +
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_hombres"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_mujeres"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase("% " +
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_mujeres"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_no_binario"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase("% " +
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_no_binario"), CELDA_TITULO));
		tablaCedula.addCell(new Phrase(
				Utilidades.mensajeProperties("04E_cedulaResultadosInsaculacionPDF_columna_total"), CELDA_TITULO));

		tablaCedula.setHeaderRows(3);
		tablaCedula.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
		
		inicializaTotales();
	}
	
	private void inicializaTotales() {
		totales = new DTOResultados1aInsa();
		totales.setListaNominal(0);
		totales.setEnero(0);
		totales.setFebrero(0);
		totales.setMarzo(0);
		totales.setAbril(0);
		totales.setMayo(0);
		totales.setJunio(0);
		totales.setJulio(0);
		totales.setAgosto(0);
		totales.setSeptiembre(0);
		totales.setOctubre(0);
		totales.setNoviembre(0);
		totales.setDiciembre(0);
		totales.setHombres(0);
		totales.setMujeres(0);
		totales.setNoBinario(0);
		totalInsaculados = 0;
	}

	public void escribeDatos(List<DTOResultados1aInsa> resultados) {

		for (DTOResultados1aInsa seccionCasilla : resultados) {

			tablaCedula.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCedula.addCell(new Phrase(seccionCasilla.getSeccion().toString(), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(
					seccionCasilla.getTipoCasilla().equalsIgnoreCase("B") ? seccionCasilla.getTipoCasilla()
							: seccionCasilla.getTipoCasilla() + seccionCasilla.getIdCasilla().toString(),
					CELDA_TEXTO));
			
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getListaNominal()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getEnero()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getFebrero()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getMarzo()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getAbril()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getMayo()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getJunio()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getJulio()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getAgosto()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getSeptiembre()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getOctubre()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getNoviembre()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getDiciembre()), CELDA_TEXTO));

			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getHombres()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getPorcentajeHombres()), CELDA_TEXTO));
			
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getMujeres()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getPorcentajeMujeres()), CELDA_TEXTO));
			
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getNoBinario()), CELDA_TEXTO));
			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getPorcentajeNoBinarios()), CELDA_TEXTO));

			tablaCedula.addCell(new Phrase(numberformatter.format(seccionCasilla.getInsaculados()), CELDA_TEXTO));

			totales.setListaNominal(seccionCasilla.getListaNominal() + totales.getListaNominal());
			totales.setEnero(seccionCasilla.getEnero() + totales.getEnero());
			totales.setFebrero(seccionCasilla.getFebrero() + totales.getFebrero());
			totales.setMarzo(seccionCasilla.getMarzo() + totales.getMarzo());
			totales.setAbril(seccionCasilla.getAbril() + totales.getAbril());
			totales.setMayo(seccionCasilla.getMayo() + totales.getMayo());
			totales.setJunio(seccionCasilla.getJunio() + totales.getJunio());
			totales.setJulio(seccionCasilla.getJulio() + totales.getJulio());
			totales.setAgosto(seccionCasilla.getAgosto() + totales.getAgosto());
			totales.setSeptiembre(seccionCasilla.getSeptiembre() + totales.getSeptiembre());
			totales.setOctubre(seccionCasilla.getOctubre() + totales.getOctubre());
			totales.setNoviembre(seccionCasilla.getNoviembre() + totales.getNoviembre());
			totales.setDiciembre(seccionCasilla.getDiciembre() + totales.getDiciembre());
			totales.setHombres(seccionCasilla.getHombres() + totales.getHombres());
			totales.setMujeres(seccionCasilla.getMujeres() + totales.getMujeres());
			totales.setNoBinario(seccionCasilla.getNoBinario() + totales.getNoBinario());
			
			totalInsaculados += seccionCasilla.getInsaculados();
		}
	}
}
