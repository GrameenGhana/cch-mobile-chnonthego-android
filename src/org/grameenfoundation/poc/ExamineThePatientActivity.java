package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ExamineThePatientActivity extends BaseActivity {

	private ListView listView_ask;
	private ListView listView_look;
	private ListView listView_check;
//	private Context mContext;
	private Button button_next;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_examine_patient);
		mContext = ExamineThePatientActivity.this;
		listView_ask = (ListView) findViewById(R.id.listView_ask);
		listView_look = (ListView) findViewById(R.id.listView_look);
		listView_check = (ListView) findViewById(R.id.listView_check);
		String[] ask_items = { "Severe headache", "Severe Abdominal Pains",
				"Excessive vomiting", "Blurred vision", "Bleeding",
				"Offensive/discoloured vaginal discharge", "Fever",
				"Difficulty breathing", "Epigastric pain", "Foetal movements" };
		String[] look_items = {
				"Examine conjunctiva, tongue, palms, and nail beds for palor",
				"Oedaema of the feet, hands face, ankles", "Bleeding",
				"Jaundice", "Signs of shock", "Offensive vaginal discharge" };
		String[] check_items = { "Blood pressure, if possible", "Temperature",
				"Pulse" };

		ListAdapter adapter1 = new ListAdapter(mContext, ask_items);
		ListAdapter adapter2 = new ListAdapter(mContext, look_items);
		ListAdapter adapter3 = new ListAdapter(mContext, check_items);
		listView_ask.setAdapter(adapter1);
		listView_look.setAdapter(adapter2);
		listView_check.setAdapter(adapter3);
		button_next = (Button) findViewById(R.id.button_next);
		button_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, AskHerActivity.class);
				startActivity(intent);

			}

		});
	}

	class ListAdapter extends BaseAdapter {
		Context mContext;
		String[] items;
		public LayoutInflater minflater;

		public ListAdapter(Context mContext, String[] items) {
			this.mContext = mContext;
			this.items = items;
			minflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
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
			text.setText(items[position]);
			if (position % 2 == 0) {
				convertView.setBackgroundColor(getResources().getColor(
						R.color.BackgroundGrey));
			} else {
				convertView.setBackgroundColor(getResources().getColor(
						R.color.White));
			}
			return convertView;
		}

	}
}
