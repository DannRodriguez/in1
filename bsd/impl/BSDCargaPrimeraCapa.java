package mx.ine.procprimerinsa.bsd.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASCargaPrimeraCapaInterface;
import mx.ine.procprimerinsa.bsd.BSDCargaPrimeraCapaInterface;
import mx.ine.procprimerinsa.dto.DTOEstatusCargaPrimeraCapaDistrito;
import mx.ine.procprimerinsa.form.FormCargaPrimeraCapa;

@Component("bsdCargaPrimeraCapa")
@Scope("prototype")
public class BSDCargaPrimeraCapa implements BSDCargaPrimeraCapaInterface {

	private static final long serialVersionUID = -4234270448965554672L;
	
	@Autowired
	@Qualifier("asCargaPrimeraCapa")
	private ASCargaPrimeraCapaInterface asCargaPrimeraCapa;

	@Override
	public List<DTOEstatusCargaPrimeraCapaDistrito> obtieneEstatusCargaPrimeraCapa(List<Integer> detalles,
			Integer idCorte) {
		return asCargaPrimeraCapa.obtieneEstatusCargaPrimeraCapa(detalles, idCorte);
	}
	
	@Override
	public List<DTOEstatusCargaPrimeraCapaDistrito> obtieneLogEstatusCargaPrimeraCapa(List<Integer> detalles, Integer idCorte) {
		return asCargaPrimeraCapa.obtieneLogEstatusCargaPrimeraCapa(detalles, idCorte);
	}
	
	@Override
	public boolean reiniciarEstatusCarga(List<Integer> detalles, String usuario) {
		return asCargaPrimeraCapa.reiniciarEstatusCarga(detalles, usuario);
	}
	
	@Override
	public boolean reiniciaCargaOrdenada(Integer idProceso, List<Integer> detalles) {
		return asCargaPrimeraCapa.reiniciaCargaOrdenada(idProceso, detalles);
	}
	
	@Override
	public List<String> obtieneEstatusIndices() {
		return asCargaPrimeraCapa.obtieneEstatusIndices();
	}
	
	@Override
	public String creaEliminaIndices(boolean isCrea) {
		return asCargaPrimeraCapa.creaEliminaIndices(isCrea);
	}
	
	@Override
	public String recolectarEstadisticas() {
		return asCargaPrimeraCapa.recolectarEstadisticas();
	}
	
	@Override
	public boolean obtieneEstatusServidoresPublicos(FormCargaPrimeraCapa form, Integer idDetalle) {
		return asCargaPrimeraCapa.obtieneEstatusServidoresPublicos(form, idDetalle);
	}
	
	@Override
	public boolean obtieneEstatusInsumos(FormCargaPrimeraCapa form, Integer idProceso) {
		return asCargaPrimeraCapa.obtieneEstatusInsumos(form, idProceso);
	}

	@Override
	public String ejecutaCargaARES(Integer idDetalle, String usuario) {
		return asCargaPrimeraCapa.ejecutaCargaARES(idDetalle, usuario);
	}
	
	@Override
	public boolean spoolServidoresPublicos(Integer idDetalle, String rutaArchivo) {
		return asCargaPrimeraCapa.spoolServidoresPublicos(idDetalle, rutaArchivo);
	}
	
	@Override
	public boolean spoolAreasSecciones(Integer idProceso, String tipoEleccion, String rutaArchivo) {
		return asCargaPrimeraCapa.spoolAreasSecciones(idProceso, tipoEleccion, rutaArchivo);
	}
	
	@Override
	public boolean spoolSeccionesCompartidas(Integer idProceso, String tipoEleccion, String rutaArchivo) {
		return asCargaPrimeraCapa.spoolSeccionesCompartidas(idProceso, tipoEleccion, rutaArchivo);
	}
	
	@Override
	public boolean ejecutaCargaOrdenada(Integer idProceso, String tipoEleccion, String rutaArchivo, String usuario) {
		return asCargaPrimeraCapa.ejecutaCargaOrdenada(idProceso, tipoEleccion, rutaArchivo, usuario);
	}
	
	@Override
	public boolean spoolOrdenada(Integer idProceso, String tipoEleccion, String rutaArchivo) {
		return asCargaPrimeraCapa.spoolOrdenada(idProceso, tipoEleccion, rutaArchivo);
	}
	
	@Override
	public DTOEstatusCargaPrimeraCapaDistrito obtieneEstatusCargaPrimeraCapaDistrito(Integer idDetalle,
			Integer idParticipacion, Integer idCorte) {
		return asCargaPrimeraCapa.obtieneEstatusCargaPrimeraCapaDistrito(idDetalle, idParticipacion, idCorte);
	}
	
	@Override
	public boolean actualizaEstatusCargaPrimeraCapaDistrito(DTOEstatusCargaPrimeraCapaDistrito distrito) {
		return asCargaPrimeraCapa.actualizaEstatusCargaPrimeraCapaDistrito(distrito);
	}

	@Override
	public String ejecutaMarcadoNombreOrden(DTOEstatusCargaPrimeraCapaDistrito distrito) {
		return asCargaPrimeraCapa.ejecutaMarcadoNombreOrden(distrito);
	}
	
	@Override
	public String ejecutaReinicioYaEsInsaculado(DTOEstatusCargaPrimeraCapaDistrito distrito) {
		return asCargaPrimeraCapa.ejecutaReinicioYaEsInsaculado(distrito);
	}
	
	@Override
	public String reiniciaCargaServidoresPublicos(Integer idDetalle) {
		return asCargaPrimeraCapa.reiniciaCargaServidoresPublicos(idDetalle);
	}
	
	@Override
	public boolean ejecutaCargaServidoresPublicos(Integer idProceso, Integer idDetalle) {
		return asCargaPrimeraCapa.ejecutaCargaServidoresPublicos(idProceso, idDetalle);
	}
	
	@Override
	public String ejecutaMarcadoServidorPublico(Integer idProceso, Integer idDetalle) {
		return asCargaPrimeraCapa.ejecutaMarcadoServidorPublico(idProceso, idDetalle);
	}
	
	@Override
	public String ejecutaReinicioServidorPublico(Integer idProceso, Integer idDetalle) {
		return asCargaPrimeraCapa.ejecutaReinicioServidorPublico(idProceso, idDetalle);
	}
	
	@Override
	public String ejecutaCarga(Integer tipoEjecucion, DTOEstatusCargaPrimeraCapaDistrito distrito, 
			String letra, String usuario) {
		return asCargaPrimeraCapa.ejecutaCarga(tipoEjecucion, distrito, letra, usuario);
	}
	
	@Override
	public List<String> obtieneEstatusTriggers() {
		return asCargaPrimeraCapa.obtieneEstatusTriggers();
	}
	
	@Override
	public String habilitaDeshabilitaTriggers(boolean isHabilita) {
		return asCargaPrimeraCapa.habilitaDeshabilitaTriggers(isHabilita);
	}

}
