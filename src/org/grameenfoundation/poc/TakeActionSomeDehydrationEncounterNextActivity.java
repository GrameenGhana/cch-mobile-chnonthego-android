package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.poc.NoInjuriesActivity.NoInjuriesListAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TakeActionSomeDehydrationEncounterNextActivity extends BaseActivity {

	private String take_action_category;
	private ListView listView_someDehydrationNo;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Diarrhoea");
	    dbh=new DbHelper(TakeActionSomeDehydrationEncounterNextActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("chps_one_next")){
	    setContentView(R.layout.activity_chps_one_next);
        }else if(take_action_category.equals("chps_two_next")){
        setContentView(R.layout.activity_chps_two_next);
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Diarrhoea" , start_time.toString(), end_time.toString());
		finish();
	}
}
