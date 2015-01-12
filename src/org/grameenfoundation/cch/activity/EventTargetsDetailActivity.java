package org.grameenfoundation.cch.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.mobile.learningGF.R.color;
import org.digitalcampus.oppia.activity.MainScreenActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.EventBaseAdapter;
import org.grameenfoundation.cch.activity.TargetSettingActivity.EventsActivity.DatePickerFragment;
import org.grameenfoundation.cch.activity.TargetSettingActivity.EventsActivity.DatePickerFragment2;
import org.grameenfoundation.cch.caldroid.CaldroidFragment;
import org.grameenfoundation.cch.caldroid.CaldroidListener;
import org.grameenfoundation.cch.utils.TextProgressBar;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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
	private Button button_edit;
	private Button button_delete;
	private Button button_update;
	private String start_date_extra;
	private TextView textView_startDate;
	private String achieved;
	private TextView textView_achieved;
	private TextView textView_percentageAchieved;
	private String today;
	private int today_year;
	private int today_day;
	private int today_month;
	private Integer due_date_day;
	private Integer due_date_month;
	private Integer due_date_year;
	private TextView textView_progressCheck;
	private int date_difference;
	private TextProgressBar progress_bar;
	private static TextView dueDateValue;
	static String start_date ;
	static long due_date_to_compare;
	private static TextView startDateValue;
	private static HashMap<String, String> updateItems;
	private static ArrayList<String> number_entered;
	private static ArrayList<String> number_remaining;
	private static ArrayList<String> number_achieved;
	private static ArrayList<String> update_id;
	int progress_status;
	private long end_time;
	private long start_time;
	private String last_updated;
	private TextView day_difference;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_event_target_detail);
	    db=new DbHelper(EventTargetsDetailActivity.this);
	    getActionBar().setTitle("Event Planner");
	    getActionBar().setSubtitle("Event Target Details");
	    Calendar c = Calendar.getInstance();
        today_month=c.get(Calendar.MONTH)+1;
        today_day=c.get(Calendar.DAY_OF_WEEK);
        today_year=c.get(Calendar.YEAR);
        start_time=System.currentTimeMillis();
      	//today=day+"-"+month+"-"+year;
	    textView_name=(TextView) findViewById(R.id.textView_name);
	    textView_period=(TextView) findViewById(R.id.textView_period);
	    textView_number=(TextView) findViewById(R.id.textView_number);
	    textView_dueDate=(TextView) findViewById(R.id.textView_dueDate);
	    textView_startDate=(TextView) findViewById(R.id.textView_startDate);
	    textView_achieved=(TextView) findViewById(R.id.textView_achieved);
	    textView_progressCheck=(TextView) findViewById(R.id.textView_progressCheck);
	    textView_percentageAchieved=(TextView) findViewById(R.id.textView_percentageAchieved);
	    progress_bar=(TextProgressBar) findViewById(R.id.progressBar1);
	    imageView_status=(ImageView) findViewById(R.id.imageView_status);
	    day_difference=(TextView) findViewById(R.id.textView_dayDifference);
	    button_edit=(Button) findViewById(R.id.button_edit);
	    button_delete=(Button) findViewById(R.id.button_delete);
	    button_update=(Button) findViewById(R.id.button_update);
	    
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
        	event_name=extras.getString("event_name");
			event_number=extras.getString("event_number");
			event_period=extras.getString("event_period");
			due_date_extra=extras.getString("due_date");
			start_date_extra=extras.getString("start_date");
			achieved=extras.getString("achieved");
			status=extras.getString("status");
			event_id=extras.getLong("event_id");
			last_updated=extras.getString("last_updated");
			System.out.println(String.valueOf(event_id));
        }
       
        int number_achieved_current=Integer.valueOf(achieved);
        int number_entered_current=Integer.valueOf(event_number);
        Double percentage=((double)number_achieved_current/number_entered_current)*100;
        String percentage_achieved=String.format("%.0f", percentage);
        long difference_in_days=differenceIndays(start_date_extra,due_date_extra,getDateTime());
        
        if (difference_in_days==1){
        	day_difference.setTextColor(Color.rgb(225,170,7));
        	day_difference.setText("Due in: "+String.valueOf(difference_in_days)+ " day");
        	imageView_status.setImageResource(R.drawable.ic_achieved_waiting);
        }else if(difference_in_days==0) {
        	day_difference.setTextColor(Color.RED);
        	day_difference.setText("Due today!!!!");
        	imageView_status.setImageResource(R.drawable.ic_achieved_waiting);
        }else if(difference_in_days==-1) {
        	day_difference.setTextColor(Color.RED);
        	day_difference.setText("Due date is past");
        	imageView_status.setImageResource(R.drawable.sad);
        }else {
        	day_difference.setText("Due in: "+String.valueOf(difference_in_days)+ " days");
        	imageView_status.setImageResource(R.drawable.ic_achieved_waiting);
        }
        textView_name.setText(event_name);
        textView_period.setText(event_period);
        textView_dueDate.setText(due_date_extra);
        textView_number.setText(event_number);
        textView_startDate.setText(start_date_extra);
        textView_achieved.setText(achieved);
        textView_percentageAchieved.setText(String.valueOf(percentage_achieved)+"%");
        progress_status=(int) percentage.doubleValue();
        progress_bar.setProgress(progress_status);
        progress_bar.setPrefixText(percentage_achieved+"%");
        progress_bar.setPrefixText(" ");
      
      /*
        if(status.equalsIgnoreCase("updated")){
        	imageView_status.setImageResource(R.drawable.ic_achieved_smile);
        }else if(status.equalsIgnoreCase("new_record")){
        	imageView_status.setImageResource(R.drawable.ic_achieved_waiting);
        }else if(status.equalsIgnoreCase("not_achieved")){
        	imageView_status.setImageResource(R.drawable.ic_achieved_waiting);
        }
        */
        button_edit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(EventTargetsDetailActivity.this);
				dialog.setContentView(R.layout.event_set_dialog);
				dialog.setTitle("Edit Event Target");
				final EditText editText_eventNumber=(EditText) dialog.findViewById(R.id.editText_dialogEventPeriodNumber);
				String[] items={"Daily","Weekly","Monthly","Mid-year","Quarterly","Annually"};
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(EventTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
				final Spinner spinner_event_name=(Spinner) dialog.findViewById(R.id.spinner_eventName);
				final Spinner spinner_eventPeriod=(Spinner) dialog.findViewById(R.id.spinner_dialogEventPeriod);
				spinner_eventPeriod.setAdapter(adapter);
				int spinner_position_period=adapter.getPosition(event_period);
				spinner_eventPeriod.setSelection(spinner_position_period);
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
				dueDateValue=(TextView) dialog.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) dialog.findViewById(R.id.textView_startDate);
				ImageButton datepickerDialog=(ImageButton) dialog.findViewById(R.id.imageButton_dueDate);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", today_month, today_year);
						dialogCaldroidFragment.show(getSupportFragmentManager(),"TAG");
						//dialogCaldroidFragment.setEnableSwipe(true);
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	dueDateValue.setText(formatter.format(date));
						    	due_date=formatter.format(date);
						        Toast.makeText(EventTargetsDetailActivity.this, formatter.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
					}
					
				});
				ImageButton datepickerDialog2=(ImageButton) dialog.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", today_month, today_year);
						dialogCaldroidFragment.show(getSupportFragmentManager(),"TAG");
						//dialogCaldroidFragment.setEnableSwipe(true);
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	startDateValue.setText(formatter.format(date));
						    	start_date=formatter.format(date);
						        Toast.makeText(EventTargetsDetailActivity.this, formatter.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
					}
					
				});
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
						//dialog.dismiss();
						String duration = null;
						if(isToday(due_date_to_compare)){
							duration="Today";
						}else if(isTomorrow(due_date_to_compare)){
							duration="Tomorrow";
						}else if(isThisWeek(due_date_to_compare)){
							duration="This week";
						}else if(isThisMonth(due_date_to_compare)){
							duration="This month";
						}else if(isThisQuarter(due_date_to_compare)){
							duration="This quarter";
						}
						String event_name=spinner_event_name.getSelectedItem().toString();
						String event_number=editText_eventNumber.getText().toString();
						String event_period=spinner_eventPeriod.getSelectedItem().toString();
						if(isDateAfter(start_date,due_date)==true){
				      		 startDateValue.requestFocus();
				      		startDateValue.setError("Check this date!");
				      	}else{
					    if(db.editEventCategory(event_name, event_number, event_period,duration,start_date,due_date, event_id)==true){
					    	number_achieved=db.getForUpdateEventNumberAchieved(event_id,event_period);
							//number_remaining=db.getAllForEventsNumberRemaining(event_period);
					    	JSONObject json = new JSONObject();
							 try {
								json.put("id", event_id);
								json.put("target_type", event_name);
								 json.put("target_number", event_number);
								 json.put("start_date", start_date_extra);
								 json.put("due_date", due_date_extra);
								 json.put("achieved_number", achieved);
								 json.put("last_updated", last_updated);
								 json.put("changed", 1);
								 json.put("deleted", 0);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							 end_time=System.currentTimeMillis();
							 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
					    	Intent intent2 = new Intent(Intent.ACTION_MAIN);
				 	          intent2.setClass(EventTargetsDetailActivity.this, NewEventPlannerActivity.class);
				 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 	          startActivity(intent2);
				 	          finish();	
					    	 Toast.makeText(EventTargetsDetailActivity.this, "Event target edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(EventTargetsDetailActivity.this, "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
					}
				});
				
	 				dialog.show();
	 	         
				
			}
        	
        });
        button_delete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						EventTargetsDetailActivity.this);
		 
					// set title
					alertDialogBuilder.setTitle("Delete Confirmation");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("You are about to delete this target. Proceed?")
						.setCancelable(false)
						.setIcon(R.drawable.ic_error)
						.setPositiveButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close
								// current activity
								dialog.cancel();
							}
						  })
						.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								if(db.deleteEventCategory(event_id)==true){
									number_achieved=db.getForUpdateEventNumberAchieved(event_id,event_period);
									//number_remaining=db.getAllForEventsNumberRemaining(event_period);
									JSONObject json = new JSONObject();
									 try {
										json.put("id", event_id);
										json.put("target_type", event_name);
										 json.put("target_number", event_number);
										 json.put("start_date", start_date_extra);
										 json.put("due_date", due_date_extra);
										 json.put("achieved_number", achieved);
										 json.put("last_updated", last_updated);
										 json.put("changed", 0);
										 json.put("deleted", 1);
									} catch (JSONException e) {
										e.printStackTrace();
									}
									 end_time=System.currentTimeMillis();
									 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
					        		Intent intent2 = new Intent(Intent.ACTION_MAIN);
						 	          intent2.setClass(EventTargetsDetailActivity.this, NewEventPlannerActivity.class);
						 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						 	          startActivity(intent2);
						 	          finish();	
					        	}
						 	         Toast.makeText(getApplicationContext().getApplicationContext(), "Deleted successfully!",
									         Toast.LENGTH_LONG).show();
					        	}
							
						});
		 
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
				
			}
        	
        });
        button_update.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				number_achieved=db.getForUpdateEventNumberAchieved(event_id,event_period);
				System.out.println("Printing id: "+String.valueOf(event_id));
				Intent intent3=new Intent(EventTargetsDetailActivity.this,UpdateActivity.class);
	        	intent3.putExtra("id", event_id);
				intent3.putExtra("number",event_number);
				intent3.putExtra("name", event_name);
				intent3.putExtra("type", "event");
				intent3.putExtra("start_date", start_date_extra);
				intent3.putExtra("due_date", due_date_extra);
				intent3.putExtra("period", event_period);
				intent3.putExtra("number_achieved", number_achieved.get(0));//0
	        	startActivity(intent3);
			}
			
        });
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
	        	
	            return true;
	        case R.id.action_delete:
	        	
	        	return true;
	        case R.id.action_update:
	        
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	public boolean isToday(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
 	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
 	       				.toString().equals(today)) ? true : false;
 	}
 	
 	public boolean isTomorrow(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	Calendar c = Calendar.getInstance();
 	    	c.add(Calendar.DATE, 1);
 	    	String tomorrow = new SimpleDateFormat("MM/dd/yyyy").format(new Date(c.getTimeInMillis()));
 	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
 	       				.toString().equals(tomorrow)) ? true : false;
 	}
 	    
 	public boolean isThisWeek(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	Calendar c = Calendar.getInstance();
 	    	c.add(Calendar.DATE, 7);
 	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
 	}
 	public boolean isThisMonth(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	Calendar c = Calendar.getInstance();
 	    	c.add(Calendar.DATE, 30);
 	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
 	}
 	public boolean isThisQuarter(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	Calendar c = Calendar.getInstance();
 	    	c.add(Calendar.DATE, 90);
 	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
 	}
 	
 	public boolean isMidYear(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	Calendar c = Calendar.getInstance();
 	    	c.add(Calendar.DATE, 120);
 	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
 	}
 	public boolean isThisYear(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	Calendar c = Calendar.getInstance();
 	    	c.add(Calendar.DATE, 365);
 	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
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
	 public static class DatePickerFragment2 extends DialogFragment
		implements DatePickerDialog.OnDateSetListener {

@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
final Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH);
	int day = c.get(Calendar.DAY_OF_MONTH);

	// Create a new instance of DatePickerDialog and return it
	return new DatePickerDialog(getActivity(), this, year, month, day);
}

