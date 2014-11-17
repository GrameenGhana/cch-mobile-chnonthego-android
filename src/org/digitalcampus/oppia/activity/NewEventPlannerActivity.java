package org.digitalcampus.oppia.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
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
        actionBar.setTitle("Event Planner");
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
        Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
            month_passed = extras.getString("month");
            // and get whatever type user account id is
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
		
	 public static class EventsActivity extends Fragment implements OnChildClickListener{

			private Context mContext;															
			private ExpandableListView listView_events;
			 private ArrayList<String> dailyEventName;
			 private ArrayList<String> dailyEventNumber;
			 private ArrayList<String> dailyEventPeriod;
			 private ArrayList<String> dailyEventDueDate;
			 private ArrayList<String> dailyEventStatus;
			 private ArrayList<String>  dailyEventId;
			 
			 private ArrayList<String> weeklyEventName;
			 private ArrayList<String> weeklyEventNumber;
			 private ArrayList<String> weeklyEventPeriod;
			 private ArrayList<String> weeklyEventDueDate;
			 private ArrayList<String> weeklyEventStatus;
			 private ArrayList<String> weeklyEventId;
			 
			 private ArrayList<String> monthlyEventName;
			 private ArrayList<String> monthlyEventNumber;
			 private ArrayList<String> monthlyEventPeriod;
			 private ArrayList<String> monthlyEventDueDate;
			 private ArrayList<String> monthlyEventStatus;
			 private ArrayList<String> monthlyEventId;
			 
			 private ArrayList<String> yearlyEventName;
			 private ArrayList<String> yearlyEventNumber;
			 private ArrayList<String> yearlyEventPeriod;
			 private ArrayList<String> yearlyEventDueDate;
			 private ArrayList<String> yearlyEventStatus;
			 private ArrayList<String> yearlyEventId;
			 
			 private ArrayList<String> midYearEventName;
			 private ArrayList<String> midYearEventNumber;
			 private ArrayList<String> midYearEventPeriod;
			 private ArrayList<String> midYearEventDueDate;
			 private ArrayList<String> midYearEventStatus;
			 private ArrayList<String> midYearEventId;
			 
			 private HashMap<String,String> dailyEventTargets;
			 private HashMap<String,String> weeklyEventTargets;
			 private HashMap<String,String> monthlyEventTargets;
			 private HashMap<String,String> yearlyEventTargets;
			 private HashMap<String,String> midYearEventTargets;
			 private String[] groupItems;
			private DbHelper db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private EventBaseAdapter events_adapter;
			private String[] selected_items;
			int selected_position;
			private long selected_id;
			static String due_date ;
			private static TextView dueDateValue;
			 public EventsActivity(){

            }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				 rootView=inflater.inflate(R.layout.activity_events,null,false);
			    mContext=getActivity().getApplicationContext();
			    //TypefaceUtil.overrideFont(mContext, "SERIF", "fonts/Roboto-Thin.ttf");
			    db=new DbHelper(getActivity());
			    listView_events=(ExpandableListView) rootView.findViewById(R.id.expandableListView_events);
			  
			    dailyEventTargets=db.getAllUnupdatedDailyEvents(month_passed);
			    System.out.println(dailyEventTargets);
			    weeklyEventTargets=db.getAllUnupdatedWeeklyEvents(month_passed);
			    System.out.println(weeklyEventTargets);
			    monthlyEventTargets=db.getAllUnupdatedMonthlyEvents(month_passed);
			    System.out.println(monthlyEventTargets);
			    yearlyEventTargets=db.getAllUnupdatedYearlyEvents(month_passed);
			    System.out.println(yearlyEventTargets);
			    midYearEventTargets=db.getAllUnupdatedMidyearEvents(month_passed);
			    System.out.println(midYearEventTargets);
			   
			    dailyEventName=new ArrayList<String>();
			    dailyEventName.add(dailyEventTargets.get("event_name"));
			    dailyEventNumber=new ArrayList<String>();
			    dailyEventNumber.add(dailyEventTargets.get("event_number"));
			    dailyEventPeriod=new ArrayList<String>();
			    dailyEventPeriod.add(dailyEventTargets.get("event_period"));
			    dailyEventDueDate=new ArrayList<String>();
			    dailyEventDueDate.add(dailyEventTargets.get("due_date"));
			    dailyEventStatus=new ArrayList<String>();
			    dailyEventStatus.add(dailyEventTargets.get("sync_status"));
			    
			    dailyEventId=new ArrayList<String>();
			    dailyEventId.add(dailyEventTargets.get("event_id"));
			    
			    weeklyEventName=new ArrayList<String>();
			    weeklyEventName.add(weeklyEventTargets.get("event_name"));
			    weeklyEventNumber=new ArrayList<String>();
			    weeklyEventNumber.add(weeklyEventTargets.get("event_number"));
			    weeklyEventPeriod=new ArrayList<String>();
			    weeklyEventPeriod.add(weeklyEventTargets.get("event_period"));
			    weeklyEventDueDate=new ArrayList<String>();
			    weeklyEventDueDate.add(weeklyEventTargets.get("due_date"));
			    weeklyEventStatus=new ArrayList<String>();
			    weeklyEventStatus.add(weeklyEventTargets.get("sync_status"));
			    weeklyEventId=new ArrayList<String>();
			    weeklyEventId.add(weeklyEventTargets.get("event_id"));
			    
			    monthlyEventName=new ArrayList<String>();
			    monthlyEventName.add(monthlyEventTargets.get("event_name"));
			    monthlyEventNumber=new ArrayList<String>();
			    monthlyEventNumber.add(monthlyEventTargets.get("event_number"));
			    monthlyEventPeriod=new ArrayList<String>();
			    monthlyEventPeriod.add(monthlyEventTargets.get("event_period"));
			    monthlyEventDueDate=new ArrayList<String>();
			    monthlyEventDueDate.add(monthlyEventTargets.get("due_date"));
			    monthlyEventStatus=new ArrayList<String>();
			    monthlyEventStatus.add(monthlyEventTargets.get("sync_status"));
			    monthlyEventId=new ArrayList<String>();
			    monthlyEventId.add(monthlyEventTargets.get("event_id"));
			    
			    yearlyEventName=new ArrayList<String>();
			    yearlyEventName.add(yearlyEventTargets.get("event_name"));
			    yearlyEventNumber=new ArrayList<String>();
			    yearlyEventNumber.add(yearlyEventTargets.get("event_number"));
			    yearlyEventPeriod=new ArrayList<String>();
			    yearlyEventPeriod.add(yearlyEventTargets.get("event_period"));
			    yearlyEventDueDate=new ArrayList<String>();
			    yearlyEventDueDate.add(yearlyEventTargets.get("due_date"));
			    yearlyEventStatus=new ArrayList<String>();
			    yearlyEventStatus.add(yearlyEventTargets.get("sync_status"));
			    yearlyEventId=new ArrayList<String>();
			    yearlyEventId.add(yearlyEventTargets.get("event_id"));
			    
			    midYearEventName=new ArrayList<String>();
			    midYearEventName.add(midYearEventTargets.get("event_name"));
			    midYearEventNumber=new ArrayList<String>();
			    midYearEventNumber.add(midYearEventTargets.get("event_number"));
			    midYearEventPeriod=new ArrayList<String>();
			    midYearEventPeriod.add(midYearEventTargets.get("event_period"));
			    midYearEventDueDate=new ArrayList<String>();
			    midYearEventDueDate.add(midYearEventTargets.get("due_date"));
			    midYearEventStatus=new ArrayList<String>();
			    midYearEventStatus.add(midYearEventTargets.get("sync_status"));
			    midYearEventId=new ArrayList<String>();
			    midYearEventId.add(midYearEventTargets.get("event_id"));
			    groupItems=new String[]{"Daily","Weekly","Monthly","Annually","Mid-year"};
			    events_adapter=new EventBaseAdapter(mContext,dailyEventName ,
															dailyEventNumber,
															dailyEventPeriod,
															dailyEventDueDate,
															dailyEventStatus,
															dailyEventId,
							
															weeklyEventName,
															weeklyEventNumber,
															weeklyEventPeriod,
							weeklyEventDueDate,
							weeklyEventStatus,
							weeklyEventId,
							
							monthlyEventName,
							monthlyEventNumber,
							monthlyEventPeriod,
							monthlyEventDueDate,
							monthlyEventStatus,
							monthlyEventId,
							
							yearlyEventName,
							yearlyEventNumber,
							yearlyEventPeriod,
							yearlyEventDueDate,
							yearlyEventStatus,
							yearlyEventId,
							
							midYearEventName,
							midYearEventNumber,
							midYearEventPeriod,
							midYearEventDueDate,
							midYearEventStatus,
							midYearEventId,
							groupItems,
							listView_events);
			  
			   
			    listView_events.setAdapter(events_adapter);	
			    View empty_view=new View(getActivity());
			    listView_events.setEmptyView(empty_view);
			    events_adapter.notifyDataSetChanged();
			   
			    listView_events.setOnChildClickListener(this);
			   
			   Button b = (Button) rootView.findViewById(R.id.button_addEvent);
			   /*if(!month_passed.equalsIgnoreCase(current_month)){
				  b.setVisibility(View.GONE); 
			   }
			   */
			    b.setOnClickListener(new OnClickListener() {
			    	

					@Override
				       public void onClick(View v) {
			    		final Dialog dialog = new Dialog(getActivity());
						dialog.setContentView(R.layout.event_set_dialog);
						dialog.setTitle("Set Event Target");
						String[] items={"Daily","Weekly","Monthly","Annually","Quarterly","Mid-year"};
						final Spinner spinner_event_period=(Spinner) dialog.findViewById(R.id.spinner_dialogEventPeriod);
						final Spinner spinner_event_name=(Spinner) dialog.findViewById(R.id.spinner_eventName);
						
						ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
						spinner_event_period.setAdapter(adapter);
						String[] items_names={"ANC Static","ANC Outreach","CWC Static","CWC Outreach",
												"PNC Clinic","Routine Home visit","Special Home visit",
												"Family Planning","Health Talk","CMAM Clinic","School Health",
												"Adolescent Health","Mop-up Activity/Event","Community Durbar",
												"National Activity/Event","Staff meetings/durbars","Workshops","Leave/Excuse Duty",
												"Personal","Other"};
						//ArrayList<String> list=db.getAllEventCategory();
						ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items_names);
						spinner_event_name.setAdapter(adapter2);
						Button datepickerDialog=(Button) dialog.findViewById(R.id.button_setDateDialog);
						datepickerDialog.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								 DialogFragment newFragment = new DatePickerFragment();
								    newFragment.show(getFragmentManager(), "datePicker");
								
							}
							
						});
						Button cancel=(Button) dialog.findViewById(R.id.button_cancel);
						cancel.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
							dialog.dismiss();
								
							}
							
						});
						Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogSetEVent);
						dialogButton.setText("Save");
						dueDateValue=(TextView) dialog.findViewById(R.id.textView_dueDateValue);
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							
								EditText editText_event_period=(EditText) dialog.findViewById(R.id.editText_dialogEventPeriodNumber);
								String event_period=spinner_event_period.getSelectedItem().toString();
								String event_name=spinner_event_name.getSelectedItem().toString();
								String event_period_number=editText_event_period.getText().toString();
							    if(db.insertEventSet(event_name, event_period, event_period_number, month_passed,due_date,"new_record") ==true){
							    	getActivity().runOnUiThread(new Runnable() {
							            @Override
							            public void run() {
							            	dailyEventTargets=db.getAllUnupdatedDailyEvents(month_passed);
										    System.out.println(dailyEventTargets);
										    weeklyEventTargets=db.getAllUnupdatedWeeklyEvents(month_passed);
										    System.out.println(weeklyEventTargets);
										    monthlyEventTargets=db.getAllUnupdatedMonthlyEvents(month_passed);
										    System.out.println(monthlyEventTargets);
										    yearlyEventTargets=db.getAllUnupdatedYearlyEvents(month_passed);
										    System.out.println(yearlyEventTargets);
										    midYearEventTargets=db.getAllUnupdatedMidyearEvents(month_passed);
										    System.out.println(midYearEventTargets);
										   
										    dailyEventName=new ArrayList<String>();
										    dailyEventName.add(dailyEventTargets.get("event_name"));
										    dailyEventNumber=new ArrayList<String>();
										    dailyEventNumber.add(dailyEventTargets.get("event_number"));
										    dailyEventPeriod=new ArrayList<String>();
										    dailyEventPeriod.add(dailyEventTargets.get("event_period"));
										    dailyEventDueDate=new ArrayList<String>();
										    dailyEventDueDate.add(dailyEventTargets.get("due_date"));
										    dailyEventStatus=new ArrayList<String>();
										    dailyEventStatus.add(dailyEventTargets.get("sync_status"));
										    
										    dailyEventId=new ArrayList<String>();
										    dailyEventId.add(dailyEventTargets.get("event_id"));
										    
										    weeklyEventName=new ArrayList<String>();
										    weeklyEventName.add(weeklyEventTargets.get("event_name"));
										    weeklyEventNumber=new ArrayList<String>();
										    weeklyEventNumber.add(weeklyEventTargets.get("event_number"));
										    weeklyEventPeriod=new ArrayList<String>();
										    weeklyEventPeriod.add(weeklyEventTargets.get("event_period"));
										    weeklyEventDueDate=new ArrayList<String>();
										    weeklyEventDueDate.add(weeklyEventTargets.get("due_date"));
										    weeklyEventStatus=new ArrayList<String>();
										    weeklyEventStatus.add(weeklyEventTargets.get("sync_status"));
										    weeklyEventId=new ArrayList<String>();
										    weeklyEventId.add(weeklyEventTargets.get("event_id"));
										    
										    monthlyEventName=new ArrayList<String>();
										    monthlyEventName.add(monthlyEventTargets.get("event_name"));
										    monthlyEventNumber=new ArrayList<String>();
										    monthlyEventNumber.add(monthlyEventTargets.get("event_number"));
										    monthlyEventPeriod=new ArrayList<String>();
										    monthlyEventPeriod.add(monthlyEventTargets.get("period"));
										    monthlyEventDueDate=new ArrayList<String>();
										    monthlyEventDueDate.add(monthlyEventTargets.get("due_date"));
										    monthlyEventStatus=new ArrayList<String>();
										    monthlyEventStatus.add(monthlyEventTargets.get("sync_status"));
										    monthlyEventId=new ArrayList<String>();
										    monthlyEventId.add(monthlyEventTargets.get("event_id"));
										    
										    yearlyEventName=new ArrayList<String>();
										    yearlyEventName.add(yearlyEventTargets.get("event_name"));
										    yearlyEventNumber=new ArrayList<String>();
										    yearlyEventNumber.add(yearlyEventTargets.get("event_number"));
										    yearlyEventPeriod=new ArrayList<String>();
										    yearlyEventPeriod.add(yearlyEventTargets.get("event_period"));
										    yearlyEventDueDate=new ArrayList<String>();
										    yearlyEventDueDate.add(yearlyEventTargets.get("event_due_date"));
										    yearlyEventStatus=new ArrayList<String>();
										    yearlyEventStatus.add(yearlyEventTargets.get("sync_status"));
										    yearlyEventId=new ArrayList<String>();
										    yearlyEventId.add(yearlyEventTargets.get("event_id"));
										    
										    midYearEventName=new ArrayList<String>();
										    midYearEventName.add(midYearEventTargets.get("event_name"));
										    midYearEventNumber=new ArrayList<String>();
										    midYearEventNumber.add(midYearEventTargets.get("event_number"));
										    midYearEventPeriod=new ArrayList<String>();
										    midYearEventPeriod.add(midYearEventTargets.get("event_period"));
										    midYearEventDueDate=new ArrayList<String>();
										    midYearEventDueDate.add(midYearEventTargets.get("due_date"));
										    midYearEventStatus=new ArrayList<String>();
										    midYearEventStatus.add(midYearEventTargets.get("sync_status"));
										    midYearEventId=new ArrayList<String>();
										    midYearEventId.add(midYearEventTargets.get("event_id"));
										    groupItems=new String[]{"Daily","Weekly","Monthly","Annually","Mid-year"};
										    events_adapter=new EventBaseAdapter(mContext,dailyEventName ,
																						dailyEventNumber,
																						dailyEventPeriod,
																						dailyEventDueDate,
																						dailyEventStatus,
																						dailyEventId,
														
																						weeklyEventName,
																						weeklyEventNumber,
																						weeklyEventPeriod,
														weeklyEventDueDate,
														weeklyEventStatus,
														weeklyEventId,
														
														monthlyEventName,
														monthlyEventNumber,
														monthlyEventPeriod,
														monthlyEventDueDate,
														monthlyEventStatus,
														monthlyEventId,
														
														yearlyEventName,
														yearlyEventNumber,
														yearlyEventPeriod,
														yearlyEventDueDate,
														yearlyEventStatus,
														yearlyEventId,
														
														midYearEventName,
														midYearEventNumber,
														midYearEventPeriod,
														midYearEventDueDate,
														midYearEventStatus,
														midYearEventId,
														groupItems,
														listView_events);
										  
										   
										    listView_events.setAdapter(events_adapter);	
										    events_adapter.notifyDataSetChanged();
							 			   
							            }
							        });
							    
							    	 Toast.makeText(getActivity().getApplicationContext(), "Event target set successfully!",
									         Toast.LENGTH_LONG).show();
							    }else{
							    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
									         Toast.LENGTH_LONG).show();
							    }
							}
						});
			 				dialog.show();
					}

			    });
			return rootView;
				   
			}
			 public static class DatePickerFragment extends DialogFragment
			 				implements DatePickerDialog.OnDateSetListener {

				 @Override
				 	public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
final Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);

