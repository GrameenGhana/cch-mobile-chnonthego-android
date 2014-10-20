package org.digitalcampus.oppia.activity;

import java.util.Calendar;
import org.digitalcampus.mobile.learningGF.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TargetMonthOptionsActivity extends Activity implements OnItemClickListener{

	private ListView listView_monthOption;
	private String current_month;
	private String month_value;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_month_options);
	    listView_monthOption=(ListView) findViewById(R.id.listView_targetMonthOptions);
	    listView_monthOption.setOnItemClickListener(this);
	    Calendar c = Calendar.getInstance();
        int month=c.get(Calendar.MONTH)+1;
        switch(month){
        case 1:
	        month_value="January";
	        	break;
        case 2:
        	month_value="February";
        	break;
        case 3:
        	month_value="March";
        	break;
        case 4:
        	month_value="April";
        	break;
        case 5:
        	month_value="May";
        	break;
        case 6:
        	month_value="June";
        	break;
        case 7:
        	month_value="July";
        	break;
        case 8:
        	month_value="August";
        	break;
        case 9:
        	month_value="September";
        	break;
        case 10:
        	month_value="October";
        	break;
        case 11:
        	month_value="November";
        	break;
        case 12:
        	month_value="December";
        	break;
        }
	    String[] items={"January","February","March","April","May","June","July","August","September","October","November","December"};
	    ArrayAdapter<String> adapter=new ArrayAdapter<String>(TargetMonthOptionsActivity.this, android.R.layout.simple_list_item_1, items);
	 
	    listView_monthOption.setAdapter(adapter);
	    
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		switch(position){
		case 0:
			current_month="January";
			intent=new Intent(TargetMonthOptionsActivity.this,NewEventPlannerActivity.class);
        	intent.putExtra("month", current_month);
        	startActivity(intent);
        	break;
        case 1:
        	current_month="February";
        	intent=new Intent(TargetMonthOptionsActivity.this,NewEventPlannerActivity.class);
        	intent.putExtra("month", current_month);
        	startActivity(intent);
        	break;
        case 2:
        	current_month="March";
        	intent=new Intent(TargetMonthOptionsActivity.this,NewEventPlannerActivity.class);
        	intent.putExtra("month", current_month);
        	startActivity(intent);
        	break;
        case 3:
        	current_month="April";
        	intent=new Intent(TargetMonthOptionsActivity.this,NewEventPlannerActivity.class);
        	intent.putExtra("month", current_month);
        	startActivity(intent);
        	break;
        case 4:
        	current_month="May";
        	intent=new Intent(TargetMonthOptionsActivity.this,NewEventPlannerActivity.class);
        	intent.putExtra("month", current_month);
        	startActivity(intent);
        	break;
        case 5:
        	current_month="June";
        	intent=new Intent(TargetMonthOptionsActivity.this,NewEventPlannerActivity.class);
        	intent.putExtra("month", current_month);
        	startActivity(intent);
        	break;
        case 6:
        	current_month="July";
        	intent=new Intent(TargetMonthOptionsActivity.this,NewEventPlannerActivity.class);
        	intent.putExtra("month", current_month);
        	startActivity(intent);
        	break;
        case 7:
        	current_month="August";
        	intent=new Intent(TargetMonthOptionsActivity.this,NewEventPlannerActivity.class);
        	intent.putExtra("month", current_month);
        	startActivity(intent);
        	break;
        case 8:
        	current_month="September";
        	intent=new Intent(TargetMonthOptionsActivity.this,NewEventPlannerActivity.class);
        	intent.putExtra("month", current_month);
        	startActivity(intent);
        	break;
        case 9:
        	current_month="October";
        	intent=new Intent(TargetMonthOptionsActivity.this,NewEventPlannerActivity.class);
        	intent.putExtra("month", current_month);
        	startActivity(intent);
        	break;
        case 10:
        	current_month="November";
        	intent=new Intent(TargetMonthOptionsActivity.this,NewEventPlannerActivity.class);
        	intent.putExtra("month", current_month);
        	startActivity(intent);
        	break;
        case 11:
        	current_month="December";
        	intent=new Intent(TargetMonthOptionsActivity.this,NewEventPlannerActivity.class);
        	intent.putExtra("month", current_month);
        	startActivity(intent);
        	break;
		}
		
	}

}
