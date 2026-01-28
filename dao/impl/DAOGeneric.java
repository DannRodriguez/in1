package mx.ine.procprimerinsa.dao.impl;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.procprimerinsa.dao.DAOGenericInterface;
import mx.ine.procprimerinsa.query.QRYContainer;

public class DAOGeneric<T extends Serializable, ID extends Serializable> implements DAOGenericInterface<T, ID> {

	@Autowired
	@Qualifier("qryContainer")
	@SessionScoped
	private QRYContainer qryContainer;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private SessionFactory sessionFactoryLN;

	@Autowired
	private SessionFactory sessionFactoryReportes;
	
	private Class<T> persistentClass;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DAOGeneric() {
		this.persistentClass = ((Class) ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0]);
	}

	@Override
	public QRYContainer getContainer() {
		return qryContainer;
	}
	
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@Override
	public Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		Statistics stats = sessionFactory.getStatistics();
		stats.setStatisticsEnabled(true);
		return session;
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Override
	public Session openSession() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void flush() {
		getSession().flush();
	}

	public SessionFactory getSessionFactoryLN() {
		return sessionFactoryLN;
	}

	public Session getSessionLN() {
		return sessionFactoryLN.getCurrentSession();
	}

	public Session openSessionLN() {
		return sessionFactoryLN.openSession();
	}

	public void closeSessionLN() {
		sessionFactoryLN.close();
	}
	
	public Session getSessionReportes() {
		return sessionFactoryReportes.getCurrentSession();
	}

	public SessionFactory getSessionFactoryReportes() {
		return sessionFactoryReportes;
	}
	
	public Session openSessionReportes() {
		return sessionFactoryReportes.openSession();
	}

	public void closeSessionReportes() {
		sessionFactoryReportes.close();
	}
}
