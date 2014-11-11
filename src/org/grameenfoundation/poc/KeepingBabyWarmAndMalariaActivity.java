package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class KeepingBabyWarmAndMalariaActivity extends Activity {

	private String category;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling");
	   // listView_takeAction=(ListView) findViewById(R.id.listView_takeAction);
	  //  textView_takeAction=(TextView) findViewById(R.id.textView_takeActionCategory);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
         category= extras.getString("value");
        }
        if(category.equals("keeping_baby_warm")){
        	   setContentView(R.layout.activity_keeping_baby_warm);
        }else  if(category.equals("malaria")){
     	   setContentView(R.layout.activity_malaria_prevention_counselling);
        }else  if(category.equals("psychosocial_support")){
      	   setContentView(R.layout.activity_psychosocial_support);
    	}else  if(category.equals("rest_activity")){
       	   setContentView(R.layout.activity_rest_and_activity);
     	}else  if(category.equals("sexual_relations")){
        	   setContentView(R.layout.activity_sexual_relationships);
      	}else  if(category.equals("tt_immunization")){
     	   setContentView(R.layout.activity_tt_immunization_schedule);
      	}else  if(category.equals("bloody_diarrhoea")){
  	   setContentView(R.layout.activity_treating_bloody_diarrhoea);
	}
	}
}
