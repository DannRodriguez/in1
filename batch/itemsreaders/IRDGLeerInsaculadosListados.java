package mx.ine.procprimerinsa.batch.itemsreaders;

import java.util.Queue;

import org.jboss.logging.Logger;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import mx.ine.procprimerinsa.as.ASDatosInsaculadosInterface;
import mx.ine.procprimerinsa.dto.db.DTODatosInsaculados;

public class IRDGLeerInsaculadosListados implements ItemReader<DTODatosInsaculados>, ItemStream, InitializingBean {

	private static final Logger logger = Logger.getLogger(IRDGLeerInsaculadosListados.class);
	private static final String INDICE_ACTUAL = "indice.actual";

	private Integer indiceActual = 0;
	private Queue<DTODatosInsaculados> dtoListCache = null;
	
	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	private Integer idParticipacion;
	private Integer idTipoVoto;
	private Integer tipoIntegracion;
	private String letra;
	private Integer maxResults;
	
	@Autowired
	@Qualifier("asDatosInsaculados")
	private ASDatosInsaculadosInterface asDatosInsaculados;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(idProcesoElectoral, "Se debe proporcionar el idProcesoElectoral.");
		Assert.notNull(idDetalleProceso, "Se debe proporcionar el idDetalleProceso");
		Assert.notNull(idParticipacion, "El idParticipacion es requerido");
		Assert.notNull(idTipoVoto, "El idTipoVoto no puede ser nulo");
		Assert.notNull(tipoIntegracion, "El tipo de integración no puede ser nulo");
		Assert.notNull(letra, "La letra a insacular es requerida");
	}

	@Override
	public void open(@NonNull ExecutionContext executionContext) throws ItemStreamException {
		if (executionContext.containsKey(INDICE_ACTUAL)) {
			indiceActual = executionContext.getInt(INDICE_ACTUAL);
		} else {
			indiceActual = 0;
		}
	}

	@Override
	public void update(@NonNull ExecutionContext executionContext) throws ItemStreamException {
		executionContext.putInt(INDICE_ACTUAL, indiceActual);
	}

	@Override
	public void close() throws ItemStreamException {
		logger.info("close");
	}

	@Override
	public DTODatosInsaculados read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		if (dtoListCache == null || dtoListCache.isEmpty()) {
			dtoListCache = asDatosInsaculados.obtenerListaInsaculados(idProcesoElectoral,
																	idDetalleProceso,
																	idParticipacion, 
																	idTipoVoto,
																	tipoIntegracion,
																	letra,
																	maxResults, 
																	indiceActual.longValue());
		}

		if (dtoListCache != null && !dtoListCache.isEmpty()) {
			indiceActual++;
			return dtoListCache.poll();
		}

		return null;
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

	public Integer getIdTipoVoto() {
		return idTipoVoto;
	}

	public void setIdTipoVoto(Integer idTipoVoto) {
		this.idTipoVoto = idTipoVoto;
	}

	public Integer getTipoIntegracion() {
		return tipoIntegracion;
	}

	public void setTipoIntegracion(Integer tipoIntegracion) {
		this.tipoIntegracion = tipoIntegracion;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
}
