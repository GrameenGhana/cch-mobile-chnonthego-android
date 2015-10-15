package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Collections;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.model.User;
import org.grameenfoundation.adapters.GroupParticipantsAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

public class GroupParticipantsSelectActivity extends Activity {

	private CheckBox self;
	private ListView list_group;
	private ArrayList<String> groupmembernames;
	private DbHelper db;
	private GroupParticipantsAdapter adapter;
	private Button submit;
	private CheckBox cb;
	private ArrayList<User> fullName;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_group_participants_select);
	    self=(CheckBox) findViewById(R.id.checkBox_self);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Group Participants");
	    db=new DbHelper(GroupParticipantsSelectActivity.this);
	    list_group=(ListView) findViewById(R.id.listView_groups);
	    groupmembernames=db.getAllGroupMembers();
	    fullName=new ArrayList<User>();
	    fullName=db.getUserFullName();
	    self.setText(fullName.get(0).getFirstname()+" "+fullName.get(0).getLastname()+" (me)");
		 Collections.sort(groupmembernames,String.CASE_INSENSITIVE_ORDER);
		 adapter=new GroupParticipantsAdapter(GroupParticipantsSelectActivity.this, groupmembernames);
		 list_group.setAdapter(adapter);
		 submit=(Button) findViewById(R.id.button_select);
		 submit.setOnClickListener(new OnClickListener() {
			
			private StringBuilder result;
			private Intent returnIntent;
			private String self_value;

			@Override
			public void onClick(View v) {
				result=new StringBuilder();
				for (int i=0;i<list_group.getChildCount();i++){
					cb=(CheckBox)list_group.getChildAt(i).findViewById(R.id.uuid);
					if(cb.isChecked()){	
						 System.out.println(result.toString());
						result.append(list_group.getItemAtPosition(i).toString()+", ");
					}if(self.isChecked()){
						self_value=self.getText().toString();
					}else{
						self_value="";
						//setResult(RESULT_OK,returnIntent);
					}	
					returnIntent=new Intent();
					returnIntent.putExtra("groups",result.toString()+self_value);
					setResult(RESULT_OK,returnIntent);
				}
				
				finish();
	
			}
		});
	}

}
