package org.grameenfoundation.poc;


import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CousellingPerTrimesterActivity extends Activity implements OnItemClickListener{

	private Context mContext;
	private ListView listView_cousellingMenu;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_couselling);
	    mContext=CousellingPerTrimesterActivity.this;
	    listView_cousellingMenu=(ListView) findViewById(R.id.listView_cousellingMenu);
	    listView_cousellingMenu.setOnItemClickListener(this);
	    String[] category={"First Trimester Couselling","Second Trimester Couselling","Third Trimester Couselling"};
	    CounsellingBaseAdapter adapter=new CounsellingBaseAdapter(mContext,category);
	    listView_cousellingMenu.setAdapter(adapter);
	}
	
	class CounsellingBaseAdapter extends BaseAdapter{
		Context mContext;
		String[] category;
		
		CounsellingBaseAdapter(Context mContext, String[] category){
		this.mContext=mContext;
		this.category=category;
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
	        	  list = inflater.inflate(R.layout.diagnostic_listview_single, null);
	       
	          } else {
	        	  list = (View) convertView;
	          }
			  TextView category_text=(TextView) list.findViewById(R.id.textView_diagnosticCategory);
			  category_text.setText(category[position]);
			  
			  Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
	          	      "fonts/Roboto-Thin.ttf");
			  category_text.setTypeface(custom_font);
		return list;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		switch(position){
		case 0:
			intent=new Intent(mContext, FirstTrimesterCounsellingActivity.class);
			startActivity(intent);
			break;
		
		}
		
	}

}
