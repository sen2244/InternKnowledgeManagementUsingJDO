package com.webProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.Query;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class FetchServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Knowledge> knowledge = null;
		Query q = pm.newQuery(Details.class);
		q.setFilter("Mail == nameParameter");
		q.declareParameters("String nameParameter");
		String topic = req.getParameter("topic");
		String subTopic = req.getParameter("subTopic");
		String level = req.getParameter("level");
		String mail = req.getParameter("mail");
		String para = "String mail";
		String query = "Mail == mail";
		String args = new String();
		if(!topic.isEmpty())
		{
			query += " && Topic == topic";
			para += " ,String topic";
			args += topic;
		}
		if(!subTopic.isEmpty())
		{
			query += " && SubTopic == subTopic";
			para += " ,String subTopic";
			if(args.isEmpty())
			{
				args += subTopic;
			}
			else
			{
				args += ","+subTopic;
			}
		}
		String list[] = args.split(",");
		if(!level.isEmpty() && list.length < 2)
		{
			query += " && Level == level";
			para += " ,String level";
			if(args.isEmpty())
			{
				args += level;
			}
			else
			{
				args += ","+level;
			}
		}
		String lis[] = args.split(",");
		if(args.isEmpty())
		{
			System.out.println("Only mail given! ");
			Query q1 = pm.newQuery(Knowledge.class);
			q1.setFilter(query);
			q1.declareParameters(para);
			try
			{
				knowledge = (List<Knowledge>) q1.execute(mail);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(lis.length == 1)
		{
			System.out.println("2 given!");
			Query q1 = pm.newQuery(Knowledge.class);
			q1.setFilter(query);
			q1.declareParameters(para);
			try
			{
				knowledge = (List<Knowledge>) q1.execute(mail,lis[0]);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(lis.length == 2)
		{
			if(level.isEmpty())
			{
				System.out.println("3 given!");
				Query q1 = pm.newQuery(Knowledge.class);
				q1.setFilter(query);
				q1.declareParameters(para);
				try
				{
					knowledge = (List<Knowledge>) q1.execute(mail,lis[0],lis[1]);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("4 given!");
				Query q1 = pm.newQuery(Knowledge.class);
				q1.setFilter(query);
				q1.declareParameters(para);
				try
				{
					List<Knowledge> know = new ArrayList<Knowledge>();
					knowledge = (List<Knowledge>) q1.execute(mail,lis[0],lis[1]);
					for(Knowledge kn : knowledge)
					{
						if(kn.getLevel().equals(level))
						{
							know.add(kn);
						}
					}
					knowledge = know;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		List<Details> detail = null;
		try{
			detail = (List<Details>) q.execute(mail);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		Details details = null;
		if(!detail.isEmpty())
		{
			details = detail.get(0);
		}
		if(details != null)
		{
			try {
				json.put("name", details.getName());
				json.put("mail", req.getParameter("mail"));
				json.put("phone", details.getPhone());
				json.put("qual", details.getQualification());
				json.put("type", details.getType());
				json.put("month", details.getMonth());
			} catch (JSONException e) { e.printStackTrace();}
		}
	    if(knowledge != null)
	    {
	    	JSONArray jarr = new JSONArray();
	    	jarr.put(json);
	    	for(Knowledge ent: knowledge)
	    	{
	    		try{
	    			JSONObject jsonobj = new JSONObject();
	    			jsonobj.put("topic", ent.getTopic());
	    			jsonobj.put("subtopic", ent.getSubTopic());
	    			jsonobj.put("level", ent.getLevel());
	    			jsonobj.put("descr", ent.getDescription());
	    			jarr.put(jsonobj);
	    		}catch(JSONException e){ 
	    			e.printStackTrace();
	    		}
	    	}
	    	out.print(jarr);
	    }
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException
	{
		doGet(req, resp);
	}
}
