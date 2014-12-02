package org.digitalcampus.oppia.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.CoverageTargetsDetailActivity.DatePickerFragment;
import org.digitalcampus.oppia.activity.CoverageTargetsDetailActivity.DatePickerFragment2;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.service.TrackerService;
import org.grameenfoundation.adapters.CoverageListAdapter;
import org.grameenfoundation.adapters.EventBaseAdapter;
import org.grameenfoundation.adapters.LearningBaseAdapter;
import org.grameenfoundation.adapters.OtherBaseAdapter;
import org.grameenfoundation.cch.activity.HomeActivity;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.ActionMode;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;


public class NewEventPlannerActivity extends SherlockFragmentActivity implements ActionBar.TabListener, OnSharedPreferenceChangeListener{
	 private DbHelper dbh;
	SectionsPagerAdapter mSectionsPagerAdapter;
	public static String current_month;
	
	private static final String EVENT_PLANNER_ID = "Event Planner";
	public static final String TAG = NewEventPlannerActivity.class.getSimpleName();
	public static String month_passed = null;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
	private SharedPreferences prefs;
	private DbHelper db;
	private long todayEventId;
	private long thisMonthEventId;
	private long thisWeekEventId;
	private long midYearEventId;
	private long thisQuarterEventId;
	private long thisYearEventId;
	private long todayCoverageId;
	private long thisWeekCoverageId;
	private long thisMonthCoverageId;
	private long midYearCoverageId;
	private long thisQuarterCoverageId;
	private long thisYearCoverageId;
	private long todayLearningId;
	private long thisWeekLearningId;
	private long thisMonthLearningId;
	private long midYearLearningId;
	private long thisQuarterLearningId;
	private long thisYearLearningId;
	private long todayOtherId;
	private long thisWeekOtherName;
	private long thisWeekOtherId;
	private long thisMonthOtherId;
	private long midYearOtherId;
	private long thisQuarterOtherId;
	private long thisYearOtherId;
	 int counter3;
	 int counter;
	 int counter2;
	 int counter4;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_new_event_planner);
	    
	   // final PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_header);
       // pagerTabStrip.setDrawFullUnderline(true);
       // pagerTabStrip.setTabIndicatorColor(Color.rgb(83,171,32));
        dbh = new DbHelper(NewEventPlannerActivity.this);
      
        final ActionBar actionBar =getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle("Planner");
        actionBar.setSubtitle("Target Setting");
        actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        
        mViewPager
        .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                }
        });

        // For each of the sections in the app, add a tab to the action bar.
        
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                // Create a tab with text corresponding to the page title defined by
                // the adapter. Also specify this Activity object, which implements
                // the TabListener interface, as the callback (listener) for when
                // this tab is selected.
        	
                actionBar.addTab(actionBar.newTab()
                                .setText(mSectionsPagerAdapter.getPageTitle(i))
                                .setTabListener(this));
        }
        Calendar c = Calendar.getInstance();
        int month=c.get(Calendar.MONTH)+1;
        switch(month){
        case 1:
        	current_month="January";
        	break;
        case 2:
        	current_month="February";
        	break;
        case 3:
        	current_month="March";
        	break;
        case 4:
        	current_month="April";
        	break;
        case 5:
        	current_month="May";
        	break;
        case 6:
        	current_month="June";
        	break;
        case 7:
        	current_month="July";
        	break;
        case 8:
        	current_month="August";
        	break;
        case 9:
        	current_month="September";
        	break;
        case 10:
        	current_month="October";
        	break;
        case 11:
        	current_month="November";
        	break;
        case 12:
        	current_month="December";
        	break;
        }
       
       
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
		
		Intent service = new Intent(this, TrackerService.class);
		Bundle tb = new Bundle();
		tb.putBoolean("backgroundData", true);
		service.putExtras(tb);
		this.startService(service);
        // Create the adapter that will return a fragment for each of the four
        // primary sections of the app.
		
		 	
        
}
	 public class SectionsPagerAdapter extends FragmentPagerAdapter {

         public SectionsPagerAdapter(FragmentManager fm) {
                 super(fm);
         }

         @Override
         public Fragment getItem(int position) {
                 Fragment fragment = null;
                 if(position==0 ){
                        fragment= new EventsActivity();
                 }else if(position==1){
                	 fragment= new CoverageActivity();
                 }else if(position==2){
                	 fragment= new LearningActivity();
                 }else if(position==3){
                	 fragment= new OtherActivity();
                 }
                 return fragment;
         }

         @Override
         public int getCount() {
                 return 4;
         }

         @Override
         public CharSequence getPageTitle(int position) {
                 Locale l = Locale.getDefault();
                 db=new DbHelper(NewEventPlannerActivity.this);
     		    todayEventId=db.getEventIdCount("Daily");
     		    thisMonthEventId=db.getEventIdCount("Monthly");
     		    thisWeekEventId=db.getEventIdCount("Weekly");
     		    midYearEventId=db.getEventIdCount("Mid-year");
     		    thisQuarterEventId=db.getEventIdCount("Quarterly");
     		    thisYearEventId=db.getEventIdCount("Annually");
     		     int event_number1=(int)todayEventId;
     			 int event_number2=(int)thisMonthEventId;
     			 int event_number3=(int)thisWeekEventId;
     			 int event_number4=(int)midYearEventId;
     			 int event_number5=(int)thisQuarterEventId;
     			 int event_number6=(int)thisYearEventId;
     	
     			counter=event_number1+event_number2+event_number3+event_number4+event_number5+event_number6;
     			
     			todayCoverageId=db.getCoverageIdCount("Daily");
     			thisWeekCoverageId=db.getCoverageIdCount("Weekly");
     			thisMonthCoverageId=db.getCoverageIdCount("Monthly");
     			midYearCoverageId=db.getCoverageIdCount("Mid-year");
     			thisQuarterCoverageId=db.getCoverageIdCount("Quarterly");
     			thisYearCoverageId=db.getCoverageIdCount("Annually");
     			
     			     int coverage_number1=(int)todayCoverageId;
     				 int coverage_number2=(int)thisWeekCoverageId;
     				 int coverage_number3=(int)thisMonthCoverageId;
     				 int coverage_number4=(int)midYearCoverageId;
     				 int coverage_number5=(int)thisQuarterCoverageId;
     				 int coverage_number6=(int)thisYearCoverageId;
     				
     				counter2=coverage_number1+coverage_number2+coverage_number3+coverage_number4+coverage_number5+coverage_number6;
     			
     			todayLearningId=db.getLearningIdCount("Daily");
     			thisWeekLearningId=db.getLearningIdCount("Weekly");
     			thisMonthLearningId=db.getLearningIdCount("Monthly");
     			midYearLearningId=db.getLearningIdCount("Mid-year");
     			thisQuarterLearningId=db.getLearningIdCount("Quarterly");
     			thisYearLearningId=db.getLearningIdCount("Annually");
     			
     			 int learning_number1=(int)todayLearningId;
     			 int learning_number2=(int)thisWeekLearningId;
     			 int learning_number3=(int)thisMonthLearningId;
     			 int learning_number4=(int)midYearLearningId;
     			 int learning_number5=(int)thisQuarterLearningId;
     			 int learning_number6=(int)thisYearLearningId;
     			counter3=learning_number1+
     					learning_number2+
     					learning_number3+
     					learning_number4+
     					learning_number5+
     					learning_number6;
     			 todayOtherId=db.getOtherIdCount("Daily");
     			 thisWeekOtherId=db.getOtherIdCount("Weekly");
     			 thisMonthOtherId=db.getOtherIdCount("Monthly");
     			 midYearOtherId=db.getOtherIdCount("Mid-year");
     			 thisQuarterOtherId=db.getOtherIdCount("Quarterly");
     			 thisYearOtherId=db.getOtherIdCount("Annually");
     			 
     			 int other_number1=(int)todayOtherId;
     			 int other_number2=(int)thisWeekOtherId;
     			 int other_number3=(int)thisMonthOtherId;
     			 int other_number4=(int)midYearOtherId;
     			 int other_number5=(int)thisQuarterOtherId;
     			 int other_number6=(int)thisYearOtherId;
     		
     			counter4=other_number1+
     					other_number2+
     					other_number3+
     					other_number4+
     					other_number5+
     					other_number6;
                 switch (position) {
                         case 0:
                                 return "EVENTS"+" ("+String.valueOf(counter)+")";
                         case 1:
                                 return "COVERAGE"+" ("+String.valueOf(counter2)+")";
                         case 2: 
                    	 		return "LEARNING"+" ("+String.valueOf(counter3)+")";
                         case 3:
                        		return "OTHER"+" ("+String.valueOf(counter4)+")";
                 
                 }
                 return null;
         }
 }
		
	 public static class EventsActivity extends Fragment implements OnChildClickListener{

			private Context mContext;															
			private ExpandableListView listView_events;
			 private ArrayList<String> todayEventName;
			 private ArrayList<String> todayEventNumber;
			 private ArrayList<String> todayEventPeriod;
			 private ArrayList<String> todayEventDueDate;
			 private ArrayList<String> todayEventStartDate;
			 private ArrayList<String> todayEventAchieved;
			 private ArrayList<String> todayEventStatus;
			 private ArrayList<String> todayEventId;
			 private ArrayList<String> todayEventLastUpdated;
			 private ArrayList<String> todayEventNumberRemaining;
			 
			 private ArrayList<String> tomorrowEventName;
			 private ArrayList<String> tomorrowEventNumber;
			 private ArrayList<String> tomorrowEventPeriod;
			 private ArrayList<String> tomorrowEventDueDate;
			 private ArrayList<String> tomorrowEventStartDate;
			 private ArrayList<String> tomorrowEventAchieved;
			 private ArrayList<String> tomorrowEventStatus;
			 private ArrayList<String> tomorrowEventId;
			 
			 private ArrayList<String> thisWeekEventName;
			 private ArrayList<String> thisWeekEventNumber;
			 private ArrayList<String> thisWeekEventPeriod;
			 private ArrayList<String> thisWeekEventDueDate;
			 private ArrayList<String> thisWeekEventStartDate;
			 private ArrayList<String> thisWeekEventAchieved;
			 private ArrayList<String> thisWeekEventStatus;
			 private ArrayList<String> thisWeekEventId;
			 private ArrayList<String> thisWeekEventLastUpdated;
			 private ArrayList<String> thisWeekEventNumberRemaining;
			 
			 private ArrayList<String> thisMonthEventName;
			 private ArrayList<String> thisMonthEventNumber;
			 private ArrayList<String> thisMonthEventPeriod;
			 private ArrayList<String> thisMonthEventDueDate;
			 private ArrayList<String>  thisMonthEventStartDate;
			 private ArrayList<String>  thisMonthEventAchieved;
			 private ArrayList<String> thisMonthEventStatus;
			 private ArrayList<String> thisMonthEventId;
			 private ArrayList<String> thisMonthEventLastUpdated;
			 private ArrayList<String> thisMonthEventNumberRemaining;
			 
			 private ArrayList<String> thisQuarterEventName;
			 private ArrayList<String> thisQuarterEventNumber;
			 private ArrayList<String> thisQuarterEventPeriod;
			 private ArrayList<String> thisQuarterEventDueDate;
			 private ArrayList<String>  thisQuarterEventStartDate;
			 private ArrayList<String>  thisQuarterEventAchieved;
			 private ArrayList<String> thisQuarterEventStatus;
			 private ArrayList<String> thisQuarterEventId;
			 private ArrayList<String> thisQuarterEventLastUpdated;
			 private ArrayList<String> thisQuarterEventNumberRemaining;
			 
			 private ArrayList<String> midYearEventName;
			 private ArrayList<String> midYearEventNumber;
			 private ArrayList<String> midYearEventPeriod;
			 private ArrayList<String> midYearEventDueDate;
			 private ArrayList<String>  midYearEventStartDate;
			 private ArrayList<String>  midYearEventAchieved;
			 private ArrayList<String> midYearEventStatus;
			 private ArrayList<String> midYearEventId;
			 private ArrayList<String> midYearEventLastUpdated;
			 private ArrayList<String> midYearEventNumberRemaining;
			 
			 private ArrayList<String> thisYearEventName;
			 private ArrayList<String> thisYearEventNumber;
			 private ArrayList<String> thisYearEventPeriod;
			 private ArrayList<String> thisYearEventDueDate;
			 private ArrayList<String>  thisYearEventStartDate;
			 private ArrayList<String> thisYearEventAchieved;
			 private ArrayList<String> thisYearEventStatus;
			 private ArrayList<String> thisYearEventId;
			 private ArrayList<String> thisYearEventLastUpdated;
			 private ArrayList<String> thisYearEventNumberRemaining;
			 
			 private HashMap<String,String> todayEventTargets;//Today
			 private HashMap<String,String> tomorrowEventTargets;
			 private HashMap<String,String> thisWeekEventTargets;
			 private HashMap<String,String> thisMonthEventTargets;
			 private HashMap<String,String> thisQuarterEventTargets;
			 private HashMap<String,String> midYearEventTargets;
			 private HashMap<String,String> thisYearEventTargets;
			 
			private String[] groupItems;
			private DbHelper db;
			public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private EventBaseAdapter events_adapter;
			private String[] selected_items;
			int selected_position;
			private long selected_id;
			private Button button_show;
			private ArrayList<String> eventId;
			private ArrayList<String> coverageId;
			private ArrayList<String> otherId;
			private ArrayList<String> learningId;
			static String due_date ;
			static String start_date;
			private static TextView dueDateValue;
			private static TextView startDateValue;
			static long due_date_to_compare;
			 public EventsActivity(){

            }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				 rootView=inflater.inflate(R.layout.activity_events,null,false);
			    mContext=getActivity().getApplicationContext();
			    //TypefaceUtil.overrideFont(mContext, "SERIF", "fonts/Roboto-Thin.ttf");
			    db=new DbHelper(getActivity());
			    listView_events=(ExpandableListView) rootView.findViewById(R.id.expandableListView1);
			  
			   
			   todayEventName=new ArrayList<String>();
			    todayEventName=db.getAllForEventsName("Daily");
			   // todayEventName.add(todayEventTargets.get("event_name"));
			   todayEventNumber=new ArrayList<String>();
			    todayEventNumber=db.getAllForEventsNumber("Daily");
			    
			   // todayEventNumber.add(todayEventTargets.get("event_number"));
			    todayEventPeriod=new ArrayList<String>();
			    //todayEventPeriod.add(todayEventTargets.get("event_period"));
			    todayEventPeriod=db.getAllForEventsPeriod("Daily");
			    
			    todayEventAchieved=new ArrayList<String>();
			    todayEventAchieved=db.getAllForEventsNumberAchieved("Daily");
			    
			   // todayEventNumber.add(todayEventTargets.get("event_number"));
			    todayEventStartDate=new ArrayList<String>();
			    //todayEventPeriod.add(todayEventTargets.get("event_period"));
			    todayEventStartDate=db.getAllForEventsStartDate("Daily");
			    
			    todayEventDueDate=new ArrayList<String>();
			    todayEventDueDate=db.getAllForEventsDueDate("Daily");
			   // todayEventDueDate.add(todayEventTargets.get("due_date"));
			    
			    todayEventStatus=new ArrayList<String>();
			   // todayEventStatus.add(todayEventTargets.get("sync_status"));
			    todayEventStatus=db.getAllForEventsSyncStatus("Daily");
			    
			    todayEventId=new ArrayList<String>();
			  //  todayEventId.add(todayEventTargets.get("event_id"));
			    todayEventId=db.getAllForEventsId("Daily");
			    
			    todayEventLastUpdated=new ArrayList<String>();
				  //  todayEventId.add(todayEventTargets.get("event_id"));
				    todayEventLastUpdated=db.getAllForEventsLastUpdated("Daily");
			    
			    
			    tomorrowEventName=new ArrayList<String>();
			    tomorrowEventName=db.getAllForEventsName("Weekly");
			    //tomorrowEventName.add(tomorrowEventTargets.get("event_name"));
			    
			    /*
			    tomorrowEventNumber=new ArrayList<String>();
			    tomorrowEventNumber=db.getAllForEventsNumber("Weekly");
			    //tomorrowEventNumber.add(tomorrowEventTargets.get("event_number"));
			    tomorrowEventPeriod=new ArrayList<String>();
			    tomorrowEventPeriod=db.getAllForEventsPeriod("Weekly");
			    //tomorrowEventPeriod.add(tomorrowEventTargets.get("event_period"));
			    tomorrowEventDueDate=new ArrayList<String>();
			    tomorrowEventDueDate=db.getAllForEventsDueDate("Weekly");
			    //tomorrowEventDueDate.add(tomorrowEventTargets.get("due_date"));
			    tomorrowEventStatus=new ArrayList<String>();
			    tomorrowEventStatus=db.getAllForEventsSyncStatus("Weekly");
			    //tomorrowEventStatus.add(tomorrowEventTargets.get("sync_status"));
			    tomorrowEventId=new ArrayList<String>();
			    tomorrowEventId=db.getAllForEventsId("Weekly");
			    //tomorrowEventId.add(tomorrowEventTargets.get("event_id"));
			    */
			    
			    thisWeekEventName=new ArrayList<String>();
			    thisWeekEventName=db.getAllForEventsName("Weekly");
			    //thisWeekEventName.add(thisWeekEventTargets.get("event_name"));
			    thisWeekEventNumber=new ArrayList<String>();
			    thisWeekEventNumber=db.getAllForEventsNumber("Weekly");
			   // thisWeekEventNumber.add(thisWeekEventTargets.get("event_number"));
			    thisWeekEventPeriod=new ArrayList<String>();
			    thisWeekEventPeriod=db.getAllForEventsPeriod("Weekly");
			   // thisWeekEventPeriod.add(thisWeekEventTargets.get("event_period"));
			    thisWeekEventAchieved=new ArrayList<String>();
			    thisWeekEventAchieved=db.getAllForEventsNumberAchieved("Weekly");
			   // thisWeekEventNumber.add(thisWeekEventTargets.get("event_number"));
			    thisWeekEventStartDate=new ArrayList<String>();
			    thisWeekEventStartDate=db.getAllForEventsStartDate("Weekly");
			    
			    thisWeekEventDueDate=new ArrayList<String>();
			    thisWeekEventDueDate=db.getAllForEventsDueDate("Weekly");
			    //thisWeekEventDueDate.add(thisWeekEventTargets.get("due_date"));
			    thisWeekEventStatus=new ArrayList<String>();
			    thisWeekEventStatus=db.getAllForEventsSyncStatus("Weekly");
			    //thisWeekEventStatus.add(thisWeekEventTargets.get("sync_status"));
			    thisWeekEventId=new ArrayList<String>();
			    thisWeekEventId=db.getAllForEventsId("Weekly");
			    //thisWeekEventId.add(thisWeekEventTargets.get("event_id"));
			    thisWeekEventLastUpdated=new ArrayList<String>();
			    thisWeekEventLastUpdated=db.getAllForEventsLastUpdated("Weekly");
			    //thisWeekEventId.add(thisWeekEventTargets.get("event_id"));
			    
			    thisMonthEventName=new ArrayList<String>();
			    thisMonthEventName=db.getAllForEventsName("Monthly");
			   //thisMonthEventName.add(thisMonthEventTargets.get("event_name"));
			    thisMonthEventNumber=new ArrayList<String>();
			    thisMonthEventNumber=db.getAllForEventsNumber("Monthly");
			    //thisMonthEventNumber.add(thisMonthEventTargets.get("event_number"));
			    thisMonthEventPeriod=new ArrayList<String>();
			    thisMonthEventPeriod=db.getAllForEventsPeriod("Monthly");
			    
			    thisMonthEventAchieved=new ArrayList<String>();
			    thisMonthEventAchieved=db.getAllForEventsNumberAchieved("Monthly");
			    //thisMonthEventNumber.add(thisMonthEventTargets.get("event_number"));
			    thisMonthEventStartDate=new ArrayList<String>();
			    thisMonthEventStartDate=db.getAllForEventsStartDate("Monthly");
			    //thisMonthEventPeriod.add(thisMonthEventTargets.get("event_period"));
			    thisMonthEventDueDate=new ArrayList<String>();
			    thisMonthEventDueDate=db.getAllForEventsDueDate("Monthly");
			    //thisMonthEventDueDate.add(thisMonthEventTargets.get("due_date"));
			    thisMonthEventStatus=new ArrayList<String>();
			    thisMonthEventStatus=db.getAllForEventsSyncStatus("Monthly");
			    //thisMonthEventStatus.add(thisMonthEventTargets.get("sync_status"));
			    thisMonthEventId=new ArrayList<String>();
			    thisMonthEventId=db.getAllForEventsId("Monthly");
			    //thisMonthEventId.add(thisMonthEventTargets.get("event_id"));
			    
			    thisMonthEventLastUpdated=new ArrayList<String>();
			    thisMonthEventLastUpdated=db.getAllForEventsLastUpdated("Monthly");
			    //thisMonthEventId.add(thisMonthEventTargets.get("event_id"));
			    
			    midYearEventName=new ArrayList<String>();
			   // midYearEventName.add(midYearEventTargets.get("event_name"));
			    midYearEventName=db.getAllForEventsName("Mid-year");
			    midYearEventNumber=new ArrayList<String>();
			    midYearEventNumber=db.getAllForEventsNumber("Mid-year");
			   // midYearEventNumber.add(midYearEventTargets.get("event_number"));
			    midYearEventPeriod=new ArrayList<String>();
			    midYearEventPeriod=db.getAllForEventsPeriod("Mid-year");
			    midYearEventAchieved=new ArrayList<String>();
			    midYearEventAchieved=db.getAllForEventsNumberAchieved("Mid-year");
			   // midYearEventNumber.add(midYearEventTargets.get("event_number"));
			 
			   // midYearEventNumber.add(midYearEventTargets.get("event_number"));
			    midYearEventStartDate=new ArrayList<String>();
			    midYearEventStartDate=db.getAllForEventsStartDate("Mid-year");
			    
			    midYearEventDueDate=new ArrayList<String>();
			    midYearEventDueDate=db.getAllForEventsDueDate("Mid-year");
			   // midYearEventDueDate.add(midYearEventTargets.get("due_date"));
			    midYearEventStatus=new ArrayList<String>();
			    midYearEventStatus=db.getAllForEventsSyncStatus("Mid-year");
			    //midYearEventStatus.add(midYearEventTargets.get("sync_status"));
			    midYearEventId=new ArrayList<String>();
			    midYearEventId=db.getAllForEventsId("Mid-year");
			   // midYearEventId.add(midYearEventTargets.get("event_id"));
			    midYearEventLastUpdated=new ArrayList<String>();
			    midYearEventLastUpdated=db.getAllForEventsLastUpdated("Mid-year");
			   // midYearEventId.add(midYearEventTargets.get("event_id"));
			    
			    thisQuarterEventName=new ArrayList<String>();
			    thisQuarterEventName=db.getAllForEventsName("Quarterly");
			    //thisQuarterEventName.add(thisQuarterEventTargets.get("event_name"));
			    thisQuarterEventNumber=new ArrayList<String>();
			    thisQuarterEventNumber=db.getAllForEventsNumber("Quarterly");
			    //thisQuarterEventNumber.add(thisQuarterEventTargets.get("event_number"));
			    thisQuarterEventPeriod=new ArrayList<String>();
			    thisQuarterEventPeriod=db.getAllForEventsPeriod("Quarterly");
			    
			    thisQuarterEventAchieved=new ArrayList<String>();
			    thisQuarterEventAchieved=db.getAllForEventsNumberAchieved("Quarterly");
			    //thisQuarterEventNumber.add(thisQuarterEventTargets.get("event_number"));
			    thisQuarterEventStartDate=new ArrayList<String>();
			    thisQuarterEventStartDate=db.getAllForEventsStartDate("Quarterly");
			   // thisQuarterEventPeriod.add(thisQuarterEventTargets.get("event_period"));
			    thisQuarterEventDueDate=new ArrayList<String>();
			    thisQuarterEventDueDate=db.getAllForEventsDueDate("Quarterly");
			   // thisQuarterEventDueDate.add(thisQuarterEventTargets.get("due_date"));
			    thisQuarterEventStatus=new ArrayList<String>();
			    thisQuarterEventStatus=db.getAllForEventsDueDate("Quarterly");
			    //thisQuarterEventStatus.add(thisQuarterEventTargets.get("sync_status"));
			    thisQuarterEventId=new ArrayList<String>();
			    thisQuarterEventId=db.getAllForEventsId("Quarterly");
			    //thisQuarterEventId.add(thisQuarterEventTargets.get("event_id"));
			    
			    thisQuarterEventLastUpdated=new ArrayList<String>();
			    thisQuarterEventLastUpdated=db.getAllForEventsLastUpdated("Quarterly");
			    //thisQuarterEventId.add(thisQuarterEventTargets.get("event_id"));
			    
			    thisYearEventName=new ArrayList<String>();
			    thisYearEventName=db.getAllForEventsName("Annually");
			    //thisYearEventName.add(thisYearEventTargets.get("event_name"));
			    thisYearEventNumber=new ArrayList<String>();
			    thisYearEventNumber=db.getAllForEventsNumber("Annually");
			    //thisYearEventNumber.add(thisYearEventTargets.get("event_number"));
			    thisYearEventPeriod=new ArrayList<String>();
			    thisYearEventPeriod=db.getAllForEventsPeriod("Annually");
			    
			    thisYearEventStartDate=new ArrayList<String>();
			    thisYearEventStartDate=db.getAllForEventsStartDate("Annually");
			    //thisYearEventNumber.add(thisYearEventTargets.get("event_number"));
			    thisYearEventPeriod=new ArrayList<String>();
			    thisYearEventPeriod=db.getAllForEventsPeriod("Annually");
			    //thisYearEventPeriod.add(thisYearEventTargets.get("event_period"));
			    thisYearEventDueDate=new ArrayList<String>();
			    thisYearEventDueDate=db.getAllForEventsDueDate("Annually");
			    //thisYearEventDueDate.add(thisYearEventTargets.get("due_date"));
			    thisYearEventStatus=new ArrayList<String>();
			    thisYearEventStatus=db.getAllForEventsSyncStatus("Annually");
			    //thisYearEventStatus.add(thisYearEventTargets.get("sync_status"));
			    thisYearEventId=new ArrayList<String>();
			    thisYearEventId=db.getAllForEventsId("Annually");
			    //thisYearEventId.add(thisYearEventTargets.get("event_id"));
			    
			    thisYearEventLastUpdated=new ArrayList<String>();
			    thisYearEventLastUpdated=db.getAllForEventsLastUpdated("Annually");
			    
			    thisYearEventAchieved=new ArrayList<String>();
			    thisYearEventAchieved=db.getAllForEventsNumberAchieved("Annually");
			    //thisYearEventId.add(thisYearEventTargets.get("event_id"));
			    
			    groupItems=new String[]{"To update today","To update this week","To update this month","To update this quarter","Half-year update","To update this year"};
			    events_adapter=new EventBaseAdapter(mContext,todayEventName ,
															todayEventNumber,
																todayEventPeriod,
																todayEventDueDate,
																todayEventAchieved,
																todayEventStartDate,
																todayEventStatus,
																todayEventId,
																todayEventLastUpdated,
																//todayEventNumberRemaining,
							
															/*	tomorrowEventName,
																tomorrowEventNumber,
																tomorrowEventPeriod,
																tomorrowEventDueDate,
																tomorrowEventAchieved,
																tomorrowEventStartDate,
																tomorrowEventStatus,
																tomorrowEventId,
																*/
							
																thisWeekEventName,
																thisWeekEventNumber,
																thisWeekEventPeriod,
																thisWeekEventDueDate,
																thisWeekEventAchieved,
																thisWeekEventStartDate,
																thisWeekEventStatus,
																thisWeekEventId,
																thisWeekEventLastUpdated,
																//thisWeekEventNumberRemaining,

																thisMonthEventName,
																thisMonthEventNumber,
																thisMonthEventPeriod,
																thisMonthEventDueDate,
																thisMonthEventAchieved,
																thisMonthEventStartDate,
																thisMonthEventStatus,
																thisMonthEventId,
																thisMonthEventLastUpdated,
																//thisMonthEventNumberRemaining,
						
																thisQuarterEventName,
																thisQuarterEventNumber,
																thisQuarterEventPeriod,
																thisQuarterEventDueDate,
																thisQuarterEventAchieved,
																thisQuarterEventStartDate,
																thisQuarterEventStatus,
																thisQuarterEventId,
																thisQuarterEventLastUpdated,
																//thisQuarterEventNumberRemaining,
																
																midYearEventName,
																midYearEventNumber,
																midYearEventPeriod,
																midYearEventDueDate, 
																midYearEventAchieved,
																midYearEventStartDate,
																midYearEventStatus,
																midYearEventId,
																midYearEventLastUpdated,
																//midYearEventNumberRemaining,
																
																thisYearEventName,
																thisYearEventNumber,
																thisYearEventPeriod,
																thisYearEventDueDate,
																thisYearEventAchieved,
																thisYearEventStartDate,
																thisYearEventStatus,
																thisYearEventId,
																thisYearEventLastUpdated,
																//thisYearEventNumberRemaining,
																 groupItems,
																listView_events);
			  
			   
			    listView_events.setAdapter(events_adapter);	
			    View empty_view=new View(getActivity());
			    listView_events.setEmptyView(empty_view);
			    events_adapter.notifyDataSetChanged();
			   
			    listView_events.setOnChildClickListener(this);
			    button_show=(Button) rootView.findViewById(R.id.button_show);
			 
			    button_show.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						   eventId=new ArrayList<String>();
							 eventId=db.getAllForEventsId("Daily");
							 coverageId=new ArrayList<String>();
							 coverageId=db.getAllForCoverageId("Daily");
							 otherId=new ArrayList<String>();
							 otherId=db.getAllForOtherId("Daily");
							 learningId=new ArrayList<String>();
							 learningId=db.getAllForLearningId("Daily");
							 int number=eventId.size();
							 int number2=coverageId.size();
							 int number3=otherId.size();
							 int number4=learningId.size();
							 final int counter;
							if(eventId.size()<0){
								number=0;
							}else{
								number=eventId.size();
							}
							if(coverageId.size()<0){
								number2=0;
							}else {
								number2=coverageId.size();
							}
							if(otherId.size()<0){
								number3=0;
							}else{
								number3=otherId.size();
							}
							
							if(learningId.size()<0){
								number4=0;
							}else{
								number4=learningId.size();
							}
							counter=number+number2+number3+number4;	
						if(counter>0){
						Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
						startActivity(intent);
						}else if(counter==0){
							 Toast.makeText(getActivity(), "You have no targets to update!",
							         Toast.LENGTH_SHORT).show();
						}
						
					}
			    	
			    });
			return rootView;
				   
			}
			 
		
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, final long id) {
				selected_items=events_adapter.getChild(groupPosition, childPosition);
				selected_id=Long.parseLong(selected_items[7]);
				String event_name=selected_items[0];
				String event_number=selected_items[1];
				String event_period=selected_items[2];
				String due_date=selected_items[3];
				String start_date=selected_items[5];
				String status=selected_items[6];
				String last_updated=selected_items[8];
				//String achieved=selected_items[4];
				ArrayList<String> number_achieved_list=db.getForUpdateEventNumberAchieved(selected_id, event_period);
				System.out.println(number_achieved_list.get(0));
				System.out.println(event_number);
				Intent intent=new Intent(getActivity(),EventTargetsDetailActivity.class);
				intent.putExtra("event_id",selected_id);
				intent.putExtra("event_name",event_name);
				intent.putExtra("event_number",event_number);
				intent.putExtra("event_period", event_period);
				intent.putExtra("due_date", due_date);
				intent.putExtra("start_date", start_date);
				intent.putExtra("achieved", number_achieved_list.get(0));
				intent.putExtra("status", status);
				intent.putExtra("last_updated", last_updated);
				startActivity(intent);
					return true;
		}
			
	 }
	 public static class CoverageActivity extends Fragment implements OnChildClickListener{

			private Context mContext;															
			private ExpandableListView listView_events;
			private ArrayList<String> todayEventName;
			 private ArrayList<String> todayEventNumber;
			 private ArrayList<String> todayEventPeriod;
			 private ArrayList<String> todayEventDueDate;
			 private ArrayList<String> todayEventStartDate;
			 private ArrayList<String> todayEventAchieved;
			 private ArrayList<String> todayEventStatus;
			 private ArrayList<String> todayEventId;
			 private ArrayList<String> todayEventLastUpdated;
			 private ArrayList<String> todayEventNumberRemaining;
			 
			 private ArrayList<String> tomorrowEventName;
			 private ArrayList<String> tomorrowEventNumber;
			 private ArrayList<String> tomorrowEventPeriod;
			 private ArrayList<String> tomorrowEventDueDate;
			 private ArrayList<String> tomorrowEventStartDate;
			 private ArrayList<String> tomorrowEventAchieved;
			 private ArrayList<String> tomorrowEventStatus;
			 private ArrayList<String> tomorrowEventId;
			 
			 private ArrayList<String> thisWeekEventName;
			 private ArrayList<String> thisWeekEventNumber;
			 private ArrayList<String> thisWeekEventPeriod;
			 private ArrayList<String> thisWeekEventDueDate;
			 private ArrayList<String> thisWeekEventStartDate;
			 private ArrayList<String> thisWeekEventAchieved;
			 private ArrayList<String> thisWeekEventStatus;
			 private ArrayList<String> thisWeekEventId;
			 private ArrayList<String> thisWeekEventLastUpdated;
			 private ArrayList<String> thisWeekEventNumberRemaining;
			 
			 private ArrayList<String> thisMonthEventName;
			 private ArrayList<String> thisMonthEventNumber;
			 private ArrayList<String> thisMonthEventPeriod;
			 private ArrayList<String> thisMonthEventDueDate;
			 private ArrayList<String>  thisMonthEventStartDate;
			 private ArrayList<String>  thisMonthEventAchieved;
			 private ArrayList<String> thisMonthEventStatus;
			 private ArrayList<String> thisMonthEventId;
			 private ArrayList<String> thisMonthEventLastUpdated;
			 private ArrayList<String> thisMonthEventNumberRemaining;
			 
			 private ArrayList<String> thisQuarterEventName;
			 private ArrayList<String> thisQuarterEventNumber;
			 private ArrayList<String> thisQuarterEventPeriod;
			 private ArrayList<String> thisQuarterEventDueDate;
			 private ArrayList<String>  thisQuarterEventStartDate;
			 private ArrayList<String>  thisQuarterEventAchieved;
			 private ArrayList<String> thisQuarterEventStatus;
			 private ArrayList<String> thisQuarterEventId;
			 private ArrayList<String> thisQuarterEventLastUpdated;
			 private ArrayList<String> thisQuarterEventNumberRemaining;
			 
			 private ArrayList<String> midYearEventName;
			 private ArrayList<String> midYearEventNumber;
			 private ArrayList<String> midYearEventPeriod;
			 private ArrayList<String> midYearEventDueDate;
			 private ArrayList<String>  midYearEventStartDate;
			 private ArrayList<String>  midYearEventAchieved;
			 private ArrayList<String> midYearEventStatus;
			 private ArrayList<String> midYearEventId;
			 private ArrayList<String> midYearEventLastUpdated;
			 private ArrayList<String> midYearEventNumberRemaining;
			 
			 private ArrayList<String> thisYearEventName;
			 private ArrayList<String> thisYearEventNumber;
			 private ArrayList<String> thisYearEventPeriod;
			 private ArrayList<String> thisYearEventDueDate;
			 private ArrayList<String>  thisYearEventStartDate;
			 private ArrayList<String> thisYearEventAchieved;
			 private ArrayList<String> thisYearEventStatus;
			 private ArrayList<String> thisYearEventId;
			 private ArrayList<String> thisYearEventLastUpdated;
			 private ArrayList<String> thisYearEventNumberRemaining;
			 
			 private HashMap<String,String> todayEventTargets;//Today
			 private HashMap<String,String> tomorrowEventTargets;
			 private HashMap<String,String> thisWeekEventTargets;
			 private HashMap<String,String> thisMonthEventTargets;
			 private HashMap<String,String> thisQuarterEventTargets;
			 private HashMap<String,String> midYearEventTargets;
			 private HashMap<String,String> thisYearEventTargets;
			 private String[] groupItems;
			private DbHelper db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private ExpandableListView listView_coverage;
			private EventBaseAdapter coverage_adapter;
			private String[] selected_items;
			private RadioGroup category_options;
			private String[] items3;
			int selected_position;
			protected RadioButton category_people;
			private long selected_id;
			private Button button_show;
			private ArrayList<String> eventId;
			private ArrayList<String> coverageId;
			private ArrayList<String> otherId;
			private ArrayList<String> learningId;
			static String due_date ;
			private static TextView dueDateValue;
			static String start_date ;
			static long due_date_to_compare;
			private static TextView startDateValue;
			
			 public CoverageActivity(){

         }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				 rootView=inflater.inflate(R.layout.activity_coverage,null,false);
			    mContext=getActivity().getApplicationContext();
			    db=new DbHelper(getActivity());
			    listView_coverage=(ExpandableListView) rootView.findViewById(R.id.expandableListView1);
			    listView_coverage.setOnChildClickListener(this);
			   
			    todayEventName=new ArrayList<String>();
			    todayEventName=db.getAllForCoverageName("Daily");
			   // todayEventName.add(todayEventTargets.get("event_name"));
			   todayEventNumber=new ArrayList<String>();
			    todayEventNumber=db.getAllForCoverageNumber("Daily");
			    
			    todayEventAchieved=new ArrayList<String>();
			    todayEventAchieved=db.getAllForCoverageNumberAchieved("Daily");
			    
			    todayEventStartDate=new ArrayList<String>();
			    todayEventStartDate=db.getAllForCoverageStartDate("Daily");
			    
			   // todayEventNumber.add(todayEventTargets.get("event_number"));
			    todayEventPeriod=new ArrayList<String>();
			    //todayEventPeriod.add(todayEventTargets.get("event_period"));
			    todayEventPeriod=db.getAllForCoveragePeriod("Daily");
			    
			    todayEventDueDate=new ArrayList<String>();
			    todayEventDueDate=db.getAllForCoverageDueDate("Daily");
			   // todayEventDueDate.add(todayEventTargets.get("due_date"));
			    
			    todayEventStatus=new ArrayList<String>();
			   // todayEventStatus.add(todayEventTargets.get("sync_status"));
			    todayEventStatus=db.getAllForCoverageSyncStatus("Daily");
			    
			    todayEventId=new ArrayList<String>();
			  //  todayEventId.add(todayEventTargets.get("event_id"));
			    todayEventId=db.getAllForCoverageId("Daily");
			    
			    todayEventLastUpdated=new ArrayList<String>();
				  //  todayEventId.add(todayEventTargets.get("event_id"));
				todayEventLastUpdated=db.getAllForCoverageLastUpdated("Daily");
			    
			    /*
			    tomorrowEventName=new ArrayList<String>();
			    tomorrowEventName=db.getAllForCoverageName("Weekly");
			    //tomorrowEventName.add(tomorrowEventTargets.get("event_name"));
			    
			    /*
			    tomorrowEventNumber=new ArrayList<String>();
			    tomorrowEventNumber=db.getAllForCoverageNumber("Weekly");
			    //tomorrowEventNumber.add(tomorrowEventTargets.get("event_number"));
			    tomorrowEventPeriod=new ArrayList<String>();
			    tomorrowEventPeriod=db.getAllForCoveragePeriod("Weekly");
			    //tomorrowEventPeriod.add(tomorrowEventTargets.get("event_period"));
			    tomorrowEventDueDate=new ArrayList<String>();
			    tomorrowEventDueDate=db.getAllForCoverageDueDate("Weekly");
			    //tomorrowEventDueDate.add(tomorrowEventTargets.get("due_date"));
			    tomorrowEventStatus=new ArrayList<String>();
			    tomorrowEventStatus=db.getAllForCoverageSyncStatus("Weekly");
			    //tomorrowEventStatus.add(tomorrowEventTargets.get("sync_status"));
			    tomorrowEventId=new ArrayList<String>();
			    tomorrowEventId=db.getAllForCoverageId("Weekly");
			    //tomorrowEventId.add(tomorrowEventTargets.get("event_id"));
			    */
			    
			    thisWeekEventName=new ArrayList<String>();
			    thisWeekEventName=db.getAllForCoverageName("Weekly");
			    //thisWeekEventName.add(thisWeekEventTargets.get("event_name"));
			    thisWeekEventNumber=new ArrayList<String>();
			    thisWeekEventNumber=db.getAllForCoverageNumber("Weekly");
			   // thisWeekEventNumber.add(thisWeekEventTargets.get("event_number"));
			    thisWeekEventPeriod=new ArrayList<String>();
			    thisWeekEventPeriod=db.getAllForCoveragePeriod("Weekly");
			   // thisWeekEventPeriod.add(thisWeekEventTargets.get("event_period"));
			    thisWeekEventDueDate=new ArrayList<String>();
			    thisWeekEventDueDate=db.getAllForCoverageDueDate("Weekly");
			    //thisWeekEventDueDate.add(thisWeekEventTargets.get("due_date"));
			    thisWeekEventStatus=new ArrayList<String>();
			    thisWeekEventStatus=db.getAllForCoverageSyncStatus("Weekly");
			    //thisWeekEventStatus.add(thisWeekEventTargets.get("sync_status"));
			    thisWeekEventId=new ArrayList<String>();
			    thisWeekEventId=db.getAllForCoverageId("Weekly");
			    //thisWeekEventId.add(thisWeekEventTargets.get("event_id"));
			    
			    thisWeekEventId=new ArrayList<String>();
			    thisWeekEventId=db.getAllForCoverageId("Weekly");
			    //thisWeekEventId.add(thisWeekEventTargets.get("event_id"));
			    
			    thisWeekEventLastUpdated=new ArrayList<String>();
			    thisWeekEventLastUpdated=db.getAllForCoverageLastUpdated("Weekly");
			    //thisWeekEventId.add(thisWeekEventTargets.get("event_id"));
			    
			    thisWeekEventAchieved=new ArrayList<String>();
			    thisWeekEventAchieved=db.getAllForCoverageNumberAchieved("Weekly");
			    
			    thisWeekEventStartDate=new ArrayList<String>();
			    thisWeekEventStartDate=db.getAllForCoverageStartDate("Weekly");
			    
			    thisMonthEventName=new ArrayList<String>();
			    thisMonthEventName=db.getAllForCoverageName("Monthly");
			   //thisMonthEventName.add(thisMonthEventTargets.get("event_name"));
			    thisMonthEventNumber=new ArrayList<String>();
			    thisMonthEventNumber=db.getAllForCoverageNumber("Monthly");
			    //thisMonthEventNumber.add(thisMonthEventTargets.get("event_number"));
			    thisMonthEventPeriod=new ArrayList<String>();
			    thisMonthEventPeriod=db.getAllForCoveragePeriod("Monthly");
			    //thisMonthEventPeriod.add(thisMonthEventTargets.get("event_period"));
			    thisMonthEventDueDate=new ArrayList<String>();
			    thisMonthEventDueDate=db.getAllForCoverageDueDate("Monthly");
			    //thisMonthEventDueDate.add(thisMonthEventTargets.get("due_date"));
			    thisMonthEventStatus=new ArrayList<String>();
			    thisMonthEventStatus=db.getAllForCoverageSyncStatus("Monthly");
			    //thisMonthEventStatus.add(thisMonthEventTargets.get("sync_status"));
			    thisMonthEventId=new ArrayList<String>();
			    thisMonthEventId=db.getAllForCoverageId("Monthly");
			    //thisMonthEventId.add(thisMonthEventTargets.get("event_id"));
			    
			    thisMonthEventLastUpdated=new ArrayList<String>();
			    thisMonthEventLastUpdated=db.getAllForCoverageLastUpdated("Monthly");
			    //thisMonthEventId.add(thisMonthEventTargets.get("event_id"));
			    thisMonthEventAchieved=new ArrayList<String>();
			    thisMonthEventAchieved=db.getAllForCoverageNumberAchieved("Monthly");
			    
			    thisMonthEventStartDate=new ArrayList<String>();
			    thisMonthEventStartDate=db.getAllForCoverageStartDate("Monthly");
			    
			    midYearEventName=new ArrayList<String>();
			   // midYearEventName.add(midYearEventTargets.get("event_name"));
			    midYearEventName=db.getAllForCoverageName("Mid-year");
			    midYearEventNumber=new ArrayList<String>();
			    midYearEventNumber=db.getAllForCoverageNumber("Mid-year");
			   // midYearEventNumber.add(midYearEventTargets.get("event_number"));
			    midYearEventPeriod=new ArrayList<String>();
			    midYearEventPeriod=db.getAllForCoveragePeriod("Mid-year");
			    //midYearEventPeriod.add(midYearEventTargets.get("event_period"));
			    midYearEventDueDate=new ArrayList<String>();
			    midYearEventDueDate=db.getAllForCoverageDueDate("Mid-year");
			   // midYearEventDueDate.add(midYearEventTargets.get("due_date"));
			    midYearEventStatus=new ArrayList<String>();
			    midYearEventStatus=db.getAllForCoverageSyncStatus("Mid-year");
			    //midYearEventStatus.add(midYearEventTargets.get("sync_status"));
			    midYearEventId=new ArrayList<String>();
			    midYearEventId=db.getAllForCoverageId("Mid-year");
			   // midYearEventId.add(midYearEventTargets.get("event_id"));
			    midYearEventAchieved=new ArrayList<String>();
			    midYearEventAchieved=db.getAllForCoverageNumberAchieved("Mid-year");
			    
			    midYearEventStartDate=new ArrayList<String>();
			    midYearEventStartDate=db.getAllForCoverageStartDate("Mid-year");
			    
			    midYearEventLastUpdated=new ArrayList<String>();
			    midYearEventLastUpdated=db.getAllForCoverageLastUpdated("Mid-year");
			    
			    thisQuarterEventName=new ArrayList<String>();
			    thisQuarterEventName=db.getAllForCoverageName("Quarterly");
			    //thisQuarterEventName.add(thisQuarterEventTargets.get("event_name"));
			    thisQuarterEventNumber=new ArrayList<String>();
			    thisQuarterEventNumber=db.getAllForCoverageNumber("Quarterly");
			    //thisQuarterEventNumber.add(thisQuarterEventTargets.get("event_number"));
			    thisQuarterEventPeriod=new ArrayList<String>();
			    thisQuarterEventPeriod=db.getAllForCoveragePeriod("Quarterly");
			   // thisQuarterEventPeriod.add(thisQuarterEventTargets.get("event_period"));
			    thisQuarterEventDueDate=new ArrayList<String>();
			    thisQuarterEventDueDate=db.getAllForCoverageDueDate("Quarterly");
			   // thisQuarterEventDueDate.add(thisQuarterEventTargets.get("due_date"));
			    thisQuarterEventStatus=new ArrayList<String>();
			    thisQuarterEventStatus=db.getAllForCoverageSyncStatus("Quarterly");
			    //thisQuarterEventStatus.add(thisQuarterEventTargets.get("sync_status"));
			    thisQuarterEventId=new ArrayList<String>();
			    thisQuarterEventId=db.getAllForCoverageId("Quarterly");
			    //thisQuarterEventId.add(thisQuarterEventTargets.get("event_id"));
			    thisQuarterEventAchieved=new ArrayList<String>();
			    thisQuarterEventAchieved=db.getAllForCoverageNumberAchieved("Quarterly");
			    
			    thisQuarterEventStartDate=new ArrayList<String>();
			    thisQuarterEventStartDate=db.getAllForCoverageStartDate("Quarterly");
			    
			    thisQuarterEventLastUpdated=new ArrayList<String>();
			    thisQuarterEventLastUpdated=db.getAllForCoverageLastUpdated("Quarterly");
			    
			    thisYearEventName=new ArrayList<String>();
			    thisYearEventName=db.getAllForCoverageName("Annually");
			    //thisYearEventName.add(thisYearEventTargets.get("event_name"));
			    thisYearEventNumber=new ArrayList<String>();
			    thisYearEventNumber=db.getAllForCoverageNumber("Annually");
			    //thisYearEventNumber.add(thisYearEventTargets.get("event_number"));
			    thisYearEventPeriod=new ArrayList<String>();
			    thisYearEventPeriod=db.getAllForCoveragePeriod("Annually");
			    //thisYearEventPeriod.add(thisYearEventTargets.get("event_period"));
			    thisYearEventDueDate=new ArrayList<String>();
			    thisYearEventDueDate=db.getAllForCoverageDueDate("Annually");
			    //thisYearEventDueDate.add(thisYearEventTargets.get("due_date"));
			    thisYearEventStatus=new ArrayList<String>();
			    thisYearEventStatus=db.getAllForCoverageSyncStatus("Annually");
			    //thisYearEventStatus.add(thisYearEventTargets.get("sync_status"));
			    thisYearEventId=new ArrayList<String>();
			    thisYearEventId=db.getAllForCoverageId("Annually");
			    //thisYearEventId.add(thisYearEventTargets.get("event_id"));
			    thisYearEventAchieved=new ArrayList<String>();
			    thisYearEventAchieved=db.getAllForCoverageNumberAchieved("Annually");
			    
			    thisYearEventLastUpdated=new ArrayList<String>();
			    thisYearEventLastUpdated=db.getAllForCoverageLastUpdated("Annually");
			    
			    
			    thisYearEventStartDate=new ArrayList<String>();
			    thisYearEventStartDate=db.getAllForCoverageStartDate("Annually");
			    
			    groupItems=new String[]{"To update today","To update this week","To update this month","To update this quarter","Half-year update","To update this year"};
			    
			    coverage_adapter=new EventBaseAdapter(mContext,todayEventName ,
															todayEventNumber,
															todayEventPeriod,
															todayEventDueDate,
															todayEventAchieved,
															todayEventStartDate,
															todayEventStatus,
															todayEventId,
															todayEventLastUpdated,
															//todayEventNumberRemaining,

					/*	tomorrowEventName,
						tomorrowEventNumber,
						tomorrowEventPeriod,
						tomorrowEventDueDate,
						tomorrowEventAchieved,
						tomorrowEventStartDate,
						tomorrowEventStatus,
						tomorrowEventId,
						*/

						thisWeekEventName,
						thisWeekEventNumber,
						thisWeekEventPeriod,
						thisWeekEventDueDate,
						thisWeekEventAchieved,
						thisWeekEventStartDate,
						thisWeekEventStatus,
						thisWeekEventId,
						thisWeekEventLastUpdated,
						//thisWeekEventNumberRemaining,

						thisMonthEventName,
						thisMonthEventNumber,
						thisMonthEventPeriod,
						thisMonthEventDueDate,
						thisMonthEventAchieved,
						thisMonthEventStartDate,
						thisMonthEventStatus,
						thisMonthEventId,
						thisMonthEventLastUpdated,
						//thisMonthEventNumberRemaining,

						thisQuarterEventName,
						thisQuarterEventNumber,
						thisQuarterEventPeriod,
						thisQuarterEventDueDate,
						thisQuarterEventAchieved,
						thisQuarterEventStartDate,
						thisQuarterEventStatus,
						thisQuarterEventId,
						thisQuarterEventLastUpdated,
						//thisQuarterEventNumberRemaining,
						
						midYearEventName,
						midYearEventNumber,
						midYearEventPeriod,
						midYearEventDueDate, 
						midYearEventAchieved,
						midYearEventStartDate,
						midYearEventStatus,
						midYearEventId,
						midYearEventLastUpdated,
						//midYearEventNumberRemaining,
						
						thisYearEventName,
						thisYearEventNumber,
						thisYearEventPeriod,
						thisYearEventDueDate,
						thisYearEventAchieved,
						thisYearEventStartDate,
						thisYearEventStatus,
						thisYearEventId,
						thisYearEventLastUpdated,
						//thisYearEventNumberRemaining,
						 groupItems,
						listView_coverage);
			  
			   
			    listView_coverage.setAdapter(coverage_adapter);	
			    button_show=(Button) rootView.findViewById(R.id.button_show);
			  
			    button_show.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						  eventId=new ArrayList<String>();
							 eventId=db.getAllForEventsId("Daily");
							 coverageId=new ArrayList<String>();
							 coverageId=db.getAllForCoverageId("Daily");
							 otherId=new ArrayList<String>();
							 otherId=db.getAllForOtherId("Daily");
							 learningId=new ArrayList<String>();
							 learningId=db.getAllForLearningId("Daily");
							 int number=eventId.size();
							 int number2=coverageId.size();
							 int number3=otherId.size();
							 int number4=learningId.size();
							 final int counter;
							if(eventId.size()<0){
								number=0;
							}else{
								number=eventId.size();
							}
							if(coverageId.size()<0){
								number2=0;
							}else {
								number2=coverageId.size();
							}
							if(otherId.size()<0){
								number3=0;
							}else{
								number3=otherId.size();
							}
							
							if(learningId.size()<0){
								number4=0;
							}else{
								number4=learningId.size();
							}
							counter=number+number2+number3+number4;	
						if(counter>0){
						Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
						startActivity(intent);
						}else if(counter==0){
							 Toast.makeText(getActivity(), "You have no targets to update!",
							         Toast.LENGTH_SHORT).show();
						}
						
					}
			    	
			    });
		    	listView_coverage.setOnChildClickListener(this);
			return rootView;
				   
			}
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, final long id) {
				selected_items=coverage_adapter.getChild(groupPosition, childPosition);
				selected_id=Long.parseLong(selected_items[7]);
				//System.out.println(selected_items[0]+" "+selected_items[1]);
				String coverage_name=selected_items[0];
				String coverage_number=selected_items[1];
				String coverage_period=selected_items[2];
				String due_date=selected_items[3];
				String start_date=selected_items[5];
				String status=selected_items[6];
				String achieved=selected_items[4];
				String last_updated=selected_items[8];
				ArrayList<String> number_achieved_list=db.getForUpdateCoverageNumberAchieved(selected_id, coverage_period);
				Intent intent=new Intent(getActivity(),CoverageTargetsDetailActivity.class);
				intent.putExtra("coverage_id",selected_id);
				intent.putExtra("coverage_name",coverage_name);
				intent.putExtra("coverage_number",coverage_number);
				intent.putExtra("coverage_period", coverage_period);
				intent.putExtra("due_date", due_date);
				intent.putExtra("start_date", start_date);
				intent.putExtra("achieved", number_achieved_list.get(0));
				intent.putExtra("status", status);
				intent.putExtra("last_updated", last_updated);
				startActivity(intent);
				return true;
			}
			
	 }
	 
	 public static class LearningActivity extends Fragment implements OnChildClickListener{

			private Context mContext;															
			
			private ArrayList<String> todayCategory;
			private  ArrayList<String> todayCourse;
			 private  ArrayList<String> todayTopic;
			 private  ArrayList<String> todayEventPeriod;
			 private  ArrayList<String> todayEventDueDate;
			 private  ArrayList<String> todayEventStartDate;
			 private  ArrayList<String> todayEventStatus;
			 private  ArrayList<String> todayEventId;
			 private ArrayList<String> todayEventLastUpdated;
			 
			 /*
			 private  ArrayList<String> tomorrowCategory;
			 private  ArrayList<String> tomorrowCourse;
			 private  ArrayList<String> tomorrowTopic;
			 private  ArrayList<String> tomorrowEventPeriod;
			 private  ArrayList<String> tomorrowEventDueDate;
			 private  ArrayList<String> tomorrowEventStartDate;
			 private  ArrayList<String> tomorrowEventStatus;
			 private  ArrayList<String> tomorrowEventId;
			 */
			 private  ArrayList<String> thisWeekCategory;
			 private  ArrayList<String> thisWeekCourse;
			 private  ArrayList<String> thisWeekTopic;
			 private  ArrayList<String> thisWeekEventPeriod;
			 private  ArrayList<String> thisWeekEventDueDate;
			 private  ArrayList<String> thisWeekEventStartDate;
			 private  ArrayList<String> thisWeekEventStatus;
			 private  ArrayList<String> thisWeekEventId; 
			 private ArrayList<String>  thisWeekEventLastUpdated;
			 
			 private ArrayList<String> thisMonthCategory;
			 private  ArrayList<String> thisMonthCourse;
			 private  ArrayList<String> thisMonthTopic;
			 private  ArrayList<String> thisMonthEventPeriod;
			 private  ArrayList<String> thisMonthEventDueDate;
			 private  ArrayList<String> thisMonthEventStartDate;
			 private  ArrayList<String> thisMonthEventStatus;
			 private  ArrayList<String> thisMonthEventId;
			 private ArrayList<String>  thisMonthEventLastUpdated;
			 
			 private  ArrayList<String> thisQuarterCategory;
			 private  ArrayList<String> thisQuarterCourse;
			 private  ArrayList<String> thisQuarterTopic;
			 private  ArrayList<String> thisQuarterEventPeriod;
			 private  ArrayList<String> thisQuarterEventDueDate;
			 private  ArrayList<String> thisQuarterEventStartDate;
			 private  ArrayList<String> thisQuarterEventStatus;
			 private  ArrayList<String> thisQuarterEventId;
			 private ArrayList<String>  thisQuarterEventLastUpdated;
			 
			 private  ArrayList<String> midYearCategory;
			 private  ArrayList<String> midYearCourse;
			 private  ArrayList<String> midYearTopic;
			 private  ArrayList<String> midYearEventPeriod;
			 private  ArrayList<String> midYearEventDueDate;
			 private  ArrayList<String> midYearEventStartDate;
			 private  ArrayList<String> midYearEventStatus;
			 private  ArrayList<String> midYearEventId;
			 private ArrayList<String>  midYearEventLastUpdated;
			 
			 private  ArrayList<String> thisYearCategory;
			 private  ArrayList<String> thisYearCourse;
			 private  ArrayList<String> thisYearTopic;
			 private  ArrayList<String> thisYearEventPeriod;
			 private  ArrayList<String> thisYearEventDueDate;
			 private  ArrayList<String> thisYearEventStartDate;
			 private  ArrayList<String> thisYearEventStatus;
			 private  ArrayList<String> thisYearEventId;
			 private ArrayList<String>  thisYearEventLastUpdated;
			 
			 private HashMap<String,String> todayEventTargets;//Today
			 private HashMap<String,String> tomorrowEventTargets;
			 private HashMap<String,String> thisWeekEventTargets;
			 private HashMap<String,String> thisMonthEventTargets;
			 private HashMap<String,String> thisQuarterEventTargets;
			 private HashMap<String,String> midYearEventTargets;
			 private HashMap<String,String> thisYearEventTargets;
			 private DbHelper db;
			 private LearningBaseAdapter learning_adapter;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			 View rootView;
			private TextView textStatus;

			private String selected_item;

			private String[] groupItem;

			private int[] imageId;
			static long due_date_to_compare;
			int selected_position;

			private ExpandableListView learningList;

			private String[] selected_items;
			static String due_date ;
			private static TextView dueDateValueLearning;
			static String start_date ;
			private static TextView startDateValue;
			private long selected_id;

			private String[] groupItems;

			private Button button_show;

			private ArrayList<String> eventId;

			private ArrayList<String> coverageId;

			private ArrayList<String> otherId;

			private ArrayList<String> learningId;
			 public LearningActivity(){

      }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				rootView=inflater.inflate(R.layout.activity_learning,null,false);
			    mContext=getActivity().getApplicationContext();
			    db=new DbHelper(getActivity());
			    learningList=(ExpandableListView) rootView.findViewById(R.id.listView_learningCategory);
			    todayCategory=new ArrayList<String>();
			    todayCategory=db.getAllForLearningCategory("Daily");
			    //todayCategory.add(todayEventTargets.get("learning_category"));
			    todayCourse=new ArrayList<String>();
			    todayCourse=db.getAllForLearningCourse("Daily");
			    //todayCourse.add(todayEventTargets.get("learning_topic"));
			    todayTopic=new ArrayList<String>();
			    todayTopic=db.getAllForLearningTopic("Daily");
			    //todayTopic.add(todayEventTargets.get("learning_description"));
			    todayEventPeriod=new ArrayList<String>();
			    todayEventPeriod=db.getAllForLearningPeriod("Daily");
			   // todayEventPeriod.add(todayEventTargets.get("learning_period"));
			    todayEventDueDate=new ArrayList<String>();
			    todayEventDueDate=db.getAllForLearningDueDate("Daily");
			    
			    todayEventStartDate=new ArrayList<String>();
			    todayEventStartDate=db.getAllForLearningStartDate("Daily");
			    //todayEventDueDate.add(todayEventTargets.get("due_date"));
			    todayEventStatus=new ArrayList<String>();
			    todayEventStatus=db.getAllForLearningSyncStatus("Daily");
			  //  todayEventStatus.add(todayEventTargets.get("sync_status"));
			    todayEventId=new ArrayList<String>();
			    todayEventId=db.getAllForLearningId("Daily");
			    //todayEventId.add(todayEventTargets.get("learning_id"));
			    todayEventLastUpdated=new ArrayList<String>();
			    todayEventLastUpdated=db.getAllForLearningLastUpdated("Daily");
			    //todayEventId.add(todayEventTargets.get("learning_id"));
			    
			    /*
			    tomorrowCategory=new ArrayList<String>();
			    tomorrowCategory.add(tomorrowEventTargets.get("learning_category"));
			    tomorrowCourse=new ArrayList<String>();
			    tomorrowCourse.add(tomorrowEventTargets.get("learning_description"));
			    tomorrowTopic=new ArrayList<String>();
			    tomorrowTopic.add(tomorrowEventTargets.get("learning_topic"));
			    tomorrowEventPeriod=new ArrayList<String>();
			    tomorrowEventPeriod.add(tomorrowEventTargets.get("learning_period"));
			    tomorrowEventDueDate=new ArrayList<String>();
			    tomorrowEventDueDate.add(tomorrowEventTargets.get("due_date"));
			    tomorrowEventStatus=new ArrayList<String>();
			    tomorrowEventStatus.add(tomorrowEventTargets.get("sync_status"));
			    tomorrowEventId=new ArrayList<String>();
			    tomorrowEventId.add(tomorrowEventTargets.get("learning_id"));
			    */
			    thisWeekCategory=new ArrayList<String>();
			    thisWeekCategory=db.getAllForLearningCategory("Weekly");
			    //thisWeekCategory.add(thisWeekEventTargets.get("learning_category"));
			    thisWeekCourse=new ArrayList<String>();
			    thisWeekCourse=db.getAllForLearningCourse("Weekly");
			    //thisWeekCourse.add(thisWeekEventTargets.get("learning_description"));
			    thisWeekTopic=new ArrayList<String>();
			    thisWeekTopic=db.getAllForLearningTopic("Weekly");
			    //thisWeekTopic.add(thisWeekEventTargets.get("learning_topic"));
			    thisWeekEventPeriod=new ArrayList<String>();
			    thisWeekEventPeriod=db.getAllForLearningPeriod("Weekly");
			   // thisWeekEventPeriod.add(thisWeekEventTargets.get("learning_period"));
			    thisWeekEventDueDate=new ArrayList<String>();
			    thisWeekEventDueDate=db.getAllForLearningDueDate("Weekly");
			    
			    thisWeekEventStartDate=new ArrayList<String>();
			    thisWeekEventStartDate=db.getAllForLearningStartDate("Weekly");
			   // thisWeekEventDueDate.add(thisWeekEventTargets.get("due_date"));
			    thisWeekEventStatus=new ArrayList<String>();
			    thisWeekEventStatus=db.getAllForLearningSyncStatus("Weekly");
			   // thisWeekEventStatus.add(thisWeekEventTargets.get("sync_status"));
			    thisWeekEventId=new ArrayList<String>();
			    thisWeekEventId=db.getAllForLearningId("Weekly");
			   // thisWeekEventId.add(thisWeekEventTargets.get("learning_id"));
			    thisWeekEventLastUpdated=new ArrayList<String>();
			    thisWeekEventLastUpdated=db.getAllForLearningLastUpdated("Weekly");
			   // thisWeekEventId.add(thisWeekEventTargets.get("learning_id"));
			    
			    thisMonthCategory=new ArrayList<String>();
			    thisMonthCategory=db.getAllForLearningCategory("Monthly");
			    //thisMonthCategory.add(thisMonthEventTargets.get("learning_category"));
			    thisMonthCourse=new ArrayList<String>();
			    thisMonthCourse=db.getAllForLearningCourse("Monthly");
			    //thisMonthCourse.add(thisMonthEventTargets.get("learning_description"));
			    thisMonthTopic=new ArrayList<String>();
			    thisMonthTopic=db.getAllForLearningTopic("Monthly");
			    //thisMonthTopic.add(thisMonthEventTargets.get("learning_topic"));
			    thisMonthEventPeriod=new ArrayList<String>();
			    thisMonthEventPeriod=db.getAllForLearningPeriod("Monthly");
			    //thisMonthEventPeriod.add(thisMonthEventTargets.get("learning_period"));
			    thisMonthEventDueDate=new ArrayList<String>();
			    thisMonthEventDueDate=db.getAllForLearningDueDate("Monthly");
			    
			    thisMonthEventStartDate=new ArrayList<String>();
			    thisMonthEventStartDate=db.getAllForLearningStartDate("Monthly");
			    //thisMonthEventDueDate.add(thisMonthEventTargets.get("due_date"));
			    thisMonthEventStatus=new ArrayList<String>();
			    thisMonthEventStatus=db.getAllForLearningSyncStatus("Monthly");
			    //thisMonthEventStatus.add(thisMonthEventTargets.get("sync_status"));
			    thisMonthEventId=new ArrayList<String>();
			    thisMonthEventId=db.getAllForLearningId("Monthly");
			   // thisMonthEventId.add(thisMonthEventTargets.get("learning_id"));
			    thisMonthEventLastUpdated=new ArrayList<String>();
			    thisMonthEventLastUpdated=db.getAllForLearningLastUpdated("Monthly");
			   // thisMonthEventId.add(thisMonthEventTargets.get("learning_id"));
			    
			    
			    midYearCategory=new ArrayList<String>();
			    midYearCategory=db.getAllForLearningCategory("Mid-year");
			    //midYearCategory.add(midYearEventTargets.get("learning_category"));
			    midYearCourse=new ArrayList<String>();
			    midYearCourse=db.getAllForLearningCourse("Mid-year");
			   // midYearCourse.add(midYearEventTargets.get("learning_description"));
			    midYearTopic=new ArrayList<String>();
			    midYearTopic=db.getAllForLearningTopic("Mid-year");
			    //midYearTopic.add(midYearEventTargets.get("learning_topic"));
			    midYearEventPeriod=new ArrayList<String>();
			    midYearEventPeriod=db.getAllForLearningPeriod("Mid-year");
			   // midYearEventPeriod.add(midYearEventTargets.get("learning_period"));
			    midYearEventDueDate=new ArrayList<String>();
			    midYearEventDueDate=db.getAllForLearningDueDate("Mid-year");
			   // midYearEventDueDate.add(midYearEventTargets.get("due_date"));
			    
			    midYearEventStartDate=new ArrayList<String>();
			    midYearEventStartDate=db.getAllForLearningStartDate("Mid-year");
			    
			    midYearEventStatus=new ArrayList<String>();
			    midYearEventStatus=db.getAllForLearningSyncStatus("Mid-year");
			    //midYearEventStatus.add(midYearEventTargets.get("sync_status"));
			    midYearEventId=new ArrayList<String>();
			    midYearEventId=db.getAllForLearningId("Mid-year");
			    //midYearEventId.add(midYearEventTargets.get("learning_id"));
			    midYearEventLastUpdated=new ArrayList<String>();
			    midYearEventLastUpdated=db.getAllForLearningLastUpdated("Mid-year");
			    //midYearEventId.add(midYearEventTargets.get("learning_id"));
			    
			    thisQuarterCategory=new ArrayList<String>();
			    thisQuarterCategory=db.getAllForLearningCategory("Quarterly");
			    //thisQuarterCategory.add(thisQuarterEventTargets.get("learning_category"));
			    thisQuarterCourse=new ArrayList<String>();
			    thisQuarterCourse=db.getAllForLearningCourse("Quarterly");
			    //thisQuarterCourse.add(thisQuarterEventTargets.get("learning_description"));
			    thisQuarterTopic=new ArrayList<String>();
			    thisQuarterTopic=db.getAllForLearningTopic("Quarterly");
			   // thisQuarterTopic.add(thisQuarterEventTargets.get("learning_topic"));
			    thisQuarterEventPeriod=new ArrayList<String>();
			    thisQuarterEventPeriod=db.getAllForLearningPeriod("Quarterly");
			    //thisQuarterEventPeriod.add(thisQuarterEventTargets.get("learning_period"));
			    thisQuarterEventDueDate=new ArrayList<String>();
			    thisQuarterEventDueDate=db.getAllForLearningDueDate("Quarterly");
			    
			    thisQuarterEventStartDate=new ArrayList<String>();
			    thisQuarterEventStartDate=db.getAllForLearningStartDate("Quarterly");
			    //thisQuarterEventDueDate.add(thisQuarterEventTargets.get("due_date"));
			    thisQuarterEventStatus=new ArrayList<String>();
			    thisQuarterEventStatus=db.getAllForLearningSyncStatus("Quarterly");
			   // thisQuarterEventStatus.add(thisQuarterEventTargets.get("sync_status"));
			    thisQuarterEventId=new ArrayList<String>();
			    thisQuarterEventId=db.getAllForLearningId("Quarterly");
			   // thisQuarterEventId.add(thisQuarterEventTargets.get("learning_id"));
			    thisQuarterEventLastUpdated=new ArrayList<String>();
			    thisQuarterEventLastUpdated=db.getAllForLearningLastUpdated("Quarterly");
			   // thisQuarterEventId.add(thisQuarterEventTargets.get("learning_id"));
			    
			    thisYearCategory=new ArrayList<String>();
			    thisYearCategory=db.getAllForLearningCategory("Annually");
			    //thisYearCategory.add(thisYearEventTargets.get("learning_category"));
			    thisYearCourse=new ArrayList<String>();
			    thisYearCourse=db.getAllForLearningCourse("Annually");
			    //thisYearCourse.add(thisYearEventTargets.get("learning_description"));
			    thisYearTopic=new ArrayList<String>();
			    thisYearTopic=db.getAllForLearningTopic("Annually");
			    //thisYearTopic.add(thisYearEventTargets.get("learning_topic"));
			    thisYearEventPeriod=new ArrayList<String>();
			    thisYearEventPeriod=db.getAllForLearningPeriod("Annually");
			    //thisYearEventPeriod.add(thisYearEventTargets.get("learning_period"));
			    thisYearEventStartDate=new ArrayList<String>();
			    thisYearEventStartDate=db.getAllForLearningStartDate("Annually");
			    
			    thisYearEventDueDate=new ArrayList<String>();
			    thisYearEventDueDate=db.getAllForLearningDueDate("Annually");
			    //thisYearEventDueDate.add(thisYearEventTargets.get("due_date"));
			    thisYearEventStatus=new ArrayList<String>();
			    thisYearEventStatus=db.getAllForLearningSyncStatus("Annually");
			    //thisYearEventStatus.add(thisYearEventTargets.get("sync_status"));
			    thisYearEventId=new ArrayList<String>();
			    thisYearEventId=db.getAllForLearningId("Annually");
			    
			    thisYearEventLastUpdated=new ArrayList<String>();
			    thisYearEventLastUpdated=db.getAllForLearningLastUpdated("Annually");
			    //thisYearEventId.add(thisYearEventTargets.get("learning_id"));
			    
			    groupItems=new String[]{"To update today","To update this week","To update this month","To update this quarter","Half-year update","To update this year"};
			    learning_adapter=new LearningBaseAdapter(mContext, todayCategory,
						 todayCourse,
						 todayTopic,
						 todayEventPeriod,
						 todayEventDueDate,
						 todayEventStartDate,
						 todayEventStatus,
						  todayEventId,
						  todayEventLastUpdated,
						  
						  /*
						 tomorrowCategory,
						 tomorrowCourse,
						 tomorrowTopic,
						 tomorrowEventPeriod,
						 tomorrowEventDueDate,
						 tomorrowEventStatus,
						 tomorrowEventId,
*/
						 thisWeekCategory,
						 thisWeekCourse,
						 thisWeekTopic,
						 thisWeekEventPeriod,
						 thisWeekEventDueDate,
						 thisWeekEventStartDate,
						 thisWeekEventStatus,
						 thisWeekEventId,
						 thisWeekEventLastUpdated,

						 thisMonthCategory,
						 thisMonthCourse,
						 thisMonthTopic,
						 thisMonthEventPeriod,
						 thisMonthEventDueDate,
						 thisMonthEventStartDate,
						 thisMonthEventStatus,
						 thisMonthEventId,
						 thisMonthEventLastUpdated,

						 thisQuarterCategory,
						 thisQuarterCourse,
						 thisQuarterTopic,
						 thisQuarterEventPeriod,
						 thisQuarterEventDueDate,
						 thisQuarterEventStartDate,
						 thisQuarterEventStatus,
						 thisQuarterEventId,
						 thisQuarterEventLastUpdated,

						 midYearCategory,
						 midYearCourse,
						 midYearTopic,
						 midYearEventPeriod,
						 midYearEventDueDate,
						 midYearEventStartDate,
						 midYearEventStatus,
						 midYearEventId,
						 midYearEventLastUpdated,

						 thisYearCategory,
						 thisYearCourse,
						 thisYearTopic,
						 thisYearEventPeriod,
						 thisYearEventDueDate,
						 thisYearEventStartDate,
						 thisYearEventStatus,
						 thisYearEventId,
						 thisYearEventLastUpdated,
							
																 groupItems,
																learningList);
			  
			 learning_adapter.notifyDataSetChanged();
	    	learningList.setAdapter(learning_adapter);	
	    	learningList.setOnChildClickListener(this);
		   
	    	button_show=(Button) rootView.findViewById(R.id.button_show);
	  
		    button_show.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
				  	eventId=new ArrayList<String>();
					 eventId=db.getAllForEventsId("Daily");
					 coverageId=new ArrayList<String>();
					 coverageId=db.getAllForCoverageId("Daily");
					 otherId=new ArrayList<String>();
					 otherId=db.getAllForOtherId("Daily");
					 learningId=new ArrayList<String>();
					 learningId=db.getAllForLearningId("Daily");
					 int number=eventId.size();
					 int number2=coverageId.size();
					 int number3=otherId.size();
					 int number4=learningId.size();
					 final int counter;
					if(eventId.size()<0){
						number=0;
					}else{
						number=eventId.size();
					}
					if(coverageId.size()<0){
						number2=0;
					}else {
						number2=coverageId.size();
					}
					if(otherId.size()<0){
						number3=0;
					}else{
						number3=otherId.size();
					}
					
					if(learningId.size()<0){
						number4=0;
					}else{
						number4=learningId.size();
					}
					counter=number+number2+number3+number4;	
					if(counter>0){
					Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
					startActivity(intent);
					}else if(counter==0){
						 Toast.makeText(getActivity(), "You have no targets to update!",
						         Toast.LENGTH_SHORT).show();
					}
					
				}
		    	
		    });											
			return rootView;
				   
			 }			
				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					selected_items=learning_adapter.getChild(groupPosition,childPosition);
					selected_id=Long.parseLong(selected_items[7]);
					//System.out.println(selected_items[0]+" "+selected_items[1]);
					String learning_category=selected_items[0];
					String learning_course=selected_items[1];
					String learing_topic=selected_items[2];
					String due_date=selected_items[4];
					String status=selected_items[6];
					String start_date=selected_items[5];
					String last_updated=selected_items[8];
					String period=selected_items[3];
					Intent intent=new Intent(getActivity(),LearningTargetsDetailActivity.class);
					intent.putExtra("learning_id",selected_id);
					intent.putExtra("learning_category",learning_category);
					intent.putExtra("learning_course",learning_course);
					intent.putExtra("learning_topic", learing_topic);
					intent.putExtra("due_date", due_date);
					intent.putExtra("start_date", start_date);
					intent.putExtra("status", status);
					intent.putExtra("last_updated", last_updated);
					intent.putExtra("period", period);
					startActivity(intent);
					return true;
				}

		
	 }
	 
	 public static class OtherActivity extends Fragment implements OnChildClickListener{

			private Context mContext;															
			private ArrayList<String> todayEventName;
			 private ArrayList<String> todayEventNumber;
			 private ArrayList<String> todayEventPeriod;
			 private ArrayList<String> todayEventDueDate;
			 private ArrayList<String> todayEventStartDate;
			 private ArrayList<String> todayEventAchieved;
			 private ArrayList<String> todayEventStatus;
			 private ArrayList<String> todayEventId;
			 private ArrayList<String> todayEventLastUpdated;
			 private ArrayList<String> todayEventNumberRemaining;
			 
			 private ArrayList<String> tomorrowEventName;
			 private ArrayList<String> tomorrowEventNumber;
			 private ArrayList<String> tomorrowEventPeriod;
			 private ArrayList<String> tomorrowEventDueDate;
			 private ArrayList<String> tomorrowEventStartDate;
			 private ArrayList<String> tomorrowEventAchieved;
			 private ArrayList<String> tomorrowEventStatus;
			 private ArrayList<String> tomorrowEventId;
			 
			 private ArrayList<String> thisWeekEventName;
			 private ArrayList<String> thisWeekEventNumber;
			 private ArrayList<String> thisWeekEventPeriod;
			 private ArrayList<String> thisWeekEventDueDate;
			 private ArrayList<String> thisWeekEventStartDate;
			 private ArrayList<String> thisWeekEventAchieved;
			 private ArrayList<String> thisWeekEventStatus;
			 private ArrayList<String> thisWeekEventId;
			 private ArrayList<String> thisWeekEventLastUpdated;
			 private ArrayList<String> thisWeekEventNumberRemaining;
			 
			 private ArrayList<String> thisMonthEventName;
			 private ArrayList<String> thisMonthEventNumber;
			 private ArrayList<String> thisMonthEventPeriod;
			 private ArrayList<String> thisMonthEventDueDate;
			 private ArrayList<String>  thisMonthEventStartDate;
			 private ArrayList<String>  thisMonthEventAchieved;
			 private ArrayList<String> thisMonthEventStatus;
			 private ArrayList<String> thisMonthEventId;
			 private ArrayList<String> thisMonthEventLastUpdated;
			 private ArrayList<String> thisMonthEventNumberRemaining;
			 
			 private ArrayList<String> thisQuarterEventName;
			 private ArrayList<String> thisQuarterEventNumber;
			 private ArrayList<String> thisQuarterEventPeriod;
			 private ArrayList<String> thisQuarterEventDueDate;
			 private ArrayList<String>  thisQuarterEventStartDate;
			 private ArrayList<String>  thisQuarterEventAchieved;
			 private ArrayList<String> thisQuarterEventStatus;
			 private ArrayList<String> thisQuarterEventId;
			 private ArrayList<String> thisQuarterEventLastUpdated;
			 private ArrayList<String> thisQuarterEventNumberRemaining;
			 
			 private ArrayList<String> midYearEventName;
			 private ArrayList<String> midYearEventNumber;
			 private ArrayList<String> midYearEventPeriod;
			 private ArrayList<String> midYearEventDueDate;
			 private ArrayList<String>  midYearEventStartDate;
			 private ArrayList<String>  midYearEventAchieved;
			 private ArrayList<String> midYearEventStatus;
			 private ArrayList<String> midYearEventId;
			 private ArrayList<String> midYearEventLastUpdated;
			 private ArrayList<String> midYearEventNumberRemaining;
			 
			 private ArrayList<String> thisYearEventName;
			 private ArrayList<String> thisYearEventNumber;
			 private ArrayList<String> thisYearEventPeriod;
			 private ArrayList<String> thisYearEventDueDate;
			 private ArrayList<String>  thisYearEventStartDate;
			 private ArrayList<String> thisYearEventAchieved;
			 private ArrayList<String> thisYearEventStatus;
			 private ArrayList<String> thisYearEventId;
			 private ArrayList<String> thisYearEventLastUpdated;
			 private ArrayList<String> thisYearEventNumberRemaining;
			 
			 private HashMap<String,String> todayEventTargets;//Today
			 private HashMap<String,String> tomorrowEventTargets;
			 private HashMap<String,String> thisWeekEventTargets;
			 private HashMap<String,String> thisMonthEventTargets;
			 private HashMap<String,String> thisQuarterEventTargets;
			 private HashMap<String,String> midYearEventTargets;
			 private HashMap<String,String> thisYearEventTargets;
			 private String[] groupItems;
			private DbHelper db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private ExpandableListView listView_other;
			private TextView textStatus;
			private EventBaseAdapter other_adapter;
			int selected_position;
			private long selected_id;
			private Button button_show;
			private ArrayList<String> eventId;
			private ArrayList<String> coverageId;
			private ArrayList<String> otherId;
			private ArrayList<String> learningId;
			static String due_date ;
			private static TextView dueDateValue;
			
			static String start_date ;
			private static TextView startDateValue;
			static long due_date_to_compare;
			 public OtherActivity(){

   }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				 rootView=inflater.inflate(R.layout.activity_other,null,false);
			    mContext=getActivity().getApplicationContext();
			    db=new DbHelper(getActivity());
			   
			    todayEventName=new ArrayList<String>();
			    todayEventName=db.getAllForOtherName("Daily");
			   // todayEventName.add(todayEventTargets.get("event_name"));
			   todayEventNumber=new ArrayList<String>();
			    todayEventNumber=db.getAllForOtherNumber("Daily");
			    
			   // todayEventNumber.add(todayEventTargets.get("event_number"));
			    todayEventAchieved=new ArrayList<String>();
			    //todayEventPeriod.add(todayEventTargets.get("event_period"));
			    todayEventAchieved=db.getAllForOtherNumberAchieved("Daily");
			    
			    todayEventStartDate=new ArrayList<String>();
			    todayEventStartDate=db.getAllForOtherStartDate("Daily");
			    
			   // todayEventNumber.add(todayEventTargets.get("event_number"));
			    todayEventPeriod=new ArrayList<String>();
			    //todayEventPeriod.add(todayEventTargets.get("event_period"));
			    todayEventPeriod=db.getAllForOtherPeriod("Daily");
			    
			    todayEventDueDate=new ArrayList<String>();
			    todayEventDueDate=db.getAllForOtherDueDate("Daily");
			   // todayEventDueDate.add(todayEventTargets.get("due_date"));
			    
			    todayEventStatus=new ArrayList<String>();
			   // todayEventStatus.add(todayEventTargets.get("sync_status"));
			    todayEventStatus=db.getAllForOtherSyncStatus("Daily");
			    
			    todayEventId=new ArrayList<String>();
			  //  todayEventId.add(todayEventTargets.get("event_id"));
			    todayEventId=db.getAllForOtherId("Daily");
			    todayEventLastUpdated=new ArrayList<String>();
				  //  todayEventId.add(todayEventTargets.get("event_id"));
				    todayEventLastUpdated=db.getAllForOtherLastUpdated("Daily");
			    
			    
			    //tomorrowEventName=new ArrayList<String>();
			    //tomorrowEventName=db.getAllForOtherName("Weekly");
			    //tomorrowEventName.add(tomorrowEventTargets.get("event_name"));
			    
			    /*
			    tomorrowEventNumber=new ArrayList<String>();
			    tomorrowEventNumber=db.getAllForOthersNumber("Weekly");
			    //tomorrowEventNumber.add(tomorrowEventTargets.get("event_number"));
			    tomorrowEventPeriod=new ArrayList<String>();
			    tomorrowEventPeriod=db.getAllForOthersPeriod("Weekly");
			    //tomorrowEventPeriod.add(tomorrowEventTargets.get("event_period"));
			    tomorrowEventDueDate=new ArrayList<String>();
			    tomorrowEventDueDate=db.getAllForOthersDueDate("Weekly");
			    //tomorrowEventDueDate.add(tomorrowEventTargets.get("due_date"));
			    tomorrowEventStatus=new ArrayList<String>();
			    tomorrowEventStatus=db.getAllForOthersSyncStatus("Weekly");
			    //tomorrowEventStatus.add(tomorrowEventTargets.get("sync_status"));
			    tomorrowEventId=new ArrayList<String>();
			    tomorrowEventId=db.getAllForOthersId("Weekly");
			    //tomorrowEventId.add(tomorrowEventTargets.get("event_id"));
			    */
			    
			    thisWeekEventName=new ArrayList<String>();
			    thisWeekEventName=db.getAllForOtherName("Weekly");
			    //thisWeekEventName.add(thisWeekEventTargets.get("event_name"));
			    thisWeekEventNumber=new ArrayList<String>();
			    thisWeekEventNumber=db.getAllForOtherNumber("Weekly");
			   // thisWeekEventNumber.add(thisWeekEventTargets.get("event_number"));
			    thisWeekEventPeriod=new ArrayList<String>();
			    thisWeekEventPeriod=db.getAllForOtherPeriod("Weekly");
			    
			    thisWeekEventAchieved=new ArrayList<String>();
			    thisWeekEventAchieved=db.getAllForOtherNumberAchieved("Weekly");
			   // thisWeekEventNumber.add(thisWeekEventTargets.get("event_number"));
			    thisWeekEventStartDate=new ArrayList<String>();
			    thisWeekEventStartDate=db.getAllForOtherStartDate("Weekly");
			   // thisWeekEventPeriod.add(thisWeekEventTargets.get("event_period"));
			    thisWeekEventDueDate=new ArrayList<String>();
			    thisWeekEventDueDate=db.getAllForOtherDueDate("Weekly");
			    //thisWeekEventDueDate.add(thisWeekEventTargets.get("due_date"));
			    thisWeekEventStatus=new ArrayList<String>();
			    thisWeekEventStatus=db.getAllForOtherSyncStatus("Weekly");
			    //thisWeekEventStatus.add(thisWeekEventTargets.get("sync_status"));
			    thisWeekEventId=new ArrayList<String>();
			    thisWeekEventId=db.getAllForOtherId("Weekly");
			    //thisWeekEventId.add(thisWeekEventTargets.get("event_id"));
			    thisWeekEventLastUpdated=new ArrayList<String>();
			    thisWeekEventLastUpdated=db.getAllForOtherLastUpdated("Weekly");
			    //thisWeekEventId.add(thisWeekEventTargets.get("event_id"));
			    
			    thisMonthEventName=new ArrayList<String>();
			    thisMonthEventName=db.getAllForOtherName("Monthly");
			   //thisMonthEventName.add(thisMonthEventTargets.get("event_name"));
			    thisMonthEventNumber=new ArrayList<String>();
			    thisMonthEventNumber=db.getAllForOtherNumber("Monthly");
			    //thisMonthEventNumber.add(thisMonthEventTargets.get("event_number"));
			    thisMonthEventPeriod=new ArrayList<String>();
			    thisMonthEventPeriod=db.getAllForOtherPeriod("Monthly");
			    
			    thisMonthEventAchieved=new ArrayList<String>();
			    thisMonthEventAchieved=db.getAllForOtherNumberAchieved("Monthly");
			    //thisMonthEventNumber.add(thisMonthEventTargets.get("event_number"));
			    thisMonthEventStartDate=new ArrayList<String>();
			    thisMonthEventStartDate=db.getAllForOtherStartDate("Monthly");
			    //thisMonthEventPeriod.add(thisMonthEventTargets.get("event_period"));
			    thisMonthEventDueDate=new ArrayList<String>();
			    thisMonthEventDueDate=db.getAllForOtherDueDate("Monthly");
			    //thisMonthEventDueDate.add(thisMonthEventTargets.get("due_date"));
			    thisMonthEventStatus=new ArrayList<String>();
			    thisMonthEventStatus=db.getAllForOtherSyncStatus("Monthly");
			    //thisMonthEventStatus.add(thisMonthEventTargets.get("sync_status"));
			    thisMonthEventId=new ArrayList<String>();
			    thisMonthEventId=db.getAllForOtherId("Monthly");
			    //thisMonthEventId.add(thisMonthEventTargets.get("event_id"));
			    
			    thisMonthEventLastUpdated=new ArrayList<String>();
			    thisMonthEventLastUpdated=db.getAllForOtherLastUpdated("Monthly");
			    //thisMonthEventId.add(thisMonthEventTargets.get("event_id"));
			    
			    midYearEventName=new ArrayList<String>();
			   // midYearEventName.add(midYearEventTargets.get("event_name"));
			    midYearEventName=db.getAllForOtherName("Mid-year");
			    midYearEventNumber=new ArrayList<String>();
			    midYearEventNumber=db.getAllForOtherNumber("Mid-year");
			   // midYearEventNumber.add(midYearEventTargets.get("event_number"));
			    midYearEventPeriod=new ArrayList<String>();
			    midYearEventPeriod=db.getAllForOtherPeriod("Mid-year");
			    
			    midYearEventAchieved=new ArrayList<String>();
			    midYearEventAchieved=db.getAllForOtherNumberAchieved("Mid-year");
			   // midYearEventNumber.add(midYearEventTargets.get("event_number"));
			    midYearEventStartDate=new ArrayList<String>();
			    midYearEventStartDate=db.getAllForOtherStartDate("Mid-year");
			    //midYearEventPeriod.add(midYearEventTargets.get("event_period"));
			    midYearEventDueDate=new ArrayList<String>();
			    midYearEventDueDate=db.getAllForOtherDueDate("Mid-year");
			   // midYearEventDueDate.add(midYearEventTargets.get("due_date"));
			    midYearEventStatus=new ArrayList<String>();
			    midYearEventStatus=db.getAllForOtherSyncStatus("Mid-year");
			    //midYearEventStatus.add(midYearEventTargets.get("sync_status"));
			    midYearEventId=new ArrayList<String>();
			    midYearEventId=db.getAllForOtherId("Mid-year");
			   // midYearEventId.add(midYearEventTargets.get("event_id"));
			    midYearEventLastUpdated=new ArrayList<String>();
			    midYearEventLastUpdated=db.getAllForOtherLastUpdated("Mid-year");
			   // midYearEventId.add(midYearEventTargets.get("event_id"));
			    
			    thisQuarterEventName=new ArrayList<String>();
			    thisQuarterEventName=db.getAllForOtherName("Quarterly");
			    //thisQuarterEventName.add(thisQuarterEventTargets.get("event_name"));
			    thisQuarterEventNumber=new ArrayList<String>();
			    thisQuarterEventNumber=db.getAllForOtherNumber("Quarterly");
			    //thisQuarterEventNumber.add(thisQuarterEventTargets.get("event_number"));
			    thisQuarterEventPeriod=new ArrayList<String>();
			    thisQuarterEventPeriod=db.getAllForOtherPeriod("Quarterly");
			   // thisQuarterEventPeriod.add(thisQuarterEventTargets.get("event_period"));
			    thisQuarterEventDueDate=new ArrayList<String>();
			    thisQuarterEventDueDate=db.getAllForOtherDueDate("Quarterly");
			   // thisQuarterEventDueDate.add(thisQuarterEventTargets.get("due_date"));
			    thisQuarterEventStatus=new ArrayList<String>();
			    thisQuarterEventStatus=db.getAllForOtherDueDate("Quarterly");
			    //thisQuarterEventStatus.add(thisQuarterEventTargets.get("sync_status"));
			    thisQuarterEventId=new ArrayList<String>();
			    thisQuarterEventId=db.getAllForOtherId("Quarterly");
			    //thisQuarterEventId.add(thisQuarterEventTargets.get("event_id"));
			    
			    thisQuarterEventAchieved=new ArrayList<String>();
			    thisQuarterEventAchieved=db.getAllForOtherNumberAchieved("Quarterly");
			    //thisQuarterEventId.add(thisQuarterEventTargets.get("event_id"));
			    
			    thisQuarterEventStartDate=new ArrayList<String>();
			    thisQuarterEventStartDate=db.getAllForOtherStartDate("Quarterly");
			    //thisQuarterEventId.add(thisQuarterEventTargets.get("event_id"));
			    
			    thisQuarterEventLastUpdated=new ArrayList<String>();
			    thisQuarterEventLastUpdated=db.getAllForOtherLastUpdated("Quarterly");
			    //thisQuarterEventId.add(thisQuarterEventTargets.get("event_id"));
			    
			    thisYearEventName=new ArrayList<String>();
			    thisYearEventName=db.getAllForOtherName("Annually");
			    //thisYearEventName.add(thisYearEventTargets.get("event_name"));
			    thisYearEventNumber=new ArrayList<String>();
			    thisYearEventNumber=db.getAllForOtherNumber("Annually");
			    //thisYearEventNumber.add(thisYearEventTargets.get("event_number"));
			    thisYearEventPeriod=new ArrayList<String>();
			    thisYearEventPeriod=db.getAllForOtherPeriod("Annually");
			    
			    thisYearEventAchieved=new ArrayList<String>();
			    thisYearEventAchieved=db.getAllForOtherNumberAchieved("Annually");
			    //thisYearEventNumber.add(thisYearEventTargets.get("event_number"));
			    thisYearEventStartDate=new ArrayList<String>();
			    thisYearEventStartDate=db.getAllForOtherStartDate("Annually");
			    //thisYearEventPeriod.add(thisYearEventTargets.get("event_period"));
			    thisYearEventDueDate=new ArrayList<String>();
			    thisYearEventDueDate=db.getAllForOtherDueDate("Annually");
			    //thisYearEventDueDate.add(thisYearEventTargets.get("due_date"));
			    thisYearEventStatus=new ArrayList<String>();
			    thisYearEventStatus=db.getAllForOtherSyncStatus("Annually");
			    //thisYearEventStatus.add(thisYearEventTargets.get("sync_status"));
			    thisYearEventId=new ArrayList<String>();
			    thisYearEventId=db.getAllForOtherId("Annually");
			    //thisYearEventId.add(thisYearEventTargets.get("event_id"));
			    
			    thisYearEventLastUpdated=new ArrayList<String>();
			    thisYearEventLastUpdated=db.getAllForOtherLastUpdated("Annually");
			    //thisYearEventId.add(thisYearEventTargets.get("event_id"));
			    
			    groupItems=new String[]{"To update today","To update this week","To update this month","To update this quarter","Half-year update","To update this year"};
			    other_adapter=new EventBaseAdapter(mContext,todayEventName ,
						todayEventNumber,
						todayEventPeriod,
						todayEventDueDate,
						todayEventAchieved,
						todayEventStartDate,
						todayEventStatus,
						todayEventId,
						todayEventLastUpdated,
						//todayEventNumberRemaining,

					/*	tomorrowEventName,
						tomorrowEventNumber,
						tomorrowEventPeriod,
						tomorrowEventDueDate,
						tomorrowEventAchieved,
						tomorrowEventStartDate,
						tomorrowEventStatus,
						tomorrowEventId,
						*/

						thisWeekEventName,
						thisWeekEventNumber,
						thisWeekEventPeriod,
						thisWeekEventDueDate,
						thisWeekEventAchieved,
						thisWeekEventStartDate,
						thisWeekEventStatus,
						thisWeekEventId,
						thisWeekEventLastUpdated,
						//thisWeekEventNumberRemaining,

						thisMonthEventName,
						thisMonthEventNumber,
						thisMonthEventPeriod,
						thisMonthEventDueDate,
						thisMonthEventAchieved,
						thisMonthEventStartDate,
						thisMonthEventStatus,
						thisMonthEventId,
						thisMonthEventLastUpdated,
						//thisMonthEventNumberRemaining,

						thisQuarterEventName,
						thisQuarterEventNumber,
						thisQuarterEventPeriod,
						thisQuarterEventDueDate,
						thisQuarterEventAchieved,
						thisQuarterEventStartDate,
						thisQuarterEventStatus,
						thisQuarterEventId,
						thisQuarterEventLastUpdated,
						//thisQuarterEventNumberRemaining,
						
						midYearEventName,
						midYearEventNumber,
						midYearEventPeriod,
						midYearEventDueDate, 
						midYearEventAchieved,
						midYearEventStartDate,
						midYearEventStatus,
						midYearEventId,
						midYearEventLastUpdated,
						//midYearEventNumberRemaining,
						
						thisYearEventName,
						thisYearEventNumber,
						thisYearEventPeriod,
						thisYearEventDueDate,
						thisYearEventAchieved,
						thisYearEventStartDate,
						thisYearEventStatus,
						thisYearEventId,
						thisYearEventLastUpdated,
						//thisYearEventNumberRemaining,
						 groupItems,
						listView_other);
			  
			    listView_other=(ExpandableListView) rootView.findViewById(R.id.expandableListView_other);
			    listView_other.setAdapter(other_adapter);
			    listView_other.setOnChildClickListener(this);
			   button_show=(Button) rootView.findViewById(R.id.button_show);
			   
			    button_show.setOnClickListener(new OnClickListener(){
			    	
					@Override
					public void onClick(View v) {
						eventId=new ArrayList<String>();
						 eventId=db.getAllForEventsId("Daily");
						 coverageId=new ArrayList<String>();
						 coverageId=db.getAllForCoverageId("Daily");
						 otherId=new ArrayList<String>();
						 otherId=db.getAllForOtherId("Daily");
						 learningId=new ArrayList<String>();
						 learningId=db.getAllForLearningId("Daily");
						 int number=eventId.size();
						 int number2=coverageId.size();
						 int number3=otherId.size();
						 int number4=learningId.size();
						 final int counter;
						if(eventId.size()<0){
							number=0;
						}else{
							number=eventId.size();
						}
						if(coverageId.size()<0){
							number2=0;
						}else {
							number2=coverageId.size();
						}
						if(otherId.size()<0){
							number3=0;
						}else{
							number3=otherId.size();
						}
						
						if(learningId.size()<0){
							number4=0;
						}else{
							number4=learningId.size();
						}
						counter=number+number2+number3+number4;	
						if(counter>0){
						Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
						startActivity(intent);
						}else if(counter==0){
							 Toast.makeText(getActivity(), "You have no targets to update!",
							         Toast.LENGTH_SHORT).show();
						}
						
					}
			    	
			    });
			   
			return rootView;
				   
			}
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				String[] selected_items=other_adapter.getChild(groupPosition, childPosition);
				selected_id=Long.parseLong(selected_items[7]);
				//System.out.println(selected_items[0]+" "+selected_items[1]);
				String other_name=selected_items[0];
				String other_number=selected_items[1];
				String other_period=selected_items[2];
				String due_date=selected_items[3];
				String status=selected_items[6];
				String startDate=selected_items[5];
				String achieved=selected_items[4];
				String last_updated=selected_items[8];
				ArrayList<String> number_achieved_list=db.getForUpdateOtherNumberAchieved(selected_id, other_period);
				Intent intent=new Intent(getActivity(),OtherTargetsDetailActivity.class);
				intent.putExtra("other_id",selected_id);
				intent.putExtra("other_name",other_name);
				intent.putExtra("other_number",other_number);
				intent.putExtra("other_period", other_period);
				intent.putExtra("due_date", due_date);
				intent.putExtra("start_date", startDate);
				intent.putExtra("status", status);
				intent.putExtra("last_updated", last_updated);
				intent.putExtra("achieved", number_achieved_list.get(0));
				startActivity(intent);
				return true;
			}
				
			}
	 
	 @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if ((keyCode == KeyEvent.KEYCODE_BACK)) {
				NewEventPlannerActivity.this.finish();
				
			} 
			
		    return true; 
		}
	  public void saveToLog(Long starttime) 
		{
		  Long endtime = System.currentTimeMillis();  
		  dbh.insertCCHLog(EVENT_PLANNER_ID, "Target Setting", starttime.toString(), endtime.toString());	
		}
	 public void onDestroy(){
		 super.onDestroy();
		 Long starttime=System.currentTimeMillis();  
		 saveToLog(starttime); 
	 }


	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Log.d(TAG, key + " changed");
		if(key.equalsIgnoreCase(getString(R.string.prefs_server))){
			Editor editor = sharedPreferences.edit();
			if(!sharedPreferences.getString(getString(R.string.prefs_server), "").endsWith("/")){
				String newServer = sharedPreferences.getString(getString(R.string.prefs_server), "").trim()+"/";
				editor.putString(getString(R.string.prefs_server), newServer);
		    	editor.commit();
			}
		}
		if(key.equalsIgnoreCase(getString(R.string.prefs_points)) || key.equalsIgnoreCase(getString(R.string.prefs_badges))){
			supportInvalidateOptionsMenu();
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		  mViewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
