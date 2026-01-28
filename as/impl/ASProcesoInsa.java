package mx.ine.procprimerinsa.as.impl;

import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASProcesoInsaInterface;
import mx.ine.procprimerinsa.batch.launchers.JobLauncherProcesoInsaculacionInterface;
import mx.ine.procprimerinsa.bo.BOProcesoInsaInterface;
import mx.ine.procprimerinsa.dao.DAOBitacoraProcesosInterface;
import mx.ine.procprimerinsa.dao.DAOCategoriasProcesoInterface;
import mx.ine.procprimerinsa.dao.DAODatosInsaculadosInterface;
import mx.ine.procprimerinsa.dao.DAOEstatusProcesosInterface;
import mx.ine.procprimerinsa.dao.DAOHorariosInsaculacionInterface;
import mx.ine.procprimerinsa.dao.DAOLlavesProcesosInterface;
import mx.ine.procprimerinsa.dg.dao.DAOListaNominalV7Interface;
import mx.ine.procprimerinsa.dto.db.DTOCategoriasProceso;
import mx.ine.procprimerinsa.dto.db.DTOEstatusProcesos;
import mx.ine.procprimerinsa.dto.db.DTOHorariosInsaculacion;
import mx.ine.procprimerinsa.dto.db.DTOLlavesProcesos;
import mx.ine.procprimerinsa.form.FormProcesoInsa;
import mx.ine.procprimerinsa.util.Constantes;

@Service("asProcesoInsa")
@Scope("prototype")
public class ASProcesoInsa implements ASProcesoInsaInterface {
	
	private static final long serialVersionUID = -2400262357690428328L;
	private static final Logger logger = Logger.getLogger(ASProcesoInsa.class);

	@Autowired
	@Qualifier("daoLlavesProcesos")
	private transient DAOLlavesProcesosInterface daoLlavesProcesos;

	@Autowired
	@Qualifier("daoEstatusProceso")
	private transient DAOEstatusProcesosInterface daoEstatusProceso;

	@Autowired
	@Qualifier("daoBitacoraProcesos")
	private transient DAOBitacoraProcesosInterface daoBitacoraProcesos;

	@Autowired
	@Qualifier("boProcesoInsa")
	private transient BOProcesoInsaInterface boProcesoInsa;

	@Autowired
	@Qualifier("jobLauncherProcesoInsaculacion")
	private transient JobLauncherProcesoInsaculacionInterface jobLauncherProcesoInsaculacion;

	@Autowired
	@Qualifier("daoListaNominalV7")
	private transient DAOListaNominalV7Interface daoListaNominalV7;

	@Autowired
	@Qualifier("daoHorariosInsaculacion")
	private transient DAOHorariosInsaculacionInterface daoHorariosInsaculacion;

	@Autowired
	@Qualifier("daoCategoriasProceso")
	private transient DAOCategoriasProcesoInterface daoCategoriasProceso;

	@Autowired
	@Qualifier("daoDatosInsaculados")
	private transient DAODatosInsaculadosInterface daoDatosInsaculados;
	
	@Override
	@Transactional(readOnly = true, rollbackFor = { Exception.class })
	public List<DTOLlavesProcesos> obtenerLlaves(FormProcesoInsa formProceso) {
		return daoLlavesProcesos.obtenerParLlavesProceso(formProceso.getIdDetalle(),
														formProceso.getIdParticipacion(), 
														formProceso.getTipoEjecucion().equals("S") ? 0 : 1);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = { Exception.class })
	public Integer obtenerIdEstatusActual(Integer idDetalleProceso, Integer idParticipacion) throws Exception {
		DTOEstatusProcesos estatus = daoEstatusProceso.consultaEstatusProceso(idDetalleProceso, idParticipacion);

		return (estatus != null && estatus.getIdEstatusProceso() != null) ? 
				estatus.getIdEstatusProceso() : 0;
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = { Exception.class })
	public Integer obtenerIdReinicio(Integer idDetalleProceso, Integer idParticipacion) {
		DTOEstatusProcesos estatus = daoEstatusProceso.consultaEstatusProceso(idDetalleProceso, idParticipacion);
		return (estatus != null && estatus.getIdReinicio() != null) ? 
				estatus.getIdReinicio() : 0;
	}
	
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public Integer actualizaEstatus(Integer idDetalleProceso, Integer idParticipacion, Integer estatus, 
		Integer jobExecutionId) throws Exception {
		// Se obtiene el estatus actual
		DTOEstatusProcesos estatusProceso = daoEstatusProceso.consultaEstatusProceso(idDetalleProceso, idParticipacion);

		// Si el objeto no es nulo, se actualiza el estatus
		if (estatusProceso != null) {
			// Se actualiza el estatus
			estatusProceso.setIdEstatusProceso(++estatus);
			estatusProceso.setJobExecutionId(jobExecutionId);

			switch (estatus) {

				// Si el estatus a almacenar es la validación de llaves, se
				// actualiza la fecha de inicio del proceso
				case Constantes.ESTATUS_PERMISOS_VALIDOS:
					estatusProceso.setFechaHoraInicioProceso(new Date());
					break;
	
				// Se verifica, si el ESTATUS a guardar es 7, se incrementa el id de
				// ejecución y se guarda el tiempo de inicio
				case Constantes.ESTATUS_EJECUTANDO_PROCESO_I:
					estatusProceso.setIdEjecucion(estatusProceso.getIdEjecucion() + 1);
					break;
	
				// Si el estatus a guardar es 11, se aguarda la hora del termino del
				// proceso
				case Constantes.ESTATUS_GUARDANDO_BD_I:
					estatusProceso.setFechaHoraFinProceso(new Date());
					break;
	
				// Se verifica si el ESTATUS a guardar es 14, se incrementa el
				// reinicio y se eliminan fechas del proceso
				case Constantes.ESTATUS_PROCESO_REINICIADO:
					estatusProceso.setIdReinicio(estatusProceso.getIdReinicio() + 1);
					estatusProceso.setFechaHoraInicioProceso(null);
					estatusProceso.setFechaHoraFinProceso(null);
					break;
	
				// Se verificia si el ESTATUS a guardar es 15 (no existe), se guarda
				// el ESTATUS a 5
				case (Constantes.ESTATUS_PROCESO_REINICIADO + 1):
					estatusProceso.setIdEstatusProceso(Constantes.ESTATUS_PREPARANDO_DG_F);
					break;
			}

			try {
				// Se guarda en base de datos
				estatusProceso.setUsuario(SecurityContextHolder.getContext().getAuthentication() == null ? 
											  estatusProceso.getUsuario() 
											: SecurityContextHolder.getContext().getAuthentication().getName());
				estatusProceso.setFechaHora(new Date());
				daoEstatusProceso.actualizaEstatus(estatusProceso);

				// Se guarda el registro del nuevo estatus en la bitacora
				daoBitacoraProcesos.guardarBitacora(boProcesoInsa.generaBitacora(estatusProceso));

			} catch (Exception e) {
				logger.error("ERROR: ASProcesoInsa.actualizaEstatus(): Error al actualizar estatus del proceso");
				throw e;
			}

			return estatusProceso.getIdEstatusProceso();
		} else {
			return estatus;
		}
	}
	
