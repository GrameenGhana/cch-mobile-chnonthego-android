package org.grameenfoundation.cch.tasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.activity.ReferencesDownloadActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

public class AchievementsTask  extends AsyncTask<String, String, String>{
	private Context ctx;
	private SharedPreferences prefs;
	private String name;
	DbHelper db;
	 private ProgressDialog dialog;
	
	public AchievementsTask(Context ctx){
		this.ctx = ctx;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
	    name=prefs.getString("first_name", "name");
	    db=new DbHelper(ctx);
	    dialog=new ProgressDialog(ctx);
	    dialog.setMax(100);
	    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	   // dialog.setIndeterminate(true);
	   
	}
	protected void onPreExecute() {
		dialog.setMessage("Retrieving user data.....");
		
		 dialog.setCancelable(false);
		   dialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Close", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
			   
		   });
		   dialog.show();
		 //publishProgress("Retrieving user data.....");
		 
	 };
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
                     dialog.setProgress(response.length());
                  //   System.out.println(String.valueOf(response.length()));
                 } 
             } catch (Exception e) {
            	 dialog.setMessage("Oops! Something went wrong, Check your internet connection and try again");
                 e.printStackTrace();
             }
         }
         return response;
     }
	 @Override
     protected void onPostExecute(String result) {
		 dialog.setProgress(100);
		 JSONObject json;
		 JSONArray jsonArrayTargets;
		 JSONArray jsonArrayCourses;
		 String target_status = null;
		 System.out.println(result);
		 try {
			 json=new JSONObject(result);
			 jsonArrayTargets=new JSONArray(json.getString("targets"));
			 jsonArrayCourses=new JSONArray(json.getString("courses"));
			 
			 for (int i=0;i<jsonArrayTargets.length();i++){
				 String target_id=jsonArrayTargets.getJSONObject(i).getString("target_id");
				 String target_type=jsonArrayTargets.getJSONObject(i).getString("target_type");
				 String target_category=jsonArrayTargets.getJSONObject(i).getString("category");
				 String target_number=jsonArrayTargets.getJSONObject(i).getString("target_number");
				 String target_achieved=jsonArrayTargets.getJSONObject(i).getString("achieved");
				 int number_achieved=Integer.valueOf(target_achieved);
				 int target_no=Integer.valueOf(target_number);
         		   //Double percentage= ((double)number_achieved/Integer.valueOf(target_number))*100;	
				   //String percentage_achieved=String.format("%.0f", percentage);
				 if(target_no>number_achieved){
					 target_status="new_record";
				 }else if(target_no>=number_achieved){
					 target_status="updated";
				 }else{
					 target_status="updated";
				 }
				 String last_updated=jsonArrayTargets.getJSONObject(i).getString("last_updated");
				 String target_start_date=jsonArrayTargets.getJSONObject(i).getString("target_start_date");
				 String target_due_date=jsonArrayTargets.getJSONObject(i).getString("due_date");
				 db.insertTargetAchievement(target_id, target_type, target_category, target_number,
						 					target_achieved, target_status, last_updated,
						 					target_start_date, target_due_date);
			 }
			 
			 for(int i=0;i<jsonArrayCourses.length();i++){
				 String course=jsonArrayCourses.getJSONObject(i).getString("course");
				 String record_id=jsonArrayCourses.getJSONObject(i).getString("id");
				 String section=jsonArrayCourses.getJSONObject(i).getString("section_title");
				 String section_value=section.replaceAll("\\u00a0","");
				 String score=jsonArrayCourses.getJSONObject(i).getString("score");
				 String max_score=jsonArrayCourses.getJSONObject(i).getString("max_possible_score");
				 String quiz_type=jsonArrayCourses.getJSONObject(i).getString("quiz_type");
				 String quiz_title=jsonArrayCourses.getJSONObject(i).getString("quiz_title");
				 String date_taken=jsonArrayCourses.getJSONObject(i).getString("datetime");
				 db.insertCourseAchievement(course, record_id, section_value, score, max_score,
						 							quiz_type, quiz_title, date_taken);
			 }
	 dialog.setMessage("Data successfully retrieved!");
	 //dialog.dismiss();
		} catch (NullPointerException e) {
			 dialog.setMessage("Oops! Something went wrong, Check your internet connection and try again");
			e.printStackTrace();
		}catch (Exception e) {
			 dialog.setMessage("Oops! Something went wrong, Check your internet connection and try again");
				e.printStackTrace();
			}
		 
     }
	 
	

}
