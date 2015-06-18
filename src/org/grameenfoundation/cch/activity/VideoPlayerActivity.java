package org.grameenfoundation.cch.activity;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {

	private VideoView mVideo;
	private String STREAM_URL;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_video);
	    mVideo = (VideoView) findViewById(R.id.videoView1);
	    MediaController controller = new MediaController(this);
	    mVideo.setMediaController(controller);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
        	STREAM_URL= extras.getString("url");
        }
	    mVideo.setVideoURI(Uri.parse(STREAM_URL));
	    mVideo.start();
	}
	@Override
	protected void onPause() {
	    super.onPause();
	    mVideo.suspend();
	}

	@Override
	protected void onResume() {
	    super.onResume();
	    mVideo.resume();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	}
}
