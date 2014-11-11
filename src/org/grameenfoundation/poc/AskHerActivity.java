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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class AskHerActivity extends Activity {
	
	Context mContext;
	private ListView listView_askHer;
	private Button button_no;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_ask_her);
	    mContext=AskHerActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic");
	    listView_askHer=(ListView) findViewById(R.id.listView_askHer);
	    String[] items={"Excessive Vomiting","Offensive/discolored discharge",
	    				"Sever abdominal pain",
	    				"Epigastric Pain","Bleeding","Edema of the feet and hands, face, or ankles",
	    				"BP  ≥  90mm Hg","Sever headache/blurred vision",
	    				"Difficulty breathing",
	    				"Signs of shock"};
	    AskListAdapter adapter=new AskListAdapter(mContext,items);
	    listView_askHer.setAdapter(adapter);
	    listView_askHer.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Excessive Vomiting");
					startActivity(intent);
					break;
				case 1	:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Offensive/discolored vaginal discharge");
					startActivity(intent);
					break;
				case 2:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Sever abdominal pain");
					startActivity(intent);
					break;
				case 3:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Epigastric Pain");
					startActivity(intent);
					break;
				case 4:
					intent=new Intent(mContext,AskBleedingActivity.class);
					startActivity(intent);
					break;
				case 5:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Edema of the feet, face or ankles");
					startActivity(intent);
					break;
				case 6:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "BP  ≥  90mm Hg");
					startActivity(intent);
					break;
				case 7:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Severe headache/blurred vision");
					startActivity(intent);
					break;
				case 8:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Difficulty Breathing");
					startActivity(intent);
					break;
				case 9:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Shock");
					startActivity(intent);
					break;
				}
				
			}
	    	
	    });
	    button_no=(Button) findViewById(R.id.button_no);
	    button_no.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, AskMalariaFeverActivity.class);
				startActivity(intent);
			}
	    	
	    });
	}

	
	class AskListAdapter extends BaseAdapter{
		Context mContext;
		String[] listItems;
		 public LayoutInflater minflater;
		
		public AskListAdapter(Context mContext,String[] listItems){
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
