package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.model.LearningTargets;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class LearningTargetAdapter extends BaseExpandableListAdapter{
	 public String[] groupItem;
	 public ArrayList<LearningTargets> DailyLearningTargets;
	 public ArrayList<LearningTargets> WeeklyLearningTargets;
	 public ArrayList<LearningTargets> MonthlyLearningTargets;
	 public ArrayList<LearningTargets> QuarterlyLearningTargets;
	 public ArrayList<LearningTargets> MidyearLearningTargets;
	 public ArrayList<LearningTargets> AnnualLearningTargets;
	 public ExpandableListView LearningsList;
	 public LayoutInflater minflater;
	 private int count;
	 public int lastExpandedGroupPosition;    
	 private Context mContext;
	private DbHelper dbh;
	private ArrayList<LearningTargets> dailyLearningTarget;
	private ArrayList<LearningTargets> weeklyLearningTargets;
	private ArrayList<LearningTargets> monthlyLearningTargets;
	private ArrayList<LearningTargets> quarterlyLearningTargets;
	private ArrayList<LearningTargets> midYearLearningTargets;
	private ArrayList<LearningTargets> annualLearningTargets;
	LearningTargets calendarLearnings=new LearningTargets();

	 public LearningTargetAdapter(Context mContext,String[] grList,
			 					ArrayList<LearningTargets> DailyLearningTargets,
			 					ArrayList<LearningTargets> WeeklyLearningTargets,
			 					ArrayList<LearningTargets> MonthlyLearningTargets,
			 					ArrayList<LearningTargets> QuarterlyLearningTargets,
			 					ArrayList<LearningTargets> MidyearLearningTargets,
			 					ArrayList<LearningTargets> AnnualLearningTargets,
			 					ExpandableListView LearningsList) {
		 dbh = new DbHelper(mContext);
		 
		 dailyLearningTarget = new ArrayList<LearningTargets>();
		 weeklyLearningTargets = new ArrayList<LearningTargets>();
		 monthlyLearningTargets = new ArrayList<LearningTargets>();
		 quarterlyLearningTargets=new ArrayList<LearningTargets>();
		 midYearLearningTargets = new ArrayList<LearningTargets>();
		 annualLearningTargets = new ArrayList<LearningTargets>();
		 
		 dailyLearningTarget.addAll(DailyLearningTargets);
		 weeklyLearningTargets.addAll(WeeklyLearningTargets);
		 monthlyLearningTargets.addAll(MonthlyLearningTargets);
		 quarterlyLearningTargets.addAll(QuarterlyLearningTargets);
		 midYearLearningTargets.addAll(MidyearLearningTargets);
		 annualLearningTargets.addAll(AnnualLearningTargets);
	  groupItem = grList;
	  this.mContext=mContext;
	  minflater = LayoutInflater.from(mContext);
	  this.LearningsList=LearningsList;
	 
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
		   text.setText(dailyLearningTarget.get(childPosition).getLearningTargetName());
		   text2.setText(dailyLearningTarget.get(childPosition).getLearningTargetTopic());
		   text3.setText(dailyLearningTarget.get(childPosition).getLearningTargetCourse());
		   text4.setText(dailyLearningTarget.get(childPosition).getLearningTargetEndDate());
		   text5.setText(dailyLearningTarget.get(childPosition).getLearningTargetStartDate());
		   text6.setText(dailyLearningTarget.get(childPosition).getLearningTargetPeriod());
		   text7.setText(dailyLearningTarget.get(childPosition).getLearningTargetLastUpdated());
		   
		   
	   }else if(groupPosition==1){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_learningCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_learningCourse);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_learningTopic);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_period);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(weeklyLearningTargets.get(childPosition).getLearningTargetName());
		   text2.setText(weeklyLearningTargets.get(childPosition).getLearningTargetTopic());
		   text3.setText(weeklyLearningTargets.get(childPosition).getLearningTargetCourse());
		   text4.setText(weeklyLearningTargets.get(childPosition).getLearningTargetEndDate());
		   text5.setText(weeklyLearningTargets.get(childPosition).getLearningTargetStartDate());
		   text6.setText(weeklyLearningTargets.get(childPosition).getLearningTargetPeriod());
		   text7.setText(weeklyLearningTargets.get(childPosition).getLearningTargetLastUpdated());
		   
	   }else if(groupPosition==2){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_learningCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_learningCourse);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_learningTopic);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_period);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(monthlyLearningTargets.get(childPosition).getLearningTargetName());
		   text2.setText(monthlyLearningTargets.get(childPosition).getLearningTargetTopic());
		   text3.setText(monthlyLearningTargets.get(childPosition).getLearningTargetCourse());
		   text4.setText(monthlyLearningTargets.get(childPosition).getLearningTargetEndDate());
		   text5.setText(monthlyLearningTargets.get(childPosition).getLearningTargetStartDate());
		   text6.setText(monthlyLearningTargets.get(childPosition).getLearningTargetPeriod());
		   text7.setText(monthlyLearningTargets.get(childPosition).getLearningTargetLastUpdated());
		  
	   }else if(groupPosition==3){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_learningCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_learningCourse);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_learningTopic);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_period);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(quarterlyLearningTargets.get(childPosition).getLearningTargetName());
		   text2.setText(quarterlyLearningTargets.get(childPosition).getLearningTargetTopic());
		   text3.setText(quarterlyLearningTargets.get(childPosition).getLearningTargetCourse());
		   text4.setText(quarterlyLearningTargets.get(childPosition).getLearningTargetEndDate());
		   text5.setText(quarterlyLearningTargets.get(childPosition).getLearningTargetStartDate());
		   text6.setText(quarterlyLearningTargets.get(childPosition).getLearningTargetPeriod());
		   text7.setText(quarterlyLearningTargets.get(childPosition).getLearningTargetLastUpdated());
		   
	   }else if(groupPosition==4){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_learningCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_learningCourse);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_learningTopic);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_period);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(midYearLearningTargets.get(childPosition).getLearningTargetName());
		   text2.setText(midYearLearningTargets.get(childPosition).getLearningTargetTopic());
		   text3.setText(midYearLearningTargets.get(childPosition).getLearningTargetCourse());
		   text4.setText(midYearLearningTargets.get(childPosition).getLearningTargetEndDate());
		   text5.setText(midYearLearningTargets.get(childPosition).getLearningTargetStartDate());
		   text6.setText(midYearLearningTargets.get(childPosition).getLearningTargetPeriod());
		   text7.setText(midYearLearningTargets.get(childPosition).getLearningTargetLastUpdated());
		   
	   }else if(groupPosition==5){
		   TextView text=(TextView) convertView.findViewById(R.id.textView_learningCategory);
		   TextView text2=(TextView) convertView.findViewById(R.id.textView_learningCourse);
		   TextView text3=(TextView) convertView.findViewById(R.id.textView_learningTopic);
		   TextView text4=(TextView) convertView.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) convertView.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) convertView.findViewById(R.id.textView_period);
		   TextView text7=(TextView) convertView.findViewById(R.id.textView_lastUpdated);
		   ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
		   text.setText(annualLearningTargets.get(childPosition).getLearningTargetName());
		   text2.setText(annualLearningTargets.get(childPosition).getLearningTargetTopic());
		   text3.setText(annualLearningTargets.get(childPosition).getLearningTargetCourse());
		   text4.setText(annualLearningTargets.get(childPosition).getLearningTargetEndDate());
		   text5.setText(annualLearningTargets.get(childPosition).getLearningTargetStartDate());
		   text6.setText(annualLearningTargets.get(childPosition).getLearningTargetPeriod());
		   text7.setText(annualLearningTargets.get(childPosition).getLearningTargetLastUpdated());
		  
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
		      
			  convertView = minflater.inflate(R.layout.listview_single,parent, false);
		    }
		 TextView text=(TextView) convertView.findViewById(R.id.textView_textSingle);
		 text.setText(groupItem[groupPosition]);
		    return convertView;
	 }

	 
	 																																				
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupItem.length;
	}


	@Override
	public int getChildrenCount(int groupPosition) {
		int count = 0;
		if(groupPosition==0){
			count=dailyLearningTarget.size();
		}else if(groupPosition==1){
			//count=tomorrowLearningId.size();
			count=weeklyLearningTargets.size();
		}else if(groupPosition==2){
			count=monthlyLearningTargets.size();
		}else if(groupPosition==3){
			count=quarterlyLearningTargets.size();
		}else if(groupPosition==4){
			count=midYearLearningTargets.size();
		}else if(groupPosition==5){
			count=annualLearningTargets.size();
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
			childDetails=new String[]{dailyLearningTarget.get(childPosition).getLearningTargetName(),//0
									  dailyLearningTarget.get(childPosition).getLearningTargetCourse(),//1
									  dailyLearningTarget.get(childPosition).getLearningTargetPeriod(),//2
									  dailyLearningTarget.get(childPosition).getLearningTargetEndDate(),//3
									  dailyLearningTarget.get(childPosition).getLearningTargetTopic(),//4
									  dailyLearningTarget.get(childPosition).getLearningTargetStartDate(),//5
									  dailyLearningTarget.get(childPosition).getLearningTargetStatus(),//6
									  dailyLearningTarget.get(childPosition).getLearningTargetId(),//7
									  dailyLearningTarget.get(childPosition).getLearningTargetLastUpdated()//8		
									  };
		}else if(groupPosition==1){
			childDetails=new String[]{weeklyLearningTargets.get(childPosition).getLearningTargetName(),
									  weeklyLearningTargets.get(childPosition).getLearningTargetCourse(),
									  weeklyLearningTargets.get(childPosition).getLearningTargetPeriod(),
									  weeklyLearningTargets.get(childPosition).getLearningTargetEndDate(),
									  weeklyLearningTargets.get(childPosition).getLearningTargetTopic(),
									  weeklyLearningTargets.get(childPosition).getLearningTargetStartDate(),
									  weeklyLearningTargets.get(childPosition).getLearningTargetStatus(),
									  weeklyLearningTargets.get(childPosition).getLearningTargetId(),
									  weeklyLearningTargets.get(childPosition).getLearningTargetLastUpdated()};
		}else if(groupPosition==2){
			childDetails=new String[]{monthlyLearningTargets.get(childPosition).getLearningTargetName(),
					  				  monthlyLearningTargets.get(childPosition).getLearningTargetCourse(),
					  				  monthlyLearningTargets.get(childPosition).getLearningTargetPeriod(),
					  				  monthlyLearningTargets.get(childPosition).getLearningTargetEndDate(),
					  				  monthlyLearningTargets.get(childPosition).getLearningTargetTopic(),
					  				  monthlyLearningTargets.get(childPosition).getLearningTargetStartDate(),
					  				  monthlyLearningTargets.get(childPosition).getLearningTargetStatus(),
					  				  monthlyLearningTargets.get(childPosition).getLearningTargetId(),
					  				  monthlyLearningTargets.get(childPosition).getLearningTargetLastUpdated()};
		}else if(groupPosition==3){
			childDetails=new String[]{quarterlyLearningTargets.get(childPosition).getLearningTargetName(),
									  quarterlyLearningTargets.get(childPosition).getLearningTargetCourse(),
									  quarterlyLearningTargets.get(childPosition).getLearningTargetPeriod(),
									  quarterlyLearningTargets.get(childPosition).getLearningTargetEndDate(),
									  quarterlyLearningTargets.get(childPosition).getLearningTargetTopic(),
									  quarterlyLearningTargets.get(childPosition).getLearningTargetStartDate(),
									  quarterlyLearningTargets.get(childPosition).getLearningTargetStatus(),
									  quarterlyLearningTargets.get(childPosition).getLearningTargetId(),
									  quarterlyLearningTargets.get(childPosition).getLearningTargetLastUpdated()};
		}else if(groupPosition==4){
			childDetails=new String[]{midYearLearningTargets.get(childPosition).getLearningTargetName(),
									  midYearLearningTargets.get(childPosition).getLearningTargetCourse(),
									  midYearLearningTargets.get(childPosition).getLearningTargetPeriod(),
									  midYearLearningTargets.get(childPosition).getLearningTargetEndDate(),
									  midYearLearningTargets.get(childPosition).getLearningTargetTopic(),
									  midYearLearningTargets.get(childPosition).getLearningTargetStartDate(),
									  midYearLearningTargets.get(childPosition).getLearningTargetStatus(),
									  midYearLearningTargets.get(childPosition).getLearningTargetId(),
									  midYearLearningTargets.get(childPosition).getLearningTargetLastUpdated()};
		}else if(groupPosition==5){
			
			childDetails=new String[]{annualLearningTargets.get(childPosition).getLearningTargetName(),
									  annualLearningTargets.get(childPosition).getLearningTargetCourse(),
									  annualLearningTargets.get(childPosition).getLearningTargetPeriod(),
									  annualLearningTargets.get(childPosition).getLearningTargetEndDate(),
									  annualLearningTargets.get(childPosition).getLearningTargetTopic(),
									  annualLearningTargets.get(childPosition).getLearningTargetStartDate(),
									  annualLearningTargets.get(childPosition).getLearningTargetStatus(),
									  annualLearningTargets.get(childPosition).getLearningTargetId(),
									  annualLearningTargets.get(childPosition).getLearningTargetLastUpdated()};
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

	public void onGroupExpanded(int groupPosition) {
   	
   	if(groupPosition != lastExpandedGroupPosition){
           LearningsList.collapseGroup(lastExpandedGroupPosition);
      
   }
   	
       super.onGroupExpanded(groupPosition);
    
       lastExpandedGroupPosition = groupPosition;
       

}
}