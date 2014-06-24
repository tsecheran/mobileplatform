package com.example.simpletwitter;

import java.util.List;

import com.example.simpletwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	public TweetArrayAdapter(Context context, List<Tweet> objects) {
		super(context, 0, objects);
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
		TextView tvUN = (TextView) v.findViewById(R.id.tvScreenName);
		TextView tvBdy = (TextView) v.findViewById(R.id.tvBody);
		TextView tvCreated = (TextView) v.findViewById(R.id.tvCreatedAt);
		imgIV.setImageResource(android.R.color.transparent);
		
		ImageLoader loader = ImageLoader.getInstance();
		loader.displayImage(tweet.getUser().getImageUrl(), imgIV);
		tvUN.setText("@" + tweet.getUser().getScreenName());
		tvBdy.setText(tweet.getBody());
		tvCreated.setText(tweet.getCreatedAt());
		
		return v;
	}
	
}
