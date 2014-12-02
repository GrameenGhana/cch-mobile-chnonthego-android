package org.digitalcampus.oppia.activity;

import java.util.Calendar;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.mobile.learningGF.R.color;
import org.grameenfoundation.adapters.PlannerBaseAdapter;
import org.grameenfoundation.cch.activity.TargetViewActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PlannerViewOptionsActivity extends Activity implements OnItemClickListener{

	private ListView listView_monthOption;
	private String current_month;
	private String month_value;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_month_options);
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("View events/Targets");
	    listView_monthOption=(ListView) findViewById(R.id.listView_targetMonthOptions);
	    listView_monthOption.setOnItemClickListener(this);
	    String[] items={"View planned events","View targets"};
		   int[] images={R.drawable.ic_view,R.drawable.ic_view};
		   PlannerBaseAdapter adapter=new PlannerBaseAdapter(PlannerViewOptionsActivity.this,items,images);
		   
		    listView_monthOption.setAdapter(adapter);
        }
	
	    
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		switch(position){
		case 0:
			current_month="November";
			intent=new Intent(PlannerViewOptionsActivity.this,EventsViewActivity.class);
        	startActivity(intent);
        	break;
        case 1:
        	current_month="December";
        	intent=new Intent(PlannerViewOptionsActivity.this,NewEventPlannerActivity.class);
        	startActivity(intent);
        	break;
       
		
	}
	
	}
}
