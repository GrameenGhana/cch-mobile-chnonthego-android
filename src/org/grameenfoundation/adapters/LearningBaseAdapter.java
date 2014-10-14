package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;

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

public class LearningBaseAdapter extends BaseExpandableListAdapter {

	 public String[] groupItem;
	 public ArrayList<String> ChildItemAntenatalCare;
	 public ArrayList<String> ChildItemPostnatalCare;
	 public ArrayList<String> ChildItemFamilyPlanning;
	 public ArrayList<String> ChildItemChildHealth;
	 public ArrayList<String> ChildItemGeneral;
	 public ArrayList<String> ChildItemOther;
	 public ExpandableListView learningList;
	 public int[] imageId;
	 public LayoutInflater minflater;
	 private int count;
	 public int lastExpandedGroupPosition;    
	 private Context mContext;

	 public LearningBaseAdapter(Context mContext,String[] grList,//ArrayList<String>ChildItemAntenatalCare,
			// ArrayList<String>ChildItemPostnatalCare,
			 ArrayList<String>ChildItemFamilyPlanning,
			// ArrayList<String>ChildItemChildHealth,
			// ArrayList<String>ChildItemGeneral,
			 //ArrayList<String>ChildItemOther,
			 int[] imageId, ExpandableListView learningList) {
	  groupItem = grList;
	  this.mContext=mContext;
	  minflater = LayoutInflater.from(mContext);
	  //this.ChildItemAntenatalCare = ChildItemAntenatalCare;
	  //this.ChildItemPostnatalCare = ChildItemPostnatalCare;
	  this.ChildItemFamilyPlanning=ChildItemFamilyPlanning;
	  //this.ChildItemChildHealth = ChildItemChildHealth;
	  //this.ChildItemGeneral=ChildItemGeneral;
	  //this.ChildItemOther=ChildItemOther;
	  this.imageId=imageId;
	  this.learningList=learningList;
	 
	 }
	 @Override
	 public long getChildId(int groupPosition, int childPosition) {
	  return 0;
	 }

	 @Override
	 public View getChildView(int groupPosition, final int childPosition,
	   boolean isLastChild, View convertView, ViewGroup parent) {
		 Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
	       	      "fonts/Roboto-Thin.ttf");
	  
	   if(convertView==null){
		   convertView=minflater.inflate(R.layout.other_listview_single,null);
	   }
	   TextView text=(TextView) convertView.findViewById(R.id.textView_otherCategory);
	   switch(groupPosition){
	   case 0:
		   text.setText(ChildItemFamilyPlanning.get(childPosition)); 
		   //text.setText(ChildItemAntenatalCare.get(childPosition));
		   text.setTypeface(custom_font);
		   break;
		   /*
	   case 1:
		  // TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		   text.setText(ChildItemPostnatalCare.get(childPosition)); 
		   text.setTypeface(custom_font);
		   break;
	   case 2:
		   text.setText(ChildItemFamilyPlanning.get(childPosition)); 
		   text.setTypeface(custom_font);
		   break;
	   case 3:
		   text.setText(ChildItemChildHealth.get(childPosition)); 
		   text.setTypeface(custom_font);
		   break;
	   case 4:
		   text.setText(ChildItemGeneral.get(childPosition)); 
		   text.setTypeface(custom_font);		   
		   break;
	   case 5:
		   text.setText(ChildItemOther.get(childPosition)); 
		   text.setTypeface(custom_font);
		   break;
		   */
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
	   //category.setTypeface(custom_font);
	   
	  return convertView;
	 }

	 
	 																																				
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupItem.length;
	}


	@Override
	public int getChildrenCount(int groupPosition) {
		switch(groupPosition){
		case 0:
			count=ChildItemFamilyPlanning.size();	
			//count=ChildItemAntenatalCare.size();
			break;
			/*
		case 1:
			count=ChildItemPostnatalCare.size();
			break;
		case 2:
			count=ChildItemFamilyPlanning.size();	
			break;
		case 3:
			count=ChildItemChildHealth.size();
			break;
		case 4:
			count=ChildItemGeneral.size();	
			break;
		case 5:
			count=ChildItemOther.size();
			break;
			*/
		}
		return count;
	}


	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}



	@Override
	public String getChild(int groupPosition, int childPosition) {
		String item=null;
		switch(groupPosition){
		case 0:
			item=ChildItemFamilyPlanning.get(childPosition);
			//item=ChildItemAntenatalCare.get(childPosition);
			break;
			/*
		case 1:
			item=ChildItemPostnatalCare.get(childPosition);
			break;
		case 2:
			item=ChildItemFamilyPlanning.get(childPosition);
			break;
		case 3:
			item=ChildItemChildHealth.get(childPosition);
			break;
		case 4:
			item=ChildItemGeneral.get(childPosition);
			break;
		case 5:
			item=ChildItemOther.get(childPosition);
			break;
			*/
		}
		return item;
	}



	@Override
	public boolean hasStableIds() {
		return true;
	}



	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	public void onGroupExpanded(int groupPosition) {
   	
   	if(groupPosition != lastExpandedGroupPosition){
           learningList.collapseGroup(lastExpandedGroupPosition);
      
   }
   	
       super.onGroupExpanded(groupPosition);
    
       lastExpandedGroupPosition = groupPosition;
       
   }
	}
