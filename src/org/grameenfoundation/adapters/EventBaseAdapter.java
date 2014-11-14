package org.grameenfoundation.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;




import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.mobile.learningGF.R.color;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventBaseAdapter extends BaseAdapter{
	 private Context mContext;
	 private final ArrayList<String> eventName;
	 private final ArrayList<String> eventNumber;
	 private final ArrayList<String> eventPeriod;
	 private final ArrayList<String> id;
	 private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();
	 public EventBaseAdapter(Context c,ArrayList<String> eventName ,ArrayList<String> eventNumber,ArrayList<String> eventPeriod,ArrayList<String> id) {
       mContext = c;
       this.eventName = eventName;
       this.eventNumber=eventNumber;
       this.eventPeriod=eventPeriod;
       this.id=id;
   }
	@Override
	public int getCount() {
	
		return eventName.size();
	}

	@Override
	public String[] getItem(int position) {
		String[] items={eventName.get(position),eventNumber.get(position),eventPeriod.get(position)};
		return items;
	}

	@Override
	public long getItemId(int position) {														
		
		return Integer.valueOf(id.get(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 View list;	
	          if (convertView == null) {
	        	  LayoutInflater inflater = (LayoutInflater) mContext
	        		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        	  list = new View(mContext);
	        	  list = inflater.inflate(R.layout.coverage_expandable_child_single, null);
	       
	          } else {
	        	  list = (View) convertView;
	          }
	          TextView textView2 = (TextView) list.findViewById(R.id.textView_coverageCategory);
	          textView2.setText(eventName.get(position));
	          
	          TextView textView3 = (TextView) list.findViewById(R.id.textView_coverageNumber);
	          textView3.setText(eventNumber.get(position));
	          
	          TextView textView4 = (TextView) list.findViewById(R.id.textView_coveragePeriod);
	          textView4.setText(eventPeriod.get(position));
	            Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
		          	      "fonts/Roboto-Thin.ttf");
		         //textView2.setTypeface(custom_font);
		         //textView3.setTypeface(custom_font);
	            if(isPositionChecked(position)==true){
	            	list.setBackgroundColor(color.BackgroundGrey);
	            }else if(clearSelection()){
	            	list.setBackgroundColor(color.White);
	            }

	      return list;
	    }
	public void setNewSelection(int position, boolean value) {
		mSelection.put(position, value);
        notifyDataSetChanged();
    }

    public boolean isPositionChecked(int position) {
        Boolean result = mSelection.get(position);
        return result == null ? false : result;
    }

    public Set<Integer> getCurrentCheckedPosition() {
        return mSelection.keySet();
    }

    public void removeSelection(int position) {
        mSelection.remove(position);
        notifyDataSetChanged();
    }

    public boolean clearSelection() {
        mSelection = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
        return true;
    }
		
	}


