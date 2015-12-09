package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Collections;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.model.User;
import org.grameenfoundation.adapters.GroupParticipantsAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

public class GroupParticipantsSelectActivity extends Activity {

	private CheckBox self;
	private ArrayList<User> groupmembernames;
	private DbHelper db;
	private GroupParticipantsAdapter adapter;
	private Button submit;
	private CheckBox cb;
	private ArrayList<User> fullName;
	private LinearLayout parentView;
	private LinearLayout childViewOne;
	private ArrayList<CheckBox> allCb;
	private JSONObject group_members;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_group_participants_select);
	    self=(CheckBox) findViewById(R.id.checkBox_self);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Group Participants");
	    db=new DbHelper(GroupParticipantsSelectActivity.this);
	   // list_group=(ListView) findViewById(R.id.listView_groups);
	    group_members=new JSONObject();
	    groupmembernames=db.getAllGroupMembers();
	    allCb = new ArrayList<CheckBox>();
	    parentView=(LinearLayout) findViewById(R.id.parent_view);
		 
	    parentView.removeAllViews();
	    fullName=new ArrayList<User>();
	    fullName=db.getUserFullName();
	    self.setText(fullName.get(0).getFirstname()+" "+fullName.get(0).getLastname()+" (me)");
		// Collections.sort(groupmembernames,String.CASE_INSENSITIVE_ORDER);
		 submit=(Button) findViewById(R.id.button_select);
	    try{
	    	for(int i=0;i<groupmembernames.size();i++){
	    		childViewOne=new LinearLayout(GroupParticipantsSelectActivity.this);
	   		    childViewOne.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	   			childViewOne.setOrientation(LinearLayout.HORIZONTAL);
	    		cb=new CheckBox(GroupParticipantsSelectActivity.this);
	    		cb.setText(groupmembernames.get(i).getFirstname()+ " "+ groupmembernames.get(i).getLastname());
	    		childViewOne.addView(cb);
	    		allCb.add(cb);
	    	 	parentView.addView(childViewOne);
	    	}
	   
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		
		 submit.setOnClickListener(new OnClickListener() {
			
			private StringBuilder result;
			private Intent returnIntent;
			private String self_value;
			private StringBuilder ids;
			private String self_id;

			@Override
			public void onClick(View v) {
				result=new StringBuilder();
				ids=new StringBuilder();
				//result.delete(0, result.length());
				for (int i=0;i<parentView.getChildCount();i++){
					if(allCb.get(i).isChecked()){	
						 System.out.println(result.toString());
						result.append(allCb.get(i).getText().toString()+", ");
						ids.append(groupmembernames.get(i).getStaffId()+",");
					}if(self.isChecked()){
						self_value=self.getText().toString();
						self_id=fullName.get(0).getStaffId();
					}else{
						self_value="";
						self_id="";
						//setResult(RESULT_OK,returnIntent);
					}	
					returnIntent=new Intent();
					returnIntent.putExtra("groups",result.toString()+self_value);
					returnIntent.putExtra("group_ids",ids.toString()+self_id);
					setResult(RESULT_OK,returnIntent);
				}
				
				finish();
	
			}
		});
	}

}
