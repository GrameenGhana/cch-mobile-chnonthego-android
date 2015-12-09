package org.grameenfoundation.adapters;

import java.util.ArrayList;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.model.FacilityTargets;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class FacilityTargetAdapter extends BaseAdapter implements Filterable{

	Context context;
	ArrayList<FacilityTargets> facilityTargets;
	private TextView target_type;
	private TextView reminder;
	private TextView textView_achieved;
	private TextView textView_lastUpdated;
	private TextView textView_startDate;
	private TextView textView_dueDate;
	private double percentage;
	private String percentage_achieved;
	private ArrayList<FacilityTargets> mOriginalValues;
	private TextView target_overall;
	private double overall_percentage;
	private String overall_percentage_achieved;
	DbHelper db;
	
	public FacilityTargetAdapter(Context c, ArrayList<FacilityTargets> FacilityTargets) {
		 context = c;
		 facilityTargets = new ArrayList<FacilityTargets>();
		 db=new DbHelper(c);
		 facilityTargets.addAll(FacilityTargets);
	 }
	@Override
	public int getCount() {
		return facilityTargets.size();
	}

	@Override
	public String[] getItem(int position) {
		String[] items;
		
		items=new String[]{facilityTargets.get(position).getTargetId(),//0
				facilityTargets.get(position).getTargetType(),//1
				facilityTargets.get(position).getTargetNumber(),//2
				facilityTargets.get(position).getTargetNumberAchieved(),//3
				facilityTargets.get(position).getTargetNumberRemaining(),//4
				facilityTargets.get(position).getTargetStartDate(),//5
				facilityTargets.get(position).getTargetEndDate(),//6
				facilityTargets.get(position).getTargetReminder(),//7
				facilityTargets.get(position).getTargetStatus(),//8
				facilityTargets.get(position).getTargetLastUpdated(),//9
				facilityTargets.get(position).getTargetGroupMembers(),//10
				facilityTargets.get(position).getTargetMonth(),//11
				facilityTargets.get(position).getTargetOldId(),//12
				facilityTargets.get(position).getTargetDetail(),//13
				facilityTargets.get(position).getTargetOverall(),//14
				facilityTargets.get(position).getTargetGroup()};//15
		return items;
	}

	@Override
	public long getItemId(int position) {
		return Long.valueOf(facilityTargets.get(position).getTargetId());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View list=null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
	        		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			  list = new View(context);
			  list = inflater.inflate(R.layout.facility_target_listview_single, null);
		}  else {
			 list = (View) convertView;  
		}
		 int number_achieved_today=Integer.valueOf(facilityTargets.get(position).getTargetNumberAchieved());
		    percentage= ((double)number_achieved_today/Integer.parseInt(facilityTargets.get(position).getTargetNumber()))*100;	
		    percentage_achieved=String.format("%.0f", percentage);
		    overall_percentage= ((double)number_achieved_today/Integer.valueOf(facilityTargets.get(position).getTargetOverall()))*100;	
		   overall_percentage_achieved=String.format("%.0f", overall_percentage);
		 target_type = (TextView) list.findViewById(R.id.textView_targetName);
		 target_overall=(TextView) list.findViewById(R.id.textView_overall);
		 reminder = (TextView) list.findViewById(R.id.textView_reminder);  
		 textView_achieved = (TextView) list.findViewById(R.id.textView_achieved);  
		 textView_lastUpdated = (TextView) list.findViewById(R.id.textView_lastUpdated);  
		 textView_startDate = (TextView) list.findViewById(R.id.textView_startDate);
		 textView_dueDate = (TextView) list.findViewById(R.id.textView_dueDate);
		 System.out.println(facilityTargets.get(position).getTargetId()+facilityTargets.get(position).getTargetType());
		 if(facilityTargets.get(position).getTargetDetail().equals("")){
			 target_type.setText(facilityTargets.get(position).getTargetType());
			 
		 }else if(facilityTargets.get(position).getTargetType().equals("50yrs-60yrs")){
			 target_type.setText(facilityTargets.get(position).getTargetCategory());
		 }else{
			 target_type.setText(facilityTargets.get(position).getTargetDetail());
		 }
		 reminder.setText(facilityTargets.get(position).getTargetReminder());
		 String first="<font color='#520000'><strong>"+"Annual Target: "+"</strong></font>";
		 String second="<font color='#520000'><strong>"+"Last Updated: "+"</strong></font>";
		 String third="<font color='#520000'><strong>"+"Month's Target progress: "+"</strong></font>";
		 target_overall.setText(Html.fromHtml(first+facilityTargets.get(position).getTargetOverall()+" ("+overall_percentage_achieved+"%)"));
		 
		 textView_achieved.setText(Html.fromHtml(third+facilityTargets.get(position).getTargetNumberAchieved()+"/"+String.valueOf(facilityTargets.get(position).getTargetNumber())+" ("+percentage_achieved+"%)"));
		 textView_lastUpdated.setText(Html.fromHtml(second+facilityTargets.get(position).getTargetLastUpdated()));
		 textView_startDate.setText("Start: "+facilityTargets.get(position).getTargetStartDate());
		 textView_dueDate.setText("Due: "+facilityTargets.get(position).getTargetEndDate());
		return list;
	}

	
	@Override
	 public Filter getFilter() {
	        Filter filter = new Filter() {
	         
				@Override
	            protected void publishResults(CharSequence constraint, FilterResults results) {
	            	facilityTargets = (ArrayList<FacilityTargets>) results.values; // has the filtered values
	                notifyDataSetChanged();  // notifies the data with new filtered values
	            }
				
	           @Override
	            protected FilterResults performFiltering(CharSequence constraint) {
	               FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
	               ArrayList<FacilityTargets> FilteredArrList = new ArrayList<FacilityTargets>();

	               if (mOriginalValues == null) {
	                   mOriginalValues = new ArrayList<FacilityTargets>(facilityTargets); // saves the original data in mOriginalValues
	               }

	               /********
	                *
	                *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
	                *  else does the Filtering and returns FilteredArrList(Filtered)
	                *
	                ********/
	               if (constraint == null || constraint.length() == 0) {

	                   // set the Original result to return
	                   results.count = mOriginalValues.size();
	                   results.values = mOriginalValues;
	               } else {
	            	   mOriginalValues = new ArrayList<FacilityTargets>(facilityTargets);
	                   constraint = constraint.toString().toLowerCase();
	                   
	                   for (int i = 0; i < mOriginalValues.size(); i++) {
	                       String data = mOriginalValues.get(i).getTargetReminder();
	                       if (data.toLowerCase().startsWith(constraint.toString())) {
	                           FilteredArrList.add(new FacilityTargets(mOriginalValues.get(i).getTargetId(),
	                                                         mOriginalValues.get(i).getTargetType(),
	                                                         mOriginalValues.get(i).getTargetDetail(),
	                                                         mOriginalValues.get(i).getTargetCategory(),
	                                                         mOriginalValues.get(i).getTargetNumber(),
	                                                         mOriginalValues.get(i).getTargetNumberAchieved(),
	                                                         mOriginalValues.get(i).getTargetNumberRemaining(),
	                                                         mOriginalValues.get(i).getTargetStartDate(),
	                                                         mOriginalValues.get(i).getTargetEndDate(),
	                                                         mOriginalValues.get(i).getTargetReminder(),
	                                                         mOriginalValues.get(i).getTargetStatus(),
	                                                         mOriginalValues.get(i).getTargetLastUpdated(),
	                                                         mOriginalValues.get(i).getTargetOverall(),
	                                                         mOriginalValues.get(i).getTargetGroupMembers(),
	                                                         mOriginalValues.get(i).getTargetMonth()
	                                                        ));
	                       }
	                   }
	                   // set the Filtered result to return
	                   results.count = FilteredArrList.size();
	                   results.values = FilteredArrList;
	               }
	               return results;
	           }
	        };
	        return filter;
	            }
	 public void updateAdapter(ArrayList<FacilityTargets> facilityList) {
	        this.facilityTargets= facilityList;

	        //and call notifyDataSetChanged
	        notifyDataSetChanged();
	    }
}
