package org.digitalcampus.oppia.activity;

import java.util.ArrayList;
import java.util.Calendar;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.EventBaseAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.calendar.CalendarEvents.MyEvent;
import org.grameenfoundation.poc.BaseActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_add_event);
	    mContext=PlanEventActivity.this;
	    dbh = new DbHelper(getApplicationContext());
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Event Planning");
	    c= new CalendarEvents(mContext);
	    startTime = System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          mode= extras.getString("mode");
        }
		String[] items_names={"ANC Static","ANC Outreach","CWC Static","CWC Outreach",
								"PNC Clinic","Routine Home visit","Special Home visit",
								"Family Planning","Health Talk","CMAM Clinic","School Health",
								"Adolescent Health","Mop-up Activity/Event","Community Durbar",
								"National Activity/Event","Staff meetings/durbars","Workshops","Leave/Excuse Duty",
								"Personal","Other"};
		//ArrayList<String> list=db.getAllEventCategory();
		 adapter2=new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items_names);
		 spinner_eventName=(Spinner) findViewById(R.id.spinner_eventPlanType);
		 spinner_eventName.setAdapter(adapter2);
	   
	    editText_eventDescription=(EditText) findViewById(R.id.editText_eventPlanDescription);
	    editText_event_location=(AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView_location);
	   String[] locations = new String[] {
	         "Ada", "Adedetsekope", "Adutor", "Agbakope", "Agordome","Agorkpo","Agorta",
	         "Anyaman","Asidowui","Asigbekope","Azizanyah","Bonikope","Comboni","Dabala",
	         "District","Dogo","Dordoekope-Angorto","Dorkploame","Gamenu","Hlevi","Kasseh",
	         "Koni","Kpotame","Larve","Lolonya","Luhuor","Madavuno","Matsekope","Pediatorkope",
	         "Pute","Sasekope","Sege","Sogakope","Sokutime","Tamatoku","Tefle","Teyekpitikope",
	         "Abui-Tsita","Adzokoe","Afienya","Agbadzakope","Agbate","Agbeve","Ahwiam","Ayertepa",
	         "Dawa","Dawhenya","Dikato","Duga","Dzake","Dzebetato","Dzetorkoe","Dzorgborve",
	         "Kpeve-Adzokoe","Kua","Lekpongunor","NewNingo","Nyigbenya","OldNingo","Peki",
	         "Tsanakpe","Tsatee","Tsiyinu","Wegbe","Prampram"
	     };
	     adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, locations);
	    editText_event_location.setAdapter(adapter);
	    button_addEvent=(Button) findViewById(R.id.button_eventPlanAdd);
	    button_addEvent.setOnClickListener(this);
	   // repeatingLayout=(TableRow) findViewById(R.id.tableRow_Repeating);
	   // repeatingLayout.setVisibility(View.GONE);
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
	    	
	    	int spinner_position=adapter2.getPosition(event_type);
	    	spinner_eventName.setSelection(spinner_position);
	    	editText_event_location.setText(event_location);
	    	editText_eventDescription.setText(event_desc);
	    	button_edit.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
				c.editEvent(Long.parseLong(event_id), event_type, event_location, event_desc);		
							
				}
	    		
	    	});
	    	button_delete.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
				c.deleteEvent(Long.parseLong(event_id));
				}
	    		
	    	});
	    	
	    }
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
		
		int id = v.getId();
		if (id == R.id.button_eventPlanAdd) {
			String eventName=spinner_eventName.getSelectedItem().toString();
			String eventLocation=editText_event_location.getText().toString();
			String eventDescription=editText_eventDescription.getText().toString();
					c.addEvent(eventName, eventLocation, eventDescription);
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							PlanEventActivity.this);
			 
						// set title
						alertDialogBuilder.setTitle("Confirmation");
			 
						// set dialog message
						alertDialogBuilder
							.setMessage("You have successfully added an event. \n Do you want to add another one?")
							.setCancelable(false)
							.setIcon(R.drawable.ic_error)
							.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, close
									// current activity
									dialog.cancel();
								  	editText_event_location.setText(" ");
			                    	editText_eventDescription.setText(" ");
								}
							  })
							.setNegativeButton("No",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, just close
									Intent intent=new Intent(PlanEventActivity.this,EventPlannerOptionsActivity.class);
			                    	startActivity(intent);
			                    	finish();
								}
							});
			 
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
			 
							// show it
							alertDialog.show();
					
		
		}
		else if (id == R.id.button_eventViewCalendar) {
			Intent intent =  new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("content://com.android.calendar/time"));
			startActivity(intent);
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
	  dbh.insertCCHLog(EVENT_PLANNER_ID, "Event Planner", starttime.toString(), endtime.toString());	
	}
	public void onDestroy(){
		 super.onDestroy();
		 Long starttime=System.currentTimeMillis();  
		 saveToLog(starttime); 
	 }
}
