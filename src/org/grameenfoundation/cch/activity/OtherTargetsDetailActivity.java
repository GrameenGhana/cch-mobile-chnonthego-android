package org.grameenfoundation.cch.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.MainScreenActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.cch.caldroid.CaldroidFragment;
import org.grameenfoundation.cch.caldroid.CaldroidListener;
import org.grameenfoundation.cch.model.Validation;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.grameenfoundation.cch.utils.TextProgressBar;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

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
	private String details;
	private static TextView startDateValue;
	private static ArrayList<String> number_entered;
	private static ArrayList<String> number_achieved;
	private static ArrayList<String> update_id;
	private long end_time;
	private long start_time;
	private String last_updated;
	private TextView day_difference;
	private TableRow goal_layout;
	private EditText editText_otherCategory;
	private EditText editText_otherNumber;
	public long other_old_id;
	private MaterialSpinner spinner_otherPeriod;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_event_target_detail);
	    db=new DbHelper(OtherTargetsDetailActivity.this);
	    getActionBar().setTitle("Event Planner");
	    getActionBar().setSubtitle("Other Target Details");
	    start_time=System.currentTimeMillis();
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
	    day_difference=(TextView) findViewById(R.id.textView_dayDifference);
	    goal_layout=(TableRow) findViewById(R.id.tableRow_goal);
	    button_edit=(Button) findViewById(R.id.button_edit);
	    button_delete=(Button) findViewById(R.id.button_delete);
	    button_update=(Button) findViewById(R.id.button_update);
	    new GetData().execute();
        button_edit.setOnClickListener(new OnClickListener(){

			private RadioGroup personal;
		

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(OtherTargetsDetailActivity.this);
				dialog.setContentView(R.layout.event_add_dialog);
				dialog.setTitle("Edit Other Target");
				editText_otherCategory=(EditText) dialog.findViewById(R.id.editText_dialogOtherName);
				editText_otherCategory.setText(other_name);
				spinner_otherPeriod=(MaterialSpinner) dialog.findViewById(R.id.spinner_dialogOtherPeriod);
				String[] items=getResources().getStringArray(R.array.ReminderFrequency);
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(OtherTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
				spinner_otherPeriod.setAdapter(adapter);
				int spinner_position=adapter.getPosition(other_period);
				spinner_otherPeriod.setSelection(spinner_position);
				final Button button_show=(Button) dialog.findViewById(R.id.button_show);
				button_show.setVisibility(View.GONE);
				editText_otherNumber=(EditText) dialog.findViewById(R.id.editText_dialogOtherNumber);
				editText_otherNumber.setText(other_number);
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddEvent);
				dialogButton.setText("Save");
				dueDateValue=(TextView) dialog.findViewById(R.id.textView_dueDateValue);
				dueDateValue.setText(due_date_extra);
				startDateValue=(TextView) dialog.findViewById(R.id.textView_startDate);
				startDateValue.setText(start_date_extra);
				final LinearLayout number_layout=(LinearLayout) dialog.findViewById(R.id.LinearLayout_number);
			if(!other_number.equals("0")){
				number_layout.setVisibility(View.GONE);
			}else{
				number_layout.setVisibility(View.VISIBLE);
			}
				RadioGroup enter_number=(RadioGroup) dialog.findViewById(R.id.radioGroup1);
				
				personal=(RadioGroup) dialog.findViewById(R.id.radioGroup_personal);
				if(details.equals("personal")){
					personal.check(R.id.radio_personalYes);
				}else if(details.equals("not_personal")){
					personal.check(R.id.radio_personalNo);
				}	else{
					personal.check(R.id.radio_personalNo);
				}
				personal.check(R.id.radio_personalNo);
				final RadioButton yesRadioButton;
				final RadioButton noRadioButton;
				yesRadioButton = (RadioButton) dialog.findViewById(R.id.radio_yes);
				noRadioButton = (RadioButton) dialog.findViewById(R.id.radio_no);
				enter_number.setOnCheckedChangeListener(new OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId==R.id.radio_no) {
							number_layout.setVisibility(View.GONE);
							//other_number="0";
						}else if(checkedId==R.id.radio_yes){
							number_layout.setVisibility(View.VISIBLE);
						}
					}
				});
				ImageButton datepickerDialog=(ImageButton) dialog.findViewById(R.id.imageButton_dueDate);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", today_month, today_year);
						dialogCaldroidFragment.show(getSupportFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	dueDateValue.setText(formatter.format(date));
						    	due_date=formatter.format(date);
						        Toast.makeText(OtherTargetsDetailActivity.this, formatter.format(date),
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
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	startDateValue.setText(formatter.format(date));
						    	start_date=formatter.format(date);
						        Toast.makeText(OtherTargetsDetailActivity.this, formatter.format(date),
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
						String other_category=editText_otherCategory.getText().toString();
						String other_number = null;
						if(noRadioButton.isChecked()){
			      			other_number="0";
			      		}else if (yesRadioButton.isChecked()){
			      			other_number=editText_otherNumber.getText().toString();
			      		}
						String other_period=spinner_otherPeriod.getSelectedItem().toString();
						String duration=" ";
						if(!checkValidation()){
				      		Toast.makeText(getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();
				      	}
				      		else{
				      			if(personal.getCheckedRadioButtonId()==R.id.radio_personalYes){
				      			if(db.editTarget(other_category,"",MobileLearning.CCH_TARGET_PERSONAL, other_number,startDateValue.getText().toString(),dueDateValue.getText().toString(), other_period, other_id) ==true){
				      				number_achieved=db.getNumberAchieved(other_id,other_period,MobileLearning.CCH_TARGET_STATUS_NEW);
				      				JSONObject json = new JSONObject();
				      				try {
				      					if(other_old_id!=0){
											 json.put("id", other_old_id);
										 }else{
											 json.put("id", other_id);
										 }
				      					json.put("target_type", other_category);
				      					json.put("category", "other");
				      					json.put("target_number", other_number);
				      					json.put("achieved_number", achieved);
				      					json.put("last_updated", last_updated);
				      					json.put("start_date", start_date_extra);
				      					json.put("due_date", due_date_extra);
				      					json.put("details", "personal");
				      					json.put("changed", 1);
				      					json.put("deleted", 0);
				      					 json.put("ver", db.getVersionNumber(OtherTargetsDetailActivity.this));
											json.put("battery", db.getBatteryStatus(OtherTargetsDetailActivity.this));
									    	json.put("device", db.getDeviceName());
									    	json.put("imei", db.getDeviceImei(OtherTargetsDetailActivity.this));
				      				} catch (JSONException e) {
				      					e.printStackTrace();
				      				}
				      				end_time=System.currentTimeMillis();
				      				db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
				      				Intent intent2 = new Intent(Intent.ACTION_MAIN);
				      				intent2.setClass(OtherTargetsDetailActivity.this, NewEventPlannerActivity.class);
				      				intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				      				startActivity(intent2);
				      				finish();	
				      				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
				      				Toast.makeText(OtherTargetsDetailActivity.this, "Target edtied successfully!",
							         Toast.LENGTH_LONG).show();
				      			}else{
				      				Toast.makeText(OtherTargetsDetailActivity.this, "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
				      			}
				      		}else if(personal.getCheckedRadioButtonId()==R.id.radio_personalNo){
				      			if(db.editTarget(other_category,"",MobileLearning.CCH_TARGET_NOT_PERSONAL, other_number,startDateValue.getText().toString(),dueDateValue.getText().toString(),other_period, other_id) ==true){
				      				number_achieved=db.getNumberAchieved(other_id,other_period,MobileLearning.CCH_TARGET_STATUS_NEW);
				      				JSONObject json = new JSONObject();
				      				try {
				      					if(other_old_id!=0){
											 json.put("id", other_old_id);
										 }else{
											 json.put("id", other_id);
										 }
				      					json.put("target_type", other_category);
				      					json.put("category", "other");
				      					json.put("target_number", other_number);
				      					json.put("achieved_number", achieved);
				      					json.put("last_updated", last_updated);
				      					json.put("start_date", start_date_extra);
				      					json.put("due_date", due_date_extra);
				      					json.put("details", "not_personal");
				      					json.put("changed", 1);
				      					json.put("deleted", 0);
				      					 json.put("ver", db.getVersionNumber(OtherTargetsDetailActivity.this));
											json.put("battery", db.getBatteryStatus(OtherTargetsDetailActivity.this));
									    	json.put("device", db.getDeviceName());
									    	json.put("imei", db.getDeviceImei(OtherTargetsDetailActivity.this));
				      				} catch (JSONException e) {
				      					e.printStackTrace();
				      				}
				      				end_time=System.currentTimeMillis();
				      				db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
				      				Intent intent2 = new Intent(Intent.ACTION_MAIN);
				      				intent2.setClass(OtherTargetsDetailActivity.this, NewEventPlannerActivity.class);
				      				intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				      				startActivity(intent2);
				      				finish();	
				      				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
				      				Toast.makeText(OtherTargetsDetailActivity.this, "Target edtied successfully!",
							         Toast.LENGTH_LONG).show();
				      			}else{
				      				Toast.makeText(OtherTargetsDetailActivity.this, "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
				      			}
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
								if(db.deleteTarget(other_id)==true){
									number_achieved=db.getNumberAchieved(other_id,other_period,MobileLearning.CCH_TARGET_STATUS_NEW);
									JSONObject json = new JSONObject();
									 try {
										 if(other_old_id!=0){
											 json.put("id", other_old_id);
										 }else{
											 json.put("id", other_id);
										 }
										json.put("target_type", other_name);
										 json.put("category", "other");
										 json.put("target_number", other_number);
										 json.put("last_updated", last_updated);
										 json.put("achieved_number", achieved);
										 json.put("start_date", start_date_extra);
										 json.put("due_date", due_date_extra);
										 json.put("changed", 0);
										 json.put("deleted", 1);
										 json.put("ver", db.getVersionNumber(OtherTargetsDetailActivity.this));
											json.put("battery", db.getBatteryStatus(OtherTargetsDetailActivity.this));
									    	json.put("device", db.getDeviceName());
									    	json.put("imei", db.getDeviceImei(OtherTargetsDetailActivity.this));
									} catch (JSONException e) {
										e.printStackTrace();
									}
									 end_time=System.currentTimeMillis();
									 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
					        		Intent intent2 = new Intent(Intent.ACTION_MAIN);
						 	          intent2.setClass(OtherTargetsDetailActivity.this, NewEventPlannerActivity.class);
						 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						 	          startActivity(intent2);
						 	          finish();	
						 	         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
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
				number_achieved=db.getNumberAchieved(other_id,other_period,MobileLearning.CCH_TARGET_STATUS_NEW);
				Intent intent3=new Intent(Intent.ACTION_MAIN);
				intent3.setClass(OtherTargetsDetailActivity.this,UpdateActivity.class);
				intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	intent3.putExtra("id", other_id);
	        	intent3.putExtra("old_id", other_old_id);
				intent3.putExtra("number",other_number);
				intent3.putExtra("name", other_name);
				intent3.putExtra("type", "other");
				intent3.putExtra("start_date", start_date_extra);
				intent3.putExtra("due_date", due_date_extra);
				intent3.putExtra("period", other_period);
				intent3.putExtra("number_achieved", number_achieved.get(0));//0
	        	startActivity(intent3);
				finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
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
	 	         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
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

	public static long differenceIndays(String startDate,String endDate,String today)
	 {
			// String toDateAsString = "05/11/2010";
			 Date start_date = null;
			 Date end_date = null;
			 Date today_date=null;
			 long starDateAsTimestamp=0;
			long endDateTimestamp=0;
			try {
				today_date= new SimpleDateFormat("dd-MM-yyyy").parse(today);
				if(startDate!=null){
					start_date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
					 starDateAsTimestamp = start_date.getTime();
				}else{
					System.out.println("Enter a valid date!");
				}
				if(endDate!=null){
					end_date = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
					  endDateTimestamp = end_date.getTime();
				}else {
					System.out.println("Enter a valid date!");	
				}
				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		long starDateAsTimestamp = 0;
		long endDateTimestamp = 0;
		try {
	if(startDate!=null){
		start_date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
		starDateAsTimestamp = start_date.getTime();
			}else{
				System.out.println("Enter a valid date!");
			}
			if(endDate!=null){
				end_date = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
				endDateTimestamp = end_date.getTime();
				
			}else{
				System.out.println("Enter a valid date!");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 long getRidOfTime = 1000 * 60 * 60 * 24;
		 long startDateAsTimestampWithoutTime = starDateAsTimestamp / getRidOfTime;
		 long endDateTimestampWithoutTime = endDateTimestamp / getRidOfTime;

		 if (startDateAsTimestampWithoutTime > endDateTimestampWithoutTime) {
		    return true;
		 } else {
		    return false;
		 }
	    }
	 private class GetData extends AsyncTask<Object, Void, Object> {
		 DbHelper db=new DbHelper(OtherTargetsDetailActivity.this);
		private Double percentage;
		private String percentage_achieved;
		

	    @Override
	    protected Object doInBackground(Object... params) {
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
	 			other_old_id=extras.getLong("old_id");
	 			last_updated=extras.getString("last_updated");
	 			details=extras.getString("detail");
	         }
	     
	    
	         
				return null;
	    }

	    @Override
	    protected void onPostExecute(Object result) {
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
	    	 if(!other_number.equals("0")){
	    	        int number_achieved_current=Integer.valueOf(achieved);
	    	        int number_entered_current=Integer.valueOf(other_number);
	    	        Double percentage=((double)number_achieved_current/number_entered_current)*100;
	    	        String percentage_achieved=String.format("%.0f", percentage);
	    	        progress_status=(int) percentage.doubleValue();
	    	        progress_bar.setProgress(progress_status);
	    	        progress_bar.setPrefixText(percentage_achieved+"%");
	    	        progress_bar.setPrefixText(" ");
	    	        textView_dueDate.setText(due_date_extra);
	    	        textView_startDate.setText(start_date_extra);
	    	        textView_name.setText(other_name);
	    	        textView_period.setText(other_period);
	    	        textView_achieved.setText(achieved);
	    	        textView_percentageAchieved.setText(percentage_achieved+"%");
	    	        textView_number.setText(other_number);
	    	       }else{
	    	    	   textView_dueDate.setText(due_date_extra);
	    	           textView_startDate.setText(start_date_extra);
	    	    	   textView_name.setText(other_name);
	    	           textView_period.setText(other_period);
	    	    	   goal_layout.setVisibility(View.GONE);
	    	    	   progress_bar.setVisibility(View.GONE);

	    	       }
	    }
	}
	 
	 private boolean checkValidation() {
	        boolean ret = true;
	 
	        if (!Validation.hasTextTextView(startDateValue)) ret = false;
	        if (!Validation.hasTextEditText(editText_otherCategory)) ret = false;
	        if (!Validation.hasTextTextView(dueDateValue)) ret = false;
	        if (!Validation.hasSelection(spinner_otherPeriod)) ret=false;
	        if (editText_otherNumber.isShown()&&!Validation.hasTextEditText(editText_otherNumber)) ret = false;
	        if (Validation.isDateAfter(startDateValue.getText().toString(),dueDateValue.getText().toString(),startDateValue)) ret = false;
	        return ret;
	    }	
}
