package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EncouragingPNCActivity extends BaseActivity {

	private String take_action_category;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time; 
	private String data;
	private Button button_next;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    dbh=new DbHelper(EncouragingPNCActivity.this);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if(take_action_category.equals("care_of_newborns")){
        	   setContentView(R.layout.activity_anc_care_new_borns);
        	   getActionBar().setSubtitle("ANC Counselling: Care of Newborns");
        	   data="Care of Newborns";
        }else if(take_action_category.equals("breast_care")){
        	 setContentView(R.layout.activity_anc_how_breastfeed_breastcare);
        	 getActionBar().setSubtitle("ANC Counselling: Breastfeeding & Breast Care");
        	  data="Breastfeeding & Breast Care";
        	  button_next=(Button) findViewById(R.id.button_next);
        	  button_next.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(EncouragingPNCActivity.this,BreastCareANCNextActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
        		  
        	  });
        }else if(take_action_category.equals("family_planning")){
        	 setContentView(R.layout.activity_family_planning_postpartum);
        	 getActionBar().setSubtitle("ANC Counselling: Postpartum Family Planning");
        	 data="Postpartum Family Planning";
        	 button_next=(Button) findViewById(R.id.button_next);
        	 button_next.setVisibility(View.GONE);
        }else if(take_action_category.equals("when_to_return")){
       	 setContentView(R.layout.activity_anc_when_return_pnc);
       	 getActionBar().setSubtitle("ANC Counselling: When to return for PNC");
    	 data="When to return for PNC";
       }
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "ANC Counselling" +data, start_time.toString(), end_time.toString());
		finish();
	}
}
