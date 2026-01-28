package mx.ine.procprimerinsa.bsd;

import java.io.Serializable;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOGrupoHorarioInsaculacion;

public interface BSDHorariosInsaculacionInterface extends Serializable {
	
	public boolean guardaHorario(Integer idProceso, List<Integer> detalles,
			DTOGrupoHorarioInsaculacion horario, List<String> estados, String username);

}
