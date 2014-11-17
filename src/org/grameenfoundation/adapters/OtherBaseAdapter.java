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

public class OtherBaseAdapter extends BaseExpandableListAdapter{
	 private Context mContext;
	 private final ArrayList<String> dailyLearningName;
	 private final ArrayList<String> dailyLearningNumber;
	 private final ArrayList<String> dailyLearningPeriod;
	 private final ArrayList<String> dailyLearningDueDate;
	 private final ArrayList<String> dailyLearningStatus;
	 private final ArrayList<String>  dailyLearningId;
	 
	 private final ArrayList<String> weeklyLearningName;
	 private final ArrayList<String> weeklyLearningNumber;
	 private final ArrayList<String> weeklyLearningPeriod;
	 private final ArrayList<String> weeklyLearningDueDate;
	 private final ArrayList<String> weeklyLearningStatus;
	 private final ArrayList<String> weeklyLearningId;
	 
	 private final ArrayList<String> monthlyLearningName;
	 private final ArrayList<String> monthlyLearningNumber;
	 private final ArrayList<String> monthlyLearningPeriod;
	 private final ArrayList<String> monthlyLearningDueDate;
	 private final ArrayList<String> monthlyLearningStatus;
	 private final ArrayList<String> monthlyLearningId;
	 
	 private final ArrayList<String> yearlyLearningName;
	 private final ArrayList<String> yearlyLearningNumber;
	 private final ArrayList<String> yearlyLearningPeriod;
	 private final ArrayList<String> yearlyLearningDueDate;
	 private final ArrayList<String> yearlyLearningStatus;
	 private final ArrayList<String> yearlyLearningId;
	 
	 private final ArrayList<String> midYearLearningName;
	 private final ArrayList<String> midYearLearningNumber;
	 private final ArrayList<String> midYearLearningPeriod;
	 private final ArrayList<String> midYearLearningDueDate;
	 private final ArrayList<String> midYearLearningStatus;
	 private final ArrayList<String> midYearLearningId;
	 private String[] groupItems;
	 public LayoutInflater minflater;
	 ExpandableListView other_list;
	private int lastExpandedGroupPosition;
	 
	 
	 public OtherBaseAdapter(Context c,ArrayList<String> dailyLearningName ,
			 							ArrayList<String> dailyLearningNumber,
			 							ArrayList<String> dailyLearningPeriod,
			 							ArrayList<String> dailyLearningDueDate,
			 							ArrayList<String> dailyLearningStatus,
			 							ArrayList<String>  dailyLearningId,
			 							
			 							ArrayList<String> weeklyLearningName,
			 							ArrayList<String> weeklyLearningNumber,
			 							ArrayList<String> weeklyLearningPeriod,
			 							ArrayList<String> weeklyLearningDueDate,
			 							ArrayList<String> weeklyLearningStatus,
			 							ArrayList<String> weeklyLearningId,
			 							
			 							ArrayList<String> monthlyLearningName,
			 							ArrayList<String> monthlyLearningNumber,
			 							ArrayList<String> monthlyLearningPeriod,
			 							ArrayList<String> monthlyLearningDueDate,
			 							ArrayList<String> monthlyLearningStatus,
			 							ArrayList<String> monthlyLearningId,
			 							
			 							ArrayList<String> yearlyLearningName,
			 							ArrayList<String> yearlyLearningNumber,
			 							ArrayList<String> yearlyLearningPeriod,
			 							ArrayList<String> yearlyLearningDueDate,
			 							ArrayList<String> yearlyLearningStatus,
			 							ArrayList<String> yearlyLearningId,
			 							
			 							ArrayList<String> midYearLearningName,
			 							ArrayList<String> midYearLearningNumber,
			 							ArrayList<String> midYearLearningPeriod,
			 							ArrayList<String> midYearLearningDueDate,
			 							ArrayList<String> midYearLearningStatus,
			 							ArrayList<String> midYearLearningId,
			 							String[] groupItems,
			 							ExpandableListView other_list) {
       mContext = c;
       this.dailyLearningName = dailyLearningName;
       this.dailyLearningNumber=dailyLearningNumber;
       this.dailyLearningPeriod=dailyLearningPeriod;
       this.dailyLearningDueDate=dailyLearningDueDate;
       this.dailyLearningStatus=dailyLearningStatus;
       this.dailyLearningId=dailyLearningId;
       minflater = LayoutInflater.from(mContext);
       
       this.monthlyLearningName = monthlyLearningName;
       this.monthlyLearningNumber=monthlyLearningNumber;
       this.monthlyLearningPeriod=monthlyLearningPeriod;
       this.monthlyLearningDueDate=monthlyLearningDueDate;
       this.monthlyLearningStatus=monthlyLearningStatus;
       this.monthlyLearningId=monthlyLearningId;
       
       this.weeklyLearningName = weeklyLearningName;
       this.weeklyLearningNumber=weeklyLearningNumber;
       this.weeklyLearningPeriod=weeklyLearningPeriod;
       this.weeklyLearningDueDate=weeklyLearningDueDate;
       this.weeklyLearningStatus=weeklyLearningStatus;
       this.weeklyLearningId=weeklyLearningId;
       
       this.yearlyLearningName = yearlyLearningName;
       this.yearlyLearningNumber=yearlyLearningNumber;
       this.yearlyLearningPeriod=yearlyLearningPeriod;
       this.yearlyLearningDueDate=yearlyLearningDueDate;
       this.yearlyLearningStatus=yearlyLearningStatus;
       this.yearlyLearningId=yearlyLearningId;
       
       this.midYearLearningName = midYearLearningName;
       this.midYearLearningNumber=midYearLearningNumber;
       this.midYearLearningPeriod=midYearLearningPeriod;
       this.midYearLearningDueDate=midYearLearningDueDate;
       this.midYearLearningStatus=midYearLearningStatus;
       this.midYearLearningId=midYearLearningId;
       this.groupItems=groupItems;
       this.other_list=other_list;
     
   }
	


