package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionJaundiceActivity extends Activity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Jaundice");
	    dbh=new DbHelper(TakeActionJaundiceActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("severe")){
	    setContentView(R.layout.activity_severe_jaundice);
        }else if(take_action_category.equals("jaundice")){
        setContentView(R.layout.activity_just_jaundice);
        }else if(take_action_category.equals("no jaundice")){
        setContentView(R.layout.activity_no_jaundice);
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Jaundice", start_time.toString(), end_time.toString());
		finish();
	}
}
