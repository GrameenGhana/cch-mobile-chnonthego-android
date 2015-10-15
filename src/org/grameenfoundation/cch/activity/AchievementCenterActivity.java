package org.grameenfoundation.cch.activity;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.cch.model.CourseAchievments;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.tasks.AchievementsTask;
import org.grameenfoundation.cch.tasks.CourseAchievementsTask;
import org.grameenfoundation.poc.BaseActivity;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class AchievementCenterActivity extends BaseActivity {

	private Spinner spinner_categories;
	private Button button_view;
	int month;
	String month_text;
	int year;
	private SharedPreferences prefs;
	private ListView listView;
	private DbHelper db;
	private ArrayList<EventTargets> completedCoverageTargets;
	private Spinner spinner_years;
	private Button button_refresh;
	private TextView textView_status;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_achievements);
	    getActionBar().setTitle("Achievement Centre");
	   db=new DbHelper(AchievementCenterActivity.this);
	    spinner_categories=(Spinner) findViewById(R.id.spinner_months);
	    String[] months = new DateFormatSymbols().getMonths();
	    String[] years = {"2014","2015","2016","2017","2018","2019","2020","2021","2022"};
	    spinner_years=(Spinner) findViewById(R.id.spinner_years);
	    /*
	    String[] items={"November 2014","December 2014",
	    				"January 2015","February 2015",
	    				"March 2015","April 2015",
	    				"May 2015","June 2015",
	    				"July 2015","August 2015",
	    				"September 2015","October 2015",
	    				"November 2015","December 2015"};*/
	    prefs = PreferenceManager.getDefaultSharedPreferences(AchievementCenterActivity.this);
	    ArrayAdapter<String> adapter=new ArrayAdapter<String>(AchievementCenterActivity.this,android.R.layout.simple_spinner_item,months);
	    ArrayAdapter<String> adapter_years=new ArrayAdapter<String>(AchievementCenterActivity.this,android.R.layout.simple_spinner_item,years);
	    spinner_categories.setAdapter(adapter);
	    spinner_years.setAdapter(adapter_years);
	    button_view=(Button) findViewById(R.id.button_view);
	    button_refresh=(Button) findViewById(R.id.button_refresh);
	    textView_status=(TextView) findViewById(R.id.textView_status);
	    textView_status.setText("Click refresh to get the most recent data");
	    spinner_categories.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(spinner_categories.getSelectedItem().toString().equalsIgnoreCase("January")){
					month=0;
					month_text="January";
					//year=Integer.parseInt(spinner_years.getSelectedItem().toString());	
				}else if(spinner_categories.getSelectedItem().toString().equalsIgnoreCase("February")){
					month=1	;
					month_text="February";
					//year=Integer.parseInt(spinner_years.getSelectedItem().toString());	
				}else if(spinner_categories.getSelectedItem().toString().equalsIgnoreCase("March")){
					month=2	;
					month_text="March";
					//year=Integer.parseInt(spinner_years.getSelectedItem().toString());	
				}else if(spinner_categories.getSelectedItem().toString().equalsIgnoreCase("April")){
					month=3	;
					month_text="April";
					//year=Integer.parseInt(spinner_years.getSelectedItem().toString());	
				}else if(spinner_categories.getSelectedItem().toString().equalsIgnoreCase("May")){
					month=4	;
					month_text="May";
					//year=Integer.parseInt(spinner_years.getSelectedItem().toString());	
				}else if(spinner_categories.getSelectedItem().toString().equalsIgnoreCase("June")){
					month=5	;
					month_text="June";
					//year=Integer.parseInt(spinner_years.getSelectedItem().toString());	
				}else if(spinner_categories.getSelectedItem().toString().equalsIgnoreCase("July")){
					month=6;
					month_text="June";
					//year=Integer.parseInt(spinner_years.getSelectedItem().toString());	
				}else if(spinner_categories.getSelectedItem().toString().equalsIgnoreCase("August")){
					month=7;
					//year=Integer.parseInt(spinner_years.getSelectedItem().toString());	
				}else if(spinner_categories.getSelectedItem().toString().equalsIgnoreCase("September")){
					month=8;
					month_text="September";
					//year=Integer.parseInt(spinner_years.getSelectedItem().toString());	
				}else if(spinner_categories.getSelectedItem().toString().equalsIgnoreCase("October")){
					month=9;
					month_text="October";
					//year=Integer.parseInt(spinner_years.getSelectedItem().toString());	
				}else if(spinner_categories.getSelectedItem().toString().equalsIgnoreCase("November")){
					month=10;
					month_text="November";
					//year=Integer.parseInt(spinner_years.getSelectedItem().toString());	
				}else if(spinner_categories.getSelectedItem().toString().equalsIgnoreCase("December")){
					month=11;
					month_text="December";
					//year=Integer.parseInt(spinner_years.getSelectedItem().toString());	
				}

				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
	    	
	    });
	    
	    spinner_years.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				year=Integer.parseInt(spinner_years.getSelectedItem().toString());	
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	    button_view.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(AchievementCenterActivity.this,AchievementSummaryActivity.class);
				System.out.println(String.valueOf(month)+" "+String.valueOf(year));
				intent.putExtra("month", month);
				intent.putExtra("month_text", month_text);
				intent.putExtra("year", year);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    button_refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(db.isOnline()){
				 prefs = PreferenceManager.getDefaultSharedPreferences(AchievementCenterActivity.this);
				String name=prefs.getString("first_name", "name");
				AchievementsTask task=new AchievementsTask(AchievementCenterActivity.this);
				task.execute(new String[] { getResources().getString(R.string.serverDefaultAddress)+"/"+MobileLearning.CCH_USER_ACHIEVEMENTS_PATH+name});
			
			}else {
				Crouton.makeText(AchievementCenterActivity.this, "Check internet connection!", Style.ALERT).show();
			}
			}
		});
	    /*
	    listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(AchievementCenterActivity.this,OverallCourseAchievementsActivity.class);
					startActivity(intent);
					break;
				}
			}
	    	
	    });*/
	}

}
