package org.grameenfoundation.cch.activity;


import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.adapters.PlannerBaseAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PlannerViewOptionsActivity extends Activity implements OnItemClickListener{

	private ListView listView_monthOption;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_month_options);
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("View events/targets");
	    listView_monthOption=(ListView) findViewById(R.id.listView_targetMonthOptions);
	    listView_monthOption.setOnItemClickListener(this);
	    String[] items={"View planned events","View targets"};
		   int[] images={R.drawable.ic_calendar,R.drawable.ic_event_target};
		   PlannerBaseAdapter adapter=new PlannerBaseAdapter(PlannerViewOptionsActivity.this,items,images);
		   
		    listView_monthOption.setAdapter(adapter);
        }
	
	    
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		switch(position){
		case 0:
			intent=new Intent(PlannerViewOptionsActivity.this,EventsViewActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
        	break;
        case 1:
        	intent=new Intent(PlannerViewOptionsActivity.this,NewEventPlannerActivity.class);
        	startActivity(intent);
        	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
        	break;
       
		
	}
	
	}
}
