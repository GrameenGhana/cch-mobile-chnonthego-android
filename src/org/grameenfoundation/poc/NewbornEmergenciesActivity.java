package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NewbornEmergenciesActivity extends Activity {

	private ListView listView_newbornEmergency;
	Context mContext;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_newborn_emergency);
	    dbh=new DbHelper(NewbornEmergenciesActivity.this);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Newborn Emergencies");
	    mContext=NewbornEmergenciesActivity.this;
	    listView_newbornEmergency=(ListView) findViewById(R.id.listView_newbornEmergency);
	    String[] items={"Not breathing or gasping Difficulty breathing: chest in-drawing, grunting",
	    				"Cyanosis (blue skin) or pallor","Convulsion or abnormal movements"};
	    NewbornEmergencyListAdapter adapter=new NewbornEmergencyListAdapter(mContext,items);
	    listView_newbornEmergency.setAdapter(adapter);
	    listView_newbornEmergency.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,TakeActionNewbornEmergency.class);
					intent.putExtra("take_action", "difficulty");
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(mContext,TakeActionNewbornEmergency.class);
					intent.putExtra("take_action", "cyanosis");
					startActivity(intent);
					break;
				case 2:
					intent=new Intent(mContext,TakeActionNewbornEmergency.class);
					intent.putExtra("take_action", "convulsion");
					startActivity(intent);
					break;
				}
			}
	    	
	    });
	}

	class NewbornEmergencyListAdapter extends BaseAdapter{
		Context mContext;
		String[] items;
		 public LayoutInflater minflater;
		public NewbornEmergencyListAdapter(Context c,String[] items){
			this.mContext=c;
			this.items=items;
			minflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			return items.length;
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
			if( convertView == null ){
			      
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			 text.setText(items[position]);
			    return convertView;
		}
		
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic Newborn Emergencies", start_time.toString(), end_time.toString());
		finish();
	}
}
