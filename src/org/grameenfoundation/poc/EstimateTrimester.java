package org.grameenfoundation.poc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.caldroid.CaldroidFragment;
import org.grameenfoundation.cch.caldroid.CaldroidListener;
import org.grameenfoundation.cch.model.WebAppInterface;
import org.grameenfoundation.cch.utils.CalendarViewScrollable;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
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

public class EstimateTrimester extends BaseActivity {

	private static CalendarView calendarView_calendar;
	private Button button_calculate;
	private Button button_proceed;
	private TextView textView_estimatedWeeks;
	private TextView textView_estimatedTrimester;
	private static String newDate;
	private static Calendar cal;
	private WebView myWebView;
	private ViewPager mPager;
	private TextView textView_selectedDate;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private CalendarViewScrollable calendar;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_estimate_trimester);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("Trimester Calculator");
	    start_time=System.currentTimeMillis();
	    dbh=new DbHelper(EstimateTrimester.this);
	    textView_selectedDate=(TextView) findViewById(R.id.textView_selectedDate);
	    calendar=(CalendarViewScrollable) findViewById(R.id.calendarView1);
	    calendar.setSelectedWeekBackgroundColor(Color.rgb(82,0,0));
	    calendar.setOnDateChangeListener(new OnDateChangeListener(){

			@Override
			public void onSelectedDayChange(CalendarView view, int year,
					int month, int dayOfMonth) {
				newDate=String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year);
				textView_selectedDate.setText("You selected: "+newDate);
				System.out.println(newDate);
			}
	    	
	    });
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
	        @Override
	        public void onSelectDate(Date date, View view) {
	        	newDate=formatter.format(date);
	        	textView_selectedDate.setText("You selected: "+formatter.format(date));
	        	caldroidFragment.setBackgroundResourceForDate(Color.rgb(83,171,32), date);
	        	caldroidFragment.setTextColorForDate(Color.rgb(255, 255, 255), date);
	        }
	    };

	    caldroidFragment.setCaldroidListener(listener);
	*/
	    button_calculate=(Button) findViewById(R.id.button_calculate);
	    button_calculate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(newDate!=null){
					daysBetween();
				
				}else{
					Crouton.makeText(EstimateTrimester.this, "Please select a date", Style.ALERT).show();	
				}
				
			}
	    	
	    });
	    
	    button_proceed=(Button) findViewById(R.id.button_proceed);
	    button_proceed.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(EstimateTrimester.this,ExamineThePatientActivity.class);
				startActivity(intent);
				
			}
	    	
	    });
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
			if(newDate!=null){
				end=dfDate.parse(newDate);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long diff=start.getTime() - end.getTime();
		int days=(int) (diff/(1000*60*60*24));
		System.out.println(String.valueOf(days));
		if(days<90){
			textView_estimatedTrimester.setText("1st Trimester");
		}else if(days>89 && days<180){
			textView_estimatedTrimester.setText("2nd Trimester");
		}else if(days>180){
			textView_estimatedTrimester.setText("3rd Trimester");
		}
		if(days<1){
			textView_estimatedTrimester.setTextColor(Color.RED);
			textView_estimatedTrimester.setText("Please select a date in the past to proceed");
		}
		if(days>300){
			textView_estimatedTrimester.setTextColor(Color.RED);
			textView_estimatedTrimester.setText("Your client should have delivered by now");
		}
		
		double weeks=Math.ceil(days/7);
		if(weeks>1){
			String week_value=String.format("%.0f",weeks);
			textView_estimatedWeeks.setText(week_value +" weeks");	
		}else{
			if(weeks==1){
				String week_value=String.format("%.0f",weeks);
				textView_estimatedWeeks.setText(week_value +" week");		
			}
		}
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "Trimester Calculator", start_time.toString(), end_time.toString());
		finish();
	}
	}

