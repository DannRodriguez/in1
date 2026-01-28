package mx.ine.procprimerinsa.helper.impl;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import mx.ine.procprimerinsa.dto.DTOJuntaDistrital;
import mx.ine.procprimerinsa.dto.DTOParametrosMargenes;
import mx.ine.procprimerinsa.dto.DTOSeccionesLocalidades;
import mx.ine.procprimerinsa.dto.db.DTODatosInsaculados;
import mx.ine.procprimerinsa.util.Utilidades;

public class HLPPDFImpresionCarta extends PdfPageEventHelper implements Serializable {
	
	private static final long serialVersionUID = 288909087069395105L;
	private static final Logger logger = Logger.getLogger(HLPPDFImpresionCarta.class);

	private List<DTODatosInsaculados> ciudadanos;
	private List<DTOSeccionesLocalidades> sec;
	private String nameEstado;
	private DTOParametrosMargenes parametros;
	private File archivoFirma;
	private File archivoLogo;
	private DTOJuntaDistrital direccion;
	private String telefono;
	private String frenteReverso;
	private int pageNumber;
	
	public HLPPDFImpresionCarta(List<DTODatosInsaculados> ciudadanos, List<DTOSeccionesLocalidades> sec,
			String nameEstado, DTOParametrosMargenes parametros, File archivoLogo) {
		this.ciudadanos = ciudadanos;
		this.sec = sec;
		this.nameEstado = nameEstado;
		this.parametros = parametros;
		this.frenteReverso = "F";
		this.pageNumber = 0;
		this.archivoLogo = archivoLogo;
	}

	public HLPPDFImpresionCarta(File archivoFirma, File archivoLogo, DTOJuntaDistrital direccion, 
			String telefono, DTOParametrosMargenes parametros) {
		this.archivoFirma = archivoFirma;
		this.archivoLogo = archivoLogo;
		this.direccion = direccion;
		this.telefono = telefono;
		this.parametros = parametros;
		this.frenteReverso = "R";
		this.pageNumber = 0;
	}

	public void onOpenDocument(PdfWriter writer, Document document) {
		document.setPageSize(new RectangleReadOnly(Utilidades.cmtoPuntos(parametros.getdAncho()),
				Utilidades.cmtoPuntos(parametros.getdLargo())));
		document.setMargins(0f, 0f, 0f, 0f);
	}

