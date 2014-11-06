package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionDiarrhoeaActivity extends Activity {

	private String take_action_category;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("Postnatal Care");
	   // listView_takeAction=(ListView) findViewById(R.id.listView_takeAction);
	  //  textView_takeAction=(TextView) findViewById(R.id.textView_takeActionCategory);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if(take_action_category.equals("severe_diarrhoea")){
        	   setContentView(R.layout.activity_persistent_diarrhoea);
        }else if(take_action_category.equals("blood_stool")){
     	   setContentView(R.layout.activity_blood_in_stool);
     }else if(take_action_category.equals("no_diarrhoea")){
   	   setContentView(R.layout.activity_no_diarrhoea);
   }
	}

}
