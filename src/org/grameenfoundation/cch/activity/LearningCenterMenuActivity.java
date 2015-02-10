package org.grameenfoundation.cch.activity;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.OppiaMobileActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.AntenatalCareBaseAdapter;
import org.grameenfoundation.poc.AntenatalCareActivity;
import org.grameenfoundation.poc.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LearningCenterMenuActivity extends BaseActivity {

	private Context mContext;
	private DbHelper dbh;
	private ListView listView_menu;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_antenatal_care);
	    mContext=LearningCenterMenuActivity.this;
	    dbh=new DbHelper(mContext);
	    getActionBar().setTitle("Learning Center");
	    listView_menu=(ListView) findViewById(R.id.listView_antenatalCare);
	    listView_menu.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,OppiaMobileActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 1:
					intent=new Intent(mContext,LearningReferencesActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
			}
	    	
	    });
	    int[] images={R.drawable.ic_learning_center,R.drawable.ic_knowledge};
	    String[] category={"Learning Modules","References"};
	    AntenatalCareBaseAdapter adapter=new AntenatalCareBaseAdapter(mContext,images,category);
	    listView_menu.setAdapter(adapter);
	    	
	}

}
