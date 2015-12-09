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

public class TakeActionOtherSeriousConditionActivity extends BaseActivity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private JSONObject json;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    dbh=new DbHelper(TakeActionOtherSeriousConditionActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("bleeding")){
	    setContentView(R.layout.activity_bleeding_umbilical_cord);
	    getActionBar().setSubtitle("PNC Diagnostic: Other Serious Conditions > Bleeding Umbilical Cord");
	   
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Other Serious Conditions > Bleeding Umbilical Cord");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(TakeActionOtherSeriousConditionActivity.this));
			json.put("battery", dbh.getBatteryStatus(TakeActionOtherSeriousConditionActivity.this));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(TakeActionOtherSeriousConditionActivity.this));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionOtherSeriousConditionActivity.this,KeepingBabyWarmAndMalariaActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
        }else if(take_action_category.equals("soft swelling")){
        setContentView(R.layout.activity_soft_swelling);
        getActionBar().setSubtitle("PNC Diagnostic: Other Serious Conditions > Soft Swelling");
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Other Serious Conditions > Soft Swelling");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(TakeActionOtherSeriousConditionActivity.this));
			json.put("battery", dbh.getBatteryStatus(TakeActionOtherSeriousConditionActivity.this));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(TakeActionOtherSeriousConditionActivity.this));
		} catch (JSONException e) {
			e.printStackTrace();
		}
        TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionOtherSeriousConditionActivity.this,KeepingBabyWarmAndMalariaActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
        }else if(take_action_category.equals("open tissue")){
        setContentView(R.layout.activity_open_tissue);
        getActionBar().setSubtitle("PNC Diagnostic: Other Serious Conditions > Open Tissue");
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Other Serious Conditions > Open Tissue");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(TakeActionOtherSeriousConditionActivity.this));
			json.put("battery", dbh.getBatteryStatus(TakeActionOtherSeriousConditionActivity.this));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(TakeActionOtherSeriousConditionActivity.this));
		} catch (JSONException e) {
			e.printStackTrace();
		}
        TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionOtherSeriousConditionActivity.this,KeepingBabyWarmAndMalariaActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
        }else if(take_action_category.equals("no urine")){
            setContentView(R.layout.activity_no_urine);
            getActionBar().setSubtitle("PNC Diagnostic: Other Serious Conditions > No Urine");
    	    json=new JSONObject();
    	    try {
    			json.put("page", "PNC Diagnostic: Other Serious Conditions > No Urine");
    			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
    			json.put("ver", dbh.getVersionNumber(TakeActionOtherSeriousConditionActivity.this));
    			json.put("battery", dbh.getBatteryStatus(TakeActionOtherSeriousConditionActivity.this));
    			json.put("device", dbh.getDeviceName());
    			json.put("imei", dbh.getDeviceImei(TakeActionOtherSeriousConditionActivity.this));
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
            TextView take_action=(TextView) findViewById(R.id.textView1);
            take_action.setText("Take action: No urine or Meconium");
            TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
 		   click_here.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				Intent intent=new Intent(TakeActionOtherSeriousConditionActivity.this,KeepingBabyWarmAndMalariaActivity.class);
 				intent.putExtra("value", "keeping_baby_warm");
 				startActivity(intent);
 				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
 			}
 			   
 		   });
        }else if(take_action_category.equals("vomiting")){
            setContentView(R.layout.activity_no_urine);
            getActionBar().setSubtitle("PNC Diagnostic: Other Serious Conditions > Vomiting");
    	    json=new JSONObject();
    	    try {
    			json.put("page", "PNC Diagnostic: Other Serious Conditions > Vomiting");
    			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
    			json.put("ver", dbh.getVersionNumber(TakeActionOtherSeriousConditionActivity.this));
    			json.put("battery", dbh.getBatteryStatus(TakeActionOtherSeriousConditionActivity.this));
    			json.put("device", dbh.getDeviceName());
    			json.put("imei", dbh.getDeviceImei(TakeActionOtherSeriousConditionActivity.this));
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
            TextView take_action=(TextView) findViewById(R.id.textView1);
            take_action.setText("Take action: Vomiting");
            TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
 		   click_here.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				Intent intent=new Intent(TakeActionOtherSeriousConditionActivity.this,KeepingBabyWarmAndMalariaActivity.class);
 				intent.putExtra("value", "keeping_baby_warm");
 				startActivity(intent);
 				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
 			}
 			   
 		   });
        }else if(take_action_category.equals("blood_in_stool")){
            setContentView(R.layout.activity_no_urine);
            getActionBar().setSubtitle("PNC Diagnostic: Other Serious Conditions > Blood in Stool");
    	    json=new JSONObject();
    	    try {
    			json.put("page", "PNC Diagnostic: Other Serious Conditions > Blood in Stool");
    			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
    			json.put("ver", dbh.getVersionNumber(TakeActionOtherSeriousConditionActivity.this));
    			json.put("battery", dbh.getBatteryStatus(TakeActionOtherSeriousConditionActivity.this));
    			json.put("device", dbh.getDeviceName());
    			json.put("imei", dbh.getDeviceImei(TakeActionOtherSeriousConditionActivity.this));
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
            TextView take_action=(TextView) findViewById(R.id.textView1);
            take_action.setText("Take action: Blood in stool");
            TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
 		   click_here.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				Intent intent=new Intent(TakeActionOtherSeriousConditionActivity.this,KeepingBabyWarmAndMalariaActivity.class);
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
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
