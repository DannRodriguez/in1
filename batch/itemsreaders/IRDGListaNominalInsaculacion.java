package mx.ine.procprimerinsa.batch.itemsreaders;

import java.util.List;
import java.util.Queue;

import mx.ine.procprimerinsa.dg.dao.DAOListaNominalV7Interface;
import mx.ine.procprimerinsa.dg.dto.DTOListaNominalDG;

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

public class IRDGListaNominalInsaculacion implements ItemReader<DTOListaNominalDG>, ItemStream, InitializingBean {

	private static final Logger logger = Logger.getLogger(IRDGListaNominalInsaculacion.class);
	private static final String INDICE_ACTUAL = "indice.actual";
	
	private Integer indiceActual = 0;
	private Queue<DTOListaNominalDG> dtoListCache = null;
	
	private Integer idCorteLN;
	private Integer idEstado;
	private Integer idGeograficoParticipacion;
	private Integer seccion;
	private String tipoCasilla;
	private Integer idCasilla;
	private Integer mesInsacular;
	private String letraInsacular;
	private Integer numeroInsacularTotal;
	private Integer validaYaEsInsaculado;
	private List<Integer> mesesYaSorteadosList;
	private Integer consideraExtraordinarias;
	
	@Autowired
	@Qualifier("daoListaNominalV7")
	private DAOListaNominalV7Interface daoListaNominalV7;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(idCorteLN, "Se debe proporcionar el id de corte de la lista nominal.");
		Assert.notNull(idEstado, "Se debe proporcionar idEstado.");
		Assert.notNull(idGeograficoParticipacion, "Se debe proporcionar el idGeograficoParticipacion.");
		Assert.notNull(seccion, "Se debe proporcionar seccion.");
		Assert.notNull(tipoCasilla, "Se debe proporcionar tipoCasilla.");
		Assert.notNull(idCasilla, "Se debe proporcionar idCasilla.");
		Assert.notNull(mesInsacular, "Se debe proporcionar mesInsacular.");
		Assert.notNull(letraInsacular, "Se debe proporcionar letraInsacular.");
		Assert.notNull(numeroInsacularTotal, "Se debe proporcionar numeroInsacularTotal.");
		Assert.notNull(validaYaEsInsaculado, "Se debe proporcionar validaYaEsInsaculado.");
		Assert.notNull(mesesYaSorteadosList, "Se debe proporcionar mesesYaSorteadosList.");
		Assert.notNull(consideraExtraordinarias, "Se debe proporcionar consideraExtraordinarias.");
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
	public DTOListaNominalDG read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		if (dtoListCache == null) {
				dtoListCache = daoListaNominalV7.getInsaculadosPorDistritoSeccionCasilla(idCorteLN, 
																					idEstado, 
																					idGeograficoParticipacion, 
																					seccion,
																					tipoCasilla, 
																					idCasilla, 
																					mesInsacular, 
																					letraInsacular, 
																					validaYaEsInsaculado,
																					mesesYaSorteadosList,
																					consideraExtraordinarias,
																					indiceActual, 
																					numeroInsacularTotal);
		}

		if (dtoListCache != null
				&& !dtoListCache.isEmpty()
				&& (indiceActual < numeroInsacularTotal)) {
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

	public Integer getSeccion() {
		return seccion;
	}

	public void setSeccion(Integer seccion) {
		this.seccion = seccion;
	}

	public String getTipoCasilla() {
		return tipoCasilla;
	}

	public void setTipoCasilla(String tipoCasilla) {
		this.tipoCasilla = tipoCasilla;
	}

	public Integer getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Integer idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Integer getMesInsacular() {
		return mesInsacular;
	}

	public void setMesInsacular(Integer mesInsacular) {
		this.mesInsacular = mesInsacular;
	}

	public String getLetraInsacular() {
		return letraInsacular;
	}

	public void setLetraInsacular(String letraInsacular) {
		this.letraInsacular = letraInsacular;
	}

	public Integer getNumeroInsacularTotal() {
		return numeroInsacularTotal;
	}

	public void setNumeroInsacularTotal(Integer numeroInsacularTotal) {
		this.numeroInsacularTotal = numeroInsacularTotal;
	}

	public Integer getValidaYaEsInsaculado() {
		return validaYaEsInsaculado;
	}

	public void setValidaYaEsInsaculado(Integer validaYaEsInsaculado) {
		this.validaYaEsInsaculado = validaYaEsInsaculado;
	}

	public List<Integer> getMesesYaSorteadosList() {
		return mesesYaSorteadosList;
	}

	public void setMesesYaSorteadosList(List<Integer> mesesYaSorteadosList) {
		this.mesesYaSorteadosList = mesesYaSorteadosList;
	}

	public Integer getConsideraExtraordinarias() {
		return consideraExtraordinarias;
	}

	public void setConsideraExtraordinarias(Integer consideraExtraordinarias) {
		this.consideraExtraordinarias = consideraExtraordinarias;
	}

}
