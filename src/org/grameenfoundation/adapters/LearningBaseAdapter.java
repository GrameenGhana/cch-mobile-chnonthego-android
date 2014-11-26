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

public class LearningBaseAdapter extends BaseExpandableListAdapter{
	 private Context mContext;
	 private final ArrayList<String> todayCategory;
	 private final ArrayList<String> todayCourse;
	 private final ArrayList<String> todayTopic;
	 private final ArrayList<String> todayEventPeriod;
	 private final ArrayList<String> todayEventDueDate;
	 private final ArrayList<String> todayEventStartDate;
	 private final ArrayList<String> todayEventStatus;
	 private final ArrayList<String> todayEventId;
	 private final ArrayList<String> todayEventLastUpdated;
	 
	 /*
	 private final ArrayList<String> tomorrowCategory;
	 private final ArrayList<String> tomorrowCourse;
	 private final ArrayList<String> tomorrowTopic;
	 private final ArrayList<String> tomorrowEventPeriod;
	 private final ArrayList<String> tomorrowEventDueDate;
	 private final ArrayList<String> tomorrowEventAchieved;
	 private final ArrayList<String> tomorrowEventStartDate;
	 private final ArrayList<String> tomorrowEventStatus;
	 private final ArrayList<String> tomorrowEventId;
	 */
	 private final ArrayList<String> thisWeekCategory;
	 private final ArrayList<String> thisWeekCourse;
	 private final ArrayList<String> thisWeekTopic;
	 private final ArrayList<String> thisWeekEventPeriod;
	 private final ArrayList<String> thisWeekEventDueDate;
	 private final ArrayList<String> thisWeekEventStartDate;
	 private final ArrayList<String> thisWeekEventStatus;
	 private final ArrayList<String> thisWeekEventId;
	 private final ArrayList<String> thisWeekEventLastUpdated;
	 
	 private final ArrayList<String> thisMonthCategory;
	 private final ArrayList<String> thisMonthCourse;
	 private final ArrayList<String> thisMonthTopic;
	 private final ArrayList<String> thisMonthEventPeriod;
	 private final ArrayList<String> thisMonthEventDueDate;
	 private final ArrayList<String> thisMonthEventStartDate;
	 private final ArrayList<String> thisMonthEventStatus;
	 private final ArrayList<String> thisMonthEventId;
	 private final ArrayList<String> thisMonthEventLastUpdated;
	 
	 private final ArrayList<String> thisQuarterCategory;
	 private final ArrayList<String> thisQuarterCourse;
	 private final ArrayList<String> thisQuarterTopic;
	 private final ArrayList<String> thisQuarterEventPeriod;
	 private final ArrayList<String> thisQuarterEventDueDate;
	 private final ArrayList<String> thisQuarterEventStartDate;
	 private final ArrayList<String> thisQuarterEventStatus;
	 private final ArrayList<String> thisQuarterEventId;
	 private final ArrayList<String> thisQuarterEventLastUpdated;
	 
	 private final ArrayList<String> midYearCategory;
	 private final ArrayList<String> midYearCourse;
	 private final ArrayList<String> midYearTopic;
	 private final ArrayList<String> midYearEventPeriod;
	 private final ArrayList<String> midYearEventDueDate;
	 private final ArrayList<String> midYearEventStartDate;
	 private final ArrayList<String> midYearEventStatus;
	 private final ArrayList<String> midYearEventId;
	 private final ArrayList<String> midYearEventLastUpdated;
	 
