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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PostnatalCareMotherDiagnosticToolActivity extends BaseActivity {

//	Context mContext;
	private ListView listView_postnatalSections;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;  
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_postnatal_care_sections);
	    mContext=PostnatalCareMotherDiagnosticToolActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Mother");
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    listView_postnatalSections=(ListView) findViewById(R.id.listView_postnatalCareSections);
	    String[] items={"1. Maternal Emergencies ","2. Records & History","3. Management of Danger Signs",
				"4. Malaria","5. Anaemia ","6. Soft Uterus, Perineal Tear","7. Breast Problems"};
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
				case 1:
					intent=new Intent(mContext,RecordsAskActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent=new Intent(mContext,ManagingDangerSignsMotherPNCActivity.class);
					startActivity(intent);
					break;
				case 3:
					intent=new Intent(mContext,AskMalariaFeverPNCMotherActivity.class);
					startActivity(intent);
					break;
				case 4:
					intent=new Intent(mContext,AnaemiaPNCMotherAskActivity.class);
					startActivity(intent);
					break;
				case 5:
					intent=new Intent(mContext,SoftUterusPNCMotherActivity.class);
					startActivity(intent);
					break;
				case 6:
					intent=new Intent(mContext,BreastProblemsPNCMotherActivity.class);
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
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic:  Mother", start_time.toString(), end_time.toString());
		finish();
	}
		
	}