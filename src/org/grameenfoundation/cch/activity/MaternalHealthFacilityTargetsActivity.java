package org.grameenfoundation.cch.activity;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.cch.model.AgeGroupsFacilityTargetsActivity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost;

public class MaternalHealthFacilityTargetsActivity extends Activity {

	private TabHost tabHost;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facility_age_groups);
		getActionBar().setTitle("Planner");
	 	getActionBar().setSubtitle("Maternal Health Facility Targets");
		 tabHost = (TabHost) findViewById(R.id.tabhost);
		 LocalActivityManager mLocalActivityManager = new LocalActivityManager(MaternalHealthFacilityTargetsActivity.this,true);
		 mLocalActivityManager.dispatchCreate(savedInstanceState); // state will be bundle your activity state which you get in onCreate
		 tabHost.setup(mLocalActivityManager);
		 FragmentTabHost.TabSpec spec = tabHost.newTabSpec("tag");
		 spec.setIndicator("Expected Pregnancies");
		 /*
		 spec.setContent(new TabHost.TabContentFactory() {

	            @Override
	            public View createTabContent(String tag) {
	                // TODO Auto-generated method stub
	                return (new AnalogClock(getActivity()));
	            }
	        });*/
	       spec.setContent(new Intent(MaternalHealthFacilityTargetsActivity.this,AntigensFacilityTargetActivity.class).putExtra("type", "Expected Pregnancies"));
	            
	        tabHost.addTab(spec);
	        spec = tabHost.newTabSpec("tag1");
	        spec.setIndicator("Family Planning");/*
	        spec.setContent(new TabHost.TabContentFactory() {

	            @Override
	            public View createTabContent(String tag) {
	                // TODO Auto-generated method stub
	                return (new AnalogClock(getActivity()));
	            }
	        });*/
	        spec.setContent(new Intent(MaternalHealthFacilityTargetsActivity.this,AntigensFacilityTargetActivity.class).putExtra("type", "Family Planning"));
	        tabHost.addTab(spec);
	}
	@Override
	public void onResume(){
		super.onResume();				
		//facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),"Antigens");
		//adapter.updateAdapter(facilityTargets);
		//listView.setAdapter(adapter);
	}
}
