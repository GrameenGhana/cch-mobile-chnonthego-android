package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class TreatingUncomplicatedMalariaANCNextActivity extends BaseActivity {

	private Button button_next;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private ImageView image1;
	private ImageView image2;
	private ImageView image3;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_anc_treatment_unomplicate_malaria_preg_next);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC References: Treating UnComplicated Malaria");
	    dbh=new DbHelper(TreatingUncomplicatedMalariaANCNextActivity.this);
	    image1	=(ImageView) findViewById(R.id.imageView1);
	    image2=(ImageView) findViewById(R.id.imageView2);
	    image3=(ImageView) findViewById(R.id.imageView3);
	    start_time=System.currentTimeMillis();
	    button_next=(Button) findViewById(R.id.button_next);
	    button_next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			Intent intent=new Intent(TreatingUncomplicatedMalariaANCNextActivity.this,TreatingUncomplicatedMalariaANCNextTwoActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    image1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final Dialog nagDialog = new Dialog(TreatingUncomplicatedMalariaANCNextActivity.this);
	            nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
	            nagDialog.setCancelable(false);
	            nagDialog.setContentView(R.layout.image_view_dialog);
	            ImageButton btnClose = (ImageButton)nagDialog.findViewById(R.id.imageButton_close);
	            ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.imageView_largerImage);
	            ivPreview.setImageResource(R.drawable.uncomplicated_malaria1);

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
				final Dialog nagDialog = new Dialog(TreatingUncomplicatedMalariaANCNextActivity.this);
	            nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
	            nagDialog.setCancelable(false);
	            nagDialog.setContentView(R.layout.image_view_dialog);
	            ImageButton btnClose = (ImageButton)nagDialog.findViewById(R.id.imageButton_close);
	            ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.imageView_largerImage);
	            ivPreview.setImageResource(R.drawable.uncomplicated_malaria2);

	            btnClose.setOnClickListener(new OnClickListener() {
	                @Override
	                public void onClick(View arg0) {

	                    nagDialog.dismiss();
	                }
	            });
	            nagDialog.show();
				
			}
	    	
	    });
	    image3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final Dialog nagDialog = new Dialog(TreatingUncomplicatedMalariaANCNextActivity.this);
	            nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
	            nagDialog.setCancelable(false);
	            nagDialog.setContentView(R.layout.image_view_dialog);
	            ImageButton btnClose = (ImageButton)nagDialog.findViewById(R.id.imageButton_close);
	            ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.imageView_largerImage);
	            ivPreview.setImageResource(R.drawable.uncomplicated_malaria3);

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
		dbh.insertCCHLog("Point of Care", "ANC References: Treating UnComplicated Malaria" , start_time.toString(), end_time.toString());
		finish();
	}
}
