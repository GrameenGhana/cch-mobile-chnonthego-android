package org.grameenfoundation.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;













import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.mobile.learningGF.R.color;
import org.digitalcampus.oppia.application.MobileLearning;
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
	 public ArrayList<EventTargets> DailyTargets;
	 public ArrayList<EventTargets> WeeklyTargets;
	 public ArrayList<EventTargets> MonthlyTargets;
	 public ArrayList<EventTargets> QuarterlyTargets;
	 public ArrayList<EventTargets> MidyearTargets;
	 public ArrayList<EventTargets> AnnualTargets;
	 private String[] groupItems;
	 public LayoutInflater minflater;
	 ExpandableListView event_list;
	private int lastExpandedGroupPosition;
	 
	 
	 public UpdateTargetsAdapter(Context c,ArrayList<EventTargets> dailyTargets ,
										ArrayList<EventTargets> weeklyTargets,
										ArrayList<EventTargets> monthlyTargets,
										ArrayList<EventTargets> quarterlyTargets,
										ArrayList<EventTargets> midyearTargets,
										 ArrayList<EventTargets> annualTargets,
			 							String[] groupItems,
			 							ExpandableListView event_list
			 							) {
		 	mContext = c;
       		DailyTargets = new ArrayList<EventTargets>();
       		WeeklyTargets = new ArrayList<EventTargets>();
       		MonthlyTargets = new ArrayList<EventTargets>();
       		QuarterlyTargets=new ArrayList<EventTargets>();
       		MidyearTargets=new ArrayList<EventTargets>();
       		AnnualTargets=new ArrayList<EventTargets>();
       		
       		DailyTargets.addAll(dailyTargets);
       		WeeklyTargets.addAll(weeklyTargets);
       		MonthlyTargets.addAll(monthlyTargets);
       		QuarterlyTargets.addAll(quarterlyTargets);
       		MidyearTargets.addAll(midyearTargets);
       		AnnualTargets.addAll(annualTargets);
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
			count=DailyTargets.size();
		}else if(groupPosition==1){
			count=WeeklyTargets.size();
		}else if(groupPosition==2){
			count=MonthlyTargets.size();
		}else if(groupPosition==3){
			count=QuarterlyTargets.size();
		}else if(groupPosition==4){
			count=MidyearTargets.size();
		}else if(groupPosition==5){
			count=AnnualTargets.size();
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
			childDetails=new String[]{DailyTargets.get(childPosition).getEventTargetName(),//0
									  DailyTargets.get(childPosition).getEventTargetNumber(),//1	
									  DailyTargets.get(childPosition).getEventTargetPeriod(),//2
									  DailyTargets.get(childPosition).getEventTargetEndDate(),//3
									  DailyTargets.get(childPosition).getEventTargetNumberAchieved(),//4
									  DailyTargets.get(childPosition).getEventTargetStartDate(),//5
									  DailyTargets.get(childPosition).getEventTargetStatus(),//6
									  DailyTargets.get(childPosition).getEventTargetId(),//7
									  DailyTargets.get(childPosition).getEventTargetLastUpdated(),//8
									  DailyTargets.get(childPosition).getEventTargetOldId(),//9
									  DailyTargets.get(childPosition).getEventTargetCategory(),//10
									  DailyTargets.get(childPosition).getEventTargetDetail()//11
									  };
		}else if(groupPosition==1){
			childDetails=new String[]{WeeklyTargets.get(childPosition).getEventTargetName(),//0
									  WeeklyTargets.get(childPosition).getEventTargetNumber(),//1	
									  WeeklyTargets.get(childPosition).getEventTargetPeriod(),//2
									  WeeklyTargets.get(childPosition).getEventTargetEndDate(),//3
									  WeeklyTargets.get(childPosition).getEventTargetNumberAchieved(),//4
									  WeeklyTargets.get(childPosition).getEventTargetStartDate(),//5
									  WeeklyTargets.get(childPosition).getEventTargetStatus(),//6
									  WeeklyTargets.get(childPosition).getEventTargetId(),//7
									  WeeklyTargets.get(childPosition).getEventTargetLastUpdated(),//8
									  WeeklyTargets.get(childPosition).getEventTargetOldId(),//9
									  WeeklyTargets.get(childPosition).getEventTargetCategory(),//10
									  WeeklyTargets.get(childPosition).getEventTargetDetail()//11
					  };
		}else if(groupPosition==2){
			childDetails=new String[]{MonthlyTargets.get(childPosition).getEventTargetName(),//0
									  MonthlyTargets.get(childPosition).getEventTargetNumber(),//1	
									  MonthlyTargets.get(childPosition).getEventTargetPeriod(),//2
									  MonthlyTargets.get(childPosition).getEventTargetEndDate(),//3
									  MonthlyTargets.get(childPosition).getEventTargetNumberAchieved(),//4
									  MonthlyTargets.get(childPosition).getEventTargetStartDate(),//5
									  MonthlyTargets.get(childPosition).getEventTargetStatus(),//6
									  MonthlyTargets.get(childPosition).getEventTargetId(),//7
									  MonthlyTargets.get(childPosition).getEventTargetLastUpdated(),//8
									  MonthlyTargets.get(childPosition).getEventTargetOldId(),//9
									  MonthlyTargets.get(childPosition).getEventTargetCategory(),//10
									  MonthlyTargets.get(childPosition).getEventTargetDetail()//11
					  };
		}else if(groupPosition==3){
			childDetails=new String[]{QuarterlyTargets.get(childPosition).getEventTargetName(),//0
									  QuarterlyTargets.get(childPosition).getEventTargetNumber(),//1	
									  QuarterlyTargets.get(childPosition).getEventTargetPeriod(),//2
									  QuarterlyTargets.get(childPosition).getEventTargetEndDate(),//3
									  QuarterlyTargets.get(childPosition).getEventTargetNumberAchieved(),//4
									  QuarterlyTargets.get(childPosition).getEventTargetStartDate(),//5
									  QuarterlyTargets.get(childPosition).getEventTargetStatus(),//6
									  QuarterlyTargets.get(childPosition).getEventTargetId(),//7
									  QuarterlyTargets.get(childPosition).getEventTargetLastUpdated(),//8
									  QuarterlyTargets.get(childPosition).getEventTargetOldId(),//9
									  QuarterlyTargets.get(childPosition).getEventTargetCategory(),//10
									  QuarterlyTargets.get(childPosition).getEventTargetDetail()//11
					  };
		}else if(groupPosition==4){
			childDetails=new String[]{MidyearTargets.get(childPosition).getEventTargetName(),//0
									  MidyearTargets.get(childPosition).getEventTargetNumber(),//1	
									  MidyearTargets.get(childPosition).getEventTargetPeriod(),//2
									  MidyearTargets.get(childPosition).getEventTargetEndDate(),//3
									  MidyearTargets.get(childPosition).getEventTargetNumberAchieved(),//4
									  MidyearTargets.get(childPosition).getEventTargetStartDate(),//5
									  MidyearTargets.get(childPosition).getEventTargetStatus(),//6
									  MidyearTargets.get(childPosition).getEventTargetId(),//7
									  MidyearTargets.get(childPosition).getEventTargetLastUpdated(),//8
									  MidyearTargets.get(childPosition).getEventTargetOldId(),//9
									  MidyearTargets.get(childPosition).getEventTargetCategory(),//10
									  MidyearTargets.get(childPosition).getEventTargetDetail()//11
	  };
}else if(groupPosition==5){
	childDetails=new String[]{AnnualTargets.get(childPosition).getEventTargetName(),//0
							  AnnualTargets.get(childPosition).getEventTargetNumber(),//1	
							  AnnualTargets.get(childPosition).getEventTargetPeriod(),//2
							  AnnualTargets.get(childPosition).getEventTargetEndDate(),//3
							  AnnualTargets.get(childPosition).getEventTargetNumberAchieved(),//4
							  AnnualTargets.get(childPosition).getEventTargetStartDate(),//5
							  AnnualTargets.get(childPosition).getEventTargetStatus(),//6
							  AnnualTargets.get(childPosition).getEventTargetId(),//7
							  AnnualTargets.get(childPosition).getEventTargetLastUpdated(),//8
							  AnnualTargets.get(childPosition).getEventTargetOldId(),//9
							  AnnualTargets.get(childPosition).getEventTargetCategory(),//10
							  AnnualTargets.get(childPosition).getEventTargetDetail()//11
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
	   if(DailyTargets.get(childPosition).getEventTargetType().equals(MobileLearning.CCH_TARGET_TYPE_LEARNING)){
		   text.setText(DailyTargets.get(childPosition).getEventTargetCategory());
	   }else if(DailyTargets.get(childPosition).getEventTargetType().equals(MobileLearning.CCH_TARGET_TYPE_COVERAGE)){
		   text.setText(DailyTargets.get(childPosition).getEventTargetDetail());
	   }else{
		   text.setText(DailyTargets.get(childPosition).getEventTargetName());
	   }
	   text2.setText(DailyTargets.get(childPosition).getEventTargetNumber());
	   text3.setText(DailyTargets.get(childPosition).getEventTargetPeriod());
	   text4.setText(DailyTargets.get(childPosition).getEventTargetEndDate());
	   text5.setText(DailyTargets.get(childPosition).getEventTargetStartDate());
	   text7.setText(DailyTargets.get(childPosition).getEventTargetLastUpdated());
	   int number_achieved_today=Integer.valueOf(DailyTargets.get(childPosition).getEventTargetNumberAchieved());
		   Double percentage= ((double)number_achieved_today/Integer.valueOf(DailyTargets.get(childPosition).getEventTargetNumber()))*100;	
		   String percentage_achieved=String.format("%.0f", percentage);
		  text6.setText(DailyTargets.get(childPosition).getEventTargetNumberAchieved());   
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
		   if(WeeklyTargets.get(childPosition).getEventTargetType().equals(MobileLearning.CCH_TARGET_TYPE_LEARNING)){
			   text.setText(WeeklyTargets.get(childPosition).getEventTargetCategory());
		   }else if(WeeklyTargets.get(childPosition).getEventTargetType().equals(MobileLearning.CCH_TARGET_TYPE_COVERAGE)){
			   text.setText(WeeklyTargets.get(childPosition).getEventTargetDetail());
		   }else{
			   text.setText(WeeklyTargets.get(childPosition).getEventTargetName());
		   }
		   text2.setText(WeeklyTargets.get(childPosition).getEventTargetNumber());
		   text3.setText(WeeklyTargets.get(childPosition).getEventTargetPeriod());
		   text4.setText(WeeklyTargets.get(childPosition).getEventTargetEndDate());
		   text5.setText(WeeklyTargets.get(childPosition).getEventTargetStartDate());
		   text7.setText(WeeklyTargets.get(childPosition).getEventTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(WeeklyTargets.get(childPosition).getEventTargetNumberAchieved());
			   Double percentage= ((double)number_achieved_today/Integer.valueOf(WeeklyTargets.get(childPosition).getEventTargetNumber()))*100;	
			   String percentage_achieved=String.format("%.0f", percentage);
			  text6.setText(WeeklyTargets.get(childPosition).getEventTargetNumberAchieved());   
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
		   if(MonthlyTargets.get(childPosition).getEventTargetType().equals(MobileLearning.CCH_TARGET_TYPE_LEARNING)){
			   text.setText(MonthlyTargets.get(childPosition).getEventTargetCategory());
		   }else if(MonthlyTargets.get(childPosition).getEventTargetType().equals(MobileLearning.CCH_TARGET_TYPE_COVERAGE)){
			   text.setText(MonthlyTargets.get(childPosition).getEventTargetDetail());
		   }else{
			   text.setText(MonthlyTargets.get(childPosition).getEventTargetName());
		   }
		   text2.setText(MonthlyTargets.get(childPosition).getEventTargetNumber());
		   text3.setText(MonthlyTargets.get(childPosition).getEventTargetPeriod());
		   text4.setText(MonthlyTargets.get(childPosition).getEventTargetEndDate());
		   text5.setText(MonthlyTargets.get(childPosition).getEventTargetStartDate());
		   text7.setText(MonthlyTargets.get(childPosition).getEventTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(MonthlyTargets.get(childPosition).getEventTargetNumberAchieved());
			   Double percentage= ((double)number_achieved_today/Integer.valueOf(MonthlyTargets.get(childPosition).getEventTargetNumber()))*100;	
			   String percentage_achieved=String.format("%.0f", percentage);
			  text6.setText(MonthlyTargets.get(childPosition).getEventTargetNumberAchieved());   
			  text8.setText(percentage_achieved+"%");
	   }else if(groupPosition==3){
		   
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_achieved);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   TextView text8=(TextView) convertView.findViewById(R.id.textView_percentageAchieved);
		   if(QuarterlyTargets.get(childPosition).getEventTargetType().equals(MobileLearning.CCH_TARGET_TYPE_LEARNING)){
			   text.setText(QuarterlyTargets.get(childPosition).getEventTargetCategory());
		   }else if(QuarterlyTargets.get(childPosition).getEventTargetType().equals(MobileLearning.CCH_TARGET_TYPE_COVERAGE)){
			   text.setText(QuarterlyTargets.get(childPosition).getEventTargetDetail());
		   }else{
			   text.setText(QuarterlyTargets.get(childPosition).getEventTargetName());
		   }
		   text2.setText(QuarterlyTargets.get(childPosition).getEventTargetNumber());
		   text3.setText(QuarterlyTargets.get(childPosition).getEventTargetPeriod());
		   text4.setText(QuarterlyTargets.get(childPosition).getEventTargetEndDate());
		   text5.setText(QuarterlyTargets.get(childPosition).getEventTargetStartDate());
		   text7.setText(QuarterlyTargets.get(childPosition).getEventTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(QuarterlyTargets.get(childPosition).getEventTargetNumberAchieved());
			   Double percentage= ((double)number_achieved_today/Integer.valueOf(QuarterlyTargets.get(childPosition).getEventTargetNumber()))*100;	
			   String percentage_achieved=String.format("%.0f", percentage);
			  text6.setText(QuarterlyTargets.get(childPosition).getEventTargetNumberAchieved());   
			  text8.setText(percentage_achieved+"%");
	   }else if(groupPosition==4){
		   
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_achieved);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   TextView text8=(TextView) convertView.findViewById(R.id.textView_percentageAchieved);
		   if(MidyearTargets.get(childPosition).getEventTargetType().equals(MobileLearning.CCH_TARGET_TYPE_LEARNING)){
			   text.setText(MidyearTargets.get(childPosition).getEventTargetCategory());
		   }else if(MidyearTargets.get(childPosition).getEventTargetType().equals(MobileLearning.CCH_TARGET_TYPE_COVERAGE)){
			   text.setText(MidyearTargets.get(childPosition).getEventTargetDetail());
		   }else{
			   text.setText(MidyearTargets.get(childPosition).getEventTargetName());
		   }
		   text2.setText(MidyearTargets.get(childPosition).getEventTargetNumber());
		   text3.setText(MidyearTargets.get(childPosition).getEventTargetPeriod());
		   text4.setText(MidyearTargets.get(childPosition).getEventTargetEndDate());
		   text5.setText(MidyearTargets.get(childPosition).getEventTargetStartDate());
		   text7.setText(MidyearTargets.get(childPosition).getEventTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(MidyearTargets.get(childPosition).getEventTargetNumberAchieved());
			   Double percentage= ((double)number_achieved_today/Integer.valueOf(MidyearTargets.get(childPosition).getEventTargetNumber()))*100;	
			   String percentage_achieved=String.format("%.0f", percentage);
			  text6.setText(MidyearTargets.get(childPosition).getEventTargetNumberAchieved());   
			  text8.setText(percentage_achieved+"%");
	   }else if(groupPosition==5){
		   
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_achieved);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   TextView text8=(TextView) convertView.findViewById(R.id.textView_percentageAchieved);
		   if(AnnualTargets.get(childPosition).getEventTargetType().equals(MobileLearning.CCH_TARGET_TYPE_LEARNING)){
			   text.setText(AnnualTargets.get(childPosition).getEventTargetCategory());
		   }else if(AnnualTargets.get(childPosition).getEventTargetType().equals(MobileLearning.CCH_TARGET_TYPE_COVERAGE)){
			   text.setText(AnnualTargets.get(childPosition).getEventTargetDetail());
		   }else{
			   text.setText(AnnualTargets.get(childPosition).getEventTargetName());
		   }
		   text2.setText(AnnualTargets.get(childPosition).getEventTargetNumber());
		   text3.setText(AnnualTargets.get(childPosition).getEventTargetPeriod());
		   text4.setText(AnnualTargets.get(childPosition).getEventTargetEndDate());
		   text5.setText(AnnualTargets.get(childPosition).getEventTargetStartDate());
		   text7.setText(AnnualTargets.get(childPosition).getEventTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(AnnualTargets.get(childPosition).getEventTargetNumberAchieved());
			   Double percentage= ((double)number_achieved_today/Integer.valueOf(AnnualTargets.get(childPosition).getEventTargetNumber()))*100;	
			   String percentage_achieved=String.format("%.0f", percentage);
			  text6.setText(AnnualTargets.get(childPosition).getEventTargetNumberAchieved());   
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


