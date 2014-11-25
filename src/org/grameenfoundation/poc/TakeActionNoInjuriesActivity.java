package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionNoInjuriesActivity extends BaseActivity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Other Serious Conditions");
	    dbh=new DbHelper(TakeActionNoInjuriesActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("club foot")){
	    setContentView(R.layout.activity_club_foot);
        }else if(take_action_category.equals("cleft palate")){
        setContentView(R.layout.activity_cleft_palate);
        }else if(take_action_category.equals("unusual appearance")){
        setContentView(R.layout.activity_unusual_appearance);
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Other Serious Conditions", start_time.toString(), end_time.toString());
		finish();
	}
}
