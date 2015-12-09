package org.grameenfoundation.cch.activity;

import java.util.Arrays;
import java.util.Calendar;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.model.Validation;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.grameenfoundation.poc.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow;

public final class PlanEventActivity extends BaseActivity implements OnClickListener{

	private Context mContext;
	private MaterialSpinner spinner_eventName;
	private EditText editText_eventDescription;
	private AutoCompleteTextView editText_event_location;
	private Button button_addEvent;
	private static final String EVENT_PLANNER_ID = "Event Planner";
	private Button button_viewCalendar;
	private DbHelper dbh;
	Long startTime;
	 CalendarEvents c;
	String rrule;
	private String mode;
	private Button button_edit;
	private Button button_delete;
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> adapter2;
	private Long end_time;
	private RadioGroup radioGroup_personal;
	private JSONObject data;
	private TableRow other_option;
	private EditText editText_otherOption;
	private TextView textView_status;
	private int event_category;
	private String eventName;
	private String eventLocation;
	private String eventDescription;
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
        other_option=(TableRow) findViewById(R.id.other_option);
        textView_status=(TextView) findViewById(R.id.textView_status);
        textView_status.setText("Add an Event");
        editText_otherOption=(EditText) findViewById(R.id.editText_otherOption);
		other_option.setVisibility(View.GONE);
		String[] items_names=new String[]{};
		items_names=getResources().getStringArray(R.array.EventNames);
		Arrays.sort(items_names);
		 adapter2=new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items_names);
		 spinner_eventName=(MaterialSpinner) findViewById(R.id.spinner_eventPlanType);
		 spinner_eventName.setFloatingLabelText(" ");
		 spinner_eventName.setAdapter(adapter2);
		 spinner_eventName.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
					if(spinner_eventName.getSelectedItem().toString().equalsIgnoreCase("Other")){
						other_option.setVisibility(View.VISIBLE);
					}else{
						other_option.setVisibility(View.GONE);
					}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			 
		 });
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
	    
	  
	    }
	@Override
	public void onClick(View v) {
		
		int id = v.getId();
		if (id == R.id.button_eventPlanAdd) {
			if(!checkValidation()){
				Toast.makeText(getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();
			}else{
			 event_category = 0;
			 eventName="";
			if(spinner_eventName.getSelectedItem().toString().equalsIgnoreCase("Other")){
				eventName=editText_otherOption.getText().toString();
			}else{
				eventName=spinner_eventName.getSelectedItem().toString();
			}
			 eventLocation=editText_event_location.getText().toString();
			 eventDescription=editText_eventDescription.getText().toString();
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
			
			 end_time = System.currentTimeMillis();
			
			startActivityForResult(intent, 1);
		}
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
	    	 try{
				 Cursor cursor =mContext.getContentResolver().query(
			                        Uri.parse("content://com.android.calendar/events"),
			                        new String[] {"MAX(_id) as max_id"}, null, null, null);
			        cursor.moveToFirst();
			        long max_val = cursor.getLong(cursor.getColumnIndex("max_id"));     
			        JSONObject json = new JSONObject();
					 try {
						 json.put("eventid", max_val);
						 json.put("eventtype", eventName);
						 json.put("location", eventLocation);
						 if(event_category==Events.AVAILABILITY_BUSY){
							 json.put("category","personal");
						 }else if(event_category==Events.AVAILABILITY_FREE){
							 json.put("category","not_personal");
						 }
						 json.put("description", eventDescription);
						 json.put("status", "");
						 json.put("justification", ""); 
						 json.put("comments",""); 
						 json.put("ver", dbh.getVersionNumber(PlanEventActivity.this));
						 json.put("battery", dbh.getBatteryStatus(PlanEventActivity.this));
					    json.put("device", dbh.getDeviceName());
					    json.put("imei", dbh.getDeviceImei(PlanEventActivity.this));
						 
					} catch (JSONException e) {
						e.printStackTrace();
					}
				 dbh.insertCCHLog("Calendar", json.toString(), String.valueOf(startTime), String.valueOf(end_time));
				 }catch(Exception e){
					 e.printStackTrace();
				 }
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
	 private boolean checkValidation() {
	        boolean ret = true;
	 
	        if (!Validation.hasTextEditText(editText_event_location)) ret = false;
	        if (!Validation.hasTextTextView(editText_eventDescription)) ret = false;
	        if (!Validation.hasSelection(spinner_eventName))ret = false;
	        if (other_option.isShown()&&!Validation.hasTextTextView(editText_otherOption))ret = false;
	        return ret;
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
	
	 private int getIndex(MaterialSpinner spinner, String myString)
	 {
	  int index = 0;

	  for (int i=0;i<spinner.getCount();i++){
		  if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
			  index = i;
	    break;
		  }
	  }
	  return index;
	 } 
	public void onDestroy(){
		 super.onDestroy();
		 Long starttime=System.currentTimeMillis();  
		 saveToLog(starttime); 
	 }
}
