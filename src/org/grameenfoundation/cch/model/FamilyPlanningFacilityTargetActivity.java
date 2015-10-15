package org.grameenfoundation.cch.model;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.FacilityTargetAdapter;
import org.grameenfoundation.cch.activity.FacilityTargetBulkUpdateActivity;
import org.grameenfoundation.cch.activity.ReminderActivity;
import org.grameenfoundation.cch.activity.UpdateFacilityTargetsActivity;
import org.grameenfoundation.cch.model.FacilityTargets;
import org.joda.time.DateTime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class FamilyPlanningFacilityTargetActivity extends Activity {

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
	private String type;
	
	 public FamilyPlanningFacilityTargetActivity(){

	 }
	 @Override
		public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_facility_targets_view);
		 	context=FamilyPlanningFacilityTargetActivity.this;
			getActionBar().setTitle("Planner");
		 	getActionBar().setSubtitle("Maternal Health Facility Targets");
		    db=new DbHelper(context);
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
		    facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),type);
		    adapter=new FacilityTargetAdapter(context,facilityTargets);
		    listView.setAdapter(adapter);
		    
		    dailyFilter.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					  if(dailyFilter.isChecked()){
						  adapter.getFilter().filter(dailyFilter.getText().toString());
					  }else {
						  	facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),type);
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
						  	facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),type);
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
						  	facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),type);
							adapter.updateAdapter(facilityTargets);
							listView.setAdapter(adapter);
					  }
				}
			});
		    button_reminders=(Button) findViewById(R.id.button_reminders);
		    button_reminders.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,ReminderActivity.class);
					intent.putExtra("type", type);
					startActivity(intent);
				}
			});
		    button_update=(Button) findViewById(R.id.button_update);
		    button_update.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,FacilityTargetBulkUpdateActivity.class);
					intent.putExtra("category", type);
					startActivity(intent);
				}
			});
		    listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String[] selected_items=adapter.getItem(position);
					Intent intent=new Intent (context,UpdateFacilityTargetsActivity.class);
					intent.putExtra("id", id);
					intent.putExtra("category", type);
					intent.putExtra("target_id", selected_items[12]);
					if(selected_items[13].equals("")){
						intent.putExtra("target_type", selected_items[1]);
					}else{
						intent.putExtra("target_type", selected_items[13]);
					}
					intent.putExtra("target_number", selected_items[2]);
					intent.putExtra("target_achieved",selected_items[3]);
					intent.putExtra("target_remaining", selected_items[4]);
					intent.putExtra("start", selected_items[5]);
					intent.putExtra("due", selected_items[6]);
					intent.putExtra("reminder", selected_items[7]);
					intent.putExtra("status", selected_items[8]);
					intent.putExtra("last_updated", selected_items[9]);
					intent.putExtra("group", selected_items[10]);
					intent.putExtra("month", selected_items[11]);
					startActivity(intent);
				}
			});
	 }
	 /*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 rootView=inflater.inflate(R.layout.activity_facility_targets_view,null,false);
		 context=getActivity();
	    db=new DbHelper(context);
	    currentDate=new DateTime();
	    listView=(ListView) rootView.findViewById(R.id.listView_targets);
	    facilityTargets=new ArrayList<FacilityTargets>();
	    dailyFilter=(CheckBox) rootView.findViewById(R.id.checkBox_daily);
	    weeklyFilter=(CheckBox) rootView.findViewById(R.id.checkBox_weekly);
	    monthly=(CheckBox) rootView.findViewById(R.id.checkBox_monthly);
	    facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),"Family Planning");
	    adapter=new FacilityTargetAdapter(context,facilityTargets);
	    listView.setAdapter(adapter);
	    
	    dailyFilter.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				  if(dailyFilter.isChecked()){
					  adapter.getFilter().filter(dailyFilter.getText().toString());
				  }else {
					  	facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),"Family Planning");
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
					  	facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),"Family Planning");
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
					  	facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),"Family Planning");
						adapter.updateAdapter(facilityTargets);
						listView.setAdapter(adapter);
				  }
			}
		});
	    button_reminders=(Button) rootView.findViewById(R.id.button_reminders);
	    button_reminders.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context,ReminderActivity.class);
				startActivity(intent);
			}
		});
	    button_update=(Button) rootView.findViewById(R.id.button_update);
	    button_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context,FacilityTargetBulkUpdateActivity.class);
				startActivity(intent);
			}
		});
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String[] selected_items=adapter.getItem(position);
				Intent intent=new Intent (context,UpdateFacilityTargetsActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("target_id", selected_items[12]);
				intent.putExtra("target_type", selected_items[1]);
				intent.putExtra("target_number", selected_items[2]);
				intent.putExtra("target_achieved",selected_items[3]);
				intent.putExtra("target_remaining", selected_items[4]);
				intent.putExtra("start", selected_items[5]);
				intent.putExtra("due", selected_items[6]);
				intent.putExtra("reminder", selected_items[7]);
				intent.putExtra("status", selected_items[8]);
				intent.putExtra("last_updated", selected_items[9]);
				intent.putExtra("group", selected_items[10]);
				intent.putExtra("month", selected_items[11]);
				startActivity(intent);
			}
		});
	    return rootView;
		 
	}*/
	@Override
	public void onResume(){
		super.onResume();
		facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),type);
		adapter.updateAdapter(facilityTargets);
		listView.setAdapter(adapter);
	}
}
