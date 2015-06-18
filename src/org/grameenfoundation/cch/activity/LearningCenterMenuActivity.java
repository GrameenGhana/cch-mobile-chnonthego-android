package org.grameenfoundation.cch.activity;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.AboutActivity;
import org.digitalcampus.oppia.activity.AppActivity;
import org.digitalcampus.oppia.activity.DownloadActivity;
import org.digitalcampus.oppia.activity.HelpActivity;
import org.digitalcampus.oppia.activity.MainScreenActivity;
import org.digitalcampus.oppia.activity.OppiaMobileActivity;
import org.digitalcampus.oppia.activity.StartUpActivity;
import org.digitalcampus.oppia.activity.TagSelectActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.utils.UIUtils;
import org.grameenfoundation.adapters.AntenatalCareBaseAdapter;
import org.grameenfoundation.poc.AntenatalCareActivity;
import org.grameenfoundation.poc.BaseActivity;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LearningCenterMenuActivity extends AppActivity {

	private Context mContext;
	private DbHelper dbh;
	private ListView listView_menu;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_antenatal_care);
	    mContext=LearningCenterMenuActivity.this;
	    dbh=new DbHelper(mContext);
	    getActionBar().setTitle("Learning Center");
	    listView_menu=(ListView) findViewById(R.id.listView_antenatalCare);
	    listView_menu.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					//if(isOnline()){
						intent=new Intent(mContext,CourseGroupActivity.class);
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					//}else{
						//intent=new Intent(mContext,OppiaMobileActivity.class);
						//startActivity(intent);
						//overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);	
					//}
					break;
				case 1:
					intent=new Intent(mContext,ReferencesDownloadActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
			}
	    	
	    });
	    int[] images={R.drawable.references,R.drawable.learning};
	    String[] category={"Learning Modules","References"};
	    AntenatalCareBaseAdapter adapter=new AntenatalCareBaseAdapter(mContext,images,category);
	    listView_menu.setAdapter(adapter);
	    	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_references, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		UIUtils.showUserData(menu,this);
	    return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_about) {
			startActivity(new Intent(this, AboutActivity.class));
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			return true;
		} else if (itemId == R.id.menu_download) {
			startActivity(new Intent(this, ReferencesDownloadActivity.class));
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			return true;
		} else if (itemId == R.id.menu_help) {
			startActivity(new Intent(this, HelpActivity.class));
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			return true;
		} else if (itemId == R.id.menu_logout) {
			logout();
			return true;
		}
		return true;
	}
	private void logout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle(R.string.logout);
		builder.setMessage(R.string.logout_confirm);
		builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// wipe activity data
				DbHelper db = new DbHelper(LearningCenterMenuActivity.this);
				db.onLogout();
				db.close();

				// restart the app
				LearningCenterMenuActivity.this.startActivity(new Intent(LearningCenterMenuActivity.this, MainScreenActivity.class));
				LearningCenterMenuActivity.this.finish();
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);

			}
		});
		builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return; // do nothing
			}
		});
		builder.show();
	}
	
	public boolean isOnline() {
		 boolean haveConnectedWifi = false;
		    boolean haveConnectedMobile = false;

		    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		    for (NetworkInfo ni : netInfo) {
		        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
		            if (ni.isConnected())
		                haveConnectedWifi = true;
		        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
		            if (ni.isConnected())
		                haveConnectedMobile = true;
		    }
		    return haveConnectedWifi || haveConnectedMobile;
	}
}
