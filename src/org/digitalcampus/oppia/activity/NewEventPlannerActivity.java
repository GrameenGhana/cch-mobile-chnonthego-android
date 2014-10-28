package org.digitalcampus.oppia.activity;

import java.util.ArrayList;
import java.util.Calendar;
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
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.OnItemLongClickListener;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;


public class NewEventPlannerActivity extends SherlockFragmentActivity implements ActionBar.TabListener, OnSharedPreferenceChangeListener{
	 private DbHelper dbh;
	SectionsPagerAdapter mSectionsPagerAdapter;
	public String current_month;
	
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
        actionBar.setTitle("Target Setting");
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
		
	 public static class EventsActivity extends Fragment implements OnItemClickListener{

			private Context mContext;															
			private TextView textview_status;
			private ListView listView_events;
			private ArrayList<String> eventName;
			private ArrayList<String> eventNumber;
			private ArrayList<String> eventsId;
			private DbHelper db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private EventBaseAdapter events_adapter;
			private String[] selected_items;
			int selected_position;

			 public EventsActivity(){

            }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				 rootView=inflater.inflate(R.layout.activity_events,null,false);
			    mContext=getActivity().getApplicationContext();
			    //TypefaceUtil.overrideFont(mContext, "SERIF", "fonts/Roboto-Thin.ttf");
			    db=new DbHelper(getActivity());
			    textview_status=(TextView) rootView.findViewById(R.id.textView_eventsStatus);
			    listView_events=(ListView) rootView.findViewById(R.id.listView_events);
			    listView_events.setOnItemClickListener(this);
			    //registerForContextMenu(listView_events);
			    eventName=db.getAllEventName(month_passed);
			    eventNumber=db.getAllEventNumber(month_passed);
			    eventsId=db.getAllEventID(month_passed);
			    events_adapter=new EventBaseAdapter(mContext,eventName,eventNumber,eventsId);
			    events_adapter.notifyDataSetChanged();
			    listView_events.setAdapter(events_adapter);	
			    if(listView_events.getCount()>0){
			    	textview_status.setText(" ");	
			    }else if (listView_events.getCount()==0){
			    	textview_status.setText("You have not entered any events!");
			    }
			    listView_events.setOnItemClickListener(this);
			    listView_events.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		          
			    listView_events.setMultiChoiceModeListener(new MultiChoiceModeListener() {
		              
		            private int nr = 0;
		              
		            @Override
		            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		                
		                return false;
		            }
		              
		            @Override
		            public void onDestroyActionMode(ActionMode mode) {
		                
		            	events_adapter.clearSelection();
		            }
		              
		            @Override
		            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		            
		                  
		                nr = 0;
		                MenuInflater inflater = getActivity().getMenuInflater();
		                inflater.inflate(R.menu.context_menu, menu);
		                return true;
		            }
		              
