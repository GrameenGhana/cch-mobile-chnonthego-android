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

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_achievements_details);
	    expandableListview = (ExpandableListView) findViewById(R.id.expandableListView1);
	    mContext=EventsAchievementsActivity.this;
	    c= new CalendarEvents(mContext);
	    String[] groupItems={"Completed","Upcoming"};
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Achievement Center");
	    getActionBar().setSubtitle("Achievement Details");
	    textView_label=(TextView) findViewById(R.id.textView_label);
	    textView_label.setText("Events");
	    textView_number=(TextView) findViewById(R.id.textView_number);
	   
		//new addition
		completed=c.readPastCalendarEvents(mContext,11,2014);
		unCompleted=c.readFutureCalendarEvents(mContext, 11, 2014);
		 textView_number.setText("("+String.valueOf(completed.size()+unCompleted.size())+" this month)");
	    adapter=new AchievementDetailsAdapter(mContext,groupItems,completed,unCompleted,expandableListview);
	    expandableListview.setAdapter(adapter);
	}

}