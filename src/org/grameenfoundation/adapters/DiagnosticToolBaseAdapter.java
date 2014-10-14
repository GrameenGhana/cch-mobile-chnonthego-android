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

public class DiagnosticToolBaseAdapter extends BaseAdapter{
	Context mContext;
	String[] category;
	//String[] categoryDetails;
	private LayoutInflater inflater;
	private TextView category2;
	private TextView category_details_text;
	
		public DiagnosticToolBaseAdapter(Context mContext,String[] category){
		this.mContext=mContext;
		this.category=category;
		//this.categoryDetails=categoryDetails;
			
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
		View list=null;
		 if (convertView == null) {
			  LayoutInflater inflater = (LayoutInflater) mContext
      		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      	  list = new View(mContext);
      	  list = inflater.inflate(R.layout.diagnostic_listview_single, null);
     
        } else {
      	  list = (View) convertView;
        }
		 /*
		 category2=(TextView) list.findViewById(R.id.textView_diagnosticSingle2Category);
		 category2.setText(category[position]);
		  category_details_text=(TextView) list.findViewById(R.id.textView_diagnosticSingle2CategoryDetail);
		 category_details_text.setText(categoryDetails[position]);
		 */
		 TextView category_text=(TextView) list.findViewById(R.id.textView_diagnosticCategory);
		 category_text.setText(category[position]);
		 Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
         	      "fonts/Roboto-Thin.ttf");
		 //category_details_text.setTypeface(custom_font);
		// category_text.setTypeface(custom_font);
		return list;
	}

}
