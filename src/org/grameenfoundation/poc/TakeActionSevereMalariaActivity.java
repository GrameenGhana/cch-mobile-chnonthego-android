package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionSevereMalariaActivity extends Activity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private String data;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	
	    dbh=new DbHelper(TakeActionSevereMalariaActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("severe_malaria")){
        	setContentView(R.layout.activity_severe_malaria);
            getActionBar().setSubtitle("ANC Diagnostic: Malaria");
            data="ANC Diagnostic: Malaria";
        }else if(take_action_category.equals("Ist Trimester")){
        setContentView(R.layout.activity_first_trimester_malaria);
        getActionBar().setSubtitle("ANC Diagnostic: Malaria");
        data="ANC Diagnostic: Malaria";
        }else if(take_action_category.equals("2nd Trimester")){
            setContentView(R.layout.activity_second_trimester_malaria);
            getActionBar().setSubtitle("ANC Diagnostic: Malaria");
            data="ANC Diagnostic: Malaria";
            }
        else if(take_action_category.equals("3rd Trimester")){
            setContentView(R.layout.activity_third_trimester_malaria);
            getActionBar().setSubtitle("ANC Diagnostic: Malaria");
            data="ANC Diagnostic: Malaria";
            }
	
        else if(take_action_category.equals("negative")){
            setContentView(R.layout.activity_malaria_test_negative);
            getActionBar().setSubtitle("ANC Diagnostic: Malaria");
            data="ANC Diagnostic: Malaria";
            }
        else if(take_action_category.equals("not done")){
            setContentView(R.layout.activity_malaria_test_not_done);
            getActionBar().setSubtitle("ANC Diagnostic: Malaria");
            data="ANC Diagnostic: Malaria";
            }
        else if(take_action_category.equals("severe anaemia")){
            setContentView(R.layout.activity_severe_anaemia_take_action);
            getActionBar().setSubtitle("ANC Diagnostic: Anaemia");
            data="ANC Diagnostic: Anaemia";
            }
        else if(take_action_category.equals("no anaemia")){
            setContentView(R.layout.activity_no_anaemia_take_action);
            getActionBar().setSubtitle("ANC Diagnostic: Anaemia");
            data="ANC Diagnostic: Anaemia";
            }
	
	   
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", data , start_time.toString(), end_time.toString());
		finish();
	}
}
