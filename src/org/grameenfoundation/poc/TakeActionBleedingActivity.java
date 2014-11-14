package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionBleedingActivity extends Activity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic: Managing Danger Signs ");
	    dbh=new DbHelper(TakeActionBleedingActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("heavy")){
	    setContentView(R.layout.activity_heavy_bleeding);
        }else if(take_action_category.equals("light")){
        setContentView(R.layout.activity_light_bleeding);
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "ANC Diagnostic: Managing Danger Signs", start_time.toString(), end_time.toString());
		finish();
	}
}
