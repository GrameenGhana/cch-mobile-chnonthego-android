package org.grameenfoundation.cch.activity;


import java.util.Locale;
import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.AboutActivity;
import org.digitalcampus.oppia.activity.AppActivity;
import org.digitalcampus.oppia.activity.HelpActivity;
import org.digitalcampus.oppia.activity.MainScreenActivity;
import org.digitalcampus.oppia.activity.PrefsActivity;
import org.digitalcampus.oppia.activity.StartUpActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.service.TrackerService;
import org.digitalcampus.oppia.utils.UIUtils;
import org.grameenfoundation.cch.model.StayingWellWebAppInterface;

import android.annotation.SuppressLint;
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
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
public class StayingWellActivity extends AppActivity implements OnSharedPreferenceChangeListener {

	public static final String TAG = StayingWellActivity.class.getSimpleName();
	private SharedPreferences prefs;
	
	private static final String HOME_URL = "file:///android_asset/www/cch/modules/stayingwell/index.html";
	
	// MODULE IDs
	private static final String STAYING_WELL_ID  = "Staying Well";
	
	private DbHelper dbh; 
	public WebView myWebView;
	
	private long pageOpenTime;
    private String oldPageUrl;
				
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbh = new DbHelper(getApplicationContext());
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
		
		Intent service = new Intent(this, TrackerService.class);
		Bundle tb = new Bundle();
		tb.putBoolean("backgroundData", true);
		service.putExtras(tb);
		this.startService(service);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.cch_activity_home);

		// set preferred lang to the default lang
		if (prefs.getString(getString(R.string.prefs_language), "").equals("")) {
			Editor editor = prefs.edit();
			editor.putString(getString(R.string.prefs_language), Locale.getDefault().getLanguage());
			editor.commit();
		}
				
		//getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar().setHomeButtonEnabled(false);
                
		myWebView = (WebView) findViewById(R.id.webView1);	    	 
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.addJavascriptInterface(new StayingWellWebAppInterface(this), "Android");
		myWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);	  
		myWebView.setWebViewClient(new WebViewClient(){
				
			    @Override
			     public void onReceivedError(WebView view, int errorCod,String description, String failingUrl) {
		            Toast.makeText(view.getContext(), description , Toast.LENGTH_LONG).show();
		         }
			    
			     @Override
		         public void onPageFinished(WebView view, String url) {
			 			 saveToLog(pageOpenTime, oldPageUrl);
			 			 oldPageUrl = url;
			 			 pageOpenTime = System.currentTimeMillis();		 		 
		         }
			    
				 @Override
			     public boolean shouldOverrideUrlLoading(WebView view, String url) {									    
		    				Log.v(TAG,"cch to url:" + url);
		    				if (url.equals("file:///android_asset/www/cch/modules/stayingwell/done")) {				
		    					Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
				                startActivity(intent);
		    				} else  {
		    					view.loadUrl(url);
		    				}
		    				return true;
				}		
				 
				 @Override
				public boolean shouldOverrideKeyEvent (WebView view, KeyEvent event) {
					 	if ((event.getAction() == KeyEvent.KEYCODE_BACK) && view.canGoBack()) {
					        //view.goBack();
					 		Log.e("CCH","I am here");
					 		view.loadUrl("javascript:window.App.back();");
					 		
					    } else if((event.getAction() == KeyEvent.KEYCODE_BACK) && !view.canGoBack()) {
					 		Log.e("CCH","I am here");

					    	view.clearHistory();
					    	view.loadUrl(HOME_URL);	        
					    } 
						
					    return false; 
				}
		});
		
	    oldPageUrl = "";
	    pageOpenTime = System.currentTimeMillis();
	    
	    String url = HOME_URL;
	    try 
	    {
			if (!(getIntent().getStringExtra("LOAD_URL")).isEmpty()) {	url = getIntent().getStringExtra("LOAD_URL"); }				
		} catch (NullPointerException e) {}


		myWebView.loadUrl(url);
	}

	
    public void saveToLog(Long starttime, String url) 
	{
		if (! url.isEmpty())
		{
			String module = STAYING_WELL_ID;
			Long endtime = System.currentTimeMillis();	
			String data = "{'type':'url', 'value':'"+url+"'}";
			dbh.insertCCHLog(module,data, starttime.toString(), endtime.toString());	
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
	
	@Override
	public void onDestroy() {
		dbh.close();
		super.onDestroy();
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (myWebView.getUrl()).equals(HOME_URL)) {
			
			finish();
			
		} else if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
	        myWebView.goBack();
    
	    } else if((keyCode == KeyEvent.KEYCODE_BACK) && !myWebView.canGoBack()) {
	    	myWebView.clearHistory();
	    	myWebView.loadUrl(HOME_URL);	        
	    } 
		
	    return true; 
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

	private void logout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle(R.string.logout);
		builder.setMessage(R.string.logout_confirm);
		builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				DbHelper db = new DbHelper(StayingWellActivity.this);
				db.onLogout();
				db.close();
				
				// restart the app
				StayingWellActivity.this.startActivity(new Intent(StayingWellActivity.this, StartUpActivity.class));
				StayingWellActivity.this.finish();
			}
		});
		builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return; // do nothing
			}
		});
		builder.show();
	}



	@Override
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