// Create a new instance of DatePickerDialog and return it
return new DatePickerDialog(getActivity(), this, year, month, day);
}

public void onDateSet(DatePicker view, int year, int month, int day) {
		int month_value=month+1;
		due_date=day+"-"+month_value+"-"+year;
		dueDateValue.setText(due_date);
}
}
		
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, final long id) {
				selected_items=events_adapter.getChild(groupPosition, childPosition);
				selected_id=Long.parseLong(selected_items[5]);
				String event_name=selected_items[0];
				String event_number=selected_items[1];
				String event_period=selected_items[2];
				String due_date=selected_items[3];
				String status=selected_items[4];
				Intent intent=new Intent(getActivity(),EventTargetsDetailActivity.class);
				intent.putExtra("event_id",selected_id);
				intent.putExtra("event_name",event_name);
				intent.putExtra("event_number",event_number);
				intent.putExtra("event_period", event_period);
				intent.putExtra("due_date", due_date);
				intent.putExtra("status", status);
				startActivity(intent);
					return true;
		}
			
	 }
	 public static class CoverageActivity extends Fragment implements OnChildClickListener{

			private Context mContext;															
			 private ArrayList<String> dailyCoverageName;
			 private ArrayList<String> dailyCoverageNumber;
			 private ArrayList<String> dailyCoveragePeriod;
			 private ArrayList<String> dailyCoverageDueDate;
			 private ArrayList<String> dailyCoverageStatus;
			 private ArrayList<String>  dailyCoverageId;
			 
			 private ArrayList<String> weeklyCoverageName;
			 private ArrayList<String> weeklyCoverageNumber;
			 private ArrayList<String> weeklyCoveragePeriod;
			 private ArrayList<String> weeklyCoverageDueDate;
			 private ArrayList<String> weeklyCoverageStatus;
			 private ArrayList<String> weeklyCoverageId;
			 
			 private ArrayList<String> monthlyCoverageName;
			 private ArrayList<String> monthlyCoverageNumber;
			 private ArrayList<String> monthlyCoveragePeriod;
			 private ArrayList<String> monthlyCoverageDueDate;
			 private ArrayList<String> monthlyCoverageStatus;
			 private ArrayList<String> monthlyCoverageId;
			 
			 private ArrayList<String> yearlyCoverageName;
			 private ArrayList<String> yearlyCoverageNumber;
			 private ArrayList<String> yearlyCoveragePeriod;
			 private ArrayList<String> yearlyCoverageDueDate;
			 private ArrayList<String> yearlyCoverageStatus;
			 private ArrayList<String> yearlyCoverageId;
			 
			 private ArrayList<String> midYearCoverageName;
			 private ArrayList<String> midYearCoverageNumber;
			 private ArrayList<String> midYearCoveragePeriod;
			 private ArrayList<String> midYearCoverageDueDate;
			 private ArrayList<String> midYearCoverageStatus;
			 private ArrayList<String> midYearCoverageId;
			 
			 private HashMap<String,String> dailyCoverageTargets;
			 private HashMap<String,String> weeklyCoverageTargets;
			 private HashMap<String,String> monthlyCoverageTargets;
			 private HashMap<String,String> yearlyCoverageTargets;
			 private HashMap<String,String> midYearCoverageTargets;
			 private String[] groupItems;
			private DbHelper db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private ExpandableListView listView_coverage;
			private CoverageListAdapter coverage_adapter;
			private String[] selected_items;
			private RadioGroup category_options;
			private String[] items3;
			int selected_position;
			protected RadioButton category_people;
			private long selected_id;
			static String due_date ;
			private static TextView dueDateValue;
			
			 public CoverageActivity(){

         }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				 rootView=inflater.inflate(R.layout.activity_coverage,null,false);
			    mContext=getActivity().getApplicationContext();
			    db=new DbHelper(getActivity());
				groupItems=new String[]{"Daily","Weekly","Monthly","Annually","Mid-year"};
			    listView_coverage=(ExpandableListView) rootView.findViewById(R.id.expandableListView1);
			    listView_coverage.setOnChildClickListener(this);
			    dailyCoverageTargets=db.getAllUnupdatedDailyEvents(month_passed);
			    weeklyCoverageTargets=db.getAllUnupdatedWeeklyEvents(month_passed);
			    monthlyCoverageTargets=db.getAllUnupdatedMonthlyEvents(month_passed);
			    yearlyCoverageTargets=db.getAllUnupdatedYearlyEvents(month_passed);
			    midYearCoverageTargets=db.getAllUnupdatedMidyearEvents(month_passed);
			   
			    dailyCoverageName=new ArrayList<String>();
			    dailyCoverageName.add(dailyCoverageTargets.get("coverage_name"));
			    dailyCoverageNumber=new ArrayList<String>();
			    dailyCoverageNumber.add(dailyCoverageTargets.get("coverage_number"));
			    dailyCoveragePeriod=new ArrayList<String>();
			    dailyCoveragePeriod.add(dailyCoverageTargets.get("coverage_period"));
			    dailyCoverageDueDate=new ArrayList<String>();
			    dailyCoverageDueDate.add(dailyCoverageTargets.get("due_date"));
			    dailyCoverageStatus=new ArrayList<String>();
			    dailyCoverageStatus.add(dailyCoverageTargets.get("sync_status"));
			    dailyCoverageId=new ArrayList<String>();
			    dailyCoverageId.add(dailyCoverageTargets.get("coverage_id"));
			    
			    weeklyCoverageName=new ArrayList<String>();
			    weeklyCoverageName.add(weeklyCoverageTargets.get("coverage_name"));
			    weeklyCoverageNumber=new ArrayList<String>();
			    weeklyCoverageNumber.add(weeklyCoverageTargets.get("coverage_number"));
			    weeklyCoveragePeriod=new ArrayList<String>();
			    weeklyCoveragePeriod.add(weeklyCoverageTargets.get("coverage_period"));
			    weeklyCoverageDueDate=new ArrayList<String>();
			    weeklyCoverageDueDate.add(weeklyCoverageTargets.get("due_date"));
			    weeklyCoverageStatus=new ArrayList<String>();
			    weeklyCoverageStatus.add(weeklyCoverageTargets.get("sync_status"));
			    weeklyCoverageId=new ArrayList<String>();
			    weeklyCoverageId.add(weeklyCoverageTargets.get("coverage_id"));
			    
			    monthlyCoverageName=new ArrayList<String>();
			    monthlyCoverageName.add(monthlyCoverageTargets.get("coverage_name"));
			    monthlyCoverageNumber=new ArrayList<String>();
			    monthlyCoverageNumber.add(monthlyCoverageTargets.get("coverage_number"));
			    monthlyCoveragePeriod=new ArrayList<String>();
			    monthlyCoveragePeriod.add(monthlyCoverageTargets.get("coverage_period"));
			    monthlyCoverageDueDate=new ArrayList<String>();
			    monthlyCoverageDueDate.add(monthlyCoverageTargets.get("due_date"));
			    monthlyCoverageStatus=new ArrayList<String>();
			    monthlyCoverageStatus.add(monthlyCoverageTargets.get("sync_status"));
			    monthlyCoverageId=new ArrayList<String>();
			    monthlyCoverageId.add(monthlyCoverageTargets.get("coverage_id"));
			    
			    yearlyCoverageName=new ArrayList<String>();
			    yearlyCoverageName.add(yearlyCoverageTargets.get("coverage_name"));
			    yearlyCoverageNumber=new ArrayList<String>();
			    yearlyCoverageNumber.add(yearlyCoverageTargets.get("coverage_number"));
			    yearlyCoveragePeriod=new ArrayList<String>();
			    yearlyCoveragePeriod.add(yearlyCoverageTargets.get("coverage_period"));
			    yearlyCoverageDueDate=new ArrayList<String>();
			    yearlyCoverageDueDate.add(yearlyCoverageTargets.get("due_date"));
			    yearlyCoverageStatus=new ArrayList<String>();
			    yearlyCoverageStatus.add(yearlyCoverageTargets.get("sync_status"));
			    yearlyCoverageId=new ArrayList<String>();
			    yearlyCoverageId.add(yearlyCoverageTargets.get("coverage_id"));
			    
			    midYearCoverageName=new ArrayList<String>();
			    midYearCoverageName.add(midYearCoverageTargets.get("coverage_name"));
			    midYearCoverageNumber=new ArrayList<String>();
			    midYearCoverageNumber.add(midYearCoverageTargets.get("coverage_number"));
			    midYearCoveragePeriod=new ArrayList<String>();
			    midYearCoveragePeriod.add(midYearCoverageTargets.get("coverage_period"));
			    midYearCoverageDueDate=new ArrayList<String>();
			    midYearCoverageDueDate.add(midYearCoverageTargets.get("due_date"));
			    midYearCoverageStatus=new ArrayList<String>();
			    midYearCoverageStatus.add(midYearCoverageTargets.get("sync_status"));
			    midYearCoverageId=new ArrayList<String>();
			    midYearCoverageId.add(midYearCoverageTargets.get("coverage_id"));
			    groupItems=new String[]{"Daily","Weekly","Monthly","Yearly","Mid-year"};
			    coverage_adapter=new CoverageListAdapter(mContext,dailyCoverageName ,
															dailyCoverageNumber,
															dailyCoveragePeriod,
															dailyCoverageDueDate,
															dailyCoverageStatus,
															dailyCoverageId,
							
															weeklyCoverageName,
															weeklyCoverageNumber,
															weeklyCoveragePeriod,
															weeklyCoverageDueDate,
															weeklyCoverageStatus,
															weeklyCoverageId,
							
															monthlyCoverageName,
															monthlyCoverageNumber,
															monthlyCoveragePeriod,
															monthlyCoverageDueDate,
															monthlyCoverageStatus,
															monthlyCoverageId,
							
															yearlyCoverageName,
															yearlyCoverageNumber,
															yearlyCoveragePeriod,
															yearlyCoverageDueDate,
															yearlyCoverageStatus,
															yearlyCoverageId,
							
															midYearCoverageName,
															midYearCoverageNumber,
															midYearCoveragePeriod,
															midYearCoverageDueDate,
															midYearCoverageStatus,
															midYearCoverageId,
															groupItems,
															listView_coverage);
			   
			    
			    coverage_adapter.notifyDataSetChanged();
		    	listView_coverage.setAdapter(coverage_adapter);	
		    	 View empty_view=new View(getActivity());
		    	 listView_coverage.setEmptyView(empty_view);
		    	listView_coverage.setOnChildClickListener(this);
			    Button b = (Button) rootView.findViewById(R.id.button_addCoverage);
			   /* if(!month_passed.equalsIgnoreCase(current_month)){
					  b.setVisibility(View.GONE); 
				   }*/
			    b.setOnClickListener(new OnClickListener() {
			    	 String coverage_detail;
			       @Override
			       public void onClick(View v) {
			    	   final Dialog dialog = new Dialog(getActivity());
						dialog.setContentView(R.layout.coverage_add_dialog);
						Button datepickerDialog=(Button) dialog.findViewById(R.id.button_setDateDialog);
						datepickerDialog.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								 DialogFragment newFragment = new DatePickerFragment();
								    newFragment.show(getFragmentManager(), "datePicker");
								
							}
							
						});
						Button cancel=(Button) dialog.findViewById(R.id.button_cancel);
						cancel.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
							dialog.dismiss();
								
							}
							
						});
						dueDateValue=(TextView) dialog.findViewById(R.id.textView_dueDateValue);
						final Spinner spinner_coverageName=(Spinner) dialog.findViewById(R.id.spinner_dialogCoverageName);
						 category_options=(RadioGroup) dialog.findViewById(R.id.radioGroup_category);
						  category_options.check(R.id.radio_people);
						  category_people=(RadioButton) dialog.findViewById(R.id.radio_people);
						  category_people.setChecked(true);
						  items3=new String[]{"0 - 11 months","12 - 23 months",
											"24 -59 months","Women in fertile age",
											"Expected pregnancy"};
							ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
							spinner_coverageName.setAdapter(adapter3);
						dialog.setTitle("Add Coverage Target");
						    category_options.setOnCheckedChangeListener(new OnCheckedChangeListener(){
						    	
								public void onCheckedChanged(
										RadioGroup buttonView,
										int isChecked) {
									if (isChecked == R.id.radio_people) {
										items3=new String[]{"0 - 11 months","12 - 23 months",
												"24 -59 months","Women in fertile age",
												"Expected pregnancy"};
										ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
										spinner_coverageName.setAdapter(adapter3);
										coverage_detail="People";
									} else if (isChecked == R.id.radio_immunization) {
										items3=new String[]{"BCG",
												"Penta 3","OPV 3","Rota 2",
												"PCV 3","Measles Rubella","Yellow fever"};
										ArrayAdapter<String> adapter4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
										spinner_coverageName.setAdapter(adapter4);
										coverage_detail="Immunization";
									}
									
								}

						    });
						    String[] items2={"Daily","Weekly","Monthly","Annually","Quarterly","Mid-year"};
					
						
						
						final Spinner spinner_coveragePeriod=(Spinner) dialog.findViewById(R.id.spinner_coveragePeriod);
						ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items2);
						spinner_coveragePeriod.setAdapter(adapter2);
						Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddCoverage);
						dialogButton.setText("Save");
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								
								EditText editText_coverageNumber=(EditText) dialog.findViewById(R.id.editText_dialogCoverageNumber);
								
								String coverage_name=spinner_coverageName.getSelectedItem().toString();
								String coverage_period=spinner_coveragePeriod.getSelectedItem().toString();
								String coverage_number=editText_coverageNumber.getText().toString();
							    if(db.insertCoverageSet(coverage_name, coverage_detail, coverage_period, coverage_number, month_passed,due_date,"new_record") ==true){
							    	getActivity().runOnUiThread(new Runnable() {
							            @Override
							            public void run() {
							            	dailyCoverageTargets=db.getAllUnupdatedDailyEvents(month_passed);
							  			    weeklyCoverageTargets=db.getAllUnupdatedWeeklyEvents(month_passed);
							  			    monthlyCoverageTargets=db.getAllUnupdatedMonthlyEvents(month_passed);
							  			    yearlyCoverageTargets=db.getAllUnupdatedYearlyEvents(month_passed);
							  			    midYearCoverageTargets=db.getAllUnupdatedMidyearEvents(month_passed);
							  			   
							  			    dailyCoverageName=new ArrayList<String>();
							  			    dailyCoverageName.add(dailyCoverageTargets.get("coverage_name"));
							  			    dailyCoverageNumber=new ArrayList<String>();
							  			    dailyCoverageNumber.add(dailyCoverageTargets.get("coverage_number"));
							  			    dailyCoveragePeriod=new ArrayList<String>();
							  			    dailyCoveragePeriod.add(dailyCoverageTargets.get("coverage_period"));
							  			    dailyCoverageDueDate=new ArrayList<String>();
							  			    dailyCoverageDueDate.add(dailyCoverageTargets.get("due_date"));
							  			    dailyCoverageStatus=new ArrayList<String>();
							  			    dailyCoverageStatus.add(dailyCoverageTargets.get("sync_status"));
							  			    dailyCoverageId=new ArrayList<String>();
							  			    dailyCoverageId.add(dailyCoverageTargets.get("coverage_id"));
							  			    
							  			    weeklyCoverageName=new ArrayList<String>();
							  			    weeklyCoverageName.add(weeklyCoverageTargets.get("coverage_name"));
							  			    weeklyCoverageNumber=new ArrayList<String>();
							  			    weeklyCoverageNumber.add(weeklyCoverageTargets.get("coverage_number"));
							  			    weeklyCoveragePeriod=new ArrayList<String>();
							  			    weeklyCoveragePeriod.add(weeklyCoverageTargets.get("coverage_period"));
							  			    weeklyCoverageDueDate=new ArrayList<String>();
							  			    weeklyCoverageDueDate.add(weeklyCoverageTargets.get("due_date"));
							  			    weeklyCoverageStatus=new ArrayList<String>();
							  			    weeklyCoverageStatus.add(weeklyCoverageTargets.get("sync_status"));
							  			    weeklyCoverageId=new ArrayList<String>();
							  			    weeklyCoverageId.add(weeklyCoverageTargets.get("coverage_id"));
							  			    
							  			    monthlyCoverageName=new ArrayList<String>();
							  			    monthlyCoverageName.add(monthlyCoverageTargets.get("coverage_name"));
							  			    monthlyCoverageNumber=new ArrayList<String>();
							  			    monthlyCoverageNumber.add(monthlyCoverageTargets.get("coverage_number"));
							  			    monthlyCoveragePeriod=new ArrayList<String>();
							  			    monthlyCoveragePeriod.add(monthlyCoverageTargets.get("coverage_period"));
							  			    monthlyCoverageDueDate=new ArrayList<String>();
							  			    monthlyCoverageDueDate.add(monthlyCoverageTargets.get("due_date"));
							  			    monthlyCoverageStatus=new ArrayList<String>();
							  			    monthlyCoverageStatus.add(monthlyCoverageTargets.get("sync_status"));
							  			    monthlyCoverageId=new ArrayList<String>();
							  			    monthlyCoverageId.add(monthlyCoverageTargets.get("coverage_id"));
							  			    
							  			    yearlyCoverageName=new ArrayList<String>();
							  			    yearlyCoverageName.add(yearlyCoverageTargets.get("coverage_name"));
							  			    yearlyCoverageNumber=new ArrayList<String>();
							  			    yearlyCoverageNumber.add(yearlyCoverageTargets.get("coverage_number"));
							  			    yearlyCoveragePeriod=new ArrayList<String>();
							  			    yearlyCoveragePeriod.add(yearlyCoverageTargets.get("coverage_period"));
							  			    yearlyCoverageDueDate=new ArrayList<String>();
							  			    yearlyCoverageDueDate.add(yearlyCoverageTargets.get("due_date"));
							  			    yearlyCoverageStatus=new ArrayList<String>();
							  			    yearlyCoverageStatus.add(yearlyCoverageTargets.get("sync_status"));
							  			    yearlyCoverageId=new ArrayList<String>();
							  			    yearlyCoverageId.add(yearlyCoverageTargets.get("coverage_id"));
							  			    
							  			    midYearCoverageName=new ArrayList<String>();
							  			    midYearCoverageName.add(midYearCoverageTargets.get("coverage_name"));
							  			    midYearCoverageNumber=new ArrayList<String>();
							  			    midYearCoverageNumber.add(midYearCoverageTargets.get("coverage_number"));
							  			    midYearCoveragePeriod=new ArrayList<String>();
							  			    midYearCoveragePeriod.add(midYearCoverageTargets.get("coverage_period"));
							  			    midYearCoverageDueDate=new ArrayList<String>();
							  			    midYearCoverageDueDate.add(midYearCoverageTargets.get("due_date"));
							  			    midYearCoverageStatus=new ArrayList<String>();
							  			    midYearCoverageStatus.add(midYearCoverageTargets.get("sync_status"));
							  			    midYearCoverageId=new ArrayList<String>();
							  			    midYearCoverageId.add(midYearCoverageTargets.get("coverage_id"));
							  			    groupItems=new String[]{"Daily","Weekly","Monthly","Yearly","Mid-year"};
							  			    coverage_adapter=new CoverageListAdapter(mContext,dailyCoverageName ,
							  															dailyCoverageNumber,
							  															dailyCoveragePeriod,
							  															dailyCoverageDueDate,
							  															dailyCoverageStatus,
							  															dailyCoverageId,
							  							
							  															weeklyCoverageName,
							  															weeklyCoverageNumber,
							  															weeklyCoveragePeriod,
							  															weeklyCoverageDueDate,
							  															weeklyCoverageStatus,
							  															weeklyCoverageId,
							  							
							  															monthlyCoverageName,
							  															monthlyCoverageNumber,
							  															monthlyCoveragePeriod,
							  															monthlyCoverageDueDate,
							  															monthlyCoverageStatus,
							  															monthlyCoverageId,
							  							
							  															yearlyCoverageName,
							  															yearlyCoverageNumber,
							  															yearlyCoveragePeriod,
							  															yearlyCoverageDueDate,
							  															yearlyCoverageStatus,
							  															yearlyCoverageId,
							  							
							  															midYearCoverageName,
							  															midYearCoverageNumber,
							  															midYearCoveragePeriod,
							  															midYearCoverageDueDate,
							  															midYearCoverageStatus,
							  															midYearCoverageId,
							  															groupItems,
							  															listView_coverage);
							  			   
							  			    
							  			    coverage_adapter.notifyDataSetChanged();
							  		    	listView_coverage.setAdapter(coverage_adapter);	
							            }
							        });	
							    	 Toast.makeText(getActivity().getApplicationContext(), "Coverage target added successfully!",
									         Toast.LENGTH_LONG).show();
							    }else{
							    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
									         Toast.LENGTH_LONG).show();
							    }
							}
						});
			 				dialog.show();
			       }
			    });
			return rootView;
				   
			}
			
			 public static class DatePickerFragment extends DialogFragment
				implements DatePickerDialog.OnDateSetListener {

	 @Override
	 	public Dialog onCreateDialog(Bundle savedInstanceState) {
//Use the current date as the default date in the picker
final Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);

//Create a new instance of DatePickerDialog and return it
return new DatePickerDialog(getActivity(), this, year, month, day);
}

