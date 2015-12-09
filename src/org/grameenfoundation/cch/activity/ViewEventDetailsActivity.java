package org.grameenfoundation.cch.activity;

import java.util.Arrays;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.model.Validation;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ViewEventDetailsActivity extends Activity implements OnClickListener {

	private String event_type;
	private String event_desc;
	private String event_location;
	private String event_id;
	private String event_startdate;
	private Context mContext;
	private ArrayAdapter<String> adapter2;
	private MaterialSpinner spinner_eventName;
	private EditText editText_otherOption;
	private TableRow other_option;
	private EditText editText_eventDescription;
	private RadioGroup radioGroup_personal;
	private AutoCompleteTextView editText_event_location;
	private ArrayAdapter<String> adapter;
	private Button button_edit;
	private Button button_delete;
	private Button button_update;
	private static final String EVENT_PLANNER_ID = "Event Planner";
	private JSONObject data;
	private DbHelper dbh;
	private long end_time;
	private CalendarEvents c;
	private String event_enddate;
	private TextView textView_status;
	private String event_status;
	private String event_category;
	private LinearLayout LinearLayout_buttonOne;
	private TableLayout details_table;
	private String event_comment;
	private String event_justification;
	private TextView textView_comment;
	private TextView textView_justify;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_event_details_view);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Event Planning");
	    mContext=ViewEventDetailsActivity.this;
	    c= new CalendarEvents(mContext);
	    dbh=new DbHelper(mContext);
	    Bundle extras = getIntent().getExtras(); 
	    String[] items_names=new String[]{};
	    editText_otherOption=(EditText) findViewById(R.id.editText_otherOption);
	    other_option=(TableRow) findViewById(R.id.other_option);
		other_option.setVisibility(View.GONE);
		LinearLayout_buttonOne=(LinearLayout) findViewById(R.id.linearLayout_buttonsOne);
		items_names=getResources().getStringArray(R.array.EventNames);
		Arrays.sort(items_names);
		 adapter2=new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items_names);
		 spinner_eventName=(MaterialSpinner) findViewById(R.id.spinner_eventPlanType);
		 textView_status=(TextView) findViewById(R.id.textView_eventStatus);
		 textView_comment=(TextView) findViewById(R.id.textView_comment);
		 textView_justify=(TextView) findViewById(R.id.textView_justification);
		  editText_event_location=(AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView_location);
		  editText_eventDescription=(EditText) findViewById(R.id.editText_eventPlanDescription);
		 spinner_eventName.setFloatingLabelText(" ");
		 spinner_eventName.setAdapter(adapter2);
		 details_table=(TableLayout) findViewById(R.id.details);
        if (extras != null) {
        	 event_enddate=extras.getString("event_enddate");
	    	 event_type=extras.getString("event_type");
	    	 event_desc=extras.getString("event_description");
	    	 event_location=extras.getString("event_location");
	    	 event_id=extras.getString("event_id");
	    	 event_startdate=extras.getString("event_startdate");
	    	 event_status=extras.getString("event_status");
	    	 event_category=extras.getString("event_category");
	    	 event_comment=extras.getString("event_comment");
	    	 event_justification=extras.getString("event_justification");
	    	 
        }
        if(Arrays.asList(items_names).contains(event_type)){
	    	spinner_eventName.setSelection(getIndex(spinner_eventName, event_type));
    	}else{
    		spinner_eventName.setSelection(getIndex(spinner_eventName, event_type));
	    	other_option.setVisibility(View.VISIBLE);
	    	editText_otherOption.setText(event_type);
    	}
    	spinner_eventName.setSelection(getIndex(spinner_eventName, event_type));
    	editText_event_location.setText(event_location);
    	editText_eventDescription.setText(event_desc);
    	textView_comment.setText(event_comment);
    	textView_justify.setText(event_justification);
    	if(event_status.equals("complete")){
    		textView_status.setText("Completed");
    		textView_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_complete_new, 0, 0, 0);
    		LinearLayout_buttonOne.setVisibility(View.GONE);
    		editText_event_location.setEnabled(false);
    		spinner_eventName.setEnabled(false);
    		editText_eventDescription.setEnabled(false);
    		textView_comment.setEnabled(false);
    		textView_justify.setEnabled(false);
    	}else if(event_status.equals("incomplete")){
    		textView_status.setText("Not completed");
    		textView_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close, 0, 0, 0);
    		LinearLayout_buttonOne.setVisibility(View.GONE);
    		editText_event_location.setEnabled(false);
    		spinner_eventName.setEnabled(false);
    		editText_eventDescription.setEnabled(false);
    		//textView_comment.setEnabled(false);
    		//textView_justify.setEnabled(false);
    	}else {
    		textView_status.setText("Pending");
    		textView_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_question, 0, 0, 0);
    		LinearLayout_buttonOne.setVisibility(View.VISIBLE);
    		details_table.setVisibility(View.GONE);
    	}
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
	    button_edit=(Button) findViewById(R.id.button_edit);
	    button_delete=(Button) findViewById(R.id.button_delete);
	    button_update=(Button) findViewById(R.id.button_update);
	    button_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,UpdateEventActivity.class);
				intent.putExtra("event_type", event_type);
				intent.putExtra("event_description", event_desc);
				intent.putExtra("event_location",event_location);
				intent.putExtra("event_id", event_id);
				intent.putExtra("event_startdate",event_startdate);
				intent.putExtra("event_enddate", event_enddate);
				intent.putExtra("event_status", event_status);
				intent.putExtra("event_category", event_category);
				intent.putExtra("event_comment", event_comment);
				intent.putExtra("event_justification", event_justification);
				startActivity(intent);
				
			}
		});
	    button_edit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String edited_event_type;
				if(spinner_eventName.getSelectedItem().toString().equalsIgnoreCase("Other")){
					edited_event_type=editText_otherOption.getText().toString();
				}else{
					edited_event_type=spinner_eventName.getSelectedItem().toString();
				}
			String edited_event_location=editText_event_location.getText().toString();
			String edited_event_description=editText_eventDescription.getText().toString();
			if(radioGroup_personal.getCheckedRadioButtonId()==R.id.radio_yes){
				if(c.editEvent(Long.parseLong(event_id), edited_event_type, edited_event_location, edited_event_description,event_startdate,event_enddate,Events.AVAILABILITY_BUSY)==true){		
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
					
				}
				}else if(radioGroup_personal.getCheckedRadioButtonId()==R.id.radio_no){
					if(c.editEvent(Long.parseLong(event_id), edited_event_type, edited_event_location, edited_event_description,event_startdate,event_enddate,Events.AVAILABILITY_FREE)==true){		
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
									// dbh.insertCCHLog("Calendar", json.toString(), String.valueOf(startTime), String.valueOf(end_time));
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
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
	    	data.put("ver", dbh.getVersionNumber(mContext));
	    	data.put("battery", dbh.getBatteryStatus(mContext));
	    	data.put("device", dbh.getDeviceName());
			data.put("imei", dbh.getDeviceImei(mContext));
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
}
