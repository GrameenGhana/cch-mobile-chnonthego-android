package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionDiarrhoeaActivity extends Activity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Diarrhoea");
	    dbh=new DbHelper(TakeActionDiarrhoeaActivity.this);
	    start_time=System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if(take_action_category.equals("severe_diarrhoea")){
        	   setContentView(R.layout.activity_persistent_diarrhoea);
        }else if(take_action_category.equals("blood_stool")){
     	   setContentView(R.layout.activity_blood_in_stool);
     }else if(take_action_category.equals("no_diarrhoea")){
   	   setContentView(R.layout.activity_no_diarrhoea);
   }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Diarrhoea", start_time.toString(), end_time.toString());
		finish();
	}
}
