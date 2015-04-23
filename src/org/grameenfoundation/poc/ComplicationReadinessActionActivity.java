package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;

public class ComplicationReadinessActionActivity extends BaseActivity {

	private String take_action_category;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private JSONObject json;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    mContext= ComplicationReadinessActionActivity.this;
	    getActionBar().setSubtitle("PNC Counselling: Complication Readiness");
	    dbh=new DbHelper(ComplicationReadinessActionActivity.this);
	    start_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Counselling: Complication Readiness");
			json.put("section", MobileLearning.CCH_COUNSELLING);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
          if(take_action_category.equalsIgnoreCase("danger_signs_mother")){
        	  setContentView(R.layout.activity_danger_signs_in_mother);
        	  end_time=System.currentTimeMillis();
          }else  if(take_action_category.equalsIgnoreCase("danger_signs_newborn")){
        	  setContentView(R.layout.activity_newborn_danger_signs);
        	  end_time=System.currentTimeMillis();
          }else if(take_action_category.equalsIgnoreCase("readiness_plan")){
        	  setContentView(R.layout.activity_readiness_plan);
        	  end_time=System.currentTimeMillis();
          }
        }
	}
	public void onBackPressed()
	{
	    
		//dbh.insertCCHLog("Point of Care", "PNC Counselling Complication Readiness", start_time.toString(), end_time.toString());
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
