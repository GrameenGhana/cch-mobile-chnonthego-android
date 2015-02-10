package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class SoftUterusPNCMotherYesNextActivity extends BaseActivity {

	private LinearLayout linearLayout_one;
	private LinearLayout linearLayout_two;
	private LinearLayout linearLayout_oneButtons;
	private LinearLayout linearLayout_twoButtons;
	private Button button_oneYes;
	private Button button_oneNo;
	private Button button_twoNo;
	private Button button_twoYes;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private String take_action_category; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext = SoftUterusPNCMotherYesNextActivity.this;
	    setContentView(R.layout.activity_mother_pnc_soft_uterus_yes_next);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Soft Uterus");
	    dbh=new DbHelper(SoftUterusPNCMotherYesNextActivity.this);
	    start_time=System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if(take_action_category.equals("yes")){
	    linearLayout_oneButtons=(LinearLayout) findViewById(R.id.LinearLayout_oneButtons);
	    linearLayout_twoButtons=(LinearLayout) findViewById(R.id.LinearLayout_twoButtons);
	    
	    linearLayout_one=(LinearLayout) findViewById(R.id.LinearLayout_one);
	    linearLayout_one.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SoftUterusPNCMotherYesNextActivity.this,TakeActionSoftUterusPNCMotherActivity.class);
				intent.putExtra("value", "one_yes");	
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });	
	    
	    linearLayout_two=(LinearLayout) findViewById(R.id.LinearLayout_two);
	    linearLayout_two.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SoftUterusPNCMotherYesNextActivity.this,TakeActionSoftUterusPNCMotherActivity.class);
				intent.putExtra("value", "two_yes");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
        }else if(take_action_category.equals("no")){
        	linearLayout_oneButtons=(LinearLayout) findViewById(R.id.LinearLayout_oneButtons);
    	    linearLayout_twoButtons=(LinearLayout) findViewById(R.id.LinearLayout_twoButtons);
    	    
    	    linearLayout_one=(LinearLayout) findViewById(R.id.LinearLayout_one);
    	    linearLayout_one.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(SoftUterusPNCMotherYesNextActivity.this,TakeActionSoftUterusPNCMotherActivity.class);
    				intent.putExtra("value", "one_no");	
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
    	    	
    	    });	
    	    
    	    linearLayout_two=(LinearLayout) findViewById(R.id.LinearLayout_two);
    	    linearLayout_two.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(SoftUterusPNCMotherYesNextActivity.this,TakeActionSoftUterusPNCMotherActivity.class);
    				intent.putExtra("value", "two_no");
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
    	    	
    	    });
        }
	    /*
	    button_oneYes=(Button) findViewById(R.id.button_oneYes);
	    button_oneYes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SoftUterusPNCMotherYesNextActivity.this,TakeActionSoftUterusPNCMotherActivity.class);
				intent.putExtra("value", "one_yes");	
				startActivity(intent);
			}
	    	
	    });
	    button_oneNo=(Button) findViewById(R.id.button_oneNo);
	    button_oneNo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SoftUterusPNCMotherYesNextActivity.this,TakeActionSoftUterusPNCMotherActivity.class);
				intent.putExtra("value", "one_no");
				startActivity(intent);
			}
	    	
	    });
	    
	    button_twoYes=(Button) findViewById(R.id.button_twoYes);
	    button_twoYes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SoftUterusPNCMotherYesNextActivity.this,TakeActionSoftUterusPNCMotherActivity.class);
				intent.putExtra("value", "two_yes");
				startActivity(intent);
			}
	    	
	    });
	    button_twoNo=(Button) findViewById(R.id.button_twoNo);
	    button_twoNo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SoftUterusPNCMotherYesNextActivity.this,TakeActionSoftUterusPNCMotherActivity.class);
				intent.putExtra("value", "two_no");	
				startActivity(intent);
			}
	    	
	    });
	    */
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Soft Uterus", start_time.toString(), end_time.toString());
		finish();
	}
}
