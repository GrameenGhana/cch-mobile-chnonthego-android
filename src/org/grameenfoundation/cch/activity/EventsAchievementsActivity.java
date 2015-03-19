package org.grameenfoundation.cch.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.AchievementDetailsAdapter;
import org.grameenfoundation.adapters.CalendarEventsViewAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.model.MyCalendarEvents;
import org.grameenfoundation.poc.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class EventsAchievementsActivity extends BaseActivity {

	private ExpandableListView expandableListview;
	private Context mContext;
	private CalendarEvents c;
	private ArrayList<MyCalendarEvents> completed;
	private ArrayList<MyCalendarEvents> unCompleted;
	private AchievementDetailsAdapter adapter;
	private TextView textView_label;
	private TextView textView_number;
	private int month;
	private int year;
	private Long start_time;
	private Long end_time;
	private DbHelper db;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_target_details_);
	    expandableListview = (ExpandableListView) findViewById(R.id.expandableListView1);
	    mContext=EventsAchievementsActivity.this;
	    c= new CalendarEvents(mContext);
	    db=new DbHelper(EventsAchievementsActivity.this);
	    start_time=System.currentTimeMillis();
	    String[] groupItems={"Completed","Future"};
	    getActionBar().setTitle("Achievement Center");
	    getActionBar().setSubtitle("Achievement Details");
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          month= extras.getInt("month");
          year=extras.getInt("year");
        }
	    textView_label=(TextView) findViewById(R.id.textView_label);
	    textView_label.setText("Events");
	    textView_number=(TextView) findViewById(R.id.textView_number);
	   
		//new addition
		completed=c.readPastCalendarEvents(mContext,month,year,true);
		unCompleted=c.readFutureCalendarEvents(mContext, month, year,true);
		 textView_number.setText("("+String.valueOf(completed.size()+unCompleted.size())+" this month)");
	    adapter=new AchievementDetailsAdapter(mContext,groupItems,completed,unCompleted,expandableListview);
	    expandableListview.setAdapter(adapter);
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		 db.insertCCHLog("Achievement Center", "Event Achievements", start_time.toString(), end_time.toString());
		finish();
	}
}
