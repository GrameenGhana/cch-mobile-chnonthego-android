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
import android.widget.ImageView;
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
	private ArrayList<MyCalendarEvents> pastLastMonthEvents;
	private ArrayList<MyCalendarEvents> pastThisMonthEvents;
	private ArrayList<MyCalendarEvents> yesterdayEvents;
	private ArrayList<MyCalendarEvents> todayEvents;
	private ArrayList<MyCalendarEvents> tomorowsEvents;
	private ArrayList<MyCalendarEvents> futureEvents;
	 MyCalendarEvents calendarEvents=new MyCalendarEvents();

	 public CalendarEventsViewAdapter(Context mContext,String[] grList,
			 					ArrayList<MyCalendarEvents> PastLastMonthEvents,
			 					ArrayList<MyCalendarEvents> PastThisMonthEvents,
			 					ArrayList<MyCalendarEvents> YesterdayEvents,
			 					ArrayList<MyCalendarEvents> TodayCalendarEvents,
			 					ArrayList<MyCalendarEvents> TomorrowCalendarEvents,
			 					ArrayList<MyCalendarEvents> FutureCalendarEvents,
			 					ExpandableListView eventsList) {
		 dbh = new DbHelper(mContext);
		 pastLastMonthEvents = new ArrayList<MyCalendarEvents>();
		 pastThisMonthEvents = new ArrayList<MyCalendarEvents>();
		 yesterdayEvents = new ArrayList<MyCalendarEvents>();
		 todayEvents = new ArrayList<MyCalendarEvents>();
		 tomorowsEvents = new ArrayList<MyCalendarEvents>();
		 futureEvents = new ArrayList<MyCalendarEvents>();
		 
		 pastLastMonthEvents.addAll(PastLastMonthEvents);
		 pastThisMonthEvents.addAll(PastThisMonthEvents);
		 yesterdayEvents.addAll(YesterdayEvents);
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
			 convertView=minflater.inflate(R.layout.past_event_listview_single,null);
		   }
		 
		  /*
		   	switch (itemType) {
		   		case 0:
		   			convertView=minflater.inflate(R.layout.past_event_listview_single,null);
		   		break;
		   		case 1:
		   			convertView=minflater.inflate(R.layout.past_event_listview_single,null);
		   		break;
		   		case 2:
		   			convertView=minflater.inflate(R.layout.past_event_listview_single,null);
		   		break;
		   		case 3:
		   			convertView=minflater.inflate(R.layout.event_expandable_listview_single,null);
		   		break;
		   		case 4:
		   			convertView=minflater.inflate(R.layout.event_expandable_listview_single,null);
		   		break;
		   		case 5:
		   			convertView=minflater.inflate(R.layout.event_expandable_listview_single,null);
		   		break;
		   		
	   }*/
		  TextView eventname=(TextView) convertView.findViewById(R.id.textView_eventname);
		   TextView event_justification=(TextView) convertView.findViewById(R.id.textView_justification);
		   TextView eventdate=(TextView) convertView.findViewById(R.id.textView_date);
		   ImageView eventStatus=(ImageView) convertView.findViewById(R.id.imageView_status);
		   ImageView commentStatus=(ImageView) convertView.findViewById(R.id.imageView_commentStatus);
	   if(groupPosition==0){
		   eventname.setText(pastLastMonthEvents.get(childPosition).getEventType()+" at "+pastLastMonthEvents.get(childPosition).getEventLocation());
		   event_justification.setText(pastLastMonthEvents.get(childPosition).getEventJustification());
		   eventdate.setText(pastLastMonthEvents.get(childPosition).getEventTime());
		   if(pastLastMonthEvents.get(childPosition).getEventStatus().equals("incomplete")){
			   eventStatus.setImageResource(R.drawable.ic_close);
		   }else if (pastLastMonthEvents.get(childPosition).getEventStatus().equals("complete")){
			   eventStatus.setImageResource(R.drawable.ic_complete_new);
		   }else{
			   eventStatus.setImageResource(R.drawable.ic_question);
		   }
		   
		   if(pastLastMonthEvents.get(childPosition).getEventComment().equals("")){
			   commentStatus.setVisibility(View.GONE);
		   }else{
			   commentStatus.setImageResource(R.drawable.ic_comment);
		   }
	 
	   }else if(groupPosition==1){
	 
		   eventname.setText(pastThisMonthEvents.get(childPosition).getEventType()+" at "+pastThisMonthEvents.get(childPosition).getEventLocation());
		   event_justification.setText(pastThisMonthEvents.get(childPosition).getEventJustification());
		   eventdate.setText(pastThisMonthEvents.get(childPosition).getEventTime());
		   if(pastThisMonthEvents.get(childPosition).getEventStatus().equals("incomplete")){
			   eventStatus.setImageResource(R.drawable.ic_close);
		   }else if (pastThisMonthEvents.get(childPosition).getEventStatus().equals("complete")){
			   eventStatus.setImageResource(R.drawable.ic_complete_new);
		   }else{
			   eventStatus.setImageResource(R.drawable.ic_question);
		   }
		   
		   if(pastThisMonthEvents.get(childPosition).getEventComment().equals("")){
			   commentStatus.setVisibility(View.GONE);
		   }else{
			   commentStatus.setImageResource(R.drawable.ic_comment);
		   }
	   }
	   else if(groupPosition==2){
		   eventname.setText(yesterdayEvents.get(childPosition).getEventType()+" at "+yesterdayEvents.get(childPosition).getEventLocation());
		   event_justification.setText(yesterdayEvents.get(childPosition).getEventJustification());
		   eventdate.setText(yesterdayEvents.get(childPosition).getEventTime());
		   if(yesterdayEvents.get(childPosition).getEventStatus().equals("incomplete")){
			   eventStatus.setImageResource(R.drawable.ic_close);
		   }else if (yesterdayEvents.get(childPosition).getEventStatus().equals("complete")){
			   eventStatus.setImageResource(R.drawable.ic_complete_new);
		   }else{
			   eventStatus.setImageResource(R.drawable.ic_question);
		   }
		   
		   if(yesterdayEvents.get(childPosition).getEventComment().equals("")){
			   commentStatus.setVisibility(View.GONE);
		   }else{
			   commentStatus.setImageResource(R.drawable.ic_comment);
		   }
	   }else if(groupPosition==3){
		   eventname.setText(todayEvents.get(childPosition).getEventType()+" at "+todayEvents.get(childPosition).getEventLocation());
		   event_justification.setVisibility(View.GONE);
		   eventdate.setText(todayEvents.get(childPosition).getEventTime());
		   eventStatus.setVisibility(View.GONE);
		   commentStatus.setVisibility(View.GONE);
		  /* TextView text=(TextView) convertView.findViewById(R.id.textView_eventType);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventDescription);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventDetails);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_eventTime);
		   text.setText(todayEvents.get(childPosition).getEventType());
		   text2.setText(todayEvents.get(childPosition).getEventDescription());
		   text3.setText(todayEvents.get(childPosition).getEventLocation());
		   text4.setText(todayEvents.get(childPosition).getEventTime());*/
	   }else if(groupPosition==4){
		   eventname.setText(tomorowsEvents.get(childPosition).getEventType()+" at "+tomorowsEvents.get(childPosition).getEventLocation());
		   event_justification.setVisibility(View.GONE);
		   eventdate.setText(tomorowsEvents.get(childPosition).getEventTime());
		   eventStatus.setVisibility(View.GONE);
		   commentStatus.setVisibility(View.GONE);
		   /*
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventType);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventDescription);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventDetails);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_eventTime);
		   text.setText(tomorowsEvents.get(childPosition).getEventType());
		   text2.setText(tomorowsEvents.get(childPosition).getEventDescription());
		   text3.setText(tomorowsEvents.get(childPosition).getEventLocation());
		   text4.setText(tomorowsEvents.get(childPosition).getEventTime());*/
	   }else if(groupPosition==5){
		  
		   eventname.setText(futureEvents.get(childPosition).getEventType()+" at "+futureEvents.get(childPosition).getEventLocation());
		   event_justification.setVisibility(View.GONE);
		   eventdate.setText(futureEvents.get(childPosition).getEventTime());
		   eventStatus.setVisibility(View.GONE);
		   commentStatus.setVisibility(View.GONE);
		   /*
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventType);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventDescription);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventDetails);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_eventTime);
		   text.setText(futureEvents.get(childPosition).getEventType());
		   text2.setText(futureEvents.get(childPosition).getEventDescription());
		   text3.setText(futureEvents.get(childPosition).getEventLocation());
		   text4.setText(futureEvents.get(childPosition).getEventTime());*/
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
		count=pastLastMonthEvents.size();
		}else if (groupPosition==1){
		count=pastThisMonthEvents.size();
		}else if(groupPosition==2){
			count=yesterdayEvents.size();	
		}else if(groupPosition==3){
			count=todayEvents.size();	
		}else if(groupPosition==4){
			count=tomorowsEvents.size();	
		}else if(groupPosition==5){
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
			item=new String[]{pastLastMonthEvents.get(childPosition).getEventType(),
					pastLastMonthEvents.get(childPosition).getEventDescription(),
					pastLastMonthEvents.get(childPosition).getEventLocation(),
					pastLastMonthEvents.get(childPosition).getEventId(),
					pastLastMonthEvents.get(childPosition).getEventStartDate(),
					pastLastMonthEvents.get(childPosition).getEventEndDate(),
					pastLastMonthEvents.get(childPosition).getEventStatus(),
					pastLastMonthEvents.get(childPosition).getEventCategory(),
					pastLastMonthEvents.get(childPosition).getEventComment(),
					pastLastMonthEvents.get(childPosition).getEventJustification()};
			}else if (groupPosition==1){
				item=new String[]{pastThisMonthEvents.get(childPosition).getEventType(),
						pastThisMonthEvents.get(childPosition).getEventDescription(),
						pastThisMonthEvents.get(childPosition).getEventLocation(),
						pastThisMonthEvents.get(childPosition).getEventId(),
						pastThisMonthEvents.get(childPosition).getEventStartDate(),
						pastThisMonthEvents.get(childPosition).getEventEndDate(),
						pastThisMonthEvents.get(childPosition).getEventStatus(),
						pastThisMonthEvents.get(childPosition).getEventCategory(),
						pastThisMonthEvents.get(childPosition).getEventComment(),
						pastThisMonthEvents.get(childPosition).getEventJustification()};
			}else if(groupPosition==2){
				item=new String[]{yesterdayEvents.get(childPosition).getEventType(),
						yesterdayEvents.get(childPosition).getEventDescription(),
						yesterdayEvents.get(childPosition).getEventLocation(),
						yesterdayEvents.get(childPosition).getEventId(),
						yesterdayEvents.get(childPosition).getEventStartDate(),
						yesterdayEvents.get(childPosition).getEventEndDate(),
						yesterdayEvents.get(childPosition).getEventStatus(),
						yesterdayEvents.get(childPosition).getEventCategory(),
						yesterdayEvents.get(childPosition).getEventComment(),
						yesterdayEvents.get(childPosition).getEventJustification()};
			}else if(groupPosition==3){
				item=new String[]{todayEvents.get(childPosition).getEventType(),
						todayEvents.get(childPosition).getEventDescription(),
						todayEvents.get(childPosition).getEventLocation(),
						todayEvents.get(childPosition).getEventId(),
						todayEvents.get(childPosition).getEventStartDate(),
						todayEvents.get(childPosition).getEventEndDate(),
						todayEvents.get(childPosition).getEventStatus(),
						todayEvents.get(childPosition).getEventCategory(),
						todayEvents.get(childPosition).getEventComment(),
						todayEvents.get(childPosition).getEventJustification()};
			}
			else if(groupPosition==4){
				item=new String[]{tomorowsEvents.get(childPosition).getEventType(),
						tomorowsEvents.get(childPosition).getEventDescription(),
						tomorowsEvents.get(childPosition).getEventLocation(),
						tomorowsEvents.get(childPosition).getEventId(),
						tomorowsEvents.get(childPosition).getEventStartDate(),
						tomorowsEvents.get(childPosition).getEventEndDate(),
						tomorowsEvents.get(childPosition).getEventStatus(),
						tomorowsEvents.get(childPosition).getEventCategory(),
						tomorowsEvents.get(childPosition).getEventComment(),
						tomorowsEvents.get(childPosition).getEventJustification()};
			}else if(groupPosition==5){
				item=new String[]{futureEvents.get(childPosition).getEventType(),
						futureEvents.get(childPosition).getEventDescription(),
						futureEvents.get(childPosition).getEventLocation(),
						futureEvents.get(childPosition).getEventId(),
						futureEvents.get(childPosition).getEventStartDate(),
						futureEvents.get(childPosition).getEventEndDate(),
						futureEvents.get(childPosition).getEventStatus(),
						futureEvents.get(childPosition).getEventCategory(),
						futureEvents.get(childPosition).getEventComment(),
						futureEvents.get(childPosition).getEventJustification()};
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

