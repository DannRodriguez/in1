package mx.ine.procprimerinsa.as.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASParametrosInterface;
import mx.ine.procprimerinsa.dao.DAOCParametrosInterface;
import mx.ine.procprimerinsa.dto.db.DTOCParametros;

@Service("asParametros")
@Scope("prototype")
public class ASParametros implements ASParametrosInterface{

	private static final long serialVersionUID = 9169756922461987647L;
	
	@Autowired
	@Qualifier("daoParametros")
	private transient DAOCParametrosInterface daoParametros;
	
	@Override
	@Transactional(readOnly = true, rollbackFor = { Exception.class })
	public String obtenerParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer id, Integer idParametro) throws Exception{
		
		//Se busca el parámetro solicitado
		DTOCParametros parametro = daoParametros.obtenerParametro(idProceso, idDetalle, idEstado, id, idParametro);
		
		//Si no se obtuvo el parametro, se busca en los parametros generales del estado
		if(parametro == null)
			parametro = daoParametros.obtenerParametro(idProceso, idDetalle, idEstado, 0, idParametro);
		
		//Si no se obtuvo el parametro, se busca en los parametros generales del detalle
		if(parametro == null)
			parametro = daoParametros.obtenerParametro(idProceso, idDetalle, 0, 0, idParametro);
		
		//Si se obtuvo parametro se retorna el valor, si no, se regresa nulo
		if (parametro != null)
			return parametro.getValorParametro();
		else
			return null;
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public boolean actualizarParametro(Integer idProceso, Integer idDetalle, Integer idEstado, Integer id, Integer idParametro, String valor) throws Exception{
		
		//Se busca el parámetro solicitado
		DTOCParametros parametro = daoParametros.obtenerParametro(idProceso, idDetalle, idEstado, id, idParametro);

		// Si no se obtuvo el parametro, se busca en los parametros generales del estado
		if (parametro == null)
			parametro = daoParametros.obtenerParametro(idProceso, idDetalle, idEstado, 0, idParametro);

		// Si no se obtuvo el parametro, se busca en los parametros generales del detalle
		if (parametro == null)
			parametro = daoParametros.obtenerParametro(idProceso, idDetalle, 0, 0, idParametro);
		
		//Si se encontró el parametro, se prodece a actualizarlo
		if(parametro != null){
			
			parametro.setValorParametro(valor);
			
			//Se actualiza el parametro
			daoParametros.actualizaParametro(parametro);
			
			//Se retorna éxito en el guardado del parametro
			return true;
		}
		
		//Si no se puede actualizar el parametro se retorna false
		return false;
	}

}
