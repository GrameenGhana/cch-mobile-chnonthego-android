package org.digitalcampus.oppia.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.CoverageTargetsDetailActivity.DatePickerFragment;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.EventBaseAdapter;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LearningTargetsDetailActivity extends FragmentActivity {

	
	private ImageView imageView_status;
	private String learning_category;
	private String learning_course;
	private String learning_topic;
	private String status;
	private long learning_id;
	private DbHelper db;
	private TextView textView_category;
	private TextView textView_course;
	private TextView textView_topic;
	private TextView textView_dueDate;
	private Button button_edit;
	private Button button_delete;
	private Button button_update;
	private String start_date_extra;
	private TextView textView_startDate;
	private static TextView dueDateValue;
	private static String due_date;
	private static String due_date_extra;
	static String start_date ;
	static long due_date_to_compare;
	private static TextView startDateValue;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_learning_target_detail);
	    db=new DbHelper(LearningTargetsDetailActivity.this);
	    getActionBar().setTitle("Event Planner");
	    getActionBar().setSubtitle("Target Details");
	    textView_category=(TextView) findViewById(R.id.textView_learningCategory);
	    textView_course=(TextView) findViewById(R.id.textView_learningCourse);
	    textView_topic=(TextView) findViewById(R.id.textView_learningTopic);
	    textView_dueDate=(TextView) findViewById(R.id.textView_dueDate);
	    textView_startDate=(TextView) findViewById(R.id.textView_startDate);
	    imageView_status=(ImageView) findViewById(R.id.imageView_status);
	    button_edit=(Button) findViewById(R.id.button_edit);
	    button_delete=(Button) findViewById(R.id.button_delete);
	    button_update=(Button) findViewById(R.id.button_update);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
        	learning_category=extras.getString("learning_category");
        	learning_course=extras.getString("learning_course");
        	learning_topic=extras.getString("learning_topic");
			due_date_extra=extras.getString("due_date");
			start_date_extra=extras.getString("start_date");
			status=extras.getString("status");
			learning_id=extras.getLong("learning_id");
        }
        
        textView_category.setText(learning_category);
        textView_course.setText(learning_course);
        textView_dueDate.setText(due_date_extra);
        textView_topic.setText(learning_topic);
        textView_startDate.setText(start_date_extra);
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
				final Dialog dialog = new Dialog(LearningTargetsDetailActivity.this);
				dialog.setContentView(R.layout.learning_add_dialog);
				dialog.setTitle("Edit Learning Target");
				final Spinner spinner_learningCatagory=(Spinner) dialog.findViewById(R.id.spinner_learningHeader);
				String[] items={"Family Planning"};
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
				spinner_learningCatagory.setAdapter(adapter);
				final Spinner spinner_learningCourse=(Spinner) dialog.findViewById(R.id.spinner_learningCourse);
				String[] items2={"Family Planning 101","Family Planning Counselling",
								"Family Planning for people living with HIV","Hormonal Contraceptives",
								"Postpartum Family Planning"};
				final Spinner spinner_learningDescription=(Spinner) dialog.findViewById(R.id.spinner_learningDescription);
				ArrayAdapter<String> adapter2=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items2);
				spinner_learningCourse.setAdapter(adapter2);
				
				spinner_learningCourse.setOnItemSelectedListener(new OnItemSelectedListener(){
					
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						switch (position){
						case 0:
							String[] items3={"Rationale for voluntary family planning",
									"Family Planning method considerations",
									"Short-acting contraceptive methods",
									"Long-acting contraceptive methods",
									"Special needs"};
					ArrayAdapter<String> adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items3);
					spinner_learningDescription.setAdapter(adapter3);
							break;
						case 1:
							String[] items4={"Family planning counselling",
									"Family planning counselling skills"};
					ArrayAdapter<String> adapter4=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items4);
					spinner_learningDescription.setAdapter(adapter4);
							break;
						case 2:
							String[] items5={"Family Planning/Reproductive Health",
									"Family planning for people living with HIV",
									"Reproductive Health",
									"Helping Clients Make a Family Planning",
									"Family Planning in PMTCT Services"};
					ArrayAdapter<String> adapter5=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items5);
					spinner_learningDescription.setAdapter(adapter5);
							break;
						case 3:
							String[] items6={"Hormonal Contraceptives",
									"Oral contraceptives",
									"Emergency contraceptive pills",
									"Injectable contraceptives",
									"Implants",
									"Benefits and risks of hormonal contraceptives"};
					ArrayAdapter<String> adapter6=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items6);
					spinner_learningDescription.setAdapter(adapter6);
							break;
						case 4:
							String[] items7={"Rationale for postpartum family planning",
									"Contraceptive method considerations",
									"Service delivery:Clinical Considerations",
									"Service delivery:Integration and linkage"};
					ArrayAdapter<String> adapter7=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items7);
					spinner_learningDescription.setAdapter(adapter7);
							break;
						}
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}

					
					
				});
				
				int spinner_position=adapter2.getPosition(learning_course);
				spinner_learningDescription.setSelection(spinner_position);
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddLearning);
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
				
				Button cancel=(Button) dialog.findViewById(R.id.button_cancel);
				cancel.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
					dialog.dismiss();
						
					}
					
				});
				dueDateValue=(TextView) dialog.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) dialog.findViewById(R.id.textView_startDate);
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
						String learning_category=spinner_learningCatagory.getSelectedItem().toString();
						String learning_course=spinner_learningCourse.getSelectedItem().toString();
						String learning_topic=spinner_learningDescription.getSelectedItem().toString();
					    if(db.editLearning(learning_category, learning_course,learning_topic,duration,start_date,due_date, learning_id) ==true){
					    	
					    	 Toast.makeText(LearningTargetsDetailActivity.this.getApplicationContext(), "Learning target edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(LearningTargetsDetailActivity.this.getApplicationContext(), "Oops! Something went wrong. Please try again",
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
						LearningTargetsDetailActivity.this);
		 
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
								if(db.deleteLearningCategory(learning_id)==true){
					        		Intent intent2 = new Intent(Intent.ACTION_MAIN);
						 	          intent2.setClass(LearningTargetsDetailActivity.this, NewEventPlannerActivity.class);
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
				Intent intent3=new Intent(LearningTargetsDetailActivity.this,UpdateActivity.class);
	        	intent3.putExtra("id", learning_id);
				//intent3.putExtra("number",event_number);
				intent3.putExtra("learning_topic", learning_topic);
				intent3.putExtra("type", "learning");
				intent3.putExtra("due_date", due_date_extra);
				intent3.putExtra("start_date", start_date_extra);
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
	 	          goHome.setClass(LearningTargetsDetailActivity.this, MainScreenActivity.class);
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
