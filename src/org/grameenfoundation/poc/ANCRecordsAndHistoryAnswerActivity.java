package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ANCRecordsAndHistoryAnswerActivity extends BaseActivity {

	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private String take_action_category;
	private TextView textView_clickHere;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic: Records & History");
	    dbh=new DbHelper(ANCRecordsAndHistoryAnswerActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        
        if(take_action_category.equals("yes")){
        	setContentView(R.layout.activity_ask_records_yes);
        }else if(take_action_category.equals("no")){
        	setContentView(R.layout.activity_ask_records_no);
        	textView_clickHere=(TextView) findViewById(R.id.textView_clickHere);
        	textView_clickHere.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(ANCRecordsAndHistoryAnswerActivity.this,EstimateTrimester.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					
				}
        		
        	});
        }
	}
	public void onBackPressed()
	{
		end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "ANC Diagnostic: Records & History", start_time.toString(), end_time.toString());
		finish();
	}
}
