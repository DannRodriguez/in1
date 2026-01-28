package mx.ine.procprimerinsa.bsd.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASArchivosInterface;
import mx.ine.procprimerinsa.as.ASCTipoVotoInterface;
import mx.ine.procprimerinsa.as.ASDatosInsaculadosInterface;
import mx.ine.procprimerinsa.as.ASGenerarLlavesInterface;
import mx.ine.procprimerinsa.as.ASParametrosInterface;
import mx.ine.procprimerinsa.as.ASProcesoInsaInterface;
import mx.ine.procprimerinsa.bsd.BSDProcesoInsaInterface;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.dto.db.DTOLlavesProcesos;
import mx.ine.procprimerinsa.dto.db.DTOResultados1aInsa;
import mx.ine.procprimerinsa.form.FormProcesoInsa;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@Component("bsdProcesoInsa")
@Scope("prototype")
public class BSDProcesoInsa implements BSDProcesoInsaInterface {

	private static final long serialVersionUID = -4237575153902316047L;
	private static final Logger logger = Logger.getLogger(BSDProcesoInsa.class);

	@Autowired
	@Qualifier("asProcesoInsa")
	private ASProcesoInsaInterface asProcesoInsa;

	@Autowired
	@Qualifier("asGenerarLlaves")
	private ASGenerarLlavesInterface asGenerarLlaves;

	@Autowired
	@Qualifier("asParametros")
	private ASParametrosInterface asParametros;

	@Autowired
	@Qualifier("asArchivos")
	private ASArchivosInterface asArchivos;

	@Autowired
	@Qualifier("asDatosInsaculados")
	private ASDatosInsaculadosInterface asDatosInsaculados;
	
	@Autowired
	@Qualifier("asCTipoVoto")
	private ASCTipoVotoInterface asCTipoVoto;

	@Override
	public Integer obtenerIdEstatusActual(Integer idDetalle, Integer idParticipacion) throws Exception {
		return asProcesoInsa.obtenerIdEstatusActual(idDetalle, idParticipacion);
	}
	
	@Override
	public boolean validarLlaves(FormProcesoInsa formProceso) {
		
		try {
			
			if (!formProceso.getValidarLlaves().equals(0)) {

				// Se consultan las llaves del proceso
				List<DTOLlavesProcesos> llaves = asProcesoInsa.obtenerLlaves(formProceso);
	
				// Si las llaves no se encuentran, se notifica al usuario
				if(llaves.isEmpty()) {
					formProceso.setMensaje("No se encuentran las llaves correspondientes a este proceso");
					formProceso.setFallaLlaves(true);
					return false;
				}
	
				// Se obtiene el hash de cada llave
				String llaveVE = asGenerarLlaves.generaLlave("SHA-1", llaves.get(0));
				String llaveVCEyEC = asGenerarLlaves.generaLlave("SHA-1", llaves.get(1));
	
				// Si las llaves son inválidas
				if (formProceso.getLlaveVE() == null
					|| formProceso.getLlaveVCEyEC() == null
					|| !formProceso.getLlaveVE().trim().equals(llaveVE.substring(0, 10))
					|| !formProceso.getLlaveVCEyEC().trim().equals(llaveVCEyEC.substring(0, 10))) {
					formProceso.setMensaje("Llaves de acceso inválidas, intente nuevamente");
					formProceso.setFallaLlaves(true);
					return false;
				}
			}
			
			formProceso.setMensaje("Llaves de acceso válidas");

			// Si el estatus del proceso es 5, se actualiza el status a 6 - VALIDA LLAVES
			if (formProceso.getStatusActual().equals(Constantes.ESTATUS_PREPARANDO_DG_F))
				formProceso.setStatusActual(actualizaEstatus(formProceso.getIdDetalle(), 
															formProceso.getIdParticipacion(),
															formProceso.getStatusActual()));

			// Se habilita la navegación y se eliminan mensajes de error
			formProceso.setFallaLlaves(false);
			formProceso.setLlavesValidas(true);
			formProceso.setNavegacionPermitida(true);
			formProceso.setSaltoActivo(true);
			
			return true;

		} catch (Exception e) {
			logger.error("ERROR BSDProcesoInsa -validarLlaves: ", e);
			formProceso.setMensaje("Ocurrió un error al intentar validar las llaves");
			formProceso.setFallaLlaves(true);
			return false;
		}
		
	}

