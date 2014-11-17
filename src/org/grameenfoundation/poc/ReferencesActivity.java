package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.cch.model.WebAppInterface;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ReferencesActivity extends BaseActivity {

	private WebView myWebView;
	private String url;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = ReferencesActivity.this;
		setContentView(R.layout.activity_third_trimester_counselling);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			url = extras.getString("url");
		}
		myWebView = (WebView) findViewById(R.id.webView_thirdTrimesterCounselling);
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
		myWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		myWebView.loadUrl(url);
		myWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onReceivedError(WebView view, int errorCod,
					String description, String failingUrl) {
				Toast.makeText(view.getContext(), description,
						Toast.LENGTH_LONG).show();
			}

		});
	}

}
