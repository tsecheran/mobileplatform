package com.example.simpletwitter.fragments;

import java.util.ArrayList;

import com.example.simpletwitter.ProfileActivity;
import com.example.simpletwitter.R;
import com.example.simpletwitter.SimpleTwtrClient;
import com.example.simpletwitter.TweetArrayAdapter;
import com.example.simpletwitter.TweetArrayAdapter.TweetItemListener;
import com.example.simpletwitter.TwitterClientApp;
import com.example.simpletwitter.models.Tweet;
import com.example.simpletwitter.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TweetListFragment extends Fragment implements TweetItemListener {

	
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	 SimpleTwtrClient client = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterClientApp.getRestClient();
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		lvTweets = (ListView) v.findViewById(R.id.tweetsLV);
		lvTweets.setAdapter(aTweets);
		/**lvTweets.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(view.getId() == R.id.ivProfileImage ) { //v.findViewById(R.id.ivProfileImage).getId()) {
					Log.d("debug", "Clicked on the user image profile");
				}
			}
		});**/
		return v;
	}
	
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	
	public interface ImageViewClickedListener {
		public void onClickedUser(User user);
	}

	@Override
	public void onCickUserProfile(User user) {
		Intent i = new Intent(getActivity(), ProfileActivity.class);
		i.putExtra("userprofile", user);
		startActivity(i);
	}

	@Override
	public void onClickTweet() {
		
	}
}
