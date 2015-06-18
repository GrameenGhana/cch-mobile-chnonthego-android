package org.grameenfoundation.cch.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.adapters.UpdateTargetsAdapter;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.poc.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;

public class LearningTargetUpdateActivity extends BaseActivity {

	Context mContext;
	private DbHelper db;
	private ExpandableListView listView_learning;
	private UpdateTargetsAdapter learning_adapter;
	private TextView textView_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_targets);
	    mContext=LearningTargetUpdateActivity.this;
	    db=new DbHelper(mContext);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Update Learning Targets");
	    listView_learning=(ExpandableListView) findViewById(R.id.expandableListView1);
	    textView_name=(TextView) findViewById(R.id.textView_name);
	    textView_name.setText("Learning Targets");
	    new GetData().execute();
	    listView_learning.setOnChildClickListener(new OnChildClickListener() {
			
			private String[] selected_items;
			private long selected_id;
			
			

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				selected_items=learning_adapter.getChild(groupPosition, childPosition);
				selected_id=Long.parseLong(selected_items[7]);
				String learning_target_category=selected_items[10];
				String learning_target_detail=selected_items[11];
				String coverage_number=selected_items[1];
				String coverage_period=selected_items[2];
				String due_date=selected_items[3];
				String start_date=selected_items[5];
				String status=selected_items[6];
				String last_updated=selected_items[8];
				long old_id=Long.parseLong(selected_items[9]);
				//String achieved=selected_items[4];
				ArrayList<String> number_achieved_list=db.getNumberAchieved(selected_id, coverage_period,MobileLearning.CCH_TARGET_STATUS_NEW);
				Intent intent=new Intent(mContext,UpdateActivity.class);
				intent.putExtra("id",selected_id);
				intent.putExtra("old_id",old_id);
				intent.putExtra("learning_topic", learning_target_category);
				intent.putExtra("learning_section", learning_target_detail);
				intent.putExtra("type", "learning");
				intent.putExtra("number",coverage_number);
				intent.putExtra("period", coverage_period);
				intent.putExtra("due_date", due_date);
				intent.putExtra("start_date", start_date);
				intent.putExtra("number_achieved", number_achieved_list.get(0));
				intent.putExtra("status", status);
				intent.putExtra("last_updated", last_updated);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					return true;
			}
	    
		});
	}

	private class GetData extends AsyncTask<Object, Void, Object> {
		 DbHelper db=new DbHelper(mContext);
		private ArrayList<EventTargets> DailyLearningTargets;
		private ArrayList<EventTargets> WeeklyLearningTargets;
		private ArrayList<EventTargets> MonthlyLearningTargets;
		private ArrayList<EventTargets> QuarterlyLearningTargets;
		private ArrayList<EventTargets> MidyearLearningTargets;
		private ArrayList<EventTargets> AnnualLearningTargets;
		private int event_number1;
		private int event_number2;
		private int event_number3;
		private int event_number4;
		private int event_number5;
		private int event_number6;
		private String[] groupItems;
	
	

	    @Override
	    protected Object doInBackground(Object... params) {
 		      
	    	DailyLearningTargets=db.getListOfDailyTargetsToUpdate(MobileLearning.CCH_TARGET_TYPE_LEARNING);
	    	WeeklyLearningTargets=db.getListOfWeeklyTargetsToUpdate(MobileLearning.CCH_TARGET_TYPE_LEARNING);
	    	MonthlyLearningTargets=db.getListOfMonthlyTargetsToUpdate(MobileLearning.CCH_TARGET_TYPE_LEARNING);
	    	QuarterlyLearningTargets=db.getListOfQuarterlylyTargetsToUpdate(MobileLearning.CCH_TARGET_TYPE_LEARNING);
	    	MidyearLearningTargets=db.getListOfMidYearTargetsToUpdate(MobileLearning.CCH_TARGET_TYPE_LEARNING);
	    	AnnualLearningTargets=db.getListOfAnnualTargetsToUpdate(MobileLearning.CCH_TARGET_TYPE_LEARNING);
				return null;
	    }
	    @Override
	    protected void onPostExecute(Object result) {
	    	 event_number1=(int)DailyLearningTargets.size();
			  event_number2=(int)WeeklyLearningTargets.size();
			  event_number3=(int)MonthlyLearningTargets.size();
			  event_number4=(int)QuarterlyLearningTargets.size();
			  event_number5=(int)MidyearLearningTargets.size();
			  event_number6=(int)AnnualLearningTargets.size();
			  groupItems=new String[]{"To update daily ("+String.valueOf(event_number1)+")",
						"To update weekly ("+String.valueOf(event_number2)+")",
						"To update monthly ("+String.valueOf(event_number3)+")",
						"To update quarterly ("+String.valueOf(event_number4)+")",
						"To update mid-yearly ("+String.valueOf(event_number5)+")",
						"To update annually ("+String.valueOf(event_number6)+")"};
			   
			  learning_adapter=new UpdateTargetsAdapter(mContext,DailyLearningTargets,
					  WeeklyLearningTargets,
					  MonthlyLearningTargets,
					  QuarterlyLearningTargets,
					  MidyearLearningTargets,
					  AnnualLearningTargets,
					  groupItems,
					  listView_learning);
			  listView_learning.setAdapter(learning_adapter);
	    }
	}
	
	
	/*
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		 data=new JSONObject();
		    try {
		    	data.put("page", "Target Updates");
		    	data.put("ver", db.getVersionNumber(mContext));
		    	data.put("battery", db.getBatteryStatus(mContext));
		    	data.put("device", db.getDeviceName());
				data.put("imei", db.getDeviceImei(mContext));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		 db.insertCCHLog("Achievement Center",data.toString(), start_time.toString(), end_time.toString());
		finish();
	}*/
}