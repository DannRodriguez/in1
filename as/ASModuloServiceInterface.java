package mx.ine.procprimerinsa.as;

public interface ASModuloServiceInterface {

	public String validaModuloAbierto(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idMunDto,
			Integer idSistema, Integer idModulo, String grupo, String ambitoCaptura);

}
