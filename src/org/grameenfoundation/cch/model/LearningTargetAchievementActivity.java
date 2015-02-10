package org.grameenfoundation.cch.model;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.LearningTargetAchievementsAdapter;
import org.grameenfoundation.adapters.NumericalTargetAchievementsAdapter;
import org.grameenfoundation.calendar.CalendarEvents;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class LearningTargetAchievementActivity extends Fragment{
	private ExpandableListView expandableListview;
	private Context mContext;
	private CalendarEvents c;
	 private int learningTargets;
	 private ArrayList<LearningTargets> completedLearningTargets;
	 private ArrayList<LearningTargets> unCompletedLearningTargets;
	private LearningTargetAchievementsAdapter adapter;
	private DbHelper db;
	private TextView textView_label;
	private TextView textView_number;
	private View rootView;
	private int month;
	private int year;
	private String[] groupItems;
	 public LearningTargetAchievementActivity(){

	 }
	@Override
	public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    rootView=inflater.inflate(R.layout.activity_achievements_details,null,false);
	    expandableListview = (ExpandableListView) rootView.findViewById(R.id.expandableListView1);
	    mContext=getActivity().getApplicationContext();
	    c= new CalendarEvents(mContext);
	    db=new DbHelper(mContext);
	    Bundle extras = getActivity().getIntent().getExtras(); 
        if (extras != null) {
          month= extras.getInt("month");
          year=extras.getInt("year");
        }
	    groupItems=new String[]{"Completed","Upcoming"};
	    textView_label=(TextView) rootView.findViewById(R.id.textView_label);
	    textView_label.setText("Learning Targets");
	    textView_number=(TextView) rootView.findViewById(R.id.textView_number);
	    learningTargets=db.getAllLearningTargetsForAchievements(month+1, year);
	    textView_number.setText("("+String.valueOf(learningTargets)+" this month)");
	    
	    new GetData().execute();
		return rootView;
	}
	private class GetData extends AsyncTask<Object, Void, Object> {
		 DbHelper db=new DbHelper(mContext);

	    @Override
	    protected Object doInBackground(Object... params) {
	         completedLearningTargets=db.getAllLearningTargetsCompletedForAchievements("updated",month+1, year);
	         unCompletedLearningTargets=db.getAllLearningTargetsCompletedForAchievements("new_record",month+1, year);
	            return null;
	        
	    }

	    @Override
	    protected void onPostExecute(Object result) {
	        	 adapter=new LearningTargetAchievementsAdapter(mContext,groupItems,completedLearningTargets ,
	 	    			unCompletedLearningTargets,
						expandableListview);
		    expandableListview.setAdapter(adapter);
	    }
	}
}