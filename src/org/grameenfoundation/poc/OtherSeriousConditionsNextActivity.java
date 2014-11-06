package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class OtherSeriousConditionsNextActivity extends Activity {

	private ListView listView_otherCondition;
	private Context mContext;
	private Button button_no;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_other_serious_condition_next);
	    mContext=OtherSeriousConditionsNextActivity.this;
	    listView_otherCondition=(ListView) findViewById(R.id.listView_otherConditions);
	    String[] items={"Bleeding from Umbilical Cord or Elsewhere from the Body",
	    				"Soft swelling Covering Whole Scalp",
	    				"Open tissue on head, abdomen or back",
	    				"No urine or meconium since birth & baby > 24 hours old ",
	    				"Vomiting after every feed; Vomit green, bloody",
	    				"Blood in stool"};
	    OtherConditionsListAdapter adapter=new OtherConditionsListAdapter(mContext,items);
	    listView_otherCondition.setAdapter(adapter);
	    button_no=(Button) findViewById(R.id.button_no);
	    button_no.setOnClickListener(new OnClickListener(){

			private Intent intent;

			@Override
			public void onClick(View v) {
				intent=new Intent(mContext,NoSeriousConditionsActivity.class);
				startActivity(intent);
				
			}
	    	
	    });
	    listView_otherCondition.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,TakeActionOtherSeriousConditionActivity.class);
					intent.putExtra("category", "bleeding");
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(mContext,TakeActionOtherSeriousConditionActivity.class);
					intent.putExtra("category", "soft swelling");
					startActivity(intent);
					break;
				case 2:
					intent=new Intent(mContext,TakeActionOtherSeriousConditionActivity.class);
					intent.putExtra("category", "open tissue");
					startActivity(intent);
					break;
				case 3:
					intent=new Intent(mContext,TakeActionOtherSeriousConditionActivity.class);
					intent.putExtra("category", "no urine");
					startActivity(intent);
					break;
				case 4:
					intent=new Intent(mContext,TakeActionOtherSeriousConditionActivity.class);
					intent.putExtra("category", "no urine");
					startActivity(intent);
					break;
				case 5:
					intent=new Intent(mContext,TakeActionOtherSeriousConditionActivity.class);
					intent.putExtra("category", "no urine");
					startActivity(intent);
					break;
			
				}
			}
	    	
	    });
	}
	class OtherConditionsListAdapter extends BaseAdapter{
		Context mContext;
		String[] listItems;
		 public LayoutInflater minflater;
		
		public OtherConditionsListAdapter(Context mContext,String[] listItems){
		this.mContext=mContext;
		this.listItems=listItems;
		 minflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			return listItems.length;
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
			if( convertView == null ){
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			 text.setText(listItems[position]);
			    return convertView;
		}
		
	}
}
