package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HIVInfectionAskActivity extends Activity {

	private String category;
	private Button button_no;
	private Button button_yes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic");
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          category= extras.getString("value");
        }
        if(category.equals("negative")){
        	   setContentView(R.layout.activity_hiv_negative_status);
        	   button_no=(Button) findViewById(R.id.button_no);
        	   button_no.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(HIVInfectionAskActivity.this,TakeActionHIVInfectionActivity.class);
					intent.putExtra("value", "negative_no");
					startActivity(intent);
				}
        		   
        	   });
        	   button_yes=(Button) findViewById(R.id.button_yes);
        	   button_yes.setOnClickListener(new OnClickListener(){

   				@Override
   				public void onClick(View v) {
   					Intent intent=new Intent(HIVInfectionAskActivity.this,TakeActionHIVInfectionActivity.class);
   					intent.putExtra("value", "negative_yes");
   					startActivity(intent);
   				}
           		   
           	   });
        }else  if(category.equals("positive")){
     	   setContentView(R.layout.activity_hiv_positive_status);
     	   button_no=(Button) findViewById(R.id.button_no);
     	   button_no.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(HIVInfectionAskActivity.this,TakeActionHIVInfectionActivity.class);
					intent.putExtra("value", "positive_no");
					startActivity(intent);
				}
     		   
     	   });
     	   button_yes=(Button) findViewById(R.id.button_yes);
     	   button_yes.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(HIVInfectionAskActivity.this,TakeActionHIVInfectionActivity.class);
					intent.putExtra("value", "positive_yes");
					startActivity(intent);
				}
        		   
        	   });
     }
	  
	}

}
