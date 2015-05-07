package org.grameenfoundation.cch.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.adapters.EventTargetAdapter;
import org.grameenfoundation.adapters.LearningBaseAdapter;
import org.grameenfoundation.adapters.LearningTargetAdapter;
import org.grameenfoundation.cch.activity.LearningTargetsDetailActivity;
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


public class LearningTargetActivity extends Fragment implements OnChildClickListener{

		private Context mContext;															
		
		 public ArrayList<EventTargets> DailyLearningTargets;
		 public ArrayList<EventTargets> WeeklyLearningTargets;
		 public ArrayList<EventTargets> MonthlyLearningTargets;
		 public ArrayList<EventTargets> QuarterlyLearningTargets;
		 public ArrayList<EventTargets> MidyearLearningTargets;
		 public ArrayList<EventTargets> AnnualLearningTargets;

		 private DbHelper db;
		 private EventTargetAdapter learning_adapter;
		 public static final String ARG_SECTION_NUMBER = "section_number";       
		 View rootView;
		private TextView textStatus;

		private String selected_item;

		private String[] groupItem;

		private int[] imageId;
		static long due_date_to_compare;
		int selected_position;

		private ExpandableListView learningList;

		private String[] selected_items;
		static String due_date ;
		private static TextView dueDateValueLearning;
		static String start_date ;
		private static TextView startDateValue;
		private long selected_id;

		private String[] groupItems;

		private Button button_show;

		private ArrayList<EventTargets> eventId;
		private long coverageId;
		private long otherId;
		private long learningId;

		private long todayLearningId;

		private long thisWeekLearningId;

		private long thisMonthLearningId;

		private long midYearLearningId;

		private long thisQuarterLearningId;

		private long thisYearLearningId;
		 public LearningTargetActivity(){

 }
		 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			rootView=inflater.inflate(R.layout.activity_learning,null,false);
		    mContext=getActivity().getApplicationContext();
		    db=new DbHelper(getActivity());
		    learningList=(ExpandableListView) rootView.findViewById(R.id.listView_learningCategory);
			new GetData().execute();
			learningList.setOnChildClickListener(this);
	   
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
				selected_items=learning_adapter.getChild(groupPosition,childPosition);
				selected_id=Long.parseLong(selected_items[7]);
				String learning_course=selected_items[0];
				String learning_section=selected_items[9];
				String learing_topic=selected_items[12];
				String due_date=selected_items[3];
				String status=selected_items[6];
				String start_date=selected_items[5];
				String last_updated=selected_items[8];
				String period=selected_items[2];
				long old_id=Long.parseLong(selected_items[11]);
				Intent intent=new Intent(getActivity(),LearningTargetsDetailActivity.class);
				intent.putExtra("learning_id",selected_id);
				intent.putExtra("old_id",old_id);
				intent.putExtra("learning_course",learning_course);
				intent.putExtra("learning_section",learning_section);
				intent.putExtra("learning_topic", learing_topic);
				intent.putExtra("due_date", due_date);
				intent.putExtra("start_date", start_date);
				intent.putExtra("status", status);
				intent.putExtra("last_updated", last_updated);
				intent.putExtra("period", period);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				return true;
			}

			private class GetData extends AsyncTask<Object, Void, Object> {
				 DbHelper db=new DbHelper(mContext);

			    @Override
			    protected Object doInBackground(Object... params) {
			        //db.open();
			    	todayLearningId=db.getCount("Daily",MobileLearning.CCH_TARGET_TYPE_LEARNING);
		 			thisWeekLearningId=db.getCount("Weekly",MobileLearning.CCH_TARGET_TYPE_LEARNING);
		 			thisMonthLearningId=db.getCount("Monthly",MobileLearning.CCH_TARGET_TYPE_LEARNING);
		 			midYearLearningId=db.getCount("Mid-year",MobileLearning.CCH_TARGET_TYPE_LEARNING);
		 			thisQuarterLearningId=db.getCount("Quarterly",MobileLearning.CCH_TARGET_TYPE_LEARNING);
		 			thisYearLearningId=db.getCount("Annually",MobileLearning.CCH_TARGET_TYPE_LEARNING);
		 			
			    	DailyLearningTargets=db.getAllTargetsToView("Daily",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_LEARNING);
				    WeeklyLearningTargets=db.getAllTargetsToView("Weekly",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_LEARNING);
					MonthlyLearningTargets=db.getAllTargetsToView("Monthly",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_LEARNING);
					QuarterlyLearningTargets=db.getAllTargetsToView("Quarterly",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_LEARNING);
					MidyearLearningTargets=db.getAllTargetsToView("Mid-year",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_LEARNING);
					AnnualLearningTargets=db.getAllTargetsToView("Annually",MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_LEARNING);
						return null;
			        
			    }

			    @Override
			    protected void onPostExecute(Object result) {
			    	int learning_number1=(int)todayLearningId;
		 			 int learning_number2=(int)thisWeekLearningId;
		 			 int learning_number3=(int)thisMonthLearningId;
		 			 int learning_number4=(int)thisQuarterLearningId;
		 			 int learning_number5=(int)midYearLearningId;
		 			 int learning_number6=(int)thisYearLearningId;
		 			groupItems=new String[]{"To update daily ("+String.valueOf(learning_number1)+")",
		 									"To upate weekly ("+String.valueOf(learning_number2)+")",
		 									"To update monthly ("+String.valueOf(learning_number3)+")",
		 									"To update quarterly ("+String.valueOf(learning_number4)+")",
		 									"To update mid-yearly ("+String.valueOf(learning_number5)+")",
		 									"To update annually ("+String.valueOf(learning_number6)+")"};
			    	learning_adapter=new EventTargetAdapter(mContext,groupItems,DailyLearningTargets,
							WeeklyLearningTargets,
							MonthlyLearningTargets,
							QuarterlyLearningTargets,
							MidyearLearningTargets,
							AnnualLearningTargets,
							  learningList);
			    	learningList.setAdapter(learning_adapter);
			        
			    }
			}
}