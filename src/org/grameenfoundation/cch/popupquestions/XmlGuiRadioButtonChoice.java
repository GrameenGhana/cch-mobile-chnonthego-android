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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class XmlGuiRadioButtonChoice extends LinearLayout {
	String tag = XmlGuiRadioButtonChoice.class.getName();
	TextView label;
	ArrayAdapter<String> aa;
	RadioGroup choices;
	private RadioButton radioButton;
	
	public XmlGuiRadioButtonChoice(Context context,String labelText,String options) {
		super(context);
		label = new TextView(context);
		label.setText(labelText);
		this.addView(label);
		choices = new RadioGroup(context);
		choices.setOrientation(RadioGroup.VERTICAL);
		 this.addView(choices);
		
		String []opts = options.split("\\|");

		 for (int i=0;i<opts.length;i++){
			radioButton=new RadioButton(context);
			radioButton.setButtonDrawable(R.drawable.apptheme_btn_radio_holo_light);
			radioButton.setText(opts[i]);
			radioButton.setId((1*2)+i);
			choices.addView(radioButton);
		 }
	
	}

	public XmlGuiRadioButtonChoice(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	
	public String getValue()
	{
		String value;
		//return (String) spinner.getSelectedItem().toString();
		radioButton=(RadioButton) findViewById(choices.getCheckedRadioButtonId());
		value=radioButton.getText().toString();
		return (String)  value;
	}
	

}
