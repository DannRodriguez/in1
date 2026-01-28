package mx.ine.procprimerinsa.batch.itemsprocessors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.logging.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import mx.ine.procprimerinsa.dg.dto.DTOListaNominalDG;
import mx.ine.procprimerinsa.util.Constantes;

public class IPProcesoPrimeraInsa implements ItemProcessor<DTOListaNominalDG, DTOListaNominalDG>, InitializingBean {

	private static final Logger logger = Logger.getLogger(IPProcesoPrimeraInsa.class);

	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	private Integer idParticipacion;
	private AtomicInteger folio;
	private Map<Integer, Integer> numeroInsacular;
	private Integer idCorteLNAActualizar;
	private Map<Integer, ConcurrentHashMap<String, Integer>> conteoSexo;
	private Map<Integer, ConcurrentHashMap<Integer, Integer>> conteoMes;
	private Map<Integer, AtomicInteger> conteoDobleNacionalidad;
	private Map<Integer, ConcurrentHashMap<String, Integer>> conteoSexoDobleNacionalidad;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(idProcesoElectoral, "Se debe proporcionar el idProcesoElectoral.");
		Assert.notNull(idDetalleProceso, "Se debe proporcionar el idDetalleProceso.");
		Assert.notNull(idParticipacion, "Se debe proporcionar idParticipacion.");
		Assert.notNull(folio, "Se debe proporcionar folio.");
		Assert.notNull(numeroInsacular, "Se debe proporcionar numeroInsacular.");
		Assert.notNull(idCorteLNAActualizar, "Se debe proporcionar idCorteLNAActualizar.");
		Assert.notNull(conteoSexo, "Se debe proporcionar conteoSexo.");
		Assert.notNull(conteoMes, "Se debe proporcionar conteoMes.");
		Assert.notNull(conteoDobleNacionalidad, "Se debe proporcionar conteoDobleNacionalidad.");
		Assert.notNull(conteoSexoDobleNacionalidad, "Se debe proporcionar conteoSexoDobleNacionalidad.");
	}

	@Override
	public DTOListaNominalDG process(@NonNull DTOListaNominalDG item) throws Exception {
		
		try {
			item.setIdProcesoElectoral(idProcesoElectoral);
			item.setIdDetalleProceso(idDetalleProceso);
			item.setIdParticipacion(idParticipacion);
			item.setFolio(folio.incrementAndGet());
			item.setIdCorteLNAActualizar(idCorteLNAActualizar);
			
			for(Map.Entry<Integer, Integer> numeroInsacularVoto : numeroInsacular.entrySet()) {
				if(numeroInsacularVoto.getValue() > 0) {
					numeroInsacularVoto.setValue(numeroInsacularVoto.getValue()-1);
					item.setIdTipoVoto(numeroInsacularVoto.getKey());
					conteoSexo.get(numeroInsacularVoto.getKey()).compute(item.getSexo(), (k , v)  -> v + 1);
					conteoMes.get(numeroInsacularVoto.getKey()).compute(item.getMesNacimiento(), (k , v)  -> v + 1);
					
					if(Constantes.ENTIDAD_NACIMIENTO_DOBLE_NACIONALIDAD.equals(item.getEntidadNacimiento())) {
						conteoDobleNacionalidad.get(numeroInsacularVoto.getKey()).incrementAndGet();
						conteoSexoDobleNacionalidad.get(numeroInsacularVoto.getKey()).compute(item.getSexo(), (k , v)  -> v + 1);
					}
					
					break;
				}
			}
			
			return item;
		} catch(Exception e) {
			logger.error("ERROR IPProcesoPrimeraInsa -process: ", e);
			throw e;
		}
	}

	public Integer getIdProcesoElectoral() {
		return idProcesoElectoral;
	}

	public void setIdProcesoElectoral(Integer idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}

	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}

	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}

	public AtomicInteger getFolio() {
		return folio;
	}

	public void setFolio(AtomicInteger folio) {
		this.folio = folio;
	}

	public Map<Integer, Integer> getNumeroInsacular() {
		return numeroInsacular;
	}

	public void setNumeroInsacular(Map<Integer, Integer> numeroInsacular) {
		this.numeroInsacular = numeroInsacular;
	}

	public Integer getIdCorteLNAActualizar() {
		return idCorteLNAActualizar;
	}

	public void setIdCorteLNAActualizar(Integer idCorteLNAActualizar) {
		this.idCorteLNAActualizar = idCorteLNAActualizar;
	}

	public Map<Integer, ConcurrentHashMap<String, Integer>> getConteoSexo() {
		return conteoSexo;
	}

	public void setConteoSexo(Map<Integer, ConcurrentHashMap<String, Integer>> conteoSexo) {
		this.conteoSexo = conteoSexo;
	}

	public Map<Integer, ConcurrentHashMap<Integer, Integer>> getConteoMes() {
		return conteoMes;
	}

	public void setConteoMes(Map<Integer, ConcurrentHashMap<Integer, Integer>> conteoMes) {
		this.conteoMes = conteoMes;
	}

	public Map<Integer, AtomicInteger> getConteoDobleNacionalidad() {
		return conteoDobleNacionalidad;
	}

	public void setConteoDobleNacionalidad(Map<Integer, AtomicInteger> conteoDobleNacionalidad) {
		this.conteoDobleNacionalidad = conteoDobleNacionalidad;
	}

	public Map<Integer, ConcurrentHashMap<String, Integer>> getConteoSexoDobleNacionalidad() {
		return conteoSexoDobleNacionalidad;
	}

	public void setConteoSexoDobleNacionalidad(
			Map<Integer, ConcurrentHashMap<String, Integer>> conteoSexoDobleNacionalidad) {
		this.conteoSexoDobleNacionalidad = conteoSexoDobleNacionalidad;
	}
	
}
