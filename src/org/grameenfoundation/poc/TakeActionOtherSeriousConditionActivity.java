package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionOtherSeriousConditionActivity extends Activity {

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
        if(take_action_category.equals("bleeding")){
	    setContentView(R.layout.activity_bleeding_umbilical_cord);
        }else if(take_action_category.equals("soft swelling")){
        setContentView(R.layout.activity_soft_swelling);
        }else if(take_action_category.equals("open tissue")){
        setContentView(R.layout.activity_open_tissue);
        }else if(take_action_category.equals("no urine")){
            setContentView(R.layout.activity_no_urine);
        }
	}

}
