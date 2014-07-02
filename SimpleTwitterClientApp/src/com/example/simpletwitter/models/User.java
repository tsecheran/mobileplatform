package com.example.simpletwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8942537934041099247L;
	private long uid;
	private String name;
	private String screenName;
	private String imageUrl;
	private String description;
	private int followers;
	private int following;
	
	public static User fromJSON(JSONObject obj) {
		User user = new User();
		try {
			user.uid = obj.getLong("id");
			user.name = obj.getString("name");
			user.imageUrl = obj.getString("profile_image_url");
			user.screenName = obj.getString("screen_name");
			user.description = obj.getString("description");
			user.followers = obj.getInt("followers_count");
			user.following = obj.getInt("friends_count");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	public long getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getTagLine() {
		return description;
	}
	
	public int getFollowersCount() {
		return followers;
	}
	
	public int getFollowingCount() {
		return following;
	}
	
	@Override
	public String toString() {
		return "User [uid=" + uid + ", name=" + name + ", screenName="
				+ screenName + ", imageUrl=" + imageUrl + "]";
	}

}
