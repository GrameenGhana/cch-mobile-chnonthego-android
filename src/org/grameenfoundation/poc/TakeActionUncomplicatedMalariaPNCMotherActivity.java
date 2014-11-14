package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

public class TakeActionUncomplicatedMalariaPNCMotherActivity extends Activity {

	private Button button_next;
	 private DbHelper dbh;
		private Long start_time;
		private Long end_time;
		private Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_uncomplicated_malaria_pnc_mother);
	    mContext=TakeActionUncomplicatedMalariaPNCMotherActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Malaria");
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Malaria", start_time.toString(), end_time.toString());
		finish();
	}
}
