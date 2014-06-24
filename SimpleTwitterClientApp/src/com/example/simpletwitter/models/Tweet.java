package com.example.simpletwitter.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.DateUtils;

public class Tweet {
	private String body;
	private String createdAt;
	private long uid;
	private User user;
	
	
	
	public void setBody(String body) {
		this.body = body;
	}



	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}



	public void setUid(long uid) {
		this.uid = uid;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public String getBody() {
		return body;
	}



	public String getCreatedAt() {
		//return createdAt;
		return getRelativeTimeAgo(createdAt);
	}



	public long getUid() {
		return uid;
	}



	public User getUser() {
		return user;
	}


	public static Tweet fromJSON(JSONObject jsonObj) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = jsonObj.getString("text");
			tweet.uid = jsonObj.getLong("id");
			tweet.createdAt = jsonObj.getString("created_at");
			tweet.user = User.fromJSON(jsonObj.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}
	
	public static ArrayList<Tweet> fromJSONArray(JSONArray array) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		for(int i = 0; i < array.length(); i++ ) {
			try {
				Tweet tweet = fromJSON(array.getJSONObject(i));
				if(tweet != null) {
					tweets.add(tweet);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return tweets;
	}

	public static String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return relativeDate;
	}

	@Override
	public String toString() {
		return "Tweet [body=" + body + ", createdAt=" + createdAt + ", uid="
				+ uid + ", user=" + user + "]";
	}
	
}
