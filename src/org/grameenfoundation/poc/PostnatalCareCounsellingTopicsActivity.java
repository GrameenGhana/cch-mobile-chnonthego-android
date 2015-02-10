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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PostnatalCareCounsellingTopicsActivity extends BaseActivity {

	private ListView listView_counselling;
//	private Context mContext;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_postnatal_care_counselling);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling");
	    mContext=PostnatalCareCounsellingTopicsActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    listView_counselling=(ListView) findViewById(R.id.listView_counsellingTopics);
	    String[] items={"Breast Problems","Complication Readiness & Newborn Dangers",
	    				"Family Planning in the Postpartum Period","Home Care for the Infant",
	    				"Immunisation Schedule for Infants","Infant Feeding","Kangaroo Mother Care at Home",
	    				"Keeping Baby Warm & Breastfeeding on the Way to the Hospital",
	    				"Malaria Prevention","Nutrition Requirements for the Mother",
	    				"Postpartum Exercises","Psychosocial Support","Rest & Activity",
	    				"Self-Care & Hygiene","Sexual Relations & Safer Sex","TT Immunisation Schedule",
	    				"Treating Local Infections at Home","When to Return for Care",
	    				"Treating Some Dehydration with ORS ","Treatment of Uncomplicated Malaria in Adolescents and Adults",
	    				"Treatment of Bloody Diarrhoea with Benzylpenicillin & Gentamicin"};
	    ListAdapter adapter=new ListAdapter(mContext,items);
	    listView_counselling.setAdapter(adapter);
	    listView_counselling.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,BreastProblemsCounsellingActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 1:
					intent=new Intent(mContext,ComplicationReadinessMenuActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 2:
					intent=new Intent(mContext,FamilyPlanningPostpartumActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 3:
					intent=new Intent(mContext,HomeCareForInfantActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 4:
					intent=new Intent(mContext,ImmunisationScheduleActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 5:
					intent=new Intent(mContext,InfantFeedingMenuActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 6:
					intent=new Intent(mContext,KangarooCareActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 7:
					intent=new Intent(mContext,KeepingBabyWarmAndMalariaActivity.class);
					intent.putExtra("value", "keeping_baby_warm");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 8:
					intent=new Intent(mContext,KeepingBabyWarmAndMalariaActivity.class);
					intent.putExtra("value", "malaria");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 9:
					intent=new Intent(mContext,NutritionCounsellingActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 10:
					intent=new Intent(mContext,PostpartumExercisesActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 11	:
					intent=new Intent(mContext,KeepingBabyWarmAndMalariaActivity.class);
					intent.putExtra("value", "psychosocial_support");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 12	:
					intent=new Intent(mContext,KeepingBabyWarmAndMalariaActivity.class);
					intent.putExtra("value", "rest_activity");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 13:
					intent=new Intent(mContext,SelfCareActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 14:
					intent=new Intent(mContext,KeepingBabyWarmAndMalariaActivity.class);
					intent.putExtra("value", "sexual_relations");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 15:
					intent=new Intent(mContext,KeepingBabyWarmAndMalariaActivity.class);
					intent.putExtra("value", "tt_immunization");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 16:
					intent=new Intent(mContext,TreatingLocationInfectionActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 17:
					intent=new Intent(mContext,ReturningForCareActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 18:
					intent=new Intent(mContext,TreatingDiarrhoeaActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 19:
					intent=new Intent(mContext,TreatingUnComplicatedMalariaActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 20:
					intent=new Intent(mContext,KeepingBabyWarmAndMalariaActivity.class);
					intent.putExtra("value", "bloody_diarrhoea");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				}
			}
	    	
	    });
	}
	class ListAdapter extends BaseAdapter{
		Context mContext;
		String[] listItems;
		 public LayoutInflater minflater;
		
		public ListAdapter(Context mContext,String[] listItems){
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
		dbh.insertCCHLog("Point of Care", "PNC Counselling", start_time.toString(), end_time.toString());
		finish();
	}
}
