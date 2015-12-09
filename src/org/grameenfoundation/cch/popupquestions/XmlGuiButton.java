/*
 * XmlGui application.
 * Written by Frank Ableson for IBM Developerworks
 * June 2010
 * Use the code as you wish -- no warranty of fitness, etc, etc.
 */
package org.grameenfoundation.cch.popupquestions;

import org.digitalcampus.mobile.learningGF.R;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XmlGuiButton extends LinearLayout {
	Button label;
	
	public XmlGuiButton(final Context context,String labelText,String color,final String link) {
		super(context);
		label = new Button(context);
		//
		if(color.equals("Green")){
			label.setBackgroundColor(getResources().getColor(R.color.Green));
			label.setTextColor(getResources().getColor(R.color.White));
		}else if(color.equals("Amber")){
			label.setBackgroundColor(getResources().getColor(R.color.Curry));
			label.setTextColor(getResources().getColor(R.color.White));
		}else if(color.equals("Red")){
			label.setBackgroundColor(getResources().getColor(R.color.Red));
			label.setTextColor(getResources().getColor(R.color.White));
		}
		label.setText(labelText);
		label.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		label.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context,POCDynamicActivity.class);
				intent.putExtra("link", link);
				context.startActivity(intent);
				
			}
		});
		this.addView(label);
	}

	public XmlGuiButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	

}
