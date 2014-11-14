package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionSeverDiseasesActivity extends Activity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private String data;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Very Severe Disease");
	    dbh=new DbHelper(TakeActionSeverDiseasesActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("difficulty")){
	    setContentView(R.layout.activity_difficulty_breathing);
	   
        }else if(take_action_category.equals("cyanosis")){
        setContentView(R.layout.activity_cyanosis);
        }else if(take_action_category.equals("convulsion")){
        setContentView(R.layout.activity_convulsion);
        }else if(take_action_category.equals("fever")){
        	setContentView(R.layout.activity_fever_take_action);	
        }else if(take_action_category.equals("feeding")){
        	 setContentView(R.layout.activity_feeding_difficulty);	
        }else if(take_action_category.equals("umbilicus")){
        	setContentView(R.layout.activity_umbilicus_infection);	
        }else if(take_action_category.equals("eye")){
        	setContentView(R.layout.activity_eye_infection);	
        }else if(take_action_category.equals("no symptoms")){
        	setContentView(R.layout.activity_no_symptoms);	
        }else if(take_action_category.equals("hypothermia")){
        	setContentView(R.layout.activity_hypothermia);	
        }else if(take_action_category.equals("low")){
        	setContentView(R.layout.activity_low_temperature);
        }else if(take_action_category.equals("fast_breathing")){
        	setContentView(R.layout.activity_fast_breathing);
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Very Severe Disease", start_time.toString(), end_time.toString());
		finish();
	}
}
