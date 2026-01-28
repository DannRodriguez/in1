package mx.ine.procprimerinsa.batch.itemsprocessors;

import java.util.regex.Pattern;

import org.jboss.logging.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.NonNull;

import mx.ine.procprimerinsa.dto.DTODatosPersonales;

public class IPValidaDatosPersonales implements ItemProcessor<DTODatosPersonales, DTODatosPersonales>, InitializingBean {
	
	private static final Logger logger = Logger.getLogger(IPValidaDatosPersonales.class);
	
	private Integer idEstado;
	private boolean notValidatePattern = true;
	private String patternToValidate;
	private Pattern pattern;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(patternToValidate != null && !patternToValidate.isBlank()) {
			notValidatePattern = false;
			pattern = Pattern.compile(patternToValidate);
		}
	}

	@Override
	public DTODatosPersonales process(@NonNull DTODatosPersonales item) throws Exception {	
		return validaEstado(item)
			&& validaLongitudCampos(item) 
			&& (notValidatePattern || validaCaracteresEspeciales(item)) 
			? item : null;
	}
	
	private boolean validaEstado(DTODatosPersonales item) {
		if(!item.getIdEstado().equals(idEstado)) {
			logger.error("El estado del registro es diferente al que se está validando: (idEstado-" + idEstado + "), " + item);
			return false;
		}
		
		return true;
	}
	
	private boolean validaLongitudCampos(DTODatosPersonales item) {
		if(item.getClaveElector().length() > 18
			|| item.getCalle().length() > 50
			|| item.getNumeroExterior().length() > 8
			|| item.getNumeroInterior().length() > 8
			|| item.getColonia().length() > 60
			|| item.getCodigoPostal().length() > 8) {
			logger.error("La dirección del registro no cumple con las longitudes establecidas de los campos: " + item);
			return false;
		}
		
		return true;
	}
	
	private boolean validaCaracteresEspeciales(DTODatosPersonales item) {
		if(pattern.matcher(item.getCalle()).matches()
			|| pattern.matcher(item.getColonia()).matches()) {
			logger.error("La dirección del registro no cumple con las validaciones de caracteres establecidas de los campos: " + item);
			return false;
		}
		
		return true;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getPatternToValidate() {
		return patternToValidate;
	}

	public void setPatternToValidate(String patternToValidate) {
		this.patternToValidate = patternToValidate;
	}
	
}
