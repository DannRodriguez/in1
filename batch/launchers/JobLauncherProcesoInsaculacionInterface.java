package mx.ine.procprimerinsa.batch.launchers;

public interface JobLauncherProcesoInsaculacionInterface {
	
	public boolean ejecutaCalculaInsaculados(Integer idCorteLN, Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idEstado, Integer idParticipacion, Integer idGeograficoParticipacion, Integer mesInsacular, 
			String letraInsacular, Integer idCorteLNAActualizar, Integer validaYaEsInsaculado, 
			String mesesYaSorteados, Integer consideraExtraordinarias, Integer idEjecucion) throws Exception;

}
