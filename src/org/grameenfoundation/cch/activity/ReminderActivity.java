package org.grameenfoundation.cch.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.TargetRemindersAdapter;
import org.grameenfoundation.cch.model.FacilityTargets;
import org.joda.time.DateTime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

public class ReminderActivity extends Activity {

	private Spinner spinner_reminders;
	private ListView listView_targets;
	private ArrayList<FacilityTargets> facilityTargets;
	private Context context;
	private DbHelper db;
	private DateTime currentDate;
	private CheckBox cb;
	private Button set_reminders;
	private TargetRemindersAdapter adapter;
	private String type;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_set_reminders);
	    context=ReminderActivity.this;
	    getActionBar().setTitle("Planner");
		getActionBar().setSubtitle("Reminders");
	    db=new DbHelper(context);
	    Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	          type= extras.getString("type");
	        }
	    currentDate=new DateTime();
	    spinner_reminders=(Spinner) findViewById(R.id.spinner_reminderFrequencies);
	    String[] reminder_frequencies={"Daily","Weekly","Monthly"};
	    ArrayAdapter<String> reminders_adapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, reminder_frequencies);
	    spinner_reminders.setAdapter(reminders_adapter);
	    listView_targets=(ListView) findViewById(R.id.listView_reminders);
	    facilityTargets=new ArrayList<FacilityTargets>();
	    facilityTargets=db.getTargetsForMonthViewAgeGroups(currentDate.toString("MMMM"),type);
	    adapter=new TargetRemindersAdapter(context, facilityTargets);
	    listView_targets.setAdapter(adapter);
	    set_reminders=(Button) findViewById(R.id.button_submit);
	    set_reminders.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for (int i=0;i<listView_targets.getChildCount();i++){
					cb=(CheckBox)listView_targets.getChildAt(i).findViewById(R.id.uuid);
					if(cb.isChecked()){
						db.editFacilityTargetReminders(spinner_reminders.getSelectedItem().toString(), listView_targets.getItemIdAtPosition(i));
					}
				}
				facilityTargets=db.getTargetsForMonthViewAgeGroups(currentDate.toString("MMMM"),type);
				if(facilityTargets.size()>0){
					adapter.updateAdapter(facilityTargets);
					listView_targets.setAdapter(adapter);
				}else{
					 Intent intent=new Intent(Intent.ACTION_MAIN);
					 intent.setClass(context, FacilityTargetOptionsActivity.class);
					 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 startActivity(intent);
					 ReminderActivity.this.finish();
					 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
				}
			}
		});
	}

}
