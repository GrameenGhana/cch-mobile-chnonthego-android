package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionSevereMalariaActivity extends Activity {

	private String take_action_category;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("severe_malaria")){
        	setContentView(R.layout.activity_severe_malaria);
        }else if(take_action_category.equals("Ist Trimester")){
        setContentView(R.layout.activity_first_trimester_malaria);
        }else if(take_action_category.equals("2nd Trimester")){
            setContentView(R.layout.activity_second_trimester_malaria);
            }
        else if(take_action_category.equals("3rd Trimester")){
            setContentView(R.layout.activity_third_trimester_malaria);
            }
	
        else if(take_action_category.equals("negative")){
            setContentView(R.layout.activity_malaria_test_negative);
            }
        else if(take_action_category.equals("not done")){
            setContentView(R.layout.activity_malaria_test_not_done);
            }
        else if(take_action_category.equals("severe anaemia")){
            setContentView(R.layout.activity_severe_anaemia_take_action);
            }
        else if(take_action_category.equals("no anaemia")){
            setContentView(R.layout.activity_no_anaemia_take_action);
            }
	
	   
	}

}
