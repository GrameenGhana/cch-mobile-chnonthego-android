package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SevereDiseasesNextActivity extends BaseActivity {

	private ListView listView_severDiseaseSymptoms;
//	private Context mContext;
	private Button button_no;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private View button_next; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext = SevereDiseasesNextActivity.this;
	    setContentView(R.layout.activity_very_severe_disease_next);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Very Severe Diseases");
	    dbh=new DbHelper(SevereDiseasesNextActivity.this);
	    start_time=System.currentTimeMillis();
	    button_next=(Button) findViewById(R.id.button_next);
	    button_next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SevereDiseasesNextActivity.this,SevereDiseasesNextTwoActivity.class);
				startActivity(intent);
				
			}
	    	
	    });
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Very Severe Diseases", start_time.toString(), end_time.toString());
		finish();
	}
}
