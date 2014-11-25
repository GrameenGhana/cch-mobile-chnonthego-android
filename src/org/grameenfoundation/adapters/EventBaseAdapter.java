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

public class EventBaseAdapter extends BaseExpandableListAdapter{
	 private Context mContext;
	 private final ArrayList<String> todayEventName;
	 private final ArrayList<String> todayEventNumber;
	 private final ArrayList<String> todayEventPeriod;
	 private final ArrayList<String> todayEventDueDate;
	 private final ArrayList<String> todayEventAchieved;
	 private final ArrayList<String> todayEventStartDate;
	 private final ArrayList<String> todayEventStatus;
	 private final ArrayList<String> todayEventId;
	 private final ArrayList<String> todayEventLastUpdated;
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
	 private final ArrayList<String> thisWeekEventName;
	 private final ArrayList<String> thisWeekEventNumber;
	 private final ArrayList<String> thisWeekEventPeriod;
	 private final ArrayList<String> thisWeekEventDueDate;
	 private final ArrayList<String> thisWeekEventAchieved;
	 private final ArrayList<String> thisWeekEventStartDate;
	 private final ArrayList<String> thisWeekEventStatus;
	 private final ArrayList<String> thisWeekEventId;
	 private final ArrayList<String> thisWeekEventLastUpdated;
	// private final ArrayList<String> thisWeekEventNumberRemaining;
	 
	 private final ArrayList<String> thisMonthEventName;
	 private final ArrayList<String> thisMonthEventNumber;
	 private final ArrayList<String> thisMonthEventPeriod;
	 private final ArrayList<String> thisMonthEventDueDate;
	 private final ArrayList<String> thisMonthEventAchieved;
	 private final ArrayList<String> thisMonthEventStartDate;
	 private final ArrayList<String> thisMonthEventStatus;
	 private final ArrayList<String> thisMonthEventId;
	 private final ArrayList<String> thisMonthEventLastUpdated;
	// private final ArrayList<String> thisMonthEventNumberRemaining;
	 
	 private final ArrayList<String> thisQuarterEventName;
	 private final ArrayList<String> thisQuarterEventNumber;
	 private final ArrayList<String> thisQuarterEventPeriod;
	 private final ArrayList<String> thisQuarterEventDueDate;
	 private final ArrayList<String> thisQuarterEventAchieved;
	 private final ArrayList<String> thisQuarterEventStartDate;
	 private final ArrayList<String> thisQuarterEventStatus;
	 private final ArrayList<String> thisQuarterEventId;
	 private final ArrayList<String> thisQuarterEventLastUpdated;
	 //private final ArrayList<String> thisQuarterEventNumberRemaining;
	 
	 private final ArrayList<String> midYearEventName;
	 private final ArrayList<String> midYearEventNumber;
	 private final ArrayList<String> midYearEventPeriod;
	 private final ArrayList<String> midYearEventDueDate;
	 private final ArrayList<String> midYearEventAchieved;
	 private final ArrayList<String> midYearEventStartDate;
	 private final ArrayList<String> midYearEventStatus;
	 private final ArrayList<String> midYearEventId;
	 private final ArrayList<String> midYearEventLastUpdated;
	// private final ArrayList<String> midYearEventNumberRemaining;
	 
