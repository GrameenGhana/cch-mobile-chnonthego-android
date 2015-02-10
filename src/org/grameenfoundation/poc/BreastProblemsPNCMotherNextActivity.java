package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BreastProblemsPNCMotherNextActivity extends BaseActivity {

//	private Context mContext;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private ListView listView_breastProblems;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_breast_problems_pnc_mother_next);
	    mContext=BreastProblemsPNCMotherNextActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Breast Problems");
	    listView_breastProblems=(ListView) findViewById(R.id.listView_breastProblems);
	    String[] items={"Mastitis \n (Click for definition)","Breast engorgement \n (Click for definition)",
	    				"Cracked/sore nipples","No Problems"};
	    
	    ListAdapter adapter=new ListAdapter(mContext,items);
	    listView_breastProblems.setAdapter(adapter);
	    listView_breastProblems.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,TakeActionBreastProblemsPNCMotherActivity.class);
					intent.putExtra("value", "mastitis");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 1:
					intent=new Intent(mContext,TakeActionBreastProblemsPNCMotherActivity.class);
					intent.putExtra("value", "breast_engorgment");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 2:
					intent=new Intent(mContext,TakeActionBreastProblemsPNCMotherActivity.class);
					intent.putExtra("value", "cracked_nipples");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 3:
					intent=new Intent(mContext,TakeActionBreastProblemsPNCMotherActivity.class);
					intent.putExtra("value", "no_problems");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
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
			 
			    return convertView;
		}
	public void onBackPressed()
	{
		end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Breast Problems", start_time.toString(), end_time.toString());
		finish();
	}
	}
}
