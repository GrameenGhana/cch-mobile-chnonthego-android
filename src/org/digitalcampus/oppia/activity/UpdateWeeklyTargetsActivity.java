																																																															package org.digitalcampus.oppia.activity;

import java.util.ArrayList;
import java.util.Calendar;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.UpdateTargetsAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;

public class UpdateWeeklyTargetsActivity extends Activity implements OnChildClickListener{

	private ListView listView_eventsUpdate;
	private ListView listView_coverageUpdate;
	private ListView listView_otherUpdate;
	private ListView listView_learningUpdate;
	private DbHelper db;
	private Context mContext;
	ArrayList<String> eventName;
	ArrayList<String> eventId;
	ArrayList<String> eventNumber;
	ArrayList<String> eventPeriod;
	ArrayList<String> eventDueDate;
	ArrayList<String> eventAchieved;
	ArrayList<String> eventStartDate;
	
	ArrayList<String> coverageName;
	ArrayList<String> coverageId;
	ArrayList<String> coverageNumber;
	ArrayList<String> coveragePeriod;
	ArrayList<String> coverageDueDate;
	ArrayList<String> coverageAchieved;
	ArrayList<String> coverageStartDate;
	
	ArrayList<String> otherName;
	ArrayList<String> otherId;
	ArrayList<String> otherNumber;
	ArrayList<String> otherPeriod;
	ArrayList<String> otherDueDate;
	ArrayList<String> otherAchieved;
	ArrayList<String> otherStartDate;
	String due_date;
	private ArrayList<String> learningId;
	private ArrayList<String> learningDueDate;
	private ArrayList<String> learningName;
	private ArrayList<String> learningPeriod;
	private ArrayList<String> learningStartDate;
	private ExpandableListView expandableListView_updates;
	private UpdateTargetsAdapter updates_adapter;
	private ArrayList<String> eventStatus;
	private ArrayList<String> eventLastUpdated;
	private ArrayList<String> coverageStatus;
	private ArrayList<String> coverageLastUpdated;
	private ArrayList<String> learningStatus;
	private ArrayList<String> learningLastUpdated;
	private ArrayList<String> otherStatus;
	private ArrayList<String> otherLastUpdated;
	private String[] groupItems;
	private long selected_id;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_update_targets);
	    mContext=UpdateWeeklyTargetsActivity.this;
	    db=new DbHelper(mContext);
	    Calendar c = Calendar.getInstance();
        int month=c.get(Calendar.MONTH)+1;
        Time time = new Time();
	    time.setToNow();
	    String today= String.valueOf(time.monthDay)+"-"+String.valueOf(time.month+1)+"-"+String.valueOf(time.year);
	    /*
        eventId=new ArrayList<String>();
    	eventNumber=new ArrayList<String>();
    	eventType=new ArrayList<String>();
    	eventDueDate=new ArrayList<String>();
    	eventPeriod=new ArrayList<String>();
    	eventAchieved=new ArrayList<String>();
    	eventStartDate=new ArrayList<String>();
    	*/
    	
 	   eventId=db.getAllForEventsId("Weekly");
 	   eventNumber=db.getAllForEventsNumber("Weekly");
 	   eventName=db.getAllForEventsName("Weekly");
 	   eventDueDate=db.getAllForEventsDueDate("Weekly");
 	   eventPeriod=db.getAllForEventsPeriod("Weekly");
 	   eventAchieved=db.getAllForEventsNumberAchieved("Weekly");
 	   eventStartDate=db.getAllForEventsStartDate("Weekly");
 	   eventStatus=db.getAllForEventsSyncStatus("Weekly");
 	   eventLastUpdated=db.getAllForEventsLastUpdated("Weekly");
 	   
 	   /*
		coverageId=new ArrayList<String>();
		coverageNumber=new ArrayList<String>();
    	coverageType=new ArrayList<String>();
    	coverageDueDate=new ArrayList<String>();
    	coveragePeriod=new ArrayList<String>();
    	coverageAchieved=new ArrayList<String>();
    	coverageStartDate=new ArrayList<String>();
    	*/
		coverageName=db.getAllForCoverageName("Weekly");
		coverageId=db.getAllForCoverageId("Weekly");
		coverageNumber=db.getAllForCoverageNumber("Weekly");
		coverageDueDate=db.getAllForCoverageDueDate("Weekly");
		coveragePeriod=db.getAllForCoveragePeriod("Weekly");
		coverageAchieved=db.getAllForCoverageNumberAchieved("Weekly");
		coverageStartDate=db.getAllForCoverageStartDate("Weekly");
		coverageStatus=db.getAllForCoverageSyncStatus("Weekly");
		coverageLastUpdated=db.getAllForCoverageLastUpdated("Weekly");
		
		/*
		learningId=new ArrayList<String>();
    	learningName=new ArrayList<String>();
    	learningDueDate=new ArrayList<String>();
    	learningPeriod=new ArrayList<String>();
    	//coverageAchieved=new ArrayList<String>();
    	learningStartDate=new ArrayList<String>();
    	*/
    	learningName=db.getAllForLearningTopic("Weekly");
		learningId=db.getAllForLearningId("Weekly");
		learningDueDate=db.getAllForLearningDueDate("Weekly");
		learningPeriod=db.getAllForLearningPeriod("Weekly");
		learningStartDate=db.getAllForLearningStartDate("Weekly");
		learningStatus=db.getAllForLearningSyncStatus("Weekly");
		learningLastUpdated=db.getAllForLearningLastUpdated("Weekly");
		
		/*
		otherId=new ArrayList<String>();
    	otherNumber=new ArrayList<String>();
		otherType=new ArrayList<String>();
		otherDueDate=new ArrayList<String>();
		otherPeriod=new ArrayList<String>();
		otherAchieved=new ArrayList<String>();
		otherStartDate=new ArrayList<String>();
		*/
    	otherName=db.getAllForOtherName("Weekly");
 	    otherId=db.getAllForOtherId("Weekly");
 	    otherNumber=db.getAllForOtherNumber("Weekly");
 	    otherDueDate=db.getAllForOtherDueDate("Weekly");
 	    otherPeriod=db.getAllForOtherPeriod("Weekly");
 	    otherAchieved=db.getAllForOtherNumberAchieved("Weekly");
 	    otherStartDate=db.getAllForOtherStartDate("Weekly");
 	    otherStatus=db.getAllForOtherSyncStatus("Weekly");
 	    otherLastUpdated=db.getAllForOtherLastUpdated("Weekly");
	    //retrieve daily event targets that need to be updated
 	    groupItems=new String[]{"Events","Coverage","Learning","Other"};
 	    expandableListView_updates=(ExpandableListView) findViewById(R.id.expandableListView_updates);
 	    updates_adapter=new UpdateTargetsAdapter(mContext,eventName,
										eventNumber,
										eventPeriod,
										eventDueDate,
										eventAchieved,
										eventStartDate,
										eventStatus,
										eventId,
										eventLastUpdated,
										//ArrayList<String> todayEventNumberRemaining,
											
										/*
										ArrayList<String> tomorrowEventName,
										ArrayList<String> tomorrowEventNumber,
										ArrayList<String> tomorrowEventPeriod,
										ArrayList<String> tomorrowEventDueDate,
										ArrayList<String> tomorrowEventStartDate,
										ArrayList<String> tomorrowEventStatus,
										ArrayList<String> tomorrowEventId,
				*/
										coverageName,
										coverageNumber,
										coveragePeriod,
										coverageDueDate,
										coverageAchieved,
										coverageStartDate,
										coverageStatus,
										coverageId,
										coverageLastUpdated,
										//ArrayList<String> thisWeekEventNumberRemaining,
				
										learningName,
										//ArrayList<String> learningNumber,
										learningPeriod,
										learningDueDate,
										//ArrayList<String> learningAchieved,
										learningStartDate,
										learningStatus,
										learningId,
										learningLastUpdated,
										
										otherName,
										otherNumber,
										otherPeriod,
										otherDueDate,
										otherAchieved,
										otherStartDate,
										otherStatus,
										otherId,
										otherLastUpdated,
										
			 							groupItems,
			 							expandableListView_updates);
 	   expandableListView_updates.setAdapter(updates_adapter);
 	  expandableListView_updates.setOnChildClickListener(this);
 		}

 		@Override
 		public boolean onChildClick(ExpandableListView parent, View v,
 				int groupPosition, int childPosition, long id) {
 			String[] selected_items=updates_adapter.getChild(groupPosition, childPosition);
 			if(groupPosition==0){
 			selected_id=Long.parseLong(selected_items[7]);
 			//System.out.println(selected_items[0]+" "+selected_items[1]);
 			String name=selected_items[0];
 			String number=selected_items[1];
 			String period=selected_items[2];
 			String due_date=selected_items[3];
 			String status=selected_items[6];
 			String startDate=selected_items[5];
 			String achieved=selected_items[4];
 			Intent intent=new Intent(mContext,UpdateActivity.class);
 			intent.putExtra("id",selected_id);
 			intent.putExtra("name",name);
 			intent.putExtra("number",number);
 			intent.putExtra("period", period);
 			intent.putExtra("type", "events");
 			intent.putExtra("due_date", due_date);
 			intent.putExtra("start_date", startDate);
 			intent.putExtra("status", status);
 			intent.putExtra("number_achieved", achieved);
 			startActivity(intent);
 			}else if(groupPosition==1){
 				selected_id=Long.parseLong(selected_items[7]);
 				//System.out.println(selected_items[0]+" "+selected_items[1]);
 				String name=selected_items[0];
 				String number=selected_items[1];
 				String period=selected_items[2];
 				String due_date=selected_items[3];
 				String status=selected_items[6];
 				String startDate=selected_items[5];
 				String achieved=selected_items[4];
 				Intent intent=new Intent(mContext,UpdateActivity.class);
 				intent.putExtra("id",selected_id);
 				intent.putExtra("name",name);
 				intent.putExtra("number",number);
 				intent.putExtra("period", period);
 				intent.putExtra("type", "coverage");
 				intent.putExtra("due_date", due_date);
 				intent.putExtra("start_date", startDate);
 				intent.putExtra("status", status);
 				intent.putExtra("number_achieved", achieved);
 				startActivity(intent);
 			}else if(groupPosition==2){
 				selected_id=Long.parseLong(selected_items[5]);
 				//System.out.println(selected_items[0]+" "+selected_items[1]);
 				String name=selected_items[0];
 				//String number=selected_items[1];
 				String period=selected_items[1];
 				String due_date=selected_items[2];
 				String status=selected_items[4];
 				String startDate=selected_items[3];
 				//String achieved=selected_items[4];
 				Intent intent=new Intent(mContext,UpdateActivity.class);
 				intent.putExtra("id",selected_id);
 				intent.putExtra("learning_topic",name);
 				//intent.putExtra("number",number);
 				intent.putExtra("period", period);
 				intent.putExtra("type", "learning");
 				intent.putExtra("due_date", due_date);
 				intent.putExtra("start_date", startDate);
 				intent.putExtra("status", status);
 				//intent.putExtra("achieved", achieved);
 				startActivity(intent);
 			}else if(groupPosition==3){
 				selected_id=Long.parseLong(selected_items[7]);
 				//System.out.println(selected_items[0]+" "+selected_items[1]);
 				String name=selected_items[0];
 				String number=selected_items[1];
 				String period=selected_items[2];
 				String due_date=selected_items[3];
 				String status=selected_items[6];
 				String startDate=selected_items[5];
 				String achieved=selected_items[4];
 				Intent intent=new Intent(mContext,UpdateActivity.class);
 				intent.putExtra("id",selected_id);
 				intent.putExtra("name",name);
 				intent.putExtra("number",number);
 				intent.putExtra("period", period);
 				intent.putExtra("type", "other");
 				intent.putExtra("due_date", due_date);
 				intent.putExtra("start_date", startDate);
 				intent.putExtra("status", status);
 				intent.putExtra("number_achieved", achieved);
 			}
 			return true;
 		}
 		}	