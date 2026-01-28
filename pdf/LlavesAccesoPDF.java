package mx.ine.procprimerinsa.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import mx.ine.procprimerinsa.dto.DTOLlave;

public class LlavesAccesoPDF extends PdfPageEventHelper{
	
	private static final Font FONT_TEXT_SMALL = new Font(FontFamily.HELVETICA, 6, Font.NORMAL);
	private static final Font FONT_TEXT_NORMAL = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
	private static final Font FONT_TEXT_BOLD = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
	private static final Font FONT_TEXT_BOLD_MEDIUM = new Font(FontFamily.HELVETICA, 11, Font.BOLD);
	private static final Font FONT_TEXT_BOLD_BIG = new Font(FontFamily.HELVETICA, 16, Font.BOLD);
	private static final Font FONT_TEXT_BOLD_UNDERLINE = new Font(FontFamily.HELVETICA, 10, Font.BOLD|Font.UNDERLINE);
	private static final Font FONT_MARCA_AGUA = new Font(FontFamily.HELVETICA, 90, Font.NORMAL, BaseColor.LIGHT_GRAY);
	
	private Document documento;
	private DTOLlave llave;
	private FileOutputStream fos;
	private File ruta;
	private PdfWriter writer;
	private PdfGState gStateMarcaAgua;
	private Phrase marcaAgua;

	public LlavesAccesoPDF(DTOLlave llave) throws Exception {
		documento = new Document(PageSize.LETTER, 50, 50,10, 30);
		ruta = new File(llave.getNombreArchivo());
		fos = new FileOutputStream(ruta);
		writer = PdfWriter.getInstance(documento, fos);
		this.llave = llave;
		marcaAgua = new Phrase("SIMULACRO", FONT_MARCA_AGUA);
		gStateMarcaAgua = new PdfGState();
		gStateMarcaAgua.setFillOpacity(0.3f);
	}
	
	public void abrirDocumento() throws Exception {
		documento.open();
	}
	
	public void cerrarDocumento() throws Exception {
		documento.close();
		fos.flush();
		fos.close();
	}
	
	public void agregaEncabezado() throws DocumentException, IOException {
		Image logoINE = Image.getInstance(llave.getRutaLogo());
		logoINE.setAlignment(Element.ALIGN_CENTER);
		logoINE.scalePercent(45F);
		Paragraph header = new Paragraph();
		Chunk saltoLinea = new Chunk("\n");
		
		header.setAlignment(Element.ALIGN_CENTER);
		header.add(logoINE);
		header.add(new Chunk(llave.getTituloProceso(), FONT_TEXT_BOLD));
		
		Chunk c = new Chunk("                                                 ");
		c.setUnderline(0.1f, -2f);
		header.add(saltoLinea);
		header.add(c);
		
		header.add(saltoLinea);
		header.add(new Chunk(llave.getLeyendaSimulacro().isEmpty() ? "" : "SIMULACRO"));
		header.add(saltoLinea);
		header.add(saltoLinea);
		
		documento.add(header);
	}
	
	public void agregaLinea() throws DocumentException {
		Paragraph linea = new Paragraph();
		Chunk c = new Chunk("                                                                                                                                  ");
		c.setUnderline(0.1f, -2f);
		linea.add(c);
		documento.add(linea);
		
		Paragraph doblar = new Paragraph();
		doblar.add(new Chunk("Doblar aquí", FONT_TEXT_SMALL));
		documento.add(doblar);
	}
	
	public void agregaCaratula() throws DocumentException {
		Chunk saltoLinea = new Chunk("\n");
		Chunk espacio = new Chunk(" ");
		Chunk coma = new Chunk(",");
		Chunk guion = new Chunk("-");
		
		Paragraph remitente = new Paragraph();
		
		Phrase l1 = new Phrase(new Chunk(llave.getVocal(), FONT_TEXT_BOLD_UNDERLINE));
		l1.add(saltoLinea);
		remitente.add(l1);
		
		Phrase l2 = new Phrase();
		l2.add(new Chunk("Estado:", FONT_TEXT_NORMAL));
		l2.add(espacio);
		l2.add(new Chunk(llave.getIdEstado().toString(), FONT_TEXT_BOLD));
		l2.add(coma);
		l2.add(espacio);
		l2.add(new Chunk(llave.getEstado(), FONT_TEXT_BOLD));
		l2.add(saltoLinea);
		remitente.add(l2);
		
		Phrase l3 = new Phrase();
		l3.add(new Chunk("Distrito Electoral Federal:", FONT_TEXT_NORMAL));
		l3.add(espacio);
		l3.add(new Chunk(llave.getIdDistrito().toString(), FONT_TEXT_BOLD));
		l3.add(espacio);
		l3.add(guion);
		l3.add(espacio);
		l3.add(new Chunk(llave.getDistrito(), FONT_TEXT_BOLD));
		l3.add(saltoLinea);
		l3.add(saltoLinea);
		remitente.add(l3);
		
		documento.add(remitente);
	}
	
