package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TakeActionManagingDangerSignsMotherPNCActivity extends BaseActivity {

	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private String take_action_category;
	private JSONObject json;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    mContext=TakeActionManagingDangerSignsMotherPNCActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	   
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }   
        if(take_action_category.equals("difficulty_breathing")){
        	   setContentView(R.layout.activity_mng_danger_sign_cyanosis);
        	   getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Difficulty Breathing");
       	    json=new JSONObject();
       	    try {
       			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Difficulty Breathing");
       			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
       			json.put("ver", dbh.getVersionNumber(mContext));
       			json.put("battery", dbh.getBatteryStatus(mContext));
       			json.put("device", dbh.getDeviceName());
       			json.put("imei", dbh.getDeviceImei(mContext));
       		} catch (JSONException e) {
       			e.printStackTrace();
       		}
        }else if(take_action_category.equalsIgnoreCase("shock")){
            setContentView(R.layout.activity_mng_danger_sign_shock);// Same as the ANC content		
            getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Shock");
       	    json=new JSONObject();
       	    try {
       			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Shock");
       			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
       			json.put("ver", dbh.getVersionNumber(mContext));
       			json.put("battery", dbh.getBatteryStatus(mContext));
       			json.put("device", dbh.getDeviceName());
       			json.put("imei", dbh.getDeviceImei(mContext));
       		} catch (JSONException e) {
       			e.printStackTrace();
       		}
         }else if(take_action_category.equalsIgnoreCase("heavy_bleeding")){
             setContentView(R.layout.activity_mng_danger_sign_heavy_bleeding);// Same as the ANC content	
             getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Heavy Bleeding");
        	    json=new JSONObject();
        	    try {
        			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Heavy Bleeding");
        			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
        			json.put("ver", dbh.getVersionNumber(mContext));
        			json.put("battery", dbh.getBatteryStatus(mContext));
        			json.put("device", dbh.getDeviceName());
        			json.put("imei", dbh.getDeviceImei(mContext));
        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
         }else if(take_action_category.equalsIgnoreCase("convulsing")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	  getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Convulsion");
      	    json=new JSONObject();
      	    try {
      			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Convulsion");
      			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
      			json.put("ver", dbh.getVersionNumber(mContext));
      			json.put("battery", dbh.getBatteryStatus(mContext));
      			json.put("device", dbh.getDeviceName());
      			json.put("imei", dbh.getDeviceImei(mContext));
      		} catch (JSONException e) {
      			e.printStackTrace();
      		}
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Convulsing (now or recently), Unconscious ");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	 image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("severe_headache")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	  getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Severe Headache");
        	    json=new JSONObject();
        	    try {
        			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Severe Headache");
        			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
        			json.put("ver", dbh.getVersionNumber(mContext));
        			json.put("battery", dbh.getBatteryStatus(mContext));
        			json.put("device", dbh.getDeviceName());
        			json.put("imei", dbh.getDeviceImei(mContext));
        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Severe headache/blurred vision ");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	image.setImageResource(R.drawable.severe_headache);
         }else if(take_action_category.equalsIgnoreCase("diastolic")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Diastolic BP ≥ 90 mmHg");
     	    json=new JSONObject();
     	    try {
     			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Diastolic BP ≥ 90 mmHg");
     			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
     			json.put("ver", dbh.getVersionNumber(mContext));
     			json.put("battery", dbh.getBatteryStatus(mContext));
     			json.put("device", dbh.getDeviceName());
     			json.put("imei", dbh.getDeviceImei(mContext));
     		} catch (JSONException e) {
     			e.printStackTrace();
     		}
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Diastolic BP ≥ 90 mmHg");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	 image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("severe_abdominal")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Severe abdominal pain");
      	    json=new JSONObject();
      	    try {
      			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Severe abdominal pain");
      			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
      			json.put("ver", dbh.getVersionNumber(mContext));
      			json.put("battery", dbh.getBatteryStatus(mContext));
      			json.put("device", dbh.getDeviceName());
      			json.put("imei", dbh.getDeviceImei(mContext));
      		} catch (JSONException e) {
      			e.printStackTrace();
      		}
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Severe abdominal pain ");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	 image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("persistent_vomiting")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Persistent vomiting");
       	    json=new JSONObject();
       	    try {
       			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Persistent vomiting");
       			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
       			json.put("ver", dbh.getVersionNumber(mContext));
       			json.put("battery", dbh.getBatteryStatus(mContext));
       			json.put("device", dbh.getDeviceName());
       			json.put("imei", dbh.getDeviceImei(mContext));
       		} catch (JSONException e) {
       			e.printStackTrace();
       		}
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Persistent vomiting");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	image.setImageResource(R.drawable.persistent_vomiting);
         }else if(take_action_category.equalsIgnoreCase("persistent_vomiting")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Persistent vomiting");
        	    json=new JSONObject();
        	    try {
        			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Persistent vomiting");
        			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
        			json.put("ver", dbh.getVersionNumber(mContext));
        			json.put("battery", dbh.getBatteryStatus(mContext));
        			json.put("device", dbh.getDeviceName());
        			json.put("imei", dbh.getDeviceImei(mContext));
        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Persistent vomiting");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("pain_in_calf")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Pain in calf");
        	    json=new JSONObject();
        	    try {
        			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Pain in calf");
        			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
        			json.put("ver", dbh.getVersionNumber(mContext));
        			json.put("battery", dbh.getBatteryStatus(mContext));
        			json.put("device", dbh.getDeviceName());
        			json.put("imei", dbh.getDeviceImei(mContext));
        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Pain in calf with or without swelling ");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	image.setImageResource(R.drawable.edema);
         }else if(take_action_category.equalsIgnoreCase("painful_or_tender_wound")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Painful or tender wound");
        	 getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Painful or tender wound");
     	    json=new JSONObject();
     	    try {
     			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Painful or tender wound");
     			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
     			json.put("ver", dbh.getVersionNumber(mContext));
     			json.put("battery", dbh.getBatteryStatus(mContext));
     			json.put("device", dbh.getDeviceName());
     			json.put("imei", dbh.getDeviceImei(mContext));
     		} catch (JSONException e) {
     			e.printStackTrace();
     		}
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("pain_on_urination")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Pain on urination");
      	    json=new JSONObject();
      	    try {
      			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Pain on urination");
      			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
      			json.put("ver", dbh.getVersionNumber(mContext));
      			json.put("battery", dbh.getBatteryStatus(mContext));
      			json.put("device", dbh.getDeviceName());
      			json.put("imei", dbh.getDeviceImei(mContext));
      		} catch (JSONException e) {
      			e.printStackTrace();
      		}
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Pain on urination/dribbling urine");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("pallor")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Pallor");
        	 getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Pallor");
       	    json=new JSONObject();
       	    try {
       			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Pallor");
       			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
       			json.put("ver", dbh.getVersionNumber(mContext));
       			json.put("battery", dbh.getBatteryStatus(mContext));
       			json.put("device", dbh.getDeviceName());
       			json.put("imei", dbh.getDeviceImei(mContext));
       		} catch (JSONException e) {
       			e.printStackTrace();
       		}
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	image.setImageResource(R.drawable.pallor);
         }else if(take_action_category.equalsIgnoreCase("abnormal_behaviour")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Abnormal behaviour/depression");
        	 getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs > Abnormal behaviour/depression");
        	    json=new JSONObject();
        	    try {
        			json.put("page", "PNC Mother Diagnostic: Managing Danger Signs > Abnormal behaviour/depression");
        			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
        			json.put("ver", dbh.getVersionNumber(mContext));
        			json.put("battery", dbh.getBatteryStatus(mContext));
        			json.put("device", dbh.getDeviceName());
        			json.put("imei", dbh.getDeviceImei(mContext));
        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	image.setVisibility(View.GONE);
         }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
