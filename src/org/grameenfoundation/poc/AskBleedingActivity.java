package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.AboutActivity;
import org.digitalcampus.oppia.activity.HelpActivity;
import org.digitalcampus.oppia.activity.MainScreenActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

public class AskBleedingActivity extends BaseActivity {

	private ListView listView_bleeding;
//	private Context mContext;
	String[] groupItems;
	String[] ChildItemsOne;
	String[] ChildItemsTwo;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private JSONObject json;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_acuteemergencies_bleeding);
	    mContext=AskBleedingActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic: Acute Emergencies > Excessive Bleeding");
	    json=new JSONObject();
	    try {
			json.put("page", "ANC Diagnostic: Acute Emergencies > Excessive Bleeding");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    listView_bleeding=(ListView) findViewById(R.id.listView_excessiveBleeding);
	    String[] items={"Heavy(more than 2 pad changes in 24hours)","Light(fewer than 2 pad changes in 24hours)"};
	    ListAdapter adapter=new ListAdapter(mContext,items);
	    listView_bleeding.setAdapter(adapter);
	    listView_bleeding.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,TakeActionBleedingActivity.class);
					intent.putExtra("category", "heavy");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 1:
					intent=new Intent(mContext,TakeActionBleedingActivity.class);
					intent.putExtra("category", "light");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				}
				
			}
	    	
	    });

	    
	}
	class ListAdapter extends BaseAdapter{
		Context mContext;
		String[] items;
		 public LayoutInflater minflater;
		
		public ListAdapter(Context c, String[] items){
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
			 text.setPadding(10, 0, 0, 0);
			 text.setText(items[position]);
			    return convertView;
		}
	}

	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		//dbh.insertCCHLog("Point of Care", "ANC Diagnostic Excessive Bleeding", start_time.toString(), end_time.toString());
	    dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
