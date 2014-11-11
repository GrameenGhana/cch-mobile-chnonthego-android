package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.HelpActivity;
import org.digitalcampus.oppia.activity.MainScreenActivity;
import org.grameenfoundation.adapters.PointOfCareBaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PointOfCareActivity extends Activity implements OnItemClickListener {

	private ListView listView_menu;
	private Context mContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_point_of_care);
	    mContext=PointOfCareActivity.this;
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Point of Care");
	    listView_menu=(ListView) findViewById(R.id.listView_pocMenu);
	    listView_menu.setOnItemClickListener(this);
	    int[] imageIds={R.drawable.ic_antenatal,R.drawable.ic_postnatal};
	    String[] category={"Antenatal Care","Postnatal Care"};
	    PointOfCareBaseAdapter adapter=new PointOfCareBaseAdapter(mContext,imageIds,category);
	    listView_menu.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		switch(position){
		case 0:
			intent=new Intent(mContext, AntenatalCareActivity.class);
			startActivity(intent);
			break;
		case 1:
			intent=new Intent(mContext, PostnatalCareActivity.class);
			startActivity(intent);
			break;
			
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.custom_action_bar, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	Intent goHome = new Intent(Intent.ACTION_MAIN);
	 	          goHome.setClass(PointOfCareActivity.this, MainScreenActivity.class);
	 	          goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(goHome);
	 	          finish();
	 	         
	            return true;
	        case R.id.action_help:
	        	intent = new Intent(Intent.ACTION_MAIN);
	        	intent.setClass(PointOfCareActivity.this, HelpActivity.class);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(intent);
	 	          finish();
	 	         
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
