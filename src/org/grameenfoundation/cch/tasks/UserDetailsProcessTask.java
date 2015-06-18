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

public class UserDetailsProcessTask  extends AsyncTask<String, Void, String>{
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
		 String user_role;	
		 String plan = "";
		 long largest = 0;
		 System.out.println(result);
		 try {
			 json=new JSONObject(result);
			 if(json.getString("ischn").equals("1")||json.getString("role").contains("Nurse")){
				 user_role="chn";
				 db.updateUserRole(user_role);
			 }else if(json.getString("ischn").equals("0")&&json.getString("role").contains("Supervisor")){
				 user_role="Supervisor";
				 db.updateUserRole(user_role);
			 }
			 jsonArray=new JSONArray(json.getString("survey_data"));
			//json2=new JSONObject(jsonArray.getJSONObject(0).getString("data"));
			//json3=new JSONObject(jsonArray.getJSONObject(1).getString("data"));
			 //System.out.println(jsonArray.getJSONObject(0).getString("start_time"));	
			 //db.updateSurveyData("Agreed", json2.getString("profile"), json2.getString("responses"), json3.getString("plan"),jsonArray.getJSONObject(0).getString("start_time"));
			 for(int i=0;i<jsonArray.length();i++){
				 if(!jsonArray.getJSONObject(i).getString("data").contains("responses")){
					 planData=new JSONObject(jsonArray.getJSONObject(i).getString("data"));
					 //plan=planData.getString("plan");
					// System.out.println(planData);
					if(!planData.has("values")){
						System.out.println(planData);
						//JSONObject planData2=new JSONObject(planData.getString("values"));
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
			
			 db.updateSurveyData("Agreed", profileData.getString("profile"), profileData.getString("responses"), plan,String.valueOf(largest));
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
     }
	 
	

}
