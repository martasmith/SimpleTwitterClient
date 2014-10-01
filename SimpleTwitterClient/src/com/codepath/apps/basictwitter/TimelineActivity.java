package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.adapters.EndlessScrollListener;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.data.UserPreferences;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	
	private final int REQUEST_CODE = 10;
	private TwitterClient client;
	private UserPreferences userPrefs;
	private User user;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	private long maxId = 0;
	private long sinceId = 0;
	private String tweetText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		userPrefs = new UserPreferences(this);
		populateTimeline();
		
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		
    	// set up scrolling mechanism
    	lvTweets.setOnScrollListener(new EndlessScrollListener() {	
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// here we need to extract max_id and since_id from tweet
				// pass it on to client.getHomeTimeline as argument
				populateTimeline();
			}
		});
    	
    	// we only need this on the next activity page, but if we don't do this here, 
    	// the user data will be empty upon the first compose activity view.
    	populateUserData();
	}
	
	
	public void populateTimeline() {
		client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				tweets = Tweet.fromJSONArray(json);
				if (tweets.size() != 0) {
					//last id in my currently returned data
					maxId = tweets.get(tweets.size() - 1).getUid();
					maxId -= 1;
					aTweets.addAll(Tweet.fromJSONArray(json));
				//Log.d("debug", json.toString());
				}
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(getBaseContext(), "Twitter has reached a rate limit. Please try again later.", Toast.LENGTH_LONG).show();
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	
	public void populateUserData() {
		client.getUserCredentials(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				//populate the user model with json data
				userPrefs.updateUserData(User.fromJSON(json));
				user = User.fromJSON(json);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(getBaseContext(), "Unable to get user credentials from Twitter.", Toast.LENGTH_LONG).show();
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});		
	}
	
	// This function was used originally to refresh timeline upon adding a new tweet
	// however it can be used to implement pull-to-refresh, so leaving it here
	public void refreshTimeline() {
		client.getHomeTimelineRefreshed(sinceId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				tweets = Tweet.fromJSONArray(json);
				if (tweets.size() != 0) {
					aTweets.addAll(Tweet.fromJSONArray(json));
				//Log.d("debug", json.toString());
				}
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("martas", "I'm in onActivityResult");
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			tweetText = data.getExtras().getString("tweetText");
			client.postStatusUpdate(tweetText, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject json) {
					//populate the tweet model with json data
					Tweet newTweet = Tweet.fromJSON(json);
					aTweets.insert(newTweet, 0);
				}
				
				@Override
				public void onFailure(Throwable e, String s) {
					Toast.makeText(getBaseContext(),"Posting to Twitter has failed. Please try again!", Toast.LENGTH_LONG).show();
					Log.d("debug", e.toString());
					Log.d("debug", s.toString());
				}
			});
		}
	}
	
	
    public void onCompose(MenuItem mi) {
    	Intent i = new Intent(TimelineActivity.this, ComposeTweetActivity.class);
    	startActivityForResult(i,REQUEST_CODE);	
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
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
}
