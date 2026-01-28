package mx.ine.procprimerinsa.bsd;

import java.io.Serializable;
import java.util.Map;

import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.form.FormProcesoInsa;

public interface BSDProcesoInsaInterface extends Serializable{
	
	//Método para obtener el status actual del sistema
	public Integer obtenerIdEstatusActual(Integer idDetalle, Integer idParticipacion) throws Exception;
	
	//Método para la validación de llaves de acceso
	public boolean validarLlaves(FormProcesoInsa formProceso);
	
	//Método para actualizar el status actual
	public Integer actualizaEstatus(Integer idDetalle, Integer idParticipacion, Integer estatus) throws Exception;
	
	//Método para obtener la lista de parametros del proceso de insaculacion
	public boolean obtenerParametrosInsaculacion(FormProcesoInsa form) throws Exception;
	
	//Método para obtener los tipos de voto que se insacularán para la participación
	public Map<Integer, DTOCTipoVoto> obtieneTiposVotoPorParticipacion(Integer idDetalle, Integer idParticipacion);
	
	//Método para la ejecución del proceso de insaculación
	public boolean ejecutarProceso(FormProcesoInsa form) throws Exception;
	
	//Método para generar los listados del proceso
	public boolean generaListadosBatch(FormProcesoInsa form, String nombreProceso, String nombreDetalle) throws Exception;
		
	//Método para actualizar los resultados a mostrar en el proceso de insaculación
	public void actualizarResultadosInsaculacion(FormProcesoInsa form);
	
	//Método para la descarga de datos del DG a la BD
	public boolean finalizaDespliegue(FormProcesoInsa form) throws Exception;

	//Método para obtener los resultados del proceso
	public void obtenerResultadosProceso(FormProcesoInsa form) throws Exception;
	
	//Método para obtener el corte de lista nominal asociado con el proceso
	public Integer getCorteLNActivo(Integer idProceso, Integer idDetalle);

}
