package com.example.simpletwitter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.simpletwitter.models.Tweet;
import com.example.simpletwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	TweetItemListener listener = null;
	public TweetArrayAdapter(Context context, List<Tweet> objects) {
		super(context, 0, objects);
	}
	
	public TweetArrayAdapter(Context context, List<Tweet> objects, Fragment fragment) {
		super(context, 0, objects);
		listener = (TweetItemListener) fragment;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		View v;
		if( convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
		ImageView imgIV = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvRN = (TextView) v.findViewById(R.id.tvRealName);
		TextView tvUN = (TextView) v.findViewById(R.id.tvScreenName);
		TextView tvBdy = (TextView) v.findViewById(R.id.tvBody);
		TextView tvCreated = (TextView) v.findViewById(R.id.tvCreatedAt);
		imgIV.setImageResource(android.R.color.transparent);
		
		ImageLoader loader = ImageLoader.getInstance();
		loader.displayImage(tweet.getUser().getImageUrl(), imgIV);
		tvRN.setText(tweet.getUser().getName());
		tvUN.setText("@" + tweet.getUser().getScreenName());
		tvBdy.setText(tweet.getBody());
		tvCreated.setText(tweet.getCreatedAt());
		imgIV.setTag(tweet.getUser());
		imgIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				User user = (User) v.getTag();
				listener.onCickUserProfile(user);
			}
		});
		
		return v;
	}
	
	public interface TweetItemListener {
		public void onCickUserProfile(User user);
		public void onClickTweet();
	}
	
}