	@Override
	public Integer actualizaEstatus(Integer idDetalle, Integer idParticipacion, Integer estatus)
			throws Exception {
		
		if (!estatus.equals(asProcesoInsa.obtenerIdEstatusActual(idDetalle, 
																idParticipacion))) {
			throw new Exception("El estatus actual del proceso no corresponde con el estatus almacenado en base de datos");
		} else {
			return asProcesoInsa.actualizaEstatus(idDetalle,
												idParticipacion, 
												estatus, 
												Constantes.DEFAULT_JOB_EXECUTION_ID);
		}
	}

	@Override
	public boolean obtenerParametrosInsaculacion(FormProcesoInsa form) throws Exception {
		
		String parametro = asParametros.obtenerParametro(form.getIdProceso(), 
														form.getIdDetalle(), 
														form.getIdEstado(), 
														form.getIdDistrito(),
														Constantes.PARAMETRO_FECHA_EJECUCION_PROCESO);
		
		form.setMostrarBotonInsaculacion(true);

		if (parametro == null) {
			form.setMensaje("La fecha para la ejecución del Proceso de Primera Insaculación no se encuentra asignada");
			logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: No se encontró el parámetro de fecha de ejecución");
			return false;
		}

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = format.parse(parametro);
		Date fechaActual = new Date();

		if (DateUtils.isSameDay(fecha, fechaActual)) {
			asParametros.actualizarParametro(form.getIdProceso(), 
											form.getIdDetalle(), 
											form.getIdEstado(), 
											form.getIdDistrito(), 
											Constantes.PARAMETRO_TIPO_DE_EJECUCION, 
											"J");
		} else {
			String parametroInicio = asParametros.obtenerParametro(form.getIdProceso(), 
																form.getIdDetalle(), 
																form.getIdEstado(), 
																form.getIdDistrito(),
																Constantes.PARAMETRO_INICIO_PERIODO_SIMULACRO);
			
			String parametroFin = asParametros.obtenerParametro(form.getIdProceso(), 
																form.getIdDetalle(), 
																form.getIdEstado(), 
																form.getIdDistrito(),
																Constantes.PARAMETRO_FIN_PERIODO_SIMULACRO);

			if (parametroInicio == null || parametroFin == null) {
				form.setMensaje("Las fechas de inicio y fin para la ejecución de los simulacros del Proceso de Primera Insaculación no se encuentran asignadas");
				logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: No se encontraron las fechas del simulacro");
				return false;
			}

			Date fechaInicio = format.parse(parametroInicio);
			Date fechaFin = format.parse(parametroFin);
			Calendar c = Calendar.getInstance();
			c.setTime(fechaFin);
			c.add(Calendar.DATE, 1);
			fechaFin = c.getTime();
			if (fechaActual.before(fechaInicio) || fechaActual.after(fechaFin)) {
				form.setMensaje("La fecha actual no se encuentra dentro del periodo válido para simulacros o ejecución del Proceso de Primera Insaculación.");
				logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: La fecha actual no se encuentra dentro del rango del periodo de simulacro");
				return false;
			}

			asParametros.actualizarParametro(form.getIdProceso(), 
											form.getIdDetalle(), 
											form.getIdEstado(), 
											form.getIdDistrito(),
											Constantes.PARAMETRO_TIPO_DE_EJECUCION,
											"S");

			form.setSimulacro(true);
		}
		
		parametro = asParametros.obtenerParametro(form.getIdProceso(), 
												form.getIdDetalle(), 
												form.getIdEstado(), 
												form.getIdDistrito(), 
												Constantes.PARAMETRO_TIPO_DE_EJECUCION);

		if (parametro == null) {
			form.setMensaje("No se encuentra el parámetro de tipo de ejecución del proceso");
			logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: No se encontró el parámetro de tipo de ejecución");
			return false;
		} 
		
		form.setTipoEjecucion(parametro);

		if (parametro.equals("S")) {

			parametro = asParametros.obtenerParametro(form.getIdProceso(), 
													form.getIdDetalle(), 
													form.getIdEstado(), 
													form.getIdDistrito(), 
													Constantes.PARAMETRO_LETRA_A_INSACULAR_SIMULACRO);

			if (parametro == null) {
				form.setMensaje("El parámetro de la letra simulacro no se encuentra asignado");
				logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: No se encontró el parámetro de letra simulacro");
				return false;
			} 
			
			form.setLetraSorteada(parametro);

			parametro = asParametros.obtenerParametro(form.getIdProceso(), 
													form.getIdDetalle(), 
													form.getIdEstado(), 
													form.getIdDistrito(), 
													Constantes.PARAMETRO_MES_A_INSACULAR_SIMULACRO);

			if (parametro == null) {
				form.setMensaje("El parámetro del mes simulacro no se encuentra asignado");
				logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: No se encontró el parámetro de mes simulacro");
				return false;
			}
			
			form.setMesSorteado(Integer.valueOf(parametro));
			form.setMesNombre(Utilidades.obtenerMes(parametro));
			
			parametro = asParametros.obtenerParametro(form.getIdProceso(), 
													form.getIdDetalle(), 
													form.getIdEstado(), 
													form.getIdDistrito(), 
													Constantes.PARAMETRO_BOTON_EJECUCION_PROCESO);
			
			form.setMostrarBotonInsaculacion(parametro != null && parametro.equals("1"));	
			
		} else {

			parametro = asParametros.obtenerParametro(form.getIdProceso(), 
													form.getIdDetalle(), 
													form.getIdEstado(), 
													form.getIdDistrito(), 
													Constantes.PARAMETRO_LETRA_A_INSACULAR);

			if (parametro == null) {
				form.setMensaje("El parámetro de la letra sorteada no se encuentra asignado");
				logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: No se encontró el parámetro de letra sorteada");
				return false;
			} 
			
			form.setLetraSorteada(parametro);

			parametro = asParametros.obtenerParametro(form.getIdProceso(), 
													form.getIdDetalle(), 
													form.getIdEstado(), 
													form.getIdDistrito(), 
													Constantes.PARAMETRO_MES_A_INSACULAR);

			if (parametro == null) {
				form.setMensaje("El parámetro del mes sorteado no se encuentra asignado");
				logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: No se encontró el parámetro de mes sorteado");
				return false;
			} 
			
			form.setMesSorteado(Integer.valueOf(parametro));
			form.setMesNombre(Utilidades.obtenerMes(parametro));
			
		}

		parametro = asParametros.obtenerParametro(form.getIdProceso(), 
												form.getIdDetalle(), 
												form.getIdEstado(), 
												form.getIdDistrito(), 
												Constantes.PARAMETRO_POLL_DESPLIEGUE_PROCESO);

		if (parametro == null) {
			form.setMensaje("El parámetro del tiempo de despliegue de resultados no se encuentra asignado");
			logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: No se encontró el parámetro del tiempo de despliegue de resultados");
			return false;
		} 
		
		form.setTiempoPoll(Integer.valueOf(parametro));

		parametro = asParametros.obtenerParametro(form.getIdProceso(), 
												form.getIdDetalle(), 
												form.getIdEstado(), 
												form.getIdDistrito(), 
												Constantes.PARAMETRO_POLL_EJECUCION_PROCESO);

		if (parametro == null) {
			form.setMensaje("El parámetro del tiempo de ejecución no se encuentra asignado");
			logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: No se encontró el parámetro de tiempo de recarga de poll para el proceso");
			return false;
		} 
		
		form.setTiempoPollEjecucion(Integer.valueOf(parametro));
		
		parametro = asParametros.obtenerParametro(form.getIdProceso(), 
												form.getIdDetalle(), 
												form.getIdEstado(), 
												form.getIdDistrito(), 
												Constantes.PARAMETRO_CORTE_LN_A_ACTUALIZAR);
		
		if (parametro == null) {
			form.setMensaje("El parámetro para especificar si se actualiza un corte de LN no se encuentra asignado");
			logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: No se encontró el parámetro de corte de LN a actualizar");
			return false;
		}
		
		form.setIdCorteLNAActualizar(Integer.valueOf(parametro));
		
		if(!form.getIdCorteLNAActualizar().equals(0) 
			&& form.getIdCorteLN().equals(form.getIdCorteLNAActualizar())) {
			form.setMensaje("El corte de LN a actualizar no puede ser el mismo corte de LN del proceso");
			logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: El corte de LN a actualizar no puede ser el mismo corte de LN del proceso");
			return false;
		}
		
		parametro = asParametros.obtenerParametro(form.getIdProceso(), 
												form.getIdDetalle(), 
												form.getIdEstado(), 
												form.getIdDistrito(), 
												Constantes.PARAMETRO_VALIDAR_YA_ES_INSACULADO);
		
		if (parametro == null) {
			form.setMensaje("El parámetro para determinar si se valida si el ciudadano ya fue previamente insaculado no se encuentra asignado");
			logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: No se encontró el parámetro de validación de ciudadano ya insaculado");
			return false;
		} 
		
		form.setValidaYaEsInsaculado(Integer.valueOf(parametro));
		
		parametro = asParametros.obtenerParametro(form.getIdProceso(), 
												form.getIdDetalle(), 
												form.getIdEstado(), 
												form.getIdDistrito(), 
												Constantes.PARAMETRO_MESES_YA_SORTEADOS);
		
		form.setMesesYaSorteados(parametro);
		
		parametro = asParametros.obtenerParametro(form.getIdProceso(), 
												form.getIdDetalle(), 
												form.getIdEstado(), 
												form.getIdDistrito(), 
												Constantes.PARAMETRO_CONSIDERA_EXTRAORDINARIAS);
		
		if (parametro == null) {
			form.setMensaje("El parámetro para determinar si se consideran secciones extraordinarias no se encuentra asignado");
			logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: No se encontró el parámetro de consideración de secciones extraordinarias");
			return false;
		} 
		
		form.setConsideraExtraordinarias(Integer.valueOf(parametro));

		parametro = asParametros.obtenerParametro(form.getIdProceso(), 
												form.getIdDetalle(), 
												form.getIdEstado(), 
												form.getIdDistrito(), 
												Constantes.PARAMETRO_VALIDAR_LLAVES);

		if (parametro == null) {
			form.setMensaje("El parámetro para validar las llaves del proceso no se encuentra asignado");
			logger.error("ERROR BSDProcesoInsa -obtenerParametrosInsaculacion: No se encontró el parámetro para validar llaves del proceso");
			return false;
		} 
		
		form.setValidarLlaves(Integer.valueOf(parametro));
		form.setMensaje("Llaves válidas");
		
		return true;
	}
	
