package org.grameenfoundation.cch.model;

import java.util.ArrayList;
import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.adapters.EventTargetAdapter;
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
	private EventTargetAdapter other_adapter;
	int selected_position;
	private long selected_id;
	private Button button_show;
	private ArrayList<EventTargets> eventId;
	private long todayOtherId;
	private long thisWeekOtherId;
	private long thisMonthOtherId;
	private long midYearOtherId;
	private long thisQuarterOtherId;
	private long thisYearOtherId;
	static String due_date ;
	
	static String start_date ;
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
	    new GetData().execute();
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
			int groupPosition, int childPosition, long id) {
		String[] selected_items=other_adapter.getChild(groupPosition, childPosition);
		selected_id=Long.parseLong(selected_items[7]);
		String other_name=selected_items[0];
		String other_number=selected_items[1];
		String other_period=selected_items[2];
		String due_date=selected_items[3];
		String status=selected_items[6];
		String startDate=selected_items[5];
		String last_updated=selected_items[8];
		String detail=selected_items[12];//personal or not
		long old_id=Long.parseLong(selected_items[11]);
		//System.out.println(detail);
		ArrayList<String> number_achieved_list=db.getNumberAchieved(selected_id, other_period,MobileLearning.CCH_TARGET_STATUS_NEW);
		Intent intent=new Intent(getActivity(),OtherTargetsDetailActivity.class);
		intent.putExtra("other_id",selected_id);
		intent.putExtra("old_id",old_id);
		intent.putExtra("other_name",other_name);
		intent.putExtra("other_number",other_number);
		intent.putExtra("other_period", other_period);
		intent.putExtra("due_date", due_date);
		intent.putExtra("start_date", startDate);
		intent.putExtra("status", status);
		intent.putExtra("last_updated", last_updated);
		intent.putExtra("achieved", number_achieved_list.get(0));
		intent.putExtra("detail",detail);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		return true;
	}

	private class GetData extends AsyncTask<Object, Void, Object> {
		 DbHelper db=new DbHelper(mContext);

	    @Override
	    protected Object doInBackground(Object... params) {
	    	 todayOtherId=db.getCount("Daily",MobileLearning.CCH_TARGET_TYPE_OTHER);
			 thisWeekOtherId=db.getCount("Weekly",MobileLearning.CCH_TARGET_TYPE_OTHER);
			 thisMonthOtherId=db.getCount("Monthly",MobileLearning.CCH_TARGET_TYPE_OTHER);
			 midYearOtherId=db.getCount("Mid-year",MobileLearning.CCH_TARGET_TYPE_OTHER);
			 thisQuarterOtherId=db.getCount("Quarterly",MobileLearning.CCH_TARGET_TYPE_OTHER);
			 thisYearOtherId=db.getCount("Annually",MobileLearning.CCH_TARGET_TYPE_OTHER);
			 
	    	DailyOtherTargets=db.getAllTargetsToView("Daily",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_OTHER);
	 	    WeeklyOtherTargets=db.getAllTargetsToView("Weekly",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_OTHER);
	 	    MonthlyOtherTargets=db.getAllTargetsToView("Monthly",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_OTHER);
	 	    QuarterlyOtherTargets=db.getAllTargetsToView("Quarterly",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_OTHER);
	 	    MidyearOtherTargets=db.getAllTargetsToView("Mid-year",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_OTHER);
	 	    AnnualOtherTargets=db.getAllTargetsToView("Annually",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_OTHER);
				return null;
	        
	    }

	    @Override
	    protected void onPostExecute(Object result) {
	    	 int other_number1=(int)todayOtherId;
			 int other_number2=(int)thisWeekOtherId;
			 int other_number3=(int)thisMonthOtherId;
			 int other_number4=(int)thisQuarterOtherId;
			 int other_number5=(int)midYearOtherId;
			 int other_number6=(int)thisYearOtherId;
			 groupItems=new String[]{"To update daily ("+String.valueOf(other_number1)+")",
						"To upate weekly ("+String.valueOf(other_number2)+")",
						"To update monthly ("+String.valueOf(other_number3)+")",
						"To update quarterly ("+String.valueOf(other_number4)+")",
						"To update mid-yearly ("+String.valueOf(other_number5)+")",
						"To update annually ("+String.valueOf(other_number6)+")"};
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
