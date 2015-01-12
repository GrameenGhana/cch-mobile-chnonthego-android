package org.grameenfoundation.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.mobile.learningGF.R.color;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.LearningTargets;
import org.grameenfoundation.cch.model.TargetsForAchievements;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class TargetsAchievementAdapter extends BaseExpandableListAdapter{
	private Context mContext;
	 private final ArrayList<TargetsForAchievements> eventTargets;
	 private final ArrayList<TargetsForAchievements> coverageTargets;
	 private final ArrayList<TargetsForAchievements> otherTargets;
	 private final ArrayList<TargetsForAchievements> learningTargets;
	 private String[] groupItems;
	 public LayoutInflater minflater;
	 ExpandableListView event_list;
	private int lastExpandedGroupPosition;
	 
	 
	 public TargetsAchievementAdapter(Context c,ArrayList<TargetsForAchievements> EventTargets ,
										ArrayList<TargetsForAchievements> CoverageTargets,
										ArrayList<TargetsForAchievements> OtherTargets,
										ArrayList<TargetsForAchievements> LearningTargets,
			 							String[] groupItems,
			 							ExpandableListView event_list
			 							) {
      mContext = c;
      		eventTargets = new ArrayList<TargetsForAchievements>();
      		coverageTargets = new ArrayList<TargetsForAchievements>();
      		otherTargets = new ArrayList<TargetsForAchievements>();
      		learningTargets=new ArrayList<TargetsForAchievements>();
      		
      		eventTargets.addAll(EventTargets);
      		coverageTargets.addAll(CoverageTargets);
      		otherTargets.addAll(OtherTargets);
      		learningTargets.addAll(LearningTargets);
      	   minflater = LayoutInflater.from(mContext);
      this.groupItems=groupItems;
      this.event_list=event_list;
    
  }
	


	@Override
	public int getGroupCount() {
		
		return groupItems.length;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		int count = 0;
		if(groupPosition==0){
			count=eventTargets.size();
		}else if(groupPosition==1){
			count=coverageTargets.size();
		}else if(groupPosition==2){
			count=learningTargets.size();
		}else if(groupPosition==3){
			count=otherTargets.size();
		
		}
		return count;
	}
	@Override
	public Object getGroup(int groupPosition) {
	
		return null;
	}
	@Override
	public String[] getChild(int groupPosition, int childPosition) {
		String[] childDetails = null;
		if(groupPosition==0){
			childDetails=new String[]{eventTargets.get(childPosition).getEventTargetName(),//0
									  eventTargets.get(childPosition).getEventTargetNumber(),//1	
									  eventTargets.get(childPosition).getEventTargetPeriod(),//2
									  eventTargets.get(childPosition).getEventTargetEndDate(),//3
									  eventTargets.get(childPosition).getEventTargetNumberAchieved(),//4
									  eventTargets.get(childPosition).getEventTargetStartDate(),//5
									  eventTargets.get(childPosition).getEventTargetStatus(),//6
									  eventTargets.get(childPosition).getEventTargetId(),//7
									  eventTargets.get(childPosition).getEventTargetLastUpdated()//8
									  };
		}else if(groupPosition==1){
			childDetails=new String[]{coverageTargets.get(childPosition).getEventTargetName(),//0
									  coverageTargets.get(childPosition).getEventTargetNumber(),//1	
									  coverageTargets.get(childPosition).getEventTargetPeriod(),//2
									  coverageTargets.get(childPosition).getEventTargetEndDate(),//3
									  coverageTargets.get(childPosition).getEventTargetNumberAchieved(),//4
									  coverageTargets.get(childPosition).getEventTargetStartDate(),//5
									  coverageTargets.get(childPosition).getEventTargetStatus(),//6
									  coverageTargets.get(childPosition).getEventTargetId(),//7
									  coverageTargets.get(childPosition).getEventTargetLastUpdated()//8
					  };
		}else if(groupPosition==2){
			childDetails=new String[]{learningTargets.get(childPosition).getLearningTargetName(),//0
									  learningTargets.get(childPosition).getLearningTargetPeriod(),//1
									  learningTargets.get(childPosition).getLearningTargetEndDate(),//2
									  learningTargets.get(childPosition).getLearningTargetStartDate(),//3
									  learningTargets.get(childPosition).getLearningTargetStatus(),//4
									  learningTargets.get(childPosition).getLearningTargetId(),//5
									  learningTargets.get(childPosition).getLearningTargetLastUpdated()//6
					  };
		}else if(groupPosition==3){
			childDetails=new String[]{otherTargets.get(childPosition).getEventTargetName(),//0
									  otherTargets.get(childPosition).getEventTargetNumber(),//1	
									  otherTargets.get(childPosition).getEventTargetPeriod(),//2
									  otherTargets.get(childPosition).getEventTargetEndDate(),//3
									  otherTargets.get(childPosition).getEventTargetNumberAchieved(),//4
									  otherTargets.get(childPosition).getEventTargetStartDate(),//5
									  otherTargets.get(childPosition).getEventTargetStatus(),//6
									  otherTargets.get(childPosition).getEventTargetId(),//7
									  otherTargets.get(childPosition).getEventTargetLastUpdated()//8				  
					  };
		}
		return childDetails;
	}
	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		long id=0;
		return id;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			   convertView = minflater.inflate(R.layout.listview_single,parent, false);
			  }
			   
			   TextView category=(TextView) convertView.findViewById(R.id.textView_textSingle);
			   category.setText(groupItems[groupPosition]);
			   
			   Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
		       	      "fonts/Roboto-Thin.ttf");
			   category.setTypeface(custom_font);
			  return convertView;
	}
	@Override
	public View getChildView(int groupPosition, int childPosition,
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
	   text.setText(eventTargets.get(childPosition).getEventTargetName());
	   text2.setText(eventTargets.get(childPosition).getEventTargetNumber());
	   if(eventTargets.get(childPosition).getEventTargetStatus().equalsIgnoreCase("updated")){
		   text3.setTextColor(Color.GREEN);
		   text3.setText("Completed");
	   }else{
		   text3.setTextColor(Color.RED);
		   text3.setText("Upcoming"); 
	   }
	   text4.setText(eventTargets.get(childPosition).getEventTargetEndDate());
	   text5.setText(eventTargets.get(childPosition).getEventTargetStartDate());
	   text7.setText(eventTargets.get(childPosition).getEventTargetLastUpdated());
	   int number_achieved_today=Integer.valueOf(eventTargets.get(childPosition).getEventTargetNumberAchieved());
		   Double percentage= ((double)number_achieved_today/Integer.valueOf(eventTargets.get(childPosition).getEventTargetNumber()))*100;	
		   String percentage_achieved=String.format("%.0f", percentage);
	  
		  text6.setText(eventTargets.get(childPosition).getEventTargetNumberAchieved());   
	   text8.setText(percentage_achieved+"%");
	   }else if(groupPosition==1){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_achieved);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   TextView text8=(TextView) convertView.findViewById(R.id.textView_percentageAchieved);
		   text.setText(coverageTargets.get(childPosition).getCoverageTargetName());
		   text2.setText(coverageTargets.get(childPosition).getCoverageTargetNumber());
		   if(coverageTargets.get(childPosition).getCoverageTargetStatus().equalsIgnoreCase("updated")){
			   text3.setTextColor(Color.GREEN);
			   text3.setText("Completed");
		   }else{
			   text3.setTextColor(Color.RED);
			   text3.setText("Upcoming"); 
		   }
		   text4.setText(coverageTargets.get(childPosition).getCoverageTargetEndDate());
		   text5.setText(coverageTargets.get(childPosition).getCoverageTargetStartDate());
		   text7.setText(coverageTargets.get(childPosition).getCoverageTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(coverageTargets.get(childPosition).getCoverageTargetNumberAchieved());
			   Double percentage= ((double)number_achieved_today/Integer.valueOf(coverageTargets.get(childPosition).getCoverageTargetNumber()))*100;	
			   String percentage_achieved=String.format("%.0f", percentage);
			  text6.setText(coverageTargets.get(childPosition).getCoverageTargetNumberAchieved());   
			  text8.setText(percentage_achieved+"%");
		   
	   }else if(groupPosition==2){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_achieved);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   TextView text8=(TextView) convertView.findViewById(R.id.textView_percentageAchieved);
		   text.setText(learningTargets.get(childPosition).getLearningTargetName());
		   text2.setText(" ");
		   if(learningTargets.get(childPosition).getLearningTargetStatus().equalsIgnoreCase("updated")){
			   text3.setTextColor(Color.GREEN);
			   text3.setText("Completed");
		   }else{
			   text3.setTextColor(Color.RED);
			   text3.setText("Upcoming"); 
		   }
		   text4.setText(learningTargets.get(childPosition).getLearningTargetEndDate());
		   text5.setText(learningTargets.get(childPosition).getLearningTargetStartDate());
		   text7.setText(learningTargets.get(childPosition).getLearningTargetLastUpdated());
		   text6.setText(" ");
		  text8.setText(" ");
	   }else if(groupPosition==3){
		   
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_achieved);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   TextView text8=(TextView) convertView.findViewById(R.id.textView_percentageAchieved);
		   text.setText(otherTargets.get(childPosition).getOtherTargetName());
		   text2.setText(otherTargets.get(childPosition).getOtherTargetNumber());
		   if(otherTargets.get(childPosition).getOtherTargetStatus().equalsIgnoreCase("updated")){
			   text3.setTextColor(Color.GREEN);
			   text3.setText("Completed");
		   }else{
			   text3.setTextColor(Color.RED);
			   text3.setText("Upcoming"); 
		   }
		   text4.setText(otherTargets.get(childPosition).getOtherTargetEndDate());
		   text5.setText(otherTargets.get(childPosition).getOtherTargetStartDate());
		   text7.setText(otherTargets.get(childPosition).getOtherTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(otherTargets.get(childPosition).getOtherTargetNumberAchieved());
			   Double percentage= ((double)number_achieved_today/Integer.valueOf(otherTargets.get(childPosition).getOtherTargetNumber()))*100;	
			   String percentage_achieved=String.format("%.0f", percentage);
			  text6.setText(otherTargets.get(childPosition).getOtherTargetNumberAchieved());   
			  text8.setText(percentage_achieved+"%");
	   }
	  
	  return convertView;
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

	/*
	public void onGroupExpanded(int groupPosition) {
   	
   	if(groupPosition != lastExpandedGroupPosition){
           event_list.collapseGroup(lastExpandedGroupPosition);
      
   }
   	
       super.onGroupExpanded(groupPosition);
    
       lastExpandedGroupPosition = groupPosition;
       
   }
*/
	}


