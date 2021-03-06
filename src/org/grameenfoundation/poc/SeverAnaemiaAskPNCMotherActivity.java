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

public class SeverAnaemiaAskPNCMotherActivity extends Activity {


	private Button button_yes;
	private Button button_no;
	Context mContext;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_severe_anaemia);
	    mContext=SeverAnaemiaAskPNCMotherActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Anaemia");
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    button_yes=(Button) findViewById(R.id.button_yes);
	    button_yes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,TakeActionSevereMalariaPNCMotherActivity.class);
				intent.putExtra("category", "severe anaemia");
				startActivity(intent);
			}
	    	
	    });
	    button_no=(Button) findViewById(R.id.button_no);
	    button_no.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,TakeActionSevereMalariaPNCMotherActivity.class);
				intent.putExtra("category", "no anaemia");
				startActivity(intent);
			}
	    	
	    });
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Anaemia", start_time.toString(), end_time.toString());
		finish();
	}
}
