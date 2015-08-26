package org.grameenfoundation.adapters;


	import java.util.ArrayList;

	import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.LearningTargets;
import org.grameenfoundation.cch.model.TargetsForAchievements;

	import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

	public class LearningTargetAchievementsAdapter extends BaseExpandableListAdapter {

		
		 public String[] groupItem;
		 public ArrayList<EventTargets> CompletedTargets;
		 public ArrayList<EventTargets> UnCompletedTargets;
		 public ExpandableListView eventsList;
		 public LayoutInflater minflater;
		 private int count;
		 public int lastExpandedGroupPosition;    
		 private Context mContext;
		private DbHelper dbh;
		private ArrayList<EventTargets> completedTargets;
		private ArrayList<EventTargets> unCompletedTargets;
		EventTargets calendarEvents=new EventTargets();

		 public LearningTargetAchievementsAdapter(Context mContext,String[] grList,
				 					ArrayList<EventTargets> CompletedTargets,
				 					ArrayList<EventTargets> UnCompletedTargets,
				 					ExpandableListView eventsList) {
			 dbh = new DbHelper(mContext);
			 
			 completedTargets = new ArrayList<EventTargets>();
			 unCompletedTargets = new ArrayList<EventTargets>();
			 
			 completedTargets.addAll(CompletedTargets);
			 unCompletedTargets.addAll(UnCompletedTargets);
		  groupItem = grList;
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
		   if(convertView==null){
			   convertView=minflater.inflate(R.layout.achievements_learning_target_single,null);
		   }
		   if(groupPosition==0){
			  
		   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
		  // TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   //TextView text6=(TextView) convertView.findViewById(R.id.textView_achieved);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   //TextView text8=(TextView) convertView.findViewById(R.id.textView_percentageAchieved);
		   text.setText(completedTargets.get(childPosition).getEventTargetType());
		   //text2.setText(" ");
		   if(completedTargets.get(childPosition).getEventTargetStatus().equalsIgnoreCase("updated")){
			   text3.setTextColor(Color.GREEN);
			   text3.setText("Completed");
		   }else{
			   text3.setTextColor(Color.RED);
			   text3.setText("In progress"); 
		   }
		   text4.setText(completedTargets.get(childPosition).getEventTargetEndDate());
		   text5.setText(completedTargets.get(childPosition).getEventTargetStartDate());
		   text7.setText(completedTargets.get(childPosition).getEventTargetLastUpdated());
		  // text6.setText(" ");
		  //text8.setText(" ");
		   }else if(groupPosition==1){
			   TextView text=(TextView) convertView.findViewById(R.id.textView_eventCategory);
			   //TextView text2=(TextView) convertView.findViewById(R.id.textView_eventNumber);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView_eventPeriod);
			   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
			   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
			   //TextView text6=(TextView) convertView.findViewById(R.id.textView_achieved);
			   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
			   //TextView text8=(TextView) convertView.findViewById(R.id.textView_percentageAchieved);
			   text.setText(unCompletedTargets.get(childPosition).getEventTargetType());
			   //text2.setText(" ");
			   if(unCompletedTargets.get(childPosition).getEventTargetStatus().equalsIgnoreCase("updated")){
				   text3.setTextColor(Color.GREEN);
				   text3.setText("Completed");
			   }else{
				   text3.setTextColor(Color.RED);
				   text3.setText("In progress"); 
			   }
			   text4.setText(unCompletedTargets.get(childPosition).getEventTargetEndDate());
			   text5.setText(unCompletedTargets.get(childPosition).getEventTargetStartDate());
			   text7.setText(unCompletedTargets.get(childPosition).getEventTargetLastUpdated());
			   //text6.setText(" ");
			   //text8.setText(" ");
			   
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
				   convertView = minflater.inflate(R.layout.other_listview_single,parent, false);
				  }
				   
				   TextView category=(TextView) convertView.findViewById(R.id.textView_otherCategory);
				   category.setText(groupItem[groupPosition]);
				   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
				   if(groupPosition==0){
					   image.setImageResource(R.drawable.ic_complete_new);
				   }else if(groupPosition==1){
					   image.setImageResource(R.drawable.ic_close);
				   }
				  return convertView;
		 }

		 
		 																																				
		@Override
		public int getGroupCount() {
			return groupItem.length;
		}


		@Override
		public int getChildrenCount(int groupPosition) {
			int count = 0;
			if(groupPosition==0){
				count=completedTargets.size();
			}else if(groupPosition==1){
				count=unCompletedTargets.size();
			}
			return count;
		}


		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}



		@Override
		public String[] getChild(int groupPosition, int childPosition) {
			String[] childDetails = null;

			return childDetails;
				
		}



		@Override
		public boolean hasStableIds() {
			return true;
		}



		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
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

