package org.grameenfoundation.cch.activity;


import java.util.Locale;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.AboutActivity;
import org.digitalcampus.oppia.activity.AppActivity;
import org.digitalcampus.oppia.activity.HelpActivity;
import org.digitalcampus.oppia.activity.OppiaMobileActivity;
import org.digitalcampus.oppia.activity.PrefsActivity;
import org.digitalcampus.oppia.activity.StartUpActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.service.TrackerService;
import org.digitalcampus.oppia.utils.UIUtils;
import org.grameenfoundation.cch.model.WebAppInterface;
import org.grameenfoundation.cch.activity.PDFActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
public class HomeActivity extends AppActivity implements OnSharedPreferenceChangeListener {

	public static final String TAG = HomeActivity.class.getSimpleName();
	private SharedPreferences prefs;
	
	private static final String HOME_URL = "file:///android_asset/www/cch/index.html";
	private static final String EVENT_BLANK_URL = "file:///android_asset/www/cch/modules/eventplanner/blank.html";
	private static final String EVENT_HOME_URL = "file:///android_asset/www/cch/modules/eventplanner/index.html";
	
	// MODULE IDs
	private static final String EVENT_PLANNER_ID      = "Event Planner";
	private static final String STAYING_WELL_ID       = "Staying Well";
	private static final String POINT_OF_CARE_ID      = "Point of Care";
	private static final String LEARNING_CENTER_ID    = "Learning Center";
	private static final String ACHIEVEMENT_CENTER_ID = "Achievement Center";
	
	private DbHelper dbh; 
	private WebView myWebView;
	
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
		
		setContentView(R.layout.cch_activity_home);

		// set preferred lang to the default lang
		if (prefs.getString(getString(R.string.prefs_language), "").equals("")) {
			Editor editor = prefs.edit();
			editor.putString(getString(R.string.prefs_language), Locale.getDefault().getLanguage());
			editor.commit();
		}
				
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
                
		myWebView = (WebView) findViewById(R.id.webView1);	    	 
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
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
																    
					    Pattern viewEventPattern = Pattern.compile("viewcal\\/(\\d+)");
					    Matcher viewEventMatcher = viewEventPattern.matcher(url);
					    
					    Pattern pdfPattern = Pattern.compile("\\.pdf");
					    Matcher pdfMatcher = pdfPattern.matcher(url);
					    
					    Log.v("CCH: url",url);
					    
						if (viewEventMatcher.find()) {
							    
								long calendarEventID = Long.parseLong(viewEventMatcher.group().replace("viewcal/", ""));
							   
								Intent intent = new Intent(Intent.ACTION_VIEW);

						    	intent.setData(Uri.parse("content://com.android.calendar/events/" + String.valueOf(calendarEventID)));
						    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						    			| Intent.FLAG_ACTIVITY_SINGLE_TOP
						    			| Intent.FLAG_ACTIVITY_CLEAR_TOP
						    			| Intent.FLAG_ACTIVITY_NO_HISTORY
						    			| Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
						    	getApplicationContext().startActivity(intent);
						
						}  else if (url.equals("file:///android_asset/www/cch/modules/eventplanner/viewcal")) {	
							
								
							Intent intent =  new Intent(Intent.ACTION_VIEW);
						    intent.setData(Uri.parse("content://com.android.calendar/time"));
						    startActivity(intent);
						    	
							
						} else if (url.equals("file:///android_asset/www/cch/modules/learning/learner")) {							
							Intent intent = new Intent(getApplicationContext(), OppiaMobileActivity.class);
			                startActivity(intent);	
			            
						} else if (pdfMatcher.find()) {
												           
					         Intent intent = new Intent(getApplicationContext(), PDFActivity.class);
					         intent.putExtra(PDFActivity.EXTRA_PDFFILENAME, url);
					         startActivity(intent);
					         
						} else {
							view.loadUrl(url);
						}
											
						return true;
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
			String module = "unknown";
			Long endtime = System.currentTimeMillis();
				
			// get module
			if (url.contains("/cch/index.html")) {
				module = "Main Page";
				
			} else if (url.contains("/modules/eventplanner")) {
				module = EVENT_PLANNER_ID;
		
			} else if (url.contains("/modules/stayingwell")) {
				module = STAYING_WELL_ID;
	
			} else if (url.contains("/modules/poc")) {
				module = POINT_OF_CARE_ID;

			} else if (url.contains("/modules/learning")) {
				module = LEARNING_CENTER_ID;

			} else if (url.contains("/modules/achievements")) {
				module = ACHIEVEMENT_CENTER_ID;
			}
			
			dbh.insertCCHLog(module, url, starttime.toString(), endtime.toString());	
		}	
	}

	
	@Override
	public void onStart() {
		super.onStart();		
	}

	@Override
	public void onResume(){
		super.onResume();
		
		String url = myWebView.getUrl();
		
		if (url != null)
		{
			if (url.equals(EVENT_BLANK_URL)) {
	 			myWebView.clearHistory();
				myWebView.loadUrl(EVENT_HOME_URL);
	 		 }
		}
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
			
		} else if ((keyCode == KeyEvent.KEYCODE_BACK) && (myWebView.getUrl()).equals(EVENT_HOME_URL)) {
			myWebView.clearHistory();
	    	myWebView.loadUrl(HOME_URL);	
      
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
		int itemId = item.getItemId();
		if (itemId == R.id.menu_settings) {
			Intent i = new Intent(this, PrefsActivity.class);
			startActivity(i);
			return true;
		} else if (itemId == R.id.menu_about) {
			startActivity(new Intent(this, AboutActivity.class));
			return true;
		} else if (itemId == R.id.menu_help) {
			startActivity(new Intent(this, HelpActivity.class));
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
				
				DbHelper db = new DbHelper(HomeActivity.this);
				db.onLogout();
				db.close();
				
				// restart the app
				HomeActivity.this.startActivity(new Intent(HomeActivity.this, StartUpActivity.class));
				HomeActivity.this.finish();
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
