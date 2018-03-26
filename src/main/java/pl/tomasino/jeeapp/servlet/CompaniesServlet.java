package pl.tomasino.jeeapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import pl.tomasino.jeeapp.model.Company;
import pl.tomasino.jeeapp.service.CDIService;

public class CompaniesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	CDIService service;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		out.println(service.getMessage());
		getFromDB(out);

	}

	private void getFromDB(PrintWriter out) {
		SessionFactory sessionFactory;

		try {
			sessionFactory = new Configuration().configure().addAnnotatedClass(Company.class).buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<Company> companies = session.createQuery("from Company").list();
		for (Company c : companies) {
			out.print(c.getId());
			out.print(";");
			out.print(c.getName());
			out.print(";");
			out.print(c.getAge());
			out.print(";");
			out.print(c.getAddress());
			out.print(";");
		}

		session.close();
		sessionFactory.close();
	}

}