	@Override
	public Map<Integer, DTOCTipoVoto> obtieneTiposVotoPorParticipacion(Integer idDetalle, Integer idParticipacion) {
		return asCTipoVoto.obtieneTiposVotoPorParticipacion(idDetalle, idParticipacion);
	}

	@Override
	public boolean ejecutarProceso(FormProcesoInsa form) throws Exception {

		form.setPermitirEjecucion(false);

		if (!form.getStatusActual().equals(Constantes.ESTATUS_PERMISOS_VALIDOS)) {
			return false;
		}
		
		try {
			// Se actualiza el estatus de ejecución a 7 - EJECUTANDO PROCESO (INICIO)
			form.setStatusActual(actualizaEstatus(form.getIdDetalle(), 
												form.getIdParticipacion(),
												form.getStatusActual()));
			
			if (!asProcesoInsa.ejecutaCalculoInsaculados(form.getIdCorteLN(),
													form.getIdProceso(), 
													form.getIdDetalle(), 
													form.getIdEstado(),
													form.getIdParticipacion(), 
													form.getIdDistrito(), 
													form.getMesSorteado(), 
													form.getLetraSorteada(),
													form.getIdCorteLNAActualizar(),
													form.getValidaYaEsInsaculado(),
													form.getMesesYaSorteados(),
													form.getConsideraExtraordinarias(),
													asProcesoInsa.obtenerIdReinicio(form.getIdDetalle(),
																					form.getIdParticipacion()))) {
				return false;
			}

			// Si el proceso de ejecutó correctamente, se actualiza el estatus a 8 - EJECUTANDO PROCESO (FIN)
			form.setStatusActual(actualizaEstatus(form.getIdDetalle(), 
												form.getIdParticipacion(),
												form.getStatusActual()));
			
			// Se obtiene la lista de resultados para mostrar en el despliegue
			obtenerResultadosProceso(form);
			
			// Se activa la navegación
			form.setNavegacionPermitida(true);
			
			return true;
			
		} catch (Exception e) {
			logger.error("ERROR BSDProcesoInsa -ejecutarProceso: ", e);
			return false;
		}
		
	}

