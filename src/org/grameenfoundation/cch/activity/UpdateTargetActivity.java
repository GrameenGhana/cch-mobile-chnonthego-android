package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Calendar;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.adapters.UpdateTargetsAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.activity.TargetAchievementDetailActivity.ListAdapter;
import org.grameenfoundation.cch.model.CoverageTargetAchievementActivity;
import org.grameenfoundation.cch.model.EventTargetAchievementActivity;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.LearningTargetAchievementActivity;
import org.grameenfoundation.cch.model.LearningTargets;
import org.grameenfoundation.cch.model.OtherTargetAchievementActivity;
import org.grameenfoundation.poc.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;

public class UpdateTargetActivity extends BaseActivity{

	private DbHelper db;
	private Context mContext;
	 public ArrayList<EventTargets> DailyTargets;
	 public ArrayList<EventTargets> WeeklyTargets;
	 public ArrayList<EventTargets> MonthlyTargets;
	 public ArrayList<EventTargets> QuarterlyTargets;
	 public ArrayList<EventTargets> MidyearTargets;
	 public ArrayList<EventTargets> AnnualTargets;
	String due_date;
	private ExpandableListView expandableListView_updates;
	private UpdateTargetsAdapter updates_adapter;
	private String[] groupItems;
	private long selected_id;
	private ListView expandableListview;
	private TextView textView_label;
	private TextView textView_number;
	private ListAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_achievements_details);
	    expandableListview = (ListView) findViewById(R.id.expandableListView1);
	    mContext=UpdateTargetActivity.this;
	    db=new DbHelper(UpdateTargetActivity.this);
	    String[] groupItems={"Events","Coverage","Learning","Other"};
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Update Targets");
	    textView_label=(TextView) findViewById(R.id.textView_label);
	    textView_label.setVisibility(View.GONE);
	    textView_number=(TextView) findViewById(R.id.textView_number);
	    textView_number.setVisibility(View.GONE);
	    adapter=new ListAdapter(mContext,groupItems);
	    expandableListview.setAdapter(adapter);
	    expandableListview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,EventTargetUpdateActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(mContext,CoverageTargetsUpdateActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent=new Intent(mContext,LearningTargetUpdateActivity.class);
					startActivity(intent);
					break;
					
				case 3:
					intent=new Intent(mContext,OtherTargetsUpdateActivity.class);
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
	
}