	 private final ArrayList<String> thisYearCategory;
	 private final ArrayList<String> thisYearCourse;
	 private final ArrayList<String> thisYearTopic;
	 private final ArrayList<String> thisYearEventPeriod;
	 private final ArrayList<String> thisYearEventDueDate;
	 private final ArrayList<String> thisYearEventStartDate;
	 private final ArrayList<String> thisYearEventStatus;
	 private final ArrayList<String> thisYearEventId;
	 private final ArrayList<String> thisYearEventLastUpdated;
	 private String[] groupItems;
	 public LayoutInflater minflater;
	 ExpandableListView event_list;
	private int lastExpandedGroupPosition;
	 
	 
	 public LearningBaseAdapter(Context c,ArrayList<String> todayCategory,
										ArrayList<String> todayCourse,
										ArrayList<String> todayTopic,
										ArrayList<String> todayEventPeriod,
										ArrayList<String> todayEventDueDate,
										ArrayList<String> todayEventStartDate,
										ArrayList<String> todayEventStatus,
										ArrayList<String>  todayEventId,
										ArrayList<String> todayEventLastUpdated,
										/*
										ArrayList<String> tomorrowCategory,
										ArrayList<String> tomorrowCourse,
										ArrayList<String> tomorrowTopic,
										ArrayList<String> tomorrowEventPeriod,
										ArrayList<String> tomorrowEventDueDate,
										ArrayList<String> tomorrowEventStatus,
										ArrayList<String> tomorrowEventId,
				*/
										ArrayList<String> thisWeekCategory,
										ArrayList<String> thisWeekCourse,
										ArrayList<String> thisWeekTopic,
										ArrayList<String> thisWeekEventPeriod,
										ArrayList<String> thisWeekEventDueDate,
										ArrayList<String> thisWeekEventStartDate,
										ArrayList<String> thisWeekEventStatus,
										ArrayList<String> thisWeekEventId,
										ArrayList<String> thisWeekEventLastUpdated,
				
										ArrayList<String> thisMonthCategory,
										ArrayList<String> thisMonthCourse,
										ArrayList<String> thisMonthTopic,
										ArrayList<String> thisMonthEventPeriod,
										ArrayList<String> thisMonthEventDueDate,
										ArrayList<String> thisMonthEventStartDate,
										ArrayList<String> thisMonthEventStatus,
										ArrayList<String> thisMonthEventId,
										ArrayList<String> thisMonthEventLastUpdated,
				
										ArrayList<String> thisQuarterCategory,
										ArrayList<String> thisQuarterCourse,
										ArrayList<String> thisQuarterTopic,
										ArrayList<String> thisQuarterEventPeriod,
										ArrayList<String> thisQuarterEventDueDate,
										ArrayList<String> thisQuarterEventStartDate,
										ArrayList<String> thisQuarterEventStatus,
										ArrayList<String> thisQuarterEventId,
										ArrayList<String> thisQuarterEventLastUpdated,
				
										ArrayList<String> midYearCategory,
										ArrayList<String> midYearCourse,
										ArrayList<String> midYearTopic,
										ArrayList<String> midYearEventPeriod,
										ArrayList<String> midYearEventDueDate,
										ArrayList<String> midYearEventStartDate,
										ArrayList<String> midYearEventStatus,
										ArrayList<String> midYearEventId,
										ArrayList<String> midYearEventLastUpdated,
				
										ArrayList<String> thisYearCategory,
										ArrayList<String> thisYearCourse,
										ArrayList<String> thisYearTopic,
										ArrayList<String> thisYearEventPeriod,
										ArrayList<String> thisYearEventDueDate,
										ArrayList<String> thisYearEventStartDate,
										ArrayList<String> thisYearEventStatus,
										ArrayList<String> thisYearEventId,
										ArrayList<String> thisYearEventLastUpdated,
			 							String[] groupItems,
			 							ExpandableListView event_list
			 							) {
       mContext = c;
       this.todayCategory = todayCategory;
       this.todayCourse=todayCourse;
       this.todayTopic=todayTopic;
       this.todayEventPeriod=todayEventPeriod;
       this.todayEventDueDate=todayEventDueDate;
       this.todayEventStartDate=todayEventStartDate;
       this.todayEventStatus=todayEventStatus;
       this.todayEventId=todayEventId;
       this.todayEventLastUpdated=todayEventLastUpdated;
       minflater = LayoutInflater.from(mContext);
       /*
       this.tomorrowCategory = tomorrowCategory;
       this.tomorrowCourse=tomorrowCourse;
       this.tomorrowTopic=tomorrowTopic;
       this.tomorrowEventPeriod=tomorrowEventPeriod;
       this.tomorrowEventDueDate=tomorrowEventDueDate;
       this.tomorrowEventStatus=tomorrowEventStatus;
       this.tomorrowEventId=tomorrowEventId;
       */
       this.thisWeekCategory = thisWeekCategory;
       this.thisWeekCourse=thisWeekCourse;
       this.thisWeekTopic=thisWeekTopic;
       this.thisWeekEventPeriod=thisWeekEventPeriod;
       this.thisWeekEventDueDate=thisWeekEventDueDate;
       this.thisWeekEventStartDate=thisWeekEventStartDate;
       this.thisWeekEventStatus=thisWeekEventStatus;
       this.thisWeekEventId=thisWeekEventId;
       this.thisWeekEventLastUpdated=thisWeekEventLastUpdated;
       
       this.thisMonthCategory = thisMonthCategory;
       this.thisMonthCourse=thisMonthCourse;
       this.thisMonthTopic=thisMonthTopic;
       this.thisMonthEventPeriod=thisMonthEventPeriod;
       this.thisMonthEventDueDate=thisMonthEventDueDate;
       this.thisMonthEventStartDate=thisMonthEventStartDate;
       this.thisMonthEventStatus=thisMonthEventStatus;
       this.thisMonthEventId=thisMonthEventId;
       this.thisMonthEventLastUpdated=thisMonthEventLastUpdated;
       
       this.thisQuarterCategory = thisQuarterCategory;
       this.thisQuarterCourse=thisQuarterCourse;
       this.thisQuarterTopic=thisQuarterTopic;
       this.thisQuarterEventPeriod=thisQuarterEventPeriod;
       this.thisQuarterEventDueDate=thisQuarterEventDueDate;
       this.thisQuarterEventStartDate=thisQuarterEventStartDate;
       this.thisQuarterEventStatus=thisQuarterEventStatus;
       this.thisQuarterEventId=thisQuarterEventId;
       this.thisQuarterEventLastUpdated=thisQuarterEventLastUpdated;
       
       this.midYearCategory = midYearCategory;
       this.midYearCourse=midYearCourse;
       this.midYearTopic=midYearTopic;
       this.midYearEventPeriod=midYearEventPeriod;
       this.midYearEventDueDate=midYearEventDueDate;
       this.midYearEventStartDate=midYearEventStartDate;
       this.midYearEventStatus=midYearEventStatus;
       this.midYearEventId=midYearEventId;
       this.midYearEventLastUpdated=midYearEventLastUpdated;

       this.thisYearCategory = thisYearCategory;
       this.thisYearCourse=thisYearCourse;
       this.thisYearTopic=thisYearTopic;
       this.thisYearEventPeriod=thisYearEventPeriod;
       this.thisYearEventDueDate=thisYearEventDueDate;
       this.thisYearEventStartDate=thisYearEventStartDate;
       this.thisYearEventStatus=thisYearEventStatus;
       this.thisYearEventId=thisYearEventId;
       this.thisYearEventLastUpdated=thisYearEventLastUpdated;
       
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
			count=todayCourse.size();
		}else if(groupPosition==1){
			count=thisWeekCourse.size();
		}else if(groupPosition==2){
			count=thisMonthCourse.size();
		}else if(groupPosition==3){
			count=thisQuarterCourse.size();
		}else if(groupPosition==4){
			count=midYearCourse.size();
		}else if(groupPosition==5){
			count=thisYearCourse.size();
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
			childDetails=new String[]{todayCategory.get(childPosition),
									  todayCourse.get(childPosition),
									  todayTopic.get(childPosition),
									  todayEventPeriod.get(childPosition),
									  todayEventDueDate.get(childPosition),
									  todayEventStartDate.get(childPosition),
									  todayEventStatus.get(childPosition),
									  todayEventId.get(childPosition)};
		}else if(groupPosition==1){
			childDetails=new String[]{thisWeekCategory.get(childPosition),
					  thisWeekCourse.get(childPosition),
					  thisWeekTopic.get(childPosition),
					  thisWeekEventPeriod.get(childPosition),
					  thisWeekEventDueDate.get(childPosition),
					  thisWeekEventStartDate.get(childPosition),
					  thisWeekEventStatus.get(childPosition),
					  thisWeekEventId.get(childPosition)};
			/*
			childDetails=new String[]{tomorrowCategory.get(childPosition),
									  tomorrowCourse.get(childPosition),
									  tomorrowTopic.get(childPosition),
									  tomorrowEventPeriod.get(childPosition),
									  tomorrowEventDueDate.get(childPosition),
									  tomorrowEventStatus.get(childPosition),
									  tomorrowEventId.get(childPosition)};*/
		}else if(groupPosition==2){
			childDetails=new String[]{thisMonthCategory.get(childPosition),
					  thisMonthCourse.get(childPosition),
					  thisMonthTopic.get(childPosition),
					  thisMonthEventPeriod.get(childPosition),
					  thisMonthEventDueDate.get(childPosition),
					  thisMonthEventStartDate.get(childPosition),
					  thisMonthEventStatus.get(childPosition),
					  thisMonthEventId.get(childPosition)};
		}else if(groupPosition==3){
			childDetails=new String[]{thisQuarterCategory.get(childPosition),
					  thisQuarterCourse.get(childPosition),
					  thisQuarterTopic.get(childPosition),
					  thisQuarterEventPeriod.get(childPosition),
					  thisQuarterEventDueDate.get(childPosition),
					  thisQuarterEventStartDate.get(childPosition),
					  thisQuarterEventStatus.get(childPosition),
					  thisQuarterEventId.get(childPosition)};
		}else if(groupPosition==4){
			childDetails=new String[]{midYearCategory.get(childPosition),
					 midYearCourse.get(childPosition),
					 midYearTopic.get(childPosition),
					 midYearEventPeriod.get(childPosition),
					 midYearEventDueDate.get(childPosition),
					 midYearEventStartDate.get(childPosition),
					 midYearEventStatus.get(childPosition),
					 midYearEventId.get(childPosition)};
		}else if(groupPosition==5){
			childDetails=new String[]{thisYearCategory.get(childPosition),
					  thisYearCourse.get(childPosition),
					  thisYearTopic.get(childPosition),
					  thisYearEventPeriod.get(childPosition),
					  thisYearEventDueDate.get(childPosition),
					  thisYearEventStartDate.get(childPosition),
					  thisYearEventStatus.get(childPosition),
					  thisYearEventId.get(childPosition)};
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
		   convertView=minflater.inflate(R.layout.learning_listview_single,null);
	   }
	   if(groupPosition==0){
	   TextView text=(TextView) convertView.findViewById(R.id.textView_learningCategory);
	   TextView text2=(TextView) convertView.findViewById(R.id.textView_learningCourse);
	   TextView text3=(TextView) convertView.findViewById(R.id.textView_learningTopic);
	   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
	   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
	   TextView text6=(TextView) convertView.findViewById(R.id.textView_period);
	   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
	   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
	   text.setText(todayCategory.get(childPosition));
	   text2.setText(todayCourse.get(childPosition));
	   text3.setText(todayTopic.get(childPosition));
	   text4.setText(todayEventDueDate.get(childPosition));
	   text5.setText(todayEventStartDate.get(childPosition));
	   text6.setText(todayEventPeriod.get(childPosition));
	   text7.setText(todayEventLastUpdated.get(childPosition));
	   /*
	   if(!dailyEventStatus.isEmpty()){
   if(dailyEventStatus!=null&&dailyEventStatus.get(childPosition).equalsIgnoreCase("updated")){
		   image.setImageResource(R.drawable.ic_achieved);
	   }else if(dailyEventStatus!=null&&dailyEventStatus.get(childPosition).equalsIgnoreCase("new_record")){
		   image.setImageResource(R.drawable.ic_loading);
	   }else if(dailyEventStatus!=null&&dailyEventStatus.get(childPosition).equalsIgnoreCase("not_achieved")){
		   image.setImageResource(R.drawable.ic_not_achieved);
	   }
	   }*/
	   //text.setTypeface(custom_font);
	   //text2.setTypeface(custom_font);
	   //text3.setTypeface(custom_font);
	   }else if(groupPosition==1){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_learningCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_learningCourse);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_learningTopic);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_period);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(thisWeekCategory.get(childPosition));
		   text2.setText(thisWeekCourse.get(childPosition));
		   text3.setText(thisWeekTopic.get(childPosition));
		   text4.setText(thisWeekEventDueDate.get(childPosition));
		   text5.setText(thisWeekEventStartDate.get(childPosition));
		   text6.setText(thisWeekEventPeriod.get(childPosition));
		   text7.setText(thisWeekEventLastUpdated.get(childPosition));
		   /*
	   if(!weeklyEventStatus.isEmpty()){
		   if(weeklyEventStatus.get(childPosition).equalsIgnoreCase("updated")){
			   image.setImageResource(R.drawable.ic_achieved);
		   }else if(weeklyEventStatus.get(childPosition).equalsIgnoreCase("new_record")){
			   image.setImageResource(R.drawable.ic_loading);
		   }else if(weeklyEventStatus.get(childPosition).equalsIgnoreCase("not_achieved")){
			   image.setImageResource(R.drawable.ic_not_achieved);
		   }
		   }*/
		   //text.setTypeface(custom_font);
		   //text2.setTypeface(custom_font);
		   //text3.setTypeface(custom_font);
	   }else if(groupPosition==2){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_learningCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_learningCourse);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_learningTopic);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_period);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(thisMonthCategory.get(childPosition));
		   text2.setText(thisMonthCourse.get(childPosition));
		   text3.setText(thisMonthTopic.get(childPosition));
		   text4.setText(thisMonthEventDueDate.get(childPosition));
		   text5.setText(thisMonthEventStartDate.get(childPosition));
		   text6.setText(thisMonthEventPeriod.get(childPosition));
		   text7.setText(thisMonthEventLastUpdated.get(childPosition));
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
		   TextView text=(TextView) convertView.findViewById(R.id.textView_learningCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_learningCourse);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_learningTopic);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_period);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_period);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(thisQuarterCategory.get(childPosition));
		   text2.setText(thisQuarterCourse.get(childPosition));
		   text3.setText(thisQuarterTopic.get(childPosition));
		   text4.setText(thisQuarterEventDueDate.get(childPosition));
		   text5.setText(thisQuarterEventStartDate.get(childPosition));
		   text6.setText(thisQuarterEventPeriod.get(childPosition));
		   text7.setText(thisQuarterEventLastUpdated.get(childPosition));
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
		   TextView text=(TextView) convertView.findViewById(R.id.textView_learningCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_learningCourse);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_learningTopic);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_period);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(midYearCategory.get(childPosition));
		   text2.setText(midYearCourse.get(childPosition));
		   text3.setText(midYearTopic.get(childPosition));
		   text4.setText(midYearEventDueDate.get(childPosition));
		   text5.setText(midYearEventStartDate.get(childPosition));
		   text6.setText(midYearEventPeriod.get(childPosition));
		   text7.setText(midYearEventLastUpdated.get(childPosition));
		   /*
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
		   TextView text=(TextView) convertView.findViewById(R.id.textView_learningCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_learningCourse);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_learningTopic);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_period);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(thisYearCategory.get(childPosition));
		   text2.setText(thisYearCourse.get(childPosition));
		   text3.setText(thisYearTopic.get(childPosition));
		   text4.setText(thisYearEventDueDate.get(childPosition));
		   text5.setText(thisYearEventStartDate.get(childPosition));
		   text6.setText(thisYearEventPeriod.get(childPosition));
		   text7.setText(thisYearEventLastUpdated.get(childPosition));
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


