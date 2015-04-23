package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Calendar;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.EventBaseAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.calendar.CalendarEvents.MyEvent;
import org.grameenfoundation.poc.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

public final class PlanEventActivity extends BaseActivity implements OnClickListener{

	private Context mContext;
	private Spinner spinner_eventName;
	private EditText editText_eventDescription;
	private AutoCompleteTextView editText_event_location;
	private Button button_addEvent;
	private static final String EVENT_PLANNER_ID = "Event Planner";
	private Button button_viewCalendar;
	private DbHelper dbh;
	Long startTime;
	 CalendarEvents c;
	String rrule;
	private DatePicker datePicker;
	private String mode;
	private LinearLayout linearLayout_buttonsOne;
	private LinearLayout linearLayout_buttonsTwo;
	private Button button_edit;
	private Button button_delete;
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> adapter2;
	private Long end_time;
	private RadioGroup radioGroup_personal;
	private JSONObject data;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_add_event);
	    mContext=PlanEventActivity.this;
	    dbh = new DbHelper(getApplicationContext());
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Event Planning");
	    c= new CalendarEvents(mContext);
	    startTime = System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          mode= extras.getString("mode");
        }
		String[] items_names=new String[]{};
		items_names=getResources().getStringArray(R.array.EventNames);
		 adapter2=new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items_names);
		 spinner_eventName=(Spinner) findViewById(R.id.spinner_eventPlanType);
		 spinner_eventName.setAdapter(adapter2);
	   
	    editText_eventDescription=(EditText) findViewById(R.id.editText_eventPlanDescription);
	    radioGroup_personal=(RadioGroup) findViewById(R.id.radioGroup_personal);
	    radioGroup_personal.check(R.id.radio_no);
	    editText_event_location=(AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView_location);
	    String[] locations = new String[] {};
	    locations=getResources().getStringArray(R.array.Locations);
	     adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, locations);
	    editText_event_location.setAdapter(adapter);
	    button_addEvent=(Button) findViewById(R.id.button_eventPlanAdd);
	    button_addEvent.setOnClickListener(this);
	    button_viewCalendar=(Button) findViewById(R.id.button_eventViewCalendar);
	    button_viewCalendar.setOnClickListener(this);
	    linearLayout_buttonsOne=(LinearLayout) findViewById(R.id.linearLayout_buttonsOne);
	    linearLayout_buttonsTwo=(LinearLayout) findViewById(R.id.linearLayout_buttonsTwo);
	    
	    if(!mode.isEmpty()&&mode.equalsIgnoreCase("edit_mode")){
	    	
	    	linearLayout_buttonsOne.setVisibility(View.GONE);
	    	linearLayout_buttonsTwo.setVisibility(View.VISIBLE);
	    	
	    	button_edit=(Button) findViewById(R.id.button_edit);
	    	button_delete=(Button) findViewById(R.id.button_delete);
	    	final String event_type=extras.getString("event_type");
	    	final String event_desc=extras.getString("event_description");
	    	final String event_location=extras.getString("event_location");
	    	final String event_id=extras.getString("event_id");
	    	final int availability=c.readCalendarEventForEdit(mContext, Long.parseLong(event_id));
	    	if(availability==Events.AVAILABILITY_BUSY){
	    		radioGroup_personal.check(R.id.radio_yes);
	    	}else if(availability==Events.AVAILABILITY_FREE){
	    		radioGroup_personal.check(R.id.radio_no);	
	    	}
	    	int spinner_position=adapter2.getPosition(event_type);
	    	spinner_eventName.setSelection(spinner_position);
	    	editText_event_location.setText(event_location);
	    	editText_eventDescription.setText(event_desc);
	    	button_edit.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
				
				String edited_event_type=spinner_eventName.getSelectedItem().toString();
				String edited_event_location=editText_event_location.getText().toString();
				String edited_event_description=editText_eventDescription.getText().toString();
				if(radioGroup_personal.getCheckedRadioButtonId()==R.id.radio_yes){
					if(c.editEvent(Long.parseLong(event_id), edited_event_type, edited_event_location, edited_event_description,Events.AVAILABILITY_BUSY)==true){		
						JSONObject json = new JSONObject();
						try {
							json.put("eventid", event_id);
							json.put("eventtype", edited_event_type);
							json.put("location", edited_event_location);
							json.put("description", edited_event_description);
							json.put("category", "personal");
							json.put("changed", 1);
							json.put("deleted", 0);
							json.put("ver", dbh.getVersionNumber(mContext));
							json.put("battery", dbh.getBatteryStatus(mContext));
					    	json.put("device", dbh.getDeviceName());
					    	json.put("imei", dbh.getDeviceImei(mContext));
					 
						} catch (JSONException e) {
							e.printStackTrace();
						}
						end_time=System.currentTimeMillis();
						dbh.insertCCHLog("Calendar", json.toString(), String.valueOf(startTime), String.valueOf(end_time));
						Intent intent=new Intent(mContext, EventsViewActivity.class);
						startActivity(intent);
						finish();
						overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
						Toast.makeText(mContext, "Event edited successfully!",
				         Toast.LENGTH_LONG).show();
					}
					}else if(radioGroup_personal.getCheckedRadioButtonId()==R.id.radio_no){
						if(c.editEvent(Long.parseLong(event_id), edited_event_type, edited_event_location, edited_event_description,Events.AVAILABILITY_FREE)==true){		
							JSONObject json = new JSONObject();
							try {
								json.put("eventid", event_id);
								json.put("eventtype", edited_event_type);
								json.put("location", edited_event_location);
								json.put("description", edited_event_description);
								json.put("category", "not_personal");
								json.put("changed", 1);
								json.put("deleted", 0);
								json.put("ver", dbh.getVersionNumber(mContext));
								json.put("battery", dbh.getBatteryStatus(mContext));
						    	json.put("device", dbh.getDeviceName());
						    	json.put("imei", dbh.getDeviceImei(mContext));
							} catch (JSONException e) {
								e.printStackTrace();
							}
							end_time=System.currentTimeMillis();
							dbh.insertCCHLog("Calendar", json.toString(), String.valueOf(startTime), String.valueOf(end_time));
							Intent intent=new Intent(mContext, EventsViewActivity.class);
							startActivity(intent);
							finish();
							overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
							Toast.makeText(mContext, "Event edited successfully!",
					         Toast.LENGTH_LONG).show();
						}
					}
					}
	    	});
	    	button_delete.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							mContext);
						alertDialogBuilder.setTitle("Delete Confirmation");
						alertDialogBuilder
							.setMessage("You are about to delete this event. Proceed?")
							.setCancelable(false)
							.setIcon(R.drawable.ic_error)
							.setPositiveButton("No",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									dialog.cancel();
								}
							  })
							.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									if(c.deleteEvent(Long.parseLong(event_id))==true){
										JSONObject json = new JSONObject();
										 try {
											json.put("eventid", event_id);
											 json.put("eventtype", event_type);
											 json.put("location", event_location);
											 json.put("description", event_desc);
											 json.put("changed", 0);
											 json.put("deleted", 1);
											 json.put("ver", dbh.getVersionNumber(mContext));
												json.put("battery", dbh.getBatteryStatus(mContext));
										    	json.put("device", dbh.getDeviceName());
										    	json.put("imei", dbh.getDeviceImei(mContext));
										} catch (JSONException e) {
											e.printStackTrace();
										}
										 end_time=System.currentTimeMillis();
										 dbh.insertCCHLog("Calendar", json.toString(), String.valueOf(startTime), String.valueOf(end_time));
										 Intent intent=new Intent(mContext, EventsViewActivity.class);
										 startActivity(intent);
										 finish();
										 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
										 Toast.makeText(mContext, "Event deleted successfully!",
										         Toast.LENGTH_LONG).show();
										}
				}
	    	});
						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
			}	
        });
	    	}
	    }
	@Override
	public void onClick(View v) {
		
		int id = v.getId();
		if (id == R.id.button_eventPlanAdd) {
			int event_category = 0;
			String eventName=spinner_eventName.getSelectedItem().toString();
			String eventLocation=editText_event_location.getText().toString();
			String eventDescription=editText_eventDescription.getText().toString();
			Calendar cal = Calendar.getInstance();
			//This is used to indicate if an event is personal or not
			   if(radioGroup_personal.getCheckedRadioButtonId()==R.id.radio_yes){
				   event_category=Events.AVAILABILITY_BUSY;
			   }else if(radioGroup_personal.getCheckedRadioButtonId()==R.id.radio_no){
				   event_category=Events.AVAILABILITY_FREE;
			   }
			Intent	intent = new Intent(Intent.ACTION_INSERT)
			        .setData(Events.CONTENT_URI)
			        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
			        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,  cal.getTimeInMillis()+60*60*1000)
			        .putExtra(Events.TITLE, eventName)
			        .putExtra(Events.DESCRIPTION, eventDescription)
			        .putExtra(Events.EVENT_LOCATION, eventLocation)
			        .putExtra(Events.AVAILABILITY, event_category)
			 		.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY , false);
			
			startActivityForResult(intent, 1);
		}
		else if (id == R.id.button_eventViewCalendar) {
			Intent intent =  new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("content://com.android.calendar/time"));
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == 1) {
	    	Intent intent=new Intent(Intent.ACTION_MAIN);
	    	intent.setClass(PlanEventActivity.this, EventPlannerOptionsActivity.class);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	startActivity(intent);
	    	PlanEventActivity.this.finish();
	    	overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
	    }
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			PlanEventActivity.this.finish();
			
		} 
		
	    return true; 
	}

	public void saveToLog(Long starttime) 
	{
	  Long endtime = System.currentTimeMillis();
	  data=new JSONObject();
	    try {
	    	data.put("page", "Event Planner");
	    	data.put("ver", dbh.getVersionNumber(PlanEventActivity.this));
	    	data.put("battery", dbh.getBatteryStatus(PlanEventActivity.this));
	    	data.put("device", dbh.getDeviceName());
			data.put("imei", dbh.getDeviceImei(PlanEventActivity.this));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	  dbh.insertCCHLog(EVENT_PLANNER_ID, data.toString(), starttime.toString(), endtime.toString());	
	}
	public void onDestroy(){
		 super.onDestroy();
		 Long starttime=System.currentTimeMillis();  
		 saveToLog(starttime); 
	 }
}
