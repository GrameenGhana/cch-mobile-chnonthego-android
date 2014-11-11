package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class NutritionCounsellingNextThreeActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_nutrition_counselling_next_three_activity);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling");
	}

}
