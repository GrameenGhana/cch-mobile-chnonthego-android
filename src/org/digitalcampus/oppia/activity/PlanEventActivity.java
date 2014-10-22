package org.digitalcampus.oppia.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.calendar.CalendarEvents.MyEvent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

public final class PlanEventActivity extends Activity implements OnClickListener{

	private Context mContext;
	private Spinner spinner_eventName;
	private EditText editText_eventDescription;
	private EditText editText_event_location;
	private Button button_addEvent;
	private static final String EVENT_PLANNER_ID = "Event Planner";
	private MyEvent events;
	private Button button_viewCalendar;
	private DbHelper dbh;
	Long startTime;
	 CalendarEvents c;
	private Spinner spinner_eventRecurring;
	String rrule;
	private RadioGroup radioGroup_repeating;
	private TableRow repeatingLayout;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_add_event);
	    mContext=PlanEventActivity.this;
	    dbh = new DbHelper(getApplicationContext());
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Event Planning");
	    c= new CalendarEvents(mContext);
	    startTime = System.currentTimeMillis();
	    spinner_eventName=(Spinner) findViewById(R.id.spinner_eventPlanType);
	    editText_eventDescription=(EditText) findViewById(R.id.editText_eventPlanDescription);
	    editText_event_location=(EditText) findViewById(R.id.editText_eventPlanLocation);
	    button_addEvent=(Button) findViewById(R.id.button_eventPlanAdd);
	    button_addEvent.setOnClickListener(this);
	   // repeatingLayout=(TableRow) findViewById(R.id.tableRow_Repeating);
	   // repeatingLayout.setVisibility(View.GONE);
	    button_viewCalendar=(Button) findViewById(R.id.button_eventViewCalendar);
	    button_viewCalendar.setOnClickListener(this);
	}
	    /*
	    radioGroup_repeating=(RadioGroup) findViewById(R.id.radioGroup_recurringChoice);
	    radioGroup_repeating.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.radio_repeatNo:
					repeatingLayout.setVisibility(View.GONE);
					break;
				case R.id.radio_repeatYes:
					repeatingLayout.setVisibility(View.VISIBLE);
					break;
				}
			}
	    	
	    });
	    
	    spinner_eventRecurring=(Spinner) findViewById(R.id.spinner_eventRecurring);
	    String[] items={"Daily","Weekly","Monthly","Yearly"};
	    ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items);
	    spinner_eventRecurring.setAdapter(adapter);
	    spinner_eventRecurring.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch(position){
			
				case 0:
					rrule="FREQ=DAILY";
					break;
				case 1:
					rrule="FREQ=WEEKLY";
					break;
				case 2:
					rrule="FREQ=MONTHLY";
					break;
				case 3:
					rrule="FREQ=YEARLY";
					break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	}
*/
	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		
		case R.id.button_eventPlanAdd:
			
			String eventName=spinner_eventName.getSelectedItem().toString();
			String eventLocation=editText_event_location.getText().toString();
			String eventDescription=editText_eventDescription.getText().toString();
			c.addEvent(eventName, eventLocation, eventDescription);
			
			break;
			
		case R.id.button_eventViewCalendar:
			Intent intent =  new Intent(Intent.ACTION_VIEW);
		    intent.setData(Uri.parse("content://com.android.calendar/time"));
		    startActivity(intent);
			break;
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
	  dbh.insertCCHLog(EVENT_PLANNER_ID, "Event Planning", starttime.toString(), endtime.toString());	
	}
	public void onDestroy(){
		 super.onDestroy();
		 Long starttime=System.currentTimeMillis();  
		 saveToLog(starttime); 
	 }
}
