package org.grameenfoundation.cch.model;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.EventTargetAdapter;
import org.grameenfoundation.cch.activity.EventTargetsDetailActivity;
import org.grameenfoundation.cch.activity.UpdateTargetActivity;

import android.content.Context;
import android.content.Intent;
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
	 

	private String[] groupItems;
	private DbHelper db;
	public static final String ARG_SECTION_NUMBER = "section_number";       
	View rootView;
	private EventTargetAdapter events_adapter;
	private String[] selected_items;
	int selected_position;
	private long selected_id;
	private Button button_show;
	private long eventId;
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
	    DailyEventTargets=db.getAllEventTargets("Daily");
	    WeeklyEventTargets=db.getAllEventTargets("Weekly");
		MonthlyEventTargets=db.getAllEventTargets("Monthly");
		QuarterlyEventTargets=db.getAllEventTargets("Quarterly");
		MidyearEventTargets=db.getAllEventTargets("Mid-year");
		AnnualEventTargets=db.getAllEventTargets("Annually");
		groupItems=new String[]{"To update today","To update this week","To update this month","To update this quarter","Half-year update","To update this year"};
		events_adapter=new EventTargetAdapter(mContext,groupItems,DailyEventTargets,
																	  WeeklyEventTargets,
																	  MonthlyEventTargets,
																	  QuarterlyEventTargets,
																	  MidyearEventTargets,
																	  AnnualEventTargets,
																	  listView_events);
		listView_events.setAdapter(events_adapter);
	   
	    View empty_view=new View(getActivity());
	    listView_events.setEmptyView(empty_view);
	    events_adapter.notifyDataSetChanged();
	   
	    listView_events.setOnChildClickListener(this);
	    button_show=(Button) rootView.findViewById(R.id.button_show);
	 
	    button_show.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//eventId=new ArrayList<String>();
				 eventId=db.getEventIdCount("Daily");
				 //coverageId=new ArrayList<String>();
				 coverageId=db.getCoverageIdCount("Daily");
				 //otherId=new ArrayList<String>();
				 otherId=db.getOtherIdCount("Daily");
				// learningId=new ArrayList<String>();
				 learningId=db.getLearningIdCount("Daily");
				 int number=(int)eventId;
				 int number2=(int)coverageId;
				 int number3=(int)otherId;
				 int number4=(int)learningId;
				 final int counter;
				
				counter=number+number2+number3+number4;	
				if(counter>0){
				Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
				startActivity(intent);
				}else if(counter==0){
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
		//String achieved=selected_items[4];
		ArrayList<String> number_achieved_list=db.getForUpdateEventNumberAchieved(selected_id, event_period);
		System.out.println(number_achieved_list.get(0));
		System.out.println(event_number);
		Intent intent=new Intent(getActivity(),EventTargetsDetailActivity.class);
		intent.putExtra("event_id",selected_id);
		intent.putExtra("event_name",event_name);
		intent.putExtra("event_number",event_number);
		intent.putExtra("event_period", event_period);
		intent.putExtra("due_date", due_date);
		intent.putExtra("start_date", start_date);
		intent.putExtra("achieved", number_achieved_list.get(0));
		intent.putExtra("status", status);
		intent.putExtra("last_updated", last_updated);
		startActivity(intent);
			return true;
}
	
}