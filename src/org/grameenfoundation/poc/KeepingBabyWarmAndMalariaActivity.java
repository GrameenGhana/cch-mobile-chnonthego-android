package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class KeepingBabyWarmAndMalariaActivity extends BaseActivity {

	private String category;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	String data;
	private ImageView image1;
	private JSONObject json;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	mContext= KeepingBabyWarmAndMalariaActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling");
	    dbh=new DbHelper(KeepingBabyWarmAndMalariaActivity.this);
	    start_time=System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
         category= extras.getString("value");
        }
        if(category.equals("keeping_baby_warm")){
        	  setContentView(R.layout.activity_keeping_baby_warm);
        	  getActionBar().setSubtitle("PNC Counselling: Keeping Baby Warm");
        	  data="PNC Counselling: Keeping Baby Warm";
        }else  if(category.equals("malaria")){
     	   	  setContentView(R.layout.activity_malaria_prevention_counselling);
     	   	  getActionBar().setSubtitle("PNC Counselling: Malaria Prevention");
     	   	  data="PNC Counselling: Malaria Prevention";
        }else  if(category.equals("psychosocial_support")){
      	   	  setContentView(R.layout.activity_psychosocial_support);
      	      getActionBar().setSubtitle("PNC Counselling: Psychosocial Support");
      	      data="PNC Counselling: Psychosocial Support";
    	}else  if(category.equals("rest_activity")){
       	       setContentView(R.layout.activity_rest_and_activity);
       	       getActionBar().setSubtitle("PNC Counselling: Rest & Activity");
       	       data="PNC Counselling: Rest & Activity";
     	}else  if(category.equals("sexual_relations")){
     		   setContentView(R.layout.activity_sexual_relationships);
        	   getActionBar().setSubtitle("PNC Counselling: Sexual Relations");
               data="PNC Counselling: Sexual Relations";
      	}else  if(category.equals("tt_immunization")){
     	       setContentView(R.layout.activity_tt_immunization_schedule);
     	       getActionBar().setSubtitle("PNC Counselling: TT Immunisation");
     	       data="PNC Counselling: TT Immunisation";
      	}else  if(category.equals("bloody_diarrhoea")){
  	           setContentView(R.layout.activity_treating_bloody_diarrhoea);
  	            getActionBar().setSubtitle("PNC Counselling: Treating Bloody Diarrhoea");
  	            image1=(ImageView) findViewById(R.id.imageView1);
  	          image1.setOnClickListener(new OnClickListener(){

  				@Override
  				public void onClick(View v) {
  					final Dialog nagDialog = new Dialog(KeepingBabyWarmAndMalariaActivity.this);
  		            nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
  		            nagDialog.setCancelable(false);
  		            nagDialog.setContentView(R.layout.image_view_dialog);
  		            ImageButton btnClose = (ImageButton)nagDialog.findViewById(R.id.imageButton_close);
  		            ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.imageView_largerImage);
  		            ivPreview.setImageResource(R.drawable.treating_diarrhoea);

  		            btnClose.setOnClickListener(new OnClickListener() {
  		                @Override
  		                public void onClick(View arg0) {

  		                    nagDialog.dismiss();
  		                }
  		            });
  		            nagDialog.show();
  					
  				}
  		    	
  		    });
 	           data="PNC Counselling: Treating Bloody Diarrhoea";
	}
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", data);
			json.put("section", MobileLearning.CCH_COUNSELLING);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
