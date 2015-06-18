package org.grameenfoundation.cch.tasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.digitalcampus.oppia.application.DbHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

public class CourseDetailsTask  extends AsyncTask<String, Void, String>{
	private Context ctx;
	DbHelper db;

	
	public CourseDetailsTask(Context ctx){
		this.ctx = ctx;
	    db=new DbHelper(ctx);
	}

	@Override
	protected String doInBackground(String... urls) {
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
		
		 JSONArray jsonArray;
		 String courseGroup;	
		 String courseTitle;	
		// System.out.println(result);
		 try {
			 jsonArray=new JSONArray(result);
			 for (int i=0;i<jsonArray.length();i++){
				 courseTitle=jsonArray.getJSONObject(i).getString("shortname"); 
				 courseGroup=jsonArray.getJSONObject(i).getString("name"); 
				 db.updateCourseGroup(courseTitle, courseGroup);
			 }
		} catch (JSONException e) {
			e.printStackTrace();
		}
		 
     }
	 
	

}
