package mx.ine.procprimerinsa.batch.listeners;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.logging.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;

import mx.ine.procprimerinsa.dao.DAOResultados1AInsaInterface;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.dto.db.DTOResultados1aInsa;
import mx.ine.procprimerinsa.util.Constantes;

public class SELProcesoInsaculacion implements StepExecutionListener {

	private static final Logger logger = Logger.getLogger(SELProcesoInsaculacion.class);

	@Autowired
	@Qualifier("daoResultadosInsaculacion")
	private DAOResultados1AInsaInterface daoResultadosInsaculacion;

	@Override
	public void beforeStep(@NonNull StepExecution stepExecution) {
		logger.debug("SELProcesoInsaculacion - beforeStep: " + stepExecution);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExitStatus afterStep(@NonNull StepExecution stepExecution) {
		logger.debug("SELProcesoInsaculacion - beforeStep: " + stepExecution);
		ExecutionContext executionContext = stepExecution.getExecutionContext();
		JobParameters jobParameters = stepExecution.getJobParameters();
		
		try {
			
			Map<Integer, DTOCTipoVoto> tiposVoto = ((Map<Integer, DTOCTipoVoto>) executionContext.get("tiposVoto"));
			Map<Integer, ConcurrentHashMap<String, Integer>> conteoSexo = ((Map<Integer, ConcurrentHashMap<String, Integer>>) executionContext.get("conteoSexo"));
			Map<Integer, ConcurrentHashMap<Integer, Integer>> conteoMes = ((Map<Integer, ConcurrentHashMap<Integer, Integer>>) executionContext.get("conteoMes"));
			Map<Integer, AtomicInteger> conteoDobleNacionalidad = (Map<Integer, AtomicInteger>) executionContext.get("conteoDobleNacionalidad");
			Map<Integer, ConcurrentHashMap<String, Integer>> conteoSexoDobleNacionalidad = ((Map<Integer, ConcurrentHashMap<String, Integer>>) executionContext.get("conteoSexoDobleNacionalidad"));
			
			for(Integer idTipoVoto : tiposVoto.keySet()) {
				DTOResultados1aInsa dto = new DTOResultados1aInsa();
				dto.setIdProcesoElectoral(jobParameters.getLong("idProcesoElectoral").intValue());
				dto.setIdDetalleProceso(jobParameters.getLong("idDetalleProceso").intValue());
				dto.setIdParticipacion(jobParameters.getLong("idParticipacion").intValue());
				dto.setSeccion(executionContext.getInt("seccion"));
				dto.setIdCasilla(executionContext.getInt("idCasilla"));
				dto.setTipoCasilla(executionContext.getString("tipoCasilla"));
				dto.setListaNominal(executionContext.getInt("numeroElectores"));
				dto.setHombres(conteoSexo.get(idTipoVoto).get(Constantes.GENERO_HOMBRE));
				dto.setMujeres(conteoSexo.get(idTipoVoto).get(Constantes.GENERO_MUJER));
				dto.setNoBinario(conteoSexo.get(idTipoVoto).get(Constantes.GENERO_NO_BINARIO));
				dto.setDobleNacionalidad(conteoDobleNacionalidad.get(idTipoVoto).get());
				dto.setHombresDobleNacionalidad(conteoSexoDobleNacionalidad.get(idTipoVoto).get(Constantes.GENERO_HOMBRE));
				dto.setMujeresDobleNacionalidad(conteoSexoDobleNacionalidad.get(idTipoVoto).get(Constantes.GENERO_MUJER));
				dto.setNoBinarioDobleNacionalidad(conteoSexoDobleNacionalidad.get(idTipoVoto).get(Constantes.GENERO_NO_BINARIO));
				dto.setEnero(conteoMes.get(idTipoVoto).get(1));
				dto.setFebrero(conteoMes.get(idTipoVoto).get(2));
				dto.setMarzo(conteoMes.get(idTipoVoto).get(3));
				dto.setAbril(conteoMes.get(idTipoVoto).get(4));
				dto.setMayo(conteoMes.get(idTipoVoto).get(5));
				dto.setJunio(conteoMes.get(idTipoVoto).get(6));
				dto.setJulio(conteoMes.get(idTipoVoto).get(7));
				dto.setAgosto(conteoMes.get(idTipoVoto).get(8));
				dto.setSeptiembre(conteoMes.get(idTipoVoto).get(9));
				dto.setOctubre(conteoMes.get(idTipoVoto).get(10));
				dto.setNoviembre(conteoMes.get(idTipoVoto).get(11));
				dto.setDiciembre(conteoMes.get(idTipoVoto).get(12));
				dto.setIdTipoVoto(idTipoVoto);
				if ((dto.getHombres() != null && dto.getHombres() > 0)
						|| (dto.getMujeres() != null && dto.getMujeres() > 0)
						|| (dto.getNoBinario() != null && dto.getNoBinario() > 0)) {
					int total = dto.getHombres() + dto.getMujeres() + dto.getNoBinario();
					dto.setPorcentajeHombres((int) Math.round(dto.getHombres() * 100.0 / total));
					dto.setPorcentajeMujeres((int) Math.round(dto.getMujeres() * 100.0 / total));
					dto.setPorcentajeNoBinarios((int) Math.round(dto.getNoBinario() * 100.0 / total));
					dto.setPorcentajeDobleNacionalidad((int) Math.round(dto.getDobleNacionalidad() * 100.0 / total));
					dto.setPorcentajeHombresDobleNacionalidad((int) Math.round(dto.getHombresDobleNacionalidad() * 100.0 / total));
					dto.setPorcentajeMujeresDobleNacionalidad((int) Math.round(dto.getMujeresDobleNacionalidad() * 100.0 / total));
					dto.setPorcentajeNoBinDobleNacionalidad((int) Math.round(dto.getNoBinarioDobleNacionalidad() * 100.0 / total));
				}
				
				daoResultadosInsaculacion.guardar(dto);
			}
			
		} catch(Exception e ) {
			logger.error("ERROR SELProcesoInsaculacion -afterStep: ", e);
		}
		
		return null;
	}

}
