package org.grameenfoundation.cch.activity;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Locale;

	import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.service.TrackerService;
import org.grameenfoundation.cch.model.CoverageTargetAchievementActivity;
import org.grameenfoundation.cch.model.CoverageTargetActivity;
import org.grameenfoundation.cch.model.EventTargetAchievementActivity;
import org.grameenfoundation.cch.model.EventTargetActivity;
import org.grameenfoundation.cch.model.LearningTargetAchievementActivity;
import org.grameenfoundation.cch.model.LearningTargetActivity;
import org.grameenfoundation.cch.model.OtherTargetAchievementActivity;
import org.grameenfoundation.cch.model.OtherTargetActivity;
import org.grameenfoundation.cch.model.RoutineActivity;
import org.grameenfoundation.cch.model.RoutineActivityDetails;

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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;

	import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;


	public class TargetAchievementsActivity extends SherlockFragmentActivity implements ActionBar.TabListener, OnSharedPreferenceChangeListener{
		 private DbHelper dbh;
		 private static Context mContext;

		 
		 SectionsPagerAdapter mSectionsPagerAdapter;
		public static String current_month;
		
		private static final String EVENT_PLANNER_ID = "Event Planner";
		public static final String TAG = TargetAchievementsActivity.class.getSimpleName();
		public static String month_passed = null;
	    /**
	     * The {@link ViewPager} that will host the section contents.
	     */
	    ViewPager mViewPager;
		private SharedPreferences prefs;
		private DbHelper db;
		@Override
		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_new_event_planner);
		    
		    mContext = TargetAchievementsActivity.this;
		    
		   // final PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_header);
	       // pagerTabStrip.setDrawFullUnderline(true);
	       // pagerTabStrip.setTabIndicatorColor(Color.rgb(83,171,32));
	        dbh = new DbHelper(mContext);
	      
	        final ActionBar actionBar =getSupportActionBar();
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        actionBar.setTitle("Achievement Center");
	        actionBar.setSubtitle("Achievement Details");
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
	                        fragment= new EventTargetAchievementActivity();
	                 }else if(position==1){
	                	 fragment= new CoverageTargetAchievementActivity();
	                 }else if(position==2){
	                	 fragment= new LearningTargetAchievementActivity();
	                 }else if(position==3){
	                	 fragment= new OtherTargetAchievementActivity();           
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
	                 db=new DbHelper(TargetAchievementsActivity.this);
	     			ArrayList<RoutineActivity> todos = dbh.getSWRoutineActivities();
	     			
	     			
	                 switch (position) {
	                         case 0:
	                                 return "EVENTS";
	                         case 1:
	                                 return "COVERAGE";
	                         case 2: 
	                    	 		return "LEARNING";
	                         case 3: 
	                        	 	return "OTHER";
	                 }
	                 return null;
	         }
	 }

		
		 
		
		
		 
		 
		 @Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if ((keyCode == KeyEvent.KEYCODE_BACK)) {
					TargetAchievementsActivity.this.finish();
					
				} 
				
			    return true; 
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
