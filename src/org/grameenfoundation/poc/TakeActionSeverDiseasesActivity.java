package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionSeverDiseasesActivity extends BaseActivity {

	private String take_action_category;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=  TakeActionSeverDiseasesActivity.this;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			take_action_category = extras.getString("category");
		}
		if (take_action_category.equals("difficulty")) {
			setContentView(R.layout.activity_difficulty_breathing);
		} else if (take_action_category.equals("cyanosis")) {
			setContentView(R.layout.activity_cyanosis);
		} else if (take_action_category.equals("convulsion")) {
			setContentView(R.layout.activity_convulsion);
		} else if (take_action_category.equals("fever")) {
			setContentView(R.layout.activity_fever_take_action);
		} else if (take_action_category.equals("feeding")) {
			setContentView(R.layout.activity_feeding_difficulty);
		} else if (take_action_category.equals("umbilicus")) {
			setContentView(R.layout.activity_umbilicus_infection);
		} else if (take_action_category.equals("eye")) {
			setContentView(R.layout.activity_eye_infection);
		} else if (take_action_category.equals("no symptoms")) {
			setContentView(R.layout.activity_no_symptoms);
		} else if (take_action_category.equals("hypothermia")) {
			setContentView(R.layout.activity_hypothermia);
		} else if (take_action_category.equals("low")) {
			setContentView(R.layout.activity_low_temperature);
		}
	}

}
