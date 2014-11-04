package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BreastProblemsCounsellingActivity extends Activity {

	private ListView listView_breastProblem;
	Context mContext;
	private Button button_next;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_breast_problems_counselling);
	    mContext=BreastProblemsCounsellingActivity.this;
	   listView_breastProblem=(ListView) findViewById(R.id.listView_breastfeeding);
	   String[] items={"1. Position and attach baby correctly on the breast.  Breastfeeding should not hurt",
			   			"2. If you develop cracked nipples, put some breast milk on them.  Do not use any types of creams or ointments except when prescribed by a health care provider ",
			   			"3. Feed frequently to prevent breasts from becoming swollen",
			   			"4. If baby misses a feed you should express some milk to breast soft",
			   			"5. If one or both breasts become painful or hot to the touch, mother should contact a health care provider",
			   			"6. If you have any trouble practicing exclusive breastfeeding, discuss with a trained counselor"};
	   ListAdapter adapter=new ListAdapter(mContext,items);  
	   listView_breastProblem.setAdapter(adapter);
	   button_next=(Button) findViewById(R.id.button_next);
	   button_next.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(mContext,BreastProblemsCounsellingNextActivity.class);
			startActivity(intent);
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
			      
				  convertView = minflater.inflate(R.layout.listview_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_textSingle);
			 text.setText(items[position]);
			 if(position%2==0){
				 convertView.setBackgroundColor(getResources().getColor(R.color.BackgroundGrey));
			 }else{
				 convertView.setBackgroundColor(getResources().getColor(R.color.White));
			 }
			    return convertView;
		}
		
	}
}
