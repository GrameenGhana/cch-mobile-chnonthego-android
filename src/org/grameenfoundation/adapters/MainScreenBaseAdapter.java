package org.grameenfoundation.adapters;


import org.digitalcampus.mobile.learningGF.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainScreenBaseAdapter extends BaseAdapter{
	 private Context mContext;
	 private final String[] category;
	    private final int[] Imageid;
	
	 public MainScreenBaseAdapter(Context c,String[] category,int[] Imageid ) {
        mContext = c;
        this.Imageid = Imageid;
        this.category = category;
    }
	@Override
	public int getCount() {
	
		return category.length;
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 View list;	     
	          if (convertView == null) {
	        	  LayoutInflater inflater = (LayoutInflater) mContext
	        		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        	  list = new View(mContext);
	        	  list = inflater.inflate(R.layout.main_screen_gridview_single, null);
	       
	          } else {
	        	  list = (View) convertView;
	          }
	          
	          TextView textView2 = (TextView) list.findViewById(R.id.textView_category);
	            ImageView imageView = (ImageView)list.findViewById(R.id.imageView_categoryIcon);
	            textView2.setText(category[position]);
	           
	            imageView.setImageResource(Imageid[position]);
	            imageView.setMaxHeight(250);
	            if(position==4){
	            	
	            }
	      return list;
	    }
		
	}


