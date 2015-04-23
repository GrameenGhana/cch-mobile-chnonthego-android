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

public class TakeActionSeverDiseasesActivity extends BaseActivity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private String data;
	private JSONObject json;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Very Severe Disease");
	    mContext=TakeActionSeverDiseasesActivity.this;
	    dbh=new DbHelper(TakeActionSeverDiseasesActivity.this);
	    start_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Very Severe Disease");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("difficulty")){
	    setContentView(R.layout.activity_difficulty_breathing);
	    TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,KeepingBabyWarmAndMalariaActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
        }else if(take_action_category.equals("cyanosis")){
        setContentView(R.layout.activity_cyanosis);
        TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,KeepingBabyWarmAndMalariaActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
        }else if(take_action_category.equals("convulsion")){
        setContentView(R.layout.activity_convulsion);
        TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,KeepingBabyWarmAndMalariaActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
        }else if(take_action_category.equals("fever")){
        	setContentView(R.layout.activity_fever_take_action);	
        	TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
  		   click_here.setOnClickListener(new OnClickListener(){

  			@Override
  			public void onClick(View v) {
  				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,KeepingBabyWarmAndMalariaActivity.class);
  				intent.putExtra("value", "keeping_baby_warm");
  				startActivity(intent);
  				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
  			}
  			   
  		   });
        }else if(take_action_category.equals("feeding")){
        	 setContentView(R.layout.activity_feeding_difficulty);	
        	 TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
    		   click_here.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,KeepingBabyWarmAndMalariaActivity.class);
    				intent.putExtra("value", "keeping_baby_warm");
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
    			   
    		   });
        }else if(take_action_category.equals("umbilicus")){
        	setContentView(R.layout.activity_umbilicus_infection);	
        	 TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
  		   click_here.setOnClickListener(new OnClickListener(){

  			@Override
  			public void onClick(View v) {
  				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,KeepingBabyWarmAndMalariaActivity.class);
  				intent.putExtra("value", "keeping_baby_warm");
  				startActivity(intent);
  				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
  			}
  			   
  		   });
  		 TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
  		 click_here_too.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,ReturningForCareActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
        }else if(take_action_category.equals("eye")){
        	setContentView(R.layout.activity_eye_infection);
       	 TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,KeepingBabyWarmAndMalariaActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
        }else if(take_action_category.equals("no symptoms")){
        	setContentView(R.layout.activity_no_symptoms);	
        	TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
 		   click_here.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,HomeCareForInfantActivity.class);
 				intent.putExtra("value", "keeping_baby_warm");
 				startActivity(intent);
 				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
 			}
 			   
 		   });
        	 TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
      		 click_here_too.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,JaundiceActivity.class);
    				intent.putExtra("value", "keeping_baby_warm");
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
    			   
    		   });
            }else if(take_action_category.equals("eye")){
            	setContentView(R.layout.activity_eye_infection);
           	 TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
    		   click_here.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,HomeCareForInfantActivity.class);
    				intent.putExtra("value", "keeping_baby_warm");
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
    			   
    		   });
        }else if(take_action_category.equals("hypothermia")){
        	setContentView(R.layout.activity_hypothermia);	
        	 TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
  		   click_here.setOnClickListener(new OnClickListener(){

  			@Override
  			public void onClick(View v) {
  				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,KeepingBabyWarmAndMalariaActivity.class);
  				intent.putExtra("value", "keeping_baby_warm");
  				startActivity(intent);
  				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
  			}
  			   
  		   });
        }else if(take_action_category.equals("low")){
        	setContentView(R.layout.activity_low_temperature);
        	TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
 		   click_here.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,KeepingBabyWarmAndMalariaActivity.class);
 				intent.putExtra("value", "keeping_baby_warm");
 				startActivity(intent);
 				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
 			}
 			   
 		   });
        }else if(take_action_category.equals("fast_breathing")){
        	setContentView(R.layout.activity_fast_breathing);
        	TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
  		   click_here.setOnClickListener(new OnClickListener(){

  			@Override
  			public void onClick(View v) {
  				Intent intent=new Intent(TakeActionSeverDiseasesActivity.this,KeepingBabyWarmAndMalariaActivity.class);
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
