package org.grameenfoundation.cch.model;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.LearningTargetAchievementsAdapter;
import org.grameenfoundation.adapters.NumericalTargetAchievementsAdapter;
import org.grameenfoundation.calendar.CalendarEvents;

import android.content.Context;
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
	 private ArrayList<TargetsForAchievements> learningTargets;
	 private ArrayList<TargetsForAchievements> completedLearningTargets;
	 private ArrayList<TargetsForAchievements> unCompletedLearningTargets;
	private LearningTargetAchievementsAdapter adapter;
	private DbHelper db;
	private TextView textView_label;
	private TextView textView_number;
	private View rootView;
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
	    String[] groupItems={"Completed","Upcoming"};
	    textView_label=(TextView) rootView.findViewById(R.id.textView_label);
	    textView_label.setText("Learning Targets");
	    textView_number=(TextView) rootView.findViewById(R.id.textView_number);
	    learningTargets=db.getAllLearningForAchievements(12, 2014);
	    completedLearningTargets=db.getAllLearningTargetsCompletedForAchievements("updated", 12, 2014);
	    unCompletedLearningTargets=db.getAllLearningTargetsCompletedForAchievements("new_record", 12, 2014);
	    textView_number.setText("("+String.valueOf(learningTargets.size())+" this month)");
	    adapter=new LearningTargetAchievementsAdapter(mContext,groupItems,completedLearningTargets ,
	    			unCompletedLearningTargets,
					expandableListview);
	    expandableListview.setAdapter(adapter);
		return rootView;
	}

}