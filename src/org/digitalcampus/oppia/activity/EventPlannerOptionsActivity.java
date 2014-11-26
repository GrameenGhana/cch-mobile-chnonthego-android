
package org.digitalcampus.oppia.activity;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.adapters.PlannerBaseAdapter;
import org.grameenfoundation.poc.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class EventPlannerOptionsActivity extends BaseActivity implements OnItemClickListener{

	private Context mContext;
	private ListView listView_eventPlannerOptions;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_event_planner_options);
	    mContext=EventPlannerOptionsActivity.this;
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Planner Options");
	    listView_eventPlannerOptions=(ListView) findViewById(R.id.listView_eventPlannerOptions);
	    listView_eventPlannerOptions.setOnItemClickListener(this);
	   String[] items={"Plan an event","Set targets","View events/ targets"};
	   int[] images={R.drawable.ic_plan_event,R.drawable.ic_event_target,R.drawable.ic_view};
	   PlannerBaseAdapter adapter=new PlannerBaseAdapter(mContext,items,images);
	   listView_eventPlannerOptions.setAdapter(adapter);
	   
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		switch(position){
		case 0:
			intent=new Intent(mContext,PlanEventActivity.class);
			intent.putExtra("mode","plan_mode");
			startActivity(intent);
			break;
			
		case 1:
			intent=new Intent(mContext,TargetSettingActivity.class);
			startActivity(intent);
			break;
			
		case 2:
			intent=new Intent(mContext,PlannerViewOptionsActivity.class);
			startActivity(intent);
			break;
		}
		
	}

}