	public void agregaContenido() throws DocumentException {
		Chunk saltoLinea = new Chunk("\n");
		Chunk espacio = new Chunk(" ");
		Chunk coma = new Chunk(",");
		Chunk guion = new Chunk("-");
		
		Paragraph content = new Paragraph();
		
		Phrase l1 = new Phrase(new Chunk("Llave de acceso para el: ", FONT_TEXT_NORMAL));
		l1.add(new Chunk(llave.getVocal(), FONT_TEXT_BOLD_MEDIUM));
		l1.add(saltoLinea);
		content.add(l1);
		
		Phrase l2 = new Phrase();
		l2.add(saltoLinea);
		content.add(l2);
		
		Phrase l3 = new Phrase();
		l3.add(new Chunk("Estado: ", FONT_TEXT_NORMAL));
		l3.add(new Chunk(llave.getIdEstado().toString(), FONT_TEXT_BOLD));
		l3.add(coma);
		l3.add(espacio);
		l3.add(new Chunk(llave.getEstado(), FONT_TEXT_BOLD));
		l3.add(saltoLinea);
		content.add(l3);
		
		Phrase l4 = new Phrase();
		l4.add(new Chunk("Distrito Electoral Federal:", FONT_TEXT_NORMAL));
		l4.add(espacio);
		l4.add(new Chunk(llave.getIdDistrito().toString(), FONT_TEXT_BOLD));
		l4.add(espacio);
		l4.add(guion);
		l4.add(espacio);
		l4.add(new Chunk(llave.getDistrito(), FONT_TEXT_BOLD));
		l4.add(saltoLinea);
		content.add(l4);
		
		Phrase l5 = new Phrase();
		l5.add(saltoLinea);
		l5.add(saltoLinea);
		content.add(l5);

		documento.add(content);
	}
	
	public void agregaLlave() throws DocumentException {
		Chunk saltoLinea = new Chunk("\n");
		
		Paragraph llaveEscribir = new Paragraph();
		llaveEscribir.setAlignment(Element.ALIGN_CENTER);
		
		Phrase l1 = new Phrase();
		l1.add(new Chunk(this.llave.getLlave(), FONT_TEXT_BOLD_BIG));
		llaveEscribir.add(l1);
		
		Phrase l2 = new Phrase();
		l2.add(saltoLinea);
		llaveEscribir.add(l2);
		
		Phrase l3 = new Phrase();
		l3.add(saltoLinea);
		llaveEscribir.add(l3);
		
		Phrase l4 = new Phrase();
		l4.add(saltoLinea);
		llaveEscribir.add(l4);
		
		documento.add(llaveEscribir);
	}
	
	public void agregaTrama() throws DocumentException, IOException {
		Image tramaImage = Image.getInstance(llave.getRutaTrama());
		tramaImage.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph trama = new Paragraph();
		trama.setAlignment(Element.ALIGN_CENTER);
		trama.add(tramaImage);
		documento.add(trama);
	}
	
	public void escribirLlave(DTOLlave llave) throws Exception {
		
		agregaEncabezado();
		
		documento.add(new Paragraph("\n"));
		documento.add(new Paragraph("\n"));
		documento.add(new Paragraph("\n"));
		
		agregaCaratula();
		
		documento.add(new Paragraph("\n"));
		
		agregaLinea();
		
		documento.add(new Paragraph("\n"));
		documento.add(new Paragraph("\n"));
		
		agregaContenido();
		
		documento.add(new Paragraph("\n"));
		
		agregaLlave();
		
		documento.add(new Paragraph("\n"));
		documento.add(new Paragraph("\n"));
		
		agregaLinea();
		
		agregaTrama();
		
		if(!llave.getLeyendaSimulacro().isEmpty()) {
			PdfContentByte canvas = writer.getDirectContent();
	        canvas.saveState();
	        canvas.setGState(gStateMarcaAgua);
	        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, marcaAgua, 330, (documento.top()+documento.bottom())/2, 45);
	        canvas.restoreState();
		}
	}
}
