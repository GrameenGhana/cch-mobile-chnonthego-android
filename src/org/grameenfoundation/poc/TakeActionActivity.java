package org.grameenfoundation.poc;


import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TakeActionActivity extends Activity{

	private ListView listView_takeAction;
	private String take_action_category;
	private TextView textView_takeAction;
	private Context mContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_take_action);
	    mContext=TakeActionActivity.this;
	    listView_takeAction=(ListView) findViewById(R.id.listView_takeAction);
	    textView_takeAction=(TextView) findViewById(R.id.textView_takeActionCategory);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("take_action");
          textView_takeAction.setText(take_action_category);
        }
        if(take_action_category.equals("Difficulty breathing")){
        	 String[] emergencies={"Renmove tight clothing around client's chest",
        			 			"Prop client up if she is conscious",
        			 			"If unconscious, tilt her head back and lift chin to open airway",
        			 			"Clear mouth of any secretions",
        			 			"Open nearby windows to ensure adequate ventilation",
        			 			"Refer to the nearest health centre or hospital immediately",
        			 			"Call provider and arrange transport",
        			 			"Record personal info in maternal health book. Fill out referral form",
        			 			"Accompany client and have a family member accompany client",
        			 			"Follow up with client after discharge from health center or hospital"};
     	    ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1, emergencies);
     	   listView_takeAction.setAdapter(adapter);
        }else if(take_action_category.equals("Refer patient now")){
        	String[] actions={"Refer to the nearest health centre or hospital immediately",
        						"Call provider and arrange transport",
        						"Record personal info in maternal health n=book. Fill out referral form",
        						"Accompany client and have a family member accompany client",
        						"Follow up with client after discharge from health centre or hospital"};
        	 ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1, actions);
       	   listView_takeAction.setAdapter(adapter);
        }else if(take_action_category.equals("Shock")){
        	String[] shock_items={"Put client on her left side with the legs high above the chest.",
        						  "Cover client for warmth",
        						  "Refer to the nearest health centre or hospital immediately",
        						  "Call provider and arrange transport",
        						  " Record personal info in maternal health book. Fill out referral form",
        						  "Accompany client and have a family member accompany client",
        						  "Follow up with client after discharge from health centre or hospital"};
        	 ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1, shock_items);
         	   listView_takeAction.setAdapter(adapter);
        }
	}
	
	
}
