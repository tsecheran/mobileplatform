package com.example.simpletwitter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.example.simpletwitter.fragments.HomeTimeLineFragment;
import com.example.simpletwitter.fragments.MentionsTimeLineFragment;
import com.example.simpletwitter.fragments.TweetDialogFragment;
import com.example.simpletwitter.fragments.TweetListFragment;
import com.example.simpletwitter.fragments.TweetDialogFragment.TweetDialogListener;
import com.example.simpletwitter.listeners.FragmentTabListener;
import com.example.simpletwitter.models.Tweet;
import com.example.simpletwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimeLineActivity extends FragmentActivity implements TweetDialogListener { //Activity {
	private SimpleTwtrClient client = null;
	
	private TweetListFragment tweetsFragment = null;
	
	private User currentUser = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar bar = getActionBar(); // or MainActivity.getInstance().getActionBar()
        bar.setBackgroundDrawable(new ColorDrawable(0xff00DDED));
        bar.setDisplayShowTitleEnabled(false);  // required to force redraw, without, gray color
        bar.setDisplayShowTitleEnabled(true);
        client = TwitterClientApp.getRestClient();
        
        //tweetsFragment = (TweetListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_time_line);
		setContentView(R.layout.activity_time_line);
		setupTabs();
		
		//aTweets = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
		
		
		//lvTweets.setOnScrollListener(scrollListener);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_menu, menu);
		return true;
	}

	public void onProfileAction(MenuItem mi) {
		Toast.makeText(this, "Getting Profile", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, ProfileActivity.class);
		
		startActivity(i);
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
	
	
	
	public TweetsScrollListener scrollListener = new TweetsScrollListener() {
		private int sinceId = 1;
		private int maxId = 0;
		@Override
		public void loadMoreTweets(int page, int totalCount) {
			Log.d("debug", "Page " + page);
			//maxId = maxId + sinceId;
			if(page > 2) {
				//client.getHomeTimeLine(new TweetsResponseHandler(), sinceId, tweets.get(tweets.size()-1).getUid());
			}
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
			tweetsFragment.addAll(Tweet.fromJSONArray(json));
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
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home_tab)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimeLineFragment>(R.id.flContainer, this, "home",
						HomeTimeLineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_metions_tab)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimeLineFragment>(R.id.flContainer, this, "mentions",
			    		MentionsTimeLineFragment.class));

		actionBar.addTab(tab2);
	}
	
	public String getFormattedDate(Date date) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
		
		return sf.format(date);
	}
}
