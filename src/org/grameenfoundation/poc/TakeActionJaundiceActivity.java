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
import android.widget.TextView;

public class TakeActionJaundiceActivity extends BaseActivity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private JSONObject json;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    mContext=TakeActionJaundiceActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("severe")){
	    setContentView(R.layout.activity_severe_jaundice);
	    getActionBar().setSubtitle("PNC Diagnostic: Jaundice > Severe Jaundice");
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Jaundice > Severe Jaundice");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionJaundiceActivity.this,KeepingBabyWarmAndMalariaActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
        }else if(take_action_category.equals("jaundice")){
        setContentView(R.layout.activity_just_jaundice);
        getActionBar().setSubtitle("PNC Diagnostic: Jaundice > Just Jaundice");
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Jaundice > Just Jaundice");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
        TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionJaundiceActivity.this,HomeCareForInfantActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
        }else if(take_action_category.equals("no jaundice")){
        setContentView(R.layout.activity_no_jaundice);
        getActionBar().setSubtitle("PNC Diagnostic: Jaundice > No Jaundice");
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Jaundice > No Jaundice");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
        TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionJaundiceActivity.this,HomeCareForInfantActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
		   TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
		   click_here_too.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionJaundiceActivity.this,OtherSeriousConditionsActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care",json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
