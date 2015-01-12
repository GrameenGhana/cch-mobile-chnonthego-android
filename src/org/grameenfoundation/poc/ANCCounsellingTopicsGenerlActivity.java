package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ANCCounsellingTopicsGenerlActivity extends BaseActivity {

	private String take_action_category;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time; 
	private String data;
	private Button button_next;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    dbh=new DbHelper(ANCCounsellingTopicsGenerlActivity.this);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if(take_action_category.equals("birth_preparedness")){
        	   setContentView(R.layout.activity_readiness_plan);
        	   getActionBar().setSubtitle("ANC Counselling: Birth Preparedness");
        	   data="Birth Preparedness";
        }else if(take_action_category.equals("drug_abuse")){
        	 setContentView(R.layout.activity_anc_drug_substance_abuse);
        	 getActionBar().setSubtitle("ANC Counselling: Drug Abuse");
        	  data="Drug Abuse";
        }else if(take_action_category.equals("establishing_rapport")){
        	 setContentView(R.layout.activity_anc_establish_rapport);
        	 getActionBar().setSubtitle("ANC Counselling: Establishing Rapport");
        	 data="Establishing Rapport";
        }else if(take_action_category.equals("hiv_care")){
       		setContentView(R.layout.activity_anc_hiv_care);
       		getActionBar().setSubtitle("ANC Counselling: HIV Care");
       		data="HIV Care";
       }else if(take_action_category.equals("malaria_prevention")){
         	 setContentView(R.layout.activity_anc_malaria_prevention);
         	getActionBar().setSubtitle("ANC Counselling: Malaria Prevention");
       		data="Malaria Prevention";
         }else if(take_action_category.equals("nutrition")){
         	 setContentView(R.layout.activity_anc_nutrition);
         	getActionBar().setSubtitle("ANC Counselling: Nutrition");
       		data="Nutrition";
       		button_next=(Button) findViewById(R.id.button_next);
       		button_next.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(ANCCounsellingTopicsGenerlActivity.this,NutritionANCNextActivity.class);
					startActivity(intent);
				}
       			
       		});
         }else if(take_action_category.equals("personal_hygiene")){
         	 setContentView(R.layout.activity_anc_personal_hygiene_practicess);
         	getActionBar().setSubtitle("ANC Counselling: Personal Hygiene Practices");
       		data="Personal Hygiene Practices";
         }else if(take_action_category.equals("pregnancy_danger_signs")){
         	 setContentView(R.layout.activity_pregnancy_danger_signs);
         	getActionBar().setSubtitle("ANC Counselling: Pregnancy Danger Signs");
       		data="Pregnancy Danger Signs";
         }else if(take_action_category.equals("safe_food_preparation")){
         	 setContentView(R.layout.activity_anc_safe_preparation_food);
         	getActionBar().setSubtitle("ANC Counselling: Safe Preparation of Food");
       		data="Safe Preparation of Food";
         }else if(take_action_category.equals("sti_prevention")){
         	 setContentView(R.layout.activity_anc_sti_prevention);
         	getActionBar().setSubtitle("ANC Counselling: STI Prevention");
       		data="STI Prevention";
         }else if(take_action_category.equals("supplementation_during_pregnancy")){
         	 setContentView(R.layout.activity_anc_suppliment_during_pregnancy);
         	getActionBar().setSubtitle("ANC Counselling: Supplementation During Pregnancy");
       		data="Supplementation During Pregnancy";
         }else if(take_action_category.equals("tt_immunisation")){
         	 setContentView(R.layout.activity_tt_immunization_schedule);
         	getActionBar().setSubtitle("ANC Counselling: TT Immunisation Schedule");
       		data="TT Immunisation Schedule";
         }else if(take_action_category.equals("what_expect_during_labour")){
         	 setContentView(R.layout.activity_anc_what_to_expect_during_labour);
         	getActionBar().setSubtitle("ANC Counselling: During Labor");
       		data="What to expect during labour";
         }else if(take_action_category.equals("when_to_return_anc")){
         	 setContentView(R.layout.activity_anc_when_return_anc);
         	getActionBar().setSubtitle("ANC Counselling: When to return for ANC");
       		data=" When to return for ANC";
         }
        
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "ANC Counselling" +data, start_time.toString(), end_time.toString());
		finish();
	}
}
