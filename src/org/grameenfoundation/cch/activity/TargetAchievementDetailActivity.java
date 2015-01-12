package org.grameenfoundation.cch.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.AchievementDetailsAdapter;
import org.grameenfoundation.adapters.TargetsAchievementAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.LearningTargets;
import org.grameenfoundation.cch.model.MyCalendarEvents;
import org.grameenfoundation.cch.model.TargetsForAchievements;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class TargetAchievementDetailActivity extends Activity {

	private ExpandableListView expandableListview;
	private Context mContext;
	private CalendarEvents c;
	 private ArrayList<TargetsForAchievements> eventTargets;
	 private ArrayList<TargetsForAchievements> coverageTargets;
	 private ArrayList<TargetsForAchievements> otherTargets;
	 private ArrayList<TargetsForAchievements> learningTargets;
	private TargetsAchievementAdapter adapter;
	private DbHelper db;
	private TextView textView_label;
	private TextView textView_number;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_achievements_details);
	    expandableListview = (ExpandableListView) findViewById(R.id.expandableListView1);
	    mContext=TargetAchievementDetailActivity.this;
	    c= new CalendarEvents(mContext);
	    db=new DbHelper(TargetAchievementDetailActivity.this);
	    String[] groupItems={"Events","Coverage","Learning","Other"};
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Achievement Center");
	    getActionBar().setSubtitle("Achievement Details");
	    textView_label=(TextView) findViewById(R.id.textView_label);
	    textView_label.setText("Targets");
	    textView_number=(TextView) findViewById(R.id.textView_number);
	    eventTargets=db.getAllEventForAchievements(12, 2014);
	    coverageTargets=db.getAllCoverageForAchievements(12, 2014);
	    learningTargets=db.getAllLearningForAchievements(12, 2014);
	    otherTargets=db.getAllOtherForAchievements(12, 2014);
	    textView_number.setText("("+String.valueOf(eventTargets.size()+coverageTargets.size()+learningTargets.size()+otherTargets.size())+" this month)");
	    adapter=new TargetsAchievementAdapter(mContext,eventTargets ,
	    		coverageTargets,
	    		otherTargets,
	    		learningTargets,
					groupItems,
					expandableListview);
	    expandableListview.setAdapter(adapter);
	}

}
