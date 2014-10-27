package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DiarrhoeaSectionActivity extends Activity {

	private ListView listView_diarrhoeaSections;
	Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_diarrhoea_sections);
	    mContext=DiarrhoeaSectionActivity.this;
	    listView_diarrhoeaSections=(ListView) findViewById(R.id.listView_diarrhoeaSections);
	    String[] items={"Diarrhoea with Severe Dehydration ",
	    				"Diarrhoea with Some Dehydration"};
	    DiarrhoeaSectionsListAdapter adapter=new DiarrhoeaSectionsListAdapter(mContext,items);
	    listView_diarrhoeaSections.setAdapter(adapter);
	    listView_diarrhoeaSections.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,TakeActionSevereDehydrationActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(mContext,TakeActionSomeDehydration.class);
					startActivity(intent);
					break;
				}
			}
	    	
	    });
	}
	
	
	
	class DiarrhoeaSectionsListAdapter extends BaseAdapter{
		Context mContext;
		String[] listItems;
		 public LayoutInflater minflater;
		
		public DiarrhoeaSectionsListAdapter(Context mContext,String[] listItems){
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
				  convertView = minflater.inflate(R.layout.other_listview_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_otherCategory);
			 text.setText(listItems[position]);
			    return convertView;
		}
		
	}

}
