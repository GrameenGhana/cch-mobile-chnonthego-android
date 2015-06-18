package org.grameenfoundation.poc;


import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TakeActionActivity extends BaseActivity{

	//private ListView listView_takeAction;
	private String take_action_category;
	//private TextView textView_takeAction;
//	private Context mContext;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	String data;
	private JSONObject json;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=TakeActionActivity.this;
	    dbh=new DbHelper(mContext);
	    getActionBar().setTitle("Point of Care");
	   
	   
	   // listView_takeAction=(ListView) findViewById(R.id.listView_takeAction);
	  //  textView_takeAction=(TextView) findViewById(R.id.textView_takeActionCategory);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("take_action");
          start_time=extras.getLong("start_time");
        }
        if(take_action_category.equals("Difficulty breathing")){
        	   setContentView(R.layout.activity_difficulty_breathing_anc);
        	   getActionBar().setSubtitle("ANC Diagnostic: Acute Emergencies > Difficulty Breathing");
        	   json=new JSONObject();
       	    try {
       			json.put("page", "ANC Diagnostic: Acute Emergencies > Difficulty Breathing");
       			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
       			json.put("ver", dbh.getVersionNumber(mContext));
       			json.put("battery", dbh.getBatteryStatus(mContext));
       			json.put("device", dbh.getDeviceName());
       			json.put("imei", dbh.getDeviceImei(mContext));
       		} catch (JSONException e) {
       			e.printStackTrace();
       		}
        }else if(take_action_category.equals("Edema")){
        	 setContentView(R.layout.activity_edema_of_feet);
        	 getActionBar().setSubtitle("ANC Diagnostic: Acute Emergencies > Edema");
        	 json=new JSONObject();
     	    try {
 			json.put("page", "ANC Diagnostic: Acute Emergencies > Edema");
     			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
     			json.put("ver", dbh.getVersionNumber(mContext));
     			json.put("battery", dbh.getBatteryStatus(mContext));
     			json.put("device", dbh.getDeviceName());
     			json.put("imei", dbh.getDeviceImei(mContext));
     		} catch (JSONException e) {
     			e.printStackTrace();
     		}
        }else if(take_action_category.equals("Shock")){
        	 setContentView(R.layout.activity_shock_anc);
        	 getActionBar().setSubtitle("ANC Diagnostic: Acute Emergencies > Shock");
        	 json=new JSONObject();
     	    try {
 			json.put("page", "ANC Diagnostic: Acute Emergencies > Shock");
     			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
     			json.put("ver", dbh.getVersionNumber(mContext));
     			json.put("battery", dbh.getBatteryStatus(mContext));
     			json.put("device", dbh.getDeviceName());
     			json.put("imei", dbh.getDeviceImei(mContext));
     		} catch (JSONException e) {
     			e.printStackTrace();
     		}
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
	
}
