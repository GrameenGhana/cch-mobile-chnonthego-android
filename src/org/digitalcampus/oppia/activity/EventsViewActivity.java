package org.digitalcampus.oppia.activity;


import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.calendar.CalendarEvents;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class EventsViewActivity extends Activity {
	public String[] groupItem;
	 public ArrayList<String> ChildItemEventTypeToday;
	 public ArrayList<String> ChildItemEventTypeTomorrow;
	 public ArrayList<String> ChildItemEventTypeFuture;
	 
	 public ArrayList<String> ChildItemEventDescriptionToday;
	 public ArrayList<String> ChildItemEventDescriptionTomorrow;
	 public ArrayList<String> ChildItemEventDescriptionFuture;
	 
	 public ArrayList<String> ChildItemEventDetailToday;
	 public ArrayList<String> ChildItemEventDetailTomorrow;
	 public ArrayList<String> ChildItemEventDetailFuture;
	 
	 public ArrayList<String> ChildItemEventTimeToday;
	 public ArrayList<String> ChildItemEventTimeTomorrow;
	 public ArrayList<String> ChildItemEventTimeFuture;
	 CalendarEvents c;
	 private DbHelper dbh;
	 private static final String EVENT_PLANNER_ID = "Event Planner";
	private ExpandableListView expandableList_events;
	private Context mContext;
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
	    getActionBar().setTitle("Event Planning> Current Events");
	    ChildItemEventTypeToday=c.getTodaysEventsType();
	    ChildItemEventTypeTomorrow=c.getTomorrowEventsType();
		ChildItemEventTypeFuture=c.getFutureEventsType();
		System.out.println("Printing    "+ChildItemEventTypeTomorrow);
		 
		ChildItemEventDescriptionToday=c.getTodaysEventsDestcription();
		ChildItemEventDescriptionTomorrow=c.getTommorowEventsDestcription();
		ChildItemEventDescriptionFuture=c.getFutureEventsDestcription();
		 
		ChildItemEventDetailToday=c.getTodaysEventsDetail();
		ChildItemEventDetailTomorrow=c.getTommorowEventsDetail();
		ChildItemEventDetailFuture=c.getFutureEventsDetail();
		 
		ChildItemEventTimeToday=c.getTodaysEventsTime(false);
		ChildItemEventTimeTomorrow=c.getTommorowEventsTime(false);
		ChildItemEventTimeFuture=c.getFutureEventsTime(false);
	    expandableList_events=(ExpandableListView) findViewById(R.id.expandableListView_calendarEvents);
	    eventsListAdapter adapter=new eventsListAdapter(mContext,groupItems,
	    											ChildItemEventTypeToday,
	    											ChildItemEventTypeTomorrow,
	    											ChildItemEventTypeFuture,
	    											ChildItemEventDescriptionToday,
	    											ChildItemEventDescriptionTomorrow,
	    											ChildItemEventDescriptionFuture,		 
	    											ChildItemEventDetailToday,
	    											ChildItemEventDetailTomorrow,
	    											ChildItemEventDetailFuture,		 
	    											ChildItemEventTimeToday,
	    											ChildItemEventTimeTomorrow,
	    											ChildItemEventTimeFuture,
	    											expandableList_events);
	expandableList_events.setAdapter(adapter);

	}
	
	class eventsListAdapter extends BaseExpandableListAdapter {

		 public String[] groupItem;
		 public ArrayList<String> ChildItemEventTypeToday;
		 public ArrayList<String> ChildItemEventTypeTomorrow;
		 public ArrayList<String> ChildItemEventTypeFuture;
		 
		 public ArrayList<String> ChildItemEventDescriptionToday;
		 public ArrayList<String> ChildItemEventDescriptionTomorrow;
		 public ArrayList<String> ChildItemEventDescriptionFuture;
		 
		 public ArrayList<String> ChildItemEventDetailToday;
		 public ArrayList<String> ChildItemEventDetailTomorrow;
		 public ArrayList<String> ChildItemEventDetailFuture;
		 
		 public ArrayList<String> ChildItemEventTimeToday;
		 public ArrayList<String> ChildItemEventTimeTomorrow;
		 public ArrayList<String> ChildItemEventTimeFuture;
		 public ExpandableListView eventsList;
		 public LayoutInflater minflater;
		 private int count;
		 public int lastExpandedGroupPosition;    
		 private Context mContext;
		

		 public eventsListAdapter(Context mContext,String[] grList,
				 					ArrayList<String> ChildItemEventTypeToday,
				 					ArrayList<String> ChildItemEventTypeTomorrow,
				 					ArrayList<String> ChildItemEventTypeFuture,
				 					ArrayList<String> ChildItemEventDescriptionToday,
				 					ArrayList<String> ChildItemEventDescriptionTomorrow,
				 					ArrayList<String> ChildItemEventDescriptionFuture,		 
				 					ArrayList<String> ChildItemEventDetailToday,
				 					ArrayList<String> ChildItemEventDetailTomorrow,
				 					ArrayList<String> ChildItemEventDetailFuture,		 
				 					ArrayList<String> ChildItemEventTimeToday,
				 					ArrayList<String> ChildItemEventTimeTomorrow,
				 					ArrayList<String> ChildItemEventTimeFuture,
				 					ExpandableListView eventsList) {
		  groupItem = grList;
		  this.mContext=mContext;
		  minflater = LayoutInflater.from(mContext);
		  this.ChildItemEventTypeToday = ChildItemEventTypeToday;
		  this.ChildItemEventTypeTomorrow=ChildItemEventTypeTomorrow;
		  this.ChildItemEventTypeFuture=ChildItemEventTypeFuture;
		  this.ChildItemEventDescriptionToday=ChildItemEventDescriptionToday;
		  this.ChildItemEventDescriptionTomorrow=ChildItemEventDescriptionTomorrow;
		  this.ChildItemEventDescriptionFuture=ChildItemEventDescriptionFuture;
		  this.ChildItemEventDetailToday = ChildItemEventDetailToday;
		  this.ChildItemEventDetailToday=ChildItemEventDetailToday;
		  this.ChildItemEventDetailTomorrow=ChildItemEventDetailTomorrow;
		  this.ChildItemEventDetailFuture=ChildItemEventDetailFuture;
		  this.ChildItemEventTimeToday=ChildItemEventTimeToday;
		  this.ChildItemEventTimeTomorrow=ChildItemEventTimeTomorrow;
		  this.ChildItemEventTimeFuture=ChildItemEventTimeFuture;
		  this.eventsList=eventsList;
		 
		 }
		 @Override
		 public long getChildId(int groupPosition, int childPosition) {
			 long id = 0;
				
				
			return id;
		 }

		 @Override
		 public View getChildView(int groupPosition, final int childPosition,
		   boolean isLastChild, View convertView, ViewGroup parent) {
			 Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
		       	      "fonts/Roboto-Thin.ttf");
		  
		   if(convertView==null){
			   convertView=minflater.inflate(R.layout.event_expandable_listview_single,null);
		   }
		   if(groupPosition==0){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventType);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventDescription);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventDetails);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_eventTime);
		   text.setText(ChildItemEventTypeToday.get(childPosition));
		   text2.setText(ChildItemEventDescriptionToday.get(childPosition));
		   text3.setText(ChildItemEventDetailToday.get(childPosition));
		   convertView.setBackgroundColor(Color.WHITE);
		  text4.setText(ChildItemEventTimeToday.get(childPosition));
		   //text.setTypeface(custom_font);
		   //text2.setTypeface(custom_font);
		   //text3.setTypeface(custom_font);
		   //text4.setTypeface(custom_font);
		   }else if(groupPosition==1){
			   TextView text=(TextView) convertView.findViewById(R.id.textView_eventType);
			   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventDescription);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventDetails);
			   TextView text4=(TextView) convertView.findViewById(R.id.textView_eventTime);
			   text.setText(ChildItemEventTypeTomorrow.get(childPosition));
			   text2.setText(ChildItemEventDescriptionTomorrow.get(childPosition));
			   text3.setText(ChildItemEventDetailTomorrow.get(childPosition));
			  text4.setText(ChildItemEventTimeTomorrow.get(childPosition));
			   convertView.setBackgroundColor(Color.WHITE);
			   //text.setTypeface(custom_font);
			   //text2.setTypeface(custom_font);
			   //text3.setTypeface(custom_font);
			   //text4.setTypeface(custom_font);
		   }else if(groupPosition==2){
			   TextView text=(TextView) convertView.findViewById(R.id.textView_eventType);
			   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventDescription);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventDetails);
			   TextView text4=(TextView) convertView.findViewById(R.id.textView_eventTime);
			   text.setText(ChildItemEventTypeFuture.get(childPosition));
			   text2.setText(ChildItemEventDescriptionFuture.get(childPosition));
			   text3.setText(ChildItemEventDetailFuture.get(childPosition));
			   text4.setText(ChildItemEventTimeFuture.get(childPosition));
			   convertView.setBackgroundColor(Color.WHITE);
			   //text.setTypeface(custom_font);
			   //text2.setTypeface(custom_font);
			   //text3.setTypeface(custom_font);
			   //text4.setTypeface(custom_font);
		   }
		  
		  return convertView;
		 }


		 													
		 @Override
		 public Object getGroup(int groupPosition) {
		  return null;
		 }


		 @Override
		 public View getGroupView(int groupPosition, boolean isExpanded,
		   View convertView, ViewGroup parent) {
			 if( convertView == null ){
			      
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			 text.setText(groupItem[groupPosition]);
			    return convertView;
		 }

		 
		 																																				
		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groupItem.length;
		}


		@Override
		public int getChildrenCount(int groupPosition) {
			if(groupPosition==0){
			count=ChildItemEventDetailToday.size();
			}else if (groupPosition==1){
			count=ChildItemEventDetailTomorrow.size();
			}else if(groupPosition==2){
				count=ChildItemEventDetailFuture.size();	
			}
			return count;
		}


		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}



		@Override
		public String[] getChild(int groupPosition, int childPosition) {
			String[] item = null;

			return item;
				
		}



		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}



		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

		public void onGroupExpanded(int groupPosition) {
	    	
	    	if(groupPosition != lastExpandedGroupPosition){
	            eventsList.collapseGroup(lastExpandedGroupPosition);
	       
	    }
	    	
	        super.onGroupExpanded(groupPosition);
	     
	        lastExpandedGroupPosition = groupPosition;
	        
	    }
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
