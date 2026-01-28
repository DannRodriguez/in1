package mx.ine.procprimerinsa.helper.impl;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import mx.ine.procprimerinsa.form.FormImpresionCartas;
import mx.ine.procprimerinsa.util.Utilidades;

public class HLPValidadorImpresionCartaNotificacion extends HLPValidador{
	
	private static final long serialVersionUID = -1398023756287142921L;
	
	private FormImpresionCartas form;
	
	public HLPValidadorImpresionCartaNotificacion(FormImpresionCartas form) {
		super();
		this.form = form;
	}
	
	public boolean validaDatos() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(form.getTabActivo().equals(1)) {
			if( (form.getFirma() == null
					|| !form.getFirma())
				&& (form.getLogo() == null 
					|| !form.getLogo())
				&& (form.getDomicilioJunta() == null
					|| !form.getDomicilioJunta())
				&& (form.getTelefonoJunta() == null
					|| form.getTelefonoJunta().isBlank())) {
				context.addMessage("mensajeAlert", new FacesMessage(FacesMessage.SEVERITY_WARN, " ", 
																Utilidades.mensajeProperties("04E_seleccionaJunta")));
				return false;
			}
			return true;
		}
		
		if(form.getFiltro() == null 
				|| form.getFiltro().equals(0)) {
			agregarMensajePersonalizado(TipoMensajes.ERROR, "filtro", 
										Utilidades.mensajeProperties("validacion_mensajes_generales_dato_requerido"));
			return false;
		}
		
		if(form.getFiltro().equals(1)) {
			if(form.getIdAreaResponsabilidad() == null
					|| form.getIdAreaResponsabilidad().equals(0)) {
				agregarMensajePersonalizado(TipoMensajes.ERROR, "idAreaResponsabilidad", 
											Utilidades.mensajeProperties("validacion_mensajes_generales_dato_requerido"));
				return false;
			}
		} else if(form.getFiltro().equals(2)) {
			if(form.getSeccion() == null
					|| form.getSeccion().equals(0)) {
				agregarMensajePersonalizado(TipoMensajes.ERROR, "seccion", 
											Utilidades.mensajeProperties("validacion_mensajes_generales_dato_requerido"));
				return false;
			}
			
		} else if(form.getFiltro().equals(3)) {
			if(form.getFiltroIndividual() == null
					|| form.getFiltroIndividual().equals(0)) {
				agregarMensajePersonalizado(TipoMensajes.ERROR, "filtroIndividual", 
											Utilidades.mensajeProperties("validacion_mensajes_generales_dato_requerido"));
				return false;
			}
			
			if(form.getFiltroIndividual().equals(1)
					&& (form.getNumeroCredencialElector() == null
					|| form.getNumeroCredencialElector().isBlank())) {
				agregarMensajePersonalizado(TipoMensajes.ERROR, "numeroCredencialElector", 
											Utilidades.mensajeProperties("validacion_mensajes_generales_dato_requerido"));
				return false;
			}
			
			if(form.getFiltroIndividual().equals(2)
					&& (form.getFolio() == null
						|| form.getFolio().equals(0))) {
				agregarMensajePersonalizado(TipoMensajes.ERROR, "folio", 
											Utilidades.mensajeProperties("validacion_mensajes_generales_dato_requerido"));
				return false;
			}
			
		}
		
		return true;
	}

}
