package mx.ine.procprimerinsa.batch.itemsprocessors;

import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.NonNull;

import mx.ine.procprimerinsa.dto.DTODatosPersonales;

public class IPActualizaDatosPersonales implements ItemProcessor<DTODatosPersonales, DTODatosPersonales>, InitializingBean {
	
	private Integer idDetalleProceso;
	private Map<String, String> participacionesMap;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public DTODatosPersonales process(@NonNull DTODatosPersonales item) throws Exception {
		item.setIdDetalle(idDetalleProceso);
		
		String[] participacion = participacionesMap.get(item.getIdEstado() + "-" + item.getIdDistrito()).split("-");
		
		item.setIdParticipacion(Integer.parseInt(participacion[1]));
		
		return item;
	}

	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}
	
	public Map<String, String> getParticipacionesMap() {
		return participacionesMap;
	}

	public void setParticipacionesMap(Map<String, String> participacionesMap) {
		this.participacionesMap = participacionesMap;
	}
	
}
