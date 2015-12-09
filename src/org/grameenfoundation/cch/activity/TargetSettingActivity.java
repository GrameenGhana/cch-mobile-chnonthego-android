package org.grameenfoundation.cch.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.service.TrackerService;
import org.grameenfoundation.cch.caldroid.CaldroidFragment;
import org.grameenfoundation.cch.caldroid.CaldroidListener;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.Validation;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
public class TargetSettingActivity extends FragmentActivity implements OnSharedPreferenceChangeListener{
	private Context mContext;															
	private DbHelper db;
	 public static final String ARG_SECTION_NUMBER = "section_number";       
	private Button button_show;
	private ArrayList<EventTargets> eventId;
	private long coverageId;
	private long otherId;
	private long learningId;
	private EditText editText_otherCategory;
	private EditText editText_otherNumber;
	private RadioGroup personal;
	private MaterialSpinner spinner_otherPeriod;
	static String due_date ;
	private static TextView dueDateValue;
	static String start_date ;
	private static TextView startDateValue;
	static long due_date_to_compare;
	
	private static final String EVENT_PLANNER_ID = "Event Planner";
	public static final String TAG = NewEventPlannerActivity.class.getSimpleName();
	public static String month_passed = null;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
	private SharedPreferences prefs;
	private Calendar c;
	protected long start_time;
	protected long end_time;
	DateTime currentDate;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.event_add_dialog);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Target Setting");
	    mContext=TargetSettingActivity.this;
	    db=new DbHelper(mContext);
	    currentDate=new DateTime();
	    start_time=System.currentTimeMillis();
	   button_show=(Button) findViewById(R.id.button_show);
	   	editText_otherCategory=(EditText) findViewById(R.id.editText_dialogOtherName);
		spinner_otherPeriod=(MaterialSpinner) findViewById(R.id.spinner_dialogOtherPeriod);
		String[] items=getResources().getStringArray(R.array.ReminderFrequency);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items);
		spinner_otherPeriod.setAdapter(adapter);
		editText_otherNumber=(EditText) findViewById(R.id.editText_dialogOtherNumber);
		Button dialogButton = (Button) findViewById(R.id.button_dialogAddEvent);
		dialogButton.setText("Save");
		dueDateValue=(TextView) findViewById(R.id.textView_dueDateValue);
		startDateValue=(TextView) findViewById(R.id.textView_startDate);
		final LinearLayout number_layout=(LinearLayout) findViewById(R.id.LinearLayout_number);
		number_layout.setVisibility(View.GONE);
		RadioGroup enter_number=(RadioGroup) findViewById(R.id.radioGroup1);
		personal=(RadioGroup) findViewById(R.id.radioGroup_personal);
		final RadioButton yesRadioButton;
		final RadioButton noRadioButton;
		yesRadioButton = (RadioButton) findViewById(R.id.radio_yes);
		noRadioButton = (RadioButton) findViewById(R.id.radio_no);
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
		ImageButton datepickerDialog=(ImageButton) findViewById(R.id.imageButton_dueDate);
		datepickerDialog.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", currentDate.getMonthOfYear(), currentDate.getYear());
				dialogCaldroidFragment.show(getSupportFragmentManager(),"TAG");
				final CaldroidListener listener = new CaldroidListener() {
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
				    @Override
				    public void onSelectDate(Date date, View view) {
				    	dialogCaldroidFragment.dismiss();
				    	dueDateValue.setText(formatter2.format(date));
				    	dueDateValue.setError(null);
				    	due_date=formatter.format(date);
				        Toast.makeText(mContext, formatter2.format(date),
				                Toast.LENGTH_SHORT).show();
				    }
				};

				dialogCaldroidFragment.setCaldroidListener(listener);
				
			}
			
		});
		ImageButton datepickerDialog2=(ImageButton) findViewById(R.id.imageButton_startDate);
		datepickerDialog2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", currentDate.getMonthOfYear(), currentDate.getYear());
				dialogCaldroidFragment.show(getSupportFragmentManager(),"TAG");
				final CaldroidListener listener = new CaldroidListener() {
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
				    @Override
				    public void onSelectDate(Date date, View view) {
				    	dialogCaldroidFragment.dismiss();
				    	startDateValue.setText(formatter2.format(date));
				    	startDateValue.setError(null);
				    	start_date=formatter.format(date);
				    	Date today=new Date();
			        	if(isDateAfter(formatter.format(today),formatter.format(date))==true){
			        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
			        				mContext);
								alertDialogBuilder.setTitle("Date Confirmation");
								alertDialogBuilder
									.setMessage("You selected a start date in the past. Click ok to proceed")
									.setCancelable(false)
									.setIcon(R.drawable.ic_error)
									.setPositiveButton("OK",new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,int id) {
											dialog.cancel();
										}
									  });
								AlertDialog alertDialog = alertDialogBuilder.create();
								alertDialog.show();
			        	}
				        Toast.makeText(mContext, formatter2.format(date),
				                Toast.LENGTH_SHORT).show();
				    }
				};

				dialogCaldroidFragment.setCaldroidListener(listener);
				
			}
			
		});
		
		Button cancel=(Button) findViewById(R.id.button_cancel);
		cancel.setVisibility(View.GONE);
		
		dialogButton.setOnClickListener(new OnClickListener() {
		

			@Override
			public void onClick(View v) {
				String other_category=editText_otherCategory.getText
						().toString();
				String other_number = null;
				if(noRadioButton.isChecked()){
	      			other_number="0";
	      		}else if (yesRadioButton.isChecked()){
	      			other_number=editText_otherNumber.getText().toString();
	      		}
				String other_period=spinner_otherPeriod.getSelectedItem().toString();
				String duration=" ";
				if(!checkValidation()){
		      		Toast.makeText(mContext.getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();
		      	}
		      	else{
		      		if(personal.getCheckedRadioButtonId()==R.id.radio_personalYes){
		      		//long id=db.insertOther(other_category,other_number,other_period,duration,start_date,due_date,"personal",0,Integer.valueOf(other_number),"new_record");
		      			long id=db.insertTarget(0, 
  								MobileLearning.CCH_TARGET_TYPE_OTHER,
  								other_category,
  								"", 
  								MobileLearning.CCH_TARGET_PERSONAL,
  								Integer.parseInt(other_number), 
  								0,
  								Integer.parseInt(other_number),
  								start_date,
  								due_date,
  								other_period,
  								MobileLearning.CCH_TARGET_STATUS_NEW,
  								"Not yet");
			    if(id!=0){
			    	JSONObject json = new JSONObject();
					 try {
						 json.put("id", id);
						 json.put("target_type", other_category);
						 json.put("category", "other");
						 json.put("start_date", start_date);
						 json.put("target_number", 	other_number);
						 json.put("due_date", due_date);
						 json.put("achieved_number", 0);
						 json.put("last_updated", getDateTime());
						 json.put("details", "personal");
						 json.put("ver", db.getVersionNumber(mContext));
							json.put("battery", db.getBatteryStatus(mContext));
					    	json.put("device", db.getDeviceName());
					    	json.put("imei", db.getDeviceImei(mContext));
						 end_time=System.currentTimeMillis();
						 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
					 } catch (JSONException e) {
							e.printStackTrace();
						}
			    	Intent intent2 = new Intent(Intent.ACTION_MAIN);
		 	          intent2.setClass(mContext, EventPlannerOptionsActivity.class);
		 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 	          startActivity(intent2);
		 	         finish();	
		 	         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
			    	 Toast.makeText(mContext.getApplicationContext(), "Added target successfully!",
					         Toast.LENGTH_LONG).show();
			    }else{
			    	Toast.makeText(mContext, "Oops! Something went wrong. Please try again",
					         Toast.LENGTH_LONG).show();
			    }
		      		}else if(personal.getCheckedRadioButtonId()==R.id.radio_personalNo){
		      			//long id=db.insertOther(other_category,other_number,other_period,duration,start_date,due_date,"not_personal",0,Integer.valueOf(other_number),"new_record");
		      			long id=db.insertTarget(0, 
  								MobileLearning.CCH_TARGET_TYPE_OTHER,
  								other_category,
  								"", 
  								MobileLearning.CCH_TARGET_NOT_PERSONAL,
  								Integer.parseInt(other_number), 
  								0,
  								Integer.parseInt(other_number),
  								start_date,
  								due_date,
  								other_period,
  								MobileLearning.CCH_TARGET_STATUS_NEW,
  								"Not yet");
					    if(id!=0){
					    	JSONObject json = new JSONObject();
							 try {
								 json.put("id", id);
								 json.put("target_type", other_category);
								 json.put("category", "other");
								 json.put("start_date", start_date);
								 json.put("target_number", 	other_number);
								 json.put("due_date", due_date);
								 json.put("achieved_number", 0);
								 json.put("last_updated", getDateTime());
								 json.put("details", "not_personal");
								 json.put("ver", db.getVersionNumber(mContext));
									json.put("battery", db.getBatteryStatus(mContext));
							    	json.put("device", db.getDeviceName());
							    	json.put("imei", db.getDeviceImei(mContext));
								 end_time=System.currentTimeMillis();
								 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
							 } catch (JSONException e) {
									e.printStackTrace();
								}
					    	Intent intent2 = new Intent(Intent.ACTION_MAIN);
				 	          intent2.setClass(mContext, EventPlannerOptionsActivity.class);
				 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 	          startActivity(intent2);
				 	          finish();	
				 	          overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
					    	 Toast.makeText(mContext, "Added target successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(mContext, "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
		      		}
			}
			}
		});
		button_show=(Button) findViewById(R.id.button_show);
		eventId=db.getAllTargetsForUpdate("Daily",MobileLearning.CCH_TARGET_STATUS_NEW);
		 final int number=(int)eventId.size();
		 button_show.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					if(number>0){
					Intent intent= new Intent(mContext, UpdateTargetActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					}else if(number==0){
						 Toast.makeText(mContext, "You have no targets to update!",
						         Toast.LENGTH_SHORT).show();
					}
				}
				
			});		
 }
		
	

	
			 private boolean checkValidation() {
			        boolean ret = true;
			 
			        if (!Validation.hasTextTextView(startDateValue)) ret = false;
			        if (!Validation.hasTextEditText(editText_otherCategory)) ret = false;
			        if (!Validation.hasTextTextView(dueDateValue)) ret = false;
			        if (!Validation.hasSelection(spinner_otherPeriod)) ret = false;
			        if (editText_otherNumber.isShown()&&!Validation.hasTextEditText(editText_otherNumber)) ret = false;
			        if (Validation.isDateAfter(start_date,due_date,startDateValue)) ret = false;
			        return ret;
			    }	
	 public static boolean isDateAfter(String startDate,String endDate)
	    {
		 Date start_date = null;
		 Date end_date = null;
		long starDateAsTimestamp = 0;
		long endDateTimestamp = 0;
		try {
			if(startDate==null){
			System.out.println("Enter a valid date!");
			}else{
			start_date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
			 starDateAsTimestamp = start_date.getTime();
			}
			if(endDate==null){
				System.out.println("Enter a valid date!");
			}else{
			end_date = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
			endDateTimestamp = end_date.getTime();
			}
		} catch (ParseException e) {
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
	 
	 
	 @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if ((keyCode == KeyEvent.KEYCODE_BACK)) {
				TargetSettingActivity.this.finish();
				
			} 
			
		    return true; 
		}
	  
	 public void onBackPressed()
		{
			finish();
		}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Log.d(TAG, key + " changed");
		if(key.equalsIgnoreCase(getString(R.string.prefs_server))){
			Editor editor = sharedPreferences.edit();
			if(!sharedPreferences.getString(getString(R.string.prefs_server), "").endsWith("/")){
				String newServer = sharedPreferences.getString(getString(R.string.prefs_server), "").trim()+"/";
				editor.putString(getString(R.string.prefs_server), newServer);
		    	editor.commit();
			}
		}
	}

	public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
}
}
