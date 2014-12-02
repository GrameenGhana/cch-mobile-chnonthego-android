package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TakeActionJaundiceActivity extends BaseActivity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Jaundice");
	    dbh=new DbHelper(TakeActionJaundiceActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("severe")){
	    setContentView(R.layout.activity_severe_jaundice);
	    TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionJaundiceActivity.this,KeepingBabyWarmAndMalariaActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
			}
			   
		   });
        }else if(take_action_category.equals("jaundice")){
        setContentView(R.layout.activity_just_jaundice);
        TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionJaundiceActivity.this,HomeCareForInfantActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
			}
			   
		   });
        }else if(take_action_category.equals("no jaundice")){
        setContentView(R.layout.activity_no_jaundice);
        TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionJaundiceActivity.this,HomeCareForInfantActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
			}
			   
		   });
		   TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
		   click_here_too.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionJaundiceActivity.this,OtherSeriousConditionsActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
			}
			   
		   });
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Jaundice", start_time.toString(), end_time.toString());
		finish();
	}
}
