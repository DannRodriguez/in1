package mx.ine.procprimerinsa.dg.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Queue;

import mx.ine.procprimerinsa.dao.DAOGenericInterface;
import mx.ine.procprimerinsa.dg.dto.DTOListaNominalDG;

public interface DAOListaNominalV7Interface extends DAOGenericInterface<DTOListaNominalDG, Serializable> {
	
	public Integer getCorteLNActivo(Integer idProceso, Integer idDetalle);
	
	public List<Object[]> getSeccionesCasillasAInsacularPorDistrito(Integer idCorteLN, Integer idEstado, 
			Integer idDistrito, Integer consideraExtraordinarias);
	
	public Integer getTotalListaNominalPorDistrito(Integer idCorteLN, Integer idEstado, Integer idDistrito);
	
	public Queue<DTOListaNominalDG> getInsaculadosPorDistritoSeccionCasilla(Integer idCorteLN, Integer idEstado, 
			Integer idDistrito, Integer seccion, String tipoCasilla, Integer idCasilla, Integer mesInsacular, 
			String letraInsacular, Integer validaYaEsInsaculado, List<Integer> mesesYaSorteados, 
			Integer consideraExtraordinarias, Integer offset, Integer limite);
	
	public void generaSpool(String ruta, String[] header, String query) throws Exception;
	
}
