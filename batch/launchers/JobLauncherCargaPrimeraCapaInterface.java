package mx.ine.procprimerinsa.batch.launchers;

public interface JobLauncherCargaPrimeraCapaInterface {
	
	public boolean ejecutaCargaServidoresPublicos(Integer idProceso, Integer idDetalle) throws Exception;
	
	public boolean spoolServidoresPublicos(Integer idDetalle, String rutaArchivo) throws Exception;
	
	public boolean spoolAreasSecciones(Integer idProceso, String tipoEleccion, String rutaArchivo) throws Exception;
	
	public boolean spoolSeccionesCompartidas(Integer idProceso, String tipoEleccion, String rutaArchivo) throws Exception;
	
	public boolean ejecutaCargaOrdenada(Integer idProceso, String tipoEleccion, String rutaArchivo, String usuario) 
			throws Exception;
	
	public boolean spoolOrdenada(Integer idProceso, String tipoEleccion, String rutaArchivo) throws Exception;

}
