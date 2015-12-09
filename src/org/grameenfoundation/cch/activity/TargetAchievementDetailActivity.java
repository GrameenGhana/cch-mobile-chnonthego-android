package org.grameenfoundation.cch.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.model.FacilityTargets;
import org.grameenfoundation.cch.model.FacilityTargetsAchievementActivity;
import org.grameenfoundation.cch.model.OtherTargetAchievementActivity;
import org.grameenfoundation.poc.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

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

public class TargetAchievementDetailActivity extends BaseActivity {

	private ListView expandableListview;
	private Context mContext;
	private CalendarEvents c;
	 private int otherTargets;
	private ListAdapter adapter;
	private DbHelper db;
	private TextView textView_label;
	private TextView textView_number;
	private int month;
	private int year;
	private Long start_time;
	private Long end_time;
	private JSONObject data;
	private String month_text;
	private ArrayList<FacilityTargets> completedCoverageTargets;
	private ArrayList<FacilityTargets> unCompletedCoverageTargets;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_achievements_details);
	    expandableListview = (ListView) findViewById(R.id.expandableListView1);
	    		Bundle extras =getIntent().getExtras(); 
	            if (extras != null) {
	              month= extras.getInt("month");
	              year=extras.getInt("year");
	              month_text=extras.getString("month_text");
	            }
	    mContext=TargetAchievementDetailActivity.this;
	    c= new CalendarEvents(mContext);
	    db=new DbHelper(TargetAchievementDetailActivity.this);
	    start_time=System.currentTimeMillis();
	   
	    getActionBar().setTitle("Achievement Center");
	    getActionBar().setSubtitle("Achievement Details");
	    textView_label=(TextView) findViewById(R.id.textView_label);
	    textView_label.setText("Targets");
	    textView_number=(TextView) findViewById(R.id.textView_number);
	    otherTargets=db.getTargetsForAchievements(MobileLearning.CCH_TARGET_TYPE_OTHER,month+1, year);
	    completedCoverageTargets=db.getListOfFacilityTargetsForAchievements(MobileLearning.CCH_TARGET_STATUS_UPDATED,month_text, year);
        unCompletedCoverageTargets=db.getListOfFacilityTargetsForAchievements(MobileLearning.CCH_TARGET_STATUS_NEW,month_text, year);
        int all=otherTargets+completedCoverageTargets.size()+unCompletedCoverageTargets.size();
	    textView_number.setText(" ("+String.valueOf(all)+" this month)");
	    String[] groupItems={"Other ("+String.valueOf(otherTargets)+")"
	    					,"Facility Targets ("+String.valueOf(completedCoverageTargets.size()+
	    							unCompletedCoverageTargets.size())+")"};
	    adapter=new ListAdapter(mContext,groupItems);
	    expandableListview.setAdapter(adapter);
	    expandableListview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,OtherTargetAchievementActivity.class);
					intent.putExtra("month", month);
					intent.putExtra("year", year);
					intent.putExtra("number", otherTargets);
					startActivity(intent);
					
					break;
				case 1:
					intent=new Intent(mContext,FacilityTargetsAchievementActivity.class);
					intent.putExtra("month", month);
					intent.putExtra("month_text", month_text);
					intent.putExtra("year", year);
					//intent.putExtra("number", otherTargets);
					startActivity(intent);
					break;
					
			
				}
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
				  convertView = minflater.inflate(R.layout.other_listview_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_otherCategory);
			 text.setText(listItems[position]);
			    return convertView;
		}
		
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		 data=new JSONObject();
		    try {
		    	data.put("page", "Target Achievements");
		    	data.put("ver", db.getVersionNumber(mContext));
		    	data.put("battery", db.getBatteryStatus(mContext));
		    	data.put("device", db.getDeviceName());
				data.put("imei", db.getDeviceImei(mContext));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		 db.insertCCHLog("Achievement Center",data.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
