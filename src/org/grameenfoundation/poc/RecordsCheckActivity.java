package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class RecordsCheckActivity extends BaseActivity {

	private DbHelper dbh;
	private Long start_time;
	private Long end_time;  

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext = RecordsCheckActivity.this;
	    
	    setContentView(R.layout.activity_records_check);
	    dbh=new DbHelper(RecordsCheckActivity.this);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Records & History");
	    TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
	 	   click_here.setOnClickListener(new OnClickListener(){

	 		@Override
	 		public void onClick(View v) {
	 			Intent intent=new Intent(RecordsCheckActivity.this,SevereDiseasesActivity.class);
	 			intent.putExtra("value", "keeping_baby_warm");
	 			startActivity(intent);
	 			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
	 		}
	 		   
	 	   });
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Records & History", start_time.toString(), end_time.toString());
		finish();
	}
}
