package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionSevereDehydrationActivity extends Activity {
	
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_sever_dehydration);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Diarrheoa");
	    dbh=new DbHelper(TakeActionSevereDehydrationActivity.this);
	    start_time=System.currentTimeMillis();
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Diarrheoa", start_time.toString(), end_time.toString());
		finish();
	}
}
