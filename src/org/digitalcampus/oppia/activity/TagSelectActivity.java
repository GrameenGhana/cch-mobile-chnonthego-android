/* 
 * This file is part of OppiaMobile - http://oppia-mobile.org/
 * 
 * OppiaMobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * OppiaMobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OppiaMobile. If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalcampus.oppia.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.adapter.DownloadCourseListAdapter;
import org.digitalcampus.oppia.adapter.TagListAdapter;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.listener.APIRequestListener;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.model.Lang;
import org.digitalcampus.oppia.model.Tag;
import org.digitalcampus.oppia.task.APIRequestTask;
import org.digitalcampus.oppia.task.Payload;
import org.digitalcampus.oppia.utils.UIUtils;
import org.grameenfoundation.poc.TakeActionOtherSeriousConditionActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;

import com.bugsense.trace.BugSenseHandler;

public class TagSelectActivity extends AppActivity implements APIRequestListener {

	public static final String TAG = TagSelectActivity.class.getSimpleName();

	private ProgressDialog pDialog;
	private JSONObject json;
	private TagListAdapter tla;
	private ArrayList<Course> courses;
	int[] imageIds;
	ArrayList<Tag> tags;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;

	private int[] imageid;
	

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
	    getActionBar().setTitle("Learning Center");
	    dbh=new DbHelper(TagSelectActivity.this);
	    start_time=System.currentTimeMillis();
	   
	}
	 public void populateImages(){
		 if(tags.size()==2){
			 imageIds=new int[]{R.drawable.ic_family,R.drawable.ic_postnatal};
		 }else {
		 imageIds=new int[tags.size()];  
		 
		 for(int i=0;i<tags.size();i++){
			 imageIds[i]=R.drawable.ic_family;
		 }
		 }
	   }
	@Override
	public void onResume(){
		super.onResume();
		// Get Course list
		if(this.json == null){
			this.getTagList();
		}
		
	}
	
	@Override
	public void onPause(){
		// kill any open dialogs
		if (pDialog != null){
			pDialog.dismiss();
		}
		if (tla != null){
			tla.closeDialogs();
		}
		// kill any open dialogs
				if (pDialog != null){
					pDialog.dismiss();
				}
				
		super.onPause();
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    try {
			this.json = new JSONObject(savedInstanceState.getString("json"));
		} catch (JSONException e) {
		}
	    
	    
	    Log.d(TAG,"restored instance state");
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
	    super.onSaveInstanceState(savedInstanceState);
	    savedInstanceState.putString("json", json.toString());
	   
	    Log.d(TAG,"saved instance state");
	}
	
	private void getTagList() {
		// show progress dialog
		pDialog = new ProgressDialog(this);
		pDialog.setTitle(R.string.loading);
		pDialog.setMessage(getString(R.string.loading));
		pDialog.setCancelable(true);
		pDialog.show();

		APIRequestTask task = new APIRequestTask(this);
		Payload p = new Payload(MobileLearning.SERVER_TAG_PATH);
		task.setAPIRequestListener(this);
		task.execute(p);
	}

	public void refreshTagList() {
		tags = new ArrayList<Tag>();
		System.out.println(json.toString());
		try {
			for (int i = 0; i < (json.getJSONArray("tags").length()); i++) {
				JSONObject json_obj = (JSONObject) json.getJSONArray("tags").get(i);
				Tag t = new Tag();
				t.setName(json_obj.getString("name"));
				t.setId(json_obj.getInt("id"));
				t.setCount(json_obj.getInt("count"));
				tags.add(t);
			}
			 populateImages();
			tla = new TagListAdapter(this, tags,imageIds);
			ListView listView = (ListView) findViewById(R.id.tag_list);
			listView.setAdapter(tla);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Tag t = (Tag) view.getTag();
					Intent i = new Intent(TagSelectActivity.this, DownloadActivity.class);
					Bundle tb = new Bundle();
					tb.putSerializable(Tag.TAG, t);
					i.putExtras(tb);
					startActivity(i);
							
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public void apiRequestComplete(Payload response) {
		// close dialog and process results
		pDialog.dismiss();
	
		if(response.isResult()){
			try {
				json = new JSONObject(response.getResultResponse());
				refreshTagList();
			} catch (JSONException e) {
				if(!MobileLearning.DEVELOPER_MODE){
					BugSenseHandler.sendException(e);
				} else {
					e.printStackTrace();
				}
				UIUtils.showAlert(this, R.string.loading, R.string.error_connection);
				
			}
		} else {
			UIUtils.showAlert(this, R.string.error, R.string.error_connection_required, new Callable<Boolean>() {
				public Boolean call() throws Exception {
					TagSelectActivity.this.finish();
					return true;
				}
			});
		}

	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Learning Center", "Course Download Selection", start_time.toString(), end_time.toString());
		finish();
	}

}
