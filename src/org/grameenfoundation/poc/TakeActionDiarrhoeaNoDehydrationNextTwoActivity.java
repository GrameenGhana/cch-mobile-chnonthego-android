package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionDiarrhoeaNoDehydrationNextTwoActivity extends BaseActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic");
	    mContext = TakeActionDiarrhoeaNoDehydrationNextTwoActivity.this;
	    	setContentView(R.layout.activity_diarrhoea_no_dehydration_next_two);
	}

}
