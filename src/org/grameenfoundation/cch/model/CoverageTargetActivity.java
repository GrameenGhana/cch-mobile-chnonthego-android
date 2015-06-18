package org.grameenfoundation.cch.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.adapters.EventBaseAdapter;
import org.grameenfoundation.adapters.EventTargetAdapter;
import org.grameenfoundation.cch.activity.CoverageTargetsDetailActivity;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

public class CoverageTargetActivity extends Fragment implements OnChildClickListener{

	private Context mContext;															
	 public ArrayList<EventTargets> DailyCoverageTargets;
	 public ArrayList<EventTargets> WeeklyCoverageTargets;
	 public ArrayList<EventTargets> MonthlyCoverageTargets;
	 public ArrayList<EventTargets> QuarterlyCoverageTargets;
	 public ArrayList<EventTargets> MidyearCoverageTargets;
	 public ArrayList<EventTargets> AnnualCoverageTargets;

	 private String[] groupItems;
	private DbHelper db;
	 public static final String ARG_SECTION_NUMBER = "section_number";       
	View rootView;
	private ExpandableListView listView_coverage;
	private EventTargetAdapter coverage_adapter;
	private String[] selected_items;
	private RadioGroup category_options;
	private String[] items3;
	int selected_position;
	protected RadioButton category_people;
	private long selected_id;
	private Button button_show;
	private ArrayList<EventTargets> eventId;
	private long coverageId;
	private long otherId;
	private long learningId;
	private long todayCoverageId;
	private long thisWeekCoverageId;
	private long thisMonthCoverageId;
	private long midYearCoverageId;
	private long thisQuarterCoverageId;
	private long thisYearCoverageId;
	static String due_date ;
	private static TextView dueDateValue;
	static String start_date ;
	static long due_date_to_compare;
	private static TextView startDateValue;
	
	 public CoverageTargetActivity(){

 }
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 rootView=inflater.inflate(R.layout.activity_coverage,null,false);
	    mContext=getActivity().getApplicationContext();
	    db=new DbHelper(getActivity());
	    listView_coverage=(ExpandableListView) rootView.findViewById(R.id.expandableListView1);
	    listView_coverage.setOnChildClickListener(this);
	    
		
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
    	listView_coverage.setOnChildClickListener(this);
	return rootView;
		   
	}
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, final long id) {
		selected_items=coverage_adapter.getChild(groupPosition, childPosition);
		selected_id=Long.parseLong(selected_items[7]);
		String coverage_name=selected_items[9];
		String coverage_detail=selected_items[0];
		String coverage_number=selected_items[1];
		String coverage_period=selected_items[2];
		String due_date=selected_items[3];
		String start_date=selected_items[5];
		String status=selected_items[6];
		String achieved=selected_items[4];
		String last_updated=selected_items[8];
		String coverage_category=selected_items[12];
		long old_id=Long.parseLong(selected_items[11]);
		ArrayList<String> number_achieved_list=db.getNumberAchieved(selected_id, coverage_period,MobileLearning.CCH_TARGET_STATUS_NEW);
		Intent intent=new Intent(getActivity(),CoverageTargetsDetailActivity.class);
		intent.putExtra("coverage_id",selected_id);
		intent.putExtra("old_id",old_id);
		intent.putExtra("coverage_name",coverage_name);
		intent.putExtra("coverage_number",coverage_number);
		intent.putExtra("coverage_period", coverage_period);
		intent.putExtra("due_date", due_date);
		intent.putExtra("start_date", start_date);
		intent.putExtra("achieved", number_achieved_list.get(0));
		intent.putExtra("status", status);
		intent.putExtra("last_updated", last_updated);
		intent.putExtra("coverage_category", coverage_category);
		intent.putExtra("coverage_detail", coverage_detail);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		return true;
	}
	private class GetData extends AsyncTask<Object, Void, Object> {
		 DbHelper db=new DbHelper(mContext);

	    @Override
	    protected Object doInBackground(Object... params) {
	    	todayCoverageId=db.getCount("Daily",MobileLearning.CCH_TARGET_TYPE_COVERAGE);
			thisWeekCoverageId=db.getCount("Weekly",MobileLearning.CCH_TARGET_TYPE_COVERAGE);
			thisMonthCoverageId=db.getCount("Monthly",MobileLearning.CCH_TARGET_TYPE_COVERAGE);
			midYearCoverageId=db.getCount("Mid-year",MobileLearning.CCH_TARGET_TYPE_COVERAGE);
			thisQuarterCoverageId=db.getCount("Quarterly",MobileLearning.CCH_TARGET_TYPE_COVERAGE);
			thisYearCoverageId=db.getCount("Annually",MobileLearning.CCH_TARGET_TYPE_COVERAGE);
				
	    	DailyCoverageTargets=db.getAllTargetsToView("Daily",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_COVERAGE);
		    WeeklyCoverageTargets=db.getAllTargetsToView("Weekly",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_COVERAGE);
		    MonthlyCoverageTargets=db.getAllTargetsToView("Monthly",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_COVERAGE);
		    MidyearCoverageTargets=db.getAllTargetsToView("Mid-year",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_COVERAGE);
		    QuarterlyCoverageTargets=db.getAllTargetsToView("Quarterly",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_COVERAGE);
		    AnnualCoverageTargets=db.getAllTargetsToView("Annually",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_COVERAGE);
				return null;
	        
	    }

	    @Override
	    protected void onPostExecute(Object result) {
	    	int coverage_number1=(int)todayCoverageId;
			int coverage_number2=(int)thisWeekCoverageId;
			int coverage_number3=(int)thisMonthCoverageId;
			int coverage_number4=(int)thisQuarterCoverageId;
			int coverage_number5=(int)midYearCoverageId;
			int coverage_number6=(int)thisYearCoverageId;
			
			groupItems=new String[]{"To update daily ("+String.valueOf(coverage_number1)+")",
									"To upate weekly ("+String.valueOf(coverage_number2)+")",
									"To update monthly ("+String.valueOf(coverage_number3)+")",
									"To update quarterly ("+String.valueOf(coverage_number4)+")",
									"To update mid-yearly ("+String.valueOf(coverage_number5)+")",
									"To update annually ("+String.valueOf(coverage_number6)+")"};
	    	coverage_adapter=new EventTargetAdapter(mContext,groupItems,DailyCoverageTargets,
					WeeklyCoverageTargets,
					MonthlyCoverageTargets,
					QuarterlyCoverageTargets,
					MidyearCoverageTargets,
					AnnualCoverageTargets,
					listView_coverage);
	    	listView_coverage.setAdapter(coverage_adapter);
	        												
	    }
	}
}