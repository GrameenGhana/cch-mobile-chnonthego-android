package org.grameenfoundation.cch.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.EventBaseAdapter;
import org.grameenfoundation.adapters.EventTargetAdapter;
import org.grameenfoundation.adapters.LearningTargetAdapter;
import org.grameenfoundation.cch.activity.OtherTargetsDetailActivity;
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

public class OtherTargetActivity extends Fragment implements OnChildClickListener{

	private Context mContext;	
	 public ArrayList<EventTargets> DailyOtherTargets;
	 public ArrayList<EventTargets> WeeklyOtherTargets;
	 public ArrayList<EventTargets> MonthlyOtherTargets;
	 public ArrayList<EventTargets> QuarterlyOtherTargets;
	 public ArrayList<EventTargets> MidyearOtherTargets;
	 public ArrayList<EventTargets> AnnualOtherTargets;
	 private String[] groupItems;
	private DbHelper db;
	 public static final String ARG_SECTION_NUMBER = "section_number";       
	View rootView;
	private ExpandableListView listView_other;
	private TextView textStatus;
	private EventTargetAdapter other_adapter;
	int selected_position;
	private long selected_id;
	private Button button_show;
	private long eventId;
	private long coverageId;
	private long otherId;
	private long learningId;
	static String due_date ;
	private static TextView dueDateValue;
	
	static String start_date ;
	private static TextView startDateValue;
	static long due_date_to_compare;
	 public OtherTargetActivity(){

}
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 rootView=inflater.inflate(R.layout.activity_other,null,false);
	    mContext=getActivity().getApplicationContext();
	    db=new DbHelper(getActivity());
	    listView_other=(ExpandableListView) rootView.findViewById(R.id.expandableListView_other);
	    listView_other.setAdapter(other_adapter);
	    listView_other.setOnChildClickListener(this);
	    groupItems=new String[]{};
	    groupItems=getResources().getStringArray(R.array.UpdateFrequencies);
	    new GetData().execute();
	   button_show=(Button) rootView.findViewById(R.id.button_show);
	   
	    button_show.setOnClickListener(new OnClickListener(){
	    	
			@Override
			public void onClick(View v) {
				 eventId=db.getEventIdCount("Daily");
				 coverageId=db.getCoverageIdCount("Daily");
				 otherId=db.getOtherIdCount("Daily");
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
				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
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
			int groupPosition, int childPosition, long id) {
		String[] selected_items=other_adapter.getChild(groupPosition, childPosition);
		selected_id=Long.parseLong(selected_items[7]);
		String other_name=selected_items[0];
		String other_number=selected_items[1];
		String other_period=selected_items[2];
		String due_date=selected_items[3];
		String status=selected_items[6];
		String startDate=selected_items[5];
		String achieved=selected_items[4];
		String last_updated=selected_items[8];
		ArrayList<String> number_achieved_list=db.getForUpdateOtherNumberAchieved(selected_id, other_period);
		Intent intent=new Intent(getActivity(),OtherTargetsDetailActivity.class);
		intent.putExtra("other_id",selected_id);
		intent.putExtra("other_name",other_name);
		intent.putExtra("other_number",other_number);
		intent.putExtra("other_period", other_period);
		intent.putExtra("due_date", due_date);
		intent.putExtra("start_date", startDate);
		intent.putExtra("status", status);
		intent.putExtra("last_updated", last_updated);
		intent.putExtra("achieved", number_achieved_list.get(0));
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		return true;
	}

	private class GetData extends AsyncTask<Object, Void, Object> {
		 DbHelper db=new DbHelper(mContext);

	    @Override
	    protected Object doInBackground(Object... params) {
	    	DailyOtherTargets=db.getAllOtherTargets("Daily");
	 	    WeeklyOtherTargets=db.getAllOtherTargets("Weekly");
	 	    MonthlyOtherTargets=db.getAllOtherTargets("Monthly");
	 	    QuarterlyOtherTargets=db.getAllOtherTargets("Quarterly");
	 	    MidyearOtherTargets=db.getAllOtherTargets("Mid-year");
	 	    AnnualOtherTargets=db.getAllOtherTargets("Annually");
				return null;
	        
	    }

	    @Override
	    protected void onPostExecute(Object result) {
	    	other_adapter=new EventTargetAdapter(mContext,groupItems,DailyOtherTargets,
					 WeeklyOtherTargets,
					 MonthlyOtherTargets,
					 QuarterlyOtherTargets,
					 MidyearOtherTargets,
					 AnnualOtherTargets,
					listView_other);
	    	listView_other.setAdapter(other_adapter);
	        
	    }
	}
	}
