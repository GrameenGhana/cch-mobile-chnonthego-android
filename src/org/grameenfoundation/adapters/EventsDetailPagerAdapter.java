package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.cch.model.MyCalendarEvents;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventsDetailPagerAdapter extends BaseAdapter{
	public Context mContext;
	 public ArrayList<MyCalendarEvents> TodayCalendarEvents;
	 public ArrayList<MyCalendarEvents> todayEvents;
	 MyCalendarEvents calendarEvents=new MyCalendarEvents();

	 public EventsDetailPagerAdapter(Context c,
			 						 ArrayList<MyCalendarEvents> TodayCalendarEvents) {
      mContext = c;
      todayEvents = new ArrayList<MyCalendarEvents>();
      todayEvents.addAll(TodayCalendarEvents);
  }
	@Override
	public int getCount() {
	
		return todayEvents.size();
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
		//return Integer.valueOf(id.get(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 View list;	
	          if (convertView == null) {
	        	  LayoutInflater inflater = (LayoutInflater) mContext
	        		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        	  list = new View(mContext);
	        	  list = inflater.inflate(R.layout.events_detail_listview_single, null);
	       
	          } else {
	        	  list = (View) convertView;
	          }
	          TextView textView2 = (TextView) list.findViewById(R.id.textView_pagerEventName);
	          TextView textView3 = (TextView) list.findViewById(R.id.textView_pagerEventNumber);
	        	  textView2.setText(todayEvents.get(position).getEventType());
	        	  textView3.setText(todayEvents.get(position).getEventTime());
	          
	            Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
		          	      "fonts/Roboto-Thin.ttf");
		           // textView2.setTypeface(custom_font);
		            //textView3.setTypeface(custom_font);
	           
	      return list;
	    }
		
	}


