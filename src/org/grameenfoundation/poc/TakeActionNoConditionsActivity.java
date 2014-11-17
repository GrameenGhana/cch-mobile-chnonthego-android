package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionNoConditionsActivity extends BaseActivity {

	private String take_action_category;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext= TakeActionNoConditionsActivity.this;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			take_action_category = extras.getString("category");
		}
		if (take_action_category.equals("asymmetrical")) {
			setContentView(R.layout.activity_asymmetrical_limb);
		} else if (take_action_category.equals("firm swelling")) {
			setContentView(R.layout.activity_swelling_on_head);
		}
	}

}
