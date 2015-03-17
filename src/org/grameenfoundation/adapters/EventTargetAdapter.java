package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.MyCalendarEvents;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class EventTargetAdapter extends BaseExpandableListAdapter{

	 public String[] groupItem;
	 public ArrayList<EventTargets> DailyEventTargets;
	 public ArrayList<EventTargets> WeeklyEventTargets;
	 public ArrayList<EventTargets> MonthlyEventTargets;
	 public ArrayList<EventTargets> QuarterlyEventTargets;
	 public ArrayList<EventTargets> MidyearEventTargets;
	 public ArrayList<EventTargets> AnnualEventTargets;
	 public ExpandableListView eventsList;
	 public LayoutInflater minflater;
	 private int count;
	 public int lastExpandedGroupPosition;    
	 private Context mContext;
	private DbHelper dbh;
	private ArrayList<EventTargets> dailyEventTarget;
	private ArrayList<EventTargets> weeklyEventTargets;
	private ArrayList<EventTargets> monthlyEventTargets;
	private ArrayList<EventTargets> quarterlyEventTargets;
	private ArrayList<EventTargets> midYearEventTargets;
	private ArrayList<EventTargets> annualEventTargets;
	EventTargets calendarEvents=new EventTargets();

	 public EventTargetAdapter(Context mContext,String[] grList,
			 					ArrayList<EventTargets> DailyEventTargets,
			 					ArrayList<EventTargets> WeeklyEventTargets,
			 					ArrayList<EventTargets> MonthlyEventTargets,
			 					ArrayList<EventTargets> QuarterlyEventTargets,
			 					ArrayList<EventTargets> MidyearEventTargets,
			 					ArrayList<EventTargets> AnnualEventTargets,
			 					ExpandableListView eventsList) {
		 dbh = new DbHelper(mContext);
		 
		 dailyEventTarget = new ArrayList<EventTargets>();
		 weeklyEventTargets = new ArrayList<EventTargets>();
		 monthlyEventTargets = new ArrayList<EventTargets>();
		 quarterlyEventTargets=new ArrayList<EventTargets>();
		 midYearEventTargets = new ArrayList<EventTargets>();
		 annualEventTargets = new ArrayList<EventTargets>();
		 
		 dailyEventTarget.addAll(DailyEventTargets);
		 weeklyEventTargets.addAll(WeeklyEventTargets);
		 monthlyEventTargets.addAll(MonthlyEventTargets);
		 quarterlyEventTargets.addAll(QuarterlyEventTargets);
		 midYearEventTargets.addAll(MidyearEventTargets);
		 annualEventTargets.addAll(AnnualEventTargets);
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
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(dailyEventTarget.get(childPosition).getEventTargetName());
		   text2.setText(dailyEventTarget.get(childPosition).getEventTargetNumber());
		   text3.setText(dailyEventTarget.get(childPosition).getEventTargetPeriod());
		   text4.setText(dailyEventTarget.get(childPosition).getEventTargetEndDate());
		   text5.setText(dailyEventTarget.get(childPosition).getEventTargetStartDate());
		   text7.setText(dailyEventTarget.get(childPosition).getEventTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(dailyEventTarget.get(childPosition).getEventTargetNumberAchieved());
		   Double percentage= ((double)number_achieved_today/Integer.valueOf(dailyEventTarget.get(childPosition).getEventTargetNumber()))*100;	
		   String percentage_achieved=String.format("%.0f", percentage);
		   text6.setText(dailyEventTarget.get(childPosition).getEventTargetNumberAchieved());   
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
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(weeklyEventTargets.get(childPosition).getEventTargetName());
		   text2.setText(weeklyEventTargets.get(childPosition).getEventTargetNumber());
		   text3.setText(weeklyEventTargets.get(childPosition).getEventTargetPeriod());
		   text4.setText(weeklyEventTargets.get(childPosition).getEventTargetEndDate());
		   text5.setText(weeklyEventTargets.get(childPosition).getEventTargetStartDate());
		   text7.setText(weeklyEventTargets.get(childPosition).getEventTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(weeklyEventTargets.get(childPosition).getEventTargetNumberAchieved());
		   Double percentage= ((double)number_achieved_today/Integer.valueOf(weeklyEventTargets.get(childPosition).getEventTargetNumber()))*100;	
		   String percentage_achieved=String.format("%.0f", percentage);
		   text6.setText(weeklyEventTargets.get(childPosition).getEventTargetNumberAchieved());   
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
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(monthlyEventTargets.get(childPosition).getEventTargetName());
		   text2.setText(monthlyEventTargets.get(childPosition).getEventTargetNumber());
		   text3.setText(monthlyEventTargets.get(childPosition).getEventTargetPeriod());
		   text4.setText(monthlyEventTargets.get(childPosition).getEventTargetEndDate());
		   text5.setText(monthlyEventTargets.get(childPosition).getEventTargetStartDate());
		   text7.setText(monthlyEventTargets.get(childPosition).getEventTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(monthlyEventTargets.get(childPosition).getEventTargetNumberAchieved());
		   Double percentage= ((double)number_achieved_today/Integer.valueOf(monthlyEventTargets.get(childPosition).getEventTargetNumber()))*100;	
		   String percentage_achieved=String.format("%.0f", percentage);
		   text6.setText(monthlyEventTargets.get(childPosition).getEventTargetNumberAchieved());   
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
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(quarterlyEventTargets.get(childPosition).getEventTargetName());
		   text2.setText(quarterlyEventTargets.get(childPosition).getEventTargetNumber());
		   text3.setText(quarterlyEventTargets.get(childPosition).getEventTargetPeriod());
		   text4.setText(quarterlyEventTargets.get(childPosition).getEventTargetEndDate());
		   text5.setText(quarterlyEventTargets.get(childPosition).getEventTargetStartDate());
		   text7.setText(quarterlyEventTargets.get(childPosition).getEventTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(quarterlyEventTargets.get(childPosition).getEventTargetNumberAchieved());
		   Double percentage= ((double)number_achieved_today/Integer.valueOf(quarterlyEventTargets.get(childPosition).getEventTargetNumber()))*100;	
		   String percentage_achieved=String.format("%.0f", percentage);
		   text6.setText(quarterlyEventTargets.get(childPosition).getEventTargetNumberAchieved());   
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
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(midYearEventTargets.get(childPosition).getEventTargetName());
		   text2.setText(midYearEventTargets.get(childPosition).getEventTargetNumber());
		   text3.setText(midYearEventTargets.get(childPosition).getEventTargetPeriod());
		   text4.setText(midYearEventTargets.get(childPosition).getEventTargetEndDate());
		   text5.setText(midYearEventTargets.get(childPosition).getEventTargetStartDate());
		   text7.setText(midYearEventTargets.get(childPosition).getEventTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(midYearEventTargets.get(childPosition).getEventTargetNumberAchieved());
		   Double percentage= ((double)number_achieved_today/Integer.valueOf(midYearEventTargets.get(childPosition).getEventTargetNumber()))*100;	
		   String percentage_achieved=String.format("%.0f", percentage);
		   text6.setText(midYearEventTargets.get(childPosition).getEventTargetNumberAchieved());   
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
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(annualEventTargets.get(childPosition).getEventTargetName());
		   text2.setText(annualEventTargets.get(childPosition).getEventTargetNumber());
		   text3.setText(annualEventTargets.get(childPosition).getEventTargetPeriod());
		   text4.setText(annualEventTargets.get(childPosition).getEventTargetEndDate());
		   text5.setText(annualEventTargets.get(childPosition).getEventTargetStartDate());
		   text7.setText(annualEventTargets.get(childPosition).getEventTargetLastUpdated());
		   int number_achieved_today=Integer.valueOf(annualEventTargets.get(childPosition).getEventTargetNumberAchieved());
		   Double percentage= ((double)number_achieved_today/Integer.valueOf(annualEventTargets.get(childPosition).getEventTargetNumber()))*100;	
		   String percentage_achieved=String.format("%.0f", percentage);
		   text6.setText(annualEventTargets.get(childPosition).getEventTargetNumberAchieved());   
		   text8.setText(percentage_achieved+"%");
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
		 if( convertView == null ){
		      
			  convertView = minflater.inflate(R.layout.listview_single,parent, false);
		    }
		 TextView text=(TextView) convertView.findViewById(R.id.textView_textSingle);
		 text.setText(groupItem[groupPosition]);
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
			count=dailyEventTarget.size();
		}else if(groupPosition==1){
			//count=tomorrowEventId.size();
			count=weeklyEventTargets.size();
		}else if(groupPosition==2){
			count=monthlyEventTargets.size();
		}else if(groupPosition==3){
			count=quarterlyEventTargets.size();
		}else if(groupPosition==4){
			count=midYearEventTargets.size();
		}else if(groupPosition==5){
			count=annualEventTargets.size();
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
		}
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

