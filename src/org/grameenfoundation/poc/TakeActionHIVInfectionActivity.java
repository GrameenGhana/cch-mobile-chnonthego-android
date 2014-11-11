package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionHIVInfectionActivity extends Activity {

	private String category;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic");
        if (extras != null) {
          category= extras.getString("value");
        }
        if(category.equalsIgnoreCase("negative_no")){
        	setContentView(R.layout.activity_hiv_negative_no);
        }else if(category.equalsIgnoreCase("negative_yes")){
        	setContentView(R.layout.activity_hiv_negative_yes);
        }else if(category.equalsIgnoreCase("positive_no")){
        	setContentView(R.layout.activity_hiv_positive_no);
        }else if(category.equalsIgnoreCase("positive_yes")){
        	setContentView(R.layout.activity_hiv_positive_yes);
        }
	   
	   
	}

}
