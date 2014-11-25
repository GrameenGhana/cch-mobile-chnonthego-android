package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionHIVInfectionActivity extends BaseActivity {

	private String category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: HIV Infection");
	    dbh=new DbHelper(TakeActionHIVInfectionActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          category= extras.getString("value");
        }
        if(category.equalsIgnoreCase("negative_no")){
        	setContentView(R.layout.activity_hiv_negative_no);
        }else if(category.equalsIgnoreCase("negative_yes")){
        	setContentView(R.layout.activity_hiv_negative_yes);
        }else if(category.equalsIgnoreCase("positive_no")){
        	setContentView(R.layout.activity_hiv_positive_no);
        }else if(category.equalsIgnoreCase("positive_yes")){
        	setContentView(R.layout.activity_hiv_positive_yes);
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: HIV Infection", start_time.toString(), end_time.toString());
		finish();
	}
}
