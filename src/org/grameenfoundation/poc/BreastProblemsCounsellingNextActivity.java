package org.grameenfoundation.poc;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.activity.EventsViewActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class BreastProblemsCounsellingNextActivity extends BaseActivity {

	private ExpandableListView expandableListView_breastProblems;
//	 private Context mContext;
	 private DbHelper dbh;
		private Long start_time;
		private Long end_time;
		private String take_action_category;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=BreastProblemsCounsellingNextActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling:  Breast Problems");
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
	    if(take_action_category.equals("common_problems")){
	    	 setContentView(R.layout.activity_breast_counselling_next);
	    }else if(take_action_category.equals("breast_engorgment")){
	    	setContentView(R.layout.activity_breast_engorgment_counselling);
	    }else if(take_action_category.equals("cracked_nipples")){
	    	setContentView(R.layout.activity_cracked_nipples_counselling);
	    }else if(take_action_category.equals("mastitis")){
	    	setContentView(R.layout.activity_mastitis_counselling);
	    }
	 
	}
	

	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Counselling Breast Problems", start_time.toString(), end_time.toString());
		finish();
	}
}
