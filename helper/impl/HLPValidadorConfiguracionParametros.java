package mx.ine.procprimerinsa.helper.impl;

import java.io.Serializable;

import mx.ine.procprimerinsa.form.FormConfiguracionParametros;

public class HLPValidadorConfiguracionParametros extends HLPTransformadorMensajes implements Serializable {

	private static final long serialVersionUID = -2644278054333933089L;
	
	private FormConfiguracionParametros form;
	
	public boolean validaDescripcion() {
		return !(form.getDescripcionParametro() == null
				|| form.getDescripcionParametro().trim().isEmpty()
				|| form.getDescripcionValores() == null
				|| form.getDescripcionValores().trim().isEmpty());
	}
	
	public boolean validaParametro() {
		return !(form.getIdProceso() == null
					|| form.getIdDetalle() == null
					|| form.getTipoJunta() == null
					|| form.getTipoJunta().trim().isEmpty()
					|| form.getTipoJunta().equals("0"));
	}

	public FormConfiguracionParametros getForm() {
		return form;
	}

	public void setForm(FormConfiguracionParametros form) {
		this.form = form;
	}

}
