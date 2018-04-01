package pl.tomasino.jeeapp.service;

import java.util.List;

import javax.enterprise.context.Dependent;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import pl.tomasino.jeeapp.model.Company;

@Dependent
public class DBManager {

	final static Logger logger = Logger.getLogger(DBManager.class);
	private SessionFactory sessionFactory;

	public DBManager() {
		try {
			sessionFactory = new Configuration().configure().addAnnotatedClass(Company.class).buildSessionFactory();
		} catch (Throwable ex) {
			logger.error("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public List<Company> getAllFromDB() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<Company> companies = session.createQuery("from Company").list();

		session.close();
		sessionFactory.close();

		return companies;
	}

}
