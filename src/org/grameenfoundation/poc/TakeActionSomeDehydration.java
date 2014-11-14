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

public class TakeActionSomeDehydration extends Activity {

	private Button button_yes;
	private Button button_no;
	private Context mContext;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_some_dehydration);
		mContext=TakeActionSomeDehydration.this;
		getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Diarrhoea");
	    
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
		button_yes=(Button) findViewById(R.id.button_yes);
		button_yes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			Intent intent=new Intent(mContext, TakeActionSomeDehydrationNoActivity.class);
			intent.putExtra("category","yes");	
			startActivity(intent);
			}
			
		});
		button_no=(Button) findViewById(R.id.button_no);
		button_no.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			Intent intent=new Intent(mContext, TakeActionSomeDehydrationNoActivity.class);
			intent.putExtra("category","no");	
			startActivity(intent);
			}
			
		});
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Diarrhoea" , start_time.toString(), end_time.toString());
		finish();
	}
}
