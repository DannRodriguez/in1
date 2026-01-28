package mx.ine.procprimerinsa.bsd;

import java.io.Serializable;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOEstatusCargaPrimeraCapaDistrito;
import mx.ine.procprimerinsa.form.FormCargaPrimeraCapa;

public interface BSDCargaPrimeraCapaInterface extends Serializable {
	
	public List<DTOEstatusCargaPrimeraCapaDistrito> obtieneEstatusCargaPrimeraCapa(List<Integer> detalles, 
			Integer idCorte);
	
	public List<DTOEstatusCargaPrimeraCapaDistrito> obtieneLogEstatusCargaPrimeraCapa(List<Integer> detalles, Integer idCorte);
	
	public boolean reiniciarEstatusCarga(List<Integer> detalles, String usuario);
	
	public boolean reiniciaCargaOrdenada(Integer idProceso, List<Integer> detalles);
	
	public List<String> obtieneEstatusIndices();
	
	public String creaEliminaIndices(boolean isCrea);
	
	public String recolectarEstadisticas();
	
	public boolean obtieneEstatusServidoresPublicos(FormCargaPrimeraCapa form, Integer idDetalle);
	
	public boolean obtieneEstatusInsumos(FormCargaPrimeraCapa form, Integer idProceso);
	
	public String ejecutaCargaARES(Integer idDetalle, String usuario);
	
	public boolean spoolServidoresPublicos(Integer idDetalle, String rutaArchivo);
	
	public boolean spoolAreasSecciones(Integer idProceso, String tipoEleccion, String rutaArchivo);
	
	public boolean spoolSeccionesCompartidas(Integer idProceso, String tipoEleccion, String rutaArchivo);
	
	public boolean ejecutaCargaOrdenada(Integer idProceso, String tipoEleccion, String rutaArchivo, String usuario);
	
	public boolean spoolOrdenada(Integer idProceso, String tipoEleccion, String rutaArchivo);
	
	public DTOEstatusCargaPrimeraCapaDistrito obtieneEstatusCargaPrimeraCapaDistrito(Integer idDetalle,
			Integer idParticipacion, Integer idCorte);
	
	public boolean actualizaEstatusCargaPrimeraCapaDistrito(DTOEstatusCargaPrimeraCapaDistrito distrito);
	
	public String ejecutaMarcadoNombreOrden(DTOEstatusCargaPrimeraCapaDistrito distrito);
	
	public String ejecutaReinicioYaEsInsaculado(DTOEstatusCargaPrimeraCapaDistrito distrito);
	
	public String reiniciaCargaServidoresPublicos(Integer idDetalle);
	
	public boolean ejecutaCargaServidoresPublicos(Integer idProceso, Integer idDetalle);
	
	public String ejecutaMarcadoServidorPublico(Integer idProceso, Integer idDetalle);
	
	public String ejecutaReinicioServidorPublico(Integer idProceso, Integer idDetalle);
	
	public String ejecutaCarga(Integer tipoEjecucion, DTOEstatusCargaPrimeraCapaDistrito distrito, 
			String letra, String usuario);
	
	public List<String> obtieneEstatusTriggers();
	
	public String habilitaDeshabilitaTriggers(boolean isHabilita);

}
