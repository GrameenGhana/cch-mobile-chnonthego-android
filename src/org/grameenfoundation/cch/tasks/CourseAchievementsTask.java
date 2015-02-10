package org.grameenfoundation.cch.tasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.grameenfoundation.cch.model.CourseAchievments;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.AsyncTask;

public class CourseAchievementsTask extends AsyncTask<String, Void, String> {
	
	 protected String doInBackground(String ... urls) {
         String response = "";
         for (String url : urls) {
             DefaultHttpClient client = new DefaultHttpClient();
             HttpGet httpGet = new HttpGet(url);
             try {
                 HttpResponse execute = client.execute(httpGet);
                 InputStream content = execute.getEntity().getContent();

                 BufferedReader buffer = new BufferedReader(
                         new InputStreamReader(content));
                 String s = "";
                 while ((s = buffer.readLine()) != null) {
                     response += s;
                 }

             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
         return response;
     }

     @Override
     protected void onPostExecute(String result) {
          try {
			JSONObject jObj = new JSONObject(result);
			JSONArray date = jObj.getJSONArray("quizzes");
			CourseAchievments courses= new CourseAchievments();
			for (int i=0;i<date.length();i++){
				String myresult=date.getString(i);
				JSONObject newobj=new JSONObject(myresult);
				//System.out.println(newobj.toString());
				courses.setCourseName(newobj.getString("course"));
				courses.setCourseSection(newobj.getString("section_title"));
				courses.setType(newobj.getString("quiz_type"));
				courses.setScore(newobj.getString("score"));
				
				//System.out.println(courses.getCourseName());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }

}
