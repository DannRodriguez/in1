package mx.ine.procprimerinsa.bo;

public interface BOGenerarLlavesInterface {

	public String generaRuta(Integer idProceso, Integer idDetalle,  String estado, String participacion, 
			Integer id, Integer modo);
	
	public String generaSemilla();
}
