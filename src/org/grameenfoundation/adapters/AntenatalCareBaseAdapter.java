package org.grameenfoundation.adapters;


import org.digitalcampus.mobile.learningGF.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AntenatalCareBaseAdapter extends BaseAdapter{
	Context mContext;
	int[] imageIds;
	String[] category;
	String[] categoryDetail;
	
	public AntenatalCareBaseAdapter(Context mContext,int[] imageIds,String[] category){
		
		this.mContext=mContext;
		this.imageIds=imageIds;
		this.category=category;
		this.categoryDetail=categoryDetail;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 	category.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View list=null;
		 if (convertView == null) {
       	  LayoutInflater inflater = (LayoutInflater) mContext
       		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       	  list = new View(mContext);
       	  list = inflater.inflate(R.layout.antenatal_care_listview_single, null);
      
         } else {
       	  list = (View) convertView;
         }
		 TextView category_text= (TextView) list.findViewById(R.id.textView_ancCategory);
		 category_text.setText(category[position]);
		 
		 //TextView category_detail_text= (TextView) list.findViewById(R.id.textView_ancDescription);
		 //category_detail_text.setText(categoryDetail[position]);
		 
		 ImageView icon=(ImageView) list.findViewById(R.id.imageView_ancIcon);
		 icon.setImageResource(imageIds[position]);
		return list;
	}

}
