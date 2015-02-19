package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.activity.EventsViewActivity;
import org.grameenfoundation.cch.model.MyCalendarEvents;
import org.grameenfoundation.cch.model.RoutineActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

	public class CalendarEventsViewAdapter extends BaseExpandableListAdapter {

	 public String[] groupItem;
	 public ArrayList<MyCalendarEvents> TodayCalendarEvents;
	 public ArrayList<MyCalendarEvents> TomorrowCalendarEvents;
	 public ArrayList<MyCalendarEvents> FutureCalendarEvents;
	 public ExpandableListView eventsList;
	 public LayoutInflater minflater;
	 private int count;
	 public int lastExpandedGroupPosition;    
	 private Context mContext;
	private DbHelper dbh;
	private ArrayList<MyCalendarEvents> todayEvents;
	private ArrayList<MyCalendarEvents> tomorowsEvents;
	private ArrayList<MyCalendarEvents> futureEvents;
	 MyCalendarEvents calendarEvents=new MyCalendarEvents();

	 public CalendarEventsViewAdapter(Context mContext,String[] grList,
			 					
			 					ArrayList<MyCalendarEvents> TodayCalendarEvents,
			 					ArrayList<MyCalendarEvents> TomorrowCalendarEvents,
			 					ArrayList<MyCalendarEvents> FutureCalendarEvents,
			 					ExpandableListView eventsList) {
		 dbh = new DbHelper(mContext);
		 
		 todayEvents = new ArrayList<MyCalendarEvents>();
		 tomorowsEvents = new ArrayList<MyCalendarEvents>();
		 futureEvents = new ArrayList<MyCalendarEvents>();
		 todayEvents.addAll(TodayCalendarEvents);
		 tomorowsEvents.addAll(TomorrowCalendarEvents);
		 futureEvents.addAll(FutureCalendarEvents);
	  groupItem = grList;
	  this.mContext=mContext;
	  minflater = LayoutInflater.from(mContext);
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
	  
	   if(convertView==null){
		   convertView=minflater.inflate(R.layout.event_expandable_listview_single,null);
	   }
	   if(groupPosition==0){
	   TextView text=(TextView) convertView.findViewById(R.id.textView_eventType);
	   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventDescription);
	   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventDetails);
	   TextView text4=(TextView) convertView.findViewById(R.id.textView_eventTime);
	 
	   text.setText(todayEvents.get(childPosition).getEventType());
	   text2.setText(todayEvents.get(childPosition).getEventDescription());
	   text3.setText(todayEvents.get(childPosition).getEventLocation());
	  text4.setText(todayEvents.get(childPosition).getEventTime());
	 
	   }else if(groupPosition==1){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventType);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventDescription);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventDetails);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_eventTime);
		   text.setText(tomorowsEvents.get(childPosition).getEventType());
		   text2.setText(tomorowsEvents.get(childPosition).getEventDescription());
		   text3.setText(tomorowsEvents.get(childPosition).getEventLocation());
		   text4.setText(tomorowsEvents.get(childPosition).getEventTime());
	   }else if(groupPosition==2){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventType);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventDescription);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventDetails);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_eventTime);
		   text.setText(futureEvents.get(childPosition).getEventType());
		   text2.setText(futureEvents.get(childPosition).getEventDescription());
		   text3.setText(futureEvents.get(childPosition).getEventLocation());
		   text4.setText(futureEvents.get(childPosition).getEventTime());
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
		count=todayEvents.size();
		}else if (groupPosition==1){
		count=tomorowsEvents.size();
		}else if(groupPosition==2){
			count=futureEvents.size();	
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
		if(groupPosition==0){
			item=new String[]{todayEvents.get(childPosition).getEventType(),
						todayEvents.get(childPosition).getEventDescription(),
						todayEvents.get(childPosition).getEventLocation(),
						todayEvents.get(childPosition).getEventId()};
			}else if (groupPosition==1){
				item=new String[]{tomorowsEvents.get(childPosition).getEventType(),
						tomorowsEvents.get(childPosition).getEventDescription(),
						tomorowsEvents.get(childPosition).getEventLocation(),
						tomorowsEvents.get(childPosition).getEventId()};
			}else if(groupPosition==2){
				item=new String[]{futureEvents.get(childPosition).getEventType(),
						futureEvents.get(childPosition).getEventDescription(),
						futureEvents.get(childPosition).getEventLocation(),
						futureEvents.get(childPosition).getEventId()};
			}
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

