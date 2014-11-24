package org.digitalcampus.oppia.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.NewEventPlannerActivity.EventsActivity.DatePickerFragment;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.EventBaseAdapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EventTargetsDetailActivity extends FragmentActivity {

	private TextView textView_dueDate;
	private TextView textView_period;
	private TextView textView_name;
	private ImageView imageView_status;
	private String event_name;
	private String event_number;
	private String event_period;
	private static String due_date;
	private String status;
	private long event_id;
	private TextView textView_number;
	private DbHelper db;
	private String due_date_extra;
	private static TextView dueDateValue;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_event_target_detail);
	    db=new DbHelper(EventTargetsDetailActivity.this);
	    getActionBar().setTitle("Event Planner");
	    getActionBar().setSubtitle("Target Details");
	    textView_name=(TextView) findViewById(R.id.textView_name);
	    textView_period=(TextView) findViewById(R.id.textView_period);
	    textView_number=(TextView) findViewById(R.id.textView_number);
	    textView_dueDate=(TextView) findViewById(R.id.textView_dueDate);
	    imageView_status=(ImageView) findViewById(R.id.imageView_status);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
        	event_name=extras.getString("event_name");
			event_number=extras.getString("event_number");
			event_period=extras.getString("event_period");
			due_date_extra=extras.getString("due_date");
			status=extras.getString("status");
			event_id=extras.getLong("event_id");
        }
        
        textView_name.setText(event_name);
        textView_period.setText(event_period);
        textView_dueDate.setText(due_date_extra);
        textView_number.setText(event_number);
        if(status.equalsIgnoreCase("updated")){
        	imageView_status.setImageResource(R.drawable.ic_achieved);
        }else if(status.equalsIgnoreCase("new_record")){
        	imageView_status.setImageResource(R.drawable.ic_loading);
        }else if(status.equalsIgnoreCase("not_achieved")){
        	imageView_status.setImageResource(R.drawable.ic_not_achieved);
        }
        
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.update_menu_icons, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	Intent goHome = new Intent(Intent.ACTION_MAIN);
	 	          goHome.setClass(EventTargetsDetailActivity.this, MainScreenActivity.class);
	 	          goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(goHome);
	 	          finish();
	 	         
	            return true;
	        case R.id.action_edit:
	        	final Dialog dialog = new Dialog(EventTargetsDetailActivity.this);
				dialog.setContentView(R.layout.event_set_dialog);
				dialog.setTitle("Edit Event Target");
				final EditText editText_eventNumber=(EditText) dialog.findViewById(R.id.editText_dialogEventPeriodNumber);
				String[] items={"Daily","Weekly","Monthly","Annually","Quarterly","Mid-year"};
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(EventTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
				final Spinner spinner_event_name=(Spinner) dialog.findViewById(R.id.spinner_eventName);
				final Spinner spinner_eventPeriod=(Spinner) dialog.findViewById(R.id.spinner_dialogEventPeriod);
				spinner_eventPeriod.setAdapter(adapter);
				int spinner_position_period=adapter.getPosition(event_period);
				String[] items_names={"ANC Static","ANC Outreach","CWC Static","CWC Outreach",
						"PNC Clinic","Routine Home visit","Special Home visit",
						"Family Planning","Health Talk","CMAM Clinic","School Health",
						"Adolescent Health","Mop-up Activity/Event","Community Durbar",
						"National Activity/Event","Staff meetings/durbars","Workshops","Leave/Excuse Duty",
						"Personal","Other"};
				ArrayAdapter<String> adapter2=new ArrayAdapter<String>(EventTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items_names);
				spinner_event_name.setAdapter(adapter2);
				int spinner_position=adapter2.getPosition(event_name);
				spinner_event_name.setSelection(spinner_position);
				editText_eventNumber.setText(event_number);
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogSetEVent);
				dialogButton.setText("Save");
				Button datepickerDialog=(Button) dialog.findViewById(R.id.button_setDateDialog);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment();
						    newFragment.show(getSupportFragmentManager(), "datePicker");
						
					}
					
				});
				dueDateValue=(TextView) dialog.findViewById(R.id.textView_dueDateValue);
				Button cancel=(Button) dialog.findViewById(R.id.button_cancel);
				cancel.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
					dialog.dismiss();
						
					}
					
				});
				dialogButton.setOnClickListener(new OnClickListener() {
				
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						
						String event_name=spinner_event_name.getSelectedItem().toString();
						String event_number=editText_eventNumber.getText().toString();
						String event_period=spinner_eventPeriod.getSelectedItem().toString();
					    if(db.editEventCategory(event_name, event_number, event_period,due_date, event_id)==true){
					    	
					    	 Toast.makeText(EventTargetsDetailActivity.this, "Event target edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(EventTargetsDetailActivity.this, "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
				
	 				dialog.show();
	 	         
	            return true;
	        case R.id.action_delete:
	        	if(db.deleteEventCategory(event_id)==true){
	        		Intent intent2 = new Intent(Intent.ACTION_MAIN);
		 	          intent2.setClass(EventTargetsDetailActivity.this, NewEventPlannerActivity.class);
		 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 	          startActivity(intent2);
		 	          finish();	
	        	}
	        	return true;
	        case R.id.action_update:
	        	Intent intent3=new Intent(EventTargetsDetailActivity.this,UpdateActivity.class);
	        	intent3.putExtra("id", event_id);
				intent3.putExtra("number",event_number);
				intent3.putExtra("name", event_name);
				intent3.putExtra("type", "event");
				intent3.putExtra("due_date", due_date_extra);
				intent3.putExtra("period", event_period);
	        	startActivity(intent3);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	public static class DatePickerFragment extends DialogFragment
		implements DatePickerDialog.OnDateSetListener {

@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
//Use the current date as the default date in the picker
final Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);

//Create a new instance of DatePickerDialog and return it
return new DatePickerDialog(getActivity(), this, year, month, day);
}

public void onDateSet(DatePicker view, int year, int month, int day) {
int month_value=month+1;
due_date=day+"-"+month_value+"-"+year;
dueDateValue.setText(due_date);
}
}
}
