package org.grameenfoundation.cch.model;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.NumericalTargetAchievementsAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.poc.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class CoverageTargetAchievementActivity extends BaseActivity {
	private ExpandableListView expandableListview;
	private Context mContext;
	private CalendarEvents c;
	 private ArrayList<EventTargets> completedCoverageTargets;
	 private ArrayList<EventTargets> unCompletedCoverageTargets;
	private NumericalTargetAchievementsAdapter adapter;
	private DbHelper db;
	private TextView textView_label;
	private TextView textView_number;
	//private View rootView;
	private int month;
	private int year;
	private String[] groupItems;
	private int number;
	 public CoverageTargetAchievementActivity(){

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
	    db=new DbHelper(mContext);
	    new GetData().execute();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          month= extras.getInt("month");
          year=extras.getInt("year");
          number=extras.getInt("number");
        }
	    groupItems=new String[]{"Completed","Upcoming"};
	    textView_label=(TextView) findViewById(R.id.textView_label);
	    textView_label.setText("Coverage Targets");
	    textView_number=(TextView) findViewById(R.id.textView_number);
	   
	    textView_number.setText(" ("+String.valueOf(number)+" this month)");
	}
	private class GetData extends AsyncTask<Object, Void, Object> {
		 DbHelper db=new DbHelper(mContext);

	    @Override
	    protected Object doInBackground(Object... params) {
	          completedCoverageTargets=db.getAllCoverageTargetsCompletedForAchievements("updated",month+1, year);
	          unCompletedCoverageTargets=db.getAllCoverageTargetsCompletedForAchievements("new_record",month+1, year);
	            return null;
	        
	    }

	    @Override
	    protected void onPostExecute(Object result) {
	        	 adapter=new NumericalTargetAchievementsAdapter(mContext,groupItems,completedCoverageTargets ,
	        			 unCompletedCoverageTargets,expandableListview);
	 	    expandableListview.setAdapter(adapter);
	    }
	}
}