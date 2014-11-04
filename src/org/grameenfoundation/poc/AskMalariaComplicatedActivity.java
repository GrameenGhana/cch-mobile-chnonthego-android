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

public class AskMalariaComplicatedActivity extends Activity {

	private Context mContext;
	private ListView listView_malaria;
	private Button button_yes;
	private Button button_no;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_positive_malaria);
	    mContext=AskMalariaComplicatedActivity.this;
	    listView_malaria=(ListView) findViewById(R.id.listView_malaria);
	    String[] items={"Persistent vomiting","Prostration","Convulsions","Jaundice",
    					"Altered consciousness","Severe pallor","Dark, coca-cola coloured urine",
    					"Shock","Persistent temperature ≥ 39 °C","Bleeding"};
	    ListAdapter adapter=new ListAdapter(mContext,items);
	    listView_malaria.setAdapter(adapter);
	    
	    button_yes=(Button) findViewById(R.id.button_yes);
	    button_yes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,TakeActionSevereMalariaActivity.class);
				intent.putExtra("category","severe_malaria");
				startActivity(intent);
			}
	    	
	    });
	    button_no=(Button) findViewById(R.id.button_no);
	    button_no.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,AskMalariaUnComplicatedActivity.class);
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
			      
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			 text.setText(items[position]);
			 
			    return convertView;
		}
		
	}
}
