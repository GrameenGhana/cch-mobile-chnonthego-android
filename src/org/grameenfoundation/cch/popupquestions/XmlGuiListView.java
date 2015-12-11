package org.grameenfoundation.cch.popupquestions;

import org.digitalcampus.mobile.learningGF.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class XmlGuiListView extends LinearLayout {
	ListView label;
	
	public XmlGuiListView(final Context context,String[] labelText,String color,final String link) {
		super(context);
		label = new ListView(context);
		//label.setTextColor(getResources().getColor(R.color.White));
		label.setBackgroundColor(getResources().getColor(R.color.BackgroundGrey));
		label.setEnabled(true);;
		label.setDividerHeight(1);
	    label.setDivider(getResources().getDrawable(R.color.White));
		 ListAdapter adapter=new ListAdapter(context, labelText);
		 label.setAdapter(adapter);
		label.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		label.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(context,POCDynamicActivity.class);
				intent.putExtra("link", link);
				context.startActivity(intent);
			}
		});
		this.addView(label);
		
	}

	public XmlGuiListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	

}

class ListAdapter extends BaseAdapter{
	Context mContext;
	String[] items;
	 public LayoutInflater minflater;
	public ListAdapter(Context mContext,String[] items){
		this.mContext=mContext;
		this.items=items;
		 minflater = LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		return items.length;
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
		if( convertView == null ){
			  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
		    }
		 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
		 text.setText(items[position]);
		 
		    return convertView;
	}
	
}