	@Override
	public boolean ejecutaCalculoInsaculados(Integer idCorteLN, Integer idProcesoElectoral, Integer idDetalleProcesoElectoral,
			Integer idEstado, Integer idParticipacion, Integer idGeograficoParticipacion, Integer mesInsacular, 
			String letraInsacular, Integer idCorteLNAActualizar, Integer validaYaEsInsaculado, 
			String mesesYaSorteados, Integer consideraExtraordinarias, Integer idReinicio) throws Exception {
		return jobLauncherProcesoInsaculacion.ejecutaCalculaInsaculados(idCorteLN,
																		idProcesoElectoral, 
																		idDetalleProcesoElectoral, 
																		idEstado,
																		idParticipacion, 
																		idGeograficoParticipacion, 
																		mesInsacular, 
																		letraInsacular,
																		idCorteLNAActualizar,
																		validaYaEsInsaculado,
																		mesesYaSorteados,
																		consideraExtraordinarias,
																		idReinicio);
	}
	
	@Override
	public Integer getTotalListaNominalPorDistrito(Integer idCorteLN, Integer idEstado, Integer idDistrito) {
		return daoListaNominalV7.getTotalListaNominalPorDistrito(idCorteLN, idEstado, idDistrito);
	}
	
	@Override
	public Integer getCorteLNActivo(Integer idProceso, Integer idDetalle) {
		try {
			return daoListaNominalV7.getCorteLNActivo(idProceso, idDetalle);
		} catch (Exception e) {
			logger.error("ERROR ASProcesoInsa -getCorteLNActivo: ", e);
			return null;
		}
		
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = { Exception.class })
	public List<DTOHorariosInsaculacion> obtenerHorarios(List<Integer> detalles) {
		return daoHorariosInsaculacion.obtenerHorarios(detalles);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = { Exception.class })
	public List<DTOCategoriasProceso> obtenerListaCategorias(Integer idDetalleProceso) {
		return daoCategoriasProceso.obtenerCategorias(idDetalleProceso);
	}
	
	@Override
	public boolean truncaParticionDatosInsaculados(Integer idDetalleProcesoElectoral, Integer idParticipacion) {
		try {
			daoDatosInsaculados.truncatePartitionDatosInsaculados(idDetalleProcesoElectoral, idParticipacion);
			return true;
		} catch (Exception e) {
			logger.error("ERROR ASProcesoInsa -truncaParticionDatosInsaculados: ", e);
			return false;
		}
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public boolean eliminaResultadosInsa(Integer idDetalleProceso, Integer idParticipacion) {
		try {
			daoDatosInsaculados.eliminaResultadosInsa(idDetalleProceso, idParticipacion);
			return true;
		} catch (Exception e) {
			logger.error("ERROR ASProcesoInsa -eliminaResultadosInsa: ", e);
		}

		return false;
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public Integer actualizaEstatusEtapasInsaculacion(Integer idDetalleProceso, Integer idParticipacion, 
			Integer estatus, Integer jobExecutionId) throws Exception {
		DTOEstatusProcesos estatusProceso = daoEstatusProceso.consultaEstatusProceso(idDetalleProceso, idParticipacion);

		if (estatusProceso != null) {
			estatusProceso.setIdEstatusProceso(estatus);
			estatusProceso.setJobExecutionId(jobExecutionId);

			try {
				estatusProceso.setUsuario(SecurityContextHolder.getContext().getAuthentication() == null ? 
											estatusProceso.getUsuario() 
										  : SecurityContextHolder.getContext().getAuthentication().getName());
				estatusProceso.setFechaHora(new Date());
				daoEstatusProceso.actualizaEstatus(estatusProceso);
				
				daoBitacoraProcesos.guardarBitacora(boProcesoInsa.generaBitacora(estatusProceso));
			} catch (Exception e) {
				logger.error("ERROR ASProcesoInsa -actualizaEstatus: ", e);
				throw e;
			}

			return estatusProceso.getIdEstatusProceso();
		} else {
			return estatus;
		}
	}
	
}
