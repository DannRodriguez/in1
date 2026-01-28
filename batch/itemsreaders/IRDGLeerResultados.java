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
import mx.ine.procprimerinsa.dto.db.DTOResultados1aInsa;

public class IRDGLeerResultados implements ItemReader<DTOResultados1aInsa>, ItemStream, InitializingBean {

	private static final Logger logger = Logger.getLogger(IRDGLeerResultados.class);
	private static final String INDICE_ACTUAL = "indice.actual";

	private Integer indiceActual = 0;
	private Queue<DTOResultados1aInsa> dtoListCache = null;

	private Integer idDetalleProceso;
	private Integer idParticipacion;
	private Integer idTipoVoto;
	private Integer maxResults;

	@Autowired
	@Qualifier("asDatosInsaculados")
	private ASDatosInsaculadosInterface asDatosInsaculados;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(idDetalleProceso, "Se debe proporcionar el idDetalleProceso");
		Assert.notNull(idParticipacion, "Se debe proporcionar el idParticipacion");
		Assert.notNull(idTipoVoto, "Se debe proporcionar el idTipoVoto");
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
	public DTOResultados1aInsa read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		if (dtoListCache == null || dtoListCache.isEmpty()) {
			dtoListCache = asDatosInsaculados.consultaResultados(idDetalleProceso,
																idParticipacion, 
																idTipoVoto, 
																indiceActual.longValue(), 
																maxResults);
		}

		if (dtoListCache != null && !dtoListCache.isEmpty()) {
			indiceActual++;
			return dtoListCache.poll();
		}

		return null;
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

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
}
