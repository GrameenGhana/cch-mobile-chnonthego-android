package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

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
        	TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
  		   click_here.setOnClickListener(new OnClickListener(){

  			@Override
  			public void onClick(View v) {
  				Intent intent=new Intent(TakeActionHIVInfectionActivity.this,InfantFeedingNextActivity.class);
  				intent.putExtra("value", "not_taking_arv");
  				startActivity(intent);
  				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
  			}
  			   
  		   });
        }else if(category.equalsIgnoreCase("positive_yes")){
        	setContentView(R.layout.activity_hiv_positive_yes);
        	TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
 		   click_here.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				Intent intent=new Intent(TakeActionHIVInfectionActivity.this,InfantFeedingNextActivity.class);
 				intent.putExtra("value", "taking_arv");
 				startActivity(intent);
 				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
 			}
 			   
 		   });
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: HIV Infection", start_time.toString(), end_time.toString());
		finish();
	}
}
