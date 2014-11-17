package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionNewbornEmergency extends BaseActivity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=TakeActionNewbornEmergency.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Newborn Emergencies");
	    dbh=new DbHelper(TakeActionNewbornEmergency.this);
	    start_time=System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("take_action");
        }
        if(take_action_category.equals("difficulty")){
	    setContentView(R.layout.activity_difficulty_breathing);
        }else if(take_action_category.equals("cyanosis")){
        setContentView(R.layout.activity_cyanosis);
        }else if(take_action_category.equals("convulsion")){
        setContentView(R.layout.activity_convulsion);
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Newborn Emergencies", start_time.toString(), end_time.toString());
		finish();
	}
}
