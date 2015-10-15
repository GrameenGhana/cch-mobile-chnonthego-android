package org.grameenfoundation.cch.tasks;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

public class FacilityTargetsTask extends AsyncTask<String, String, String> {
	private Context ctx;
	private DbHelper dbh;
	private double each_target;

	public FacilityTargetsTask(Context ctx) {
		this.ctx = ctx;
		this.dbh = new DbHelper(ctx);
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

	protected void onProgressUpdate(String... obj) {}
	
	@Override
    protected void onPostExecute(String p) {
		DateTime dateTime=new DateTime();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
		String start;
		String end;
		JSONArray targets_array;
		JSONArray targets_detail_array;
		JSONObject targets_object=new JSONObject();
		JSONObject targets_details=new JSONObject();
		try {
			targets_array=new JSONArray(p);
			
		
		for(int i=0;i<12;i++){
			System.out.println("Inserting: "+i);
			for(int j=0;j<targets_array.length();j++){
					targets_object=targets_array.getJSONObject(j);	
					System.out.println("Inserting: "+targets_object.toString());
					targets_detail_array=new JSONArray(targets_object.getString("target_detail"));
				for(int k=0;k<targets_detail_array.length();k++){
					targets_details=targets_detail_array.getJSONObject(k);		
					System.out.println("Inserting: "+targets_details.toString());
					each_target=((double)(Math.ceil((Integer.parseInt(targets_object.getString("target_overall")))/12.0)));
					String target=String.format("%.0f", each_target);
					dateTime=dateTime.withMonthOfYear(i+1);
					start=dateTime.dayOfMonth().withMinimumValue().toString("dd-MM-yy");
					end=dateTime.dayOfMonth().withMaximumValue().toString("dd-MM-yy");
					System.out.println("Inserting Target Type: "+TargetTypeNames(targets_object.getString("target_type")));
					dbh.insertFacilityTarget(i+1,TargetTypeNames(targets_object.getString("target_type")),
											targets_object.getString("target_category") ,
						   					targets_detail_array.getJSONObject(k).getString("name"),
						   					targets_object.getString("target_group") ,
						   					Integer.valueOf(targets_object.getString("target_overall")),
						   					Integer.valueOf(target), 0, 0, 
						   					start, end,
						   					"Not set",
						   					MobileLearning.CCH_TARGET_STATUS_NEW, "Not yet",
		   					"",dateTime.toString("MMMM"));
			}
			}
		}
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }


		public String TargetTypeNames(String target_type){
			String refined_type = null;
			
			switch(target_type){
			case "expected_pregnancies":
				refined_type="Expected Pregnancies";
				break;
			case "chn_6_to_11_mnths":
				refined_type="6-11 months";
				break;
			case "chn_0_to_11_mnths":
				refined_type="0-11 months";
				break;
				
			case "chn_12_to_23_mnths":
				refined_type="12-23 months";
				break;
				
			case "chn_0_to_23_mnths":
				refined_type="0-23 months";
				break;
				
			case "chn_24_to_59_mnths":
				refined_type="24-59 months";
				break;
				
			case "chn_less_than_5_yrs":
				refined_type="< 5 years";
				break;
				
			case "men_women_50_to_60_yrs":
				refined_type="50yrs-60yrs";
				break;
			case "wifa_15_49_yrs":
				refined_type="WIFA (15-49 yrs)";
				break;
			}
			
			return refined_type;
		}
}