	 private final ArrayList<String> thisYearEventName;
	 private final ArrayList<String> thisYearEventNumber;
	 private final ArrayList<String> thisYearEventPeriod;
	 private final ArrayList<String> thisYearEventDueDate;
	 private final ArrayList<String> thisYearEventAchieved;
	 private final ArrayList<String> thisYearEventStartDate;
	 private final ArrayList<String> thisYearEventStatus;
	 private final ArrayList<String> thisYearEventId;
	 private final ArrayList<String> thisYearEventLastUpdated;
	// private final ArrayList<String> thisYearEventNumberRemaining;
	 private String[] groupItems;
	 public LayoutInflater minflater;
	 ExpandableListView event_list;
	private int lastExpandedGroupPosition;
	 
	 
	 public EventBaseAdapter(Context c,ArrayList<String> todayEventName ,
										ArrayList<String> todayEventNumber,
										ArrayList<String> todayEventPeriod,
										ArrayList<String> todayEventDueDate,
										ArrayList<String> todayEventAchieved,
										ArrayList<String> todayEventStartDate,
										ArrayList<String> todayEventStatus,
										ArrayList<String> todayEventId,
										ArrayList<String> todayEventLastUpdated,
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
										ArrayList<String> thisWeekEventName,
										ArrayList<String> thisWeekEventNumber,
										ArrayList<String> thisWeekEventPeriod,
										ArrayList<String> thisWeekEventDueDate,
										ArrayList<String> thisWeekEventAchieved,
										ArrayList<String> thisWeekEventStartDate,
										ArrayList<String> thisWeekEventStatus,
										ArrayList<String> thisWeekEventId,
										ArrayList<String> thisWeekEventLastUpdated,
										//ArrayList<String> thisWeekEventNumberRemaining,
				
										ArrayList<String> thisMonthEventName,
										ArrayList<String> thisMonthEventNumber,
										ArrayList<String> thisMonthEventPeriod,
										ArrayList<String> thisMonthEventDueDate,
										ArrayList<String> thisMonthEventAchieved,
										ArrayList<String> thisMonthEventStartDate,
										ArrayList<String> thisMonthEventStatus,
										ArrayList<String> thisMonthEventId,
										ArrayList<String> thisMonthEventLastUpdated,
										//ArrayList<String> thisMonthEventNumberRemaining,
				
										ArrayList<String> thisQuarterEventName,
										ArrayList<String> thisQuarterEventNumber,
										ArrayList<String> thisQuarterEventPeriod,
										ArrayList<String> thisQuarterEventDueDate,
										ArrayList<String> thisQuarterEventAchieved,
										ArrayList<String> thisQuarterEventStartDate,
										ArrayList<String> thisQuarterEventStatus,
										ArrayList<String> thisQuarterEventId,
										ArrayList<String> thisQuarterEventLastUpdated,
										//ArrayList<String> thisQuarterEventNumberRemaining,
				
										ArrayList<String> midYearEventName,
										ArrayList<String> midYearEventNumber,
										ArrayList<String> midYearEventPeriod,
										ArrayList<String> midYearEventDueDate,
										ArrayList<String> midYearEventAchieved,
										ArrayList<String> midYearEventStartDate,
										ArrayList<String> midYearEventStatus,
										ArrayList<String> midYearEventId,
										ArrayList<String> midYearEventLastUpdated,
										//ArrayList<String> midYearEventNumberRemaining,
				
										ArrayList<String> thisYearEventName,
										ArrayList<String> thisYearEventNumber,
										ArrayList<String> thisYearEventPeriod,
										ArrayList<String> thisYearEventDueDate,
										ArrayList<String> thisYearEventAchieved,
										ArrayList<String> thisYearEventStartDate,
										ArrayList<String> thisYearEventStatus,
										ArrayList<String> thisYearEventId,
										ArrayList<String> thisYearEventLastUpdated,
										//ArrayList<String> thisYearEventNumberRemaining,
			 							String[] groupItems,
			 							ExpandableListView event_list
			 							) {
       mContext = c;
       this.todayEventName = todayEventName;
       this.todayEventNumber=todayEventNumber;
       this.todayEventPeriod=todayEventPeriod;
       this.todayEventDueDate=todayEventDueDate;
       this.todayEventAchieved=todayEventAchieved;
       this.todayEventStartDate=todayEventStartDate;
       this.todayEventStatus=todayEventStatus;
       this.todayEventId=todayEventId;
       this.todayEventLastUpdated=todayEventLastUpdated;
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
       this.thisWeekEventName = thisWeekEventName;
       this.thisWeekEventNumber=thisWeekEventNumber;
       this.thisWeekEventPeriod=thisWeekEventPeriod;
       this.thisWeekEventDueDate=thisWeekEventDueDate;
       this.thisWeekEventAchieved=thisWeekEventAchieved;
       this.thisWeekEventStartDate=thisWeekEventStartDate;
       this.thisWeekEventStatus=thisWeekEventStatus;
       this.thisWeekEventId=thisWeekEventId;
       this.thisWeekEventLastUpdated=thisWeekEventLastUpdated;
      // this.thisWeekEventNumberRemaining=thisWeekEventNumberRemaining;
       
       this.thisMonthEventName = thisMonthEventName;
       this.thisMonthEventNumber=thisMonthEventNumber;
       this.thisMonthEventPeriod=thisMonthEventPeriod;
       this.thisMonthEventDueDate=thisMonthEventDueDate;
       this.thisMonthEventAchieved=thisMonthEventAchieved;
       this.thisMonthEventStartDate=thisMonthEventStartDate;
       this.thisMonthEventStatus=thisMonthEventStatus;
       this.thisMonthEventId=thisMonthEventId;
       this.thisMonthEventLastUpdated=thisMonthEventLastUpdated;
       //this.thisMonthEventNumberRemaining=thisMonthEventNumberRemaining;
       
       this.thisQuarterEventName = thisQuarterEventName;
       this.thisQuarterEventNumber=thisQuarterEventNumber;
       this.thisQuarterEventPeriod=thisQuarterEventPeriod;
       this.thisQuarterEventDueDate=thisQuarterEventDueDate;
       this.thisQuarterEventAchieved=thisQuarterEventAchieved;
       this.thisQuarterEventStartDate=thisQuarterEventStartDate;
       this.thisQuarterEventStatus=thisQuarterEventStatus;
       this.thisQuarterEventId=thisQuarterEventId;
       this.thisQuarterEventLastUpdated=thisQuarterEventLastUpdated;
      // this.thisQuarterEventNumberRemaining=thisQuarterEventNumberRemaining;
       
       this.midYearEventName = midYearEventName;
       this.midYearEventNumber=midYearEventNumber;
       this.midYearEventPeriod=midYearEventPeriod;
       this.midYearEventDueDate=midYearEventDueDate;
       this.midYearEventAchieved=midYearEventAchieved;
       this.midYearEventStartDate=midYearEventStartDate;
       this.midYearEventStatus=midYearEventStatus;
       this.midYearEventId=midYearEventId;
       this.midYearEventLastUpdated=midYearEventLastUpdated;
      // this.midYearEventNumberRemaining=midYearEventNumberRemaining;
       
       this.thisYearEventName = thisYearEventName;
       this.thisYearEventNumber=thisYearEventNumber;
       this.thisYearEventPeriod=thisYearEventPeriod;
       this.thisYearEventDueDate=thisYearEventDueDate;
       this.thisYearEventAchieved=thisYearEventAchieved;
       this.thisYearEventStartDate=thisYearEventStartDate;
       this.thisYearEventStatus=thisYearEventStatus;
       this.thisYearEventId=thisYearEventId;
       this.thisYearEventLastUpdated=thisYearEventLastUpdated;
       //this.thisYearEventNumberRemaining=thisYearEventNumberRemaining;
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
			count=todayEventId.size();
		}else if(groupPosition==1){
			//count=tomorrowEventId.size();
			count=thisWeekEventId.size();
		}else if(groupPosition==2){
			count=thisMonthEventId.size();
		}else if(groupPosition==3){
			count=thisQuarterEventId.size();
		}else if(groupPosition==4){
			count=midYearEventId.size();
		}else if(groupPosition==5){
			count=thisYearEventId.size();
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
			int number_achieved_today=Integer.valueOf(todayEventAchieved.get(childPosition));
			//int number_remaining_today=Integer.valueOf(todayEventNumberRemaining.get(childPosition));
			Double percentage= ((double)number_achieved_today/Integer.valueOf(todayEventNumber.get(childPosition)))*100;	
			String percentage_achieved=String.format("%.1f", percentage);
			childDetails=new String[]{todayEventName.get(childPosition),
									  todayEventNumber.get(childPosition),
									  todayEventPeriod.get(childPosition),
									  todayEventDueDate.get(childPosition),
									  todayEventAchieved.get(childPosition),
									  todayEventStartDate.get(childPosition),
									  todayEventStatus.get(childPosition),
									  todayEventId.get(childPosition),
									  todayEventLastUpdated.get(childPosition),
									  percentage_achieved
									 // todayEventNumberRemaining.get(childPosition)
									  };
		}else if(groupPosition==1){
			/*
			childDetails=new String[]{tomorrowEventName.get(childPosition),
									  tomorrowEventNumber.get(childPosition),
									  tomorrowEventPeriod.get(childPosition),
									  tomorrowEventDueDate.get(childPosition),
									  tomorrowEventStatus.get(childPosition),
									  tomorrowEventId.get(childPosition)};
									  */
			int number_achieved_this_week=Integer.valueOf(thisWeekEventAchieved.get(childPosition));
			//int number_remaining_this_week=Integer.valueOf(thisWeekEventNumberRemaining.get(childPosition));
			Double percentage_this_week= ((double)number_achieved_this_week/Integer.valueOf(thisWeekEventNumber.get(childPosition))) *100;	
			String percentage_achieved_this_week=String.format("%.1f", percentage_this_week);
			childDetails=new String[]{thisWeekEventName.get(childPosition),
					  thisWeekEventNumber.get(childPosition),
					  thisWeekEventPeriod.get(childPosition),
					  thisWeekEventDueDate.get(childPosition),
					  thisWeekEventAchieved.get(childPosition),
					  thisWeekEventStartDate.get(childPosition),
					  thisWeekEventStatus.get(childPosition),
					  thisWeekEventId.get(childPosition),
					  thisWeekEventLastUpdated.get(childPosition),
					  percentage_achieved_this_week
					 //thisWeekEventNumberRemaining.get(childPosition)
					  };
		}else if(groupPosition==2){
			//int number_achieved_this_month=Integer.valueOf(thisMonthEventAchieved.get(childPosition));
			//int number_remaining_this_month=Integer.valueOf(thisMonthEventNumberRemaining.get(childPosition));
			//Double percentage_this_month= ((double)number_achieved_this_month/Integer.valueOf(thisMonthEventNumber.get(childPosition)))*100;	
			//String percentage_achieved_this_month=String.format("%.1f", percentage_this_month);
			childDetails=new String[]{thisMonthEventName.get(childPosition),
					  thisMonthEventNumber.get(childPosition),
					  thisMonthEventPeriod.get(childPosition),
					  thisMonthEventDueDate.get(childPosition),
					  thisMonthEventAchieved.get(childPosition),
					  thisMonthEventStartDate.get(childPosition),
					  thisMonthEventStatus.get(childPosition),
					  thisMonthEventId.get(childPosition),
					  thisMonthEventLastUpdated.get(childPosition)
					 // percentage_achieved_this_month																	
					  //thisMonthEventNumberRemaining.get(childPosition)
					  };
		}else if(groupPosition==3){
			int number_achieved_this_quarter=Integer.valueOf(thisQuarterEventAchieved.get(childPosition));
			//int number_remaining_this_quarter=Integer.valueOf(thisMonthEventNumberRemaining.get(childPosition));
			Double percentage_this_quarter= ((double)number_achieved_this_quarter/Integer.valueOf(thisQuarterEventNumber.get(childPosition))) *100;	
			String percentage_achieved_this_quarter=String.format("%.1f", percentage_this_quarter);
			childDetails=new String[]{thisQuarterEventName.get(childPosition),
					  thisQuarterEventNumber.get(childPosition),
					  thisQuarterEventPeriod.get(childPosition),
					  thisQuarterEventDueDate.get(childPosition),
					  thisQuarterEventAchieved.get(childPosition),
					  thisQuarterEventStartDate.get(childPosition),
					  thisQuarterEventStatus.get(childPosition),
					  thisQuarterEventId.get(childPosition),
					  thisQuarterEventLastUpdated.get(childPosition),
					  percentage_achieved_this_quarter
					  //thisQuarterEventNumberRemaining.get(childPosition)
					  };
		}else if(groupPosition==4){
			int number_achieved_this_midYear=Integer.valueOf(midYearEventAchieved.get(childPosition));
			//int number_remaining_this_midYear=Integer.valueOf(midYearEventNumberRemaining.get(childPosition));
			Double percentage_this_midYear= ((double)number_achieved_this_midYear/Integer.valueOf(midYearEventNumber.get(childPosition))) *100;	
			String percentage_achieved_this_midYear=String.format("%.1f", percentage_this_midYear);
			childDetails=new String[]{midYearEventName.get(childPosition),
					 midYearEventNumber.get(childPosition),
					 midYearEventPeriod.get(childPosition),
					 midYearEventDueDate.get(childPosition),
					 midYearEventAchieved.get(childPosition),
					 midYearEventStartDate.get(childPosition),
					 midYearEventStatus.get(childPosition),
					 midYearEventId.get(childPosition),
					 midYearEventLastUpdated.get(childPosition),
					 percentage_achieved_this_midYear
					// midYearEventNumberRemaining.get(childPosition)
					 };
		}else if(groupPosition==5){
			int number_achieved_thisYear=Integer.valueOf(thisYearEventAchieved.get(childPosition));
			//int number_remaining_thisYear=Integer.valueOf(thisYearEventNumberRemaining.get(childPosition));
			Double percentage_thisYear= ((double)number_achieved_thisYear/Integer.valueOf(midYearEventNumber.get(childPosition)) )*100;	
			String percentage_achieved_thisYear=String.format("%.1f", percentage_thisYear);
			childDetails=new String[]{thisYearEventName.get(childPosition),
					  thisYearEventNumber.get(childPosition),
					  thisYearEventPeriod.get(childPosition),
					  thisYearEventDueDate.get(childPosition),
					  thisYearEventAchieved.get(childPosition),
					  thisYearEventStartDate.get(childPosition),
					  thisYearEventStatus.get(childPosition),
					  thisYearEventId.get(childPosition),
					  thisYearEventLastUpdated.get(childPosition),
					  percentage_achieved_thisYear
					//  thisYearEventNumberRemaining.get(childPosition)
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
	   text.setText(todayEventName.get(childPosition));
	   text2.setText(todayEventNumber.get(childPosition));
	   text3.setText(todayEventPeriod.get(childPosition));
	   text4.setText(todayEventDueDate.get(childPosition));
	   text5.setText(todayEventStartDate.get(childPosition));
	   text7.setText(todayEventLastUpdated.get(childPosition));
	   int number_achieved_today=Integer.valueOf(todayEventAchieved.get(childPosition));
	   //System.out.println(String.valueOf(number_achieved_today));
		  // int number_remaining_today=Integer.valueOf(todayEventNumberRemaining.get(childPosition));
		   Double percentage= ((double)number_achieved_today/Integer.valueOf(todayEventNumber.get(childPosition)))*100;	
		   //System.out.println(String.valueOf(percentage));
		   String percentage_achieved=String.format("%.0f", percentage);
	  
	   if(todayEventAchieved.isEmpty()){
	   text6.setText("0");
	   }else {
		  text6.setText(todayEventAchieved.get(childPosition));   
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
		   text.setText(thisWeekEventName.get(childPosition));
		   text2.setText(thisWeekEventNumber.get(childPosition));
		   text3.setText(thisWeekEventPeriod.get(childPosition));
		   text4.setText(thisWeekEventDueDate.get(childPosition));
		   text5.setText(thisWeekEventStartDate.get(childPosition));
		   text7.setText(thisWeekEventLastUpdated.get(childPosition));
		   int number_achieved_this_week=Integer.valueOf(thisWeekEventAchieved.get(childPosition));
		   Double percentage_this_week= ((double)number_achieved_this_week/Integer.valueOf(thisWeekEventNumber.get(childPosition)))*100;	
		   String percentage_achieved_this_week=String.format("%.0f", percentage_this_week);
		   if(thisWeekEventAchieved.size()<0){
		   text6.setText("0");
		   }else {
			  text6.setText(thisWeekEventAchieved.get(childPosition));   
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
		   text.setText(thisMonthEventName.get(childPosition));
		   text2.setText(thisMonthEventNumber.get(childPosition));
		   text3.setText(thisMonthEventPeriod.get(childPosition));
		   text4.setText(thisMonthEventDueDate.get(childPosition));
		   if(thisMonthEventStartDate.size()>0){
		   text5.setText(thisMonthEventStartDate.get(childPosition));
		   }
		   text7.setText(thisMonthEventLastUpdated.get(childPosition));
		   if(thisMonthEventAchieved.size()<0){
			   text6.setText("0");
			 
			   }else {
				 // text6.setText(thisMonthEventAchieved.get(childPosition));   
			   }
		   if(thisMonthEventAchieved.size()>0){
			   int number_achieved_this_month=Integer.valueOf(thisMonthEventAchieved.get(childPosition));	
		   		//int number_remaining_this_month=Integer.valueOf(thisMonthEventNumberRemaining.get(childPosition));
				Double percentage_this_month= ((double)number_achieved_this_month/Integer.valueOf(thisMonthEventNumber.get(childPosition))) *100;	
				String percentage_achieved_this_month=String.format("%.0f", percentage_this_month);
		   
		  
		   text8.setText(percentage_achieved_this_month+"%");  
		   }
		   		
		   /*
		   if(!monthlyEventStatus.isEmpty()){
		   if(monthlyEventStatus!=null&&monthlyEventStatus.get(childPosition).equalsIgnoreCase("updated")){
			   image.setImageResource(R.drawable.ic_achieved);
		   }else if(monthlyEventStatus!=null&&monthlyEventStatus.get(childPosition).equalsIgnoreCase("new_record")){
			   image.setImageResource(R.drawable.ic_loading);
		   }else if(monthlyEventStatus!=null&&monthlyEventStatus.get(childPosition).equalsIgnoreCase("not_achieved")){
			   image.setImageResource(R.drawable.ic_not_achieved);
		   }
		   }*/
		   //text.setTypeface(custom_font);
		   //text2.setTypeface(custom_font);
		   //text3.setTypeface(custom_font);
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
		   text.setText(thisQuarterEventName.get(childPosition));
		   text2.setText(thisQuarterEventNumber.get(childPosition));
		   text3.setText(thisQuarterEventPeriod.get(childPosition));
		   text4.setText(thisQuarterEventDueDate.get(childPosition));
		   text5.setText(thisQuarterEventStartDate.get(childPosition));
		   text7.setText(thisQuarterEventLastUpdated.get(childPosition));
		  // text8.setText(percentage_achieved_this_quarter);
		   if(thisQuarterEventAchieved.isEmpty()){
		   text6.setText("0");
		   }else {
			  text6.setText(thisQuarterEventAchieved.get(childPosition));   
		   }
		   int number_achieved_this_quarter=Integer.valueOf(thisQuarterEventAchieved.get(childPosition));
			//int number_remaining_this_quarter=Integer.valueOf(thisMonthEventNumberRemaining.get(childPosition));
			Double percentage_this_quarter= ((double)number_achieved_this_quarter/Integer.valueOf(thisQuarterEventNumber.get(childPosition))) *100;	
			String percentage_achieved_this_quarter=String.format("%.0f", percentage_this_quarter);
		   text8.setText(percentage_achieved_this_quarter+"%");
		   /*
	   if(!yearlyEventStatus.isEmpty()){
		   if(yearlyEventStatus!=null&&yearlyEventStatus.get(childPosition).equalsIgnoreCase("updated")){
			   image.setImageResource(R.drawable.ic_achieved);
		   }else if(yearlyEventStatus!=null&&yearlyEventStatus.get(childPosition).equalsIgnoreCase("new_record")){
			   image.setImageResource(R.drawable.ic_loading);
		   }else if(yearlyEventStatus!=null&&yearlyEventStatus.get(childPosition).equalsIgnoreCase("not_achieved")){
			   image.setImageResource(R.drawable.ic_not_achieved);
		   }
	   }*/
		   //text.setTypeface(custom_font);
		   //text2.setTypeface(custom_font);
		   //text3.setTypeface(custom_font);
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
		   text.setText(midYearEventName.get(childPosition));
		   text2.setText(midYearEventNumber.get(childPosition));
		   text3.setText(midYearEventPeriod.get(childPosition));
		   text4.setText(midYearEventDueDate.get(childPosition));
		   text5.setText(midYearEventStartDate.get(childPosition));
		   text7.setText(midYearEventLastUpdated.get(childPosition));
		   int number_achieved_this_midYear=Integer.valueOf(midYearEventAchieved.get(childPosition));
			//int number_remaining_this_midYear=Integer.valueOf(midYearEventNumberRemaining.get(childPosition));
			Double percentage_this_midYear=((double)number_achieved_this_midYear/Integer.valueOf(midYearEventNumber.get(childPosition))) *100;	
			String percentage_achieved_this_midYear=String.format("%.0f", percentage_this_midYear);
	
		  
		   if(midYearEventAchieved.isEmpty()){
		   text6.setText("0");
		   }else {
			  text6.setText(midYearEventAchieved.get(childPosition));   
		   }
		   text8.setText(percentage_achieved_this_midYear+"%");
		   /*
		   if(!midYearEventStatus.isEmpty()){
		   if(midYearEventStatus!=null&&midYearEventStatus.get(childPosition).equalsIgnoreCase("updated")){
			   image.setImageResource(R.drawable.ic_achieved);
		   }else if(midYearEventStatus!=null&&midYearEventStatus.get(childPosition).equalsIgnoreCase("new_record")){
			   image.setImageResource(R.drawable.ic_loading);
		   }else if(midYearEventStatus!=null&&midYearEventStatus.get(childPosition).equalsIgnoreCase("not_achieved")){
			   image.setImageResource(R.drawable.ic_not_achieved);
		   }
		   }*/
		   //text.setTypeface(custom_font);
		   //text2.setTypeface(custom_font);
		   //text3.setTypeface(custom_font);
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
		   text.setText(thisYearEventName.get(childPosition));
		   text2.setText(thisYearEventNumber.get(childPosition));
		   text3.setText(thisYearEventPeriod.get(childPosition));
		   text4.setText(thisYearEventDueDate.get(childPosition));
		   text5.setText(thisYearEventStartDate.get(childPosition));
		   text7.setText(thisYearEventLastUpdated.get(childPosition));
		   int number_achieved_thisYear=Integer.valueOf(thisYearEventAchieved.get(childPosition));
			//int number_remaining_thisYear=Integer.valueOf(thisYearEventNumberRemaining.get(childPosition));
			Double percentage_thisYear= ((double)number_achieved_thisYear/Integer.valueOf(midYearEventNumber.get(childPosition))) *100;	
			String percentage_achieved_thisYear=String.format("%.0f", percentage_thisYear);
		  
		   if(thisYearEventAchieved.isEmpty()){
		   text6.setText("0");
		   }else {
			  text6.setText(thisYearEventAchieved.get(childPosition));   
		   }
		   text8.setText(percentage_achieved_thisYear+"%");
		   /*
		   if(!midYearEventStatus.isEmpty()){
		   if(midYearEventStatus!=null&&midYearEventStatus.get(childPosition).equalsIgnoreCase("updated")){
			   image.setImageResource(R.drawable.ic_achieved);
		   }else if(midYearEventStatus!=null&&midYearEventStatus.get(childPosition).equalsIgnoreCase("new_record")){
			   image.setImageResource(R.drawable.ic_loading);
		   }else if(midYearEventStatus!=null&&midYearEventStatus.get(childPosition).equalsIgnoreCase("not_achieved")){
			   image.setImageResource(R.drawable.ic_not_achieved);
		   }
		   }*/
		   //text.setTypeface(custom_font);
		   //text2.setTypeface(custom_font);
		   //text3.setTypeface(custom_font);
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


