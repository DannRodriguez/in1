package mx.ine.procprimerinsa.bsd.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.procprimerinsa.as.ASHorariosInsaculacionInterface;
import mx.ine.procprimerinsa.bsd.BSDHorariosInsaculacionInterface;
import mx.ine.procprimerinsa.dto.DTOGrupoHorarioInsaculacion;

@Scope("prototype")
@Service("bsdHorariosInsaculacion")
public class BSDHorariosInsaculacion implements BSDHorariosInsaculacionInterface {

	private static final long serialVersionUID = -2178168421014843800L;
	
	@Autowired
	@Qualifier("asHorariosInsaculacion")
	private ASHorariosInsaculacionInterface asHorariosInsaculacion;

	@Override
	public boolean guardaHorario(Integer idProceso, List<Integer> detalles, 
			DTOGrupoHorarioInsaculacion horario, List<String> estados, String username) {
		return asHorariosInsaculacion.guardaHorario(idProceso, detalles, horario, estados, username);
	}

}
