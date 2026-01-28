package mx.ine.procprimerinsa.pdf;

import java.io.File;
import java.io.IOException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import mx.ine.procprimerinsa.util.ServletContextUtils;

public abstract class GenericPDF {

	protected static final BaseColor BACKGROUND_GRIS = new BaseColor(239, 239, 239);
	protected static final Font HEADER_TITULO = new Font(FontFamily.HELVETICA, 11F, Font.NORMAL, BaseColor.BLACK);
	protected static final Font HEADER_TEXTO  = new Font(FontFamily.HELVETICA, 8F, Font.BOLD, BaseColor.BLACK);		
	protected static final Font FOOTER_PAGINADO = new Font(FontFamily.HELVETICA, 8F, Font.NORMAL, BaseColor.BLACK);
	protected static final Font CELDA_TITULO 	= new Font(FontFamily.HELVETICA, 7F, Font.BOLD, BaseColor.BLACK);
	protected static final Font CELDA_FECHA		= new Font(FontFamily.HELVETICA, 5F, Font.BOLD, BaseColor.BLACK);
	protected static final Font CELDA_TEXTO 	= new Font(FontFamily.HELVETICA, 6F, Font.NORMAL, BaseColor.BLACK);
	
	protected Document document;
	protected PdfWriter writer;
	protected PdfPTable tablaHeader;
	protected PdfPTable tablaFooter;
	protected PdfTemplate templateTotalPaginas;
	protected Phrase marcaAgua;
	private PdfGState gStateMarcaAgua;
	protected String documentTitle;	
	protected String leyendaTitulo;
	protected boolean writeMarcaAgua;
	
	protected void openDocument(PdfWriter pdfWriter, Document document) throws Exception {
		document.addTitle(this.documentTitle);
		document.addAuthor("Instituto Nacional Electoral (INE)");
		document.addCreator("Proceso Primera Insaculación");
		document.addCreationDate();
		document.addProducer();
		
		templateTotalPaginas = pdfWriter.getDirectContent().createTemplate(30, 16);
		
		marcaAgua = new Phrase("SIMULACRO", new Font(FontFamily.HELVETICA, 90, Font.NORMAL, BaseColor.LIGHT_GRAY));
		gStateMarcaAgua = new PdfGState();
		gStateMarcaAgua.setFillOpacity(0.5f);
		
		initHeader();
		
		initFooter();
	}
	
	protected void closeDocument(PdfWriter pdfWriter, Document document) {		
		ColumnText.showTextAligned(templateTotalPaginas, Element.ALIGN_LEFT, new Phrase(String.valueOf(pdfWriter.getPageNumber()), FOOTER_PAGINADO), 0, 2, 0);
	}
	
	protected void startPage(PdfWriter pdfWriter, Document document) {
		tablaHeader.writeSelectedRows(0, -1, document.left()+20, document.getPageSize().getHeight()-20, pdfWriter.getDirectContent());
	}
	
	protected void endPage(PdfWriter pdfWriter, Document document) {
		
		if(writeMarcaAgua){
			writeMarcaAgua(pdfWriter);
		}
		
		tablaFooter.getRow(0).getCells()[0].setPhrase(new Phrase(String.format("Página %d de ", pdfWriter.getPageNumber()), FOOTER_PAGINADO));
		tablaFooter.writeSelectedRows(0, -1, document.left()+20, document.bottom() - 5, pdfWriter.getDirectContent());
	}
	
	private float getWidthDocument() {
		float padding = document.right() > 600 ? 15F: 30F;
		return document.right() - document.left() - padding;
	}
	
	protected void initHeader() throws BadElementException, IOException {
		String rutaImagenes	= ServletContextUtils.getRealResourcesPath("img")+File.separator;
		
		tablaHeader = new PdfPTable(new float[]{8F, 15F, 8F});
		
		tablaHeader.setTotalWidth(getWidthDocument());
		
		tablaHeader.setLockedWidth(true);
		
		tablaHeader.getDefaultCell().setBorderColor(BaseColor.WHITE);		
		tablaHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		tablaHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		
		Image imgINSA = Image.getInstance(rutaImagenes + "idPrimeraInsa.png");
		imgINSA.scalePercent(25F);
		PdfPCell cellImgINSA = new PdfPCell(imgINSA);
		cellImgINSA.setBorderColor(BaseColor.WHITE);
		cellImgINSA.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cellImgINSA.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaHeader.addCell(cellImgINSA);
		
		tablaHeader.addCell(new Phrase(leyendaTitulo, HEADER_TITULO));	
		
		Image imgINE = Image.getInstance(rutaImagenes + "logoINE.png");
		imgINE.scalePercent(45F);
		PdfPCell cellImgINE = new PdfPCell(imgINE);
		cellImgINE.setBorderColor(BaseColor.WHITE);
		cellImgINE.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cellImgINE.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaHeader.addCell(cellImgINE);
		
		tablaHeader.addCell("");
	}
	
	protected void startContenido() throws Exception {
		
	}
	
	protected void endContenido() throws Exception {
		
	}
	
	protected void initFooter() throws Exception {
		tablaFooter = new PdfPTable(new float[] {20F, 1F});
		
		tablaFooter.setTotalWidth(getWidthDocument());
		tablaFooter.setLockedWidth(true);
		
		tablaFooter.getDefaultCell().setBorderColor(BaseColor.WHITE);	
		tablaFooter.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
		tablaFooter.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		
		tablaFooter.addCell("");
		
		PdfPCell cellTotalPaginas = new PdfPCell(Image.getInstance(Image.getInstance(templateTotalPaginas)));
		cellTotalPaginas.setBorderColor(BaseColor.WHITE);
		tablaFooter.addCell(cellTotalPaginas);
	}
	
	private void writeMarcaAgua(PdfWriter pdfWriter) {
		PdfContentByte canvas = pdfWriter.getDirectContent();
        canvas.saveState();
        canvas.setGState(gStateMarcaAgua);
        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, marcaAgua, (document.right()-document.left())/2, (document.top()+document.bottom())/2, 45);
        canvas.restoreState();
	}
	
	protected class PEHGeneric extends PdfPageEventHelper {																				
				
		@Override
		public void onOpenDocument(PdfWriter pdfWriter, Document document) {
			try {
				openDocument(pdfWriter, document);				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		@Override
		public void onCloseDocument(PdfWriter pdfWriter, Document document) {
			try {
				closeDocument(pdfWriter, document);				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}						
		}

		@Override
		public void onStartPage(PdfWriter pdfWriter, Document document) {
			try {
				startPage(pdfWriter, document);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		@Override
		public void onEndPage(PdfWriter pdfWriter, Document document) {
			try {
				endPage(pdfWriter, document);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	
}
