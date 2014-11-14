package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TreatingUncomplicatedMalariaNextFourActivity extends Activity {

	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_treating_uncomplicated_malaria_next_four);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Treating UnComplicated Malaria");
	    dbh=new DbHelper(TreatingUncomplicatedMalariaNextFourActivity.this);
	    start_time=System.currentTimeMillis();
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Counselling: Treating UnComplicated Malaria" , start_time.toString(), end_time.toString());
		finish();
	}
}
