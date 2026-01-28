package mx.ine.procprimerinsa.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import mx.ine.procprimerinsa.dto.DTOEstatusCargaPrimeraCapaDistrito;
import mx.ine.procprimerinsa.dto.db.DTOServidoresPublicos;

public interface DAOCargaPrimeraCapaInterface extends DAOGenericInterface<Serializable, Serializable> {
	
	public List<DTOEstatusCargaPrimeraCapaDistrito> obtieneEstatusCargaPrimeraCapa(List<Integer> detalles,
			Integer idCorte);
	
	public DTOEstatusCargaPrimeraCapaDistrito obtieneEstatusCargaPrimeraCapaDistrito(Integer idDetalle,
			Integer idParticipacion, Integer idCorte);
	
	public List<DTOEstatusCargaPrimeraCapaDistrito> obtieneLogEstatusCargaPrimeraCapa(List<Integer> detalles, Integer idCorte);
		
	public void actualizaEstatusCargaPrimeraCapaDistrito(DTOEstatusCargaPrimeraCapaDistrito distrito);
	
	public void reiniciarEstatusCarga(List<Integer> detalles, String usuario);
	
	public void reiniciaCargaOrdenada(Integer idProceso, List<Integer> detalles);
	
	public void reiniciaCargaServidoresPublicos(Integer idDetalle);
	
	public Queue<DTOServidoresPublicos> obtieneCargaServidoresPublicos(Integer idProceso, Integer idDetalle, 
			Integer maxResults, Long offset);
	
	public Queue<DTOServidoresPublicos> obtieneSpoolServidoresPublicos(Integer idDetalle, 
			Integer maxResults, Long offset);
	
	public Object[] obtieneCorteServidoresPublicos(Integer idDetalle);
	
	public Object[] obtieneCorteAreasSecciones(List<Integer> detalles);
	
	public Object[] obtieneCorteSeccionesCompartidas(List<Integer> detalles);
	
	public Object[] obtieneCorteOrdenada(Integer idProceso, List<Integer> detalles);
	
	public List<String> obtieneEstatusIndices();
	
	public List<String> obtieneEstatusTriggers();
	
	public Map<String, String> obtieneMapaParticipaciones(Integer idProceso, String tipoEleccion);
	
}
