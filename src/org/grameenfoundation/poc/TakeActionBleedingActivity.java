package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionBleedingActivity extends Activity {

	private String take_action_category;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic");
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("heavy")){
	    setContentView(R.layout.activity_heavy_bleeding);
        }else if(take_action_category.equals("light")){
        setContentView(R.layout.activity_light_bleeding);
        }
	}

}
