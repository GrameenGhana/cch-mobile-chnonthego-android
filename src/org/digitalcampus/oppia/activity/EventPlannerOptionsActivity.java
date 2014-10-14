package org.digitalcampus.oppia.activity;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.adapters.PlannerBaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class EventPlannerOptionsActivity extends Activity implements OnItemClickListener{

	private Context mContext;
	private ListView listView_eventPlannerOptions;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_event_planner_options);
	    mContext=EventPlannerOptionsActivity.this;
	    listView_eventPlannerOptions=(ListView) findViewById(R.id.listView_eventPlannerOptions);
	    listView_eventPlannerOptions.setOnItemClickListener(this);
	   String[] items={"Plan an event","Set event targets","View planned events"};
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
			startActivity(intent);
			break;
			
		case 1:
			intent=new Intent(mContext,TargetMonthOptionsActivity.class);
			startActivity(intent);
			break;
			
		case 2:
			intent=new Intent(mContext,EventsViewActivity.class);
			startActivity(intent);
			break;
		}
		
	}

}
