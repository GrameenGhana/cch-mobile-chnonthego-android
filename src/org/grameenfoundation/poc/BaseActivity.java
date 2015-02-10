package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.AboutActivity;
import org.digitalcampus.oppia.activity.HelpActivity;
import org.digitalcampus.oppia.activity.MainScreenActivity;
import org.digitalcampus.oppia.activity.StartUpActivity;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
	        	 intent = new Intent(Intent.ACTION_MAIN);
	        	 intent.setClass(BaseActivity.this, MainScreenActivity.class);
	        	 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(intent);
	 	          finish();
	 	         overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
	            return true;
        case R.id.action_help:
        	   	intent = new Intent(Intent.ACTION_MAIN);
	        	intent.setClass(BaseActivity.this, HelpActivity.class);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(intent);
	 	          finish();
	 	         overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
	            return true;
        case R.id.action_about:
	        	intent = new Intent(Intent.ACTION_MAIN);
	        	intent.setClass(BaseActivity.this, AboutActivity.class);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(intent);
	 	          finish();
	 	         overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
	        	return true;
	        	
        case R.id.action_logout:
        	logout();
        	
        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	
	
	}
	
	private void logout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle(R.string.logout);
		builder.setMessage(R.string.logout_confirm);
		builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				DbHelper db = new DbHelper(BaseActivity.this);
				db.onLogout();
				db.close();
				
				// restart the app
				BaseActivity.this.startActivity(new Intent(BaseActivity.this, StartUpActivity.class));
				BaseActivity.this.finish();
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
}
