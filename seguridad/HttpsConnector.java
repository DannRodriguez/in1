package mx.ine.procprimerinsa.seguridad;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.jboss.logging.Logger;

public class HttpsConnector {
	
	private static final Logger logger = Logger.getLogger(HttpsConnector.class);
	
	private HttpsConnector() {
	    throw new IllegalStateException("Utility class");
	}

    public static String getResponse(String url, String request)  {
    	
    	try {
    		
    		URL obj = new URL(url);
    		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(request);  
            wr.flush();
            wr.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
    		return response.toString();
    		
		} catch (Exception e) {
			logger.error("ERROR HttpsConnector -getResponse: ", e); 
			return null;
		}
    	
    }

}
