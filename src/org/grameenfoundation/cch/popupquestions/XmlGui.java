/*
 * XmlGui application.
 * Written by Frank Ableson for IBM Developerworks
 * June 2010
 * Use the code as you wish -- no warranty of fitness, etc, etc.
 */
package org.grameenfoundation.cch.popupquestions;


import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.util.Log;

public class XmlGui extends Activity {
	final String tag = XmlGui.class.getName();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btnRunForm = (Button) this.findViewById(R.id.btnRunForm);
        btnRunForm.setOnClickListener(new Button.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		EditText formNumber = (EditText) findViewById(R.id.formNumber);
        		Log.i(tag,"Attempting to process Form # [" + formNumber.getText().toString() + "]");
        		Intent newFormInfo = new Intent(XmlGui.this,RunForm.class);
        		newFormInfo.putExtra("formNumber", formNumber.getText().toString());
        		startActivity(newFormInfo);
        	}
        });
    }
} 