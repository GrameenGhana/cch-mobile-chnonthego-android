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

public class UserDetailsProcessTask  extends AsyncTask<String, String, String>{
	private Context ctx;
	private SharedPreferences prefs;
	private String name;
	DbHelper db;

	
	public UserDetailsProcessTask(Context ctx){
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
		 JSONObject json;
		 JSONObject planData = null;
		 JSONObject profileData = null;
		 JSONArray jsonArray;
		 JSONArray surveyResponses;
		 String user_role = null;	
		 String district;
		 String plan = "";
		 JSONObject responses;
		 int id = 0;
		 String data = null;
		 long largest = 0;
		 try {
			 json=new JSONObject(result);
			 if(json.getString("ischn").equals("1")||json.getString("role").contains("Nurse")){
				 user_role="chn";
				 district=json.getString("district");
				 db.updateUserData(user_role,district);
			 }else if(json.getString("role").equalsIgnoreCase("District Supervisor")||json.getString("role").equalsIgnoreCase("Sub-District Supervisor")){
				 user_role=json.getString("role");
				 district=json.getString("district");
				 db.updateUserData(user_role,district);
			 }
			 jsonArray=new JSONArray(json.getString("survey_data"));
			 surveyResponses=new JSONArray(json.getString("survey_popup"));
			 for(int i=0;i<jsonArray.length();i++){
				 if(!jsonArray.getJSONObject(i).getString("data").contains("responses")){
					 planData=new JSONObject(jsonArray.getJSONObject(i).getString("data"));
					if(!planData.has("values")){
						plan=planData.getString("plan");
					 }else{
						 plan=planData.getString("plan"); 
					 }
					 long[] times={Long.valueOf(jsonArray.getJSONObject(i).getString("start_time"))};
					 largest=Long.MIN_VALUE;
					 	if(times.length>1&&times[i]>largest){
					 		largest=times[i];
					 	}else if(times.length<=1){
					 		largest=Long.valueOf(jsonArray.getJSONObject(i).getString("start_time"));
					 	}
				 	}else if(jsonArray.getJSONObject(i).getString("data").contains("responses")){
					 profileData=new JSONObject(jsonArray.getJSONObject(i).getString("data"));
				 }
			 }
			 
			 for(int k=0;k<surveyResponses.length();k++){
				 responses=new JSONObject(surveyResponses.getJSONObject(k).getString("data"));
				if(surveyResponses.length()==1&&!responses.toString().contains("survey_type")){
					 id=1;
					 data=surveyResponses.getJSONObject(0).getString("data").toString();
				 }else if(surveyResponses.length()==2 && !responses.toString().contains("survey_type")){
					 id=2;
					 data=surveyResponses.getJSONObject(1).getString("data").toString();
				 }else if(surveyResponses.length()==3 && !responses.toString().contains("survey_type")){
					 id=3;
					 data=surveyResponses.getJSONObject(2).getString("data").toString();
				 }else if(responses.getString("survey_type").contains("survey1")){
					id=1; 
					 data=surveyResponses.getJSONObject(k).getString("data").toString();
				 }else if(responses.getString("survey_type").contains("survey2")){
					id=2; 
					data=surveyResponses.getJSONObject(k).getString("data").toString();
				}else if(responses.getString("survey_type").contains("survey3")){
					id=3; 
					data=surveyResponses.getJSONObject(k).getString("data");
				}
				 db.updateSurveyData(data, name, user_role, "done", db.getDate(), id);
			 }
			
			 db.updateSurveyData("Agreed", profileData.getString("profile"), profileData.getString("responses"), plan,String.valueOf(largest));
		
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
