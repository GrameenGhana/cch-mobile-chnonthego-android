package org.grameenfoundation.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;












import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.mobile.learningGF.R.color;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.LearningTargets;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class UpdateTargetsAdapter extends BaseExpandableListAdapter{
	 private Context mContext;
	 private final ArrayList<EventTargets> eventTargets;
	 private final ArrayList<EventTargets> coverageTargets;
	 private final ArrayList<EventTargets> otherTargets;
	 private final ArrayList<LearningTargets> learningTargets;
	 private String[] groupItems;
	 public LayoutInflater minflater;
	 ExpandableListView event_list;
	private int lastExpandedGroupPosition;
	 
	 
	 public UpdateTargetsAdapter(Context c,ArrayList<EventTargets> EventTargets ,
										ArrayList<EventTargets> CoverageTargets,
										ArrayList<EventTargets> OtherTargets,
										ArrayList<LearningTargets> LearningTargets,
			 							String[] groupItems,
			 							ExpandableListView event_list
			 							) {
       mContext = c;
       		eventTargets = new ArrayList<EventTargets>();
       		coverageTargets = new ArrayList<EventTargets>();
       		otherTargets = new ArrayList<EventTargets>();
       		learningTargets=new ArrayList<LearningTargets>();
       		
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
			childDetails=new String[]{learningTargets.get(childPosition).getLearningTargetTopic(),//0
									  learningTargets.get(childPosition).getLearningTargetPeriod(),//1
									  learningTargets.get(childPosition).getLearningTargetEndDate(),//2
									  learningTargets.get(childPosition).getLearningTargetStartDate(),//3
									  learningTargets.get(childPosition).getLearningTargetStatus(),//4
									  learningTargets.get(childPosition).getLearningTargetId(),//5
									  learningTargets.get(childPosition).getLearningTargetLastUpdated(),//6
									  learningTargets.get(childPosition).getLearningTargetCourse()//7
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
			  return convertView;
	}
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
	   if(convertView==null){
		   convertView=minflater.inflate(R.layout.event_listview_single,null);
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
	   text3.setText(eventTargets.get(childPosition).getEventTargetPeriod());
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
		   text.setText(coverageTargets.get(childPosition).getEventTargetName());
		   text2.setText(coverageTargets.get(childPosition).getEventTargetNumber());
		   text3.setText(coverageTargets.get(childPosition).getEventTargetPeriod());
		   text4.setText(coverageTargets.get(childPosition).getEventTargetEndDate());
		   text5.setText(coverageTargets.get(childPosition).getEventTargetStartDate());
		   text7.setText(coverageTargets.get(childPosition).getEventTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(coverageTargets.get(childPosition).getEventTargetNumberAchieved());
			   Double percentage= ((double)number_achieved_today/Integer.valueOf(coverageTargets.get(childPosition).getEventTargetNumber()))*100;	
			   String percentage_achieved=String.format("%.0f", percentage);
			  text6.setText(coverageTargets.get(childPosition).getEventTargetNumberAchieved());   
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
		   text3.setText(learningTargets.get(childPosition).getLearningTargetPeriod());
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
		   text.setText(otherTargets.get(childPosition).getEventTargetName());
		   text2.setText(otherTargets.get(childPosition).getEventTargetNumber());
		   text3.setText(otherTargets.get(childPosition).getEventTargetPeriod());
		   text4.setText(otherTargets.get(childPosition).getEventTargetEndDate());
		   text5.setText(otherTargets.get(childPosition).getEventTargetStartDate());
		   text7.setText(otherTargets.get(childPosition).getEventTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(otherTargets.get(childPosition).getEventTargetNumberAchieved());
			   Double percentage= ((double)number_achieved_today/Integer.valueOf(otherTargets.get(childPosition).getEventTargetNumber()))*100;	
			   String percentage_achieved=String.format("%.0f", percentage);
			  text6.setText(otherTargets.get(childPosition).getEventTargetNumberAchieved());   
			  text8.setText(percentage_achieved+"%");
	   }
	  
	  return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}



	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	}


