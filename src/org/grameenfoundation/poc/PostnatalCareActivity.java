package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PostnatalCareActivity extends BaseActivity implements AnimationListener{

//	Context mContext;
	private ListView listView_postnatal;
	private ImageButton imageButton_baby;
	private ImageButton imageButton_mother;
	private LinearLayout linearLayout_counselling;
	private LinearLayout linearLayout_diagnosticDetails;
	private LinearLayout linearLayout_diagnostic;
	private LinearLayout linearLayout_quickReads;
	private Animation slide_up;
	private Animation slide_down;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext = PostnatalCareActivity.this;
	    setContentView(R.layout.activity_postnatal_care_menu);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("Postnatal Care");
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    imageButton_baby=(ImageButton) findViewById(R.id.imageButton_newBorn);
	    imageButton_mother=(ImageButton) findViewById(R.id.imageButton_mother);
	    linearLayout_counselling=(LinearLayout) findViewById(R.id.linearLayout_counselling);
	    linearLayout_diagnosticDetails=(LinearLayout) findViewById(R.id.linearLayout_diagnosticDetails);
	    linearLayout_diagnostic=(LinearLayout) findViewById(R.id.linearLayout_diagnostic);
	    linearLayout_quickReads=(LinearLayout) findViewById(R.id.linearLayout_quickReads);
	    slide_up=AnimationUtils.loadAnimation(getApplicationContext(),
	              R.anim.slide_up);
	    slide_up.setAnimationListener(this);
		   
	    slide_down=AnimationUtils.loadAnimation(getApplicationContext(),
	              R.anim.slide_down);
	    slide_down.setAnimationListener(this);
	    
	    imageButton_baby.setOnClickListener(new OnClickListener(){
	    	Intent intent;
			@Override
			public void onClick(View v) {
				intent=new Intent(mContext,PostnatalCareSectionActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    imageButton_mother.setOnClickListener(new OnClickListener(){
	    	Intent intent;
			@Override
			public void onClick(View v) {
				intent=new Intent(mContext,PostnatalCareMotherDiagnosticToolActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    linearLayout_counselling.setOnClickListener(new OnClickListener(){

			private Intent intent;

			@Override
			public void onClick(View v) {
				intent=new Intent(mContext,PostnatalCareCounsellingTopicsActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    
	    linearLayout_diagnostic.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(linearLayout_diagnosticDetails.getVisibility()==View.VISIBLE){
					linearLayout_diagnosticDetails.setVisibility(View.GONE);
				}else if(linearLayout_diagnosticDetails.getVisibility()==View.GONE){
					linearLayout_diagnosticDetails.setVisibility(View.VISIBLE);
				}
				
			}
	    	
	    });
	    
	    linearLayout_quickReads.setOnClickListener(new OnClickListener(){

			private Intent intent;

			@Override
			public void onClick(View v) {
				intent=new Intent(mContext,QuickReadsMenuActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });

	}
	class PostnatalCareListAdapter extends BaseAdapter{
		Context mContext;
		String[] items;
		 public LayoutInflater minflater;
		 int[] images;
		
		public PostnatalCareListAdapter(Context c, String[] items,int[] images){
			this.mContext=c;
			this.items=items;
			 minflater = LayoutInflater.from(mContext);
			 this.images=images;
		}

		@Override
		public int getCount() {
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if( convertView == null ){
			      
				  convertView = minflater.inflate(R.layout.expandable_group_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_groupCategory);
			 text.setGravity(Gravity.LEFT);
			 text.setPadding(10, 0, 0, 0);
			 text.setText(items[position]);
			 ImageView image=(ImageView) convertView.findViewById(R.id.imageView_groupImage);
			 image.setImageResource(images[position]);
			    return convertView;
		}
	}
	
	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		//dbh.insertCCHLog("Point of Care", "Postnatal Care", start_time.toString(), end_time.toString());
		finish();
	}
}
