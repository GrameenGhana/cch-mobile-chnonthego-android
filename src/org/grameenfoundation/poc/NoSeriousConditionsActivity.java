package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NoSeriousConditionsActivity extends BaseActivity {

//	Context mContext;
	private ListView listView_noConditions;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_no_serious_conditions);
		mContext = NoSeriousConditionsActivity.this;
		listView_noConditions = (ListView) findViewById(R.id.listView_noConditions);
		String[] items = {
				"Asymmetrical limb movement, one limb does not move",
				"Firm swelling/bump on one or both sides of head",
				"Bruises, swelling on buttocks ", "No injuries" };
		NoConditionsListAdapter adapter = new NoConditionsListAdapter(mContext,
				items);
		listView_noConditions.setAdapter(adapter);
		listView_noConditions.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch (position) {
				case 0:
					intent = new Intent(mContext,
							TakeActionNoConditionsActivity.class);
					intent.putExtra("category", "asymmetrical");
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(mContext,
							TakeActionNoConditionsActivity.class);
					intent.putExtra("category", "firm swelling");
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(mContext,
							TakeActionNoConditionsActivity.class);
					intent.putExtra("category", "firm swelling");
					startActivity(intent);
					break;
				case 3:
					intent = new Intent(mContext, NoInjuriesActivity.class);
					startActivity(intent);
					break;
				}

			}

		});
	}

	class NoConditionsListAdapter extends BaseAdapter {
		Context mContext;
		String[] listItems;
		public LayoutInflater minflater;

		public NoConditionsListAdapter(Context mContext, String[] listItems) {
			this.mContext = mContext;
			this.listItems = listItems;
			minflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			return listItems.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = minflater.inflate(R.layout.listview_text_single,
						parent, false);
			}
			TextView text = (TextView) convertView
					.findViewById(R.id.textView_listViewText);
			text.setText(listItems[position]);
			return convertView;
		}

	}
}
