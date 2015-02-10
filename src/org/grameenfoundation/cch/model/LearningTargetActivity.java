package org.grameenfoundation.cch.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
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
		
		 public ArrayList<LearningTargets> DailyLearningTargets;
		 public ArrayList<LearningTargets> WeeklyLearningTargets;
		 public ArrayList<LearningTargets> MonthlyLearningTargets;
		 public ArrayList<LearningTargets> QuarterlyLearningTargets;
		 public ArrayList<LearningTargets> MidyearLearningTargets;
		 public ArrayList<LearningTargets> AnnualLearningTargets;

		 private DbHelper db;
		 private LearningTargetAdapter learning_adapter;
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

		private long eventId;
		private long coverageId;
		private long otherId;
		private long learningId;
		 public LearningTargetActivity(){

 }
		 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			rootView=inflater.inflate(R.layout.activity_learning,null,false);
		    mContext=getActivity().getApplicationContext();
		    db=new DbHelper(getActivity());
		    learningList=(ExpandableListView) rootView.findViewById(R.id.listView_learningCategory);
		    groupItems=new String[]{};
		    groupItems=getResources().getStringArray(R.array.UpdateFrequencies);
			new GetData().execute();
			learningList.setOnChildClickListener(this);
	   
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
				selected_items=learning_adapter.getChild(groupPosition,childPosition);
				selected_id=Long.parseLong(selected_items[7]);
				String learning_category=selected_items[0];
				String learning_course=selected_items[1];
				String learing_topic=selected_items[4];
				String due_date=selected_items[3];
				String status=selected_items[6];
				String start_date=selected_items[5];
				String last_updated=selected_items[8];
				String period=selected_items[2];
				Intent intent=new Intent(getActivity(),LearningTargetsDetailActivity.class);
				intent.putExtra("learning_id",selected_id);
				intent.putExtra("learning_category",learning_category);
				intent.putExtra("learning_course",learning_course);
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
			    
			    	DailyLearningTargets=db.getAllLearningTargets("Daily");
				    WeeklyLearningTargets=db.getAllLearningTargets("Weekly");
					MonthlyLearningTargets=db.getAllLearningTargets("Monthly");
					QuarterlyLearningTargets=db.getAllLearningTargets("Quarterly");
					MidyearLearningTargets=db.getAllLearningTargets("Mid-year");
					AnnualLearningTargets=db.getAllLearningTargets("Annually");
						return null;
			        
			    }

			    @Override
			    protected void onPostExecute(Object result) {
			    	learning_adapter=new LearningTargetAdapter(mContext,groupItems,DailyLearningTargets,
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