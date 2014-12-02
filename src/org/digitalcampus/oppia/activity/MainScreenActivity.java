package org.digitalcampus.oppia.activity;

import java.util.ArrayList;
import java.util.Calendar;


import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.service.TrackerService;
import org.grameenfoundation.adapters.EventsDetailPagerAdapter;
import org.grameenfoundation.adapters.MainScreenBaseAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.activity.StayingWellActivity;
import org.grameenfoundation.poc.PointOfCareActivity;
import org.grameenfoundation.cch.model.RoutineActivity;
import org.grameenfoundation.cch.model.RoutineActivityDetails;
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
	public static final String TAG = MainScreenActivity.class.getSimpleName();
	Time time_now;
	Time compared_time;
	Time week;
	Time end_of_month;

	SectionsPagerAdapter mSectionsPagerAdapter;
    static ViewPager mViewPager;
	private static DbHelper dbh;
	private SharedPreferences prefs;
	
	// MODULE IDs
	/*	private static final String EVENT_PLANNER_ID      = "Event Planner";
		private static final String STAYING_WELL_ID       = "Staying Well";
		private static final String POINT_OF_CARE_ID      = "Point of Care";
		private static final String LEARNING_CENTER_ID    = "Learning Center";
		private static final String ACHIEVEMENT_CENTER_ID = "Achievement Center";
    */

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
        mViewPager.setOffscreenPageLimit(3);
        
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
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
                Fragment fragment = null;
                if(position==0 ){
                	 fragment= new EventsSummary();   
                }else if(position==1){
                	 fragment= new EventsDetails();   
                } else if (position==2) {
                	 fragment = new RoutineActivityDetails();
                }
               	
                return fragment;
        }

        @Override
        public int getCount() {
                return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
}
	
	 public static class EventsSummary extends Fragment {
		 View rootView;
		 private TextView event_number;
		 private TextView textView_eventsClickHere;
		 private TextView textView_eventTargetsNumber;
		 private TextView textView_clickHere;
		 private TextView textView_routinesNumber;
		 private TextView textView_routinesClickHere;
		 private TextView tv8;
		 int month;
		 String month_text;
		 String due_date;
		 CalendarEvents c;
		 private SharedPreferences prefs;
		 private String name;
		 private String user_first_name;
			private long eventId;
			private long coverageId;
			private long otherId;
			private long learningId;
		 private ArrayList<String> firstName;
		 public ArrayList<String> EventTypeToday;
		private int numactivities;
		 public EventsSummary(){
			 
		 }

		 
		 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			 	rootView=inflater.inflate(R.layout.events_pager_layout,null,false);
			 	prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
			 	//loginPref=getApplicationContext().getSharedPreferences("loginPrefs", MODE_WORLD_READABLE);
			    name=prefs.getString("first_name", "name");
			    dbh=new DbHelper(getActivity());
			    firstName=dbh.getUserFirstName(name);
			    status=(TextView) rootView.findViewById(R.id.textView_status);
			    event_number=(TextView) rootView.findViewById(R.id.textView_eventsNumber);
			    Time time = new Time();
			    time.setToNow();
			    //String today= String.valueOf(time.monthDay)+"-"+String.valueOf(time.month+1)+"-"+String.valueOf(time.year);
			   
			    c= new CalendarEvents(mContext);
			    
			    EventTypeToday=c.getTodaysEventsType();
			    
			    if(firstName.size()>0){
			    	user_first_name=firstName.get(0);
			    }else if(firstName.size()==0){
			    	user_first_name.equals(name);
			    }
			    
		    	status.setText("Good "+dbh.getTime()+", "+user_first_name+"!");

			 
		    	//eventsNumber=db.getAllEventsForMonth("September");
			 if(EventTypeToday.size()>0&&EventTypeToday.get(0).equalsIgnoreCase("No planned events for today")){
				 event_number.setText("0"); 
			 }else {
				 event_number.setText(String.valueOf(EventTypeToday.size())); 
			 }
			 Calendar c = Calendar.getInstance();
		        int month=c.get(Calendar.MONTH)+1;
		        int day=c.get(Calendar.DAY_OF_WEEK);
		        int year=c.get(Calendar.YEAR);
		        due_date=day+"-"+month+"-"+year;
		        //System.out.println(today);
	
				textView_eventTargetsNumber=(TextView) rootView.findViewById(R.id.textView_eventTargetsNumber);
				textView_clickHere=(TextView) rootView.findViewById(R.id.textView_clickHere);
				
				// eventId=new ArrayList<String>();
				 eventId=dbh.getEventIdCount("Daily");
				// coverageId=new ArrayList<String>();
				 coverageId=dbh.getCoverageIdCount("Daily");
				// otherId=new ArrayList<String>();
				 otherId=dbh.getOtherIdCount("Daily");
				//learningId=new ArrayList<String>();
				 learningId=dbh.getLearningIdCount("Daily");
				 int number=(int)eventId;
				 int number2=(int)coverageId;
				 int number3=(int)otherId;
				 int number4=(int)learningId;
				 final int counter;
				
				counter=number+number2+number3+number4;
				//System.out.println(counter);
				textView_eventTargetsNumber.setText(String.valueOf(counter));
				textView_clickHere.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						if(counter>0){
						Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
						startActivity(intent);
						}else if(counter==0){
							 Toast.makeText(getActivity(), "You have no targets to update!",
							         Toast.LENGTH_SHORT).show();
						}
					}
					
				});
				textView_eventsClickHere = (TextView) rootView.findViewById(R.id.textView_eventsClickHere);
			    textView_eventsClickHere.setOnClickListener(new OnClickListener(){
			    	@Override
					public void onClick(View v) {
							mViewPager.setCurrentItem(1, true);	
					}
				});
			    
				
				/* Routine Info */
				ArrayList<RoutineActivity> todos = dbh.getSWRoutineActivities();
		    			    	
			    textView_routinesNumber = (TextView) rootView.findViewById(R.id.textView_routinesNumber);
				textView_routinesNumber.setText(String.valueOf(numactivities));
			    tv8 = (TextView) rootView.findViewById(R.id.textView8);
			    tv8.setText(" activities this "+dbh.getTime()+".");
				
			    textView_routinesClickHere = (TextView) rootView.findViewById(R.id.textView_routinesClickHere);
			    textView_routinesClickHere.setOnClickListener(new OnClickListener(){

			    	@Override
					public void onClick(View v) {
						if(numactivities > 0){
							mViewPager.setCurrentItem(2, true);
						} else {
							 Toast.makeText(getActivity(), "You have no activities for this "+dbh.getTime(),Toast.LENGTH_SHORT).show();
						}
					}
				});
			    
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
	 public void onResume()
	 {
		 super.onResume();
	     mViewPager.getAdapter().notifyDataSetChanged();
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
