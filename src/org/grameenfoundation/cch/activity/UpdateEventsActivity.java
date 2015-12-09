package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.CalendarEventsViewAdapter;
import org.grameenfoundation.cch.model.MyCalendarEvents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class UpdateEventsActivity extends Activity {

	private ListView listView;
	private DbHelper db;
	private FutureListAdapter adapter;
	private ArrayList<MyCalendarEvents> CalendarEvents;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_event_update);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Update Events");
	    listView=(ListView) findViewById(R.id.listView1);
	    db=new DbHelper(UpdateEventsActivity.this);
	    CalendarEvents=db.getCalendarEventsForUpdate();
	    adapter=new FutureListAdapter(UpdateEventsActivity.this,CalendarEvents);
    	listView.setAdapter(adapter);
    	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String[] selected_items=adapter.getItem(position);
				Intent intent=new Intent(UpdateEventsActivity.this,ViewEventDetailsActivity.class);
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
		});
	}
	class FutureListAdapter extends BaseAdapter{
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
