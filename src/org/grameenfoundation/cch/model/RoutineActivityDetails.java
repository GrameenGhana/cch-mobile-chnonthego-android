package org.grameenfoundation.cch.model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.RoutinesDetailPagerAdapter;
import org.grameenfoundation.cch.activity.StayingWellActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RoutineActivityDetails extends Fragment {
	 
	private Context mContext;
	private DbHelper dbh;

	View rootView;
	private TextView title;
	private ListView listView_details;
	private TextView textView_clickHere;
	ViewPager mViewPager;
	public static final String TAG = RoutineActivityDetails.class.getSimpleName();

	public RoutineActivityDetails() { 
	
	}
	public RoutineActivityDetails(ViewPager mViewPager) { 
		this.mViewPager=mViewPager;
	}
	 
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 	mContext = getActivity().getApplicationContext();
		 	dbh = new DbHelper(mContext);
		 
		 	rootView = inflater.inflate(R.layout.routines_detail_pager_layout,null,false);
		    title = (TextView) rootView.findViewById(R.id.textView1);
		    listView_details=(ListView) rootView.findViewById(R.id.listView_routineDetail);
		    textView_clickHere = (TextView) rootView.findViewById(R.id.textView_back);
		    textView_clickHere.setOnClickListener(new OnClickListener(){

		    	@Override
				public void onClick(View v) {
						mViewPager.setCurrentItem(0, true);
				
				}
			});
		    
			ArrayList<RoutineActivity> todos = dbh.getSWRoutineActivities();
		   	if(todos!=null){	
		    if (todos.size()==0) {
		    	title.setText("No activites planned for this "+dbh.getTime()+"!"); 
		    } else {
				title.setText("  This " + dbh.getTime()+ "'s activities.");
		    	RoutinesDetailPagerAdapter adapter = new RoutinesDetailPagerAdapter(getActivity(), R.layout.routines_detail_listview_single, todos);
		    	adapter.notifyDataSetChanged();
		    	listView_details.setAdapter(adapter);	
		    	listView_details.setClickable(true);
		    	listView_details.setOnItemClickListener(new OnItemClickListener() {
		    		   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    		    // When clicked, show a toast with the TextView text
		    		    RoutineActivity item =  (RoutineActivity) parent.getItemAtPosition(position);
		    		    
		    		    Pattern p = Pattern.compile(".*?data\\-view=\"(.*?)\".*?");
						Matcher m = p.matcher(item.getAction());
						
						if (m.matches())
						{
							String url = "file:///android_asset/www/cch/modules/stayingwell/templates/"+m.group(1);
							Intent intent = new Intent(mContext, StayingWellActivity.class);								
							intent.putExtra("LOAD_URL", url);
							startActivity(intent);
						} 
		    		  }
		    	});
		    }
		   	}
		    return rootView;
	 }
}