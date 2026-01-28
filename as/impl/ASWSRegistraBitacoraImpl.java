package mx.ine.procprimerinsa.as.impl;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import java.util.ResourceBundle;

import org.jboss.logging.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import mx.ine.procprimerinsa.as.ASWSRegistraBitacoraInterface;
import mx.ine.procprimerinsa.dto.DTOUsuarioLogin;
import mx.ine.procprimerinsa.dto.DTOWsRegistraBitacora;
import mx.ine.procprimerinsa.util.ApplicationContextUtils;

@Scope("prototype")
@Service("wsRegistraBitacora")
public class ASWSRegistraBitacoraImpl implements ASWSRegistraBitacoraInterface {
	
	private static final Logger logger = Logger.getLogger(ASWSRegistraBitacoraImpl.class);
	private static final String URL = (String) ApplicationContextUtils.getApplicationContext().getBean("urlWSBitacora") + "registraAccMod";
	 
	@Override
	public void solicitaRegistro(DTOUsuarioLogin usuario, Integer idProceso, Integer idDetalle, String vista, String modulo) {
		try(Client client = ClientBuilder.newClient()) {
			
			DTOWsRegistraBitacora registro = new DTOWsRegistraBitacora();
			
			WebTarget target = client.target(URL);
			Invocation.Builder solicitud = target.request("application/json");
			
			registro.setIdProcesoElectoral(idProceso);
			registro.setIdDetalleProceso(idDetalle);
			registro.setIdSistema(Integer.valueOf(ResourceBundle.getBundle("ApplicationConfig").getString("application.id")));
			registro.setIdEstado(usuario.getIdEstado());
			registro.setIdDistrito(usuario.getIdDistrito());
			registro.setUsuarioSistema(usuario.getNombreUsuario());
			registro.setRolUsuario(usuario.getRolUsuario());
			registro.setNivelGeografico(usuario.getNivelRol());
			registro.setVista(vista); 
			registro.setModulo(modulo);
			registro.setIpUsuario(usuario.getIpUsuario());
			
			Response post = solicitud.post(Entity.json(new Gson().toJson(registro)));
			post.readEntity(String.class);
		    
		} catch (Exception e) {
			logger.error("ERROR ASWSRegistraBitacoraImpl -solicitaRegistro: ", e);
		}
	}

}