	public void onStartPage(PdfWriter writer, Document document) {
		try {

			if (frenteReverso.equals("F") && ciudadanos.size() > pageNumber) {
				Font f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
				Font f2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
				
				PdfContentByte cb = writer.getDirectContent();
				
				DTODatosInsaculados ciudadano = ciudadanos.get(pageNumber);
				String name = ciudadano.getNombre() + " " + ciudadano.getApellidoPaterno() + " " + ciudadano.getApellidoMaterno();
				String nombreLocalidad = "";
				String claveElector = ciudadano.getNumeroCredencialElector();
				String geoElectorales;
				String nOrdenV = "Ruta de visita: " + (ciudadano.getOrdenVisita() != null ? ciudadano.getOrdenVisita() : "");
				String nReferenciaG = "Referencia geográfica: " + (ciudadano.getOrdenGeografico() != null ? ciudadano.getOrdenGeografico() : "");
				String semilla = "Ciudadana/o semilla";
				Integer noSeccion = ciudadano.getSeccion() != null ? ciudadano.getSeccion() : 0;
				String seccion = "Sección: " + ciudadano.getSeccion();
				
				if (!noSeccion.equals(0)
						&& ciudadano.getIdLocalidad() != null) {
					Optional<DTOSeccionesLocalidades> seccionOptional = sec.stream()
																			.filter(s -> s.getSec().equals(noSeccion))
																			.findAny();
					
					if(seccionOptional.isPresent()) {
						DTOSeccionesLocalidades seccionLocalidad = seccionOptional.get();
						
						if(seccionLocalidad.getLocalidades().containsKey(ciudadano.getIdLocalidad())) {
							nombreLocalidad = seccionLocalidad.getLocalidades()
															.get(ciudadano.getIdLocalidad())
															.getValue();
						}
						
					}
				}

				String direccion = ciudadano.getCalle() != null ? ciudadano.getCalle() : "";
				String direccion2;
				String direccion3;
				if(ciudadano.getNumeroExterior() != null 
					&& !ciudadano.getNumeroExterior().isBlank()) {
					direccion += " " + ciudadano.getNumeroExterior();
				}
				
				if(ciudadano.getNumeroInterior() != null
					&& !ciudadano.getNumeroInterior().isBlank()) {
					direccion += ", INT " + ciudadano.getNumeroInterior();
				}
				
				if (ciudadano.getColonia() != null
					&& !ciudadano.getColonia().isBlank()) {
					direccion += direccion.isBlank() ? "" :  ", ";
					direccion2 = ciudadano.getColonia();
					if(!nombreLocalidad.isEmpty()) {
						direccion2 += ", " + nombreLocalidad;
					}
					if(ciudadano.getManzana() != null) {
						direccion2 += ", MZ " + ciudadano.getManzana();
					}
				} else {
					direccion2 = "";
				}
				
				direccion3 = "";
				if (nameEstado != null
					&& !nameEstado.isBlank()) {
					direccion2 = direccion2.isBlank() ? "" : direccion2 + ", ";
					direccion3 = nameEstado;
					if (ciudadano.getCodigoPostal() != null) {
						direccion3 += ", C.P. " + ciudadano.getCodigoPostal();
					}
				}
				
				if (ciudadano.getIdEstado() != null 
						&& ciudadano.getIdDistritoFederal() != null
						&& ciudadano.getIdMunicipio() != null 
						&& ciudadano.getSeccion() != null) {
					int manzana = 0;
					int noLocalidad = 0;
					if (ciudadano.getManzana() != null) {
						manzana = ciudadano.getManzana();
					}
					if (ciudadano.getIdLocalidad() != null) {
						noLocalidad = ciudadano.getIdLocalidad().intValue();
					}
					geoElectorales = ciudadano.getIdEstado().intValue() + " "
									+ ciudadano.getIdDistritoFederal().intValue() + " "
									+ ciudadano.getIdMunicipio().intValue() + " " 
									+ ciudadano.getSeccion().intValue() + " "
									+ noLocalidad + " "
									+ manzana + " ";
				} else {
					geoElectorales = "";
				}
				
				if (archivoLogo != null 
					&& archivoLogo.exists()) {
					Image img = Image.getInstance(archivoLogo.getPath());
					img.scaleAbsolute(Utilidades.cmtoPuntos(parametros.getLogoLargo()),
							Utilidades.cmtoPuntos(parametros.getLogoAncho()));
					img.setAbsolutePosition(document.right() - Utilidades.cmtoPuntos(parametros.getLogoRight()),
							document.top() - Utilidades.cmtoPuntos(parametros.getLogoTop()));
					document.add(img);
				}

				PdfPCell nombreciudadano = new PdfPCell(new Paragraph(name, f1));
				nombreciudadano.setHorizontalAlignment(Element.ALIGN_LEFT);
				nombreciudadano.setVerticalAlignment(Element.ALIGN_MIDDLE);
				nombreciudadano.setBorder(0);
				
				PdfPCell celdaClaveElector = new PdfPCell(new Paragraph(claveElector, f1));
				celdaClaveElector.setHorizontalAlignment(Element.ALIGN_LEFT);
				celdaClaveElector.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celdaClaveElector.setBorder(0);
				
				PdfPCell celdaDireccion = new PdfPCell(new Paragraph(direccion, f1));
				celdaDireccion.setHorizontalAlignment(Element.ALIGN_LEFT);
				celdaDireccion.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celdaDireccion.setBorder(0);
				
				PdfPCell celdaDireccion2 = new PdfPCell(new Paragraph(direccion2, f1));
				celdaDireccion2.setHorizontalAlignment(Element.ALIGN_LEFT);
				celdaDireccion2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celdaDireccion2.setBorder(0);
				
				PdfPCell celdaDireccion3 = new PdfPCell(new Paragraph(direccion3, f1));
				celdaDireccion3.setHorizontalAlignment(Element.ALIGN_LEFT);
				celdaDireccion3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celdaDireccion3.setBorder(0);
				
				PdfPCell celdaGeoElec = new PdfPCell(new Paragraph(geoElectorales, f1));
				celdaGeoElec.setHorizontalAlignment(Element.ALIGN_LEFT);
				celdaGeoElec.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celdaGeoElec.setBorder(0);

				PdfPCell celdaSeccion = new PdfPCell(new Paragraph(seccion, f1));
				celdaSeccion.setHorizontalAlignment(Element.ALIGN_LEFT);
				celdaSeccion.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celdaSeccion.setBorder(0);
				
				PdfPCell celdanOrdenV = new PdfPCell(new Paragraph(nOrdenV, f1));
				celdanOrdenV.setHorizontalAlignment(Element.ALIGN_LEFT);
				celdanOrdenV.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celdanOrdenV.setBorder(0);
				
				PdfPCell celdablanco = new PdfPCell();
				celdablanco.setBorder(0);

				// Folio
				Barcode128 code128 = new Barcode128();
				code128.setCode(ciudadano.getFolio() != null ? ciudadano.getFolio().toString() : "");
				code128.setCodeType(Barcode.CODE128);
				
				// folio 1
				Image folio = code128.createImageWithBarcode(cb, null, null);
				folio.scaleAbsolute(Utilidades.cmtoPuntos(parametros.getFolioLargo()), 
									Utilidades.cmtoPuntos(parametros.getFolioAncho()));
				folio.setAbsolutePosition(document.right() - Utilidades.cmtoPuntos(parametros.getFolio1Right()),
						document.top() - Utilidades.cmtoPuntos(parametros.getFolio1Top()));
				folio.scalePercent(100);
				document.add(folio);
				
				// folio 2
				folio.setAbsolutePosition(document.right() - Utilidades.cmtoPuntos(parametros.getFolio2Right()),
						document.top() - Utilidades.cmtoPuntos(parametros.getFolio2Top()));
				folio.scalePercent(100);
				document.add(folio);

				// datos1
				PdfPTable tabla = new PdfPTable(1);
				tabla.addCell(nombreciudadano);
				tabla.addCell(celdaClaveElector);
				tabla.addCell(celdaDireccion);
				tabla.addCell(celdaDireccion2);
				tabla.addCell(celdaDireccion3);
				tabla.addCell(celdaGeoElec);
				tabla.setTotalWidth(Utilidades.cmtoPuntos(parametros.getDatos1Width()));
				tabla.writeSelectedRows(0, -1, document.left() + Utilidades.cmtoPuntos(parametros.getDatos1Left()),
						document.top() - Utilidades.cmtoPuntos(parametros.getDatos1Top()),
						writer.getDirectContent());

				// datos2
				nombreciudadano = new PdfPCell(new Paragraph(name, f2));
				nombreciudadano.setHorizontalAlignment(Element.ALIGN_LEFT);
				nombreciudadano.setVerticalAlignment(Element.ALIGN_MIDDLE);
				nombreciudadano.setBorder(0);
				
				celdaClaveElector = new PdfPCell(new Paragraph(claveElector, f2));
				celdaClaveElector.setHorizontalAlignment(Element.ALIGN_LEFT);
				celdaClaveElector.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celdaClaveElector.setBorder(0);
				
				celdaSeccion = new PdfPCell(new Paragraph(seccion, f2));
				celdaSeccion.setHorizontalAlignment(Element.ALIGN_LEFT);
				celdaSeccion.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celdaSeccion.setBorder(0);
				
				celdanOrdenV = new PdfPCell(new Paragraph(nOrdenV, f2));
				celdanOrdenV.setHorizontalAlignment(Element.ALIGN_LEFT);
				celdanOrdenV.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celdanOrdenV.setBorder(0);
								
				PdfPCell celdanOrdenG = new PdfPCell(new Paragraph(nReferenciaG, f2));
				celdanOrdenG.setHorizontalAlignment(Element.ALIGN_LEFT);
				celdanOrdenG.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celdanOrdenG.setBorder(0);
				
				PdfPCell celdaSemilla = new PdfPCell(new Paragraph(semilla, f2));
				celdaSemilla.setHorizontalAlignment(Element.ALIGN_LEFT);
				celdaSemilla.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celdaSemilla.setBorder(0);
				
				celdablanco = new PdfPCell();
				celdablanco.setBorder(0);

				PdfPTable tabla2 = new PdfPTable(2);
				tabla2.addCell(nombreciudadano);
				tabla2.addCell(celdanOrdenV);
				tabla2.addCell(celdaClaveElector);
				tabla2.addCell(celdanOrdenG);
				tabla2.addCell(celdaSeccion);
				tabla2.addCell(ciudadano.getSemilla() == null?celdablanco:celdaSemilla);
				tabla2.setTotalWidth(Utilidades.cmtoPuntos(parametros.getDatos2Width()));
				tabla2.writeSelectedRows(0, -1, document.left() + Utilidades.cmtoPuntos(parametros.getDatos2Left()),
						document.top() - Utilidades.cmtoPuntos(parametros.getDatos2Top()),
						writer.getDirectContent());

				// Datos 3
				tabla2 = new PdfPTable(2);
				tabla2.addCell(nombreciudadano);
				tabla2.addCell(celdanOrdenV);
				tabla2.addCell(celdaClaveElector);
				tabla2.addCell(celdanOrdenG);
				tabla2.addCell(celdaSeccion);
				tabla2.addCell(ciudadano.getSemilla() == null?celdablanco:celdaSemilla);
				tabla2.setTotalWidth(Utilidades.cmtoPuntos(parametros.getDatos3Width()));
				tabla2.writeSelectedRows(0, -1, document.left() + Utilidades.cmtoPuntos(parametros.getDatos3Left()),
						document.top() - Utilidades.cmtoPuntos(parametros.getDatos3Top()),
						writer.getDirectContent());

			} else if (frenteReverso.equals("R")) {
				Font f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
				boolean art = false;
				
				if (archivoFirma != null 
						&& archivoFirma.exists()) {
					Image img1 = Image.getInstance(archivoFirma.getPath());
					img1.scaleAbsolute(Utilidades.cmtoPuntos(parametros.getFirmaLargo()),
							Utilidades.cmtoPuntos(parametros.getFirmaAncho()));
					img1.setAbsolutePosition(document.right() - Utilidades.cmtoPuntos(parametros.getFirmaRight()),
							document.top() - Utilidades.cmtoPuntos(parametros.getFirmaTop()));
					document.add(img1);
					art = true;
				} 
				
				if (direccion != null) {
					PdfPTable tabla = new PdfPTable(1);
					tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell nombreJunta = new PdfPCell(new Paragraph(direccion.getName(), f1));
					PdfPCell direc = new PdfPCell(new Paragraph("Domicilio: " + direccion.getDomicilio(), f1));
					PdfPCell telefono = new PdfPCell(new Paragraph(this.telefono != null && !this.telefono.isBlank() ? 
																	"Teléfono: " + this.telefono 
																	: "Teléfono: ", f1));
					PdfPCell blanco = new PdfPCell(new Paragraph("                  ", f1));

					nombreJunta.setHorizontalAlignment(Element.ALIGN_LEFT);
					nombreJunta.setVerticalAlignment(Element.ALIGN_TOP);
					nombreJunta.setBorder(0);

					direc.setHorizontalAlignment(Element.ALIGN_LEFT);
					direc.setVerticalAlignment(Element.ALIGN_MIDDLE);
					direc.setBorder(0);
					
					blanco.setHorizontalAlignment(Element.ALIGN_LEFT);
					blanco.setVerticalAlignment(Element.ALIGN_BASELINE);
					blanco.setBorder(0);

					telefono.setHorizontalAlignment(Element.ALIGN_LEFT);
					telefono.setVerticalAlignment(Element.ALIGN_BASELINE);
					telefono.setBorder(0);

					tabla.addCell(nombreJunta);
					tabla.addCell(direc);
					tabla.addCell(telefono);

					tabla.setTotalWidth(Utilidades.cmtoPuntos(parametros.getDireJuntaWidth()));
					tabla.writeSelectedRows(0, -1,
							document.left() - Utilidades.cmtoPuntos(parametros.getDireJuntaLeft()),
							document.top() - Utilidades.cmtoPuntos(parametros.getDireJuntaTop()),
							writer.getDirectContent());
					art = true;
				} else if (this.telefono != null) {
					PdfPTable tabla = new PdfPTable(1);
					PdfPCell telefono = new PdfPCell(new Paragraph(this.telefono, f1));
					telefono.setHorizontalAlignment(Element.ALIGN_CENTER);
					telefono.setVerticalAlignment(Element.ALIGN_MIDDLE);
					telefono.setBorder(0);
					tabla.addCell(telefono);
					tabla.setTotalWidth(Utilidades.cmtoPuntos(parametros.getDireJuntaWidth()));
					tabla.writeSelectedRows(0, -1,
							document.left() - Utilidades.cmtoPuntos(parametros.getDireJuntaLeft()),
							document.top() - Utilidades.cmtoPuntos(parametros.getDireJuntaTop()),
							writer.getDirectContent());
					art = true;
				}
				
				if (!art) {
					PdfPTable tabla = new PdfPTable(1);
					PdfPCell telefono = new PdfPCell(new Paragraph("    ", f1));
					telefono.setHorizontalAlignment(Element.ALIGN_CENTER);
					telefono.setVerticalAlignment(Element.ALIGN_MIDDLE);
					telefono.setBorder(0);
					tabla.addCell(telefono);
					tabla.setTotalWidth(Utilidades.cmtoPuntos(parametros.getDireJuntaWidth()));
					tabla.writeSelectedRows(0, -1,
							document.left() - Utilidades.cmtoPuntos(parametros.getDireJuntaLeft()),
							document.top() - Utilidades.cmtoPuntos(parametros.getDireJuntaTop()),
							writer.getDirectContent());
				}

			}
			
		} catch (Exception e) {
			logger.error("ERROR HLPPDFImpresionCarta -onStartPage: " ,e);
		}
		
		pageNumber++;
	}

	public List<DTODatosInsaculados> getCiudadanos() {
		return ciudadanos;
	}

	public DTOParametrosMargenes getParametros() {
		return parametros;
	}

	public String getFrenteReverso() {
		return frenteReverso;
	}
	
}
