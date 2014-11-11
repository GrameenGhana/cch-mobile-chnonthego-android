package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionSomeDehydrationEncounterNextActivity extends Activity {

	private String take_action_category;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic");
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equalsIgnoreCase("chps_one_next")){
        	setContentView(R.layout.activity_chps_one_next);
        } else if(take_action_category.equalsIgnoreCase("chps_two_next")){
        	setContentView(R.layout.activity_chps_two_next);
        }
	  
	}

}
