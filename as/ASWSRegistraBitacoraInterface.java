package mx.ine.procprimerinsa.as;

import mx.ine.procprimerinsa.dto.DTOUsuarioLogin;

public interface ASWSRegistraBitacoraInterface {

	public void solicitaRegistro(DTOUsuarioLogin usuario, Integer idProceso, Integer idDetalle, String vista, String modulo);

}
