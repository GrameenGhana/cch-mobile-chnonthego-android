package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

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

public class AskHerActivity extends BaseActivity {
	
//	Context mContext;
	private ListView listView_askHer;
	private Button button_no;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private JSONObject json;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_ask_her);
	    mContext=AskHerActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic: Managing Danger Signs");
	    json=new JSONObject();
	    try {
			json.put("page", "ANC Diagnostic: Managing Danger Signs");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    listView_askHer=(ListView) findViewById(R.id.listView_askHer);
	    String[] items={"Excessive Vomiting",
	    				"Offensive/discolored discharge",
	    				"Sever abdominal pain",
	    				"Epigastric Pain",
	    				"Bleeding",
	    				"Edema of the feet and hands, face, or ankles",
	    				"BP  ≥  90mm Hg",
	    				"Sever headache/blurred vision",
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
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 1	:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Offensive/discolored vaginal discharge");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 2:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Sever abdominal pain");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 3:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Epigastric Pain");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 4:
					intent=new Intent(mContext,AskBleedingActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 5:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Edema of the feet, face or ankles");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 6:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "BP  ≥  90mm Hg");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 7:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Severe headache/blurred vision");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 8:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Difficulty Breathing");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 9:
					intent=new Intent(mContext,TakeActionAskHerActivity.class);
					intent.putExtra("take_action", "Shock");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
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
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
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
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		//dbh.insertCCHLog("Point of Care", "ANC Diagnostic Managing Danger Signs", start_time.toString(), end_time.toString());
	    dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
