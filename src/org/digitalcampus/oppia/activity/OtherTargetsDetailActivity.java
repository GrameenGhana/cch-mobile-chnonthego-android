package org.digitalcampus.oppia.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.mobile.learningGF.R.color;
import org.digitalcampus.oppia.activity.EventTargetsDetailActivity.DatePickerFragment;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.EventBaseAdapter;
import org.grameenfoundation.adapters.OtherBaseAdapter;
import org.grameenfoundation.cch.utils.TextProgressBar;
import org.grameenfoundation.chnonthego.LoginActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
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

public class OtherTargetsDetailActivity extends FragmentActivity {

	private TextView textView_dueDate;
	private TextView textView_period;
	private TextView textView_name;
	private ImageView imageView_status;
	private String other_name;
	private String other_number;
	private String other_period;
	private String status;
	private long other_id;
	private TextView textView_number;
	private DbHelper db;
	private String due_date_extra;
	private Button button_edit;
	private Button button_delete;
	private Button button_update;
	private TextView textView_startDate;
	private String start_date_extra;
	private TextView textView_achieved;
	private String achieved;
	private TextView textView_percentageAchieved;
	private int today_month;
	private int today_day;
	private int today_year;
	private TextView textView_progressCheck;
	private Integer due_date_day;
	private Integer due_date_month;
	private Integer due_date_year;
	private int date_difference;
	private TextProgressBar progress_bar;
	private int progress_status;
	private static TextView dueDateValue;
	private static String due_date;
	static String start_date ;
	static long due_date_to_compare;
	private static TextView startDateValue;
	private static HashMap<String, String> updateItems;
	private static ArrayList<String> number_entered;
	private static ArrayList<String> number_remaining;
	private static ArrayList<String> number_achieved;
	private static ArrayList<String> update_id;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_event_target_detail);
	    db=new DbHelper(OtherTargetsDetailActivity.this);
	    getActionBar().setTitle("Event Planner");
	    getActionBar().setSubtitle("Other Target Details");
	    Calendar c = Calendar.getInstance();
        today_month=c.get(Calendar.MONTH)+1;
        today_day=c.get(Calendar.DAY_OF_WEEK);
        today_year=c.get(Calendar.YEAR);
	    textView_name=(TextView) findViewById(R.id.textView_name);
	    textView_period=(TextView) findViewById(R.id.textView_period);
	    textView_number=(TextView) findViewById(R.id.textView_number);
	    textView_dueDate=(TextView) findViewById(R.id.textView_dueDate);
	    textView_startDate=(TextView) findViewById(R.id.textView_startDate);
	    imageView_status=(ImageView) findViewById(R.id.imageView_status);
	    textView_achieved=(TextView) findViewById(R.id.textView_achieved);
	    textView_percentageAchieved=(TextView) findViewById(R.id.textView_percentageAchieved);
	    textView_progressCheck=(TextView) findViewById(R.id.textView_progressCheck);
	    progress_bar=(TextProgressBar) findViewById(R.id.progressBar1);
	    button_edit=(Button) findViewById(R.id.button_edit);
	    button_delete=(Button) findViewById(R.id.button_delete);
	    button_update=(Button) findViewById(R.id.button_update);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
        	other_name=extras.getString("other_name");
        	other_number=extras.getString("other_number");
        	other_period=extras.getString("other_period");
			due_date_extra=extras.getString("due_date");
			achieved=extras.getString("achieved");
			start_date_extra=extras.getString("start_date");
			status=extras.getString("status");
			other_id=extras.getLong("other_id");
        }
        String[] due_date_extra_split=due_date_extra.split("-");
        due_date_day=Integer.valueOf(due_date_extra_split[0]);
        due_date_month=Integer.valueOf(due_date_extra_split[1]);
        due_date_year=Integer.valueOf(due_date_extra_split[2]);
        date_difference=due_date_day-today_day;
        int number_achieved_current=Integer.valueOf(achieved);
        int number_entered_current=Integer.valueOf(other_number);
        Double percentage=((double)number_achieved_current/number_entered_current)*100;
        String percentage_achieved=String.format("%.0f", percentage);
        progress_status=(int) percentage.doubleValue();
        progress_bar.setProgress(progress_status);
        progress_bar.setText(percentage_achieved+"%");
        progress_bar.setTextColor(color.TextColorWine);
        textView_percentageAchieved.setText(percentage_achieved+"%");
        textView_name.setText(other_name);
        textView_period.setText(other_period);
        textView_dueDate.setText(due_date_extra);
        textView_number.setText(other_number);
        textView_startDate.setText(start_date_extra);
        textView_achieved.setText(achieved);
        if(achieved==other_number){
        	button_edit.setClickable(false);
        	button_delete.setClickable(false);
        	button_update.setClickable(false);
        }
        /*
        if(date_difference==4&&due_date_month==today_month&&due_date_year==today_year&&percentage<50){
        	textView_progressCheck.setText("You are behind. Please work hard to improve!");	
        	imageView_status.setImageResource(R.drawable.sad);
            }else {
            	textView_progressCheck.setText("You are on track. Keep up the good work");	
            	imageView_status.setImageResource(R.drawable.ic_achieved_smile);
            }*/
        
        if(status.equalsIgnoreCase("updated")){
        	imageView_status.setImageResource(R.drawable.ic_achieved_smile);
        }else if(status.equalsIgnoreCase("new_record")){
        	imageView_status.setImageResource(R.drawable.ic_achieved_waiting);
        }else if(status.equalsIgnoreCase("not_achieved")){
        	imageView_status.setImageResource(R.drawable.ic_not_achieved);
        }
        button_edit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(OtherTargetsDetailActivity.this);
				dialog.setContentView(R.layout.event_add_dialog);
				dialog.setTitle("Edit Other Target");
				final EditText editText_otherCategory=(EditText) dialog.findViewById(R.id.editText_dialogOtherName);
				editText_otherCategory.setText(other_name);
				final Spinner spinner_otherPeriod=(Spinner) dialog.findViewById(R.id.spinner_dialogOtherPeriod);
				String[] items={"Daily","Weekly","Monthly","Annually","Quarterly","Mid-year"};
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(OtherTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
				spinner_otherPeriod.setAdapter(adapter);
				int spinner_position=adapter.getPosition(other_period);
				spinner_otherPeriod.setSelection(spinner_position);
				final EditText editText_otherNumber=(EditText) dialog.findViewById(R.id.editText_dialogOtherNumber);
				editText_otherNumber.setText(other_number);
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddEvent);
				dialogButton.setText("Save");
				
				ImageButton datepickerDialog=(ImageButton) dialog.findViewById(R.id.imageButton_dueDate);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment();
						    newFragment.show(getSupportFragmentManager(), "datePicker");
						
					}
					
				});
				
				ImageButton datepickerDialog2=(ImageButton) dialog.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment2();
						    newFragment.show(getSupportFragmentManager(), "datePicker2");
						
					}
					
				});
				dueDateValue=(TextView) dialog.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) dialog.findViewById(R.id.textView_startDate);
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
						String duration=null;
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
						String other_category=editText_otherCategory.getText().toString();
						String other_number=editText_otherNumber.getText().toString();
						String other_period=spinner_otherPeriod.getSelectedItem().toString();
					    if(db.editOther(other_category, other_number, other_period,duration,start_date,due_date, other_id) ==true){
					    	editText_otherCategory.setText(" ");
					    	editText_otherNumber.setText(" ");
					    	 Toast.makeText(OtherTargetsDetailActivity.this, "Target edtied successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(OtherTargetsDetailActivity.this, "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
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
						OtherTargetsDetailActivity.this);
		 
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
								if(db.deleteOtherCategory(other_id)==true){
					        		Intent intent2 = new Intent(Intent.ACTION_MAIN);
						 	          intent2.setClass(OtherTargetsDetailActivity.this, NewEventPlannerActivity.class);
						 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						 	          startActivity(intent2);
						 	          finish();	
						 	         Toast.makeText(getApplicationContext().getApplicationContext(), "Deleted successfully!",
									         Toast.LENGTH_LONG).show();
					        	}
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
				//updateItems=db.getAllForUpdate(other_name,other_id);
				//number_entered=new ArrayList<String>();
				//number_achieved=new ArrayList<String>();
				//number_remaining=new ArrayList<String>();
				//update_id=new ArrayList<String>();
					number_achieved=db.getForUpdateOtherNumberAchieved(other_id,other_period);
					number_remaining=db.getAllForOtherNumberRemaining(other_period);
					//update_id.add(updateItems.get("justification_id"));
					
				Intent intent3=new Intent(OtherTargetsDetailActivity.this,UpdateActivity.class);
	        	intent3.putExtra("id", other_id);
				intent3.putExtra("number",other_number);
				intent3.putExtra("name", other_name);
				intent3.putExtra("type", "other");
				intent3.putExtra("start_date", start_date_extra);
				intent3.putExtra("due_date", due_date_extra);
				intent3.putExtra("period", other_period);
				intent3.putExtra("number_achieved", number_achieved.get(0));//0
				intent3.putExtra("number_remaining", number_remaining.get(0));//number entered
				//intent3.putExtra("update_id", update_id.get(0));
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
	 	          goHome.setClass(OtherTargetsDetailActivity.this, MainScreenActivity.class);
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
}
