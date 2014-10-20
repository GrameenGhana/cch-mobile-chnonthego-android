package org.grameenfoundation.poc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.digitalcampus.mobile.learningGF.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;

public class EstimateTrimester extends Activity {

	private CalendarView calendarView_calendar;
	private Button button_calculate;
	private Button button_proceed;
	private TextView textView_estimatedWeeks;
	private TextView textView_estimatedTrimester;
	private String newDate;
	private Calendar cal;
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_estimate_trimester);
	    calendarView_calendar=(CalendarView) findViewById(R.id.calendarView1);
	    calendarView_calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.TextColorGreen));
	    calendarView_calendar.setSelectedDateVerticalBar(R.color.TextColorWine);
	    calendarView_calendar.setOnDateChangeListener(new OnDateChangeListener(){
			@Override
			public void onSelectedDayChange(CalendarView view, int year,
					int month, int dayOfMonth) {
				month=month+1;
				SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");
				cal=Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.YEAR, year);
				newDate=dfDate.format(cal.getTime());
			}
	    });
	    button_calculate=(Button) findViewById(R.id.button_calculate);
	    button_calculate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				daysBetween();			
			}
	    	
	    });
	    button_proceed=(Button) findViewById(R.id.button_proceed);
	    textView_estimatedWeeks=(TextView) findViewById(R.id.textView_estimatedWeeks);
	    textView_estimatedTrimester=(TextView) findViewById(R.id.textView_estimatedTrimester);
	    
	}

	public void daysBetween(){
		SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");
		Calendar today=Calendar.getInstance();
		String now=dfDate.format(today.getTime());
		Date start = null;
		Date end = null;
		try {
			start = dfDate.parse(now);
			end=dfDate.parse(newDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long diff=end.getTime() - start.getTime();
		int days=(int) (diff/(1000*60*60*24));
		
		if(days<90){
			textView_estimatedTrimester.setText("1st Trimester");
		}else if(days>89 && days<180){
			textView_estimatedTrimester.setText("2nd Trimester");
		}else if(days>180){
			textView_estimatedTrimester.setText("3rd Trimester");
		}
		if(days<1){
			textView_estimatedTrimester.setText("Please select a date in the past to proceed");
		}
		if(days>300){
			textView_estimatedTrimester.setText("Your client should have delivered by now");
		}
		
		double weeks=Math.ceil(days/7);
		if(weeks>1){
			textView_estimatedWeeks.setText(String.valueOf(weeks) +" weeks");	
		}else{
			if(weeks==1){
				textView_estimatedWeeks.setText(String.valueOf(weeks) +" weeks");		
			}
		}
	}
	
	
}
