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

package org.digitalcampus.oppia.adapter;

import java.util.ArrayList;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.DownloadActivity;
import org.digitalcampus.oppia.listener.InstallCourseListener;
import org.digitalcampus.oppia.listener.UpdateScheduleListener;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.model.DownloadProgress;
import org.digitalcampus.oppia.model.Tag;
import org.digitalcampus.oppia.task.DownloadCourseTask;
import org.digitalcampus.oppia.task.InstallDownloadedCoursesTask;
import org.digitalcampus.oppia.task.Payload;
import org.digitalcampus.oppia.task.ScheduleUpdateTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TagListAdapter extends ArrayAdapter<Tag>{

	public static final String TAG = TagListAdapter.class.getSimpleName();
	
	private final Context ctx;
	private final ArrayList<Tag> tagList;
	private final int[] imageIds;
	public TagListAdapter(Activity context, ArrayList<Tag> tagList,int[] imageIds) {
		super(context, R.layout.tag_row, tagList);
		this.ctx = context;
		this.tagList = tagList;
		this.imageIds=imageIds;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.tag_row, parent, false);
	    Tag t = tagList.get(position);
	    rowView.setTag(t);
	    TextView tagName = (TextView) rowView.findViewById(R.id.tag_name);
	    tagName.setText(ctx.getString(R.string.tag_label,t.getName(),t.getCount()));
	    ImageView tageIcon=(ImageView) rowView.findViewById(R.id.tag_icon);
	    tageIcon.setImageResource(imageIds[position]);
	    return rowView;
	}
	
	public void closeDialogs(){
		//if (myProgress != null){
		//	myProgress.dismiss();
		//}
	}
	

