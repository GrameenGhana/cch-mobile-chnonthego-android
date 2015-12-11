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

public class XmlGuiSectionHeader extends LinearLayout {
	TextView label;
	
	@SuppressLint("NewApi")
	public XmlGuiSectionHeader(Context context,String labelText,String initialText) {
		super(context);
		label = new TextView(context);
		label.setText(labelText);
		//label.setTextColor(getResources().getColor(R.color.Brown));
		label.setTextSize(17);
		label.setTextAlignment(TEXT_ALIGNMENT_CENTER);
		label.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		this.addView(label);
	}

	public XmlGuiSectionHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	

}
