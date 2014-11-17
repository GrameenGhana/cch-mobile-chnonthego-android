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

public class SoftUterusPNCMotherYesNextActivity extends Activity {

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_mother_pnc_soft_uterus_yes_next);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Soft Uterus");
	    dbh=new DbHelper(SoftUterusPNCMotherYesNextActivity.this);
	    start_time=System.currentTimeMillis();

	    linearLayout_oneButtons=(LinearLayout) findViewById(R.id.LinearLayout_oneButtons);
	    linearLayout_twoButtons=(LinearLayout) findViewById(R.id.LinearLayout_twoButtons);
	    
	    linearLayout_one=(LinearLayout) findViewById(R.id.LinearLayout_one);
	    linearLayout_one.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(linearLayout_oneButtons.getVisibility()==View.VISIBLE){
					linearLayout_oneButtons.setVisibility(View.GONE);
				}else if(linearLayout_oneButtons.getVisibility()==View.GONE){
				linearLayout_oneButtons.setVisibility(View.VISIBLE);
				}
			}
	    	
	    });	
	    
	    linearLayout_two=(LinearLayout) findViewById(R.id.LinearLayout_two);
	    linearLayout_two.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(linearLayout_twoButtons.getVisibility()==View.VISIBLE){
					linearLayout_twoButtons.setVisibility(View.GONE);
				}else if(linearLayout_twoButtons.getVisibility()==View.GONE){
					linearLayout_twoButtons.setVisibility(View.VISIBLE);
				}
			}
	    	
	    });
	    
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
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Soft Uterus", start_time.toString(), end_time.toString());
		finish();
	}
}
