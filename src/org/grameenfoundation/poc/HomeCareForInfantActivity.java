package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class HomeCareForInfantActivity extends BaseActivity {
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private ImageView image1;
	private ImageView image2;
	private JSONObject json;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=HomeCareForInfantActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Home care for infant");
	    setContentView(R.layout.activity_home_care_infant);
	    dbh=new DbHelper(HomeCareForInfantActivity.this);
	    start_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Counselling: Home care for infant");
			json.put("section", MobileLearning.CCH_COUNSELLING);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    image1=(ImageView) findViewById(R.id.imageView1);
      	image2=(ImageView) findViewById(R.id.imageView2);
      	
      	image1.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				final Dialog nagDialog = new Dialog(HomeCareForInfantActivity.this);
 	            nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
 	            nagDialog.setCancelable(false);
 	            nagDialog.setContentView(R.layout.image_view_dialog);
 	            ImageButton btnClose = (ImageButton)nagDialog.findViewById(R.id.imageButton_close);
 	            ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.imageView_largerImage);
 	            ivPreview.setImageResource(R.drawable.infant_home_care);

 	            btnClose.setOnClickListener(new OnClickListener() {
 	                @Override
 	                public void onClick(View arg0) {

 	                    nagDialog.dismiss();
 	                }
 	            });
 	            nagDialog.show();
 				
 			}
 	    	
 	    });
       	 image2.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				final Dialog nagDialog = new Dialog(HomeCareForInfantActivity.this);
 	            nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
 	            nagDialog.setCancelable(false);
 	            nagDialog.setContentView(R.layout.image_view_dialog);
 	            ImageButton btnClose = (ImageButton)nagDialog.findViewById(R.id.imageButton_close);
 	            ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.imageView_largerImage);
 	            ivPreview.setImageResource(R.drawable.infant_home_care_2);

 	            btnClose.setOnClickListener(new OnClickListener() {
 	                @Override
 	                public void onClick(View arg0) {

 	                    nagDialog.dismiss();
 	                }
 	            });
 	            nagDialog.show();
 				
 			}
 	    	
 	    });
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
