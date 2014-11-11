package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InfantFeedingNextActivity extends Activity {

	private String take_action_category;
	private Button button_next;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling");
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if (take_action_category.equals("exclusive_breastfeeding")){
        	setContentView(R.layout.activity_exclusive_breastfeeding_next);
        }else if (take_action_category.equals("breast_attachement")){
        	setContentView(R.layout.activity_breast_attachement_next);
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
        }else if (take_action_category.equals("low_birth_weight")){
        	setContentView(R.layout.activity_breastfeeding_low_birth_weight);
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
        }else if(take_action_category.equals("feeding_sick_baby")){
        	setContentView(R.layout.activity_feeding_sick_baby);
        }else if(take_action_category.equals("taking_arv")){
        	setContentView(R.layout.activity_mother_taking_arv);
        }else if(take_action_category.equals("not_taking_arv")){
        	setContentView(R.layout.activity_mother_not_taking_arv);
        }else if(take_action_category.equals("mother_remember")){
        	setContentView(R.layout.activity_mother_must_remember);
        }
        
        
	}

}
