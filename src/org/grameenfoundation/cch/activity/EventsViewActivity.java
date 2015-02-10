package org.grameenfoundation.cch.activity;


import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.CalendarEventsViewAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.model.MyCalendarEvents;
import org.grameenfoundation.poc.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class EventsViewActivity extends  BaseActivity {
	public String[] groupItem;
	
	 
	 public ArrayList<MyCalendarEvents> TodayCalendarEvents;
	 public ArrayList<MyCalendarEvents> TomorrowCalendarEvents;
	 public ArrayList<MyCalendarEvents> FutureCalendarEvents;
	 CalendarEvents c;
	 private DbHelper dbh;
	 private static final String EVENT_PLANNER_ID = "Event Planner";
	private ExpandableListView expandableList_events;
	private Context mContext;
	private Button button_viewCalendar;
	
	private CalendarEventsViewAdapter adapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_view_events);
	    mContext=EventsViewActivity.this;
	    c= new CalendarEvents(mContext);
	    dbh = new DbHelper(mContext);
	    String[] groupItems={"Today","Tomorrow","Future"};
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Planned Events");
		//new addition
		TodayCalendarEvents=c.getTodaysEvents(false);
		TomorrowCalendarEvents=c.getTomorrowsEvents(false);
		FutureCalendarEvents=c.getFutureEvents(false);
		
	    expandableList_events=(ExpandableListView) findViewById(R.id.expandableListView_calendarEvents);
	    adapter=new CalendarEventsViewAdapter(mContext,groupItems,TodayCalendarEvents,TomorrowCalendarEvents,FutureCalendarEvents,expandableList_events);
	    expandableList_events.setAdapter(adapter);
	   
	    button_viewCalendar=(Button) findViewById(R.id.button_viewCalendar);
	    button_viewCalendar.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent intent =  new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("content://com.android.calendar/time"));
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		}
		
	});
	expandableList_events.setOnChildClickListener(new OnChildClickListener(){

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			String[] selected_items=adapter.getChild(groupPosition, childPosition);
			Intent intent=new Intent(EventsViewActivity.this,PlanEventActivity.class);
			intent.putExtra("event_type", selected_items[0]);
			intent.putExtra("event_description", selected_items[1]);
			intent.putExtra("event_location", selected_items[2]);
			intent.putExtra("event_id", selected_items[3]);
			intent.putExtra("mode", "edit_mode");
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			return true;
		}
		
	});
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			EventsViewActivity.this.finish();
		} 
	    return true; 
	}
	
	public void saveToLog(Long starttime) 
	{
	  Long endtime = System.currentTimeMillis();  
	  dbh.insertCCHLog(EVENT_PLANNER_ID, "Events View", starttime.toString(), endtime.toString());	
	}
	public void onDestroy(){
		 super.onDestroy();
		 Long starttime=System.currentTimeMillis();  
		 saveToLog(starttime); 
	 }
	

}
