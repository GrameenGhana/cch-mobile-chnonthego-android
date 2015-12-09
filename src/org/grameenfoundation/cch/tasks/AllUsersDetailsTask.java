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
import android.preference.PreferenceManager;

public class AllUsersDetailsTask  extends AsyncTask<String, String, String>{
	private Context ctx;
	private SharedPreferences prefs;
	private String name;
	DbHelper db;

	
	public AllUsersDetailsTask(Context ctx){
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
		 db.alterUserTableForZone();
		 db.alterUserTableForSubdistrict();
		 db.deleteGroupsTable();
		 System.out.println(result);
		 JSONObject jsonfromresult2;
		 JSONArray jsonGroups;
		 String user_role = null;	
		 String district;
		 String subdistrict = null;
		 String zone = null;
		 String facility;
		 try {
			 //Setting user details
			 jsonfromresult2=new JSONObject(result);
			 if(jsonfromresult2.getString("ischn").equals("1")||jsonfromresult2.getString("role").contains("Nurse")){
				 user_role="chn";
				 district=jsonfromresult2.getString("district_name");
				 subdistrict=jsonfromresult2.getString("subdistrict_name");
				 zone=jsonfromresult2.getString("zone_name");
				 jsonGroups=new JSONArray(jsonfromresult2.getString("groups"));
				 facility=jsonfromresult2.getString("primary_facility");
				 for (int j=0;j<jsonGroups.length();j++){
				db.insertUserGroupMembers(jsonGroups.getJSONObject(j).getString("username"),
												jsonGroups.getJSONObject(j).getString("first_name"), 
												jsonGroups.getJSONObject(j).getString("last_name"),
												jsonGroups.getJSONObject(j).getString("district_name"),
												jsonGroups.getJSONObject(j).getString("subdistrict_name"),
												jsonGroups.getJSONObject(j).getString("facility_name"),
												jsonGroups.getJSONObject(j).getString("zone_name"));
				 }
				 db.updateUserData(user_role,district,facility,subdistrict,zone);
			 }else if(jsonfromresult2.getString("role").equalsIgnoreCase("District Supervisor")||jsonfromresult2.getString("role").equalsIgnoreCase("Sub-District Supervisor")){
				 user_role=jsonfromresult2.getString("role");
				 district=jsonfromresult2.getString("district");
				 facility=jsonfromresult2.getString("primary_facility");
				 db.updateUserData(user_role,district,facility,subdistrict,zone);
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
