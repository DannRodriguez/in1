package mx.ine.procprimerinsa.helper.impl;

import java.io.Serializable;

import mx.ine.procprimerinsa.dto.DTOConfiguracionParametros;
import mx.ine.procprimerinsa.form.FormConfiguracionParametros;

public class HLPTransformadorConfiguracionParametros implements Serializable {
	
	private static final long serialVersionUID = -1303639263204928423L;

	public DTOConfiguracionParametros obtenerDTOConfiguracionParametros(FormConfiguracionParametros form, 
			boolean agregaDescripcion) {
		DTOConfiguracionParametros param = new DTOConfiguracionParametros();
		
		if(agregaDescripcion) {
			param.setDescripcionParametro(form.getDescripcionParametro());
			param.setDescripcionValores(form.getDescripcionValores());
		} else {
			param.setIdParametro(form.getIdParametroSeleccionado());
		}
		
		param.setIdProceso(form.getIdProceso());
		param.setIdDetalle(form.getIdDetalle());
		
		if(form.getEstadoSeleccionado() == null) {
			param.setIdEstado(0);
		} else {
			param.setIdEstado(form.getEstadoSeleccionado().getIdEstado());
		}
		
		if(form.getDistritoSeleccionado() == null) {
			param.setIdDistrito(0);
		} else {
			param.setIdDistrito(form.getDistritoSeleccionado().getId());
		}
		
		param.setTipoJunta(form.getTipoJunta());
		param.setValorParametro(form.getValorParametro());
		
		return param;
	}

}
