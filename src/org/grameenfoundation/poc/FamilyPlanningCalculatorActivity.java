package org.grameenfoundation.poc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.cch.caldroid.CaldroidFragment;
import org.grameenfoundation.cch.caldroid.CaldroidListener;
import org.grameenfoundation.cch.model.Validation;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.json.JSONException;
import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class FamilyPlanningCalculatorActivity extends FragmentActivity {

	private Context mContext;
	private Long start_time;
	private DateTime currentDate;
	private DbHelper dbh;
	private JSONObject json;
	private TextView textView_selectedDate;
	private TextView textView_injectionDate;
	private Long end_time;
	private Button button_calculate;
	private MaterialSpinner spinner;
	private String interval;
	private String newDate;
	private DateTime selected_date;
	private ScrollView scroll;
	private DatePicker datePicker;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_family_planning_calculators);
	    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    mContext=FamilyPlanningCalculatorActivity.this;
	    getActionBar().setTitle("Point of Care");
	    scroll=(ScrollView) findViewById(R.id.ScrollView1);
	    getActionBar().setSubtitle("Family Planning Calculator");
	    datePicker=(DatePicker) findViewById(R.id.datePicker1);
	    start_time=System.currentTimeMillis();
	    currentDate=new DateTime();
	    spinner=(MaterialSpinner) findViewById(R.id.spinner1);
	    dbh=new DbHelper(FamilyPlanningCalculatorActivity.this);
	    json=new JSONObject();
	    try {
			json.put("page", "Family Planning Calculator");
			json.put("section", MobileLearning.CCH_COUNSELLING);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    textView_selectedDate=(TextView) findViewById(R.id.textView_selectedDate);
	    textView_injectionDate=(TextView) findViewById(R.id.textView_injectionDate);
	    button_calculate=(Button) findViewById(R.id.button_calculate);
	    /*
	    final CaldroidFragment caldroidFragment = new CaldroidFragment();
	    Bundle args = new Bundle();
	    Calendar cal = Calendar.getInstance();
	    args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
	    args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
	    caldroidFragment.setArguments(args);

	    FragmentTransaction t = getSupportFragmentManager().beginTransaction();
	    t.replace(R.id.fragment1, caldroidFragment);
	    t.commit();
	    
	    final CaldroidListener listener = new CaldroidListener() {
	    	SimpleDateFormat formatter  = new SimpleDateFormat("dd/MM/yyyy");
	    	SimpleDateFormat formatter2  = new SimpleDateFormat("dd-MMM-yyyy");
	        @Override
	        public void onSelectDate(Date date, View view) {
	        	newDate=formatter.format(date);
	        	selected_date=new DateTime(date);
	        	caldroidFragment.refreshView();
	        	Date today=new Date();
	        	textView_selectedDate.setText("You selected: "+formatter2.format(date));
	        	/*
	        	if(isDateAfter(formatter.format(date),formatter.format(today))==true){
	        		Crouton.makeText(FamilyPlanningCalculatorActivity.this, "Select a date in the past", Style.ALERT).show();	
	        		button_calculate.setVisibility(View.GONE);
	        	}else{
	        		button_calculate.setVisibility(View.VISIBLE);
	        		textView_selectedDate.setText("You selected: "+formatter2.format(date));
	        	}*/
	        	//caldroidFragment.refreshView();
	       // }
	    //};
	    /*
	    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					interval.equals("one");
					System.out.println(interval);
					break;
				case 1:
					interval.equals("three");
					System.out.println(interval);
					break;

				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	    caldroidFragment.setCaldroidListener(listener);*/
	    button_calculate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!checkValidation()){
					Crouton.makeText(FamilyPlanningCalculatorActivity.this, "Select an interval!", Style.ALERT).show();	
				}else{
					calculateDueDate();
					
				}
			}
		} );
	}
	public void calculateDueDate(){
		selected_date=new DateTime(getDateFromDatePicker(datePicker));
		if(spinner.getSelectedItem().toString().equals("One month interval")){
			DateTime newDate=new DateTime(selected_date).plusDays(27);
			textView_injectionDate.setText(newDate.toString("dd-MMM-YYYY"));
		}else if(spinner.getSelectedItem().toString().equals("Three month interval")){
			DateTime newDate=new DateTime(selected_date).plusDays(83);
			textView_injectionDate.setText(newDate.toString("dd-MMM-YYYY"));
		}
		scroll.post(new Runnable() {
			  @Override
			public void run() {
			    scroll.scrollTo(0, scroll.getBottom());
			}
			});
	}
	public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
	    int day = datePicker.getDayOfMonth();
	    int month = datePicker.getMonth();
	    int year =  datePicker.getYear();

	    Calendar calendar = Calendar.getInstance();
	    calendar.set(year, month, day);

	    return calendar.getTime();
	}
	private boolean checkValidation() {
        boolean ret = true;
 
        if (!Validation.hasSelection(spinner))ret = false;
        return ret;
    }
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
	

}
