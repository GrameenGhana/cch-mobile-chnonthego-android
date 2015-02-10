package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TreatingUncomplicatedMalariaANCNextThreeActivity extends BaseActivity {

	private Button button_next;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_anc_treatment_unomplicate_malaria_preg_next_3);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC References: Treating UnComplicated Malaria");
	    dbh=new DbHelper(TreatingUncomplicatedMalariaANCNextThreeActivity.this);
	    start_time=System.currentTimeMillis();
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "ANC References: Treating UnComplicated Malaria" , start_time.toString(), end_time.toString());
		finish();
	}
}