public void onDateSet(DatePicker view, int year, int month, int day) {
int month_value=month+1;
start_date=day+"-"+month_value+"-"+year;
startDateValue.setText(start_date);
}
}
	 public static long differenceIndays(String startDate,String endDate,String today)
	    {
		// String toDateAsString = "05/11/2010";
		 Date start_date = null;
		 Date end_date = null;
		 Date today_date=null;
		try {
			start_date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
			end_date = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
			today_date= new SimpleDateFormat("dd-MM-yyyy").parse(today);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 long starDateAsTimestamp = start_date.getTime();
		 long endDateTimestamp = end_date.getTime();
		 long todayDateTimestamp = today_date.getTime();
		 long diff = endDateTimestamp - todayDateTimestamp;
		  
		  long diffDays = diff / (24 * 60 * 60 * 1000);
		  if(todayDateTimestamp>endDateTimestamp){
		  return -1;
		  }else{
			return diffDays;
		  }
	    }
	 private String getDateTime() {
	        SimpleDateFormat dateFormat = new SimpleDateFormat(
	                "dd-MM-yyyy", Locale.getDefault());
	        Date date = new Date();
	        return dateFormat.format(date);
	}
	 
	 public static boolean isDateAfter(String startDate,String endDate)
	    {
		// String toDateAsString = "05/11/2010";
		 Date start_date = null;
		 Date end_date = null;
		try {
			start_date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
			end_date = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 long starDateAsTimestamp = start_date.getTime();
		 long endDateTimestamp = end_date.getTime();
		 long getRidOfTime = 1000 * 60 * 60 * 24;
		 long startDateAsTimestampWithoutTime = starDateAsTimestamp / getRidOfTime;
		 long endDateTimestampWithoutTime = endDateTimestamp / getRidOfTime;

		 if (startDateAsTimestampWithoutTime > endDateTimestampWithoutTime) {
		    return true;
		 } else {
		    return false;
		 }
	    }
}
