package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

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

public class ComplicationReadinessMenuActivity extends Activity {

	private ListView listView_complication;
	Context mContext;
	private Button button_next;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_complication_readiness_counselling);
	    mContext=ComplicationReadinessMenuActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Complication Readiness");
	    listView_complication=(ListView) findViewById(R.id.listView_complicationReadinessMenu);
	    String[] items={"Danger Signs in the Mother ","Newborn danger signs","Other maternal danger signs","Other newborn danger signs"};
	    ListAdapter adapter=new ListAdapter(mContext,items);
	    listView_complication.setAdapter(adapter);
	    listView_complication.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,ComplicationReadinessActionActivity.class);
					intent.putExtra("value", "danger_signs_mother");
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(mContext,ComplicationReadinessActionActivity.class);
					intent.putExtra("value", "danger_signs_newborn");
					startActivity(intent);
					break;
				case 2:
					intent=new Intent(mContext,ComplicationReadinessActionActivity.class);
					intent.putExtra("value", "other_maternal");
					startActivity(intent);
					break;
				case 3:
					intent=new Intent(mContext,ComplicationReadinessActionActivity.class);
					intent.putExtra("value", "other_newborn");
					startActivity(intent);
					break;
				}
				
			}
	    	
	    });
	    button_next=(Button) findViewById(R.id.button_next);
	    button_next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,ComplicationReadinessActionActivity.class);
				intent.putExtra("value", "readiness_plan");
				startActivity(intent);
			}
	    	
	    });
	}
	class ListAdapter extends BaseAdapter{
		Context mContext;
		String[] listItems;
		 public LayoutInflater minflater;
		
		public ListAdapter(Context mContext,String[] listItems){
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
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Counselling Complication Readiness", start_time.toString(), end_time.toString());
		finish();
	}
}
