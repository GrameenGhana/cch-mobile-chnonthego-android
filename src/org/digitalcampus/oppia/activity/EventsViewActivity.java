package org.digitalcampus.oppia.activity;


import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.calendar.CalendarEvents;
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
	private Button button_viewCalendar;
	private ArrayList<String> ChildItemEventIdToday;
	private ArrayList<String> ChildItemEventIdTomorrow;
	private ArrayList<String> ChildItemEventIdFuture;
	private eventsListAdapter adapter;
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
	    getActionBar().setTitle("Event Planner");
	    getActionBar().setSubtitle("Planned Events");
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
		
		ChildItemEventIdToday=c.getTodaysEventsId();
		ChildItemEventIdTomorrow=c.getTomorrowEventsId();
		ChildItemEventIdFuture=c.getFutureEventsId();
	    expandableList_events=(ExpandableListView) findViewById(R.id.expandableListView_calendarEvents);
	    adapter=new eventsListAdapter(mContext,groupItems,
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
	    											
	    											ChildItemEventIdToday,
	    											ChildItemEventIdTomorrow,
	    											ChildItemEventIdFuture,
	    											
	    											expandableList_events);
	expandableList_events.setAdapter(adapter);
	button_viewCalendar=(Button) findViewById(R.id.button_viewCalendar);
	button_viewCalendar.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent intent =  new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("content://com.android.calendar/time"));
			startActivity(intent);
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
			return true;
		}
		
	});
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
		 
		 private ArrayList<String> ChildItemEventIdToday;
			private ArrayList<String> ChildItemEventIdTomorrow;
			private ArrayList<String> ChildItemEventIdFuture;
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
				 					
				 					ArrayList<String> ChildItemEventIdToday,
				 					ArrayList<String> ChildItemEventIdTomorrow,
				 					ArrayList<String> ChildItemEventIdFuture,
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
		  this.ChildItemEventDetailTomorrow=ChildItemEventDetailTomorrow;
		  this.ChildItemEventDetailFuture=ChildItemEventDetailFuture;
		  
		  this.ChildItemEventTimeToday=ChildItemEventTimeToday;
		  this.ChildItemEventTimeTomorrow=ChildItemEventTimeTomorrow;
		  this.ChildItemEventTimeFuture=ChildItemEventTimeFuture;
		  
		  this.ChildItemEventIdToday=ChildItemEventIdToday;
		  this.ChildItemEventIdTomorrow=ChildItemEventIdTomorrow;
		  this.ChildItemEventIdFuture=ChildItemEventIdFuture;
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
		   if(ChildItemEventTypeToday.size()>0){
		   text.setText(ChildItemEventTypeToday.get(childPosition));
		   }if(ChildItemEventDescriptionToday.size()>0){
		   text2.setText(ChildItemEventDescriptionToday.get(childPosition));
		   }if(ChildItemEventDetailToday.size()>0){
		   text3.setText(ChildItemEventDetailToday.get(childPosition));
		   }
		  
		   if(ChildItemEventTimeToday.size()>0){
		  text4.setText(ChildItemEventTimeToday.get(childPosition));
		  
		   }
		 
		   //text.setTypeface(custom_font);
		   //text2.setTypeface(custom_font);
		   //text3.setTypeface(custom_font);
		   //text4.setTypeface(custom_font);
		   }else if(groupPosition==1){
			   TextView text=(TextView) convertView.findViewById(R.id.textView_eventType);
			   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventDescription);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventDetails);
			   TextView text4=(TextView) convertView.findViewById(R.id.textView_eventTime);
			   if(ChildItemEventTypeTomorrow.size()>0){
			   text.setText(ChildItemEventTypeTomorrow.get(childPosition));
			   }if(ChildItemEventDescriptionTomorrow.size()>0){
			   text2.setText(ChildItemEventDescriptionTomorrow.get(childPosition));
			   }if(ChildItemEventDetailTomorrow.size()>0){
			   text3.setText(ChildItemEventDetailTomorrow.get(childPosition));
			   }if(ChildItemEventTimeTomorrow.size()>0){
			  text4.setText(ChildItemEventTimeTomorrow.get(childPosition));
			   }
			   //text.setTypeface(custom_font);
			   //text2.setTypeface(custom_font);
			   //text3.setTypeface(custom_font);
			   //text4.setTypeface(custom_font);
		   }else if(groupPosition==2){
			   TextView text=(TextView) convertView.findViewById(R.id.textView_eventType);
			   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventDescription);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventDetails);
			   TextView text4=(TextView) convertView.findViewById(R.id.textView_eventTime);
			   if(ChildItemEventTypeFuture.size()>0){
			   text.setText(ChildItemEventTypeFuture.get(childPosition));
			   }
			   if(ChildItemEventDescriptionFuture.size()>0){
			   text2.setText(ChildItemEventDescriptionFuture.get(childPosition));
			   }
			   if(ChildItemEventDetailFuture.size()>0){
			   text3.setText(ChildItemEventDetailFuture.get(childPosition));
			   }
			   if(ChildItemEventTimeFuture.size()>0){
			   text4.setText(ChildItemEventTimeFuture.get(childPosition));
			   }
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
				count=ChildItemEventIdFuture.size();	
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
				item=new String[]{ChildItemEventTypeToday.get(childPosition),
						ChildItemEventDescriptionToday.get(childPosition)
							,ChildItemEventDetailToday.get(childPosition),
							ChildItemEventIdToday.get(childPosition)};
				}else if (groupPosition==1){
					item=new String[]{ChildItemEventTypeTomorrow.get(childPosition),
							ChildItemEventDescriptionTomorrow.get(childPosition)
							,ChildItemEventDetailTomorrow.get(childPosition),
							ChildItemEventIdTomorrow.get(childPosition)};
				}else if(groupPosition==2){
					item=new String[]{ChildItemEventTypeFuture.get(childPosition),
							ChildItemEventDescriptionFuture.get(childPosition)
							,ChildItemEventDetailFuture.get(childPosition),
							ChildItemEventIdFuture.get(childPosition)};
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
