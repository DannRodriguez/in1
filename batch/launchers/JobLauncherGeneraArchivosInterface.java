package mx.ine.procprimerinsa.batch.launchers;

public interface JobLauncherGeneraArchivosInterface {

	public boolean ejecutaGeneracionArchivos(Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idEstado, Integer id, Integer idParticipacion, String nombreProceso, String nombreDetalleProceso, 
			String nombreEstado, String nombreParticipacion, String modoEjecucion, String letra) 
			throws Exception;
	
}
