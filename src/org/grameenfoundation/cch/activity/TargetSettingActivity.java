package org.grameenfoundation.cch.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.service.TrackerService;
import org.grameenfoundation.cch.caldroid.CaldroidFragment;
import org.grameenfoundation.cch.caldroid.CaldroidListener;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.Validation;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragmentActivity;

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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;



public class TargetSettingActivity extends SherlockFragmentActivity implements ActionBar.TabListener, OnSharedPreferenceChangeListener{
	 private DbHelper dbh;
	SectionsPagerAdapter mSectionsPagerAdapter;
	public static String current_month;
	private static Long start_time;
	private static Long end_time;
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
        DateTime dt=new DateTime();
        this_month=dt.getMonthOfYear();
        this_year=dt.getYear();
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
			private ArrayList<EventTargets> eventId;
			private long coverageId;
			private long otherId;
			private long learningId;
			private String eventDetailText;
			private RadioGroup event_detail;

			private String[] items3;

			private EditText editText_event_period;

			private MaterialSpinner spinner_event_name;

			private MaterialSpinner spinner_event_period;

			private TableRow other_option;

			private EditText editText_otherOption;
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
			    
			    //if other is selected specify
			    other_option=(TableRow) rootView.findViewById(R.id.other_option);
			    editText_otherOption=(EditText) rootView.findViewById(R.id.editText_otherOption);
			    other_option.setVisibility(View.GONE);
			   
				
				spinner_event_name=(MaterialSpinner) rootView.findViewById(R.id.spinner_eventName);
				spinner_event_name.setFloatingLabelText(" ");
				
				spinner_event_period=(MaterialSpinner) rootView.findViewById(R.id.spinner_dialogEventPeriod);
				spinner_event_period.setFloatingLabelText(" ");
				String[] items=getResources().getStringArray(R.array.ReminderFrequency);
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
				spinner_event_period.setAdapter(adapter);
				
