package org.grameenfoundation.cch.activity;



import android.app.Activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class SearchableActivity extends Activity  {

	public static final String TAG = SearchableActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
		
		// Get the intent, verify the action and get the query
	    Intent intent = getIntent();
	    handleIntent(intent);
	}

	public void onNewIntent(Intent intent)
	{
		setIntent(intent);
		handleIntent(intent);
	}
	
	public void handleIntent(Intent intent)
	{
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
		    String query = intent.getStringExtra(SearchManager.QUERY);
		    doSearch(query);
		}
	}
	
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		
	}
	
	public void doSearch(String query) 
	{
		this.onSearchRequested();
		Toast.makeText(this, query, Toast.LENGTH_LONG).show();
		/*SearchView searchView = 
			      (SearchView) MenuItemCompat.getActionView(item);
			SearchManager searchManager = 
			      (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			SearchableInfo info = 
			      searchManager.getSearchableInfo(getComponentName());
			searchView.setSearchableInfo(info);*/
		
	}
	
	/*@Override
	public boolean onSearchRequested()
	{
		return true;
	}*/
}
