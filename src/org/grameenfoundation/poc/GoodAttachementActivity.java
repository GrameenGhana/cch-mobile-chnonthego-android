package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;

public class GoodAttachementActivity extends BaseActivity {

	private String take_action_category;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private JSONObject json;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=GoodAttachementActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Good Attachment");
	    dbh=new DbHelper(GoodAttachementActivity.this);
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Counselling: Good Attachment");
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
        }
        if(take_action_category.equalsIgnoreCase("good_attachement")){
	    setContentView(R.layout.activity_good_attachement);
	    start_time=System.currentTimeMillis();
        }else if(take_action_category.equalsIgnoreCase("low_birth_weight")){
        setContentView(R.layout.activity_breastfeeding_low_birth_weight);
        start_time=System.currentTimeMillis();
        }else if(take_action_category.equalsIgnoreCase("low_birth_weight_next")){
            setContentView(R.layout.activity_low_birth_weight_next);
            start_time=System.currentTimeMillis();
            }
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		//dbh.insertCCHLog("Point of Care", "PNC Counselling Good Attachment", start_time.toString(), end_time.toString());
	    dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