				items3=new String[]{};
				items3=getResources().getStringArray(R.array.EventNames);
				Arrays.sort(items3);
				ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
				spinner_event_name.setAdapter(adapter2);
				spinner_event_name.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if(spinner_event_name.getSelectedItem().toString().equalsIgnoreCase("Other")){
							other_option.setVisibility(View.VISIBLE);
						}else{
							 other_option.setVisibility(View.GONE);
						}
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
				});
				editText_event_period=(EditText) rootView.findViewById(R.id.editText_dialogEventPeriodNumber);
				dueDateValue=(TextView) rootView.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) rootView.findViewById(R.id.textView_startDate);
				event_detail=(RadioGroup) rootView.findViewById(R.id.radioGroup_category);
				eventDetailText="Event";
				
				items3=new String[]{};
				items3=getResources().getStringArray(R.array.Monitoring);
				Arrays.sort(items3);
				event_detail.setOnCheckedChangeListener(new OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.radio_events) {
							 items3=new String[]{};
							 items3=getResources().getStringArray(R.array.EventNames);
							 Arrays.sort(items3);
							 ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
								spinner_event_name.setAdapter(adapter2);
								eventDetailText="Event";
						}else if(checkedId == R.id.radio_monitoring){
							 items3=new String[]{};
							 items3=getResources().getStringArray(R.array.Monitoring);
							 Arrays.sort(items3);
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
						    	dueDateValue.setError(null);
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
						    	startDateValue.setError(null);
						    	start_date=formatter.format(date);
						    	Date today=new Date();
					        	if(isDateAfter(formatter.format(today),formatter.format(date))==true){
					        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					        				getActivity());
										alertDialogBuilder.setTitle("Date Confirmation");
										alertDialogBuilder
											.setMessage("You selected a start date in the past. Click ok to proceed")
											.setCancelable(false)
											.setIcon(R.drawable.ic_error)
											.setPositiveButton("OK",new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,int id) {
													dialog.cancel();
												}
											  });
										AlertDialog alertDialog = alertDialogBuilder.create();
										alertDialog.show();
					        	}
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};
						dialogCaldroidFragment.setCaldroidListener(listener);
					}
				});
				Button cancel=(Button) rootView.findViewById(R.id.button_cancel);
				cancel.setVisibility(View.GONE);
				Button dialogButton = (Button) rootView.findViewById(R.id.button_dialogSetEVent);
				dialogButton.setText("Save");
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String event_name = null;
						String event_period=spinner_event_period.getSelectedItem().toString();
						if(spinner_event_name.getSelectedItem().toString().equalsIgnoreCase("Other")){
							event_name=editText_otherOption.getText().toString();
						}else{
							event_name=spinner_event_name.getSelectedItem().toString();
						}
						String event_period_number=editText_event_period.getText().toString();
					if(!checkValidation()){
				      		 Toast.makeText(getActivity().getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();
				      	}else{
				      		//long id=db.insertEventSet(event_name,eventDetailText, event_period, event_period_number, duration,start_date,due_date,0,Integer.valueOf(event_period_number),"new_record");
				      		long id=db.insertTarget(0, 
      								MobileLearning.CCH_TARGET_TYPE_EVENT,
      								event_name,
      								"", 
      								eventDetailText,
      								Integer.parseInt(event_period_number), 
      								0,
      								Integer.parseInt(event_period_number),
      								start_date,
      								due_date,
      								event_period,
      								MobileLearning.CCH_TARGET_STATUS_NEW,
      								"Not yet");
					    if(id!=0){
					    	
					    	JSONObject json = new JSONObject();
							 try {
								 json.put("id", id);
								 json.put("target_type", event_name);
								 json.put("category", "event");
								 json.put("start_date", start_date);
								 json.put("target_number", event_period_number);
								 json.put("due_date", due_date);
								 json.put("achieved_number", 0);
								 json.put("last_updated", getDateTime());
								 json.put("ver", db.getVersionNumber(getActivity().getApplicationContext()));
									json.put("battery", db.getBatteryStatus(getActivity().getApplicationContext()));
							    	json.put("device", db.getDeviceName());
							    	json.put("imei", db.getDeviceImei(getActivity().getApplicationContext()));
								 end_time=System.currentTimeMillis();
								 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
							 } catch (JSONException e) {
									e.printStackTrace();
								}
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
				eventId=db.getAllTargetsForUpdate("Daily",MobileLearning.CCH_TARGET_STATUS_NEW);
				 final int number=(int)eventId.size();
				 button_show.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							if(number>0){
							Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
							startActivity(intent);
							getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
							}else if(number==0){
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
			        if (!Validation.hasSelection(spinner_event_name))ret = false;
			        if (!Validation.hasSelection(spinner_event_period))ret = false;
			        if (other_option.isShown()&&!Validation.hasTextTextView(editText_otherOption))ret = false;
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
			private ArrayList<EventTargets> eventId;
			private EditText editText_coverageNumber;
			private MaterialSpinner spinner_coverageName;
			private MaterialSpinner spinner_coverageDetails;
			private MaterialSpinner spinner_coveragePeriod;
			static String due_date ;
			private static TextView dueDateValue;
			static String start_date ;
			static String coverage_category ;
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
						    	dueDateValue.setError(null);
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
						    	startDateValue.setError(null);
						    	start_date=formatter.format(date);
						    	Date today=new Date();
					        	if(isDateAfter(formatter.format(today),formatter.format(date))==true){
					        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					        				getActivity());
										alertDialogBuilder.setTitle("Date Confirmation");
										alertDialogBuilder
											.setMessage("You selected a start date in the past. Click ok to proceed")
											.setCancelable(false)
											.setIcon(R.drawable.ic_error)
											.setPositiveButton("OK",new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,int id) {
													dialog.cancel();
												}
											  });
										AlertDialog alertDialog = alertDialogBuilder.create();
										alertDialog.show();
					        	}
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
					}
					
				});
				Button cancel=(Button) rootView.findViewById(R.id.button_cancel);
				cancel.setVisibility(View.GONE);
				dueDateValue=(TextView) rootView.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) rootView.findViewById(R.id.textView_startDate);
				spinner_coverageName=(MaterialSpinner) rootView.findViewById(R.id.spinner_dialogCoverageName);
				spinner_coverageDetails=(MaterialSpinner) rootView.findViewById(R.id.spinner_dialogCoverageDetail);
				
				  category_options=(RadioGroup) rootView.findViewById(R.id.radioGroup_category);
				  category_options.check(R.id.radio_people);
				  category_people=(RadioButton) rootView.findViewById(R.id.radio_people);
				  category_people.setChecked(true);
				  coverage_category="Indicators";
				  items3=new String[]{};
				  items3=getResources().getStringArray(R.array.CoverageIndicatorsName);
				  ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
					spinner_coverageName.setAdapter(adapter3);
					String[] detail_items1=getResources().getStringArray(R.array.CoverageIndicatorsDetailFamilyPlanning);
					ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items1);
					spinner_coverageDetails.setAdapter(details_adapter);
				  spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

						@Override
						public void onItemSelected(
								AdapterView<?> parent, View view,
								int position, long id) {
					if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Family Planning")){
							String[] detail_items1=getResources().getStringArray(R.array.CoverageIndicatorsDetailFamilyPlanning);
							ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items1);
							spinner_coverageDetails.setAdapter(details_adapter);
							coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
					}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Age Groups")){
							String[] detail_items2=getResources().getStringArray(R.array.CoverageIndicatorsDetailAgeGroups);
							ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items2);
							spinner_coverageDetails.setAdapter(details_adapter2);
							coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
					}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("School Health")){	
							String[] detail_items3=getResources().getStringArray(R.array.CoverageIndicatorsSchoolHealth);
							ArrayAdapter<String> details_adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items3);
							spinner_coverageDetails.setAdapter(details_adapter3);
							coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
					}
						
							
						}

						@Override
						public void onNothingSelected(
								AdapterView<?> parent) {
							
						}
						
					});
					
				    category_options.setOnCheckedChangeListener(new OnCheckedChangeListener(){
						public void onCheckedChanged(
								RadioGroup buttonView,
								int isChecked) {
							if (isChecked == R.id.radio_people) {
								coverage_category="Indicators";
								 items3=new String[]{};
								 items3=getResources().getStringArray(R.array.CoverageIndicatorsName);
								ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
								spinner_coverageName.setAdapter(adapter3);
								String[] detail_items1=getResources().getStringArray(R.array.CoverageIndicatorsDetailFamilyPlanning);
								ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items1);
								spinner_coverageDetails.setAdapter(details_adapter);
								spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

									@Override
									public void onItemSelected(
											AdapterView<?> parent, View view,
											int position, long id) {
									if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Family Planning")){
											String[] detail_items1=getResources().getStringArray(R.array.CoverageIndicatorsDetailFamilyPlanning);
											ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items1);
											spinner_coverageDetails.setAdapter(details_adapter);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
									}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Age Groups")){
											String[] detail_items2=getResources().getStringArray(R.array.CoverageIndicatorsDetailAgeGroups);
											ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items2);
											spinner_coverageDetails.setAdapter(details_adapter2);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
									}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("School Health")){	
											String[] detail_items3=getResources().getStringArray(R.array.CoverageIndicatorsSchoolHealth);
											ArrayAdapter<String> details_adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items3);
											spinner_coverageDetails.setAdapter(details_adapter3);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
									}
										
									}

									@Override
									public void onNothingSelected(
											AdapterView<?> parent) {
									}
									
								});
								
							} else if (isChecked == R.id.radio_immunization) {
								coverage_category="Immunizations";
								items3=new String[]{};
								items3=getResources().getStringArray(R.array.CoverageImmunizationTypes);
								ArrayAdapter<String> adapter4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
								spinner_coverageName.setAdapter(adapter4);
								String[] detail_items1=getResources().getStringArray(R.array.CoverageImmunizationTypeBCG);
								ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items1);
								spinner_coverageDetails.setAdapter(details_adapter);
								spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

									@Override
									public void onItemSelected(
											AdapterView<?> parent, View view,
											int position, long id) {
										if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("BCG")){
											String[] detail_items1=getResources().getStringArray(R.array.CoverageImmunizationTypeBCG);
											ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items1);
											spinner_coverageDetails.setAdapter(details_adapter);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}
										else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Penta")){
											String[] detail_items2=getResources().getStringArray(R.array.CoverageImmunizationPenta);
											ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items2);
											spinner_coverageDetails.setAdapter(details_adapter2);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("OPV")){
											String[] detail_items3=getResources().getStringArray(R.array.CoverageImmunizationOPV);
											ArrayAdapter<String> details_adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items3);
											spinner_coverageDetails.setAdapter(details_adapter3);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("ROTA")){
											String[] detail_items4=getResources().getStringArray(R.array.CoverageImmunizationROTA);
											ArrayAdapter<String> details_adapter4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items4);
											spinner_coverageDetails.setAdapter(details_adapter4);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("PCV")){
											String[] detail_itemsPCV=getResources().getStringArray(R.array.CoverageImmunizationPCV);
											ArrayAdapter<String> details_adapterPCV=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_itemsPCV);
											spinner_coverageDetails.setAdapter(details_adapterPCV);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Measles")){
											String[] detail_items5=getResources().getStringArray(R.array.CoverageImmunizationMeaslesRubella);
											ArrayAdapter<String> details_adapter5=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items5);
											spinner_coverageDetails.setAdapter(details_adapter5);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Vitamin A")){
											String[] detail_items6=getResources().getStringArray(R.array.CoverageImmunizationVitaminA);
											ArrayAdapter<String> details_adapter6=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items6);
											spinner_coverageDetails.setAdapter(details_adapter6);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("TT pregnant")){
											String[] detail_items7=getResources().getStringArray(R.array.CoverageImmunizationTT);
											ArrayAdapter<String> details_adapter7=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items7);
											spinner_coverageDetails.setAdapter(details_adapter7);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("TT non-pregnant")){
											String[] detail_items8=getResources().getStringArray(R.array.CoverageImmunizationTT);
											ArrayAdapter<String> details_adapter8=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items8);
											spinner_coverageDetails.setAdapter(details_adapter8);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Yellow Fever")){
											String[] detail_items9=getResources().getStringArray(R.array.CoverageImmunizationYellowFever);
											ArrayAdapter<String> details_adapter9=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detail_items9);
											spinner_coverageDetails.setAdapter(details_adapter9);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
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
				spinner_coveragePeriod=(MaterialSpinner) rootView.findViewById(R.id.spinner_coveragePeriod);
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
						if(!checkValidation()){
					      			Toast.makeText(getActivity().getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();
					      	}else{
					//long id=db.insertCoverageSet(coverage_name, coverage_detail, coverage_period, coverage_number, duration,start_date,due_date,0,Integer.valueOf(coverage_number),"new_record") ;
					      		long id=db.insertTarget(0, 
	      								MobileLearning.CCH_TARGET_TYPE_COVERAGE,
	      								coverage_name,
	      								coverage_detail, 
	      								coverage_category,
	      								Integer.parseInt(coverage_number), 
	      								0,
	      								Integer.parseInt(coverage_number),
	      								start_date,
	      								due_date,
	      								coverage_period,
	      								MobileLearning.CCH_TARGET_STATUS_NEW,
	      								"Not yet");
				    if(id!=0){
				    	
				    	JSONObject json = new JSONObject();
						 try {
							 json.put("id", id);
							 json.put("target_type", coverage_detail);
							 json.put("category", "coverage");
							 json.put("start_date", start_date);
							 json.put("target_number", coverage_number);
							 json.put("due_date", due_date);
							 json.put("achieved_number", 0);
							 json.put("last_updated", getDateTime());
							 json.put("ver", db.getVersionNumber(getActivity().getApplicationContext()));
								json.put("battery", db.getBatteryStatus(getActivity().getApplicationContext()));
						    	json.put("device", db.getDeviceName());
						    	json.put("imei", db.getDeviceImei(getActivity().getApplicationContext()));
							 end_time=System.currentTimeMillis();
							 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
						 } catch (JSONException e) {
								e.printStackTrace();
							}
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
				 button_show=(Button) rootView.findViewById(R.id.button_show);
					eventId=db.getAllTargetsForUpdate("Daily",MobileLearning.CCH_TARGET_STATUS_NEW);
					 final int number=(int)eventId.size();
					 button_show.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								if(number>0){
								Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
								startActivity(intent);
								getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
								}else if(number==0){
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
			        if (!Validation.hasSelection(spinner_coverageDetails)) ret = false;
			        if (!Validation.hasSelection(spinner_coverageName)) ret = false;
			        if (!Validation.hasSelection(spinner_coveragePeriod)) ret = false;
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
			private ArrayList<EventTargets> eventId;
			private long coverageId;
			private long otherId;
			private long learningId;
			private MaterialSpinner target_name;
			private MaterialSpinner target_category;
			private MaterialSpinner target_detail;
			private MaterialSpinner spinner_period;
			 public LearningActivity(){

      }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				rootView=inflater.inflate(R.layout.learning_add_dialog,null,false);
			    mContext=getActivity().getApplicationContext();
			    db=new DbHelper(getActivity());
			    target_name=(MaterialSpinner) rootView.findViewById(R.id.spinner_learningHeader);
				String[] items={"Family Planning","Maternal and Child Health"};
				target_category=(MaterialSpinner) rootView.findViewById(R.id.spinner_learningDescription);
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
				target_name.setAdapter(adapter);
				target_detail=(MaterialSpinner) rootView.findViewById(R.id.spinner_learningCourse);
				String[] items2={"Family Planning 101",
						"Family Planning Counselling",
						"Family Planning for people living with HIV",
						"Hormonal Contraceptives",
						"Postpartum Family Planning"};
				ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items2);
				target_detail.setAdapter(adapter2);
				String[] items3=new String[]{"Rationale for voluntary family planning",
				"Family Planning method considerations",
				"Short-acting contraceptive methods",
				"Long-acting contraceptive methods",
				"Special needs"};
		ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
		target_category.setAdapter(adapter3);
				target_name.setOnItemSelectedListener(new OnItemSelectedListener(){
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if(target_name.getSelectedItem().toString().equalsIgnoreCase("Family Planning")){
							String[] items2={"Family Planning 101",
											"Family Planning Counselling",
											"Family Planning for people living with HIV",
											"Hormonal Contraceptives",
											"Postpartum Family Planning"};
							ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items2);
							target_detail.setAdapter(adapter2);
							String[] items=new String[]{"Rationale for voluntary family planning",
									"Family Planning method considerations",
									"Short-acting contraceptive methods",
									"Long-acting contraceptive methods",
									"Special needs"};
							ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_name.getSelectedItem().toString().equalsIgnoreCase("Maternal and Child Health")){
							String[] items2_1={"Essential Newborn care","Antenatal Care",
												"Diarrhoea Disease","Emergency obstetrics",
												"Malaria in Pregnancy",
												"Postpartum Care",
												"Preventing Postpartum Hemorrhage"};
							ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items2_1);
							target_detail.setAdapter(adapter3);
							String[] items=new String[]{"Newborn mortality",
									"Care during labor and birth",
									"Newborn care following birth",
									"Newborn care following birth: Later",
									"Infant feeding",
									"Household to hospital continuum"};
							ArrayAdapter<String> adapter4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter4);
						}
						
					}
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
					
					
				});
				spinner_period=(MaterialSpinner) rootView.findViewById(R.id.spinner_period);
				final String[] items_period=getResources().getStringArray(R.array.ReminderFrequency);
				ArrayAdapter<String> adapter_period=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items_period);
				spinner_period.setAdapter(adapter_period);
				
				
				target_detail.setOnItemSelectedListener(new OnItemSelectedListener(){
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						String items[];
						ArrayAdapter<String> adapter3;
						if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Family Planning 101")){
							items=new String[]{"Rationale for voluntary family planning",
									"Family Planning method considerations",
									"Short-acting contraceptive methods",
									"Long-acting contraceptive methods",
									"Special needs"};
							adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Family Planning Counselling")){
							items=new String[]{"Family planning counselling",
							"Family planning counselling skills"};
							adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Family Planning for people living with HIV")){
							items= new String[]{"Family Planning/Reproductive Health",
									"Family planning for people living with HIV",
									"Reproductive Health",
									"Helping Clients Make a Family Planning",
									"Family Planning in PMTCT Services"};
							adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Hormonal Contraceptives")){
							items=new String[]{"Hormonal Contraceptives",
									"Oral contraceptives",
									"Emergency contraceptive pills",
									"Injectable contraceptives",
									"Implants",
									"Benefits and risks of hormonal contraceptives"};
							adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Postpartum Family Planning")){
							items=new String[]{"Rationale for postpartum family planning",
									"Contraceptive method considerations",
									"Service delivery:Clinical Considerations",
									"Service delivery:Integration and linkage"};
							adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}
						else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Essential Newborn care")){
							items=new String[]{"Newborn mortality",
									"Care during labor and birth",
									"Newborn care following birth",
									"Newborn care following birth: Later",
									"Infant feeding",
									"Household to hospital continuum"};
							adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Antenatal Care")){
							items=new String[]{"Significance of Antenatal Care",
									"Goal and Principles of Antenatal Care",
									"Elements of Focused (Goal-directed) Assessment",
									"Screening to Detect, Not Predict, Problems",
									"Preventive Measures",
									"Malaria in Pregnancy",
									"HIV in Pregnancy","Syphilis in Pregnancy",
									"Program Considerations"};
							adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Diarrhoea Disease")){
							items=new String[]{"Etiology and Epidemiology",
									"Clinical Assessment and Classification",
									"Treatment",
									"Prevention"};
							adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Emergency obstetrics")){
							items=new String[]{"Background and Definitions",
									"Basic and Comprehensive EmONC",
									"Implementation of EmONC Services"};
							adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Malaria in Pregnancy")){
							items=new String[]{"Why Is Malaria in Pregnancy (MIP) Important?",
									"MIP: Strategic Framework, Main Interventions",
									"Insecticide-treated Nets, Case Management",
									"Partnerships for MIP, MIP Readiness",
									"Case Study: Frequent Problems/Practical Solutions"};
							adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Postpartum Care")){
							items=new String[]{"Postpartum Care: Overview",
									"Field Realities",
									"Preventing Postpartum Mortality and Morbidity One",
									"Preventing Postpartum Mortality and Morbidity Two",
									"Case Study: Frequent Problems/Practical Solutions"};
							adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Preventing Postpartum Hemorrhage")){
							items=new String[]{"Postpartum Hemorrhage and Maternal Mortality",
									"Causes of Postpartum Hemorrhage",
									"Prevention of Postpartum Hemorrhage One",
									"Prevention of Postpartum Hemorrhage Two: AMTSL"};	
							adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
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
						    	dueDateValueLearning.setError(null);
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
						    	startDateValue.setError(null);
						    	start_date=formatter.format(date);
						    	Date today=new Date();
					        	if(isDateAfter(formatter.format(today),formatter.format(date))==true){
					        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					        				getActivity());
										alertDialogBuilder.setTitle("Date Confirmation");
										alertDialogBuilder
											.setMessage("You selected a start date in the past. Click ok to proceed")
											.setCancelable(false)
											.setIcon(R.drawable.ic_error)
											.setPositiveButton("OK",new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,int id) {
													dialog.cancel();
												}
											  });
										AlertDialog alertDialog = alertDialogBuilder.create();
										alertDialog.show();
					        	}
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
					}
					
				});
				Button cancel=(Button) rootView.findViewById(R.id.button_cancel);
				cancel.setVisibility(View.GONE);
				dueDateValueLearning=(TextView) rootView.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) rootView.findViewById(R.id.textView_startDate);
				dialogButton.setOnClickListener(new OnClickListener() {
					

					@Override
					public void onClick(View v) {
						
						String learning_target_name=target_name.getSelectedItem().toString();
						String learning_target_detail=target_detail.getSelectedItem().toString();
						String learning_period=spinner_period.getSelectedItem().toString();
						String learning_target_category=target_category.getSelectedItem().toString();
				      if(!checkValidation()){
				      		Toast.makeText(getActivity().getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();
				      	}else{
				      		//long id=db.insertLearning(learning_category, learning_description,learning_course,duration,learning_period,start_date,due_date, "new_record") ;
				      		long id=db.insertTarget(0, 
      								MobileLearning.CCH_TARGET_TYPE_LEARNING,
      								learning_target_name,
      								learning_target_detail, 
      								learning_target_category,
      								Integer.parseInt("0"), 
      								0,
      								Integer.parseInt("0"),
      								start_date,
      								due_date,
      								learning_period,
      								MobileLearning.CCH_TARGET_STATUS_NEW,
      								"Not yet");
					    if(id!=0){
					    	
					    	JSONObject json = new JSONObject();
							 try {
								 json.put("id", id);
								 json.put("target_type", learning_target_category);
								 json.put("category", "learning");
								 json.put("start_date", start_date);
								 json.put("target_number", 	1);
								 json.put("due_date", due_date);
								 json.put("achieved_number", 0);
								 json.put("last_updated", getDateTime());
								 json.put("ver", db.getVersionNumber(getActivity().getApplicationContext()));
									json.put("battery", db.getBatteryStatus(getActivity().getApplicationContext()));
							    	json.put("device", db.getDeviceName());
							    	json.put("imei", db.getDeviceImei(getActivity().getApplicationContext()));
								 end_time=System.currentTimeMillis();
								 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
							 } catch (JSONException e) {
									e.printStackTrace();
								}
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
			eventId=db.getAllTargetsForUpdate("Daily",MobileLearning.CCH_TARGET_STATUS_NEW);
			 final int number=(int)eventId.size();
			 button_show.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						if(number>0){
						Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
						}else if(number==0){
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
			        if (!Validation.hasSelection(target_name)) ret=false;
			        if (!Validation.hasSelection(target_category)) ret=false;
			        if (!Validation.hasSelection(target_detail)) ret=false;
			        if (!Validation.hasSelection(spinner_period)) ret=false;
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
			private ArrayList<EventTargets> eventId;
			private long coverageId;
			private long otherId;
			private long learningId;
			private EditText editText_otherCategory;
			private EditText editText_otherNumber;
			private RadioGroup personal;
			private MaterialSpinner spinner_otherPeriod;
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
			   	editText_otherCategory=(EditText) rootView.findViewById(R.id.editText_dialogOtherName);
				spinner_otherPeriod=(MaterialSpinner) rootView.findViewById(R.id.spinner_dialogOtherPeriod);
				String[] items=getResources().getStringArray(R.array.ReminderFrequency);
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
				spinner_otherPeriod.setAdapter(adapter);
				editText_otherNumber=(EditText) rootView.findViewById(R.id.editText_dialogOtherNumber);
				Button dialogButton = (Button) rootView.findViewById(R.id.button_dialogAddEvent);
				dialogButton.setText("Save");
				dueDateValue=(TextView) rootView.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) rootView.findViewById(R.id.textView_startDate);
				final LinearLayout number_layout=(LinearLayout) rootView.findViewById(R.id.LinearLayout_number);
				number_layout.setVisibility(View.GONE);
				RadioGroup enter_number=(RadioGroup) rootView.findViewById(R.id.radioGroup1);
				personal=(RadioGroup) rootView.findViewById(R.id.radioGroup_personal);
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
						    	dueDateValue.setError(null);
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
						    	startDateValue.setError(null);
						    	start_date=formatter.format(date);
						    	Date today=new Date();
					        	if(isDateAfter(formatter.format(today),formatter.format(date))==true){
					        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					        				getActivity());
										alertDialogBuilder.setTitle("Date Confirmation");
										alertDialogBuilder
											.setMessage("You selected a start date in the past. Click ok to proceed")
											.setCancelable(false)
											.setIcon(R.drawable.ic_error)
											.setPositiveButton("OK",new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,int id) {
													dialog.cancel();
												}
											  });
										AlertDialog alertDialog = alertDialogBuilder.create();
										alertDialog.show();
					        	}
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
					}
					
				});
				
				Button cancel=(Button) rootView.findViewById(R.id.button_cancel);
				cancel.setVisibility(View.GONE);
				
				dialogButton.setOnClickListener(new OnClickListener() {
				

					@Override
					public void onClick(View v) {
						String other_category=editText_otherCategory.getText
								().toString();
						String other_number = null;
						if(noRadioButton.isChecked()){
			      			other_number="0";
			      		}else if (yesRadioButton.isChecked()){
			      			other_number=editText_otherNumber.getText().toString();
			      		}
						String other_period=spinner_otherPeriod.getSelectedItem().toString();
						String duration=" ";
						if(!checkValidation()){
				      		Toast.makeText(getActivity().getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();
				      	}
				      	else{
				      		if(personal.getCheckedRadioButtonId()==R.id.radio_personalYes){
				      		//long id=db.insertOther(other_category,other_number,other_period,duration,start_date,due_date,"personal",0,Integer.valueOf(other_number),"new_record");
				      			long id=db.insertTarget(0, 
	      								MobileLearning.CCH_TARGET_TYPE_OTHER,
	      								other_category,
	      								"", 
	      								MobileLearning.CCH_TARGET_PERSONAL,
	      								Integer.parseInt(other_number), 
	      								0,
	      								Integer.parseInt(other_number),
	      								start_date,
	      								due_date,
	      								other_period,
	      								MobileLearning.CCH_TARGET_STATUS_NEW,
	      								"Not yet");
					    if(id!=0){
					    	JSONObject json = new JSONObject();
							 try {
								 json.put("id", id);
								 json.put("target_type", other_category);
								 json.put("category", "other");
								 json.put("start_date", start_date);
								 json.put("target_number", 	other_number);
								 json.put("due_date", due_date);
								 json.put("achieved_number", 0);
								 json.put("last_updated", getDateTime());
								 json.put("details", "personal");
								 json.put("ver", db.getVersionNumber(getActivity().getApplicationContext()));
									json.put("battery", db.getBatteryStatus(getActivity().getApplicationContext()));
							    	json.put("device", db.getDeviceName());
							    	json.put("imei", db.getDeviceImei(getActivity().getApplicationContext()));
								 end_time=System.currentTimeMillis();
								 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
							 } catch (JSONException e) {
									e.printStackTrace();
								}
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
				      		}else if(personal.getCheckedRadioButtonId()==R.id.radio_personalNo){
				      			//long id=db.insertOther(other_category,other_number,other_period,duration,start_date,due_date,"not_personal",0,Integer.valueOf(other_number),"new_record");
				      			long id=db.insertTarget(0, 
	      								MobileLearning.CCH_TARGET_TYPE_OTHER,
	      								other_category,
	      								"", 
	      								MobileLearning.CCH_TARGET_NOT_PERSONAL,
	      								Integer.parseInt(other_number), 
	      								0,
	      								Integer.parseInt(other_number),
	      								start_date,
	      								due_date,
	      								other_period,
	      								MobileLearning.CCH_TARGET_STATUS_NEW,
	      								"Not yet");
							    if(id!=0){
							    	JSONObject json = new JSONObject();
									 try {
										 json.put("id", id);
										 json.put("target_type", other_category);
										 json.put("category", "other");
										 json.put("start_date", start_date);
										 json.put("target_number", 	other_number);
										 json.put("due_date", due_date);
										 json.put("achieved_number", 0);
										 json.put("last_updated", getDateTime());
										 json.put("details", "not_personal");
										 json.put("ver", db.getVersionNumber(getActivity().getApplicationContext()));
											json.put("battery", db.getBatteryStatus(getActivity().getApplicationContext()));
									    	json.put("device", db.getDeviceName());
									    	json.put("imei", db.getDeviceImei(getActivity().getApplicationContext()));
										 end_time=System.currentTimeMillis();
										 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
									 } catch (JSONException e) {
											e.printStackTrace();
										}
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
					}
				});
				button_show=(Button) rootView.findViewById(R.id.button_show);
				eventId=db.getAllTargetsForUpdate("Daily",MobileLearning.CCH_TARGET_STATUS_NEW);
				 final int number=(int)eventId.size();
				 button_show.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							if(number>0){
							Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
							startActivity(intent);
							getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
							}else if(number==0){
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
			        if (!Validation.hasTextEditText(editText_otherCategory)) ret = false;
			        if (!Validation.hasTextTextView(dueDateValue)) ret = false;
			        if (!Validation.hasSelection(spinner_otherPeriod)) ret = false;
			        if (editText_otherNumber.isShown()&&!Validation.hasTextEditText(editText_otherNumber)) ret = false;
			        if (Validation.isDateAfter(start_date,due_date,startDateValue)) ret = false;
			        return ret;
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
	public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
}
}
