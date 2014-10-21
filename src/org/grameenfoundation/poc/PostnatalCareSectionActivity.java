package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

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

public class PostnatalCareSectionActivity extends Activity {

	private ListView listView_postnatalSections;
	private Context mContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_postnatal_care_sections);
	    mContext=PostnatalCareSectionActivity.this;
	    listView_postnatalSections=(ListView) findViewById(R.id.listView_postnatalCareSections);
	    String[] items={"Newborn Emergency","Records & History","Very Severe Disease & Local Bacterial Infections",
				"Jaundice","Other Serious Conditions, Birth Injury & Abnormalities",
				"Diarrhoea","HIV Infection ","Feeding Problems, Low Weight for Age"};
	    PostnatalSectionsListAdapter adapter=new PostnatalSectionsListAdapter(mContext,items);
	    listView_postnatalSections.setAdapter(adapter);
	    listView_postnatalSections.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,NewbornEmergenciesActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(mContext,RecordsAskActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent=new Intent(mContext,SevereDiseasesActivity.class);
					startActivity(intent);
					break;
				}
				
			}
	    	
	    });
	}
	class PostnatalSectionsListAdapter extends BaseAdapter{
		Context mContext;
		String[] listItems;
		 public LayoutInflater minflater;
		
		public PostnatalSectionsListAdapter(Context mContext,String[] listItems){
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
			 text.setGravity(Gravity.LEFT);
			 text.setText(listItems[position]);
			    return convertView;
		}
		
	}

}
