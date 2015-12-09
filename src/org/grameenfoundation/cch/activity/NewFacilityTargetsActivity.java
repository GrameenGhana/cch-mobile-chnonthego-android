package org.grameenfoundation.cch.activity;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.cch.model.AgeGroupsFacilityTargetsActivity;
import org.grameenfoundation.cch.model.SchoolHealthFacilityTargetActivity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost;

public class NewFacilityTargetsActivity extends Activity {

	private TabHost tabHost;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_facility_age_groups);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Child Health Facility Targets");
	    tabHost = (TabHost) findViewById(R.id.tabhost);
	    LocalActivityManager mLocalActivityManager = new LocalActivityManager(NewFacilityTargetsActivity.this,true);
		 mLocalActivityManager.dispatchCreate(savedInstanceState); // state will be bundle your activity state which you get in onCreate
		 tabHost.setup(mLocalActivityManager);
		 FragmentTabHost.TabSpec spec = tabHost.newTabSpec("tag");
		 spec.setIndicator("Antigens");
		   spec.setContent(new Intent(NewFacilityTargetsActivity.this,AgeGroupsFacilityTargetsActivity.class));//.putExtra("type", "0-11 months"));
		   tabHost.addTab(spec);
		   
		   spec = tabHost.newTabSpec("tag1");
		 spec.setIndicator("School Health");
		spec.setContent(new Intent(NewFacilityTargetsActivity.this,SchoolHealthFacilityTargetActivity.class));//.putExtra("type", "0-11 months"));
		  tabHost.addTab(spec);
	}

}
