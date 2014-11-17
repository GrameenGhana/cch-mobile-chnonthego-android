package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class HomeCareForInfantActivity extends BaseActivity {
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=HomeCareForInfantActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Home care for infant");
	    setContentView(R.layout.activity_home_care_infant);
	    dbh=new DbHelper(HomeCareForInfantActivity.this);
	    start_time=System.currentTimeMillis();
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Counselling Home care for infant", start_time.toString(), end_time.toString());
		finish();
	}
}
