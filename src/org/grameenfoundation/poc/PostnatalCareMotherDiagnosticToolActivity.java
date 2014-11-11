package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.poc.PostnatalCareSectionActivity.PostnatalSectionsListAdapter;

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

public class PostnatalCareMotherDiagnosticToolActivity extends Activity {

	Context mContext;
	private ListView listView_postnatalSections;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_postnatal_care_sections);
	    mContext=PostnatalCareMotherDiagnosticToolActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic");
	    listView_postnatalSections=(ListView) findViewById(R.id.listView_postnatalCareSections);
	    String[] items={"Maternal Emergencies ","Records & History","Management of Danger Signs",
				"Malaria","Anaemia ","Soft Uterus, Perineal Tear","Breast Problems"};
	    PostnatalSectionsListAdapter adapter=new PostnatalSectionsListAdapter(mContext,items);
	    listView_postnatalSections.setAdapter(adapter);
	    listView_postnatalSections.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,MaternalEmergenciesActivity.class);
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
				  convertView = minflater.inflate(R.layout.other_listview_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_otherCategory);
			 text.setText(listItems[position]);
			    return convertView;
		}
	}
		
	}