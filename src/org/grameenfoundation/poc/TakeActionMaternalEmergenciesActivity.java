package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionMaternalEmergenciesActivity extends BaseActivity {

	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private String take_action_category;
	private JSONObject json;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Maternal Emergencies");
	    mContext=TakeActionMaternalEmergenciesActivity.this;
	    dbh=new DbHelper(TakeActionMaternalEmergenciesActivity.this);
	    start_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Maternal Emergencies");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
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
        }
        if(take_action_category.equalsIgnoreCase("difficulty_breathing")){
        setContentView(R.layout.activity_mng_danger_sign_cyanosis);															
        }else if(take_action_category.equalsIgnoreCase("shock")){
            setContentView(R.layout.activity_mng_danger_sign_shock);// Same as the ANC content															
         }else if(take_action_category.equalsIgnoreCase("heavy_bleeding")){
             setContentView(R.layout.activity_mng_danger_sign_heavy_bleeding);// Same as the ANC content															
             }else if(take_action_category.equalsIgnoreCase("convulsion")){
                 setContentView(R.layout.activity_convulsion_pnc);														
                 }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
