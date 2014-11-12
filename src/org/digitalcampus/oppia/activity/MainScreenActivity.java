package org.digitalcampus.oppia.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.service.TrackerService;
import org.digitalcampus.oppia.service.UpdateMonthlyTargetService;
import org.digitalcampus.oppia.service.UpdateTargetsService;
import org.digitalcampus.oppia.service.UpdateTargetsWeeklyService;
import org.grameenfoundation.adapters.EventsDetailPagerAdapter;
import org.grameenfoundation.adapters.MainScreenBaseAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.activity.HomeActivity;
import org.grameenfoundation.cch.activity.StayingWellActivity;
import org.grameenfoundation.database.CHNDatabaseHandler;
import org.grameenfoundation.poc.PointOfCareActivity;
import org.grameenfoundation.cch.utils.TypefaceUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainScreenActivity extends FragmentActivity implements OnItemClickListener, OnSharedPreferenceChangeListener {

	private ListView main_menu_listview;
	private static Context mContext;
	private static TextView status;
	public static final String TAG = HomeActivity.class.getSimpleName();
	Time time_now;
	Time compared_time;
	Time week;
	Time end_of_month;
	SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
	private static DbHelper dbh;
	private SharedPreferences prefs;
	
	// MODULE IDs
		private static final String EVENT_PLANNER_ID      = "Event Planner";
		private static final String STAYING_WELL_ID       = "Staying Well";
		private static final String POINT_OF_CARE_ID      = "Point of Care";
		private static final String LEARNING_CENTER_ID    = "Learning Center";
		private static final String ACHIEVEMENT_CENTER_ID = "Achievement Center";
  

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main_screen);
	    mContext=MainScreenActivity.this;
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Welcome");
	    getActionBar().setSubtitle("Home Page");
	    TypefaceUtil.overrideFont(mContext, "SERIF", "fonts/Roboto-Thin.ttf");
	    main_menu_listview=(ListView) findViewById(R.id.listView_mainScreenMenu);
	    time_now=new Time();
		compared_time=new Time();
		week=new Time();
		end_of_month=new Time();
		time_now.setToNow();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy kk:mm");
		compared_time.set(time_now.second, 0, 17, time_now.monthDay, time_now.month, time_now.year);
		week.set(time_now.second, 0, 17, 5, time_now.month, time_now.year);
		end_of_month.set(time_now.second, 0, 17, 31, time_now.month, time_now.year);
		String today= String.valueOf(time_now.hour) +":"+String.valueOf(time_now.minute)+String.valueOf(time_now.second)
					+","+String.valueOf(time_now.monthDay)+"/"+String.valueOf(time_now.month)+"/"+String.valueOf(time_now.year);
		String compared_today= String.valueOf(compared_time.hour) +":"+String.valueOf(compared_time.minute)+String.valueOf(compared_time.second)
				+","+String.valueOf(compared_time.monthDay)+"/"+String.valueOf(compared_time.month)+"/"+String.valueOf(compared_time.year);
		
		
		String compared_week= String.valueOf(week.hour) +":"+String.valueOf(week.minute)+String.valueOf(week.second)
			+","+String.valueOf(week.monthDay)+"/"+String.valueOf(week.month)+"/"+String.valueOf(week.year);
		
		String compared_endofmonth= String.valueOf(end_of_month.hour) +":"+String.valueOf(end_of_month.minute)+String.valueOf(end_of_month.second)
				+","+String.valueOf(end_of_month.monthDay)+"/"+String.valueOf(end_of_month.month)+"/"+String.valueOf(end_of_month.year);
		System.out.println(today);
		System.out.println(compared_today);
		System.out.println(compared_week);
		System.out.println(compared_endofmonth);
	    
	    String[] categories={"Planner",
	    		"Point of Care",
	    		"Learning Center",
	    		"Achievements",
	    		"Staying Well"};
	    
	    int[] images={R.drawable.ic_calendar,
	    			  R.drawable.ic_point_of_care_cross,
	    			  R.drawable.ic_learning_center,
	    			  R.drawable.ic_achievement,
	    			  R.drawable.ic_staying_well};
	    
	    MainScreenBaseAdapter adapter=new MainScreenBaseAdapter(mContext,categories,images);
	    main_menu_listview.setAdapter(adapter);				
	    main_menu_listview.setOnItemClickListener(this);
		dbh = new DbHelper(getApplicationContext());
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
		
		Intent service = new Intent(this, TrackerService.class);
		Bundle tb = new Bundle();
		tb.putBoolean("backgroundData", true);
		service.putExtras(tb);
		this.startService(service);
		
	    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager2);
        
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
	  
	}
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
        }

        @Override
        public Fragment getItem(int position) {
                Fragment fragment = null;
                if(position==0 ){
                	 fragment= new EventsSummary();   
                }else if(position==1){
                	 fragment= new EventsDetails();   
                }
               	
                return fragment;
        }

        @Override
        public int getCount() {
                return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
}
	
	 public static class EventsSummary extends Fragment {
		 View rootView;
		// private SharedPreferences loginPref;
		 private String name;
		 //private ArrayList<String> eventsNumber;
		private TextView event_number;
		int month;
		String month_text;
		 CalendarEvents c;
		 public ArrayList<String> EventTypeToday;
		private SharedPreferences prefs;
		private String current_month;
		private HashMap<String, String> eventUpdateItemsDaily;
		private HashMap<String, String> coverageUpdateItemsDaily;
		private HashMap<String, String> otherUpdateItemsDaily;
		private ArrayList<String> eventId;
		private ArrayList<String> coverageId;
		private ArrayList<String> otherId;
		private TextView textView_eventTargetsNumber;
		private TextView textView_clickHere;
		 public EventsSummary(){
			 
		 }
		 
		 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			 	rootView=inflater.inflate(R.layout.events_pager_layout,null,false);
			 	prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
			 	//loginPref=getApplicationContext().getSharedPreferences("loginPrefs", MODE_WORLD_READABLE);
			    name=prefs.getString(getString(R.string.prefs_username), "name");
			    dbh=new DbHelper(getActivity());
			    status=(TextView) rootView.findViewById(R.id.textView_status);
			    event_number=(TextView) rootView.findViewById(R.id.textView_eventsNumber);
			    Time time = new Time();
			    time.setToNow();
			    Calendar rightNow = Calendar.getInstance();
			    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MMMM");
			   // month= rightNow.get(Calendar.MONTH)+1;
			   // month_text.equals("September");
			   // System.out.println(month_text);
			    c= new CalendarEvents(mContext);
			    EventTypeToday=c.getTodaysEventsType();
			    if(time.hour<12)
			    {
			    	System.out.println("name");
			    	  status.setText("Good morning, "+name+"!");
			    }else if(time.hour>12&& time.hour<17)
			    {
			    	 status.setText("Good afternoon, "+name+"!");
			    }else if(time.hour>17&& time.hour<20)
			    {
			    	 status.setText("Good evening, "+name+"!");
			    }else{
			    	 status.setText("Good day, "+name+"!");
			    }
			 //eventsNumber=db.getAllEventsForMonth("September");
			 if(EventTypeToday.get(0).equalsIgnoreCase("No planned events for today")){
				 event_number.setText("0"); 
			 }else {
				 event_number.setText(String.valueOf(EventTypeToday.size())); 
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
		        eventUpdateItemsDaily=dbh.getAllDailyEvents(current_month);
				coverageUpdateItemsDaily=dbh.getAllDailyCoverage(current_month);
				otherUpdateItemsDaily=dbh.getAllDailyOther(current_month);
				//Intent service2 = new Intent(this,UpdateTargetsService.class);
				//this.startService(service2);
				
				textView_eventTargetsNumber=(TextView) rootView.findViewById(R.id.textView_eventTargetsNumber);
				textView_clickHere=(TextView) rootView.findViewById(R.id.textView_clickHere);
				textView_clickHere.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
						startActivity(intent);
					}
					
				});
				 eventId=new ArrayList<String>();
				 eventId.add(eventUpdateItemsDaily.get("event_id"));
				 coverageId=new ArrayList<String>();
				 coverageId.add(coverageUpdateItemsDaily.get("coverage_id"));
				 otherId=new ArrayList<String>();
				 otherId.add(otherUpdateItemsDaily.get("other_id"));
				 int number=eventId.size();
				 int number2=coverageId.size();
				 int number3=otherId.size();
				 int counter;
				if(eventUpdateItemsDaily.isEmpty()){
					number=0;
				}else{
					number=eventId.size();
				}
				if(coverageUpdateItemsDaily.isEmpty()){
					number2=0;
				}else {
					number2=coverageId.size();
				}
				if(otherUpdateItemsDaily.isEmpty()){
					number3=0;
				}else{
					number3=otherId.size();
				}
				counter=number+number2+number3;
				System.out.println(counter);
				textView_eventTargetsNumber.setText(String.valueOf(counter));
			 return rootView;
		 }
	 }
	 
	 public static class EventsDetails extends Fragment {
		 View rootView;
		 CalendarEvents c;
		 public ArrayList<String> EventTypeToday;
		 public ArrayList<String> EventTypeTime;
		private TextView eventStatus;
		int month;
		String month_text;
		private ListView listView_details;
		 public EventsDetails(){
			 
		 }
		 
		 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			 	rootView=inflater.inflate(R.layout.events_detail_pager_layout,null,false);
			    dbh=new DbHelper(getActivity());
			    status=(TextView) rootView.findViewById(R.id.textView_status);
			    eventStatus=(TextView) rootView.findViewById(R.id.textView1);
			    listView_details=(ListView) rootView.findViewById(R.id.listView_eventsDetail);
			    Time time = new Time();
			    time.setToNow();
			    Calendar rightNow = Calendar.getInstance();
			    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MMMM");
			   // month= rightNow.get(Calendar.MONTH)+1;
			    //month_text.equals(df.format(month));
			    c= new CalendarEvents(mContext);
			    EventTypeToday=c.getTodaysEventsType();
			    EventTypeTime=c.getTodaysEventsTime(false);
			 if(EventTypeToday.size()==0){
				 eventStatus.setText("No events planned for today!"); 
			 }else if(EventTypeToday.size()>0){
				 EventsDetailPagerAdapter adapter=new EventsDetailPagerAdapter(getActivity(),EventTypeToday,EventTypeTime);
			    	adapter.notifyDataSetChanged();
			    	listView_details.setAdapter(adapter);	 
			 }
			 return rootView;
		 }
	 }
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.activity_home, menu);
			return true;
		}

		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int itemId = item.getItemId();
			if (itemId == R.id.menu_settings) {
				Intent i = new Intent(this, PrefsActivity.class);
				startActivity(i);
				return true;
			} else if (itemId == R.id.menu_about) {
				startActivity(new Intent(this, AboutActivity.class));
				return true;
			} else if (itemId == R.id.menu_help) {
				startActivity(new Intent(this, HelpActivity.class));
				return true;
			} else if (itemId == R.id.menu_logout) {
				logout();
				return true;
			}
			return true;
		}

		private void logout() {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setCancelable(false);
			builder.setTitle(R.string.logout);
			builder.setMessage(R.string.logout_confirm);
			builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					
					DbHelper db = new DbHelper(MainScreenActivity.this);
					db.onLogout();
					db.close();
					
					// restart the app
					MainScreenActivity.this.startActivity(new Intent(MainScreenActivity.this, StartUpActivity.class));
					MainScreenActivity.this.finish();
				}
			});
			builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					return; // do nothing
				}
			});
			builder.show();
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		switch(position){
		case 0:
			intent=new Intent(mContext, EventPlannerOptionsActivity.class);
			startActivity(intent);
			break;
		case 1:
			intent=new Intent(mContext, PointOfCareActivity.class);
			startActivity(intent);
			break;
			
		case 2:
			intent = new Intent(getApplicationContext(), OppiaMobileActivity.class);
            startActivity(intent);	
			break;
		case 3:
			intent = new Intent(getApplicationContext(), AchievementCenterActivity.class);
            startActivity(intent);	
			break;
		case 4:
			intent = new Intent(getApplicationContext(), StayingWellActivity.class);
			startActivity(intent);
			break;
		}
		
	}
	
	private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
	private long mBackPressed;

	@Override
	public void onBackPressed()
	{
	    if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) 
	    { 
	        super.onBackPressed(); 
	        return;
	    }
	    else { Toast.makeText(getBaseContext(), "Press back button again to exit", Toast.LENGTH_SHORT).show(); }

	    mBackPressed = System.currentTimeMillis();
	}

}
