package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionOtherSeriousConditionActivity extends BaseActivity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext= TakeActionOtherSeriousConditionActivity.this;
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Other Serious Conditions");
	    dbh=new DbHelper(TakeActionOtherSeriousConditionActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("bleeding")){
	    setContentView(R.layout.activity_bleeding_umbilical_cord);
        }else if(take_action_category.equals("soft swelling")){
        setContentView(R.layout.activity_soft_swelling);
        }else if(take_action_category.equals("open tissue")){
        setContentView(R.layout.activity_open_tissue);
        }else if(take_action_category.equals("no urine")){
            setContentView(R.layout.activity_no_urine);
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Other Serious Conditions", start_time.toString(), end_time.toString());
		finish();
	}
}