	@Override
	public boolean generaListadosBatch(FormProcesoInsa form, String nombreProceso, String nombreDetalle) throws Exception {
		return asArchivos.ejecutaGeneracionArchivos(form.getIdProceso(),
													form.getIdDetalle(),
													form.getIdEstado(), 
													form.getIdDistrito(), 
													form.getIdParticipacion(),
													nombreProceso, 
													nombreDetalle,
													form.getNombreEstado(),
													form.getNombreParticipacion(),
													form.getTipoEjecucion(),
													form.getLetraSorteada());
	}

	@Override
	public void actualizarResultadosInsaculacion(FormProcesoInsa form) {
		int tipoVoto = form.getTipoVotoDespliegue().peek();
		
		if(form.getContadorDespliegue() < form.getResultados().get(tipoVoto).size()) {
			mapeaResultadoDespliegue(form, form.getResultados().get(tipoVoto).get(form.getContadorDespliegue()));
			form.setContadorDespliegue(form.getContadorDespliegue() + 1);
			form.setPorcentajeEjecucion((float) Math.floor((form.getContadorDespliegue() * 100.0)
														/ form.getResultados().get(tipoVoto).size()));
		} else if(form.getContadorDespliegue() == form.getResultados().get(tipoVoto).size()) {
			form.getTipoVotoDespliegue().poll();
			if(!form.getTipoVotoDespliegue().isEmpty()) {
				form.setDescripcionTipoVotoDespliegue(form.getmTipoVoto().get(form.getTipoVotoDespliegue().peek()).getDescripcion());
				form.setLeyendaCasillaDespliegue(form.getmTipoVoto().get(form.getTipoVotoDespliegue().peek()).getLeyendaCasilla());
				form.setContadorDespliegue(0);
				form.inicializaTotales();
				form.setVentanaExito(true);
			} else {
				form.setDetenDespliegue(true);
				form.setMensajeNavegacion("Siguiente");
				form.setNavegacionPermitida(true);
				form.setVentanaExitoFinal(true);
			}
		}
	}
	
