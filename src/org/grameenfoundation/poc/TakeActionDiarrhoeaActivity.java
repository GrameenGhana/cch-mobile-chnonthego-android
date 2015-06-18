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

public class TakeActionDiarrhoeaActivity extends BaseActivity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private JSONObject json;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=TakeActionDiarrhoeaActivity.this;
	    getActionBar().setTitle("Point of Care");
	    start_time=System.currentTimeMillis();
	    dbh=new DbHelper(TakeActionDiarrhoeaActivity.this);
	  
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if(take_action_category.equals("severe_diarrhoea")){
        	   setContentView(R.layout.activity_persistent_diarrhoea);
        	   getActionBar().setSubtitle("PNC Diagnostic: Diarrhoea > Severe Diarrhoea");
        		 
       	    json=new JSONObject();
       	    try {
       			json.put("page", "PNC Diagnostic: Diarrhoea > Severe Diarrhoea");
       			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
       			json.put("ver", dbh.getVersionNumber(mContext));
       			json.put("battery", dbh.getBatteryStatus(mContext));
       			json.put("device", dbh.getDeviceName());
       			json.put("imei", dbh.getDeviceImei(mContext));
       		} catch (JSONException e) {
       			e.printStackTrace();
       		}
        	   TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
    		   click_here_too.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(TakeActionDiarrhoeaActivity.this,TreatingDiarrhoeaActivity.class);
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
    			   
    		   });
    		   TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
    		   click_here.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(TakeActionDiarrhoeaActivity.this,KeepingBabyWarmAndMalariaActivity.class);
    				intent.putExtra("value", "keeping_baby_warm");
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
    			   
    		   });
        }else if(take_action_category.equals("blood_stool")){
     	   setContentView(R.layout.activity_blood_in_stool);
     	  getActionBar().setSubtitle("PNC Diagnostic: Diarrhoea > Blood in Stool");
 		 
     	    json=new JSONObject();
     	    try {
     			json.put("page", "PNC Diagnostic: Diarrhoea > Blood in Stool");
     			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
     			json.put("ver", dbh.getVersionNumber(mContext));
     			json.put("battery", dbh.getBatteryStatus(mContext));
     			json.put("device", dbh.getDeviceName());
     			json.put("imei", dbh.getDeviceImei(mContext));
     		} catch (JSONException e) {
     			e.printStackTrace();
     		}
     	  TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
		   click_here_too.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionDiarrhoeaActivity.this,TreatingDiarrhoeaActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
		   TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionDiarrhoeaActivity.this,KeepingBabyWarmAndMalariaActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
		   TextView click_here_three=(TextView) findViewById(R.id.textView_clickHereThree);
		   click_here_three.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionDiarrhoeaActivity.this,KeepingBabyWarmAndMalariaActivity.class);
				intent.putExtra("value", "bloody_diarrhoea");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
     }else if(take_action_category.equals("no_diarrhoea")){
   	   setContentView(R.layout.activity_no_diarrhoea);
   	 getActionBar().setSubtitle("PNC Diagnostic: Diarrhoea > No Diarrhoea");
		 
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Diarrhoea > No Diarrhoea");
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
			Intent intent=new Intent(TakeActionDiarrhoeaActivity.this,HomeCareForInfantActivity.class);
			intent.putExtra("value", "keeping_baby_warm");
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		}
		   
	   });
	   TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
	   click_here_too.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(TakeActionDiarrhoeaActivity.this,HIVInfectionActivity.class);
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
