package org.grameenfoundation.cch.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.model.MyCalendarEvents;
import org.grameenfoundation.poc.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Filterable;

public class EventsListViewActivity extends BaseActivity {

	private TextView title;
	private ListView listview_events;
	private DbHelper dbh;
	private ArrayList<MyCalendarEvents> PastLastMonthCalendarEvents;
	private ArrayList<MyCalendarEvents> PastThisMonthCalendarEvents;
	private ArrayList<MyCalendarEvents> YesterdayCalendarEvents;
	private ArrayList<MyCalendarEvents> TodayCalendarEvents;
	private ArrayList<MyCalendarEvents> TomorrowCalendarEvents;
	private ArrayList<MyCalendarEvents> FutureCalendarEvents;
	private String category;
	private PastListAdapter adapter;
	private FutureListAdapter adapter2;
	private CheckBox checkBox_complete;
	private CheckBox checkBox_incomplete;
	private CheckBox checkBox_pending;
	private LinearLayout filters;
	private ArrayList<MyCalendarEvents> mOriginalValues;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_events_list_view);	
	    title=(TextView) findViewById(R.id.textView_title);
	    dbh=new DbHelper(EventsListViewActivity.this);
	    listview_events=(ListView) findViewById(R.id.listView_events);
	    checkBox_complete=(CheckBox) findViewById(R.id.checkBox_complete);
	    checkBox_incomplete=(CheckBox) findViewById(R.id.checkBox_incomplete);
	    checkBox_pending=(CheckBox) findViewById(R.id.checkBox_pending);
	    filters=(LinearLayout) findViewById(R.id.filters);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("View/Update Events");
	    Bundle extras = getIntent().getExtras(); 
	    if (extras != null) {
	    	 category=extras.getString("event_category");
       }
	    
	    if(category.equals("past_last_month")){
	    	title.setText("Last Month's Events");
	    	PastLastMonthCalendarEvents = new ArrayList<MyCalendarEvents>();
	    	PastLastMonthCalendarEvents=dbh.getPastLastMonthEvents();
	    	 adapter=new PastListAdapter(EventsListViewActivity.this, PastLastMonthCalendarEvents);
	    	listview_events.setAdapter(adapter);
	    	checkBox_complete.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	 			
	 			@Override
	 			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	 				  if(checkBox_complete.isChecked()){
	 					 PastLastMonthCalendarEvents=dbh.getPastLastMonthEvents("complete");
	 						adapter.updateAdapter(PastLastMonthCalendarEvents);
	 						listview_events.setAdapter(adapter);
	 				  }else {
	 					 PastLastMonthCalendarEvents=dbh.getPastLastMonthEvents();
	 						adapter.updateAdapter(PastLastMonthCalendarEvents);
	 						listview_events.setAdapter(adapter);
	 				  }
	 			}
	 		});
	    	checkBox_incomplete.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	 			
	 			@Override
	 			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	 				  if(checkBox_incomplete.isChecked()){
	 					 PastLastMonthCalendarEvents=dbh.getPastLastMonthEvents("incomplete");
	 						adapter.updateAdapter(PastLastMonthCalendarEvents);
	 						listview_events.setAdapter(adapter);
	 				  }else {
	 					 PastLastMonthCalendarEvents=dbh.getPastLastMonthEvents();
	 						adapter.updateAdapter(PastLastMonthCalendarEvents);
	 						listview_events.setAdapter(adapter);
	 				  }
	 			}
	 		});
	    	checkBox_pending.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	 			
	 			@Override
	 			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	 				  if(checkBox_pending.isChecked()){
	 					 PastLastMonthCalendarEvents=dbh.getPastLastMonthEvents("");
	 						adapter.updateAdapter(PastLastMonthCalendarEvents);
	 						listview_events.setAdapter(adapter);
	 				  }else {
	 					 PastLastMonthCalendarEvents=dbh.getPastLastMonthEvents();
	 						adapter.updateAdapter(PastLastMonthCalendarEvents);
	 						listview_events.setAdapter(adapter);
	 				  }
	 			}
	 		});
	    }else if(category.equals("past_this_month")){
	    	title.setText("Past This Month's Events");
	    	PastThisMonthCalendarEvents = new ArrayList<MyCalendarEvents>();
	    	PastThisMonthCalendarEvents=dbh.getPastThisMonthEvents();
	    	 adapter=new PastListAdapter(EventsListViewActivity.this, PastThisMonthCalendarEvents);
	    	listview_events.setAdapter(adapter);
	    	checkBox_complete.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	 			
	 			@Override
	 			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	 				  if(checkBox_complete.isChecked()){
	 					 PastThisMonthCalendarEvents=dbh.getPastThisMonthEvents("complete");
	 						adapter.updateAdapter(PastThisMonthCalendarEvents);
	 						listview_events.setAdapter(adapter);
	 				  }else {
	 					 PastThisMonthCalendarEvents=dbh.getPastThisMonthEvents();
	 						adapter.updateAdapter(PastThisMonthCalendarEvents);
	 						listview_events.setAdapter(adapter);
	 				  }
	 			}
	 		});
	    	checkBox_incomplete.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	 			
	 			@Override
	 			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	 				  if(checkBox_incomplete.isChecked()){
	 					 PastThisMonthCalendarEvents=dbh.getPastThisMonthEvents("incomplete");
	 						adapter.updateAdapter(PastThisMonthCalendarEvents);
	 						listview_events.setAdapter(adapter);
	 				  }else {
	 					 PastThisMonthCalendarEvents=dbh.getPastThisMonthEvents();
	 						adapter.updateAdapter(PastThisMonthCalendarEvents);
	 						listview_events.setAdapter(adapter);
	 				  }
	 			}
	 		});
	    	checkBox_pending.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	 			
	 			@Override
	 			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	 				  if(checkBox_pending.isChecked()){
	 					 PastThisMonthCalendarEvents=dbh.getPastThisMonthEvents("");
	 						adapter.updateAdapter(PastThisMonthCalendarEvents);
	 						listview_events.setAdapter(adapter);
	 				  }else {
	 					 PastThisMonthCalendarEvents=dbh.getPastThisMonthEvents();
	 						adapter.updateAdapter(PastThisMonthCalendarEvents);
	 						listview_events.setAdapter(adapter);
	 				  }
	 			}
	 		});
	    }else if(category.equals("yesterday")){
	    	title.setText("Yesterday's Events");
	    	YesterdayCalendarEvents = new ArrayList<MyCalendarEvents>();
	    	YesterdayCalendarEvents=dbh.getYesterdaysEvents();
	    	 adapter=new PastListAdapter(EventsListViewActivity.this, YesterdayCalendarEvents);
	    	listview_events.setAdapter(adapter);
	    	checkBox_complete.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	 			
		 			@Override
		 			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		 				  if(checkBox_complete.isChecked()){
		 					 YesterdayCalendarEvents=dbh.getYesterdaysEvents("complete");
		 						adapter.updateAdapter(YesterdayCalendarEvents);
		 						listview_events.setAdapter(adapter);
		 				  }else {
		 					 YesterdayCalendarEvents=dbh.getYesterdaysEvents();
		 						adapter.updateAdapter(YesterdayCalendarEvents);
		 						listview_events.setAdapter(adapter);
		 				  }
		 			}
		 		});
		    	checkBox_incomplete.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		 			
		 			@Override
		 			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		 				  if(checkBox_incomplete.isChecked()){
		 					 YesterdayCalendarEvents=dbh.getYesterdaysEvents("incomplete");
		 						adapter.updateAdapter(YesterdayCalendarEvents);
		 						listview_events.setAdapter(adapter);
		 				  }else {
		 					 YesterdayCalendarEvents=dbh.getYesterdaysEvents();
		 						adapter.updateAdapter(YesterdayCalendarEvents);
		 						listview_events.setAdapter(adapter);
		 				  }
		 			}
		 		});
		    	checkBox_pending.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		 			
		 			@Override
		 			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		 				  if(checkBox_pending.isChecked()){
		 					 YesterdayCalendarEvents=dbh.getYesterdaysEvents("");
		 						adapter.updateAdapter(YesterdayCalendarEvents);
		 						listview_events.setAdapter(adapter);
		 				  }else {
		 					 YesterdayCalendarEvents=dbh.getYesterdaysEvents();
		 						adapter.updateAdapter(YesterdayCalendarEvents);
		 						listview_events.setAdapter(adapter);
		 				  }
		 			}
		 		});
	    }else if(category.equals("today")){
	    	filters.setVisibility(View.GONE);
	    	title.setText("Today's Events");
	    	TodayCalendarEvents = new ArrayList<MyCalendarEvents>();
			TodayCalendarEvents=dbh.getTodaysEvents();
	    	 adapter2=new FutureListAdapter(EventsListViewActivity.this, TodayCalendarEvents);
	    	listview_events.setAdapter(adapter2);
	    }else if(category.equals("tomorrow")){
	    	filters.setVisibility(View.GONE);
	    	title.setText("Tomorrow's Events");
	    	TomorrowCalendarEvents = new ArrayList<MyCalendarEvents>();
	    	TomorrowCalendarEvents=dbh.getTomorrowEvents();
	    	 adapter2=new FutureListAdapter(EventsListViewActivity.this, TomorrowCalendarEvents);
	    	listview_events.setAdapter(adapter2);
	    }else if(category.equals("future")){
	    	filters.setVisibility(View.GONE);
	    	title.setText("Future Events");
	    	FutureCalendarEvents = new ArrayList<MyCalendarEvents>();
	    	FutureCalendarEvents=dbh.getFutureEvents();
	    	 adapter2=new FutureListAdapter(EventsListViewActivity.this, FutureCalendarEvents);
	    	listview_events.setAdapter(adapter2);
	    }
	    listview_events.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(category.equals("past_last_month")||category.equals("past_this_month")||category.equals("yesterday")){
				String[] selected_items=adapter.getItem(position);
				Intent intent=new Intent(EventsListViewActivity.this,ViewEventDetailsActivity.class);
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
				}else if(category.equals("today")||category.equals("tomorrow")||category.equals("future")){
					String[] selected_items=adapter2.getItem(position);
					Intent intent=new Intent(EventsListViewActivity.this,ViewEventDetailsActivity.class);
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
				}
			}
		});
	  
	}
	class PastListAdapter extends BaseAdapter implements Filterable{
		Context mContext;
		ArrayList<MyCalendarEvents> listItems;
		 public LayoutInflater minflater;
		public PastListAdapter(Context c,ArrayList<MyCalendarEvents> ListItems){
		mContext=c;
		listItems = new ArrayList<MyCalendarEvents>();
		listItems.addAll(ListItems);
		}
		@Override
		public int getCount() {
			return listItems.size();
		}

		@Override
		public String[] getItem(int position) {
			String[] item;
			item=new String[]{listItems.get(position).getEventType(),
					listItems.get(position).getEventDescription(),
					listItems.get(position).getEventLocation(),
					listItems.get(position).getEventId(),
					listItems.get(position).getEventStartDate(),
					listItems.get(position).getEventEndDate(),
					listItems.get(position).getEventStatus(),
					listItems.get(position).getEventCategory(),
					listItems.get(position).getEventComment(),
					listItems.get(position).getEventJustification()};
			return item;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View list=null;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) mContext
		        		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				  list = new View(mContext);
				  list = inflater.inflate(R.layout.past_event_listview_single, null);
				 
				
			}  else {
				 list = (View) convertView;  
			}
			 TextView eventname=(TextView) list.findViewById(R.id.textView_eventname);
			 TextView event_justification=(TextView) list.findViewById(R.id.textView_justification);
			 TextView eventdate=(TextView) list.findViewById(R.id.textView_date);
			 ImageView eventStatus=(ImageView) list.findViewById(R.id.imageView_status);
			 ImageView commentStatus=(ImageView) list.findViewById(R.id.imageView_commentStatus);
			 
			 eventname.setText(listItems.get(position).getEventType()+" at \n "+listItems.get(position).getEventLocation());
			 String first="<font color='#520000'><strong>"+"Justification: "+"</strong></font>";
			 if(listItems.get(position).getEventJustification().equals("null")){
				 event_justification.setText(Html.fromHtml(first+""));
			 }else{
			   event_justification.setText(Html.fromHtml(first+listItems.get(position).getEventJustification()));
			 }
			   eventdate.setText(listItems.get(position).getEventTime());
			 
			   try{
				   
			   if(listItems.get(position).getEventStatus()==null ||listItems.get(position).getEventStatus()==""){
				   eventStatus.setImageResource(R.drawable.ic_question);
			   }else if (listItems.get(position).getEventStatus().equals("complete")){
				   eventStatus.setImageResource(R.drawable.ic_complete_new);
				   event_justification.setVisibility(View.GONE);
			   }else if(listItems.get(position).getEventStatus().equals("incomplete")){
				   eventStatus.setImageResource(R.drawable.ic_close);
			   }
			   if(listItems.get(position).getEventComment().equals("")){
				   commentStatus.setVisibility(View.GONE);
			   }else{
				   commentStatus.setVisibility(View.VISIBLE);
				   commentStatus.setImageResource(R.drawable.ic_comment);
			   }
			   }catch(Exception e){
				   e.printStackTrace();
			   }
			    return list;
		}
		@Override
		 public Filter getFilter() {
		        Filter filter = new Filter() {
		         
					@Override
		            protected void publishResults(CharSequence constraint, FilterResults results) {
						listItems = (ArrayList<MyCalendarEvents>) results.values; // has the filtered values
		                notifyDataSetChanged();  // notifies the data with new filtered values
		            }
					
		           @Override
		            protected FilterResults performFiltering(CharSequence constraint) {
		               FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
		               ArrayList<MyCalendarEvents> FilteredArrList = new ArrayList<MyCalendarEvents>();

		               if (mOriginalValues == null) {
		            	  
		                   mOriginalValues = new ArrayList<MyCalendarEvents>(listItems); // saves the original data in mOriginalValues
		               }
		               if (constraint == null || constraint.length() == 0) {
		            	 
		                   // set the Original result to return
		                   results.count = mOriginalValues.size();
		                   results.values = mOriginalValues;
		               } else {
		            	   mOriginalValues = new ArrayList<MyCalendarEvents>();
		                   constraint = constraint.toString().toLowerCase();
		                
		                   for (int i = 0; i < mOriginalValues.size(); i++) {
		                       String data = mOriginalValues.get(i).getEventStatus();
		                       
		                       if (data.toLowerCase().startsWith(constraint.toString())) {
		                    	  
		                           FilteredArrList.add(new MyCalendarEvents(mOriginalValues.get(i).getEventType(),
		                                                         mOriginalValues.get(i).getEventDescription(),
		                                                         mOriginalValues.get(i).getEventLocation(),
		                                                         mOriginalValues.get(i).getEventId(),
		                                                         mOriginalValues.get(i).getEventStartDate(),
		                                                         mOriginalValues.get(i).getEventEndDate(),
		                                                         mOriginalValues.get(i).getEventStatus(),
		                                                         mOriginalValues.get(i).getEventCategory(),
		                                                         mOriginalValues.get(i).getEventComment(),
		                                                         mOriginalValues.get(i).getEventJustification()
		                                                        ));
		                       }
		                   }
		                   // set the Filtered result to return
		                   results.count = FilteredArrList.size();
		                   results.values = FilteredArrList;
		               }
		               return results;
		           }
		        };
		        return filter;
		            }
		public void updateAdapter(ArrayList<MyCalendarEvents> ListItems) {
	        this.listItems= ListItems;

	        //and call notifyDataSetChanged
	        notifyDataSetChanged();
	    }
		
	}
	class FutureListAdapter extends BaseAdapter {
		Context mContext;
		ArrayList<MyCalendarEvents> listItems;
		
		public FutureListAdapter(Context c,ArrayList<MyCalendarEvents> ListItems){
		mContext=c;
		this.listItems = new ArrayList<MyCalendarEvents>();
		this.listItems.addAll(ListItems);
		}
		@Override
		public int getCount() {
			return listItems.size();
		}

		@Override
		public String[] getItem(int position) {
			String[] item;
			item=new String[]{listItems.get(position).getEventType(),
					listItems.get(position).getEventDescription(),
					listItems.get(position).getEventLocation(),
					listItems.get(position).getEventId(),
					listItems.get(position).getEventStartDate(),
					listItems.get(position).getEventEndDate(),
					listItems.get(position).getEventStatus(),
					listItems.get(position).getEventCategory(),
					listItems.get(position).getEventComment(),
					listItems.get(position).getEventJustification()};
			return item;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View list=null;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) mContext
		        		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				  list = new View(mContext);
				  list = inflater.inflate(R.layout.event_expandable_listview_single, null);
			}  else {
				 list = (View) convertView;  
			}
			TextView text=(TextView) list.findViewById(R.id.textView_eventType);
			  // TextView text2=(TextView) list.findViewById(R.id.textView_eventDescription);
			   TextView text3=(TextView) list.findViewById(R.id.textView_eventDetails);
			   TextView text4=(TextView) list.findViewById(R.id.textView_eventTime);
			   try{
				   text.setText(listItems.get(position).getEventType()+ " at ");
			  // text2.setText(listItems.get(position).getEventDescription());
			   text3.setText(listItems.get(position).getEventLocation());
			   text4.setText(listItems.get(position).getEventTime());
			   }catch(Exception e){
				   e.printStackTrace();
			   }
			    return list;
		}
		
		public void updateAdapter(ArrayList<MyCalendarEvents> ListItems) {
	        this.listItems= ListItems;

	        //and call notifyDataSetChanged
	        notifyDataSetChanged();
	    }
	}
}
