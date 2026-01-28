package mx.ine.procprimerinsa.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.ine.procprimerinsa.dto.db.DTOHorariosInsaculacion;

public interface DAOHorariosInsaculacionInterface extends DAOGenericInterface<DTOHorariosInsaculacion, Serializable>{

	public List<DTOHorariosInsaculacion> obtenerHorarios(List<Integer> detalles);
		
	public void eliminaHorarios(List<Integer> detalles, Integer idHorario);
	
	public Long obtieneIdHorario(List<Integer> detalles);
	
	public void insertaHorario(Integer idProceso, List<Integer> detalles, Integer idEstado, 
			Integer idHorario, Date horaInicio,  Date horaFinal, String username);
	
}
