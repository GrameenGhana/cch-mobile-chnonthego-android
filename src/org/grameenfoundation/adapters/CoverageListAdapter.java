package org.grameenfoundation.adapters;

import java.util.ArrayList;



import java.util.HashMap;
import java.util.Set;

import org.digitalcampus.mobile.learningGF.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class CoverageListAdapter extends BaseExpandableListAdapter {

	 public String[] groupItem;
	 private final ArrayList<String> dailyCoverageName;
	 private final ArrayList<String> dailyCoverageNumber;
	 private final ArrayList<String> dailyCoveragePeriod;
	 private final ArrayList<String> dailyCoverageDueDate;
	 private final ArrayList<String> dailyCoverageStatus;
	 private final ArrayList<String> dailyCoverageId;
	 
	 private final ArrayList<String> weeklyCoverageName;
	 private final ArrayList<String> weeklyCoverageNumber;
	 private final ArrayList<String> weeklyCoveragePeriod;
	 private final ArrayList<String> weeklyCoverageDueDate;
	 private final ArrayList<String> weeklyCoverageStatus;
	 private final ArrayList<String> weeklyCoverageId;
	 
	 private final ArrayList<String> monthlyCoverageName;
	 private final ArrayList<String> monthlyCoverageNumber;
	 private final ArrayList<String> monthlyCoveragePeriod;
	 private final ArrayList<String> monthlyCoverageDueDate;
	 private final ArrayList<String> monthlyCoverageStatus;
	 private final ArrayList<String> monthlyCoverageId;
	 
	 private final ArrayList<String> yearlyCoverageName;
	 private final ArrayList<String> yearlyCoverageNumber;
	 private final ArrayList<String> yearlyCoveragePeriod;
	 private final ArrayList<String> yearlyCoverageDueDate;
	 private final ArrayList<String> yearlyCoverageStatus;
	 private final ArrayList<String> yearlyCoverageId;
	 
	 private final ArrayList<String> midYearCoverageName;
	 private final ArrayList<String> midYearCoverageNumber;
	 private final ArrayList<String> midYearCoveragePeriod;
	 private final ArrayList<String> midYearCoverageDueDate;
	 private final ArrayList<String> midYearCoverageStatus;
	 private final ArrayList<String> midYearCoverageId;
	 private String[] groupItems;
	 public LayoutInflater minflater;
	 private int count;
	 public int lastExpandedGroupPosition;    
	 ExpandableListView coverage_list;
	 private Context mContext;
	

	 public CoverageListAdapter(Context mContext,ArrayList<String> dailyCoverageName ,
				ArrayList<String> dailyCoverageNumber,
				ArrayList<String> dailyCoveragePeriod,
				ArrayList<String> dailyCoverageDueDate,
				ArrayList<String> dailyCoverageStatus,
				ArrayList<String> dailyCoverageId,
				
				ArrayList<String> weeklyCoverageName,
				ArrayList<String> weeklyCoverageNumber,
				ArrayList<String> weeklyCoveragePeriod,
				ArrayList<String> weeklyCoverageDueDate,
				ArrayList<String> weeklyCoverageStatus,
				ArrayList<String> weeklyCoverageId,
				
				ArrayList<String> monthlyCoverageName,
				ArrayList<String> monthlyCoverageNumber,
				ArrayList<String> monthlyCoveragePeriod,
				ArrayList<String> monthlyCoverageDueDate,
				ArrayList<String> monthlyCoverageStatus,
				ArrayList<String> monthlyCoverageId,
				
				ArrayList<String> yearlyCoverageName,
				ArrayList<String> yearlyCoverageNumber,
				ArrayList<String> yearlyCoveragePeriod,
				ArrayList<String> yearlyCoverageDueDate,
				ArrayList<String> yearlyCoverageStatus,
				ArrayList<String> yearlyCoverageId,
				
				ArrayList<String> midYearCoverageName,
				ArrayList<String> midYearCoverageNumber,
				ArrayList<String> midYearCoveragePeriod,
				ArrayList<String> midYearCoverageDueDate,
				ArrayList<String> midYearCoverageStatus,
				ArrayList<String> midYearCoverageId,
				String[] groupItems,
				ExpandableListView coverage_list) {
		 		mContext = mContext;
		 		this.dailyCoverageName = dailyCoverageName;
		 		this.dailyCoverageNumber=dailyCoverageNumber;
		 		this.dailyCoveragePeriod=dailyCoveragePeriod;
		 		this.dailyCoverageDueDate=dailyCoverageDueDate;
		 		this.dailyCoverageStatus=dailyCoverageStatus;
		 		this.dailyCoverageId=dailyCoverageId;
		 		minflater = LayoutInflater.from(mContext);

		 		this.monthlyCoverageName = monthlyCoverageName;
		 		this.monthlyCoverageNumber=monthlyCoverageNumber;
		 		this.monthlyCoveragePeriod=monthlyCoveragePeriod;
		 		this.monthlyCoverageDueDate=monthlyCoverageDueDate;
		 		this.monthlyCoverageStatus=monthlyCoverageStatus;
		 		this.monthlyCoverageId=monthlyCoverageId;

		 		this.weeklyCoverageName = weeklyCoverageName;
		 		this.weeklyCoverageNumber=weeklyCoverageNumber;
		 		this.weeklyCoveragePeriod=weeklyCoveragePeriod;
		 		this.weeklyCoverageDueDate=weeklyCoverageDueDate;
		 		this.weeklyCoverageStatus=weeklyCoverageStatus;
		 		this.weeklyCoverageId=weeklyCoverageId;

		 		this.yearlyCoverageName = yearlyCoverageName;
		 		this.yearlyCoverageNumber=yearlyCoverageNumber;
		 		this.yearlyCoveragePeriod=yearlyCoveragePeriod;
		 		this.yearlyCoverageDueDate=yearlyCoverageDueDate;
		 		this.yearlyCoverageStatus=yearlyCoverageStatus;
		 		this.yearlyCoverageId=yearlyCoverageId;

		 		this.midYearCoverageName = midYearCoverageName;
		 		this.midYearCoverageNumber=midYearCoverageNumber;
		 		this.midYearCoveragePeriod=midYearCoveragePeriod;
		 		this.midYearCoverageDueDate=midYearCoverageDueDate;
		 		this.midYearCoverageStatus=midYearCoverageStatus;
		 		this.midYearCoverageId=midYearCoverageId;
		 		this.groupItems=groupItems;
		 		this.coverage_list=coverage_list;
	 
	 }


	
	 @Override
	 public long getChildId(int groupPosition, int childPosition) {
		 long id=0;
		 /*
			if(groupPosition==0){
				id=Integer.valueOf(dailyCoverageId.get(childPosition));
			}else if(groupPosition==1){
				id=Integer.valueOf(weeklyCoverageId.get(childPosition));
			}else if(groupPosition==2){
				id=Integer.valueOf(monthlyCoverageId.get(childPosition));
			}else if(groupPosition==3){
				id=Integer.valueOf(yearlyCoverageId.get(childPosition));
			}else if(groupPosition==4){
				id=Integer.valueOf(midYearCoverageId.get(childPosition));
			}
			*/
			return id;
	 }

	 @Override
	 public View getChildView(int groupPosition, final int childPosition,
	   boolean isLastChild, View convertView, ViewGroup parent) {
		 if (convertView == null) {
			   convertView = minflater.inflate(R.layout.coverage_expandable_child_single,parent, false);
			  }
	  
	   
	   if(groupPosition==0){
	   TextView text=(TextView) convertView.findViewById(R.id.textView_coverageCategory);
	   TextView text2=(TextView) convertView.findViewById(R.id.textView_coverageNumber);
	   TextView text3=(TextView) convertView.findViewById(R.id.textView_coveragePeriod);
	   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
	   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
	   text.setText(dailyCoverageName.get(childPosition));
	   text2.setText(dailyCoverageNumber.get(childPosition));
	   text3.setText(dailyCoveragePeriod.get(childPosition));
	   text4.setText(dailyCoverageDueDate.get(childPosition));
	   }else if(groupPosition==1){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_coverageCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_coverageNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_coveragePeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(weeklyCoverageName.get(childPosition));
		   text2.setText(weeklyCoverageNumber.get(childPosition));
		   text3.setText(weeklyCoveragePeriod.get(childPosition));
		   text4.setText(weeklyCoverageDueDate.get(childPosition));
	   }else if(groupPosition==2){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_coverageCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_coverageNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_coveragePeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(monthlyCoverageName.get(childPosition));
		   text2.setText(monthlyCoverageNumber.get(childPosition));
		   text3.setText(monthlyCoveragePeriod.get(childPosition));
		   text4.setText(monthlyCoverageDueDate.get(childPosition));
	   }else if(groupPosition==3){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_coverageCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_coverageNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_coveragePeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(yearlyCoverageName.get(childPosition));
		   text2.setText(yearlyCoverageNumber.get(childPosition));
		   text3.setText(yearlyCoveragePeriod.get(childPosition));
		   text4.setText(yearlyCoverageDueDate.get(childPosition));
	   }else if(groupPosition==4){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_coverageCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_coverageNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_coveragePeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(midYearCoverageName.get(childPosition));
		   text2.setText(midYearCoverageNumber.get(childPosition));
		   text3.setText(midYearCoveragePeriod.get(childPosition));
		   text4.setText(midYearCoverageDueDate.get(childPosition));
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
			   convertView = minflater.inflate(R.layout.listview_single,parent, false);
			  }
			   
			   TextView category=(TextView) convertView.findViewById(R.id.textView_textSingle);
			   category.setText(groupItems[groupPosition]);
			   
			   
			  return convertView;
	 }

	 
	 																																				
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupItems.length;
	}


	@Override
	public int getChildrenCount(int groupPosition) {
		int count = 0;
		if(groupPosition==0){
			count=dailyCoverageName.size();
		}else if(groupPosition==1){
			count=weeklyCoverageName.size();
		}else if(groupPosition==2){
			count=monthlyCoverageName.size();
		}else if(groupPosition==3){
			count=yearlyCoverageName.size();
		}else if(groupPosition==4){
			count=midYearCoverageName.size();
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
		String[] childDetails = null;
		if(groupPosition==0){
			childDetails=new String[]{dailyCoverageName.get(childPosition),
									 dailyCoverageNumber.get(childPosition),
									 dailyCoveragePeriod.get(childPosition),
									 dailyCoverageDueDate.get(childPosition),
									 dailyCoverageStatus.get(childPosition),
									 dailyCoverageId.get(childPosition)};
		}else if(groupPosition==1){
			childDetails=new String[]{weeklyCoverageName.get(childPosition),
					weeklyCoverageNumber.get(childPosition),
					weeklyCoveragePeriod.get(childPosition),
					weeklyCoverageDueDate.get(childPosition),
					weeklyCoverageStatus.get(childPosition),
					weeklyCoverageId.get(childPosition)};
		}else if(groupPosition==2){
			childDetails=new String[]{monthlyCoverageName.get(childPosition),
					 monthlyCoverageNumber.get(childPosition),
					 monthlyCoveragePeriod.get(childPosition),
					 monthlyCoverageDueDate.get(childPosition),
					 monthlyCoverageStatus.get(childPosition),
					 monthlyCoverageId.get(childPosition)};
		}else if(groupPosition==3){
			childDetails=new String[]{yearlyCoverageName.get(childPosition),
					yearlyCoverageNumber.get(childPosition),
					yearlyCoveragePeriod.get(childPosition),
					yearlyCoverageDueDate.get(childPosition),
					yearlyCoverageStatus.get(childPosition),
					yearlyCoverageId.get(childPosition)};
		}else if(groupPosition==4){
			childDetails=new String[]{midYearCoverageName.get(childPosition),
					midYearCoverageNumber.get(childPosition),
					midYearCoveragePeriod.get(childPosition),
					midYearCoverageDueDate.get(childPosition),
					midYearCoverageStatus.get(childPosition),
					midYearCoverageId.get(childPosition)};
		}
		return childDetails;
			
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
            coverage_list.collapseGroup(lastExpandedGroupPosition);
       
    }
    	
        super.onGroupExpanded(groupPosition);
     
        lastExpandedGroupPosition = groupPosition;
        
    }
*/

	}
