package org.grameenfoundation.cch.model;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.adapters.EventTargetAdapter;
import org.grameenfoundation.adapters.NumericalTargetAchievementsAdapter;
import org.grameenfoundation.cch.activity.EventTargetsDetailActivity;
import org.grameenfoundation.cch.activity.UpdateTargetActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

public class EventTargetActivity extends Fragment implements OnChildClickListener{

	private Context mContext;															
	private ExpandableListView listView_events;
	public ArrayList<EventTargets> DailyEventTargets;
	 public ArrayList<EventTargets> WeeklyEventTargets;
	 public ArrayList<EventTargets> MonthlyEventTargets;
	 public ArrayList<EventTargets> QuarterlyEventTargets;
	 public ArrayList<EventTargets> MidyearEventTargets;
	 public ArrayList<EventTargets> AnnualEventTargets;
	 
		private long todayEventId;
		private long thisMonthEventId;
		private long thisWeekEventId;
		private long midYearEventId;
		private long thisQuarterEventId;
		private long thisYearEventId;
		private int event_number1;
		private int event_number2;
		private int event_number3;
		private int event_number4;
		private int event_number5;
		private int event_number6;
	private String[] groupItems;
	private DbHelper db;
	public static final String ARG_SECTION_NUMBER = "section_number";       
	View rootView;
	private EventTargetAdapter events_adapter;
	private String[] selected_items;
	int selected_position;
	private long selected_id;
	private Button button_show;
	private ArrayList<EventTargets> eventId;
	private long coverageId;
	private long otherId;
	private long learningId;
	static String due_date ;
	static String start_date;
	private static TextView dueDateValue;
	private static TextView startDateValue;
	static long due_date_to_compare;
	 public EventTargetActivity(){

    }
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 rootView=inflater.inflate(R.layout.activity_events,null,false);
	    mContext=getActivity().getApplicationContext();
	    db=new DbHelper(getActivity());
	    listView_events=(ExpandableListView) rootView.findViewById(R.id.expandableListView1);
	    new GetData().execute();
	    listView_events.setOnChildClickListener(this);
	 
	    button_show=(Button) rootView.findViewById(R.id.button_show);
		eventId=db.getAllTargetsForUpdate("Daily",MobileLearning.CCH_TARGET_STATUS_NEW);
		 final int number=(int)eventId.size();
		 button_show.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					if(number>0){
					Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					}else if(number==0){
						 Toast.makeText(getActivity(), "You have no targets to update!",
						         Toast.LENGTH_SHORT).show();
					}
				}
				
			});		
	return rootView;
		   
	}
	 

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, final long id) {
		selected_items=events_adapter.getChild(groupPosition, childPosition);
		selected_id=Long.parseLong(selected_items[7]);
		String event_name=selected_items[0];
		String event_number=selected_items[1];
		String event_period=selected_items[2];
		String due_date=selected_items[3];
		String start_date=selected_items[5];
		String status=selected_items[6];
		String last_updated=selected_items[8];
		String target_category=selected_items[12];
		long old_id=Long.parseLong(selected_items[11]);
		//String achieved=selected_items[4];
		ArrayList<String> number_achieved_list=db.getNumberAchieved(selected_id, event_period,MobileLearning.CCH_TARGET_STATUS_NEW);
		System.out.println(number_achieved_list.get(0));
		System.out.println(event_number);
		Intent intent=new Intent(getActivity(),EventTargetsDetailActivity.class);
		intent.putExtra("event_id",selected_id);
		intent.putExtra("old_id",old_id);
		intent.putExtra("event_name",event_name);
		intent.putExtra("event_number",event_number);
		intent.putExtra("event_period", event_period);
		intent.putExtra("type", "event");
		intent.putExtra("due_date", due_date);
		intent.putExtra("start_date", start_date);
		intent.putExtra("achieved", number_achieved_list.get(0));
		intent.putExtra("status", status);
		intent.putExtra("last_updated", last_updated);
		intent.putExtra("event_detail", target_category);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			return true;
}
	private class GetData extends AsyncTask<Object, Void, Object> {
		 DbHelper db=new DbHelper(mContext);
	

	    @Override
	    protected Object doInBackground(Object... params) {
	    	todayEventId=db.getCount("Daily",MobileLearning.CCH_TARGET_TYPE_EVENT);
		    thisMonthEventId=db.getCount("Monthly",MobileLearning.CCH_TARGET_TYPE_EVENT);
		    thisWeekEventId=db.getCount("Weekly",MobileLearning.CCH_TARGET_TYPE_EVENT);
		    midYearEventId=db.getCount("Mid-year",MobileLearning.CCH_TARGET_TYPE_EVENT);
		    thisQuarterEventId=db.getCount("Quarterly",MobileLearning.CCH_TARGET_TYPE_EVENT);
		    thisYearEventId=db.getCount("Annually",MobileLearning.CCH_TARGET_TYPE_EVENT);
  		      
	           	DailyEventTargets=db.getAllTargetsToView("Daily",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_EVENT);
	           	WeeklyEventTargets=db.getAllTargetsToView("Weekly",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_EVENT);
	   			MonthlyEventTargets=db.getAllTargetsToView("Monthly",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_EVENT);
	   			QuarterlyEventTargets=db.getAllTargetsToView("Quarterly",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_EVENT);
	   			MidyearEventTargets=db.getAllTargetsToView("Mid-year",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_EVENT);
	   			AnnualEventTargets=db.getAllTargetsToView("Annually",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_EVENT);
				return null;
	    }
	    @Override
	    protected void onPostExecute(Object result) {
	    	 event_number1=(int)todayEventId;
			  event_number2=(int)thisWeekEventId;
			  event_number3=(int)thisMonthEventId;
			  event_number4=(int)thisQuarterEventId;
			  event_number5=(int)midYearEventId;
			  event_number6=(int)thisYearEventId;
			  groupItems=new String[]{"To update daily ("+String.valueOf(event_number1)+")",
						"To upate weekly ("+String.valueOf(event_number2)+")",
						"To update monthly ("+String.valueOf(event_number3)+")",
						"To update quarterly ("+String.valueOf(event_number4)+")",
						"To update mid-yearly ("+String.valueOf(event_number5)+")",
						"To update annually ("+String.valueOf(event_number6)+")"};
			   
	    	events_adapter=new EventTargetAdapter(mContext,groupItems,DailyEventTargets,
					  WeeklyEventTargets,
					  MonthlyEventTargets,
					  QuarterlyEventTargets,
					  MidyearEventTargets,
					  AnnualEventTargets,
					  listView_events);
	    	listView_events.setAdapter(events_adapter);
	    }
	}
}