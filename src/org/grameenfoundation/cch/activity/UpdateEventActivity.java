package org.grameenfoundation.cch.activity;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.model.Validation;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class UpdateEventActivity extends Activity {

	private MaterialSpinner justification;
	private TableRow other_option;
	private EditText editText_otherOption;
	private String justification_text;
	private LinearLayout linearLayout_justification;
	private String event_enddate;
	private String event_type;
	private String event_desc;
	private String event_location;
	private String event_id;
	private String event_startdate;
	private DbHelper db;
	private EditText editText_comments;
	private Long start_time;
	private String event_category;
	private static final String CALENDAR = "Calendar";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.event_update_dialog);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Update Events");
	    db=new DbHelper(UpdateEventActivity.this);
	    if (extras != null) {
       	 	event_enddate=extras.getString("event_enddate");
	    	event_type=extras.getString("event_type");
	    	event_desc=extras.getString("event_description");
	    	 event_location=extras.getString("event_location");
	    	event_id=extras.getString("event_id");
	    	 event_startdate=extras.getString("event_startdate");
	    	 event_category=extras.getString("event_category");
       }
	    justification=(MaterialSpinner) findViewById(R.id.spinner1);
		other_option=(TableRow) findViewById(R.id.other_option);
		editText_otherOption=(EditText) findViewById(R.id.editText_otherOption);
		editText_comments=(EditText) findViewById(R.id.editText_comment);
		other_option.setVisibility(View.GONE);
		String[] items=getResources().getStringArray(R.array.Justification);
		ArrayAdapter<String> adapter2=new ArrayAdapter<String>(UpdateEventActivity.this, android.R.layout.simple_list_item_1, items);
		justification.setAdapter(adapter2);
		justification.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				if(justification.getSelectedItem().toString().equalsIgnoreCase("Other")){
					other_option.setVisibility(View.VISIBLE);
					justification_text=editText_otherOption.getText().toString();
				}else{
					other_option.setVisibility(View.GONE);
					justification_text=justification.getSelectedItem().toString();
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		linearLayout_justification=(LinearLayout) findViewById(R.id.Linearlayout_justification);
		final RadioGroup answer=(RadioGroup) findViewById(R.id.radioGroup_answer);
		TextView question=(TextView) findViewById(R.id.textView_question);
		try{
			String first="<font color='#53AB20'>Were you able to complete the event: </font>";
			String next="<font color='#520000'>"+event_type+"</font>";
			String next_two="<font color='#53AB20'> at </font>";
			String next_three="<font color='#520000'>"+event_location+"</font>";
			//message.setText("Were you able to complete the course "+course+" under the topic: "+topic);
			question.setText(Html.fromHtml(first+next+next_two+next_three));
		}catch(Exception e){
			e.printStackTrace();	
		}
		linearLayout_justification.setVisibility(View.GONE);
		Button update=(Button) findViewById(R.id.button_update);
		
		answer.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==R.id.radio_yes){
					linearLayout_justification.setVisibility(View.GONE);
				}else if(checkedId==R.id.radio_no){
					linearLayout_justification.setVisibility(View.VISIBLE);
				}
				
			}
			
		});
		
		update.setOnClickListener(new OnClickListener(){
			private Long end_time;

			@Override
			public void onClick(View v) {
				try{
					if(!checkValidation()){
						Toast.makeText(UpdateEventActivity.this, "Provide data for required fields!", Toast.LENGTH_LONG).show();	
					}else{
					if(answer.getCheckedRadioButtonId()==R.id.radio_yes){
						db.updateCalendarEvent(Long.parseLong(event_id),
												editText_comments.getText().toString(), 
												justification_text,
												"complete");
						JSONObject json = new JSONObject();
						 try {
							 json.put("eventid", event_id);
							 json.put("eventtype", event_type);
							 json.put("location", event_location);
							 json.put("description", event_desc);
							 json.put("status", "complete");
							 json.put("category",event_category);
							 json.put("justification", justification_text); 
							 json.put("comments", editText_comments.getText().toString()); 
							 json.put("ver", db.getVersionNumber(UpdateEventActivity.this));
							 json.put("battery", db.getBatteryStatus(UpdateEventActivity.this));
						    json.put("device", db.getDeviceName());
						    json.put("imei", db.getDeviceImei(UpdateEventActivity.this));
							 
						} catch (JSONException e) {
							e.printStackTrace();
						}
						 end_time=System.currentTimeMillis();
						 db.insertCCHLog(CALENDAR, json.toString(),event_startdate, event_enddate);
						 	Intent intent=new Intent(Intent.ACTION_MAIN);
							intent.setClass(UpdateEventActivity.this,EventsViewActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							UpdateEventActivity.this.finish();
							startActivity(intent);
					    
						 Toast.makeText(UpdateEventActivity.this, "Event updated!",
						         Toast.LENGTH_SHORT).show();
					}else if(answer.getCheckedRadioButtonId()==R.id.radio_no){
						db.updateCalendarEvent(Long.parseLong(event_id),
								editText_comments.getText().toString(), 
								justification_text,
								"incomplete");
						JSONObject json = new JSONObject();
						 try {
							 json.put("eventid", event_id);
							 json.put("eventtype", event_type);
							 json.put("location", event_location);
							 json.put("description", event_desc);
							 json.put("status", "incomplete");
							json.put("category",event_category);
							 json.put("justification", justification_text); 
							 json.put("comments", editText_comments.getText().toString()); 
							 json.put("ver", db.getVersionNumber(UpdateEventActivity.this));
							 json.put("battery", db.getBatteryStatus(UpdateEventActivity.this));
						    json.put("device", db.getDeviceName());
						    json.put("imei", db.getDeviceImei(UpdateEventActivity.this));
							 
						} catch (JSONException e) {
							e.printStackTrace();
						}
						 end_time=System.currentTimeMillis();
						 db.insertCCHLog(CALENDAR, json.toString(),event_startdate, event_enddate);
						
						 Intent intent=new Intent(Intent.ACTION_MAIN);
							intent.setClass(UpdateEventActivity.this,EventsViewActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							UpdateEventActivity.this.finish();
							startActivity(intent);
							 Toast.makeText(UpdateEventActivity.this, "Event updated!",
							         Toast.LENGTH_SHORT).show();
					}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			
		});
	}
	private boolean checkValidation() {
        boolean ret = true;
 
       // if (!Validation.hasTextTextView(editText_comments)) ret = false;
        if (!linearLayout_justification.isShown()&&Validation.hasSelection(justification))ret = false;
        if (other_option.isShown()&&!Validation.hasTextTextView(editText_otherOption))ret = false;
        return ret;
    }
}
