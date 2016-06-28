package com.webProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.soap.Detail;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		resp.setContentType("text/plain");
	    PrintWriter out = resp.getWriter();
	    String err = "Success";
		if(!(req.getParameter("mail").isEmpty()) && !(req.getParameter("name").isEmpty()) && !(req.getParameter("phone").isEmpty()) && !(req.getParameter("qual").isEmpty()) && !(req.getParameter("type").isEmpty()) && !(req.getParameter("month").isEmpty()) && !(req.getParameter("empId").isEmpty()))
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query query = pm.newQuery(Details.class);
			query.setFilter("Mail == mail && EmpId == empId");
			query.declareParameters(Detail.class.getName() +" mail, String empId");
			try
			{
				List<Details> det = (List<Details>) query.execute(req.getParameter("mail"),req.getParameter("empId"));
				if(det.isEmpty())
				{
				    Details d = new Details();
				    d.setMail(req.getParameter("mail"));
				    d.setName(req.getParameter("name"));
				    d.setPhone(req.getParameter("phone"));
				    d.setQualification(req.getParameter("qual"));
				    d.setType(req.getParameter("type"));
				    d.setMonth(req.getParameter("month"));
				    d.setEmpId(req.getParameter("empId"));
				    try
				    {
				    	pm.makePersistent(d);
				    	Cookie cookie2 = new Cookie("mail",req.getParameter("mail"));
					    cookie2.setMaxAge(300);
					    Cookie cookie1 = new Cookie("name",req.getParameter("name"));
					    cookie1.setMaxAge(300);
					    resp.addCookie(cookie1);
					    resp.addCookie(cookie2);
				    }
				    catch(Exception ee)
				    {
				    	ee.printStackTrace();
				    	err = "Error";
				    }
				}
				else
				{
					err = "Exists";
					System.out.println(det.get(0).getName());
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				err = "Error";
			}
		}
		else{
			err = "Error";
		}
	    out.print(err);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException
	{
		doGet(req, resp);
	}
}
