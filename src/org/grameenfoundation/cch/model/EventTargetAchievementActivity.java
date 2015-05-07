package org.grameenfoundation.cch.model;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.adapters.NumericalTargetAchievementsAdapter;
import org.grameenfoundation.adapters.TargetsAchievementAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.activity.TargetAchievementDetailActivity;

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

public class EventTargetAchievementActivity extends Activity {

	private ExpandableListView expandableListview;
	private Context mContext;
	private CalendarEvents c;
	 private ArrayList<EventTargets> completedEventTargets;
	 private ArrayList<EventTargets> unCompletedEventTargets;
	private NumericalTargetAchievementsAdapter adapter;
	private DbHelper db;
	private TextView textView_label;
	private TextView textView_number;
	//private View rootView;
	private int month;
	private int year;
	private int number;
	private String[] groupItems;
	 public EventTargetAchievementActivity(){

	 }
	@Override
	public  void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_target_details_);
	    getActionBar().setTitle("Achievement Center");
	    getActionBar().setSubtitle("Achievement Details");
	    expandableListview = (ExpandableListView) findViewById(R.id.expandableListView1);
	    mContext=getApplicationContext();
	    db=new DbHelper(mContext);
	    groupItems=new String[]{"Completed","In progress"};
	    textView_label=(TextView) findViewById(R.id.textView_label);
	    textView_label.setText("Event Targets");
	    new GetData().execute();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          month= extras.getInt("month");
          year=extras.getInt("year");
          number=extras.getInt("number");
        }
	    textView_number=(TextView) findViewById(R.id.textView_number);
	}
	private class GetData extends AsyncTask<Object, Void, Object> {
		 DbHelper db=new DbHelper(mContext);

	    @Override
	    protected Object doInBackground(Object... params) {
	    	System.out.println(month);
	          completedEventTargets=db.getListOfTargetsForAchievements(MobileLearning.CCH_TARGET_STATUS_UPDATED,MobileLearning.CCH_TARGET_TYPE_EVENT,month+1, year);
	          unCompletedEventTargets=db.getListOfTargetsForAchievements(MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_EVENT,month+1, year);
			return null;
	    }

	    @Override
	    protected void onPostExecute(Object result) {
	    	  textView_number.setText(" ("+number+" this month)");
	        	 adapter=new NumericalTargetAchievementsAdapter(mContext,groupItems,completedEventTargets,
	        			 unCompletedEventTargets,expandableListview);
	 	    expandableListview.setAdapter(adapter);
	 	    
	    }

	}
}