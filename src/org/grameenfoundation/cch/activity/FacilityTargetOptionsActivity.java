package org.grameenfoundation.cch.activity;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.adapters.PlannerBaseAdapter;
import org.grameenfoundation.cch.model.FamilyPlanningFacilityTargetActivity;
import org.grameenfoundation.poc.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FacilityTargetOptionsActivity extends BaseActivity implements OnItemClickListener {

private ListView listView_monthOption;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_month_options);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("View events/targets");
	    listView_monthOption=(ListView) findViewById(R.id.listView_targetMonthOptions);
	    listView_monthOption.setOnItemClickListener(this);
	    String[] items={"Child Health","Maternal Health","Others"};
		   int[] images={R.drawable.ic_child,R.drawable.ic_maternal,R.drawable.ic_other_facility};
		   PlannerBaseAdapter adapter=new PlannerBaseAdapter(FacilityTargetOptionsActivity.this,items,images);
		   
		    listView_monthOption.setAdapter(adapter);
        }
	
	    
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		switch(position){
		case 0:
			intent=new Intent(FacilityTargetOptionsActivity.this,NewFacilityTargetsActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
        	break;
        case 1:
        	intent=new Intent(FacilityTargetOptionsActivity.this,MaternalHealthFacilityTargetsActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
        	break;
        case 2:
        	intent=new Intent(FacilityTargetOptionsActivity.this,OtherFacilityTargetActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
        	break;
       
		
	}
	
	}
}
