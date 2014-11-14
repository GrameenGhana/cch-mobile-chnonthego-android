package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class GoodAttachementActivity extends Activity {

	private String take_action_category;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Good Attachment");
	    dbh=new DbHelper(GoodAttachementActivity.this);
	    
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if(take_action_category.equalsIgnoreCase("good_attachement")){
	    setContentView(R.layout.activity_good_attachement);
	    start_time=System.currentTimeMillis();
        }else if(take_action_category.equalsIgnoreCase("low_birth_weight")){
        setContentView(R.layout.activity_breastfeeding_low_birth_weight);
        start_time=System.currentTimeMillis();
        }else if(take_action_category.equalsIgnoreCase("low_birth_weight_next")){
            setContentView(R.layout.activity_low_birth_weight_next);
            start_time=System.currentTimeMillis();
            }
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Counselling Good Attachment", start_time.toString(), end_time.toString());
		finish();
	}
}
