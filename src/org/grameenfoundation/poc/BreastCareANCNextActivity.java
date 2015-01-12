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

public class BreastCareANCNextActivity extends BaseActivity {

	private Context mContext;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private Button button_next; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_anc_how_breastfeed_breastcare_next);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Counselling: Breastfeeding & Breast Care");
	    mContext=BreastCareANCNextActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    
	    button_next=(Button) findViewById(R.id.button_next);
	    button_next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(BreastCareANCNextActivity.this,BreastCareANCNextTwoActivity.class);
				startActivity(intent);
			}
	    	
	    });
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "ANC Counselling: Breastfeeding & Breast Care", start_time.toString(), end_time.toString());
		finish();
	}
}
