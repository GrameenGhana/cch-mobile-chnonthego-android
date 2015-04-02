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

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.utils.ImageUtils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CourseListAdapter extends ArrayAdapter<Course> {

	public static final String TAG = CourseListAdapter.class.getSimpleName();

	private final Context ctx;
	private final ArrayList<Course> courseList;
	private final ArrayList<String> sortedList;
	private SharedPreferences prefs;
	
	public CourseListAdapter(Activity context, ArrayList<Course> courseList,ArrayList<String> sortedList) {
		super(context, R.layout.course_list_row, courseList);
		this.ctx = context;
		this.courseList = courseList;
		this.sortedList=sortedList;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.course_download_row_new, parent, false);
	    Course c = courseList.get(position);
	    rowView.setTag(c);
	    ImageButton actionBtn = (ImageButton) rowView.findViewById(R.id.action_btn);
	    actionBtn.setVisibility(View.GONE);
	    TextView courseTitle = (TextView) rowView.findViewById(R.id.module_title);
	    TextView courseProgress=(TextView) rowView.findViewById(R.id.textView_progress);
	    courseProgress.setVisibility(View.GONE);
	    LinearLayout moduleRow=(LinearLayout) rowView.findViewById(R.id.module_row);
	    //courseTitle.setText(c.getTitle(prefs.getString(ctx.getString(R.string.prefs_language), Locale.getDefault().getLanguage())));
	    courseTitle.setText(sortedList.get(position));
	    ProgressBar  pb = (ProgressBar ) rowView.findViewById(R.id.course_progress_bar);
	    pb.setProgress((int) c.getProgress());
	    
	    LayoutParams params = moduleRow.getLayoutParams();
    	params.width = ctx.getResources().getDimensionPixelSize(R.dimen.textView_width);
    	moduleRow.setLayoutParams(params);
    	TextView txt1 = new TextView(ctx);
    	TextView txt2 = new TextView(ctx);
    	txt1.setBackgroundResource(R.drawable.fab_shape);
    	txt1.setText(String.valueOf((int) c.getProgress())+"%");
    	File file=new File(courseList.get(position).getLocation());
    	System.out.println(courseList.get(position).getLocation());
    	if(file.exists()){
    		long size=0;
    		double kilobytes = 0;
    		double megabyte = 0;
    		for(File files:file.listFiles()){
    			if(files.isFile()){
    				size +=file.length();
    			}
    			kilobytes=(size/1024);
    			megabyte=(kilobytes/1024);
    		}
    		if(kilobytes>1000){
    			txt2.setText(String.valueOf(megabyte)+"MB");
    		}else{
    			txt2.setText(String.valueOf(kilobytes)+"KB");
    		}
    	}else{
    		 System.out.println("File does not exists!");
    	}
    	moduleRow.addView(txt1);
    	moduleRow.addView(txt2);
		// set image
    	/*
		if(c.getImageFile() != null){
			ImageView iv = (ImageView) rowView.findViewById(R.id.course_image);
			BitmapDrawable bm = ImageUtils.LoadBMPsdcard(c.getImageFile(), ctx.getResources(), R.drawable.ic_books);
			iv.setImageDrawable(bm);
		}*/
	    return rowView;
	}

}
