package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionFeedingProblemsActivity extends Activity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Feeding Problems");
	    dbh=new DbHelper(TakeActionFeedingProblemsActivity.this);
	    start_time=System.currentTimeMillis();
	   // listView_takeAction=(ListView) findViewById(R.id.listView_takeAction);
	  //  textView_takeAction=(TextView) findViewById(R.id.textView_takeActionCategory);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if(take_action_category.equals("not_attached")){
        	   setContentView(R.layout.activity_poor_attachement);
        }else if(take_action_category.equals("not_suckling")){
     	   setContentView(R.layout.activity_poor_attachement);
     }else if(take_action_category.equals("breastfeeding")){
   	   setContentView(R.layout.activity_breastfeeding);
     }else if(take_action_category.equals("receive_food")){
   	   setContentView(R.layout.activity_receive_other_foods);
     }else if(take_action_category.equals("not_breastfeeding")){
     	   setContentView(R.layout.activity_not_breastfeeding);
       }else if(take_action_category.equals("low_weight_for_age")){
     	   setContentView(R.layout.activity_low_birth_weight_for_age);
       }else if(take_action_category.equals("thrush")){
     	   setContentView(R.layout.activity_thrush);
       }else if(take_action_category.equals("no_feeding_problems")){
     	   setContentView(R.layout.activity_no_feeding_problems);
       }
	
	
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Feeding Problems", start_time.toString(), end_time.toString());
		finish();
	}
}
