package org.grameenfoundation.cch.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.cch.model.CourseAchievments;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.tasks.CourseAchievementsTask;
import org.grameenfoundation.poc.BaseActivity;

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

public class AchievementCenterActivity extends BaseActivity {

	private Spinner spinner_categories;
	private Button button_view;
	int month;
	int year;
	private SharedPreferences prefs;
	private ListView listView;
	private DbHelper db;
	private ArrayList<EventTargets> completedCoverageTargets;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_achievements);
	    getActionBar().setTitle("Achievement Centre");
	   // db=new DbHelper(AchievementCenterActivity.this);
	    CourseAchievments courses= new CourseAchievments();
	    spinner_categories=(Spinner) findViewById(R.id.spinner_categories);
	    String[] items={"November 2014","December 2014",
	    				"January 2015","February 2015",
	    				"March 2015","April 2015",
	    				"May 2015","June 2015",
	    				"July 2015","August 2015",
	    				"September 2015","October 2015",
	    				"November 2015","December 2015"};
	    prefs = PreferenceManager.getDefaultSharedPreferences(AchievementCenterActivity.this);
	    ArrayAdapter<String> adapter=new ArrayAdapter<String>(AchievementCenterActivity.this,android.R.layout.simple_spinner_item,items);
	    spinner_categories.setAdapter(adapter);
	    button_view=(Button) findViewById(R.id.button_view);
	    listView=(ListView) findViewById(R.id.listView1);
	    String[] items2={"Overall course achievements"};
	    ArrayAdapter<String> adapter2=new ArrayAdapter<String>(AchievementCenterActivity.this,android.R.layout.simple_spinner_item,items2);
	    listView.setAdapter(adapter2);
	    //completedCoverageTargets=db.getAllCoverageTargetsCompletedForAchievements("updated",2, 2015);
	   // System.out.println(completedCoverageTargets.get(0).getEventTargetEndDate());
	    spinner_categories.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch(position){
				case 0:
					month=10;
					year=2014;
					
					break;
				case 1:
					month=11;
					year=2014;
					break;
				case 2:
					month=0;
					year=2015;
					break;
				case 3:
					month=1	;
					year=2015;
					break;
				case 4:
					month=2;
					year=2015;
					break;
				case 5:
					month=3;
					year=2015;
					break;
				case 6:
					month=4;
					year=2015;
					break;
				case 7:
					month=5;
					year=2015;
					break;
				case 8:
					month=6;
					year=2015;
					break;
				case 9:
					month=7;
					year=2015;
					break;
				case 10:
					month=8;
					year=2015;
					break;
				case 11:
					month=9;
					year=2015;
					break;
				case 12:
					month=10;
					year=2015;
					break;
				case 13:
					month=11;
					year=2015;
					break;
				}
				
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
				intent.putExtra("month", month);
				intent.putExtra("year", year);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    
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
	    	
	    });
	}

}
