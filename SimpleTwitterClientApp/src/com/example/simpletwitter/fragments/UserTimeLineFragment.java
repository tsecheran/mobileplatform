package com.example.simpletwitter.fragments;

import org.json.JSONArray;

import com.example.simpletwitter.TweetsScrollListener;
import com.example.simpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class UserTimeLineFragment extends TweetListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client.getUserTimeLine(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray array) {
				addAll(Tweet.fromJSONArray(array));
			}
		});
	}

}
