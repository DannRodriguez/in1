package mx.ine.procprimerinsa.query;

import java.util.Properties;

import org.springframework.stereotype.Component;

@Component("qryContainer")
public class QRYContainer {
	
	private Properties querySource;
	
	public QRYContainer() throws Exception {
		querySource = new Properties();
		querySource.load(QRYContainer.class.getResourceAsStream( "/QuerySource.properties" ));
	}

	public String getQuery(String nombreQuery) {
		return querySource.getProperty(nombreQuery);
	}

}
