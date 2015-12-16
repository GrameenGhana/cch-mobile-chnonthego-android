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
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class XmlGuiTextViewChoice extends LinearLayout {
	TextView label;
	
	public XmlGuiTextViewChoice(final Context context,String labelText,final String link) {
		super(context);
		  LinearLayout ll = new LinearLayout(context);
          ll.setOrientation(LinearLayout.VERTICAL);
          LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        		     LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          ll.setLayoutParams(layoutParams);
          layoutParams.setMargins(40, 0, 0, 0);
		
			 label = new TextView(context);
			 label.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		     if(!link.equals("")){
		    	 Drawable img = getContext().getResources().getDrawable( R.drawable.ic_click );
					img.setBounds( 0, 0, 40, 40 );
					label.setText(labelText);
					label.setCompoundDrawables(img, null, null, null);		
					label.setTextColor(getResources().getColor(R.color.WhileWaitingForTransport));
					label.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent=new Intent(context,POCDynamicActivity.class);
							intent.putExtra("link", link);
							context.startActivity(intent);
							
						}
					});
		     }else{
		    	 SpannableString content = new SpannableString(labelText);
			     content.setSpan(new BulletSpan(15), 0, content.length(), 0);
			     label.setText(content);
		     }
		    
		     ll.addView(label,layoutParams);
	      
		 this.addView(ll);
		
	}

	public XmlGuiTextViewChoice(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	

}
