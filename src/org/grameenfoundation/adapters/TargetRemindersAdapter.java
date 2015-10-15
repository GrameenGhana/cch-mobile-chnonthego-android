package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.model.FacilityTargets;
import org.grameenfoundation.cch.model.RoutineActivity;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class TargetRemindersAdapter extends BaseAdapter{

	Context context;
	ArrayList<FacilityTargets> facilityTargets;
	private CheckBox uuid;
	private TextView target_type;
	
	
	public TargetRemindersAdapter(Context c, ArrayList<FacilityTargets> FacilityTargets) {
		 context = c;
		 facilityTargets = new ArrayList<FacilityTargets>();
		 facilityTargets.addAll(FacilityTargets);
	 }
	 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return facilityTargets.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return Long.valueOf(facilityTargets.get(position).getTargetId());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View list=null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
	        		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			  list = new View(context);
			  list = inflater.inflate(R.layout.routines_detail_listview_single, null);
		}  else {
			 list = (View) convertView;  
		}
		uuid = (CheckBox) list.findViewById(R.id.uuid);
		uuid.setFocusable(false);
		//holder.uuid.setFocusableInTouchMode(true);
		target_type = (TextView) list.findViewById(R.id.action);
		 if(facilityTargets.get(position).getTargetDetail().equals("")){
			 target_type.setText(facilityTargets.get(position).getTargetType());
		 }else if(facilityTargets.get(position).getTargetType().equals("50yrs-60yrs")){
			 target_type.setText(facilityTargets.get(position).getTargetCategory());
		 }else{
			 target_type.setText(facilityTargets.get(position).getTargetDetail());
		 }
	
		return list;
	}
	
	public void updateAdapter(ArrayList<FacilityTargets> facilityList) {
        this.facilityTargets= facilityList;

        //and call notifyDataSetChanged
        notifyDataSetChanged();
    }
}
