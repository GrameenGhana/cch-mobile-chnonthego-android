package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class AskMotherRecordsPNCNextActivity extends BaseActivity {

	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext = AskMotherRecordsPNCNextActivity.this;
	    setContentView(R.layout.activity_ask_mother_records_pnc_next);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Records & History");
	    dbh=new DbHelper(AskMotherRecordsPNCNextActivity.this);
	    start_time=System.currentTimeMillis();
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Records & History", start_time.toString(), end_time.toString());
		finish();
	}
}
