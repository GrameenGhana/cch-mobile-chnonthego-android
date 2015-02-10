package org.grameenfoundation.cch.model;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.NumericalTargetAchievementsAdapter;
import org.grameenfoundation.calendar.CalendarEvents;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class OtherTargetAchievementActivity extends Fragment{
	private ExpandableListView expandableListview;
	private Context mContext;
	private CalendarEvents c;
	 private int otherTargets;
	 private ArrayList<EventTargets> completedOtherTargets;
	 private ArrayList<EventTargets> unCompletedOtherTargets;
	private NumericalTargetAchievementsAdapter adapter;
	private DbHelper db;
	private TextView textView_label;
	private TextView textView_number;
	private View rootView;
	private int month;
	private int year;
	private String[] groupItems;
	 public OtherTargetAchievementActivity(){

	 }
	@Override
	public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    rootView=inflater.inflate(R.layout.activity_achievements_details,null,false);
	    expandableListview = (ExpandableListView) rootView.findViewById(R.id.expandableListView1);
	    mContext=getActivity().getApplicationContext();
	    db=new DbHelper(mContext);
	    groupItems=new String[]{"Completed","Upcoming"};
	    textView_label=(TextView) rootView.findViewById(R.id.textView_label);
	    textView_label.setText("Other Targets");
	    textView_number=(TextView) rootView.findViewById(R.id.textView_number);
	    Bundle extras = getActivity().getIntent().getExtras(); 
        if (extras != null) {
          month= extras.getInt("month");
          year=extras.getInt("year");
        }
        new GetData().execute();
	    textView_number.setText("("+String.valueOf(otherTargets)+" this month)");
	 
		return rootView;
	}

	private class GetData extends AsyncTask<Object, Void, Object> {
		 DbHelper db=new DbHelper(mContext);

	    @Override
	    protected Object doInBackground(Object... params) {
	    	 otherTargets=db.getAllOtherTargetsForAchievements(month+1, year);
	          completedOtherTargets=db.getAllOtherTargetsCompletedForAchievements("updated",month+1, year);
	         unCompletedOtherTargets=db.getAllOtherTargetsCompletedForAchievements("new_record",month+1, year);
	            return null;
	        
	    }

	    @Override
	    protected void onPostExecute(Object result) {
	        	 adapter=new NumericalTargetAchievementsAdapter(mContext,groupItems,completedOtherTargets ,
	 	    			unCompletedOtherTargets,
	 					expandableListview);
	 	    expandableListview.setAdapter(adapter);
	    }
	}
}