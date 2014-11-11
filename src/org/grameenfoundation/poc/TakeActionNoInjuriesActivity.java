package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionNoInjuriesActivity extends Activity {

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
        if(take_action_category.equals("club foot")){
	    setContentView(R.layout.activity_club_foot);
        }else if(take_action_category.equals("cleft palate")){
        setContentView(R.layout.activity_cleft_palate);
        }else if(take_action_category.equals("unusual appearance")){
        setContentView(R.layout.activity_unusual_appearance);
        }
	}

}
