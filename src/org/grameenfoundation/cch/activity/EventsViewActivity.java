package org.grameenfoundation.cch.activity;


import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.CalendarEventsViewAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.model.MyCalendarEvents;
import org.grameenfoundation.poc.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class EventsViewActivity extends  BaseActivity {
	public String[] groupItem;
	
	 public ArrayList<MyCalendarEvents> PastThisMonthCalendarEvents;
	 public ArrayList<MyCalendarEvents> AllEvents;
	 public ArrayList<MyCalendarEvents> AllCalendarEvents;
	 public ArrayList<MyCalendarEvents> PastLastMonthCalendarEvents;
	 public ArrayList<MyCalendarEvents> YesterdayCalendarEvents;
	 public ArrayList<MyCalendarEvents> TodayCalendarEvents;
	 public ArrayList<MyCalendarEvents> TomorrowCalendarEvents;
	 public ArrayList<MyCalendarEvents> FutureCalendarEvents;
	 CalendarEvents c;
	 private DbHelper dbh;
	 private static final String EVENT_PLANNER_ID = "Event Planner";
	private ListView expandableList_events;
	private Context mContext;
	private Button button_viewCalendar;
	private CalendarEventsViewAdapter adapter;
	private JSONObject data;
	private Button button_update;

	private String status;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_view_events);
	    mContext=EventsViewActivity.this;
	    c= new CalendarEvents(mContext);
	    dbh = new DbHelper(mContext);
	    PastLastMonthCalendarEvents=dbh.getPastLastMonthEvents();
		PastThisMonthCalendarEvents=dbh.getPastThisMonthEvents();
		YesterdayCalendarEvents=dbh.getYesterdaysEvents();
		TodayCalendarEvents=dbh.getTodaysEvents();
    	TomorrowCalendarEvents=dbh.getTomorrowEvents();
    	FutureCalendarEvents=dbh.getFutureEvents();
	    String[] groupItems={"Last month ("+PastLastMonthCalendarEvents.size()+")",
	    					 "Past this month ("+PastThisMonthCalendarEvents.size()+")",
	    					 "Yesterday ("+YesterdayCalendarEvents.size()+")",
	    					 "Today ("+ TodayCalendarEvents.size()+")",
	    					 "Tomorrow ("+TomorrowCalendarEvents.size()+")",
	    					 "Future ("+FutureCalendarEvents.size()+")"};
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Planned Events");
	    expandableList_events=(ListView) findViewById(R.id.calendarEvents);
	    ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,  android.R.layout.simple_list_item_1,groupItems);
	    expandableList_events.setAdapter(adapter);
		//new addition
	    try{
	    	AllEvents=dbh.getCalendarEvents();
	    	AllCalendarEvents=c.readAllCalendarEvents(mContext);
	    	if(AllEvents.size()<AllCalendarEvents.size()){
	    		for(int i=0;i<AllCalendarEvents.size();i++){
	    			if(AllCalendarEvents.get(i).getEventStatus()==null){
	    				status="";
	    			}else{
	    				status=AllCalendarEvents.get(i).getEventStatus();
	    			}
	    			dbh.insertCalendarEvent(Long.parseLong(AllCalendarEvents.get(i).getEventId()), 
	    								AllCalendarEvents.get(i).getEventType(), 
	    								"", 
	    								AllCalendarEvents.get(i).getEventDescription(),
	    								AllCalendarEvents.get(i).getEventCategory(), 
	    								AllCalendarEvents.get(i).getEventLocation(), 
	    								"", 
	    								"", 
	    								"", 
	    								AllCalendarEvents.get(i).getEventStartDate(), 
	    								AllCalendarEvents.get(i).getEventEndDate(), 
	    								status, 
	    								"", 
	    								AllCalendarEvents.get(i).getEventDeleted());
	    		}/*
	    		PastLastMonthCalendarEvents=dbh.getPastLastMonthEvents();
	    		PastThisMonthCalendarEvents=dbh.getPastThisMonthEvents();
	    		YesterdayCalendarEvents=dbh.getYesterdaysEvents();
	    		TodayCalendarEvents=dbh.getTodaysEvents();
		    	TomorrowCalendarEvents=dbh.getTomorrowEvents();
		    	FutureCalendarEvents=dbh.getFutureEvents();
		    	
		    	//adapter=new CalendarEventsViewAdapter(mContext,groupItems,PastLastMonthCalendarEvents,
		    		//							   PastThisMonthCalendarEvents,YesterdayCalendarEvents,TodayCalendarEvents,TomorrowCalendarEvents,FutureCalendarEvents,expandableList_events);
		    	//expandableList_events.setAdapter(adapter);
	    	/*}else{
	    		PastLastMonthCalendarEvents=dbh.getPastLastMonthEvents();
	    		PastThisMonthCalendarEvents=dbh.getPastThisMonthEvents();
	    		YesterdayCalendarEvents=dbh.getYesterdaysEvents();
	    		TodayCalendarEvents=dbh.getTodaysEvents();
		    	TomorrowCalendarEvents=dbh.getTomorrowEvents();
		    	FutureCalendarEvents=dbh.getFutureEvents();
	    	
	    	adapter=new CalendarEventsViewAdapter(mContext,groupItems,PastLastMonthCalendarEvents,
	    									   PastThisMonthCalendarEvents,YesterdayCalendarEvents,TodayCalendarEvents,TomorrowCalendarEvents,FutureCalendarEvents,expandableList_events);
	    	expandableList_events.setAdapter(adapter);*/
	    		 PastLastMonthCalendarEvents=dbh.getPastLastMonthEvents();
	    			PastThisMonthCalendarEvents=dbh.getPastThisMonthEvents();
	    			YesterdayCalendarEvents=dbh.getYesterdaysEvents();
	    			TodayCalendarEvents=dbh.getTodaysEvents();
	    	    	TomorrowCalendarEvents=dbh.getTomorrowEvents();
	    	    	FutureCalendarEvents=dbh.getFutureEvents();
	    		    String[] groups={"Past last month ("+PastLastMonthCalendarEvents.size()+")",
	    		    					 "Past this month ("+PastThisMonthCalendarEvents.size()+")",
	    		    					 "Yesterday ("+YesterdayCalendarEvents.size()+")",
	    		    					 "Today ("+ TodayCalendarEvents.size()+")",
	    		    					 "Tomorrow ("+TomorrowCalendarEvents.size()+")",
	    		    					 "Future ("+FutureCalendarEvents.size()+")"};
	    		    ArrayAdapter<String> adapter2=new ArrayAdapter<String>(mContext,  android.R.layout.simple_list_item_1,groups);
	    		    expandableList_events.setAdapter(adapter2);
	    	}
	    	expandableList_events.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent;
					switch(position){
					case 0:
						intent=new Intent(mContext,EventsListViewActivity.class);
						intent.putExtra("event_category", "past_last_month");
						System.out.println("Clicked");
						startActivity(intent);
						break;
					case 1:
						intent=new Intent(mContext,EventsListViewActivity.class);
						intent.putExtra("event_category", "past_this_month");
						System.out.println("Clicked");
						startActivity(intent);
						break;
					case 2:
						intent=new Intent(mContext,EventsListViewActivity.class);
						intent.putExtra("event_category", "yesterday");
						System.out.println("Clicked");
						startActivity(intent);
						break;
					case 3:
						intent=new Intent(mContext,EventsListViewActivity.class);
						intent.putExtra("event_category", "today");
						System.out.println("Clicked");
						startActivity(intent);
						break;
					case 4:
						intent=new Intent(mContext,EventsListViewActivity.class);
						intent.putExtra("event_category", "tomorrow");
						startActivity(intent);
						break;
					case 5:
						intent=new Intent(mContext,EventsListViewActivity.class);
						intent.putExtra("event_category", "future");
						startActivity(intent);
						break;
					}
				}
			});
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
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
	    button_update=(Button) findViewById(R.id.button_update);
	    button_update.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(EventsViewActivity.this,UpdateEventsActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    });
	    /*
	expandableList_events.setOnChildClickListener(new OnChildClickListener(){
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			String[] selected_items=adapter.getChild(groupPosition, childPosition);
			Intent intent=new Intent(EventsViewActivity.this,ViewEventDetailsActivity.class);
			intent.putExtra("event_type", selected_items[0]);
			intent.putExtra("event_description", selected_items[1]);
			intent.putExtra("event_location", selected_items[2]);
			intent.putExtra("event_id", selected_items[3]);
			intent.putExtra("event_startdate", selected_items[4]);
			intent.putExtra("event_enddate", selected_items[5]);
			intent.putExtra("event_status", selected_items[6]);
			intent.putExtra("event_category", selected_items[7]);
			intent.putExtra("event_comment", selected_items[8]);
			intent.putExtra("event_justification", selected_items[9]);
			intent.putExtra("mode", "edit_mode");
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			return true;
		}
		
	});*/
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
	  data=new JSONObject();
	    try {
	    	data.put("page", "Events View");
	    	data.put("ver", dbh.getVersionNumber(EventsViewActivity.this));
	    	data.put("battery", dbh.getBatteryStatus(EventsViewActivity.this));
	    	data.put("device", dbh.getDeviceName());
			data.put("imei", dbh.getDeviceImei(EventsViewActivity.this));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	  dbh.insertCCHLog(EVENT_PLANNER_ID, data.toString(), starttime.toString(), endtime.toString());	
	}
	public void onDestroy(){
		 super.onDestroy();
		 Long starttime=System.currentTimeMillis();  
		 saveToLog(starttime); 
	 }
	

}
