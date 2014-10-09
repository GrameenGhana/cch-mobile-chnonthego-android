package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.adapters.DiagnosticToolBaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DiagnosticToolActivity extends Activity implements OnItemClickListener{

	private ListView listView_diagnosticMenu;
	private Context mContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_disgnostic_tool);
	    mContext=DiagnosticToolActivity.this;
	    listView_diagnosticMenu=(ListView) findViewById(R.id.listView_diagnosticMenu);
	    listView_diagnosticMenu.setOnItemClickListener(this);
	    
	    String[] category={"Breast Problems","Complication Readiness & Newborn Danger Signs",
	    					"Family Planning","Home Care for the infant",
	    					"Immunisation Schedule for Infant", "Infant Feeding",
	    					"Kangaroo Mother Care- Keeping Low Birth Weight Baby Warm at Home",
	    					"Keeping Infant Warm & Breastfeeding on the Way to the Hospital",
	    					"Malaria Prevention"};
	   // String[] categoryDetails={"Importance of Exclusive Breastfeeding, Breast Attachement"};
	    DiagnosticToolBaseAdapter adapter=new DiagnosticToolBaseAdapter(mContext,category);
	    listView_diagnosticMenu.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

}
