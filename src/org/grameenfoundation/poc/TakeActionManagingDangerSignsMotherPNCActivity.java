package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TakeActionManagingDangerSignsMotherPNCActivity extends Activity {

	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private String take_action_category;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs");
	    dbh=new DbHelper(TakeActionManagingDangerSignsMotherPNCActivity.this);
	    start_time=System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }   
        if(take_action_category.equals("difficulty_breathing")){
        	   setContentView(R.layout.activity_mng_danger_sign_cyanosis);
        	
        }else if(take_action_category.equalsIgnoreCase("shock")){
            setContentView(R.layout.activity_mng_danger_sign_shock);// Same as the ANC content															
         }else if(take_action_category.equalsIgnoreCase("heavy_bleeding")){
             setContentView(R.layout.activity_mng_danger_sign_heavy_bleeding);// Same as the ANC content															
         }else if(take_action_category.equalsIgnoreCase("convulsing")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Convulsing (now or recently), Unconscious ");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	// image.setImageResource(R.drawable.ic_image_placeholder);
        	 image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("severe_headache")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Severe headache/blurred vision ");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	image.setImageResource(R.drawable.severe_headache);
        	// image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("diastolic")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Diastolic BP ≥ 90 mmHg");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	//image.setImageResource(R.drawable.severe_headache);
        	 image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("severe_abdominal")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Severe abdominal pain ");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	//image.setImageResource(R.drawable.severe_headache);
        	 image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("persistent_vomiting")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Persistent vomiting");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	image.setImageResource(R.drawable.persistent_vomiting);
        	// image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("persistent_vomiting")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Persistent vomiting");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	//image.setImageResource(R.drawable.persistent_vomiting);
        	image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("pain_in_calf")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Pain in calf with or without swelling ");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	image.setImageResource(R.drawable.edema);
        	// image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("painful_or_tender_wound")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Painful or tender wound");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	//image.setImageResource(R.drawable.edema);
        	image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("pain_on_urination")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Pain on urination/dribbling urine");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	//image.setImageResource(R.drawable.edema);
        	image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("pallor")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Pallor");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	image.setImageResource(R.drawable.pallor);
        	//image.setVisibility(View.GONE);
         }else if(take_action_category.equalsIgnoreCase("abnormal_behaviour")){
        	 setContentView(R.layout.activity_mng_danger_sign_gen_takeaction);
        	 TextView text=(TextView) findViewById(R.id.textView_takeActionCategory);
        	 text.setText("Abnormal behaviour/depression  ");
        	 ImageView image=(ImageView) findViewById(R.id.imageView1);
        	//image.setImageResource(R.drawable.pallor);
        	image.setVisibility(View.GONE);
         }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Managing Danger Signs", start_time.toString(), end_time.toString());
		finish();
	}
}
