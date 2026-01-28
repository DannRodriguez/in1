package mx.ine.procprimerinsa.as.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASMapasInterface;
import mx.ine.procprimerinsa.dao.DAOMapasInterface;
import mx.ine.procprimerinsa.dto.DTOEstado;
import mx.ine.procprimerinsa.dto.DTOEstadoDetalles;
import mx.ine.procprimerinsa.dto.DTOMapaInformacion;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.db.DTOMapas;

@Scope("prototype")
@Service("asMapas")
public class ASMapas implements ASMapasInterface {
	
	private static final long serialVersionUID = 7889648384525510436L;
	
	@Autowired
	@Qualifier("daoMapas")
	private transient DAOMapasInterface daoMapas;
	
	@Override
	@Transactional(value="transactionManagerReportes", readOnly = true)
	public List<DTOMapas> getBaseGrafica(Integer idEstado) {		
		return daoMapas.getBaseGrafica(idEstado);
	}
	
	@Override
	@Transactional(value="transactionManagerReportes", readOnly = true)
	public List<DTOMapaInformacion> obtenerDatosMapasBD(List<Integer> estados, List<Integer> detalles, 
			Integer idCorte) {
		return daoMapas.obtenerDatosMapasBD(estados, detalles, idCorte);
	}
	
	@Override
	@Transactional(value="transactionManagerReportes", readOnly = true)
	public List<Integer> getDetallesProceso(Integer idProceso, String tipoEleccion) {
		return daoMapas.getDetallesProceso(idProceso, tipoEleccion);
	}
	
	@Override
	@Transactional(value="transactionManagerReportes", readOnly = true)
	public List<DTOEstadoDetalles> getDetallesProcesoHistoricos(Integer idEstado, Integer idDistrito) {
		return daoMapas.getDetallesProcesoHistoricos(idEstado, idDistrito);
	}
	
	@Override
	@Transactional(value="transactionManagerReportes", readOnly = true)
	public Map<Integer, DTOEstado> getEstados(List<Integer> detalles, Integer idCorte) {
		return daoMapas.getEstados(detalles, idCorte);
	}
	
	@Override
	@Transactional(value="transactionManagerReportes", readOnly = true)
	public Map<Integer, DTOParticipacionGeneral> getParticipaciones(Integer idDetalle, Integer idCorte) {
		return daoMapas.getParticipaciones(idDetalle, idCorte);
	}
	
}
