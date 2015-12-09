package org.grameenfoundation.cch.activity;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.model.User;
import org.grameenfoundation.adapters.PlannerBaseAdapter;
import org.grameenfoundation.cch.model.FacilityTargets;
import org.grameenfoundation.cch.model.FamilyPlanningFacilityTargetActivity;
import org.grameenfoundation.cch.tasks.FacilityTargetsSyncTask;
import org.grameenfoundation.poc.BaseActivity;
import org.joda.time.DateTime;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FacilityTargetOptionsActivity extends BaseActivity implements OnItemClickListener {

private ListView listView_monthOption;
private DbHelper db;
private DateTime currentDate;
private ArrayList<FacilityTargets> facilityTargets;
private Button sync;
private ArrayList<User> userdetails;
private String zonename;
private SharedPreferences prefs;
private String name;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_month_options);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("View events/targets");
	    listView_monthOption=(ListView) findViewById(R.id.listView_targetMonthOptions);
	    listView_monthOption.setOnItemClickListener(this);
	    db=new DbHelper(FacilityTargetOptionsActivity.this);
	    currentDate=new DateTime();
	    String[] items={"Child Health","Maternal Health","Others"};
		   int[] images={R.drawable.ic_child,R.drawable.ic_maternal,R.drawable.ic_other_facility};
		   final PlannerBaseAdapter adapter=new PlannerBaseAdapter(FacilityTargetOptionsActivity.this,items,images);
		   sync=(Button) findViewById(R.id.button_sync);
		   prefs = PreferenceManager.getDefaultSharedPreferences(this);
		   name=prefs.getString("first_name", "name");
		   sync.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
				 userdetails=new ArrayList<User>();
			     userdetails=db.getUserFirstName(name);
			     zonename=userdetails.get(0).getUserZone();
			     System.out.println("Clicked");
				 FacilityTargetsSyncTask task = new FacilityTargetsSyncTask(FacilityTargetOptionsActivity.this);
				 task.execute(getResources().getString(R.string.serverDefaultAddress)+"/"+MobileLearning.FACILITY_TARGETS_SYNC_PATH+URLEncoder.encode(zonename));
				 facilityTargets=new ArrayList<FacilityTargets>();
				   facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"));
				   listView_monthOption.setAdapter(adapter);
				   int targetnumber = 0;
				   ArrayList<FacilityTargets> previousmonth=new ArrayList<FacilityTargets>();
				   previousmonth=db.getTargetsForCarryForward(db.getPreviousMonth(currentDate.toString("MMMM")));
				   for(int i=0;i<previousmonth.size();i++){
					   if(previousmonth.get(i).getTargetNumberRemaining()!=null){
						   int previousmonthremaining=Integer.parseInt(previousmonth.get(i).getTargetNumberRemaining());
						  //int currentmonthremaining=Integer.parseInt(facilityTargets.get(i).getTargetNumberRemaining());
						   if(previousmonthremaining>0){
							   targetnumber=Integer.parseInt(facilityTargets.get(i).getTargetNumber())+previousmonthremaining;
							   db.targetCarryForward(facilityTargets.get(i).getTargetId(),
						 					facilityTargets.get(i).getTargetType(),
						 					facilityTargets.get(i).getTargetCategory(),
							 				String.valueOf(targetnumber),
							 				previousmonth.get(i).getTargetId(),
							 				previousmonth.get(i).getTargetType());
						   }else{
							   targetnumber=Integer.parseInt(facilityTargets.get(i).getTargetNumber());
						   }
					   }
				   }
				 
				   ArrayList<FacilityTargets> synced_targets=new ArrayList<FacilityTargets>();
				   synced_targets=db.getTargetsForMonthSynced(returnMonth(currentDate.toString("MMMM")));
				   if(synced_targets.size()>0){
					 for(int j=0;j<synced_targets.size();j++){
						 	/*System.out.println(synced_targets.get(j).getTargetNumberAchieved()+" "+
						 						synced_targets.get(j).getTargetType()+" "
						 							+" "+synced_targets.get(j).getTargetDetail()+" "
						 						+returnMonthString(synced_targets.get(j).getTargetMonth()));*/
						 	db.targetSynced(synced_targets.get(j).getTargetOldId(),
							 		synced_targets.get(j).getTargetType(),
							 		synced_targets.get(j).getTargetNumberAchieved(),
							 		synced_targets.get(j).getTargetDetail(),
							 		returnMonthString(synced_targets.get(j).getTargetMonth()));
					 }
				 }
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		   try{
			   facilityTargets=new ArrayList<FacilityTargets>();
			   facilityTargets=db.getTargetsForMonthView(currentDate.toString("MMMM"));
			   listView_monthOption.setAdapter(adapter);
			   int targetnumber = 0;
			   ArrayList<FacilityTargets> previousmonth=new ArrayList<FacilityTargets>();
			   previousmonth=db.getTargetsForCarryForward(db.getPreviousMonth(currentDate.toString("MMMM")));
			   for(int i=0;i<previousmonth.size();i++){
				   if(previousmonth.get(i).getTargetNumberRemaining()!=null){
					   int previousmonthremaining=Integer.parseInt(previousmonth.get(i).getTargetNumberRemaining());
					  // int currentmonthremaining=Integer.parseInt(facilityTargets.get(i).getTargetNumberRemaining());
					   if(previousmonthremaining>0){
						   targetnumber=Integer.parseInt(facilityTargets.get(i).getTargetNumber())+previousmonthremaining;
						   db.targetCarryForward(facilityTargets.get(i).getTargetId(),
					 					facilityTargets.get(i).getTargetType(),
					 					facilityTargets.get(i).getTargetCategory(),
						 				String.valueOf(targetnumber),
						 				previousmonth.get(i).getTargetId(),
						 				previousmonth.get(i).getTargetType());
					   }else{
						   targetnumber=Integer.parseInt(facilityTargets.get(i).getTargetNumber());
					   }
				   }
			   }
			 
			   ArrayList<FacilityTargets> synced_targets=new ArrayList<FacilityTargets>();
			   synced_targets=db.getTargetsForMonthSynced(returnMonth(currentDate.toString("MMMM")));
			   if(synced_targets.size()>0){
				 for(int j=0;j<synced_targets.size();j++){
					 	System.out.println(synced_targets.get(j).getTargetNumberAchieved()+" "+
					 						synced_targets.get(j).getTargetType()+" "
					 							+" "+synced_targets.get(j).getTargetDetail()+" "
					 						+returnMonthString(synced_targets.get(j).getTargetMonth()));
					 	db.targetSynced(synced_targets.get(j).getTargetOldId(),
						 		synced_targets.get(j).getTargetType(),
						 		synced_targets.get(j).getTargetNumberAchieved(),
						 		synced_targets.get(j).getTargetDetail(),
						 		returnMonthString(synced_targets.get(j).getTargetMonth()));
				 }
				 
			 }
		   }catch(Exception e){
			   e.printStackTrace();
		   }
        }
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		switch(position){
		case 0:
			intent=new Intent(FacilityTargetOptionsActivity.this,NewFacilityTargetsActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
        	break;
        case 1:
        	intent=new Intent(FacilityTargetOptionsActivity.this,MaternalHealthFacilityTargetsActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
        	break;
        case 2:
        	intent=new Intent(FacilityTargetOptionsActivity.this,OtherFacilityTargetActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
        	break;
       
		
	}
	
	}
	public String returnMonth(String month){
		String monthInt = null;
		switch (month) {
		case "January":
			monthInt="1";
			break;
		case "February":
			monthInt="2";
			break;
		case "March":
			monthInt="3";
			break;
		case "April":
			monthInt="4";
			break;
		case "May":
			monthInt="5";
			break;
		case "June":
			monthInt="6";
			break;
		case "July":
			monthInt="7";
			break;
		case "August":
			monthInt="8";
			break;
		case "September":
			monthInt="9";
			break;
		case "October":
			monthInt="10";
			break;
		case "November":
			monthInt="11";
			break;
		case "December":
			monthInt="12";
			break;
		default:
			break;
		}
		return monthInt;
	}
	
	public String returnMonthString(String month){
		String monthInt = null;
		switch (month) {
		case "1":
			monthInt="January";
			break;
		case "2":
			monthInt="February";
			break;
		case "3":
			monthInt="March";
			break;
		case "4":
			monthInt="April";
			break;
		case "5":
			monthInt="May";
			break;
		case "6":
			monthInt="June";
			break;
		case "7":
			monthInt="July";
			break;
		case "8":
			monthInt="August";
			break;
		case "9":
			monthInt="September";
			break;
		case "10":
			monthInt="October";
			break;
		case "11":
			monthInt="November";
			break;
		case "12":
			monthInt="December";
			break;
		default:
			break;
		}
		return monthInt;
	}
}