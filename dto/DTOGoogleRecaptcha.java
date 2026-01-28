package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

public class DTOGoogleRecaptcha implements Serializable {

	private static final long serialVersionUID = 9060287110411415463L;
	
	private String url;
	private String request;
	
	public DTOGoogleRecaptcha(String url, String keysecret) {
		super();
		this.url = url;
		this.request = "secret=" + keysecret + "&response=";
	}
	
	public String getUrl() {
		return url;
	}

	public String getRequest() {
		return request;
	}
	
}
