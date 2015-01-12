package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MalariaWithSPForPregnantWomenNextActivity extends Activity {
	private DbHelper dbh;
	private Long start_time;
	private Long end_time; 
	private Context mContext;
	private Button button_next;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_anc_ref_malaria_para_next);
	    mContext=MalariaWithSPForPregnantWomenNextActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC References: Malaria with SP");
	   
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "ANC References: Malaria with SP", start_time.toString(), end_time.toString());
		finish();
	}
}
