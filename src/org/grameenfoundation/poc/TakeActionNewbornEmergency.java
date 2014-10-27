package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionNewbornEmergency extends Activity {

	private String take_action_category;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("take_action");
        }
        if(take_action_category.equals("difficulty")){
	    setContentView(R.layout.activity_difficulty_breathing);
        }else if(take_action_category.equals("cyanosis")){
        setContentView(R.layout.activity_cyanosis);
        }else if(take_action_category.equals("convulsion")){
        setContentView(R.layout.activity_convulsion);
        }
	}

}
