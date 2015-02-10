package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TakeActionSoftUterusPNCMotherActivity extends BaseActivity {
	
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private String take_action_category; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Soft Uterus");
	    dbh=new DbHelper(TakeActionSoftUterusPNCMotherActivity.this);
	    start_time=System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if(take_action_category.equals("one_yes")){
        	   setContentView(R.layout.activity_soft_uterus_one_yes);
        }else if(take_action_category.equals("one_no")){
        	 setContentView(R.layout.activity_soft_uterus_one_no);
        	 TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
     	    click_here.setOnClickListener(new OnClickListener(){

     			@Override
     			public void onClick(View v) {
     				Intent intent=new Intent(TakeActionSoftUterusPNCMotherActivity.this,HomeCareForInfantActivity.class);
     				intent.putExtra("value", "malaria");
     				startActivity(intent);
     				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
     			}
     	    	
     	    });
     	    
     	    TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
     	    click_here_too.setOnClickListener(new OnClickListener(){

     			@Override
     			public void onClick(View v) {
     				Intent intent=new Intent(TakeActionSoftUterusPNCMotherActivity.this,BreastProblemsPNCMotherActivity.class);
     				startActivity(intent);
     				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
     			}
     	    	
     	    });
        }else if(take_action_category.equals("two_yes")){
       	 setContentView(R.layout.activity_soft_uterus_two_yes);
       }else if(take_action_category.equals("two_no")){
         	 setContentView(R.layout.activity_soft_uterus_two_no);
         	TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
    	    click_here.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(TakeActionSoftUterusPNCMotherActivity.this,ReturningForCareActivity.class);
    				intent.putExtra("value", "malaria");
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
    	    	
    	    });
    	    
    	    TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
    	    click_here_too.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(TakeActionSoftUterusPNCMotherActivity.this,HomeCareForInfantActivity.class);
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
    	    	
    	    });
    	    
    	    TextView click_here_three=(TextView) findViewById(R.id.textView_clickHereThree);
    	    click_here_three.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(TakeActionSoftUterusPNCMotherActivity.this,BreastProblemsPNCMotherActivity.class);
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
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Soft Uterus", start_time.toString(), end_time.toString());
		finish();
	}
}
