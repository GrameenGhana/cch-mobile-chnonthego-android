package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TakeActionAskHerActivity extends Activity {

	//private ListView listView_takeAction;
	private TextView textView_takeAction;
	private String take_action_category;
	Context mContext;
	private ImageView imageView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   
	    mContext=TakeActionAskHerActivity.this;
	    
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
	    //listView_takeAction=(ListView) findViewById(R.id.listView_takeAction);
	    /*
	    if(take_action_category.equals("Excessive Vomiting")){
	    String[] items={"Refer to the nearest health centre or hospital immediately",
	    				"Call provider and arrange transport",
	    				"Record personal info in maternal health book. Fill out referral form",
	    				"Accompany client and have a family member accompany client",
	    				"Follow up with client after discharge from health centre or hospital"};
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1, items);
  	   listView_takeAction.setAdapter(adapter);
	    }else if(take_action_category.equals("Offensive/discolored discharge")){
	    	String[] items={"Refer to the nearest health centre or hospital immediately",
    				"Call provider and arrange transport",
    				"Record personal info in maternal health book. Fill out referral form",
    				"Accompany client and have a family member accompany client",
    				"Follow up with client after discharge from health centre or hospital"};
	ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1, items);
	   listView_takeAction.setAdapter(adapter);
	    }else if(take_action_category.equals("Sever abdominal pain")){
	    	String[] items={"Refer to the nearest health centre or hospital immediately",
    				"Call provider and arrange transport",
    				"Record personal info in maternal health book. Fill out referral form",
    				"Accompany client and have a family member accompany client",
    				"Follow up with client after discharge from health centre or hospital"};
	ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1, items);
	   listView_takeAction.setAdapter(adapter);
	    }else if(take_action_category.equals("Feotal Movements")){
	    	String[] items={"Refer to the nearest health centre or hospital immediately",
    				"Call provider and arrange transport",
    				"Record personal info in maternal health book. Fill out referral form",
    				"Accompany client and have a family member accompany client",
    				"Follow up with client after discharge from health centre or hospital"};
	ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1, items);
	   listView_takeAction.setAdapter(adapter);
	    }else if(take_action_category.equals("Epigastric Pain")){
	    	String[] items={"Refer to the nearest health centre or hospital immediately",
    				"Call provider and arrange transport",
    				"Record personal info in maternal health book. Fill out referral form",
    				"Accompany client and have a family member accompany client",
    				"Follow up with client after discharge from health centre or hospital"};
	ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1, items);
	   listView_takeAction.setAdapter(adapter);
	    }else if(take_action_category.equals("Oedaema of the feet, face or ankles")){
	    	String[] items={"Refer to the nearest health centre or hospital immediately",
    				"Call provider and arrange transport",
    				"Record personal info in maternal health book. Fill out referral form",
    				"Accompany client and have a family member accompany client",
    				"Follow up with client after discharge from health centre or hospital"};
	ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1, items);
	   listView_takeAction.setAdapter(adapter);
	    }else if(take_action_category.equals("REFER PATIENT NOW!")){
	    	String[] items={"Refer to the nearest health centre or hospital immediately",
    				"Call provider and arrange transport",
    				"Record personal info in maternal health book. Fill out referral form",
    				"Accompany client and have a family member accompany client",
    				"Follow up with client after discharge from health centre or hospital"};
	ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1, items);
	   listView_takeAction.setAdapter(adapter);
	    }else if(take_action_category.equals("DIFFICULTY BREATHING!")){
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
	    */
	}

}
