package mx.ine.procprimerinsa.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import mx.ine.procprimerinsa.query.QRYContainer;

public interface DAOGenericInterface<T extends Serializable, ID extends Serializable>{
	
	public QRYContainer getContainer();
	
	public Class<T> getPersistentClass();
	
	public Session getSession();
	
	public SessionFactory getSessionFactory();
	
	public Session openSession();
	
	public void flush();

}
