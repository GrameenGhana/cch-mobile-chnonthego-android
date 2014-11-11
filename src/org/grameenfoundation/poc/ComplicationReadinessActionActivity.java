package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class ComplicationReadinessActionActivity extends Activity {

	private String take_action_category;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling");
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
          if(take_action_category.equalsIgnoreCase("danger_signs_mother")){
        	  setContentView(R.layout.activity_danger_signs_in_mother);
          }else  if(take_action_category.equalsIgnoreCase("danger_signs_newborn")){
        	  setContentView(R.layout.activity_newborn_danger_signs);
          }else if(take_action_category.equalsIgnoreCase("other_maternal")){
        	  setContentView(R.layout.activity_other_maternal_danger_signs);
          }else if(take_action_category.equalsIgnoreCase("other_newborn")){
        	  setContentView(R.layout.activity_other_newborn_danger_signs);
          }else if(take_action_category.equalsIgnoreCase("readiness_plan")){
        	  setContentView(R.layout.activity_readiness_plan);
          }
        }
	}

}
