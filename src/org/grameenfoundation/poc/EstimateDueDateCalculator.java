package org.grameenfoundation.poc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.caldroid.CaldroidFragment;
import org.grameenfoundation.cch.caldroid.CaldroidListener;
import org.grameenfoundation.cch.utils.CalendarViewScrollable;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.CalendarView.OnDateChangeListener;

public class EstimateDueDateCalculator extends BaseActivity {

	private Button button_calculate;
	private TextView textView_selectedDate;
	private String newDate;
	private TextView textView_estimatedDueDate;
	private String estimated_due_date;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private CalendarViewScrollable calendar;
	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_estimate_due_date);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("Due Date Calculator");
	    start_time=System.currentTimeMillis();
	    dbh=new DbHelper(EstimateDueDateCalculator.this);
	    textView_selectedDate=(TextView) findViewById(R.id.textView_selectedDate);
	    textView_estimatedDueDate=(TextView) findViewById(R.id.textView_estimatedDueDate);
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
	    final CaldroidFragment caldroidFragment  = new CaldroidFragment();
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
				calculateDueDate();		
				}else{
					Crouton.makeText(EstimateDueDateCalculator.this, "Please select a date", Style.ALERT).show();	
				}
			}
	    	
	    });
	}
	
	public void calculateDueDate(){
		SimpleDateFormat formatter  = new SimpleDateFormat("dd/MM/yyyy");
		 Calendar cal = Calendar.getInstance();
    	 try {
			cal.setTime(formatter.parse(newDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 cal.add(Calendar.DATE,280);
    	 estimated_due_date=formatter.format(cal.getTime());
    	 textView_estimatedDueDate.setText("Estimated due date is: "+estimated_due_date);
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "Due Date Calculator", start_time.toString(), end_time.toString());
		finish();
	}
}
