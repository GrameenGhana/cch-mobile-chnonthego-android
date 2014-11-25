package org.grameenfoundation.poc;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.EventsViewActivity;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class BreastProblemsCounsellingNextActivity extends BaseActivity {

	private ExpandableListView expandableListView_breastProblems;
//	 private Context mContext;
	 private DbHelper dbh;
		private Long start_time;
		private Long end_time;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_breast_counselling_next);
	    mContext=BreastProblemsCounsellingNextActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling:  Breast Problems");
	   expandableListView_breastProblems=(ExpandableListView) findViewById(R.id.expandableListView_breastProblems);
	   String[] groupItems={"Breast Engorgement","Cracked/Sore Nipples","Mastitis"};
	   String[] firstItems={"Continue breastfeeding","Make sure baby correctly position and attach properly to your breast",
			   				"Empty breasts by expressing breast milk","Apply warm compress before feeds and cold compress in-between feeds",
			   				"Breastfeed more frequently "};
	   String[] secondItems={"Continue breastfeeding"," Make sure baby correctly position and attach properly to your breast",
			   				"Apply breast milk to affected nipple after feeding and air dry"};
	   String[] thirdItems={"Continue breastfeeding","Make sure baby correctly position and attach properly to your breast",
			   				"Empty breasts by expressing breast milk",
			   				"Apply warm compress before feeds and cold compress in-between feeds",
			   				"Breastfeed more frequently, once mother starts treatment"};
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
		   CharSequence t1 = firstItems[childPosition];
		   SpannableString s1 = new SpannableString(t1);
		   s1.setSpan(new BulletSpan(15), 0, t1.length(), 0);
		   text.setText(s1);
		   text.setTypeface(custom_font);
		   //text.setGravity(Gravity.LEFT);
		   text.setTextColor(getResources().getColor(R.color.TextBrown));
		   }else if(groupPosition==1){
			   TextView text=(TextView) convertView.findViewById(R.id.textView_textSingle);
			   CharSequence t2 = secondItems[childPosition];
			   SpannableString s2 = new SpannableString(t2);
			   s2.setSpan(new BulletSpan(15), 0, t2.length(), 0);
			   text.setText(s2);
			   //text.setGravity(Gravity.LEFT);
			   text.setTypeface(custom_font);
			   text.setTextColor(getResources().getColor(R.color.TextBrown));
		   }else if(groupPosition==2){
			   TextView text=(TextView) convertView.findViewById(R.id.textView_textSingle);
			   CharSequence t3= thirdItems[childPosition];
			   SpannableString s3 = new SpannableString(t3);
			   s3.setSpan(new BulletSpan(15), 0, t3.length(), 0);
			   text.setText(s3);
			  
			   text.setTypeface(custom_font);
			   text.setTextColor(getResources().getColor(R.color.TextBrown));
			   //text.setGravity(Gravity.LEFT);
		   }
		   if(childPosition%2==0){
				 convertView.setBackgroundColor(getResources().getColor(R.color.White));
				// convertView.setGravity(Gravity.LEFT);
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

	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Counselling Breast Problems", start_time.toString(), end_time.toString());
		finish();
	}
}
