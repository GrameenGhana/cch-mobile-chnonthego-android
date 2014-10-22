package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.adapters.AntenatalCareBaseAdapter;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AntenatalCareActivity extends Activity implements OnItemClickListener{

	private ListView listView_ancMenu;
	private Context mContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_antenatal_care);
	    mContext=AntenatalCareActivity.this;
	    listView_ancMenu=(ListView) findViewById(R.id.listView_antenatalCare);
	    listView_ancMenu.setOnItemClickListener(this);
	    int[] images={R.drawable.ic_diagnostic,R.drawable.ic_counselling,R.drawable.ic_calculator,R.drawable.ic_references};
	    String[] category={"Diagnostic Tool","Counselling per Trimester","Calculators","References"};
	    String [] categoryDetail={"All ANC","First Trimester, Second Trimester, Third Trimester","Malaria Treatment Drug Dosage, Estimating Trimester"
    							,"Tetanus Toxoid Immunisation, Infant Immunisation Schedule, IPTP Treatment"};
	    AntenatalCareBaseAdapter adapter=new AntenatalCareBaseAdapter(mContext,images,category,categoryDetail);
	    listView_ancMenu.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		switch(position){
		
		case 0:
			intent=new Intent(mContext, DiagnosticToolActivity.class);
			startActivity(intent);
			break;
		case 1:
			intent=new Intent(mContext, CounsellingPerTrimesterActivtiy.class);
			startActivity(intent);
			break;
		case 2:
			intent=new Intent(mContext, CalculatorsMenuActivity.class);
			startActivity(intent);
			break;
		case 3:
			intent=new Intent(mContext, ReferencesMenuActivity.class);
			startActivity(intent);
			break;
		}
		
		
	}

}
