package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class GoodAttachementActivity extends Activity {

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
        }
        if(take_action_category.equalsIgnoreCase("good_attachement")){
	    setContentView(R.layout.activity_good_attachement);
        }else if(take_action_category.equalsIgnoreCase("low_birth_weight")){
        setContentView(R.layout.activity_breastfeeding_low_birth_weight);
        }else if(take_action_category.equalsIgnoreCase("low_birth_weight_next")){
            setContentView(R.layout.activity_low_birth_weight_next);
            }
	}

}
