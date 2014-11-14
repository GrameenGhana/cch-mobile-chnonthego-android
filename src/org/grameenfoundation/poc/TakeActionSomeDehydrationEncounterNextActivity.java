package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionSomeDehydrationEncounterNextActivity extends Activity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Diarrhoea");
	    dbh=new DbHelper(TakeActionSomeDehydrationEncounterNextActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equalsIgnoreCase("chps_one_next")){
        	setContentView(R.layout.activity_chps_one_next);
        } else if(take_action_category.equalsIgnoreCase("chps_two_next")){
        	setContentView(R.layout.activity_chps_two_next);
        }
	  
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Diarrhoea" , start_time.toString(), end_time.toString());
		finish();
	}
}
