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
public class InternKnowledgeManagementUsingJDOServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String topic = req.getParameter("topic");
		String subTopic = req.getParameter("subTopic");
		String level = req.getParameter("level");
		String month = req.getParameter("month");
		JSONArray jarr = new JSONArray();
		if(topic.isEmpty() && subTopic.isEmpty() && level.isEmpty() && !month.isEmpty())
		{
			Query q1 = pm.newQuery(Details.class);
			q1.setFilter("Month == month");
			q1.declareParameters("String month");
			try{
				List<Details> results = (List<Details>) q1.execute(month);
				System.out.println("Month alone given!");
				if(!results.isEmpty())
				{
					for(Details details : results)
					{
						JSONObject json = new JSONObject();
						try {
							json.put("name", details.getName());
							json.put("mail", req.getParameter("mail"));
							json.put("phone", details.getPhone());
							json.put("qual", details.getQualification());
							json.put("type", details.getType());
							json.put("month", details.getMonth());
							jarr.put(json);
						} catch (JSONException e) { e.printStackTrace();}
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			if(month.isEmpty())
			{
				String para = new String();
				String query = new String();
				String args = new String();
				if(!topic.isEmpty())
				{
					query += "Topic == topic";
					para += "String topic";
					args += topic;
				}
				if(!subTopic.isEmpty())
				{
					if(args.isEmpty())
					{
						query += "SubTopic == subTopic";
						para += "String subTopic";
						args += subTopic;
					}
					else
					{
						query += " && SubTopic == subTopic";
						para += " ,String subTopic";
						args += ","+subTopic;
					}
				}
				if(!level.isEmpty())
				{
					if(args.isEmpty())
					{
						query += "Level == level";
						para += "String level";
						args += level;
					}
					else
					{
						query += " && Level == level";
						para += " ,String level";
						args += ","+level;
					}
				}
				Query q2 = pm.newQuery(Knowledge.class);
				q2.setFilter(query);
				q2.declareParameters(para);
				List<Knowledge> knowledge = new ArrayList<Knowledge>();
				String list[] = args.split(",");
				if(!topic.isEmpty() && subTopic.isEmpty() && level.isEmpty())
				{
					knowledge = (List<Knowledge>) q2.execute(list[0]);
					System.out.println("Topic given!");
				}
				else if(!topic.isEmpty() && !subTopic.isEmpty() && level.isEmpty())
				{
					knowledge = (List<Knowledge>) q2.execute(list[0],list[1]);
					System.out.println("Topic and Subtopic given!");
				}
				else if(!topic.isEmpty() && !subTopic.isEmpty() && !level.isEmpty())
				{
					knowledge = (List<Knowledge>) q2.execute(list[0],list[1],list[2]);
					System.out.println("Topic , Subtopic and Level given!");
				}
				if(!knowledge.isEmpty())
				{
					for(Knowledge kn : knowledge)
					{
						try{
			    			JSONObject jsonobj = new JSONObject();
			    			jsonobj.put("topic", kn.getTopic());
			    			jsonobj.put("subtopic", kn.getSubTopic());
			    			jsonobj.put("level", kn.getLevel());
			    			jsonobj.put("descr", kn.getDescription());
			    			jsonobj.put("name", kn.getName());
			    			jsonobj.put("mail", kn.getMail());
			    			jarr.put(jsonobj);
			    		}catch(JSONException e){
			    			e.printStackTrace();
			    		}
					}
				}
			}
			else
			{
				String para = new String();
				String query = new String();
				String args = new String();
				Query q1 = pm.newQuery(Details.class);
				q1.setFilter("Month == month");
				q1.declareParameters("String month");
				List<String> mails = new ArrayList<String>();
				try{
					List<Details> results = (List<Details>) q1.execute(month);
					for(Details det : results)
					{
						mails.add(det.getMail());
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				if(!topic.isEmpty())
				{
					query += "Topic == topic";
					para += "String topic";
					args += topic;
				}
				if(!subTopic.isEmpty())
				{
					if(args.isEmpty())
					{
						query += "SubTopic == subTopic";
						para += "String subTopic";
						args += subTopic;
					}
					else
					{
						query += " && SubTopic == subTopic";
						para += " ,String subTopic";
						args += ","+subTopic;
					}
				}
				if(!level.isEmpty())
				{
					if(args.isEmpty())
					{
						query += "Level == level";
						para += "String level";
						args += level;
					}
					else
					{
						query += " && Level == level";
						para += " ,String level";
						args += ","+level;
					}
				}
				Query q2 = pm.newQuery(Knowledge.class);
				q2.setFilter(query);
				q2.declareParameters(para);
				List<Knowledge> knowledge = new ArrayList<Knowledge>();
				String list[] = args.split(",");
				if(!topic.isEmpty() && subTopic.isEmpty() && level.isEmpty())
				{
					knowledge = (List<Knowledge>) q2.execute(list[0]);
					System.out.println("Topic and Month given!");
				}
				else if(!topic.isEmpty() && !subTopic.isEmpty() && level.isEmpty())
				{
					knowledge = (List<Knowledge>) q2.execute(list[0],list[1]);
					System.out.println("Topic , Subtopic and Month given!");
				}
				else if(!topic.isEmpty() && !subTopic.isEmpty() && !level.isEmpty())
				{
					knowledge = (List<Knowledge>) q2.execute(list[0],list[1],list[2]);
					System.out.println("Topic , Subtopic , Level and Month given!");
				}
				if(!knowledge.isEmpty())
				{
					for(Knowledge kn : knowledge)
					{
						if(mails.contains(kn.getMail()))
						{
							try{
				    			JSONObject jsonobj = new JSONObject();
				    			jsonobj.put("topic", kn.getTopic());
				    			jsonobj.put("subTopic", kn.getSubTopic());
				    			jsonobj.put("level", kn.getLevel());
				    			jsonobj.put("description", kn.getDescription());
				    			jsonobj.put("name", kn.getName());
				    			jsonobj.put("mail", kn.getMail());
				    			jarr.put(jsonobj);
				    		}catch(JSONException e){
				    			e.printStackTrace();
				    		}
						}
					}
				}
			}
		}
	    out.print(jarr);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException
	{
		doGet(req, resp);
	}
}
	
