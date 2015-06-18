/*
 * XmlGui application.
 * Written by Frank Ableson for IBM Developerworks
 * June 2010
 * Use the code as you wish -- no warranty of fitness, etc, etc.
 */
package org.grameenfoundation.cch.popupquestions;

import org.digitalcampus.mobile.learningGF.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XmlGuiTextViewPreamble extends LinearLayout {
	TextView label;
	
	public XmlGuiTextViewPreamble(Context context,String labelText,String initialText) {
		super(context);
		label = new TextView(context);
		label.setText(labelText);
		label.setTextColor(getResources().getColor(R.color.TextColorWine));
		label.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		
		this.addView(label);
	}

	public XmlGuiTextViewPreamble(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	

}
