package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class KeepingBabyWarmAndMalariaActivity extends BaseActivity {

	private String category;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	String data;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	mContext= KeepingBabyWarmAndMalariaActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling");
	    dbh=new DbHelper(KeepingBabyWarmAndMalariaActivity.this);
	    start_time=System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
         category= extras.getString("value");
        }
        if(category.equals("keeping_baby_warm")){
        	  setContentView(R.layout.activity_keeping_baby_warm);
        	  getActionBar().setSubtitle("PNC Counselling: Keeping Baby Warm");
        	  data="PNC Counselling: Keeping Baby Warm";
        }else  if(category.equals("malaria")){
     	   	  setContentView(R.layout.activity_malaria_prevention_counselling);
     	   	  getActionBar().setSubtitle("PNC Counselling: Malaria Prevention");
     	   	  data="PNC Counselling: Malaria Prevention";
        }else  if(category.equals("psychosocial_support")){
      	   	  setContentView(R.layout.activity_psychosocial_support);
      	      getActionBar().setSubtitle("PNC Counselling: Psychosocial Support");
      	      data="PNC Counselling: Psychosocial Support";
    	}else  if(category.equals("rest_activity")){
       	       setContentView(R.layout.activity_rest_and_activity);
       	       getActionBar().setSubtitle("PNC Counselling: Rest & Activity");
       	       data="PNC Counselling: Rest & Activity";
     	}else  if(category.equals("sexual_relations")){
     		   setContentView(R.layout.activity_sexual_relationships);
        	   getActionBar().setSubtitle("PNC Counselling: Sexual Relations");
               data="PNC Counselling: Sexual Relations";
      	}else  if(category.equals("tt_immunization")){
     	       setContentView(R.layout.activity_tt_immunization_schedule);
     	       getActionBar().setSubtitle("PNC Counselling: TT Immunisation");
     	       data="PNC Counselling: TT Immunisation";
      	}else  if(category.equals("bloody_diarrhoea")){
  	           setContentView(R.layout.activity_treating_bloody_diarrhoea);
  	            getActionBar().setSubtitle("PNC Counselling: Treating Bloody Diarrhoea");
 	           data="PNC Counselling: Treating Bloody Diarrhoea";
	}
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", data, start_time.toString(), end_time.toString());
		finish();
	}
}
