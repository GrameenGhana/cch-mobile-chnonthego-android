package org.grameenfoundation.cch.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.FacilityTargetAdapter;
import org.grameenfoundation.cch.model.FacilityTargets;
import org.grameenfoundation.cch.model.FamilyPlanningFacilityTargetActivity;
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

public class OtherFacilityTargetActivity extends Activity {

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
	
	 public OtherFacilityTargetActivity(){

	 }
	 @Override
		public void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
		 	setContentView(R.layout.activity_facility_targets_view);
		 	getActionBar().setTitle("Planner");
		 	getActionBar().setSubtitle("Other Facility Targets");
		 	context=OtherFacilityTargetActivity.this;
		    db=new DbHelper(context);
		    currentDate=new DateTime();
		    listView=(ListView) findViewById(R.id.listView_targets);
		    facilityTargets=new ArrayList<FacilityTargets>();
		    dailyFilter=(CheckBox) findViewById(R.id.checkBox_daily);
		    weeklyFilter=(CheckBox) findViewById(R.id.checkBox_weekly);
		    monthly=(CheckBox) findViewById(R.id.checkBox_monthly);
		    facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),"Home Visit");
		    adapter=new FacilityTargetAdapter(context,facilityTargets);
		    listView.setAdapter(adapter);
		    
		    dailyFilter.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					  if(dailyFilter.isChecked()){
						  adapter.getFilter().filter(dailyFilter.getText().toString());
					  }else {
						  	facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),"Home Visit");
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
						  	facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),"Home Visit");
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
						  	facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),"Home Visit");
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
					intent.putExtra("type", "50yrs-60yrs");
					startActivity(intent);
				}
			});
		    button_update=(Button) findViewById(R.id.button_update);
		    button_update.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,FacilityTargetBulkUpdateActivity.class);
					intent.putExtra("category", "Other");
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
					intent.putExtra("category", "Antigens");
					intent.putExtra("target_id", selected_items[12]);
					if(selected_items[13].equals("")){
						intent.putExtra("target_type", selected_items[1]);
					}else{
						intent.putExtra("target_type", selected_items[13]);//if target type is generic, use target detail
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
					intent.putExtra("target_group", selected_items[15]);//target_name ie Child Health etc
					intent.putExtra("month", selected_items[11]);
					intent.putExtra("target_detail", selected_items[13]);//actual target detail
					intent.putExtra("target_overall", selected_items[14]);
					//startActivity(intent);
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
		facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"),"Home Visit");
		adapter.updateAdapter(facilityTargets);
		listView.setAdapter(adapter);
	}
}
