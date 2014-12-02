package org.grameenfoundation.adapters;

import org.digitalcampus.mobile.learningGF.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TargetsViewCursorAdapter extends CursorTreeAdapter{

	public TargetsViewCursorAdapter(Cursor cursor, Context context) {
		super(cursor, context);
	
	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected View newGroupView(Context context, Cursor cursor,
			boolean isExpanded, ViewGroup parent) {
		 LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	        View retView = inflater.inflate(R.layout.listview_single, parent, false);
	 
	        return retView;
	}

	@Override
	protected void bindGroupView(View view, Context context, Cursor cursor,
			boolean isExpanded) {
		 TextView category=(TextView) view.findViewById(R.id.textView_textSingle);
		 category.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
	}

	@Override
	protected View newChildView(Context context, Cursor cursor,
			boolean isLastChild, ViewGroup parent) {
		 LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	        View retView = inflater.inflate(R.layout.event_listview_single, parent, false);
	        TextView text=(TextView) retView.findViewById(R.id.textView_eventCategory);
			   TextView text2=(TextView) retView.findViewById(R.id.textView_eventNumber);
			   TextView text3=(TextView) retView.findViewById(R.id.textView_eventPeriod);
			   TextView text4=(TextView) retView.findViewById(R.id.textView_dueDate);
			   TextView text5=(TextView) retView.findViewById(R.id.textView_startDate);
			   TextView text6=(TextView) retView.findViewById(R.id.textView_achieved);
			   TextView text7=(TextView) retView.findViewById(R.id.textView_lastUpdated);
			   TextView text8=(TextView) retView.findViewById(R.id.textView_percentageAchieved);
			   ImageView image=(ImageView) retView.findViewById(R.id.imageView1);
			   text.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
			   text2.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(10))));
			   text3.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
			   text4.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(6))));
			   text5.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(5))));
			   text7.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(9))));
			   int number_achieved_today=Integer.valueOf(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(7))));
			   //System.out.println(String.valueOf(number_achieved_today));
				  // int number_remaining_today=Integer.valueOf(todayEventNumberRemaining.get(childPosition));
				   Double percentage= ((double)number_achieved_today/Integer.valueOf(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(10)))))*100;	
				   //System.out.println(String.valueOf(percentage));
				   String percentage_achieved=String.format("%.0f", percentage);
			
				  text6.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(7))));   
			  
			   text8.setText(percentage_achieved+"%");
	        return retView;
	}

	@Override
	protected void bindChildView(View view, Context context, Cursor cursor,
			boolean isLastChild) {
		TextView text=(TextView) view.findViewById(R.id.textView_eventCategory);
		   TextView text2=(TextView) view.findViewById(R.id.textView_eventNumber);
		   TextView text3=(TextView) view.findViewById(R.id.textView_eventPeriod);
		   TextView text4=(TextView) view.findViewById(R.id.textView_dueDate);
		   TextView text5=(TextView) view.findViewById(R.id.textView_startDate);
		   TextView text6=(TextView) view.findViewById(R.id.textView_achieved);
		   TextView text7=(TextView) view.findViewById(R.id.textView_lastUpdated);
		   TextView text8=(TextView) view.findViewById(R.id.textView_percentageAchieved);
		   ImageView image=(ImageView) view.findViewById(R.id.imageView1);
		   text.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
		   text2.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(10))));
		   text3.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
		   text4.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(6))));
		   text5.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(5))));
		   text7.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(9))));
		   int number_achieved_today=Integer.valueOf(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(7))));
		   //System.out.println(String.valueOf(number_achieved_today));
			  // int number_remaining_today=Integer.valueOf(todayEventNumberRemaining.get(childPosition));
			   Double percentage= ((double)number_achieved_today/Integer.valueOf(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(10)))))*100;	
			   //System.out.println(String.valueOf(percentage));
			   String percentage_achieved=String.format("%.0f", percentage);
		
			  text6.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(7))));   
		  
		   text8.setText(percentage_achieved+"%");
		
	}

}
