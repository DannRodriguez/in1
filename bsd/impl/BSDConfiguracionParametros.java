package mx.ine.procprimerinsa.bsd.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASConfiguracionParametrosInterface;
import mx.ine.procprimerinsa.bsd.BSDConfiguracionParametrosInterface;
import mx.ine.procprimerinsa.dto.DTOCombo;
import mx.ine.procprimerinsa.dto.DTOConfiguracionParametros;

@Component("bsdConfiguracionParametros")
@Scope("prototype")
public class BSDConfiguracionParametros implements BSDConfiguracionParametrosInterface {
	
	private static final long serialVersionUID = -8893864437550363280L;
	
	@Autowired
	@Qualifier("asConfiguracionParametros")
	private ASConfiguracionParametrosInterface asConfiguracionParametros;
	
	@Override
	public List<DTOConfiguracionParametros> obtenerLista(Integer idCorte){
		return asConfiguracionParametros.obtenerLista(idCorte);
	}
	
	@Override
	public Map<String, DTOCombo> obtenerDescripcionParametros(){
		return asConfiguracionParametros.obtenerDescripcionParametros();
	}
	
	@Override
	public boolean actualizaParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito, String tipoJunta, Integer idParametro, String valorParametro) {
		return asConfiguracionParametros.actualizaParametro(idProceso, idDetalle, idEstado, idDistrito, tipoJunta, idParametro, valorParametro);
	}
	
	@Override
	public boolean eliminaParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito, String tipoJunta, Integer idParametro) {
		return asConfiguracionParametros.eliminaParametro(idProceso, idDetalle, idEstado, idDistrito, tipoJunta, idParametro);
	}
	
	@Override
	public boolean agregaParametro(DTOConfiguracionParametros parametro, boolean agregaDescripcion) {
		return asConfiguracionParametros.agregaParametro(parametro, agregaDescripcion);
	}
	
}
