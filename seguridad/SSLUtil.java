package mx.ine.procprimerinsa.seguridad;

import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.jboss.logging.Logger;

public class SSLUtil {
	
	private static Logger logger = Logger.getLogger(SSLUtil.class);
	
	public static SSLContext disableCertificateValidation(String codificacion) {
		
		HostnameVerifier hv = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) { return true; }
		};
		
		try {
			SSLContext sc = SSLContext.getInstance(codificacion);
			sc.init(null, null, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			return sc;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return null;
	}
}
