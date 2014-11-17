package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NoInjuriesActivity extends BaseActivity {


	private ListView listView_noInjuries;
//	Context mContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_no_injuries);
		mContext = NoInjuriesActivity.this;
		listView_noInjuries = (ListView) findViewById(R.id.listView_noInjuries);
		String[] items = { "Club Foot", "Cleft palate",
				"Unusual Appearance, other abnormalities",
				"No Injuries (Check baby for diarrhoea) " };
		NoInjuriesListAdapter adapter = new NoInjuriesListAdapter(mContext,
				items);
		listView_noInjuries.setAdapter(adapter);
		listView_noInjuries.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch (position) {
				case 0:
					intent = new Intent(mContext,
							TakeActionNoInjuriesActivity.class);
					intent.putExtra("category", "club foot");
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(mContext,
							TakeActionNoInjuriesActivity.class);
					intent.putExtra("category", "cleft palate");
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(mContext,
							TakeActionNoInjuriesActivity.class);
					intent.putExtra("category", "unusual appearance");
					startActivity(intent);
					break;
				}

			}

		});
	}

	class NoInjuriesListAdapter extends BaseAdapter {
		Context mContext;
		String[] listItems;
		public LayoutInflater minflater;

		public NoInjuriesListAdapter(Context mContext, String[] listItems) {
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
