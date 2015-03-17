package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ImmunisationScheduleActivity extends BaseActivity {
	
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private ImageView image;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=ImmunisationScheduleActivity.this;
	    setContentView(R.layout.activity_immunisation_schedule);
	    dbh=new DbHelper(ImmunisationScheduleActivity.this);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Immunisation Schedule");
	    image=(ImageView) findViewById(R.id.imageView2);
	    
	    image.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final Dialog nagDialog = new Dialog(ImmunisationScheduleActivity.this);
	            nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
	            nagDialog.setCancelable(false);
	            nagDialog.setContentView(R.layout.image_view_dialog);
	            ImageButton btnClose = (ImageButton)nagDialog.findViewById(R.id.imageButton_close);
	            ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.imageView_largerImage);
	            ivPreview.setImageResource(R.drawable.immunisation_schedule);

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
		dbh.insertCCHLog("Point of Care", "PNC Counselling Immunisation Schedule", start_time.toString(), end_time.toString());
		finish();
	}
}
