package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FirstTrimesterCousellingNext extends Activity implements OnItemClickListener{

	private ListView listView_next;
	private Context mContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_first_trimester_next);
	    mContext=FirstTrimesterCousellingNext.this;
	    String[] items={"Excessive vomiting","Offensive/discolored discharge","Severe abdominal pain"
	    				,"Feotal movements","Epigastric pain","Bleeding","Oedema of feet, face, or ankles"
	    				,"BP ≥ 90mm Hg","Severe headache/blurred vision",
	    				"Difficulty breathing","Sings of shock"};
	    listView_next=(ListView) findViewById(R.id.listView_firstTrimesterNext);
	    listView_next.setOnItemClickListener(this);
	    SimpleAdapter adapter=new SimpleAdapter(mContext,items);
	    listView_next.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}
	
	class SimpleAdapter extends BaseAdapter{
		Context mContext;
		String[] items;
		
		SimpleAdapter(Context mContext,	String[] items){
			this.mContext=mContext;
			this.items=items;
		}
		@Override
		public int getCount() {
			
			return items.length;
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
			 View list = null;
	         if (convertView == null) {	 
	        	 LayoutInflater inflater = (LayoutInflater) mContext
	     		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	     	  list = new View(mContext);
	     	  list = inflater.inflate(R.layout.listview_text_single, null);
	       	
	         } else {
	       	  list = (View) convertView;  
	         }
	     
	         Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
	         	      "fonts/Roboto-Thin.ttf");
	       
	     	TextView items_text=(TextView) list.findViewById(R.id.textView_listViewText);
		 		items_text.setText(items[position]);
		 		items_text.setTextColor(Color.rgb(82,0,0));
		 		items_text.setTypeface(custom_font);
			return list;
		}
		
	}


}
