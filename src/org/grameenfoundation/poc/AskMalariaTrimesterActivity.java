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

public class AskMalariaTrimesterActivity extends Activity {

	private ListView listView_askMalariaTrimester;
	  Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_ask_malaria_trimester);
	    mContext=AskMalariaTrimesterActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic");
	    listView_askMalariaTrimester=(ListView) findViewById(R.id.listView_askMalariaTrimester);
	    String[] items={"1st Trimester","2nd Trimester","3rd Trimester"};
	    ListAdapter adapter=new ListAdapter(mContext,items);
	    listView_askMalariaTrimester.setAdapter(adapter);
	    listView_askMalariaTrimester.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,TakeActionSevereMalariaActivity.class);
					intent.putExtra("category", "Ist Trimester");
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(mContext,TakeActionSevereMalariaActivity.class);
					intent.putExtra("category", "2nd Trimester");
					startActivity(intent);
					break;
				case 2:
					intent=new Intent(mContext,TakeActionSevereMalariaActivity.class);
					intent.putExtra("category", "3rd Trimester");
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
		
		public ListAdapter(Context c, String[] items){
			this.mContext=c;
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
			 text.setPadding(10, 0, 0, 0);
			 text.setText(items[position]);
			    return convertView;
		}
	}
}
