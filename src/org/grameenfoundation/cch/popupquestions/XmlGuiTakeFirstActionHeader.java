/*
 * XmlGui application.
 * Written by Frank Ableson for IBM Developerworks
 * June 2010
 * Use the code as you wish -- no warranty of fitness, etc, etc.
 */
package org.grameenfoundation.cch.popupquestions;

import org.digitalcampus.mobile.learningGF.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XmlGuiTakeFirstActionHeader extends LinearLayout {
	TextView label;
	
	@SuppressLint("NewApi")
	public XmlGuiTakeFirstActionHeader(Context context,String labelText,String color_code) {
		super(context);
		label = new TextView(context);
		label.setText(labelText);
		if(color_code.equals("Red")){
			label.setTextColor(getResources().getColor(R.color.Red));
		}else if(color_code.equals("Amber")){
			label.setTextColor(getResources().getColor(R.color.Curry));
		}else if(color_code.equals("Green")){
			label.setTextColor(getResources().getColor(R.color.Green));
		}
		
		label.setTextSize(20);
		label.setTextAlignment(TEXT_ALIGNMENT_CENTER);
		label.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		this.addView(label);
	}

	public XmlGuiTakeFirstActionHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	

}
