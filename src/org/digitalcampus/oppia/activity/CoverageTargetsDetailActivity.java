package org.digitalcampus.oppia.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.EventTargetsDetailActivity.DatePickerFragment;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.CoverageListAdapter;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class CoverageTargetsDetailActivity extends FragmentActivity {

	private TextView textView_dueDate;
	private TextView textView_period;
	private TextView textView_name;
	private ImageView imageView_status;
	private static String due_date;
	private String status;
	private long coverage_id;
	private TextView textView_number;
	private DbHelper db;
	private RadioGroup category_options;
	private String[] items3;
	private String coverage_name;
	private String coverage_number;
	private String coverage_period;
	private String due_date_extra;
	private static TextView dueDateValue;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_event_target_detail);
	    db=new DbHelper(CoverageTargetsDetailActivity.this);
	    getActionBar().setTitle("Event Planner");
	    getActionBar().setSubtitle("Target Details");
	    textView_name=(TextView) findViewById(R.id.textView_name);
	    textView_period=(TextView) findViewById(R.id.textView_period);
	    textView_number=(TextView) findViewById(R.id.textView_number);
	    textView_dueDate=(TextView) findViewById(R.id.textView_dueDate);
	    imageView_status=(ImageView) findViewById(R.id.imageView_status);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
        	coverage_name=extras.getString("coverage_name");
			coverage_number=extras.getString("coverage_number");
			coverage_period=extras.getString("coverage_period");
			due_date_extra=extras.getString("due_date");
			status=extras.getString("status");
			coverage_id=extras.getLong("coverage_id");
        }
        
        textView_name.setText(coverage_name);
        textView_period.setText(coverage_period);
        textView_dueDate.setText(due_date_extra);
        textView_number.setText(coverage_number);
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
	 	          goHome.setClass(CoverageTargetsDetailActivity.this, MainScreenActivity.class);
	 	          goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(goHome);
	 	          finish();
	 	         
	            return true;
	        case R.id.action_edit:
	        	final Dialog dialog = new Dialog(CoverageTargetsDetailActivity.this);
				dialog.setContentView(R.layout.coverage_add_dialog);
				final Spinner spinner_coverageName=(Spinner) dialog.findViewById(R.id.spinner_dialogCoverageName);
				dialog.setTitle("Edit Coverage Target");
				 category_options=(RadioGroup) dialog.findViewById(R.id.radioGroup_category);
				  category_options.check(R.id.radio_people);
				  items3=new String[]{"0 - 11 months","12 - 23 months",
							"24 -59 months","Women in fertile age",
							"Expected pregnancy"};
					ArrayAdapter<String> adapter3=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items3);
					spinner_coverageName.setAdapter(adapter3);
				    category_options.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				    	
						public void onCheckedChanged(
								RadioGroup buttonView,
								int isChecked) {
							if (isChecked == R.id.radio_people) {
								items3=new String[]{"0 - 11 months","12 - 23 months",
										"24 -59 months","Women in fertile age",
										"Expected pregnancy"};
								ArrayAdapter<String> adapter3=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items3);
								spinner_coverageName.setAdapter(adapter3);
								int spinner_position=adapter3.getPosition(coverage_name);
								spinner_coverageName.setSelection(spinner_position);
							} else if (isChecked == R.id.radio_immunization) {
								items3=new String[]{"BCG",
										"Penta 3","OPV 0","OPV 1","OPV 2","OPV 3","Rota 1","Rota 2",
										"PCV 3","Measles 2","Measles Rubella","Yellow fever","Vit A 6-11 months","Vit A 12-59 months"};
								ArrayAdapter<String> adapter4=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items3);
								spinner_coverageName.setAdapter(adapter4);
								int spinner_position2=adapter4.getPosition(coverage_name);
								spinner_coverageName.setSelection(spinner_position2);
							}	
						}
				    });
				    String[] items2={"Daily","Weekly","Monthly","Annually","Quarterly","Mid-year"};
				final Spinner spinner_coveragePeriod=(Spinner) dialog.findViewById(R.id.spinner_coveragePeriod);
				ArrayAdapter<String> spinner_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items2);
				spinner_coveragePeriod.setAdapter(spinner_adapter);
				
				final EditText editText_coverageNumber=(EditText) dialog.findViewById(R.id.editText_dialogCoverageNumber);
				editText_coverageNumber.setText(coverage_period);
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddCoverage);
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
						
						String coverage_name=spinner_coverageName.getSelectedItem().toString();
						String coverage_period=spinner_coveragePeriod.getSelectedItem().toString();
						String coverage_number=editText_coverageNumber.getText().toString();
					    if(db.editCoverage(coverage_name, coverage_number, coverage_period,due_date, coverage_id) ==true){
					    	CoverageTargetsDetailActivity.this.runOnUiThread(new Runnable() {
					            @Override
					            public void run() {
					            	
					            	
					            }
					        });	
					    	 Toast.makeText(CoverageTargetsDetailActivity.this.getApplicationContext(), "Coverage target edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(CoverageTargetsDetailActivity.this.getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
	 				dialog.show();
	 	         
	            return true;
	        case R.id.action_delete:
	        	if(db.deleteCoverageCategory(coverage_id)==true){
	        		Intent intent2 = new Intent(Intent.ACTION_MAIN);
		 	          intent2.setClass(CoverageTargetsDetailActivity.this, NewEventPlannerActivity.class);
		 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 	          startActivity(intent2);
		 	          finish();	
	        	}
	         	return true;
	        case R.id.action_update:
	        	Intent intent3=new Intent(CoverageTargetsDetailActivity.this,UpdateActivity.class);
	        	intent3.putExtra("id", coverage_id);
				intent3.putExtra("number",coverage_number);
				intent3.putExtra("name", coverage_name);
				intent3.putExtra("type", "event");
				intent3.putExtra("due_date", due_date_extra);
				intent3.putExtra("period", coverage_period);
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
