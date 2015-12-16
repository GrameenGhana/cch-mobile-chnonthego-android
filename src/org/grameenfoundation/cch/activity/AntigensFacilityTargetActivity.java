package org.grameenfoundation.cch.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.FacilityTargetAdapter;
import org.grameenfoundation.cch.model.FacilityTargets;
import org.joda.time.DateTime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AntigensFacilityTargetActivity extends Activity {

	private DbHelper db;
	private DateTime currentDate;
	private ListView listView;
	private ArrayList<FacilityTargets> facilityTargets;
	private CheckBox dailyFilter;
	private CheckBox weeklyFilter;
	private CheckBox monthly;
	private FacilityTargetAdapter adapter;
	private Button button_reminders;
	private Button button_update;
	private String type;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_facility_targets_view);
	    db=new DbHelper(AntigensFacilityTargetActivity.this);
	    Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	          type= extras.getString("type");
	        }
	    currentDate=new DateTime();
	    listView=(ListView) findViewById(R.id.listView_targets);
	    facilityTargets=new ArrayList<FacilityTargets>();
	    dailyFilter=(CheckBox) findViewById(R.id.checkBox_daily);
	    weeklyFilter=(CheckBox) findViewById(R.id.checkBox_weekly);
	    monthly=(CheckBox) findViewById(R.id.checkBox_monthly);
	   
	    facilityTargets=db.getTargetsForMonthViewAgeGroups(currentDate.toString("MMMM"),type);
	    
	    adapter=new FacilityTargetAdapter(AntigensFacilityTargetActivity.this,facilityTargets);
	    listView.setAdapter(adapter);
	    
	    dailyFilter.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				  if(dailyFilter.isChecked()){
					  adapter.getFilter().filter(dailyFilter.getText().toString());
				  }else {
					  facilityTargets=db.getTargetsForMonthViewAgeGroups(currentDate.toString("MMMM"),type);
						adapter.updateAdapter(facilityTargets);
						listView.setAdapter(adapter);
				  }
			}
		});
	    weeklyFilter.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				 if(weeklyFilter.isChecked()){
					  adapter.getFilter().filter(weeklyFilter.getText().toString());
				  }else {
					  facilityTargets=db.getTargetsForMonthViewAgeGroups(currentDate.toString("MMMM"),type);
						adapter.updateAdapter(facilityTargets);
						listView.setAdapter(adapter);
				  }
			}
		});
	    
	    monthly.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				 if(monthly.isChecked()){
					  adapter.getFilter().filter(monthly.getText().toString());
				  }else {
					  facilityTargets=db.getTargetsForMonthViewAgeGroups(currentDate.toString("MMMM"),type);
						adapter.updateAdapter(facilityTargets);
						listView.setAdapter(adapter);
				  }
			}
		});
	    button_reminders=(Button) findViewById(R.id.button_reminders);
	    button_reminders.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(AntigensFacilityTargetActivity.this,ReminderActivity.class);
				intent.putExtra("type", type);
				startActivity(intent);
			}
		});
	    button_update=(Button) findViewById(R.id.button_update);
	    button_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(AntigensFacilityTargetActivity.this,FacilityTargetBulkUpdateActivity.class);
				intent.putExtra("category", type);
				startActivity(intent);
			}
		});
	}
	@Override
	public void onResume(){
		super.onResume();				
		facilityTargets=db.getTargetsForMonthViewAgeGroups(currentDate.toString("MMMM"),type);
		adapter.updateAdapter(facilityTargets);
		listView.setAdapter(adapter);
	}
	@Override
	public void onStart(){
		super.onStart();				
		facilityTargets=db.getTargetsForMonthViewAgeGroups(currentDate.toString("MMMM"),type);
		adapter.updateAdapter(facilityTargets);
		listView.setAdapter(adapter);
	}
	@Override
	public void onRestart(){
		super.onRestart();				
		facilityTargets=db.getTargetsForMonthViewAgeGroups(currentDate.toString("MMMM"),type);
		adapter.updateAdapter(facilityTargets);
		listView.setAdapter(adapter);
	}
}
