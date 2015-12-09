package org.grameenfoundation.cch.tasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

@SuppressLint("NewApi")
public class FacilityTargetsSyncTask  extends AsyncTask<String, String, String>{
	private Context ctx;
	private SharedPreferences prefs;
	
	private String name;
	DbHelper db;
	private ArrayList<User> userdetails;
	private JSONObject facilityname;
	

	
	public FacilityTargetsSyncTask(Context ctx){
		this.ctx = ctx;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
	    name=prefs.getString("first_name", "name");
	    db=new DbHelper(ctx);
	   
	}
	protected void onPreExecute() {
		 publishProgress("Retrieving user data.....");
		 
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
                 }

             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
         return response;
     }
	 @Override
     protected void onPostExecute(String result) {
		 JSONObject jsonfromresult2;
		 JSONArray jsonTargets;
		 String facility;
		 JSONObject targets_details;
		 try {
			 //Setting user details
			 System.out.println("Inserting sync....");
			 jsonfromresult2=new JSONObject(result);
			 jsonTargets=jsonfromresult2.getJSONArray("targets");
			 for (int i = 0; i < jsonTargets.length(); i++) {
				 targets_details=jsonTargets.getJSONObject(i);
				 db.insertOrUpdateFacilityTargetUpdate(Integer.parseInt(targets_details.getString("target_id")), 
						 								targets_details.getString("target_type"),
						 								targets_details.getString("target_category"),
						 								targets_details.getString("target_detail"), 
						 								"",
						 								"",
						 								targets_details.getString("target_number"), 
						 								targets_details.getString("achieved_number"), 
						 								"",
						 								"", 
						 								targets_details.getString("last_updated"), 
						 								targets_details.getString("group_members"), 
						 								targets_details.getString("target_month"), 
						 								targets_details.getString("comment"),
						 								targets_details.getString("justification"), 
						 								targets_details.getString("facility"), 
						 								targets_details.getString("zone"));
			}
			 publishProgress("Finished setting user data");
		} catch (NullPointerException e) {
			db.updateSurveyData("", "", "", "","");
			e.printStackTrace();
		}catch (JSONException e) {
			e.printStackTrace();
		}catch (Exception e) {
				e.printStackTrace();
			}
		 
     }
	 
	

}
