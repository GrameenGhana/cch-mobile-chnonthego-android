package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TakeActionBreastProblemsPNCMotherActivity extends BaseActivity {
	private Context mContext;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private String take_action_category;
	private JSONObject json;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=TakeActionBreastProblemsPNCMotherActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	  
	    Bundle extras = getIntent().getExtras(); 
	    if (extras != null) {
	          take_action_category= extras.getString("value");
	        }
	        if(take_action_category.equals("mastitis")){
	        	   setContentView(R.layout.activity_mastitis_pnc_mother);
	        	   getActionBar().setSubtitle("PNC Mother Diagnostic: Breast Problems > Mastitis");
	       	    	json=new JSONObject();
	       	    	try {
	       			json.put("page", "PNC Mother Diagnostic: Breast Problems > Mastitis");
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
	   					Intent intent=new Intent(mContext,BreastProblemsCounsellingActivity.class);
	   					startActivity(intent);
	   					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
	   				}
	               	
	               });
	        }else if(take_action_category.equals("breast_engorgment")){
	        	 setContentView(R.layout.activity_breast_engorgment_pnc_mother);
	        	  getActionBar().setSubtitle("PNC Mother Diagnostic: Breast Problems > Breast Engorgment");
	      	    json=new JSONObject();
	      	    try {
	      			json.put("page", "PNC Mother Diagnostic: Breast Problems > Breast Engorgment");
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
	     				Intent intent=new Intent(mContext,BreastProblemsCounsellingActivity.class);
	     				startActivity(intent);
	     				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
	     			}
	             	
	             });
	             TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
	             click_here_too.setOnClickListener(new OnClickListener(){

	     			@Override
	     			public void onClick(View v) {
	     				Intent intent=new Intent(mContext,InfantFeedingMenuActivity.class);
	     				startActivity(intent);
	     				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
	     			}
	             	
	             });
	        }else if(take_action_category.equals("cracked_nipples")){
	        	 setContentView(R.layout.activity_cracked_nipples_pnc_mother);
	        	  getActionBar().setSubtitle("PNC Mother Diagnostic: Breast Problems > Cracked Nipples");
		      	    json=new JSONObject();
		      	    try {
		      			json.put("page", "PNC Mother Diagnostic: Breast Problems > Cracked Nipples");
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
	     				Intent intent=new Intent(mContext,BreastProblemsCounsellingActivity.class);
	     				startActivity(intent);
	     				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
	     			}
	             	
	             });
	             TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
	             click_here_too.setOnClickListener(new OnClickListener(){

	     			@Override
	     			public void onClick(View v) {
	     				Intent intent=new Intent(mContext,InfantFeedingMenuActivity.class);
	     				startActivity(intent);
	     				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
	     			}
	             	
	             });
	        }else if(take_action_category.equals("no_problems")){
	        	 setContentView(R.layout.activity_no_breast_problems_pnc_mother);
	        	 getActionBar().setSubtitle("PNC Mother Diagnostic: Breast Problems > No Problems");
		      	    json=new JSONObject();
		      	    try {
		      			json.put("page", "PNC Mother Diagnostic: Breast Problems > No Problems");
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
	     				Intent intent=new Intent(mContext,PostnatalCareCounsellingTopicsActivity.class);
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
