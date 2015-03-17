package org.grameenfoundation.cch.activity;

import java.text.ParseException;
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
import org.grameenfoundation.cch.caldroid.CaldroidFragment;
import org.grameenfoundation.cch.caldroid.CaldroidListener;
import org.grameenfoundation.cch.model.Validation;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class TargetSettingActivity extends SherlockFragmentActivity implements ActionBar.TabListener, OnSharedPreferenceChangeListener{
	 private DbHelper dbh;
	SectionsPagerAdapter mSectionsPagerAdapter;
	public static String current_month;
	private Long start_time;
	private Long end_time;
	Context mContext;
	
	private static final String EVENT_PLANNER_ID = "Event Planner";
	public static final String TAG = NewEventPlannerActivity.class.getSimpleName();
	public static String month_passed = null;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
	private SharedPreferences prefs;
	private Calendar c;
	private static int this_month;
	private static int this_year;
	private static String today;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_new_event_planner);
	    start_time=System.currentTimeMillis();
        dbh = new DbHelper(TargetSettingActivity.this);
        mContext=TargetSettingActivity.this;
        final ActionBar actionBar =getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle("Planner");
        actionBar.setSubtitle("Target Setting");
        actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
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
       
         c = Calendar.getInstance();
         this_month=c.get(Calendar.MONTH)+1;
         this_year=c.get(Calendar.YEAR);
       
       
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
		
		Intent service = new Intent(this, TrackerService.class);
		Bundle tb = new Bundle();
		tb.putBoolean("backgroundData", true);
		service.putExtras(tb);
		this.startService(service);
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
			private long eventId;
			private long coverageId;
			private long otherId;
			private long learningId;
			private String eventDetailText;
			private RadioGroup event_detail;

			private String[] items3;

			private EditText editText_event_period;
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
			    db=new DbHelper(getActivity());
			    String[] items=getResources().getStringArray(R.array.ReminderFrequency);
				final Spinner spinner_event_period=(Spinner) rootView.findViewById(R.id.spinner_dialogEventPeriod);
				final Spinner spinner_event_name=(Spinner) rootView.findViewById(R.id.spinner_eventName);
				
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
				spinner_event_period.setAdapter(adapter);
				items3=new String[]{};
				items3=getResources().getStringArray(R.array.EventNames);
				ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
				spinner_event_name.setAdapter(adapter2);
				editText_event_period=(EditText) rootView.findViewById(R.id.editText_dialogEventPeriodNumber);
				dueDateValue=(TextView) rootView.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) rootView.findViewById(R.id.textView_startDate);
				event_detail=(RadioGroup) rootView.findViewById(R.id.radioGroup_category);
				items3=new String[]{};
				items3=getResources().getStringArray(R.array.Monitoring);
				event_detail.setOnCheckedChangeListener(new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.radio_events) {
							 items3=new String[]{};
							 items3=getResources().getStringArray(R.array.EventNames);
							 ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
								spinner_event_name.setAdapter(adapter2);
								eventDetailText="Event";
						}else if(checkedId == R.id.radio_monitoring){
							 items3=new String[]{};
							 items3=getResources().getStringArray(R.array.Monitoring);
							 ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
								spinner_event_name.setAdapter(adapter2);
								eventDetailText="Monitoring";
						}
						
					}
					
				});
				ImageButton datepickerDialog=(ImageButton) rootView.findViewById(R.id.imageButton_dueDate);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", this_month, this_year);
						dialogCaldroidFragment.show(getFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	dueDateValue.setText(formatter2.format(date));
						    	due_date=formatter.format(date);
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};
						dialogCaldroidFragment.setCaldroidListener(listener);
					}
				});
				
				ImageButton datepickerDialog2=(ImageButton) rootView.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", this_month, this_year);
						dialogCaldroidFragment.show(getFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	startDateValue.setText(formatter2.format(date));
						    	start_date=formatter.format(date);
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};
						dialogCaldroidFragment.setCaldroidListener(listener);
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
						String duration = " ";
						
				      	/*if(isDateAfter(start_date,due_date)==true){
				      		startDateValue.requestFocus();
				      		startDateValue.setError("Check this date!");
				      	}else if(event_period_number.isEmpty()==true){
				      		editText_event_period.requestFocus();
				      		editText_event_period.setError("Please enter a number");
				      	}else if(start_date==null){
				      		startDateValue.requestFocus();
				      		startDateValue.setError("Select a start date");
				      	}else if(due_date==null){
				      		dueDateValue.requestFocus();
				      		dueDateValue.setError("Select an end date");
				      	}*/if(!checkValidation()){
				      		 Toast.makeText(getActivity().getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();
				      	}else{
				     
					    if(db.insertEventSet(event_name,eventDetailText, event_period, event_period_number, duration,start_date,due_date,0,Integer.valueOf(event_period_number),"new_record") !=0){
					    	Intent intent2 = new Intent(Intent.ACTION_MAIN);
				 	          intent2.setClass(getActivity(), EventPlannerOptionsActivity.class);
				 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 	          startActivity(intent2);
				 	          getActivity().finish();	
				 	        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
					    	 	Toast.makeText(getActivity().getApplicationContext(), "Event target set successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
					}
				});
				
				 button_show=(Button) rootView.findViewById(R.id.button_show);
				 eventId=db.getEventIdCount("Daily");
				 coverageId=db.getCoverageIdCount("Daily");
				 otherId=db.getOtherIdCount("Daily");
				 learningId=db.getLearningIdCount("Daily");
				 int number=(int)eventId;
				 int number2=(int)coverageId;
				 int number3=(int)otherId;
				 int number4=(int)learningId;
				 final int counter;
				
				counter=number+number2+number3+number4;	
			    button_show.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						if(counter>0){
						Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
						}else if(counter==0){
							 Toast.makeText(getActivity(), "You have no targets to update!",
							         Toast.LENGTH_SHORT).show();
						}
					}
			    	
			    });
			  
			return rootView;
				   
			}
		
			 private boolean checkValidation() {
			        boolean ret = true;
			 
			        if (!Validation.hasTextEditText(editText_event_period)) ret = false;
			        if (!Validation.hasTextTextView(startDateValue)) ret = false;
			        if (!Validation.hasTextTextView(dueDateValue)) ret = false;
			        if (Validation.isDateAfter(start_date,due_date,startDateValue)) ret = false;
			        return ret;
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
			private long eventId;
			private long coverageId;
			private long otherId;
			private long learningId;
			private EditText editText_coverageNumber;
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
			    editText_coverageNumber=(EditText) rootView.findViewById(R.id.editText_dialogCoverageNumber);
			    button_show=(Button) rootView.findViewById(R.id.button_show);
				ImageButton datepickerDialog=(ImageButton) rootView.findViewById(R.id.imageButton_dueDate);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", this_month, this_year);
						dialogCaldroidFragment.show(getFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	dueDateValue.setText(formatter2.format(date));
						    	due_date=formatter.format(date);
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
					}
					
				});
				ImageButton datepickerDialog2=(ImageButton) rootView.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", this_month, this_year);
						dialogCaldroidFragment.show(getFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	startDateValue.setText(formatter2.format(date));
						    	start_date=formatter.format(date);
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
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
				  items3=new String[]{};
				  items3=getResources().getStringArray(R.array.CoverageIndicatorsName);
				  spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

						@Override
						public void onItemSelected(
								AdapterView<?> parent, View view,
								int position, long id) {
						switch(position){
						case 0:
							String[] detail_items1=getResources().getStringArray(R.array.CoverageIndicatorsDetailFamilyPlanning);
							ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items1);
							spinner_coverageDetails.setAdapter(details_adapter);
							coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
							break;
						case 1:
							String[] detail_items2=getResources().getStringArray(R.array.CoverageIndicatorsDetailAgeGroups);
							ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items2);
							spinner_coverageDetails.setAdapter(details_adapter2);
							coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
							break;
						case 2:
							String[] detail_items3=getResources().getStringArray(R.array.CoverageIndicatorsSchoolHealth);
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
					ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
					spinner_coverageName.setAdapter(adapter3);
				    category_options.setOnCheckedChangeListener(new OnCheckedChangeListener(){
						public void onCheckedChanged(
								RadioGroup buttonView,
								int isChecked) {
							if (isChecked == R.id.radio_people) {
								 items3=new String[]{};
								 items3=getResources().getStringArray(R.array.CoverageIndicatorsName);
								ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
								spinner_coverageName.setAdapter(adapter3);
								spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

									@Override
									public void onItemSelected(
											AdapterView<?> parent, View view,
											int position, long id) {
									switch(position){
									case 0:
										String[] detail_items1=getResources().getStringArray(R.array.CoverageIndicatorsDetailFamilyPlanning);
										ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items1);
										spinner_coverageDetails.setAdapter(details_adapter);
										coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										break;
									case 1:
										String[] detail_items2=getResources().getStringArray(R.array.CoverageIndicatorsDetailAgeGroups);
										ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items2);
										spinner_coverageDetails.setAdapter(details_adapter2);
										coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										break;
									case 2:
										String[] detail_items3=getResources().getStringArray(R.array.CoverageIndicatorsSchoolHealth);
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
								items3=new String[]{};
								items3=getResources().getStringArray(R.array.CoverageImmunizationTypes);
								ArrayAdapter<String> adapter4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
								spinner_coverageName.setAdapter(adapter4);
								spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

									@Override
									public void onItemSelected(
											AdapterView<?> parent, View view,
											int position, long id) {
										switch(position){
										case 0:
											String[] detail_items1=getResources().getStringArray(R.array.CoverageImmunizationTypeBCG);
											ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items1);
											spinner_coverageDetails.setAdapter(details_adapter);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 1:
											String[] detail_items2=getResources().getStringArray(R.array.CoverageImmunizationPenta);
											ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items2);
											spinner_coverageDetails.setAdapter(details_adapter2);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 2:
											String[] detail_items3=getResources().getStringArray(R.array.CoverageImmunizationOPV);
											ArrayAdapter<String> details_adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items3);
											spinner_coverageDetails.setAdapter(details_adapter3);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 3:
											String[] detail_items4=getResources().getStringArray(R.array.CoverageImmunizationROTA);
											ArrayAdapter<String> details_adapter4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items4);
											spinner_coverageDetails.setAdapter(details_adapter4);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 4:
											String[] detail_itemsPCV=getResources().getStringArray(R.array.CoverageImmunizationPCV);
											ArrayAdapter<String> details_adapterPCV=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_itemsPCV);
											spinner_coverageDetails.setAdapter(details_adapterPCV);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 5:
											String[] detail_items5=getResources().getStringArray(R.array.CoverageImmunizationMeaslesRubella);
											ArrayAdapter<String> details_adapter5=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items5);
											spinner_coverageDetails.setAdapter(details_adapter5);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 6:
											String[] detail_items6=getResources().getStringArray(R.array.CoverageImmunizationVitaminA);
											ArrayAdapter<String> details_adapter6=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items6);
											spinner_coverageDetails.setAdapter(details_adapter6);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 7:
											String[] detail_items7=getResources().getStringArray(R.array.CoverageImmunizationTT);
											ArrayAdapter<String> details_adapter7=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items7);
											spinner_coverageDetails.setAdapter(details_adapter7);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 8:
											String[] detail_items8=getResources().getStringArray(R.array.CoverageImmunizationTT);
											ArrayAdapter<String> details_adapter8=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items8);
											spinner_coverageDetails.setAdapter(details_adapter8);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 9:
											String[] detail_items9=getResources().getStringArray(R.array.CoverageImmunizationYellowFever);
											ArrayAdapter<String> details_adapter9=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items9);
											spinner_coverageDetails.setAdapter(details_adapter9);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										}
										
									}

									@Override
									public void onNothingSelected(
											AdapterView<?> parent) {
										
									}
									
								});
								
							}
						}
				    });
				    String[] items2=getResources().getStringArray(R.array.ReminderFrequency);
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
						String duration = " ";
						/*
				      	if(isDateAfter(start_date,due_date)==true){
				      		 startDateValue.requestFocus();
				      		startDateValue.setError("Check this date!");
				      	}else if(coverage_number.isEmpty()==true){
				      		editText_coverageNumber.requestFocus();
				      		editText_coverageNumber.setError("Please enter a number");
				      	}else if(start_date==null){
					      		startDateValue.requestFocus();
					      		startDateValue.setError("Select a start date");
					      	}else if(due_date==null){
					      		dueDateValue.requestFocus();
					      		dueDateValue.setError("Select an end date");
					      	}*/if(!checkValidation()){
					      			Toast.makeText(getActivity().getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();
					      	}else{
				    if(db.insertCoverageSet(coverage_name, coverage_detail, coverage_period, coverage_number, duration,start_date,due_date,0,Integer.valueOf(coverage_number),"new_record") !=0){
				    	Intent intent2 = new Intent(Intent.ACTION_MAIN);
			 	          intent2.setClass(getActivity(), EventPlannerOptionsActivity.class);
			 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			 	          startActivity(intent2);
			 	          getActivity().finish();	
			 	         getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
					    	 Toast.makeText(getActivity().getApplicationContext(), "Coverage target added successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
					}
				});
				 eventId=db.getEventIdCount("Daily");
				 coverageId=db.getCoverageIdCount("Daily");
				 otherId=db.getOtherIdCount("Daily");
				 learningId=db.getLearningIdCount("Daily");
				 int number=(int)eventId;
				 int number2=(int)coverageId;
				 int number3=(int)otherId;
				 int number4=(int)learningId;
				 final int counter;
				counter=number+number2+number3+number4;	
			    button_show.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						if(counter>0){
						Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
						}else if(counter==0){
							 Toast.makeText(getActivity(), "You have no targets to update!",
							         Toast.LENGTH_SHORT).show();
						}
					}
			    });
			return rootView;
				   
			}
			 private boolean checkValidation() {
			        boolean ret = true;
			 
			        if (!Validation.hasTextEditText(editText_coverageNumber)) ret = false;
			        if (!Validation.hasTextTextView(startDateValue)) ret = false;
			        if (!Validation.hasTextTextView(dueDateValue)) ret = false;
			        if (Validation.isDateAfter(start_date,due_date,startDateValue)) ret = false;
			        return ret;
			    }	
	 }
	 
	 public static class LearningActivity extends Fragment {
			private Context mContext;															
			 private DbHelper db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			 View rootView;
			static long due_date_to_compare;
			int selected_position;
			static String due_date ;
			private static TextView dueDateValueLearning;
			static String start_date ;
			private static TextView startDateValue;
			private Button button_show;
			private long eventId;
			private long coverageId;
			private long otherId;
			private long learningId;
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
												"Preventing Postpartum Hemorrhage"};
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
				final String[] items_period=getResources().getStringArray(R.array.ReminderFrequency);
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
							String[] items4;
							if(spinner_learningCourse.getSelectedItem().equals("Antenatal Care")){
								items4=new String[]{"Significance of Antenatal Care",
										"Goal and Principles of Antenatal Care",
										"Elements of Focused (Goal-directed) Assessment",
										"Screening to Detect, Not Predict, Problems",
										"Preventive Measures",
										"Malaria in Pregnancy",
										"HIV in Pregnancy","Syphilis in Pregnancy",
										"Program Considerations"};
							}else {
							items4=new String[]{"Family planning counselling",
									"Family planning counselling skills"};
							}
					ArrayAdapter<String> adapter4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items4);
					spinner_learningDescription.setAdapter(adapter4);
							break;
						case 2:
							String[] items5;
							if(spinner_learningCourse.getSelectedItem().equals("Diarrhoeal Disease")){
								items5=new String[]{"Etiology and Epidemiology",
													"Clinical Assessment and Classification",
													"Treatment",
													"Prevention"};
							}else {
							items5= new String[]{"Family Planning/Reproductive Health",
												"Family planning for people living with HIV",
												"Reproductive Health",
												"Helping Clients Make a Family Planning",
												"Family Planning in PMTCT Services"};
							}
					ArrayAdapter<String> adapter5=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items5);
					spinner_learningDescription.setAdapter(adapter5);
							break;
						case 3:
							String[] items6;
							if(spinner_learningCourse.getSelectedItem().equals("Emergency obstetrics")){
								items6=new String[]{"Background and Definitions",
													"Basic and Comprehensive EmONC",
													"Implementation of EmONC Services"};
							}else {
							items6=new String[]{"Hormonal Contraceptives",
												"Oral contraceptives",
												"Emergency contraceptive pills",
												"Injectable contraceptives",
												"Implants",
												"Benefits and risks of hormonal contraceptives"};
							}
					ArrayAdapter<String> adapter6=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items6);
					spinner_learningDescription.setAdapter(adapter6);
							break;
						case 4:
							String[] items7;
							 if(spinner_learningCourse.getSelectedItem().equals("Malaria in Pregnancy")){
									items7=new String[]{"Why Is Malaria in Pregnancy (MIP) Important?",
														"MIP: Strategic Framework, Main Interventions",
														"Insecticide-treated Nets, Case Management",
														"Partnerships for MIP, MIP Readiness",
														"Case Study: Frequent Problems/Practical Solutions"};
							 }else{
							items7=new String[]{"Rationale for postpartum family planning",
												"Contraceptive method considerations",
												"Service delivery:Clinical Considerations",
												"Service delivery:Integration and linkage"};
							 }
					ArrayAdapter<String> adapter7=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items7);
					spinner_learningDescription.setAdapter(adapter7);
							break;
						case 5:
							items=new String[]{"Postpartum Care: Overview",
												"Field Realities",
												"Preventing Postpartum Mortality and Morbidity One",
												"Preventing Postpartum Mortality and Morbidity Two",
												"Case Study: Frequent Problems/Practical Solutions"};
							ArrayAdapter<String> adapter8=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							spinner_learningDescription.setAdapter(adapter8);
							break;
						case 6:
							items=new String[]{"Postpartum Hemorrhage and Maternal Mortality",
												"Causes of Postpartum Hemorrhage",
												"Prevention of Postpartum Hemorrhage One",
												"Prevention of Postpartum Hemorrhage Two: AMTSL"};	
							ArrayAdapter<String> adapter9=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							spinner_learningDescription.setAdapter(adapter9);
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
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", this_month, this_year);
						dialogCaldroidFragment.show(getFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	dueDateValueLearning.setText(formatter2.format(date));
						    	due_date=formatter.format(date);
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
					}
					
				});
				ImageButton datepickerDialog2=(ImageButton) rootView.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", this_month, this_year);
						dialogCaldroidFragment.show(getFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	startDateValue.setText(formatter2.format(date));
						    	start_date=formatter.format(date);
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
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
						String duration=" ";
				      	/*
				      	if(isDateAfter(start_date,due_date)==true){
				      		startDateValue.requestFocus();
				      		startDateValue.setError("Check this date!");
				      	}else if(start_date==null){
				      		startDateValue.requestFocus();
				      		startDateValue.setError("Select a start date");
				      	}else if(due_date==null){
				      		dueDateValueLearning.requestFocus();
				      		dueDateValueLearning.setError("Select an end date");
				      	}*/if(!checkValidation()){
				      		Toast.makeText(getActivity().getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();
				      	}else{
					    if(db.insertLearning(learning_category, learning_description,learning_course,duration,learning_period,start_date,due_date, "new_record")!=0){
					    	Intent intent2 = new Intent(Intent.ACTION_MAIN);
				 	          intent2.setClass(getActivity(), EventPlannerOptionsActivity.class);
				 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 	          startActivity(intent2);
				 	          getActivity().finish();	
				 	         getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
					    	 Toast.makeText(getActivity().getApplicationContext(), "Learning target added successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
					}
				});
				
	    	button_show=(Button) rootView.findViewById(R.id.button_show);
			 eventId=db.getEventIdCount("Daily");
			 coverageId=db.getCoverageIdCount("Daily");
			 otherId=db.getOtherIdCount("Daily");
			 learningId=db.getLearningIdCount("Daily");
			 int number=(int)eventId;
			 int number2=(int)coverageId;
			 int number3=(int)otherId;
			 int number4=(int)learningId;
			 final int counter;
			counter=number+number2+number3+number4;	
		    button_show.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					if(counter>0){
					Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					}else if(counter==0){
						 Toast.makeText(getActivity(), "You have no targets to update!",
						         Toast.LENGTH_SHORT).show();
					}
					
				}
		    	
		    });											
			return rootView;
				   
			 }		
			 
			 private boolean checkValidation() {
			        boolean ret = true;
			 
			        if (!Validation.hasTextTextView(startDateValue)) ret = false;
			        if (!Validation.hasTextTextView(dueDateValueLearning)) ret = false;
			        if (Validation.isDateAfter(start_date,due_date,startDateValue)) ret = false;
			        return ret;
			    }	
	 }
	 
	 public static class OtherActivity extends Fragment{

			private Context mContext;															
			private DbHelper db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private Button button_show;
			private long eventId;
			private long coverageId;
			private long otherId;
			private long learningId;
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
				String[] items=getResources().getStringArray(R.array.ReminderFrequency);
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
				spinner_otherPeriod.setAdapter(adapter);
				final EditText editText_otherNumber=(EditText) rootView.findViewById(R.id.editText_dialogOtherNumber);
				Button dialogButton = (Button) rootView.findViewById(R.id.button_dialogAddEvent);
				dialogButton.setText("Save");
				dueDateValue=(TextView) rootView.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) rootView.findViewById(R.id.textView_startDate);
				final LinearLayout number_layout=(LinearLayout) rootView.findViewById(R.id.LinearLayout_number);
				number_layout.setVisibility(View.GONE);
				RadioGroup enter_number=(RadioGroup) rootView.findViewById(R.id.radioGroup1);
				final RadioButton yesRadioButton;
				final RadioButton noRadioButton;
				yesRadioButton = (RadioButton) rootView.findViewById(R.id.radio_yes);
				noRadioButton = (RadioButton) rootView.findViewById(R.id.radio_no);
				enter_number.setOnCheckedChangeListener(new OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId==R.id.radio_no) {
							number_layout.setVisibility(View.GONE);
							//other_number="0";
						}else if(checkedId==R.id.radio_yes){
							number_layout.setVisibility(View.VISIBLE);
						}
						
					}
				});
				ImageButton datepickerDialog=(ImageButton) rootView.findViewById(R.id.imageButton_dueDate);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", this_month, this_year);
						dialogCaldroidFragment.show(getFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	dueDateValue.setText(formatter2.format(date));
						    	due_date=formatter.format(date);
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
					}
					
				});
				ImageButton datepickerDialog2=(ImageButton) rootView.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", this_month, this_year);
						dialogCaldroidFragment.show(getFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	startDateValue.setText(formatter2.format(date));
						    	start_date=formatter.format(date);
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
					}
					
				});
				
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
						String other_number = null;
						if(noRadioButton.isChecked()){
			      			other_number="0";
			      		}else if (yesRadioButton.isChecked()){
			      			other_number=editText_otherNumber.getText().toString();
			      		}
						//other_number=editText_otherNumber.getText().toString();
						String other_period=spinner_otherPeriod.getSelectedItem().toString();
						String duration=" ";
						
				      	if(isDateAfter(start_date,due_date)==true){
				      		 startDateValue.requestFocus();
				      		startDateValue.setError("Check this date!");
				      	}else if(other_number.isEmpty()==true&&yesRadioButton.isChecked()){
				      		editText_otherNumber.requestFocus();
				      		editText_otherNumber.setError("Please enter a number");
				      	}else if(other_category.isEmpty()==true){
				      		editText_otherCategory.requestFocus();
				      		editText_otherCategory.setError("Please enter a description");
				      	}else if(start_date==null){
				      		startDateValue.requestFocus();
				      		startDateValue.setError("Please enter a start date");
				      	}else if(due_date==null){
				      		dueDateValue.requestFocus();
				      		dueDateValue.setError("Please enter an end date");
				      	}
				      	else{
					    if(db.insertOther(other_category,other_number,other_period,duration,start_date,due_date,0,Integer.valueOf(other_number),"new_record")!=0){
					    	Intent intent2 = new Intent(Intent.ACTION_MAIN);
				 	          intent2.setClass(getActivity(), EventPlannerOptionsActivity.class);
				 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 	          startActivity(intent2);
				 	          getActivity().finish();	
				 	         getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
					    	 Toast.makeText(getActivity().getApplicationContext(), "Added target successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
					}
				});
				 eventId=db.getEventIdCount("Daily");
				 coverageId=db.getCoverageIdCount("Daily");
				 otherId=db.getOtherIdCount("Daily");
				 learningId=db.getLearningIdCount("Daily");
				 int number=(int)eventId;
				 int number2=(int)coverageId;
				 int number3=(int)otherId;
				 int number4=(int)learningId;
				 final int counter;
				counter=number+number2+number3+number4;	
			    button_show.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						if(counter>0){
						Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
						}else if(counter==0){
							 Toast.makeText(getActivity(), "You have no targets to update!",
							         Toast.LENGTH_SHORT).show();
						}
						
					}
			    	
			    });						
			   
			return rootView;
				   
			}
			}
	 public static boolean isDateAfter(String startDate,String endDate)
	    {
		 Date start_date = null;
		 Date end_date = null;
		long starDateAsTimestamp = 0;
		long endDateTimestamp = 0;
		try {
			if(startDate==null){
			System.out.println("Enter a valid date!");
			}else{
			start_date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
			 starDateAsTimestamp = start_date.getTime();
			}
			if(endDate==null){
				System.out.println("Enter a valid date!");
			}else{
			end_date = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
			endDateTimestamp = end_date.getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		 long getRidOfTime = 1000 * 60 * 60 * 24;
		 long startDateAsTimestampWithoutTime = starDateAsTimestamp / getRidOfTime;
		 long endDateTimestampWithoutTime = endDateTimestamp / getRidOfTime;

		 if (startDateAsTimestampWithoutTime > endDateTimestampWithoutTime) {
		    return true;
		 } else {
		    return false;
		 }
	    }
	 
	 
	 @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if ((keyCode == KeyEvent.KEYCODE_BACK)) {
				TargetSettingActivity.this.finish();
				
			} 
			
		    return true; 
		}
	  
	 public void onBackPressed()
		{
			finish();
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
