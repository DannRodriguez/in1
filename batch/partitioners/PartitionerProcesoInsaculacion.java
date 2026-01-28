package mx.ine.procprimerinsa.batch.partitioners;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.logging.Logger;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import mx.ine.procprimerinsa.as.ASCTipoVotoInterface;
import mx.ine.procprimerinsa.bo.BOProcesoInsaInterface;
import mx.ine.procprimerinsa.dg.dao.DAOListaNominalV7Interface;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.util.Constantes;

public class PartitionerProcesoInsaculacion implements Partitioner, InitializingBean {

	private static final Logger logger = Logger.getLogger(PartitionerProcesoInsaculacion.class);

	private Integer idDetalleProceso;
	private Integer idParticipacion;
	private Integer idCorteLN;
	private Integer idEstado;
	private Integer idGeograficoParticipacion;
	private Integer consideraExtraordinarias;
	
	@Autowired
	@Qualifier("boProcesoInsa")
	private BOProcesoInsaInterface boProcesoInsa;

	@Autowired
	@Qualifier("asCTipoVoto")
	private ASCTipoVotoInterface asCTipoVoto;
	
	@Autowired
	@Qualifier("daoListaNominalV7")
	private DAOListaNominalV7Interface daoListaNominalV7;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(idDetalleProceso, "Se debe proporcionar el idDetalleProceso.");
		Assert.notNull(idParticipacion, "Se debe proporcionar idParticipacion.");
		Assert.notNull(idCorteLN, "Se debe proporcionar el id de corte de la lista nominal.");
		Assert.notNull(idEstado, "Se debe proporcionar idEstado.");
		Assert.notNull(idGeograficoParticipacion, "Se debe proporcionar idGeograficoParticipacion.");
		Assert.notNull(consideraExtraordinarias, "Se debe proporcionar consideraExtraordinarias.");
	}
	
	@Override
	public @NonNull Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> partitionMap = new HashMap<>();
		
		try {
			
			
			List<Object[]> secciones =	daoListaNominalV7.getSeccionesCasillasAInsacularPorDistrito(
																		idCorteLN,
																		idEstado,
																		idGeograficoParticipacion,
																		consideraExtraordinarias);
			
			Map<Integer, DTOCTipoVoto> tiposVoto = asCTipoVoto.obtieneTiposVotoPorParticipacion(idDetalleProceso, idParticipacion);
			
			ExecutionContext executionContext = null;

			for (Object[] s : secciones) {
				int seccion = Integer.parseInt(s[0].toString());
				String tipoCasilla = s[1].toString();
				int idCasilla = Integer.parseInt(s[2].toString());
				int numeroElectores = Integer.parseInt(s[3].toString());
				
				executionContext = new ExecutionContext();
				
				Map<Integer, Integer> numeroInsacular = new LinkedHashMap<>();
				Map<Integer, ConcurrentHashMap<String, Integer>> conteoSexo = new LinkedHashMap<>();
				Map<Integer, ConcurrentHashMap<Integer, Integer>> conteoMes = new LinkedHashMap<>();
				Map<Integer, AtomicInteger> conteoDobleNacionalidad = new LinkedHashMap<>();
				Map<Integer, ConcurrentHashMap<String, Integer>> conteoSexoDobleNacionalidad = new LinkedHashMap<>();
				
				Integer numeroInsacularTotal = 0;
				for(Map.Entry<Integer, DTOCTipoVoto> tipoVoto : tiposVoto.entrySet()) {
					numeroInsacular.put(tipoVoto.getKey(), boProcesoInsa.calculaNumeroInsacular(numeroElectores,
															tipoVoto.getValue().getPorcentajeInsacular(),
															tipoVoto.getValue().getMinimoInsacular()));
					numeroInsacularTotal += numeroInsacular.get(tipoVoto.getKey());
					conteoSexo.put(tipoVoto.getKey(), inicializaConteoSexo());
					conteoMes.put(tipoVoto.getKey(), inicializaConteoMeses());
					conteoDobleNacionalidad.put(tipoVoto.getKey(), new AtomicInteger());
					conteoSexoDobleNacionalidad.put(tipoVoto.getKey(), inicializaConteoSexo());
				}
				
				executionContext.putInt("seccion", seccion);
				executionContext.putString("tipoCasilla",  tipoCasilla);
				executionContext.putInt("idCasilla", idCasilla);
				executionContext.put("tiposVoto", tiposVoto);
				executionContext.putInt("numeroElectores", numeroElectores);
				executionContext.put("numeroInsacular", numeroInsacular);
				executionContext.putInt("numeroInsacularTotal", numeroInsacularTotal);
				executionContext.put("conteoSexo", conteoSexo);
				executionContext.put("conteoMes", conteoMes);
				executionContext.put("conteoDobleNacionalidad", conteoDobleNacionalidad);
				executionContext.put("conteoSexoDobleNacionalidad", conteoSexoDobleNacionalidad);
				
				partitionMap.put("E" + idEstado 
						+ "PA" + idGeograficoParticipacion 
						+ "S"  + seccion + "C" + tipoCasilla + idCasilla, executionContext);
			}

		} catch (Exception e) {
			logger.error("ERROR PartitionerProcesoInsaculacion -partition: ", e);
			throw new RuntimeException("ERROR PartitionerProcesoInsaculacion -partition: ", e);
		}
		
		logger.info("PartitionerProcesoInsaculacion - partition: Número de particiones creadas: " + partitionMap.size());
		return partitionMap;
	}

	private ConcurrentHashMap<String, Integer> inicializaConteoSexo() {
		ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(3);
		map.put(Constantes.GENERO_HOMBRE, 0);
		map.put(Constantes.GENERO_MUJER, 0);
		map.put(Constantes.GENERO_NO_BINARIO, 0);
		return map;
	}

	private ConcurrentHashMap<Integer, Integer> inicializaConteoMeses() {
		ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>(12);
		for (int i = 1; i <= 12; i++) map.put(i, 0);
		return map;
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

	public Integer getIdCorteLN() {
		return idCorteLN;
	}

	public void setIdCorteLN(Integer idCorteLN) {
		this.idCorteLN = idCorteLN;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Integer getIdGeograficoParticipacion() {
		return idGeograficoParticipacion;
	}

	public void setIdGeograficoParticipacion(Integer idGeograficoParticipacion) {
		this.idGeograficoParticipacion = idGeograficoParticipacion;
	}

	public Integer getConsideraExtraordinarias() {
		return consideraExtraordinarias;
	}

	public void setConsideraExtraordinarias(Integer consideraExtraordinarias) {
		this.consideraExtraordinarias = consideraExtraordinarias;
	}
	
}
