package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PerformMalariaTestActivity extends Activity {

	private Button button_positive;
	private Button button_negative;
	private Button button_testNotDone;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_malaria_test_);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic: Malaria");
	    dbh=new DbHelper(PerformMalariaTestActivity.this);
	    start_time=System.currentTimeMillis();
	    button_positive=(Button) findViewById(R.id.button_positive);
	    button_positive.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(PerformMalariaTestActivity.this,AskMalariaComplicatedActivity.class);
				startActivity(intent);
			}
	    	
	    });
	    button_negative=(Button) findViewById(R.id.button_negative);
	    button_negative.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(PerformMalariaTestActivity.this,TakeActionSevereMalariaActivity.class);
				intent.putExtra("category", "negative");
				startActivity(intent);
			}
	    	
	    });
	    button_testNotDone=(Button) findViewById(R.id.button_testNotDone);
	    button_testNotDone.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(PerformMalariaTestActivity.this,TakeActionSevereMalariaActivity.class);
				intent.putExtra("category", "not done");
				startActivity(intent);
			}
	    	
	    });
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "ANC Diagnostic: Malaria", start_time.toString(), end_time.toString());
		finish();
	}
}
