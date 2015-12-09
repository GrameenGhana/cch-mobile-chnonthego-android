package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.service.TrackerService;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.model.MyCalendarEvents;
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
	private long todayOtherId;
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
        mViewPager.setOffscreenPageLimit(2);
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

         private ArrayList<MyCalendarEvents> events;
		private CalendarEvents c;
		private ArrayList<RoutineActivity> todos;
		public SectionsPagerAdapter(FragmentManager fm) {
                 super(fm);
         }

         @Override
         public Fragment getItem(int position) {
                 Fragment fragment = null;
                 if(position==0 ){
                       // fragment= new EventUpdateActivity();
                	 fragment= new OtherTargetActivity();  
                 }else if(position==1){
                	 fragment= new RoutineActivityDetails(mViewPager);   
                 }
                 return fragment;
         }

         @Override
         public int getCount() {
                 return 2;
         }

         @Override
         public CharSequence getPageTitle(int position) {
                 Locale l = Locale.getDefault();
             	events=new ArrayList<MyCalendarEvents>();
                c= new CalendarEvents(mContext);
            	//events=c.readEventsToUpdate(mContext, true);
                db=new DbHelper(NewEventPlannerActivity.this);
    		    
    		
    		 todayOtherId=db.getCount("Daily",MobileLearning.CCH_TARGET_TYPE_OTHER);
    		 thisWeekOtherId=db.getCount("Weekly",MobileLearning.CCH_TARGET_TYPE_OTHER);
    		 thisMonthOtherId=db.getCount("Monthly",MobileLearning.CCH_TARGET_TYPE_OTHER);
    		 midYearOtherId=db.getCount("Mid-year",MobileLearning.CCH_TARGET_TYPE_OTHER);
    		 thisQuarterOtherId=db.getCount("Quarterly",MobileLearning.CCH_TARGET_TYPE_OTHER);
    		 thisYearOtherId=db.getCount("Annually",MobileLearning.CCH_TARGET_TYPE_OTHER);
    		
    		 /* Get StayingWell todos */
    		todos = db.getSWRoutineActivities();
	    	//counter=events.size();
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
			if(todos!=null){
				counter5=todos.size();
			}else{
				counter5=0;
			}
                 switch (position) {
                         case 0:
                                 //return "EVENTS"+" ("+String.valueOf(counter)+")";
                                 return "OTHER"+" ("+String.valueOf(counter4)+")";
                         case 1:
                        	 return "ROUTINES (" +  String.valueOf(counter5) + ")";
                        		
                       
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
