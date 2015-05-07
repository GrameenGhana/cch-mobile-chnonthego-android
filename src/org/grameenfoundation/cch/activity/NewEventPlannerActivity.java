package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.service.TrackerService;
import org.grameenfoundation.cch.model.CoverageTargetActivity;
import org.grameenfoundation.cch.model.EventTargetActivity;
import org.grameenfoundation.cch.model.LearningTargetActivity;
import org.grameenfoundation.cch.model.OtherTargetActivity;
import org.grameenfoundation.cch.model.RoutineActivity;
import org.grameenfoundation.cch.model.RoutineActivityDetails;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;


public class NewEventPlannerActivity extends SherlockFragmentActivity implements ActionBar.TabListener, OnSharedPreferenceChangeListener{
	 private DbHelper dbh;
	 private static Context mContext;

	 
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
	 int counter5;
	private JSONObject data;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_new_event_planner);
	    
	    mContext = NewEventPlannerActivity.this;
        dbh = new DbHelper(mContext);
      
        final ActionBar actionBar =getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle("Planner");
        actionBar.setSubtitle("Target Setting");
        actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager
        .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                }
        });
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                actionBar.addTab(actionBar.newTab()
                                .setText(mSectionsPagerAdapter.getPageTitle(i))
                                .setTabListener(this));
        }
       
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
		
		Intent service = new Intent(this, TrackerService.class);
		Bundle tb = new Bundle();
		tb.putBoolean("backgroundData", true);
		service.putExtras(tb);
		this.startService(service);
		try 
	    {
			if (!(getIntent().getStringExtra("FRAGMENT_IDX")).isEmpty()) {	
				int page = Integer.parseInt(getIntent().getStringExtra("FRAGMENT_IDX"));
				mViewPager.setCurrentItem(page, true);	
			}				
		} catch (NullPointerException e) { Log.e(TAG,"Trying to switch panes failed :("); }	
        
}
	 public class SectionsPagerAdapter extends FragmentPagerAdapter {

         public SectionsPagerAdapter(FragmentManager fm) {
                 super(fm);
         }

         @Override
         public Fragment getItem(int position) {
                 Fragment fragment = null;
                 if(position==0 ){
                        fragment= new EventTargetActivity();
                 }else if(position==1){
                	 fragment= new CoverageTargetActivity();
                 }else if(position==2){
                	 fragment= new LearningTargetActivity();
                 }else if(position==3){
                	 fragment= new RoutineActivityDetails(mViewPager);           
                 }else if(position==4){
                	 fragment= new OtherTargetActivity();
                 }
                 return fragment;
         }

         @Override
         public int getCount() {
                 return 5;
         }

         @Override
         public CharSequence getPageTitle(int position) {
                 Locale l = Locale.getDefault();
                 
                 db=new DbHelper(NewEventPlannerActivity.this);
                 
     		    todayEventId=db.getCount("Daily",MobileLearning.CCH_TARGET_TYPE_EVENT);
     		    thisMonthEventId=db.getCount("Monthly",MobileLearning.CCH_TARGET_TYPE_EVENT);
     		    thisWeekEventId=db.getCount("Weekly",MobileLearning.CCH_TARGET_TYPE_EVENT);
     		    midYearEventId=db.getCount("Mid-year",MobileLearning.CCH_TARGET_TYPE_EVENT);
     		    thisQuarterEventId=db.getCount("Quarterly",MobileLearning.CCH_TARGET_TYPE_EVENT);
     		    thisYearEventId=db.getCount("Annually",MobileLearning.CCH_TARGET_TYPE_EVENT);
     		     int event_number1=(int)todayEventId;
     			 int event_number2=(int)thisMonthEventId;
     			 int event_number3=(int)thisWeekEventId;
     			 int event_number4=(int)midYearEventId;
     			 int event_number5=(int)thisQuarterEventId;
     			 int event_number6=(int)thisYearEventId;
     	
     			counter=event_number1+event_number2+event_number3+event_number4+event_number5+event_number6;
     			
     			todayCoverageId=db.getCount("Daily",MobileLearning.CCH_TARGET_TYPE_COVERAGE);
     			thisWeekCoverageId=db.getCount("Weekly",MobileLearning.CCH_TARGET_TYPE_COVERAGE);
     			thisMonthCoverageId=db.getCount("Monthly",MobileLearning.CCH_TARGET_TYPE_COVERAGE);
     			midYearCoverageId=db.getCount("Mid-year",MobileLearning.CCH_TARGET_TYPE_COVERAGE);
     			thisQuarterCoverageId=db.getCount("Quarterly",MobileLearning.CCH_TARGET_TYPE_COVERAGE);
     			thisYearCoverageId=db.getCount("Annually",MobileLearning.CCH_TARGET_TYPE_COVERAGE);
     			
     			     int coverage_number1=(int)todayCoverageId;
     				 int coverage_number2=(int)thisWeekCoverageId;
     				 int coverage_number3=(int)thisMonthCoverageId;
     				 int coverage_number4=(int)midYearCoverageId;
     				 int coverage_number5=(int)thisQuarterCoverageId;
     				 int coverage_number6=(int)thisYearCoverageId;
     				
     				counter2=coverage_number1+coverage_number2+coverage_number3+coverage_number4+coverage_number5+coverage_number6;
     			
     			todayLearningId=db.getCount("Daily",MobileLearning.CCH_TARGET_TYPE_LEARNING);
     			thisWeekLearningId=db.getCount("Weekly",MobileLearning.CCH_TARGET_TYPE_LEARNING);
     			thisMonthLearningId=db.getCount("Monthly",MobileLearning.CCH_TARGET_TYPE_LEARNING);
     			midYearLearningId=db.getCount("Mid-year",MobileLearning.CCH_TARGET_TYPE_LEARNING);
     			thisQuarterLearningId=db.getCount("Quarterly",MobileLearning.CCH_TARGET_TYPE_LEARNING);
     			thisYearLearningId=db.getCount("Annually",MobileLearning.CCH_TARGET_TYPE_LEARNING);
     			
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
     			 todayOtherId=db.getCount("Daily",MobileLearning.CCH_TARGET_TYPE_OTHER);
     			 thisWeekOtherId=db.getCount("Weekly",MobileLearning.CCH_TARGET_TYPE_OTHER);
     			 thisMonthOtherId=db.getCount("Monthly",MobileLearning.CCH_TARGET_TYPE_OTHER);
     			 midYearOtherId=db.getCount("Mid-year",MobileLearning.CCH_TARGET_TYPE_OTHER);
     			 thisQuarterOtherId=db.getCount("Quarterly",MobileLearning.CCH_TARGET_TYPE_OTHER);
     			 thisYearOtherId=db.getCount("Annually",MobileLearning.CCH_TARGET_TYPE_OTHER);
     			 
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
     			
     			
     			 /* Get StayingWell todos */
     			ArrayList<RoutineActivity> todos = dbh.getSWRoutineActivities();
     			if(todos!=null){
     				counter5=todos.size();
     			}else{
     				counter5=0;
     			}
     			
                 switch (position) {
                         case 0:
                                 return "EVENTS"+" ("+String.valueOf(counter)+")";
                         case 1:
                                 return "COVERAGE"+" ("+String.valueOf(counter2)+")";
                         case 2: 
                    	 		return "LEARNING"+" ("+String.valueOf(counter3)+")";
                         case 3: 
                        	 	return "ROUTINES (" +  String.valueOf(counter5) + ")";
                         case 4:
                        		return "OTHER"+" ("+String.valueOf(counter4)+")";
                 }
                 return null;
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
		  data=new JSONObject();
		    try {
		    	data.put("page", "Target Setting");
		    	data.put("ver", db.getVersionNumber(NewEventPlannerActivity.this));
		    	data.put("battery", db.getBatteryStatus(NewEventPlannerActivity.this));
		    	data.put("device", db.getDeviceName());
				data.put("imei", db.getDeviceImei(NewEventPlannerActivity.this));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		  dbh.insertCCHLog(EVENT_PLANNER_ID, data.toString(), starttime.toString(), endtime.toString());	
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
