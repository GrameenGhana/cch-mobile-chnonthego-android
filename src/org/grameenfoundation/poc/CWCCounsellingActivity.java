package org.grameenfoundation.poc;

import java.io.File;
import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.cch.model.POCSections;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CWCCounsellingActivity extends BaseActivity implements OnItemClickListener{

//	private Context mContext;
	private ListView listView_encounter;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private JSONObject json;
	private ArrayList<POCSections> list;
	private String first_file;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_encounter);
	    mContext=CWCCounsellingActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic");
	    json=new JSONObject();
	    try {
			json.put("page", "ANC Diagnostic");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    listView_encounter=(ListView) findViewById(R.id.listView_encounter);
	    listView_encounter.setOnItemClickListener(this);
	    //String[] conditions={"Emergencies","Records and History","Management of Danger Signs","Malaria","Anaemia"};
	    list=new ArrayList<POCSections>();
	    list=dbh.getPocSections("CWC Counselling");
	    ListAdapter adapter=new ListAdapter(mContext, list);
	    listView_encounter.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		File folder = new File(list.get(position).getSectionUrl());
		File[] listOfFiles = folder.listFiles();
		for(int i=0;i<listOfFiles.length;i++){
			String filename=listOfFiles[i].getName();
			int pos=filename.lastIndexOf(".");
			if(pos>0){
				filename=filename.substring(0,pos);
			}
			if(filename.endsWith("1")){
				first_file=filename;
			}
		}
		Intent intent;
			intent=new Intent(mContext, POCDynamicActivity.class);
			intent.putExtra("shortname", list.get(position).getSectionShortname());
			intent.putExtra("link", list.get(position).getSectionShortname()+File.separator+first_file);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		}
		
	
	
	class ListAdapter extends BaseAdapter{
		Context mContext;
		ArrayList<POCSections> items;
		 public LayoutInflater minflater;
		public ListAdapter(Context mContext,ArrayList<POCSections>items){
			this.mContext=mContext;
			this.items=items;
			 minflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			return items.size();
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
			      
				  convertView = minflater.inflate(R.layout.other_listview_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_otherCategory);
			 text.setText(items.get(position).getSectionName());
			 
			    return convertView;
		}
		
	}
	public void onBackPressed()
	{
		end_time=System.currentTimeMillis();
		//dbh.insertCCHLog("Point of Care", "ANC Diagnostic Tool", start_time.toString(), end_time.toString());
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}

}
