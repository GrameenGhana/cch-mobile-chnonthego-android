package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InfantFeedingNextActivity extends BaseActivity {

	private String take_action_category;
	private Button button_next;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private String data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	  mContext=InfantFeedingNextActivity.this;
	    dbh=new DbHelper(InfantFeedingNextActivity.this);
	    start_time=System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if (take_action_category.equals("exclusive_breastfeeding")){
        	setContentView(R.layout.activity_exclusive_breastfeeding_next);
        	  getActionBar().setSubtitle("PNC Counselling: Exclusive Breastfeeding");
        	  data="PNC Counselling Exclusive Breastfeeding";
        }else if (take_action_category.equals("breast_attachement")){
        	setContentView(R.layout.activity_breast_attachement_next);
        	  getActionBar().setSubtitle("PNC Counselling: Breast Attachment");
        	  data="PNC Counselling Breast Attachment";
        	button_next=(Button) findViewById(R.id.button_next);
        	button_next.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(InfantFeedingNextActivity.this,GoodAttachementActivity.class);
					intent.putExtra("value", "good_attachement");
					startActivity(intent);
				}
        	});
        }else if(take_action_category.equals("feeding_frequency")){
        	setContentView(R.layout.activity_feeding_frequency_next);
        	  getActionBar().setSubtitle("PNC Counselling: Feeding Frequency");
        	  data="PNC Counselling Feeding Frequency";
        }else if (take_action_category.equals("low_birth_weight")){
        	setContentView(R.layout.activity_breastfeeding_low_birth_weight);
        	  getActionBar().setSubtitle("PNC Counselling: Low Birth Weight Baby");
        	  data="PNC Counselling Low Birth Weight Baby";
        	button_next=(Button) findViewById(R.id.button_next);
        	button_next.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(InfantFeedingNextActivity.this,GoodAttachementActivity.class);
					intent.putExtra("value", "low_birth_weight_next");
					startActivity(intent);
				}
        		
        	});
        }else if(take_action_category.equals("separated_from_baby")){
        	setContentView(R.layout.activity_away_from_baby);
        	  getActionBar().setSubtitle("PNC Counselling: Infant Feeding");
        	  data="PNC Counselling Infant Feeding";
        }else if(take_action_category.equals("feeding_sick_baby")){
        	setContentView(R.layout.activity_feeding_sick_baby);
        	  getActionBar().setSubtitle("PNC Counselling: Infant Feeding");
        	  data="PNC Counselling Infant Feeding";
        }else if(take_action_category.equals("taking_arv")){
        	setContentView(R.layout.activity_mother_taking_arv);
        	  getActionBar().setSubtitle("PNC Counselling: Infant Feeding");
        	  data="PNC Counselling Infant Feeding";
        }else if(take_action_category.equals("not_taking_arv")){
        	setContentView(R.layout.activity_mother_not_taking_arv);
        	  getActionBar().setSubtitle("PNC Counselling: Infant Feeding");
        	  data="PNC Counselling Infant Feeding";
        }else if(take_action_category.equals("mother_remember")){
        	setContentView(R.layout.activity_mother_must_remember);
        	  getActionBar().setSubtitle("PNC Counselling: Infant Feeding");
        	  data="PNC Counselling Infant Feeding";
        }
        
        
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", data, start_time.toString(), end_time.toString());
		finish();
	}
}
