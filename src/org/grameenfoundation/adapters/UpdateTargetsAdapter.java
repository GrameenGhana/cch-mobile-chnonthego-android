package org.grameenfoundation.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;










import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.mobile.learningGF.R.color;

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
	 private final ArrayList<String> eventName;
	 private final ArrayList<String> eventNumber;
	 private final ArrayList<String> eventPeriod;
	 private final ArrayList<String> eventDueDate;
	 private final ArrayList<String> eventAchieved;
	 private final ArrayList<String> eventStartDate;
	 private final ArrayList<String> eventStatus;
	 private final ArrayList<String> eventId;
	 private final ArrayList<String> eventLastUpdated;
	// private final ArrayList<String> todayEventNumberRemaining;
	 
	 /*
	 private final ArrayList<String> tomorrowEventName;
	 private final ArrayList<String> tomorrowEventNumber;
	 private final ArrayList<String> tomorrowEventPeriod;
	 private final ArrayList<String> tomorrowEventDueDate;
	 private final ArrayList<String> tomorrowEventStartDate;
	 private final ArrayList<String> tomorrowEventStatus;
	 private final ArrayList<String> tomorrowEventId;
	 */
	 private final ArrayList<String> coverageName;
	 private final ArrayList<String> coverageNumber;
	 private final ArrayList<String> coveragePeriod;
	 private final ArrayList<String> coverageDueDate;
	 private final ArrayList<String> coverageAchieved;
	 private final ArrayList<String> coverageStartDate;
	 private final ArrayList<String> coverageStatus;
	 private final ArrayList<String> coverageId;
	 private final ArrayList<String> coverageLastUpdated;
	// private final ArrayList<String> thisWeekEventNumberRemaining;
	 
	 private final ArrayList<String> learningName;
	 //private final ArrayList<String> learningNumber;
	 private final ArrayList<String> learningPeriod;
	 private final ArrayList<String> learningDueDate;
	 //private final ArrayList<String> learningAchieved;
	 private final ArrayList<String> learningStartDate;
	 private final ArrayList<String> learningStatus;
	 private final ArrayList<String> learningId;
	 private final ArrayList<String> learningLastUpdated;
	// private final ArrayList<String> thisMonthEventNumberRemaining;
	 
	 private final ArrayList<String> otherName;
	 private final ArrayList<String> otherNumber;
	 private final ArrayList<String> otherPeriod;
	 private final ArrayList<String> otherDueDate;
	 private final ArrayList<String> otherAchieved;
	 private final ArrayList<String> otherStartDate;
	 private final ArrayList<String> otherStatus;
	 private final ArrayList<String> otherId;
	 private final ArrayList<String> otherLastUpdated;
	 //private final ArrayList<String> thisQuarterEventNumberRemaining;
	 
	 private String[] groupItems;
	 public LayoutInflater minflater;
	 ExpandableListView event_list;
	private int lastExpandedGroupPosition;
	 
	 
	 public UpdateTargetsAdapter(Context c,ArrayList<String> eventName ,
										ArrayList<String> eventNumber,
										ArrayList<String> eventPeriod,
										ArrayList<String> eventDueDate,
										ArrayList<String> eventAchieved,
										ArrayList<String> eventStartDate,
										ArrayList<String> eventStatus,
										ArrayList<String> eventId,
										ArrayList<String> eventLastUpdated,
										//ArrayList<String> todayEventNumberRemaining,
											
										/*
										ArrayList<String> tomorrowEventName,
										ArrayList<String> tomorrowEventNumber,
										ArrayList<String> tomorrowEventPeriod,
										ArrayList<String> tomorrowEventDueDate,
										ArrayList<String> tomorrowEventStartDate,
										ArrayList<String> tomorrowEventStatus,
										ArrayList<String> tomorrowEventId,
				*/
										ArrayList<String> coverageName,
										ArrayList<String> coverageNumber,
										ArrayList<String> coveragePeriod,
										ArrayList<String> coverageDueDate,
										ArrayList<String> coverageAchieved,
										ArrayList<String> coverageStartDate,
										ArrayList<String> coverageStatus,
										ArrayList<String> coverageId,
										ArrayList<String> coverageLastUpdated,
										//ArrayList<String> thisWeekEventNumberRemaining,
				
										ArrayList<String> learningName,
										//ArrayList<String> learningNumber,
										ArrayList<String> learningPeriod,
										ArrayList<String> learningDueDate,
										//ArrayList<String> learningAchieved,
										ArrayList<String> learningStartDate,
										ArrayList<String> learningStatus,
										ArrayList<String> learningId,
										ArrayList<String> learningLastUpdated,
										//ArrayList<String> thisMonthEventNumberRemaining,
				
										ArrayList<String> otherName,
										ArrayList<String> otherNumber,
										ArrayList<String> otherPeriod,
										ArrayList<String> otherDueDate,
										ArrayList<String> otherAchieved,
										ArrayList<String> otherStartDate,
										ArrayList<String> otherStatus,
										ArrayList<String> otherId,
										ArrayList<String> otherLastUpdated,
										//ArrayList<String> thisQuarterEventNumberRemaining,
				
										//ArrayList<String> thisYearEventNumberRemaining,
			 							String[] groupItems,
			 							ExpandableListView event_list
			 							) {
       mContext = c;
       this.eventName = eventName;
       this.eventNumber=eventNumber;
       this.eventPeriod=eventPeriod;
       this.eventDueDate=eventDueDate;
       this.eventAchieved=eventAchieved;
       this.eventStartDate=eventStartDate;
       this.eventStatus=eventStatus;
       this.eventId=eventId;
       this.eventLastUpdated=eventLastUpdated;
      // this.todayEventNumberRemaining=todayEventNumberRemaining;
       minflater = LayoutInflater.from(mContext);
       /*
       this.tomorrowEventName = tomorrowEventName;
       this.tomorrowEventNumber=tomorrowEventNumber;
       this.tomorrowEventPeriod=tomorrowEventPeriod;
       this.tomorrowEventDueDate=tomorrowEventDueDate;
       this.tomorrowEventStatus=tomorrowEventStatus;
       this.tomorrowEventId=tomorrowEventId;
       */
       this.coverageName = coverageName;
       this.coverageNumber=coverageNumber;
       this.coveragePeriod=coveragePeriod;
       this.coverageDueDate=coverageDueDate;
       this.coverageAchieved=coverageAchieved;
       this.coverageStartDate=coverageStartDate;
       this.coverageStatus=coverageStatus;
       this.coverageId=coverageId;
       this.coverageLastUpdated=coverageLastUpdated;
      // this.thisWeekEventNumberRemaining=thisWeekEventNumberRemaining;
       
       this.learningName = learningName;
      // this.learningNumber=learningNumber;
       this.learningPeriod=learningPeriod;
       this.learningDueDate=learningDueDate;
       //this.learningAchieved=learningAchieved;
       this.learningStartDate=learningStartDate;
       this.learningStatus=learningStatus;
       this.learningId=learningId;
       this.learningLastUpdated=learningLastUpdated;
       //this.thisMonthEventNumberRemaining=thisMonthEventNumberRemaining;
       
       this.otherName = otherName;
       this.otherNumber=otherNumber;
       this.otherPeriod=otherPeriod;
       this.otherDueDate=otherDueDate;
       this.otherAchieved=otherAchieved;
       this.otherStartDate=otherStartDate;
       this.otherStatus=otherStatus;
       this.otherId=otherId;
       this.otherLastUpdated=otherLastUpdated;
      // this.thisQuarterEventNumberRemaining=thisQuarterEventNumberRemaining;
       
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
			count=eventId.size();
		}else if(groupPosition==1){
			//count=tomorrowEventId.size();
			count=coverageId.size();
		}else if(groupPosition==2){
			count=learningId.size();
		}else if(groupPosition==3){
			count=otherId.size();
		
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
			childDetails=new String[]{eventName.get(childPosition),//0
									  eventNumber.get(childPosition),//1	
									  eventPeriod.get(childPosition),//2
									  eventDueDate.get(childPosition),//3
									  eventAchieved.get(childPosition),//4
									  eventStartDate.get(childPosition),//5
									  eventStatus.get(childPosition),//6
									  eventId.get(childPosition),//7
									  eventLastUpdated.get(childPosition)//8
									 // todayEventNumberRemaining.get(childPosition)
									  };
		}else if(groupPosition==1){
			childDetails=new String[]{coverageName.get(childPosition),
						coverageNumber.get(childPosition),
						coveragePeriod.get(childPosition),
						coverageDueDate.get(childPosition),
						coverageAchieved.get(childPosition),
						coverageStartDate.get(childPosition),
						coverageStatus.get(childPosition),
						coverageId.get(childPosition),
						coverageLastUpdated.get(childPosition)
					  };
		}else if(groupPosition==2){
			childDetails=new String[]{learningName.get(childPosition),//0
						//learningNumber.get(childPosition),
						learningPeriod.get(childPosition),//1
						learningDueDate.get(childPosition),//2
						//learningAchieved.get(childPosition),
						learningStartDate.get(childPosition),//3
						learningStatus.get(childPosition),//4
						learningId.get(childPosition),//5
						learningLastUpdated.get(childPosition)//6
					 // percentage_achieved_this_month																	
					  //thisMonthEventNumberRemaining.get(childPosition)
					  };
		}else if(groupPosition==3){
			childDetails=new String[]{otherName.get(childPosition),
						otherNumber.get(childPosition),
						otherPeriod.get(childPosition),
						otherDueDate.get(childPosition),
						otherAchieved.get(childPosition),
						otherStartDate.get(childPosition),
						otherStatus.get(childPosition),
						otherId.get(childPosition),
						otherLastUpdated.get(childPosition)					  
					  //thisQuarterEventNumberRemaining.get(childPosition)
					  };
		}
		return childDetails;
	}
	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		long id=0;
		/*
		if(groupPosition==0){
		if(!dailyEventId.equals(null)){
			id=Integer.valueOf(dailyEventId.get(childPosition));
		}
		}else if(groupPosition==1){
			if(!weeklyEventId.equals(null)){
			id=Integer.valueOf(weeklyEventId.get(childPosition));
			}
		}else if(groupPosition==2){
			if(!monthlyEventId.equals(null)){
			id=Integer.valueOf(monthlyEventId.get(childPosition));
			}
		}else if(groupPosition==3){
			if(!yearlyEventId.equals(null)){
			id=Integer.valueOf(yearlyEventId.get(childPosition));
			}
		}else if(groupPosition==4){
			if(!midYearEventId.equals(null)){
			id=Integer.valueOf(midYearEventId.get(childPosition));
			}
		}*/
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
	   text.setText(eventName.get(childPosition));
	   text2.setText(eventNumber.get(childPosition));
	   text3.setText(eventPeriod.get(childPosition));
	   text4.setText(eventDueDate.get(childPosition));
	   text5.setText(eventStartDate.get(childPosition));
	   text7.setText(eventLastUpdated.get(childPosition));
	   int number_achieved_today=Integer.valueOf(eventAchieved.get(childPosition));
	   System.out.println(String.valueOf(number_achieved_today));
		  // int number_remaining_today=Integer.valueOf(todayEventNumberRemaining.get(childPosition));
		   Double percentage= ((double)number_achieved_today/Integer.valueOf(eventNumber.get(childPosition)))*100;	
		   System.out.println(String.valueOf(percentage));
		   String percentage_achieved=String.format("%.0f", percentage);
	  
	   if(eventAchieved.isEmpty()){
	   text6.setText("0");
	   }else {
		  text6.setText(eventAchieved.get(childPosition));   
	   }
	   text8.setText(percentage_achieved+"%");
	   /*
	   if(!todayEventStatus.isEmpty()){
		   if(todayEventStatus!=null&&todayEventStatus.get(childPosition).equalsIgnoreCase("updated")){
		   image.setImageResource(R.drawable.ic_achieved);
	   }else if(todayEventStatus!=null&&todayEventStatus.get(childPosition).equalsIgnoreCase("new_record")){
		   image.setImageResource(R.drawable.ic_loading);
	   }else if(todayEventStatus!=null&&todayEventStatus.get(childPosition).equalsIgnoreCase("not_achieved")){
		   image.setImageResource(R.drawable.ic_not_achieved);
	   }
	   }*/
	   //text.setTypeface(custom_font);
	   //text2.setTypeface(custom_font);
	   //text3.setTypeface(custom_font);
	   }else if(groupPosition==1){
		  // if(thisWeekEventAchieved.size()>0&&thisWeekEventNumber.size()>0){
		
		   //}
			//int number_remaining_this_week=Integer.valueOf(thisWeekEventNumberRemaining.get(childPosition));
			
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_achieved);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   TextView text8=(TextView) convertView.findViewById(R.id.textView_percentageAchieved);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(coverageName.get(childPosition));
		   text2.setText(coverageNumber.get(childPosition));
		   text3.setText(coveragePeriod.get(childPosition));
		   text4.setText(coverageDueDate.get(childPosition));
		   text5.setText(coverageStartDate.get(childPosition));
		   text7.setText(coverageLastUpdated.get(childPosition));
		   int number_achieved_this_week=Integer.valueOf(coverageAchieved.get(childPosition));
		   Double percentage_this_week= ((double)number_achieved_this_week/Integer.valueOf(coverageNumber.get(childPosition)))*100;	
		   String percentage_achieved_this_week=String.format("%.0f", percentage_this_week);
		   if(coverageAchieved.size()<0){
		   text6.setText("0");
		   }else {
			  text6.setText(coverageAchieved.get(childPosition));   
		   }
		   text8.setText(percentage_achieved_this_week+"%");
		   /*
	   if(!tomorrowEventStatus.isEmpty()){
		   if(tomorrowEventStatus.get(childPosition).equalsIgnoreCase("updated")){
			   image.setImageResource(R.drawable.ic_achieved);
		   }else if(tomorrowEventStatus.get(childPosition).equalsIgnoreCase("new_record")){
			   image.setImageResource(R.drawable.ic_loading);
		   }else if(tomorrowEventStatus.get(childPosition).equalsIgnoreCase("not_achieved")){
			   image.setImageResource(R.drawable.ic_not_achieved);
		   }
		   }*/
		   //text.setTypeface(custom_font);
		   //text2.setTypeface(custom_font);
		   //text3.setTypeface(custom_font);
	   }else if(groupPosition==2){
		 //  if(thisMonthEventAchieved.size()>0&&thisMonthEventNumber.size()>0){
		  
			
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_achieved);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   TextView text8=(TextView) convertView.findViewById(R.id.textView_percentageAchieved);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(learningName.get(childPosition));
		   text2.setText(" ");
		   text3.setText(learningPeriod.get(childPosition));
		   text4.setText(learningDueDate.get(childPosition));
		   if(learningStartDate.size()>0){
		   text5.setText(learningStartDate.get(childPosition));
		   }
		   text7.setText(learningLastUpdated.get(childPosition));
		  
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
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(otherName.get(childPosition));
		   text2.setText(otherNumber.get(childPosition));
		   text3.setText(otherPeriod.get(childPosition));
		   text4.setText(otherDueDate.get(childPosition));
		   text5.setText(otherStartDate.get(childPosition));
		   text7.setText(otherLastUpdated.get(childPosition));
		  // text8.setText(percentage_achieved_this_quarter);
		   if(otherAchieved.isEmpty()){
		   text6.setText("0");
		   }else {
			  text6.setText(otherAchieved.get(childPosition));   
		   }
		   int number_achieved_this_quarter=Integer.valueOf(otherAchieved.get(childPosition));
			//int number_remaining_this_quarter=Integer.valueOf(thisMonthEventNumberRemaining.get(childPosition));
			Double percentage_this_quarter= ((double)number_achieved_this_quarter/Integer.valueOf(otherNumber.get(childPosition))) *100;	
			String percentage_achieved_this_quarter=String.format("%.0f", percentage_this_quarter);
		   text8.setText(percentage_achieved_this_quarter+"%");
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


