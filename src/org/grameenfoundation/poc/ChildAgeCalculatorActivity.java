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
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Period;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class ChildAgeCalculatorActivity extends FragmentActivity {

	private Context mContext;
	private Long start_time;
	private DbHelper dbh;
	private JSONObject json;
	private TextView textView_selectedDate;
	private TextView textView_ageWeeks;
	private TextView textView_ageMonths;
	private TextView textView_ageYears;
	private TextView textView_exact_age;
	protected String newDate;
	private Long end_time;
	private Button button_calculate;
	private DateTime currentDate;
	private DateTime selected_date;
	private DatePicker datePicker;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_child_age_calculator);
	   this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    mContext=ChildAgeCalculatorActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("CWC Calculators: Child Age Calculator");
	    start_time=System.currentTimeMillis();
	    currentDate=new DateTime();
	    datePicker=(DatePicker) findViewById(R.id.datePicker1);
	    dbh=new DbHelper(ChildAgeCalculatorActivity.this);
	    json=new JSONObject();
	    try {
			json.put("page", "Child Age Calculator");
			json.put("section", MobileLearning.CCH_COUNSELLING);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    textView_selectedDate=(TextView) findViewById(R.id.textView_selectedDate);
	    textView_ageWeeks=(TextView) findViewById(R.id.textView_ageWeeks);
	    textView_ageMonths=(TextView) findViewById(R.id.textView_ageMonths);
	    textView_ageYears=(TextView) findViewById(R.id.textView_ageYears);
	    textView_exact_age=(TextView) findViewById(R.id.textView_exact_age);
	    button_calculate=(Button) findViewById(R.id.button_calculate);
	    /*final CaldroidFragment caldroidFragment = new CaldroidFragment();
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
	        	if(isDateAfter(formatter.format(date),formatter.format(today))==true){
	        		Crouton.makeText(ChildAgeCalculatorActivity.this, "Select a date in the past", Style.ALERT).show();	
	        		button_calculate.setVisibility(View.GONE);
	        	}else{
	        		button_calculate.setVisibility(View.VISIBLE);
	        		textView_selectedDate.setText("You selected: "+formatter2.format(date));
	        	}
	        	caldroidFragment.refreshView();
	        }
	    };

	    caldroidFragment.setCaldroidListener(listener);*/
	    button_calculate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				calculateDueDate();
			}
		} );
	}
	public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
	    int day = datePicker.getDayOfMonth();
	    int month = datePicker.getMonth();
	    int year =  datePicker.getYear();

	    Calendar calendar = Calendar.getInstance();
	    calendar.set(year, month, day);

	    return calendar.getTime();
	}
	public void calculateDueDate(){
		selected_date=new DateTime(getDateFromDatePicker(datePicker));
		int weeks = Weeks.weeksBetween(selected_date, currentDate).getWeeks();
		int months = Months.monthsBetween(selected_date, currentDate).getMonths();
		int years = Years.yearsBetween(selected_date, currentDate).getYears();
		LocalDate today = LocalDate.now();
		LocalDate birthday = new LocalDate(selected_date);
		 
		Period p = new Period(birthday, today);
		textView_exact_age.setText(p.getYears()+" year(s),"+p.getMonths()+" month(s), "+p.getDays()+" day(s)");
		if(weeks>1){
			textView_ageWeeks.setText(String.valueOf(weeks)+" weeks");	
		}else if(weeks==1){
			textView_ageWeeks.setText(String.valueOf(weeks)+" week");
		}else{
			textView_ageWeeks.setText(String.valueOf(weeks)+" weeks");	
		}
		if(months>1){
			textView_ageMonths.setText(String.valueOf(months)+" months");	
		}else if(months==1){
			textView_ageMonths.setText(String.valueOf(months)+" month");
		}else{
			textView_ageMonths.setText(String.valueOf(months)+" months");	
		}
		if(years>1){
			textView_ageYears.setText(String.valueOf(years)+" years");	
		}else if(years==1){
			textView_ageYears.setText(String.valueOf(years)+" year");
		}else{
			textView_ageYears.setText(String.valueOf(years)+" years");	
		}
	}
	
	public String calculateExactAge(DateTime birthDate, DateTime currentDate){
		String exactAge = null;
		int years = 0;
	    int months = 0;
	    int days = 0;
		
		return exactAge;
	}
	public static boolean isDateAfter(String startDate,String endDate)
    {
	 Date start_date = null;
	 Date end_date = null;
	long starDateAsTimestamp = 0;
	long endDateTimestamp = 0;
	try {
		if(startDate==null){
		}else{
		start_date = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
		 starDateAsTimestamp = start_date.getTime();
		}
		if(endDate==null){
		}else{
		end_date = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
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
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