		            @Override
		            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		                int itemId = item.getItemId();
						if (itemId == R.id.option1) {
							nr = 0;
							System.out.println(selected_position);
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
		            				getActivity());
							// set title
							alertDialogBuilder.setTitle("Delete event?");
							// set dialog message
							alertDialogBuilder
								.setMessage("You are about to delete an event. Proceed?")
								.setCancelable(false)
								.setIcon(R.drawable.ic_error)
								.setPositiveButton("No",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// if this button is clicked, close
										// current activity
										dialog.cancel();
									}
								  })
								.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										if(db.deleteEventCategory(selected_position)==true){
											getActivity().runOnUiThread(new Runnable() {
									            @Override
									            public void run() {
									            	
									            	 eventName=db.getAllEventName(month_passed);
									 			    eventNumber=db.getAllEventNumber(month_passed);
									 			    eventsId=db.getAllEventID(month_passed);
									 			    events_adapter=new EventBaseAdapter(mContext,eventName,eventNumber,eventsId);
									 			    events_adapter.notifyDataSetChanged();
									 			    listView_events.setAdapter(events_adapter);	
									            }
									        });
							    		 Toast.makeText(getActivity().getApplicationContext(), "Deleted!",
											         Toast.LENGTH_LONG).show();
							        	}
							        	
										events_adapter.clearSelection();
							          
									}
								});
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
							// show it
							alertDialog.show();
							mode.finish();
						}
		                return false;
		            }
		              
		            @Override	
		            public void onItemCheckedStateChanged(ActionMode mode, int position,
		                    long id, boolean checked) {
		                // TODO Auto-generated method stub
		                 if (checked) {
		                        nr++;
		                        selected_position=(int) id;
		                        
		                        events_adapter.setNewSelection(position, checked);                    
		                    } else {
		                        nr--;
		                        events_adapter.removeSelection(position);                 
		                    }
		                    mode.setTitle(nr + " selected");
		                    
		            }
		        });
		          
		      listView_events.setOnItemLongClickListener(new OnItemLongClickListener() {
		  
		            @Override
		            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		                    int position, long arg3) {
		                // TODO Auto-generated method stub
		                  
		            	listView_events.setItemChecked(position, !events_adapter.isPositionChecked(position));
		            	
		                return false;
		            }
		        });
		    
			   Button b = (Button) rootView.findViewById(R.id.button_addEvent);
			    b.setOnClickListener(new OnClickListener() {
			    	@Override
				       public void onClick(View v) {
			    		final Dialog dialog = new Dialog(getActivity());
						dialog.setContentView(R.layout.event_set_dialog);
						dialog.setTitle("Set Event Target");
						String[] items={"Daily","Monthly","Weekly","Yearly"};
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
						
						Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogSetEVent);
						dialogButton.setText("Save");
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								
								EditText editText_event_period=(EditText) dialog.findViewById(R.id.editText_dialogEventPeriodNumber);
								String event_period=spinner_event_period.getSelectedItem().toString();
								String event_name=spinner_event_name.getSelectedItem().toString();
								String event_period_number=editText_event_period.getText().toString();
							    if(db.insertEventSet(event_name, event_period, event_period_number, month_passed,"new_record") ==true){
							    	getActivity().runOnUiThread(new Runnable() {
							            @Override
							            public void run() {
							            	
							            	 eventName=db.getAllEventName(month_passed);
							 			    eventNumber=db.getAllEventNumber(month_passed);
							 			    eventsId=db.getAllEventID(month_passed);
							 			    events_adapter=new EventBaseAdapter(mContext,eventName,eventNumber,eventsId);
							 			    events_adapter.notifyDataSetChanged();
							 			    listView_events.setAdapter(events_adapter);	
							            }
							        });
							    
							    	 Toast.makeText(getActivity().getApplicationContext(), "Event set successfully!",
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
			public void onItemClick(AdapterView<?> parent, View view, int position,
					final long id) {
				selected_items=events_adapter.getItem(position);
				System.out.println(selected_items[0]+" "+selected_items[1]);
				final Dialog dialog = new Dialog(getActivity());
				dialog.setContentView(R.layout.event_set_dialog);
				dialog.setTitle("Edit Event");
				final EditText editText_eventNumber=(EditText) dialog.findViewById(R.id.editText_dialogEventPeriodNumber);
				String[] items={"Daily","Monthly","Weekly","Yearly"};
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
				final Spinner spinner_event_name=(Spinner) dialog.findViewById(R.id.spinner_eventName);
				final Spinner spinner_eventPeriod=(Spinner) dialog.findViewById(R.id.spinner_dialogEventPeriod);
				spinner_eventPeriod.setAdapter(adapter);
				String[] items_names={"ANC Static","ANC Outreach","CWC Static","CWC Outreach",
						"PNC Clinic","Routine Home visit","Special Home visit",
						"Family Planning","Health Talk","CMAM Clinic","School Health",
						"Adolescent Health","Mop-up Activity/Event","Community Durbar",
						"National Activity/Event","Staff meetings/durbars","Workshops","Leave/Excuse Duty",
						"Personal","Other"};
				ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items_names);
				spinner_event_name.setAdapter(adapter2);
				int spinner_position=adapter2.getPosition(selected_items[0]);
				spinner_event_name.setSelection(spinner_position);
				editText_eventNumber.setText(selected_items[1]);
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogSetEVent);
				dialogButton.setText("Save");
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						
						String event_name=spinner_event_name.getSelectedItem().toString();
						String event_number=editText_eventNumber.getText().toString();
						String event_period=spinner_eventPeriod.getSelectedItem().toString();
					    if(db.editEventCategory(event_name, event_number, event_period, id)==true){
					    	getActivity().runOnUiThread(new Runnable() {
					            @Override
					            public void run() {
					            	eventName=db.getAllEventName(month_passed);
					 			    eventNumber=db.getAllEventNumber(month_passed);
					 			    eventsId=db.getAllEventID(month_passed);
					 			    events_adapter=new EventBaseAdapter(mContext,eventName,eventNumber,eventsId);
					 			    events_adapter.notifyDataSetChanged();
					 			    listView_events.setAdapter(events_adapter);	
					            }
					        });	
					    	 Toast.makeText(getActivity().getApplicationContext(), "Event edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
				
	 				dialog.show();
		}
	 
	 }
	 public static class CoverageActivity extends Fragment implements OnChildClickListener{

			private Context mContext;															
			private ArrayList<String> coveragePeopleTarget;
			private ArrayList<String> coveragePeopleNumber;
			private ArrayList<String> coveragePeoplePeriod;
			private ArrayList<String> coverageImmunzationTarget;
			private ArrayList<String> coverageImmunzationNumber;
			private ArrayList<String> coverageImmunzationPeriod;
			private ArrayList<String> coveragePeopleId;
			private ArrayList<String> coverageImmunizationId;
			private DbHelper db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private ExpandableListView listView_coverage;
			private TextView textStatus;
			private String[] group={"People","Immunizations"};
			private int[] imageId={R.drawable.ic_people,R.drawable.ic_syringe};
			private CoverageListAdapter coverage_adapter;
			private String[] selected_items;
			private RadioGroup category_options;
			private String[] items3;
			int selected_position;
			 public CoverageActivity(){

         }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				 rootView=inflater.inflate(R.layout.activity_coverage,null,false);
			    mContext=getActivity().getApplicationContext();
			    db=new DbHelper(getActivity());
			    coveragePeopleTarget=db.getAllCoveragePeopleTarget(month_passed);
			    coveragePeopleNumber=db.getAllCoveragePeopleNumber(month_passed);
			    coveragePeoplePeriod=db.getAllCoveragePeoplePeriod(month_passed);
			    
			    coverageImmunzationTarget=db.getAllCoverageImmunizationsTarget(month_passed);
			    coverageImmunzationNumber=db.getAllCoverageImmunizationsNumber(month_passed);
			    coverageImmunzationPeriod=db.getAllCoverageImmunizationsPeriod(month_passed);
			    coveragePeopleId=db.getAllCoveragePeopleId(month_passed);
			    coverageImmunizationId=db.getAllCoverageImmunizationsId(month_passed);
			    listView_coverage=(ExpandableListView) rootView.findViewById(R.id.expandableListView1);
			    listView_coverage.setOnChildClickListener(this);
			    //registerForContextMenu(listView_coverage);
			    textStatus=(TextView) rootView.findViewById(R.id.textView_coverageStatus);
			    coverage_adapter=new CoverageListAdapter(getActivity(),group,coveragePeopleTarget,coveragePeopleNumber,
			    																		coveragePeoplePeriod,coverageImmunzationTarget,
			    																		coverageImmunzationNumber,coverageImmunzationPeriod,
			    																		coveragePeopleId,coverageImmunizationId,
			    																		imageId,listView_coverage);
			    coverage_adapter.notifyDataSetChanged();
		    	listView_coverage.setAdapter(coverage_adapter);	
			    if(listView_coverage.getChildCount()>0){
			    	textStatus.setText(" ");
			    	
			    }else if (listView_coverage.getChildCount()==0){
			    	textStatus.setText(" ");
			    }
			  
			    Button b = (Button) rootView.findViewById(R.id.button_addCoverage);
			    b.setOnClickListener(new OnClickListener() {
			    	 String coverage_detail;
			       @Override
			       public void onClick(View v) {
			    	   final Dialog dialog = new Dialog(getActivity());
						dialog.setContentView(R.layout.coverage_add_dialog);
						final Spinner spinner_coverageName=(Spinner) dialog.findViewById(R.id.spinner_dialogCoverageName);
						dialog.setTitle("Add Coverage");
						
						  category_options=(RadioGroup) dialog.findViewById(R.id.radioGroup_category);
						  category_options.check(R.id.radio_people);
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
						String[] items2={"Daily","Monthly","Weekly"};
					
						listView_coverage.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
				          
						listView_coverage.setMultiChoiceModeListener(new MultiChoiceModeListener() {
				              
				            private int nr = 0;
				              
				            @Override
				            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				                
				                return false;
				            }
				              
				            @Override
				            public void onDestroyActionMode(ActionMode mode) {
				                
				            	coverage_adapter.clearSelection();
				            }
				              
				            @Override
				            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				            
				                  
				                nr = 0;
				                MenuInflater inflater = getActivity().getMenuInflater();
				                inflater.inflate(R.menu.context_menu, menu);
				                return true;
				            }
				              
				            @Override
				            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				                int itemId = item.getItemId();
								if (itemId == R.id.option1) {
									nr = 0;
									System.out.println(selected_position);
									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				            				getActivity());
									// set title
									alertDialogBuilder.setTitle("Delete event?");
									// set dialog message
									alertDialogBuilder
										.setMessage("You are about to delete an event. Proceed?")
										.setCancelable(false)
										.setIcon(R.drawable.ic_error)
										.setPositiveButton("No",new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,int id) {
												// if this button is clicked, close
												// current activity
												dialog.cancel();
											}
										  })
										.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,int id) {
												// if this button is clicked, just close
												// the dialog box and do nothing
												if(db.deleteCoverageCategory(selected_position)==true){
													getActivity().runOnUiThread(new Runnable() {
											            @Override
											            public void run() {
											            	
											            	coveragePeopleTarget=db.getAllCoveragePeopleTarget(month_passed);
														    coveragePeopleNumber=db.getAllCoveragePeopleNumber(month_passed);
														    coveragePeoplePeriod=db.getAllCoveragePeoplePeriod(month_passed);
														    
														    coverageImmunzationTarget=db.getAllCoverageImmunizationsTarget(month_passed);
														    coverageImmunzationNumber=db.getAllCoverageImmunizationsNumber(month_passed);
														    coverageImmunzationPeriod=db.getAllCoverageImmunizationsPeriod(month_passed);
														    coveragePeopleId=db.getAllCoveragePeopleId(month_passed);
														    coverageImmunizationId=db.getAllCoverageImmunizationsId(month_passed);
														    listView_coverage=(ExpandableListView) rootView.findViewById(R.id.expandableListView1);
														    coverage_adapter=new CoverageListAdapter(getActivity(),group,coveragePeopleTarget,coveragePeopleNumber,
																	coveragePeoplePeriod,coverageImmunzationTarget,
																	coverageImmunzationNumber,coverageImmunzationPeriod,
																	coveragePeopleId,coverageImmunizationId,
																	imageId,listView_coverage);
														    coverage_adapter.notifyDataSetChanged();
														    listView_coverage.setAdapter(coverage_adapter);	
											            }
											        });	
									    		 Toast.makeText(getActivity().getApplicationContext(), "Deleted!",
													         Toast.LENGTH_LONG).show();
									        	}
									        	
												coverage_adapter.clearSelection();
									          
											}
										});
									// create alert dialog
									AlertDialog alertDialog = alertDialogBuilder.create();
									// show it
									alertDialog.show();
									mode.finish();
								}
				                return false;
				            }
				              
				            @Override	
				            public void onItemCheckedStateChanged(ActionMode mode, int position,
				                    long id, boolean checked) {
				                // TODO Auto-generated method stub
				                 if (checked) {
				                        nr++;
				                        selected_position=(int) id;
				                        
				                        coverage_adapter.setNewSelection(position, checked);                    
				                    } else {
				                        nr--;
				                        coverage_adapter.removeSelection(position);                 
				                    }
				                    mode.setTitle(nr + " selected");
				                    
				            }
				        });
						
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
							    if(db.insertCoverageSet(coverage_name, coverage_detail, coverage_period, coverage_number, month_passed,"new_record") ==true){
							    	getActivity().runOnUiThread(new Runnable() {
							            @Override
							            public void run() {
							            	
							            	coveragePeopleTarget=db.getAllCoveragePeopleTarget(month_passed);
										    coveragePeopleNumber=db.getAllCoveragePeopleNumber(month_passed);
										    coveragePeoplePeriod=db.getAllCoveragePeoplePeriod(month_passed);
										    
										    coverageImmunzationTarget=db.getAllCoverageImmunizationsTarget(month_passed);
										    coverageImmunzationNumber=db.getAllCoverageImmunizationsNumber(month_passed);
										    coverageImmunzationPeriod=db.getAllCoverageImmunizationsPeriod(month_passed);
										    coveragePeopleId=db.getAllCoveragePeopleId(month_passed);
										    coverageImmunizationId=db.getAllCoverageImmunizationsId(month_passed);
										    listView_coverage=(ExpandableListView) rootView.findViewById(R.id.expandableListView1);
										    coverage_adapter=new CoverageListAdapter(getActivity(),group,coveragePeopleTarget,coveragePeopleNumber,
													coveragePeoplePeriod,coverageImmunzationTarget,
													coverageImmunzationNumber,coverageImmunzationPeriod,
													coveragePeopleId,coverageImmunizationId,
													imageId,listView_coverage);
										    coverage_adapter.notifyDataSetChanged();
										    listView_coverage.setAdapter(coverage_adapter);	
							            }
							        });	
							    	 Toast.makeText(getActivity().getApplicationContext(), "Coverage added successfully!",
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
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, final long id) {
				final Dialog dialog = new Dialog(getActivity());
				selected_items=coverage_adapter.getChild(groupPosition,childPosition);
				dialog.setContentView(R.layout.coverage_add_dialog);
				final Spinner spinner_coverageName=(Spinner) dialog.findViewById(R.id.spinner_dialogCoverageName);
				dialog.setTitle("Edit Coverage");
			
				 category_options=(RadioGroup) dialog.findViewById(R.id.radioGroup_category);
				  category_options.check(R.id.radio_people);
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
								int spinner_position=adapter3.getPosition(selected_items[0]);
								spinner_coverageName.setSelection(spinner_position);
							} else if (isChecked == R.id.radio_immunization) {
								items3=new String[]{"BCG",
										"Penta 3","OPV 3","Rota 2",
										"PCV 3","Measles Rubella","Yellow fever"};
								ArrayAdapter<String> adapter4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
								spinner_coverageName.setAdapter(adapter4);
								int spinner_position2=adapter4.getPosition(selected_items[0]);
								spinner_coverageName.setSelection(spinner_position2);
							}	
						}
				    });
				String[] items2={"Daily","Monthly","Weekly"};
				final Spinner spinner_coveragePeriod=(Spinner) dialog.findViewById(R.id.spinner_coveragePeriod);
				ArrayAdapter<String> spinner_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items2);
				spinner_coveragePeriod.setAdapter(spinner_adapter);
				
				final EditText editText_coverageNumber=(EditText) dialog.findViewById(R.id.editText_dialogCoverageNumber);
				editText_coverageNumber.setText(selected_items[1]);
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddCoverage);
				dialogButton.setText("Save");
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						
						String coverage_name=spinner_coverageName.getSelectedItem().toString();
						String coverage_period=spinner_coveragePeriod.getSelectedItem().toString();
						String coverage_number=editText_coverageNumber.getText().toString();
					    if(db.editCoverage(coverage_name, coverage_number, coverage_period, id) ==true){
					    	getActivity().runOnUiThread(new Runnable() {
					            @Override
					            public void run() {
					            	
					            	coveragePeopleTarget=db.getAllCoveragePeopleTarget(month_passed);
								    coveragePeopleNumber=db.getAllCoveragePeopleNumber(month_passed);
								    coveragePeoplePeriod=db.getAllCoveragePeoplePeriod(month_passed);
								    
								    coverageImmunzationTarget=db.getAllCoverageImmunizationsTarget(month_passed);
								    coverageImmunzationNumber=db.getAllCoverageImmunizationsNumber(month_passed);
								    coverageImmunzationPeriod=db.getAllCoverageImmunizationsPeriod(month_passed);
								    coveragePeopleId=db.getAllCoveragePeopleId(month_passed);
								    coverageImmunizationId=db.getAllCoverageImmunizationsId(month_passed);
								    listView_coverage=(ExpandableListView) rootView.findViewById(R.id.expandableListView1);
								    coverage_adapter=new CoverageListAdapter(getActivity(),group,coveragePeopleTarget,coveragePeopleNumber,
											coveragePeoplePeriod,coverageImmunzationTarget,
											coverageImmunzationNumber,coverageImmunzationPeriod,
											coveragePeopleId,coverageImmunizationId,
											imageId,listView_coverage);
								    coverage_adapter.notifyDataSetChanged();
								    listView_coverage.setAdapter(coverage_adapter);	
					            }
					        });	
					    	 Toast.makeText(getActivity().getApplicationContext(), "Coverage edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
	 				dialog.show();
				return true;
			}
			
	 }
	 
	 public static class LearningActivity extends Fragment implements OnChildClickListener{

			private Context mContext;															
			
			 public ArrayList<String> AntenatalCare;
			 public ArrayList<String> PostnatalCare;
			 public ArrayList<String> FamilyPlanning;
			 public ArrayList<String> ChildHealth;
			 public ArrayList<String> General;
			 public ArrayList<String> Other;
			 public ExpandableListView learningList;
			 private DbHelper db;
			 private LearningBaseAdapter learning_adapter;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			 View rootView;
			private TextView textStatus;

			private String selected_item;

			private String[] groupItem;

			private int[] imageId;
			int selected_position;
			 public LearningActivity(){

      }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				rootView=inflater.inflate(R.layout.activity_learning,null,false);
			    mContext=getActivity().getApplicationContext();
			    db=new DbHelper(getActivity());
			    learningList=(ExpandableListView) rootView.findViewById(R.id.expandableListView_learningCategory);
			    AntenatalCare=db.getAllLearningAntenatalCare();
			    PostnatalCare=db.getAllLearningPostnatalCare();
			    FamilyPlanning=db.getAllLearningFamilyPlanning(month_passed);
			    ChildHealth=db.getAllLearningChildHealth();
			    General=db.getAllLearningGeneral();
			    Other=db.getAllLearningOther();
			    textStatus=(TextView) rootView.findViewById(R.id.textView_learningStatus);
			   groupItem=new String[]{"Family Planning"};
			   imageId=new int[]{R.drawable.ic_family};
		   
			   learning_adapter=new LearningBaseAdapter(getActivity(),groupItem,//AntenatalCare,
				   													//PostnatalCare,
				   													FamilyPlanning,
				   													//ChildHealth,
				   													//General,
				   													//Other,
				   													imageId,learningList);
			   learning_adapter.notifyDataSetChanged();
	    	learningList.setAdapter(learning_adapter);	
	    	learningList.setOnChildClickListener(this);
		   	if(learningList.getChildCount()>0){
		    	textStatus.setText(" ");
		    }else if (learningList.getChildCount()==0){
		    	textStatus.setText(" ");
		    }
			    //registerForContextMenu(learningList);
			    
			    textStatus=(TextView) rootView.findViewById(R.id.textView_learningStatus);
			    learningList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		          
			    learningList.setMultiChoiceModeListener(new MultiChoiceModeListener() {
		              
		            private int nr = 0;
		              
		            @Override
		            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		                
		                return false;
		            }
		              
		            @Override
		            public void onDestroyActionMode(ActionMode mode) {
		                
		            	learning_adapter.clearSelection();
		            }
		              
		            @Override
		            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		            
		                  
		                nr = 0;
		                MenuInflater inflater = getActivity().getMenuInflater();
		                inflater.inflate(R.menu.context_menu, menu);
		                return true;
		            }
		              
		            @Override
		            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		                int itemId = item.getItemId();
						if (itemId == R.id.option1) {
							nr = 0;
							System.out.println(selected_position);
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
		            				getActivity());
							// set title
							alertDialogBuilder.setTitle("Delete event?");
							// set dialog message
							alertDialogBuilder
								.setMessage("You are about to delete an event. Proceed?")
								.setCancelable(false)
								.setIcon(R.drawable.ic_error)
								.setPositiveButton("No",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// if this button is clicked, close
										// current activity
										dialog.cancel();
									}
								  })
								.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										if(db.deleteLearningCategory(selected_position)==true){
											getActivity().runOnUiThread(new Runnable() {
												@Override
									            public void run() {
									            	
									            	AntenatalCare=db.getAllLearningAntenatalCare();
												    PostnatalCare=db.getAllLearningPostnatalCare();
												    FamilyPlanning=db.getAllLearningFamilyPlanning(month_passed);
												    ChildHealth=db.getAllLearningChildHealth();
												    General=db.getAllLearningGeneral();
												    Other=db.getAllLearningOther();
												    learning_adapter=new LearningBaseAdapter(getActivity(),groupItem,//AntenatalCare,
															//PostnatalCare,
															FamilyPlanning,
															//ChildHealth,
															//General,
															//Other,
															imageId,learningList);
												    learning_adapter.notifyDataSetChanged();
												    learningList.setAdapter(learning_adapter);	
									            }
									        });	
							    		 Toast.makeText(getActivity().getApplicationContext(), "Deleted!",
											         Toast.LENGTH_LONG).show();
							        	}
							        	
										learning_adapter.clearSelection();
							          
									}
								});
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
							// show it
							alertDialog.show();
							mode.finish();
						}
		                return false;
		            }
		              
		            @Override	
		            public void onItemCheckedStateChanged(ActionMode mode, int position,
		                    long id, boolean checked) {
		                // TODO Auto-generated method stub
		                 if (checked) {
		                        nr++;
		                        selected_position=(int) id;
		                        
		                        learning_adapter.setNewSelection(position, checked);                    
		                    } else {
		                        nr--;
		                        learning_adapter.removeSelection(position);                 
		                    }
		                    mode.setTitle(nr + " selected");
		                    
		            }
		        });
			    Button b = (Button) rootView.findViewById(R.id.button_addLearning);
			    b.setOnClickListener(new OnClickListener() {

			       @Override
			       public void onClick(View v) {
			    	   final Dialog dialog = new Dialog(getActivity());
						dialog.setContentView(R.layout.learning_add_dialog);
						dialog.setTitle("Add Learning Category");
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
								// TODO Auto-generated method stub
								
							}
							
						});
						
						Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddLearning);
						dialogButton.setText("Save");
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								
								Spinner editText_learningDescription=(Spinner) dialog.findViewById(R.id.spinner_learningDescription);
								String learning_category=spinner_learningCatagory.getSelectedItem().toString();
								String learning_description=editText_learningDescription.getSelectedItem().toString();
							    if(db.insertLearning(learning_category, learning_description,month_passed, "new_record") ==true){
							    	getActivity().runOnUiThread(new Runnable() {
										@Override
							            public void run() {
							            	
							            	AntenatalCare=db.getAllLearningAntenatalCare();
										    PostnatalCare=db.getAllLearningPostnatalCare();
										    FamilyPlanning=db.getAllLearningFamilyPlanning(month_passed);
										    ChildHealth=db.getAllLearningChildHealth();
										    General=db.getAllLearningGeneral();
										    Other=db.getAllLearningOther();
										    learning_adapter=new LearningBaseAdapter(getActivity(),groupItem,//AntenatalCare,
   													//PostnatalCare,
   													FamilyPlanning,
   													//ChildHealth,
   													//General,
   													//Other,
   													imageId,learningList);
										    learning_adapter.notifyDataSetChanged();
										    learningList.setAdapter(learning_adapter);	
							            }
							        });	
							    	 Toast.makeText(getActivity().getApplicationContext(), "Added successfully!",
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
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, final long id) {
					selected_item=learning_adapter.getChild(groupPosition, childPosition);
				 	final Dialog dialog = new Dialog(getActivity());
					dialog.setContentView(R.layout.learning_add_dialog);
					dialog.setTitle("Edit Learning Category");
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
							// TODO Auto-generated method stub
							
						}
						
					});
					
					int spinner_position=adapter2.getPosition(selected_item);
					spinner_learningDescription.setSelection(spinner_position);
					Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddLearning);
					dialogButton.setText("Save");
					dialogButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
							
							String learning_category=spinner_learningCatagory.getSelectedItem().toString();
							String learning_description=spinner_learningDescription.getSelectedItem().toString();
						    if(db.editLearning(learning_category, learning_description, id) ==true){
						    	getActivity().runOnUiThread(new Runnable() {
									@Override
						            public void run() {
						            	
						            	AntenatalCare=db.getAllLearningAntenatalCare();
									    PostnatalCare=db.getAllLearningPostnatalCare();
									    FamilyPlanning=db.getAllLearningFamilyPlanning(month_passed);
									    ChildHealth=db.getAllLearningChildHealth();
									    General=db.getAllLearningGeneral();
									    Other=db.getAllLearningOther();
									    learning_adapter=new LearningBaseAdapter(getActivity(),groupItem,//AntenatalCare,
													//PostnatalCare,
													FamilyPlanning,
													//ChildHealth,
													//General,
													//Other,
													imageId,learningList);
									    learning_adapter.notifyDataSetChanged();
									    learningList.setAdapter(learning_adapter);	
						            }
						        });	
						    	 Toast.makeText(getActivity().getApplicationContext(), "Edited successfully!",
								         Toast.LENGTH_LONG).show();
						    }else{
						    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
								         Toast.LENGTH_LONG).show();
						    }
						}
					});
		 				dialog.show();
				return true;
			}
			
	 }
	 
	 public static class OtherActivity extends Fragment implements OnItemClickListener{

			private Context mContext;															
			private ArrayList<String> otherCategory;
			 private ArrayList<String> otherNumber;
			 private ArrayList<String> otherPeriod;
			private ArrayList<String> otherId;
			private DbHelper db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private ListView listView_other;
			private TextView textStatus;
			private OtherBaseAdapter other_adapter;
			int selected_position;
			 public OtherActivity(){

   }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				 rootView=inflater.inflate(R.layout.activity_other,null,false);
			    mContext=getActivity().getApplicationContext();
			    db=new DbHelper(getActivity());
			    otherCategory=db.getAllOtherCategory(month_passed);
			    otherNumber=db.getAllOtherNumber(month_passed);
			    otherPeriod=db.getAllOtherPeriod(month_passed);
			    otherId=db.getAllOthersId(month_passed);
			    listView_other=(ListView) rootView.findViewById(R.id.listView_other);
			    listView_other.setOnItemClickListener(this);
			
			   textStatus=(TextView) rootView.findViewById(R.id.textView_otherStatus);
			   listViewPopulate();
			   listView_other.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		          
			   listView_other.setMultiChoiceModeListener(new MultiChoiceModeListener() {
		              
		            private int nr = 0;
		              
		            @Override
		            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		                
		                return false;
		            }
		              
		            @Override
		            public void onDestroyActionMode(ActionMode mode) {
		                
		            	other_adapter.clearSelection();
		            }
		              
		            @Override
		            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		            
		                  
		                nr = 0;
		                MenuInflater inflater = getActivity().getMenuInflater();
		                inflater.inflate(R.menu.context_menu, menu);
		                return true;
		            }
		              
		            @Override
		            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		                int itemId = item.getItemId();
						if (itemId == R.id.option1) {
							nr = 0;
							System.out.println(selected_position);
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
		            				getActivity());
							// set title
							alertDialogBuilder.setTitle("Delete event?");
							// set dialog message
							alertDialogBuilder
								.setMessage("You are about to delete an event. Proceed?")
								.setCancelable(false)
								.setIcon(R.drawable.ic_error)
								.setPositiveButton("No",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// if this button is clicked, close
										// current activity
										dialog.cancel();
									}
								  })
								.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										if(db.deleteOthereCategory(selected_position)==true){
											getActivity().runOnUiThread(new Runnable() {
												@Override
									            public void run() {
													otherCategory=db.getAllOtherCategory(month_passed);
												    otherNumber=db.getAllOtherNumber(month_passed);
												    otherPeriod=db.getAllOtherPeriod(month_passed);
												    otherId=db.getAllOthersId(month_passed);
												    other_adapter=new OtherBaseAdapter(getActivity(),otherCategory,otherNumber,otherPeriod,otherId);
											    	other_adapter.notifyDataSetChanged();
											    	listView_other.setAdapter(other_adapter);	
									            }
									        });	
							    		 Toast.makeText(getActivity().getApplicationContext(), "Deleted!",
											         Toast.LENGTH_LONG).show();
							        	}
							        	
										other_adapter.clearSelection();
							          
									}
								});
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
							// show it
							alertDialog.show();
							mode.finish();
						}
		                return false;
		            }
		              
		            @Override	
		            public void onItemCheckedStateChanged(ActionMode mode, int position,
		                    long id, boolean checked) {
		                // TODO Auto-generated method stub
		                 if (checked) {
		                        nr++;
		                        selected_position=(int) id;
		                        
		                        other_adapter.setNewSelection(position, checked);                    
		                    } else {
		                        nr--;
		                        other_adapter.removeSelection(position);                 
		                    }
		                    mode.setTitle(nr + " selected");
		                    
		            }
		        });
			    Button b = (Button) rootView.findViewById(R.id.button_addOther);
			    b.setOnClickListener(new OnClickListener() {

			       @Override
			       public void onClick(View v) {
			    	   final Dialog dialog = new Dialog(getActivity());
						dialog.setContentView(R.layout.event_add_dialog);
						dialog.setTitle("Add other Category");
						final EditText editText_otherCategory=(EditText) dialog.findViewById(R.id.editText_dialogOtherName);
						final Spinner spinner_otherPeriod=(Spinner) dialog.findViewById(R.id.spinner_dialogOtherPeriod);
						String[] items={"Daily","Monthly","Weekly"};
						ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
						spinner_otherPeriod.setAdapter(adapter);
						final EditText editText_otherNumber=(EditText) dialog.findViewById(R.id.editText_dialogOtherNumber);
						Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddEvent);
						dialogButton.setText("Save");
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								
								String other_category=editText_otherCategory.getText().toString();
								String other_number=editText_otherNumber.getText().toString();
								String other_period=spinner_otherPeriod.getSelectedItem().toString();
							    if(db.insertOther(other_category,other_number,other_period,month_passed, "new_record") ==true){
							    	getActivity().runOnUiThread(new Runnable() {
										@Override
							            public void run() {
							            	
											otherCategory=db.getAllOtherCategory(month_passed);
										    otherNumber=db.getAllOtherNumber(month_passed);
										    otherPeriod=db.getAllOtherPeriod(month_passed);
										    otherId=db.getAllOthersId(month_passed);
										    other_adapter=new OtherBaseAdapter(getActivity(),otherCategory,otherNumber,otherPeriod,otherId);
									    	other_adapter.notifyDataSetChanged();
									    	listView_other.setAdapter(other_adapter);	
							            }
							        });	
							    	 Toast.makeText(getActivity().getApplicationContext(), "Added successfully!",
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
			
			public boolean listViewPopulate(){
				 if(otherCategory.size()>0){
				    	textStatus.setText(" ");
				    	other_adapter=new OtherBaseAdapter(getActivity(),otherCategory,otherNumber,otherPeriod,otherId);
				    	other_adapter.notifyDataSetChanged();
				    	listView_other.setAdapter(other_adapter);	
				    }else if (otherCategory.size()==0){
				    	textStatus.setText("No other categories found!");
				    }
				    
				
				return true;
			}
			
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, final long id) {
				String[] selected_items=other_adapter.getItem(position);
				final Dialog dialog = new Dialog(getActivity());
				dialog.setContentView(R.layout.event_add_dialog);
				dialog.setTitle("Edit other Category");
				final EditText editText_otherCategory=(EditText) dialog.findViewById(R.id.editText_dialogOtherName);
				editText_otherCategory.setText(selected_items[0]);
				final Spinner spinner_otherPeriod=(Spinner) dialog.findViewById(R.id.spinner_dialogOtherPeriod);
				String[] items={"Daily","Monthly","Weekly","Yearly"};
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
				spinner_otherPeriod.setAdapter(adapter);
				int spinner_position=adapter.getPosition(selected_items[2]);
				spinner_otherPeriod.setSelection(spinner_position);
				final EditText editText_otherNumber=(EditText) dialog.findViewById(R.id.editText_dialogOtherNumber);
				editText_otherNumber.setText(selected_items[1]);
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddEvent);
				dialogButton.setText("Save");
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						
						String other_category=editText_otherCategory.getText().toString();
						String other_number=editText_otherNumber.getText().toString();
						String other_period=spinner_otherPeriod.getSelectedItem().toString();
					    if(db.editOther(other_category, other_number, other_period, id) ==true){
					    	getActivity().runOnUiThread(new Runnable() {
								@Override
					            public void run() {
									otherCategory=db.getAllOtherCategory(month_passed);
								    otherNumber=db.getAllOtherNumber(month_passed);
								    otherPeriod=db.getAllOtherPeriod(month_passed);
								    otherId=db.getAllOthersId(month_passed);
								    other_adapter=new OtherBaseAdapter(getActivity(),otherCategory,otherNumber,otherPeriod,otherId);
							    	other_adapter.notifyDataSetChanged();
							    	listView_other.setAdapter(other_adapter);	
					            }
					        });	
					    	 Toast.makeText(getActivity().getApplicationContext(), "Edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
	 				dialog.show();
				
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
