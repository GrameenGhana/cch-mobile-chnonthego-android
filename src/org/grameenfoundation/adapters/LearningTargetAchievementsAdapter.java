package org.grameenfoundation.adapters;


	import java.util.ArrayList;

	import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.TargetChild;
import org.grameenfoundation.cch.model.TargetGroups;
import org.grameenfoundation.cch.model.TargetsForAchievements;

	import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

	public class LearningTargetAchievementsAdapter extends BaseExpandableListAdapter {

		
		 public String[] groupItem;
		 public ArrayList<TargetsForAchievements> CompletedTargets;
		 public ArrayList<TargetsForAchievements> UnCompletedTargets;
		 public ExpandableListView eventsList;
		 public LayoutInflater minflater;
		 private int count;
		 public int lastExpandedGroupPosition;    
		 private Context mContext;
		private DbHelper dbh;
		private ArrayList<TargetsForAchievements> completedTargets;
		private ArrayList<TargetsForAchievements> unCompletedTargets;
		EventTargets calendarEvents=new EventTargets();

		 public LearningTargetAchievementsAdapter(Context mContext,String[] grList,
				 					ArrayList<TargetsForAchievements> CompletedTargets,
				 					ArrayList<TargetsForAchievements> UnCompletedTargets,
				 					ExpandableListView eventsList) {
			 dbh = new DbHelper(mContext);
			 
			 completedTargets = new ArrayList<TargetsForAchievements>();
			 unCompletedTargets = new ArrayList<TargetsForAchievements>();
			 
			 completedTargets.addAll(CompletedTargets);
			 unCompletedTargets.addAll(UnCompletedTargets);
		  groupItem = grList;
		  this.mContext=mContext;
		  minflater = LayoutInflater.from(mContext);
		  this.eventsList=eventsList;
		 
		 }
		 @Override
		 public long getChildId(int groupPosition, int childPosition) {
			 long id = 0;
				
				
			return id;
		 }

		 @Override
		 public View getChildView(int groupPosition, final int childPosition,
		   boolean isLastChild, View convertView, ViewGroup parent) {
			 Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
		       	      "fonts/Roboto-Thin.ttf");
		  
		   if(convertView==null){
			   convertView=minflater.inflate(R.layout.achievements_target_listview_single,null);
		   }
		   if(groupPosition==0){
			  
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_achieved);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   TextView text8=(TextView) convertView.findViewById(R.id.textView_percentageAchieved);
		   text.setText(completedTargets.get(childPosition).getLearningTargetName());
		   text2.setText(" ");
		   if(completedTargets.get(childPosition).getLearningTargetStatus().equalsIgnoreCase("updated")){
			   text3.setTextColor(Color.GREEN);
			   text3.setText("Completed");
		   }else{
			   text3.setTextColor(Color.RED);
			   text3.setText("Upcoming"); 
		   }
		   text4.setText(completedTargets.get(childPosition).getLearningTargetEndDate());
		   text5.setText(completedTargets.get(childPosition).getLearningTargetStartDate());
		   text7.setText(completedTargets.get(childPosition).getLearningTargetLastUpdated());
		   text6.setText(" ");
		  text8.setText(" ");
		   }else if(groupPosition==1){
			   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
			   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
			   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
			   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
			   TextView text6=(TextView) convertView.findViewById(R.id.textView_achieved);
			   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
			   TextView text8=(TextView) convertView.findViewById(R.id.textView_percentageAchieved);
			   text.setText(unCompletedTargets.get(childPosition).getLearningTargetName());
			   text2.setText(" ");
			   if(unCompletedTargets.get(childPosition).getLearningTargetStatus().equalsIgnoreCase("updated")){
				   text3.setTextColor(Color.GREEN);
				   text3.setText("Completed");
			   }else{
				   text3.setTextColor(Color.RED);
				   text3.setText("Upcoming"); 
			   }
			   text4.setText(unCompletedTargets.get(childPosition).getLearningTargetEndDate());
			   text5.setText(unCompletedTargets.get(childPosition).getLearningTargetStartDate());
			   text7.setText(unCompletedTargets.get(childPosition).getLearningTargetLastUpdated());
			   text6.setText(" ");
			  text8.setText(" ");
			   
		   }
		  return convertView;
		 }


		 													
		 @Override
		 public Object getGroup(int groupPosition) {
		  return null;
		 }


		 @Override
		 public View getGroupView(int groupPosition, boolean isExpanded,
		   View convertView, ViewGroup parent) {
			 if (convertView == null) {
				   convertView = minflater.inflate(R.layout.other_listview_single,parent, false);
				  }
				   
				   TextView category=(TextView) convertView.findViewById(R.id.textView_otherCategory);
				   category.setText(groupItem[groupPosition]);
				   
				   Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
			       	      "fonts/Roboto-Thin.ttf");
				   category.setTypeface(custom_font);
				   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
				   if(groupPosition==0){
					   image.setImageResource(R.drawable.ic_complete);
				   }else if(groupPosition==1){
					   image.setImageResource(R.drawable.ic_incomplete);
				   }
				  return convertView;
		 }

		 
		 																																				
		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groupItem.length;
		}


		@Override
		public int getChildrenCount(int groupPosition) {
			int count = 0;
			if(groupPosition==0){
				count=completedTargets.size();
			}else if(groupPosition==1){
				//count=tomorrowEventId.size();
				count=unCompletedTargets.size();
			}
			return count;
		}


		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}



		@Override
		public String[] getChild(int groupPosition, int childPosition) {
			String[] childDetails = null;
			/*
			if(groupPosition==0){
				childDetails=new String[]{dailyEventTarget.get(childPosition).getEventTargetName(),
										  dailyEventTarget.get(childPosition).getEventTargetNumber(),
										  dailyEventTarget.get(childPosition).getEventTargetPeriod(),
										  dailyEventTarget.get(childPosition).getEventTargetEndDate(),
										  dailyEventTarget.get(childPosition).getEventTargetNumberAchieved(),
										  dailyEventTarget.get(childPosition).getEventTargetStartDate(),
										  dailyEventTarget.get(childPosition).getEventTargetStatus(),
										  dailyEventTarget.get(childPosition).getEventTargetId(),
										  dailyEventTarget.get(childPosition).getEventTargetLastUpdated()
										  };
			}else if(groupPosition==1){
				childDetails=new String[]{weeklyEventTargets.get(childPosition).getEventTargetName(),
										  weeklyEventTargets.get(childPosition).getEventTargetNumber(),
										  weeklyEventTargets.get(childPosition).getEventTargetPeriod(),
										  weeklyEventTargets.get(childPosition).getEventTargetEndDate(),
										  weeklyEventTargets.get(childPosition).getEventTargetNumberAchieved(),
										  weeklyEventTargets.get(childPosition).getEventTargetStartDate(),
										  weeklyEventTargets.get(childPosition).getEventTargetStatus(),
										  weeklyEventTargets.get(childPosition).getEventTargetId(),
										  weeklyEventTargets.get(childPosition).getEventTargetLastUpdated()};
			}else if(groupPosition==2){
				childDetails=new String[]{monthlyEventTargets.get(childPosition).getEventTargetName(),
						  				  monthlyEventTargets.get(childPosition).getEventTargetNumber(),
						  				  monthlyEventTargets.get(childPosition).getEventTargetPeriod(),
						  				  monthlyEventTargets.get(childPosition).getEventTargetEndDate(),
						  				  monthlyEventTargets.get(childPosition).getEventTargetNumberAchieved(),
						  				  monthlyEventTargets.get(childPosition).getEventTargetStartDate(),
						  				  monthlyEventTargets.get(childPosition).getEventTargetStatus(),
						  				  monthlyEventTargets.get(childPosition).getEventTargetId(),
						  				  monthlyEventTargets.get(childPosition).getEventTargetLastUpdated()};
			}else if(groupPosition==3){
				childDetails=new String[]{quarterlyEventTargets.get(childPosition).getEventTargetName(),
										  quarterlyEventTargets.get(childPosition).getEventTargetNumber(),
										  quarterlyEventTargets.get(childPosition).getEventTargetPeriod(),
										  quarterlyEventTargets.get(childPosition).getEventTargetEndDate(),
										  quarterlyEventTargets.get(childPosition).getEventTargetNumberAchieved(),
										  quarterlyEventTargets.get(childPosition).getEventTargetStartDate(),
										  quarterlyEventTargets.get(childPosition).getEventTargetStatus(),
										  quarterlyEventTargets.get(childPosition).getEventTargetId(),
										  quarterlyEventTargets.get(childPosition).getEventTargetLastUpdated()};
			}else if(groupPosition==4){
				childDetails=new String[]{midYearEventTargets.get(childPosition).getEventTargetName(),
										  midYearEventTargets.get(childPosition).getEventTargetNumber(),
										  midYearEventTargets.get(childPosition).getEventTargetPeriod(),
										  midYearEventTargets.get(childPosition).getEventTargetEndDate(),
										  midYearEventTargets.get(childPosition).getEventTargetNumberAchieved(),
										  midYearEventTargets.get(childPosition).getEventTargetStartDate(),
										  midYearEventTargets.get(childPosition).getEventTargetStatus(),
										  midYearEventTargets.get(childPosition).getEventTargetId(),
										  midYearEventTargets.get(childPosition).getEventTargetLastUpdated()};
			}else if(groupPosition==5){
				
				childDetails=new String[]{annualEventTargets.get(childPosition).getEventTargetName(),
										  annualEventTargets.get(childPosition).getEventTargetNumber(),
										  annualEventTargets.get(childPosition).getEventTargetPeriod(),
										  annualEventTargets.get(childPosition).getEventTargetEndDate(),
										  annualEventTargets.get(childPosition).getEventTargetNumberAchieved(),
										  annualEventTargets.get(childPosition).getEventTargetStartDate(),
										  annualEventTargets.get(childPosition).getEventTargetStatus(),
										  annualEventTargets.get(childPosition).getEventTargetId(),
										  annualEventTargets.get(childPosition).getEventTargetLastUpdated()};
			}*/
			return childDetails;
				
		}



		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}



		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

		public void onGroupExpanded(int groupPosition) {
	   	
	   	if(groupPosition != lastExpandedGroupPosition){
	           eventsList.collapseGroup(lastExpandedGroupPosition);
	      
	   }
	   	
	       super.onGroupExpanded(groupPosition);
	    
	       lastExpandedGroupPosition = groupPosition;
	       
	   }
	} 

