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
	
	public static User fromJSON(JSONObject obj) {
		User user = new User();
		try {
			user.uid = obj.getLong("id");
			user.name = obj.getString("name");
			user.imageUrl = obj.getString("profile_image_url");
			user.screenName = obj.getString("screen_name");
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

	@Override
	public String toString() {
		return "User [uid=" + uid + ", name=" + name + ", screenName="
				+ screenName + ", imageUrl=" + imageUrl + "]";
	}

}
