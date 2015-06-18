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

public class TakeActionFeedingProblemsActivity extends BaseActivity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private JSONObject json;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=TakeActionFeedingProblemsActivity.this;
	    dbh=new DbHelper(TakeActionFeedingProblemsActivity.this);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	  
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if(take_action_category.equals("not_attached")){
        	   setContentView(R.layout.activity_poor_attachement);
        	   getActionBar().setSubtitle("PNC Diagnostic: Feeding Problems > Not Attached");
       	    json=new JSONObject();
       	    try {
       			json.put("page", "PNC Diagnostic: Feeding Problems > Not Attached");
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
      				Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,InfantFeedingNextActivity.class);
      				intent.putExtra("value", "breast_attachement");
      				startActivity(intent);
      				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
      			}
      			   
      		   });
      		 TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
      		click_here_too.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,ExpressBreastmilkActivity.class);
    				intent.putExtra("value", "breast_attachement");
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
    			   
    		   });
      		
      		TextView click_here_three=(TextView) findViewById(R.id.textView_clickHereThree);
      		click_here_three.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,HomeCareForInfantActivity.class);
    				intent.putExtra("value", "exclusive_breastfeeding");
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
    			   
    		   });
        }else if(take_action_category.equals("not_suckling")){
     	   setContentView(R.layout.activity_poor_attachement);
     	   getActionBar().setSubtitle("PNC Diagnostic: Feeding Problems > Not Suckling");
     	    json=new JSONObject();
     	    try {
     			json.put("page", "PNC Diagnostic: Feeding Problems > Not Suckling");
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
 				Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,InfantFeedingNextActivity.class);
 				intent.putExtra("value", "breast_attachement");
 				startActivity(intent);
 				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
 			}
 			   
 		   });
 		 TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
 		click_here_too.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,ExpressBreastmilkActivity.class);
				intent.putExtra("value", "breast_attachement");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
 		
 		TextView click_here_three=(TextView) findViewById(R.id.textView_clickHereThree);
 		click_here_three.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,HomeCareForInfantActivity.class);
				intent.putExtra("value", "exclusive_breastfeeding");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			   
		   });
     }else if(take_action_category.equals("breastfeeding")){
   	   setContentView(R.layout.activity_breastfeeding);
   	 getActionBar().setSubtitle("PNC Diagnostic: Feeding Problems > BreastFeeding");
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Feeding Problems > BreastFeeding");
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
			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,ExclusiveBreastFeedingActivity.class);
			intent.putExtra("value", "breast_attachement");
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		}
		   
	   });
	 TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
	click_here_too.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,InfantFeedingNextActivity.class);
			intent.putExtra("value", "feeding_frequency");
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		}
		   
	   });
	
	TextView click_here_three=(TextView) findViewById(R.id.textView_clickHereThree);
	click_here_three.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,HomeCareForInfantActivity.class);
			intent.putExtra("value", "exclusive_breastfeeding");
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		}
		   
	   });
     }else if(take_action_category.equals("receive_food")){
   	   setContentView(R.layout.activity_receive_other_foods);
   	   getActionBar().setSubtitle("PNC Diagnostic: Feeding Problems > Receive Other Foods");
   	   json=new JSONObject();
   	   try {
   		   json.put("page", "PNC Diagnostic: Feeding Problems > Receive Other Foods");
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
  			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,InfantFeedingNextActivity.class);
  			intent.putExtra("value", "feeding_frequency");
  			startActivity(intent);
  			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
  		}
  		   
  	   });
  	 TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
  	click_here_too.setOnClickListener(new OnClickListener(){

  		@Override
  		public void onClick(View v) {
  			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,ExpressBreastmilkActivity.class);
  			intent.putExtra("value", "feeding_frequency");
  			startActivity(intent);
  			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
  		}
  		   
  	   });
  	
  	TextView click_here_three=(TextView) findViewById(R.id.textView_clickHereThree);
  	click_here_three.setOnClickListener(new OnClickListener(){

  		@Override
  		public void onClick(View v) {
  			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,HomeCareForInfantActivity.class);
  			intent.putExtra("value", "exclusive_breastfeeding");
  			startActivity(intent);
  			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
  		}
  		   
  	   });
     }else if(take_action_category.equals("not_breastfeeding")){
     	   setContentView(R.layout.activity_not_breastfeeding);
     	  getActionBar().setSubtitle("PNC Diagnostic: Feeding Problems > Not Breastfeeding");
      	   json=new JSONObject();
      	   try {
      		   json.put("page", "PNC Diagnostic: Feeding Problems > Not Breastfeeding");
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
     			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,ExpressBreastmilkActivity.class);
     			intent.putExtra("value", "feeding_frequency");
     			startActivity(intent);
     			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
     		}
     		   
     	   });
     	 TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
     	click_here_too.setOnClickListener(new OnClickListener(){

     		@Override
     		public void onClick(View v) {
     			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,HomeCareForInfantActivity.class);
     			intent.putExtra("value", "feeding_frequency");
     			startActivity(intent);
     			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
     		}
     		   
     	   });
       }else if(take_action_category.equals("low_weight_for_age")){
     	   setContentView(R.layout.activity_low_birth_weight_for_age);
     	   getActionBar().setSubtitle("PNC Diagnostic: Feeding Problems > Low Weight for age");
     	   json=new JSONObject();
     	   try {
     		   json.put("page", "PNC Diagnostic: Feeding Problems > Low Weight for age");
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
     	  			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,InfantFeedingNextActivity.class);
     	  			intent.putExtra("value", "low_birth_weight");
     	  			startActivity(intent);
     	  			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
     	  		}
     	  		   
     	  	   });
     	  	 TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
     	  	click_here_too.setOnClickListener(new OnClickListener(){

     	  		@Override
     	  		public void onClick(View v) {
     	  			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,ExpressBreastmilkActivity.class);
     	  			intent.putExtra("value", "feeding_frequency");
     	  			startActivity(intent);
     	  			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
     	  		}
     	  		   
     	  	   });
     	  	
     	  	TextView click_here_three=(TextView) findViewById(R.id.textView_clickHereThree);
     	  	click_here_three.setOnClickListener(new OnClickListener(){

     	  		@Override
     	  		public void onClick(View v) {
     	  			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,KangarooCareActivity.class);
     	  			startActivity(intent);
     	  			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
     	  		}
     	  		   
     	  	   });
     	  	TextView click_here_four=(TextView) findViewById(R.id.textView_clickHereFour);
     	  	click_here_four.setOnClickListener(new OnClickListener(){

     	  		@Override
     	  		public void onClick(View v) {
     	  			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,HomeCareForInfantActivity.class);
     	  			startActivity(intent);
     	  			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
     	  		}
     	  		   
     	  	   });
       }else if(take_action_category.equals("thrush")){
     	   setContentView(R.layout.activity_thrush);
     	  getActionBar().setSubtitle("PNC Diagnostic: Feeding Problems > Thrush");
    	   json=new JSONObject();
    	   try {
    		   json.put("page", "PNC Diagnostic: Feeding Problems > Thrush");
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
   	  			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,TreatingLocationInfectionActivity.class);
   	  			intent.putExtra("value", "low_birth_weight");
   	  			startActivity(intent);
   	  		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
   	  		}
   	  		   
   	  	   });
   	  	 TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
   	  	click_here_too.setOnClickListener(new OnClickListener(){

   	  		@Override
   	  		public void onClick(View v) {
   	  			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,HomeCareForInfantActivity.class);
   	  			intent.putExtra("value", "feeding_frequency");
   	  			startActivity(intent);
   	  		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
   	  		}
   	  		   
   	  	   });
       }else if(take_action_category.equals("no_feeding_problems")){
     	   setContentView(R.layout.activity_no_feeding_problems);
     	   getActionBar().setSubtitle("PNC Diagnostic: Feeding Problems > No Feeding Problems");
     	   json=new JSONObject();
     	   try {
     		   json.put("page", "PNC Diagnostic: Feeding Problems > No Feeding Problems");
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
  	  			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,HomeCareForInfantActivity.class);
  	  			intent.putExtra("value", "low_birth_weight");
  	  			startActivity(intent);
  	  		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
  	  		}
  	  		   
  	  	   });
  	  	 TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
  	  	click_here_too.setOnClickListener(new OnClickListener(){

  	  		@Override
  	  		public void onClick(View v) {
  	  			Intent intent=new Intent(TakeActionFeedingProblemsActivity.this,PostnatalCareCounsellingTopicsActivity.class);
  	  			intent.putExtra("value", "feeding_frequency");
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
