package com.webProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class LoadSampleDetails extends HttpServlet {
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Knowledge.class);
		query.setRange(0,7);
		List<Knowledge> sample = (List<Knowledge>) query.execute();
		JSONArray jarr = new JSONArray();
		for(Knowledge kn : sample)
		{
			JSONObject json = new JSONObject();
			try {
				json.put("name", kn.getName());
				json.put("mail", kn.getMail());
				json.put("topic", kn.getTopic());
				json.put("subTopic", kn.getSubTopic());
				json.put("level", kn.getLevel());
				json.put("description", kn.getDescription());
				jarr.put(json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		out.print(jarr);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException
	{
		doGet(req, resp);
	}
}
