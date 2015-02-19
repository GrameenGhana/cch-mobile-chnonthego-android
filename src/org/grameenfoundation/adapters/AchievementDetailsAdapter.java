package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.activity.EventsViewActivity;
import org.grameenfoundation.cch.model.MyCalendarEvents;
import org.grameenfoundation.cch.model.RoutineActivity;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

	public class AchievementDetailsAdapter extends BaseExpandableListAdapter {

	 public String[] groupItem;
	 public ArrayList<MyCalendarEvents> CompletedEvents;
	 public ArrayList<MyCalendarEvents> UncompletedEvents;
	 public ExpandableListView eventsList;
	 public LayoutInflater minflater;
	 private int count;
	 public int lastExpandedGroupPosition;    
	 private Context mContext;
	private DbHelper dbh;
	 MyCalendarEvents calendarEvents=new MyCalendarEvents();

	 public AchievementDetailsAdapter(Context mContext,String[] grList,
			 					ArrayList<MyCalendarEvents> completedEvents,
			 					ArrayList<MyCalendarEvents> uncompletedEvents,
			 					ExpandableListView eventsList) {
		 dbh = new DbHelper(mContext);
		 
		 CompletedEvents = new ArrayList<MyCalendarEvents>();
		 UncompletedEvents = new ArrayList<MyCalendarEvents>();
		 CompletedEvents.addAll(completedEvents);
		 UncompletedEvents.addAll(uncompletedEvents);
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
	 
	   text.setText(CompletedEvents.get(childPosition).getEventType());
	   text2.setText(CompletedEvents.get(childPosition).getEventDescription());
	   text3.setText(CompletedEvents.get(childPosition).getEventLocation());
	   text4.setText(CompletedEvents.get(childPosition).getEventTime());
	   }else if(groupPosition==1){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventType);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventDescription);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventDetails);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_eventTime);
		   text.setText(UncompletedEvents.get(childPosition).getEventType());
		   text2.setText(UncompletedEvents.get(childPosition).getEventDescription());
		   text3.setText(UncompletedEvents.get(childPosition).getEventLocation());
		   text4.setText(UncompletedEvents.get(childPosition).getEventTime());
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
		return groupItem.length;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		if(groupPosition==0){
		count=CompletedEvents.size();
		}else if (groupPosition==1){
		count=UncompletedEvents.size();
		}
		return count;
	}
	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}
	@Override
	public String[] getChild(int groupPosition, int childPosition) {
		String[] item = null;
		if(groupPosition==0){
			item=new String[]{CompletedEvents.get(childPosition).getEventType(),
							  CompletedEvents.get(childPosition).getEventDescription(),
							  CompletedEvents.get(childPosition).getEventLocation(),
							  CompletedEvents.get(childPosition).getEventId()};
			}else if (groupPosition==1){
				item=new String[]{UncompletedEvents.get(childPosition).getEventType(),
								  UncompletedEvents.get(childPosition).getEventDescription(),
								  UncompletedEvents.get(childPosition).getEventLocation(),
								  UncompletedEvents.get(childPosition).getEventId()};
			}
		return item;
			
	}
	@Override
	public boolean hasStableIds() {
		return true;
	}
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
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

