package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.cch.model.FacilityTargets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class GroupParticipantsAdapter extends BaseAdapter {
	Context context;
	ArrayList<String> Groups;
	private CheckBox uuid;
	private TextView target_type;
	
	
	public GroupParticipantsAdapter(Context c, ArrayList<String> group) {
		 context = c;
		 Groups = new ArrayList<String>();
		 Groups.addAll(group);
	 }
	 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Groups.size();
	}

	@Override
	public String getItem(int position) {
		
		return Groups.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
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
		uuid.setChecked(false);
		//holder.uuid.setFocusableInTouchMode(true);
		target_type = (TextView) list.findViewById(R.id.action);
		target_type.setText(Groups.get(position));
	
		return list;
	}
	
	public void updateAdapter(ArrayList<String> group) {
        this.Groups= group;

        //and call notifyDataSetChanged
        notifyDataSetChanged();
    }
}