public void onDateSet(DatePicker view, int year, int month, int day) {
int month_value=month+1;
due_date=day+"-"+month_value+"-"+year;
dueDateValue.setText(due_date);
}
}

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, final long id) {
				selected_items=coverage_adapter.getChild(groupPosition, childPosition);
				selected_id=Long.parseLong(selected_items[5]);
				System.out.println(selected_items[0]+" "+selected_items[1]);
				String coverage_name=selected_items[0];
				String coverage_number=selected_items[1];
				String coverage_period=selected_items[2];
				String due_date=selected_items[3];
				String status=selected_items[4];
				Intent intent=new Intent(getActivity(),CoverageTargetsDetailActivity.class);
				intent.putExtra("coverage_id",selected_id);
				intent.putExtra("coverage_name",coverage_name);
				intent.putExtra("coverage_number",coverage_number);
				intent.putExtra("coverage_period", coverage_period);
				intent.putExtra("due_date", due_date);
				intent.putExtra("status", status);
				startActivity(intent);
				return true;
			}
			
	 }
	 
	 public static class LearningActivity extends Fragment implements OnItemClickListener{

			private Context mContext;															
			
			 private ArrayList<String> learningCategory;
			 private ArrayList<String> learningDescription;
			 private ArrayList<String> learningDueDate;
			 private ArrayList<String> learningStatus;
			 private ArrayList<String> learningTopic;
			 private ArrayList<String> learningId;
			 
			 private HashMap<String,String> learning;
			 private DbHelper db;
			 private LearningBaseAdapter learning_adapter;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			 View rootView;
			private TextView textStatus;

			private String selected_item;

			private String[] groupItem;

			private int[] imageId;
			int selected_position;

			private ListView learningList;

			private String[] selected_items;
			static String due_date ;
			private static TextView dueDateValue;
			private long selected_id;
			 public LearningActivity(){

      }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				rootView=inflater.inflate(R.layout.activity_learning,null,false);
			    mContext=getActivity().getApplicationContext();
			    db=new DbHelper(getActivity());
			    learningList=(ListView) rootView.findViewById(R.id.listView_learningCategory);
			    learning=db.getAllUnupdatedLearning(month_passed);
			   System.out.println(learning);
			    learningCategory=new ArrayList<String>();
			    learningCategory.add(learning.get("learning_category"));
			    learningDescription=new ArrayList<String>();
			    learningDescription.add(learning.get("learning_description"));
			    learningTopic=new ArrayList<String>();
			    learningTopic.add(learning.get("learning_topic"));
			    learningDueDate=new ArrayList<String>();
			    learningDueDate.add(learning.get("due_date"));
			    learningStatus=new ArrayList<String>();
			    learningStatus.add(learning.get("sync_status"));
			    learningId=new ArrayList<String>();
			    learningId.add(learning.get("learning_id"));
		   
			   learning_adapter=new LearningBaseAdapter(getActivity(),learningCategory ,
						learningDescription,
						learningDueDate,
						learningStatus,
						learningTopic,
						learningId);
			 learning_adapter.notifyDataSetChanged();
	    	learningList.setAdapter(learning_adapter);	
	    	learningList.setOnItemClickListener(this);
		   
	    														
			    Button b = (Button) rootView.findViewById(R.id.button_addLearning);
			   /* if(!month_passed.equalsIgnoreCase(current_month)){
					  b.setVisibility(View.GONE); 
				   }*/
			    b.setOnClickListener(new OnClickListener() {

			       private TextView dueDateValue;

				@Override
			       public void onClick(View v) {
			    	   final Dialog dialog = new Dialog(getActivity());
						dialog.setContentView(R.layout.learning_add_dialog);
						dialog.setTitle("Add Learning Target");
						final Spinner spinner_learningCatagory=(Spinner) dialog.findViewById(R.id.spinner_learningHeader);
						String[] items={"Family Planning"};
						ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
						spinner_learningCatagory.setAdapter(adapter);
						final Spinner spinner_learningCourse=(Spinner) dialog.findViewById(R.id.spinner_learningCourse);
						String[] items2={"Family Planning 101","Family Planning Counselling",
										"Family Planning for people living with HIV","Hormonal Contraceptives",
										"Postpartum Family Planning"};
						final Spinner spinner_learningDescription=(Spinner) dialog.findViewById(R.id.spinner_learningDescription);
						ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items2);
						spinner_learningCourse.setAdapter(adapter2);
						
						spinner_learningCourse.setOnItemSelectedListener(new OnItemSelectedListener(){
							
							@Override
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								switch (position){
								case 0:
									String[] items3={"Rationale for voluntary family planning",
											"Family Planning method considerations",
											"Short-acting contraceptive methods",
											"Long-acting contraceptive methods",
											"Special needs"};
							ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
							spinner_learningDescription.setAdapter(adapter3);
									break;
								case 1:
									String[] items4={"Family planning counselling",
											"Family planning counselling skills"};
							ArrayAdapter<String> adapter4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items4);
							spinner_learningDescription.setAdapter(adapter4);
									break;
								case 2:
									String[] items5={"Family Planning/Reproductive Health",
											"Family planning for people living with HIV",
											"Reproductive Health",
											"Helping Clients Make a Family Planning",
											"Family Planning in PMTCT Services"};
							ArrayAdapter<String> adapter5=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items5);
							spinner_learningDescription.setAdapter(adapter5);
									break;
								case 3:
									String[] items6={"Hormonal Contraceptives",
											"Oral contraceptives",
											"Emergency contraceptive pills",
											"Injectable contraceptives",
											"Implants",
											"Benefits and risks of hormonal contraceptives"};
							ArrayAdapter<String> adapter6=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items6);
							spinner_learningDescription.setAdapter(adapter6);
									break;
								case 4:
									String[] items7={"Rationale for postpartum family planning",
											"Contraceptive method considerations",
											"Service delivery:Clinical Considerations",
											"Service delivery:Integration and linkage"};
							ArrayAdapter<String> adapter7=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items7);
							spinner_learningDescription.setAdapter(adapter7);
									break;
								}
								
							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {
								
							}
							
						});
						
						Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddLearning);
						dialogButton.setText("Save");
						Button datepickerDialog=(Button) dialog.findViewById(R.id.button_setDateDialog);
						datepickerDialog.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								 DialogFragment newFragment = new DatePickerFragment();
								    newFragment.show(getFragmentManager(), "datePicker");
								
							}
							
						});
						dueDateValue=(TextView) dialog.findViewById(R.id.textView_dueDateValue);
						Button cancel=(Button) dialog.findViewById(R.id.button_cancel);
						cancel.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
							dialog.dismiss();
								
							}
							
						});
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								
								Spinner editText_learningDescription=(Spinner) dialog.findViewById(R.id.spinner_learningDescription);
								String learning_category=spinner_learningCatagory.getSelectedItem().toString();
								String learning_course=spinner_learningCourse.getSelectedItem().toString();
								String learning_description=editText_learningDescription.getSelectedItem().toString();
							    if(db.insertLearning(learning_category, learning_description,learning_course,month_passed,due_date, "new_record") ==true){
							    	getActivity().runOnUiThread(new Runnable() {
										@Override
							            public void run() {
											 learning=db.getAllUnupdatedDailyEvents(month_passed);
											   
											    learningCategory=new ArrayList<String>();
											    learningCategory.add(learning.get("learning_category"));
											    learningDescription=new ArrayList<String>();
											    learningDescription.add(learning.get("learning_description"));
											    learningTopic=new ArrayList<String>();
											    learningTopic.add(learning.get("learning_topic"));
											    learningDueDate=new ArrayList<String>();
											    learningDueDate.add(learning.get("due_date"));
											    learningStatus=new ArrayList<String>();
											    learningStatus.add(learning.get("sync_status"));
											    learningId=new ArrayList<String>();
											    learningId.add(learning.get("learning_id"));
										   
											   learning_adapter=new LearningBaseAdapter(getActivity(),learningCategory ,
														learningDescription,
														learningDueDate,
														learningStatus,
														learningTopic,
														learningId);
											 learning_adapter.notifyDataSetChanged();
									    	learningList.setAdapter(learning_adapter);	
							            }
							        });	
							    	 Toast.makeText(getActivity().getApplicationContext(), "Learning target added successfully!",
									         Toast.LENGTH_LONG).show();
							    }else{
							    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
									         Toast.LENGTH_LONG).show();
							    }
							}
						});
			 				dialog.show();
			       }
			    });
			return rootView;
				   
			 }			
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			selected_items=learning_adapter.getItem(position);
			selected_id=Long.parseLong(selected_items[5]);
			System.out.println(selected_items[0]+" "+selected_items[1]);
			String learning_category=selected_items[0];
			String learning_course=selected_items[1];
			String learing_topic=selected_items[2];
			String due_date=selected_items[3];
			String status=selected_items[4];
			Intent intent=new Intent(getActivity(),LearningTargetsDetailActivity.class);
			intent.putExtra("learning_id",selected_id);
			intent.putExtra("learning_category",learning_category);
			intent.putExtra("learning_course",learning_course);
			intent.putExtra("learning_topic", learing_topic);
			intent.putExtra("due_date", due_date);
			intent.putExtra("status", status);
			startActivity(intent);
			}
			public static class DatePickerFragment extends DialogFragment
				implements DatePickerDialog.OnDateSetListener {

 @Override
 	public Dialog onCreateDialog(Bundle savedInstanceState) {
//Use the current date as the default date in the picker
final Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);

//Create a new instance of DatePickerDialog and return it
return new DatePickerDialog(getActivity(), this, year, month, day);
}

