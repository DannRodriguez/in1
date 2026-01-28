package mx.ine.procprimerinsa.as.impl;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.procprimerinsa.as.ASHorariosInsaculacionInterface;
import mx.ine.procprimerinsa.dao.DAOHorariosInsaculacionInterface;
import mx.ine.procprimerinsa.dto.DTOGrupoHorarioInsaculacion;

@Scope("prototype")
@Service("asHorariosInsaculacion")
public class ASHorariosInsaculacion implements ASHorariosInsaculacionInterface {

	private static final long serialVersionUID = -483621534183822681L;
	private static final Logger logger = Logger.getLogger(ASHorariosInsaculacion.class);
	
	@Autowired
	@Qualifier("daoHorariosInsaculacion")
	private transient DAOHorariosInsaculacionInterface daoHorariosInsaculacion;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public boolean guardaHorario(Integer idProceso, List<Integer> detalles, 
			DTOGrupoHorarioInsaculacion horario, List<String> estados, String username) {
		try {
			if(horario.getIdGrupo() != 0) {
				daoHorariosInsaculacion.eliminaHorarios(detalles, horario.getIdGrupo());
			} else {
				horario.setIdGrupo(daoHorariosInsaculacion.obtieneIdHorario(detalles).intValue());
			}
			
			for(String estado : estados) {
				daoHorariosInsaculacion.insertaHorario(idProceso, 
														detalles,
														Integer.parseInt(estado.split("-")[0]),
														horario.getIdGrupo(), 
														horario.getHoraInicio(), 
														horario.getHoraFinal(),
														username);
			}
			
			return true;
		} catch (Exception e) {
			logger.error("ERROR ASHorariosInsaculacion -guardaHorario: ", e);
			return false;
		}
	}

}