	private void mapeaResultadoDespliegue(FormProcesoInsa form, DTOResultados1aInsa resultado) {
		form.getTotales().setSeccion(resultado.getSeccion());
		form.getTotales().setTipoCasilla(resultado.getTipoCasilla());
		form.getTotales().setIdCasilla(resultado.getTipoCasilla().equals("B") ? null : resultado.getIdCasilla());
		form.getTotales().setEnero(resultado.getEnero());
		form.getTotales().setFebrero(resultado.getFebrero());
		form.getTotales().setMarzo(resultado.getMarzo());
		form.getTotales().setAbril(resultado.getAbril());
		form.getTotales().setMayo(resultado.getMayo());
		form.getTotales().setJunio(resultado.getJunio());
		form.getTotales().setJulio(resultado.getJulio());
		form.getTotales().setAgosto(resultado.getAgosto());
		form.getTotales().setSeptiembre(resultado.getSeptiembre());
		form.getTotales().setOctubre(resultado.getOctubre());
		form.getTotales().setNoviembre(resultado.getNoviembre());
		form.getTotales().setDiciembre(resultado.getDiciembre());
		form.getTotales().setListaNominal(resultado.getListaNominal());
		form.getTotales().setHombres(form.getTotales().getHombres() + resultado.getHombres());
		form.getTotales().setMujeres(form.getTotales().getMujeres() + resultado.getMujeres());
		form.getTotales().setNoBinario(form.getTotales().getNoBinario() + resultado.getNoBinario());
		
		form.setInsaculadosSeccion(resultado.getInsaculados());
		form.setInsaculadosTotales(form.getInsaculadosTotales() + resultado.getInsaculados());
		
	}

	@Override
	public boolean finalizaDespliegue(FormProcesoInsa form) throws Exception {
		
		// se actualiza estatus a 11 - TERMINA DESPLIEGUE
		form.setStatusActual(actualizaEstatus(form.getIdDetalle(), 
										form.getIdParticipacion(),
										form.getStatusActual()));
		
		form.inicializaTotales();

		return true;
	}

	@Override
	public void obtenerResultadosProceso(FormProcesoInsa form) throws Exception {
		
		form.setTotalLNDistrito(asProcesoInsa.getTotalListaNominalPorDistrito(form.getIdCorteLN(), 
																			form.getIdEstado(), 
																			form.getIdDistrito()));
		
		form.setResultados(new LinkedHashMap<>());
		for(Integer tipoVoto : form.getmTipoVoto().keySet()) {
			form.getResultados().put(tipoVoto, asDatosInsaculados.consultaResultados(form.getIdDetalle(),
																			form.getIdParticipacion(), 
																			tipoVoto));
		}
		
		form.setTipoVotoDespliegue(new LinkedList<>(form.getmTipoVoto().keySet()));
		form.setDescripcionTipoVotoDespliegue(form.getmTipoVoto().get(form.getTipoVotoDespliegue().peek()).getDescripcion());
		form.setLeyendaCasillaDespliegue(form.getmTipoVoto().get(form.getTipoVotoDespliegue().peek()).getLeyendaCasilla());
		
		form.setContadorDespliegue(0);
	}
	
	@Override
	public Integer getCorteLNActivo(Integer idProceso, Integer idDetalle) {
		return asProcesoInsa.getCorteLNActivo(idProceso, idDetalle);
	}
	
}