	@Override
	public int getGroupCount() {
		
		return groupItems.length;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		int count = 0;
		if(groupPosition==0){
			count=dailyLearningName.size();
		}else if(groupPosition==1){
			count=weeklyLearningName.size();
		}else if(groupPosition==2){
			count=monthlyLearningName.size();
		}else if(groupPosition==3){
			count=yearlyLearningName.size();
		}else if(groupPosition==4){
			count=midYearLearningName.size();
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
			childDetails=new String[]{dailyLearningName.get(childPosition),
									 dailyLearningNumber.get(childPosition),
									 dailyLearningPeriod.get(childPosition),
									 dailyLearningDueDate.get(childPosition),
									 dailyLearningStatus.get(childPosition),
									 dailyLearningId.get(childPosition)};
		}else if(groupPosition==1){
			childDetails=new String[]{weeklyLearningName.get(childPosition),
					weeklyLearningNumber.get(childPosition),
					weeklyLearningPeriod.get(childPosition),
					weeklyLearningDueDate.get(childPosition),
					weeklyLearningStatus.get(childPosition),
					weeklyLearningId.get(childPosition)};
		}else if(groupPosition==2){
			childDetails=new String[]{monthlyLearningName.get(childPosition),
					 monthlyLearningNumber.get(childPosition),
					 monthlyLearningPeriod.get(childPosition),
					 monthlyLearningDueDate.get(childPosition),
					 monthlyLearningStatus.get(childPosition),
					 monthlyLearningId.get(childPosition)};
		}else if(groupPosition==3){
			childDetails=new String[]{yearlyLearningName.get(childPosition),
					yearlyLearningNumber.get(childPosition),
					yearlyLearningPeriod.get(childPosition),
					yearlyLearningDueDate.get(childPosition),
					yearlyLearningStatus.get(childPosition),
					yearlyLearningId.get(childPosition)};
		}else if(groupPosition==4){
			childDetails=new String[]{midYearLearningName.get(childPosition),
					midYearLearningNumber.get(childPosition),
					midYearLearningPeriod.get(childPosition),
					midYearLearningDueDate.get(childPosition),
					midYearLearningStatus.get(childPosition),
					midYearLearningId.get(childPosition)};
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
			id=Integer.valueOf(dailyLearningId.get(childPosition));
		}else if(groupPosition==1){
			id=Integer.valueOf(weeklyLearningId.get(childPosition));
		}else if(groupPosition==2){
			id=Integer.valueOf(monthlyLearningId.get(childPosition));
		}else if(groupPosition==3){
			id=Integer.valueOf(yearlyLearningId.get(childPosition));
		}else if(groupPosition==4){
			id=Integer.valueOf(midYearLearningId.get(childPosition));
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
	   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
	   text.setText(dailyLearningName.get(childPosition));
	   text2.setText(dailyLearningNumber.get(childPosition));
	   text3.setText(dailyLearningPeriod.get(childPosition));
	   text4.setText(dailyLearningDueDate.get(childPosition));
	   /*
	   if(!dailyLearningStatus.isEmpty()){
	   if(dailyLearningStatus!=null&&dailyLearningStatus.get(childPosition).equalsIgnoreCase("updated")){
		   image.setImageResource(R.drawable.ic_achieved);
	   }else if(dailyLearningStatus!=null&&dailyLearningStatus.get(childPosition).equalsIgnoreCase("new_record")){
		   image.setImageResource(R.drawable.ic_loading);
	   }else if(dailyLearningStatus!=null&&dailyLearningStatus.get(childPosition).equalsIgnoreCase("not_achieved")){
		   image.setImageResource(R.drawable.ic_not_achieved);
	   }
	   }*/
	   //text.setTypeface(custom_font);
	   //text2.setTypeface(custom_font);
	   //text3.setTypeface(custom_font);
	   }else if(groupPosition==1){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(weeklyLearningName.get(childPosition));
		   text2.setText(weeklyLearningNumber.get(childPosition));
		   text3.setText(weeklyLearningPeriod.get(childPosition));
		   text4.setText(weeklyLearningDueDate.get(childPosition));
		   /*
		   if(weeklyLearningStatus!=null&&weeklyLearningStatus.get(childPosition).equalsIgnoreCase("updated")){
			   image.setImageResource(R.drawable.ic_achieved);
		   }else if(weeklyLearningStatus!=null&&weeklyLearningStatus.get(childPosition).equalsIgnoreCase("new_record")){
			   image.setImageResource(R.drawable.ic_loading);
		   }else if(weeklyLearningStatus!=null&&weeklyLearningStatus.get(childPosition).equalsIgnoreCase("not_achieved")){
			   image.setImageResource(R.drawable.ic_not_achieved);
		   }*/
		   //text.setTypeface(custom_font);
		   //text2.setTypeface(custom_font);
		   //text3.setTypeface(custom_font);
	   }else if(groupPosition==2){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(monthlyLearningName.get(childPosition));
		   text2.setText(monthlyLearningNumber.get(childPosition));
		   text3.setText(monthlyLearningPeriod.get(childPosition));
		   text4.setText(monthlyLearningDueDate.get(childPosition));
		   /*
		   if(monthlyLearningStatus!=null&&monthlyLearningStatus.get(childPosition).equalsIgnoreCase("updated")){
			   image.setImageResource(R.drawable.ic_achieved);
		   }else if(monthlyLearningStatus!=null&&monthlyLearningStatus.get(childPosition).equalsIgnoreCase("new_record")){
			   image.setImageResource(R.drawable.ic_loading);
		   }else if(monthlyLearningStatus!=null&&monthlyLearningStatus.get(childPosition).equalsIgnoreCase("not_achieved")){
			   image.setImageResource(R.drawable.ic_not_achieved);
		   }*/
		   //text.setTypeface(custom_font);
		   //text2.setTypeface(custom_font);
		   //text3.setTypeface(custom_font);
	   }else if(groupPosition==3){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(yearlyLearningName.get(childPosition));
		   text2.setText(yearlyLearningNumber.get(childPosition));
		   text3.setText(yearlyLearningPeriod.get(childPosition));
		   text4.setText(yearlyLearningDueDate.get(childPosition));
		   /*
		   if(yearlyLearningStatus!=null&&yearlyLearningStatus.get(childPosition).equalsIgnoreCase("updated")){
			   image.setImageResource(R.drawable.ic_achieved);
		   }else if(yearlyLearningStatus!=null&&yearlyLearningStatus.get(childPosition).equalsIgnoreCase("new_record")){
			   image.setImageResource(R.drawable.ic_loading);
		   }else if(yearlyLearningStatus!=null&&yearlyLearningStatus.get(childPosition).equalsIgnoreCase("not_achieved")){
			   image.setImageResource(R.drawable.ic_not_achieved);
		   }*/
		   //text.setTypeface(custom_font);
		   //text2.setTypeface(custom_font);
		   //text3.setTypeface(custom_font);
	   }else if(groupPosition==4){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(midYearLearningName.get(childPosition));
		   text2.setText(midYearLearningNumber.get(childPosition));
		   text3.setText(midYearLearningPeriod.get(childPosition));
		   text4.setText(midYearLearningDueDate.get(childPosition));
		   /*
		   if(midYearLearningStatus!=null&&midYearLearningStatus.get(childPosition).equalsIgnoreCase("updated")){
			   image.setImageResource(R.drawable.ic_achieved);
		   }else if(midYearLearningStatus!=null&&midYearLearningStatus.get(childPosition).equalsIgnoreCase("new_record")){
			   image.setImageResource(R.drawable.ic_loading);
		   }else if(midYearLearningStatus!=null&&midYearLearningStatus.get(childPosition).equalsIgnoreCase("not_achieved")){
			   image.setImageResource(R.drawable.ic_not_achieved);
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
            other_list.collapseGroup(lastExpandedGroupPosition);
       
    }
    	
        super.onGroupExpanded(groupPosition);
     
        lastExpandedGroupPosition = groupPosition;
        
    }
*/
		
	}


