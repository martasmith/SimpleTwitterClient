package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.codepath.apps.basictwitter.data.UserPreferences;
import com.codepath.apps.basictwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeTweetActivity extends Activity {
	
	private final int MAX_TWEET_LENGTH = 140;
	private TextView tvUserNameCompose, tvFullNameCompose, tvTweetCount;	
	private EditText etTweet;
	private ImageView imgProfilePictureCompose;
	private User user;
	private UserPreferences userPrefs;
	private String tweetText;
	private Button btnTweet;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		userPrefs = new UserPreferences(this);
		tvFullNameCompose = (TextView) findViewById(R.id.tvFullNameCompose);
		tvUserNameCompose = (TextView) findViewById(R.id.tvUserNameCompose);
		tvTweetCount =  (TextView) findViewById(R.id.tvTweetCount);
		etTweet = (EditText) findViewById(R.id.etTweet);
		imgProfilePictureCompose = (ImageView) findViewById(R.id.imgProfilePictureCompose);
		btnTweet = (Button) findViewById(R.id.btnTweet);
		btnTweet.setEnabled(false);
		
		setupTweetListener();
		populateView();
		
	}

	private void setupTweetListener() {
		etTweet.addTextChangedListener(new TextWatcher(){
			
			@Override
			public void afterTextChanged(Editable s) {
				int currentTweetLength = etTweet.length();
				Integer charsLeft = MAX_TWEET_LENGTH - currentTweetLength;
				tvTweetCount.setText(charsLeft.toString());
				if (charsLeft == 140 || charsLeft < 0) {
					if (charsLeft < 0) {
						tvTweetCount.setTextColor(Color.RED);
					}
					btnTweet.setEnabled(false);	
				} else {
					btnTweet.setEnabled(true);
					tvTweetCount.setTextColor(Color.BLACK);
				}
			}

			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// do nothing
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int count, int after) {
				// do nothing	
			}
			
		});
		
	}
	
	protected void populateView() {
		user = userPrefs.getUserData();
		Log.d("debug prefs", "name: " + user.getName() + ", screenname: " + user.getScreenName() + ", profileImg url: " + user.getProfileImageUrl());
		tvFullNameCompose.setText(user.getName());
		tvUserNameCompose.setText("@" + user.getScreenName());
		imgProfilePictureCompose.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(user.getProfileImageUrl(), imgProfilePictureCompose);
	}
	
	public void onTweet(View v) {
		//get the tweet value from the edit text
		Log.d("martas", "I'm in onTweet");
		tweetText = etTweet.getText().toString();
		Log.d("martas", "Tweet text: " + tweetText);
		Intent data = new Intent();
		data.putExtra("tweetText", etTweet.getText().toString());
		setResult(RESULT_OK, data);
		finish();
	}

	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	*/
}
