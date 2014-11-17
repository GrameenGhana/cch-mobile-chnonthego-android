package org.digitalcampus.oppia.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.EventTargetsDetailActivity.DatePickerFragment;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.EventBaseAdapter;
import org.grameenfoundation.adapters.OtherBaseAdapter;

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
	private static TextView dueDateValue;
	private static String due_date;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_event_target_detail);
	    db=new DbHelper(OtherTargetsDetailActivity.this);
	    getActionBar().setTitle("Event Planner");
	    getActionBar().setSubtitle("Target Details");
	    textView_name=(TextView) findViewById(R.id.textView_name);
	    textView_period=(TextView) findViewById(R.id.textView_period);
	    textView_number=(TextView) findViewById(R.id.textView_number);
	    textView_dueDate=(TextView) findViewById(R.id.textView_dueDate);
	    imageView_status=(ImageView) findViewById(R.id.imageView_status);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
        	other_name=extras.getString("other_name");
        	other_number=extras.getString("other_number");
        	other_period=extras.getString("other_period");
			due_date_extra=extras.getString("due_date");
			status=extras.getString("status");
			other_id=extras.getLong("other_id");
        }
        
        textView_name.setText(other_name);
        textView_period.setText(other_period);
        textView_dueDate.setText(due_date_extra);
        textView_number.setText(other_number);
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
	 	          goHome.setClass(OtherTargetsDetailActivity.this, MainScreenActivity.class);
	 	          goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(goHome);
	 	          finish();
	 	         
	            return true;
	        case R.id.action_edit:
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
						
						String other_category=editText_otherCategory.getText().toString();
						String other_number=editText_otherNumber.getText().toString();
						String other_period=spinner_otherPeriod.getSelectedItem().toString();
					    if(db.editOther(other_category, other_number, other_period,due_date, other_id) ==true){
					    	OtherTargetsDetailActivity.this.runOnUiThread(new Runnable() {
								@Override
					            public void run() {
									
								    
					            }
					        });	
					    	 Toast.makeText(OtherTargetsDetailActivity.this, "Target edtied successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(OtherTargetsDetailActivity.this, "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
	 				dialog.show();
	 	         
	            return true;
	        case R.id.action_delete:
	        	if(db.deleteOtherCategory(other_id)==true){
	        		Intent intent2 = new Intent(Intent.ACTION_MAIN);
		 	          intent2.setClass(OtherTargetsDetailActivity.this, NewEventPlannerActivity.class);
		 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 	          startActivity(intent2);
		 	          finish();	
	        	}
	        	return true;
	        case R.id.action_update:
	        	Intent intent3=new Intent(OtherTargetsDetailActivity.this,UpdateActivity.class);
	        	intent3.putExtra("id", other_id);
				intent3.putExtra("number",other_number);
				intent3.putExtra("name", other_name);
				intent3.putExtra("type", "event");
				intent3.putExtra("due_date", due_date_extra);
				intent3.putExtra("period", other_period);
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
