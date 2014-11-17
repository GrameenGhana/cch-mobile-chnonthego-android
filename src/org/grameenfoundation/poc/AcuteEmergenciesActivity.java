package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.AboutActivity;
import org.grameenfoundation.cch.activity.HomeActivity;
import org.grameenfoundation.chnonthego.MainScreenActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AcuteEmergenciesActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {

	private ListView listView_acuteEmergencies;
//	private Context mContext;
	private Button button_acuteEmergenciesNo;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acuteemergencies);
		mContext = AcuteEmergenciesActivity.this;
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle("Point of Care> Acute Emergencies");
		listView_acuteEmergencies = (ListView) findViewById(R.id.listView_acuteEmergencies);
		String[] emergencies = { "Difficulty breathing",
				"Oedema(feet and hands, face or ankles)", "Excessive bleeding",
				"Signs of shock" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_list_item_1, emergencies);
		listView_acuteEmergencies.setAdapter(adapter);
		listView_acuteEmergencies.setOnItemClickListener(this);
		button_acuteEmergenciesNo = (Button) findViewById(R.id.button_acuteEmergenciesNo);
		button_acuteEmergenciesNo.setOnClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		String extra_info;
		switch (position) {
		case 0:
			extra_info = "Difficulty breathing";
			intent = new Intent(mContext, TakeActionActivity.class);
			intent.putExtra("take_action", extra_info);
			startActivity(intent);
			break;
		case 1:
			extra_info = "Refer patient now";
			intent = new Intent(mContext, TakeActionActivity.class);
			intent.putExtra("take_action", extra_info);
			startActivity(intent);
			break;

		case 2:
			// extra_info="Refer patient now";
			intent = new Intent(mContext, AskBleedingActivity.class);
			// intent.putExtra("take_action", extra_info);
			startActivity(intent);
			break;
		case 3:
			extra_info = "Shock";
			intent = new Intent(mContext, TakeActionActivity.class);
			intent.putExtra("take_action", extra_info);
			startActivity(intent);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.button_acuteEmergenciesNo:
			intent = new Intent(mContext, PreviousVisitActivity.class);
			startActivity(intent);
			break;
		}

	}
	
	
	

}
