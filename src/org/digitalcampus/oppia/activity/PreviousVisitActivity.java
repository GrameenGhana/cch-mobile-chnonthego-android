package org.digitalcampus.oppia.activity;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.poc.FirstVisitActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PreviousVisitActivity extends Activity {

	private ListView listView_previousVisit;
	Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_previous_visit);
	    mContext=PreviousVisitActivity.this;
	    listView_previousVisit=(ListView) findViewById(R.id.listView_previousVisit);
	    String[] listItems={"Yes","No"};
	    PreviousVisitListAdapter adapter=new PreviousVisitListAdapter(mContext,listItems);
	    listView_previousVisit.setAdapter(adapter);
	    listView_previousVisit.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,FirstVisitActivity.class);
					startActivity(intent);
					break;
				}
			}
	    	
	    });
	}
	class PreviousVisitListAdapter extends BaseAdapter{
		Context mContext;
		String[] listItems;
		 public LayoutInflater minflater;
		
		public PreviousVisitListAdapter(Context mContext,String[] listItems){
		this.mContext=mContext;
		this.listItems=listItems;
		 minflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			return listItems.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if( convertView == null ){
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			 text.setText(listItems[position]);
			    return convertView;
		}
		
	}
}
