package com.example.simpletwitter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simpletwitter.TweetDialogFragment.TweetDialogListener;
import com.example.simpletwitter.models.Tweet;
import com.example.simpletwitter.models.User;

public class TimeLineActivity extends FragmentActivity implements TweetDialogListener { //Activity {
	private SimpleTwtrClient client = null;
	
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	
	private User currentUser = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		client = TwitterClientApp.getRestClient();
		populateTimeLine();
		lvTweets = (ListView) findViewById(R.id.tweetsLV);
		tweets = new ArrayList<Tweet>();
		//aTweets = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(scrollListener);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_menu, menu);
		return true;
	}

	public void onComposeTweetAction(MenuItem mi) {
		Toast.makeText(this, "Composing Tweet", Toast.LENGTH_SHORT).show();
		if( currentUser == null) {
			// Just get the user details once
			client.getCurrentUserDetails(new UserResponseHandler());
		}
		FragmentManager fm = getSupportFragmentManager();
		TweetDialogFragment tweetDialog = TweetDialogFragment.newInstance();
		tweetDialog.show(fm, "tweet_dialog");
	}
	
	public void populateTimeLine() {
		//aTweets.clear();
		client.getHomeTimeLine(new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable t, String s) {
				Log.d("debug", t.toString());
				Log.d("debug", s);
			}

			@Override
			public void onSuccess(JSONArray json) {
				//tweets.clear();
				aTweets.addAll(Tweet.fromJSONArray(json));
				Log.d("debug", json.toString());
			}
		});
	}
	
	public TweetsScrollListener scrollListener = new TweetsScrollListener() {
		private int sinceId = 21;
		private int maxId = 0;
		private int currentPage = 0;
		@Override
		public void loadMoreTweets(int page, int totalCount) {
			if(page > 0 && page > currentPage) {
				sinceId = sinceId + 20;
				maxId = maxId + sinceId;
			} else {
				return;
			}
			Log.d("debug", "Page " + page);
			//maxId = maxId + sinceId;
			client.getHomeTimeLine(new TweetsResponseHandler(), sinceId, maxId);
			currentPage = page;
		}
	};
	
	class TweetsResponseHandler extends JsonHttpResponseHandler {
		@Override
		public void onFailure(Throwable t, String s) {
			Log.d("debug", t.toString());
			Log.d("debug", s);
		}

		@Override
		public void onSuccess(JSONArray json) {
			//aTweets.clear();
			aTweets.addAll(Tweet.fromJSONArray(json));
			Log.d("debug", json.toString());
		}
	}
	
	class UserResponseHandler extends JsonHttpResponseHandler {
		@Override
		public void onFailure(Throwable t, String s) {
			Log.d("debug", t.toString());
			Log.d("debug", s);
		}

		@Override
		public void onSuccess(JSONObject json) {
			//currentUser = User.fromJSON(json);
			Log.d("debug", json.toString());
		}
	}

	@Override
	public void onTweetDialogFinished(String tweetBody) {
		Toast.makeText(this, "Tweeting... :-)" + tweetBody, Toast.LENGTH_SHORT).show();
		client.postTweet(tweetBody, new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable t, String s) {
				Log.d("debug", t.toString());
				Log.d("debug", s);
			}

			@Override
			public void onSuccess(JSONObject json) {
				Log.d("debug", "Tweet posted " + json.toString());
			}
		});
		// Once posted the new tweet just clear up the old tweets
		//populateTimeLine();
		// Add the new tweet to aTweet
		//Tweet tweet = new Tweet();
		//tweet.
	}
	
	public String getFormattedDate(Date date) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
		
		return sf.format(date);
	}
}
