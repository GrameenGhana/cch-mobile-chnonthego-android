package org.grameenfoundation.cch.model;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.FacilityTargetAdapter;
import org.grameenfoundation.cch.activity.AntigensFacilityTargetActivity;
import org.grameenfoundation.cch.activity.FacilityTargetBulkUpdateActivity;
import org.grameenfoundation.cch.activity.NewFacilityTargetsActivity;
import org.grameenfoundation.cch.activity.ReminderActivity;
import org.grameenfoundation.cch.activity.UpdateFacilityTargetsActivity;
import org.joda.time.DateTime;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

public class AgeGroupsFacilityTargetsActivity extends Activity {

	private Context context;
	private DbHelper db;
	private DateTime currentDate;
	private ArrayList<FacilityTargets> facilityTargets;
	private FacilityTargetAdapter adapter;
	private ListView listView;
	private Button button_reminders;
	private CheckBox dailyFilter;
	private CheckBox weeklyFilter;
	private CheckBox monthly;
	private Button button_update;
	private View rootView;
	private TabHost tabHost;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 //rootView=inflater.inflate(R.layout.activity_facility_age_groups,container,false);
		setContentView(R.layout.activity_facility_age_groups);
		 tabHost = (TabHost) findViewById(R.id.tabhost);
		 LocalActivityManager mLocalActivityManager = new LocalActivityManager(AgeGroupsFacilityTargetsActivity.this,true);
		 mLocalActivityManager.dispatchCreate(savedInstanceState); // state will be bundle your activity state which you get in onCreate
		 tabHost.setup(mLocalActivityManager);
		 FragmentTabHost.TabSpec spec = tabHost.newTabSpec("tag");
		 spec.setIndicator("0-11 months");
		 /*
		 spec.setContent(new TabHost.TabContentFactory() {

	            @Override
	            public View createTabContent(String tag) {
	                // TODO Auto-generated method stub
	                return (new AnalogClock(getActivity()));
	            }
	        });*/
		 /* spec.setContent(new Intent(AgeGroupsFacilityTargetsActivity.this,AntigensFacilityTargetActivity.class).putExtra("type", "0-11 months"));
	            
	        tabHost.addTab(spec);
	        spec = tabHost.newTabSpec("tag1");
	        spec.setIndicator("6-11	months");
	        spec.setContent(new TabHost.TabContentFactory() {

	            @Override
	            public View createTabContent(String tag) {
	                // TODO Auto-generated method stub
	                return (new AnalogClock(getActivity()));
	            }
	        });*/
		 /*spec.setContent(new Intent(AgeGroupsFacilityTargetsActivity.this,AntigensFacilityTargetActivity.class).putExtra("type", "6-11 months"));
	        tabHost.addTab(spec);
	        spec = tabHost.newTabSpec("tag2");
	        spec.setIndicator("0-23 months");
	       
	        spec.setContent(new TabHost.TabContentFactory() {

	            @Override
	            public View createTabContent(String tag) {
	                // TODO Auto-generated method stub
	                return (new AnalogClock(getActivity()));
	            }
	        });*/
	        spec.setContent(new Intent(AgeGroupsFacilityTargetsActivity.this,AntigensFacilityTargetActivity.class).putExtra("type", "0-11 months"));
	        tabHost.addTab(spec);
	        spec = tabHost.newTabSpec("tag3");
	        spec.setIndicator("12-23 months");/*
	        spec.setContent(new TabHost.TabContentFactory() {

	            @Override
	            public View createTabContent(String tag) {
	                // TODO Auto-generated method stub
	                return (new AnalogClock(getActivity()));
	            }
	        });*/
	       spec.setContent(new Intent(AgeGroupsFacilityTargetsActivity.this,AntigensFacilityTargetActivity.class).putExtra("type", "12-23 months"));
	        tabHost.addTab(spec);
	        spec = tabHost.newTabSpec("tag4");
	        spec.setIndicator("24-59 months");
	        /*
	        spec.setContent(new TabHost.TabContentFactory() {

	            @Override
	            public View createTabContent(String tag) {
	                // TODO Auto-generated method stub
	                return (new AnalogClock(getActivity()));
	            }
	        });*/
	       spec.setContent(new Intent(AgeGroupsFacilityTargetsActivity.this,AntigensFacilityTargetActivity.class).putExtra("type", "24-59 months"));
	        tabHost.addTab(spec);
	}

}
