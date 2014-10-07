package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.grameenfoundation.chnonthego.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class CoverageListAdapter extends BaseExpandableListAdapter {

	 public String[] groupItem;
	 public ArrayList<String> ChildItemPeopleTarget;
	 public ArrayList<String> ChildItemPeopleNumber;
	 public ArrayList<String> ChildItemPeoplePeriod;
	 
	 public ArrayList<String> ChildItemImmunizationTarget;
	 public ArrayList<String> ChildItemImmunizationNumber;
	 public ArrayList<String> ChildItemImmunizationPeriod;
	 public ArrayList<String> ChildItemPeopleId;
	 public ArrayList<String> ChildItemImmunizationId;
	 public ExpandableListView coverageList;
	 public int[] imageId;
	 public LayoutInflater minflater;
	 private int count;
	 public int lastExpandedGroupPosition;    
	 private Context mContext;
	

	 public CoverageListAdapter(Context mContext,String[] grList,
			 ArrayList<String> ChildItemPeopleTarget,
			 ArrayList<String> ChildItemPeopleNumber,
			 ArrayList<String> ChildItemPeoplePeriod,
			 ArrayList<String> ChildItemImmunizationTarget,
			 ArrayList<String> ChildItemImmunizationNumber,
			 ArrayList<String> ChildItemImmunizationPeriod,
			 ArrayList<String> childItemPeopleId,
			 ArrayList<String> ChildItemImmunizationId,
			 int[] imageId,
			 ExpandableListView coverageList) {
	  groupItem = grList;
	  this.mContext=mContext;
	  minflater = LayoutInflater.from(mContext);
	  this.ChildItemPeopleTarget = ChildItemPeopleTarget;
	  this.ChildItemPeopleNumber=ChildItemPeopleNumber;
	  this.ChildItemPeoplePeriod=ChildItemPeoplePeriod;
	  this.ChildItemImmunizationTarget=ChildItemImmunizationTarget;
	  this.ChildItemImmunizationNumber=ChildItemImmunizationNumber;
	  this.ChildItemImmunizationPeriod=ChildItemImmunizationPeriod;
	  this.ChildItemPeopleId = childItemPeopleId;
	  this.ChildItemImmunizationId=ChildItemImmunizationId;
	  this.imageId=imageId;
	  this.coverageList=coverageList;
	 
	 }


	
	 @Override
	 public long getChildId(int groupPosition, int childPosition) {
		 long id = 0;
			if(groupPosition==0){
			id=Integer.valueOf(ChildItemPeopleId.get(childPosition));	
			}else if(groupPosition==1){
				id=Integer.valueOf(ChildItemImmunizationId.get(childPosition));		
			}
		return id;
	 }

	 @Override
	 public View getChildView(int groupPosition, final int childPosition,
	   boolean isLastChild, View convertView, ViewGroup parent) {
		 Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
	       	      "fonts/Roboto-Thin.ttf");
	  
	   if(convertView==null){
		   convertView=minflater.inflate(R.layout.coverage_expandable_child_single,null);
	   }
	   if(groupPosition==0){
	   TextView text=(TextView) convertView.findViewById(R.id.textView_coverageCategory);
	   TextView text2=(TextView) convertView.findViewById(R.id.textView_coverageNumber);
	   TextView text3=(TextView) convertView.findViewById(R.id.textView_coveragePeriod);
	   text.setText(ChildItemPeopleTarget.get(childPosition));
	   text2.setText(ChildItemPeopleNumber.get(childPosition));
	   text3.setText(ChildItemPeoplePeriod.get(childPosition));
	   text.setTypeface(custom_font);
	   text2.setTypeface(custom_font);
	   text3.setTypeface(custom_font);
	   }else if(groupPosition==1){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_coverageCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_coverageNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_coveragePeriod);
		   text.setText(ChildItemImmunizationTarget.get(childPosition));
		   text2.setText(ChildItemImmunizationNumber.get(childPosition));
		   text3.setText(ChildItemImmunizationPeriod.get(childPosition));
		   text.setTypeface(custom_font);
		   text2.setTypeface(custom_font);
		   text3.setTypeface(custom_font);
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
	  if (convertView == null) {
	   convertView = minflater.inflate(R.layout.expandable_group_single,parent, false);
	  }
	   
	   TextView category=(TextView) convertView.findViewById(R.id.textView_groupCategory);
	   category.setText(groupItem[groupPosition]);
	   ImageView categoryImage=(ImageView) convertView.findViewById(R.id.imageView_groupImage);
	   categoryImage.setImageResource(imageId[groupPosition]);
	   
	   Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
       	      "fonts/Roboto-Thin.ttf");
	   category.setTypeface(custom_font);
	  return convertView;
	 }

	 
	 																																				
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupItem.length;
	}


	@Override
	public int getChildrenCount(int groupPosition) {
		if(groupPosition==0){
		count=ChildItemPeopleTarget.size();
		}else if (groupPosition==1){
		count=ChildItemImmunizationTarget.size();
		}
		return count;
	}


	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public String[] getChild(int groupPosition, int childPosition) {
		String[] item = null;
		switch (groupPosition){
		case 0:
			item= new String[]{ChildItemPeopleTarget.get(childPosition),ChildItemPeopleNumber.get(childPosition)};
			break;
		case 1:
			item= new String[]{ChildItemImmunizationTarget.get(childPosition),ChildItemImmunizationNumber.get(childPosition)};
			break;
		}
		return item;
			
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

	public void onGroupExpanded(int groupPosition) {
    	
    	if(groupPosition != lastExpandedGroupPosition){
            coverageList.collapseGroup(lastExpandedGroupPosition);
       
    }
    	
        super.onGroupExpanded(groupPosition);
     
        lastExpandedGroupPosition = groupPosition;
        
    }
	}
