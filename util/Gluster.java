package mx.ine.procprimerinsa.util;

import java.io.File;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Gluster {

private static Context  context   = null;

	static	{		
			try {
				context   = new InitialContext ();
			} catch (NamingException e) {
				e.printStackTrace();
			}          			
	}
	
	public static String getGlusterInsa(){
	  String path = "";
	  try {
		  path = (String) context.lookup( "util/glusterSistDECEYEC" );
		  if(path != null && path.trim().length() == 1){
			  path = "";
		  }
	
		  return path != null? path.trim()+File.separator+"Insa1"+File.separator:"";
	  } catch (Exception e) {
		  return path;
	  }
	}
	
	public static String getSimpleGluster(){
		  String path = "";
		  try {
			  path = (String) context.lookup( "util/glusterFS" );
			  if(path != null && path.trim().length() == 1){
				  path = "";
			  }
			
			  return path != null? path.trim():"";
		  } catch (Exception e) {
			  return path;
		  }
	}
}