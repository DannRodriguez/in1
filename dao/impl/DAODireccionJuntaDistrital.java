package mx.ine.procprimerinsa.dao.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import mx.ine.procprimerinsa.dao.DAOCEtiquetasInterface;
import mx.ine.procprimerinsa.dao.DAODireccionJuntaDistritalInterface;
import mx.ine.procprimerinsa.dto.DTODireccionJuntaDistrital;
import mx.ine.procprimerinsa.dto.DTODireccionJuntaDistritalResponse;
import mx.ine.procprimerinsa.seguridad.SSLUtil;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@Scope("prototype")
@Repository("daoDireccionJuntaDistrital")
public class DAODireccionJuntaDistrital implements DAODireccionJuntaDistritalInterface {
	private static final Logger logger = Logger.getLogger(DAODireccionJuntaDistrital.class);

	@Autowired
    @Qualifier("daoEtiquetas")
	private DAOCEtiquetasInterface daoEtiquetas;
	
	@Override
	public List<DTODireccionJuntaDistrital> getDireccionJuntasDistrital(String params) {
		
		try {
			Context context = new InitialContext();
			String path= (String) context.lookup(daoEtiquetas.obtenerEtiqueta(0, 0, 0, 0,Constantes.SERVIDOR_WS_SESIONES).getDescripcionEtiqueta())
												+ daoEtiquetas.obtenerEtiqueta(0, 0, 0, 0, Constantes.URL_SERVICIO_DIR_JUNTAS_DISTRITALES).getDescripcionEtiqueta();
			URL url = new URL(path);
			
			SSLContext sc = SSLUtil.disableCertificateValidation("TLSv1.2");
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setSSLSocketFactory(sc.getSocketFactory());
			OutputStream os = conn.getOutputStream();
			os.write(params.getBytes());
			os.flush();

			if (conn.getResponseCode() != 200) {
				
				if (conn.getResponseCode() == 404) {
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getErrorStream()), 
																				StandardCharsets.UTF_8));
					String respuesta = br.readLine();
					if (respuesta != null
						&& respuesta.contains("No se encontraron registros de Direcciones de Juntas")) {
						return Collections.emptyList();
					} else {
						logger.error("ERROR DTODireccionJuntaDistrital -getdireccionJuntasDistritales: " + params + "; response: " + respuesta);
					}
				} else {
					logger.error("ERROR DTODireccionJuntaDistrital -getdireccionJuntasDistritales: " + params + "; response: " + conn.getResponseCode() + conn.getResponseMessage());
				}

			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()), StandardCharsets.UTF_8));
			
			String responseLine = Utilidades.sanitizeSQLInjection(br.readLine());
			conn.disconnect();
			
			Gson gson = new Gson();
			DTODireccionJuntaDistritalResponse direcciones = gson.fromJson(responseLine, DTODireccionJuntaDistritalResponse.class);
			
			return direcciones.getDomicilios();

		} catch (Exception e) {
			logger.error("ERROR DTODireccionJuntaDistrital -getdireccionJuntasDistritales: ", e);
			return Collections.emptyList();
		}

	}

}
