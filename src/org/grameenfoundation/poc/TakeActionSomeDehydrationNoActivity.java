package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.poc.NoInjuriesActivity.NoInjuriesListAdapter;

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

public class TakeActionSomeDehydrationNoActivity extends Activity {

	private String take_action_category;
	private ListView listView_someDehydrationNo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic");
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("yes")){
	    setContentView(R.layout.activity_some_dehydration_yes);
        }else if(take_action_category.equals("no")){
        setContentView(R.layout.activity_some_dehydration_no);
        listView_someDehydrationNo=(ListView) findViewById(R.id.listView_someDehydrationNo);
        String[] items={"Home visit or Outreach Clinic",
        				"CHPS + mother & baby is able to stay for > 4 hours at facility ",
        				"CHPS or Health Center + clients NOT able to stay for 4 hours at facility"};
        SomeDehydrationListAdapter adapter=new SomeDehydrationListAdapter(TakeActionSomeDehydrationNoActivity.this,items);
	    listView_someDehydrationNo.setAdapter(adapter);
	    listView_someDehydrationNo.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					Intent intent;
					switch(position){
					case 0:
						intent =new Intent(TakeActionSomeDehydrationNoActivity.this,TakeActionSomeDehydrationEncounterActivity.class);
						intent.putExtra("category","home_visit");
						startActivity(intent);
						break;
					case 1:
						intent =new Intent(TakeActionSomeDehydrationNoActivity.this,TakeActionSomeDehydrationEncounterActivity.class);
						intent.putExtra("category","chps_one");
						startActivity(intent);
						break;
					case 2:
						intent =new Intent(TakeActionSomeDehydrationNoActivity.this,TakeActionSomeDehydrationEncounterActivity.class);
						intent.putExtra("category","chps_two");
						startActivity(intent);
						break;
					
					}
				
			}
	    	
	    });
        }
	}
	class SomeDehydrationListAdapter extends BaseAdapter{
		Context mContext;
		String[] listItems;
		 public LayoutInflater minflater;
		
		public SomeDehydrationListAdapter(Context mContext,String[] listItems){
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
