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
	 private final ArrayList<String> dailyEventName;
	 private final ArrayList<String> dailyEventNumber;
	 private final ArrayList<String> dailyEventPeriod;
	 private final ArrayList<String> dailyEventDueDate;
	 private final ArrayList<String> dailyEventStatus;
	 private final ArrayList<String>  dailyEventId;
	 
	 private final ArrayList<String> weeklyEventName;
	 private final ArrayList<String> weeklyEventNumber;
	 private final ArrayList<String> weeklyEventPeriod;
	 private final ArrayList<String> weeklyEventDueDate;
	 private final ArrayList<String> weeklyEventStatus;
	 private final ArrayList<String> weeklyEventId;
	 
	 private final ArrayList<String> monthlyEventName;
	 private final ArrayList<String> monthlyEventNumber;
	 private final ArrayList<String> monthlyEventPeriod;
	 private final ArrayList<String> monthlyEventDueDate;
	 private final ArrayList<String> monthlyEventStatus;
	 private final ArrayList<String> monthlyEventId;
	 
	 private final ArrayList<String> yearlyEventName;
	 private final ArrayList<String> yearlyEventNumber;
	 private final ArrayList<String> yearlyEventPeriod;
	 private final ArrayList<String> yearlyEventDueDate;
	 private final ArrayList<String> yearlyEventStatus;
	 private final ArrayList<String> yearlyEventId;
	 
	 private final ArrayList<String> midYearEventName;
	 private final ArrayList<String> midYearEventNumber;
	 private final ArrayList<String> midYearEventPeriod;
	 private final ArrayList<String> midYearEventDueDate;
	 private final ArrayList<String> midYearEventStatus;
	 private final ArrayList<String> midYearEventId;
	 private String[] groupItems;
	 public LayoutInflater minflater;
	 ExpandableListView event_list;
	private int lastExpandedGroupPosition;
	 
	 
	 public EventBaseAdapter(Context c,ArrayList<String> dailyEventName ,
			 							ArrayList<String> dailyEventNumber,
			 							ArrayList<String> dailyEventPeriod,
			 							ArrayList<String> dailyEventDueDate,
			 							ArrayList<String> dailyEventStatus,
			 							ArrayList<String>  dailyEventId,
			 							
			 							ArrayList<String> weeklyEventName,
			 							ArrayList<String> weeklyEventNumber,
			 							ArrayList<String> weeklyEventPeriod,
			 							ArrayList<String> weeklyEventDueDate,
			 							ArrayList<String> weeklyEventStatus,
			 							ArrayList<String> weeklyEventId,
			 							
			 							ArrayList<String> monthlyEventName,
			 							ArrayList<String> monthlyEventNumber,
			 							ArrayList<String> monthlyEventPeriod,
			 							ArrayList<String> monthlyEventDueDate,
			 							ArrayList<String> monthlyEventStatus,
			 							ArrayList<String> monthlyEventId,
			 							
			 							ArrayList<String> yearlyEventName,
			 							ArrayList<String> yearlyEventNumber,
			 							ArrayList<String> yearlyEventPeriod,
			 							ArrayList<String> yearlyEventDueDate,
			 							ArrayList<String> yearlyEventStatus,
			 							ArrayList<String> yearlyEventId,
			 							
			 							ArrayList<String> midYearEventName,
			 							ArrayList<String> midYearEventNumber,
			 							ArrayList<String> midYearEventPeriod,
			 							ArrayList<String> midYearEventDueDate,
			 							ArrayList<String> midYearEventStatus,
			 							ArrayList<String> midYearEventId,
			 							String[] groupItems,
			 							ExpandableListView event_list
			 							) {
       mContext = c;
       this.dailyEventName = dailyEventName;
       this.dailyEventNumber=dailyEventNumber;
       this.dailyEventPeriod=dailyEventPeriod;
       this.dailyEventDueDate=dailyEventDueDate;
       this.dailyEventStatus=dailyEventStatus;
       this.dailyEventId=dailyEventId;
       minflater = LayoutInflater.from(mContext);
       
       this.monthlyEventName = monthlyEventName;
       this.monthlyEventNumber=monthlyEventNumber;
       this.monthlyEventPeriod=monthlyEventPeriod;
       this.monthlyEventDueDate=monthlyEventDueDate;
       this.monthlyEventStatus=monthlyEventStatus;
       this.monthlyEventId=monthlyEventId;
       
       this.weeklyEventName = weeklyEventName;
       this.weeklyEventNumber=weeklyEventNumber;
       this.weeklyEventPeriod=weeklyEventPeriod;
       this.weeklyEventDueDate=weeklyEventDueDate;
       this.weeklyEventStatus=weeklyEventStatus;
       this.weeklyEventId=weeklyEventId;
       
       this.yearlyEventName = yearlyEventName;
       this.yearlyEventNumber=yearlyEventNumber;
       this.yearlyEventPeriod=yearlyEventPeriod;
       this.yearlyEventDueDate=yearlyEventDueDate;
       this.yearlyEventStatus=yearlyEventStatus;
       this.yearlyEventId=yearlyEventId;
       
       this.midYearEventName = midYearEventName;
       this.midYearEventNumber=midYearEventNumber;
       this.midYearEventPeriod=midYearEventPeriod;
       this.midYearEventDueDate=midYearEventDueDate;
       this.midYearEventStatus=midYearEventStatus;
       this.midYearEventId=midYearEventId;
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
			count=dailyEventName.size();
		}else if(groupPosition==1){
			count=weeklyEventName.size();
		}else if(groupPosition==2){
			count=monthlyEventName.size();
		}else if(groupPosition==3){
			count=yearlyEventName.size();
		}else if(groupPosition==4){
			count=midYearEventName.size();
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
			childDetails=new String[]{dailyEventName.get(childPosition),
									 dailyEventNumber.get(childPosition),
									 dailyEventPeriod.get(childPosition),
									 dailyEventDueDate.get(childPosition),
									 dailyEventStatus.get(childPosition),
									 dailyEventId.get(childPosition)};
		}else if(groupPosition==1){
			childDetails=new String[]{weeklyEventName.get(childPosition),
					weeklyEventNumber.get(childPosition),
					weeklyEventPeriod.get(childPosition),
					weeklyEventDueDate.get(childPosition),
					weeklyEventStatus.get(childPosition),
					weeklyEventId.get(childPosition)};
		}else if(groupPosition==2){
			childDetails=new String[]{monthlyEventName.get(childPosition),
					 monthlyEventNumber.get(childPosition),
					 monthlyEventPeriod.get(childPosition),
					 monthlyEventDueDate.get(childPosition),
					 monthlyEventStatus.get(childPosition),
					 monthlyEventId.get(childPosition)};
		}else if(groupPosition==3){
			childDetails=new String[]{yearlyEventName.get(childPosition),
					yearlyEventNumber.get(childPosition),
					yearlyEventPeriod.get(childPosition),
					yearlyEventDueDate.get(childPosition),
					yearlyEventStatus.get(childPosition),
					yearlyEventId.get(childPosition)};
		}else if(groupPosition==4){
			childDetails=new String[]{midYearEventName.get(childPosition),
					midYearEventNumber.get(childPosition),
					midYearEventPeriod.get(childPosition),
					midYearEventDueDate.get(childPosition),
					midYearEventStatus.get(childPosition),
					midYearEventId.get(childPosition)};
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
	   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
	   text.setText(dailyEventName.get(childPosition));
	   text2.setText(dailyEventNumber.get(childPosition));
	   text3.setText(dailyEventPeriod.get(childPosition));
	   text4.setText(dailyEventDueDate.get(childPosition));
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
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(weeklyEventName.get(childPosition));
		   text2.setText(weeklyEventNumber.get(childPosition));
		   text3.setText(weeklyEventPeriod.get(childPosition));
		   text4.setText(weeklyEventDueDate.get(childPosition));
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
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(monthlyEventName.get(childPosition));
		   text2.setText(monthlyEventNumber.get(childPosition));
		   text3.setText(monthlyEventPeriod.get(childPosition));
		   text4.setText(monthlyEventDueDate.get(childPosition));
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
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(yearlyEventName.get(childPosition));
		   text2.setText(yearlyEventNumber.get(childPosition));
		   text3.setText(yearlyEventPeriod.get(childPosition));
		   text4.setText(yearlyEventDueDate.get(childPosition));
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
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(midYearEventName.get(childPosition));
		   text2.setText(midYearEventNumber.get(childPosition));
		   text3.setText(midYearEventPeriod.get(childPosition));
		   text4.setText(midYearEventDueDate.get(childPosition));
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


