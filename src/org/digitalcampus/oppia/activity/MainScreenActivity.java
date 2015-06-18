package org.digitalcampus.oppia.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.model.User;
import org.digitalcampus.oppia.service.TrackerService;
import org.grameenfoundation.adapters.EventsDetailPagerAdapter;
import org.grameenfoundation.adapters.MainScreenBaseAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.activity.AchievementCenterActivity;
import org.grameenfoundation.cch.activity.AchievementSummaryActivity;
import org.grameenfoundation.cch.activity.EventPlannerOptionsActivity;
import org.grameenfoundation.cch.activity.EventUpdateActivity;
import org.grameenfoundation.cch.activity.LearningCenterMenuActivity;
import org.grameenfoundation.cch.activity.StayingWellActivity;
import org.grameenfoundation.cch.activity.UpdateTargetActivity;
import org.grameenfoundation.poc.PointOfCareActivity;
import org.grameenfoundation.schedulers.EventUpdateService;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.LearningTargets;
import org.grameenfoundation.cch.model.MyCalendarEvents;
import org.grameenfoundation.cch.model.RoutineActivity;
import org.grameenfoundation.cch.model.RoutineActivityDetails;
import org.grameenfoundation.cch.model.Survey;
import org.grameenfoundation.cch.popupquestions.RunForm;
import org.grameenfoundation.cch.popupquestions.XmlGui;
import org.grameenfoundation.cch.tasks.CourseDetailsTask;
import org.grameenfoundation.cch.tasks.SurveyNotifyTask;
import org.grameenfoundation.cch.tasks.UserDetailsProcessTask;
import org.grameenfoundation.cch.utils.CCHTimeUtil;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract.Events;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainScreenActivity extends FragmentActivity implements OnSharedPreferenceChangeListener {

	private ListView main_menu_listview;
	private static Context mContext;
	private static TextView status;
	public static final String TAG = MainScreenActivity.class.getSimpleName();

	SectionsPagerAdapter mSectionsPagerAdapter;
    static ViewPager mViewPager;
	private static DbHelper dbh;
	private SharedPreferences prefs;
	private LinearLayout planner;
	private LinearLayout poc;
	private LinearLayout achievements;
	private LinearLayout stayingWell;
	private LinearLayout learning;
	private TextView events;
	private ArrayList<MyCalendarEvents> TodayCalendarEvents;
	private CalendarEvents c;
	private Animation slide_up;
	private CCHTimeUtil timeUtils;
	private DateTime today;
	private String name;
	private RadioGroup reminder;
	private DateTime newReminderDate;
	private DateTime nextReminderDate;
	private DateTime reminderDate;
	private ArrayList<Survey> surveyData;
	private LocalDate date;
	private LocalDate date2;
	public static Dialog dialog ;
	
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
	    setContentView(R.layout.activity_new_mainscreen);
	    mContext=MainScreenActivity.this;
	    getActionBar().setDisplayShowHomeEnabled(true);
	    getActionBar().setTitle("Welcome");
	    getActionBar().setSubtitle("Home Page");
	    c= new CalendarEvents(mContext);
	    TodayCalendarEvents=new ArrayList<MyCalendarEvents>();
	    TodayCalendarEvents=c.getTodaysEvents(false);
		dbh = new DbHelper(getApplicationContext());
		timeUtils=new CCHTimeUtil();
		today=new DateTime();
		 try{
			dbh.alterTables();
			dbh.deleteTables();
			dbh.alterCourseTable();
			dbh.updateDateDefault();
			dbh.alterEventTable();
			dbh.updateEventDetailDefault();
			dbh.alterOtherTable();
			dbh.updateOtherDetailDefault();
			dbh.alterUserTable(); 
			dbh.alterCourseTableGroup();
			prefs = PreferenceManager.getDefaultSharedPreferences(this);
			name=prefs.getString("first_name", "name");
			 	//SurveyNotifyTask omSurveyNotifyTask = new SurveyNotifyTask(this);
			 	//omSurveyNotifyTask.execute();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 if(isOnline()){
				try{
					UserDetailsProcessTask usd = new UserDetailsProcessTask(this);
					usd.execute(new String[] { getResources().getString(R.string.serverDefaultAddress)+"/"+MobileLearning.CCH_USER_DETAILS_PATH+name});
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		 if(isOnline()){
				try{
					//
			if(dbh.getCourseGroups()>=0){
						CourseDetailsTask courseDetails = new CourseDetailsTask(this);
						courseDetails.execute(new String[] { getResources().getString(R.string.serverDefaultAddress)+"/"+MobileLearning.CCH_COURSE_DETAILS_PATH});
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		try{
			date=new LocalDate();
			date2=new LocalDate();
			DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
			DateTimeFormatter formatter2 = DateTimeFormat.forPattern("dd-MM-yyyy");
				ArrayList<User> user_role = dbh.getUserFirstName(name);
				//ArrayList<Survey> surveys = dbh.getSurveys();
				surveyData = dbh.getSurveyDetails(date2.toString("dd-MM-yyyy"));
				reminderDate=formatter.parseDateTime(surveyData.get(0).getSurveyReminderDate());
				if(!surveyData.get(0).getSurveyNextReminderDate().equals("")){
					nextReminderDate=formatter.parseDateTime(surveyData.get(0).getSurveyNextReminderDate());
				}
				System.out.println("Reminder date: "+reminderDate.toString("dd-MM-yyyy HH:mm"));
				System.out.println("Today's date: "+date2.toString("dd-MM-yyyy"));
				newReminderDate=new DateTime();
			if(user_role.get(0).getUserrole().equals("chn")||user_role.get(0).getUserrole().equals("Supervisor")){
				if(surveyData.get(0).getSurveyNextReminderDate().equals("")){
					if(reminderDate.toString("dd-MM-yyyy HH:mm").contains(date2.toString("dd-MM-yyyy"))&&surveyData.get(0).getSurveyStatus().equals("")){
						System.out.println("Reminder date: "+reminderDate.toString("dd-MM-yyyy HH:mm"));
						System.out.println("Today's date: "+date2.toString("dd-MM-yyyy"));
						dialog = new Dialog(MainScreenActivity.this);
						dialog.setContentView(R.layout.survey_popup_dialog);
						dialog.setTitle("Satisfaction Survey");
						dialog.setCancelable(false);
						Button next=(Button) dialog.findViewById(R.id.button_next);
						Button close=(Button) dialog.findViewById(R.id.button_cancel);
						TextView instruction=(TextView) dialog.findViewById(R.id.textView_instruct);
						String first="<font color='#53AB20'>Click next to take the </font>";
						String second="<font color='#520000'>Satisfaction Survey. </font>";
						String third="<font color='#53AB20'> If you would like to do this another time, select one of the reminders below and click close. "
									+ "If you would like to access the survey before the reminder selected, go to the menu on this page to access the survey. </font>";
						instruction.setText(Html.fromHtml(first+second+third));
						final TextView remindMe=(TextView) dialog.findViewById(R.id.textView2);
								reminder=(RadioGroup) dialog.findViewById(R.id.radioGroup_reminder);
						close.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								if(reminder.getCheckedRadioButtonId()==-1){
									remindMe.setFocusable(true);
									remindMe.setFocusableInTouchMode(true);
								remindMe.requestFocus();
								remindMe.setError("Please select a reminder");
								}else{
									if(reminder.getCheckedRadioButtonId()==R.id.radio0){
										newReminderDate=reminderDate.plusHours(1);
										dbh.updateSurveyReminder(surveyData.get(0).getSurveyReminderDate(), newReminderDate.toString("dd-MM-yyyy HH:mm"),Integer.valueOf(surveyData.get(0).getSurveyId()));
										dialog.dismiss();
										Crouton.makeText(MainScreenActivity.this, "Thank you!", Style.CONFIRM).show();
									}else if(reminder.getCheckedRadioButtonId()==R.id.radio1){
										newReminderDate=reminderDate.plusHours(6);
										dbh.updateSurveyReminder(surveyData.get(0).getSurveyReminderDate(), newReminderDate.toString("dd-MM-yyyy HH:mm"),Integer.valueOf(surveyData.get(0).getSurveyId()));
										dialog.dismiss();
										Crouton.makeText(MainScreenActivity.this, "Thank you!", Style.CONFIRM).show();
									}else if(reminder.getCheckedRadioButtonId()==R.id.radio2){
										newReminderDate=reminderDate.plusDays(1);
										dbh.updateSurveyReminder(surveyData.get(0).getSurveyReminderDate(), newReminderDate.toString("dd-MM-yyyy HH:mm"),Integer.valueOf(surveyData.get(0).getSurveyId()));
										dialog.dismiss();
										Crouton.makeText(MainScreenActivity.this, "Thank you!", Style.CONFIRM).show();
									}
								}
							}
							
						});
						next.setOnClickListener(new OnClickListener(){
							@Override
							public void onClick(View v) {
								try{
									Intent intent=new Intent(MainScreenActivity.this, RunForm.class);
									intent.putExtra("date",date2.toString("dd-MM-yyyy"));
									startActivity(intent);
									dialog.dismiss();
								}catch(Exception e){
									e.printStackTrace();
								}
								
							}
							
						});
						dialog.show();
					}
					}else {
						if(date.toString("dd-MM-yyyy HH:mm").equals(surveyData.get(0).getSurveyNextReminderDate())&&surveyData.get(0).getSurveyStatus().equals("")){
							dialog = new Dialog(MainScreenActivity.this);
							dialog.setContentView(R.layout.survey_popup_dialog);
							dialog.setTitle("Satisfaction Survey");
							dialog.setCancelable(false);
							Button next=(Button) dialog.findViewById(R.id.button_next);
							Button close=(Button) dialog.findViewById(R.id.button_cancel);
							TextView instruction=(TextView) dialog.findViewById(R.id.textView_instruct);
							String first="<font color='#53AB20'>Click next to take the </font>";
							String second="<font color='#520000'>Satisfaction Survey.</font>";
							String third="<font color='#53AB20'> If you would like to do this another time, select one of the reminders below and click close. "
										+ "If you would like to access the survey before the reminder selected, go to the menu on this page to access the survey. </font>";
							instruction.setText(Html.fromHtml(first+second+third));
							final TextView remindMe=(TextView) dialog.findViewById(R.id.textView2);
									reminder=(RadioGroup) dialog.findViewById(R.id.radioGroup_reminder);
							close.setOnClickListener(new OnClickListener(){

								@Override
								public void onClick(View v) {
									if(reminder.getCheckedRadioButtonId()==-1){
										remindMe.setFocusable(true);
										remindMe.setFocusableInTouchMode(true);
									remindMe.requestFocus();
									remindMe.setError("Please select a reminder");
									}else{
										if(reminder.getCheckedRadioButtonId()==R.id.radio0){
											newReminderDate=reminderDate.plusHours(1);
											dbh.updateSurveyReminder(surveyData.get(0).getSurveyReminderDate(), newReminderDate.toString("dd-MM-yyyy HH:mm"),Integer.valueOf(surveyData.get(0).getSurveyId()));
											dialog.dismiss();
											Crouton.makeText(MainScreenActivity.this, "Thank you!", Style.CONFIRM).show();
										}else if(reminder.getCheckedRadioButtonId()==R.id.radio1){
											newReminderDate=reminderDate.plusHours(6);
											dbh.updateSurveyReminder(surveyData.get(0).getSurveyReminderDate(), newReminderDate.toString("dd-MM-yyyy HH:mm"),Integer.valueOf(surveyData.get(0).getSurveyId()));
											dialog.dismiss();
											Crouton.makeText(MainScreenActivity.this, "Thank you!", Style.CONFIRM).show();
										}else if(reminder.getCheckedRadioButtonId()==R.id.radio2){
											newReminderDate=reminderDate.plusDays(1);
											dbh.updateSurveyReminder(surveyData.get(0).getSurveyReminderDate(), newReminderDate.toString("dd-MM-yyyy HH:mm"),Integer.valueOf(surveyData.get(0).getSurveyId()));
											dialog.dismiss();
											Crouton.makeText(MainScreenActivity.this, "Thank you!", Style.CONFIRM).show();
										}
									}
									
									
								}
								
							});
							next.setOnClickListener(new OnClickListener(){
								@Override
								public void onClick(View v) {
									try{
										Intent intent=new Intent(MainScreenActivity.this, RunForm.class);
										intent.putExtra("date",date2.toString("dd-MM-yyyy"));
										startActivity(intent);
										dialog.dismiss();
									}catch(Exception e){
										e.printStackTrace();
									}
									
								}
								
							});
							dialog.show();
						}
					}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		if(isOnline()){
			try{
				//
		if(dbh.getCourseGroups()>=0){
					CourseDetailsTask courseDetails = new CourseDetailsTask(this);
					courseDetails.execute(new String[] { getResources().getString(R.string.serverDefaultAddress)+"/"+MobileLearning.CCH_COURSE_DETAILS_PATH});
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		prefs.registerOnSharedPreferenceChangeListener(this);
		PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
		
		Intent service = new Intent(this, TrackerService.class);
		Bundle tb = new Bundle();
		tb.putBoolean("backgroundData", true);
		service.putExtras(tb);
		this.startService(service);
		
		Intent service2 = new Intent(this, EventUpdateService.class);
		this.startService(service2);
		planner=(LinearLayout) findViewById(R.id.planner);
		poc=(LinearLayout) findViewById(R.id.poc);
		learning=(LinearLayout) findViewById(R.id.learning);
		stayingWell=(LinearLayout) findViewById(R.id.stayingWell);
		achievements=(LinearLayout) findViewById(R.id.achievements);
		events=(TextView) findViewById(R.id.textView_events);
		slide_up=AnimationUtils.loadAnimation(getApplicationContext(),
	              R.anim.slide_up);
		for(int i=0;i<TodayCalendarEvents.size();i++){
			//TextView nextevents=new TextView(this);
			if(TodayCalendarEvents.size()==0){
				events.setText("No planned events");
				events.setAnimation(slide_up);
			}else{
			events.setText(TodayCalendarEvents.get(i).getEventType());
			events.setAnimation(slide_up);
			}
		}
		planner.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, EventPlannerOptionsActivity.class);
				startActivity(intent);
				 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				
			}
		});
		
		poc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, PointOfCareActivity.class);
				startActivity(intent);
				 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				
			}
		});
		learning.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), LearningCenterMenuActivity.class);
	            startActivity(intent);	
	            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				
			}
		});
		stayingWell.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), StayingWellActivity.class);
				startActivity(intent);
				 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				
			}
		});
		achievements.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), AchievementCenterActivity.class);
	            startActivity(intent);	
	            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				
			}
		});
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
                	 fragment = new RoutineActivityDetails(mViewPager);
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
			private int eventId;
			private ArrayList<EventTargets> coverageId;
			private ArrayList<EventTargets> otherId;
			private ArrayList<LearningTargets> learningId;
		 private ArrayList<User> firstName;
		 public ArrayList<MyCalendarEvents> EventTypeToday;
		private int numactivities;
		 public EventsSummary(){
			 
		 }

		 
		 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			 	rootView=inflater.inflate(R.layout.events_pager_layout,null,false);
			 	prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
			    name=prefs.getString("first_name", "name");
			    dbh=new DbHelper(getActivity());
			    firstName=dbh.getUserFirstName(name);
			    status=(TextView) rootView.findViewById(R.id.textView_status);
			    event_number=(TextView) rootView.findViewById(R.id.textView_eventsNumber);
			    c= new CalendarEvents(mContext);
			    
			    EventTypeToday=c.getTodaysEvents(false);
			    
		    if(firstName.size()>0 &&firstName!=null){
			    	user_first_name=firstName.get(0).getFirstname();
			    }else if(firstName.size()==0&&firstName!=null){
			    	user_first_name.equals(" ");
			    }
			    
		    	status.setText("Good "+dbh.getTime()+", "+user_first_name+"!");
		 if(EventTypeToday.size()==0 &&EventTypeToday!=null){
				 event_number.setText("0"); 
			 }else {
				 event_number.setText(String.valueOf(EventTypeToday.size())); 
			 }
			 Calendar c = Calendar.getInstance();
		        int month=c.get(Calendar.MONTH)+1;
		        int day=c.get(Calendar.DAY_OF_WEEK);
		        int year=c.get(Calendar.YEAR);
		        due_date=day+"-"+month+"-"+year;
	
				textView_eventTargetsNumber=(TextView) rootView.findViewById(R.id.textView_eventTargetsNumber);
				textView_clickHere=(TextView) rootView.findViewById(R.id.textView_clickHere);
				eventId=dbh.getDailyTargetsToUpdate()+
						dbh.getWeeklyTargetsToUpdate()+
						dbh.getMonthlyTargetsToUpdate()+
						dbh.getQuarterlylyTargetsToUpdate()+
						dbh.getMidYearTargetsToUpdate()+
						dbh.getAnnualTargetsToUpdate();
				 final int number=(int)eventId;
				 textView_eventTargetsNumber.setText(String.valueOf(number));
					textView_clickHere.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							if(number>0){
							Intent intent= new Intent(getActivity(), UpdateTargetActivity.class);
							startActivity(intent);
							}else if(number==0){
								 Toast.makeText(getActivity(), "You have no targets to update!",
								         Toast.LENGTH_SHORT).show();
							}
						}
						
					});
				/*
				 eventId=dbh.getAllEventTargetsForUpdate("Daily");
				 coverageId=dbh.getAllCoverageTargetsForUpdate("Daily");
				 otherId=dbh.getAllOtherTargetsForUpdate("Daily");
				 learningId=dbh.getAllLearningTargetsForUpdate("Daily");
				 int number=(int)eventId.size();
				 int number2=(int)coverageId.size();
				 int number3=(int)otherId.size();
				 int number4=(int)learningId.size();
				 final int counter;
				
				counter=number+number2+number3+number4;
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
					
				});*/
				textView_eventsClickHere = (TextView) rootView.findViewById(R.id.textView_eventsClickHere);
			    textView_eventsClickHere.setOnClickListener(new OnClickListener(){
			    	@Override
					public void onClick(View v) {
							mViewPager.setCurrentItem(1, true);	
					}
				});
			    
				
				/* Routine Info */
			    
				ArrayList<RoutineActivity> todos = new ArrayList<RoutineActivity>();
				todos=dbh.getSWRoutineActivities();
				if(todos!=null){
					numactivities = todos.size();
				}else{
					numactivities=0;
				}
			    textView_routinesNumber = (TextView) rootView.findViewById(R.id.textView_routinesNumber);
			   // numactivities=todos.size();
				textView_routinesNumber.setText(String.valueOf(numactivities));
			    tv8 = (TextView) rootView.findViewById(R.id.textView8);
			    tv8.setText(" activity(ies) this "+dbh.getTime()+".");
				
			    textView_routinesClickHere = (TextView) rootView.findViewById(R.id.textView_routinesClickHere);
			    textView_routinesClickHere.setOnClickListener(new OnClickListener(){

			    	@Override
					public void onClick(View v) {
						if(numactivities > 0){
							mViewPager.setCurrentItem(2, true);
						} else {
							 Toast.makeText(getActivity(), "No activities for this "+dbh.getTime(),Toast.LENGTH_SHORT).show();
						}
					}
				});
			    
			    return rootView;
		 }
	 }
	 
	 public static class EventsDetails extends Fragment {
		 View rootView;
		 CalendarEvents c;
		private TextView eventStatus;
		int month;
		String month_text;
		private ListView listView_details;
		private ArrayList<MyCalendarEvents> TodayCalendarEvents;
		private TextView textView_clickHere;
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
			    TodayCalendarEvents=c.getTodaysEvents(false);
		 if(TodayCalendarEvents.size()==0&&TodayCalendarEvents!=null){
				 eventStatus.setText("No events planned for today!"); 
		 }else if(TodayCalendarEvents.size()>0&&TodayCalendarEvents!=null){
				 EventsDetailPagerAdapter adapter=new EventsDetailPagerAdapter(getActivity(),TodayCalendarEvents);
			    	adapter.notifyDataSetChanged();
			    	listView_details.setAdapter(adapter);	 
			 }

		    textView_clickHere = (TextView) rootView.findViewById(R.id.textView_back);
		    textView_clickHere.setOnClickListener(new OnClickListener(){

		    	@Override
				public void onClick(View v) {
						mViewPager.setCurrentItem(0, true);
				
				}
			});
		    
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
			} else if (itemId == R.id.menu_sync) {
				Intent service = new Intent(this, TrackerService.class);
				Bundle tb = new Bundle();
				tb.putBoolean("backgroundData", true);
				service.putExtras(tb);
				this.startService(service);
				return true;
			}else if (itemId == R.id.menu_survey) {
				try{
					DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
				DateTime today=new DateTime();
				today=formatter.parseDateTime(today.toString("dd-MM-yyyy HH:mm"));
				
				ArrayList<Survey> surveys=new ArrayList<Survey>();
				surveys=dbh.getSurveys();
				System.out.println("Today "+today.getMillis());
				System.out.println("Reminder Date "+reminderDate.getMillis());
				System.out.println("Next Reminder Date "+surveyData.get(0).getSurveyStatus());
				DateTime surveyFirstReminderDate=formatter.parseDateTime(surveys.get(0).getSurveyReminderDate());
				DateTime surveySecondReminderDate=formatter.parseDateTime(surveys.get(1).getSurveyReminderDate());
				System.out.println("Next Reminder Date "+surveySecondReminderDate.getMillis());
				DateTime surveyFirstNextReminderDate = null;
				DateTime surveySecondNextReminderDate=null;
				//for(int i=0;i<surveys.size();i++){
					//DateTime surveyReminderDate=formatter.parseDateTime(surveys.get(0).getSurveyReminderDate());
				if(!surveys.get(0).getSurveyNextReminderDate().equals("")){
					surveyFirstNextReminderDate=formatter.parseDateTime(surveys.get(0).getSurveyNextReminderDate());
				}
				if(!surveys.get(1).getSurveyNextReminderDate().equals("")){
					surveySecondNextReminderDate = formatter.parseDateTime(surveys.get(1).getSurveyNextReminderDate());
				}
				if(today.getMillis()>=surveyFirstReminderDate.getMillis()&&today.getMillis()<=surveyFirstNextReminderDate.getMillis()&&surveys.get(0).getSurveyStatus().equals("")){
					Intent intent = new Intent(this, RunForm.class);
					intent.putExtra("date", surveyFirstReminderDate.toString("dd-MM-yyyy"));
					this.startActivity(intent);
				}else if(today.getMillis()>=surveyFirstReminderDate.getMillis()&&today.getMillis()<=surveySecondReminderDate.getMillis()&&surveys.get(0).getSurveyStatus().equals("")){
					Intent intent = new Intent(this, RunForm.class);
					intent.putExtra("date", surveyFirstReminderDate.toString("dd-MM-yyyy"));
					this.startActivity(intent);
				}else if(today.getMillis()>=surveySecondReminderDate.getMillis()&&surveys.get(1).getSurveyStatus().equals("")){
					Intent intent = new Intent(this, RunForm.class);
					intent.putExtra("date", surveySecondReminderDate.toString("dd-MM-yyyy"));
					this.startActivity(intent);
				}else if(surveys.get(0).getSurveyNextReminderDate().equals("")&&today.getMillis()>=surveyFirstReminderDate.getMillis()&&surveys.get(0).getSurveyStatus().equals("")){
					Intent intent = new Intent(this, RunForm.class);
					intent.putExtra("date", surveyFirstReminderDate.toString("dd-MM-yyyy"));
					this.startActivity(intent);
				}else if(surveys.get(1).getSurveyNextReminderDate().equals("")&&today.getMillis()>=surveySecondReminderDate.getMillis()&&surveys.get(0).getSurveyStatus().equals("")){
					Intent intent = new Intent(this, RunForm.class);
					intent.putExtra("date", surveySecondReminderDate.toString("dd-MM-yyyy"));
					this.startActivity(intent);
				}else if(today.getMillis()>=surveySecondReminderDate.getMillis()&&today.getMillis()<=surveySecondReminderDate.getMillis()&&surveys.get(0).getSurveyStatus().equals("")){
						Intent intent = new Intent(this, RunForm.class);
						intent.putExtra("date", surveySecondReminderDate.toString("dd-MM-yyyy"));
						this.startActivity(intent);
				}else{
					Crouton.makeText(this, "This survey does not exist at this time", Style.ALERT).show();
				}
				//}
				}catch(Exception e){
					e.printStackTrace();
					Crouton.makeText(this, "This survey does not exist at this time", Style.ALERT).show();
				}
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
	public boolean isOnline() {
		 boolean haveConnectedWifi = false;
		    boolean haveConnectedMobile = false;

		    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		    for (NetworkInfo ni : netInfo) {
		        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
		            if (ni.isConnected())
		                haveConnectedWifi = true;
		        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
		            if (ni.isConnected())
		                haveConnectedMobile = true;
		    }
		    return haveConnectedWifi || haveConnectedMobile;
	}
}
