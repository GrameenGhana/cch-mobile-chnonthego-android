package org.grameenfoundation.cch.model;



import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
    }
    
    public void getCalendarEvents()
    {
    	/*
    	String[] projection = new String[] { "title", "description", "begin", "eventLocation" };
    	String selection = "calendar_id = " + calID;
    	String path = "instances/when/" + (now - window) + "/" + (now + window);
    	String sortOrder = "begin DESC";
    	Cursor managedCursor = getCalendarManagedCursor(projection, selection, path, sortorder);
    	*/
    }
 
 
    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
    
    @JavascriptInterface
    public String getQuote() {
    	return "<p>Horray!! I am rocking this world!!!</p><footer><cite title=\"dkh\">gf</cite></footer>";
    }
    
    @JavascriptInterface
    public String getTodayInHistory() {
    	return "<h2>Today In History</h2><br><p><b>2011-02-03:</b> Horray!! I am rocking this world!!!</p>";
    }
}

