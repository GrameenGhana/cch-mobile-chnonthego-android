package org.grameenfoundation.cch.activity;


import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.AboutActivity;
import org.digitalcampus.oppia.activity.AppActivity;
import org.digitalcampus.oppia.activity.HelpActivity;
import org.digitalcampus.oppia.activity.OppiaMobileActivity;
import org.digitalcampus.oppia.activity.PrefsActivity;
import org.digitalcampus.oppia.activity.StartUpActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.utils.UIUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class ModuleLearningActivity extends AppActivity implements OnSharedPreferenceChangeListener {

	public static final String TAG = ModuleLearningActivity.class.getSimpleName();
	private SharedPreferences prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.cch_modules_learning_home);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
		
		// set preferred lang to the default lang
		if (prefs.getString(getString(R.string.prefs_language), "").equals("")) {
			Editor editor = prefs.edit();
			editor.putString(getString(R.string.prefs_language), Locale.getDefault().getLanguage());
			editor.commit();
		}
	}

	@Override
	public void onStart() {
		super.onStart();		
	}

	@Override
	public void onResume(){
		super.onResume();
	}
	
	@Override
	public void onPause(){
		super.onPause();
	}
	
	public void onTrainingModuleClick(View v)
	{
		startActivity(new Intent(ModuleLearningActivity.this, OppiaMobileActivity.class));
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		UIUtils.showUserData(menu,this);
	    return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.menu_settings:
				Intent i = new Intent(this, PrefsActivity.class);				
				startActivity(i);
				return true;
			
			case R.id.menu_about:
				startActivity(new Intent(this, AboutActivity.class));
				return true;
			case R.id.menu_help:
				startActivity(new Intent(this, HelpActivity.class));
				return true;
			case R.id.menu_logout:
				logout();
				return true;
		}
		return true;
	}

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

	        // Back?
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	            // Back
	        	startActivity(new Intent(ModuleLearningActivity.this, HomeActivity.class));
	    		finish();
	            return true;
	        }
	        else {
	            // Return
	            return super.onKeyDown(keyCode, event);
	        }
    }
	 
	private void logout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle(R.string.logout);
		builder.setMessage(R.string.logout_confirm);
		builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// wipe activity data
				DbHelper db = new DbHelper(ModuleLearningActivity.this);
				db.onLogout();
				db.close();

				// wipe user prefs
				Editor editor = prefs.edit();
				editor.putString(getString(R.string.prefs_username), "");
				editor.putString(getString(R.string.prefs_api_key), "");
				editor.putInt(getString(R.string.prefs_badges), 0);
				editor.putInt(getString(R.string.prefs_points), 0);
				editor.commit();

				// restart the app
				ModuleLearningActivity.this.startActivity(new Intent(ModuleLearningActivity.this, StartUpActivity.class));
				ModuleLearningActivity.this.finish();

			}
		});
		builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return; // do nothing
			}
		});
		builder.show();
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Log.d(TAG, key + " changed");
		if(key.equalsIgnoreCase(getString(R.string.prefs_server))){
			Editor editor = sharedPreferences.edit();
			if(!sharedPreferences.getString(getString(R.string.prefs_server), "").endsWith("/")){
				String newServer = sharedPreferences.getString(getString(R.string.prefs_server), "").trim()+"/";
				editor.putString(getString(R.string.prefs_server), newServer);
		    	editor.commit();
			}
		}
		if(key.equalsIgnoreCase(getString(R.string.prefs_points)) || key.equalsIgnoreCase(getString(R.string.prefs_badges))){
			supportInvalidateOptionsMenu();
		}
	}
}
