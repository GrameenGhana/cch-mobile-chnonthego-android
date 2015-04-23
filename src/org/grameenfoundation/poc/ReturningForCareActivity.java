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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ReturningForCareActivity extends BaseActivity {

	private Button button_next;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private ImageView image2;
	private JSONObject json;  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext = ReturningForCareActivity.this;
	    setContentView(R.layout.activity_returning_for_care);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Returning for care");
	    dbh=new DbHelper(ReturningForCareActivity.this);
	    start_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Counselling: Returning for care");
			json.put("section", MobileLearning.CCH_COUNSELLING);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    button_next=(Button) findViewById(R.id.button_next);
		image2=(ImageView) findViewById(R.id.imageView2);
	    button_next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			Intent intent=new Intent (ReturningForCareActivity.this,ReturningForCareNextActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    
	    image2.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				final Dialog nagDialog = new Dialog(ReturningForCareActivity.this);
 	            nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
 	            nagDialog.setCancelable(false);
 	            nagDialog.setContentView(R.layout.image_view_dialog);
 	            ImageButton btnClose = (ImageButton)nagDialog.findViewById(R.id.imageButton_close);
 	            ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.imageView_largerImage);
 	            ivPreview.setImageResource(R.drawable.returning_for_care1);

 	            btnClose.setOnClickListener(new OnClickListener() {
 	                @Override
 	                public void onClick(View arg0) {

 	                    nagDialog.dismiss();
 	                }
 	            });
 	           WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
 	          lp.copyFrom(nagDialog.getWindow().getAttributes());
 	          lp.width = WindowManager.LayoutParams.MATCH_PARENT;
 	          lp.height = WindowManager.LayoutParams.MATCH_PARENT;
 	          nagDialog.show();
 	          nagDialog.getWindow().setAttributes(lp);
 	           // nagDialog.show();
 				
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
