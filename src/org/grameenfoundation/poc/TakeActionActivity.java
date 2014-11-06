package org.grameenfoundation.poc;


import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TakeActionActivity extends Activity{

	//private ListView listView_takeAction;
	private String take_action_category;
	//private TextView textView_takeAction;
	private Context mContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=TakeActionActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("Antenatal Care");
	   // listView_takeAction=(ListView) findViewById(R.id.listView_takeAction);
	  //  textView_takeAction=(TextView) findViewById(R.id.textView_takeActionCategory);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("take_action");
        }
        if(take_action_category.equals("Difficulty breathing")){
        	   setContentView(R.layout.activity_difficulty_breathing_anc);
        }else if(take_action_category.equals("Edema")){
        	 setContentView(R.layout.activity_edema_of_feet);
        }else if(take_action_category.equals("Shock")){
        	 setContentView(R.layout.activity_shock_anc);
        }
	}
	
	
}
