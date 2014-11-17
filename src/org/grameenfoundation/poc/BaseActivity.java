package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.AboutActivity;
import org.digitalcampus.oppia.activity.HelpActivity;


import org.digitalcampus.oppia.activity.MainScreenActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BaseActivity  extends Activity{
	
	Context mContext;


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
	 	          goHome.setClass(mContext, MainScreenActivity.class);
	 	          goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(goHome);
	 	          finish();
	 	         
	            return true;
//	        case R.id.action_help:
//	        	intent = new Intent(Intent.ACTION_MAIN);
//	        	intent.setClass(BaseActivity.this, HelpActivity.class);
//	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//	 	          startActivity(intent);
//	 	          finish();
//	 	         
//	            return true;
//	        case R.id.action_about:
//	        	intent = new Intent(Intent.ACTION_MAIN);
//	        	intent.setClass(BaseActivity.this, AboutActivity.class);
//	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//	 	          startActivity(intent);
//	 	          finish();
//	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	
	
	}
}
