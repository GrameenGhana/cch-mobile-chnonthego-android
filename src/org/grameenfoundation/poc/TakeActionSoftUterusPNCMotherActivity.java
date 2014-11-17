package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionSoftUterusPNCMotherActivity extends Activity {
	
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private String take_action_category; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Soft Uterus");
	    dbh=new DbHelper(TakeActionSoftUterusPNCMotherActivity.this);
	    start_time=System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if(take_action_category.equals("one_yes")){
        	   setContentView(R.layout.activity_soft_uterus_one_yes);
        }else if(take_action_category.equals("one_no")){
        	 setContentView(R.layout.activity_soft_uterus_one_no);
        }else if(take_action_category.equals("two_yes")){
       	 setContentView(R.layout.activity_soft_uterus_two_yes);
       }else if(take_action_category.equals("two_no")){
         	 setContentView(R.layout.activity_soft_uterus_two_no);
         }
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Soft Uterus", start_time.toString(), end_time.toString());
		finish();
	}
}
