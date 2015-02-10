package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HIVInfectionAskActivity extends BaseActivity {

	private String category;
	private Button button_no;
	private Button button_yes;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=HIVInfectionAskActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: HIV Infection");
	    dbh=new DbHelper(HIVInfectionAskActivity.this);
	    start_time=System.currentTimeMillis();
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
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
        		   
        	   });
        	   button_yes=(Button) findViewById(R.id.button_yes);
        	   button_yes.setOnClickListener(new OnClickListener(){

   				@Override
   				public void onClick(View v) {
   					Intent intent=new Intent(HIVInfectionAskActivity.this,TakeActionHIVInfectionActivity.class);
   					intent.putExtra("value", "negative_yes");
   					startActivity(intent);
   					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
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
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
     		   
     	   });
     	   button_yes=(Button) findViewById(R.id.button_yes);
     	   button_yes.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(HIVInfectionAskActivity.this,TakeActionHIVInfectionActivity.class);
					intent.putExtra("value", "positive_yes");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
        		   
        	   });
     }
	  
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic HIV Infection", start_time.toString(), end_time.toString());
		finish();
	}
}
