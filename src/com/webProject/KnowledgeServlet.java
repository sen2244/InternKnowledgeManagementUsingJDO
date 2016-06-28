package com.webProject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;

@SuppressWarnings("serial")
public class KnowledgeServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		String data = req.getParameter("data");
		String level = null;
		String top = null;
		String subTop = null;
		String des = null;
		String err = "Success";
		String name = null;
		String mail = null;
		try{
			Cookie[] cookie = req.getCookies();
			for(Cookie cook : cookie)
			{
				System.out.println(cook.getName()+" : "+cook.getValue());
				if(cook.getName().equals("mail"))
				{
					mail = cook.getValue();
				}
				if(cook.getName().equals("name"))
				{
					name = cook.getValue();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			err = "Error";
		}
		if(!(data.isEmpty()) && !name.isEmpty() && !mail.isEmpty())
		{
			
			try {
				JSONArray jArr = new JSONArray(data);
				for(int i = 0; i < jArr.length(); i++)
				{
					top = jArr.getJSONObject(i).getString("topic");
					subTop = jArr.getJSONObject(i).getString("subTopic");
					level = jArr.getJSONObject(i).getString("level");
					des = jArr.getJSONObject(i).getString("description");
				    PersistenceManager pm = PMF.get().getPersistenceManager();
				    Knowledge k = new Knowledge();
				    k.setMail(mail);
				    k.setTopic(top);
				    k.setSubTopic(subTop);
				    k.setLevel(level);
				    k.setDescription(des);
				    k.setName(name);
				    try
				    {
				    	pm.makePersistent(k);
				    }
				    catch(Exception ee)
				    {
				    	ee.printStackTrace();
				    	err = "Error";
				    }
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else
		{
			err = "Error";
		}
		resp.setContentType("text/plain");
	    PrintWriter out = resp.getWriter();
	    Cookie cookie2 = new Cookie("mail",req.getParameter("mail"));
	    cookie2.setMaxAge(0);
	    Cookie cookie1 = new Cookie("name",req.getParameter("name"));
	    cookie1.setMaxAge(0);
	    resp.addCookie(cookie1);
	    resp.addCookie(cookie2);
	    out.print(err);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException
	{
		doGet(req, resp);
	}
}
