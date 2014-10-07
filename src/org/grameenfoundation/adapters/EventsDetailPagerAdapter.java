package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.grameenfoundation.chnonthego.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventsDetailPagerAdapter extends BaseAdapter{
	 private Context mContext;
	 private final ArrayList<String> eventName;
	 private final ArrayList<String> eventNumber;
	
	 public EventsDetailPagerAdapter(Context c,ArrayList<String> eventName ,ArrayList<String> eventNumber) {
      mContext = c;
      this.eventName = eventName;
      this.eventNumber=eventNumber;
  }
	@Override
	public int getCount() {
	
		return eventName.size();
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
	        	  textView2.setText(eventName.get(position));
	        	  textView3.setText(eventNumber.get(position));
	          
	            Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
		          	      "fonts/Roboto-Thin.ttf");
		            textView2.setTypeface(custom_font);
		            textView3.setTypeface(custom_font);
	           
	      return list;
	    }
		
	}


