package mx.ine.procprimerinsa.batch.itemsreaders;

import java.util.Queue;

import mx.ine.procprimerinsa.as.ASCargaPrimeraCapaInterface;
import mx.ine.procprimerinsa.dto.db.DTOServidoresPublicos;

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

public class IRDGSpoolServidoresPublicos implements ItemReader<DTOServidoresPublicos>, ItemStream, InitializingBean {

	private static final Logger logger = Logger.getLogger(IRDGSpoolServidoresPublicos.class);
	private static final String INDICE_ACTUAL = "indice.actual";
	
	private Integer indiceActual = 0;
	private Queue<DTOServidoresPublicos> dtoListCache = null;
	
	private Integer idDetalle;
	private Integer maxResults;
	
	@Autowired
	@Qualifier("asCargaPrimeraCapa")
	private ASCargaPrimeraCapaInterface asCargaPrimeraCapa;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(idDetalle, "Se debe proporcionar el id del detalle del proceso electoral.");
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
		logger.debug("close");
	}

	@Override
	public DTOServidoresPublicos read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		if (dtoListCache == null || dtoListCache.isEmpty()) {
				dtoListCache = asCargaPrimeraCapa.obtieneSpoolServidoresPublicos(idDetalle,
																				maxResults, 
																				indiceActual.longValue());
		}

		if (dtoListCache != null && !dtoListCache.isEmpty()) {
			indiceActual++;
			return dtoListCache.poll();
		}
		
		return null;
	}

	public Integer getIndiceActual() {
		return indiceActual;
	}

	public void setIndiceActual(Integer indiceActual) {
		this.indiceActual = indiceActual;
	}

	public Integer getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

}
