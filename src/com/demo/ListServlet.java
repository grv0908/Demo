package com.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class ListServlet extends HttpServlet{
	
	private PersonDAO personDAO;
	
	@Override
	public void init() throws ServletException {
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx
					.lookup("java:comp/env/jdbc/DefaultDB");
			personDAO = new PersonDAO(ds);
		} catch (SQLException e) {
			throw new ServletException(e);
		} catch (NamingException e) {
			throw new ServletException(e);
		}
	}

	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		List<Person> persons = null;
////		List<Person> persons = new ArrayList<>();
//		Person person = new Person();
//		person.setFirstName("Gaurav");
//		person.setLastName("Rajput");
//		persons.add(person);
		String name = "";
		try {
			persons = personDAO.selectAllPersons();
			name = persons.get(1).getFirstName();
			req.setAttribute("persons", persons);
			req.setAttribute("name", name);
			req.getRequestDispatcher("/WEB-INF/Person.jsp").forward(req, res);
		}
		catch (ServletException s) {
			
		}
		catch (SQLException s) {
			
		}
	}
}
