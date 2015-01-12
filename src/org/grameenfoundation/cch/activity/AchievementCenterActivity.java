package org.grameenfoundation.cch.activity;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AchievementCenterActivity extends Activity {

	private Spinner spinner_categories;
	private Button button_view;
	int month;
	int year;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_achievements);
	    spinner_categories=(Spinner) findViewById(R.id.spinner_categories);
	    String[] items={"November 2014","December 2014",
	    				"January 2015","February 2015",
	    				"March 2015","April 2015",
	    				"May 2015","June 2015",
	    				"July 2015","August 2015",
	    				"September 2015","October 2015",
	    				"November 2015","December 2015"};
	    
	    ArrayAdapter<String> adapter=new ArrayAdapter<String>(AchievementCenterActivity.this,android.R.layout.simple_spinner_item,items);
	    spinner_categories.setAdapter(adapter);
	    
	    button_view=(Button) findViewById(R.id.button_view);
	    spinner_categories.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch(position){
				case 0:
					month=11;
					year=2014;
					
					break;
				case 1:
					month=12;
					year=2014;
					break;
				case 2:
					month=1;
					year=2015;
					break;
				case 3:
					month=2;
					year=2015;
					break;
				case 4:
					month=3;
					year=2015;
					break;
				case 5:
					month=4;
					year=2015;
					break;
				case 6:
					month=5;
					year=2015;
					break;
				case 7:
					month=6;
					year=2015;
					break;
				case 8:
					month=7;
					year=2015;
					break;
				case 9:
					month=8;
					year=2015;
					break;
				case 10:
					month=9;
					year=2015;
					break;
				case 11:
					month=10;
					year=2015;
					break;
				case 12:
					month=11;
					year=2015;
					break;
				case 13:
					month=12;
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
			}
	    	
	    });
	}

}
