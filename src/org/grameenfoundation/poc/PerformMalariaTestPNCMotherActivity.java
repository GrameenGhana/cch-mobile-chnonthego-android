package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PerformMalariaTestPNCMotherActivity extends BaseActivity {

	private Button button_positive;
	private Button button_negative;
	private Button button_testNotDone;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private JSONObject json; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=PerformMalariaTestPNCMotherActivity.this;
	    setContentView(R.layout.activity_malaria_test_);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Malaria");
	    dbh=new DbHelper(PerformMalariaTestPNCMotherActivity.this);
	    start_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Mother Diagnostic: Malaria");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    button_positive=(Button) findViewById(R.id.button_positive);
	    button_positive.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(PerformMalariaTestPNCMotherActivity.this,AskMalariaComplicatedPNCMotherActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    button_negative=(Button) findViewById(R.id.button_negative);
	    button_negative.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(PerformMalariaTestPNCMotherActivity.this,TakeActionSevereMalariaPNCMotherActivity.class);
				intent.putExtra("category", "negative");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    button_testNotDone=(Button) findViewById(R.id.button_testNotDone);
	    button_testNotDone.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(PerformMalariaTestPNCMotherActivity.this,TakeActionSevereMalariaPNCMotherActivity.class);
				intent.putExtra("category", "not done");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
