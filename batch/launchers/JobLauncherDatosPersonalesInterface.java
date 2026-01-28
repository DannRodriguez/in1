package mx.ine.procprimerinsa.batch.launchers;

public interface JobLauncherDatosPersonalesInterface {	
	
	public boolean generarArchivoDatosMinimosPorEstado(Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idEstado, String usuario) throws Exception;
	
	public boolean validaArchivosDatosPersonalesPorEstado(Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idEstado, String rutaArchivo, String usuario, String patternToValidate, String encoding) throws Exception;
	
	public boolean actualizarDatosPersonalesPorParticipacion(Integer idProceso, String tipoEleccion,
			Integer idDetalleProceso, Integer idEstado, String rutaArchivo, String encoding) throws Exception;
}
