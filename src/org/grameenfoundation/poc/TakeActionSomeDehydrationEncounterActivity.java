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
import android.widget.TextView;

public class TakeActionSomeDehydrationEncounterActivity extends BaseActivity {

	private String take_action_category;
	private Button button_next;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private JSONObject json;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    mContext=TakeActionSomeDehydrationEncounterActivity.this;
	    dbh=new DbHelper(TakeActionSomeDehydrationEncounterActivity.this);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	   
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equalsIgnoreCase("home_visit")){
        	setContentView(R.layout.activity_diarrhoea_home_visit);
        	 getActionBar().setSubtitle("PNC Diagnostic: Diarrhoea > Home Visit");
     	    json=new JSONObject();
     	    try {
     			json.put("page", "PNC Diagnostic: Diarrhoea > Home Visit");
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
					Intent intent=new Intent(TakeActionSomeDehydrationEncounterActivity.this,KeepingBabyWarmAndMalariaActivity.class);
					intent.putExtra("value", "keeping_baby_warm");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
				   
			   });
			   
			   TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
			   click_here_too.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(TakeActionSomeDehydrationEncounterActivity.this,TreatingDiarrhoeaActivity.class);
					intent.putExtra("value", "keeping_baby_warm");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
				   
			   });
        } else if(take_action_category.equalsIgnoreCase("chps_one")){
        	setContentView(R.layout.activity_chps_one);
        	getActionBar().setSubtitle("PNC Diagnostic: Diarrhoea > CHPS Visit");
     	    json=new JSONObject();
     	    try {
     			json.put("page", "PNC Diagnostic: Diarrhoea > CHPS Visit");
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
					Intent intent=new Intent(TakeActionSomeDehydrationEncounterActivity.this,TreatingDiarrhoeaActivity.class);
					intent.putExtra("value", "keeping_baby_warm");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
				   
			   });
        	button_next=(Button) findViewById(R.id.button_next);
        	button_next.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent =new Intent(TakeActionSomeDehydrationEncounterActivity.this,TakeActionSomeDehydrationEncounterNextActivity.class);
					intent.putExtra("category","chps_one_next");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
        		
        	});
        }else if(take_action_category.equalsIgnoreCase("chps_two")){
        	setContentView(R.layout.activity_chps_two);
        	getActionBar().setSubtitle("PNC Diagnostic: Diarrhoea > CHPS Visit");
     	    json=new JSONObject();
     	    try {
     			json.put("page", "PNC Diagnostic: Diarrhoea > CHPS Visit");
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
					Intent intent=new Intent(TakeActionSomeDehydrationEncounterActivity.this,TreatingDiarrhoeaActivity.class);
					intent.putExtra("value", "keeping_baby_warm");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
				   
			   });
        	button_next=(Button) findViewById(R.id.button_next);
        	button_next.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent =new Intent(TakeActionSomeDehydrationEncounterActivity.this,TakeActionSomeDehydrationEncounterNextActivity.class);
					intent.putExtra("category","chps_two_next");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
        		
        	});
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care",json.toString() , start_time.toString(), end_time.toString());
		finish();
	}
}