/*




public class TagListAdapter extends BaseExpandableListAdapter implements InstallCourseListener, UpdateScheduleListener{

public static final String TAG = TagListAdapter.class.getSimpleName();
	
	private final Context ctx;
	private final ArrayList<Tag> tagList;
	private final int[] imageIds;
	private final ArrayList<Course> courseList;
	private ProgressDialog downloadDialog;
	private SharedPreferences prefs;
	private boolean inProgress = false;
	
	public TagListAdapter(Activity context, ArrayList<Tag> tagList,ArrayList<Course> courseList,int[] imageIds) {
		super();
		this.ctx = context;
		this.tagList = tagList;
		this.imageIds=imageIds;
		this.courseList = courseList;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
	}
	@Override
	public int getGroupCount() {
		
		return tagList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return courseList.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.tag_row, parent, false);
	    Tag t = tagList.get(groupPosition);
	    rowView.setTag(t);
	    TextView tagName = (TextView) rowView.findViewById(R.id.tag_name);
	    tagName.setText(ctx.getString(R.string.tag_label,t.getName(),t.getCount()));
	    return rowView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.course_download_row, parent, false);
	    Course c = courseList.get(childPosition);
	    rowView.setTag(c);
	    
	    TextView courseTitle = (TextView) rowView.findViewById(R.id.module_title);
	    courseTitle.setText(c.getTitle(prefs.getString(ctx.getString(R.string.prefs_language), Locale.getDefault().getLanguage())));
	    
	    TextView courseDraft = (TextView) rowView.findViewById(R.id.module_desc);
	    if (c.isDraft()){
	    	courseDraft.setText(ctx.getString(R.string.course_draft));
	    } else {
	    	courseDraft.setVisibility(View.GONE);
	    }
	    Button actionBtn = (Button) rowView.findViewById(R.id.action_btn);
	    
	    if(c.isInstalled()){
	    	if(c.isToUpdate()){
	    		actionBtn.setText(R.string.update);
		    	actionBtn.setEnabled(true);
	    	} else if (c.isToUpdateSchedule()){
	    		actionBtn.setText(R.string.update_schedule);
		    	actionBtn.setEnabled(true);
	    	} else {
	    		actionBtn.setText(R.string.installed);
		    	actionBtn.setEnabled(false);
	    	}
	    } else {
	    	actionBtn.setText(R.string.install);
	    	actionBtn.setEnabled(true);
	    }
	    if(!c.isInstalled() || c.isToUpdate()){
	    	actionBtn.setTag(c);
	    	actionBtn.setOnClickListener(new View.OnClickListener() {
             	public void onClick(View v) {
             		Course dm = (Course) v.getTag();
             		Log.d(TAG,dm.getDownloadUrl());
             		
             		ArrayList<Object> data = new ArrayList<Object>();
             		data.add(dm);
             		Payload p = new Payload(data);
             		
             		TagListAdapter.this.showProgressDialog();
             		TagListAdapter.this.inProgress = true;
                     
             		DownloadCourseTask dmt = new DownloadCourseTask(ctx);
             		dmt.setInstallerListener(TagListAdapter.this);
             		dmt.execute(p);
             	}
             });
	    }
	    if(c.isToUpdateSchedule()){
	    	actionBtn.setTag(c);
	    	actionBtn.setOnClickListener(new View.OnClickListener() {
             	public void onClick(View v) {
             		Course dm = (Course) v.getTag();
             		
             		ArrayList<Object> data = new ArrayList<Object>();
             		data.add(dm);
             		Payload p = new Payload(data);
	             		
	             	// show progress dialog
             		TagListAdapter.this.showProgressDialog();
             		TagListAdapter.this.inProgress = true;
                     
             		ScheduleUpdateTask sut = new ScheduleUpdateTask(ctx);
             		sut.setUpdateListener(TagListAdapter.this);
             		sut.execute(p);
             	}
             });
	    }
	    return rowView;
		
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void closeDialogs(){
		//if (myProgress != null){
		//	myProgress.dismiss();
		//}
	}
	
	public void downloadComplete(Payload p) {
		if (p.isResult()){
			// now set task to install
			downloadDialog.setMessage(ctx.getString(R.string.download_complete));
			downloadDialog.setIndeterminate(true);
			InstallDownloadedCoursesTask imTask = new InstallDownloadedCoursesTask(ctx);
			imTask.setInstallerListener(TagListAdapter.this);
			imTask.execute(p);
		} else {
			downloadDialog.setTitle(ctx.getString(R.string.error_download_failure));
			downloadDialog.setMessage(p.getResultResponse());
			downloadDialog.setIndeterminate(true);
		}
	}

	public void installComplete(Payload p) {
		
		
		if(p.isResult()){
			Editor e = prefs.edit();
			e.putLong(ctx.getString(R.string.prefs_last_media_scan), 0);
			e.commit();
			downloadDialog.setTitle(ctx.getString(R.string.install_complete));	
			downloadDialog.setMessage(p.getResultResponse());
			downloadDialog.setIndeterminate(false);
			downloadDialog.setProgress(100);
			// new refresh the course list
			DownloadActivity da = (DownloadActivity) ctx;
			da.refreshCourseList();
		} else {
			downloadDialog.setTitle(ctx.getString(R.string.error_install_failure));	
			downloadDialog.setMessage(p.getResultResponse());
			downloadDialog.setIndeterminate(false);
			downloadDialog.setProgress(100);
		}
		
		this.inProgress = false;
	}
	
	public void downloadProgressUpdate(DownloadProgress dp) {
		downloadDialog.setMessage(dp.getMessage());	
		downloadDialog.setProgress(dp.getProgress());
	}

	public void installProgressUpdate(DownloadProgress dp) {
		downloadDialog.setMessage(dp.getMessage());
		downloadDialog.setProgress(dp.getProgress());
	}
	
	public void updateProgressUpdate(DownloadProgress dp) {
		downloadDialog.setMessage(dp.getMessage());	
		downloadDialog.setProgress(dp.getProgress());
	}
	
	public void updateComplete(Payload p) {
		
		if(p.isResult()){
			downloadDialog.setTitle(ctx.getString(R.string.update_complete));	
			downloadDialog.setMessage(p.getResultResponse());
			downloadDialog.setIndeterminate(false);
			downloadDialog.setProgress(100);
			// new refresh the course list
			DownloadActivity da = (DownloadActivity) ctx;
			da.refreshCourseList();
			Editor e = prefs.edit();
			e.putLong(ctx.getString(R.string.prefs_last_media_scan), 0);
			e.commit();
		} else {
			downloadDialog.setTitle(ctx.getString(R.string.error_update_failure));	
			downloadDialog.setMessage(p.getResultResponse());
			downloadDialog.setIndeterminate(false);
			downloadDialog.setProgress(100);
		}
		
		this.inProgress = false;
	}
	
	
	public void showProgressDialog(){
		// show progress dialog
		downloadDialog = new ProgressDialog(ctx);
 		downloadDialog.setTitle(R.string.install);
 		downloadDialog.setMessage(ctx.getString(R.string.download_starting));
 		downloadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
 		downloadDialog.setProgress(0);
 		downloadDialog.setMax(100);
 		downloadDialog.setCancelable(true);
 		downloadDialog.show();
	}
	
	public void closeDialog(){
		if (downloadDialog != null){
			downloadDialog.dismiss();
		}
	}
	
	public void openDialog(){
		if (downloadDialog != null && this.inProgress){
			downloadDialog.show();
		}
	}
	
	public boolean isInProgress(){
		return this.inProgress;
	}
	
	public void setInProgress(boolean inProgress){
		this.inProgress = inProgress;
	}
	*/
}
