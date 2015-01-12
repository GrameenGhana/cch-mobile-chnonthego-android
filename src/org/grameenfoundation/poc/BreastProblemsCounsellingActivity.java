package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BreastProblemsCounsellingActivity extends BaseActivity {

	private ListView listView_breastProblem;
//	Context mContext;
	private Button button_next;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_breast_problems_counselling);
	    mContext=BreastProblemsCounsellingActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Breast Problems");
	   listView_breastProblem=(ListView) findViewById(R.id.listView_breastfeeding);
	   /*
	   String[] items={"Position and attach baby correctly on the breast.  Breastfeeding should not hurt",
			   			"If you develop cracked nipples, put some breast milk on them.  Do not use any types of creams or ointments except when prescribed by a health care provider ",
			   			"Feed frequently to prevent breasts from becoming swollen",
			   			"If baby misses a feed you should express some milk to breast soft",
			   			"If one or both breasts become painful or hot to the touch, mother should contact a health care provider",
			   			"If you have any trouble practicing exclusive breastfeeding, discuss with a trained counselor"};
			   			*/
	   String[] items={"Common breastfeeding difficulties","Breast engorgment","Cracked/sore nipples",
			   			"Mastitis"};
	   ListAdapter adapter=new ListAdapter(mContext,items);  
	   listView_breastProblem.setAdapter(adapter);
	   listView_breastProblem.setOnItemClickListener(new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent;
			switch(position){
			case 0:
				intent=new Intent(mContext,BreastProblemsCounsellingNextActivity.class);
				intent.putExtra("value", "common_problems");
				startActivity(intent);
				break;
				
			case 1:
				intent=new Intent(mContext,BreastProblemsCounsellingNextActivity.class);
				intent.putExtra("value", "breast_engorgment");
				startActivity(intent);
				break;
			case 2:
				intent=new Intent(mContext,BreastProblemsCounsellingNextActivity.class);
				intent.putExtra("value", "cracked_nipples");
				startActivity(intent);
				break;
			case 3:
				intent=new Intent(mContext,BreastProblemsCounsellingNextActivity.class);
				intent.putExtra("value", "mastitis");
				startActivity(intent);
				break;
			}
		}
		   
	   });
	}
	
	
	class ListAdapter extends BaseAdapter{
		Context mContext;
		String[] items;
		 public LayoutInflater minflater;
		public ListAdapter(Context mContext,String[] items){
			this.mContext=mContext;
			this.items=items;
			 minflater = LayoutInflater.from(mContext);
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
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if( convertView == null ){
			      
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			
			 text.setText(items[position]);
			 text.setPadding(10, 0, 0, 0);
			    return convertView;
		}
		
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Counselling Breast Problems", start_time.toString(), end_time.toString());
		finish();
	}
}
