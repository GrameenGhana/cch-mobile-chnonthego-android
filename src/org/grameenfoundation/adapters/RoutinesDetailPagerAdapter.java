package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.model.RoutineActivity;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class RoutinesDetailPagerAdapter extends ArrayAdapter<RoutineActivity> {
	 private Context mContext;
	 private DbHelper dbh;
	 private final ArrayList<RoutineActivity> activities;
		
	 public RoutinesDetailPagerAdapter(Context c, int resource, ArrayList<RoutineActivity> todos) {
		 super(c, resource, todos);
		 mContext = c;
		 dbh = new DbHelper(c);
		 activities = new ArrayList<RoutineActivity>();
		 activities.addAll(todos);
	 }
	 
	 private class ViewHolder {
		 CheckBox uuid;
		 TextView action;
	 }
	
	 @Override
	 public int getCount() {
		 return activities.size();
	 }

	 @Override
	 public RoutineActivity getItem(int position) {
		 return activities.get(position);
	 }

	 @Override
	 public long getItemId(int position) {
		 return 0;
	 }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder = null; 
	    
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
	        		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.routines_detail_listview_single, null);
	       
			holder = new ViewHolder();
			holder.uuid = (CheckBox) convertView.findViewById(R.id.uuid);
			holder.uuid.setFocusable(false);
			//holder.uuid.setFocusableInTouchMode(true);
			holder.action = (TextView) convertView.findViewById(R.id.action);
			convertView.setTag(holder);
		      
			holder.uuid.setOnClickListener( new View.OnClickListener() {  
				public void onClick(View v) {  
					CheckBox cb = (CheckBox) v ;  
					RoutineActivity ra = (RoutineActivity) cb.getTag();
					String message = (cb.isChecked()) ? "Congratulations!" : ":( What happened?";
					Toast.makeText(mContext, message,  Toast.LENGTH_LONG).show();
					dbh.insertSWRoutineDoneActivity(ra.getUUID());
					ra.setDone(cb.isChecked());
					cb.setEnabled(false);
				}  
		    });  
			
			holder.action.setOnClickListener( new View.OnClickListener() {  
				public void onClick(View v) {  
					
				}  
		    });  
			
			
		}  else {
		    holder = (ViewHolder) convertView.getTag();
		}
		 
		RoutineActivity ra = activities.get(position);
		//holder.action.setMovementMethod(LinkMovementMethod.getInstance());
		holder.action.setText(Html.fromHtml(ra.getAction()));
		holder.uuid.setText("");
		holder.uuid.setChecked(ra.isDone());
		if(ra.isDone()) {
			holder.uuid.setEnabled(false);
		}
		holder.uuid.setTag(ra);
	
		return convertView;
	}
}


