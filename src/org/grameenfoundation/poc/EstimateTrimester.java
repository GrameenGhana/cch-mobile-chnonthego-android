package org.grameenfoundation.poc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.cch.model.WebAppInterface;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;

public class EstimateTrimester extends FragmentActivity {

	private static CalendarView calendarView_calendar;
	private Button button_calculate;
	private Button button_proceed;
	private TextView textView_estimatedWeeks;
	private TextView textView_estimatedTrimester;
	private static String newDate;
	private static Calendar cal;
	private WebView myWebView;
	private ViewPager mPager;
	private CalendarAdapter mAdapter;
	private static final String URL = "file:///android_asset/www/cch/modules/poc/trimcalculator.html";
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_estimate_trimester);
	    mAdapter = new CalendarAdapter(getSupportFragmentManager());
	    /*
	    myWebView = (WebView) findViewById(R.id.webView_estimate);	
	    myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
		myWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);	 
		myWebView.loadUrl(URL);
		myWebView.setWebViewClient(new WebViewClient(){
				
			@Override
			     public void onReceivedError(WebView view, int errorCod,String description, String failingUrl) {
		            Toast.makeText(view.getContext(), description , Toast.LENGTH_LONG).show();
		         }
			    
			     
	});
	*/
	    mPager = (ViewPager)findViewById(R.id.pager3);
	    mPager.setAdapter(mAdapter);
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
	public static class CalendarAdapter extends FragmentStatePagerAdapter {
	    public CalendarAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		private static final int NUM_MONTHS = 10000;
	    private static final int INDEX_EPOCH = NUM_MONTHS/2;
	    private static final int MONTHS_PER_YEAR = 12;
	    private static final Calendar calendarMonthEpoch = new GregorianCalendar(2012, Calendar.AUGUST, 1);

	

	    @Override
	    public int getCount() {
	        return NUM_MONTHS;
	    }

	    @Override
	    public Fragment getItem(int position) {
	        return CalendarMonthFragment.newInstance(calendarMonthAtPosition(position));
	    }

	    private static Calendar calendarMonthAtPosition(int position) {
	        int offset = position - INDEX_EPOCH;
	        Calendar calMonthAtPosition = (Calendar) calendarMonthEpoch.clone(); 
	        calMonthAtPosition.add(Calendar.MONTH, offset % MONTHS_PER_YEAR);
	        calMonthAtPosition.add(Calendar.YEAR, offset / MONTHS_PER_YEAR);

	        return calMonthAtPosition;
	    }

	

	}
	
	 @SuppressLint("NewApi")
	public static class CalendarMonthFragment extends Fragment {
	        
	        @SuppressLint("NewApi")
			@Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                Bundle savedInstanceState) {
	            View v = inflater.inflate(R.layout.calendar_view, container, false);
	            calendarView_calendar=(CalendarView) v.findViewById(R.id.calendarView1);
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
	            return v;
	        }

			public static Fragment newInstance(Calendar calendarMonthAtPosition) {
				Fragment f=new Fragment();
				Bundle args = new Bundle();
				calendarMonthAtPosition = Calendar.getInstance();
				args.putInt("month", calendarMonthAtPosition.get(Calendar.MONTH) + 1);
				args.putInt("year", calendarMonthAtPosition.get(Calendar.YEAR));
				f.setArguments(args);
				return f;
			}

		
	       

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

