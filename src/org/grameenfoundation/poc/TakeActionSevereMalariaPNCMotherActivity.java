package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TakeActionSevereMalariaPNCMotherActivity extends BaseActivity {
	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private String data;
	private Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    mContext=TakeActionSevereMalariaPNCMotherActivity.this;
	    dbh=new DbHelper(TakeActionSevereMalariaPNCMotherActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("severe_malaria")){
        	setContentView(R.layout.activity_severe_malaria);
            getActionBar().setSubtitle("PNC Mother Diagnostic: Malaria");
            data="PNC Mother Diagnostic: Malaria";
        }else if(take_action_category.equals("Ist Trimester")){
        setContentView(R.layout.activity_first_trimester_malaria);
        getActionBar().setSubtitle("PNC Mother Diagnostic: Malaria");
        data="PNC Mother Diagnostic: Malaria";
        }else if(take_action_category.equals("2nd Trimester")){
            setContentView(R.layout.activity_second_trimester_malaria);
            getActionBar().setSubtitle("PNC Mother Diagnostic: Malaria");
            data="PNC Mother Diagnostic: Malaria";
            }
        else if(take_action_category.equals("3rd Trimester")){
            setContentView(R.layout.activity_third_trimester_malaria);
            getActionBar().setSubtitle("PNC Mother Diagnostic: Malaria");
            data="PNC Mother Diagnostic: Malaria";
            }
	
        else if(take_action_category.equals("negative")){
            setContentView(R.layout.activity_malaria_test_negative_pnc_mother);
            getActionBar().setSubtitle("PNC Mother Diagnostic: Malaria");
            data="PNC Mother Diagnostic: Malaria";
            TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
            click_here.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(mContext,KeepingBabyWarmAndMalariaActivity.class);
					intent.putExtra("value", "malaria");
					startActivity(intent);
					
				}
            	
            });
            }
        else if(take_action_category.equals("not done")){
            setContentView(R.layout.activity_malaria_test_not_done_pnc_mother);
            getActionBar().setSubtitle("PNC Mother Diagnostic: Malaria");
            data="PNC Mother Diagnostic: Malaria";
            TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
            click_here.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(mContext,KeepingBabyWarmAndMalariaActivity.class);
					intent.putExtra("value", "malaria");
					startActivity(intent);
					
				}
            	
            });
            }
        else if(take_action_category.equals("severe anaemia")){
            setContentView(R.layout.activity_severe_anaemia_take_action);
            getActionBar().setSubtitle("PNC Mother Diagnostic: Anaemia");
            data="PNC Mother Diagnostic: Anaemia";
            }
        else if(take_action_category.equals("moderate_anaemia")){
        setContentView(R.layout.activity_moderate_anaemia);
        getActionBar().setSubtitle("PNC Mother Diagnostic: Anaemia");
        data="PNC Mother Diagnostic: Anaemia";
        TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
        click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,NutritionCounsellingActivity.class);
				startActivity(intent);
				
			}
        	
        });
        }
        else if(take_action_category.equals("no anaemia")){
            setContentView(R.layout.activity_no_anaemia_take_action);
            getActionBar().setSubtitle("PNC Mother Diagnostic: Anaemia");
            data="PNC Mother Diagnostic: Anaemia";
            TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
            click_here.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(mContext,NutritionCounsellingActivity.class);
    				startActivity(intent);
    				
    			}
            	
            });
            TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
            click_here_too.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(mContext,SoftUterusPNCMotherActivity.class);
    				startActivity(intent);
    				
    			}
            	
            });
            TextView click_here_three=(TextView) findViewById(R.id.textView_clickHereThree);
            click_here_three.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(mContext,BreastProblemsCounsellingActivity.class);
    				startActivity(intent);
    				
    			}
            	
            });
            }
	
	   
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", data , start_time.toString(), end_time.toString());
		finish();
	}
}
