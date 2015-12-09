package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.cch.popupquestions.POCDynamicActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class AcuteEmergenciesActivity extends BaseActivity implements OnClickListener, OnItemClickListener{

	private ListView listView_acuteEmergencies;
	private Context mContext;
	private Button button_acuteEmergenciesNo;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private JSONObject json;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_acuteemergencies);
	    start_time=System.currentTimeMillis();
	    mContext=AcuteEmergenciesActivity.this;
	    dbh=new DbHelper(mContext);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic: Acute Emergencies");
	    json=new JSONObject();
	    try {
			json.put("page", "ANC Diagnostic Acute Emergencies");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    listView_acuteEmergencies=(ListView) findViewById(R.id.listView_acuteEmergencies);
	    String[] emergencies={"Difficulty breathing","Edema(feet and hands, face or ankles)","Heavy Bleeding","Shock"};
	    ListAdapter adapter=new ListAdapter(mContext, emergencies);
	    listView_acuteEmergencies.setAdapter(adapter);
	    listView_acuteEmergencies.setOnItemClickListener(this);
	    button_acuteEmergenciesNo=(Button) findViewById(R.id.button_acuteEmergenciesNo);
	    button_acuteEmergenciesNo.setOnClickListener(this);
	    
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		String extra_info;
		switch(position){
		case 0:
		extra_info="Difficulty breathing";
		intent=new Intent(mContext, POCDynamicActivity.class);
		intent.putExtra("take_action", extra_info);
		intent.putExtra("start_time", System.currentTimeMillis());
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		break;
		case 1:
		extra_info="Edema";
		intent=new Intent(mContext, TakeActionActivity.class);
		intent.putExtra("take_action", extra_info);
		intent.putExtra("start_time", System.currentTimeMillis());
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			break;
			
		case 2:
		intent=new Intent(mContext, AskBleedingActivity.class);
		intent.putExtra("start_time", System.currentTimeMillis());
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			break;
		case 3:
			extra_info="Shock";
			intent=new Intent(mContext, TakeActionActivity.class);
			intent.putExtra("take_action", extra_info);
			intent.putExtra("start_time", System.currentTimeMillis());
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				break;	
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		int id = v.getId();
		if (id == R.id.button_acuteEmergenciesNo) {
			intent=new Intent(mContext,ANCRecordsAndHistoryActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		}
		
	}
	class ListAdapter extends BaseAdapter{
		Context mContext;
		String[] items;
		 public LayoutInflater minflater;
		public ListAdapter(Context mContext,String[] items){
			this.mContext=mContext;
			this.items=items;
			 minflater = LayoutInflater.from(mContext);
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
	    System.out.println(json.toString());
		//dbh.insertCCHLog("Point of Care", "ANC Diagnostic Acute Emergencies", start_time.toString(), end_time.toString());
	    dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
		
	}
	

}
