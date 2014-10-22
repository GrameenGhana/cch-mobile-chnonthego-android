package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.poc.CalculatorsMenuActivity.CalculatorsSectionsListAdapter;

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

public class ReferencesMenuActivity extends Activity {

	Context mContext;
	private ListView listView_calculators;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_postnatal_care_sections);
	    mContext=ReferencesMenuActivity.this;
	    listView_calculators=(ListView) findViewById(R.id.listView_postnatalCareSections);
	    String[] items={"Tetanus Toxoid Immunisation","Baby Immunisation Schedule","IPTP Treatment"};
	    CalculatorsSectionsListAdapter adapter=new CalculatorsSectionsListAdapter(mContext,items);
	    listView_calculators.setAdapter(adapter);
	    listView_calculators.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				String url;
				switch(position){
				case 0:
					intent=new Intent(mContext,ReferencesActivity.class);
					url="file:///android_asset/www/cch/modules/poc/tetanustoxoidimmunisation.html";
					intent.putExtra("url", url);
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(mContext,ReferencesActivity.class);
					url="file:///android_asset/www/cch/modules/poc/immunisation.html";
					intent.putExtra("url", url);
					startActivity(intent);
					break;
				case 2:
					intent=new Intent(mContext,ReferencesActivity.class);
					url="file:///android_asset/www/cch/modules/poc/iptptreatment.html";
					intent.putExtra("url", url);
					startActivity(intent);
					break;
				
			}
			}
	    });
	}
	class CalculatorsSectionsListAdapter extends BaseAdapter{
		Context mContext;
		String[] listItems;
		 public LayoutInflater minflater;
		
		public CalculatorsSectionsListAdapter(Context mContext,String[] listItems){
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
