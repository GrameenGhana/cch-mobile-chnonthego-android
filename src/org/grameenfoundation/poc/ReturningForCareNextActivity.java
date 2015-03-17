package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ReturningForCareNextActivity extends BaseActivity {

	private Button button_next;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private ImageView image2;  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext = ReturningForCareNextActivity.this;
	    setContentView(R.layout.activity_returning_for_care_next);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Returning for care");
	    dbh=new DbHelper(ReturningForCareNextActivity.this);
	    start_time=System.currentTimeMillis();
	    button_next=(Button) findViewById(R.id.button_next);
	    image2=(ImageView) findViewById(R.id.imageView2);
	    button_next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			Intent intent=new Intent (ReturningForCareNextActivity.this,ReturningForCareNextTwoActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    image2.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				final Dialog nagDialog = new Dialog(ReturningForCareNextActivity.this);
 	            nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
 	            nagDialog.setCancelable(false);
 	            nagDialog.setContentView(R.layout.image_view_dialog);
 	            ImageButton btnClose = (ImageButton)nagDialog.findViewById(R.id.imageButton_close);
 	            ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.imageView_largerImage);
 	            ivPreview.setImageResource(R.drawable.returning_for_care3);

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
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Counselling: Returning for care", start_time.toString(), end_time.toString());
		finish();
	}
}
