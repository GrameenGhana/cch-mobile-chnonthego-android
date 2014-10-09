package org.grameenfoundation.chnonthego;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.grameenfoundation.adapters.CoverageListAdapter;
import org.grameenfoundation.adapters.EventsDetailPagerAdapter;
import org.grameenfoundation.adapters.MainScreenBaseAdapter;
import org.grameenfoundation.chnonthego.NewEventPlannerActivity.CoverageActivity;
import org.grameenfoundation.chnonthego.NewEventPlannerActivity.EventsActivity;
import org.grameenfoundation.chnonthego.NewEventPlannerActivity.LearningActivity;
import org.grameenfoundation.chnonthego.NewEventPlannerActivity.OtherActivity;
import org.grameenfoundation.chnonthego.NewEventPlannerActivity.SectionsPagerAdapter;
import org.grameenfoundation.database.CHNDatabaseHandler;
import org.grameenfoundation.poc.PointOfCareActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainScreenActivity extends FragmentActivity implements OnItemClickListener {

	private ListView main_menu_listview;
	private Context mContext;
	private TextView status;
	
	SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main_screen);
	    mContext=MainScreenActivity.this;
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Welcome");
	   // TypefaceUtil.overrideFont(mContext, "SERIF", "fonts/Roboto-Thin.ttf");
	    main_menu_listview=(ListView) findViewById(R.id.listView_mainScreenMenu);
	    String[] categories={"Planner",
	    		"Point of Care",
	    		"Learning Center",
	    		"Achievements",
	    		"Staying Well"};
	    
	    int[] images={R.drawable.ic_calendar,
	    			  R.drawable.ic_point_of_care,
	    			  R.drawable.ic_learning_center,
	    			  R.drawable.ic_achievement,
	    			  R.drawable.ic_staying_well};
	    MainScreenBaseAdapter adapter=new MainScreenBaseAdapter(mContext,categories,images);
	    main_menu_listview.setAdapter(adapter);				
	    main_menu_listview.setOnItemClickListener(this);
	   
	    
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
	
	 public class EventsSummary extends Fragment {
		 View rootView;
		 private SharedPreferences loginPref;
		 private String name;
		 private ArrayList<String> eventsNumber;
		 private CHNDatabaseHandler db;
		private TextView event_number;
		int month;
		String month_text;
		 public EventsSummary(){
			 
		 }
		 
		 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			 	rootView=inflater.inflate(R.layout.events_pager_layout,null,false);
			 	loginPref=getApplicationContext().getSharedPreferences("loginPrefs", MODE_WORLD_READABLE);
			    name=loginPref.getString("firstname", "name");
			    db=new CHNDatabaseHandler(getActivity());
			    status=(TextView) rootView.findViewById(R.id.textView_status);
			    event_number=(TextView) rootView.findViewById(R.id.textView_eventsNumber);
			    Time time = new Time();
			    time.setToNow();
			    Calendar rightNow = Calendar.getInstance();
			    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MMMM");
			   // month= rightNow.get(Calendar.MONTH)+1;
			   // month_text.equals("September");
			   // System.out.println(month_text);
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
			 eventsNumber=db.getAllEventsForMonth("September");
			 if(eventsNumber.size()==0){
				 event_number.setText("0"); 
			 }else if(eventsNumber.size()>0){
				 event_number.setText(String.valueOf(eventsNumber.size())); 
			 }
			 return rootView;
		 }
	 }
	 
	 public class EventsDetails extends Fragment {
		 View rootView;
		 private ArrayList<String> eventsName;
		 private ArrayList<String> eventsNumber;
		 private CHNDatabaseHandler db;
		private TextView eventStatus;
		int month;
		String month_text;
		private ListView listView_details;
		 public EventsDetails(){
			 
		 }
		 
		 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			 	rootView=inflater.inflate(R.layout.events_detail_pager_layout,null,false);
			    db=new CHNDatabaseHandler(getActivity());
			    status=(TextView) rootView.findViewById(R.id.textView_status);
			    eventStatus=(TextView) rootView.findViewById(R.id.textView1);
			    listView_details=(ListView) rootView.findViewById(R.id.listView_eventsDetail);
			    Time time = new Time();
			    time.setToNow();
			    Calendar rightNow = Calendar.getInstance();
			    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MMMM");
			   // month= rightNow.get(Calendar.MONTH)+1;
			    //month_text.equals(df.format(month));
		   eventsName=db.getAllEventsForMonth("September");
			   eventsNumber=db.getAllEventsNumberForMonth("September");
			   
			 if(eventsName.size()==0){
				 eventStatus.setText("No events planned for today!"); 
			 }else if(eventsName.size()>0){
				 EventsDetailPagerAdapter adapter=new EventsDetailPagerAdapter(getActivity(),eventsName,eventsNumber);
			    	adapter.notifyDataSetChanged();
			    	listView_details.setAdapter(adapter);	 
			 }
			 return rootView;
		 }
	 }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		switch(position){
		case 0:
			intent=new Intent(mContext, NewEventPlannerActivity.class);
			startActivity(intent);
			break;
		case 1:
			intent=new Intent(mContext, PointOfCareActivity.class);
			startActivity(intent);
			break;
		}
		
	}

}
