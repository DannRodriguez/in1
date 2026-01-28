package mx.ine.procprimerinsa.as;

import java.io.Serializable;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOGrupoHorarioInsaculacion;

public interface ASHorariosInsaculacionInterface extends Serializable {
	
	public boolean guardaHorario(Integer idProceso, List<Integer> detalles, 
			DTOGrupoHorarioInsaculacion horario, List<String> estados,  String username);

}
