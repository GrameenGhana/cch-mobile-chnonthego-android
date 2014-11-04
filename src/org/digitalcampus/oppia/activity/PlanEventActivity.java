package org.digitalcampus.oppia.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.EventBaseAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.calendar.CalendarEvents.MyEvent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

public final class PlanEventActivity extends Activity implements OnClickListener{

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
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_add_event);
	    mContext=PlanEventActivity.this;
	    dbh = new DbHelper(getApplicationContext());
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Event Planner");
	    getActionBar().setSubtitle("Event Planning");
	    c= new CalendarEvents(mContext);
	    startTime = System.currentTimeMillis();
	    spinner_eventName=(Spinner) findViewById(R.id.spinner_eventPlanType);
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
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, locations);
	    editText_event_location.setAdapter(adapter);
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
		
		int id = v.getId();
		if (id == R.id.button_eventPlanAdd) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
    				mContext);
			// set title
			alertDialogBuilder.setTitle("Add event?");
			// set dialog message
			alertDialogBuilder
				.setMessage("You are about to add an event to the calendar. Proceed?")
				.setCancelable(false)
				.setPositiveButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						dialog.cancel();
					}
				  })
				.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						String eventName=spinner_eventName.getSelectedItem().toString();
						String eventLocation=editText_event_location.getText().toString();
						String eventDescription=editText_eventDescription.getText().toString();
						c.addEvent(eventName, eventLocation, eventDescription);
						dialog.cancel();
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
			    				mContext);
			     
			    			// set title
			    			alertDialogBuilder.setTitle("Add another event?");
			     
			    			// set dialog message
			    			alertDialogBuilder
			    				.setMessage("Event added. Would you like to add another event?")
			    				.setCancelable(false)
			    				.setPositiveButton("No",new DialogInterface.OnClickListener() {
			    					public void onClick(DialogInterface dialog,int id) {
			    						// if this button is clicked, close
			    						// current activity
			    						dialog.cancel();
			    						Intent intent=new Intent(mContext,EventPlannerOptionsActivity.class);
			    						startActivity(intent);
			    					}
			    				  })
			    				.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
			    					public void onClick(DialogInterface dialog,int id) {
			    						dialog.cancel();
			    						editText_event_location.setText("");
			    						editText_eventDescription.setText("");
					    		        	}
			    				});
			    				// create alert dialog
			    				AlertDialog alertDialog = alertDialogBuilder.create();
			     
			    				// show it
			    				alertDialog.show();
				        	}
				});
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			// show it
			alertDialog.show();
		} else if (id == R.id.button_eventViewCalendar) {
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
	  dbh.insertCCHLog(EVENT_PLANNER_ID, "Event Planning", starttime.toString(), endtime.toString());	
	}
	public void onDestroy(){
		 super.onDestroy();
		 Long starttime=System.currentTimeMillis();  
		 saveToLog(starttime); 
	 }
}
