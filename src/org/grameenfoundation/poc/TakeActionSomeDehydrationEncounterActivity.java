package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TakeActionSomeDehydrationEncounterActivity extends Activity {

	private String take_action_category;
	private Button button_next;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equalsIgnoreCase("home_visit")){
        	setContentView(R.layout.activity_diarrhoea_home_visit);
        } else if(take_action_category.equalsIgnoreCase("chps_one")){
        	setContentView(R.layout.activity_chps_one);
        	button_next=(Button) findViewById(R.id.button_next);
        	button_next.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent =new Intent(TakeActionSomeDehydrationEncounterActivity.this,TakeActionSomeDehydrationEncounterNextActivity.class);
					intent.putExtra("category","chps_one_next");
					startActivity(intent);
					
				}
        		
        	});
        }else if(take_action_category.equalsIgnoreCase("chps_two")){
        	setContentView(R.layout.activity_chps_two);
        	button_next=(Button) findViewById(R.id.button_next);
        	button_next.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent =new Intent(TakeActionSomeDehydrationEncounterActivity.this,TakeActionSomeDehydrationEncounterNextActivity.class);
					intent.putExtra("category","chps_two_next");
					startActivity(intent);
					
				}
        		
        	});
        }
	}

}
