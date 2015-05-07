package org.grameenfoundation.cch.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.MainScreenActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.model.MyCalendarEvents;
import org.grameenfoundation.cch.model.Validation;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.grameenfoundation.poc.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class EventUpdateActivity extends BaseActivity {

	private ListView listView;
	private CalendarEvents c;
	private Context mContext;
	private ArrayList<MyCalendarEvents> TodayCalendarEvents;
	private CalendarEventsViewAdapter adapter;
	String[] selected_items;
	private DbHelper db;
	private Long start_time;
	private static final String EVENT_PLANNER_ID = "Event Planner";
	private LinearLayout linearLayout_justification;
	private MaterialSpinner justification;
	private TableRow other_option;
	private EditText editText_otherOption;
	private String justification_text;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_event_update);
	    getActionBar().setTitle("Planner");
		getActionBar().setSubtitle("Update Events");
	    mContext=EventUpdateActivity.this;
	    listView=(ListView) findViewById(R.id.listView1);
	    db=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    c= new CalendarEvents(mContext);
	    
	    TodayCalendarEvents=new ArrayList<MyCalendarEvents>();
	    try{
	    	TodayCalendarEvents=c.readEventsToUpdate(mContext, true);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    if(TodayCalendarEvents.size()>0){
	    	adapter=new CalendarEventsViewAdapter(mContext,TodayCalendarEvents);
	    	listView.setAdapter(adapter);
	    }else{
	    	Intent intent=new Intent(Intent.ACTION_MAIN);
			intent.setClass(EventUpdateActivity.this,MainScreenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			finish();
			startActivity(intent);
	    }
	    listView.setOnItemClickListener(new OnItemClickListener(){

		

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				final Dialog dialog = new Dialog(EventUpdateActivity.this);
				
				dialog.setContentView(R.layout.event_update_dialog);
				dialog.setTitle("Update event");
				justification=(MaterialSpinner) dialog.findViewById(R.id.spinner1);
				other_option=(TableRow) dialog.findViewById(R.id.other_option);
				editText_otherOption=(EditText) dialog.findViewById(R.id.editText_otherOption);
				other_option.setVisibility(View.GONE);
				String[] items=getResources().getStringArray(R.array.Justification);
				ArrayAdapter<String> adapter2=new ArrayAdapter<String>(EventUpdateActivity.this, android.R.layout.simple_list_item_1, items);
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
				linearLayout_justification=(LinearLayout) dialog.findViewById(R.id.Linearlayout_justification);
				final RadioGroup answer=(RadioGroup) dialog.findViewById(R.id.radioGroup_answer);
				TextView question=(TextView) dialog.findViewById(R.id.textView_question);
				try{
					selected_items=adapter.getItem(position);
					String first="<font color='#53AB20'>Were you able to complete the event: </font>";
					String next="<font color='#520000'>"+selected_items[0]+"</font>";
					String next_two="<font color='#53AB20'> at </font>";
					String next_three="<font color='#520000'>"+selected_items[2]+"</font>";
					//message.setText("Were you able to complete the course "+course+" under the topic: "+topic);
					question.setText(Html.fromHtml(first+next+next_two+next_three));
				}catch(Exception e){
					e.printStackTrace();	
				}
				linearLayout_justification.setVisibility(View.GONE);
				Button update=(Button) dialog.findViewById(R.id.button_update);
				Button cancel=(Button) dialog.findViewById(R.id.button_cancel);
				
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
				cancel.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						dialog.dismiss();
						
					}
					
				});
				update.setOnClickListener(new OnClickListener(){
					
					private Long end_time;

					@Override
					public void onClick(View v) {
						try{
							if(!checkValidation()){
								Toast.makeText(getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();	
							}else{
							if(answer.getCheckedRadioButtonId()==R.id.radio_yes){
								c.updateEvent(Long.valueOf(selected_items[3]), Events.STATUS_CONFIRMED);//event was completed
								JSONObject json = new JSONObject();
								 try {
									 json.put("eventid", selected_items[3]);
									 json.put("eventtype", selected_items[0]);
									 json.put("location", selected_items[2]);
									 json.put("description", selected_items[1]);
									 json.put("justification", "Event completed"); 
									 json.put("ver", db.getVersionNumber(getApplicationContext()));
									 json.put("battery", db.getBatteryStatus(getApplicationContext()));
								    	json.put("device", db.getDeviceName());
								    	json.put("imei", db.getDeviceImei(getApplicationContext()));
									 
								} catch (JSONException e) {
									e.printStackTrace();
								}
								 end_time=System.currentTimeMillis();
								 db.insertCCHLog(EVENT_PLANNER_ID, json.toString(), String.valueOf(start_time), String.valueOf(end_time));
								 dialog.dismiss();
								 	Intent intent=new Intent(Intent.ACTION_MAIN);
									intent.setClass(EventUpdateActivity.this,EventUpdateActivity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									finish();
									startActivity(intent);
							    
								 Toast.makeText(getApplicationContext(), "Event updated!",
								         Toast.LENGTH_SHORT).show();
							}else if(answer.getCheckedRadioButtonId()==R.id.radio_no){
								c.updateEvent(Long.valueOf(selected_items[3]), Events.STATUS_CANCELED);//event was not completed
								JSONObject json = new JSONObject();
								 try {
									 json.put("eventid", selected_items[3]);
									 json.put("eventtype", selected_items[0]);
									 json.put("location", selected_items[2]);
									 json.put("description", selected_items[1]);
									 json.put("justification", justification_text); 
									 json.put("ver", db.getVersionNumber(getApplicationContext()));
									 json.put("battery", db.getBatteryStatus(getApplicationContext()));
								    	json.put("device", db.getDeviceName());
								    	json.put("imei", db.getDeviceImei(getApplicationContext()));
									 
								} catch (JSONException e) {
									e.printStackTrace();
								}
								 end_time=System.currentTimeMillis();
								 db.insertCCHLog(EVENT_PLANNER_ID, json.toString(), String.valueOf(start_time), String.valueOf(end_time));
								 dialog.dismiss();
								 Intent intent=new Intent(Intent.ACTION_MAIN);
									intent.setClass(EventUpdateActivity.this,EventUpdateActivity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									finish();
									startActivity(intent);
								 Toast.makeText(getApplicationContext(), "Event updated!",
								         Toast.LENGTH_SHORT).show();
							}
							}
						}catch(Exception e){
							e.printStackTrace();
						}
						
					}
					
				});
				dialog.show();
	}
	    });
	}
	
	private boolean checkValidation() {
        boolean ret = true;
 
        if (linearLayout_justification.isShown()&&!Validation.hasSelection(justification)) ret = false;
        if (other_option.isShown()&&!Validation.hasTextEditText(editText_otherOption))ret = false;
        return ret;
    }
	
	public class CalendarEventsViewAdapter extends BaseAdapter {

		 public ArrayList<MyCalendarEvents> TodayCalendarEvents;
		 public LayoutInflater minflater;
		 private int count;
		 public int lastExpandedGroupPosition;    
		 private Context mContext;
		private ArrayList<MyCalendarEvents> todayEvents;
		 MyCalendarEvents calendarEvents=new MyCalendarEvents();

		 public CalendarEventsViewAdapter(Context mContext,	ArrayList<MyCalendarEvents> TodayCalendarEvents) {
			 todayEvents = new ArrayList<MyCalendarEvents>();
			 todayEvents.addAll(TodayCalendarEvents);
			 this.mContext=mContext;
			 minflater = LayoutInflater.from(mContext);
		 
		 }

		@Override
		public int getCount() {
			count=todayEvents.size();
			return count;
		}
		@Override
		public String[] getItem(int position) {
			String[] item = null;
			item=new String[]{todayEvents.get(position).getEventType(),
					todayEvents.get(position).getEventDescription(),
					todayEvents.get(position).getEventLocation(),
					todayEvents.get(position).getEventId()};
			return item;
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				   convertView=minflater.inflate(R.layout.event_expandable_listview_single,null);
			   }
			   TextView text=(TextView) convertView.findViewById(R.id.textView_eventType);
			   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventDescription);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventDetails);
			   TextView text4=(TextView) convertView.findViewById(R.id.textView_eventTime);
			 
			   text.setText(todayEvents.get(position).getEventType());
			   text2.setText(todayEvents.get(position).getEventDescription());
			   text3.setText(todayEvents.get(position).getEventLocation());
			  text4.setText(todayEvents.get(position).getEventTime());
			 
			  return convertView;
		}



	}
}
