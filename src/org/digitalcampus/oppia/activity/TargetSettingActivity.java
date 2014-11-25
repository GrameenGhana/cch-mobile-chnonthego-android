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
import org.grameenfoundation.adapters.EventBaseAdapter;
import org.grameenfoundation.adapters.LearningBaseAdapter;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
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
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;


public class TargetSettingActivity extends SherlockFragmentActivity implements ActionBar.TabListener, OnSharedPreferenceChangeListener{
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
	private static String today;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_new_event_planner);
	    
	   // final PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_header);
       // pagerTabStrip.setDrawFullUnderline(true);
       // pagerTabStrip.setTabIndicatorColor(Color.rgb(83,171,32));
        dbh = new DbHelper(TargetSettingActivity.this);
      
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
		
	 public static class EventsActivity extends Fragment {

			private Context mContext;															
			 
			private DbHelper db;
			public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private String[] selected_items;
			int selected_position;
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
				 rootView=inflater.inflate(R.layout.event_set_dialog,null,false);
			    mContext=getActivity().getApplicationContext();
			    //TypefaceUtil.overrideFont(mContext, "SERIF", "fonts/Roboto-Thin.ttf");
			    db=new DbHelper(getActivity());	String[] items={"Daily","Weekly","Monthly","Quarterly","Mid-year","Annually"};
				final Spinner spinner_event_period=(Spinner) rootView.findViewById(R.id.spinner_dialogEventPeriod);
				final Spinner spinner_event_name=(Spinner) rootView.findViewById(R.id.spinner_eventName);
				
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
				final EditText editText_event_period=(EditText) rootView.findViewById(R.id.editText_dialogEventPeriodNumber);
				dueDateValue=(TextView) rootView.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) rootView.findViewById(R.id.textView_startDate);
				ImageButton datepickerDialog=(ImageButton) rootView.findViewById(R.id.imageButton_dueDate);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment();
						    newFragment.show(getFragmentManager(), "datePicker");
						
					}
					
				});
				ImageButton datepickerDialog2=(ImageButton) rootView.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment2();
						    newFragment.show(getFragmentManager(), "datePicker2");
						
					}
					
				});
				Button cancel=(Button) rootView.findViewById(R.id.button_cancel);
				cancel.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						editText_event_period.setText(" ");
						dueDateValue.setText(" ");
						startDateValue.setText(" ");
					}
					
				});
				Button dialogButton = (Button) rootView.findViewById(R.id.button_dialogSetEVent);
				dialogButton.setText("Save");
				
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
					
						
						String event_period=spinner_event_period.getSelectedItem().toString();
						String event_name=spinner_event_name.getSelectedItem().toString();
						String event_period_number=editText_event_period.getText().toString();
						String duration = null;
						if(isToday(due_date_to_compare)){
							duration="Today";
							System.out.println(duration);
						}else if(isTomorrow(due_date_to_compare)){
							duration="Tomorrow";
							System.out.println(duration);
						}else if(isThisWeek(due_date_to_compare)){
							duration="This week";
							System.out.println(duration);
						}else if(isThisMonth(due_date_to_compare)){
							duration="This month";
							System.out.println(duration);
						}else if(isThisQuarter(due_date_to_compare)){
							duration="This quarter";
							System.out.println(duration);
						}
						Calendar c = Calendar.getInstance();
				        int month=c.get(Calendar.MONTH)+1;
				        int day=c.get(Calendar.DAY_OF_WEEK);
				        int year=c.get(Calendar.YEAR);
				      	String today=day+"-"+month+"-"+year;
					    if(db.insertEventSet(event_name, event_period, event_period_number, duration,start_date,due_date,0,Integer.valueOf(event_period_number),"new_record") !=0){
					    	editText_event_period.setText(" ");
					    	 Toast.makeText(getActivity().getApplicationContext(), "Event target set successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
			    button_show=(Button) rootView.findViewById(R.id.button_show);
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
			    button_show.setOnClickListener(new OnClickListener(){

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
			  
			return rootView;
				   
			}
			 
			 public boolean isToday(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
		    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
		    	       				.toString().equals(today)) ? true : false;
		    	}
		    	
		    	public boolean isTomorrow(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 1);
		    	    	String tomorrow = new SimpleDateFormat("MM/dd/yyyy").format(new Date(c.getTimeInMillis()));
		    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
		    	       				.toString().equals(tomorrow)) ? true : false;
		    	}
		    	    
		    	public boolean isThisWeek(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 7);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
		    	}
		    	public boolean isThisMonth(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 30);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
		    	}
		    	public boolean isThisQuarter(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 90);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
		    	}
		    	
		    	public boolean isMidYear(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 120);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
		    	}
		    	public boolean isThisYear(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 365);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
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
							Calendar calendar = Calendar.getInstance();
							calendar.set(view.getYear(), view.getMonth(), view.getDayOfMonth());
							 due_date_to_compare= calendar.getTimeInMillis();
				 }
			 }
			 
			 public static class DatePickerFragment2 extends DialogFragment
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
		 start_date=day+"-"+month_value+"-"+year;
		 startDateValue.setText(start_date);
	 }
}
		
			
	 }
	 public static class CoverageActivity extends Fragment{

			private Context mContext;															
			private DbHelper db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private RadioGroup category_options;
			private String[] items3;
			int selected_position;
			protected RadioButton category_people;
			private Button button_show;
			protected String coverage_detail;
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
				 rootView=inflater.inflate(R.layout.coverage_add_dialog,null,false);
			    mContext=getActivity().getApplicationContext();
			    db=new DbHelper(getActivity());
			    final EditText editText_coverageNumber=(EditText) rootView.findViewById(R.id.editText_dialogCoverageNumber);
			    button_show=(Button) rootView.findViewById(R.id.button_show);
				ImageButton datepickerDialog=(ImageButton) rootView.findViewById(R.id.imageButton_dueDate);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment();
						    newFragment.show(getFragmentManager(), "datePicker");
						
					}
					
				});
				ImageButton datepickerDialog2=(ImageButton) rootView.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment2();
						    newFragment.show(getFragmentManager(), "datePicker2");
						
					}
					
				});
				Button cancel=(Button) rootView.findViewById(R.id.button_cancel);
				cancel.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						editText_coverageNumber.setText(" ");
						dueDateValue.setText(" ");
						startDateValue.setText(" ");
					}
					
				});
				
				dueDateValue=(TextView) rootView.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) rootView.findViewById(R.id.textView_startDate);
				final Spinner spinner_coverageName=(Spinner) rootView.findViewById(R.id.spinner_dialogCoverageName);
				final Spinner spinner_coverageDetails=(Spinner) rootView.findViewById(R.id.spinner_dialogCoverageDetail);
				
				 category_options=(RadioGroup) rootView.findViewById(R.id.radioGroup_category);
				  category_options.check(R.id.radio_people);
				  category_people=(RadioButton) rootView.findViewById(R.id.radio_people);
				  category_people.setChecked(true);
				  items3=new String[]{"Family Planning","Age Groups","School Health"};
				  spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

						@Override
						public void onItemSelected(
								AdapterView<?> parent, View view,
								int position, long id) {
						switch(position){
						case 0:
							String[] detail_items1={"New Acceptors","Continuing Acceptors"};
							ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items1);
							spinner_coverageDetails.setAdapter(details_adapter);
							coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
							break;
						case 1:
							String[] detail_items2={"0 to 11 months","12 to 23 months","24 to 59 months"};
							ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items2);
							spinner_coverageDetails.setAdapter(details_adapter2);
							coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
							break;
						case 2:
							String[] detail_items3={"# of schools visited","# of schools with 3+ health talks","# examined ­ Pre­school","# examined - P1" ,"# examined - P3", "# examined - JHS 1"};
							ArrayAdapter<String> details_adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items3);
							spinner_coverageDetails.setAdapter(details_adapter3);
							coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
							break;
						}
							
						}

						@Override
						public void onNothingSelected(
								AdapterView<?> parent) {
							// TODO Auto-generated method stub
							
						}
						
					});
					ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
					spinner_coverageName.setAdapter(adapter3);
				    category_options.setOnCheckedChangeListener(new OnCheckedChangeListener(){
						public void onCheckedChanged(
								RadioGroup buttonView,
								int isChecked) {
							if (isChecked == R.id.radio_people) {
								items3=new String[]{"Family Planning","Age Groups","School Health"};
								ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
								spinner_coverageName.setAdapter(adapter3);
								spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

									@Override
									public void onItemSelected(
											AdapterView<?> parent, View view,
											int position, long id) {
									switch(position){
									case 0:
										String[] detail_items1={"New Acceptors","Continuing Acceptors"};
										ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items1);
										spinner_coverageDetails.setAdapter(details_adapter);
										coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										break;
									case 1:
										String[] detail_items2={"0 to 11 months","12 to 23 months","24 to 59 months"};
										ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items2);
										spinner_coverageDetails.setAdapter(details_adapter2);
										coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										break;
									case 2:
										String[] detail_items3={"Number of schools visited","Number of schools with 3+ health talks","Number examined ­ Pre­school, P1, P3, JHS 1"};
										ArrayAdapter<String> details_adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items3);
										spinner_coverageDetails.setAdapter(details_adapter3);
										coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										break;
									}
										
									}

									@Override
									public void onNothingSelected(
											AdapterView<?> parent) {
									}
									
								});
								
							} else if (isChecked == R.id.radio_immunization) {
								items3=new String[]{"BCG"," Penta",
										"OPV","ROTA",
										"PCV","Measles Rubella","Vitamin A","TT pregnant","TT non-pregnant","Yellow Fever"};
								ArrayAdapter<String> adapter4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
								spinner_coverageName.setAdapter(adapter4);
								spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

									@Override
									public void onItemSelected(
											AdapterView<?> parent, View view,
											int position, long id) {
										switch(position){
										case 0:
											String[] detail_items1={"BCG"};
											ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items1);
											spinner_coverageDetails.setAdapter(details_adapter);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 1:
											String[] detail_items2={"Penta 1","Penta 2","Penta 3"};
											ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items2);
											spinner_coverageDetails.setAdapter(details_adapter2);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 2:
											String[] detail_items3={"OPV 1","OPV 2","OPV 3"};
											ArrayAdapter<String> details_adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items3);
											spinner_coverageDetails.setAdapter(details_adapter3);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 3:
											String[] detail_items4={"ROTA 1","ROTA 2"};
											ArrayAdapter<String> details_adapter4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items4);
											spinner_coverageDetails.setAdapter(details_adapter4);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 4:
											String[] detail_itemsPCV={"PCV 1","PCV 2","PCV 3"};
											ArrayAdapter<String> details_adapterPCV=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_itemsPCV);
											spinner_coverageDetails.setAdapter(details_adapterPCV);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 5:
											String[] detail_items5={"Measles Rubella @9mnths","Measles 2"};
											ArrayAdapter<String> details_adapter5=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items5);
											spinner_coverageDetails.setAdapter(details_adapter5);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 6:
											String[] detail_items6={"100,000 IU","200,000 IU","Postpartum"};
											ArrayAdapter<String> details_adapter6=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items6);
											spinner_coverageDetails.setAdapter(details_adapter6);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 7:
											String[] detail_items7={"TT 1","TT 2","TT 3","TT 4","TT 5"};
											ArrayAdapter<String> details_adapter7=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items7);
											spinner_coverageDetails.setAdapter(details_adapter7);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 8:
											String[] detail_items8={"TT 1","TT 2","TT 3","TT 4","TT 5"};
											ArrayAdapter<String> details_adapter8=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items8);
											spinner_coverageDetails.setAdapter(details_adapter8);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 9:
											String[] detail_items9={"Yellow Fever"};
											ArrayAdapter<String> details_adapter9=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items9);
											spinner_coverageDetails.setAdapter(details_adapter9);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										}
										
									}

									@Override
									public void onNothingSelected(
											AdapterView<?> parent) {
										// TODO Auto-generated method stub
										
									}
									
								});
								
							}
						}
				    });
				    String[] items2={"Daily","Weekly","Monthly","Quarterly","Mid-year","Annually"};
				final Spinner spinner_coveragePeriod=(Spinner) rootView.findViewById(R.id.spinner_coveragePeriod);
				ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items2);
				spinner_coveragePeriod.setAdapter(adapter2);
				Button dialogButton = (Button) rootView.findViewById(R.id.button_dialogAddCoverage);
				dialogButton.setText("Save");
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String coverage_name=spinner_coverageName.getSelectedItem().toString();
						String coverage_period=spinner_coveragePeriod.getSelectedItem().toString();
						String coverage_number=editText_coverageNumber.getText().toString();
						coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
						String duration = null;
						if(isToday(due_date_to_compare)){
							duration="Today";
						}else if(isTomorrow(due_date_to_compare)){
							duration="Tomorrow";
						}else if(isThisWeek(due_date_to_compare)){
							duration="This week";
						}else if(isThisMonth(due_date_to_compare)){
							duration="This month";
						}else if(isThisQuarter(due_date_to_compare)){
							duration="This quarter";
						}
						Calendar c = Calendar.getInstance();
				        int month=c.get(Calendar.MONTH)+1;
				        int day=c.get(Calendar.DAY_OF_WEEK);
				        int year=c.get(Calendar.YEAR);
				      	String today=day+"-"+month+"-"+year;
				    if(db.insertCoverageSet(coverage_name, coverage_detail, coverage_period, coverage_number, duration,start_date,due_date,0,Integer.valueOf(coverage_number),"new_record") !=0){
				    	editText_coverageNumber.setText(" ");
					    	 Toast.makeText(getActivity().getApplicationContext(), "Coverage target added successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
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
			    button_show.setOnClickListener(new OnClickListener(){

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
		    	
			return rootView;
				   
			}
			 public boolean isToday(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
		    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
		    	       				.toString().equals(today)) ? true : false;
		    	}
		    	
		    	public boolean isTomorrow(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 1);
		    	    	String tomorrow = new SimpleDateFormat("MM/dd/yyyy").format(new Date(c.getTimeInMillis()));
		    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
		    	       				.toString().equals(tomorrow)) ? true : false;
		    	}
		    	    
		    	public boolean isThisWeek(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 7);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
		    	}
		    	public boolean isThisMonth(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 30);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
		    	}
		    	public boolean isThisQuarter(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 90);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
		    	}
		    	
		    	public boolean isMidYear(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 120);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
		    	}
		    	public boolean isThisYear(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 365);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
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
		    	 public static class DatePickerFragment2 extends DialogFragment
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
		    start_date=day+"-"+month_value+"-"+year;
		    startDateValue.setText(start_date);
		    }
		    }

			
	 }
	 
	 public static class LearningActivity extends Fragment {

			private Context mContext;															
			
			 private DbHelper db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			 View rootView;

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
				rootView=inflater.inflate(R.layout.learning_add_dialog,null,false);
			    mContext=getActivity().getApplicationContext();
			    db=new DbHelper(getActivity());
			    final Spinner editText_learningDescription=(Spinner) rootView.findViewById(R.id.spinner_learningDescription);
			    final Spinner spinner_learningCatagory=(Spinner) rootView.findViewById(R.id.spinner_learningHeader);
				String[] items={"Family Planning","Maternal and Child Health"};
				final Spinner spinner_learningDescription=(Spinner) rootView.findViewById(R.id.spinner_learningDescription);
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
				spinner_learningCatagory.setAdapter(adapter);
				final Spinner spinner_learningCourse=(Spinner) rootView.findViewById(R.id.spinner_learningCourse);
				
				
				spinner_learningCatagory.setOnItemSelectedListener(new OnItemSelectedListener(){
					
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						switch(position){
						case 0:
							String[] items2={"Family Planning 101","Family Planning Counselling",
									"Family Planning for people living with HIV","Hormonal Contraceptives",
									"Postpartum Family Planning"};
							ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items2);
							spinner_learningCourse.setAdapter(adapter2);
							break;
						case 1:
							String[] items2_1={"Essential Newborn care","Antenatal Care",
												"Diarrhoea Disease","Emergency obstetrics",
												"Malaria in Pregnancy","Postpartum Care",
												"Course: Preventing Postpartum Hemorrhage"};
							ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items2_1);
							spinner_learningCourse.setAdapter(adapter3);
						
							break;
						}
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
					
					
				});
				final Spinner spinner_period=(Spinner) rootView.findViewById(R.id.spinner_period);
				final String[] items_period={"Daily","Weekly",
						"Monthly","Quarterly",
						"Mid-year","Annually"};
				ArrayAdapter<String> adapter_period=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items_period);
				spinner_period.setAdapter(adapter_period);
				
				spinner_learningCourse.setOnItemSelectedListener(new OnItemSelectedListener(){
					
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						switch (position){
						case 0:
							String[] items;
							if(spinner_learningCourse.getSelectedItem().equals("Essential Newborn care")){
							items=new String[]{"Newborn mortality",
									"Care during labor and birth",
									"Newborn care following birth",
									"Newborn care following birth: Later",
									"Infant feeding",
									"Household to hospital continuum"};
							/*
							}else if(spinner_learningCourse.getSelectedItem().equals("Antenatal care")){
								items=new String[]{"Significance of Antenatal Care",
										"Goal and Principles of Antenatal Care",
										"Elements of Focused (Goal-directed) Assessment",
										"Screening to Detect, Not Predict, Problems",
										"Preventive Measures",
										"Malaria in Pregnancy",
										"HIV in Pregnancy","Syphilis in Pregnancy",
										"Program Considerations"};
							}else if(spinner_learningCourse.getSelectedItem().equals("Diarrhoea Disease")){
									items=new String[]{"Etiology and Epidemiology",
											"Clinical Assessment and Classification",
											"Treatment",
											"Prevention"};
							}else if(spinner_learningCourse.getSelectedItem().equals("Emergency obstetrics")){
								items=new String[]{"Background and Definitions",
										"Basic and Comprehensive EmONC",
										"Implementation of EmONC Services"};
							}else if(spinner_learningCourse.getSelectedItem().equals("Malaria in Pregnancy")){
									items=new String[]{"Why Is Malaria in Pregnancy (MIP) Important?",
											"MIP: Strategic Framework, Main Interventions",
											"Insecticide-treated Nets, Case Management",
											"Partnerships for MIP, MIP Readiness",
											"Case Study: Frequent Problems/Practical Solutions"};
							}else if(spinner_learningCourse.getSelectedItem().equals("Postpartum Care")){
										items=new String[]{"Postpartum Care: Overview",
												"Field Realities",
												"Preventing Postpartum Mortality and Morbidity One",
												"Preventing Postpartum Mortality and Morbidity Two",
												"Case Study: Frequent Problems/Practical Solutions"};
							}else if(spinner_learningCourse.getSelectedItem().equals("Preventing Postpartum Hemorrhage")){
											items=new String[]{"Postpartum Hemorrhage and Maternal Mortality",
													"Causes of Postpartum Hemorrhage",
													"Prevention of Postpartum Hemorrhage One",
													"Prevention of Postpartum Hemorrhage Two: AMTSL"};	
							*/
						}else {
							items=new String[]{"Rationale for voluntary family planning",
									"Family Planning method considerations",
									"Short-acting contraceptive methods",
									"Long-acting contraceptive methods",
									"Special needs"};
							}
							ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
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
				
				Button dialogButton = (Button) rootView.findViewById(R.id.button_dialogAddLearning);
				dialogButton.setText("Save");
				ImageButton datepickerDialog=(ImageButton) rootView.findViewById(R.id.imageButton_dueDate);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment();
						    newFragment.show(getFragmentManager(), "datePicker");
						
					}
					
				});
				
				ImageButton datepickerDialog2=(ImageButton) rootView.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment2();
						    newFragment.show(getFragmentManager(), "datePicker2");
						
					}
					
				});
				
				Button cancel=(Button) rootView.findViewById(R.id.button_cancel);
				cancel.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						
					}
					
				});
				dueDateValueLearning=(TextView) rootView.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) rootView.findViewById(R.id.textView_startDate);
				dialogButton.setOnClickListener(new OnClickListener() {
					

					@Override
					public void onClick(View v) {
						
						String learning_category=spinner_learningCatagory.getSelectedItem().toString();
						String learning_course=spinner_learningCourse.getSelectedItem().toString();
						String learning_period=spinner_period.getSelectedItem().toString();
						String learning_description=editText_learningDescription.getSelectedItem().toString();
						String duration=null;
						if(isToday(due_date_to_compare)){
							duration="Today";
						}else if(isTomorrow(due_date_to_compare)){
							duration="Tomorrow";
						}else if(isThisWeek(due_date_to_compare)){
							duration="This week";
						}else if(isThisMonth(due_date_to_compare)){
							duration="This month";
						}else if(isThisQuarter(due_date_to_compare)){
							duration="This quarter";
						}
						Calendar c = Calendar.getInstance();
				        int month=c.get(Calendar.MONTH)+1;
				        int day=c.get(Calendar.DAY_OF_WEEK);
				        int year=c.get(Calendar.YEAR);
				      	String today=day+"-"+month+"-"+year;
					    if(db.insertLearning(learning_category, learning_description,learning_course,duration,learning_period,start_date,due_date, "new_record")!=0){
					    	 Toast.makeText(getActivity().getApplicationContext(), "Learning target added successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
	    	button_show=(Button) rootView.findViewById(R.id.button_show);
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
		    button_show.setOnClickListener(new OnClickListener(){

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
			return rootView;
				   
			 }			
				
				 public boolean isToday(long dueDate)
			    	{
			    			long milliSeconds = dueDate;
			    	    	String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
			    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
			    	       				.toString().equals(today)) ? true : false;
			    	}
			    	
			    	public boolean isTomorrow(long dueDate)
			    	{
			    			long milliSeconds = dueDate;
			    	    	Calendar c = Calendar.getInstance();
			    	    	c.add(Calendar.DATE, 1);
			    	    	String tomorrow = new SimpleDateFormat("MM/dd/yyyy").format(new Date(c.getTimeInMillis()));
			    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
			    	       				.toString().equals(tomorrow)) ? true : false;
			    	}
			    	    
			    	public boolean isThisWeek(long dueDate)
			    	{
			    			long milliSeconds = dueDate;
			    	    	Calendar c = Calendar.getInstance();
			    	    	c.add(Calendar.DATE, 7);
			    	    	String thisWeek = new SimpleDateFormat("MM/dd/yyyy").format(new Date(c.getTimeInMillis()));
			    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
			    	       				.toString().equals(thisWeek)) ? true : false;
			    	    	
			    	       // return (milliSeconds >= c.getTimeInMillis()) ? true : false;
			    	}
			    	public boolean isThisMonth(long dueDate)
			    	{
			    			long milliSeconds = dueDate;
			    	    	Calendar c = Calendar.getInstance();
			    	    	c.add(Calendar.DATE, 30);
			    	    	String thisMonth = new SimpleDateFormat("MM/dd/yyyy").format(new Date(c.getTimeInMillis()));
			    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
			    	       				.toString().equals(thisMonth)) ? true : false;
			    	       // return (milliSeconds >= c.getTimeInMillis()) ? true : false;
			    	}
			    	public boolean isThisQuarter(long dueDate)
			    	{
			    			long milliSeconds = dueDate;
			    	    	Calendar c = Calendar.getInstance();
			    	    	c.add(Calendar.DATE, 90);
			    	    	String thisQuarter = new SimpleDateFormat("MM/dd/yyyy").format(new Date(c.getTimeInMillis()));
			    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
			    	       				.toString().equals(thisQuarter)) ? true : false;
			    	        //return (milliSeconds >= c.getTimeInMillis()) ? true : false;
			    	}
			    	
			    	public boolean isMidYear(long dueDate)
			    	{
			    			long milliSeconds = dueDate;
			    	    	Calendar c = Calendar.getInstance();
			    	    	c.add(Calendar.DATE, 120);
			    	    	String midYear = new SimpleDateFormat("MM/dd/yyyy").format(new Date(c.getTimeInMillis()));
			    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
			    	       				.toString().equals(midYear)) ? true : false;
			    	        //return (milliSeconds >= c.getTimeInMillis()) ? true : false;
			    	}
			    	public boolean isThisYear(long dueDate)
			    	{
			    			long milliSeconds = dueDate;
			    	    	Calendar c = Calendar.getInstance();
			    	    	c.add(Calendar.DATE, 365);
			    	    	String thisYear = new SimpleDateFormat("MM/dd/yyyy").format(new Date(c.getTimeInMillis()));
			    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
			    	       				.toString().equals(thisYear)) ? true : false;
			    	        //return (milliSeconds >= c.getTimeInMillis()) ? true : false;
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
					dueDateValueLearning.setText(due_date);
					Calendar calendar = Calendar.getInstance();
					calendar.set(view.getYear(), view.getMonth(), view.getDayOfMonth());
					 due_date_to_compare= calendar.getTimeInMillis();
				}
			}
			
			public static class DatePickerFragment2 extends DialogFragment
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
						start_date=day+"-"+month_value+"-"+year;
						startDateValue.setText(start_date);
					}
			}

		
	 }
	 
	 public static class OtherActivity extends Fragment{

			private Context mContext;															
			private DbHelper db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
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
				 rootView=inflater.inflate(R.layout.event_add_dialog,null,false);
			    mContext=getActivity().getApplicationContext();
			    db=new DbHelper(getActivity());
			   button_show=(Button) rootView.findViewById(R.id.button_show);
			   final EditText editText_otherCategory=(EditText) rootView.findViewById(R.id.editText_dialogOtherName);
				final Spinner spinner_otherPeriod=(Spinner) rootView.findViewById(R.id.spinner_dialogOtherPeriod);
				String[] items={"Daily","Weekly","Monthly","Quarterly","Mid-year","Annually"};
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
				spinner_otherPeriod.setAdapter(adapter);
				final EditText editText_otherNumber=(EditText) rootView.findViewById(R.id.editText_dialogOtherNumber);
				Button dialogButton = (Button) rootView.findViewById(R.id.button_dialogAddEvent);
				dialogButton.setText("Save");
				ImageButton datepickerDialog=(ImageButton) rootView.findViewById(R.id.imageButton_dueDate);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment();
						    newFragment.show(getFragmentManager(), "datePicker");
						
					}
					
				});
				
				ImageButton datepickerDialog2=(ImageButton) rootView.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment2();
						    newFragment.show(getFragmentManager(), "datePicker2");
						
					}
					
				});
				dueDateValue=(TextView) rootView.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) rootView.findViewById(R.id.textView_startDate);
				Button cancel=(Button) rootView.findViewById(R.id.button_cancel);
				cancel.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						
					}
					
				});
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String other_category=editText_otherCategory.getText().toString();
						String other_number=editText_otherNumber.getText().toString();
						String other_period=spinner_otherPeriod.getSelectedItem().toString();
						String duration=null;
						if(isToday(due_date_to_compare)){
							duration="Today";
						}else if(isTomorrow(due_date_to_compare)){
							duration="Tomorrow";
						}else if(isThisWeek(due_date_to_compare)){
							duration="This week";
						}else if(isThisMonth(due_date_to_compare)){
							duration="This month";
						}else if(isThisQuarter(due_date_to_compare)){
							duration="This quarter";
						}
						Calendar c = Calendar.getInstance();
				        int month=c.get(Calendar.MONTH)+1;
				        int day=c.get(Calendar.DAY_OF_WEEK);
				        int year=c.get(Calendar.YEAR);
				      	String today=day+"-"+month+"-"+year;
					    if(db.insertOther(other_category,other_number,other_period,duration,start_date,due_date,0,Integer.valueOf(other_number),"new_record")!=0){
					    	editText_otherNumber.setText(" ");
					    	editText_otherCategory.setText(" ");
					    	 Toast.makeText(getActivity().getApplicationContext(), "Added target successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
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
			    button_show.setOnClickListener(new OnClickListener(){

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
			   
			return rootView;
				   
			}
			 public boolean isToday(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
		    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
		    	       				.toString().equals(today)) ? true : false;
		    	}
		    	
		    	public boolean isTomorrow(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 1);
		    	    	String tomorrow = new SimpleDateFormat("MM/dd/yyyy").format(new Date(c.getTimeInMillis()));
		    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
		    	       				.toString().equals(tomorrow)) ? true : false;
		    	}
		    	    
		    	public boolean isThisWeek(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 7);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
		    	}
		    	public boolean isThisMonth(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 30);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
		    	}
		    	public boolean isThisQuarter(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 90);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
		    	}
		    	
		    	public boolean isMidYear(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 120);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
		    	}
		    	public boolean isThisYear(long dueDate)
		    	{
		    			long milliSeconds = dueDate;
		    	    	Calendar c = Calendar.getInstance();
		    	    	c.add(Calendar.DATE, 365);
		    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
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
			 
			 public static class DatePickerFragment2 extends DialogFragment
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
					 start_date=day+"-"+month_value+"-"+year;
					 startDateValue.setText(start_date);
				 }
}
				
			}
	 
	 @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if ((keyCode == KeyEvent.KEYCODE_BACK)) {
				TargetSettingActivity.this.finish();
				
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
