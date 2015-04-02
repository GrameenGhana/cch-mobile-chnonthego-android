package org.grameenfoundation.cch.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.widget.EditText;
import android.widget.TextView;

public class Validation {

	 private static final String REQUIRED_MSG = "required";
	 public static boolean hasTextEditText(EditText editText) {
		 
	        String text = editText.getText().toString().trim();
	        editText.setError(null);
	 
	        // length 0 means there is no text
	        if (text.length() == 0) {
	        	editText.requestFocus();
	            editText.setError(REQUIRED_MSG);
	            return false;
	        }
	 
	        return true;
	    }								
	 
	 public static boolean hasTextTextView(TextView textView) {
		 
	        String text = textView.getText().toString().trim();
	        textView.setError(null);
	 
	        // length 0 means there is no text
	        if (text.length() == 0) {
	        	textView.requestFocus();
	        	textView.setError(REQUIRED_MSG);
	            return false;
	        }
	 
	        return true;
	    }
	 
	 public static boolean isDateAfter(String startDate,String endDate,TextView text)
	    {
		 Date start_date = null;
		 Date end_date = null;
		long starDateAsTimestamp = 0;
		long endDateTimestamp = 0;
		try {
			if(startDate==null){
			System.out.println("Enter a valid date!");
			}else{
			start_date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
			 starDateAsTimestamp = start_date.getTime();
			}
			if(endDate==null){
				System.out.println("Enter a valid date!");
			}else{
			end_date = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
			endDateTimestamp = end_date.getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		 long getRidOfTime = 1000 * 60 * 60 * 24;
		 long startDateAsTimestampWithoutTime = starDateAsTimestamp / getRidOfTime;
		 long endDateTimestampWithoutTime = endDateTimestamp / getRidOfTime;
		 if(startDate!=null&&endDate!=null&&startDateAsTimestampWithoutTime > endDateTimestampWithoutTime){
			  text.requestFocus();
			  text.setError("Start date cannot be after due date");
		    return true; 
		 } else {
		    return false;
 }
	    }
	    
	 
}
