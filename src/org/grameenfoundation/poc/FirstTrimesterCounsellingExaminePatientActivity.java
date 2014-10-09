package org.grameenfoundation.poc;


import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FirstTrimesterCounsellingExaminePatientActivity extends Activity implements OnClickListener {

	Context mContext;
	private ListView listView_ask;
	private ListView listView_look;
	private ListView listView_check;
	private Button button_next;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.first_trimester_counselling_answer_no);
	    mContext=FirstTrimesterCounsellingExaminePatientActivity.this;
	    
	    listView_ask=(ListView) findViewById(R.id.listView_ask);
	    String[] items={"Sever headache","Severe abdominal pains",
	    				"Excessive vomitting","Blurred vision",
	    				"Offensive/discoloured vaginal discharge",
	    				"Fever","Difficulty breathing","Epigastric pain",
	    				"Foetal movements"};
	    SimpleAdapter adapter1=new SimpleAdapter(mContext,items);
	    listView_ask.setAdapter(adapter1);
	    
	    listView_look=(ListView) findViewById(R.id.listView_look);
	    String[] items2={"Examine conjunctiva, tongue, palms, and nail beds for palor",
	    				"Odaema of the feet, hands face, ankles","Bleeding",
	    				"Jaundice",
	    				"Signs of shock","Offensive vaginal discharge"};
	    SimpleAdapter adapter2=new SimpleAdapter(mContext,items2);
	    listView_look.setAdapter(adapter2);
	    
	    listView_check=(ListView) findViewById(R.id.listView_check);
	    String[] items3={"Blood pressure, if possible",
	    				"Temperature","Pulse"};
	    SimpleAdapter adapter3=new SimpleAdapter(mContext,items3);
	    listView_check.setAdapter(adapter3);
	    
	    button_next=(Button) findViewById(R.id.button_next);
	    button_next.setOnClickListener(this);
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
     	  list = inflater.inflate(R.layout.examine_patient_listview_single, null);
       	
         } else {
       	  list = (View) convertView;  
         }
         if (position % 2 == 1) {
 			 list.setBackgroundColor(Color.rgb(247,247,244));  
 		
 			} else {
 				list.setBackgroundColor(Color.WHITE);  
 			
 			}
         Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
         	      "fonts/Roboto-Thin.ttf");
       
     	TextView items_text=(TextView) list.findViewById(R.id.textView_examinePatientItem);
	 		items_text.setText(items[position]);
	 		items_text.setTextColor(Color.rgb(82,0,0));
	 		items_text.setTypeface(custom_font);
		return list;
	}
	
}

@Override
public void onClick(View v) {
	Intent intent;
	switch(v.getId()){
	case R.id.button_next:
		intent=new Intent(mContext, FirstTrimesterCousellingNext.class);
		startActivity(intent);
		break;
	}
}


}
