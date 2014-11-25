package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class BreastProblemsPNCMotherActivity extends BaseActivity {

//	private Context mContext;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private Button button_next;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_breast_problems_pnc_mother);
	    mContext=BreastProblemsPNCMotherActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Breast Problems");
	    button_next=(Button) findViewById(R.id.button_next);
	    button_next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,BreastProblemsPNCMotherNextActivity.class);
				startActivity(intent);
				
			}
	    });
	   
	}
	
	public void onBackPressed()
	{
		end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Breast Problems", start_time.toString(), end_time.toString());
		finish();
	}
	
}
