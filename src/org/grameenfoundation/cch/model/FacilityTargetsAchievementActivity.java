package org.grameenfoundation.cch.model;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.adapters.FacilityTargetAchievementAdapter;
import org.grameenfoundation.adapters.NumericalTargetAchievementsAdapter;
import org.grameenfoundation.calendar.CalendarEvents;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class FacilityTargetsAchievementActivity extends Activity {
	 private ExpandableListView expandableListview;
		private Context mContext;
		private CalendarEvents c;
		 private ArrayList<FacilityTargets> completedCoverageTargets;
		 private ArrayList<FacilityTargets> unCompletedCoverageTargets;
		private FacilityTargetAchievementAdapter adapter;
		private DbHelper db;
		private TextView textView_label;
		private TextView textView_number;
		//private View rootView;
		private int month;
		private int year;
		private String[] groupItems;
		private int number;
		private String month_text;
	/** Called when the activity is first created. */
		 public FacilityTargetsAchievementActivity(){

		 }
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);
		   setContentView(R.layout.activity_target_details_);
		   getActionBar().setTitle("Achievement Center");
		    getActionBar().setSubtitle("Achievement Details");
		    expandableListview = (ExpandableListView) findViewById(R.id.expandableListView1);
		    mContext= getApplicationContext();
		    c= new CalendarEvents(mContext);
		    db=new DbHelper(FacilityTargetsAchievementActivity.this);
		    new GetData().execute();
		    Bundle extras = getIntent().getExtras(); 
	        if (extras != null) {
	          month= extras.getInt("month");
	          year=extras.getInt("year");
	          month_text=extras.getString("month_text");
	        }
		    groupItems=new String[]{"Completed","In progress"};
		    textView_label=(TextView) findViewById(R.id.textView_label);
		    textView_label.setText("Facility Targets");
		    textView_number=(TextView) findViewById(R.id.textView_number);
		   
		    
		}
		private class GetData extends AsyncTask<Object, Void, Object> {
			 DbHelper db=new DbHelper(FacilityTargetsAchievementActivity.this);

		    @Override
		    protected Object doInBackground(Object... params) {
		          completedCoverageTargets=db.getListOfFacilityTargetsForAchievements(MobileLearning.CCH_TARGET_STATUS_UPDATED,month_text, year);
		          unCompletedCoverageTargets=db.getListOfFacilityTargetsForAchievements(MobileLearning.CCH_TARGET_STATUS_NEW,month_text, year);
		        textView_number.setText(" ("+String.valueOf(completedCoverageTargets.size()+unCompletedCoverageTargets.size())+" this month)");
		            return null;
		        
		    }

		    @Override
		    protected void onPostExecute(Object result) {
		        	 adapter=new FacilityTargetAchievementAdapter(FacilityTargetsAchievementActivity.this,groupItems,completedCoverageTargets ,
		        			 unCompletedCoverageTargets,expandableListview);
		 	    expandableListview.setAdapter(adapter);
		    }
		}
	}