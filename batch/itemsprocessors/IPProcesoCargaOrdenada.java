package mx.ine.procprimerinsa.batch.itemsprocessors;

import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.NonNull;

import mx.ine.procprimerinsa.dto.db.DTOOrdenadaP;

public class IPProcesoCargaOrdenada implements ItemProcessor<DTOOrdenadaP, DTOOrdenadaP>, InitializingBean {
		
	private Integer idProcesoElectoral;
	private String usuario;
	private Map<String, String> participacionesMap;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public DTOOrdenadaP process(@NonNull DTOOrdenadaP item) throws Exception {	
		item.setIdProcesoElectoral(idProcesoElectoral);
		
		String[] participacion = participacionesMap.get(item.getIdEstado() + "-" + item.getIdDistrito()).split("-");
		
		item.setIdDetalleProceso(Integer.parseInt(participacion[0]));
		item.setIdParticipacion(Integer.parseInt(participacion[1]));
		
		item.setUsuario(usuario);
		return item;
	}

	public Integer getIdProcesoElectoral() {
		return idProcesoElectoral;
	}

	public void setIdProcesoElectoral(Integer idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Map<String, String> getParticipacionesMap() {
		return participacionesMap;
	}

	public void setParticipacionesMap(Map<String, String> participacionesMap) {
		this.participacionesMap = participacionesMap;
	}

}
