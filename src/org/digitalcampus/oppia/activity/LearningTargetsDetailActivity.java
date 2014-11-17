package org.digitalcampus.oppia.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.CoverageTargetsDetailActivity.DatePickerFragment;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.EventBaseAdapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
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
	private static TextView dueDateValue;
	private static String due_date;

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
	    imageView_status=(ImageView) findViewById(R.id.imageView_status);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
        	learning_category=extras.getString("learning_category");
        	learning_course=extras.getString("learning_course");
        	learning_topic=extras.getString("learning_topic");
			due_date=extras.getString("due_date");
			status=extras.getString("status");
			learning_id=extras.getLong("learning_id");
        }
        
        textView_category.setText(learning_category);
        textView_course.setText(learning_course);
        textView_dueDate.setText(due_date);
        textView_topic.setText(learning_topic);
        if(status.equalsIgnoreCase("updated")){
        	imageView_status.setImageResource(R.drawable.ic_achieved);
        }else if(status.equalsIgnoreCase("new_record")){
        	imageView_status.setImageResource(R.drawable.ic_loading);
        }else if(status.equalsIgnoreCase("not_achieved")){
        	imageView_status.setImageResource(R.drawable.ic_not_achieved);
        }
        
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
				Button datepickerDialog=(Button) dialog.findViewById(R.id.button_setDateDialog);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment();
						    newFragment.show(getSupportFragmentManager(), "datePicker");
						
					}
					
				});
				dueDateValue=(TextView) dialog.findViewById(R.id.textView_dueDateValue);
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
						
						String learning_category=spinner_learningCatagory.getSelectedItem().toString();
						String learning_course=spinner_learningCourse.getSelectedItem().toString();
						String learning_topic=spinner_learningDescription.getSelectedItem().toString();
					    if(db.editLearning(learning_category, learning_course,learning_topic,due_date, learning_id) ==true){
					    	LearningTargetsDetailActivity.this.runOnUiThread(new Runnable() {
								@Override
					            public void run() {
					            	
					            	
					            }
					        });	
					    	 Toast.makeText(LearningTargetsDetailActivity.this.getApplicationContext(), "Learning target edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(LearningTargetsDetailActivity.this.getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
	 				dialog.show();
	 	         
	            return true;
	        case R.id.action_delete:
	        	if(db.deleteEventCategory(learning_id)==true){
	        		Intent intent2 = new Intent(Intent.ACTION_MAIN);
		 	          intent2.setClass(LearningTargetsDetailActivity.this, NewEventPlannerActivity.class);
		 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 	          startActivity(intent2);
		 	          finish();	
	        	}
	        	return true;
	        case R.id.action_update:
	        	Intent intent3=new Intent(LearningTargetsDetailActivity.this,UpdateActivity.class);
	        	intent3.putExtra("id", learning_id);
				//intent3.putExtra("number",event_number);
				//intent3.putExtra("name", event_name);
				//intent3.putExtra("type", "event");
				//intent3.putExtra("due_date", due_date_extra);
				//intent3.putExtra("period", event_period);
	        	startActivity(intent3);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
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
}
