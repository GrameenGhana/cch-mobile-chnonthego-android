package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.poc.PostnatalCareSectionActivity.PostnatalSectionsListAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PostnatalCareMaternalEmergenciesActivity extends Activity {

	private ListView listView_postnatalMotherSections;
	Context mContext;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private Button button_no;  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_maternal_emergencies);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic:  Maternal Emergenices");
	    dbh=new DbHelper(PostnatalCareMaternalEmergenciesActivity.this);
	    start_time=System.currentTimeMillis();
	    listView_postnatalMotherSections=(ListView) findViewById(R.id.listView_postnatalCareMotherSections);
	    String[] items={"Difficulty breathing or central cyanosis","Shock","Heavy bleeding ",
				"Convulsion, Unconscious "};
	    ListAdapter adapter=new ListAdapter(mContext,items);
	    listView_postnatalMotherSections.setAdapter(adapter);
	    button_no=(Button) findViewById(R.id.button_no);
	    button_no.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(PostnatalCareMaternalEmergenciesActivity.this,AskMotherRecordsPNCActivity.class);
				startActivity(intent);
			}
	    	
	    });
	    listView_postnatalMotherSections.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(PostnatalCareMaternalEmergenciesActivity.this,TakeActionMaternalEmergenciesActivity.class);
					intent.putExtra("value", "difficulty_breathing");
						startActivity(intent);
					break;
				case 1:
					intent=new Intent(PostnatalCareMaternalEmergenciesActivity.this,TakeActionMaternalEmergenciesActivity.class);
					intent.putExtra("value", "shock");
						startActivity(intent);
					break;
				case 2:
					intent=new Intent(PostnatalCareMaternalEmergenciesActivity.this,TakeActionMaternalEmergenciesActivity.class);
					intent.putExtra("value", "heavy_bleeding");
						startActivity(intent);
					break;
				case 3:
					intent=new Intent(PostnatalCareMaternalEmergenciesActivity.this,TakeActionMaternalEmergenciesActivity.class);
					intent.putExtra("value", "convulsion");
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
		public ListAdapter(Context c,String[] items){
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
			 text.setText(items[position]);
			    return convertView;
		}
		
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic:  Maternal Emergenices", start_time.toString(), end_time.toString());
		finish();
	}
}
