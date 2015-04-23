package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TakeActionAskHerActivity extends BaseActivity {

	//private ListView listView_takeAction;
	private TextView textView_takeAction;
	private String take_action_category;
//	Context mContext;
	private ImageView imageView;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private JSONObject json;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic: Managing Danger Signs");
	    mContext=TakeActionAskHerActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", "ANC Diagnostic: Managing Danger Signs");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("take_action");
        }
      
        if(take_action_category.equals("Excessive Vomiting")){
        	  setContentView(R.layout.activity_danger_signs);
        	  textView_takeAction=(TextView) findViewById(R.id.textView_takeActionCategory);
        	  imageView=(ImageView) findViewById(R.id.imageView1);
        	  textView_takeAction.setText(take_action_category);
        	  imageView.setImageResource(R.drawable.excessive_vomiting);
        	  
    	    }else if(take_action_category.equals("Offensive/discolored vaginal discharge")){
    	      setContentView(R.layout.activity_danger_signs);
           	  textView_takeAction=(TextView) findViewById(R.id.textView_takeActionCategory);
           	  imageView=(ImageView) findViewById(R.id.imageView1);
           	  textView_takeAction.setText(take_action_category);
           	  imageView.setImageResource(R.drawable.ic_image_placeholder);
           	  
    	    }else if(take_action_category.equals("Sever abdominal pain")){
    	    	 setContentView(R.layout.activity_danger_signs);
              	  textView_takeAction=(TextView) findViewById(R.id.textView_takeActionCategory);
              	  imageView=(ImageView) findViewById(R.id.imageView1);
              	  textView_takeAction.setText(take_action_category);
              	  imageView.setImageResource(R.drawable.severe_abdominal);
              	  
    	    }else if(take_action_category.equals("Epigastric Pain")){
    	    	setContentView(R.layout.activity_danger_signs);
            	  textView_takeAction=(TextView) findViewById(R.id.textView_takeActionCategory);
            	  imageView=(ImageView) findViewById(R.id.imageView1);
            	  textView_takeAction.setText(take_action_category);
            	  imageView.setImageResource(R.drawable.ic_image_placeholder);
    	    }else if(take_action_category.equals("Edema of the feet, face or ankles")){
    	    	setContentView(R.layout.activity_danger_signs);
          	  textView_takeAction=(TextView) findViewById(R.id.textView_takeActionCategory);
          	  imageView=(ImageView) findViewById(R.id.imageView1);
          	  textView_takeAction.setText(take_action_category);
          	  imageView.setImageResource(R.drawable.edema);
    	    }else if(take_action_category.equals("BP  ≥  90mm Hg")){
    	    	setContentView(R.layout.activity_danger_signs);
            	  textView_takeAction=(TextView) findViewById(R.id.textView_takeActionCategory);
            	  imageView=(ImageView) findViewById(R.id.imageView1);
            	  textView_takeAction.setText(take_action_category);
            	  imageView.setImageResource(R.drawable.ic_image_placeholder);
    	    }else if(take_action_category.equals("Severe headache/blurred vision")){
    	    	setContentView(R.layout.activity_danger_signs);
          	  textView_takeAction=(TextView) findViewById(R.id.textView_takeActionCategory);
          	  imageView=(ImageView) findViewById(R.id.imageView1);
          	  textView_takeAction.setText(take_action_category);
          	  imageView.setImageResource(R.drawable.severe_headache);
    	    }else if(take_action_category.equals("Difficulty Breathing")){
    	    	setContentView(R.layout.activity_difficulty_breathing_anc);
    	    }else if(take_action_category.equals("Shock")){
    	    	setContentView(R.layout.activity_shock_anc);
    	    }

	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
