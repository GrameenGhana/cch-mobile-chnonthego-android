package org.grameenfoundation.poc;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.EventsViewActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class BreastProblemsCounsellingNextActivity extends Activity {

	private ExpandableListView expandableListView_breastProblems;
	 private Context mContext;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_breast_counselling_next);
	    mContext=BreastProblemsCounsellingNextActivity.this;
	   expandableListView_breastProblems=(ExpandableListView) findViewById(R.id.expandableListView_breastProblems);
	   String[] groupItems={"1. Breast Engorgement","2. Cracked/Sore Nipples","3. Mastitis"};
	   String[] firstItems={"1. Continue breastfeeding","2. Make sure baby correctly position and attach properly to your breast",
			   				"3. Empty breasts by expressing breast milk","4. Apply warm compress before feeds and cold compress in-between feeds",
			   				"5.	Breastfeed more frequently "};
	   String[] secondItems={"1. Continue breastfeeding","2. Make sure baby correctly position and attach properly to your breast",
			   				"3. Apply breast milk to affected nipple after feeding and air dry"};
	   String[] thirdItems={"1. Continue breastfeeding","2. Make sure baby correctly position and attach properly to your breast",
			   				"3. Empty breasts by expressing breast milk",
			   				"4. Apply warm compress before feeds and cold compress in-between feeds",
			   				"5. Breastfeed more frequently, once mother starts treatment"};
	   ListAdapter adapter=new ListAdapter(mContext,groupItems,firstItems,secondItems,thirdItems,expandableListView_breastProblems);
	   expandableListView_breastProblems.setAdapter(adapter);
	}
	class ListAdapter extends BaseExpandableListAdapter {

		 public String[] groupItem;
		 String[] firstItems;
		 String[] secondItems;
		 String[] thirdItems;
		 public ExpandableListView eventsList;
		 public LayoutInflater minflater;
		 private int count;
		 public int lastExpandedGroupPosition;    
		 private Context mContext;
		

		 public ListAdapter(Context mContext,String[] grList,
				 					String[] firstItems,
				 					String[] secondItems,
				 					String[] thirdItems,
				 					ExpandableListView eventsList) {
		  groupItem = grList;
		  this.firstItems=firstItems;
		  this.secondItems=secondItems;
		  this.thirdItems=thirdItems;
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
			 Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
		       	      "fonts/Roboto-Thin.ttf");
		  
		   if(convertView==null){
			   convertView=minflater.inflate(R.layout.listview_single,null);
		   }
		   if(groupPosition==0){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_textSingle);
		   text.setText(firstItems[childPosition]);
		   text.setTypeface(custom_font);
		   text.setGravity(Gravity.LEFT);
		   text.setTextColor(Color.BLACK);
		   }else if(groupPosition==1){
			   TextView text=(TextView) convertView.findViewById(R.id.textView_textSingle);
			   text.setText(secondItems[childPosition]);
			   text.setGravity(Gravity.LEFT);
			   text.setTypeface(custom_font);
		   }else if(groupPosition==2){
			   TextView text=(TextView) convertView.findViewById(R.id.textView_textSingle);
			   text.setText(thirdItems[childPosition]);
			   text.setTypeface(custom_font);
			   text.setGravity(Gravity.LEFT);
		   }
		   if(childPosition%2==0){
				 convertView.setBackgroundColor(getResources().getColor(R.color.White));
			 }else{
				 convertView.setBackgroundColor(getResources().getColor(R.color.BackgroundGrey));
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
			      
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			 text.setText(groupItem[groupPosition]);
			 text.setGravity(Gravity.LEFT);
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
			count=firstItems.length;
			}else if (groupPosition==1){
			count=secondItems.length;
			}else if(groupPosition==2){
				count=thirdItems.length;	
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
	            eventsList.collapseGroup(lastExpandedGroupPosition);
	       
	    }
	    	
	        super.onGroupExpanded(groupPosition);
	     
	        lastExpandedGroupPosition = groupPosition;
	        
	    }
	} 

	
}
