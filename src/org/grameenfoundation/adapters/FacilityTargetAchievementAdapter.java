package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.FacilityTargets;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class FacilityTargetAchievementAdapter extends BaseExpandableListAdapter {

	
	 public String[] groupItem;
	 public ExpandableListView eventsList;
	 public LayoutInflater minflater;
	 private int count;
	 public int lastExpandedGroupPosition;    
	 private Context mContext;
	private DbHelper dbh;
	private ArrayList<FacilityTargets> completedTargets;
	private ArrayList<FacilityTargets> unCompletedTargets;
	EventTargets calendarEvents=new EventTargets();
	private double percentage;
	private String percentage_achieved;
	private TextView target_type;
	private TextView reminder;
	private TextView textView_achieved;
	private TextView textView_lastUpdated;
	private TextView textView_startDate;
	private TextView textView_dueDate;

	 public FacilityTargetAchievementAdapter(Context mContext,String[] grList,
			 					ArrayList<FacilityTargets> CompletedTargets,
			 					ArrayList<FacilityTargets> UnCompletedTargets,
			 					ExpandableListView eventsList) {
		 dbh = new DbHelper(mContext);
		 
		 completedTargets = new ArrayList<FacilityTargets>();
		 unCompletedTargets = new ArrayList<FacilityTargets>();
		 
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
		 View list=null;
		 if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) mContext
		        		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				  list = new View(mContext);
				  list = inflater.inflate(R.layout.facility_target_achievement_single, null);
				 
				
			}  else {
				 list = (View) convertView;  
			}
	   if(groupPosition==0){
		  
		   int number_achieved_today=Integer.valueOf(completedTargets.get(childPosition).getTargetNumberAchieved());
		    percentage= ((double)number_achieved_today/Integer.valueOf(completedTargets.get(childPosition).getTargetNumber()))*100;	
		    percentage_achieved=String.format("%.0f", percentage);
		 target_type = (TextView) list.findViewById(R.id.textView_targetName);
		 textView_achieved = (TextView) list.findViewById(R.id.textView_achieved);  
		 target_type.setText(completedTargets.get(childPosition).getTargetType());
		 textView_achieved.setText(completedTargets.get(childPosition).getTargetNumberAchieved()+"/"+completedTargets.get(childPosition).getTargetNumber()+" ("+percentage_achieved+") %");
		 textView_lastUpdated.setText("Last Updated: "+completedTargets.get(childPosition).getTargetLastUpdated());
		 textView_startDate.setText("Start: "+completedTargets.get(childPosition).getTargetStartDate());
		 textView_dueDate.setText("Due: "+completedTargets.get(childPosition).getTargetEndDate());
	   }else if(groupPosition==1){
		   int number_achieved_today=Integer.valueOf(unCompletedTargets.get(childPosition).getTargetNumberAchieved());
		    percentage= ((double)number_achieved_today/Integer.valueOf(unCompletedTargets.get(childPosition).getTargetNumber()))*100;	
		    percentage_achieved=String.format("%.0f", percentage);
		 target_type = (TextView) list.findViewById(R.id.textView_targetName);
		 textView_achieved = (TextView) list.findViewById(R.id.textView_achieved);  
		 textView_lastUpdated = (TextView) list.findViewById(R.id.textView_lastUpdated);  
		 textView_startDate = (TextView) list.findViewById(R.id.textView_startDate);
		 textView_dueDate = (TextView) list.findViewById(R.id.textView_dueDate);
		 if(unCompletedTargets.get(childPosition).getTargetDetail().equals("")){
			 target_type.setText(unCompletedTargets.get(childPosition).getTargetType());
		 }else{
			 target_type.setText(unCompletedTargets.get(childPosition).getTargetDetail());
		 }
		 textView_achieved.setText(unCompletedTargets.get(childPosition).getTargetNumberAchieved()+"/"+unCompletedTargets.get(childPosition).getTargetNumber()+" ("+percentage_achieved+") %");
		 textView_lastUpdated.setText("Last Updated: "+unCompletedTargets.get(childPosition).getTargetLastUpdated());
		 textView_startDate.setText("Start: "+unCompletedTargets.get(childPosition).getTargetStartDate());
		 textView_dueDate.setText("Due: "+unCompletedTargets.get(childPosition).getTargetEndDate());
		   
	   }
	  return list;
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
			   
			   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
			   if(groupPosition==0){
				   image.setImageResource(R.drawable.ic_complete_new);
			   }else if(groupPosition==1){
				   image.setImageResource(R.drawable.ic_close);
			   }
			  return convertView;
	 }

	 
	 																																				
	@Override
	public int getGroupCount() {
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
		return 0;
	}



	@Override
	public String[] getChild(int groupPosition, int childPosition) {
		String[] childDetails = null;

		return childDetails;
			
	}



	@Override
	public boolean hasStableIds() {
		return true;
	}



	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
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

