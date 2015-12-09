/*
 * XmlGui application.
 * Written by Frank Ableson for IBM Developerworks
 * June 2010
 * Use the code as you wish -- no warranty of fitness, etc, etc.
 */
package org.grameenfoundation.cch.popupquestions;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XmlGuiDefinition extends LinearLayout {
	TextView label;
	
	public XmlGuiDefinition(Context context,String labelText,String options) {
		super(context);
		String []opts = options.split("\\|");
		  LinearLayout ll = new LinearLayout(context);
          ll.setOrientation(LinearLayout.VERTICAL);
          LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        		     LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          
          layoutParams.setMargins(40, 0, 0, 0);
		 for (int i=0;i<opts.length;i++){
			 label = new TextView(context);
			 label.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
			 SpannableString content = new SpannableString(opts[i]);
		     content.setSpan(new BulletSpan(15), 0, content.length(), 0);
		     label.setText(content);
		     ll.addView(label,layoutParams);
		    
		 }
	      
		 this.addView(ll);
		
	}

	public XmlGuiDefinition(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	

}
