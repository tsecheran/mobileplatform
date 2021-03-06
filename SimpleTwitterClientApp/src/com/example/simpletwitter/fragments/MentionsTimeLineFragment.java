package com.example.simpletwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.activeandroid.util.Log;
import com.example.simpletwitter.SimpleTwtrClient;
import com.example.simpletwitter.TwitterClientApp;
import com.example.simpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimeLineFragment extends TweetListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		populateTimeLine();
	}
	
	public void populateTimeLine() {
		//aTweets.clear();
		client.getMentionsTimeLine(new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable t, String s) {
				Log.d("debug", t.toString());
				Log.d("debug", s);
			}

			@Override
			public void onSuccess(JSONArray json) {
				//tweets.clear();
				addAll(Tweet.fromJSONArray(json));
				Log.d("debug", json.toString());
			}
		});
	}

}
