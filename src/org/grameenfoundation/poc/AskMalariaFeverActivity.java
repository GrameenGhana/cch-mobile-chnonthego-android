package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AskMalariaFeverActivity extends BaseActivity {

	private Button button_no;
	private Button button_yes;
//	private Context mContext;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_malaria_fever_ask);
	    mContext=AskMalariaFeverActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic: Malaria");
	    button_yes=(Button) findViewById(R.id.button_yes);
	    button_yes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,AskMalariaFeverYesActivity.class);
				startActivity(intent);
			}
	    	
	    });
	    button_no=(Button) findViewById(R.id.button_no);
	    button_no.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,AnaemiaAskActivity.class);
				startActivity(intent);
			}
	    	
	    });
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "ANC Diagnostic Malaria", start_time.toString(), end_time.toString());
		finish();
	}
}
