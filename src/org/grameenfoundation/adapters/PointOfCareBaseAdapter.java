package org.grameenfoundation.adapters;

import org.grameenfoundation.chnonthego.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PointOfCareBaseAdapter extends BaseAdapter{
	Context mContext;
	int[] imageIds;
	String[] category;
	public PointOfCareBaseAdapter(Context mContext, int[] imageIds, String[] category){
		this.mContext=mContext;
		this.imageIds=imageIds;
		this.category=category;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return category.length;
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
		View list = null;
		if(convertView==null){
			  LayoutInflater inflater = (LayoutInflater) mContext
      		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      	  list = new View(mContext);
      	  list = inflater.inflate(R.layout.point_of_care_listview_single, null);
     
        } else {
      	  list = (View) convertView;
        }
		 Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
         	      "fonts/Roboto-Thin.ttf");
		TextView category_text=(TextView) list.findViewById(R.id.textView_pocCategory);
		category_text.setText(category[position]);
		category_text.setTypeface(custom_font);
		ImageView icon=(ImageView) list.findViewById(R.id.imageView_pocIcon);
		icon.setImageResource(imageIds[position]);
		
 		return list;
	}

}
