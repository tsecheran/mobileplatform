package com.example.simpletwitter.fragments;

import org.json.JSONArray;

import com.example.simpletwitter.TweetsScrollListener;
import com.example.simpletwitter.models.Tweet;
import com.example.simpletwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class UserTimeLineFragment extends TweetListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		// Check whether its there
		if( getArguments().getSerializable("userprofile") != null) {
			User user = (User) getArguments().getSerializable("userprofile");
			client.getUserTimeLine(respHandler, user);
	    } else {
			client.getUserTimeLine(respHandler);
	    }
	}
	
	
	public static UserTimeLineFragment newInstance(User user) {
		Bundle args = new Bundle();
		args.putSerializable("userprofile", user);
		UserTimeLineFragment fragment = new UserTimeLineFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	private JsonHttpResponseHandler respHandler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONArray array) {
			addAll(Tweet.fromJSONArray(array));
		}
	};

}
