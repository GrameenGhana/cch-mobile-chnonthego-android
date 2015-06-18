package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionBleedingActivity extends BaseActivity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private JSONObject json;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=TakeActionBleedingActivity.this;
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	   
	    dbh=new DbHelper(TakeActionBleedingActivity.this);
	    start_time=System.currentTimeMillis();
	    
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("heavy")){
	    setContentView(R.layout.activity_heavy_bleeding);
	    getActionBar().setSubtitle("ANC Diagnostic: Managing Danger Signs > Heavy Bleeding ");
	    json=new JSONObject();
	    try {
			json.put("page", "ANC Diagnostic: Managing Danger Signs > Heavy Bleeding");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
        }else if(take_action_category.equals("light")){
        setContentView(R.layout.activity_light_bleeding);
        getActionBar().setSubtitle("ANC Diagnostic: Managing Danger Signs > Light Bleeding ");
	    json=new JSONObject();
	    try {
			json.put("page", "ANC Diagnostic: Managing Danger Signs > Light Bleeding");
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
