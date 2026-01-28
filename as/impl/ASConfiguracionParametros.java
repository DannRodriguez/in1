package mx.ine.procprimerinsa.as.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASConfiguracionParametrosInterface;
import mx.ine.procprimerinsa.dao.DAOConfiguracionParametrosInterface;
import mx.ine.procprimerinsa.dto.DTOCombo;
import mx.ine.procprimerinsa.dto.DTOConfiguracionParametros;

@Scope("prototype")
@Service("asConfiguracionParametros")
public class ASConfiguracionParametros implements ASConfiguracionParametrosInterface {

	private static final long serialVersionUID = -6866945366429529697L;
	
	@Autowired
	@Qualifier("daoConfiguracionParametros")
	private transient DAOConfiguracionParametrosInterface daoConfiguracionParametros;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public List<DTOConfiguracionParametros> obtenerLista(Integer idCorte){
		return daoConfiguracionParametros.obtenerLista(idCorte);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
	public Map<String, DTOCombo> obtenerDescripcionParametros(){
		return daoConfiguracionParametros.obtenerDescripcionParametros();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public boolean actualizaParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito, String tipoJunta, Integer idParametro, String valorParametro) {
		return daoConfiguracionParametros.actualizaParametro(idProceso, idDetalle, idEstado, idDistrito, tipoJunta, idParametro, valorParametro);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public boolean eliminaParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito, String tipoJunta, Integer idParametro) {
		return daoConfiguracionParametros.eliminaParametro(idProceso, idDetalle, idEstado, idDistrito, tipoJunta, idParametro);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public boolean agregaParametro(DTOConfiguracionParametros parametro, boolean agregaDescripcion) {
		
		Integer idParam;
		
		if(agregaDescripcion) {
			Long idParametro = daoConfiguracionParametros.obtenerIdParametro();
			
			if(idParametro == null) return false;
			
			if(!daoConfiguracionParametros.agregaDescripcion(idParametro.intValue(), 
														parametro.getDescripcionParametro(), 
														parametro.getDescripcionValores())) {
				return false;
			}
			idParam = idParametro.intValue();
			
		} else {
			idParam = parametro.getIdParametro();
		}
		
		return daoConfiguracionParametros.agregaParametro(parametro.getIdProceso(), 
											parametro.getIdDetalle(), 
											parametro.getIdEstado(), 
											parametro.getIdDistrito(), 
											parametro.getTipoJunta(),
											idParam, 
											parametro.getValorParametro());
		
	}

}