public void onDateSet(DatePicker view, int year, int month, int day) {
int month_value=month+1;
due_date=day+"-"+month_value+"-"+year;
dueDateValue.setText(due_date);
}
}
	 }
	 
	 public static class OtherActivity extends Fragment implements OnChildClickListener{

			private Context mContext;															
			private ArrayList<String> dailyOtherName;
			 private ArrayList<String> dailyOtherNumber;
			 private ArrayList<String> dailyOtherPeriod;
			 private ArrayList<String> dailyOtherDueDate;
			 private ArrayList<String> dailyOtherStatus;
			 private ArrayList<String>  dailyOtherId;
			 
			 private ArrayList<String> weeklyOtherName;
			 private ArrayList<String> weeklyOtherNumber;
			 private ArrayList<String> weeklyOtherPeriod;
			 private ArrayList<String> weeklyOtherDueDate;
			 private ArrayList<String> weeklyOtherStatus;
			 private ArrayList<String> weeklyOtherId;
			 
			 private ArrayList<String> monthlyOtherName;
			 private ArrayList<String> monthlyOtherNumber;
			 private ArrayList<String> monthlyOtherPeriod;
			 private ArrayList<String> monthlyOtherDueDate;
			 private ArrayList<String> monthlyOtherStatus;
			 private ArrayList<String> monthlyOtherId;
			 
			 private ArrayList<String> yearlyOtherName;
			 private ArrayList<String> yearlyOtherNumber;
			 private ArrayList<String> yearlyOtherPeriod;
			 private ArrayList<String> yearlyOtherDueDate;
			 private ArrayList<String> yearlyOtherStatus;
			 private ArrayList<String> yearlyOtherId;
			 
			 private ArrayList<String> midYearOtherName;
			 private ArrayList<String> midYearOtherNumber;
			 private ArrayList<String> midYearOtherPeriod;
			 private ArrayList<String> midYearOtherDueDate;
			 private ArrayList<String> midYearOtherStatus;
			 private ArrayList<String> midYearOtherId;
			 
			 private HashMap<String,String> dailyOtherTargets;
			 private HashMap<String,String> weeklyOtherTargets;
			 private HashMap<String,String> monthlyOtherTargets;
			 private HashMap<String,String> yearlyOtherTargets;
			 private HashMap<String,String> midYearOtherTargets;
			 private String[] groupItems;
			private DbHelper db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private ExpandableListView listView_other;
			private TextView textStatus;
			private OtherBaseAdapter other_adapter;
			int selected_position;
			private long selected_id;
			static String due_date ;
			private static TextView dueDateValue;
			 public OtherActivity(){

   }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				 rootView=inflater.inflate(R.layout.activity_other,null,false);
			    mContext=getActivity().getApplicationContext();
			    db=new DbHelper(getActivity());
			    dailyOtherTargets=db.getAllUnupdatedDailyOther(month_passed);
			    weeklyOtherTargets=db.getAllUpupdatedWeeklyOther(month_passed);
			    monthlyOtherTargets=db.getAllUnupdatedMonthlyOthers(month_passed);
			    yearlyOtherTargets=db.getAllUnupdatedYearlyOther(month_passed);
			    midYearOtherTargets=db.getAllUnupdatedMidYearOther(month_passed);
			   
			    dailyOtherName=new ArrayList<String>();
			    dailyOtherName.add(dailyOtherTargets.get("other_name"));
			    dailyOtherNumber=new ArrayList<String>();
			    dailyOtherNumber.add(dailyOtherTargets.get("other_number"));
			    dailyOtherPeriod=new ArrayList<String>();
			    dailyOtherPeriod.add(dailyOtherTargets.get("other_period"));
			    dailyOtherDueDate=new ArrayList<String>();
			    dailyOtherDueDate.add(dailyOtherTargets.get("other_due_date"));
			    dailyOtherStatus=new ArrayList<String>();
			    dailyOtherStatus.add(dailyOtherTargets.get("sync_status"));
			    dailyOtherId=new ArrayList<String>();
			    dailyOtherId.add(dailyOtherTargets.get("other_id"));
			    
			    weeklyOtherName=new ArrayList<String>();
			    weeklyOtherName.add(weeklyOtherTargets.get("other_name"));
			    weeklyOtherNumber=new ArrayList<String>();
			    weeklyOtherNumber.add(weeklyOtherTargets.get("other_number"));
			    weeklyOtherPeriod=new ArrayList<String>();
			    weeklyOtherPeriod.add(weeklyOtherTargets.get("other_period"));
			    weeklyOtherDueDate=new ArrayList<String>();
			    weeklyOtherDueDate.add(weeklyOtherTargets.get("other_due_date"));
			    weeklyOtherStatus=new ArrayList<String>();
			    weeklyOtherStatus.add(weeklyOtherTargets.get("sync_status"));
			    weeklyOtherId=new ArrayList<String>();
			    weeklyOtherId.add(weeklyOtherTargets.get("other_id"));
			    
			    monthlyOtherName=new ArrayList<String>();
			    monthlyOtherName.add(monthlyOtherTargets.get("other_name"));
			    monthlyOtherNumber=new ArrayList<String>();
			    monthlyOtherNumber.add(monthlyOtherTargets.get("other_number"));
			    monthlyOtherPeriod=new ArrayList<String>();
			    monthlyOtherPeriod.add(monthlyOtherTargets.get("other_period"));
			    monthlyOtherDueDate=new ArrayList<String>();
			    monthlyOtherDueDate.add(monthlyOtherTargets.get("other_due_date"));
			    monthlyOtherStatus=new ArrayList<String>();
			    monthlyOtherStatus.add(monthlyOtherTargets.get("sync_status"));
			    monthlyOtherId=new ArrayList<String>();
			    monthlyOtherId.add(monthlyOtherTargets.get("other_id"));
			    
			    yearlyOtherName=new ArrayList<String>();
			    yearlyOtherName.add(yearlyOtherTargets.get("other_name"));
			    yearlyOtherNumber=new ArrayList<String>();
			    yearlyOtherNumber.add(yearlyOtherTargets.get("other_number"));
			    yearlyOtherPeriod=new ArrayList<String>();
			    yearlyOtherPeriod.add(yearlyOtherTargets.get("other_period"));
			    yearlyOtherDueDate=new ArrayList<String>();
			    yearlyOtherDueDate.add(yearlyOtherTargets.get("other_due_date"));
			    yearlyOtherStatus=new ArrayList<String>();
			    yearlyOtherStatus.add(yearlyOtherTargets.get("sync_status"));
			    yearlyOtherId=new ArrayList<String>();
			    yearlyOtherId.add(yearlyOtherTargets.get("other_id"));
			    
			    midYearOtherName=new ArrayList<String>();
			    midYearOtherName.add(midYearOtherTargets.get("other_name"));
			    midYearOtherNumber=new ArrayList<String>();
			    midYearOtherNumber.add(midYearOtherTargets.get("other_number"));
			    midYearOtherPeriod=new ArrayList<String>();
			    midYearOtherPeriod.add(midYearOtherTargets.get("other_period"));
			    midYearOtherDueDate=new ArrayList<String>();
			    midYearOtherDueDate.add(midYearOtherTargets.get("other_due_date"));
			    midYearOtherStatus=new ArrayList<String>();
			    midYearOtherStatus.add(midYearOtherTargets.get("sync_status"));
			    midYearOtherId=new ArrayList<String>();
			    midYearOtherId.add(midYearOtherTargets.get("other_id"));
			    groupItems=new String[]{"Daily","Weekly","Monthly","Annually","Mid-year"};
			    other_adapter=new OtherBaseAdapter(mContext,dailyOtherName ,
															dailyOtherNumber,
															dailyOtherPeriod,
															dailyOtherDueDate,
							dailyOtherStatus,
							 dailyOtherId,
							
							weeklyOtherName,
							weeklyOtherNumber,
							weeklyOtherPeriod,
							weeklyOtherDueDate,
							weeklyOtherStatus,
							weeklyOtherId,
							
							monthlyOtherName,
							monthlyOtherNumber,
							monthlyOtherPeriod,
							monthlyOtherDueDate,
							monthlyOtherStatus,
							monthlyOtherId,
							
							yearlyOtherName,
							yearlyOtherNumber,
							yearlyOtherPeriod,
							yearlyOtherDueDate,
							yearlyOtherStatus,
							yearlyOtherId,
							
							midYearOtherName,
							midYearOtherNumber,
							midYearOtherPeriod,
							midYearOtherDueDate,
							midYearOtherStatus,
							midYearOtherId,
							groupItems,
							listView_other);
			  
			    listView_other=(ExpandableListView) rootView.findViewById(R.id.expandableListView_other);
			    listView_other.setAdapter(other_adapter);
			    listView_other.setOnChildClickListener(this);
			   textStatus=(TextView) rootView.findViewById(R.id.textView_otherStatus);
			  
			   
			    Button b = (Button) rootView.findViewById(R.id.button_addOther);
			   /* if(!month_passed.equalsIgnoreCase(current_month)){
					  b.setVisibility(View.GONE); 
				   }*/
			    b.setOnClickListener(new OnClickListener() {

			       @Override
			       public void onClick(View v) {
			    	   final Dialog dialog = new Dialog(getActivity());
						dialog.setContentView(R.layout.event_add_dialog);
						dialog.setTitle("Add Other Target");
						final EditText editText_otherCategory=(EditText) dialog.findViewById(R.id.editText_dialogOtherName);
						final Spinner spinner_otherPeriod=(Spinner) dialog.findViewById(R.id.spinner_dialogOtherPeriod);
						String[] items={"Daily","Weekly","Monthly","Annually","Quarterly","Mid-year"};
						ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
						spinner_otherPeriod.setAdapter(adapter);
						final EditText editText_otherNumber=(EditText) dialog.findViewById(R.id.editText_dialogOtherNumber);
						Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddEvent);
						dialogButton.setText("Save");
						Button datepickerDialog=(Button) dialog.findViewById(R.id.button_setDateDialog);
						datepickerDialog.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								 DialogFragment newFragment = new DatePickerFragment();
								    newFragment.show(getFragmentManager(), "datePicker");
								
							}
							
						});
						dueDateValue=(TextView) dialog.findViewById(R.id.textView_dueDateValue);
						Button cancel=(Button) dialog.findViewById(R.id.button_cancel);
						cancel.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
							dialog.dismiss();
								
							}
							
						});
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							
								String other_category=editText_otherCategory.getText().toString();
								String other_number=editText_otherNumber.getText().toString();
								String other_period=spinner_otherPeriod.getSelectedItem().toString();
							    if(db.insertOther(other_category,other_number,other_period,month_passed,due_date,"new_record") ==true){
							    	getActivity().runOnUiThread(new Runnable() {
										@Override
							            public void run() {
											dailyOtherTargets=db.getAllUnupdatedDailyOther(month_passed);
										    weeklyOtherTargets=db.getAllUpupdatedWeeklyOther(month_passed);
										    monthlyOtherTargets=db.getAllUnupdatedMonthlyOthers(month_passed);
										    yearlyOtherTargets=db.getAllUnupdatedYearlyOther(month_passed);
										    midYearOtherTargets=db.getAllUnupdatedMidYearOther(month_passed);
										   
										    dailyOtherName=new ArrayList<String>();
										    dailyOtherName.add(dailyOtherTargets.get("other_name"));
										    dailyOtherNumber=new ArrayList<String>();
										    dailyOtherNumber.add(dailyOtherTargets.get("other_number"));
										    dailyOtherPeriod=new ArrayList<String>();
										    dailyOtherPeriod.add(dailyOtherTargets.get("other_period"));
										    dailyOtherDueDate=new ArrayList<String>();
										    dailyOtherDueDate.add(dailyOtherTargets.get("other_due_date"));
										    dailyOtherStatus=new ArrayList<String>();
										    dailyOtherStatus.add(dailyOtherTargets.get("sync_status"));
										    dailyOtherId=new ArrayList<String>();
										    dailyOtherId.add(dailyOtherTargets.get("other_id"));
										    
										    weeklyOtherName=new ArrayList<String>();
										    weeklyOtherName.add(weeklyOtherTargets.get("other_name"));
										    weeklyOtherNumber=new ArrayList<String>();
										    weeklyOtherNumber.add(weeklyOtherTargets.get("other_number"));
										    weeklyOtherPeriod=new ArrayList<String>();
										    weeklyOtherPeriod.add(weeklyOtherTargets.get("other_period"));
										    weeklyOtherDueDate=new ArrayList<String>();
										    weeklyOtherDueDate.add(weeklyOtherTargets.get("other_due_date"));
										    weeklyOtherStatus=new ArrayList<String>();
										    weeklyOtherStatus.add(weeklyOtherTargets.get("sync_status"));
										    weeklyOtherId=new ArrayList<String>();
										    weeklyOtherId.add(weeklyOtherTargets.get("other_id"));
										    
										    monthlyOtherName=new ArrayList<String>();
										    monthlyOtherName.add(monthlyOtherTargets.get("other_name"));
										    monthlyOtherNumber=new ArrayList<String>();
										    monthlyOtherNumber.add(monthlyOtherTargets.get("other_number"));
										    monthlyOtherPeriod=new ArrayList<String>();
										    monthlyOtherPeriod.add(monthlyOtherTargets.get("other_period"));
										    monthlyOtherDueDate=new ArrayList<String>();
										    monthlyOtherDueDate.add(monthlyOtherTargets.get("other_due_date"));
										    monthlyOtherStatus=new ArrayList<String>();
										    monthlyOtherStatus.add(monthlyOtherTargets.get("sync_status"));
										    monthlyOtherId=new ArrayList<String>();
										    monthlyOtherId.add(monthlyOtherTargets.get("other_id"));
										    
										    yearlyOtherName=new ArrayList<String>();
										    yearlyOtherName.add(yearlyOtherTargets.get("other_name"));
										    yearlyOtherNumber=new ArrayList<String>();
										    yearlyOtherNumber.add(yearlyOtherTargets.get("other_number"));
										    yearlyOtherPeriod=new ArrayList<String>();
										    yearlyOtherPeriod.add(yearlyOtherTargets.get("other_period"));
										    yearlyOtherDueDate=new ArrayList<String>();
										    yearlyOtherDueDate.add(yearlyOtherTargets.get("other_due_date"));
										    yearlyOtherStatus=new ArrayList<String>();
										    yearlyOtherStatus.add(yearlyOtherTargets.get("sync_status"));
										    yearlyOtherId=new ArrayList<String>();
										    yearlyOtherId.add(yearlyOtherTargets.get("other_id"));
										    
										    midYearOtherName=new ArrayList<String>();
										    midYearOtherName.add(midYearOtherTargets.get("other_name"));
										    midYearOtherNumber=new ArrayList<String>();
										    midYearOtherNumber.add(midYearOtherTargets.get("other_number"));
										    midYearOtherPeriod=new ArrayList<String>();
										    midYearOtherPeriod.add(midYearOtherTargets.get("other_period"));
										    midYearOtherDueDate=new ArrayList<String>();
										    midYearOtherDueDate.add(midYearOtherTargets.get("other_due_date"));
										    midYearOtherStatus=new ArrayList<String>();
										    midYearOtherStatus.add(midYearOtherTargets.get("sync_status"));
										    midYearOtherId=new ArrayList<String>();
										    midYearOtherId.add(midYearOtherTargets.get("other_id"));
										    groupItems=new String[]{"Daily","Weekly","Monthly","Annually","Mid-year"};
										    other_adapter=new OtherBaseAdapter(mContext,dailyOtherName ,
																						dailyOtherNumber,
																						dailyOtherPeriod,
																						dailyOtherDueDate,
														dailyOtherStatus,
														 dailyOtherId,
														
														weeklyOtherName,
														weeklyOtherNumber,
														weeklyOtherPeriod,
														weeklyOtherDueDate,
														weeklyOtherStatus,
														weeklyOtherId,
														
														monthlyOtherName,
														monthlyOtherNumber,
														monthlyOtherPeriod,
														monthlyOtherDueDate,
														monthlyOtherStatus,
														monthlyOtherId,
														
														yearlyOtherName,
														yearlyOtherNumber,
														yearlyOtherPeriod,
														yearlyOtherDueDate,
														yearlyOtherStatus,
														yearlyOtherId,
														
														midYearOtherName,
														midYearOtherNumber,
														midYearOtherPeriod,
														midYearOtherDueDate,
														midYearOtherStatus,
														midYearOtherId,
														groupItems,
														listView_other);
										  
										    listView_other=(ExpandableListView) rootView.findViewById(R.id.expandableListView_other);
										    listView_other.setAdapter(other_adapter);
							            }
							        });	
							    	 Toast.makeText(getActivity().getApplicationContext(), "Added target successfully!",
									         Toast.LENGTH_LONG).show();
							    }else{
							    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
									         Toast.LENGTH_LONG).show();
							    }
							}
						});
			 				dialog.show();
			       }
			    });
			return rootView;
				   
			}
			 public static class DatePickerFragment extends DialogFragment
				implements DatePickerDialog.OnDateSetListener {

@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
//Use the current date as the default date in the picker
final Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);

//Create a new instance of DatePickerDialog and return it
return new DatePickerDialog(getActivity(), this, year, month, day);
}

public void onDateSet(DatePicker view, int year, int month, int day) {
int month_value=month+1;
due_date=day+"-"+month_value+"-"+year;
dueDateValue.setText(due_date);
}
}
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				String[] selected_items=other_adapter.getChild(groupPosition, childPosition);
				selected_id=Long.parseLong(selected_items[5]);
				System.out.println(selected_items[0]+" "+selected_items[1]);
				String other_name=selected_items[0];
				String other_number=selected_items[1];
				String other_period=selected_items[2];
				String due_date=selected_items[3];
				String status=selected_items[4];
				Intent intent=new Intent(getActivity(),OtherTargetsDetailActivity.class);
				intent.putExtra("other_id",selected_id);
				intent.putExtra("other_name",other_name);
				intent.putExtra("other_number",other_number);
				intent.putExtra("other_period", other_period);
				intent.putExtra("due_date", due_date);
				intent.putExtra("status", status);
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
