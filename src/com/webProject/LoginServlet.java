package com.webProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.soap.Detail;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		resp.setContentType("text/plain");
	    PrintWriter out = resp.getWriter();
	    String err = "Success";
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    Query query = pm.newQuery(Details.class);
		query.setFilter("Mail == mail && EmpId == empId");
		query.declareParameters(Detail.class.getName() +" mail, String empId");
		List<Details> det = new ArrayList<Details>();
		try
		{
			det = (List<Details>) query.execute(req.getParameter("mail"),req.getParameter("empId"));
			if(!det.isEmpty())
			{
				Cookie cookie1 = new Cookie("name",det.get(0).getName());
				cookie1.setMaxAge(300);
				Cookie cookie2 = new Cookie("mail",det.get(0).getMail());
				cookie2.setMaxAge(300);
				resp.addCookie(cookie1);
				resp.addCookie(cookie2);
			}
			else
			{
				err = "Error";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			err = "Error";
		}
	    out.print(err);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException
	{
		doGet(req, resp);
	}
}
